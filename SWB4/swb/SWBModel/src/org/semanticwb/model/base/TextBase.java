package org.semanticwb.model.base;


public abstract class TextBase extends org.semanticwb.model.base.FormElementBase 
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_textRegExp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#textRegExp");
    public static final org.semanticwb.platform.SemanticClass swbxf_Text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#Text");

    public static class ClassMgr
    {
       /**
       * Returns a list of Text for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.Text
       */

        public static java.util.Iterator<org.semanticwb.model.Text> listTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Text>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.Text for all models
       * @return Iterator of org.semanticwb.model.Text
       */

        public static java.util.Iterator<org.semanticwb.model.Text> listTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Text>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.Text
       * @param id Identifier for org.semanticwb.model.Text
       * @param model Model of the org.semanticwb.model.Text
       * @return A org.semanticwb.model.Text
       */
        public static org.semanticwb.model.Text getText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Text)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.Text
       * @param id Identifier for org.semanticwb.model.Text
       * @param model Model of the org.semanticwb.model.Text
       * @return A org.semanticwb.model.Text
       */
        public static org.semanticwb.model.Text createText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.Text)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.Text
       * @param id Identifier for org.semanticwb.model.Text
       * @param model Model of the org.semanticwb.model.Text
       */
        public static void removeText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.Text
       * @param id Identifier for org.semanticwb.model.Text
       * @param model Model of the org.semanticwb.model.Text
       * @return true if the org.semanticwb.model.Text exists, false otherwise
       */

        public static boolean hasText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getText(id, model)!=null);
        }
    }

   /**
   * Constructs a TextBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Text
   */
    public TextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the RegExp property
* @return String with the RegExp
*/
    public String getRegExp()
    {
        return getSemanticObject().getProperty(swbxf_textRegExp);
    }

/**
* Sets the RegExp property
* @param value long with the RegExp
*/
    public void setRegExp(String value)
    {
        getSemanticObject().setProperty(swbxf_textRegExp, value);
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
