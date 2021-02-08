package br.com.zup.mercadolivre.model;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Ranking implements PurchaseSuccessEvent{

    @Override
    public void process(Purchase purchase) {
        Assert.isTrue(purchase.successfullyProcessed(), "Erro. A compra não foi concluída com sucesso " + purchase);

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> request = Map
                .of("purchaseId", purchase.getId(), "sellerId", purchase.getProduct().getProductOwner().getId());

        restTemplate.postForEntity("http://localhost:8080/api/v1/ranking", request, String.class );
    }
}
