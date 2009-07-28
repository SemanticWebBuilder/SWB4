package org.semanticwb.portal.resources.sem.directory.base;


public class DirectoryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty dir_classBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#classBase");
    public static final org.semanticwb.platform.SemanticProperty dir_OrderField=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#OrderField");
    public static final org.semanticwb.platform.SemanticProperty dir_scope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#scope");
    public static final org.semanticwb.platform.SemanticProperty dir_properties2Display=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#properties2Display");
    public static final org.semanticwb.platform.SemanticClass dir_Directory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Directory#Directory");

    public DirectoryBase()
    {
    }

    public DirectoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Directory#Directory");

    public void setClassBase(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(dir_classBase, semanticobject);
    }

    public void removeClassBase()
    {
        getSemanticObject().removeProperty(dir_classBase);
    }

    public org.semanticwb.platform.SemanticObject getClassBase()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(dir_classBase);
         return ret;
    }

    public String getOrderField()
    {
        return getSemanticObject().getProperty(dir_OrderField);
    }

    public void setOrderField(String OrderField)
    {
        getSemanticObject().setProperty(dir_OrderField, OrderField);
    }

    public String getScope()
    {
        return getSemanticObject().getProperty(dir_scope);
    }

    public void setScope(String scope)
    {
        getSemanticObject().setProperty(dir_scope, scope);
    }

    public String getProperties2Display()
    {
        return getSemanticObject().getProperty(dir_properties2Display);
    }

    public void setProperties2Display(String properties2Display)
    {
        getSemanticObject().setProperty(dir_properties2Display, properties2Display);
    }
}
