package org.semanticwb.portal.community.base;


public class DirectoryResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryClass");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryClass");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryObject");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirectoryObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirectoryObject");
    public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");

    public DirectoryResourceBase()
    {
    }

    public DirectoryResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");

    public void setDirectoryClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbcomm_directoryClass, value);
    }

    public void removeDirectoryClass()
    {
        getSemanticObject().removeProperty(swbcomm_directoryClass);
    }

    public org.semanticwb.platform.SemanticObject getDirectoryClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbcomm_directoryClass);
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjects()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject>(getSemanticObject().listObjectProperties(swbcomm_hasDirectoryObject));
    }

    public boolean hasDirectoryObject(org.semanticwb.portal.community.DirectoryObject directoryobject)
    {
        if(directoryobject==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasDirectoryObject,directoryobject.getSemanticObject());
    }

    public void addDirectoryObject(org.semanticwb.portal.community.DirectoryObject value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasDirectoryObject, value.getSemanticObject());
    }

    public void removeAllDirectoryObject()
    {
        getSemanticObject().removeProperty(swbcomm_hasDirectoryObject);
    }

    public void removeDirectoryObject(org.semanticwb.portal.community.DirectoryObject directoryobject)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasDirectoryObject,directoryobject.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryResource> listDirectoryResourceByDirectoryObject(org.semanticwb.portal.community.DirectoryObject hasdirectoryobject,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirectoryObject, hasdirectoryobject.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryResource> listDirectoryResourceByDirectoryObject(org.semanticwb.portal.community.DirectoryObject hasdirectoryobject)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryResource> it=new org.semanticwb.model.GenericIterator(hasdirectoryobject.getSemanticObject().getModel().listSubjects(swbcomm_hasDirectoryObject,hasdirectoryobject.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.DirectoryObject getDirectoryObject()
    {
         org.semanticwb.portal.community.DirectoryObject ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasDirectoryObject);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.DirectoryObject)obj.createGenericInstance();
         }
         return ret;
    }
}
