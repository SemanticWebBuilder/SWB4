package org.semanticwb.portal.community;


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
        url+="?act=detail&amp;uri="+this.getEncodedURI();
        return url;
    }
    @Override
    public WebPage getWebPage()
    {
        return super.getPhotoWebPage();
    }
}
