package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.PurchaseRequestDto;
import br.com.zup.mercadolivre.handler.ObjectHandler;
import br.com.zup.mercadolivre.model.Product;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.model.User;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import org.springframework.validation.BindException;

@RestController
@RequestMapping("/api/v1/purchases")
public class PurchaseController extends ObjectHandler {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid PurchaseRequestDto request) throws BindException {
        Product product = checkProductExists(request.getProductId());
        int quantity = request.getQuantity();
        boolean isStockEnough = product.subtractStock(request.getQuantity());

        if (isStockEnough) {
            User customer = checkUserExists();
            Purchase purchase = new Purchase(product, quantity, customer);
            purchaseRepository.save(purchase);
            return ResponseEntity.ok().body(purchase.toString());
        }

        BindException stockIssue = new BindException(request, "purchaseRequestDto");
        stockIssue.reject(null, "Não foi possível completar a compra. Quantidade indisponível " +
                "em estoque");
        throw stockIssue;
    }
}
