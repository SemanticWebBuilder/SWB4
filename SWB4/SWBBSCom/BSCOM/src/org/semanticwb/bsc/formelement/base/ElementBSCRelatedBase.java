package org.semanticwb.bsc.formelement.base;


public abstract class ElementBSCRelatedBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticClass bsc_ElementBSCRelated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ElementBSCRelated");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#ElementBSCRelated");

    public static class ClassMgr
    {
       /**
       * Returns a list of ElementBSCRelated for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.ElementBSCRelated
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.ElementBSCRelated> listElementBSCRelateds(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.ElementBSCRelated>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.ElementBSCRelated for all models
       * @return Iterator of org.semanticwb.bsc.formelement.ElementBSCRelated
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.ElementBSCRelated> listElementBSCRelateds()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.ElementBSCRelated>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param id Identifier for org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param model Model of the org.semanticwb.bsc.formelement.ElementBSCRelated
       * @return A org.semanticwb.bsc.formelement.ElementBSCRelated
       */
        public static org.semanticwb.bsc.formelement.ElementBSCRelated getElementBSCRelated(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.ElementBSCRelated)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param id Identifier for org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param model Model of the org.semanticwb.bsc.formelement.ElementBSCRelated
       * @return A org.semanticwb.bsc.formelement.ElementBSCRelated
       */
        public static org.semanticwb.bsc.formelement.ElementBSCRelated createElementBSCRelated(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.ElementBSCRelated)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param id Identifier for org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param model Model of the org.semanticwb.bsc.formelement.ElementBSCRelated
       */
        public static void removeElementBSCRelated(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param id Identifier for org.semanticwb.bsc.formelement.ElementBSCRelated
       * @param model Model of the org.semanticwb.bsc.formelement.ElementBSCRelated
       * @return true if the org.semanticwb.bsc.formelement.ElementBSCRelated exists, false otherwise
       */

        public static boolean hasElementBSCRelated(String id, org.semanticwb.model.SWBModel model)
        {
            return (getElementBSCRelated(id, model)!=null);
        }
    }

    public static ElementBSCRelatedBase.ClassMgr getElementBSCRelatedClassMgr()
    {
        return new ElementBSCRelatedBase.ClassMgr();
    }

   /**
   * Constructs a ElementBSCRelatedBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ElementBSCRelated
   */
    public ElementBSCRelatedBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
