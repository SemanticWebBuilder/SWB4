package org.semanticwb.bsc.formelement.base;


public abstract class SelectWithBlankBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticClass bsc_SelectWithBlank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectWithBlank");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#SelectWithBlank");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectWithBlank for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.SelectWithBlank
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectWithBlank> listSelectWithBlanks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectWithBlank>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.SelectWithBlank for all models
       * @return Iterator of org.semanticwb.bsc.formelement.SelectWithBlank
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.SelectWithBlank> listSelectWithBlanks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.SelectWithBlank>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.SelectWithBlank
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectWithBlank
       * @param model Model of the org.semanticwb.bsc.formelement.SelectWithBlank
       * @return A org.semanticwb.bsc.formelement.SelectWithBlank
       */
        public static org.semanticwb.bsc.formelement.SelectWithBlank getSelectWithBlank(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectWithBlank)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.SelectWithBlank
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectWithBlank
       * @param model Model of the org.semanticwb.bsc.formelement.SelectWithBlank
       * @return A org.semanticwb.bsc.formelement.SelectWithBlank
       */
        public static org.semanticwb.bsc.formelement.SelectWithBlank createSelectWithBlank(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.SelectWithBlank)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.SelectWithBlank
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectWithBlank
       * @param model Model of the org.semanticwb.bsc.formelement.SelectWithBlank
       */
        public static void removeSelectWithBlank(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.SelectWithBlank
       * @param id Identifier for org.semanticwb.bsc.formelement.SelectWithBlank
       * @param model Model of the org.semanticwb.bsc.formelement.SelectWithBlank
       * @return true if the org.semanticwb.bsc.formelement.SelectWithBlank exists, false otherwise
       */

        public static boolean hasSelectWithBlank(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectWithBlank(id, model)!=null);
        }
    }

    public static SelectWithBlankBase.ClassMgr getSelectWithBlankClassMgr()
    {
        return new SelectWithBlankBase.ClassMgr();
    }

   /**
   * Constructs a SelectWithBlankBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectWithBlank
   */
    public SelectWithBlankBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
