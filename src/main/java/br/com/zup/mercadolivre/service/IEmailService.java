package br.com.zup.mercadolivre.service;

public interface IEmailService {

    /**
     *
     * @param to -> e-mail recipient
     * @param from -> from e-mail
     * @param subject -> e-mail subject
     * @param text -> e-mail text body
     */
    void send(String to, String from, String subject, String text);
}
