package org.semanticwb.model.base;


public class ObjectBehaviorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Iconable,org.semanticwb.model.Viewable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Activeable,org.semanticwb.model.Referensable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.RoleRefable,org.semanticwb.model.Portletable,org.semanticwb.model.Calendarable,org.semanticwb.model.Traceable,org.semanticwb.model.Deleteable,org.semanticwb.model.Indexable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.PFlowRefable
{
    public static final org.semanticwb.platform.SemanticClass owl_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2002/07/owl#Class");
    public static final org.semanticwb.platform.SemanticProperty swbxf_interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#interface");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorURL");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorRefreshOnShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorRefreshOnShow");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorParams=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorParams");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");

    public ObjectBehaviorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.model.ObjectBehavior getObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(org.semanticwb.model.ObjectBehavior.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.model.ObjectBehavior> listObjectBehaviors()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ObjectBehavior>(org.semanticwb.model.ObjectBehavior.class, it, true);
    }

    public static org.semanticwb.model.ObjectBehavior createObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.model.ObjectBehavior)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasObjectBehavior(String id, org.semanticwb.model.SWBModel model)
    {
        return (getObjectBehavior(id, model)!=null);
    }

    public void setInterface(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(swbxf_interface, semanticobject);
    }

    public void removeInterface()
    {
        getSemanticObject().removeProperty(swbxf_interface);
    }

    public org.semanticwb.platform.SemanticObject getInterface()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_interface);
         return ret;
    }

    public String getBehaviorURL()
    {
        return getSemanticObject().getProperty(swbxf_behaviorURL);
    }

    public void setBehaviorURL(String behaviorURL)
    {
        getSemanticObject().setProperty(swbxf_behaviorURL, behaviorURL);
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(swb_iconClass, iconClass);
    }

    public boolean isRefreshOnShow()
    {
        return getSemanticObject().getBooleanProperty(swbxf_behaviorRefreshOnShow);
    }

    public void setRefreshOnShow(boolean behaviorRefreshOnShow)
    {
        getSemanticObject().setBooleanProperty(swbxf_behaviorRefreshOnShow, behaviorRefreshOnShow);
    }

    public String getBehaviorParams()
    {
        return getSemanticObject().getProperty(swbxf_behaviorParams);
    }

    public void setBehaviorParams(String behaviorParams)
    {
        getSemanticObject().setProperty(swbxf_behaviorParams, behaviorParams);
    }
}
