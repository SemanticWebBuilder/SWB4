package org.semanticwb.model.base;


   /**
   * Elemento que muestra un componente grafico para editar texto (Textarea) 
   */
public abstract class TextAreaBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaRows=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaRows");
    public static final org.semanticwb.platform.SemanticProperty swbxf_textAreaCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textAreaCols");
   /**
   * Elemento que muestra un componente grafico para editar texto (Textarea)
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_TextArea=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#TextArea");

    public static class ClassMgr
    {
       /**
       * Returns a list of TextArea for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.TextArea
       */

        public static java.util.Iterator<org.semanticwb.model.TextArea> listTextAreas(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TextArea>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.TextArea for all models
       * @return Iterator of org.semanticwb.model.TextArea
       */

        public static java.util.Iterator<org.semanticwb.model.TextArea> listTextAreas()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TextArea>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.TextArea
       * @param id Identifier for org.semanticwb.model.TextArea
       * @param model Model of the org.semanticwb.model.TextArea
       * @return A org.semanticwb.model.TextArea
       */
        public static org.semanticwb.model.TextArea getTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TextArea)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.TextArea
       * @param id Identifier for org.semanticwb.model.TextArea
       * @param model Model of the org.semanticwb.model.TextArea
       * @return A org.semanticwb.model.TextArea
       */
        public static org.semanticwb.model.TextArea createTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.TextArea)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.TextArea
       * @param id Identifier for org.semanticwb.model.TextArea
       * @param model Model of the org.semanticwb.model.TextArea
       */
        public static void removeTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.TextArea
       * @param id Identifier for org.semanticwb.model.TextArea
       * @param model Model of the org.semanticwb.model.TextArea
       * @return true if the org.semanticwb.model.TextArea exists, false otherwise
       */

        public static boolean hasTextArea(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextArea(id, model)!=null);
        }
    }

   /**
   * Constructs a TextAreaBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TextArea
   */
    public TextAreaBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Rows property
* @return int with the Rows
*/
    public int getRows()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaRows);
    }

/**
* Sets the Rows property
* @param value long with the Rows
*/
    public void setRows(int value)
    {
        getSemanticObject().setIntProperty(swbxf_textAreaRows, value);
    }

/**
* Gets the Cols property
* @return int with the Cols
*/
    public int getCols()
    {
        return getSemanticObject().getIntProperty(swbxf_textAreaCols);
    }

/**
* Sets the Cols property
* @param value long with the Cols
*/
    public void setCols(int value)
    {
        getSemanticObject().setIntProperty(swbxf_textAreaCols, value);
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
