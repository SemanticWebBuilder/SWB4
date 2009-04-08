package org.semanticwb.portal.resources.sem.base;


public class SWBBannerBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swbbanner_width=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#width");
    public static final org.semanticwb.platform.SemanticProperty swbbanner_height=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#height");
    public static final org.semanticwb.platform.SemanticProperty swbbanner_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#image");
    public static final org.semanticwb.platform.SemanticClass swbbanner_SWBBanner=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#SWBBanner");

    public SWBBannerBase()
    {
    }

    public SWBBannerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/SWBBanner#SWBBanner");

    public int getWidth()
    {
        return getSemanticObject().getIntProperty(swbbanner_width);
    }

    public void setWidth(int width)
    {
        getSemanticObject().setLongProperty(swbbanner_width, width);
    }

    public int getHeight()
    {
        return getSemanticObject().getIntProperty(swbbanner_height);
    }

    public void setHeight(int height)
    {
        getSemanticObject().setLongProperty(swbbanner_height, height);
    }

    public String getImage()
    {
        return getSemanticObject().getProperty(swbbanner_image);
    }

    public void setImage(String image)
    {
        getSemanticObject().setProperty(swbbanner_image, image);
    }
}
