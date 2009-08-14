package org.semanticwb.portal.community;

import org.semanticwb.model.WebPage;


public class MicroSite extends org.semanticwb.portal.community.base.MicroSiteBase 
{
    public MicroSite(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static MicroSite getMicroSite(WebPage page)
    {
        if(page instanceof MicroSiteWebPageUtil)
        {
            return ((MicroSiteWebPageUtil)page).getMicroSite();
        }
        return null;
    }

}
