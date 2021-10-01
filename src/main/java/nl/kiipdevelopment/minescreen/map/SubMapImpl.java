package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.play.MapDataPacket;
import net.minestom.server.utils.PacketUtils;
import nl.kiipdevelopment.minescreen.MineScreen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;

public class SubMapImpl implements SubMap {
    private static MessageDigest messageDigest;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public final byte[] colors;
    private final short guiId;
    private final int mapX, mapY;
    private final int width, height;

    private byte[] prevHash;

    public SubMapImpl(short guiId, int mapX, int mapY, int width, int height) {
        this.colors = new byte[width * height];
        this.guiId = guiId;
        this.mapX = mapX;
        this.mapY = mapY;
        this.width = width;
        this.height = height;

        Arrays.fill(colors, (byte) 0);
    }

    @Override
    public int mapX() {
        return mapX;
    }

    @Override
    public int mapY() {
        return mapY;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int id() {
        return MineScreen.mapIdSupplier().get(guiId, mapX, mapY);
    }

    @Override
    public byte[] colors() {
        return colors;
    }

    @Override
    public void sendPacket(Collection<Player> players) {
        MapDataPacket packet = new MapDataPacket();
        packet.mapId = id();
        packet.columns = (short) width;
        packet.rows = (short) height;
        packet.icons = new MapDataPacket.Icon[0];
        packet.x = (byte) 0;
        packet.z = (byte) 0;
        packet.data = colors;

        PacketUtils.sendGroupedPacket(players, packet);
    }

    @Override
    public void sendPacketUpdate(Collection<Player> players) {
        byte[] hash = messageDigest.digest(colors);

        if (Arrays.equals(hash, prevHash)) {
            return;
        } else {
            prevHash = hash;
        }

        sendPacket(players);
    }
}
