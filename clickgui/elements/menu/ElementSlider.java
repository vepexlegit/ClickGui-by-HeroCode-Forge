package de.vepexlegit.clickgui.elements.menu;

import java.awt.Color;
import de.vepexlegit.clickgui.elements.Element;
import de.vepexlegit.clickgui.elements.ModuleButton;
import de.vepexlegit.clickgui.settings.Setting;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import net.minecraft.util.MathHelper;

public class ElementSlider extends Element {
   public boolean dragging;

   public ElementSlider(ModuleButton iparent, Setting iset) {
      this.parent = iparent;
      this.set = iset;
      this.dragging = false;
      super.setup();
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      String displayval = "" + (double)Math.round(this.set.getValDouble() * 100.0D) / 100.0D;
      boolean hoveredORdragged = this.isSliderHovered(mouseX, mouseY) || this.dragging;
      Color temp = ColorUtils.getClickGUIColor();
      int color = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200)).getRGB();
      int color2 = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230)).getRGB();
      double percentBar = (this.set.getValDouble() - this.set.getMin()) / (this.set.getMax() - this.set.getMin());
      RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15066598);
      FontUtils.drawString(this.setstrg, this.x + 1.0D, this.y + 2.0D, -1);
      FontUtils.drawString(displayval, this.x + this.width - (double)FontUtils.getStringWidth(displayval), this.y + 2.0D, -1);
      RenderUtils.drawRect(this.x, this.y + 12.0D, this.x + this.width, this.y + 13.5D, -15724528);
      RenderUtils.drawRect(this.x, this.y + 12.0D, this.x + percentBar * this.width, this.y + 13.5D, color);
      if (percentBar > 0.0D && percentBar < 1.0D) {
         RenderUtils.drawRect(this.x + percentBar * this.width - 1.0D, this.y + 12.0D, this.x + Math.min(percentBar * this.width, this.width), this.y + 13.5D, color2);
      }
      if (this.dragging) {
         double diff = this.set.getMax() - this.set.getMin();
         double val = this.set.getMin() + MathHelper.clamp_double(((double)mouseX - this.x) / this.width, 0.0D, 1.0D) * diff;
         this.set.setValDouble(val);
      }
   }

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (mouseButton == 0 && this.isSliderHovered(mouseX, mouseY)) {
         this.dragging = true;
         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, mouseButton);
      }
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      this.dragging = false;
   }

   public boolean isSliderHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y + 11.0D && (double)mouseY <= this.y + 14.0D;
   }
}
