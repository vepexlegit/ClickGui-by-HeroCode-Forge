package de.vepexlegit.clickgui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import de.vepexlegit.Client;
import de.vepexlegit.clickgui.elements.Element;
import de.vepexlegit.clickgui.elements.ModuleButton;
import de.vepexlegit.clickgui.elements.menu.ElementSlider;
import de.vepexlegit.clickgui.settings.SettingsManager;
import de.vepexlegit.module.Module;
import de.vepexlegit.utils.ColorUtils;
import de.vepexlegit.utils.FontUtils;
import de.vepexlegit.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class ClickGUI extends GuiScreen {
   public static ArrayList<Panel> panels = new ArrayList<Panel>();
   public static ArrayList<Panel> rpanels = new ArrayList<Panel>();
   private ModuleButton mb = null;
   public SettingsManager setmgr;

   public ClickGUI() {
      this.setmgr = Client.INSTANCE.getSettingsManager();
      FontUtils.setupFontUtils();
      panels = new ArrayList<Panel>();
      double pwidth = 80.0D;
      double pheight = 15.0D;
      double px = 10.0D;
      double py = 10.0D;
      boolean extended = false;
      int i = 0;
      Module.Category[] var11 = Module.Category.values();
      int var12 = var11.length;
      for (int var13 = 0; var13 < var12; ++var13) {
         final Module.Category c = var11[var13];
         String title = Character.toUpperCase(c.name().toLowerCase().charAt(0)) + c.name().toLowerCase().substring(1);
         panels.add(new Panel(title, px, py, pwidth, pheight, extended, this) {
            @Override
            public void setup() {
               super.setup();
               Iterator iterator = Client.INSTANCE.getModuleManager().getModules().iterator();
               while (iterator.hasNext()) {
                  Module m = (Module)iterator.next();
                  if (m.getCategory().equals(c)) {
                     this.Elements.add(new ModuleButton(m, this));
                  }
               }
            }
         });
         ++i;
      }
      rpanels = new ArrayList<Panel>();
      Iterator iterator = panels.iterator();
      while (iterator.hasNext()) {
         Panel p = (Panel)iterator.next();
         rpanels.add(p);
      }
      Collections.reverse(rpanels);
   }

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      Iterator iterator = panels.iterator();
      while (iterator.hasNext()) {
         Panel p = (Panel)iterator.next();
         p.drawScreen(mouseX, mouseY, partialTicks);
      }
      ScaledResolution s = new ScaledResolution(this.mc);
      GL11.glPushMatrix();
      GL11.glTranslated((double)s.getScaledWidth(), (double)s.getScaledHeight(), 0.0D);
      GL11.glScaled(0.5D, 0.5D, 0.5D);
      FontUtils.drawStringWithShadow("byHeroCode", (double)(-Minecraft.getMinecraft().fontRendererObj.getStringWidth("byHeroCode")), (double)(-Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT), -15599509);
      GL11.glPopMatrix();
      this.mb = null;
      Iterator var16 = panels.iterator();
      Panel panel;
      Iterator var7;
      ModuleButton b;
      label105:
      while (var16.hasNext()) {
         panel = (Panel)var16.next();
         if (panel != null && panel.visible && panel.extended && panel.Elements != null && panel.Elements.size() > 0) {
            var7 = panel.Elements.iterator();
            while (var7.hasNext()) {
               b = (ModuleButton)var7.next();
               if (b.listening) {
                  this.mb = b;
                  break label105;
               }
            }
         }
      }
      var16 = panels.iterator();
      label85:
      while (true) {
         do {
            do {
               do {
                  if (!var16.hasNext()) {
                     if (this.mb != null) {
                        drawRect(0, 0, this.width, this.height, -2012213232);
                        GL11.glPushMatrix();
                        GL11.glTranslatef((float)(s.getScaledWidth() / 2), (float)(s.getScaledHeight() / 2), 0.0F);
                        GL11.glScalef(4.0F, 4.0F, 0.0F);
                        FontUtils.drawTotalCenteredStringWithShadow("Listening...", 0.0D, -10.0D, -1);
                        GL11.glScalef(0.5F, 0.5F, 0.0F);
                        FontUtils.drawTotalCenteredStringWithShadow("Press 'ENF' to unbind " + this.mb.mod.getName() + (this.mb.mod.getKey() > -1 ? " (" + Keyboard.getKeyName(this.mb.mod.getKey()) + ")" : ""), 0.0D, 0.0D, -1);
                        GL11.glPopMatrix();
                     }
                     super.drawScreen(mouseX, mouseY, partialTicks);
                     return;
                  }
                  panel = (Panel)var16.next();
               } while (!panel.extended);
            } while (!panel.visible);
         } while (panel.Elements == null);
         var7 = panel.Elements.iterator();
         while (true) {
            do {
               do {
                  do {
                     if (!var7.hasNext()) {
                        continue label85;
                     }
                     b = (ModuleButton)var7.next();
                  } while (!b.extended);
               } while (b.menuelements == null);
            } while (b.menuelements.isEmpty());
            double off = 0.0D;
            Color temp = ColorUtils.getClickGUIColor().darker();
            int outlineColor = (new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 170)).getRGB();
            Element e;
            for (Iterator var13 = b.menuelements.iterator(); var13.hasNext(); off += e.height) {
               e = (Element)var13.next();
               e.offset = off;
               e.update();
               if (Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:Design").getValString().equalsIgnoreCase("New")) {
                  RenderUtils.drawRect(e.x, e.y, e.x + e.width + 2.0D, e.y + e.height, outlineColor);
               }
               e.drawScreen(mouseX, mouseY, partialTicks);
            }
         }
      }
   }

   public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
      if (this.mb == null) {
         Iterator var4 = rpanels.iterator();
         label70:
         while (true) {
            Panel panel;
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        var4 = rpanels.iterator();
                        do {
                           if (!var4.hasNext()) {
                              try {
                                 super.mouseClicked(mouseX, mouseY, mouseButton);
                              } catch (IOException var10) {
                                 var10.printStackTrace();
                              }
                              return;
                           }
                           panel = (Panel)var4.next();
                        } while (!panel.mouseClicked(mouseX, mouseY, mouseButton));

                        return;
                     }
                     panel = (Panel)var4.next();
                  } while (!panel.extended);
               } while (!panel.visible);
            } while (panel.Elements == null);
            Iterator var6 = panel.Elements.iterator();
            while (true) {
               ModuleButton b;
               do {
                  if (!var6.hasNext()) {
                     continue label70;
                  }
                  b = (ModuleButton)var6.next();
               } while (!b.extended);
               Iterator var8 = b.menuelements.iterator();
               while (var8.hasNext()) {
                  Element e = (Element)var8.next();
                  if (e.mouseClicked(mouseX, mouseY, mouseButton)) {
                     return;
                  }
               }
            }
         }
      }
   }

   public void mouseReleased(int mouseX, int mouseY, int state) {
      if (this.mb == null) {
         Iterator var4 = rpanels.iterator();
         label59:
         while (true) {
            Panel panel;
            do {
               do {
                  do {
                     if (!var4.hasNext()) {
                        var4 = rpanels.iterator();
                        while (var4.hasNext()) {
                           panel = (Panel)var4.next();
                           panel.mouseReleased(mouseX, mouseY, state);
                        }
                        super.mouseReleased(mouseX, mouseY, state);
                        return;
                     }
                     panel = (Panel)var4.next();
                  } while (!panel.extended);
               } while (!panel.visible);
            } while (panel.Elements == null);
            Iterator var6 = panel.Elements.iterator();
            while (true) {
               ModuleButton b;
               do {
                  if (!var6.hasNext()) {
                     continue label59;
                  }
                  b = (ModuleButton)var6.next();
               } while (!b.extended);
               Iterator var8 = b.menuelements.iterator();
               while (var8.hasNext()) {
                  Element e = (Element)var8.next();
                  e.mouseReleased(mouseX, mouseY, state);
               }
            }
         }
      }
   }

   protected void keyTyped(char typedChar, int keyCode) {
      Iterator var3 = rpanels.iterator();
      while (true) {
         Panel p;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var3.hasNext()) {
                           try {
                              super.keyTyped(typedChar, keyCode);
                           } catch (IOException var8) {
                              var8.printStackTrace();
                           }
                           return;
                        }
                        p = (Panel)var3.next();
                     } while (p == null);
                  } while (!p.visible);
               } while (!p.extended);
            } while (p.Elements == null);
         } while (p.Elements.size() <= 0);
         Iterator var5 = p.Elements.iterator();
         while (var5.hasNext()) {
            ModuleButton e = (ModuleButton)var5.next();
            try {
               if (e.keyTyped(typedChar, keyCode)) {
                  return;
               }
            } catch (IOException var9) {
               var9.printStackTrace();
            }
         }
      }
   }

   public void initGui() {}

   public void onGuiClosed() {
      Iterator var1 = rpanels.iterator();
      label49:
      while (true) {
         Panel panel;
         do {
            do {
               do {
                  if (!var1.hasNext()) {
                     return;
                  }
                  panel = (Panel)var1.next();
               } while (!panel.extended);
            } while (!panel.visible);
         } while (panel.Elements == null);
         Iterator var3 = panel.Elements.iterator();
         while (true) {
            ModuleButton b;
            do {
               if (!var3.hasNext()) {
                  continue label49;
               }
               b = (ModuleButton)var3.next();
            } while (!b.extended);
            Iterator var5 = b.menuelements.iterator();
            while (var5.hasNext()) {
               Element e = (Element)var5.next();
               if (e instanceof ElementSlider) {
                  ((ElementSlider)e).dragging = false;
               }
            }
         }
      }
   }

   public void closeAllSettings() {
      Iterator var1 = rpanels.iterator();
      while (true) {
         Panel p;
         do {
            do {
               do {
                  do {
                     do {
                        if (!var1.hasNext()) {
                           return;
                        }
                        p = (Panel)var1.next();
                     } while (p == null);
                  } while (!p.visible);
               } while (!p.extended);
            } while (p.Elements == null);
         } while (p.Elements.size() <= 0);
         ModuleButton e;
         for (Iterator var3 = p.Elements.iterator(); var3.hasNext(); e.extended = false) {
            e = (ModuleButton)var3.next();
         }
      }
   }
}
