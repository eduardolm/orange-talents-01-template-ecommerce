package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.dto.PurchaseDto;
import br.com.zup.mercadolivre.enums.EmailFrom;
import br.com.zup.mercadolivre.model.ProductQuestion;
import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

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
                "Nova venda...",
                "Você tem uma nova venda: " + purchase + " !" +
                        "\nDados do comprador: " + purchase.getCustomer().getEmail() +
                        "\nFaça o envio rápido para aumentar a satisfação do comprador.");
    }

    public void purchaseSuccess(Purchase purchase) {
        emailService.send(
                purchase.getCustomer().getEmail(),
                EmailFrom.PURCHASE.label,
                "Parabéns! Seu pagamento foi aprovado.",
                "\nSua compra foi finalizada com sucesso.\n" +
                     "\n Entre em contato com o vendedor para combinar o envio do produto.\n" +
                     "\nDados da compra: " + new PurchaseDto(purchase)
        );
    }
    public void purchaseFailure(Purchase purchase) {
        emailService.send(
                purchase.getCustomer().getEmail(),
                EmailFrom.PURCHASE.label,
                "Ocorreu um erro com seu pagamento.",
                "\nAcesse o link a seguir e faça o pagamento de sua compra.\n" +
                     "\n Entre em contato com o vendedor para combinar o envio do produto.\n" +
                     "\nDados da compra: " + new PurchaseDto(purchase) +
                     "\nLink para pagamento: " + purchase.getPaymentGateway()
                        .createRedirectUrl(purchase, UriComponentsBuilder.newInstance())
        );
    }

}
