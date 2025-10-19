package hook;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

public class Hook {

    @BeforeAll
    public static void beforeAll(){
        inicializarAmbiente();
    }

    @AfterAll
    public static void afterAll(){
        limparAmbiente();
    }

    @Before
    public void before(){
        prepararDadosParaTeste();
    }

    @After
    public void after(){
        limparDadosDepoisDoTste();
    }

    private static void inicializarAmbiente() {
        System.out.println("Inicializando ambiente");
    }

    private static void limparAmbiente() {
        System.out.println("Limpando ambiente");
    }

    private void prepararDadosParaTeste(){
        System.out.println("dados preparados para o cenário dee teste.");
    }
    private  void limparDadosDepoisDoTste(){
        System.out.println("Dados limpos após o ceenário de teste.");
    }
}
