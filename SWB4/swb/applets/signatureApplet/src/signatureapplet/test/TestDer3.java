/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.test;

import java.io.*;
import java.security.PrivateKey;
import java.security.Security;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMReader;
import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.PasswordFinder;

/**
 *
 * @author serch
 */
public class TestDer3 {
    public static void main (String [] args)throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        final String pwd = args[1];

        File f = new File(args[0]);
        FileInputStream fis = new FileInputStream(f);
        DataInputStream dis = new DataInputStream(fis);
        byte[] keyBytes = new byte[(int) f.length()];
        dis.readFully(keyBytes);
        dis.close();
        
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        PEMWriter pWrt = new PEMWriter(new OutputStreamWriter(bOut));
        //PKCS8Generator pkcs8 = new PKCS8Generator(key);

        pWrt.writeObject(ASN1Primitive.fromByteArray(keyBytes));

        pWrt.close();
        
        
        PasswordFinder pf = new PasswordFinder()
        {
            public char[] getPassword()
            {
                return pwd.toCharArray();
            }
        };
        System.out.println("pf: "+new String(pf.getPassword()));
        PEMReader pReader = new PEMReader(new InputStreamReader(new ByteArrayInputStream(bOut.toByteArray())), pf);

        PrivateKey rdKey = (PrivateKey)pReader.readObject();
        System.out.println("key: "+rdKey);
        System.out.println("KeyAlg: "+rdKey.getAlgorithm());
        System.out.println("KeyFormat: "+rdKey.getFormat());
        
    }
    
}
