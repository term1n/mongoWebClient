package ru.spb.iac.security.enc;

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;

/**
 * Created by manaev on 26.12.14.
 */
@Service("encrypter")
public class BCryptHash {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(11);
    public String getEncPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
