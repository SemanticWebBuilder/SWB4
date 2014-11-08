package org.semanticwb.bsc.formelement.base;


   /**
   * Form Element que presentará el enlace a un elemento complementario. 
   */
public abstract class AdditionalLinkElementBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * Form Element que presentará el enlace a un elemento complementario.
   */
    public static final org.semanticwb.platform.SemanticClass bsc_AdditionalLinkElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AdditionalLinkElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#AdditionalLinkElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of AdditionalLinkElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.AdditionalLinkElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.AdditionalLinkElement> listAdditionalLinkElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.AdditionalLinkElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.AdditionalLinkElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.AdditionalLinkElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.AdditionalLinkElement> listAdditionalLinkElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.AdditionalLinkElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param model Model of the org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @return A org.semanticwb.bsc.formelement.AdditionalLinkElement
       */
        public static org.semanticwb.bsc.formelement.AdditionalLinkElement getAdditionalLinkElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.AdditionalLinkElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param model Model of the org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @return A org.semanticwb.bsc.formelement.AdditionalLinkElement
       */
        public static org.semanticwb.bsc.formelement.AdditionalLinkElement createAdditionalLinkElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.AdditionalLinkElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param model Model of the org.semanticwb.bsc.formelement.AdditionalLinkElement
       */
        public static void removeAdditionalLinkElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param id Identifier for org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @param model Model of the org.semanticwb.bsc.formelement.AdditionalLinkElement
       * @return true if the org.semanticwb.bsc.formelement.AdditionalLinkElement exists, false otherwise
       */

        public static boolean hasAdditionalLinkElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAdditionalLinkElement(id, model)!=null);
        }
    }

    public static AdditionalLinkElementBase.ClassMgr getAdditionalLinkElementClassMgr()
    {
        return new AdditionalLinkElementBase.ClassMgr();
    }

   /**
   * Constructs a AdditionalLinkElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the AdditionalLinkElement
   */
    public AdditionalLinkElementBase(org.semanticwb.platform.SemanticObject base)
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
