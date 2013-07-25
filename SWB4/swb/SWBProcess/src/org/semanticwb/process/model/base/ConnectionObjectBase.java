package org.semanticwb.process.model.base;


public abstract class ConnectionObjectBase extends org.semanticwb.process.model.ProcessElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swp_connectionPoints=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#connectionPoints");
    public static final org.semanticwb.platform.SemanticClass swp_GraphicalElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GraphicalElement");
    public static final org.semanticwb.platform.SemanticProperty swp_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#source");
    public static final org.semanticwb.platform.SemanticProperty swp_target=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#target");
    public static final org.semanticwb.platform.SemanticClass swp_ConnectionObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ConnectionObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of ConnectionObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ConnectionObject for all models
       * @return Iterator of org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ConnectionObject
       * @param id Identifier for org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return A org.semanticwb.process.model.ConnectionObject
       */
        public static org.semanticwb.process.model.ConnectionObject getConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConnectionObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ConnectionObject
       * @param id Identifier for org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return A org.semanticwb.process.model.ConnectionObject
       */
        public static org.semanticwb.process.model.ConnectionObject createConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ConnectionObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ConnectionObject
       * @param id Identifier for org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       */
        public static void removeConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ConnectionObject
       * @param id Identifier for org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return true if the org.semanticwb.process.model.ConnectionObject exists, false otherwise
       */

        public static boolean hasConnectionObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getConnectionObject(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.ConnectionObject with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.ConnectionObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.ConnectionObject> listConnectionObjectByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ConnectionObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ConnectionObjectBase.ClassMgr getConnectionObjectClassMgr()
    {
        return new ConnectionObjectBase.ClassMgr();
    }

   /**
   * Constructs a ConnectionObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ConnectionObject
   */
    public ConnectionObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ConnectionPoints property
* @return String with the ConnectionPoints
*/
    public String getConnectionPoints()
    {
        return getSemanticObject().getProperty(swp_connectionPoints);
    }

/**
* Sets the ConnectionPoints property
* @param value long with the ConnectionPoints
*/
    public void setConnectionPoints(String value)
    {
        getSemanticObject().setProperty(swp_connectionPoints, value);
    }
   /**
   * Sets the value for the property Source
   * @param value Source to set
   */

    public void setSource(org.semanticwb.process.model.GraphicalElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_source, value.getSemanticObject());
        }else
        {
            removeSource();
        }
    }
   /**
   * Remove the value for Source property
   */

    public void removeSource()
    {
        getSemanticObject().removeProperty(swp_source);
    }

   /**
   * Gets the Source
   * @return a org.semanticwb.process.model.GraphicalElement
   */
    public org.semanticwb.process.model.GraphicalElement getSource()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_source);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Target
   * @param value Target to set
   */

    public void setTarget(org.semanticwb.process.model.GraphicalElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swp_target, value.getSemanticObject());
        }else
        {
            removeTarget();
        }
    }
   /**
   * Remove the value for Target property
   */

    public void removeTarget()
    {
        getSemanticObject().removeProperty(swp_target);
    }

   /**
   * Gets the Target
   * @return a org.semanticwb.process.model.GraphicalElement
   */
    public org.semanticwb.process.model.GraphicalElement getTarget()
    {
         org.semanticwb.process.model.GraphicalElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_target);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.GraphicalElement)obj.createGenericInstance();
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
