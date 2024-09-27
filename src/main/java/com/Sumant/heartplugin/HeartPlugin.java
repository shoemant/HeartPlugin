package com.Sumant.heartplugin;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class HeartPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        // register event listeners
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new SleepListener(), this);
        getLogger().info("HeartPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("HeartPlugin has been disabled.");
    }
}

// listening for player interactions
class PlayerInteractListener implements Listener {

    private final Random random = new Random();
    private HashMap<UUID, Long> lastEventTrigger = new HashMap<>();


    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        // check if entity interacted w/ is a player
        if (event.getRightClicked() instanceof Player) {
            Player player = event.getPlayer();
            Player targetPlayer = (Player) event.getRightClicked();

            // minecraft detects right click as two events (main hand and offhand) hence this prevents double messaging
            if (lastEventTrigger.getOrDefault(player.getUniqueId(), 0L) + 40 > System.currentTimeMillis()){
                return;
            }else {
                lastEventTrigger.put(player.getUniqueId(), System.currentTimeMillis());
            }

            // randomly selected between kissed or hugged
            String action = random.nextBoolean() ? "kissed" : "hugged";

            // creates message w/ players' names and action
            String message = ChatColor.BLUE + player.getName() + " " + action + " " + targetPlayer.getName() + "!";

            // sound effect for both players
            player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
            targetPlayer.playSound(targetPlayer.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);

            // heart particles around the players
            targetPlayer.getWorld().spawnParticle(Particle.HEART, targetPlayer.getLocation(), 30, 0.1, 1, 0.1);

            // broadcast message to whole server
            player.getServer().broadcastMessage(message);
        }
    }
}

// listens for player bed entry events
class SleepListener implements Listener {

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        // check if player entered bed
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            Player player = event.getPlayer();
            Block bed = event.getBed();

            // finds any adjacent player who's already sleeping
            Player adjacentPlayer = getAdjacentSleepingPlayer(bed, player);

            if (adjacentPlayer != null) {
                // create message indicating both players are sleeping next to each other
                String message = ChatColor.LIGHT_PURPLE + player.getName() + " and " + adjacentPlayer.getName() + " are spooning :)";
                // broadcasts message to all
                Bukkit.broadcastMessage(message);
            }
        }
    }

    // helper method to find adjacent sleeping player
    private Player getAdjacentSleepingPlayer(Block bed, Player playerEnteringBed) {
        // directions to check for adjacent to the bed
        BlockFace[] faces = { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST };
        for (BlockFace face : faces) {
            // get block adjacent to bed
            Block adjacentBlock = bed.getRelative(face);
            // check if it is a bed
            if (Tag.BEDS.isTagged(adjacentBlock.getType())) {
                // loop through all online players
                for (Player player : Bukkit.getOnlinePlayers()) {
                    // check if another player is sleeping in the adjacent bed
                    if (!player.equals(playerEnteringBed) && player.isSleeping() && player.getLocation().getBlock().equals(adjacentBlock)) {
                        return player; //return adjecnt sleeping player
                    }
                }
            }
        }
        return null; // no adjacent sleeping player found
    }
}



