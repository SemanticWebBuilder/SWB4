/**  
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
**/ 
 
package org.semanticwb.portal;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBSessionObject.
 * 
 * @author Jei
 * Modified by Serch
 */
public class SWBSessionObject implements HttpSessionBindingListener, Serializable
{
    
    /** The log. */
    static private Logger log = SWBUtils.getLogger(SWBSessionObject.class);

    /** The mapa. */
    private transient HashMap<String, Subject> mapa;

    /**
     * Instantiates a new sWB session object.
     */
    public SWBSessionObject()
    {
        mapa = new HashMap<String, Subject>();
    }

    /**
     * Gets the subject.
     * 
     * @param website the website
     * @return the subject
     */
    public Subject getSubject(String website)
    {
        String ur = SWBContext.getWebSite(website).getUserRepository().getId();
        Subject sub = mapa.get(ur);
        if (null == sub)
        {
            sub = new Subject();
            mapa.put(ur, sub);
        }
        return sub;
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionBindingListener#valueBound(javax.servlet.http.HttpSessionBindingEvent)
     */
    /**
     * Value bound.
     * 
     * @param arg0 the arg0
     */
    public void valueBound(HttpSessionBindingEvent arg0)
    {
        //System.out.println("SWBSessionObject.valueBound:"+arg0+" "+subject);
    }

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpSessionBindingListener#valueUnbound(javax.servlet.http.HttpSessionBindingEvent)
     */
    /**
     * Value unbound.
     * 
     * @param arg0 the arg0
     */
    public void valueUnbound(HttpSessionBindingEvent arg0)
    {
        //System.out.println("SWBSessionObject.valueUnbound:"+arg0+" "+subject);
        SWBPortal.getUserMgr().unboundSessionObject(arg0.getSession().getId());
    }

    /**
     * Write object.
     * 
     * @param oos the oos
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private synchronized void writeObject(java.io.ObjectOutputStream oos) throws java.io.IOException
    {
        oos.writeLong(mapa.size());
        Iterator<String> it  =mapa.keySet().iterator();
        while (it.hasNext()){
            String key = it.next();
            Subject curr = mapa.get(key);
            User pcurr = (User)curr.getPrincipals().iterator().next();
            Object ocurr = curr.getPrivateCredentials().iterator().next();
            oos.writeObject(key);
            oos.writeObject(pcurr.getId());
            oos.writeObject(ocurr);
        }
    }

    /**
     * Read object.
     * 
     * @param s the s
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     */
    private void readObject(java.io.ObjectInputStream s) throws
            java.io.IOException,
            ClassNotFoundException
    {
        mapa = new HashMap<String, Subject>();
        long cant = s.readLong();
        for (long i =0;i<cant;i++){
            String key = (String)s.readObject();
            String id = (String)s.readObject();
            Object cred = s.readObject();
            Subject act = new Subject();
            Principal p = SWBContext.getUserRepository(key).getUser(id);
            act.getPrincipals().add(p);
            act.getPrivateCredentials().add(cred);
            try
            {
                ((User) p).checkCredential(cred);
            } catch (NoSuchAlgorithmException ex)
            {
                log.error("Can't check credential, this shoudn't pass", ex);
            }
            mapa.put(key, act);
        }
    }
}
