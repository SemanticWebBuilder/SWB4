package org.semanticwb.portal.resources.sem.base;


public class HTMLContentBase extends org.semanticwb.portal.resources.sem.Content implements org.semanticwb.model.Versionable,org.semanticwb.model.ResourceVersionable
{
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swb_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#actualVersion");
    public static final org.semanticwb.platform.SemanticProperty swb_lastVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#lastVersion");
    public static final org.semanticwb.platform.SemanticClass swbres_HTMLContent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    public HTMLContentBase()
    {
    }

    public HTMLContentBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/resource/ontology#HTMLContent");

    public void setActualVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_actualVersion, versioninfo.getSemanticObject());
    }

    public void removeActualVersion()
    {
        getSemanticObject().removeProperty(swb_actualVersion);
    }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByActualVersion(org.semanticwb.model.VersionInfo actualversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_actualVersion, actualversion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByActualVersion(org.semanticwb.model.VersionInfo actualversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(actualversion.getSemanticObject().getModel().listSubjects(swb_actualVersion,actualversion.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.VersionInfo getActualVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_actualVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

    public void setLastVersion(org.semanticwb.model.VersionInfo versioninfo)
    {
        getSemanticObject().setObjectProperty(swb_lastVersion, versioninfo.getSemanticObject());
    }

    public void removeLastVersion()
    {
        getSemanticObject().removeProperty(swb_lastVersion);
    }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByLastVersion(org.semanticwb.model.VersionInfo lastversion,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_lastVersion, lastversion.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.resources.sem.HTMLContent> listHTMLContentByLastVersion(org.semanticwb.model.VersionInfo lastversion)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.HTMLContent> it=new org.semanticwb.model.GenericIterator(lastversion.getSemanticObject().getModel().listSubjects(swb_lastVersion,lastversion.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.VersionInfo getLastVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_lastVersion);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }
}
