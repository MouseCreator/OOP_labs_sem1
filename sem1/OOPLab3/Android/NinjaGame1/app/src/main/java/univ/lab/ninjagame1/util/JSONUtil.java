package univ.lab.ninjagame1.util;

import com.google.gson.Gson;

import univ.lab.ninjagame1.dto.DesktopDTO;
import univ.lab.ninjagame1.dto.MobileDTO;

public class JSONUtil {
   private static final Gson gson = new Gson();

   public static DesktopDTO fromJson(String string) {
       return gson.fromJson(string, DesktopDTO.class);
   }

   public static String toJson(MobileDTO mobileDTO) {
       return gson.toJson(mobileDTO);
   }
}
