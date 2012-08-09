/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.EncryptedPrivateKeyInfo;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PasswordFinder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.test.SimpleTestResult;

/**
 *
 * @author serch
 */
public class KeysGetterTest {

    public static void main(final String[] args) throws GeneralSecurityException, IOException {
        
        
        Security.addProvider(new BouncyCastleProvider());
        
        File f = new File(args[0]);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        /*
         *
         */
        ByteArrayInputStream bis = new ByteArrayInputStream(keyBytes);
        ASN1InputStream aIn = new ASN1InputStream(bis);
        PemObject pob =  new org.bouncycastle.openssl.MiscPEMGenerator(aIn.readObject()).generate();
        
        PEMReader pem = new PEMReader(new InputStreamReader(aIn),new PasswordFinder() {

            @Override
            public char[] getPassword() {
                return args[1].toCharArray();
            }
        });
        System.out.println("pem:getType:"+pem.readObject());
        
        EncryptedPrivateKeyInfo pInfo = new EncryptedPrivateKeyInfo(keyBytes);
        
        System.out.println("pinfo.getAlgParameters():"+pInfo.getAlgParameters());
        
        SecretKeyFactory    keyFact = SecretKeyFactory.getInstance(pInfo.getAlgName(), "BC");
        
        

        PBEKeySpec pbeSpec = new PBEKeySpec(args[1].toCharArray());

        Cipher cipher = Cipher.getInstance(pInfo.getAlgName(), "BC");

        cipher.init(Cipher.DECRYPT_MODE, keyFact.generateSecret(pbeSpec), pInfo.getAlgParameters());

        PKCS8EncodedKeySpec keySpec = pInfo.getKeySpec(cipher);

        System.out.println("keySpec:"+keySpec.getFormat());


    }
}
