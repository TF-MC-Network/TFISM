package net.teamfekker.TFISM;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Main extends JavaPlugin {
	
    private File customConfigFile;
    private FileConfiguration customConfig;
	private UUID uuid;
	private File userFile;
	private YamlConfiguration userConfig;
	Plugin plugin = this;

	public void onEnable() {

	}
	
	public void onDisable() {
		
	}
	
	public void enterMode(Player player ) {
		UserDataHandler(player);
		float xp = player.getExp();
		Location loc = player.getLocation();
		ItemStack[] inv = player.getInventory().getContents();
		
		userConfig.set("Exp", xp);
		userConfig.set("Location", loc);
		userConfig.set("inventory", inv);
		player.setGameMode(GameMode.CREATIVE);
		player.getInventory().clear();
		player.setExp(0);
	}
	
	public void leaveMode(Player player ) { //need inventory research
		UserDataHandler(player);
		float xp = (float) userConfig.get("Exp");
		Location loc = (Location) userConfig.get("Location");
		ItemStack[] inv = (ItemStack[]) userConfig.get("inventory");
		player.getInventory().clear();
		
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] == null)
                continue;
            player.getInventory().setItem(i, inv[i]);
        }
        
        player.teleport(loc);
		player.setExp(xp);
		userFile.delete();
	}
	
	public void safeRejoin(Player player ) {
		UserDataHandler(player);
	}
	
    public void UserDataHandler(Player player) {
        this.uuid = player.getUniqueId();
        this.userFile = new File(plugin.getDataFolder() + File.separator + "playerData", player.getUniqueId().toString() + ".yml");
        this.userConfig = YamlConfiguration.loadConfiguration(this.userFile);
    }
	
    public FileConfiguration getCustomConfig() {
        return this.customConfig;
    }
}
