package org.semanticwb.process.model.base;


public abstract class ProcessBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Iconable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Hitable,org.semanticwb.process.model.Containerable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Expirable,org.semanticwb.model.TemplateRefable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessWebPage");
    public static final org.semanticwb.platform.SemanticProperty swp_processWebPageInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processWebPageInv");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessInstance");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessInstanceInv");
   /**
   * Grupo de Procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_processGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#processGroup");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");

    public static class ClassMgr
    {
       /**
       * Returns a list of Process for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Process for all models
       * @return Iterator of org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(it, true);
        }

        public static org.semanticwb.process.model.Process createProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Process.ClassMgr.createProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return A org.semanticwb.process.model.Process
       */
        public static org.semanticwb.process.model.Process getProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return A org.semanticwb.process.model.Process
       */
        public static org.semanticwb.process.model.Process createProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Process)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       */
        public static void removeProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Process
       * @param id Identifier for org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.Process
       * @return true if the org.semanticwb.process.model.Process exists, false otherwise
       */

        public static boolean hasProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessWebPage
       * @param value ProcessWebPage of the type org.semanticwb.process.model.ProcessWebPage
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessWebPage
       * @param value ProcessWebPage of the type org.semanticwb.process.model.ProcessWebPage
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processWebPageInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessInstance
       * @param value ProcessInstance of the type org.semanticwb.process.model.ProcessInstance
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessInstance
       * @param value ProcessInstance of the type org.semanticwb.process.model.ProcessInstance
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessInstance(org.semanticwb.process.model.ProcessInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessGroup(org.semanticwb.process.model.ProcessGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_processGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByProcessGroup(org.semanticwb.process.model.ProcessGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_processGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Process with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Process
       */

        public static java.util.Iterator<org.semanticwb.process.model.Process> listProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a ProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Process
   */
    public ProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ProcessWebPage
   * @param value ProcessWebPage to set
   */

    public void setProcessWebPage(org.semanticwb.process.model.ProcessWebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processWebPageInv, value.getSemanticObject());
        }else
        {
            removeProcessWebPage();
        }
    }
   /**
   * Remove the value for ProcessWebPage property
   */

    public void removeProcessWebPage()
    {
        getSemanticObject().removeProperty(swp_processWebPageInv);
    }

   /**
   * Gets the ProcessWebPage
   * @return a org.semanticwb.process.model.ProcessWebPage
   */
    public org.semanticwb.process.model.ProcessWebPage getProcessWebPage()
    {
         org.semanticwb.process.model.ProcessWebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processWebPageInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessWebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance> listProcessInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessInstance>(getSemanticObject().listObjectProperties(swp_hasProcessInstanceInv));
    }

   /**
   * Gets true if has a ProcessInstance
   * @param value org.semanticwb.process.model.ProcessInstance to verify
   * @return true if the org.semanticwb.process.model.ProcessInstance exists, false otherwise
   */
    public boolean hasProcessInstance(org.semanticwb.process.model.ProcessInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessInstance
   * @return a org.semanticwb.process.model.ProcessInstance
   */
    public org.semanticwb.process.model.ProcessInstance getProcessInstance()
    {
         org.semanticwb.process.model.ProcessInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessGroup
   * @param value ProcessGroup to set
   */

    public void setProcessGroup(org.semanticwb.process.model.ProcessGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_processGroup, value.getSemanticObject());
        }else
        {
            removeProcessGroup();
        }
    }
   /**
   * Remove the value for ProcessGroup property
   */

    public void removeProcessGroup()
    {
        getSemanticObject().removeProperty(swp_processGroup);
    }

   /**
   * Gets the ProcessGroup
   * @return a org.semanticwb.process.model.ProcessGroup
   */
    public org.semanticwb.process.model.ProcessGroup getProcessGroup()
    {
         org.semanticwb.process.model.ProcessGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_processGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Hits property
* @return long with the Hits
*/
    public long getHits()
    {
        //Override this method in Process object
        return getSemanticObject().getLongProperty(swb_hits,false);
    }

/**
* Sets the Hits property
* @param value long with the Hits
*/
    public void setHits(long value)
    {
        //Override this method in Process object
        getSemanticObject().setLongProperty(swb_hits, value,false);
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
   * Gets all the org.semanticwb.model.TemplateRef
   * @return A GenericIterator with all the org.semanticwb.model.TemplateRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(swb_hasTemplateRef));
    }

   /**
   * Gets true if has a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to verify
   * @return true if the org.semanticwb.model.TemplateRef exists, false otherwise
   */
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets all the TemplateRefs inherits
   * @return A GenericIterator with all the org.semanticwb.model.TemplateRef
   */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listInheritProperties(swb_hasTemplateRef));
    }
   /**
   * Adds a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to add
   */

    public void addTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplateRef, value.getSemanticObject());
    }
   /**
   * Removes all the TemplateRef
   */

    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(swb_hasTemplateRef);
    }
   /**
   * Removes a TemplateRef
   * @param value org.semanticwb.model.TemplateRef to remove
   */

    public void removeTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplateRef,value.getSemanticObject());
    }

   /**
   * Gets the TemplateRef
   * @return a org.semanticwb.model.TemplateRef
   */
    public org.semanticwb.model.TemplateRef getTemplateRef()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.model.CalendarRef
   * @return A GenericIterator with all the org.semanticwb.model.CalendarRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

   /**
   * Gets true if has a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to verify
   * @return true if the org.semanticwb.model.CalendarRef exists, false otherwise
   */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to add
   */

    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }
   /**
   * Removes all the CalendarRef
   */

    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }
   /**
   * Removes a CalendarRef
   * @param value org.semanticwb.model.CalendarRef to remove
   */

    public void removeCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,value.getSemanticObject());
    }

   /**
   * Gets the CalendarRef
   * @return a org.semanticwb.model.CalendarRef
   */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.GraphicalElement
   * @return A GenericIterator with all the org.semanticwb.process.model.GraphicalElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement> listContaineds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GraphicalElement>(getSemanticObject().listObjectProperties(swp_hasContainedInv));
    }

   /**
   * Gets true if has a Contained
   * @param value org.semanticwb.process.model.GraphicalElement to verify
   * @return true if the org.semanticwb.process.model.GraphicalElement exists, false otherwise
   */
    public boolean hasContained(org.semanticwb.process.model.GraphicalElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasContainedInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Contained
   * @return a org.semanticwb.process.model.GraphicalElement
   */
    public org.semanticwb.process.model.GraphicalElement getContained()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasContainedInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Expiration property
* @return java.util.Date with the Expiration
*/
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

/**
* Sets the Expiration property
* @param value long with the Expiration
*/
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

/**
* Gets the IconClass property
* @return String with the IconClass
*/
    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

/**
* Sets the IconClass property
* @param value long with the IconClass
*/
    public void setIconClass(String value)
    {
        getSemanticObject().setProperty(swb_iconClass, value);
    }

/**
* Gets the MaxHits property
* @return long with the MaxHits
*/
    public long getMaxHits()
    {
        return getSemanticObject().getLongProperty(swb_maxHits);
    }

/**
* Sets the MaxHits property
* @param value long with the MaxHits
*/
    public void setMaxHits(long value)
    {
        getSemanticObject().setLongProperty(swb_maxHits, value);
    }

    public org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject> listProcessClasses()
    {
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(swp_hasProcessClass.getRDFProperty());
        return new org.semanticwb.platform.SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().addObjectProperty(swp_hasProcessClass, value);
    }

    public void removeAllProcessClass()
    {
        getSemanticObject().removeProperty(swp_hasProcessClass);
    }

    public void removeProcessClass(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().removeObjectProperty(swp_hasProcessClass,value);
    }

/**
* Gets the ProcessClass property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getProcessClass()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swp_hasProcessClass);
         return ret;
    }

   /**
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
