package org.semanticwb.repository.base;


public class WorkspaceBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
    public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
    public static final org.semanticwb.platform.SemanticClass nt_BaseNode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
    public static final org.semanticwb.platform.SemanticProperty jcr_root=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#root");
    public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
    public static final org.semanticwb.platform.SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    public WorkspaceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static org.semanticwb.repository.Workspace getWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Workspace)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace>(org.semanticwb.repository.Workspace.class, it, true);
    }

    public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.repository.Workspace>(org.semanticwb.repository.Workspace.class, it, true);
    }

    public static org.semanticwb.repository.Workspace createWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.repository.Workspace)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasWorkspace(String id, org.semanticwb.model.SWBModel model)
    {
        return (getWorkspace(id, model)!=null);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
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
        return org.semanticwb.repository.Unstructured.getUnstructured(id, this);
    }

    public java.util.Iterator<org.semanticwb.repository.Unstructured> listUnstructureds()
    {
        return org.semanticwb.repository.Unstructured.listUnstructureds(this);
    }

    public org.semanticwb.repository.Unstructured createUnstructured(String id)
    {
        return org.semanticwb.repository.Unstructured.createUnstructured(id,this);
    }

    public org.semanticwb.repository.Unstructured createUnstructured()
    {
        long id=getSemanticObject().getModel().getCounter(nt_Unstructured);
        return org.semanticwb.repository.Unstructured.createUnstructured(String.valueOf(id),this);
    } 

    public void removeUnstructured(String id)
    {
        org.semanticwb.repository.Unstructured.removeUnstructured(id, this);
    }
    public boolean hasUnstructured(String id)
    {
        return org.semanticwb.repository.Unstructured.hasUnstructured(id, this);
    }

    public org.semanticwb.repository.BaseNode getBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.getBaseNode(id, this);
    }

    public java.util.Iterator<org.semanticwb.repository.BaseNode> listBaseNodes()
    {
        return org.semanticwb.repository.BaseNode.listBaseNodes(this);
    }

    public org.semanticwb.repository.BaseNode createBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.createBaseNode(id,this);
    }

    public void removeBaseNode(String id)
    {
        org.semanticwb.repository.BaseNode.removeBaseNode(id, this);
    }
    public boolean hasBaseNode(String id)
    {
        return org.semanticwb.repository.BaseNode.hasBaseNode(id, this);
    }
}
