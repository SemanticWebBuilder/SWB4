package org.semanticwb.portal.community.base;


public abstract class PhotoResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasPhoto");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PhotoResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PhotoResource");

    public PhotoResourceBase()
    {
    }

    public PhotoResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement> listPhotos()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.PhotoElement>(getSemanticObject().listObjectProperties(swbcomm_hasPhoto));
    }

    public boolean hasPhoto(org.semanticwb.portal.community.PhotoElement photoelement)
    {
        boolean ret=false;
        if(photoelement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasPhoto,photoelement.getSemanticObject());
        }
        return ret;
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
