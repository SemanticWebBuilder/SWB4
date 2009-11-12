package org.semanticwb.portal.community.base;


public class VideoResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swbcomm_VideoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoElement");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasVideo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasVideo");
       public static final org.semanticwb.platform.SemanticClass swbcomm_VideoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#VideoResource");
    }

    public VideoResourceBase()
    {
    }

    public VideoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement> listVideos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.VideoElement>(getSemanticObject().listObjectProperties(swbcomm_hasVideo));
    }

    public boolean hasVideo(org.semanticwb.portal.community.VideoElement videoelement)
    {
        if(videoelement==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasVideo,videoelement.getSemanticObject());
    }

    public void addVideo(org.semanticwb.portal.community.VideoElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasVideo, value.getSemanticObject());
    }

    public void removeAllVideo()
    {
        getSemanticObject().removeProperty(swbcomm_hasVideo);
    }

    public void removeVideo(org.semanticwb.portal.community.VideoElement videoelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasVideo,videoelement.getSemanticObject());
    }


    public org.semanticwb.portal.community.VideoElement getVideo()
    {
         org.semanticwb.portal.community.VideoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasVideo);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.VideoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
