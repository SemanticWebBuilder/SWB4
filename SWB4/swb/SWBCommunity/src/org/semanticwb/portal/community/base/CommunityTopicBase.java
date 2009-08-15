package org.semanticwb.portal.community.base;


public class CommunityTopicBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Hiddenable,org.semanticwb.model.Resourceable,org.semanticwb.model.RuleRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Expirable,org.semanticwb.model.Indexable,org.semanticwb.model.Rankable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Referensable,org.semanticwb.model.Viewable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.Trashable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_CommunityTopic=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityTopic");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CommunityTopic");

    public CommunityTopicBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.CommunityTopic> listCommunityTopics(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CommunityTopic>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.CommunityTopic> listCommunityTopics()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.CommunityTopic>(it, true);
    }

    public static org.semanticwb.portal.community.CommunityTopic getCommunityTopic(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.CommunityTopic)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.CommunityTopic createCommunityTopic(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.CommunityTopic)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeCommunityTopic(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasCommunityTopic(String id, org.semanticwb.model.SWBModel model)
    {
        return (getCommunityTopic(id, model)!=null);
    }
}
