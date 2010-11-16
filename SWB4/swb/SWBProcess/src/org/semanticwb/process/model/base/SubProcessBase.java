package org.semanticwb.process.model.base;


public abstract class SubProcessBase extends org.semanticwb.process.model.Activity implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.TemplateRefable,org.semanticwb.process.model.Containerable
{
    public static final org.semanticwb.platform.SemanticClass swp_SubProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SubProcess");

    public static class ClassMgr
    {
       /**
       * Returns a list of SubProcess for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcesses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SubProcess for all models
       * @return Iterator of org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcesses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess>(it, true);
        }

        public static org.semanticwb.process.model.SubProcess createSubProcess(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SubProcess.ClassMgr.createSubProcess(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SubProcess
       * @param id Identifier for org.semanticwb.process.model.SubProcess
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return A org.semanticwb.process.model.SubProcess
       */
        public static org.semanticwb.process.model.SubProcess getSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcess)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SubProcess
       * @param id Identifier for org.semanticwb.process.model.SubProcess
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return A org.semanticwb.process.model.SubProcess
       */
        public static org.semanticwb.process.model.SubProcess createSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SubProcess)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SubProcess
       * @param id Identifier for org.semanticwb.process.model.SubProcess
       * @param model Model of the org.semanticwb.process.model.SubProcess
       */
        public static void removeSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SubProcess
       * @param id Identifier for org.semanticwb.process.model.SubProcess
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return true if the org.semanticwb.process.model.SubProcess exists, false otherwise
       */

        public static boolean hasSubProcess(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSubProcess(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined FlowObjectInstance
       * @param value FlowObjectInstance of the type org.semanticwb.process.model.FlowNodeInstance
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByFlowObjectInstance(org.semanticwb.process.model.FlowNodeInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasFlowNodeInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByContained(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Contained
       * @param value Contained of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByContained(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasContainedInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.SubProcess
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SubProcess with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.SubProcess
       */

        public static java.util.Iterator<org.semanticwb.process.model.SubProcess> listSubProcessByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SubProcess> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a SubProcessBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SubProcess
   */
    public SubProcessBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
