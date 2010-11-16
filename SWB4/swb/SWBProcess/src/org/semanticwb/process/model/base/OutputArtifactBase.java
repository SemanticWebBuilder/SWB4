package org.semanticwb.process.model.base;


public abstract class OutputArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_OutputArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OutputArtifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#OutputArtifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of OutputArtifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.OutputArtifact for all models
       * @return Iterator of org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact>(it, true);
        }

        public static org.semanticwb.process.model.OutputArtifact createOutputArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.OutputArtifact.ClassMgr.createOutputArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.OutputArtifact
       * @param id Identifier for org.semanticwb.process.model.OutputArtifact
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return A org.semanticwb.process.model.OutputArtifact
       */
        public static org.semanticwb.process.model.OutputArtifact getOutputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OutputArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.OutputArtifact
       * @param id Identifier for org.semanticwb.process.model.OutputArtifact
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return A org.semanticwb.process.model.OutputArtifact
       */
        public static org.semanticwb.process.model.OutputArtifact createOutputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.OutputArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.OutputArtifact
       * @param id Identifier for org.semanticwb.process.model.OutputArtifact
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       */
        public static void removeOutputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.OutputArtifact
       * @param id Identifier for org.semanticwb.process.model.OutputArtifact
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return true if the org.semanticwb.process.model.OutputArtifact exists, false otherwise
       */

        public static boolean hasOutputArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getOutputArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.OutputArtifact
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.OutputArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.OutputArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.OutputArtifact> listOutputArtifactByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.OutputArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a OutputArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the OutputArtifact
   */
    public OutputArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
