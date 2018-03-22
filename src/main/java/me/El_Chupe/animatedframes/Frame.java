package me.El_Chupe.animatedframes;

import me.El_Chupe.packetlib.PacketDataSerializer;
import me.El_Chupe.packetlib.PacketOutContainer;
import me.El_Chupe.packetlib.PacketType;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Frame {
    static final int STARTING_ID = 153865;
    static int counter = 0;
    private Location location;
    private BlockFace direction;
    private int id;
    private UUID uuid;
    private PacketOutContainer packet;

    static Map<Integer, Frame> instances = new HashMap<Integer, Frame>();

    public Frame(Location location, BlockFace direction) {
        this.location = location;
        this.direction = direction;
        id = STARTING_ID + counter;
        counter++;
        instances.put(id, this);

        /*PacketConstructor constructor = new PacketConstructor();
        constructor.writeVarInt(id);
        constructor.writeByte((byte) 71);
        constructor.writeInt((int)Math.floor(location.getX() * 32.0D));
        constructor.writeInt((int)Math.floor(location.getY() * 32.0D));
        constructor.writeInt((int)Math.floor(location.getZ() * 32.0D));
        constructor.writeByte((byte) 0);
        constructor.writeByte((byte) 0);
        int directionEnum = 0;
        switch (direction) {
            case SOUTH: directionEnum = 0; break;
            case WEST: directionEnum = 1; break;
            case NORTH: directionEnum = 3; break;
            case EAST: directionEnum = 4;
        }
        constructor.writeInt(directionEnum);
        constructor.writeShort((short) 0);
        constructor.writeShort((short) 0);
        constructor.writeShort((short) 0);
        packet = constructor.construct(PacketType.PacketPlayOutSpawnEntity);
        */

        PacketDataSerializer data = new PacketDataSerializer();
        data.writeVarInt(id);
        uuid = UUID.nameUUIDFromBytes(("frame-" + id).getBytes(StandardCharsets.US_ASCII));
        data.writeLong(uuid.getMostSignificantBits());
        data.writeLong(uuid.getLeastSignificantBits());
        data.writeByte(71);
        data.writeDouble(location.getX());
        data.writeDouble(location.getY());
        data.writeDouble(location.getZ());
        data.writeByte(0);
        int directionEnum = 0;
        switch (direction) {
            case SOUTH: directionEnum = 0; break;
            case WEST: directionEnum = 1; break;
            case NORTH: directionEnum = 2; break;
            case EAST: directionEnum = 3;
        }
        data.writeByte(directionEnum * 64);
        data.writeInt(directionEnum);
        data.writeShort(0);
        data.writeShort(0);
        data.writeShort(0);
        packet = new PacketOutContainer(PacketType.fromName("PacketPlayOutSpawnEntity"), data);
    }

    public void showToPlayer(Player player) {
        packet.send(player);
    }
    /*
    public void setMap(int mapId, Player player) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);
        packet.getIntegers().write(0, id);
        packet.getIntegers().write(1, 0);
        packet.getIntegers().write(2, 300);
        packet.getStrings().write(0, "");
        packet.getBooleans().write(0, false).write(1, false).write(2, false);
        packet.getItemModifier().write(0, new ItemStack(Material.MAP, 1, (byte)mapId));
        packet.getIntegers().write(3, 0);
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    watcher.setObject(0, (byte) 0);
        watcher.setObject(1, 300);
        watcher.setObject(2, "");
        watcher.setObject(3, false);
        watcher.setObject(4, false);
        watcher.setObject(5, false);
        watcher.setObject(6, new ItemStack(Material.MAP, 1, mapId));
        watcher.setObject(7, 0);

    */

    public void setMap(short mapId, Player player) {
        /*PacketConstructor constructor = new PacketConstructor();
        constructor.writeVarInt(id);
        constructor.writeByte((byte) 6);
        constructor.writeByte((byte) 5);
        constructor.writeItemStack(new ItemStack(Material.MAP, 1, mapId));
        constructor.writeByte((byte) 0xff);
        constructor.construct(PacketType.PacketPlayOutEntityMetadata).send(player);*/
        PacketDataSerializer data = new PacketDataSerializer();
        data.writeVarInt(id);
        data.writeByte(6);
        data.writeByte(5);
        data.writeItemStack(new ItemStack(Material.MAP, 1, mapId));
        data.writeByte(0xff);
        new PacketOutContainer(PacketType.fromName("PacketPlayOutEntityMetadata"), data).send(player);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Frame && ((Frame) object).getId() == id;
    }
}
