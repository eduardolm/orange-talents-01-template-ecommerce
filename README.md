# Mercadolivre - API
![Version](src/main/resources/static/img/version.svg)
![License](src/main/resources/static/img/license.svg)
![Coverage](src/main/resources/static/img/coverage.svg)

## Objetivos
Desenvolver uma API que simula o backend do Mercadolivre.
Essa API deve permitir cadastro de usuários, cadastro de categorias, de produtos. Deve permitir escrever opinões sobre os produtos, enviar perguntas ao vendedor e comprar o produto.
Após a compra do produto, a API simula o reotnro do gateway de pagamento com status de sucesso ou erro.

## Tecnologias utilizadas
Este projeto foi desenvolvido utilizando-se as seguintes tecnologias:

 + Java 11
 + Spring Boot 2.4.2
 + Docker
 + Localstack
 + MySQL 8
 + Github
 
## Obsevações
Uma das funcionalidades da API é o upload de images do produto. Seguindo as práticas uauais do mercado, a funcionalidade de upload de imagens envia os arquivos para o serviço S3 da AWS.
De forma a evitar gastos com envio de arquivos, armazenagem e etc, nossa API utiliza o Localstack.

Localstack emula alguns serviços da AWS, entre eles o S3.
Executamos o Localstack num container docker. 
O projeto contém o Dockerfile necessário para a execução do Localstack em um container.

Em algumas etapas do fluxo de compra do produto, a API deve enviar e-mail para o vendedor e comprador. Estes envios estão sendo feitos usando bibliotecas nativas do Spring para envio de e-mails.

## Endpoints & Payloads
### Endpoints
#### Usuários
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar usuário | _/api/v1/users_ | POST
Autenticar | _/api/v1/auth_ | POST

#### Categorias
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar categoria | _/api/v1/categories_ | POST

#### Produtos
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar produto | _/api/v1/products_ | POST

#### Imagens dos produtos
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Adicionar imagens | _/api/v1/products/{id}/images_ | POST

#### Opiniões
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar opinião | _/api/v1/products/{id}/review_ | POST

#### Perguntas
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar pergunta | _/api/v1/products/{id}/questions_ | POST

#### Detalhes do Produto
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Exibir detalhes | _/api/v1/products/{id}_ | GET

#### Compra
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar compra | _/api/v1/purchases_ | POST

#### Simula PagSeguro
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar pagamento | _/api/v1/retorno-pagseguro/{id}_ | POST

#### Simula Paypal
**Ação** | **Endpoint** | **Método**
---------- | ------------ | ----------
Criar pagamento | _/api/v1/retorno-paypal/{id}_ | POST

### Payloads - (request & response)
#### Usuários
##### POST - request (Criar usuário)
    {
        "email": "user3@email.com",
        "password": "pass1234"
    }

##### POST - response (Criar usuário)
    {
        "id": 4,
        "email": "user3@email.com"
    }
    
##### POST - request (Autenticar usuário)
        {
            "email": "user2@email.com",
            "password": "pass5534"
        }
    
##### POST - response (Autenticar usuário)
        {
            "token": "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJEZXNhZmlvIE1lcmNhZG9MaXZyZS
            BaVVAgT3JhbmdlIFRhbGVudHMiLCJzdWIiOiJ1c2VyMkBlbWFpbC5jb20iLCJpYXQiOjE
            2MTI4Mjk3NjYsImV4cCI6MTYxMjkxNjE2Nn0.Wx_J3_ZZdevWAd_Qm2190rcqaa8ak3UP
            3dmp4nR66Xc"
        }

#### Categorias
##### POST - request
    {
        "name": "Celulares & Tablets",
        "idParentCategory": 1
    }

##### POST - response
    {
        "id": 2,
        "name": "Celulares & Tablets",
        "parent": {
            "id": 1,
            "name": "Tecnologia",
            "parent": null
        }
    }

#### Produtos
##### POST - request
    {
        "name": "Galaxy S20",
        "quantity": 100,
        "description": "Celular top na categoria",
        "price": "2000",
        "categoryId": 2,
        "characteristics": [
            {
                "name": "Peso",
                "description": "145g"
            },
                    {
                "name": "Conectividade",
                "description": "5G, WiFi, Bluetooth"
            },         {
                "name": "Itens inclusos",
                "description": "Celular, carregador, cabo mini-usb"
            }
        ]
    }

##### POST - response
    {
        "id": 1,
        "name": "Galaxy S20",
        "quantity": 100,
        "description": "Celular top na categoria",
        "price": 2000,
        "category": {
            "id": 2,
            "name": "Celulares & Tablets",
            "parent": {
                "id": 1,
                "name": "Tecnologia",
                "parent": null,
                "hibernateLazyInitializer": {}
            }
        },
        "productOwner": {
            "id": 1,
            "email": "lodi001@uol.com.br"
        },
        "characteristics": [
            {
                "id": 1,
                "name": "Peso",
                "description": "145g"
            },
            {
                "id": 3,
                "name": "Itens inclusos",
                "description": "Celular, carregador, cabo mini-usb"
            },
            {
                "id": 2,
                "name": "Conectividade",
                "description": "5G, WiFi, Bluetooth"
            }
        ],
        "images": [],
        "questions": [],
        "reviews": [],
        "averageGrade": 0.0,
        "total": 0
    }

#### Imagens dos produtos
##### POST - request
    Upload realizado por form-data. Não possui json body.

#### Opiniões
##### POST - request
    {
        "grade": 5,
        "title": "Excelente",
        "description": "Muito rápido e leve. A tela é ótima."
    }

##### POST - response
    {
        "grade": 5,
        "title": "Excelente",
        "description": "Muito rápido e leve. A tela é ótima."
    }

#### Perguntas
##### POST - request
    {
        "title": "Tem 5 unidades para envio imediato?"
    }

##### POST - response
    {
        "id": 3,
        "title": "Tem 5 unidades para envio imediato?",
        "product": "Galaxy S20",
        "interested": "user@email.com",
        "createdAt": "2021-02-08"
    }

#### Detalhes do produto
##### GET - response
    {
        "id": 1,
        "name": "Galaxy S20",
        "quantity": 94,
        "description": "Celular top na categoria",
        "price": 2000.00,
        "category": {
            "id": 2,
            "name": "Celulares & Tablets",
            "parent": {
                "id": 1,
                "name": "Tecnologia",
                "parent": null,
                "hibernateLazyInitializer": {}
            }
        },
        "productOwner": {
            "id": 1,
            "email": "lodi001@uol.com.br"
        },
        "characteristics": [
            {
                "id": 2,
                "name": "Conectividade",
                "description": "5G, WiFi, Bluetooth"
            },
            {
                "id": 1,
                "name": "Peso",
                "description": "145g"
            },
            {
                "id": 3,
                "name": "Itens inclusos",
                "description": "Celular, carregador, cabo mini-usb"
            }
        ],
        "images": [
            "https://mercadolivre-bucket1612446310284.s3.amazonaws.com/_productId_1_lodi001@uol.com.br_B1B-50x.jpg",
            "https://mercadolivre-bucket1612446310284.s3.amazonaws.com/_productId_1_lodi001@uol.com.br_CPF.jpg"
        ],
        "questions": [
            "Qual o valor do frete para São Paulo capital?",
            "Tem 5 unidades para envio imediato?",
            "Tem para pronta entrega?"
        ],
        "reviews": [
            {
                "description": "Melhor celular que já tive.",
                "title": "Muito bom"
            },
            {
                "description": "Muito rápido e leve. A tela é ótima.",
                "title": "Excelente"
            },
            {
                "description": "Bom produto, vários recursos, mas um pouco cato demais.",
                "title": "Bom produto!"
            }
        ],
        "averageGrade": 4.5,
        "total": 3
    }

#### Compras
##### POST - request
    {
        "quantity": 2,
        "productId": 1,
        "gateway": "PAG_SEGURO"
    }

##### POST - response
    pagseguro.com/4?redirectUrl=http://localhost:8080/retorno-pagseguro/4

#### Simula PagSeguro
##### POST - request
    {
        "transactionId": 2240,
        "status": "SUCESSO"
    }

##### POST - response
    {
        "id": 4,
        "product": {
            "id": 1,
            "name": "Galaxy S20",
            "quantity": 92,
            "description": "Celular top na categoria",
            "price": 2000.00
        },
        "quantity": 2,
        "paymentGateway": "PAG_SEGURO"
    }

#### Simula Paypal
##### POST - request
    {
        "transactionId": 2240,
        "status": "1"
    }

##### POSt - response
    {
        "id": 4,
        "product": {
            "id": 1,
            "name": "Galaxy S20",
            "quantity": 92,
            "description": "Celular top na categoria",
            "price": 2000.00
        },
        "quantity": 2,
        "paymentGateway": "PAYPAL"
    }
        