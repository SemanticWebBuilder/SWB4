package org.semanticwb.portal.resources.sem.base;


public abstract class CommentToElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
   /**
   * Superclase de todos los objetos con persistencia en SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBClass");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_element=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#element");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_commentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#commentToElement");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_email=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#email");
    public static final org.semanticwb.platform.SemanticClass swb_res_cmts_CommentToElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#CommentToElement");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_commentToElementInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#commentToElementInv");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#name");
    public static final org.semanticwb.platform.SemanticProperty swb_res_cmts_hasAnswerBack=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#hasAnswerBack");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/sems/SWBCommentToElement#CommentToElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of CommentToElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.CommentToElement for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.CommentToElement createCommentToElement(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.CommentToElement.ClassMgr.createCommentToElement(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.CommentToElement
       * @param id Identifier for org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return A org.semanticwb.portal.resources.sem.CommentToElement
       */
        public static org.semanticwb.portal.resources.sem.CommentToElement getCommentToElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.CommentToElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.CommentToElement
       * @param id Identifier for org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return A org.semanticwb.portal.resources.sem.CommentToElement
       */
        public static org.semanticwb.portal.resources.sem.CommentToElement createCommentToElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.CommentToElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.CommentToElement
       * @param id Identifier for org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       */
        public static void removeCommentToElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.CommentToElement
       * @param id Identifier for org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return true if the org.semanticwb.portal.resources.sem.CommentToElement exists, false otherwise
       */

        public static boolean hasCommentToElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCommentToElement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined Element
       * @param value Element of the type org.semanticwb.model.SWBClass
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByElement(org.semanticwb.model.SWBClass value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_element, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined Element
       * @param value Element of the type org.semanticwb.model.SWBClass
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByElement(org.semanticwb.model.SWBClass value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_element,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined AnswerBackTo
       * @param value AnswerBackTo of the type org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByAnswerBackTo(org.semanticwb.portal.resources.sem.CommentToElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_commentToElementInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined AnswerBackTo
       * @param value AnswerBackTo of the type org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByAnswerBackTo(org.semanticwb.portal.resources.sem.CommentToElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_commentToElementInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined AnswerBack
       * @param value AnswerBack of the type org.semanticwb.portal.resources.sem.CommentToElement
       * @param model Model of the org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByAnswerBack(org.semanticwb.portal.resources.sem.CommentToElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_hasAnswerBack, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.CommentToElement with a determined AnswerBack
       * @param value AnswerBack of the type org.semanticwb.portal.resources.sem.CommentToElement
       * @return Iterator with all the org.semanticwb.portal.resources.sem.CommentToElement
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.CommentToElement> listCommentToElementByAnswerBack(org.semanticwb.portal.resources.sem.CommentToElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_res_cmts_hasAnswerBack,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CommentToElementBase.ClassMgr getCommentToElementClassMgr()
    {
        return new CommentToElementBase.ClassMgr();
    }

   /**
   * Constructs a CommentToElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CommentToElement
   */
    public CommentToElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
   * Sets the value for the property Element
   * @param value Element to set
   */

    public void setElement(org.semanticwb.model.SWBClass value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_res_cmts_element, value.getSemanticObject());
        }else
        {
            removeElement();
        }
    }
   /**
   * Remove the value for Element property
   */

    public void removeElement()
    {
        getSemanticObject().removeProperty(swb_res_cmts_element);
    }

   /**
   * Gets the Element
   * @return a org.semanticwb.model.SWBClass
   */
    public org.semanticwb.model.SWBClass getElement()
    {
         org.semanticwb.model.SWBClass ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_element);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.SWBClass)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the CommentToElement property
* @return String with the CommentToElement
*/
    public String getCommentToElement()
    {
        return getSemanticObject().getProperty(swb_res_cmts_commentToElement);
    }

/**
* Sets the CommentToElement property
* @param value long with the CommentToElement
*/
    public void setCommentToElement(String value)
    {
        getSemanticObject().setProperty(swb_res_cmts_commentToElement, value);
    }

/**
* Gets the Email property
* @return String with the Email
*/
    public String getEmail()
    {
        return getSemanticObject().getProperty(swb_res_cmts_email);
    }

/**
* Sets the Email property
* @param value long with the Email
*/
    public void setEmail(String value)
    {
        getSemanticObject().setProperty(swb_res_cmts_email, value);
    }
   /**
   * Sets the value for the property AnswerBackTo
   * @param value AnswerBackTo to set
   */

    public void setAnswerBackTo(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_res_cmts_commentToElementInv, value.getSemanticObject());
        }else
        {
            removeAnswerBackTo();
        }
    }
   /**
   * Remove the value for AnswerBackTo property
   */

    public void removeAnswerBackTo()
    {
        getSemanticObject().removeProperty(swb_res_cmts_commentToElementInv);
    }

   /**
   * Gets the AnswerBackTo
   * @return a org.semanticwb.portal.resources.sem.CommentToElement
   */
    public org.semanticwb.portal.resources.sem.CommentToElement getAnswerBackTo()
    {
         org.semanticwb.portal.resources.sem.CommentToElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_commentToElementInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CommentToElement)obj.createGenericInstance();
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
* Gets the Name property
* @return String with the Name
*/
    public String getName()
    {
        return getSemanticObject().getProperty(swb_res_cmts_name);
    }

/**
* Sets the Name property
* @param value long with the Name
*/
    public void setName(String value)
    {
        getSemanticObject().setProperty(swb_res_cmts_name, value);
    }
   /**
   * Gets all the org.semanticwb.portal.resources.sem.CommentToElement
   * @return A GenericIterator with all the org.semanticwb.portal.resources.sem.CommentToElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement> listAnswerBacks()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.CommentToElement>(getSemanticObject().listObjectProperties(swb_res_cmts_hasAnswerBack));
    }

   /**
   * Gets true if has a AnswerBack
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to verify
   * @return true if the org.semanticwb.portal.resources.sem.CommentToElement exists, false otherwise
   */
    public boolean hasAnswerBack(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_res_cmts_hasAnswerBack,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a AnswerBack
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to add
   */

    public void addAnswerBack(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().addObjectProperty(swb_res_cmts_hasAnswerBack, value.getSemanticObject());
    }
   /**
   * Removes all the AnswerBack
   */

    public void removeAllAnswerBack()
    {
        getSemanticObject().removeProperty(swb_res_cmts_hasAnswerBack);
    }
   /**
   * Removes a AnswerBack
   * @param value org.semanticwb.portal.resources.sem.CommentToElement to remove
   */

    public void removeAnswerBack(org.semanticwb.portal.resources.sem.CommentToElement value)
    {
        getSemanticObject().removeObjectProperty(swb_res_cmts_hasAnswerBack,value.getSemanticObject());
    }

   /**
   * Gets the AnswerBack
   * @return a org.semanticwb.portal.resources.sem.CommentToElement
   */
    public org.semanticwb.portal.resources.sem.CommentToElement getAnswerBack()
    {
         org.semanticwb.portal.resources.sem.CommentToElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_res_cmts_hasAnswerBack);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.CommentToElement)obj.createGenericInstance();
         }
         return ret;
    }
}
