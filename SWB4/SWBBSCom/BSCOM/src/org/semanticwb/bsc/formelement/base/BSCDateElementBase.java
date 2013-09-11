package org.semanticwb.bsc.formelement.base;


public abstract class BSCDateElementBase extends org.semanticwb.model.DateElement 
{
    public static final org.semanticwb.platform.SemanticClass bsc_BSCDateElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCDateElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#BSCDateElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of BSCDateElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.BSCDateElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.BSCDateElement> listBSCDateElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.BSCDateElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.BSCDateElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.BSCDateElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.BSCDateElement> listBSCDateElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.BSCDateElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.BSCDateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BSCDateElement
       * @param model Model of the org.semanticwb.bsc.formelement.BSCDateElement
       * @return A org.semanticwb.bsc.formelement.BSCDateElement
       */
        public static org.semanticwb.bsc.formelement.BSCDateElement getBSCDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.BSCDateElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.BSCDateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BSCDateElement
       * @param model Model of the org.semanticwb.bsc.formelement.BSCDateElement
       * @return A org.semanticwb.bsc.formelement.BSCDateElement
       */
        public static org.semanticwb.bsc.formelement.BSCDateElement createBSCDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.BSCDateElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.BSCDateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BSCDateElement
       * @param model Model of the org.semanticwb.bsc.formelement.BSCDateElement
       */
        public static void removeBSCDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.BSCDateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.BSCDateElement
       * @param model Model of the org.semanticwb.bsc.formelement.BSCDateElement
       * @return true if the org.semanticwb.bsc.formelement.BSCDateElement exists, false otherwise
       */

        public static boolean hasBSCDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBSCDateElement(id, model)!=null);
        }
    }

    public static BSCDateElementBase.ClassMgr getBSCDateElementClassMgr()
    {
        return new BSCDateElementBase.ClassMgr();
    }

   /**
   * Constructs a BSCDateElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BSCDateElement
   */
    public BSCDateElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
