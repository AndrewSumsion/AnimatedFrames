package me.El_Chupe.animatedframes;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class FrameImage {
    private int height;
    private int width;
    private List<BufferedImage> images = new ArrayList<BufferedImage>();
    private List<MapView> mapViews = new ArrayList<MapView>();

    public FrameImage(int height, int width, BufferedImage image) {
        this.height = height;
        this.width = width;
        for(BufferedImage image1 : splitImage(image, height, width)) {
            images.add(MapPalette.resizeImage(image1));
        }
        for(final BufferedImage image1 : images) {
            MapView mapView = Bukkit.createMap(Bukkit.getWorlds().get(0));
            mapView.addRenderer(new MapRenderer() {
                @Override
                public void render(MapView mapView, MapCanvas mapCanvas, Player player) {
                    mapCanvas.drawImage(0, 0, image1);
                }
            });
            mapViews.add(mapView);
        }
    }
    public BufferedImage getImage(int right, int down) {
        int index = down * width;
        index += right;
        return images.get(index);
    }
    public MapView getMapView(int right, int down) {
        int index = down * width;
        index += right;
        return mapViews.get(index);
    }
    public List<BufferedImage> getImages() {
        return images;
    }
    public List<MapView> getMapViews() {
        return mapViews;
    }
    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
    private static BufferedImage[] splitImage(BufferedImage image, int rows, int cols) {
        int chunks = rows * cols;

        int chunkWidth = image.getWidth() / cols; // determines the chunk width and height
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage[] imgs = new BufferedImage[chunks]; //Image array to hold image chunks
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //Initialize the image array with image chunks
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

                // draws the image chunk
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        return imgs;
    }
}
