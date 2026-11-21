package dev.entropire.personalkeepinv;

import org.bukkit.plugin.java.JavaPlugin;

import dev.entropire.personalkeepinv.command.KeepInvCommand;
import dev.entropire.personalkeepinv.listener.GameRuleEnforcer;
import dev.entropire.personalkeepinv.listener.PlayerDeathListener;

public final class PersonalKeepInv extends JavaPlugin {

    private PlayerData playerData;

    @Override
    public void onEnable() {
        playerData = new PlayerData(this);
        GameRuleEnforcer enforcer = new GameRuleEnforcer(getLogger());
        enforcer.enforceAll();

        getServer().getPluginManager().registerEvents(enforcer, this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(playerData, enforcer), this);

        KeepInvCommand keepInvCommand = new KeepInvCommand(playerData);
        getCommand("keepinv").setExecutor(keepInvCommand);
        getCommand("keepinv").setTabCompleter(keepInvCommand);
    }

    public PlayerData getPlayerData(){
        return this.playerData;
    }
}
