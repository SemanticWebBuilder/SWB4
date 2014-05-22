package org.semanticwb.model.base;


   /**
   * Objeto por el cual se define un filtro para que un usuario tenga acceso o no a la estructura del sitio 
   */
public abstract class UserFilterBase extends org.semanticwb.model.SectionFilter implements org.semanticwb.model.XMLable,org.semanticwb.model.Traceable
{
   /**
   * Objeto por el cual se define un filtro para que un usuario tenga acceso o no a la estructura del sitio
   */
    public static final org.semanticwb.platform.SemanticClass swb_UserFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFilter");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserFilter");

    public static class ClassMgr
    {
       /**
       * Returns a list of UserFilter for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilters(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.UserFilter for all models
       * @return Iterator of org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilters()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter>(it, true);
        }

        public static org.semanticwb.model.UserFilter createUserFilter(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.model.UserFilter.ClassMgr.createUserFilter(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.model.UserFilter
       * @param id Identifier for org.semanticwb.model.UserFilter
       * @param model Model of the org.semanticwb.model.UserFilter
       * @return A org.semanticwb.model.UserFilter
       */
        public static org.semanticwb.model.UserFilter getUserFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFilter)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.UserFilter
       * @param id Identifier for org.semanticwb.model.UserFilter
       * @param model Model of the org.semanticwb.model.UserFilter
       * @return A org.semanticwb.model.UserFilter
       */
        public static org.semanticwb.model.UserFilter createUserFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.UserFilter)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.UserFilter
       * @param id Identifier for org.semanticwb.model.UserFilter
       * @param model Model of the org.semanticwb.model.UserFilter
       */
        public static void removeUserFilter(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.UserFilter
       * @param id Identifier for org.semanticwb.model.UserFilter
       * @param model Model of the org.semanticwb.model.UserFilter
       * @return true if the org.semanticwb.model.UserFilter exists, false otherwise
       */

        public static boolean hasUserFilter(String id, org.semanticwb.model.SWBModel model)
        {
            return (getUserFilter(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.UserFilter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserFilter
       * @return Iterator with all the org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilterByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserFilter with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilterByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserFilter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.UserFilter
       * @return Iterator with all the org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilterByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.UserFilter with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.UserFilter
       */

        public static java.util.Iterator<org.semanticwb.model.UserFilter> listUserFilterByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.UserFilter> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static UserFilterBase.ClassMgr getUserFilterClassMgr()
    {
        return new UserFilterBase.ClassMgr();
    }

   /**
   * Constructs a UserFilterBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserFilter
   */
    public UserFilterBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
