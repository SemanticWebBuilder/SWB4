/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import org.bouncycastle.util.encoders.Base64;
import signatureapplet.util.GetFIELPK;

/**
 *
 * @author serch
 */
public class SignTest {
    
    static private String data = "||0001|Sergio Martinez|Casual|11222123213213123123123||";
    
    public static void main(String[] args) throws Exception{
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initSign(GetFIELPK.getKey(new File(args[1]), args[2].toCharArray()));
        sig.update(data.getBytes());
        byte[] firma = sig.sign();
        
        byte[] firmaecd = Base64.encode(firma);
        
        System.out.println("Signature: "+new String (firmaecd));
        
        FileInputStream fis = new FileInputStream(args[0]);
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert=null;
        if (bis.available() > 0) {
            cert = cf.generateCertificate(bis);
            X509Certificate x509 = (X509Certificate)cert;
            System.out.println("SerialNumber:"+x509.getSerialNumber());
            System.out.println("DN:"+x509.getSubjectDN().getName());
            System.out.println("Subject UniqueID:"+x509.getSubjectUniqueID());
            System.out.println("X500P:"+x509.getSubjectX500Principal().getName());
        }
        bis.close();
        fis.close();
        sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(cert);
        sig.update(data.getBytes());
        System.out.println("La firma regresa: "+sig.verify(firma));
        System.out.println("\n\n");
        
    }
}
