package org.semanticwb.bsc.formelement.base;


public abstract class SelectOneSiblingBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticClass bsc_SelectOneSibling=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOneSibling");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectOneSibling");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectOneSibling for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOneSibling
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOneSibling> listSelectOneSiblings(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOneSibling>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectOneSibling for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectOneSibling
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectOneSibling> listSelectOneSiblings()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectOneSibling>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectOneSibling
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneSibling
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneSibling
       * @return A org.semanticwb.bsc.formelement.SelectOneSibling
       */
        public static org.semanticwb.bsc.formelement.SelectOneSibling getSelectOneSibling(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOneSibling)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectOneSibling
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneSibling
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneSibling
       * @return A org.semanticwb.bsc.formelement.SelectOneSibling
       */
        public static org.semanticwb.bsc.formelement.SelectOneSibling createSelectOneSibling(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectOneSibling)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectOneSibling
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneSibling
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneSibling
       */
        public static void removeSelectOneSibling(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectOneSibling
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectOneSibling
       * @param model Model of the org.semanticwb.bsc.formelement.SelectOneSibling
       * @return true if the org.semanticwb.bsc.formelement.SelectOneSibling exists, false otherwise
       */

        public static boolean hasSelectOneSibling(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectOneSibling(id, model)!=null);
        }
    }

    public static SelectOneSiblingBase.ClassMgr getSelectOneSiblingClassMgr()
    {
        return new SelectOneSiblingBase.ClassMgr();
    }

   /**
   * Constructs a SelectOneSiblingBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectOneSibling
   */
    public SelectOneSiblingBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
