package org.semanticwb.bsc.tracing.base;


public abstract class PriorityBase extends org.semanticwb.bsc.tracing.BSCTracing implements org.semanticwb.bsc.Machinable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable
{
    public static final org.semanticwb.platform.SemanticProperty bsc_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#index");
    public static final org.semanticwb.platform.SemanticClass bsc_Priority=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Priority");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Priority");

    public static class ClassMgr
    {
       /**
       * Returns a list of Priority for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.tracing.Priority for all models
       * @return Iterator of org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority>(it, true);
        }

        public static org.semanticwb.bsc.tracing.Priority createPriority(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.tracing.Priority.ClassMgr.createPriority(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.tracing.Priority
       * @param id Identifier for org.semanticwb.bsc.tracing.Priority
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return A org.semanticwb.bsc.tracing.Priority
       */
        public static org.semanticwb.bsc.tracing.Priority getPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Priority)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.tracing.Priority
       * @param id Identifier for org.semanticwb.bsc.tracing.Priority
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return A org.semanticwb.bsc.tracing.Priority
       */
        public static org.semanticwb.bsc.tracing.Priority createPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.tracing.Priority)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.tracing.Priority
       * @param id Identifier for org.semanticwb.bsc.tracing.Priority
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       */
        public static void removePriority(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.tracing.Priority
       * @param id Identifier for org.semanticwb.bsc.tracing.Priority
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return true if the org.semanticwb.bsc.tracing.Priority exists, false otherwise
       */

        public static boolean hasPriority(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPriority(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByPrevius(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_previus, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Previus
       * @param value Previus of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByPrevius(org.semanticwb.bsc.Machinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_previus,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByNext(org.semanticwb.bsc.Machinable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_next, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Next
       * @param value Next of the type org.semanticwb.bsc.Machinable
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByNext(org.semanticwb.bsc.Machinable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_next,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.tracing.Priority
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.tracing.Priority with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.tracing.Priority
       */

        public static java.util.Iterator<org.semanticwb.bsc.tracing.Priority> listPriorityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.tracing.Priority> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PriorityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Priority
   */
    public PriorityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(bsc_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(bsc_index, value);
    }
   /**
   * Sets the value for the property Previus
   * @param value Previus to set
   */

    public void setPrevius(org.semanticwb.bsc.Machinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_previus, value.getSemanticObject());
        }else
        {
            removePrevius();
        }
    }
   /**
   * Remove the value for Previus property
   */

    public void removePrevius()
    {
        getSemanticObject().removeProperty(bsc_previus);
    }

   /**
   * Gets the Previus
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getPrevius()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_previus);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Next
   * @param value Next to set
   */

    public void setNext(org.semanticwb.bsc.Machinable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_next, value.getSemanticObject());
        }else
        {
            removeNext();
        }
    }
   /**
   * Remove the value for Next property
   */

    public void removeNext()
    {
        getSemanticObject().removeProperty(bsc_next);
    }

   /**
   * Gets the Next
   * @return a org.semanticwb.bsc.Machinable
   */
    public org.semanticwb.bsc.Machinable getNext()
    {
         org.semanticwb.bsc.Machinable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_next);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.Machinable)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Orden property
* @return int with the Orden
*/
    public int getOrden()
    {
        return getSemanticObject().getIntProperty(bsc_orden);
    }

/**
* Sets the Orden property
* @param value long with the Orden
*/
    public void setOrden(int value)
    {
        getSemanticObject().setIntProperty(bsc_orden, value);
    }
}
