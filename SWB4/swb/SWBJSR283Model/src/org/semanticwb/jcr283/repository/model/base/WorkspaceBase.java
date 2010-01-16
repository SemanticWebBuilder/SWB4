package org.semanticwb.jcr283.repository.model.base;


public abstract class WorkspaceBase extends org.semanticwb.model.SWBModel implements org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass nt_Base=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#base");
       public static final org.semanticwb.platform.SemanticProperty jcr_root=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.jcp.org/jcr/1.0#root");
       public static final org.semanticwb.platform.SemanticClass nt_Unstructured=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.jcp.org/jcr/nt/1.0#unstructured");
       public static final org.semanticwb.platform.SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/jcr283#Workspace");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/jcr283#Workspace");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaces(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaces()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace>(it, true);
       }

       public static org.semanticwb.jcr283.repository.model.Workspace getWorkspace(String id)
       {
           org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
           org.semanticwb.jcr283.repository.model.Workspace ret=null;
           org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
           if(model!=null)
           {
               org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
               if(obj!=null)
               {
                   ret=(org.semanticwb.jcr283.repository.model.Workspace)obj.createGenericInstance();
               }
           }
           return ret;
       }

       public static org.semanticwb.jcr283.repository.model.Workspace createWorkspace(String id, String namespace)
       {
           org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
           org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
           return (org.semanticwb.jcr283.repository.model.Workspace)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
       }

       public static void removeWorkspace(String id)
       {
           org.semanticwb.jcr283.repository.model.Workspace obj=getWorkspace(id);
           if(obj!=null)
           {
               obj.remove();
           }
       }

       public static boolean hasWorkspace(String id)
       {
           return (getWorkspace(id)!=null);
       }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaceByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaceByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaceByRoot(org.semanticwb.jcr283.repository.model.Base root,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(jcr_root, root.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.jcr283.repository.model.Workspace> listWorkspaceByRoot(org.semanticwb.jcr283.repository.model.Base root)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.jcr283.repository.model.Workspace> it=new org.semanticwb.model.GenericIterator(root.getSemanticObject().getModel().listSubjects(jcr_root,root.getSemanticObject()));
       return it;
   }
    }

    public WorkspaceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
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

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
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

    public void setRoot(org.semanticwb.jcr283.repository.model.Base value)
    {
        getSemanticObject().setObjectProperty(jcr_root, value.getSemanticObject());
    }

    public void removeRoot()
    {
        getSemanticObject().removeProperty(jcr_root);
    }


    public org.semanticwb.jcr283.repository.model.Base getRoot()
    {
         org.semanticwb.jcr283.repository.model.Base ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(jcr_root);
         if(obj!=null)
         {
             ret=(org.semanticwb.jcr283.repository.model.Base)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.jcr283.repository.model.Base getBase(String id)
    {
        return org.semanticwb.jcr283.repository.model.Base.ClassMgr.getBase(id, this);
    }

    public java.util.Iterator<org.semanticwb.jcr283.repository.model.Base> listBases()
    {
        return org.semanticwb.jcr283.repository.model.Base.ClassMgr.listBases(this);
    }

    public org.semanticwb.jcr283.repository.model.Base createBase(String id)
    {
        return org.semanticwb.jcr283.repository.model.Base.ClassMgr.createBase(id,this);
    }

    public void removeBase(String id)
    {
        org.semanticwb.jcr283.repository.model.Base.ClassMgr.removeBase(id, this);
    }
    public boolean hasBase(String id)
    {
        return org.semanticwb.jcr283.repository.model.Base.ClassMgr.hasBase(id, this);
    }

    public org.semanticwb.jcr283.repository.model.Unstructured getUnstructured(String id)
    {
        return org.semanticwb.jcr283.repository.model.Unstructured.ClassMgr.getUnstructured(id, this);
    }

    public java.util.Iterator<org.semanticwb.jcr283.repository.model.Unstructured> listUnstructureds()
    {
        return org.semanticwb.jcr283.repository.model.Unstructured.ClassMgr.listUnstructureds(this);
    }

    public org.semanticwb.jcr283.repository.model.Unstructured createUnstructured(String id)
    {
        return org.semanticwb.jcr283.repository.model.Unstructured.ClassMgr.createUnstructured(id,this);
    }

    public void removeUnstructured(String id)
    {
        org.semanticwb.jcr283.repository.model.Unstructured.ClassMgr.removeUnstructured(id, this);
    }
    public boolean hasUnstructured(String id)
    {
        return org.semanticwb.jcr283.repository.model.Unstructured.ClassMgr.hasUnstructured(id, this);
    }
}
