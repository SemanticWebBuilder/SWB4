package org.semanticwb.process.model.base;


public abstract class SparQLQueryBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticProperty swp_sparQLQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sparQLQuery");
    public static final org.semanticwb.platform.SemanticProperty swp_sparQLCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sparQLCode");
    public static final org.semanticwb.platform.SemanticClass swp_SparQLQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SparQLQuery");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SparQLQuery");

    public static class ClassMgr
    {
       /**
       * Returns a list of SparQLQuery for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SparQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SparQLQuery> listSparQLQueries(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SparQLQuery>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SparQLQuery for all models
       * @return Iterator of org.semanticwb.process.model.SparQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SparQLQuery> listSparQLQueries()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SparQLQuery>(it, true);
        }

        public static org.semanticwb.process.model.SparQLQuery createSparQLQuery(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SparQLQuery.ClassMgr.createSparQLQuery(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SparQLQuery
       * @param id Identifier for org.semanticwb.process.model.SparQLQuery
       * @param model Model of the org.semanticwb.process.model.SparQLQuery
       * @return A org.semanticwb.process.model.SparQLQuery
       */
        public static org.semanticwb.process.model.SparQLQuery getSparQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SparQLQuery)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SparQLQuery
       * @param id Identifier for org.semanticwb.process.model.SparQLQuery
       * @param model Model of the org.semanticwb.process.model.SparQLQuery
       * @return A org.semanticwb.process.model.SparQLQuery
       */
        public static org.semanticwb.process.model.SparQLQuery createSparQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SparQLQuery)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SparQLQuery
       * @param id Identifier for org.semanticwb.process.model.SparQLQuery
       * @param model Model of the org.semanticwb.process.model.SparQLQuery
       */
        public static void removeSparQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SparQLQuery
       * @param id Identifier for org.semanticwb.process.model.SparQLQuery
       * @param model Model of the org.semanticwb.process.model.SparQLQuery
       * @return true if the org.semanticwb.process.model.SparQLQuery exists, false otherwise
       */

        public static boolean hasSparQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSparQLQuery(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.SparQLQuery with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.SparQLQuery
       * @return Iterator with all the org.semanticwb.process.model.SparQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SparQLQuery> listSparQLQueryByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SparQLQuery> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SparQLQuery with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.SparQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SparQLQuery> listSparQLQueryByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SparQLQuery> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SparQLQueryBase.ClassMgr getSparQLQueryClassMgr()
    {
        return new SparQLQueryBase.ClassMgr();
    }

   /**
   * Constructs a SparQLQueryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SparQLQuery
   */
    public SparQLQueryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Query property
* @return String with the Query
*/
    public String getQuery()
    {
        return getSemanticObject().getProperty(swp_sparQLQuery);
    }

/**
* Sets the Query property
* @param value long with the Query
*/
    public void setQuery(String value)
    {
        getSemanticObject().setProperty(swp_sparQLQuery, value);
    }

/**
* Gets the Code property
* @return String with the Code
*/
    public String getCode()
    {
        return getSemanticObject().getProperty(swp_sparQLCode);
    }

/**
* Sets the Code property
* @param value long with the Code
*/
    public void setCode(String value)
    {
        getSemanticObject().setProperty(swp_sparQLCode, value);
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
