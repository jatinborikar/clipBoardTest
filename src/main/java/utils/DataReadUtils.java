package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class DataReadUtils {
    public static Map<String,Object> readData(String jsonFilename, String uniqueIdName, String uniqueIdValue) throws IOException, ParseException {

        String dataFilePath = System.getProperty("user.dir") + File.separator + "datafiles" + File.separator;
        JSONParser jsonParser = new JSONParser();
        JSONArray jsonArray = (JSONArray)jsonParser.parse(new FileReader(dataFilePath+jsonFilename+".json"));

        for (Object value : jsonArray) {
            JSONObject jsonObject = (JSONObject)value;
            if(jsonObject.get(uniqueIdName).equals(uniqueIdValue)) {
                JSONObject obj = (JSONObject)value;
                ObjectMapper objectMapper = new ObjectMapper();
                return (Map)objectMapper.readValue(obj.toString(), Map.class);
            }
        }
        return null;
    }
}
