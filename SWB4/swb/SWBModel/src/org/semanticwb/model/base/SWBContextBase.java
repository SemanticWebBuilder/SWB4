package org.semanticwb.model.base;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBInstance;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
public class SWBContextBase
{
    private static SWBVocabulary vocabulary=new SWBVocabulary();
    private static SemanticMgr mgr=SWBInstance.getSemanticMgr();
    public static SWBVocabulary getVocabulary()
    {
        return vocabulary;
    }

    public static UserRepository getUserRepository(String uri)
    {
        SemanticObject obj=mgr.getOntology().getSemanticObject(uri);
        return (UserRepository)new UserRepository(obj);
    }

    public static Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (Iterator<org.semanticwb.model.UserRepository>)vocabulary.UserRepository.listGenericInstances();
    }

    public static void removeUserRepository(String uri)
    {
        mgr.removeModel(uri);
    }

    public static UserRepository createUserRepository(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (UserRepository)model.createGenericObject(name, vocabulary.UserRepository);
    }

    public static WebSite getWebSite(String uri)
    {
        SemanticObject obj=mgr.getOntology().getSemanticObject(uri);
        return (WebSite)new WebSite(obj);
    }

    public static Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (Iterator<org.semanticwb.model.WebSite>)vocabulary.WebSite.listGenericInstances();
    }

    public static void removeWebSite(String uri)
    {
        mgr.removeModel(uri);
    }

    public static WebSite createWebSite(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        return (WebSite)model.createGenericObject(name, vocabulary.WebSite);
    }
}
