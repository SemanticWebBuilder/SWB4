package org.semanticwb.bsc.formelement.base;


public abstract class DateElementBase extends org.semanticwb.model.DateElement 
{
    public static final org.semanticwb.platform.SemanticClass bsc_DateElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DateElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#DateElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of DateElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.DateElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DateElement> listDateElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DateElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.DateElement for all models
       * @return Iterator of org.semanticwb.bsc.formelement.DateElement
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.DateElement> listDateElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.DateElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.DateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DateElement
       * @param model Model of the org.semanticwb.bsc.formelement.DateElement
       * @return A org.semanticwb.bsc.formelement.DateElement
       */
        public static org.semanticwb.bsc.formelement.DateElement getDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DateElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.DateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DateElement
       * @param model Model of the org.semanticwb.bsc.formelement.DateElement
       * @return A org.semanticwb.bsc.formelement.DateElement
       */
        public static org.semanticwb.bsc.formelement.DateElement createDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.DateElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.DateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DateElement
       * @param model Model of the org.semanticwb.bsc.formelement.DateElement
       */
        public static void removeDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.DateElement
       * @param id Identifier for org.semanticwb.bsc.formelement.DateElement
       * @param model Model of the org.semanticwb.bsc.formelement.DateElement
       * @return true if the org.semanticwb.bsc.formelement.DateElement exists, false otherwise
       */

        public static boolean hasDateElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDateElement(id, model)!=null);
        }
    }

//    public static DateElementBase.ClassMgr getDateElementClassMgr()
//    {
//        return new DateElementBase.ClassMgr();
//    }

   /**
   * Constructs a DateElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DateElement
   */
    public DateElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
