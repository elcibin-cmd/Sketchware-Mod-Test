import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class PasswordObfuscator {
    private static volatile PasswordObfuscator instance;
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 256;
    private static final int GCM_TAG_LENGTH = 128;

    // Private constructor to enforce singleton pattern
    private PasswordObfuscator() {
    }

    // Thread-safe singleton instance getter
    public static PasswordObfuscator getInstance() {
        if (instance == null) {
            synchronized (PasswordObfuscator.class) {
                if (instance == null) {
                    instance = new PasswordObfuscator();
                }
            }
        }
        return instance;
    }

    public String encrypt(String password, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encryptedPassword, SecretKey key, byte[] iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] original = cipher.doFinal(decodedBytes);
        return new String(original, StandardCharsets.UTF_8);
    }

    public SecretKey loadKey(String keyFromEnv) throws Exception {
        // Load key from environment variable
        byte[] decodedKey = Base64.getDecoder().decode(keyFromEnv);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }
}

