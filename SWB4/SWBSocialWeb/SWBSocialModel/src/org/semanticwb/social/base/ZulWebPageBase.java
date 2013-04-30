package org.semanticwb.social.base;


public abstract class ZulWebPageBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Tagable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Localeable,org.semanticwb.model.Viewable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Undeleteable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Traceable,org.semanticwb.model.Searchable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Rankable,org.semanticwb.model.Trashable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Activeable,org.semanticwb.model.Countryable,org.semanticwb.model.Indexable,org.semanticwb.model.Resourceable,org.semanticwb.model.UserGroupRefable
{
    public static final org.semanticwb.platform.SemanticProperty social_wphOverImg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#wphOverImg");
   /**
   * Nombre del archivo zul que va ha mostrar un contenido para la página
   */
    public static final org.semanticwb.platform.SemanticProperty social_zulResourcePath=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#zulResourcePath");
   /**
   * Imagen - Icono de una sección, esta se desplegara en el menú de SWBSocial
   */
    public static final org.semanticwb.platform.SemanticProperty social_wpImg=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#wpImg");
    public static final org.semanticwb.platform.SemanticClass social_ZulWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#ZulWebPage");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#ZulWebPage");

    public static class ClassMgr
    {
       /**
       * Returns a list of ZulWebPage for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.ZulWebPage for all models
       * @return Iterator of org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.ZulWebPage
       * @param id Identifier for org.semanticwb.social.ZulWebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return A org.semanticwb.social.ZulWebPage
       */
        public static org.semanticwb.social.ZulWebPage getZulWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.ZulWebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.ZulWebPage
       * @param id Identifier for org.semanticwb.social.ZulWebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return A org.semanticwb.social.ZulWebPage
       */
        public static org.semanticwb.social.ZulWebPage createZulWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.ZulWebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.ZulWebPage
       * @param id Identifier for org.semanticwb.social.ZulWebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       */
        public static void removeZulWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.ZulWebPage
       * @param id Identifier for org.semanticwb.social.ZulWebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return true if the org.semanticwb.social.ZulWebPage exists, false otherwise
       */

        public static boolean hasZulWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getZulWebPage(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.social.ZulWebPage
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.ZulWebPage with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.social.ZulWebPage
       */

        public static java.util.Iterator<org.semanticwb.social.ZulWebPage> listZulWebPageByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.ZulWebPage> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ZulWebPageBase.ClassMgr getZulWebPageClassMgr()
    {
        return new ZulWebPageBase.ClassMgr();
    }

   /**
   * Constructs a ZulWebPageBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ZulWebPage
   */
    public ZulWebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the WphOverImg property
* @return String with the WphOverImg
*/
    public String getWphOverImg()
    {
        return getSemanticObject().getProperty(social_wphOverImg);
    }

/**
* Sets the WphOverImg property
* @param value long with the WphOverImg
*/
    public void setWphOverImg(String value)
    {
        getSemanticObject().setProperty(social_wphOverImg, value);
    }

/**
* Gets the ZulResourcePath property
* @return String with the ZulResourcePath
*/
    public String getZulResourcePath()
    {
        return getSemanticObject().getProperty(social_zulResourcePath);
    }

/**
* Sets the ZulResourcePath property
* @param value long with the ZulResourcePath
*/
    public void setZulResourcePath(String value)
    {
        getSemanticObject().setProperty(social_zulResourcePath, value);
    }

/**
* Gets the WpImg property
* @return String with the WpImg
*/
    public String getWpImg()
    {
        return getSemanticObject().getProperty(social_wpImg);
    }

/**
* Sets the WpImg property
* @param value long with the WpImg
*/
    public void setWpImg(String value)
    {
        getSemanticObject().setProperty(social_wpImg, value);
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
