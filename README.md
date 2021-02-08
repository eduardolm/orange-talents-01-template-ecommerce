# API que simula o backend do Mercadolivre.

## Objetivos
Desenvolver uma API que simula o backend do Mercadolivre.
Essa API deve permitir cadastro de usuários, cadastro de categorias, de produtos. Deve permitir escrever opinões sobre os produtos, enviar perguntas ao vendedor e comprar o produto.
Após a compra do produto, a API simula o reotnro do gateway de pagamento com status de sucesso ou erro.

## Obsevações
Uma das funcionalidades da API é o upload de images do produto. Seguindo as práticas uauais do mercado, a funcionalidade de upload de imagens envia os arquivos para o serviço S3 da AWS.
De forma a evitar gastos com envio de arquivos, armazenagem e etc, nossa API utiliza o Localstack.

Localstack emula alguns serviços da AWS, entre eles o S3.
Executamos o Localstack num container docker.

Em algumas etapas do fluxo de compra do produto, a API deve enviar e-mail para o vendedor e comprador. Estes envios estão sendo feitos usando bibliotecas nativas do Spring para envio de e-mails.
