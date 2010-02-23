package org.semanticwb.repository.base;


public abstract class VersionBase extends org.semanticwb.repository.BaseNode implements org.semanticwb.repository.Referenceable,org.semanticwb.repository.Traceable
{
    public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
    public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersions(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersions()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version>(it, true);
        }

        public static org.semanticwb.repository.Version getVersion(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.repository.Version)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
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

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByParent(org.semanticwb.repository.BaseNode parentnode,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByParent(org.semanticwb.repository.BaseNode parentnode)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionBySuccessors(org.semanticwb.repository.Version successors,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_successors, successors.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionBySuccessors(org.semanticwb.repository.Version successors)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(successors.getSemanticObject().getModel().listSubjects(jcr_successors,successors.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByPredecessors(org.semanticwb.repository.Version predecessors,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_predecessors, predecessors.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByPredecessors(org.semanticwb.repository.Version predecessors)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(predecessors.getSemanticObject().getModel().listSubjects(jcr_predecessors,predecessors.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByNode(org.semanticwb.repository.BaseNode hasnodes,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNodes, hasnodes.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.repository.Version> listVersionByNode(org.semanticwb.repository.BaseNode hasnodes)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.repository.Version> it=new org.semanticwb.model.GenericIterator(hasnodes.getSemanticObject().getModel().listSubjects(swbrep_hasNodes,hasnodes.getSemanticObject()));
            return it;
        }
    }

    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(jcr_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(jcr_created, value);
    }

    public void setSuccessors(org.semanticwb.repository.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_successors, value.getSemanticObject());
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
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }

    public String getUuid()
    {
        return getSemanticObject().getProperty(jcr_uuid);
    }

    public void setUuid(String value)
    {
        getSemanticObject().setProperty(jcr_uuid, value);
    }

    public void setPredecessors(org.semanticwb.repository.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_predecessors, value.getSemanticObject());
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
             ret=(org.semanticwb.repository.Version)obj.createGenericInstance();
         }
         return ret;
    }
}
