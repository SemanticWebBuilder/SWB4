package org.semanticwb.portal.community;


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
        return url;
    }
}
