package org.semanticwb.portal.community;

import java.net.URLEncoder;
import org.semanticwb.model.WebPage;


public class PhotoElement extends org.semanticwb.portal.community.base.PhotoElementBase 
{
    public PhotoElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getPhotoWebPage().getUrl();
        url+="?act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }
    @Override
    public WebPage getWebPage()
    {
        return super.getPhotoWebPage();
    }
}
