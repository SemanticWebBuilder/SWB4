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
}
