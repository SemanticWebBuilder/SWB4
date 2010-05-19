package org.semanticwb.portal.resources.sem.video.base;


public abstract class SWBVideoResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swbv_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#VideoElement");
    public static final org.semanticwb.platform.SemanticProperty swb_hasVideo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasVideo");
    public static final org.semanticwb.platform.SemanticClass swbv_SWBVideoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#SWBVideoResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/SWBVideo#SWBVideoResource");

    public SWBVideoResourceBase()
    {
    }

    public SWBVideoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement> listVideos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.video.VideoElement>(getSemanticObject().listObjectProperties(swb_hasVideo));
    }

    public boolean hasVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasVideo,value.getSemanticObject());
        }
        return ret;
    }

    public void addVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        getSemanticObject().addObjectProperty(swb_hasVideo, value.getSemanticObject());
    }

    public void removeAllVideo()
    {
        getSemanticObject().removeProperty(swb_hasVideo);
    }

    public void removeVideo(org.semanticwb.portal.resources.sem.video.VideoElement value)
    {
        getSemanticObject().removeObjectProperty(swb_hasVideo,value.getSemanticObject());
    }

    public org.semanticwb.portal.resources.sem.video.VideoElement getVideo()
    {
         org.semanticwb.portal.resources.sem.video.VideoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasVideo);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.video.VideoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
