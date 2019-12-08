package mctouchbar;


import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArrow;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.PotionEffect;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;

import com.thizzer.jtouchbar.common.Image;
import com.thizzer.jtouchbar.common.Color;

import javax.sound.sampled.*;



public class PlayerEvent {
	
	private Item oldItem = null;
	
	private int oldArrowsAmount = -1;
	
	private String oldCoords = "";
	private int oldHealth = 0;
	
	private String oldHealthEffectName = "";
	private String oldMovementEffectName = "";
	
	private String healthEffectName = "";
	private String movementEffectName = "";

	


	@SubscribeEvent
	public void onClientTick(TickEvent.PlayerTickEvent event) {
			final EntityPlayer player = Minecraft.getMinecraft().player;
			
			if (event.phase == TickEvent.Phase.START && event.side.isClient()) {
				
				
				// Health indicator
				
				int health = (int) player.getHealth();
				if (health != oldHealth) {
					MCTouchBar.touchBarButtonLifeImg.setTitle(""+health);
				}
				oldHealth = health;

				healthEffectName = "";
				player.getActivePotionEffects().forEach((effect) -> {
					String name = effect.getEffectName();
					if (name == "effect.absorption" || name == "effect.poison" || name == "effect.wither" || name == "effect.regeneration" || name == "effect.healthBoost" || healthEffectName == "effect.resistance") {
						healthEffectName = name;
					} else if (name == "effect.moveSpeed" || name == "effect.moveSlowdown" || name == "effect.levitation" || name == "effect.jump" || name == "effect.invisibility") {
						movementEffectName = name;
					}
					
				});
				
				if (healthEffectName != oldHealthEffectName) {
					if (healthEffectName == "effect.absorption") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.ABSO_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.YELLOW);
					} else if (healthEffectName == "effect.poison") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.POISON_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.GREEN);
					} else if (healthEffectName == "effect.wither") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.WITHER_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.PURPLE);
					} else if (healthEffectName == "effect.regeneration") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.HEALTH_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.MAGENTA);
					} else if (healthEffectName == "effect.healthBoost") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.BOOST_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.ORANGE);
					} else if (healthEffectName == "effect.resistance") {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.RESISTANCE_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.GRAY);
					} else {
						MCTouchBar.touchBarButtonLifeImg.setImage(MCTouchBar.HEALTH_IMAGE);
						MCTouchBar.touchBarButtonLifeImg.setBezelColor(Color.RED);
					}	
				}
				oldHealthEffectName = healthEffectName;

				
				
				// Arrows counter
				
				int count = 0;
				for (int slot = 0; slot < player.inventory.getSizeInventory(); slot++) {
					ItemStack stack = player.inventory.getStackInSlot(slot);

					if (stack.getItem() instanceof ItemArrow) {
							count = count + stack.getCount();
					}
				}
				if (count != oldArrowsAmount) {
					MCTouchBar.touchBarButtonArrowsImg.setTitle(""+count);
				}

				
				
				
				
				// Coordinates
				
				
				int x = (int) player.posX;
				int y = (int) player.posY;
				int z = (int) player.posZ;
				String coords = "" + x + " " + y + " " + z;
				if (coords != oldCoords) {
					MCTouchBar.touchBarCoordsButton.setTitle(coords);
				}
				
				movementEffectName = "";
				if (oldMovementEffectName != movementEffectName) {
					if (movementEffectName == "effect.moveSpeed") {
						MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.SPEED_IMAGE);
						MCTouchBar.touchBarCoordsButton.setImagePosition(2);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.CYAN);
					} else if (movementEffectName == "effect.moveSlowdown") {
						MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.SLOW_IMAGE);
						MCTouchBar.touchBarCoordsButton.setImagePosition(2);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.BLUE);
					} else if (movementEffectName == "effect.jump") {
						MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.JUMP_IMAGE);
						MCTouchBar.touchBarCoordsButton.setImagePosition(2);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.GREEN);
					} else if (movementEffectName == "effect.levitation") {
						MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.LEVI_IMAGE);
						MCTouchBar.touchBarCoordsButton.setImagePosition(2);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.MAGENTA);
					} else if (movementEffectName == "effect.invisibility") {
						MCTouchBar.touchBarCoordsButton.setImage(MCTouchBar.INVIS_IMAGE);
						MCTouchBar.touchBarCoordsButton.setImagePosition(2);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.CYAN);
					} else {
						MCTouchBar.touchBarCoordsButton.setImagePosition(0);
						MCTouchBar.touchBarCoordsButton.setBezelColor(Color.BLACK);
					}
				}
				oldMovementEffectName = movementEffectName;


			}			
		
	}

}