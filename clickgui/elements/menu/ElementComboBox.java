package de.vepexlegit.clickgui.elements.menu;

import java.awt.Color;
import de.vepexlegit.Client;
import de.vepexlegit.clickgui.elements.Element;
import de.vepexlegit.clickgui.elements.ModuleButton;
import de.vepexlegit.clickgui.settings.Setting;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import net.minecraft.client.Minecraft;

public class ElementComboBox extends Element {
   public ElementComboBox(ModuleButton iparent, Setting iset) {
      this.parent = iparent;
      this.set = iset;
      super.setup();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Color temp = ColorUtils.getClickGUIColor();
      int color = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150)).getRGB();
      RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
      FontUtils.drawTotalCenteredString(this.setstrg, this.x + this.width / 2.0D, this.y + 7.0D, -1);
      int clr1 = color;
      int clr2 = temp.getRGB();
      RenderUtils.drawRect(this.x, this.y + 14.0D, this.x + this.width, this.y + 15.0D, 1996488704);
      if (this.comboextended) {
         RenderUtils.drawRect(this.x, this.y + 15.0D, this.x + this.width, this.y + this.height, -1441656302);
         double ay = this.y + 15.0D;
         String[] var10 = this.set.getOptions();
         int var11 = var10.length;
         for (int var12 = 0; var12 < var11; ++var12) {
            String sld = var10[var12];
            String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
            FontUtils.drawCenteredString(elementtitle, this.x + this.width / 2.0D, ay + 2.0D, -1);
            if (sld.equalsIgnoreCase(this.set.getValString())) {
               RenderUtils.drawRect(this.x, ay, this.x + 1.5D, ay + (double)FontUtils.getFontHeight() + 2.0D, clr1);
            }
            if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY < ay + (double)FontUtils.getFontHeight() + 2.0D) {
               RenderUtils.drawRect(this.x + this.width - 1.2D, ay, this.x + this.width, ay + (double)FontUtils.getFontHeight() + 2.0D, clr2);
            }
            ay += (double)(FontUtils.getFontHeight() + 2);
         }
      }
   }

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0) {
         if (this.isButtonHovered(mouseX, mouseY)) {
            this.comboextended = !this.comboextended;
            return true;
         }
         if (!this.comboextended) {
            return false;
         }
         double ay = this.y + 15.0D;
         String[] var6 = this.set.getOptions();
         int var7 = var6.length;
         for (int var8 = 0; var8 < var7; ++var8) {
            String slcd = var6[var8];
            if ((double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= ay && (double)mouseY <= ay + (double)FontUtils.getFontHeight() + 2.0D) {
               if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Sound").getValBoolean()) {
                  Minecraft.getMinecraft().thePlayer.playSound("tile.piston.in", 20.0F, 20.0F);
               }
               if (this.clickgui != null && this.clickgui.setmgr != null) {
                  this.clickgui.setmgr.getSettingByName(this.set.getName()).setValString(slcd.toLowerCase());
               }
               return true;
            }
            ay += (double)(FontUtils.getFontHeight() + 2);
         }
      }
      return super.mouseClicked(mouseX, mouseY, mouseButton);
   }

   public boolean isButtonHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + 15.0D;
   }
}
