# Minecraft-TouchBar
This is a proof-of-concept of a use of the MacBook Pro's TouchBar.<br>
It relies on the Forge Modding API, and on JTouchBar (https://github.com/Thizzer/jtouchbar).<br>
By default, it shows the health, the coordinates and the amount of arrows in the player inventory.<br>
As it relies on JTouchBar, you can modify the TouchBar as you want during the game.<br><br>
Currently, the mod only supports 1.12.2.<br>
Other versions will come soon, when I'll have more time.

# Screenshots and Touchbarshots
<img src="http://madrau.fr/MCTouchBar-Github/mc1.png" height=512><br>
<img src="http://madrau.fr/MCTouchBar-Github/touchbar1.png"><hr>

<img src="http://madrau.fr/MCTouchBar-Github/mc2.png" height=512><br>
<img src="http://madrau.fr/MCTouchBar-Github/touchbar2.png">

# Downloading
To use this mod, you'll need Forge (https://files.minecraftforge.net).
You can download the mod in the Release section (<a href="https://github.com/Maxmad68/Minecraft-TouchBar/releases/tag/1.0.0">Here</a>).
Then, copy the .jar file into you mods folder. To do so:
<b> If you are on a Mac:</b>
  - Open the Finder
  - Go to the "Go" menu, then "Go to folder" (or press ⇧⌘G)
  - Type <code>~/Library/Application Support/minecraft/mods</code>
  - Copy the .jar here<br><br>
<b> If you are on Windows:</b>
"Sincere condolences"

# User manual / How it works
If you want to adapt the mod to fit all your needs, you can do so.
Basically, you just need to know how Forge modding works, and to use the JTouchBar library.
Some useful functions are already implemented.
- <code>getNSWindowUUID()</code> : Retrieves the NSWindow UUID. Used to setup the JTouchBar.
- <code>setupTouchBar()</code> : Sets up the TouchBar. Called as mod initialises.
- <code>populateTouchBar()</code> : This function adds content to the TouchBar. Modify this one if you want more or other items.
- <code>getTouchBarImageForPath()</code> : This function returns an Image object usable in JTouchBar control. It takes a string as a parameter, corresponding to the relative path (from the .jar root) to the image.
<br><br>
Note:<br>
You can only add items to the TouchBar in the main thread. So, you can't add controls on Forge events. Then, I advice creating all controls in the <code>populateTouchBar</code> function, having your controls being public and so modify them later.<br><br>
Note again:<br>
Modifying TouchBar items makes the game very laggy for a short time (mostly on servers, idk why).
Please ensure you REALLY need to modify it's content before doing it.
