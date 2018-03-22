package me.El_Chupe.animatedframes;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Canvas {
    private Location location;
    private BlockFace direction;
    private int height;
    private int width;
    private final List<Frame> frames = new ArrayList<Frame>();

    public Canvas(Location location, BlockFace direction, int height, int width) {
        this.location = location;
        this.direction = direction;
        this.height = height;
        this.width = width;
        for(int y=0;y<height;y++) {
            for(int x=0;x<width;x++) {
                Frame frame = new Frame(location.clone().add(toVector(rotateCounterClockwise(direction)).multiply(x)).subtract(0, y, 0), direction);
                frames.add(frame);
            }
        }
    }

    public Frame getFrame(int right, int down) {
        int index = width * down;
        index += right;

        if (index + 1 > frames.size()) throw new IllegalArgumentException("Coordinates out of Canvas range");
        return frames.get(index);
    }

    public void render(FrameImage image, int right, int down, Player player) {
        for(int y=0;y<image.getHeight();y++) {
            for(int x=0;x<image.getWidth();x++) {
                getFrame(right + x, down + y).setMap(image.getMapView(x, y).getId(), player);
            }
        }
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void showToPlayer(Player player) {
        for(Frame frame : frames) {
            frame.showToPlayer(player);
        }
    }

    private static Vector toVector(BlockFace face) {
        return new Vector(face.getModX(), face.getModY(), face.getModZ());
    }

    private static BlockFace rotateCounterClockwise(BlockFace face) {
        switch(face){
            case NORTH: return BlockFace.WEST;
            case WEST: return BlockFace.SOUTH;
            case SOUTH: return BlockFace.EAST;
            case EAST: return  BlockFace.NORTH;
        }
        return face;
    }
}
