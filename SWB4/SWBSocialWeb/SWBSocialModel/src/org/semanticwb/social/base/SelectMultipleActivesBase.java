package org.semanticwb.social.base;


   /**
   * SelectMultipleActives 
   */
public abstract class SelectMultipleActivesBase extends org.semanticwb.model.base.FormElementBase 
{
   /**
   * SelectMultipleActives
   */
    public static final org.semanticwb.platform.SemanticClass social_SelectMultipleActives=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SelectMultipleActives");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SelectMultipleActives");

    public static class ClassMgr
    {
       /**
       * Returns a list of SelectMultipleActives for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SelectMultipleActives
       */

        public static java.util.Iterator<org.semanticwb.social.SelectMultipleActives> listSelectMultipleActiveses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SelectMultipleActives>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SelectMultipleActives for all models
       * @return Iterator of org.semanticwb.social.SelectMultipleActives
       */

        public static java.util.Iterator<org.semanticwb.social.SelectMultipleActives> listSelectMultipleActiveses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SelectMultipleActives>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.SelectMultipleActives
       * @param id Identifier for org.semanticwb.social.SelectMultipleActives
       * @param model Model of the org.semanticwb.social.SelectMultipleActives
       * @return A org.semanticwb.social.SelectMultipleActives
       */
        public static org.semanticwb.social.SelectMultipleActives getSelectMultipleActives(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SelectMultipleActives)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SelectMultipleActives
       * @param id Identifier for org.semanticwb.social.SelectMultipleActives
       * @param model Model of the org.semanticwb.social.SelectMultipleActives
       * @return A org.semanticwb.social.SelectMultipleActives
       */
        public static org.semanticwb.social.SelectMultipleActives createSelectMultipleActives(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SelectMultipleActives)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SelectMultipleActives
       * @param id Identifier for org.semanticwb.social.SelectMultipleActives
       * @param model Model of the org.semanticwb.social.SelectMultipleActives
       */
        public static void removeSelectMultipleActives(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SelectMultipleActives
       * @param id Identifier for org.semanticwb.social.SelectMultipleActives
       * @param model Model of the org.semanticwb.social.SelectMultipleActives
       * @return true if the org.semanticwb.social.SelectMultipleActives exists, false otherwise
       */

        public static boolean hasSelectMultipleActives(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSelectMultipleActives(id, model)!=null);
        }
    }

    public static SelectMultipleActivesBase.ClassMgr getSelectMultipleActivesClassMgr()
    {
        return new SelectMultipleActivesBase.ClassMgr();
    }

   /**
   * Constructs a SelectMultipleActivesBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SelectMultipleActives
   */
    public SelectMultipleActivesBase(org.semanticwb.platform.SemanticObject base)
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
