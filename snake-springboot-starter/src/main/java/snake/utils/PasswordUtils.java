package snake.utils;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author: sabri
 * @date: 2019/8/2 13:46
 * @description:
 */
public class PasswordUtils {

    private static final BasicPasswordEncryptor basicEncryptor = new BasicPasswordEncryptor();

    private static final BCryptPasswordEncoder bcEncryptor = new BCryptPasswordEncoder();

    public static String enctypt(String password) {
        return basicEncryptor.encryptPassword(password);
    }

    public static boolean check(String plainPassword, String encryptPassword) {
        return basicEncryptor.checkPassword(plainPassword, encryptPassword);
    }

    public static String encode(String password) {
        return bcEncryptor.encode(password);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return bcEncryptor.matches(rawPassword, encodedPassword);
    }

}
