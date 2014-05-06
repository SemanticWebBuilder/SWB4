package org.semanticwb.social.base;


   /**
   * TextArea que muestra icono de ayuda 
   */
public abstract class TextAreaHelpBase extends org.semanticwb.model.TextArea 
{
    public static final org.semanticwb.platform.SemanticProperty social_helpText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#helpText");
   /**
   * TextArea que muestra icono de ayuda
   */
    public static final org.semanticwb.platform.SemanticClass social_TextAreaHelp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TextAreaHelp");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TextAreaHelp");

    public static class ClassMgr
    {
       /**
       * Returns a list of TextAreaHelp for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TextAreaHelp
       */

        public static java.util.Iterator<org.semanticwb.social.TextAreaHelp> listTextAreaHelps(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TextAreaHelp>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TextAreaHelp for all models
       * @return Iterator of org.semanticwb.social.TextAreaHelp
       */

        public static java.util.Iterator<org.semanticwb.social.TextAreaHelp> listTextAreaHelps()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TextAreaHelp>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.TextAreaHelp
       * @param id Identifier for org.semanticwb.social.TextAreaHelp
       * @param model Model of the org.semanticwb.social.TextAreaHelp
       * @return A org.semanticwb.social.TextAreaHelp
       */
        public static org.semanticwb.social.TextAreaHelp getTextAreaHelp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TextAreaHelp)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TextAreaHelp
       * @param id Identifier for org.semanticwb.social.TextAreaHelp
       * @param model Model of the org.semanticwb.social.TextAreaHelp
       * @return A org.semanticwb.social.TextAreaHelp
       */
        public static org.semanticwb.social.TextAreaHelp createTextAreaHelp(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TextAreaHelp)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TextAreaHelp
       * @param id Identifier for org.semanticwb.social.TextAreaHelp
       * @param model Model of the org.semanticwb.social.TextAreaHelp
       */
        public static void removeTextAreaHelp(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TextAreaHelp
       * @param id Identifier for org.semanticwb.social.TextAreaHelp
       * @param model Model of the org.semanticwb.social.TextAreaHelp
       * @return true if the org.semanticwb.social.TextAreaHelp exists, false otherwise
       */

        public static boolean hasTextAreaHelp(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTextAreaHelp(id, model)!=null);
        }
    }

    public static TextAreaHelpBase.ClassMgr getTextAreaHelpClassMgr()
    {
        return new TextAreaHelpBase.ClassMgr();
    }

   /**
   * Constructs a TextAreaHelpBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TextAreaHelp
   */
    public TextAreaHelpBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the HelpText property
* @return String with the HelpText
*/
    public String getHelpText()
    {
        return getSemanticObject().getProperty(social_helpText);
    }

/**
* Sets the HelpText property
* @param value long with the HelpText
*/
    public void setHelpText(String value)
    {
        getSemanticObject().setProperty(social_helpText, value);
    }
}
