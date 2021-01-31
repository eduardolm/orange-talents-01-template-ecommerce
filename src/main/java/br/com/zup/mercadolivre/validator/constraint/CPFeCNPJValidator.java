package br.com.zup.mercadolivre.validator.constraint;

import br.com.zup.mercadolivre.validator.CPFeCNPJ;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.InputMismatchException;

public class CPFeCNPJValidator implements ConstraintValidator<CPFeCNPJ, String> {

    @Override
    public void initialize(CPFeCNPJ constraintAnnotation) {}

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isEmpty() || isCpf(value) || isCnpj(value);
    }

    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        try{
            Long.parseLong(cpf);
        } catch(NumberFormatException e){
            return false;
        }

        int d1, d2;
        int digit1, digit2, remain;
        int CPFdigit;
        String nDigResult;

        d1 = d2 = 0;
        digit1 = digit2 = remain = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            CPFdigit = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            d1 = d1 + (11 - nCount) * CPFdigit;
            d2 = d2 + (12 - nCount) * CPFdigit;
        };

        remain = (d1 % 11);

        if (remain < 2)
            digit1 = 0;
        else
            digit1 = 11 - remain;

        d2 += 2 * digit1;

        remain = (d2 % 11);

        if (remain < 2)
            digit2 = 0;
        else
            digit2 = 11 - remain;

        String verifyingDigit = cpf.substring(cpf.length() - 2, cpf.length());

        nDigResult = String.valueOf(digit1) + String.valueOf(digit2);

        return verifyingDigit.equals(nDigResult);
    }

    private boolean isCnpj(String cnpj) {
        cnpj = cnpj.replace(".", "");
        cnpj = cnpj.replace("-", "");
        cnpj = cnpj.replace("/", "");

        try{
            Long.parseLong(cnpj);
        } catch(NumberFormatException e){
            return false;
        }

        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111")
                || cnpj.equals("22222222222222") || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444") || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666") || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888") || cnpj.equals("99999999999999") || (cnpj.length() != 14))
            return (false);
        char dig13, dig14;
        int sm, i, r, num, weight;

        try {
            sm = 0;
            weight = 2;
            for (i = 11; i >= 0; i--) {

                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else
                dig13 = (char) ((11 - r) + 48);

            sm = 0;
            weight = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (cnpj.charAt(i) - 48);
                sm = sm + (num * weight);
                weight = weight + 1;
                if (weight == 10)
                    weight = 2;
            }
            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else
                dig14 = (char) ((11 - r) + 48);

            if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13)))
                return (true);
            else
                return (false);
        } catch (InputMismatchException error) {
            return (false);
        }
    }
}
