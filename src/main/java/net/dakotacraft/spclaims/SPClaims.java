package net.dakotacraft.spclaims;

import java.util.HashSet;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.util.math.ChunkPos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SPClaims implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("sp-claims");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Single Player Claims initializing.");

		MinecraftClient mc = MinecraftClient.getInstance();

		final HashSet<ChunkPos> OwnedChunks = new HashSet<>();
		OwnedChunks.add(new ChunkPos(0,0));

		PlayerBlockBreakEvents.BEFORE.register((world, player, pos, state, entity) ->
		{
			ChunkPos chunkPos = new ChunkPos(pos);
			Text text = Text.of("Block not Owned!");
			
			if (OwnedChunks.contains(chunkPos))
			{
				LOGGER.info("Owned Block at:" + chunkPos.toString());
				return true;
			} else
			{
				LOGGER.info("Unowned Block at:" + chunkPos.toString());
				mc.inGameHud.getChatHud().addMessage(text);
				return false;
			}
		});
	}
}
