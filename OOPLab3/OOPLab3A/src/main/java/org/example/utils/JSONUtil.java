package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DesktopDTO;
import org.example.dto.MobileDTO;

public class JSONUtil {

    private final static ObjectMapper mapper = new ObjectMapper();
    public static String toJSON(DesktopDTO desktopDTO) {
        String jsonString;
        try {
            jsonString = mapper.writeValueAsString(desktopDTO);
            return jsonString;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static MobileDTO fromJSON(String json) {
        try {
            return mapper.readValue(json, MobileDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
