package org.semanticwb.portal.community;

import java.net.URLEncoder;


public class NewsElement extends org.semanticwb.portal.community.base.NewsElementBase 
{
    public NewsElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getNewsWebPage().getUrl();
        url+="?act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }
}
