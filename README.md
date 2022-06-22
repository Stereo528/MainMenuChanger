# MainMenuChanger
 Configurable mod that lets you change how the Main Menu is laid out.
 
---

This mod allows you to:

- Halve the size of the splash 
- Change the Copyright text to say `© Mojang AB`
- Reduce the version text, for example, `Minecraft 1.19/Fabric (Modded)` to `1.19 Fabric`
- Mod Count (without ModMenu)¹
- Disable the Realms button and Realm Notifications
- and lastly, disable the Language and Accessibility buttons from the main menu

All with a handy-dandy config!²

¹ ModMenu will still display mod count if both `shorter_version_text` and `mod_count` are set to false, as it employs the vanilla behavior, which ModMenu overwrites, but can be set to not via its config.

² Config has everything set to false by default, and can currently only be edited through a text editor by opening MainMenuChanger.json in your `config` folder of `.minecraft`