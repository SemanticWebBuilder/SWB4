package org.semanticwb.model;

import java.util.Iterator;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class WebSite extends WebSiteBase 
{
    public WebSite(SemanticObject base)
    {
        super(base);
    }
    
    @Override
    public Dns createDns(String uri)
    {
        return (Dns)getSemanticObject().getModel().createGenericObject(uri, SWBContext.getVocabulary().swb_Dns);
    }    

    @Override
    public Dns getDns(String id)
    {
        return (Dns)getSemanticObject().getModel().getGenericObject(id,SWBContext.getVocabulary().swb_Dns);
    }
    
    public Dns getDefaultDns()
    {
        Dns dns=null;
        Iterator<Dns> it=listDnss();
        while(it.hasNext())
        {
            Dns d=it.next();
            if(d.isDefault())
            {
                dns=d;
                break;
            }
        }
        return dns;
    }
    
//    @Override
//    public PortletType createPortletType(String id) {
//        return super.createPortletType(id.toLowerCase());
//    }
//
//    @Override
//    public PortletType getPortletType(String id) {
//        return super.getPortletType(id.toLowerCase());
//    }
    
}
