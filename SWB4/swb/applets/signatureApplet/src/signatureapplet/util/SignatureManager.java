package signatureapplet.util;

import java.io.*;
import java.security.*;
import org.bouncycastle.util.encoders.Base64;
import signatureapplet.data.SignatureRecord;

/**
 *
 * @author serch
 */
public class SignatureManager {

    public static SignatureRecord wrapData(String message,
            PrivateKey privateKey, java.security.cert.Certificate cert)
            throws NoSuchAlgorithmException, SignatureException,
            InvalidKeyException {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initSign(privateKey);
        sig.update(message.getBytes());
        byte[] firma = sig.sign();
        sig.initVerify(cert);
        sig.update(message.getBytes());
        if (!sig.verify(firma)) throw new SignatureException("El certificado no corresponde con la llave privada");
        return new SignatureRecord(cert, firma, message);
    }
    
    public static String encodeSignatureRecord(SignatureRecord sr) throws 
            IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024*16);
        ObjectOutputStream oout = new ObjectOutputStream(out);
        oout.writeObject(sr);
        oout.flush();
        byte[] encoded = Base64.encode(out.toByteArray());
        oout.close();
        out.close();
        return new String(encoded);
    }
    
    public static SignatureRecord decodeSignatureRecord(String data) throws 
            IOException, ClassNotFoundException {
        byte[]stream = Base64.decode(data);
        ByteArrayInputStream in = new ByteArrayInputStream(stream);
        ObjectInputStream oin = new ObjectInputStream(in);
        Object o = oin.readObject();
        if (o instanceof SignatureRecord) {
            return (SignatureRecord) o;
        } else {
            throw new ClassNotFoundException("los datos proporcionados no corresponden a un SignatureRecord");
        }
        
    }
    
    public static boolean ValidateSignatureRecord(SignatureRecord sr) throws 
            NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(sr.getCert());
        sig.update(sr.getMessage().getBytes());
        return sig.verify(sr.getSignature());
    }
}
