package com.infotec.pic.swb.base;


public abstract class RecommendationBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Trashable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass pic_Company=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Company");
    public static final org.semanticwb.platform.SemanticProperty pic_recommTo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#recommTo");
    public static final org.semanticwb.platform.SemanticProperty pic_recommendation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#recommendation");
    public static final org.semanticwb.platform.SemanticProperty pic_recommFrom=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#recommFrom");
    public static final org.semanticwb.platform.SemanticProperty pic_innapropiated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/pic.owl#innapropiated");
    public static final org.semanticwb.platform.SemanticClass pic_Recommendation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Recommendation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/pic.owl#Recommendation");

    public static class ClassMgr
    {
       /**
       * Returns a list of Recommendation for a model
       * @param model Model to find
       * @return Iterator of com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation>(it, true);
        }
       /**
       * Returns a list of com.infotec.pic.swb.Recommendation for all models
       * @return Iterator of com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation>(it, true);
        }

        public static com.infotec.pic.swb.Recommendation createRecommendation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return com.infotec.pic.swb.Recommendation.ClassMgr.createRecommendation(String.valueOf(id), model);
        }
       /**
       * Gets a com.infotec.pic.swb.Recommendation
       * @param id Identifier for com.infotec.pic.swb.Recommendation
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return A com.infotec.pic.swb.Recommendation
       */
        public static com.infotec.pic.swb.Recommendation getRecommendation(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Recommendation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a com.infotec.pic.swb.Recommendation
       * @param id Identifier for com.infotec.pic.swb.Recommendation
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return A com.infotec.pic.swb.Recommendation
       */
        public static com.infotec.pic.swb.Recommendation createRecommendation(String id, org.semanticwb.model.SWBModel model)
        {
            return (com.infotec.pic.swb.Recommendation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a com.infotec.pic.swb.Recommendation
       * @param id Identifier for com.infotec.pic.swb.Recommendation
       * @param model Model of the com.infotec.pic.swb.Recommendation
       */
        public static void removeRecommendation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a com.infotec.pic.swb.Recommendation
       * @param id Identifier for com.infotec.pic.swb.Recommendation
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return true if the com.infotec.pic.swb.Recommendation exists, false otherwise
       */

        public static boolean hasRecommendation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRecommendation(id, model)!=null);
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined RecommTo
       * @param value RecommTo of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByRecommTo(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_recommTo, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined RecommTo
       * @param value RecommTo of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByRecommTo(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_recommTo,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined RecommFrom
       * @param value RecommFrom of the type com.infotec.pic.swb.Company
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByRecommFrom(com.infotec.pic.swb.Company value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(pic_recommFrom, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined RecommFrom
       * @param value RecommFrom of the type com.infotec.pic.swb.Company
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByRecommFrom(com.infotec.pic.swb.Company value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(pic_recommFrom,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the com.infotec.pic.swb.Recommendation
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all com.infotec.pic.swb.Recommendation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the com.infotec.pic.swb.Recommendation
       */

        public static java.util.Iterator<com.infotec.pic.swb.Recommendation> listRecommendationByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<com.infotec.pic.swb.Recommendation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RecommendationBase.ClassMgr getRecommendationClassMgr()
    {
        return new RecommendationBase.ClassMgr();
    }

   /**
   * Constructs a RecommendationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Recommendation
   */
    public RecommendationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property RecommTo
   * @param value RecommTo to set
   */

    public void setRecommTo(com.infotec.pic.swb.Company value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_recommTo, value.getSemanticObject());
        }else
        {
            removeRecommTo();
        }
    }
   /**
   * Remove the value for RecommTo property
   */

    public void removeRecommTo()
    {
        getSemanticObject().removeProperty(pic_recommTo);
    }

   /**
   * Gets the RecommTo
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getRecommTo()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_recommTo);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
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
* Gets the Recommendation property
* @return String with the Recommendation
*/
    public String getRecommendation()
    {
        return getSemanticObject().getProperty(pic_recommendation);
    }

/**
* Sets the Recommendation property
* @param value long with the Recommendation
*/
    public void setRecommendation(String value)
    {
        getSemanticObject().setProperty(pic_recommendation, value);
    }

/**
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }
   /**
   * Sets the value for the property RecommFrom
   * @param value RecommFrom to set
   */

    public void setRecommFrom(com.infotec.pic.swb.Company value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(pic_recommFrom, value.getSemanticObject());
        }else
        {
            removeRecommFrom();
        }
    }
   /**
   * Remove the value for RecommFrom property
   */

    public void removeRecommFrom()
    {
        getSemanticObject().removeProperty(pic_recommFrom);
    }

   /**
   * Gets the RecommFrom
   * @return a com.infotec.pic.swb.Company
   */
    public com.infotec.pic.swb.Company getRecommFrom()
    {
         com.infotec.pic.swb.Company ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(pic_recommFrom);
         if(obj!=null)
         {
             ret=(com.infotec.pic.swb.Company)obj.createGenericInstance();
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
* Gets the Innapropiated property
* @return boolean with the Innapropiated
*/
    public boolean isInnapropiated()
    {
        return getSemanticObject().getBooleanProperty(pic_innapropiated);
    }

/**
* Sets the Innapropiated property
* @param value long with the Innapropiated
*/
    public void setInnapropiated(boolean value)
    {
        getSemanticObject().setBooleanProperty(pic_innapropiated, value);
    }
}
