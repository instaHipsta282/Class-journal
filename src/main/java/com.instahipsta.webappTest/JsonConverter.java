package com.instahipsta.webappTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String objectToJson(Object object) {
        String json = null;
        try { json = mapper.writeValueAsString(object); }
        catch (JsonProcessingException e) { e.printStackTrace(); }

        return json;
    }
}
