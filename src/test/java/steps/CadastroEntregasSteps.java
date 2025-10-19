package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import model.ErrorMenssageModel;
import org.junit.Assert;
import service.CadastroEntregasService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CadastroEntregasSteps {
    CadastroEntregasService cadastroEntregasService = new CadastroEntregasService();

    @Dado("que eu tenha os seguintes dados de entrga:")
    public void queEuTenhaOsSeguintesDadosDeEntrga(List<Map<String, String>> rows) {
        for (Map<String, String> columns : rows) {
            cadastroEntregasService.setFildsDelivery(columns.get("campo"), columns.get("valor"));
        }
    }

    @Quando("eu enviar a requisicao para o endpoint {string} dee cadastro de entregas")
    public void euEnviarARequisicaoParaOEndpointDeeCadastroDeEntregas(String endpoint) {
        cadastroEntregasService.creatDelivery(endpoint);
    }

    @Entao("status code da resposta deve ser {int}")
    public void statusCodeDaRespostaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, cadastroEntregasService.response.statusCode());
    }

    @E("a resposta de mensagem de erro deve ser {string}")
    public void aRespostaDeMensagemDeErroDeveSer(String message) {
        ErrorMenssageModel errorMenssageModel = cadastroEntregasService.gson.fromJson(
                cadastroEntregasService.response.jsonPath().prettify(), ErrorMenssageModel.class);
        Assert.assertEquals(message, errorMenssageModel.getMessage());
    }

    @Dado("que eu recupere o ID criado no contexto")
    public void queEuRecupereOIDCriadoNoContexto() {
        cadastroEntregasService.retiveId();
    }

    @Quando("eu enviar o ID na requisicao para o endpoint {string} de delecao de entreegas")
    public void euEnviarOIDNaRequisicaoParaOEndpointDeDelecaoDeEntreegas(String endpoint) {
        cadastroEntregasService.deletarDelivery(endpoint);
    }

    @E("o contrato selecionado vai ser {string}")
    public void oContratoSelecionadoVaiSer(String contrato) throws IOException {
        cadastroEntregasService.setContrato(contrato);
    }

    @Entao("a resposta da requisicao de acordo com o contrato selecionado")
    public void aRespostaDaRequisicaoDeAcordoComOContratoSelecionado() throws IOException {
        Set<ValidationMessage> validationResponse = cadastroEntregasService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. EErros encontrados" + validationResponse, validationResponse.isEmpty());
    }
}
