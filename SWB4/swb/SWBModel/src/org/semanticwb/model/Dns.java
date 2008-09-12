package org.semanticwb.model;

import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class Dns extends DnsBase 
{
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
    
}
