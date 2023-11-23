package de.vepexlegit.clickgui.settings;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import de.vepexlegit.Client;
import de.vepexlegit.module.Module;

public class SettingsManager {
   private ArrayList<Setting> settings = new ArrayList<Setting>();

   public void rSetting(Setting in) {
      this.settings.add(in);
   }

   public ArrayList<Setting> getSettings() {
      return this.settings;
   }

   public ArrayList<Setting> getSettingsByMod(Module mod) {
      ArrayList<Setting> out = new ArrayList<Setting>();
      Iterator var3 = this.getSettings().iterator();
      while (var3.hasNext()) {
         Setting s = (Setting)var3.next();
         if (s.getParentMod().equals(mod)) {
            out.add(s);
         }
      }
      if (out.isEmpty()) {
         return null;
      } else {
         return out;
      }
   }

   public Setting getSettingByName(String name) {
      Iterator var2 = this.getSettings().iterator();
      Setting set;
      do {
         if (!var2.hasNext()) {
            PrintStream var10000 = System.err;
            StringBuilder var10001 = (new StringBuilder()).append("[");
            Client var10002 = Client.INSTANCE;
            var10000.println(var10001.append("Client").append("] Error Setting NOT found: '").append(name).append("'!").toString());
            return null;
         }
         set = (Setting)var2.next();
      } while (!set.getName().equalsIgnoreCase(name));
      return set;
   }
}
