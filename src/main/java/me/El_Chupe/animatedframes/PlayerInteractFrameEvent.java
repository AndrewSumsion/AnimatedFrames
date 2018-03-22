package me.El_Chupe.animatedframes;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerInteractFrameEvent extends Event {
    private static HandlerList handlerList = new HandlerList();

    private Player player;
    private Frame frame;
    private Action action;

    public PlayerInteractFrameEvent(Player player, Frame frame, Action action) {
        this.player = player;
        this.frame = frame;
        this.action = action;
    }

    public Player getPlayer() {
        return player;
    }

    public Frame getFrame() {
        return frame;
    }

    public Action getAction() {
        return action;
    }

    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public enum Action {
        INTERACT,
        ATTACK,
        INTERACT_AT
    }
}
