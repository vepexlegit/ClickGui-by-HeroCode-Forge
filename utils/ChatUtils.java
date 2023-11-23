package de.vepexlegit.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {
   private static Minecraft mc = Minecraft.getMinecraft();

   public static void sendChatMessage(String message) {
      mc.thePlayer.addChatMessage(new ChatComponentText(message));
   }
}
