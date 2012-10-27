package org.semanticwb.domotic.model.base;


public abstract class DomEventBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb4d_onceEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#onceEvent");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomAction");
    public static final org.semanticwb.platform.SemanticProperty swb4d_hasDomAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#hasDomAction");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomContext");
    public static final org.semanticwb.platform.SemanticProperty swb4d_domEventContext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#domEventContext");
    public static final org.semanticwb.platform.SemanticClass swb4d_DomEvent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#DomEvent");

    public static class ClassMgr
    {
       /**
       * Returns a list of DomEvent for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEvents(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.DomEvent for all models
       * @return Iterator of org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEvents()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent>(it, true);
        }

        public static org.semanticwb.domotic.model.DomEvent createDomEvent(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.DomEvent.ClassMgr.createDomEvent(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.DomEvent
       * @param id Identifier for org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       * @return A org.semanticwb.domotic.model.DomEvent
       */
        public static org.semanticwb.domotic.model.DomEvent getDomEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomEvent)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.DomEvent
       * @param id Identifier for org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       * @return A org.semanticwb.domotic.model.DomEvent
       */
        public static org.semanticwb.domotic.model.DomEvent createDomEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.DomEvent)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.DomEvent
       * @param id Identifier for org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       */
        public static void removeDomEvent(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.DomEvent
       * @param id Identifier for org.semanticwb.domotic.model.DomEvent
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       * @return true if the org.semanticwb.domotic.model.DomEvent exists, false otherwise
       */

        public static boolean hasDomEvent(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDomEvent(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEventByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomEvent with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEventByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.DomEvent
       * @return Iterator with all the org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEventByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.DomEvent with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.DomEvent
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.DomEvent> listDomEventByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomEvent> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DomEventBase.ClassMgr getDomEventClassMgr()
    {
        return new DomEventBase.ClassMgr();
    }

   /**
   * Constructs a DomEventBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DomEvent
   */
    public DomEventBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
* Gets the OnceEvent property
* @return boolean with the OnceEvent
*/
    public boolean isOnceEvent()
    {
        return getSemanticObject().getBooleanProperty(swb4d_onceEvent);
    }

/**
* Sets the OnceEvent property
* @param value long with the OnceEvent
*/
    public void setOnceEvent(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb4d_onceEvent, value);
    }
   /**
   * Gets all the org.semanticwb.domotic.model.DomAction
   * @return A GenericIterator with all the org.semanticwb.domotic.model.DomAction
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction> listDomActions()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.DomAction>(getSemanticObject().listObjectProperties(swb4d_hasDomAction));
    }

   /**
   * Gets true if has a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to verify
   * @return true if the org.semanticwb.domotic.model.DomAction exists, false otherwise
   */
    public boolean hasDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb4d_hasDomAction,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to add
   */

    public void addDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().addObjectProperty(swb4d_hasDomAction, value.getSemanticObject());
    }
   /**
   * Removes all the DomAction
   */

    public void removeAllDomAction()
    {
        getSemanticObject().removeProperty(swb4d_hasDomAction);
    }
   /**
   * Removes a DomAction
   * @param value org.semanticwb.domotic.model.DomAction to remove
   */

    public void removeDomAction(org.semanticwb.domotic.model.DomAction value)
    {
        getSemanticObject().removeObjectProperty(swb4d_hasDomAction,value.getSemanticObject());
    }

   /**
   * Gets the DomAction
   * @return a org.semanticwb.domotic.model.DomAction
   */
    public org.semanticwb.domotic.model.DomAction getDomAction()
    {
         org.semanticwb.domotic.model.DomAction ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_hasDomAction);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomAction)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DomContext
   * @param value DomContext to set
   */

    public void setDomContext(org.semanticwb.domotic.model.DomContext value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_domEventContext, value.getSemanticObject());
        }else
        {
            removeDomContext();
        }
    }
   /**
   * Remove the value for DomContext property
   */

    public void removeDomContext()
    {
        getSemanticObject().removeProperty(swb4d_domEventContext);
    }

   /**
   * Gets the DomContext
   * @return a org.semanticwb.domotic.model.DomContext
   */
    public org.semanticwb.domotic.model.DomContext getDomContext()
    {
         org.semanticwb.domotic.model.DomContext ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_domEventContext);
         if(obj!=null)
         {
             ret=(org.semanticwb.domotic.model.DomContext)obj.createGenericInstance();
         }
         return ret;
    }

   /**
   * Gets the DomiticSite
   * @return a instance of org.semanticwb.domotic.model.DomiticSite
   */
    public org.semanticwb.domotic.model.DomiticSite getDomiticSite()
    {
        return (org.semanticwb.domotic.model.DomiticSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
