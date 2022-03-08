package org.jasonkaranik.boatfly;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class BoatFly extends JavaPlugin {

    public static BoatFly plugin;

    public static ProtocolManager protocolManager;

    @Override
    public void onEnable() {

        System.out.println("[BoatFly] BoatFly Enabled");

        plugin = this;

        protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.HIGHEST, PacketType.Play.Client.STEER_VEHICLE) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer();

                Entity b = player.getVehicle();
                if (b == null) { return; }
                if (b instanceof Player) { return; }
                if (b.getType() == EntityType.BOAT) {
                    if (player.hasPermission("boatfly.use")) {
                        Vector velocity = b.getVelocity();
                        double motionY = packet.getBooleans().read(0) ? 0.3 : 0;
                        b.setVelocity(new Vector(velocity.getX(), motionY, velocity.getZ()));
                    }
                }

                // if (packet.getFloat().read(1) > 0) {
                // W
                // }
                // if (packet.getFloat().read(1) < 0) {
                // S
                // }
                // if (packet.getFloat().read(0) > 0) {
                // A
                // }
                // if (packet.getFloat().read(0) < 0) {
                // D
                // }
                // if (packet.getBooleans().read(0)) {
                // JUMP
                // }
            }
        });
    }

    @Override
    public void onDisable() {
        System.out.println("[BoatFly] BoatFly Disabled");
        plugin = null;
    }
}