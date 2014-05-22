package org.semanticwb.process.model.base;


   /**
   * Grupo de Procesos 
   */
public abstract class ProcessGroupBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableClass
{
   /**
   * Grupo de Procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_ProcessGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessGroup");
    public static final org.semanticwb.platform.SemanticProperty swp_parentGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#parentGroup");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessInv");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessGroup");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessGroup for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroups(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessGroup for all models
       * @return Iterator of org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroups()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup>(it, true);
        }

        public static org.semanticwb.process.model.ProcessGroup createProcessGroup(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessGroup.ClassMgr.createProcessGroup(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return A org.semanticwb.process.model.ProcessGroup
       */
        public static org.semanticwb.process.model.ProcessGroup getProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessGroup)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return A org.semanticwb.process.model.ProcessGroup
       */
        public static org.semanticwb.process.model.ProcessGroup createProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessGroup)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       */
        public static void removeProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessGroup
       * @param id Identifier for org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return true if the org.semanticwb.process.model.ProcessGroup exists, false otherwise
       */

        public static boolean hasProcessGroup(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessGroup(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcessGroup(org.semanticwb.process.model.ProcessGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ProcessGroup
       * @param value ProcessGroup of the type org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcessGroup(org.semanticwb.process.model.ProcessGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ParentGroup
       * @param value ParentGroup of the type org.semanticwb.process.model.ProcessGroup
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByParentGroup(org.semanticwb.process.model.ProcessGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parentGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined ParentGroup
       * @param value ParentGroup of the type org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByParentGroup(org.semanticwb.process.model.ProcessGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parentGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.ProcessGroup
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessGroup with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.ProcessGroup
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessGroup> listProcessGroupByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessGroupBase.ClassMgr getProcessGroupClassMgr()
    {
        return new ProcessGroupBase.ClassMgr();
    }

   /**
   * Constructs a ProcessGroupBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessGroup
   */
    public ProcessGroupBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessGroup
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessGroup
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup> listProcessGroups()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessGroup>(getSemanticObject().listObjectProperties(swp_hasProcessGroup));
    }

   /**
   * Gets true if has a ProcessGroup
   * @param value org.semanticwb.process.model.ProcessGroup to verify
   * @return true if the org.semanticwb.process.model.ProcessGroup exists, false otherwise
   */
    public boolean hasProcessGroup(org.semanticwb.process.model.ProcessGroup value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessGroup,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessGroup
   * @return a org.semanticwb.process.model.ProcessGroup
   */
    public org.semanticwb.process.model.ProcessGroup getProcessGroup()
    {
         org.semanticwb.process.model.ProcessGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessGroup)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ParentGroup
   * @param value ParentGroup to set
   */

    public void setParentGroup(org.semanticwb.process.model.ProcessGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_parentGroup, value.getSemanticObject());
        }else
        {
            removeParentGroup();
        }
    }
   /**
   * Remove the value for ParentGroup property
   */

    public void removeParentGroup()
    {
        getSemanticObject().removeProperty(swp_parentGroup);
    }

   /**
   * Gets the ParentGroup
   * @return a org.semanticwb.process.model.ProcessGroup
   */
    public org.semanticwb.process.model.ProcessGroup getParentGroup()
    {
         org.semanticwb.process.model.ProcessGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_parentGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessGroup)obj.createGenericInstance();
         }
         return ret;
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
   * Gets all the org.semanticwb.process.model.Process
   * @return A GenericIterator with all the org.semanticwb.process.model.Process
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> listProcesses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(getSemanticObject().listObjectProperties(swp_hasProcessInv));
    }

   /**
   * Gets true if has a Process
   * @param value org.semanticwb.process.model.Process to verify
   * @return true if the org.semanticwb.process.model.Process exists, false otherwise
   */
    public boolean hasProcess(org.semanticwb.process.model.Process value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
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
