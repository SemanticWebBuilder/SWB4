package org.semanticwb.model.base;


   /**
   * Superclase utilizada por compatibilidad con estandar TopicMaps 
   */
public abstract class TopicBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Traceable
{
   /**
   * Miembro participante dentro de una asociacion en la especificacion de TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_AssMember=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AssMember");
    public static final org.semanticwb.platform.SemanticProperty swb_hasAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasAssMemberInv");
    public static final org.semanticwb.platform.SemanticProperty swb_hasThisRoleAssMemberInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisRoleAssMemberInv");
   /**
   * Define una asociacion entre dos miembros en la especificacion de TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_Association=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Association");
    public static final org.semanticwb.platform.SemanticProperty swb_hasThisTypeAssociationInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasThisTypeAssociationInv");
   /**
   * Superclase utilizada por compatibilidad con estandar TopicMaps
   */
    public static final org.semanticwb.platform.SemanticClass swb_Topic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Topic");

    public static class ClassMgr
    {
       /**
       * Returns a list of Topic for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopics(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Topic for all models
       * @return Iterator of org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopics()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Topic
       * @param id Identifier for org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.Topic
       * @return A org.semanticwb.model.Topic
       */
        public static org.semanticwb.model.Topic getTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Topic)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Topic
       * @param id Identifier for org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.Topic
       * @return A org.semanticwb.model.Topic
       */
        public static org.semanticwb.model.Topic createTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Topic)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Topic
       * @param id Identifier for org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.Topic
       */
        public static void removeTopic(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Topic
       * @param id Identifier for org.semanticwb.model.Topic
       * @param model Model of the org.semanticwb.model.Topic
       * @return true if the org.semanticwb.model.Topic exists, false otherwise
       */

        public static boolean hasTopic(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTopic(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.Topic
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.Topic with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.Topic
       */

        public static java.util.Iterator<org.semanticwb.model.Topic> listTopicByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.Topic> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TopicBase.ClassMgr getTopicClassMgr()
    {
        return new TopicBase.ClassMgr();
    }

   /**
   * Constructs a TopicBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Topic
   */
    public TopicBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.model.AssMember
   * @return A GenericIterator with all the org.semanticwb.model.AssMember
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listAssMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(swb_hasAssMemberInv));
    }

   /**
   * Gets true if has a AssMember
   * @param value org.semanticwb.model.AssMember to verify
   * @return true if the org.semanticwb.model.AssMember exists, false otherwise
   */
    public boolean hasAssMember(org.semanticwb.model.AssMember value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasAssMemberInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the AssMember
   * @return a org.semanticwb.model.AssMember
   */
    public org.semanticwb.model.AssMember getAssMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasAssMemberInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
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
   /**
   * Gets all the org.semanticwb.model.AssMember
   * @return A GenericIterator with all the org.semanticwb.model.AssMember
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember> listThisRoleAssMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.AssMember>(getSemanticObject().listObjectProperties(swb_hasThisRoleAssMemberInv));
    }

   /**
   * Gets true if has a ThisRoleAssMember
   * @param value org.semanticwb.model.AssMember to verify
   * @return true if the org.semanticwb.model.AssMember exists, false otherwise
   */
    public boolean hasThisRoleAssMember(org.semanticwb.model.AssMember value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasThisRoleAssMemberInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ThisRoleAssMember
   * @return a org.semanticwb.model.AssMember
   */
    public org.semanticwb.model.AssMember getThisRoleAssMember()
    {
         org.semanticwb.model.AssMember ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasThisRoleAssMemberInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.AssMember)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
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

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
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

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
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

/**
* Gets the Tags property
* @return String with the Tags
*/
    public String getTags()
    {
        return getSemanticObject().getProperty(swb_tags);
    }

/**
* Sets the Tags property
* @param value long with the Tags
*/
    public void setTags(String value)
    {
        getSemanticObject().setProperty(swb_tags, value);
    }

    public String getTags(String lang)
    {
        return getSemanticObject().getProperty(swb_tags, null, lang);
    }

    public String getDisplayTags(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_tags, lang);
    }

    public void setTags(String tags, String lang)
    {
        getSemanticObject().setProperty(swb_tags, tags, lang);
    }
   /**
   * Gets all the org.semanticwb.model.Association
   * @return A GenericIterator with all the org.semanticwb.model.Association
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Association> listThisTypeAssociations()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Association>(getSemanticObject().listObjectProperties(swb_hasThisTypeAssociationInv));
    }

   /**
   * Gets true if has a ThisTypeAssociation
   * @param value org.semanticwb.model.Association to verify
   * @return true if the org.semanticwb.model.Association exists, false otherwise
   */
    public boolean hasThisTypeAssociation(org.semanticwb.model.Association value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasThisTypeAssociationInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ThisTypeAssociation
   * @return a org.semanticwb.model.Association
   */
    public org.semanticwb.model.Association getThisTypeAssociation()
    {
         org.semanticwb.model.Association ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasThisTypeAssociationInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Association)obj.createGenericInstance();
         }
         return ret;
    }
}
