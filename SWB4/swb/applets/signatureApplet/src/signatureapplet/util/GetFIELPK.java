/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Based on BouncyCastle decipher for wrapped PKCS#8 in PKCS#5 for keys created by Seguridata for Mexico SAT (FIEL)
 * @author serch
 */
public class GetFIELPK {
    
    public static PrivateKey getKey(final File file, final char[] pwd) throws IOException, GeneralSecurityException {
        //Register BouncyCastleProvider
        Security.addProvider(new BouncyCastleProvider());
        //Reading FIEL key
        FileInputStream fis = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) file.length()];
        dis.readFully(keyBytes);
        dis.close();
        //Create Cipher Parameters
        EncryptedPrivateKeyInfo info = EncryptedPrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(keyBytes));
        AlgorithmIdentifier algId = info.getEncryptionAlgorithm();
        //System.out.println("algId:"+algId.getAlgorithm().toASN1Primitive().toString());
        
        PBES2Parameters params = PBES2Parameters.getInstance(algId.getParameters());
        KeyDerivationFunc func = params.getKeyDerivationFunc();
        EncryptionScheme scheme = params.getEncryptionScheme();
        PBKDF2Params defParams = (PBKDF2Params) func.getParameters();


        int iterationCount = defParams.getIterationCount().intValue();
        byte[] salt = defParams.getSalt();

        String algorithm = scheme.getAlgorithm().getId();

        PBEParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(
            PBEParametersGenerator.PKCS5PasswordToBytes(pwd),
            salt,
            iterationCount);
        SecretKey key = new SecretKeySpec(((KeyParameter)generator.generateDerivedParameters(192)).getKey(), algorithm);

        Cipher cipher = Cipher.getInstance(algorithm, "BC");
        AlgorithmParameters algParams = AlgorithmParameters.getInstance(algorithm, "BC");

        algParams.init(scheme.getParameters().toASN1Primitive().getEncoded());

        cipher.init(Cipher.DECRYPT_MODE, key, algParams);

        PrivateKeyInfo pInfo = PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(cipher.doFinal(info.getEncryptedData())));
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pInfo.getEncoded());

        KeyFactory keyFact = KeyFactory.getInstance(pInfo.getPrivateKeyAlgorithm().getAlgorithm().getId(), "BC");

        return keyFact.generatePrivate(keySpec);
    }
    
    
    
}
