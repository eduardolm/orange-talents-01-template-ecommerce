package br.com.zup.mercadolivre.enums;

public enum EmailFrom {
    PURCHASE("vendas@mercadolivre.com.br"),
    QUESTION("novapergunta@mercadolivre.com.br");

    public final String label;

    EmailFrom(String label) {
        this.label = label;
    }
}
