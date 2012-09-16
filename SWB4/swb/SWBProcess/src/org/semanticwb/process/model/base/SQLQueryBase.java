package org.semanticwb.process.model.base;


public abstract class SQLQueryBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticProperty swp_sqlQuery=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sqlQuery");
    public static final org.semanticwb.platform.SemanticClass swp_DBConnection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DBConnection");
    public static final org.semanticwb.platform.SemanticProperty swp_dbConnection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#dbConnection");
    public static final org.semanticwb.platform.SemanticProperty swp_sqlCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#sqlCode");
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
       /**
       * Gets all org.semanticwb.process.model.SQLQuery with a determined DbConnection
       * @param value DbConnection of the type org.semanticwb.process.model.DBConnection
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       * @return Iterator with all the org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueryByDbConnection(org.semanticwb.process.model.DBConnection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_dbConnection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SQLQuery with a determined DbConnection
       * @param value DbConnection of the type org.semanticwb.process.model.DBConnection
       * @return Iterator with all the org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueryByDbConnection(org.semanticwb.process.model.DBConnection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_dbConnection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SQLQuery with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.SQLQuery
       * @return Iterator with all the org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueryByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.SQLQuery with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.SQLQuery
       */

        public static java.util.Iterator<org.semanticwb.process.model.SQLQuery> listSQLQueryByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.SQLQuery> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SQLQueryBase.ClassMgr getSQLQueryClassMgr()
    {
        return new SQLQueryBase.ClassMgr();
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
   /**
   * Sets the value for the property DbConnection
   * @param value DbConnection to set
   */

    public void setDbConnection(org.semanticwb.process.model.DBConnection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_dbConnection, value.getSemanticObject());
        }else
        {
            removeDbConnection();
        }
    }
   /**
   * Remove the value for DbConnection property
   */

    public void removeDbConnection()
    {
        getSemanticObject().removeProperty(swp_dbConnection);
    }

   /**
   * Gets the DbConnection
   * @return a org.semanticwb.process.model.DBConnection
   */
    public org.semanticwb.process.model.DBConnection getDbConnection()
    {
         org.semanticwb.process.model.DBConnection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_dbConnection);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.DBConnection)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Code property
* @return String with the Code
*/
    public String getCode()
    {
        return getSemanticObject().getProperty(swp_sqlCode);
    }

/**
* Sets the Code property
* @param value long with the Code
*/
    public void setCode(String value)
    {
        getSemanticObject().setProperty(swp_sqlCode, value);
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
