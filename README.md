# HeartPlugin

HeartPlugin is a simple and fun Minecraft plugin that adds interactions between players when they interact with each other or enter beds. Players can kiss or hug each other with visual and audio effects, and receive a cute message when sleeping next to someone in adjacent beds.

## Features

### Player Interactions:

- When a player right-clicks another player, they will either "kiss" or "hug" them, with heart particles and sound effects.
- The plugin broadcasts a message to the whole server, notifying everyone of the interaction.
- Prevents double messaging caused by Minecraft detecting right clicks with both hands.

### Sleeping Together:

- When two players sleep in adjacent beds, a message will be broadcasted saying they are spooning.
- The plugin checks the blocks adjacent to a player's bed to detect if another player is sleeping nearby.

## How to Use
### Install:

- Place the `HeartPlugin.jar` file into your server's plugins folder.
- Start or restart the server.

### Player Interaction:

- Right-click another player to either kiss or hug them, complete with sound and heart particles.
- The interaction is broadcasted to all players on the server.

### Bed Interaction:

- If two players enter beds that are adjacent to each other, a special message will appear to all players indicating that they are spooning.

## Permissions
Currently, the plugin does not implement any permissions. All players can use the kiss/hug interaction and see the spooning messages.

