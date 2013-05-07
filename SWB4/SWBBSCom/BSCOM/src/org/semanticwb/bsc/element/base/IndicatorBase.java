package org.semanticwb.bsc.element.base;


public abstract class IndicatorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Searchable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Indexable,org.semanticwb.model.Traceable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Activeable,org.semanticwb.model.TemplateRefable,org.semanticwb.bsc.Commitable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Trashable,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Localeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.MetaTagable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Sortable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Tagable,org.semanticwb.model.Resourceable,org.semanticwb.model.Filterable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticClass bsc_Indicator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/bsc#Indicator");

    public static class ClassMgr
    {
       /**
       * Returns a list of Indicator for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.element.Indicator for all models
       * @return Iterator of org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicators()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator>(it, true);
        }

        public static org.semanticwb.bsc.element.Indicator createIndicator(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.element.Indicator.ClassMgr.createIndicator(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator getIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return A org.semanticwb.bsc.element.Indicator
       */
        public static org.semanticwb.bsc.element.Indicator createIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.element.Indicator)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       */
        public static void removeIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.element.Indicator
       * @param id Identifier for org.semanticwb.bsc.element.Indicator
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return true if the org.semanticwb.bsc.element.Indicator exists, false otherwise
       */

        public static boolean hasIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIndicator(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.bsc.element.Indicator
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.element.Indicator with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.bsc.element.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.element.Indicator> listIndicatorByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static IndicatorBase.ClassMgr getIndicatorClassMgr()
    {
        return new IndicatorBase.ClassMgr();
    }

   /**
   * Constructs a IndicatorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Indicator
   */
    public IndicatorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the Commited property
* @return boolean with the Commited
*/
    public boolean isCommited()
    {
        return getSemanticObject().getBooleanProperty(bsc_commited);
    }

/**
* Sets the Commited property
* @param value long with the Commited
*/
    public void setCommited(boolean value)
    {
        getSemanticObject().setBooleanProperty(bsc_commited, value);
    }

   /**
   * Gets the BSC
   * @return a instance of org.semanticwb.bsc.BSC
   */
    public org.semanticwb.bsc.BSC getBSC()
    {
        return (org.semanticwb.bsc.BSC)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
