package org.semanticwb.portal.community;


public class ProductElement extends org.semanticwb.portal.community.base.ProductElementBase 
{
    public ProductElement(org.semanticwb.platform.SemanticObject base)
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
