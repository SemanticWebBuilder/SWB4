package org.semanticwb.model.base;


   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web 
   */
public abstract class PFlowBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.XMLable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass
{
   /**
   * Referencia a un objeto de tipo PFlow
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowRef");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowRefInv");
   /**
   * Instancia de un recurso asociado a un flujo de publicación.
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlowInstance");
    public static final org.semanticwb.platform.SemanticProperty swb_hasPFlowInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasPFlowInstance");
   /**
   * Un Flujo de Publicación es una serie de autorizaciones por las que pasa un contenido antes de publicarse en un Sitio Web
   */
    public static final org.semanticwb.platform.SemanticClass swb_PFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#PFlow");

    public static class ClassMgr
    {
       /**
       * Returns a list of PFlow for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlows(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.PFlow for all models
       * @return Iterator of org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlows()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow>(it, true);
        }

        public static org.semanticwb.model.PFlow createPFlow(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.PFlow.ClassMgr.createPFlow(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.PFlow
       * @param id Identifier for org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlow
       * @return A org.semanticwb.model.PFlow
       */
        public static org.semanticwb.model.PFlow getPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.PFlow
       * @param id Identifier for org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlow
       * @return A org.semanticwb.model.PFlow
       */
        public static org.semanticwb.model.PFlow createPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.PFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.PFlow
       * @param id Identifier for org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlow
       */
        public static void removePFlow(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.PFlow
       * @param id Identifier for org.semanticwb.model.PFlow
       * @param model Model of the org.semanticwb.model.PFlow
       * @return true if the org.semanticwb.model.PFlow exists, false otherwise
       */

        public static boolean hasPFlow(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPFlow(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined PFlowRefInv
       * @param value PFlowRefInv of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByPFlowRefInv(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined PFlowRefInv
       * @param value PFlowRefInv of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByPFlowRefInv(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined PFlowInstance
       * @param value PFlowInstance of the type org.semanticwb.model.PFlowInstance
       * @param model Model of the org.semanticwb.model.PFlow
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByPFlowInstance(org.semanticwb.model.PFlowInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.PFlow with a determined PFlowInstance
       * @param value PFlowInstance of the type org.semanticwb.model.PFlowInstance
       * @return Iterator with all the org.semanticwb.model.PFlow
       */

        public static java.util.Iterator<org.semanticwb.model.PFlow> listPFlowByPFlowInstance(org.semanticwb.model.PFlowInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlow> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowInstance,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static PFlowBase.ClassMgr getPFlowClassMgr()
    {
        return new PFlowBase.ClassMgr();
    }

   /**
   * Constructs a PFlowBase with a SemanticObject
   * @param base The SemanticObject with the properties for the PFlow
   */
    public PFlowBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the Xml property
* @return String with the Xml
*/
    public String getXml()
    {
        return getSemanticObject().getProperty(swb_xml);
    }

/**
* Sets the Xml property
* @param value long with the Xml
*/
    public void setXml(String value)
    {
        getSemanticObject().setProperty(swb_xml, value);
    }
   /**
   * Gets all the org.semanticwb.model.PFlowRef
   * @return A GenericIterator with all the org.semanticwb.model.PFlowRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(swb_hasPFlowRefInv));
    }

   /**
   * Gets true if has a PFlowRefInv
   * @param value org.semanticwb.model.PFlowRef to verify
   * @return true if the org.semanticwb.model.PFlowRef exists, false otherwise
   */
    public boolean hasPFlowRefInv(org.semanticwb.model.PFlowRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PFlowRefInv
   * @return a org.semanticwb.model.PFlowRef
   */
    public org.semanticwb.model.PFlowRef getPFlowRefInv()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.model.PFlowInstance
   * @return A GenericIterator with all the org.semanticwb.model.PFlowInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance> listPFlowInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowInstance>(getSemanticObject().listObjectProperties(swb_hasPFlowInstance));
    }

   /**
   * Gets true if has a PFlowInstance
   * @param value org.semanticwb.model.PFlowInstance to verify
   * @return true if the org.semanticwb.model.PFlowInstance exists, false otherwise
   */
    public boolean hasPFlowInstance(org.semanticwb.model.PFlowInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowInstance,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the PFlowInstance
   * @return a org.semanticwb.model.PFlowInstance
   */
    public org.semanticwb.model.PFlowInstance getPFlowInstance()
    {
         org.semanticwb.model.PFlowInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowInstance)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the WebSite
   * @return a instance of org.semanticwb.model.WebSite
   */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
