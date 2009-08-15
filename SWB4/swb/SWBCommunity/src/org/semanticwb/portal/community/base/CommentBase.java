package org.semanticwb.portal.community.base;


public class CommentBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_comDescription=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#comDescription");
    public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_comMicroSiteElementInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#comMicroSiteElementInv");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_comSpam=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#comSpam");
    public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
    public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");

    public CommentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Comment> listComments(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.Comment> listComments()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment>(it, true);
    }

    public static org.semanticwb.portal.community.Comment createComment(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.Comment.createComment(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.Comment getComment(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Comment)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.Comment createComment(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.Comment)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeComment(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasComment(String id, org.semanticwb.model.SWBModel model)
    {
        return (getComment(id, model)!=null);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swbcomm_comDescription);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swbcomm_comDescription, value);
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

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public void setMicroSiteElement(org.semanticwb.portal.community.MicroSiteElement value)
    {
        getSemanticObject().setObjectProperty(swbcomm_comMicroSiteElementInv, value.getSemanticObject());
    }

    public void removeMicroSiteElement()
    {
        getSemanticObject().removeProperty(swbcomm_comMicroSiteElementInv);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByMicroSiteElement(org.semanticwb.portal.community.MicroSiteElement commicrositeelementinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_comMicroSiteElementInv, commicrositeelementinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByMicroSiteElement(org.semanticwb.portal.community.MicroSiteElement commicrositeelementinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(commicrositeelementinv.getSemanticObject().getModel().listSubjects(swbcomm_comMicroSiteElementInv,commicrositeelementinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.MicroSiteElement getMicroSiteElement()
    {
         org.semanticwb.portal.community.MicroSiteElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_comMicroSiteElementInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteElement)obj.createGenericInstance();
         }
         return ret;
    }

    public boolean isSpam()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_comSpam);
    }

    public void setSpam(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_comSpam, value);
    }

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Comment> listCommentByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Comment> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
