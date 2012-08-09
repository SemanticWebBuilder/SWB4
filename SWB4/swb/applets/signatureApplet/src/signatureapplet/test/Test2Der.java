/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.openssl.PEMUtilities2;

/**
 *
 * @author serch
 */
public class Test2Der {
    
    private static final Map KEYSIZES = new HashMap();
//    private static final Set PKCS5_SCHEME_1 = new HashSet();
//    private static final Set PKCS5_SCHEME_2 = new HashSet();

    static
    {
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndDES_CBC);
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD2AndRC2_CBC);
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndDES_CBC);
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithMD5AndRC2_CBC);
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndDES_CBC);
//        PKCS5_SCHEME_1.add(PKCSObjectIdentifiers.pbeWithSHA1AndRC2_CBC);
//
//        PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.id_PBES2);
//        PKCS5_SCHEME_2.add(PKCSObjectIdentifiers.des_EDE3_CBC);
//        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes128_CBC);
//        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes192_CBC);
//        PKCS5_SCHEME_2.add(NISTObjectIdentifiers.id_aes256_CBC);

        KEYSIZES.put(PKCSObjectIdentifiers.des_EDE3_CBC.getId(), new Integer(192));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes128_CBC.getId(), new Integer(128));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes192_CBC.getId(), new Integer(192));
        KEYSIZES.put(NISTObjectIdentifiers.id_aes256_CBC.getId(), new Integer(256));
    }


    public static void main(String[] args) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        File f = new File(args[0]);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();

        EncryptedPrivateKeyInfo info = EncryptedPrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(keyBytes));
        AlgorithmIdentifier algId = info.getEncryptionAlgorithm();
        System.out.println("algId:"+algId.getAlgorithm().toASN1Primitive().toString());
        
        PBES2Parameters params = PBES2Parameters.getInstance(algId.getParameters());
        KeyDerivationFunc func = params.getKeyDerivationFunc();
        EncryptionScheme scheme = params.getEncryptionScheme();
        PBKDF2Params defParams = (PBKDF2Params) func.getParameters();


        int iterationCount = defParams.getIterationCount().intValue();
        byte[] salt = defParams.getSalt();

        String algorithm = scheme.getAlgorithm().getId();

        SecretKey key = generateSecretKeyForPKCS5Scheme2(algorithm, args[1].toCharArray(), salt, iterationCount);

        Cipher cipher = Cipher.getInstance(algorithm, "BC");
        AlgorithmParameters algParams = AlgorithmParameters.getInstance(algorithm, "BC");

        algParams.init(scheme.getParameters().toASN1Primitive().getEncoded());

        cipher.init(Cipher.DECRYPT_MODE, key, algParams);

        PrivateKeyInfo pInfo = PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(cipher.doFinal(info.getEncryptedData())));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pInfo.getEncoded());

        KeyFactory keyFact = KeyFactory.getInstance(pInfo.getPrivateKeyAlgorithm().getAlgorithm().getId(), "BC");

        java.security.PrivateKey pKey = keyFact.generatePrivate(keySpec);

        System.out.println("pKey: "+pKey);
        System.out.println("pKey alg: "+
        pKey.getAlgorithm());
        System.out.println("pKey Format: "+pKey.getFormat());


//        System.out.println("key:"+ byteArrayToHexString(
//        PEMUtilities2.crypt(false, "BC", keyBytes, args[1].toCharArray(), "DES-EDE3-CBC", keyP))); //des-ede3-cbc



    }

    static SecretKey generateSecretKeyForPKCS5Scheme2(String algorithm, char[] password, byte[] salt, int iterationCount)
    {
        PBEParametersGenerator generator = new PKCS5S2ParametersGenerator();

        generator.init(
            PBEParametersGenerator.PKCS5PasswordToBytes(password),
            salt,
            iterationCount);

        return new SecretKeySpec(((KeyParameter)generator.generateDerivedParameters(getKeySize(algorithm))).getKey(), algorithm);
    }
    
    
    
    static int getKeySize(String algorithm)
    {
        if (!KEYSIZES.containsKey(algorithm))
        {
            throw new IllegalStateException("no key size for algorithm: " + algorithm);
        }
        
        return ((Integer)KEYSIZES.get(algorithm)).intValue();
    }
    
    public static String byteArrayToHexString(byte[] arr) {
        Formatter formatter = new Formatter();
        for (byte b : arr) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
