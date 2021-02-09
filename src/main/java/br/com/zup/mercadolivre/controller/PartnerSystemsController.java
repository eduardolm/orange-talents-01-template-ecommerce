package br.com.zup.mercadolivre.controller;

import br.com.zup.mercadolivre.controller.request.NFRequestDto;
import br.com.zup.mercadolivre.controller.request.RankingRequestDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class PartnerSystemsController {

    @PostMapping("/api/v1/notas-fiscais")
    public void createInvoice(@RequestBody @Valid NFRequestDto request) throws InterruptedException {
        System.out.println("Criando nota fiscal para a venda:  " + request.getPurchaseId() + " do comprador " + request.getCustomerId());
        Thread.sleep(150);;
    }

    @PostMapping("/api/v1/ranking")
    public void ranking(@RequestBody @Valid RankingRequestDto request) throws InterruptedException {
        System.out.println("Criando ranking " + request);
        Thread.sleep(150);;
    }
}
