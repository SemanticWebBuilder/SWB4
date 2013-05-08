package org.semanticwb.bsc.base;


public abstract class IndicatorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.CalendarRefable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableNode,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RuleRefable,org.semanticwb.bsc.Measurable,org.semanticwb.bsc.Recognizable,org.semanticwb.bsc.Committable,org.semanticwb.model.Referensable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Searchable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Tagable,org.semanticwb.model.Rankable,org.semanticwb.model.Trashable,org.semanticwb.model.Filterable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Countryable,org.semanticwb.model.Localeable,org.semanticwb.model.Viewable,org.semanticwb.model.Resourceable,org.semanticwb.model.Undeleteable
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
       * @return Iterator of org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicators(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.bsc.Indicator for all models
       * @return Iterator of org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicators()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator>(it, true);
        }

        public static org.semanticwb.bsc.Indicator createIndicator(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.bsc.Indicator.ClassMgr.createIndicator(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.bsc.Indicator
       * @param id Identifier for org.semanticwb.bsc.Indicator
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return A org.semanticwb.bsc.Indicator
       */
        public static org.semanticwb.bsc.Indicator getIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Indicator)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.bsc.Indicator
       * @param id Identifier for org.semanticwb.bsc.Indicator
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return A org.semanticwb.bsc.Indicator
       */
        public static org.semanticwb.bsc.Indicator createIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.bsc.Indicator)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.bsc.Indicator
       * @param id Identifier for org.semanticwb.bsc.Indicator
       * @param model Model of the org.semanticwb.bsc.Indicator
       */
        public static void removeIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.bsc.Indicator
       * @param id Identifier for org.semanticwb.bsc.Indicator
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return true if the org.semanticwb.bsc.Indicator exists, false otherwise
       */

        public static boolean hasIndicator(String id, org.semanticwb.model.SWBModel model)
        {
            return (getIndicator(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.Measure
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByMeasure(org.semanticwb.bsc.element.Measure value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasure, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Measure
       * @param value Measure of the type org.semanticwb.bsc.element.Measure
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByMeasure(org.semanticwb.bsc.element.Measure value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(bsc_hasMeasure,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.bsc.Indicator
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.bsc.Indicator with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.bsc.Indicator
       */

        public static java.util.Iterator<org.semanticwb.bsc.Indicator> listIndicatorByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.bsc.Indicator> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
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
   * Gets all the org.semanticwb.bsc.element.Measure
   * @return A GenericIterator with all the org.semanticwb.bsc.element.Measure
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure> listMeasures()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.bsc.element.Measure>(getSemanticObject().listObjectProperties(bsc_hasMeasure));
    }

   /**
   * Gets true if has a Measure
   * @param value org.semanticwb.bsc.element.Measure to verify
   * @return true if the org.semanticwb.bsc.element.Measure exists, false otherwise
   */
    public boolean hasMeasure(org.semanticwb.bsc.element.Measure value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(bsc_hasMeasure,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Measure
   * @param value org.semanticwb.bsc.element.Measure to add
   */

    public void addMeasure(org.semanticwb.bsc.element.Measure value)
    {
        getSemanticObject().addObjectProperty(bsc_hasMeasure, value.getSemanticObject());
    }
   /**
   * Removes all the Measure
   */

    public void removeAllMeasure()
    {
        getSemanticObject().removeProperty(bsc_hasMeasure);
    }
   /**
   * Removes a Measure
   * @param value org.semanticwb.bsc.element.Measure to remove
   */

    public void removeMeasure(org.semanticwb.bsc.element.Measure value)
    {
        getSemanticObject().removeObjectProperty(bsc_hasMeasure,value.getSemanticObject());
    }

   /**
   * Gets the Measure
   * @return a org.semanticwb.bsc.element.Measure
   */
    public org.semanticwb.bsc.element.Measure getMeasure()
    {
         org.semanticwb.bsc.element.Measure ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(bsc_hasMeasure);
         if(obj!=null)
         {
             ret=(org.semanticwb.bsc.element.Measure)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        return getSemanticObject().getProperty(bsc_prefix);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        getSemanticObject().setProperty(bsc_prefix, value);
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
