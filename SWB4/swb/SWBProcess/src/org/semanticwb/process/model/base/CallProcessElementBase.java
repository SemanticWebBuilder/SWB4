package org.semanticwb.process.model.base;


public abstract class CallProcessElementBase extends org.semanticwb.model.SelectOne 
{
    public static final org.semanticwb.platform.SemanticClass swp_CallProcessElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallProcessElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#CallProcessElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of CallProcessElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.CallProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcessElement> listCallProcessElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcessElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.CallProcessElement for all models
       * @return Iterator of org.semanticwb.process.model.CallProcessElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.CallProcessElement> listCallProcessElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.CallProcessElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.CallProcessElement
       * @param id Identifier for org.semanticwb.process.model.CallProcessElement
       * @param model Model of the org.semanticwb.process.model.CallProcessElement
       * @return A org.semanticwb.process.model.CallProcessElement
       */
        public static org.semanticwb.process.model.CallProcessElement getCallProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallProcessElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.CallProcessElement
       * @param id Identifier for org.semanticwb.process.model.CallProcessElement
       * @param model Model of the org.semanticwb.process.model.CallProcessElement
       * @return A org.semanticwb.process.model.CallProcessElement
       */
        public static org.semanticwb.process.model.CallProcessElement createCallProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.CallProcessElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.CallProcessElement
       * @param id Identifier for org.semanticwb.process.model.CallProcessElement
       * @param model Model of the org.semanticwb.process.model.CallProcessElement
       */
        public static void removeCallProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.CallProcessElement
       * @param id Identifier for org.semanticwb.process.model.CallProcessElement
       * @param model Model of the org.semanticwb.process.model.CallProcessElement
       * @return true if the org.semanticwb.process.model.CallProcessElement exists, false otherwise
       */

        public static boolean hasCallProcessElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCallProcessElement(id, model)!=null);
        }
    }

    public static CallProcessElementBase.ClassMgr getCallProcessElementClassMgr()
    {
        return new CallProcessElementBase.ClassMgr();
    }

   /**
   * Constructs a CallProcessElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CallProcessElement
   */
    public CallProcessElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
