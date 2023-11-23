package de.vepexlegit.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import de.vepexlegit.Client;
import de.vepexlegit.clickgui.Panel;
import de.vepexlegit.clickgui.elements.menu.ElementCheckBox;
import de.vepexlegit.clickgui.elements.menu.ElementComboBox;
import de.vepexlegit.clickgui.elements.menu.ElementSlider;
import de.vepexlegit.clickgui.settings.Setting;
import de.vepexlegit.module.Module;
import de.vepexlegit.module.modules.render.Notifications;
import de.vepexlegit.utils.ChatUtils;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ModuleButton {
   public Module mod;
   public ArrayList<Element> menuelements;
   public Panel parent;
   public double x;
   public double y;
   public double width;
   public double height;
   public boolean extended = false;
   public boolean listening = false;

   public ModuleButton(Module imod, Panel pl) {
      this.mod = imod;
      this.height = (double)(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2);
      this.parent = pl;
      this.menuelements = new ArrayList();
      if (Client.INSTANCE.getSettingsManager().getSettingsByMod(imod) != null) {
         Iterator var3 = Client.INSTANCE.getSettingsManager().getSettingsByMod(imod).iterator();
         while (var3.hasNext()) {
            Setting s = (Setting)var3.next();
            if (s.isCheck()) {
               this.menuelements.add(new ElementCheckBox(this, s));
            } else if (s.isSlider()) {
               this.menuelements.add(new ElementSlider(this, s));
            } else if (s.isCombo()) {
               this.menuelements.add(new ElementComboBox(this, s));
            }
         }
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Color temp = ColorUtils.getClickGUIColor();
      int color = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150)).getRGB();
      int textcolor = -5263441;
      if (this.mod.isToggled()) {
         RenderUtils.drawRect(this.x - 2.0D, this.y, this.x + this.width + 2.0D, this.y + this.height + 1.0D, color);
         textcolor = -1052689;
      }
      if (this.isHovered(mouseX, mouseY)) {
         RenderUtils.drawRect(this.x - 2.0D, this.y, this.x + this.width + 2.0D, this.y + this.height + 1.0D, 1427181841);
      }
      FontUtils.drawTotalCenteredStringWithShadow(this.mod.getName(), this.x + this.width / 2.0D, this.y + 1.0D + this.height / 2.0D, textcolor);
   }

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (!this.isHovered(mouseX, mouseY)) {
         return false;
      } else {
         if (mouseButton == 0) {
            this.mod.toggle();
            if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
               Minecraft.getMinecraft().thePlayer.playSound("random.click", 0.5F, 0.5F);
            }
         } else if (mouseButton == 1) {
            if (this.menuelements != null && this.menuelements.size() > 0) {
               boolean b = !this.extended;
               Client.INSTANCE.getClickGUI().closeAllSettings();
               this.extended = b;
               if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
                  if (this.extended) {
                     Minecraft.getMinecraft().thePlayer.playSound("tile.piston.out", 1.0F, 1.0F);
                  } else {
                     Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 1.0F, 1.0F);
                  }
               }
            }
         } else if (mouseButton == 2) {
            this.listening = true;
         }
         return true;
      }
   }

   public boolean keyTyped(char typedChar, int keyCode) throws IOException {
      if (!this.listening) {
         return false;
      } else {
         if (keyCode != 1 && keyCode != 211) {
            if (Client.INSTANCE.getModuleManager().getModule(Notifications.class).isToggled() && Client.INSTANCE.getSettingsManager().getSettingByName("Notifications:KeyBinds").getValBoolean()) {
               ChatUtils.sendChatMessage("Bound '" + this.mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
            }
            this.mod.setKey(keyCode);
         } else if (keyCode == 211) {
            if (Client.INSTANCE.getModuleManager().getModule(Notifications.class).isToggled() && Client.INSTANCE.getSettingsManager().getSettingByName("Notifications:KeyBinds").getValBoolean()) {
               ChatUtils.sendChatMessage("Unbound '" + this.mod.getName() + "'");
            }
            this.mod.setKey(-1);
         }
         this.listening = false;
         return true;
      }
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
   }
}
