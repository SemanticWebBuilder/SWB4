package org.semanticwb.process.model.base;


public abstract class ArtifactBase extends org.semanticwb.process.model.GraphicalElement implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_Artifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Artifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Artifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of Artifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.Artifact for all models
       * @return Iterator of org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact>(it, true);
        }

        public static org.semanticwb.process.model.Artifact createArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.Artifact.ClassMgr.createArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.Artifact
       * @param id Identifier for org.semanticwb.process.model.Artifact
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return A org.semanticwb.process.model.Artifact
       */
        public static org.semanticwb.process.model.Artifact getArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Artifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.Artifact
       * @param id Identifier for org.semanticwb.process.model.Artifact
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return A org.semanticwb.process.model.Artifact
       */
        public static org.semanticwb.process.model.Artifact createArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.Artifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.Artifact
       * @param id Identifier for org.semanticwb.process.model.Artifact
       * @param model Model of the org.semanticwb.process.model.Artifact
       */
        public static void removeArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.Artifact
       * @param id Identifier for org.semanticwb.process.model.Artifact
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return true if the org.semanticwb.process.model.Artifact exists, false otherwise
       */

        public static boolean hasArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.Artifact
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.Artifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.Artifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.Artifact> listArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Artifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ArtifactBase.ClassMgr getArtifactClassMgr()
    {
        return new ArtifactBase.ClassMgr();
    }

   /**
   * Constructs a ArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Artifact
   */
    public ArtifactBase(org.semanticwb.platform.SemanticObject base)
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
