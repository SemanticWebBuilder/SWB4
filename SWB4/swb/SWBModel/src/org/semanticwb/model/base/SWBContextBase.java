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
        return org.semanticwb.model.WebSite.ClassMgr.getWebSite(name);
    }

    public static java.util.Iterator<org.semanticwb.model.WebSite> listWebSites()
    {
        return (java.util.Iterator<org.semanticwb.model.WebSite>)swb_WebSite.listGenericInstances();
    }

    public static void removeWebSite(String name)
    {
        org.semanticwb.model.WebSite.ClassMgr.removeWebSite(name);
    }

    public static org.semanticwb.model.WebSite createWebSite(String name, String namespace)
    {
       return org.semanticwb.model.WebSite.ClassMgr.createWebSite(name, namespace);
    }
    public static final SemanticClass swb_UserRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#UserRepository");

    public static org.semanticwb.model.UserRepository getUserRepository(String name)
    {
        return org.semanticwb.model.UserRepository.ClassMgr.getUserRepository(name);
    }

    public static java.util.Iterator<org.semanticwb.model.UserRepository> listUserRepositories()
    {
        return (java.util.Iterator<org.semanticwb.model.UserRepository>)swb_UserRepository.listGenericInstances();
    }

    public static void removeUserRepository(String name)
    {
        org.semanticwb.model.UserRepository.ClassMgr.removeUserRepository(name);
    }

    public static org.semanticwb.model.UserRepository createUserRepository(String name, String namespace)
    {
       return org.semanticwb.model.UserRepository.ClassMgr.createUserRepository(name, namespace);
    }
}
