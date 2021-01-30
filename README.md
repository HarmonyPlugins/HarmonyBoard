# Harmony Scoreboard
![Alt text](https://github.com/HarmonyPlugins/HarmonyBoard/blob/main/images/hboard_logo.png?raw=true "Title")
## Description

Harmony Scoreboard (or HarmonyBoard for short) is a plugin that provides packet-based asynchronous scoreboards flicker-free and price free.

![Alt text](https://github.com/HarmonyPlugins/HarmonyBoard/blob/main/images/hboard_showcase.png?raw=true "Title")

## Configuration


### config.yml

config.yml is responsible for setting the default scoreboard, enabling permission-based scoreboards and per world scoreboards

<details>
  <summary>config.yml</summary>
    enable_default_scoreboard: true
    default_scoreboard: "default"

    enable_restricted_scoreboards: false
    restricted_scoreboards:
      scoreboard2: "permission.for.scoreboard2"

    enable_perworld_scoreboards: false
    world_scoreboards: 
      scoreboardworld: "world"
      scoreboardend: "world_the_end"
</details>

### language.yml

language.yml is responsible for the chat messages that you would receive whilst using the plugin.

<details>
  <summary>language.yml</summary>

    prefix: "&f[&bHarmony&f]"
    messages:
    error:
        nonplayer: "You must be a player to run this command."
        no_permission: "You do not have the required permission to perform this command."
        invalid_command: "The command you were trying to perform does not exist."
        invalid_arguments: "Please provide the right arguments for the command."
        invalid_player: "Player does not exist or is not online."
      admin:
        reloaded: "Config successfully reloaded."

</details>

### Scoreboards and Animations

The names of scoreboards are based on their filename in the Scoreboards folder.
<details>
  <summary>example_scoreboard.yml</summary>

    title: "Your Server Network"
    size: 7
    lines:
    0: "a%default%a" #Animations have the placeholder a%animation_name%a
    1: "Hello &b%player%&f,"
    2: " "
    3: "Online Players: %online%"
    4: "  "
    5: "&byourserver.gg"
    6: "a%default%a "
</details>


The names of animations are based on their filename in the Animations folder.
<details>
  <summary>example_animation.yml</summary>

      delay: 5 #number of ticks until the next line
      size: 3
      lines:
        0: "&a========================="
        1: "&b========================="
        2: "&c========================="
</details>

![Alt text](https://github.com/HarmonyPlugins/HarmonyBoard/blob/main/images/showcasegif.gif?raw=true "Title")

### Placeholders

The only official placeholders are ```%player%``` and ```%online%```. Harmony Scoreboard supports PlaceholderAPI.







