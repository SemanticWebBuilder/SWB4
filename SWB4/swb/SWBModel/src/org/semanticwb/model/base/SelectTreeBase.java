package org.semanticwb.model.base;


public abstract class SelectTreeBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_st_startElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#st_startElement");
    public static final org.semanticwb.platform.SemanticClass swbxf_SelectTree=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#SelectTree");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectTree for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.SelectTree
       */

        public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.SelectTree for all models
       * @return Iterator of org.semanticwb.model.SelectTree
       */

        public static java.util.Iterator<org.semanticwb.model.SelectTree> listSelectTrees()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.SelectTree>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.SelectTree
       * @param id Identifier for org.semanticwb.model.SelectTree
       * @param model Model of the org.semanticwb.model.SelectTree
       * @return A org.semanticwb.model.SelectTree
       */
        public static org.semanticwb.model.SelectTree getSelectTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.SelectTree
       * @param id Identifier for org.semanticwb.model.SelectTree
       * @param model Model of the org.semanticwb.model.SelectTree
       * @return A org.semanticwb.model.SelectTree
       */
        public static org.semanticwb.model.SelectTree createSelectTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.SelectTree)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.SelectTree
       * @param id Identifier for org.semanticwb.model.SelectTree
       * @param model Model of the org.semanticwb.model.SelectTree
       */
        public static void removeSelectTree(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.SelectTree
       * @param id Identifier for org.semanticwb.model.SelectTree
       * @param model Model of the org.semanticwb.model.SelectTree
       * @return true if the org.semanticwb.model.SelectTree exists, false otherwise
       */

        public static boolean hasSelectTree(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectTree(id, model)!=null);
        }
    }

   /**
   * Constructs a SelectTreeBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectTree
   */
    public SelectTreeBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the StartElement property
* @return String with the StartElement
*/
    public String getStartElement()
    {
        return getSemanticObject().getProperty(swbxf_st_startElement);
    }

/**
* Sets the StartElement property
* @param value long with the StartElement
*/
    public void setStartElement(String value)
    {
        getSemanticObject().setProperty(swbxf_st_startElement, value);
    }
}
