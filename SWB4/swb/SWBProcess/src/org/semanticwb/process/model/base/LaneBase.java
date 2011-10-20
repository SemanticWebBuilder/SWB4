package org.semanticwb.process.model.base;


public abstract class LaneBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_Lane=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Lane");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Lane");

    public static class ClassMgr
    {
       /**
       * Returns a list of Lane for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Lane for all models
       * @return Iterator of org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLanes()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane>(it, true);
        }

        public static org.semanticwb.process.model.Lane createLane(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Lane.ClassMgr.createLane(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.Lane
       * @param id Identifier for org.semanticwb.process.model.Lane
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return A org.semanticwb.process.model.Lane
       */
        public static org.semanticwb.process.model.Lane getLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Lane
       * @param id Identifier for org.semanticwb.process.model.Lane
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return A org.semanticwb.process.model.Lane
       */
        public static org.semanticwb.process.model.Lane createLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Lane)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Lane
       * @param id Identifier for org.semanticwb.process.model.Lane
       * @param model Model of the org.semanticwb.process.model.Lane
       */
        public static void removeLane(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Lane
       * @param id Identifier for org.semanticwb.process.model.Lane
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return true if the org.semanticwb.process.model.Lane exists, false otherwise
       */

        public static boolean hasLane(String id, org.semanticwb.model.SWBModel model)
        {
            return (getLane(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Lane
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Lane with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Lane
       */

        public static java.util.Iterator<org.semanticwb.process.model.Lane> listLaneByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Lane> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a LaneBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Lane
   */
    public LaneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
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
