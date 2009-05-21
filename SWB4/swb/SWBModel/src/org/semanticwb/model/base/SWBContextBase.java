package org.semanticwb.model.base;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticClass;
public class SWBContextBase
{
    private static SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
    public static final SemanticClass swb_WebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebSite");

    public static org.semanticwb.model.WebSite getWebSite(String name)
    {
        return org.semanticwb.model.WebSite.getWebSite(name);
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (java.util.Iterator<org.semanticwb.model.WebSite>)swb_WebSite.listGenericInstances();
    }

    public static void removeWebSite(String name)
    {
        org.semanticwb.model.WebSite.removeWebSite(name);
    }

    public static org.semanticwb.model.WebSite createWebSite(String name, String namespace)
    {
       return org.semanticwb.model.WebSite.createWebSite(name, namespace);
    }
    public static final SemanticClass swbc_Catalogs=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwb.org/swbcatalogs#Catalogs");

    public static org.semanticwb.model.catalogs.Catalogs getCatalogs(String name)
    {
        return org.semanticwb.model.catalogs.Catalogs.getCatalogs(name);
    }

    public static java.util.Iterator<org.semanticwb.model.catalogs.Catalogs> listCatalogss()
    {
        return (java.util.Iterator<org.semanticwb.model.catalogs.Catalogs>)swbc_Catalogs.listGenericInstances();
    }

    public static void removeCatalogs(String name)
    {
        org.semanticwb.model.catalogs.Catalogs.removeCatalogs(name);
    }

    public static org.semanticwb.model.catalogs.Catalogs createCatalogs(String name, String namespace)
    {
       return org.semanticwb.model.catalogs.Catalogs.createCatalogs(name, namespace);
    }
    public static final SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public static org.semanticwb.model.UserRepository getUserRepository(String name)
    {
        return org.semanticwb.model.UserRepository.getUserRepository(name);
    }

    public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (java.util.Iterator<org.semanticwb.model.UserRepository>)swb_UserRepository.listGenericInstances();
    }

    public static void removeUserRepository(String name)
    {
        org.semanticwb.model.UserRepository.removeUserRepository(name);
    }

    public static org.semanticwb.model.UserRepository createUserRepository(String name, String namespace)
    {
       return org.semanticwb.model.UserRepository.createUserRepository(name, namespace);
    }
    public static final SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    public static org.semanticwb.repository.Workspace getWorkspace(String name)
    {
        return org.semanticwb.repository.Workspace.getWorkspace(name);
    }

    public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces()
    {
        return (java.util.Iterator<org.semanticwb.repository.Workspace>)swbrep_Workspace.listGenericInstances();
    }

    public static void removeWorkspace(String name)
    {
        org.semanticwb.repository.Workspace.removeWorkspace(name);
    }

    public static org.semanticwb.repository.Workspace createWorkspace(String name, String namespace)
    {
       return org.semanticwb.repository.Workspace.createWorkspace(name, namespace);
    }
}
