package nl.kiipdevelopment.minescreen.map;

import net.minestom.server.entity.Player;
import net.minestom.server.network.packet.server.SendablePacket;
import net.minestom.server.network.packet.server.play.MapDataPacket;
import net.minestom.server.utils.PacketUtils;
import nl.kiipdevelopment.minescreen.MineScreen;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SubMapImpl implements SubMap {
    private static final MessageDigest DIGEST;

    static {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        DIGEST = digest;
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
        return MineScreen.instance().mapIdSupplier().get(guiId, mapX, mapY);
    }

    @Override
    public byte[] colors() {
        return colors;
    }

    @Override
    public void sendPacket(Collection<Player> players) {
        final SendablePacket packet = PacketUtils.allocateTrimmedPacket(new MapDataPacket(
                id(), (byte) 1, true, false, List.of(),
                new MapDataPacket.ColorContent((byte) width, (byte) height, (byte) 0, (byte) 0, colors)));
        for (Player player : players)
            player.sendPacket(packet);
    }

    @Override
    public void sendPacketUpdate(Collection<Player> players) {
        final byte[] hash = DIGEST.digest(colors);
        if (Arrays.equals(hash, prevHash)) return;
        else prevHash = hash;
        sendPacket(players);
    }
}
