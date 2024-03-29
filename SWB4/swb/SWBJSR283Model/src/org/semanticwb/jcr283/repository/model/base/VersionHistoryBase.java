package org.semanticwb.jcr283.repository.model.base;


public abstract class VersionHistoryBase extends org.semanticwb.jcr283.repository.model.Base implements org.semanticwb.jcr283.repository.model.Referenceable
{
       public static final org.semanticwb.platform.SemanticClass xsd_String=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.w3.org/2001/XMLSchema#string");
       public static final org.semanticwb.platform.SemanticProperty jcr_copiedFrom=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#copiedFrom");
       public static final org.semanticwb.platform.SemanticProperty jcr_versionableUuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#versionableUuid");
       public static final org.semanticwb.platform.SemanticClass nt_VersionHistory=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#versionHistory");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistories(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistories()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.VersionHistory getVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.VersionHistory)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.VersionHistory createVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.VersionHistory)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasVersionHistory(String id, org.semanticwb.model.SWBModel model)
       {
           return (getVersionHistory(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistoryByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistoryByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistoryByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.VersionHistory> listVersionHistoryByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.VersionHistory> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public VersionHistoryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getCopiedFrom()
    {
        return getSemanticObject().getProperty(jcr_copiedFrom);
    }

    public void setCopiedFrom(String value)
    {
        getSemanticObject().setProperty(jcr_copiedFrom, value);
    }

    public String getVersionableUuid()
    {
        return getSemanticObject().getProperty(jcr_versionableUuid);
    }

    public void setVersionableUuid(String value)
    {
        getSemanticObject().setProperty(jcr_versionableUuid, value);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }
}
