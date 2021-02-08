package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.PurchaseRequestDto;
import br.com.zup.mercadolivre.enums.PaymentGateway;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import br.com.zup.mercadolivre.service.EmailMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.validation.BindException;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController extends ObjectHandler {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private EmailMessages email;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid PurchaseRequestDto request,
                                    UriComponentsBuilder uriComponentsBuilder) throws BindException {

        Product product = checkProductExists(request.getProductId());
        int quantity = request.getQuantity();
        boolean isStockEnough = product.subtractStock(request.getQuantity());

        if (isStockEnough) {
            User customer = checkUserExists();
            PaymentGateway gateway = request.getGateway();

            Purchase purchase = new Purchase(product, quantity, customer, gateway);
            purchaseRepository.save(purchase);
            email.purchase(purchase);

            return ResponseEntity.ok().body(purchase.redirectUrl(uriComponentsBuilder));
        }

        BindException stockIssue = new BindException(request, "purchaseRequestDto");
        stockIssue.reject(null, "Não foi possível completar a compra. Quantidade indisponível " +
                "em estoque");
        throw stockIssue;
    }
}
