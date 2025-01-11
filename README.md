# Minissentials


### Fabric server mod inspired by Bukkit Essentials.

```
!!!!!!!!!!! DISCLAIMER !!!!!!!!!!!

CURRENTLY IN ALPHA - PLEASE DO NOT USE ON A PRODUCTION SERVER
- Data structure may change in a game-breaking way at any time
- Currently exposes server to several security flaws

!!!!!!!!!!! DISCLAIMER !!!!!!!!!!!
```

## Currently implemented features:

### Teleports

- /spawn
  - Teleport to world spawn

Warps: server-wide teleport locations
- /setwarp \<name>
  - Creates warp
- /warp \<name>
  - Teleport to warp
- /warps
  - Shows all available warps

Homes: personal teleport locations
- /sethome [\<name>]
  - Sets (a) home
- /home [\<name>]
  - Teleport to (a) home
- /homes
  - Shows all of your homes


### Debug

- /ping
  - Pong!
- /testformat [\<text>]
  - Tests `&` text formatting system

## Planned features:
- MOTD
- Whisper
- Mail
- Nick
- AFK
- Seen
- Teleport
- Help
- Broadcast
- Jail
- Block
- Online
- Shutdown
- Back

<details>
<summary>Currently in progress:</summary>

- Nickname injector
- Server-side JSON database structure
- delwarp and delhome

</details>

<details>
<summary>KNOWN ISSUES</summary>

- gamerule logAdminCommands compat
- need op-only permissions for some commands
- warps need to check if area is safe

</details>