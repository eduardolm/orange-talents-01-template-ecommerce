package br.com.zup.mercadolivre.enums;

public enum PagSeguroReturnStatus {
    SUCESSO,
    ERRO;

    public TransactionStatus normalize() {
        if (this.equals(SUCESSO)) {
            return TransactionStatus.sucesso;
        }
        return TransactionStatus.erro;
    }
}
