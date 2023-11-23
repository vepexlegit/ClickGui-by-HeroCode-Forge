package de.vepexlegit.utils;

import de.vepexlegit.Client;
import java.awt.Color;

public class ColorUtils {
   public static Color getClickGUIColor() {
      return new Color((int) Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiRed").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiGreen").getValDouble(), (int)Client.INSTANCE.getSettingsManager().getSettingByName("ClickGui:GuiBlue").getValDouble());
   }
}
