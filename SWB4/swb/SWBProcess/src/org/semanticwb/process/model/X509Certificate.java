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
package org.semanticwb.process.model;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class X509Certificate extends org.semanticwb.process.model.base.X509CertificateBase 
{
    private static Logger log=SWBUtils.getLogger(X509Certificate.class);
            
    static{
        swp_X509File.registerObserver(new SemanticObserver() {
            public void notify(SemanticObject obj, Object prop, String lang, String action) 
            {
                try
                {
                    ((X509Certificate)obj.createGenericInstance()).setPropertiesFromCertFile();
                }catch (Exception e)
                {
                    log.error(e);
                }
            }
        });
    }
    
    
    public X509Certificate(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    private void setPropertiesFromCertFile() throws IOException, GeneralSecurityException{
        FileInputStream fis = new FileInputStream(SWBPortal.getWorkPath()+getWorkPath()+"/"+getFile());
        BufferedInputStream bis = new BufferedInputStream(fis);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        Certificate cert=null;
        //System.out.println("ruta:"+SWBPortal.getWorkPath()+getWorkPath()+"/"+getFile());
        //System.out.println("Antes de Available: "+bis.available());
        if (bis.available() > 0) {
            cert = cf.generateCertificate(bis);
            java.security.cert.X509Certificate x509 = (java.security.cert.X509Certificate)cert;
            setName(getCNfromDN(x509.getSubjectDN().getName()));
            setSerial(getOID_2_5_4_45fromDN(x509.getSubjectDN().getName()));
            setSubject(x509.getSubjectDN().getName());
            //System.out.println("getName:"+getName());
            //System.out.println("getSerial:"+getSerial());
        }
        bis.close();
        fis.close();
    }
    
    private String getCNfromDN(String DN){
        String prop = SWBPortal.getEnv("swbp/X509SubjectProperty4Name","CN")+"=";
        int pos1 = DN.indexOf(prop)+prop.length();
        int pos2 = DN.indexOf(",", pos1);
        return pos2>-1?DN.substring(pos1,pos2):DN.substring(pos1);
    }
        
    private String getOID_2_5_4_45fromDN(String DN){
        String prop = SWBPortal.getEnv("swbp/X509SubjectProperty4Serial","OID.2.5.4.45")+"=";
        int pos1 = DN.indexOf(prop)+prop.length();
        int pos2 = DN.indexOf(",", pos1);
        return pos2>-1?DN.substring(pos1,pos2):DN.substring(pos1);
    }
}

