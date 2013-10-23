package org.semanticwb.model.base;


   /**
   * Es una pagina web utilizada para mostrar opciones del menu dentro de la administración de SWB 
   */
public abstract class MenuItemBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.Tagable,org.semanticwb.model.Localeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Activeable,org.semanticwb.model.Searchable,org.semanticwb.model.NSPrefixFilterable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Traceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Viewable
{
    public static final org.semanticwb.platform.SemanticProperty swb_mnuItemShowIFrame=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#mnuItemShowIFrame");
   /**
   * Es una pagina web utilizada para mostrar opciones del menu dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_MenuItem=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#MenuItem");

    public static class ClassMgr
    {
       /**
       * Returns a list of MenuItem for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.MenuItem for all models
       * @return Iterator of org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItems()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.MenuItem
       * @param id Identifier for org.semanticwb.model.MenuItem
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return A org.semanticwb.model.MenuItem
       */
        public static org.semanticwb.model.MenuItem getMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MenuItem)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.MenuItem
       * @param id Identifier for org.semanticwb.model.MenuItem
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return A org.semanticwb.model.MenuItem
       */
        public static org.semanticwb.model.MenuItem createMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.MenuItem)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.MenuItem
       * @param id Identifier for org.semanticwb.model.MenuItem
       * @param model Model of the org.semanticwb.model.MenuItem
       */
        public static void removeMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.MenuItem
       * @param id Identifier for org.semanticwb.model.MenuItem
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return true if the org.semanticwb.model.MenuItem exists, false otherwise
       */

        public static boolean hasMenuItem(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMenuItem(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.MenuItem
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.MenuItem with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.MenuItem
       */

        public static java.util.Iterator<org.semanticwb.model.MenuItem> listMenuItemByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.MenuItem> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static MenuItemBase.ClassMgr getMenuItemClassMgr()
    {
        return new MenuItemBase.ClassMgr();
    }

   /**
   * Constructs a MenuItemBase with a SemanticObject
   * @param base The SemanticObject with the properties for the MenuItem
   */
    public MenuItemBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NsPrefixFilter property
* @return String with the NsPrefixFilter
*/
    public String getNsPrefixFilter()
    {
        return getSemanticObject().getProperty(swbxf_nsPrefixFilter);
    }

/**
* Sets the NsPrefixFilter property
* @param value long with the NsPrefixFilter
*/
    public void setNsPrefixFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_nsPrefixFilter, value);
    }

/**
* Gets the ShowAs property
* @return String with the ShowAs
*/
    public String getShowAs()
    {
        return getSemanticObject().getProperty(swb_mnuItemShowIFrame);
    }

/**
* Sets the ShowAs property
* @param value long with the ShowAs
*/
    public void setShowAs(String value)
    {
        getSemanticObject().setProperty(swb_mnuItemShowIFrame, value);
    }

   /**
   * Gets the AdminWebSite
   * @return a instance of org.semanticwb.model.AdminWebSite
   */
    public org.semanticwb.model.AdminWebSite getAdminWebSite()
    {
        return (org.semanticwb.model.AdminWebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
