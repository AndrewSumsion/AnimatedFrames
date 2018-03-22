package me.El_Chupe.animatedframes;

import org.bukkit.entity.Player;
import org.bukkit.map.MapView;

public interface Animation {
    void init(Canvas canvas, Player player);
    void cycle(Canvas canvas, Player player);
    MapView[] getBuffer();
    void bufferPlayer(Player player);
}
