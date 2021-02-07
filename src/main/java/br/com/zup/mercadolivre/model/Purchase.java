package br.com.zup.mercadolivre.model;

public class Purchase {

    private String productOwner;
    private User customer;

    public String getProductOwner() {
        return productOwner;
    }

    public User getCustomer() {
        return customer;
    }
}
