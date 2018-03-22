package me.El_Chupe.animatedframes;

import me.El_Chupe.packetlib.PacketDataSerializer;
import me.El_Chupe.packetlib.PacketType;
import me.El_Chupe.packetlib.event.PacketInEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class AnimatedFrames extends JavaPlugin implements Listener {
    private static AnimatedFrames instance;

    public static AnimatedFrames getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new Animations(), this);
        /*
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if(event.getPacketType() != PacketType.Play.Client.USE_ENTITY) return;
                int id = event.getPacket().getIntegers().read(0);
                if(!Frame.instances.containsKey(id)) return;
                event.setCancelled(true);

                int actionInt = event.getPacket().getIntegers().read(1);

                PlayerInteractFrameEvent.Action action = PlayerInteractFrameEvent.Action.RIGHT_CLICK;
                if(actionInt == 1) {
                    action = PlayerInteractFrameEvent.Action.LEFT_CLICK;
                }

                Bukkit.getPluginManager().callEvent(new PlayerInteractFrameEvent(event.getPlayer(), Frame.instances.get(id), action));
            }
        });
        */
    }

    @EventHandler
    private void onRightClick(PacketInEvent event) {
        if(!event.getPacket().getType().equals(PacketType.fromName("PacketPlayInUseEntity"))) return;
        PacketDataSerializer data = event.getPacket().getData();
        int id = data.readVarInt();
        if(!Frame.instances.containsKey(id)) return;
        PlayerInteractFrameEvent.Action action = PlayerInteractFrameEvent.Action.values()[data.readVarInt()];
        Bukkit.getPluginManager().callEvent(new PlayerInteractFrameEvent(event.getPlayer(), Frame.instances.get(id), action));
    }
}
