package org.semanticwb.bsc.formelement.base;


public abstract class HelpPageBase extends org.semanticwb.model.Label 
{
   /**
   * Mostrar en una ventana o cuadro de diálogo
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#type");
   /**
   * Mostrar de modo modal
   */
    public static final org.semanticwb.platform.SemanticProperty bsc_modal=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/bsc#modal");
    public static final org.semanticwb.platform.SemanticClass bsc_HelpPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#HelpPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#HelpPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of HelpPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.HelpPage
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.HelpPage> listHelpPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.HelpPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.HelpPage for all models
       * @return Iterator of org.semanticwb.bsc.formelement.HelpPage
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.HelpPage> listHelpPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.HelpPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.HelpPage
       * @param id Identifier for org.semanticwb.bsc.formelement.HelpPage
       * @param model Model of the org.semanticwb.bsc.formelement.HelpPage
       * @return A org.semanticwb.bsc.formelement.HelpPage
       */
        public static org.semanticwb.bsc.formelement.HelpPage getHelpPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.HelpPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.HelpPage
       * @param id Identifier for org.semanticwb.bsc.formelement.HelpPage
       * @param model Model of the org.semanticwb.bsc.formelement.HelpPage
       * @return A org.semanticwb.bsc.formelement.HelpPage
       */
        public static org.semanticwb.bsc.formelement.HelpPage createHelpPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.HelpPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.HelpPage
       * @param id Identifier for org.semanticwb.bsc.formelement.HelpPage
       * @param model Model of the org.semanticwb.bsc.formelement.HelpPage
       */
        public static void removeHelpPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.HelpPage
       * @param id Identifier for org.semanticwb.bsc.formelement.HelpPage
       * @param model Model of the org.semanticwb.bsc.formelement.HelpPage
       * @return true if the org.semanticwb.bsc.formelement.HelpPage exists, false otherwise
       */

        public static boolean hasHelpPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getHelpPage(id, model)!=null);
        }
    }

    public static HelpPageBase.ClassMgr getHelpPageClassMgr()
    {
        return new HelpPageBase.ClassMgr();
    }

   /**
   * Constructs a HelpPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the HelpPage
   */
    public HelpPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Type property
* @return String with the Type
*/
    public String getType()
    {
        return getSemanticObject().getProperty(bsc_type);
    }

/**
* Sets the Type property
* @param value long with the Type
*/
    public void setType(String value)
    {
        getSemanticObject().setProperty(bsc_type, value);
    }

/**
* Gets the Modal property
* @return boolean with the Modal
*/
    public boolean isModal()
    {
        return getSemanticObject().getBooleanProperty(bsc_modal);
    }

/**
* Sets the Modal property
* @param value long with the Modal
*/
    public void setModal(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_modal, value);
    }
}
