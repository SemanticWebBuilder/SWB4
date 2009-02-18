package org.semanticwb.repository.base;


public class VersionBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
    public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");

    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.Version getVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.Version> listVersions(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(org.semanticwb.repository.Version.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.Version> listVersions()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(org.semanticwb.repository.Version.class, it, true);
    }

    public static org.semanticwb.repository.Version createVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeVersion(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasVersion(String id, org.semanticwb.model.SWBModel model)
    {
        return (getVersion(id, model)!=null);
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    public void setCreated(java.util.Date created)
    {
        getSemanticObject().setDateProperty(jcr_created, created);
    }

    public void setSuccessors(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_successors, version.getSemanticObject());
    }

    public void removeSuccessors()
    {
        getSemanticObject().removeProperty(jcr_successors);
    }

    public org.semanticwb.repository.Version getSuccessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_successors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public void setPredecessors(org.semanticwb.repository.Version version)
    {
        getSemanticObject().setObjectProperty(jcr_predecessors, version.getSemanticObject());
    }

    public void removePredecessors()
    {
        getSemanticObject().removeProperty(jcr_predecessors);
    }

    public org.semanticwb.repository.Version getPredecessors()
    {
         org.semanticwb.repository.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_predecessors);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.Version)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }
}
