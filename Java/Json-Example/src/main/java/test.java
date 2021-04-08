import java.util.Iterator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;




public class test {

    public static void main(String args[]) {
        String x =
                "{\n" +
                        "  \"id\": 123,\n" +
                        "  \"name\": { \"first\": \"Pankaj\", \"last\": \"Agarwal\" }, \n" +
                        "  \"permanent\": true,\n" +
                        "  \"address\": {\n" +
                        "    \"street\": \"Albany Dr\",\n" +
                        "    \"city\": \"San Jose\",\n" +
                        "    \"zipcode\": 95129\n" +
                        "  },\n" +
                        "  \"phoneNumbers\": [\n" +
                        "    123456,\n" +
                        "    987654\n" +
                        "  ],\n" +
                        "  \"role\": \"Manager\",\n" +
                        "  \"cities\": [\n" +
                        "    \"Los Angeles\",\n" +
                        "    \"New York\"\n" +
                        "  ],\n" +
                        "  \"properties\": {\n" +
                        "    \"age\": \"29 years\",\n" +
                        "    \"salary\": \"1000 USD\"\n" +
                        "  }\n" +
                        "}\n";

        byte[] jsonData = x.getBytes();

        try {
            JsonNode rootNode = (new ObjectMapper()).readTree(jsonData);
            JsonNode idNode = rootNode.path("id");
            System.out.println("id = " + idNode.asInt());

            String xxx = rootNode.path("name").path("first").asText();
            System.out.println("name = " + xxx);

            JsonNode phoneNosNode = rootNode.path("phoneNumbers");
            Iterator<JsonNode> elements = phoneNosNode.elements();
            while (elements.hasNext()) {
                JsonNode phone = elements.next();
                System.out.println("Phone No = " + phone.asLong());
            }
        }
        catch (Exception r)
        {
            System.out.println(r.getStackTrace());
        }
    }
}
