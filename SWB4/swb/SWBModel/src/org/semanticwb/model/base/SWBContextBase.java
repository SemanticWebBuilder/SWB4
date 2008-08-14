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
        return (UserRepository)mgr.getOntology().getSemanticObject(uri,vocabulary.UserRepository);
    }

    public static Iterator<org.semanticwb.model.UserRepository> listUserRepositorys()
    {
        return (Iterator<org.semanticwb.model.UserRepository>)vocabulary.UserRepository.listInstances();
    }

    public static void removeUserRepository(String uri)
    {
        mgr.removeModel(uri);
    }

    public static void createUserRepository(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        model.createSemanticObject(name, vocabulary.UserRepository);
    }

    public static WebSite getWebSite(String uri)
    {
        return (WebSite)mgr.getOntology().getSemanticObject(uri,vocabulary.WebSite);
    }

    public static Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (Iterator<org.semanticwb.model.WebSite>)vocabulary.WebSite.listInstances();
    }

    public static void removeWebSite(String uri)
    {
        mgr.removeModel(uri);
    }

    public static void createWebSite(String name, String namespace)
    {
        SemanticModel model=mgr.createModel(name, namespace);
        model.createSemanticObject(name, vocabulary.WebSite);
    }
}
