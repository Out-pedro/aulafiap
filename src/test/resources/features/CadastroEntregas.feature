#language: pt

  @regressivo @smoke
  Funcionalidade: Cadastro de entrega
    Como usuario da api
    Quero cadastrar uma nova entrega
    Para que o registro seja salvo corretamente

    @padrao @smoke
  Cenário: Cadastro de entrega bem-sucedido
    Dado que eu tenha os seguintes dados de entrga:
    | campo          | valor        |
    | numeroPedido   | 1            |
    | nomeEntregador | Ana Silva    |
    | statusEntrega  | EM_SEPARACAO |
    | dataEntrega    | 2025-02-14   |
    Quando eu enviar a requisicao para o endpoint "/entregas" dee cadastro de entregas
    Entao status code da resposta deve ser 201

    Cenário: Cadastro de entrega sem sucesso ao enviar statusEntrega invalido
      Dado que eu tenha os seguintes dados de entrga:
        | campo          | valor        |
        | numeroPedido   | 1            |
        | nomeEntregador | Ana Silva    |
        | statusEntrega  | EM_SEPO |
        | dataEntrega    | 2025-02-14   |
      Quando eu enviar a requisicao para o endpoint "/entregas" dee cadastro de entregas
      Entao status code da resposta deve ser 400
      E a resposta de mensagem de erro deve ser "Dados fornecidos estão em formato inválido."