/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.*;
import java.security.*;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Formatter;
import javax.crypto.Cipher;
//import javax.crypto.EncryptedPrivateKeyInfo;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.engines.DESedeEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.security.pkcs.PKCS8Key;
import sun.security.util.DerValue;

/**
 *
 * @author serch
 */
public class TestDER {
    /*
     * private PrivateKey readPrivateKey(File inpfil) throws IOException,
     * GeneralSecurityException { String[] pbeb64s; // PBE ASN.1 data base-64
     * encoded
     *
     * byte[] pbedta; // PBE ASN.1 data in bytes EncryptedPrivateKeyInfo pbeinf;
     * // PBE key info PBEParameterSpec pbeprm; // PBE parameters Cipher pbecph;
     * // PBE decryption cipher
     *
     * byte[] pk8dta; // PKCS#8 ASN.1 data in bytes KeyFactory
     * pk8fac=KeyFactory.getInstance("RSA"); // PKCS#8 key factory for decoding
     * private key from ASN.1 data.
     *
     * pbeb64s=readDataBlocks(inpfil,"ENCRYPTED PRIVATE KEY");
     * if(pbeb64s.length!=1) { throw new GeneralSecurityException("The keystore
     * '"+inpfil+"' contains multiple private keys"); }
     * pbedta=base64.decode(pbeb64s[0]); log.diagln(" - Read private key data");
     *
     * pbeinf=new EncryptedPrivateKeyInfo(pbedta);
     * pbeprm=(PBEParameterSpec)pbeinf.getAlgParameters().getParameterSpec(PBEParameterSpec.class);
     * pbecph=Cipher.getInstance(pbeinf.getAlgName());
     * pbecph.init(Cipher.DECRYPT_MODE,pbeDecryptKey,pbeprm);
     *
     * pk8dta=pbecph.doFinal(pbeinf.getEncryptedData()); log.diagln(" - Private
     * Key: Algorithm= "+pbeinf.getAlgName()+", Iterations:
     * "+pbeprm.getIterationCount()+", Salt:
     * "+Base16.toString(pbeprm.getSalt())); pk8dta=patchKeyData(inpfil,pk8dta);
     * return pk8fac.generatePrivate(new PKCS8EncodedKeySpec(pk8dta));
    }
     */

    public static void main(String[] args) throws IOException, GeneralSecurityException {
        Security.addProvider(new BouncyCastleProvider());
        
        
        File f = new File(args[0]);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        /*
         *          */
        ByteArrayInputStream bis = new ByteArrayInputStream(keyBytes);
        ASN1InputStream aIn = new ASN1InputStream(bis); 
        
        
        
        EncryptedPrivateKeyInfo info = null;

        try {
            info = EncryptedPrivateKeyInfo.getInstance(aIn.readObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        PBES2Parameters alg = new PBES2Parameters((ASN1Sequence) info.getEncryptionAlgorithm().getParameters());
        
        PBKDF2Params func = PBKDF2Params.getInstance(alg.getKeyDerivationFunc().getParameters());

        EncryptionScheme scheme = alg.getEncryptionScheme();
        int keySize = 192;
        if (func.getKeyLength() != null) {
            keySize = func.getIterationCount().intValue();
        }
        System.out.println("keySize:" + keySize);
        int iterationCount = func.getIterationCount().intValue();
        byte[] salt = func.getSalt();

        PBEParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(PBEParametersGenerator.PKCS12PasswordToBytes(args[1].toCharArray()),
                salt, iterationCount);
        CipherParameters param;
        BufferedBlockCipher cipher =
                new PaddedBufferedBlockCipher(new CBCBlockCipher(new DESedeEngine()));
        if (scheme.getAlgorithm().equals(PKCSObjectIdentifiers.RC2_CBC)) {
            RC2CBCParameter rc2Params = RC2CBCParameter.getInstance(scheme.getObject());
            byte[] iv = rc2Params.getIV();
            System.out.println("keyP1:"+byteArrayToByteString(iv));
            param = new ParametersWithIV(generator.generateDerivedParameters(keySize), iv);
        } else {
            byte[] iv = ((ASN1OctetString) scheme.getObject()).getOctets();
            System.out.println("keyP2:"+byteArrayToByteString(iv));
            param = new ParametersWithIV(generator.generateDerivedParameters(keySize), iv);
        }

        
        
        cipher.init(false, param);

        
        
        byte[] data = info.getEncryptedData();
        byte[] out = new byte[cipher.getOutputSize(data.length)];
        int len = cipher.processBytes(data, 0, data.length, out, 0);

        try {
            len += cipher.doFinal(out, len);
        } catch (Exception e) {
            ;
        }


        System.out.println("info:"
                + info.getEncryptionAlgorithm().getParameters());

        System.out.println("final:" + byteArrayToHexString(out));
        
        
        
//        KeySpec key = new PKCS8EncodedKeySpec(keyBytes);
//        KeyFactory kf = KeyFactory.getInstance("RSA");
//        PrivateKey pk = kf.generatePrivate(key);
//        System.out.println("key:"+pk.getFormat());

        // Cipher cipher = Cipher.getInstance("PBES2","BC");



        /*
         * * /
         *
         * EncryptedPrivateKeyInfo encryptPKInfo = new
         * EncryptedPrivateKeyInfo(keyBytes);
         * System.out.println("pk:"+encryptPKInfo.getAlgName());
         * System.out.println("pk:"+encryptPKInfo.getAlgParameters());
         * System.out.println(""+encryptPKInfo.getEncoded());
         *
         * Cipher cipher = Cipher.getInstance(encryptPKInfo.getAlgName());
         * PBEKeySpec pbeKeySpec = new PBEKeySpec(args[1].toCharArray());
         * SecretKeyFactory secFac =
         * SecretKeyFactory.getInstance(encryptPKInfo.getAlgName()); Key pbeKey
         * = secFac.generateSecret(pbeKeySpec); AlgorithmParameters algParams =
         * encryptPKInfo.getAlgParameters(); cipher.init(Cipher.DECRYPT_MODE,
         * pbeKey, algParams); KeySpec pkcs8KeySpec =
         * encryptPKInfo.getKeySpec(cipher); KeyFactory kf =
         * KeyFactory.getInstance("RSA"); PrivateKey pk
         * =kf.generatePrivate(pkcs8KeySpec);
         * System.out.println("pk:"+pk.toString());
        /*
         */

    }
    
    public static String byteArrayToHexString(byte[] arr) {
            Formatter formatter = new Formatter();
            for (byte b : arr) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        } 
    
    public static String byteArrayToByteString(byte[] arr) {
            Formatter formatter = new Formatter();
            for (byte b : arr) {
                formatter.format("%02d ", b);
            }
            return formatter.toString();
        } 
}
