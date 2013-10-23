package org.semanticwb.model.base;


   /**
   * Es una pagina web utilizada para mostrar comportamientos (tabs) dentro de la administración de SWB 
   */
public abstract class ObjectBehaviorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Countryable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Filterable,org.semanticwb.model.Trashable,org.semanticwb.model.Tagable,org.semanticwb.model.Localeable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.MetaTagable,org.semanticwb.model.Activeable,org.semanticwb.model.Searchable,org.semanticwb.model.NSPrefixFilterable,org.semanticwb.model.Expirable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Traceable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Viewable
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorParams=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorParams");
   /**
   * Muetra el comportamiento si cumple con la interface definida y con el filtro de propiedades especificado
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorPropertyFilter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorPropertyFilter");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorRefreshOnShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorRefreshOnShow");
    public static final org.semanticwb.platform.SemanticClass owl_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Class");
    public static final org.semanticwb.platform.SemanticProperty swbxf_interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#interface");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorURL");
   /**
   * Es una pagina web utilizada para mostrar comportamientos (tabs) dentro de la administración de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");

    public static class ClassMgr
    {
       /**
       * Returns a list of ObjectBehavior for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.ObjectBehavior for all models
       * @return Iterator of org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.ObjectBehavior
       * @param id Identifier for org.semanticwb.model.ObjectBehavior
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return A org.semanticwb.model.ObjectBehavior
       */
        public static org.semanticwb.model.ObjectBehavior getObjectBehavior(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.ObjectBehavior
       * @param id Identifier for org.semanticwb.model.ObjectBehavior
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return A org.semanticwb.model.ObjectBehavior
       */
        public static org.semanticwb.model.ObjectBehavior createObjectBehavior(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.ObjectBehavior
       * @param id Identifier for org.semanticwb.model.ObjectBehavior
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       */
        public static void removeObjectBehavior(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.ObjectBehavior
       * @param id Identifier for org.semanticwb.model.ObjectBehavior
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return true if the org.semanticwb.model.ObjectBehavior exists, false otherwise
       */

        public static boolean hasObjectBehavior(String id, org.semanticwb.model.SWBModel model)
        {
            return (getObjectBehavior(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined AssMember
       * @param value AssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ThisRoleAssMember
       * @param value ThisRoleAssMember of the type org.semanticwb.model.AssMember
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Child
       * @param value Child of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByMetaTagsValue(org.semanticwb.model.MetaTagValue value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined MetaTagsValue
       * @param value MetaTagsValue of the type org.semanticwb.model.MetaTagValue
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByMetaTagsValue(org.semanticwb.model.MetaTagValue value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasMetaTagsValue,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined ThisTypeAssociation
       * @param value ThisTypeAssociation of the type org.semanticwb.model.Association
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined TemplateRef
       * @param value TemplateRef of the type org.semanticwb.model.TemplateRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Parent
       * @param value Parent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByLanguage(org.semanticwb.model.Language value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_language, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Language
       * @param value Language of the type org.semanticwb.model.Language
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByLanguage(org.semanticwb.model.Language value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_language,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined RuleRef
       * @param value RuleRef of the type org.semanticwb.model.RuleRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined Resource
       * @param value Resource of the type org.semanticwb.model.Resource
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined WebPageVirtualChild
       * @param value WebPageVirtualChild of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined VirtualParent
       * @param value VirtualParent of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined PFlowRef
       * @param value PFlowRef of the type org.semanticwb.model.PFlowRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined UserGroupRef
       * @param value UserGroupRef of the type org.semanticwb.model.UserGroupRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined RoleRef
       * @param value RoleRef of the type org.semanticwb.model.RoleRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined CalendarRef
       * @param value CalendarRef of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @param model Model of the org.semanticwb.model.ObjectBehavior
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByFriendlyURL(org.semanticwb.model.FriendlyURL value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.ObjectBehavior with a determined FriendlyURL
       * @param value FriendlyURL of the type org.semanticwb.model.FriendlyURL
       * @return Iterator with all the org.semanticwb.model.ObjectBehavior
       */

        public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviorByFriendlyURL(org.semanticwb.model.FriendlyURL value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasFriendlyURL,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ObjectBehaviorBase.ClassMgr getObjectBehaviorClassMgr()
    {
        return new ObjectBehaviorBase.ClassMgr();
    }

   /**
   * Constructs a ObjectBehaviorBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ObjectBehavior
   */
    public ObjectBehaviorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the BehaviorParams property
* @return String with the BehaviorParams
*/
    public String getBehaviorParams()
    {
        return getSemanticObject().getProperty(swbxf_behaviorParams);
    }

/**
* Sets the BehaviorParams property
* @param value long with the BehaviorParams
*/
    public void setBehaviorParams(String value)
    {
        getSemanticObject().setProperty(swbxf_behaviorParams, value);
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
* Gets the PropertyFilter property
* @return String with the PropertyFilter
*/
    public String getPropertyFilter()
    {
        return getSemanticObject().getProperty(swbxf_behaviorPropertyFilter);
    }

/**
* Sets the PropertyFilter property
* @param value long with the PropertyFilter
*/
    public void setPropertyFilter(String value)
    {
        getSemanticObject().setProperty(swbxf_behaviorPropertyFilter, value);
    }

/**
* Gets the RefreshOnShow property
* @return boolean with the RefreshOnShow
*/
    public boolean isRefreshOnShow()
    {
        return getSemanticObject().getBooleanProperty(swbxf_behaviorRefreshOnShow);
    }

/**
* Sets the RefreshOnShow property
* @param value long with the RefreshOnShow
*/
    public void setRefreshOnShow(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_behaviorRefreshOnShow, value);
    }

    public void setInterface(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbxf_interface, value);
    }

    public void removeInterface()
    {
        getSemanticObject().removeProperty(swbxf_interface);
    }

/**
* Gets the Interface property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getInterface()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_interface);
         return ret;
    }

/**
* Gets the BehaviorURL property
* @return String with the BehaviorURL
*/
    public String getBehaviorURL()
    {
        return getSemanticObject().getProperty(swbxf_behaviorURL);
    }

/**
* Sets the BehaviorURL property
* @param value long with the BehaviorURL
*/
    public void setBehaviorURL(String value)
    {
        getSemanticObject().setProperty(swbxf_behaviorURL, value);
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
