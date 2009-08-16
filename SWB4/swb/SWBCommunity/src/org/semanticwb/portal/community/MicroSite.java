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
            MicroSite site=((MicroSiteWebPageUtil)page).getMicroSite();
            if(site==null)
            {
                WebPage parent=page.getParent();
                if(parent!=null)return getMicroSite(page);
            }
            return site;
        }else if(page instanceof MicroSite)
        {
            return ((MicroSite)page);
        }
        return null;
    }

}
