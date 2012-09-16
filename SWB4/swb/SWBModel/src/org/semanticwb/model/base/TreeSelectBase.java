package org.semanticwb.model.base;


   /**
   * TreeSelect 
   */
public abstract class TreeSelectBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * TreeSelect
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_TreeSelect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TreeSelect");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TreeSelect");

    public static class ClassMgr
    {
       /**
       * Returns a list of TreeSelect for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.TreeSelect
       */

        public static java.util.Iterator<org.semanticwb.model.TreeSelect> listTreeSelects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TreeSelect>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.TreeSelect for all models
       * @return Iterator of org.semanticwb.model.TreeSelect
       */

        public static java.util.Iterator<org.semanticwb.model.TreeSelect> listTreeSelects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TreeSelect>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.TreeSelect
       * @param id Identifier for org.semanticwb.model.TreeSelect
       * @param model Model of the org.semanticwb.model.TreeSelect
       * @return A org.semanticwb.model.TreeSelect
       */
        public static org.semanticwb.model.TreeSelect getTreeSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TreeSelect)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.TreeSelect
       * @param id Identifier for org.semanticwb.model.TreeSelect
       * @param model Model of the org.semanticwb.model.TreeSelect
       * @return A org.semanticwb.model.TreeSelect
       */
        public static org.semanticwb.model.TreeSelect createTreeSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TreeSelect)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.TreeSelect
       * @param id Identifier for org.semanticwb.model.TreeSelect
       * @param model Model of the org.semanticwb.model.TreeSelect
       */
        public static void removeTreeSelect(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.TreeSelect
       * @param id Identifier for org.semanticwb.model.TreeSelect
       * @param model Model of the org.semanticwb.model.TreeSelect
       * @return true if the org.semanticwb.model.TreeSelect exists, false otherwise
       */

        public static boolean hasTreeSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTreeSelect(id, model)!=null);
        }
    }

    public static TreeSelectBase.ClassMgr getTreeSelectClassMgr()
    {
        return new TreeSelectBase.ClassMgr();
    }

   /**
   * Constructs a TreeSelectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TreeSelect
   */
    public TreeSelectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
