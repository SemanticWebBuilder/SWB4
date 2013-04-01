package org.semanticwb.social.base;


public abstract class TreeNodePageBase extends org.semanticwb.social.ZulWebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Tagable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Filterable,org.semanticwb.model.Searchable,org.semanticwb.model.Localeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.MetaTagable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Expirable,org.semanticwb.model.Countryable,org.semanticwb.model.Rankable
{
   /**
   * Acción a implementar en un zul para esta opción en el árbol. Ej. "add", "edit", "listen", "opcionx", etc.
   */
    public static final org.semanticwb.platform.SemanticProperty social_tree_action=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#tree_action");
   /**
   * Uri de la clase a ser desplegada en el árbol de navegación como un nodo de tipo Categoría.
   */
    public static final org.semanticwb.platform.SemanticProperty social_tree_classUri=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#tree_classUri");
    public static final org.semanticwb.platform.SemanticClass social_TreeNodePage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TreeNodePage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#TreeNodePage");

    public static class ClassMgr
    {
       /**
       * Returns a list of TreeNodePage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.TreeNodePage for all models
       * @return Iterator of org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.TreeNodePage
       * @param id Identifier for org.semanticwb.social.TreeNodePage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return A org.semanticwb.social.TreeNodePage
       */
        public static org.semanticwb.social.TreeNodePage getTreeNodePage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TreeNodePage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.TreeNodePage
       * @param id Identifier for org.semanticwb.social.TreeNodePage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return A org.semanticwb.social.TreeNodePage
       */
        public static org.semanticwb.social.TreeNodePage createTreeNodePage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.TreeNodePage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.TreeNodePage
       * @param id Identifier for org.semanticwb.social.TreeNodePage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       */
        public static void removeTreeNodePage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.TreeNodePage
       * @param id Identifier for org.semanticwb.social.TreeNodePage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return true if the org.semanticwb.social.TreeNodePage exists, false otherwise
       */

        public static boolean hasTreeNodePage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTreeNodePage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.TreeNodePage
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.TreeNodePage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.TreeNodePage
       */

        public static java.util.Iterator<org.semanticwb.social.TreeNodePage> listTreeNodePageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.TreeNodePage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TreeNodePageBase.ClassMgr getTreeNodePageClassMgr()
    {
        return new TreeNodePageBase.ClassMgr();
    }

   /**
   * Constructs a TreeNodePageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TreeNodePage
   */
    public TreeNodePageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Action property
* @return String with the Action
*/
    public String getAction()
    {
        return getSemanticObject().getProperty(social_tree_action);
    }

/**
* Sets the Action property
* @param value long with the Action
*/
    public void setAction(String value)
    {
        getSemanticObject().setProperty(social_tree_action, value);
    }

/**
* Gets the ClassUri property
* @return String with the ClassUri
*/
    public String getClassUri()
    {
        return getSemanticObject().getProperty(social_tree_classUri);
    }

/**
* Sets the ClassUri property
* @param value long with the ClassUri
*/
    public void setClassUri(String value)
    {
        getSemanticObject().setProperty(social_tree_classUri, value);
    }

   /**
   * Gets the SocialAdmin
   * @return a instance of org.semanticwb.social.SocialAdmin
   */
    public org.semanticwb.social.SocialAdmin getSocialAdmin()
    {
        return (org.semanticwb.social.SocialAdmin)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
