package dev.entropire.personalkeepinv.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.GameRules;
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
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command!");
            return true;
        }

        if (args.length == 0) {
            boolean personalKeepInv = playerData.getPersonalKeepInv(player);
            player.sendMessage("Personal keepInv is currently set to " + (personalKeepInv ? "§aEnabled" : "§cDisabled"));
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "on" -> setPersonal(player, true);
            case "off" -> setPersonal(player, false);
            case "default" -> handleDefault(player, args);
            default -> sendHelp(player);
        }

        return true;
    }

    private void setPersonal(Player player, boolean value) {
        playerData.setPersonalKeepInv(player, value);
        player.sendMessage("§aPersonal KeepInv has been " + (value ? "§aenabled" : "§cdisabled") + "§a!");
    }

    private void handleDefault(Player player, String[] args) {
        if (!player.hasPermission("keepinv.admin")) {
            player.sendMessage("§cYou don't have permission to change the default KeepInv!");
            return;
        }

        if (args.length == 1) {
            boolean defaultKeepInv = player.getWorld().getGameRuleValue(GameRules.KEEP_INVENTORY);
            player.sendMessage("Default keepInv is currently set to " + (defaultKeepInv ? "§aEnabled" : "§cDisabled"));
            return;
        }

        switch (args[1].toLowerCase()) {
            case "on" -> setDefault(player, true);
            case "off" -> setDefault(player, false);
            default -> sendHelp(player);
        }
    }

    private void setDefault(Player player, boolean value) {
        player.getWorld().setGameRule(GameRules.KEEP_INVENTORY, value);
        player.sendMessage("§aDefault KeepInv has been " + (value ? "§aenabled" : "§cdisabled") + "§a!");
    }

    private void sendHelp(Player player) {
        player.sendMessage("§8§m---------§r §6KeepInv Help §8§m---------");
        player.sendMessage("§e/keepinv §7- Show KeepInv status");
        player.sendMessage("§e/keepinv on/off §7- Change personal KeepInv");
        player.sendMessage("§e/keepinv default §7- Show default KeepInv");
        player.sendMessage("§e/keepinv default on/off §7- Change default KeepInv status");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return List.of();

        List<String> suggestions = new ArrayList<>();

        if(args.length == 1){
            suggestions.add("on");
            suggestions.add("off");
            if (player.hasPermission("keepinv.admin")) suggestions.add("default");
        }

        if(args.length == 2 && args[0].equalsIgnoreCase("default") && player.hasPermission("keepinv.admin")){
            suggestions.add("on");
            suggestions.add("off");
        }

        return suggestions;
    }
}