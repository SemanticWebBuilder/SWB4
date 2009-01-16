package org.semanticwb.repository.base;


public class VersionBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Traceable,org.semanticwb.repository.Referenceable
{
    public static final org.semanticwb.platform.SemanticProperty jcr_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#created");
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
    public static final org.semanticwb.platform.SemanticProperty jcr_uuid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#uuid");
    public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");

    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
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

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String uuid)
    {
        getSemanticObject().setProperty(jcr_uuid, uuid);
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
