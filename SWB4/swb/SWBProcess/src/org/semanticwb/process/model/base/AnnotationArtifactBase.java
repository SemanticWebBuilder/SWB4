package org.semanticwb.process.model.base;


public abstract class AnnotationArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swp_AnnotationArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AnnotationArtifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#AnnotationArtifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of AnnotationArtifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.AnnotationArtifact for all models
       * @return Iterator of org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact>(it, true);
        }

        public static org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.AnnotationArtifact.ClassMgr.createAnnotationArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.AnnotationArtifact
       * @param id Identifier for org.semanticwb.process.model.AnnotationArtifact
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return A org.semanticwb.process.model.AnnotationArtifact
       */
        public static org.semanticwb.process.model.AnnotationArtifact getAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AnnotationArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.AnnotationArtifact
       * @param id Identifier for org.semanticwb.process.model.AnnotationArtifact
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return A org.semanticwb.process.model.AnnotationArtifact
       */
        public static org.semanticwb.process.model.AnnotationArtifact createAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.AnnotationArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.AnnotationArtifact
       * @param id Identifier for org.semanticwb.process.model.AnnotationArtifact
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       */
        public static void removeAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.AnnotationArtifact
       * @param id Identifier for org.semanticwb.process.model.AnnotationArtifact
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return true if the org.semanticwb.process.model.AnnotationArtifact exists, false otherwise
       */

        public static boolean hasAnnotationArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAnnotationArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.AnnotationArtifact
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.AnnotationArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.AnnotationArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.AnnotationArtifact> listAnnotationArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.AnnotationArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static AnnotationArtifactBase.ClassMgr getAnnotationArtifactClassMgr()
    {
        return new AnnotationArtifactBase.ClassMgr();
    }

   /**
   * Constructs a AnnotationArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AnnotationArtifact
   */
    public AnnotationArtifactBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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
