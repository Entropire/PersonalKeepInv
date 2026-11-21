# PersonalKeepInv

Let every player decide for themselves whether they keep their inventory when they die.

Some players want the challenge of losing everything, others just want to build in peace. PersonalKeepInv gives each player their own Keep Inventory setting, independent of everyone else, with a server-wide default for players who haven't chosen.

## Features

- **Personal setting:** every player can turn Keep Inventory on or off for themselves.
- **Global default:** admins set a default that applies to every player who hasn't made their own choice.
- **Follow the default:** players can switch back to the global default at any time with `/keepinv global`.
- **Lightweight:** one small file for storage, no dependencies, no configuration needed.
- **Runs almost anywhere:** one jar for Minecraft 1.8.8 up to the latest version, on Spigot, Paper, Purpur and Folia.

## Commands

| Command | Description |
|---|---|
| `/keepinv` | Shows your current Keep Inventory setting |
| `/keepinv on` | Turns Keep Inventory on for you |
| `/keepinv off` | Turns Keep Inventory off for you |
| `/keepinv global` | Makes your setting follow the global default |
| `/keepinv default` | Shows the global default |
| `/keepinv default on` | Turns the global default on |
| `/keepinv default off` | Turns the global default off |

## Permissions

| Permission | Description | Default |
|---|---|---|
| `keepinv.use` | Use `/keepinv` to view and change your own setting | Everyone |
| `keepinv.admin` | View and change the global default | Operators |

## How it works

When a player with Keep Inventory **on** dies, they keep their items and XP levels. When a player with Keep Inventory **off** dies, they drop everything like on a normal server.

Players who never used `/keepinv on` or `/keepinv off`, or who used `/keepinv global`, follow the global default. When an admin changes the default, it applies to all of those players straight away. Players who picked their own setting keep it.

The global default is **off** until an admin changes it.

### The keepInventory gamerule

PersonalKeepInv takes over the job of Minecraft's built-in `keepInventory` gamerule. When the server starts, and whenever a world is loaded, the plugin sets that gamerule to `false` and writes a message in the console for every world it changed.

Use `/keepinv default on` instead of the gamerule to keep inventories by default. If the gamerule is turned back on while the server is running, the plugin turns it off again the next time a player with Keep Inventory off dies. That one death still keeps its items.

If you remove the plugin, the gamerule stays `false`, which is Minecraft's normal default.

## Installation

1. Download the jar and put it in your server's `plugins` folder.
2. Restart the server.
3. Optionally, set the global default with `/keepinv default on` or `/keepinv default off`.

## Data

All settings are stored in `plugins/PersonalKeepInv/players.yml`:

```yaml
default: false
players:
  069a79f4-44e9-4726-a5be-fca90e38aaf5: true
  853c80ef-3c37-49fd-aa49-938b674adae6: false
```

Players are stored by UUID, so their setting survives name changes. Players who follow the global default are not listed.
