package org.semanticwb.jcr283.repository.model.base;


public abstract class VersionBase extends org.semanticwb.jcr283.repository.model.Base implements org.semanticwb.jcr283.repository.model.Referenceable
{
       public static final org.semanticwb.platform.SemanticClass nt_Version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
       public static final org.semanticwb.platform.SemanticProperty jcr_successors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#successors");
       public static final org.semanticwb.platform.SemanticProperty jcr_predecessors=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#predecessors");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#version");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersions(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersions()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Version getVersion(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Version)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.jcr283.repository.model.Version createVersion(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.jcr283.repository.model.Version)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeVersion(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasVersion(String id, org.semanticwb.model.SWBModel model)
       {
           return (getVersion(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_parentNode, parentnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByParentNode(org.semanticwb.jcr283.repository.model.Base parentnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(parentnode.getSemanticObject().getModel().listSubjects(swbrep_parentNode,parentnode.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionBySuccessors(org.semanticwb.jcr283.repository.model.Version successors,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_successors, successors.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionBySuccessors(org.semanticwb.jcr283.repository.model.Version successors)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(successors.getSemanticObject().getModel().listSubjects(jcr_successors,successors.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByPredecessors(org.semanticwb.jcr283.repository.model.Version predecessors,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_predecessors, predecessors.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByPredecessors(org.semanticwb.jcr283.repository.model.Version predecessors)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(predecessors.getSemanticObject().getModel().listSubjects(jcr_predecessors,predecessors.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByNode(org.semanticwb.jcr283.repository.model.Base hasnode,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbrep_hasNode, hasnode.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Version> listVersionByNode(org.semanticwb.jcr283.repository.model.Base hasnode)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Version> it=new org.semanticwb.model.GenericIterator(hasnode.getSemanticObject().getModel().listSubjects(swbrep_hasNode,hasnode.getSemanticObject()));
       return it;
   }
    }

    public VersionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setSuccessors(org.semanticwb.jcr283.repository.model.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_successors, value.getSemanticObject());
    }

    public void removeSuccessors()
    {
        getSemanticObject().removeProperty(jcr_successors);
    }


    public org.semanticwb.jcr283.repository.model.Version getSuccessors()
    {
         org.semanticwb.jcr283.repository.model.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_successors);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Version)obj.createGenericInstance();
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

    public void setPredecessors(org.semanticwb.jcr283.repository.model.Version value)
    {
        getSemanticObject().setObjectProperty(jcr_predecessors, value.getSemanticObject());
    }

    public void removePredecessors()
    {
        getSemanticObject().removeProperty(jcr_predecessors);
    }


    public org.semanticwb.jcr283.repository.model.Version getPredecessors()
    {
         org.semanticwb.jcr283.repository.model.Version ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_predecessors);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Version)obj.createGenericInstance();
         }
         return ret;
    }
}
