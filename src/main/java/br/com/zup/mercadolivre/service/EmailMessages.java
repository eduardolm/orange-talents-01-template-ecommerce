package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.enums.EmailFrom;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class EmailMessages {

    @Autowired
    private IEmailService emailService;

    public void question(@Valid ProductQuestion productQuestion) {
        emailService.send(
                productQuestion.getProductOwner().getEmail(),
                EmailFrom.QUESTION.label,
                "Nova pergunta...",
                "Você recebeu uma nova pergunta no produto: " + productQuestion.getProduct().getName() + "\n" +
                        "\nResponda rápido para aumentar as chances de fechar negócio! " + "\n" +
                        "\nPergunta: " + productQuestion.getTitle());
    }

    public void purchase(Purchase purchase) {
        emailService.send(
                purchase.getProduct().getProductOwner().getEmail(),
                EmailFrom.PURCHASE.label ,
                "Nova compra...",
                "Você tem uma nova compra: " + purchase + " !" +
                        "\nDados do comprador: " + purchase.getCustomer().getEmail() +
                        "\nFaça o envio rápido para aumentar a satisfação do comprador.");
    }
}