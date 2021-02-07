package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.PagSeguroRequestDto;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class PaymentGatewayController {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @PostMapping("/api/v1/retorno-pagseguro/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Long id, @Valid @RequestBody PagSeguroRequestDto request) throws BindException {

        Optional<Purchase> purchase = purchaseRepository.findById(id);

        if (purchase.isPresent()) {
            purchase.get().addTransaction(request);
            purchaseRepository.save(purchase.get());

            return ResponseEntity.ok().body(purchase.toString());
        }

        BindException purchaseIssue = new BindException(request, "pagSeguroRequestDto");
        purchaseIssue.reject(null, "Não foi possível completar a compra. Compra não encontrada");
        throw purchaseIssue;
    }
}
