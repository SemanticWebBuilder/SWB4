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
package org.semanticwb.portal.community.base;


public abstract class MicrositeContainerBase extends org.semanticwb.model.WebSite implements org.semanticwb.model.Trashable,org.semanticwb.model.Localeable,org.semanticwb.model.Indexable,org.semanticwb.model.Traceable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_OrganizationComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#OrganizationComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticClass swbcomm_CompanySite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#CompanySite");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteUtil");
    public static final org.semanticwb.platform.SemanticClass swbcomm_PersonalComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#PersonalComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Community=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Community");
    public static final org.semanticwb.platform.SemanticClass swbcomm_TematicComm=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#TematicComm");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteElement");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicrositeContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicrositeContainer");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer>(it, true);
        }

        public static org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer(String id)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.portal.community.MicrositeContainer ret=null;
            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);
            if(model!=null)
            {
                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));
                if(obj!=null)
                {
                    ret=(org.semanticwb.portal.community.MicrositeContainer)obj.createGenericInstance();
                }
            }
            return ret;
        }

        public static org.semanticwb.portal.community.MicrositeContainer createMicrositeContainer(String id, String namespace)
        {
            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();
            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);
            return (org.semanticwb.portal.community.MicrositeContainer)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
        }

        public static void removeMicrositeContainer(String id)
        {
            org.semanticwb.portal.community.MicrositeContainer obj=getMicrositeContainer(id);
            if(obj!=null)
            {
                obj.remove();
            }
        }

        public static boolean hasMicrositeContainer(String id)
        {
            return (getMicrositeContainer(id)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByLanguage(org.semanticwb.model.Language language,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_language, language.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByLanguage(org.semanticwb.model.Language language)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(language.getSemanticObject().getModel().listSubjects(swb_language,language.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerBySubModel(org.semanticwb.model.SWBModel hassubmodel,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasSubModel, hassubmodel.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerBySubModel(org.semanticwb.model.SWBModel hassubmodel)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(hassubmodel.getSemanticObject().getModel().listSubjects(swb_hasSubModel,hassubmodel.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByParentWebSite(org.semanticwb.model.WebSite parentwebsite,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_parentWebSite, parentwebsite.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByParentWebSite(org.semanticwb.model.WebSite parentwebsite)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(parentwebsite.getSemanticObject().getModel().listSubjects(swb_parentWebSite,parentwebsite.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByUserRepository(org.semanticwb.model.UserRepository userrepository,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_userRepository, userrepository.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByUserRepository(org.semanticwb.model.UserRepository userrepository)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(userrepository.getSemanticObject().getModel().listSubjects(swb_userRepository,userrepository.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByHomePage(org.semanticwb.model.WebPage homepage,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_homePage, homepage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByHomePage(org.semanticwb.model.WebPage homepage)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(homepage.getSemanticObject().getModel().listSubjects(swb_homePage,homepage.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByDefaultTemplate(org.semanticwb.model.Template defaulttemplate,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_defaultTemplate, defaulttemplate.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicrositeContainer> listMicrositeContainerByDefaultTemplate(org.semanticwb.model.Template defaulttemplate)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicrositeContainer> it=new org.semanticwb.model.GenericIterator(defaulttemplate.getSemanticObject().getModel().listSubjects(swb_defaultTemplate,defaulttemplate.getSemanticObject()));
            return it;
        }
    }

    public MicrositeContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.portal.community.OrganizationComm getOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.getOrganizationComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.OrganizationComm> listOrganizationComms()
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.listOrganizationComms(this);
    }

    public org.semanticwb.portal.community.OrganizationComm createOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.createOrganizationComm(id,this);
    }

    public void removeOrganizationComm(String id)
    {
        org.semanticwb.portal.community.OrganizationComm.ClassMgr.removeOrganizationComm(id, this);
    }
    public boolean hasOrganizationComm(String id)
    {
        return org.semanticwb.portal.community.OrganizationComm.ClassMgr.hasOrganizationComm(id, this);
    }

    public org.semanticwb.portal.community.MicroSite getMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.getMicroSite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites()
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.listMicroSites(this);
    }

    public org.semanticwb.portal.community.MicroSite createMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.createMicroSite(id,this);
    }

    public void removeMicroSite(String id)
    {
        org.semanticwb.portal.community.MicroSite.ClassMgr.removeMicroSite(id, this);
    }
    public boolean hasMicroSite(String id)
    {
        return org.semanticwb.portal.community.MicroSite.ClassMgr.hasMicroSite(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteType getMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.getMicroSiteType(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteType> listMicroSiteTypes()
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.listMicroSiteTypes(this);
    }

    public org.semanticwb.portal.community.MicroSiteType createMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.createMicroSiteType(id,this);
    }

    public void removeMicroSiteType(String id)
    {
        org.semanticwb.portal.community.MicroSiteType.ClassMgr.removeMicroSiteType(id, this);
    }
    public boolean hasMicroSiteType(String id)
    {
        return org.semanticwb.portal.community.MicroSiteType.ClassMgr.hasMicroSiteType(id, this);
    }

    public org.semanticwb.portal.community.CompanySite getCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.getCompanySite(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.CompanySite> listCompanySites()
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.listCompanySites(this);
    }

    public org.semanticwb.portal.community.CompanySite createCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.createCompanySite(id,this);
    }

    public void removeCompanySite(String id)
    {
        org.semanticwb.portal.community.CompanySite.ClassMgr.removeCompanySite(id, this);
    }
    public boolean hasCompanySite(String id)
    {
        return org.semanticwb.portal.community.CompanySite.ClassMgr.hasCompanySite(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteUtil getMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.getMicroSiteUtil(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteUtil> listMicroSiteUtils()
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.listMicroSiteUtils(this);
    }

    public org.semanticwb.portal.community.MicroSiteUtil createMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.createMicroSiteUtil(id,this);
    }

    public void removeMicroSiteUtil(String id)
    {
        org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.removeMicroSiteUtil(id, this);
    }
    public boolean hasMicroSiteUtil(String id)
    {
        return org.semanticwb.portal.community.MicroSiteUtil.ClassMgr.hasMicroSiteUtil(id, this);
    }

    public org.semanticwb.portal.community.PersonalComm getPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.getPersonalComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.PersonalComm> listPersonalComms()
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.listPersonalComms(this);
    }

    public org.semanticwb.portal.community.PersonalComm createPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.createPersonalComm(id,this);
    }

    public void removePersonalComm(String id)
    {
        org.semanticwb.portal.community.PersonalComm.ClassMgr.removePersonalComm(id, this);
    }
    public boolean hasPersonalComm(String id)
    {
        return org.semanticwb.portal.community.PersonalComm.ClassMgr.hasPersonalComm(id, this);
    }

    public org.semanticwb.portal.community.Comment getComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.getComment(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Comment> listComments()
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.listComments(this);
    }

    public org.semanticwb.portal.community.Comment createComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.createComment(id,this);
    }

    public org.semanticwb.portal.community.Comment createComment()
    {
        long id=getSemanticObject().getModel().getCounter(swbcomm_Comment);
        return org.semanticwb.portal.community.Comment.ClassMgr.createComment(String.valueOf(id),this);
    } 

    public void removeComment(String id)
    {
        org.semanticwb.portal.community.Comment.ClassMgr.removeComment(id, this);
    }
    public boolean hasComment(String id)
    {
        return org.semanticwb.portal.community.Comment.ClassMgr.hasComment(id, this);
    }

    public org.semanticwb.portal.community.Community getCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.getCommunity(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.Community> listCommunities()
    {
        return org.semanticwb.portal.community.Community.ClassMgr.listCommunities(this);
    }

    public org.semanticwb.portal.community.Community createCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.createCommunity(id,this);
    }

    public void removeCommunity(String id)
    {
        org.semanticwb.portal.community.Community.ClassMgr.removeCommunity(id, this);
    }
    public boolean hasCommunity(String id)
    {
        return org.semanticwb.portal.community.Community.ClassMgr.hasCommunity(id, this);
    }

    public org.semanticwb.portal.community.TematicComm getTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.getTematicComm(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.TematicComm> listTematicComms()
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.listTematicComms(this);
    }

    public org.semanticwb.portal.community.TematicComm createTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.createTematicComm(id,this);
    }

    public void removeTematicComm(String id)
    {
        org.semanticwb.portal.community.TematicComm.ClassMgr.removeTematicComm(id, this);
    }
    public boolean hasTematicComm(String id)
    {
        return org.semanticwb.portal.community.TematicComm.ClassMgr.hasTematicComm(id, this);
    }

    public org.semanticwb.portal.community.MicroSiteElement getMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.getMicroSiteElement(id, this);
    }

    public java.util.Iterator<org.semanticwb.portal.community.MicroSiteElement> listMicroSiteElements()
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.listMicroSiteElements(this);
    }

    public org.semanticwb.portal.community.MicroSiteElement createMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.createMicroSiteElement(id,this);
    }

    public void removeMicroSiteElement(String id)
    {
        org.semanticwb.portal.community.MicroSiteElement.ClassMgr.removeMicroSiteElement(id, this);
    }
    public boolean hasMicroSiteElement(String id)
    {
        return org.semanticwb.portal.community.MicroSiteElement.ClassMgr.hasMicroSiteElement(id, this);
    }
}
