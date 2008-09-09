package org.semanticwb.model;

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
        return (Dns)getSemanticObject().getModel().createGenericObject(uri, SWBContext.getVocabulary().Dns);
    }    

    public Dns getDns(String id)
    {
        return (Dns)getSemanticObject().getModel().getGenericObject(id,SWBContext.getVocabulary().Dns);
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
