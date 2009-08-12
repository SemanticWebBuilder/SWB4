package org.semanticwb.portal.community.base;


public class VideosBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Video=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Video");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasVideo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasVideo");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Videos=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Videos");

    public VideosBase()
    {
    }

    public VideosBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Videos");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Video> listVideos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Video>(getSemanticObject().listObjectProperties(swbcomm_hasVideo));
    }

    public boolean hasVideo(org.semanticwb.portal.community.Video video)
    {
        if(video==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasVideo,video.getSemanticObject());
    }

    public void addVideo(org.semanticwb.portal.community.Video video)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasVideo, video.getSemanticObject());
    }

    public void removeAllVideo()
    {
        getSemanticObject().removeProperty(swbcomm_hasVideo);
    }

    public void removeVideo(org.semanticwb.portal.community.Video video)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasVideo,video.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Videos> listVideosByHasVideo(org.semanticwb.portal.community.Video hasvideo,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Videos> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasVideo, hasvideo.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Videos> listVideosByHasVideo(org.semanticwb.portal.community.Video hasvideo)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Videos> it=new org.semanticwb.model.GenericIterator(hasvideo.getSemanticObject().getModel().listSubjects(swbcomm_hasVideo,hasvideo.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Video getVideo()
    {
         org.semanticwb.portal.community.Video ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasVideo);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Video)obj.createGenericInstance();
         }
         return ret;
    }
}
