# Minecord

A Spigot Plugin that connects your server chat with a discord channel.

## Features

- Connects your in game chat with a channel on your discord server.
<br>
![img](https://github.com/EliasSchramm/Minecord/blob/main/docs/img.png?raw=true)
- Displays the current player count.
<br>
![img](https://github.com/EliasSchramm/Minecord/blob/main/docs/img2.png?raw=true)


## Requirements

- A minecraft spigot/paper server with the version 1.18+
- A discord bot token. (How to get one is described in the next section)
- A discord server that you have admin access to

## Installation

### Getting the Bot Token

- Open the discord developer portal at [https://discord.com/developers/applications](https://discord.com/developers/applications)
- Click on 'New Application'
- Give it a name you like. The name of the application does set the bots' name so just chose one you like.
- Take note of the `Application ID` shown. It looks like this: `95997070327569420`.
- Then navigate to 'Bot' and click 'Add Bot'.
- Click `Reset Token`. Take note of the generated token as this will be the one you need for the plugin to work.
- Scroll down and toggle `Message Content Intent` to be active.
- Now visit `https://discord.com/api/oauth2/authorize?client_id=<YOUR APPLICATION ID>&scope=bot&permissions=2048`.
- There you just chose the server the bot should get added too.
- DONE! Now you should see your bot as an Offline user.

### Plugin Setup

- Download the [latest release](https://github.com/EliasSchramm/Minecord/releases/latest)
- Put it in your servers `plugins/` folder
- Start your server one. The plugin should crash because it has no token.
- Edit the generated `config.yml` in the `plugins/Minecord` folder.
- Replace 'aToken' with your discord bot token.
- You may also change the `channel_name` keep in mind that a text channel with the set name must exist on your server.
- Restart the server.
- DONE! Now your bot should be online, and it should mirror the contents of your servers.

## Updating

Just replace the minecord-x.x.x.jar file with the [latest one](https://github.com/EliasSchramm/Minecord/releases/latest)

## Commands

- `/clearDiscordBotCache` Clears the Discord bot cache. Intended to be used in case of some odd desyncs.