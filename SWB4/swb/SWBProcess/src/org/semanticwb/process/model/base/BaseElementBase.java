package org.semanticwb.process.model.base;


public abstract class BaseElementBase extends org.semanticwb.model.SWBClass 
{
    public static final org.semanticwb.platform.SemanticClass swp_BaseElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BaseElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#BaseElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of BaseElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.BaseElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.BaseElement> listBaseElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BaseElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.BaseElement for all models
       * @return Iterator of org.semanticwb.process.model.BaseElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.BaseElement> listBaseElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.BaseElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.BaseElement
       * @param id Identifier for org.semanticwb.process.model.BaseElement
       * @param model Model of the org.semanticwb.process.model.BaseElement
       * @return A org.semanticwb.process.model.BaseElement
       */
        public static org.semanticwb.process.model.BaseElement getBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BaseElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.BaseElement
       * @param id Identifier for org.semanticwb.process.model.BaseElement
       * @param model Model of the org.semanticwb.process.model.BaseElement
       * @return A org.semanticwb.process.model.BaseElement
       */
        public static org.semanticwb.process.model.BaseElement createBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.BaseElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.BaseElement
       * @param id Identifier for org.semanticwb.process.model.BaseElement
       * @param model Model of the org.semanticwb.process.model.BaseElement
       */
        public static void removeBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.BaseElement
       * @param id Identifier for org.semanticwb.process.model.BaseElement
       * @param model Model of the org.semanticwb.process.model.BaseElement
       * @return true if the org.semanticwb.process.model.BaseElement exists, false otherwise
       */

        public static boolean hasBaseElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getBaseElement(id, model)!=null);
        }
    }

    public static BaseElementBase.ClassMgr getBaseElementClassMgr()
    {
        return new BaseElementBase.ClassMgr();
    }

   /**
   * Constructs a BaseElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the BaseElement
   */
    public BaseElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
