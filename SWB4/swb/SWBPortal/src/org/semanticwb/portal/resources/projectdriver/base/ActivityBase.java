package org.semanticwb.portal.resources.projectdriver.base;


public abstract class ActivityBase extends org.semanticwb.portal.resources.projectdriver.ActivityContainer implements org.semanticwb.model.FilterableNode,org.semanticwb.model.Searchable,org.semanticwb.model.Rankable,org.semanticwb.portal.resources.projectdriver.Period,org.semanticwb.model.Expirable,org.semanticwb.model.Trashable,org.semanticwb.model.Referensable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Resourceable,org.semanticwb.model.Viewable,org.semanticwb.model.Activeable,org.semanticwb.model.Traceable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Indexable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Undeleteable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Tagable,org.semanticwb.model.Filterable,org.semanticwb.model.PFlowRefable
{
    public static final org.semanticwb.platform.SemanticClass swbproy_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasSuccessorInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasSuccessorInv");
    public static final org.semanticwb.platform.SemanticProperty swbproy_critical=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#critical");
    public static final org.semanticwb.platform.SemanticProperty swbproy_actType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#actType");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty swbproy_responsible=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#responsible");
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasPredecessor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasPredecessor");
    public static final org.semanticwb.platform.SemanticProperty swbproy_status=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#status");
    public static final org.semanticwb.platform.SemanticProperty swbproy_hasParticipants=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/swbproy#hasParticipants");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swbproy#Activity");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(it, true);
        }

        public static org.semanticwb.portal.resources.projectdriver.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.projectdriver.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.projectdriver.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivity(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByUserGroupRef(org.semanticwb.model.UserGroupRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasUserGroupRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityBySuccessor(org.semanticwb.portal.resources.projectdriver.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasSuccessorInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityBySuccessor(org.semanticwb.portal.resources.projectdriver.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasSuccessorInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResponsible(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_responsible, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResponsible(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_responsible,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByVirtualParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByVirtualParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByWebPageVirtualChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByWebPageVirtualChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageVirtualChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPFlowRef(org.semanticwb.model.PFlowRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPFlowRef(org.semanticwb.model.PFlowRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasPFlowRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByTemplateRef(org.semanticwb.model.TemplateRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByTemplateRef(org.semanticwb.model.TemplateRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasTemplateRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByChild(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByChild(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasWebPageChild,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCalendarRef(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCalendarRef(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParent(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParent(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_webPageParent,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasPredecessor, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasPredecessor,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResource(org.semanticwb.model.Resource value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByResource(org.semanticwb.model.Resource value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasResource,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRoleRef(org.semanticwb.model.RoleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRoleRef(org.semanticwb.model.RoleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRoleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisRoleAssMember(org.semanticwb.model.AssMember value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisRoleAssMember(org.semanticwb.model.AssMember value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisRoleAssMemberInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRuleRef(org.semanticwb.model.RuleRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByRuleRef(org.semanticwb.model.RuleRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasRuleRef,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisTypeAssociation(org.semanticwb.model.Association value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByThisTypeAssociation(org.semanticwb.model.Association value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasThisTypeAssociationInv,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParticipants(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasParticipants, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.projectdriver.Activity> listActivityByParticipants(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbproy_hasParticipants,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> listSuccessors()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(getSemanticObject().listObjectProperties(swbproy_hasSuccessorInv));
    }

    public boolean hasSuccessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasSuccessorInv,value.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.portal.resources.projectdriver.Activity getSuccessor()
    {
         org.semanticwb.portal.resources.projectdriver.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasSuccessorInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.projectdriver.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public int getCurrentHour()
    {
        //Override this method in Activity object
        return getSemanticObject().getIntProperty(swbproy_currentHour,false);
    }

    public void setCurrentHour(int value)
    {
        //Override this method in Activity object
        getSemanticObject().setIntProperty(swbproy_currentHour, value,false);
    }

    public boolean isCritical()
    {
        return getSemanticObject().getBooleanProperty(swbproy_critical);
    }

    public void setCritical(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbproy_critical, value);
    }

    public float getCurrentPercentage()
    {
        //Override this method in Activity object
        return getSemanticObject().getFloatProperty(swbproy_currentPercentage,false);
    }

    public void setCurrentPercentage(float value)
    {
        //Override this method in Activity object
        getSemanticObject().setFloatProperty(swbproy_currentPercentage, value,false);
    }

    public String getActType()
    {
        return getSemanticObject().getProperty(swbproy_actType);
    }

    public void setActType(String value)
    {
        getSemanticObject().setProperty(swbproy_actType, value);
    }

    public int getPlannedHour()
    {
        //Override this method in Activity object
        return getSemanticObject().getIntProperty(swbproy_plannedHour,false);
    }

    public void setPlannedHour(int value)
    {
        //Override this method in Activity object
        getSemanticObject().setIntProperty(swbproy_plannedHour, value,false);
    }

    public void setResponsible(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(swbproy_responsible, value.getSemanticObject());
    }

    public void removeResponsible()
    {
        getSemanticObject().removeProperty(swbproy_responsible);
    }

    public org.semanticwb.model.User getResponsible()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_responsible);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getEndDate()
    {
        //Override this method in Activity object
        return getSemanticObject().getDateProperty(swbproy_endDate,false);
    }

    public void setEndDate(java.util.Date value)
    {
        //Override this method in Activity object
        getSemanticObject().setDateProperty(swbproy_endDate, value,false);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity> listPredecessors()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.projectdriver.Activity>(getSemanticObject().listObjectProperties(swbproy_hasPredecessor));
    }

    public boolean hasPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasPredecessor,value.getSemanticObject());
        }
        return ret;
    }

    public void addPredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        getSemanticObject().addObjectProperty(swbproy_hasPredecessor, value.getSemanticObject());
    }

    public void removeAllPredecessor()
    {
        getSemanticObject().removeProperty(swbproy_hasPredecessor);
    }

    public void removePredecessor(org.semanticwb.portal.resources.projectdriver.Activity value)
    {
        getSemanticObject().removeObjectProperty(swbproy_hasPredecessor,value.getSemanticObject());
    }

    public org.semanticwb.portal.resources.projectdriver.Activity getPredecessor()
    {
         org.semanticwb.portal.resources.projectdriver.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasPredecessor);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.projectdriver.Activity)obj.createGenericInstance();
         }
         return ret;
    }

    public java.util.Date getStartDate()
    {
        //Override this method in Activity object
        return getSemanticObject().getDateProperty(swbproy_startDate,false);
    }

    public void setStartDate(java.util.Date value)
    {
        //Override this method in Activity object
        getSemanticObject().setDateProperty(swbproy_startDate, value,false);
    }

    public String getStatus()
    {
        return getSemanticObject().getProperty(swbproy_status);
    }

    public void setStatus(String value)
    {
        getSemanticObject().setProperty(swbproy_status, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.User> listParticipantses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.User>(getSemanticObject().listObjectProperties(swbproy_hasParticipants));
    }

    public boolean hasParticipants(org.semanticwb.model.User value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbproy_hasParticipants,value.getSemanticObject());
        }
        return ret;
    }

    public void addParticipants(org.semanticwb.model.User value)
    {
        getSemanticObject().addObjectProperty(swbproy_hasParticipants, value.getSemanticObject());
    }

    public void removeAllParticipants()
    {
        getSemanticObject().removeProperty(swbproy_hasParticipants);
    }

    public void removeParticipants(org.semanticwb.model.User value)
    {
        getSemanticObject().removeObjectProperty(swbproy_hasParticipants,value.getSemanticObject());
    }

    public org.semanticwb.model.User getParticipants()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbproy_hasParticipants);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.portal.resources.projectdriver.ProjectSite getProjectSite()
    {
        return (org.semanticwb.portal.resources.projectdriver.ProjectSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
