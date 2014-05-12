package org.semanticwb.bsc.accessory.base;


public abstract class GrapherBase extends org.semanticwb.bsc.accessory.BSCAccessory implements org.semanticwb.model.UserGroupable,org.semanticwb.model.Filterable,org.semanticwb.bsc.Help,org.semanticwb.model.Undeleteable,org.semanticwb.model.Roleable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Series=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Series");
    public static final org.semanticwb.platform.SemanticProperty bsc_serieGraph2=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#serieGraph2");
    public static final org.semanticwb.platform.SemanticProperty bsc_titleY=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#titleY");
    public static final org.semanticwb.platform.SemanticProperty bsc_titleX=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#titleX");
    public static final org.semanticwb.platform.SemanticProperty bsc_serieGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#serieGraph");
    public static final org.semanticwb.platform.SemanticProperty bsc_titleGraph=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#titleGraph");
    public static final org.semanticwb.platform.SemanticClass bsc_Grapher=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Grapher");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Grapher");

    public static class ClassMgr
    {
       /**
       * Returns a list of Grapher for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGraphers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.accessory.Grapher for all models
       * @return Iterator of org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGraphers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher>(it, true);
        }

        public static org.semanticwb.bsc.accessory.Grapher createGrapher(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.accessory.Grapher.ClassMgr.createGrapher(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.accessory.Grapher
       * @param id Identifier for org.semanticwb.bsc.accessory.Grapher
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return A org.semanticwb.bsc.accessory.Grapher
       */
        public static org.semanticwb.bsc.accessory.Grapher getGrapher(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Grapher)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.accessory.Grapher
       * @param id Identifier for org.semanticwb.bsc.accessory.Grapher
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return A org.semanticwb.bsc.accessory.Grapher
       */
        public static org.semanticwb.bsc.accessory.Grapher createGrapher(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.accessory.Grapher)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.accessory.Grapher
       * @param id Identifier for org.semanticwb.bsc.accessory.Grapher
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       */
        public static void removeGrapher(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.accessory.Grapher
       * @param id Identifier for org.semanticwb.bsc.accessory.Grapher
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return true if the org.semanticwb.bsc.accessory.Grapher exists, false otherwise
       */

        public static boolean hasGrapher(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGrapher(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined SerieGraph2
       * @param value SerieGraph2 of the type org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherBySerieGraph2(org.semanticwb.bsc.tracing.Series value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_serieGraph2, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined SerieGraph2
       * @param value SerieGraph2 of the type org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherBySerieGraph2(org.semanticwb.bsc.tracing.Series value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_serieGraph2,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByUserGroup(org.semanticwb.model.UserGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined UserGroup
       * @param value UserGroup of the type org.semanticwb.model.UserGroup
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByUserGroup(org.semanticwb.model.UserGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroup,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined SerieGraph
       * @param value SerieGraph of the type org.semanticwb.bsc.tracing.Series
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherBySerieGraph(org.semanticwb.bsc.tracing.Series value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_serieGraph, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined SerieGraph
       * @param value SerieGraph of the type org.semanticwb.bsc.tracing.Series
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherBySerieGraph(org.semanticwb.bsc.tracing.Series value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_serieGraph,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.bsc.accessory.Grapher
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.accessory.Grapher with a determined Role
       * @param value Role of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.bsc.accessory.Grapher
       */

        public static java.util.Iterator<org.semanticwb.bsc.accessory.Grapher> listGrapherByRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.accessory.Grapher> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static GrapherBase.ClassMgr getGrapherClassMgr()
    {
        return new GrapherBase.ClassMgr();
    }

   /**
   * Constructs a GrapherBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Grapher
   */
    public GrapherBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SerieGraph2
   * @param value SerieGraph2 to set
   */

    public void setSerieGraph2(org.semanticwb.bsc.tracing.Series value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_serieGraph2, value.getSemanticObject());
        }else
        {
            removeSerieGraph2();
        }
    }
   /**
   * Remove the value for SerieGraph2 property
   */

    public void removeSerieGraph2()
    {
        getSemanticObject().removeProperty(bsc_serieGraph2);
    }

   /**
   * Gets the SerieGraph2
   * @return a org.semanticwb.bsc.tracing.Series
   */
    public org.semanticwb.bsc.tracing.Series getSerieGraph2()
    {
         org.semanticwb.bsc.tracing.Series ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_serieGraph2);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Series)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the TitleY property
* @return String with the TitleY
*/
    public String getTitleY()
    {
        return getSemanticObject().getProperty(bsc_titleY);
    }

/**
* Sets the TitleY property
* @param value long with the TitleY
*/
    public void setTitleY(String value)
    {
        getSemanticObject().setProperty(bsc_titleY, value);
    }

/**
* Gets the TitleX property
* @return String with the TitleX
*/
    public String getTitleX()
    {
        return getSemanticObject().getProperty(bsc_titleX);
    }

/**
* Sets the TitleX property
* @param value long with the TitleX
*/
    public void setTitleX(String value)
    {
        getSemanticObject().setProperty(bsc_titleX, value);
    }
   /**
   * Sets the value for the property SerieGraph
   * @param value SerieGraph to set
   */

    public void setSerieGraph(org.semanticwb.bsc.tracing.Series value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(bsc_serieGraph, value.getSemanticObject());
        }else
        {
            removeSerieGraph();
        }
    }
   /**
   * Remove the value for SerieGraph property
   */

    public void removeSerieGraph()
    {
        getSemanticObject().removeProperty(bsc_serieGraph);
    }

   /**
   * Gets the SerieGraph
   * @return a org.semanticwb.bsc.tracing.Series
   */
    public org.semanticwb.bsc.tracing.Series getSerieGraph()
    {
         org.semanticwb.bsc.tracing.Series ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_serieGraph);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.tracing.Series)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the TitleGraph property
* @return String with the TitleGraph
*/
    public String getTitleGraph()
    {
        return getSemanticObject().getProperty(bsc_titleGraph);
    }

/**
* Sets the TitleGraph property
* @param value long with the TitleGraph
*/
    public void setTitleGraph(String value)
    {
        getSemanticObject().setProperty(bsc_titleGraph, value);
    }
}
