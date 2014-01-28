package org.semanticwb.process.model.base;


public abstract class ProcessPeriodBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.XMLable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriodRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriodRef");
    public static final org.semanticwb.platform.SemanticProperty swp_hasProcessPeriodRefInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasProcessPeriodRefInv");
    public static final org.semanticwb.platform.SemanticClass swp_ProcessPeriod=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessPeriod");

    public static class ClassMgr
    {
       /**
       * Returns a list of ProcessPeriod for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriods(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ProcessPeriod for all models
       * @return Iterator of org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriods()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod>(it, true);
        }

        public static org.semanticwb.process.model.ProcessPeriod createProcessPeriod(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.ProcessPeriod.ClassMgr.createProcessPeriod(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.ProcessPeriod
       * @param id Identifier for org.semanticwb.process.model.ProcessPeriod
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return A org.semanticwb.process.model.ProcessPeriod
       */
        public static org.semanticwb.process.model.ProcessPeriod getProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriod)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ProcessPeriod
       * @param id Identifier for org.semanticwb.process.model.ProcessPeriod
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return A org.semanticwb.process.model.ProcessPeriod
       */
        public static org.semanticwb.process.model.ProcessPeriod createProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ProcessPeriod)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ProcessPeriod
       * @param id Identifier for org.semanticwb.process.model.ProcessPeriod
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       */
        public static void removeProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ProcessPeriod
       * @param id Identifier for org.semanticwb.process.model.ProcessPeriod
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return true if the org.semanticwb.process.model.ProcessPeriod exists, false otherwise
       */

        public static boolean hasProcessPeriod(String id, org.semanticwb.model.SWBModel model)
        {
            return (getProcessPeriod(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined ProcessPeriodRefInv
       * @param value ProcessPeriodRefInv of the type org.semanticwb.process.model.ProcessPeriodRef
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByProcessPeriodRefInv(org.semanticwb.process.model.ProcessPeriodRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined ProcessPeriodRefInv
       * @param value ProcessPeriodRefInv of the type org.semanticwb.process.model.ProcessPeriodRef
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByProcessPeriodRefInv(org.semanticwb.process.model.ProcessPeriodRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasProcessPeriodRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ProcessPeriod
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ProcessPeriod with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ProcessPeriod
       */

        public static java.util.Iterator<org.semanticwb.process.model.ProcessPeriod> listProcessPeriodByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriod> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ProcessPeriodBase.ClassMgr getProcessPeriodClassMgr()
    {
        return new ProcessPeriodBase.ClassMgr();
    }

   /**
   * Constructs a ProcessPeriodBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ProcessPeriod
   */
    public ProcessPeriodBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessPeriodRef
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessPeriodRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef> listProcessPeriodRefInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessPeriodRef>(getSemanticObject().listObjectProperties(swp_hasProcessPeriodRefInv));
    }

   /**
   * Gets true if has a ProcessPeriodRefInv
   * @param value org.semanticwb.process.model.ProcessPeriodRef to verify
   * @return true if the org.semanticwb.process.model.ProcessPeriodRef exists, false otherwise
   */
    public boolean hasProcessPeriodRefInv(org.semanticwb.process.model.ProcessPeriodRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasProcessPeriodRefInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the ProcessPeriodRefInv
   * @return a org.semanticwb.process.model.ProcessPeriodRef
   */
    public org.semanticwb.process.model.ProcessPeriodRef getProcessPeriodRefInv()
    {
         org.semanticwb.process.model.ProcessPeriodRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasProcessPeriodRefInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessPeriodRef)obj.createGenericInstance();
         }
         return ret;
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
   * Gets the ProcessSite
   * @return a instance of org.semanticwb.process.model.ProcessSite
   */
    public org.semanticwb.process.model.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.model.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
