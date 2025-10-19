package service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.EntregaModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;


public class CadastroEntregasService {

    EntregaModel entrega = new EntregaModel();
    public Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public Response response;
    String baseUrl="http://localhost:8080";
    String idDelivery;
    JSONObject jsonSchema;
    String schemsaPath = "src/test/resources/schemas/";
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFildsDelivery(String field, String value) {
        switch (field){
            case "numeroPedido" -> entrega.setNumeroPedido(Integer.parseInt(value));
            case "nomeEntregador" -> entrega.setNomeEntregador(value);
            case "statusEntrega" -> entrega.setStatusEntrega(value);
            case "dataEntrega" -> entrega.setDataEntrega(value);
            default -> throw new IllegalArgumentException("Unexpected value: " + field);
        }
    }

    public void creatDelivery(String endpoint){
        String url=baseUrl+endpoint;
        String body = gson.toJson(entrega);
        response = given().contentType(ContentType.JSON)
                .body(body)
                .accept(ContentType.JSON)
                .when()
                .post(url)
                .then()
                .extract()
                .response();

    }

    public void retiveId(){
       idDelivery = String.valueOf(gson.fromJson(response.jsonPath().prettify(), EntregaModel.class).getNumeroPedido());
    }

    public void deletarDelivery(String endpoint){
        String url=baseUrl+endpoint;
        response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException{
        try(InputStream inputStream = Files.newInputStream(Paths.get(filePath))){
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContrato(String nameContrato) throws IOException {
        switch (nameContrato){
            case "Cadastro de entrega bem sucedida" -> jsonSchema = loadJsonFromFile(schemsaPath + "cadastro-entrega-bem-sucedida.json");
            default -> throw new IllegalArgumentException("Unexpected value: " + nameContrato);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException{
        JSONObject jsonResponse = new JSONObject(response.getBody().asString());
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());
        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());
        Set<ValidationMessage> schemaValidationErrors = schema.validate(jsonResponseNode);
        return schemaValidationErrors;
    }

}
