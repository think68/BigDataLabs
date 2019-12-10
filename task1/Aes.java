import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
 
 
/*
 * AES encode and decode
 */
public class Aes {
  /*
   * Encode
   * 1.key generator
   * 2.init key generator use encodeRules
   * 3.generate key
   * 4.create and generate a cipher
   * 5.encrypt data
   * 6.return encrypted data
   */
    public static String AESEncode(String encodeRules,String content){
        try {
            /*1*/
			KeyGenerator keygen=KeyGenerator.getInstance("AES");
			
			/*2*/
			SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(encodeRules.getBytes("utf-8"));
            keygen.init(128,random);
            
			/*3*/
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            
			/*4*/
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
			byte [] byte_encode=content.getBytes("utf-8");
            
			/*5*/
            byte [] byte_AES=cipher.doFinal(byte_encode);
            String AES_encode=new String(Base64.getEncoder().encodeToString(byte_AES));
			
			/*6*/
            return AES_encode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return null;         
    }
    /*
     * Decode
     * 1.key generator
	 * 2.init key generator use encodeRules
	 * 3.generate key
	 * 4.create and generate a cipher
     * 5.decrypt data
     * 6.return decrypted data 
     */
    public static String AESDecode(String encodeRules,String content){
        try {
            /*1*/
            KeyGenerator keygen=KeyGenerator.getInstance("AES");

			/*2*/
			SecureRandom random=SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(encodeRules.getBytes("utf-8"));
            keygen.init(128, random);
            
			/*3*/
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            
			/*4*/
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            
			/*base64 decode to byte array*/
			byte [] byte_content= Base64.getDecoder().decode(content);
			
			/*5*/
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"utf-8");
            
			/*6*/
			return AES_decode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        
        return null;         
    }
    
}
