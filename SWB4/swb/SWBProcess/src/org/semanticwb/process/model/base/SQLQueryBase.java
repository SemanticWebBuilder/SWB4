package org.semanticwb.process.model.base;


public abstract class SQLQueryBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticProperty swp_sqlQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sqlQuery");
    public static final org.semanticwb.platform.SemanticClass swp_SQLQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SQLQuery");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#SQLQuery");

    public static class ClassMgr
    {
       /**
       * Returns a list of SQLQuery for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueries(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.SQLQuery for all models
       * @return Iterator of org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueries()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery>(it, true);
        }

        public static org.semanticwb.process.model.SQLQuery createSQLQuery(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.SQLQuery.ClassMgr.createSQLQuery(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.SQLQuery
       * @param id Identifier for org.semanticwb.process.model.SQLQuery
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       * @return A org.semanticwb.process.model.SQLQuery
       */
        public static org.semanticwb.process.model.SQLQuery getSQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SQLQuery)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.SQLQuery
       * @param id Identifier for org.semanticwb.process.model.SQLQuery
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       * @return A org.semanticwb.process.model.SQLQuery
       */
        public static org.semanticwb.process.model.SQLQuery createSQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.SQLQuery)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.SQLQuery
       * @param id Identifier for org.semanticwb.process.model.SQLQuery
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       */
        public static void removeSQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.SQLQuery
       * @param id Identifier for org.semanticwb.process.model.SQLQuery
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       * @return true if the org.semanticwb.process.model.SQLQuery exists, false otherwise
       */

        public static boolean hasSQLQuery(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSQLQuery(id, model)!=null);
        }
    }

   /**
   * Constructs a SQLQueryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SQLQuery
   */
    public SQLQueryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Query property
* @return String with the Query
*/
    public String getQuery()
    {
        return getSemanticObject().getProperty(swp_sqlQuery);
    }

/**
* Sets the Query property
* @param value long with the Query
*/
    public void setQuery(String value)
    {
        getSemanticObject().setProperty(swp_sqlQuery, value);
    }
}
