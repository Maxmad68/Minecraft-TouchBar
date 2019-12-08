package mctouchbar;

import org.apache.logging.log4j.Logger;
import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.GroupTouchBarItem;
import com.thizzer.jtouchbar.item.TouchBarItem;

import com.thizzer.jtouchbar.common.Image;
import com.thizzer.jtouchbar.common.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import org.lwjgl.*;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.nio.*;

import java.io.InputStream;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
//import com.apple.jobjc.appkit.NSApplication;
//import com.apple.jobjc.appkit.NSApplicationClass;
//import com.apple.jobjc.foundation.NSString;
//import com.apple.jobjc.cocoa.NSWindow;




@Mod(modid = MCTouchBar.MODID, name = MCTouchBar.NAME, version = MCTouchBar.VERSION)
public class MCTouchBar {

	public static final String MODID = "mctouchbar";
	public static final String NAME = "MCTouchBar";
	public static final String VERSION = "1.0.0";

	public static Logger logger;
	public static JTouchBar tb;
	
	public static TouchBarButton touchBarButtonLifeImg;
	
	public static TouchBarButton touchBarButtonArrowsImg;
	public static TouchBarButton touchBarCoordsButton;
	
	
	// Preload Images
	public static Image HEALTH_IMAGE = getTouchBarImageForPath("/assets/regeneration.png");
	public static Image ABSO_IMAGE = getTouchBarImageForPath("/assets/absorption.png");
	public static Image POISON_IMAGE = getTouchBarImageForPath("/assets/poison.png");
	public static Image WITHER_IMAGE = getTouchBarImageForPath("/assets/wither.png");
	public static Image BOOST_IMAGE = getTouchBarImageForPath("/assets/health_boost.png");
	public static Image RESISTANCE_IMAGE = getTouchBarImageForPath("/assets/resistance.png");

	public static Image SPEED_IMAGE = getTouchBarImageForPath("/assets/speed.png");
	public static Image JUMP_IMAGE = getTouchBarImageForPath("/assets/jump_boost.png");
	public static Image LEVI_IMAGE = getTouchBarImageForPath("/assets/levitation.png");
	public static Image SLOW_IMAGE = getTouchBarImageForPath("/assets/slowness.png");
	public static Image INVIS_IMAGE = getTouchBarImageForPath("/assets/invisibility.png");


	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
	}
	

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		logger.info("Mod initlialised :" + NAME);
		MinecraftForge.EVENT_BUS.register(new PlayerEvent());

		
		tb = new JTouchBar();
		tb.setCustomizationIdentifier("com.madrau.MCTouchBar");

		try {
			setupTouchBar();
		} catch(Exception e) {
			logger.info("Can't set TouchBar");
		}
		
	}
	
	protected static void setupTouchBar() {
		// Setup the TouchBar
		// Linking to the window and populate items
		
		// Retrieve the NSWindow UUID
		long windowUUID = -1;
		try {
			windowUUID = getNSWindowUUID();
			logger.info("Creating TouchBar...");
		}
		catch(Exception e) {
			logger.info("Unable to retrieve NSWindow UUID");
		}


		if (windowUUID != -1) {
			// Populate TouchBar here:
			
			populateTouchBar();
			logger.info("TouchBar Content Created");
			tb.show(windowUUID);	
			logger.info("TouchBar Shown");
		} else {
			logger.info("Can't setup TouchBar");
		}
	}
	
	
	public static void populateTouchBar() {
		//Health button
		touchBarButtonLifeImg = new TouchBarButton();
		touchBarButtonLifeImg.setBezelColor(Color.RED);
		touchBarButtonLifeImg.setTitle("--");
		touchBarButtonLifeImg.setImage(HEALTH_IMAGE);
		touchBarButtonLifeImg.setImagePosition(2);

		tb.addItem(new TouchBarItem("Button_1", touchBarButtonLifeImg, true));
		
		
		
		// Flexible Space
		tb.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFlexibleSpace));

		
		
		// Coordinates button
		touchBarCoordsButton = new TouchBarButton();
		touchBarCoordsButton.setBezelColor(Color.BLACK);
		touchBarCoordsButton.setImagePosition(2);
		tb.addItem(new TouchBarItem("Button_Coords", touchBarCoordsButton, true));

		
		// Another flexible space
		tb.addItem(new TouchBarItem(TouchBarItem.NSTouchBarItemIdentifierFlexibleSpace));

		
		
		// Arrows button
		touchBarButtonArrowsImg = new TouchBarButton();
		touchBarButtonArrowsImg.setBezelColor(Color.BLACK);
		touchBarButtonArrowsImg.setImagePosition(3);
		touchBarButtonArrowsImg.setImage(getTouchBarImageForPath("/assets/arrow.png"));
		tb.addItem(new TouchBarItem("Button_Arrows", touchBarButtonArrowsImg, true));
	}
	
	
	protected static long getNSWindowUUID() throws Exception {
		// Returns Minecraft main window(s UUID.
		// Useful to link the TouchBar to the window
		
		long output = -1;
		Method implementation = Display.class.getDeclaredMethod("getImplementation");
		implementation.setAccessible(true);
		Object displayImpl = implementation.invoke(null);
		
		Field[] macDisplayFields = displayImpl.getClass().getDeclaredFields();
		
		for (Field f : macDisplayFields) {
			// Iterate through fields until we found the window
			if (!f.getName().isEmpty()) {
				f.setAccessible(true);
				if (f.getName() == "window") {
					logger.info("NSWindow Found!");
					ByteBuffer buffer = (ByteBuffer) f.get(displayImpl);
					long windowId = buffer.getLong(0);
					
					return windowId;
				}
			}
		}
		
		logger.info("Can't find NSWindow");
		return -1;
		
	}
	
	
	public static Image getTouchBarImageForPath(String path) {
		try {
			InputStream stream = MCTouchBar.class.getResourceAsStream(path);
			return new Image(stream);
		} catch(Exception e) {
			byte[] nullImageByteArray = new byte[] {};
			return new Image(nullImageByteArray);

		}
	}
}