package org.semanticwb.portal.community;


import org.semanticwb.model.WebPage;


public class VideoElement extends org.semanticwb.portal.community.base.VideoElementBase 
{
    public VideoElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getWebPage().getUrl();
        url+="?act=detail&amp;uri="+this.getEncodedURI();
        return url;
    }
    @Override
    public WebPage getWebPage()
    {
        return super.getWebPage();
    }
}
