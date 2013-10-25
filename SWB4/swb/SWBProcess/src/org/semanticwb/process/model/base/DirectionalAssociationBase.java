package org.semanticwb.process.model.base;


public abstract class DirectionalAssociationBase extends org.semanticwb.process.model.AssociationFlow implements org.semanticwb.process.model.ConnectionObjectEditable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swp_associationScriptCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#associationScriptCode");
    public static final org.semanticwb.platform.SemanticClass swp_DirectionalAssociation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DirectionalAssociation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#DirectionalAssociation");

    public static class ClassMgr
    {
       /**
       * Returns a list of DirectionalAssociation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.DirectionalAssociation for all models
       * @return Iterator of org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation>(it, true);
        }

        public static org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.DirectionalAssociation.ClassMgr.createDirectionalAssociation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.DirectionalAssociation
       * @param id Identifier for org.semanticwb.process.model.DirectionalAssociation
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return A org.semanticwb.process.model.DirectionalAssociation
       */
        public static org.semanticwb.process.model.DirectionalAssociation getDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DirectionalAssociation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.DirectionalAssociation
       * @param id Identifier for org.semanticwb.process.model.DirectionalAssociation
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return A org.semanticwb.process.model.DirectionalAssociation
       */
        public static org.semanticwb.process.model.DirectionalAssociation createDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.DirectionalAssociation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.DirectionalAssociation
       * @param id Identifier for org.semanticwb.process.model.DirectionalAssociation
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       */
        public static void removeDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.DirectionalAssociation
       * @param id Identifier for org.semanticwb.process.model.DirectionalAssociation
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return true if the org.semanticwb.process.model.DirectionalAssociation exists, false otherwise
       */

        public static boolean hasDirectionalAssociation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDirectionalAssociation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByDocumentation(org.semanticwb.process.model.Documentation value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Documentation
       * @param value Documentation of the type org.semanticwb.process.model.Documentation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByDocumentation(org.semanticwb.process.model.Documentation value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasDocumentation,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationBySource(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_source, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Source
       * @param value Source of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationBySource(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_source,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @param model Model of the org.semanticwb.process.model.DirectionalAssociation
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByTarget(org.semanticwb.process.model.GraphicalElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_target, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.DirectionalAssociation with a determined Target
       * @param value Target of the type org.semanticwb.process.model.GraphicalElement
       * @return Iterator with all the org.semanticwb.process.model.DirectionalAssociation
       */

        public static java.util.Iterator<org.semanticwb.process.model.DirectionalAssociation> listDirectionalAssociationByTarget(org.semanticwb.process.model.GraphicalElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.DirectionalAssociation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_target,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DirectionalAssociationBase.ClassMgr getDirectionalAssociationClassMgr()
    {
        return new DirectionalAssociationBase.ClassMgr();
    }

   /**
   * Constructs a DirectionalAssociationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DirectionalAssociation
   */
    public DirectionalAssociationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Script property
* @return String with the Script
*/
    public String getScript()
    {
        return getSemanticObject().getProperty(swp_associationScriptCode);
    }

/**
* Sets the Script property
* @param value long with the Script
*/
    public void setScript(String value)
    {
        getSemanticObject().setProperty(swp_associationScriptCode, value);
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
