package org.semanticwb.model;

import java.util.HashMap;
import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Dns extends DnsBase 
{
    private static HashMap<String,Dns> names=null;

    public Dns(SemanticObject base)
    {
        super(base);
    }

    @Override
    public void setDefault(boolean dnsDefault) 
    {
        super.setDefault(dnsDefault);
        Iterator<Dns> it=getWebSite().listDnss();
        while(it.hasNext())
        {
            Dns d=it.next();
            if(!d.equals(this) && d.isDefault())
            {
                d.setDefault(false);
            }
        }        
    }

    synchronized public static void refresh()
    {
        names=new HashMap();
        Iterator<Dns> it=listDnss();
        while(it.hasNext())
        {
            Dns dns=it.next();
            names.put(dns.getDns(), dns);
        }
    }

    public static Dns getDns(String serverName)
    {
        if(names==null)
        {
            refresh();
        }
        return names.get(serverName);
    }


    
}
