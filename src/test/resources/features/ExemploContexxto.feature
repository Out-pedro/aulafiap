#language: pt
  Funcionalidade: Deletar uma entrega

    Contexto: Cadastro de entrega bem-sucedido
      Dado que eu tenha os seguintes dados de entrga:
        | campo          | valor        |
        | numeroPedido   | 1            |
        | nomeEntregador | Ana Silva    |
        | statusEntrega  | EM_SEPARACAO |
        | dataEntrega    | 2025-02-14   |
      Quando eu enviar a requisicao para o endpoint "/entregas" dee cadastro de entregas
      Entao status code da resposta deve ser 201

      Cenário: Deve ser possivel deletar uma entrega
        Dado que eu recupere o ID criado no contexto
        Quando eu enviar o ID na requisicao para o endpoint "/eentregas" de delecao de entreegas
        Entao status code da resposta deve ser 400