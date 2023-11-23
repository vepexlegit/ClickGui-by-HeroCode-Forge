package de.vepexlegit.clickgui.elements.menu;

import de.vepexlegit.clickgui.elements.Element;
import de.vepexlegit.clickgui.elements.ModuleButton;
import de.vepexlegit.clickgui.settings.Setting;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import java.awt.Color;

public class ElementCheckBox extends Element {
   public ElementCheckBox(ModuleButton iparent, Setting iset) {
      this.parent = iparent;
      this.set = iset;
      super.setup();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Color temp = ColorUtils.getClickGUIColor();
      int color = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 200)).getRGB();
      RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
      FontUtils.drawString(this.setstrg, this.x + this.width - (double)FontUtils.getStringWidth(this.setstrg), this.y + (double)(FontUtils.getFontHeight() / 2) - 0.5D, -1);
      RenderUtils.drawRect(this.x + 1.0D, this.y + 2.0D, this.x + 12.0D, this.y + 13.0D, this.set.getValBoolean() ? color : -16777216);
      if (this.isCheckHovered(mouseX, mouseY)) {
         RenderUtils.drawRect(this.x + 1.0D, this.y + 2.0D, this.x + 12.0D, this.y + 13.0D, 1427181841);
      }
   }

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isCheckHovered(mouseX, mouseY)) {
         this.set.setValBoolean(!this.set.getValBoolean());
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, mouseButton);
      }
   }

   public boolean isCheckHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x + 1.0D && (double)mouseX <= this.x + 12.0D && (double)mouseY >= this.y + 2.0D && (double)mouseY <= this.y + 13.0D;
   }
}
