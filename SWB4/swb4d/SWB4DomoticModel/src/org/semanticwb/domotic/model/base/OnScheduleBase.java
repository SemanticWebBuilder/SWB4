package org.semanticwb.domotic.model.base;


public abstract class OnScheduleBase extends org.semanticwb.domotic.model.DomEvent implements org.semanticwb.model.Descriptiveable
{
   /**
   * Objeto de calendarización que permite configurar una página o un recurso para desplegarse en cierta fecha, entre un rango de fechas o incluso en periodos de tiempo definidos.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Calendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Calendar");
    public static final org.semanticwb.platform.SemanticProperty swb4d_schedule4Event=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#schedule4Event");
    public static final org.semanticwb.platform.SemanticProperty swb4d_scheduleStat=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/domotic#scheduleStat");
    public static final org.semanticwb.platform.SemanticClass swb4d_OnSchedule=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSchedule");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/domotic#OnSchedule");

    public static class ClassMgr
    {
       /**
       * Returns a list of OnSchedule for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnSchedules(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.domotic.model.OnSchedule for all models
       * @return Iterator of org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnSchedules()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule>(it, true);
        }

        public static org.semanticwb.domotic.model.OnSchedule createOnSchedule(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.domotic.model.OnSchedule.ClassMgr.createOnSchedule(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.domotic.model.OnSchedule
       * @param id Identifier for org.semanticwb.domotic.model.OnSchedule
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return A org.semanticwb.domotic.model.OnSchedule
       */
        public static org.semanticwb.domotic.model.OnSchedule getOnSchedule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnSchedule)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.domotic.model.OnSchedule
       * @param id Identifier for org.semanticwb.domotic.model.OnSchedule
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return A org.semanticwb.domotic.model.OnSchedule
       */
        public static org.semanticwb.domotic.model.OnSchedule createOnSchedule(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.domotic.model.OnSchedule)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.domotic.model.OnSchedule
       * @param id Identifier for org.semanticwb.domotic.model.OnSchedule
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       */
        public static void removeOnSchedule(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.domotic.model.OnSchedule
       * @param id Identifier for org.semanticwb.domotic.model.OnSchedule
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return true if the org.semanticwb.domotic.model.OnSchedule exists, false otherwise
       */

        public static boolean hasOnSchedule(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOnSchedule(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined Calendar
       * @param value Calendar of the type org.semanticwb.model.Calendar
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByCalendar(org.semanticwb.model.Calendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_schedule4Event, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined Calendar
       * @param value Calendar of the type org.semanticwb.model.Calendar
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByCalendar(org.semanticwb.model.Calendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_schedule4Event,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByDomAction(org.semanticwb.domotic.model.DomAction value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined DomAction
       * @param value DomAction of the type org.semanticwb.domotic.model.DomAction
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByDomAction(org.semanticwb.domotic.model.DomAction value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_hasDomAction,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @param model Model of the org.semanticwb.domotic.model.OnSchedule
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByDomContext(org.semanticwb.domotic.model.DomContext value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.domotic.model.OnSchedule with a determined DomContext
       * @param value DomContext of the type org.semanticwb.domotic.model.DomContext
       * @return Iterator with all the org.semanticwb.domotic.model.OnSchedule
       */

        public static java.util.Iterator<org.semanticwb.domotic.model.OnSchedule> listOnScheduleByDomContext(org.semanticwb.domotic.model.DomContext value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.domotic.model.OnSchedule> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb4d_domEventContext,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static OnScheduleBase.ClassMgr getOnScheduleClassMgr()
    {
        return new OnScheduleBase.ClassMgr();
    }

   /**
   * Constructs a OnScheduleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OnSchedule
   */
    public OnScheduleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Calendar
   * @param value Calendar to set
   */

    public void setCalendar(org.semanticwb.model.Calendar value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb4d_schedule4Event, value.getSemanticObject());
        }else
        {
            removeCalendar();
        }
    }
   /**
   * Remove the value for Calendar property
   */

    public void removeCalendar()
    {
        getSemanticObject().removeProperty(swb4d_schedule4Event);
    }

   /**
   * Gets the Calendar
   * @return a org.semanticwb.model.Calendar
   */
    public org.semanticwb.model.Calendar getCalendar()
    {
         org.semanticwb.model.Calendar ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb4d_schedule4Event);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Calendar)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the ScheduleStat property
* @return String with the ScheduleStat
*/
    public String getScheduleStat()
    {
        return getSemanticObject().getProperty(swb4d_scheduleStat);
    }

/**
* Sets the ScheduleStat property
* @param value long with the ScheduleStat
*/
    public void setScheduleStat(String value)
    {
        getSemanticObject().setProperty(swb4d_scheduleStat, value);
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
