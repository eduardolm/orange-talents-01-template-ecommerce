package br.com.zup.mercadolivre.service;

import br.com.zup.mercadolivre.model.Purchase;

/**
 * Todo evento relacionado ao sucesso de uma nova compra deve implementar essa interface
 */
public interface PurchaseSuccessEvent {

    void process(Purchase purchase);
}
