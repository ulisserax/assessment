package org.todoapp.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.HashMap;

public class AppiumUtils {

    public HashMap<String , String> getJsonData (String jsonFilePath) throws IOException {
        String jsonContent = FileUtils.readFileToString(new java.io.File(jsonFilePath), "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonContent, new TypeReference<HashMap<String, String>>() {});
    }
}
