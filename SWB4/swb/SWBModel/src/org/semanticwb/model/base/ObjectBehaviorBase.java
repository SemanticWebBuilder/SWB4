package org.semanticwb.model.base;


public class ObjectBehaviorBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Iconable,org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    public static final org.semanticwb.platform.SemanticProperty swb_iconClass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#iconClass");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorRefreshOnShow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorRefreshOnShow");
    public static final org.semanticwb.platform.SemanticClass swb_Interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Interface");
    public static final org.semanticwb.platform.SemanticProperty swbxf_interface=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#interface");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorURL");
    public static final org.semanticwb.platform.SemanticClass swbxf_ResourceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ResourceParameter");
    public static final org.semanticwb.platform.SemanticProperty swbxf_hasResourceParam=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#hasResourceParam");
    public static final org.semanticwb.platform.SemanticProperty swbxf_behaviorParams=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#behaviorParams");
    public static final org.semanticwb.platform.SemanticClass swbxf_ObjectBehavior=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#ObjectBehavior");

    public ObjectBehaviorBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(swb_index, index);
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

    public org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceParameter> listParams()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.ResourceParameter>(org.semanticwb.model.ResourceParameter.class, getSemanticObject().listObjectProperties(swbxf_hasResourceParam));    }

    public void addParam(org.semanticwb.model.ResourceParameter resourceparameter)
    {
        getSemanticObject().addObjectProperty(swbxf_hasResourceParam, resourceparameter.getSemanticObject());
    }

    public void removeAllParam()
    {
        getSemanticObject().removeProperty(swbxf_hasResourceParam);
    }

    public void removeParam(org.semanticwb.model.ResourceParameter resourceparameter)
    {
        getSemanticObject().removeObjectProperty(swbxf_hasResourceParam,resourceparameter.getSemanticObject());
    }

    public org.semanticwb.model.ResourceParameter getParam()
    {
         org.semanticwb.model.ResourceParameter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_hasResourceParam);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.ResourceParameter)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
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
