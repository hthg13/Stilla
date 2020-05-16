package is.hi.lokaverkefni.stilla.stilla_backend.Extras;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import is.hi.lokaverkefni.stilla.stilla_backend.Extras.MergeDuplicateFieldsJsonNodeDeserializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

public class XmlRequest {

    /**
     * Connects with veðurstofan.is xml service and returnes a Json string with the response
     *
     * example to call method
     * 		String url = "https://xmlweather.vedur.is/?op_w=xml&type=forec&lang=is&view=xml&ids=1";
     * 		String jsonStringResponse = xmlRequestTo(url)
     *
     * @param url the correctly formatted string to call veðurstofan with
     * @return Json string with response
     */
    public static String xmlRequestTo(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        return xmlResponseEntityStringToJsonString(response);
    }

    private static String xmlResponseEntityStringToJsonString(ResponseEntity<String> xmlString) {
        XmlMapper xmlMapper = new XmlMapper();
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode node = null;
        String json = null;

        // trick XmlMapper to use whatever we want: https://stackoverflow.com/questions/54597977/cannot-deserialize-convert-unwrapped-list-when-its-second-in-class-using-jack
        // uses the java class MergeDuplicateFieldsJsonNodeDeserializer
        SimpleModule mergeDuplicatesModule = new SimpleModule("Merge duplicated fields in array");
        mergeDuplicatesModule.addDeserializer(JsonNode.class, new MergeDuplicateFieldsJsonNodeDeserializer());
        xmlMapper.registerModule(mergeDuplicatesModule);

        try {
            node = xmlMapper.readTree(xmlString.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            json = jsonMapper.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
