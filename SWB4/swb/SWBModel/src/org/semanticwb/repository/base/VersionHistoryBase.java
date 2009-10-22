package org.semanticwb.repository.base;


public class VersionHistoryBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty swbrep_parentNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#parentNode");
       public static final org.semanticwb.platform.SemanticProperty jcr_primaryType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#primaryType");
       public static final org.semanticwb.platform.SemanticProperty jcr_versionableUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionableUuid");
       public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
       public static final org.semanticwb.platform.SemanticProperty swbrep_path=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#path");
       public static final org.semanticwb.platform.SemanticProperty swbrep_name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#name");
       public static final org.semanticwb.platform.SemanticProperty swbrep_hasNodes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/repository#hasNodes");
       public static final org.semanticwb.platform.SemanticProperty jcr_mixinTypes=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#mixinTypes");
       public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");

       public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistories(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.repository.VersionHistory> listVersionHistories()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.VersionHistory>(it, true);
       }

       public static org.semanticwb.repository.VersionHistory getVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.repository.VersionHistory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.repository.VersionHistory createVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.repository.VersionHistory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (getVersionHistory(id, model)!=null);
       }
    }

    public VersionHistoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getVersionableUuid()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_versionableUuid);
    }

    public void setVersionableUuid(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_versionableUuid, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(ClassMgr.jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(ClassMgr.jcr_uuid, value);
    }
}
