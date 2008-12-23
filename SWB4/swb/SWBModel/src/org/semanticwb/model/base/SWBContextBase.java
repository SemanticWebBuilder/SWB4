package org.semanticwb.model.base;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.repository.File;
import org.semanticwb.repository.FrozenNode;
import org.semanticwb.repository.Unstructured;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.office.OfficeContent;
import org.semanticwb.repository.HierarchyNode;
import org.semanticwb.repository.VersionLabels;
import org.semanticwb.repository.VersionHistory;
import org.semanticwb.repository.Resource;
import org.semanticwb.repository.office.OfficeCategory;
import org.semanticwb.repository.Folder;
import org.semanticwb.repository.Version;
import org.semanticwb.repository.Workspace;
public class SWBContextBase
{
    private static SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
    public static final SemanticClass swb_SWBModel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBModel");

    public static SWBModel getSWBModel(String uri)
    {
        org.semanticwb.model.SWBModel ret=null;
        SemanticModel model=mgr.getModel(uri);
        if(model!=null)
        {
            org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(uri);
            if(obj!=null)
            {
                ret=(org.semanticwb.model.SWBModel)new SWBModel(obj);
            }
        }
        return ret;
    }

    public static java.util.Iterator<org.semanticwb.model.SWBModel> listSWBModels()
    {
        return (java.util.Iterator<org.semanticwb.model.SWBModel>)swb_SWBModel.listGenericInstances();
    }

    public static void removeSWBModel(String uri)
    {
        mgr.removeModel(uri);
    }

    public static SWBModel createSWBModel(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (SWBModel)model.createGenericObject(name, swb_SWBModel);
    }
    public static final SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    public static WebSite getWebSite(String uri)
    {
        org.semanticwb.model.WebSite ret=null;
        SemanticModel model=mgr.getModel(uri);
        if(model!=null)
        {
            org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(uri);
            if(obj!=null)
            {
                ret=(org.semanticwb.model.WebSite)new WebSite(obj);
            }
        }
        return ret;
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (java.util.Iterator<org.semanticwb.model.WebSite>)swb_WebSite.listGenericInstances();
    }

    public static void removeWebSite(String uri)
    {
        mgr.removeModel(uri);
    }

    public static WebSite createWebSite(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (WebSite)model.createGenericObject(name, swb_WebSite);
    }
    public static final SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public static UserRepository getUserRepository(String uri)
    {
        org.semanticwb.model.UserRepository ret=null;
        SemanticModel model=mgr.getModel(uri);
        if(model!=null)
        {
            org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(uri);
            if(obj!=null)
            {
                ret=(org.semanticwb.model.UserRepository)new UserRepository(obj);
            }
        }
        return ret;
    }

    public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (java.util.Iterator<org.semanticwb.model.UserRepository>)swb_UserRepository.listGenericInstances();
    }

    public static void removeUserRepository(String uri)
    {
        mgr.removeModel(uri);
    }

    public static UserRepository createUserRepository(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (UserRepository)model.createGenericObject(name, swb_UserRepository);
    }
    public static final SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    public static Workspace getWorkspace(String uri)
    {
        org.semanticwb.repository.Workspace ret=null;
        SemanticModel model=mgr.getModel(uri);
        if(model!=null)
        {
            org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(uri);
            if(obj!=null)
            {
                ret=(org.semanticwb.repository.Workspace)new Workspace(obj);
            }
        }
        return ret;
    }

    public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces()
    {
        return (java.util.Iterator<org.semanticwb.repository.Workspace>)swbrep_Workspace.listGenericInstances();
    }

    public static void removeWorkspace(String uri)
    {
        mgr.removeModel(uri);
    }

    public static Workspace createWorkspace(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (Workspace)model.createGenericObject(name, swbrep_Workspace);
    }
}
