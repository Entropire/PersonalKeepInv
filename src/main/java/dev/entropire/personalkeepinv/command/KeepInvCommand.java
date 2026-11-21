package dev.entropire.personalkeepinv.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import dev.entropire.personalkeepinv.PlayerData;

public class KeepInvCommand implements CommandExecutor, TabCompleter {
    
    private final PlayerData playerData;

    public KeepInvCommand(PlayerData playerData) {
        this.playerData = playerData;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }
        Player player = (Player) sender;

        if(!player.hasPermission("keepinv.use")){
            player.sendMessage("§cYou don't have permission to use the command KeepInv!");
            return true;
        }

        if (args.length == 0) {
            boolean personalKeepInv = playerData.getPersonalKeepInv(player);
            String source = playerData.hasPersonalKeepInv(player) ? "" : " §7(global)";
            player.sendMessage("Personal KeepInv is currently " + (personalKeepInv ? "§aEnabled" : "§cDisabled") + source);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on": 
                playerData.setPersonalKeepInv(player, true);
                player.sendMessage("§aPersonal KeepInv has been enabled!");
                break;
            case "off": 
                playerData.setPersonalKeepInv(player, false);
                player.sendMessage("§aPersonal KeepInv has been §cdisabled§a!");
                break;
            case "global":
                playerData.resetPersonalKeepInv(player);
                player.sendMessage("§aPersonal KeepInv now follows the global default!");
                break;
            case "default": handleDefault(player, args);
                break;
            default: sendHelp(player); 
                break;
        }

        return true;
    }

    private void handleDefault(Player player, String[] args) {
        if (!player.hasPermission("keepinv.admin")) {
            player.sendMessage("§cYou don't have permission to change the default KeepInv!");
            return;
        }

        if (args.length == 1) {
            boolean defaultKeepInv = playerData.getDefaultKeepInv();
            player.sendMessage("Default keepInv is currently set to " + (defaultKeepInv ? "§aEnabled" : "§cDisabled"));
            return;
        }

        switch (args[1].toLowerCase()) {
            case "on": setDefault(player, true);
                break;
            case "off": setDefault(player, false);
                break;
            default: sendHelp(player);
                break;
        }
    }

    private void setDefault(Player player, boolean value) {
        playerData.setDefaultKeepInv(value);
        player.sendMessage("§aGlobal default KeepInv has been " + (value ? "§aenabled" : "§cdisabled") + "§a!");
    }

    private void sendHelp(Player player) {
        player.sendMessage("§8§m---------§r §6KeepInv Help §8§m---------");
        player.sendMessage("§e/keepinv §7- Show KeepInv status");
        player.sendMessage("§e/keepinv on/off/global §7- Change personal KeepInv");
        player.sendMessage("§e/keepinv default §7- Show default KeepInv");
        player.sendMessage("§e/keepinv default on/off §7- Change default KeepInv status");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }
        Player player = (Player) sender;

        List<String> suggestions = new ArrayList<>();

        if(args.length == 1){
            suggestions.add("on");
            suggestions.add("off");
            suggestions.add("global");
            if (player.hasPermission("keepinv.admin")) suggestions.add("default");
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("default") && player.hasPermission("keepinv.admin")){
            suggestions.add("on");
            suggestions.add("off");
        }

        return suggestions;
    }
}