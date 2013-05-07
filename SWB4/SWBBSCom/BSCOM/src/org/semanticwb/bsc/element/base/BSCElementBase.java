package org.semanticwb.bsc.element.base;


public abstract class BSCElementBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass bsc_BSCElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSCElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.BSCElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.BSCElement> listBSCElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.BSCElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.BSCElement for all models
       * @return Iterator of org.semanticwb.bsc.element.BSCElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.BSCElement> listBSCElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.BSCElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.element.BSCElement
       * @param id Identifier for org.semanticwb.bsc.element.BSCElement
       * @param model Model of the org.semanticwb.bsc.element.BSCElement
       * @return A org.semanticwb.bsc.element.BSCElement
       */
        public static org.semanticwb.bsc.element.BSCElement getBSCElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.BSCElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.BSCElement
       * @param id Identifier for org.semanticwb.bsc.element.BSCElement
       * @param model Model of the org.semanticwb.bsc.element.BSCElement
       * @return A org.semanticwb.bsc.element.BSCElement
       */
        public static org.semanticwb.bsc.element.BSCElement createBSCElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.BSCElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.BSCElement
       * @param id Identifier for org.semanticwb.bsc.element.BSCElement
       * @param model Model of the org.semanticwb.bsc.element.BSCElement
       */
        public static void removeBSCElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.BSCElement
       * @param id Identifier for org.semanticwb.bsc.element.BSCElement
       * @param model Model of the org.semanticwb.bsc.element.BSCElement
       * @return true if the org.semanticwb.bsc.element.BSCElement exists, false otherwise
       */

        public static boolean hasBSCElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBSCElement(id, model)!=null);
        }
    }

    public static BSCElementBase.ClassMgr getBSCElementClassMgr()
    {
        return new BSCElementBase.ClassMgr();
    }

   /**
   * Constructs a BSCElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSCElement
   */
    public BSCElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
