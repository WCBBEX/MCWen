package com.mcwen;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;


public class MCWen implements ModInitializer {

	public static AI MCWEN = new AI();

	@Override
	public void onInitialize() {

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("testAi")
					.then(CommandManager.argument("message", StringArgumentType.greedyString())
							.executes(context -> {
								String message = StringArgumentType.getString(context, "message");
								ServerPlayerEntity player = context.getSource().getPlayer();
								new Thread(() -> {
									try {
										AI.Chat chat = MCWEN.new Chat();
										String reply = chat.getReply(message);

										if (player != null) {
											player.getServer().execute(() -> {
												player.sendMessage(Text.literal(reply), false);
											});
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}).start();
								return 1;
							})
					)
			);
		});
	}
}