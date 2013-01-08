package org.semanticwb.model.base;


   /**
   * SelectMultipleTree 
   */
public abstract class SelectMultipleTreeBase extends org.semanticwb.model.SelectMultiple 
{
   /**
   * SelectMultipleTree
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectMultipleTree=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultipleTree");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectMultipleTree");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectMultipleTree for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SelectMultipleTree
       */

        public static java.util.Iterator<org.semanticwb.model.SelectMultipleTree> listSelectMultipleTrees(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultipleTree>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectMultipleTree for all models
       * @return Iterator of org.semanticwb.model.SelectMultipleTree
       */

        public static java.util.Iterator<org.semanticwb.model.SelectMultipleTree> listSelectMultipleTrees()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectMultipleTree>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectMultipleTree
       * @param id Identifier for org.semanticwb.model.SelectMultipleTree
       * @param model Model of the org.semanticwb.model.SelectMultipleTree
       * @return A org.semanticwb.model.SelectMultipleTree
       */
        public static org.semanticwb.model.SelectMultipleTree getSelectMultipleTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultipleTree)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectMultipleTree
       * @param id Identifier for org.semanticwb.model.SelectMultipleTree
       * @param model Model of the org.semanticwb.model.SelectMultipleTree
       * @return A org.semanticwb.model.SelectMultipleTree
       */
        public static org.semanticwb.model.SelectMultipleTree createSelectMultipleTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectMultipleTree)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectMultipleTree
       * @param id Identifier for org.semanticwb.model.SelectMultipleTree
       * @param model Model of the org.semanticwb.model.SelectMultipleTree
       */
        public static void removeSelectMultipleTree(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectMultipleTree
       * @param id Identifier for org.semanticwb.model.SelectMultipleTree
       * @param model Model of the org.semanticwb.model.SelectMultipleTree
       * @return true if the org.semanticwb.model.SelectMultipleTree exists, false otherwise
       */

        public static boolean hasSelectMultipleTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectMultipleTree(id, model)!=null);
        }
    }

    public static SelectMultipleTreeBase.ClassMgr getSelectMultipleTreeClassMgr()
    {
        return new SelectMultipleTreeBase.ClassMgr();
    }

   /**
   * Constructs a SelectMultipleTreeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectMultipleTree
   */
    public SelectMultipleTreeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
