package org.semanticwb.portal;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import javax.security.auth.Subject;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.semanticwb.SWBPortal;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;

/**
 *
 * @author Jei
 * Modified by Serch
 */
public class SWBSessionObject implements HttpSessionBindingListener, Serializable
{

    private transient HashMap<String, Subject> mapa;

    public SWBSessionObject()
    {
        mapa = new HashMap<String, Subject>();
    }

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

    public void valueBound(HttpSessionBindingEvent arg0)
    {
        //System.out.println("SWBSessionObject.valueBound:"+arg0+" "+subject);
    }

    public void valueUnbound(HttpSessionBindingEvent arg0)
    {
        //System.out.println("SWBSessionObject.valueUnbound:"+arg0+" "+subject);
        SWBPortal.getUserMgr().unboundSessionObject(arg0.getSession().getId());
    }

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
            mapa.put(key, act);
        }
    }
}
