package org.semanticwb.portal.community.base;


public class MicroSiteElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Rankable,org.semanticwb.model.Viewable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
    public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
    public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
    public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
    public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteElement");

    public MicroSiteElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElements(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElements()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement>(it, true);
    }

    public static org.semanticwb.portal.community.MicroSiteElement getMicroSiteElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.MicroSiteElement createMicroSiteElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.MicroSiteElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeMicroSiteElement(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasMicroSiteElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (getMicroSiteElement(id, model)!=null);
    }

    public int getVisibility()
    {
        return getSemanticObject().getIntProperty(swbcomm_visibility);
    }

    public void setVisibility(int value)
    {
        getSemanticObject().setIntProperty(swbcomm_visibility, value);
    }

    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
    }

    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public boolean isAbused()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_abused);
    }

    public void setAbused(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_abused, value);
    }

    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
    }

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public long getViews()
    {
        //Override this method in MicroSiteElement object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    public void setViews(long value)
    {
        //Override this method in MicroSiteElement object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> listComments()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment>(getSemanticObject().listObjectProperties(swbcomm_hasComment));
    }

    public boolean hasComment(org.semanticwb.portal.community.Comment comment)
    {
        if(comment==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasComment,comment.getSemanticObject());
    }

    public void addComment(org.semanticwb.portal.community.Comment value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasComment, value.getSemanticObject());
    }

    public void removeAllComment()
    {
        getSemanticObject().removeProperty(swbcomm_hasComment);
    }

    public void removeComment(org.semanticwb.portal.community.Comment comment)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasComment,comment.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByHasComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElementByHasComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Comment getComment()
    {
         org.semanticwb.portal.community.Comment ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasComment);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Comment)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
