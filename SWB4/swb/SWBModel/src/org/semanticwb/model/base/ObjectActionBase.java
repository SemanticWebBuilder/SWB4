package org.semanticwb.model.base;


public class ObjectActionBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.PFlowRefable,org.semanticwb.model.Deleteable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Iconable,org.semanticwb.model.RoleRefable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Viewable,org.semanticwb.model.Referensable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Indexable,org.semanticwb.model.Calendarable,org.semanticwb.model.Portletable,org.semanticwb.model.TemplateRefable
{
    public static final org.semanticwb.platform.SemanticProperty swbxf_actGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_actionURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#actionURL");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectAction=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectAction");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectAction");

    public ObjectActionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.ObjectAction getObjectAction(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectAction)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectAction> listObjectActions(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectAction>(org.semanticwb.model.ObjectAction.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectAction> listObjectActions()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectAction>(org.semanticwb.model.ObjectAction.class, it, true);
    }

    public static org.semanticwb.model.ObjectAction createObjectAction(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectAction)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeObjectAction(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasObjectAction(String id, org.semanticwb.model.SWBModel model)
    {
        return (getObjectAction(id, model)!=null);
    }

    public String getActGroup()
    {
        return getSemanticObject().getProperty(swbxf_actGroup);
    }

    public void setActGroup(String actGroup)
    {
        getSemanticObject().setProperty(swbxf_actGroup, actGroup);
    }

    public String getActionURL()
    {
        return getSemanticObject().getProperty(swbxf_actionURL);
    }

    public void setActionURL(String actionURL)
    {
        getSemanticObject().setProperty(swbxf_actionURL, actionURL);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }
}
