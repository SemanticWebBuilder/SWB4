package org.semanticwb.portal.community;

import java.net.URLEncoder;


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
        url+="?&act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }
}
