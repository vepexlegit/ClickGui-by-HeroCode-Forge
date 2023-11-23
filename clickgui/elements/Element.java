package de.vepexlegit.clickgui.elements;

import de.vepexlegit.clickgui.ClickGUI;
import de.vepexlegit.clickgui.settings.Setting;
import de.vepexlegit.utils.FontUtils;

public class Element {
   public ClickGUI clickgui;
   public ModuleButton parent;
   public Setting set;
   public double offset;
   public double x;
   public double y;
   public double width;
   public double height;
   public String setstrg;
   public boolean comboextended;

   public void setup() {
      this.clickgui = this.parent.parent.clickgui;
   }

   public void update() {
      this.x = this.parent.x + this.parent.width + 2.0D;
      this.y = this.parent.y + this.offset;
      this.width = this.parent.width + 10.0D;
      this.height = 15.0D;
      String sname = this.set.getName().split(":")[1];
      if (this.set.isCheck()) {
         this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
         double textx = this.x + this.width - (double) FontUtils.getStringWidth(this.setstrg);
         if (textx < this.x + 13.0D) {
            this.width += this.x + 13.0D - textx + 1.0D;
         }
      } else if (this.set.isCombo()) {
         this.height = this.comboextended ? (double)(this.set.getOptions().length * (FontUtils.getFontHeight() + 2) + 15) : 15.0D;
         this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
         int longest = FontUtils.getStringWidth(this.setstrg);
         String[] var3 = this.set.getOptions();
         int var4 = var3.length;
         for (int var5 = 0; var5 < var4; ++var5) {
            String s = var3[var5];
            int temp = FontUtils.getStringWidth(s);
            if (temp > longest) {
               longest = temp;
            }
         }
         double textx = this.x + this.width - (double)longest;
         if (textx < this.x) {
            this.width += this.x - textx + 1.0D;
         }
      } else if (this.set.isSlider()) {
         this.setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
         String displayval = "" + (double)Math.round(this.set.getValDouble() * 100.0D) / 100.0D;
         String displaymax = "" + (double)Math.round(this.set.getMax() * 100.0D) / 100.0D;
         double textx = this.x + this.width - (double)FontUtils.getStringWidth(this.setstrg) - (double)FontUtils.getStringWidth(displaymax) - 4.0D;
         if (textx < this.x) {
            this.width += this.x - textx + 1.0D;
         }
      }
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      return this.isHovered(mouseX, mouseY);
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {}

   public boolean isHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
   }
}
