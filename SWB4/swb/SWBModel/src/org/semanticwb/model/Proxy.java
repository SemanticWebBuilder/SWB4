package org.semanticwb.model;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class Proxy extends org.semanticwb.model.base.ProxyBase 
{
    /** The names. */
    private static ConcurrentHashMap<String, Proxy> names = null;

    static
    {
        sclass.registerObserver(new SemanticObserver() {

            public void notify(SemanticObject obj, Object prop, String lang, String action)
            {
                names=null;
            }
        });
    }

    /**
     * Instantiates a new dns.
     * 
     * @param base the base
     */
    public Proxy(SemanticObject base) {
        super(base);
    }


    /**
     * Refresh.
     */
    synchronized public static void refresh() {
        if(names==null)
        {
            names = new ConcurrentHashMap();
            Iterator<Proxy> it = ClassMgr.listProxies();

            while (it.hasNext())
            {
                Proxy dns = it.next();
                if(dns.getDns()!=null)
                {
                    names.put(dns.getDns(), dns);
                }
            }
        }
    }

    /**
     * Gets the dns.
     * 
     * @param serverName the server name
     * @return the dns
     */
    public static Proxy getProxy(String serverName) {
        if (names == null) {
            refresh();
        }
        return names.get(serverName);
    }
}
