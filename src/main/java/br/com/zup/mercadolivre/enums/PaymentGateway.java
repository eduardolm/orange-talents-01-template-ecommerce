package br.com.zup.mercadolivre.enums;

import br.com.zup.mercadolivre.model.Purchase;
import org.springframework.web.util.UriComponentsBuilder;

public enum PaymentGateway {
    PAG_SEGURO("pagseguro") {
        @Override
        public String createRedirectUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            String pagSeguroRedirectUrl = uriComponentsBuilder
                    .path("/retorno-pagseguro/{id}")
                    .buildAndExpand(purchase.getId()).toString();

            return "pagseguro.com/" + purchase.getId() + "?redirectUrl=" + pagSeguroRedirectUrl;
        }
    },
    PAYPAL("paypal"){
        @Override
        public String createRedirectUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder) {
            String paypalRedirectUrl = uriComponentsBuilder
                    .path("/retorno-paypal/{id}")
                    .buildAndExpand(purchase.getId()).toString();

            return "paypal.com/" + purchase.getId() + "?redirectUrl=" + paypalRedirectUrl;
        }
    };

    public abstract String createRedirectUrl(Purchase purchase, UriComponentsBuilder uriComponentsBuilder);

    public final String label;

    PaymentGateway(String label) {
        this.label = label;
    }
}
