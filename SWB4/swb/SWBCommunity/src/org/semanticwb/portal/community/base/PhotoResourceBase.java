package org.semanticwb.portal.community.base;


public class PhotoResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasPhoto");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");

    public PhotoResourceBase()
    {
    }

    public PhotoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> listPhotos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(getSemanticObject().listObjectProperties(swbcomm_hasPhoto));
    }

    public boolean hasPhoto(org.semanticwb.portal.community.PhotoElement photoelement)
    {
        if(photoelement==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasPhoto,photoelement.getSemanticObject());
    }

    public void addPhoto(org.semanticwb.portal.community.PhotoElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasPhoto, value.getSemanticObject());
    }

    public void removeAllPhoto()
    {
        getSemanticObject().removeProperty(swbcomm_hasPhoto);
    }

    public void removePhoto(org.semanticwb.portal.community.PhotoElement photoelement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasPhoto,photoelement.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoResource> listPhotoResourceByHasPhoto(org.semanticwb.portal.community.PhotoElement hasphoto,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasPhoto, hasphoto.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.PhotoResource> listPhotoResourceByHasPhoto(org.semanticwb.portal.community.PhotoElement hasphoto)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoResource> it=new org.semanticwb.model.GenericIterator(hasphoto.getSemanticObject().getModel().listSubjects(swbcomm_hasPhoto,hasphoto.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.PhotoElement getPhoto()
    {
         org.semanticwb.portal.community.PhotoElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasPhoto);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.PhotoElement)obj.createGenericInstance();
         }
         return ret;
    }
}
