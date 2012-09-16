package org.semanticwb.process.model.base;


   /**
   * Elemento que valida la unicidad y ligado de los codigos de accion de los eventos de procesos 
   */
public abstract class ActionCodeElementBase extends org.semanticwb.model.Text 
{
   /**
   * Elemento que valida la unicidad y ligado de los codigos de accion de los eventos de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swp_ActionCodeElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActionCodeElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ActionCodeElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of ActionCodeElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.ActionCodeElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ActionCodeElement> listActionCodeElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActionCodeElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.ActionCodeElement for all models
       * @return Iterator of org.semanticwb.process.model.ActionCodeElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.ActionCodeElement> listActionCodeElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ActionCodeElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.ActionCodeElement
       * @param id Identifier for org.semanticwb.process.model.ActionCodeElement
       * @param model Model of the org.semanticwb.process.model.ActionCodeElement
       * @return A org.semanticwb.process.model.ActionCodeElement
       */
        public static org.semanticwb.process.model.ActionCodeElement getActionCodeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActionCodeElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.ActionCodeElement
       * @param id Identifier for org.semanticwb.process.model.ActionCodeElement
       * @param model Model of the org.semanticwb.process.model.ActionCodeElement
       * @return A org.semanticwb.process.model.ActionCodeElement
       */
        public static org.semanticwb.process.model.ActionCodeElement createActionCodeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.ActionCodeElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.ActionCodeElement
       * @param id Identifier for org.semanticwb.process.model.ActionCodeElement
       * @param model Model of the org.semanticwb.process.model.ActionCodeElement
       */
        public static void removeActionCodeElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.ActionCodeElement
       * @param id Identifier for org.semanticwb.process.model.ActionCodeElement
       * @param model Model of the org.semanticwb.process.model.ActionCodeElement
       * @return true if the org.semanticwb.process.model.ActionCodeElement exists, false otherwise
       */

        public static boolean hasActionCodeElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActionCodeElement(id, model)!=null);
        }
    }

    public static ActionCodeElementBase.ClassMgr getActionCodeElementClassMgr()
    {
        return new ActionCodeElementBase.ClassMgr();
    }

   /**
   * Constructs a ActionCodeElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ActionCodeElement
   */
    public ActionCodeElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
