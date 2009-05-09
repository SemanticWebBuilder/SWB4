package org.semanticwb.portal.resources.sem.directory.base;


public class DirectoryBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty dir_classBase=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#classBase");
    public static final org.semanticwb.platform.SemanticProperty dir_range=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Directory#range");
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

    public String getRange()
    {
        return getSemanticObject().getProperty(dir_range);
    }

    public void setRange(String range)
    {
        getSemanticObject().setProperty(dir_range, range);
    }
}
