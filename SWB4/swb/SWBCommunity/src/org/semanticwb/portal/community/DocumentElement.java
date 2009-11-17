package org.semanticwb.portal.community;

import java.net.URLEncoder;
import org.semanticwb.model.WebPage;

public class DocumentElement extends org.semanticwb.portal.community.base.DocumentElementBase 
{
    public DocumentElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getURL()
    {
        String url = "#";
        url=this.getDocumentWebPage().getUrl();
        url+="?act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }

    @Override
    public WebPage getWebPage()
    {
        return super.getDocumentWebPage();
    }
}
