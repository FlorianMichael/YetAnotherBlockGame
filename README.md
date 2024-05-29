<div align="center">
  <img src="https://raw.githubusercontent.com/FlorianMichael/YetAnotherBlockGame/main/.github/yabg.png" width="150">
  <h1>Yet Another Block Game</h1>
  <img src="https://img.shields.io/badge/Enviroment-Server-purple">
  <a href="https://discord.gg/BwWhCHUKDf"><img src="https://img.shields.io/discord/316206679014244363?color=0098DB&label=Discord&logo=discord&logoColor=0098DB"></a> <br />
  <a href="https://github.com/FlorianMichael/YetAnotherBlockGame/actions/workflows/build.yml"><img src="https://github.com/FlorianMichael/YetAnotherBlockGame/actions/workflows/build.yml/badge.svg?branch=main"></a>  

#### Minimalistic OneBlock SkyBlock implementation for Paper (and Spigot).
</div>

# Why?
The goal of this project is to become a developer for the Cytooxien Minecraft server.

## Contact
If you encounter any issues, please report them on the [issue tracker](https://github.com/FlorianMichael/YetAnotherBlockGame/issues). If you just want to talk or need help with YetAnotherBlockGame feel free to join my [Discord](https://discord.gg/BwWhCHUKDf).

## Setup
1. Install the latest version of the plugin from either Releases or the Actions tab.
2. Put the plugin in your server's `plugins` folder.
3. Start the server.
4. Open the config.yml and change `world.name` to whatever the world should be named.
5. Go into the `bukkit.yml` and append following lines at the end:
```yaml
worlds:
  yabg_world: # Replace this with the world name you configured
    generator: YetAnotherBlockGame  
```

## Feature set
- System allowing multiple players to play in the same world.
- Various command interactions to invite other players as well as manage your island.
- Customizable messages and settings.

## API usage
TODO