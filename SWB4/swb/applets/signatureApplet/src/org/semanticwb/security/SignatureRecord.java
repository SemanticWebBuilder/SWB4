/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
