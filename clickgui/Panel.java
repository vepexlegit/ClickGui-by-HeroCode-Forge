package de.vepexlegit.clickgui;

import de.vepexlegit.Client;
import de.vepexlegit.clickgui.elements.ModuleButton;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

public class Panel {
   public String title;
   public double x;
   public double y;
   private double x2;
   private double y2;
   public double width;
   public double height;
   public boolean dragging;
   public boolean extended;
   public boolean visible;
   public ArrayList<ModuleButton> Elements = new ArrayList();
   public ClickGUI clickgui;

   public Panel(String ititle, double ix, double iy, double iwidth, double iheight, boolean iextended, ClickGUI parent) {
      this.title = ititle;
      this.x = ix;
      this.y = iy;
      this.width = iwidth;
      this.height = iheight;
      this.extended = iextended;
      this.dragging = false;
      this.visible = true;
      this.clickgui = parent;
      this.setup();
   }

   public void setup() {}

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if (this.visible) {
         if (this.dragging) {
            this.x = this.x2 + (double)mouseX;
            this.y = this.y2 + (double)mouseY;
         }
         Color temp = ColorUtils.getClickGUIColor().darker();
         int outlineColor = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170)).getRGB();
         RenderUtils.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -15592942);
         if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
            RenderUtils.drawRect(this.x - 2.0D, this.y, this.x, this.y + this.height, outlineColor);
            FontUtils.drawStringWithShadow(this.title, this.x + 2.0D, this.y + this.height / 2.0D - (double)(FontUtils.getFontHeight() / 2), -1052689);
         } else if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("JellyLike")) {
            RenderUtils.drawRect(this.x + 4.0D, this.y + 2.0D, this.x + 4.3D, this.y + this.height - 2.0D, -5592406);
            RenderUtils.drawRect(this.x - 4.0D + this.width, this.y + 2.0D, this.x - 4.3D + this.width, this.y + this.height - 2.0D, -5592406);
            FontUtils.drawTotalCenteredStringWithShadow(this.title, this.x + this.width / 2.0D, this.y + this.height / 2.0D, -1052689);
         }
         if (this.extended && !this.Elements.isEmpty()) {
            double startY = this.y + this.height;
            int epanelcolor = Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New") ? -14474461 : (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("JellyLike") ? -1156246251 : 0);
            ModuleButton et;
            for (Iterator var9 = this.Elements.iterator(); var9.hasNext(); startY += et.height + 1.0D) {
               et = (ModuleButton)var9.next();
               if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
                  RenderUtils.drawRect(this.x - 2.0D, startY, this.x + this.width, startY + et.height + 1.0D, outlineColor);
               }
               RenderUtils.drawRect(this.x, startY, this.x + this.width, startY + et.height + 1.0D, epanelcolor);
               et.x = this.x + 2.0D;
               et.y = startY;
               et.width = this.width - 4.0D;
               et.drawScreen(mouseX, mouseY, partialTicks);
            }
            RenderUtils.drawRect(this.x, startY + 1.0D, this.x + this.width, startY + 1.0D, epanelcolor);
         }
      }
   }

   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (!this.visible) {
         return false;
      } else if (mouseButton == 0 && this.isHovered(mouseX, mouseY)) {
         this.x2 = this.x - (double)mouseX;
         this.y2 = this.y - (double)mouseY;
         this.dragging = true;
         return true;
      } else if (mouseButton == 1 && this.isHovered(mouseX, mouseY)) {
         this.extended = !this.extended;
         return true;
      } else {
         if (this.extended) {
            Iterator var4 = this.Elements.iterator();
            while (var4.hasNext()) {
               ModuleButton et = (ModuleButton)var4.next();
               if (et.mouseClicked(mouseX, mouseY, mouseButton)) {
                  return true;
               }
            }
         }
         return false;
      }
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      if (this.visible) {
         if (state == 0) {
            this.dragging = false;
         }
      }
   }

   public boolean isHovered(int mouseX, int mouseY) {
      return (double)mouseX >= this.x && (double)mouseX <= this.x + this.width && (double)mouseY >= this.y && (double)mouseY <= this.y + this.height;
   }
}
