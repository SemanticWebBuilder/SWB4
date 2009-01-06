package org.semanticwb.repository.base;


public class WorkspaceBase extends org.semanticwb.model.base.GenericObjectBase 
{
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static final org.semanticwb.platform.SemanticProperty jcr_root=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#root");
    public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
    public static final org.semanticwb.platform.SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    public WorkspaceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setRoot(org.semanticwb.repository.BaseNode basenode)
    {
        getSemanticObject().setObjectProperty(jcr_root, basenode.getSemanticObject());
    }

    public void removeRoot()
    {
        getSemanticObject().removeProperty(jcr_root);
    }

    public org.semanticwb.repository.BaseNode getRoot()
    {
         org.semanticwb.repository.BaseNode ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_root);
         if(obj!=null)
         {
             ret=(org.semanticwb.repository.BaseNode)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public org.semanticwb.repository.Unstructured getUnstructured(String id)
    {
        return (org.semanticwb.repository.Unstructured)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,nt_Unstructured),nt_Unstructured);
    }

    public java.util.Iterator<org.semanticwb.repository.Unstructured> listUnstructureds()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, nt_Unstructured.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Unstructured>(org.semanticwb.repository.Unstructured.class, stit, true);
    }

    public org.semanticwb.repository.Unstructured createUnstructured(String id)
    {
        return (org.semanticwb.repository.Unstructured)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, nt_Unstructured), nt_Unstructured);
    }

    public org.semanticwb.repository.Unstructured createUnstructured()
    {
        long id=org.semanticwb.SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+"/"+nt_Unstructured.getName());
        return createUnstructured(""+id);
    } 

    public void removeUnstructured(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,nt_Unstructured));
    }
    public boolean hasUnstructured(String id)
    {
        return (getUnstructured(id)!=null);
    }

    public org.semanticwb.repository.BaseNode getBaseNode(String id)
    {
        return (org.semanticwb.repository.BaseNode)getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,nt_BaseNode),nt_BaseNode);
    }

    public java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes()
    {
        com.hp.hpl.jena.rdf.model.Property rdf=getSemanticObject().getModel().getRDFModel().getProperty( org.semanticwb.platform.SemanticVocabulary.RDF_TYPE);
        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, nt_BaseNode.getOntClass());
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.BaseNode>(org.semanticwb.repository.BaseNode.class, stit, true);
    }

    public org.semanticwb.repository.BaseNode createBaseNode(String id)
    {
        return (org.semanticwb.repository.BaseNode)getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, nt_BaseNode), nt_BaseNode);
    }

    public void removeBaseNode(String id)
    {
        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,nt_BaseNode));
    }
    public boolean hasBaseNode(String id)
    {
        return (getBaseNode(id)!=null);
    }
}
