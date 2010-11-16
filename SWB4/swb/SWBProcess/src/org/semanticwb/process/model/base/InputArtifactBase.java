package org.semanticwb.process.model.base;


public abstract class InputArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_InputArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InputArtifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#InputArtifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of InputArtifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.InputArtifact for all models
       * @return Iterator of org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact>(it, true);
        }

        public static org.semanticwb.process.model.InputArtifact createInputArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.InputArtifact.ClassMgr.createInputArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.InputArtifact
       * @param id Identifier for org.semanticwb.process.model.InputArtifact
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return A org.semanticwb.process.model.InputArtifact
       */
        public static org.semanticwb.process.model.InputArtifact getInputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.InputArtifact
       * @param id Identifier for org.semanticwb.process.model.InputArtifact
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return A org.semanticwb.process.model.InputArtifact
       */
        public static org.semanticwb.process.model.InputArtifact createInputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.InputArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.InputArtifact
       * @param id Identifier for org.semanticwb.process.model.InputArtifact
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       */
        public static void removeInputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.InputArtifact
       * @param id Identifier for org.semanticwb.process.model.InputArtifact
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return true if the org.semanticwb.process.model.InputArtifact exists, false otherwise
       */

        public static boolean hasInputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInputArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.InputArtifact
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.InputArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.InputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.InputArtifact> listInputArtifactByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.InputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a InputArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the InputArtifact
   */
    public InputArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
