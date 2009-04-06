package org.semanticwb.portal.resources.sem.base;

public class SWBBannerBase extends org.semanticwb.portal.api.GenericSemResource
{
    public static org.semanticwb.platform.SemanticProperty swbres_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#width");
    public static org.semanticwb.platform.SemanticProperty swbres_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#height");
    public static org.semanticwb.platform.SemanticProperty swbres_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#image");
    public static org.semanticwb.platform.SemanticClass swbres_SWBBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");
    public static org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");


    public void SWBBannerBase()
    {
    }

    public SWBBannerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    protected void semanticInit()
    {
        swbres_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#width");
        swbres_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#height");
        swbres_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources#image");
        swbres_SWBBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");
        sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources#SWBBanner");
    }

    @Override
    public org.semanticwb.platform.SemanticClass getSemanticClass()
    {
        return sclass;
    }

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swbres_width);
    }

    public void setWidth(int width)
    {
        getSemanticObject().setLongProperty(swbres_width, width);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swbres_height);
    }

    public void setHeight(int height)
    {
        getSemanticObject().setLongProperty(swbres_height, height);
    }

    public String getImage()
    {
        return getSemanticObject().getProperty(swbres_image);
    }

    public void setImage(String image)
    {
        getSemanticObject().setProperty(swbres_image, image);
    }
}
