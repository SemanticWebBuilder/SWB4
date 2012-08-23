package org.semanticwb.security;

import java.io.Serializable;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 *
 * @author serch
 */
public class SignatureRecord implements Serializable {
    
    private final Certificate cert;
    private final byte[] signature;
    private final String message;
    private final Long timestamp;
    private final String subject;

    public SignatureRecord(Certificate cert, byte[] signature, String message) {
        this.cert = cert;
        if (cert instanceof X509Certificate){
            X509Certificate x509 = (X509Certificate) cert;
            subject = x509.getSubjectDN().getName();
        } else {
            subject = null;
        }
        this.signature = signature;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    public Certificate getCert() {
        return cert;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getSignature() {
        return signature;
    }

    public Long getTimestamp() {
        return timestamp;
    }
    
    public String getSubject() {
        return subject;
    }
    
}
