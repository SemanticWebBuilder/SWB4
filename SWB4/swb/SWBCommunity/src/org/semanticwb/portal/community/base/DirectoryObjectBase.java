package org.semanticwb.portal.community.base;


public class DirectoryObjectBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Interactiveable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryResource");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_directoryResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#directoryResource");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirProfileWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirProfileWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirHasExtraPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirHasExtraPhoto");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirWebPage");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasDirTopicWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasDirTopicWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_dirPhoto=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#dirPhoto");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DirectoryObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryObject");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DirectoryObject");

       public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjects(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjects()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject>(it, true);
       }

       public static org.semanticwb.portal.community.DirectoryObject getDirectoryObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.DirectoryObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.DirectoryObject createDirectoryObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.DirectoryObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDirectoryObject(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDirectoryObject(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDirectoryObject(id, model)!=null);
       }
    }

    public DirectoryObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public long getReviews()
    {
        return getSemanticObject().getLongProperty(ClassMgr.swb_reviews);
    }

    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(ClassMgr.swb_reviews, value);
    }

    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(ClassMgr.swb_rank);
    }

    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(ClassMgr.swb_rank, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_tags, value);
    }

    public boolean isAbused()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swbcomm_abused);
    }

    public void setAbused(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swbcomm_abused, value);
    }

    public void setDirectoryResource(org.semanticwb.portal.community.DirectoryResource value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_directoryResource, value.getSemanticObject());
    }

    public void removeDirectoryResource()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_directoryResource);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_directoryResource, directoryresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(directoryresource.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_directoryResource,directoryresource.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.DirectoryResource getDirectoryResource()
    {
         org.semanticwb.portal.community.DirectoryResource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_directoryResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.DirectoryResource)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listProfiles()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasDirProfileWebPage));
    }

    public boolean hasProfile(org.semanticwb.model.WebPage webpage)
    {
        if(webpage==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasDirProfileWebPage,webpage.getSemanticObject());
    }

    public void addProfile(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasDirProfileWebPage, value.getSemanticObject());
    }

    public void removeAllProfile()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasDirProfileWebPage);
    }

    public void removeProfile(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasDirProfileWebPage,webpage.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasDirProfileWebPage, hasdirprofilewebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(hasdirprofilewebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasDirProfileWebPage,hasdirprofilewebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getProfile()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasDirProfileWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Iterator<String> listExtraPhotos()
    {
        java.util.ArrayList<String> values=new java.util.ArrayList<String>();
        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(ClassMgr.swbcomm_dirHasExtraPhoto);
        while(it.hasNext())
        {
                org.semanticwb.platform.SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addExtraPhoto(String extraphoto)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_dirHasExtraPhoto, extraphoto);
    }

    public void removeAllExtraPhoto()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_dirHasExtraPhoto);
    }

    public void removeExtraPhoto(String extraphoto)
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_dirHasExtraPhoto,extraphoto);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_title, title, lang);
    }

    public void setWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_dirWebPage, value.getSemanticObject());
    }

    public void removeWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_dirWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByWebPage(org.semanticwb.model.WebPage dirwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_dirWebPage, dirwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByWebPage(org.semanticwb.model.WebPage dirwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(dirwebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_dirWebPage,dirwebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_dirWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listTopicWebPages()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasDirTopicWebPage));
    }

    public boolean hasTopicWebPage(org.semanticwb.model.WebPage webpage)
    {
        if(webpage==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasDirTopicWebPage,webpage.getSemanticObject());
    }

    public void addTopicWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasDirTopicWebPage, value.getSemanticObject());
    }

    public void removeAllTopicWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasDirTopicWebPage);
    }

    public void removeTopicWebPage(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasDirTopicWebPage,webpage.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasDirTopicWebPage, hasdirtopicwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(hasdirtopicwebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasDirTopicWebPage,hasdirtopicwebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getTopicWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasDirTopicWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getPhoto()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_dirPhoto);
    }

    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_dirPhoto, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.community.Comment comment)
    {
        if(comment==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasComment,comment.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.community.Comment value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasComment, value.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasComment);
    }

    public void removeComment(org.semanticwb.portal.community.Comment comment)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasComment,comment.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DirectoryObject> listDirectoryObjectByComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DirectoryObject> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Comment getComment()
    {
         org.semanticwb.portal.community.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Comment)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(ClassMgr.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(ClassMgr.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(ClassMgr.swb_description, description, lang);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
