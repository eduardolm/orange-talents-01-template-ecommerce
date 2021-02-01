package br.com.zup.mercadolivre.config.security;

import br.com.zup.mercadolivre.config.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Base64;

@Converter
@Configuration
@Configurable
@PropertySource("classpath:application.properties")
@Profile(value = {"prod", "dev", "test"})
public class CryptoProperties implements AttributeConverter<String, String>, CryptoPropertiesInterface {

    private String secret = BeanUtil.getBean(Environment.class).getProperty("crypto.secret.key");

    private final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private final byte[] KEY = secret.getBytes();

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String convertToDatabaseColumn(String inputValue) {
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return encoder.encode(inputValue);
//        Key key = new SecretKeySpec(KEY, "AES");
//        try {
//            Cipher c = Cipher.getInstance(ALGORITHM);
//            c.init(Cipher.ENCRYPT_MODE, key);
//            return Base64.getEncoder().encodeToString(c.doFinal(inputValue.getBytes()));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            return new String(c.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getSecret() {
        return secret;
    }
}
