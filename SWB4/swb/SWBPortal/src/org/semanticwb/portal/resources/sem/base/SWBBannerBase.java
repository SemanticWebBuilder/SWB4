package org.semanticwb.portal.resources.sem.base;

public class SWBBannerBase extends org.semanticwb.portal.api.GenericSemResource
{
    public static final org.semanticwb.platform.SemanticProperty swbres_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#width");
    public static final org.semanticwb.platform.SemanticProperty swbres_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#height");
    public static final org.semanticwb.platform.SemanticClass swbres_SWBBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");

    public org.semanticwb.platform.SemanticObject getResourceData()
    {
        org.semanticwb.platform.SemanticObject obj=getResourceBase().getResourceData();
        if(obj==null)
        {
            org.semanticwb.platform.SemanticModel model=getResourceBase().getSemanticObject().getModel();
            obj=model.createSemanticObject(model.getObjectUri(getResourceBase().getId(), sclass), sclass);
            getResourceBase().setResourceData(obj);
        }
        return obj;
    }

    public int getWidth()
    {
        return getResourceData().getIntProperty(swbres_width);
    }

    public void setWidth(int width)
    {
        getResourceData().setLongProperty(swbres_width, width);
    }

    public int getHeight()
    {
        return getResourceData().getIntProperty(swbres_height);
    }

    public void setHeight(int height)
    {
        getResourceData().setLongProperty(swbres_height, height);
    }
}
