package org.semanticwb.model.base;


public class TopicBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Indexable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
       public static final org.semanticwb.platform.SemanticProperty swb_hasAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swb_indexable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#indexable");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisRoleAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisRoleAssMemberInv");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
       public static final org.semanticwb.platform.SemanticProperty swb_hasThisTypeAssociationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisTypeAssociationInv");
       public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");

       public static java.util.Iterator<org.semanticwb.model.Topic> listTopics(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.model.Topic> listTopics()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic>(it, true);
       }

       public static org.semanticwb.model.Topic getTopic(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.Topic)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.model.Topic createTopic(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.model.Topic)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeTopic(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasTopic(String id, org.semanticwb.model.SWBModel model)
       {
           return (getTopic(id, model)!=null);
       }
    }

    public TopicBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(ClassMgr.swb_modifiedBy,modifiedby.getSemanticObject()));
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listAssMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasAssMemberInv));
    }

    public boolean hasAssMember(org.semanticwb.model.AssMember assmember)
    {
        if(assmember==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasAssMemberInv,assmember.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasAssMemberInv, hasassmemberinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasAssMemberInv,hasassmemberinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.AssMember getAssMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasAssMemberInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
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

    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(ClassMgr.swb_updated);
    }

    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(ClassMgr.swb_updated, value);
    }

    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(ClassMgr.swb_indexable);
    }

    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(ClassMgr.swb_indexable, value);
    }

    public String getTags()
    {
        return getSemanticObject().getProperty(ClassMgr.swb_tags);
    }

    public void setTags(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swb_tags, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listThisRoleAssMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasThisRoleAssMemberInv));
    }

    public boolean hasThisRoleAssMember(org.semanticwb.model.AssMember assmember)
    {
        if(assmember==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasThisRoleAssMemberInv,assmember.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.AssMember getThisRoleAssMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasThisRoleAssMemberInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swb_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(ClassMgr.swb_creator);
    }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(ClassMgr.swb_creator,creator.getSemanticObject()));
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> listThisTypeAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(getSemanticObject().listObjectProperties(ClassMgr.swb_hasThisTypeAssociationInv));
    }

    public boolean hasThisTypeAssociation(org.semanticwb.model.Association association)
    {
        if(association==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swb_hasThisTypeAssociationInv,association.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjects(ClassMgr.swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.Association getThisTypeAssociation()
    {
         org.semanticwb.model.Association ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swb_hasThisTypeAssociationInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Association)obj.createGenericInstance();
         }
         return ret;
    }
}
