package org.semanticwb.process.model.base;


public abstract class GroupArtifactBase extends org.semanticwb.process.model.Artifact implements org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticClass swp_GroupArtifact=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GroupArtifact");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#GroupArtifact");

    public static class ClassMgr
    {
       /**
       * Returns a list of GroupArtifact for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifacts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.GroupArtifact for all models
       * @return Iterator of org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifacts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact>(it, true);
        }

        public static org.semanticwb.process.model.GroupArtifact createGroupArtifact(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.GroupArtifact.ClassMgr.createGroupArtifact(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.GroupArtifact
       * @param id Identifier for org.semanticwb.process.model.GroupArtifact
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return A org.semanticwb.process.model.GroupArtifact
       */
        public static org.semanticwb.process.model.GroupArtifact getGroupArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GroupArtifact)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.GroupArtifact
       * @param id Identifier for org.semanticwb.process.model.GroupArtifact
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return A org.semanticwb.process.model.GroupArtifact
       */
        public static org.semanticwb.process.model.GroupArtifact createGroupArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.GroupArtifact)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.GroupArtifact
       * @param id Identifier for org.semanticwb.process.model.GroupArtifact
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       */
        public static void removeGroupArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.GroupArtifact
       * @param id Identifier for org.semanticwb.process.model.GroupArtifact
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return true if the org.semanticwb.process.model.GroupArtifact exists, false otherwise
       */

        public static boolean hasGroupArtifact(String id, org.semanticwb.model.SWBModel model)
        {
            return (getGroupArtifact(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined InputConnectionObject
       * @param value InputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByInputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasInputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByChild(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Child
       * @param value Child of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByChild(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasChildInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByParent(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_parent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Parent
       * @param value Parent of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByParent(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_parent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByContainer(org.semanticwb.process.model.Containerable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_container, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Container
       * @param value Container of the type org.semanticwb.process.model.Containerable
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByContainer(org.semanticwb.process.model.Containerable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_container,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @param model Model of the org.semanticwb.process.model.GroupArtifact
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.GroupArtifact with a determined OutputConnectionObject
       * @param value OutputConnectionObject of the type org.semanticwb.process.model.ConnectionObject
       * @return Iterator with all the org.semanticwb.process.model.GroupArtifact
       */

        public static java.util.Iterator<org.semanticwb.process.model.GroupArtifact> listGroupArtifactByOutputConnectionObject(org.semanticwb.process.model.ConnectionObject value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.GroupArtifact> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasOutputConnectionObjectInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static GroupArtifactBase.ClassMgr getGroupArtifactClassMgr()
    {
        return new GroupArtifactBase.ClassMgr();
    }

   /**
   * Constructs a GroupArtifactBase with a SemanticObject
   * @param base The SemanticObject with the properties for the GroupArtifact
   */
    public GroupArtifactBase(org.semanticwb.platform.SemanticObject base)
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
