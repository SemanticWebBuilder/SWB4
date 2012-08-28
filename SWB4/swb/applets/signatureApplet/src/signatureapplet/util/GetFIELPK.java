/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package signatureapplet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.Security;
import mx.gob.sat.sgi.SgiCripto.SgiCriptoException;
import mx.gob.sat.sgi.SgiCripto.SgiLlavePrivada;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author serch
 */
public class GetFIELPK {

    public static PrivateKey getKey(final File file, final char[] pwd) throws IOException, GeneralSecurityException, SgiCriptoException {
        Security.addProvider(new BouncyCastleProvider());
        SgiLlavePrivada llavePrivada = new SgiLlavePrivada();
        llavePrivada.inicia(new FileInputStream(file), String.valueOf(pwd).getBytes());
        return llavePrivada.getLlave();
    }
    
}
