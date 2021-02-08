package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.PagSeguroRequestDto;
import br.com.zup.mercadolivre.controller.request.PaymentGatewayResponseDto;
import br.com.zup.mercadolivre.controller.request.PaypalRequestDto;
import br.com.zup.mercadolivre.model.Purchase;
import br.com.zup.mercadolivre.service.PurchaseEvents;
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

    @Autowired
    private PurchaseEvents purchaseEvents;

    @PostMapping("/api/v1/retorno-pagseguro/{id}")
    public ResponseEntity<?> processPagSeguro(@PathVariable("id") Long id,
                                              @Valid @RequestBody PagSeguroRequestDto request) throws BindException {
        return process(id, request);
    }

    @PostMapping("/api/v1/retorno-paypal/{id}")
    public ResponseEntity<?> processPaypal(@PathVariable("id") Long id,
                                           @Valid @RequestBody PaypalRequestDto request) throws BindException {

        return process(id, request);
    }

    private ResponseEntity<?> process(Long purchaseId, PaymentGatewayResponseDto paymentGatewayResponseDto) throws BindException {
        Optional<Purchase> purchase = purchaseRepository.findById(purchaseId);

        if (purchase.isPresent()) {
            purchase.get().addTransaction(paymentGatewayResponseDto);
            purchaseRepository.save(purchase.get());
            purchaseEvents.process(purchase.get());

            return ResponseEntity.ok().body(purchase.toString());
        }

        BindException purchaseIssue = new BindException(paymentGatewayResponseDto, "paymentGatewayResponseDto");
        purchaseIssue.reject(null, "Não foi possível completar a compra. Compra não encontrada");
        throw purchaseIssue;
    }
}
