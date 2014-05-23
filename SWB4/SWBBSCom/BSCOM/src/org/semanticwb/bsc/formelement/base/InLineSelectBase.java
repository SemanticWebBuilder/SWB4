package org.semanticwb.bsc.formelement.base;


   /**
   * Crea un elemento dijit.InlineEditBox que al activarlo mostrará un select a fin de realizar modificaciones a la información 
   */
public abstract class InLineSelectBase extends org.semanticwb.model.SelectOne 
{
   /**
   * Crea un elemento dijit.InlineEditBox que al activarlo mostrará un select a fin de realizar modificaciones a la información
   */
    public static final org.semanticwb.platform.SemanticClass bsc_InLineSelect=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#InLineSelect");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#InLineSelect");

    public static class ClassMgr
    {
       /**
       * Returns a list of InLineSelect for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.formelement.InLineSelect
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.InLineSelect> listInLineSelects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.InLineSelect>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.formelement.InLineSelect for all models
       * @return Iterator of org.semanticwb.bsc.formelement.InLineSelect
       */

        public static java.util.Iterator<org.semanticwb.bsc.formelement.InLineSelect> listInLineSelects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.formelement.InLineSelect>(it, true);
        }
       /**
       * Gets a org.semanticwb.bsc.formelement.InLineSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.InLineSelect
       * @param model Model of the org.semanticwb.bsc.formelement.InLineSelect
       * @return A org.semanticwb.bsc.formelement.InLineSelect
       */
        public static org.semanticwb.bsc.formelement.InLineSelect getInLineSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.InLineSelect)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.formelement.InLineSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.InLineSelect
       * @param model Model of the org.semanticwb.bsc.formelement.InLineSelect
       * @return A org.semanticwb.bsc.formelement.InLineSelect
       */
        public static org.semanticwb.bsc.formelement.InLineSelect createInLineSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.formelement.InLineSelect)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.formelement.InLineSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.InLineSelect
       * @param model Model of the org.semanticwb.bsc.formelement.InLineSelect
       */
        public static void removeInLineSelect(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.formelement.InLineSelect
       * @param id Identifier for org.semanticwb.bsc.formelement.InLineSelect
       * @param model Model of the org.semanticwb.bsc.formelement.InLineSelect
       * @return true if the org.semanticwb.bsc.formelement.InLineSelect exists, false otherwise
       */

        public static boolean hasInLineSelect(String id, org.semanticwb.model.SWBModel model)
        {
            return (getInLineSelect(id, model)!=null);
        }
    }

    public static InLineSelectBase.ClassMgr getInLineSelectClassMgr()
    {
        return new InLineSelectBase.ClassMgr();
    }

   /**
   * Constructs a InLineSelectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the InLineSelect
   */
    public InLineSelectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
