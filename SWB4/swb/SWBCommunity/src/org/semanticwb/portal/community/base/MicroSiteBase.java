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


public abstract class MicroSiteBase extends org.semanticwb.model.WebPage implements org.semanticwb.model.Searchable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Traceable,org.semanticwb.model.FilterableNode,org.semanticwb.model.TemplateRefable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Tagable,org.semanticwb.model.Viewable,org.semanticwb.model.Referensable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Indexable,org.semanticwb.model.RoleRefable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.Undeleteable,org.semanticwb.model.Expirable,org.semanticwb.model.Filterable,org.semanticwb.model.Activeable,org.semanticwb.model.Resourceable,org.semanticwb.model.FilterableClass
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_moderate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#moderate");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_photo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#photo");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_private=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#private");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteWebPageUtil=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteWebPageUtil");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMicroSiteUtilsInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMicroSiteUtilsInv");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_about=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#about");
    public static final org.semanticwb.platform.SemanticClass swbcomm_Member=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Member");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasMSMembersInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasMSMembersInv");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSiteType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSiteType");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#type");
    public static final org.semanticwb.platform.SemanticClass swbcomm_MicroSite=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#MicroSite");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSites()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite>(it, true);
        }

        public static org.semanticwb.portal.community.MicroSite getMicroSite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.MicroSite)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.community.MicroSite createMicroSite(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.community.MicroSite)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeMicroSite(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasMicroSite(String id, org.semanticwb.model.SWBModel model)
        {
            return (getMicroSite(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv, hasassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv,hasassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef, haspflowref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef,haspflowref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef, hastemplateref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef,hastemplateref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild, haswebpagechild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByChild(org.semanticwb.model.WebPage haswebpagechild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild,haswebpagechild.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_webPageParent, webpageparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByParent(org.semanticwb.model.WebPage webpageparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjects(swb_webPageParent,webpageparent.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtilsInv, hasmicrositeutilsinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil hasmicrositeutilsinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmicrositeutilsinv.getSemanticObject().getModel().listSubjects(swbcomm_hasMicroSiteUtilsInv,hasmicrositeutilsinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_about, about.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByAbout(org.semanticwb.model.WebPage about)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(about.getSemanticObject().getModel().listSubjects(swbcomm_about,about.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasResource, hasresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByResource(org.semanticwb.model.Resource hasresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjects(swb_hasResource,hasresource.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMember(org.semanticwb.portal.community.Member hasmsmembersinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasMSMembersInv, hasmsmembersinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByMember(org.semanticwb.portal.community.Member hasmsmembersinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasmsmembersinv.getSemanticObject().getModel().listSubjects(swbcomm_hasMSMembersInv,hasmsmembersinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRoleRef, hasroleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(swb_hasRoleRef,hasroleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_type, type.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByType(org.semanticwb.portal.community.MicroSiteType type)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(type.getSemanticObject().getModel().listSubjects(swbcomm_type,type.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRuleRef, hasruleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(swb_hasRuleRef,hasruleref.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject()));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.community.MicroSite> listMicroSiteByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSite> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject()));
            return it;
        }
    }

    public MicroSiteBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public boolean isModerate()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_moderate);
    }

    public void setModerate(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_moderate, value);
    }

    public String getPhoto()
    {
        return getSemanticObject().getProperty(swbcomm_photo);
    }

    public void setPhoto(String value)
    {
        getSemanticObject().setProperty(swbcomm_photo, value);
    }

    public boolean isPrivate()
    {
        return getSemanticObject().getBooleanProperty(swbcomm_private);
    }

    public void setPrivate(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbcomm_private, value);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil> listMicroSiteUtils()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.MicroSiteWebPageUtil>(getSemanticObject().listObjectProperties(swbcomm_hasMicroSiteUtilsInv));
    }

    public boolean hasMicroSiteUtil(org.semanticwb.portal.community.MicroSiteWebPageUtil micrositewebpageutil)
    {
        boolean ret=false;
        if(micrositewebpageutil!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasMicroSiteUtilsInv,micrositewebpageutil.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.portal.community.MicroSiteWebPageUtil getMicroSiteUtil()
    {
         org.semanticwb.portal.community.MicroSiteWebPageUtil ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMicroSiteUtilsInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteWebPageUtil)obj.createGenericInstance();
         }
         return ret;
    }

    public void setAbout(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_about, value.getSemanticObject());
    }

    public void removeAbout()
    {
        getSemanticObject().removeProperty(swbcomm_about);
    }

    public org.semanticwb.model.WebPage getAbout()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_about);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member> listMembers()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Member>(getSemanticObject().listObjectProperties(swbcomm_hasMSMembersInv));
    }

    public boolean hasMember(org.semanticwb.portal.community.Member member)
    {
        boolean ret=false;
        if(member!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasMSMembersInv,member.getSemanticObject());
        }
        return ret;
    }

    public org.semanticwb.portal.community.Member getMember()
    {
         org.semanticwb.portal.community.Member ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasMSMembersInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Member)obj.createGenericInstance();
         }
         return ret;
    }

    public void setType(org.semanticwb.portal.community.MicroSiteType value)
    {
        getSemanticObject().setObjectProperty(swbcomm_type, value.getSemanticObject());
    }

    public void removeType()
    {
        getSemanticObject().removeProperty(swbcomm_type);
    }

    public org.semanticwb.portal.community.MicroSiteType getType()
    {
         org.semanticwb.portal.community.MicroSiteType ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_type);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.MicroSiteType)obj.createGenericInstance();
         }
         return ret;
    }

    public org.semanticwb.portal.community.MicrositeContainer getMicrositeContainer()
    {
        return (org.semanticwb.portal.community.MicrositeContainer)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
