package org.semanticwb.portal.community;


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
        return url;
    }
}
