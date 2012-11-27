/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
    private static org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
    public static final SemanticClass swb_AdminWebSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#AdminWebSite");

    public static org.semanticwb.model.AdminWebSite getAdminWebSite(String name)
    {
        return org.semanticwb.model.AdminWebSite.ClassMgr.getAdminWebSite(name);
    }

    public static java.util.Iterator<org.semanticwb.model.AdminWebSite> listAdminWebSites()
    {
        return (java.util.Iterator<org.semanticwb.model.AdminWebSite>)swb_AdminWebSite.listGenericInstances();
    }

    public static void removeAdminWebSite(String name)
    {
        org.semanticwb.model.AdminWebSite.ClassMgr.removeAdminWebSite(name);
    }

    public static org.semanticwb.model.AdminWebSite createAdminWebSite(String name, String namespace)
    {
       return org.semanticwb.model.AdminWebSite.ClassMgr.createAdminWebSite(name, namespace);
    }
    public static final SemanticClass swb_Ontology=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Ontology");

    public static org.semanticwb.model.Ontology getOntology(String name)
    {
        return org.semanticwb.model.Ontology.ClassMgr.getOntology(name);
    }

    public static java.util.Iterator<org.semanticwb.model.Ontology> listOntologies()
    {
        return (java.util.Iterator<org.semanticwb.model.Ontology>)swb_Ontology.listGenericInstances();
    }

    public static void removeOntology(String name)
    {
        org.semanticwb.model.Ontology.ClassMgr.removeOntology(name);
    }

    public static org.semanticwb.model.Ontology createOntology(String name, String namespace)
    {
       return org.semanticwb.model.Ontology.ClassMgr.createOntology(name, namespace);
    }
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
    public static final SemanticClass swbrep_Workspace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/repository#Workspace");

    public static org.semanticwb.repository.Workspace getWorkspace(String name)
    {
        return org.semanticwb.repository.Workspace.ClassMgr.getWorkspace(name);
    }

    public static java.util.Iterator<org.semanticwb.repository.Workspace> listWorkspaces()
    {
        return (java.util.Iterator<org.semanticwb.repository.Workspace>)swbrep_Workspace.listGenericInstances();
    }

    public static void removeWorkspace(String name)
    {
        org.semanticwb.repository.Workspace.ClassMgr.removeWorkspace(name);
    }

    public static org.semanticwb.repository.Workspace createWorkspace(String name, String namespace)
    {
       return org.semanticwb.repository.Workspace.ClassMgr.createWorkspace(name, namespace);
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
