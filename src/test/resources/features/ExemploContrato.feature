#language: pt

  Funcionalidade: Validar contrato de cadastro de entrega

    Cenário: Validar o contrato ao realizaar um cadastro bem sucedida
      Dado que eu tenha os seguintes dados de entrga:
        | campo          | valor        |
        | numeroPedido   | 1            |
        | nomeEntregador | Ana Silva    |
        | statusEntrega  | EM_SEPARACAO |
        | dataEntrega    | 2025-02-14   |
      Quando eu enviar a requisicao para o endpoint "/entregas" dee cadastro de entregas
      Entao status code da resposta deve ser 201
      E o contrato selecionado vai ser "Cadastro de entrega bem sucedida"
      Entao a resposta da requisicao de acordo com o contrato selecionado