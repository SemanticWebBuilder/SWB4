/**  
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
 **/
package org.semanticwb.model.base;


// TODO: Auto-generated Javadoc
/**
 * The Class WebPageBase.
 */
public abstract class WebPageBase extends org.semanticwb.model.Topic implements org.semanticwb.model.Resourceable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableNode,org.semanticwb.model.Expirable,org.semanticwb.model.TemplateRefable,org.semanticwb.model.CalendarRefable,org.semanticwb.model.Searchable,org.semanticwb.model.Trashable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Undeleteable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Tagable,org.semanticwb.model.UserGroupRefable,org.semanticwb.model.Activeable,org.semanticwb.model.RuleRefable,org.semanticwb.model.Hiddenable,org.semanticwb.model.Traceable,org.semanticwb.model.Referensable,org.semanticwb.model.Indexable,org.semanticwb.model.PFlowRefable,org.semanticwb.model.RoleRefable
{
    
    /** The Constant swb_webPageSortName. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageSortName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageSortName");
    
    /** The Constant swb_webPageURLType. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURLType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURLType");
    
    /** The Constant swb_WebPage. */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    
    /** The Constant swb_hasWebPageVirtualParent. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualParent");
    
    /** The Constant swb_webPageTarget. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageTarget=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageTarget");
    
    /** The Constant swb_hasWebPageVirtualChild. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageVirtualChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageVirtualChild");
    
    /** The Constant swb_hasWebPageChild. */
    public static final org.semanticwb.platform.SemanticProperty swb_hasWebPageChild=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#hasWebPageChild");
    
    /** The Constant swb_webPageParent. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageParent=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageParent");
    
    /** The Constant swb_webPageDiskUsage. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageDiskUsage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageDiskUsage");
    
    /** The Constant swb_webPageURL. */
    public static final org.semanticwb.platform.SemanticProperty swb_webPageURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#webPageURL");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List web pages.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
        }

        /**
         * List web pages.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPages()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(it, true);
        }

        /**
         * Gets the web page.
         * 
         * @param id the id
         * @param model the model
         * @return the web page
         */
        public static org.semanticwb.model.WebPage getWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the web page.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. web page
         */
        public static org.semanticwb.model.WebPage createWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.WebPage)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the web page.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for web page.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasWebPage(String id, org.semanticwb.model.SWBModel model)
        {
            return (getWebPage(id, model)!=null);
        }

        /**
         * List web page by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef, hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by user group ref.
         * 
         * @param hasusergroupref the hasusergroupref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByUserGroupRef(org.semanticwb.model.UserGroupRef hasusergroupref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasusergroupref.getSemanticObject().getModel().listSubjects(swb_hasUserGroupRef,hasusergroupref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by ass member.
         * 
         * @param hasassmemberinv the hasassmemberinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember hasassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv, hasassmemberinv.getSemanticObject()));
            return it;
        }

        /**
         * List web page by ass member.
         * 
         * @param hasassmemberinv the hasassmemberinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByAssMember(org.semanticwb.model.AssMember hasassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasAssMemberInv,hasassmemberinv.getSemanticObject()));
            return it;
        }

        /**
         * List web page by virtual parent.
         * 
         * @param haswebpagevirtualparent the haswebpagevirtualparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent, haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        /**
         * List web page by virtual parent.
         * 
         * @param haswebpagevirtualparent the haswebpagevirtualparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByVirtualParent(org.semanticwb.model.WebPage haswebpagevirtualparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualparent.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualParent,haswebpagevirtualparent.getSemanticObject()));
            return it;
        }

        /**
         * List web page by web page virtual child.
         * 
         * @param haswebpagevirtualchild the haswebpagevirtualchild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild, haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        /**
         * List web page by web page virtual child.
         * 
         * @param haswebpagevirtualchild the haswebpagevirtualchild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByWebPageVirtualChild(org.semanticwb.model.WebPage haswebpagevirtualchild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagevirtualchild.getSemanticObject().getModel().listSubjects(swb_hasWebPageVirtualChild,haswebpagevirtualchild.getSemanticObject()));
            return it;
        }

        /**
         * List web page by template ref.
         * 
         * @param hastemplateref the hastemplateref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef, hastemplateref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by template ref.
         * 
         * @param hastemplateref the hastemplateref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByTemplateRef(org.semanticwb.model.TemplateRef hastemplateref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hastemplateref.getSemanticObject().getModel().listSubjects(swb_hasTemplateRef,hastemplateref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by p flow ref.
         * 
         * @param haspflowref the haspflowref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef haspflowref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef, haspflowref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by p flow ref.
         * 
         * @param haspflowref the haspflowref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByPFlowRef(org.semanticwb.model.PFlowRef haspflowref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haspflowref.getSemanticObject().getModel().listSubjects(swb_hasPFlowRef,haspflowref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by child.
         * 
         * @param haswebpagechild the haswebpagechild
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage haswebpagechild,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild, haswebpagechild.getSemanticObject()));
            return it;
        }

        /**
         * List web page by child.
         * 
         * @param haswebpagechild the haswebpagechild
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByChild(org.semanticwb.model.WebPage haswebpagechild)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(haswebpagechild.getSemanticObject().getModel().listSubjects(swb_hasWebPageChild,haswebpagechild.getSemanticObject()));
            return it;
        }

        /**
         * List web page by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef, hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by calendar ref.
         * 
         * @param hascalendarref the hascalendarref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCalendarRef(org.semanticwb.model.CalendarRef hascalendarref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hascalendarref.getSemanticObject().getModel().listSubjects(swb_hasCalendarRef,hascalendarref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by parent.
         * 
         * @param webpageparent the webpageparent
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage webpageparent,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_webPageParent, webpageparent.getSemanticObject()));
            return it;
        }

        /**
         * List web page by parent.
         * 
         * @param webpageparent the webpageparent
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByParent(org.semanticwb.model.WebPage webpageparent)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(webpageparent.getSemanticObject().getModel().listSubjects(swb_webPageParent,webpageparent.getSemanticObject()));
            return it;
        }

        /**
         * List web page by modified by.
         * 
         * @param modifiedby the modifiedby
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List web page by modified by.
         * 
         * @param modifiedby the modifiedby
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByModifiedBy(org.semanticwb.model.User modifiedby)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
            return it;
        }

        /**
         * List web page by resource.
         * 
         * @param hasresource the hasresource
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource hasresource,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasResource, hasresource.getSemanticObject()));
            return it;
        }

        /**
         * List web page by resource.
         * 
         * @param hasresource the hasresource
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByResource(org.semanticwb.model.Resource hasresource)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasresource.getSemanticObject().getModel().listSubjects(swb_hasResource,hasresource.getSemanticObject()));
            return it;
        }

        /**
         * List web page by role ref.
         * 
         * @param hasroleref the hasroleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef hasroleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRoleRef, hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by role ref.
         * 
         * @param hasroleref the hasroleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRoleRef(org.semanticwb.model.RoleRef hasroleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasroleref.getSemanticObject().getModel().listSubjects(swb_hasRoleRef,hasroleref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by this role ass member.
         * 
         * @param hasthisroleassmemberinv the hasthisroleassmemberinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv, hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        /**
         * List web page by this role ass member.
         * 
         * @param hasthisroleassmemberinv the hasthisroleassmemberinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisRoleAssMember(org.semanticwb.model.AssMember hasthisroleassmemberinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasthisroleassmemberinv.getSemanticObject().getModel().listSubjects(swb_hasThisRoleAssMemberInv,hasthisroleassmemberinv.getSemanticObject()));
            return it;
        }

        /**
         * List web page by creator.
         * 
         * @param creator the creator
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
            return it;
        }

        /**
         * List web page by creator.
         * 
         * @param creator the creator
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByCreator(org.semanticwb.model.User creator)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
            return it;
        }

        /**
         * List web page by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef hasruleref,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasRuleRef, hasruleref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by rule ref.
         * 
         * @param hasruleref the hasruleref
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByRuleRef(org.semanticwb.model.RuleRef hasruleref)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasruleref.getSemanticObject().getModel().listSubjects(swb_hasRuleRef,hasruleref.getSemanticObject()));
            return it;
        }

        /**
         * List web page by this type association.
         * 
         * @param hasthistypeassociationinv the hasthistypeassociationinv
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv, hasthistypeassociationinv.getSemanticObject()));
            return it;
        }

        /**
         * List web page by this type association.
         * 
         * @param hasthistypeassociationinv the hasthistypeassociationinv
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.WebPage> listWebPageByThisTypeAssociation(org.semanticwb.model.Association hasthistypeassociationinv)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> it=new org.semanticwb.model.GenericIterator(hasthistypeassociationinv.getSemanticObject().getModel().listSubjects(swb_hasThisTypeAssociationInv,hasthistypeassociationinv.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new web page base.
     * 
     * @param base the base
     */
    public WebPageBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listObjectProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#hasUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public boolean hasUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        boolean ret=false;
        if(usergroupref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#listInheritUserGroupRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef> listInheritUserGroupRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.UserGroupRef>(getSemanticObject().listInheritProperties(swb_hasUserGroupRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#addUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void addUserGroupRef(org.semanticwb.model.UserGroupRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasUserGroupRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeAllUserGroupRef()
     */
    public void removeAllUserGroupRef()
    {
        getSemanticObject().removeProperty(swb_hasUserGroupRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#removeUserGroupRef(org.semanticwb.model.UserGroupRef)
     */
    public void removeUserGroupRef(org.semanticwb.model.UserGroupRef usergroupref)
    {
        getSemanticObject().removeObjectProperty(swb_hasUserGroupRef,usergroupref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UserGroupRefableBase#getUserGroupRef()
     */
    public org.semanticwb.model.UserGroupRef getUserGroupRef()
    {
         org.semanticwb.model.UserGroupRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasUserGroupRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.UserGroupRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#getReviews()
     */
    public long getReviews()
    {
        return getSemanticObject().getLongProperty(swb_reviews);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#setReviews(long)
     */
    public void setReviews(long value)
    {
        getSemanticObject().setLongProperty(swb_reviews, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#getRank()
     */
    public double getRank()
    {
        return getSemanticObject().getDoubleProperty(swb_rank);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RankableBase#setRank(double)
     */
    public void setRank(double value)
    {
        getSemanticObject().setDoubleProperty(swb_rank, value);
    }

    /**
     * Gets the sort name.
     * 
     * @return the sort name
     */
    public String getSortName()
    {
        return getSemanticObject().getProperty(swb_webPageSortName);
    }

    /**
     * Sets the sort name.
     * 
     * @param value the new sort name
     */
    public void setSortName(String value)
    {
        getSemanticObject().setProperty(swb_webPageSortName, value);
    }

    /**
     * Gets the web page url type.
     * 
     * @return the web page url type
     */
    public int getWebPageURLType()
    {
        return getSemanticObject().getIntProperty(swb_webPageURLType);
    }

    /**
     * Sets the web page url type.
     * 
     * @param value the new web page url type
     */
    public void setWebPageURLType(int value)
    {
        getSemanticObject().setIntProperty(swb_webPageURLType, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#isActive()
     */
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ActiveableBase#setActive(boolean)
     */
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

    /**
     * List virtual parents.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listVirtualParents()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualParent));
    }

    /**
     * Checks for virtual parent.
     * 
     * @param webpage the webpage
     * @return true, if successful
     */
    public boolean hasVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        boolean ret=false;
        if(webpage!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualParent,webpage.getSemanticObject());
        }
        return ret;
    }

    /**
     * Adds the virtual parent.
     * 
     * @param value the value
     */
    public void addVirtualParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().addObjectProperty(swb_hasWebPageVirtualParent, value.getSemanticObject());
    }

    /**
     * Removes the all virtual parent.
     */
    public void removeAllVirtualParent()
    {
        getSemanticObject().removeProperty(swb_hasWebPageVirtualParent);
    }

    /**
     * Removes the virtual parent.
     * 
     * @param webpage the webpage
     */
    public void removeVirtualParent(org.semanticwb.model.WebPage webpage)
    {
        getSemanticObject().removeObjectProperty(swb_hasWebPageVirtualParent,webpage.getSemanticObject());
    }

    /**
     * Gets the virtual parent.
     * 
     * @return the virtual parent
     */
    public org.semanticwb.model.WebPage getVirtualParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#isDeleted()
     */
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TrashableBase#setDeleted(boolean)
     */
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }

    /**
     * Gets the target.
     * 
     * @return the target
     */
    public String getTarget()
    {
        return getSemanticObject().getProperty(swb_webPageTarget);
    }

    /**
     * Sets the target.
     * 
     * @param value the new target
     */
    public void setTarget(String value)
    {
        getSemanticObject().setProperty(swb_webPageTarget, value);
    }

    /**
     * List web page virtual childs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listWebPageVirtualChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageVirtualChild));
    }

    /**
     * Checks for web page virtual child.
     * 
     * @param webpage the webpage
     * @return true, if successful
     */
    public boolean hasWebPageVirtualChild(org.semanticwb.model.WebPage webpage)
    {
        boolean ret=false;
        if(webpage!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageVirtualChild,webpage.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the web page virtual child.
     * 
     * @return the web page virtual child
     */
    public org.semanticwb.model.WebPage getWebPageVirtualChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageVirtualChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#listTemplateRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listObjectProperties(swb_hasTemplateRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#hasTemplateRef(org.semanticwb.model.TemplateRef)
     */
    public boolean hasTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        boolean ret=false;
        if(templateref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasTemplateRef,templateref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#listInheritTemplateRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef> listInheritTemplateRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.TemplateRef>(getSemanticObject().listInheritProperties(swb_hasTemplateRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#addTemplateRef(org.semanticwb.model.TemplateRef)
     */
    public void addTemplateRef(org.semanticwb.model.TemplateRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasTemplateRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#removeAllTemplateRef()
     */
    public void removeAllTemplateRef()
    {
        getSemanticObject().removeProperty(swb_hasTemplateRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#removeTemplateRef(org.semanticwb.model.TemplateRef)
     */
    public void removeTemplateRef(org.semanticwb.model.TemplateRef templateref)
    {
        getSemanticObject().removeObjectProperty(swb_hasTemplateRef,templateref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.TemplateRefableBase#getTemplateRef()
     */
    public org.semanticwb.model.TemplateRef getTemplateRef()
    {
         org.semanticwb.model.TemplateRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasTemplateRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.TemplateRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#listPFlowRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listObjectProperties(swb_hasPFlowRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#hasPFlowRef(org.semanticwb.model.PFlowRef)
     */
    public boolean hasPFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        boolean ret=false;
        if(pflowref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasPFlowRef,pflowref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#listInheritPFlowRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef> listInheritPFlowRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.PFlowRef>(getSemanticObject().listInheritProperties(swb_hasPFlowRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#addPFlowRef(org.semanticwb.model.PFlowRef)
     */
    public void addPFlowRef(org.semanticwb.model.PFlowRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasPFlowRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#removeAllPFlowRef()
     */
    public void removeAllPFlowRef()
    {
        getSemanticObject().removeProperty(swb_hasPFlowRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#removePFlowRef(org.semanticwb.model.PFlowRef)
     */
    public void removePFlowRef(org.semanticwb.model.PFlowRef pflowref)
    {
        getSemanticObject().removeObjectProperty(swb_hasPFlowRef,pflowref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.PFlowRefableBase#getPFlowRef()
     */
    public org.semanticwb.model.PFlowRef getPFlowRef()
    {
         org.semanticwb.model.PFlowRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasPFlowRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PFlowRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * List childs.
     * 
     * @return the org.semanticwb.model. generic iterator
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage> listChilds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.WebPage>(getSemanticObject().listObjectProperties(swb_hasWebPageChild));
    }

    /**
     * Checks for child.
     * 
     * @param webpage the webpage
     * @return true, if successful
     */
    public boolean hasChild(org.semanticwb.model.WebPage webpage)
    {
        boolean ret=false;
        if(webpage!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasWebPageChild,webpage.getSemanticObject());
        }
        return ret;
    }

    /**
     * Gets the child.
     * 
     * @return the child
     */
    public org.semanticwb.model.WebPage getChild()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasWebPageChild);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#listCalendarRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef> listCalendarRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.CalendarRef>(getSemanticObject().listObjectProperties(swb_hasCalendarRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#hasCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public boolean hasCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        boolean ret=false;
        if(calendarref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#addCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void addCalendarRef(org.semanticwb.model.CalendarRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasCalendarRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeAllCalendarRef()
     */
    public void removeAllCalendarRef()
    {
        getSemanticObject().removeProperty(swb_hasCalendarRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#removeCalendarRef(org.semanticwb.model.CalendarRef)
     */
    public void removeCalendarRef(org.semanticwb.model.CalendarRef calendarref)
    {
        getSemanticObject().removeObjectProperty(swb_hasCalendarRef,calendarref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.CalendarRefableBase#getCalendarRef()
     */
    public org.semanticwb.model.CalendarRef getCalendarRef()
    {
         org.semanticwb.model.CalendarRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasCalendarRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.CalendarRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Sets the parent.
     * 
     * @param value the new parent
     */
    public void setParent(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swb_webPageParent, value.getSemanticObject());
    }

    /**
     * Removes the parent.
     */
    public void removeParent()
    {
        getSemanticObject().removeProperty(swb_webPageParent);
    }

    /**
     * Gets the parent.
     * 
     * @return the parent
     */
    public org.semanticwb.model.WebPage getParent()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_webPageParent);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#isUndeleteable()
     */
    public boolean isUndeleteable()
    {
        return getSemanticObject().getBooleanProperty(swb_undeleteable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.UndeleteableBase#setUndeleteable(boolean)
     */
    public void setUndeleteable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_undeleteable, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#getMaxViews()
     */
    public long getMaxViews()
    {
        return getSemanticObject().getLongProperty(swb_maxViews);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#setMaxViews(long)
     */
    public void setMaxViews(long value)
    {
        getSemanticObject().setLongProperty(swb_maxViews, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#getExpiration()
     */
    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(swb_expiration);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ExpirableBase#setExpiration(java.util.Date)
     */
    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_expiration, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#listResources()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource> listResources()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.Resource>(getSemanticObject().listObjectProperties(swb_hasResource));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#hasResource(org.semanticwb.model.Resource)
     */
    public boolean hasResource(org.semanticwb.model.Resource resource)
    {
        boolean ret=false;
        if(resource!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasResource,resource.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#addResource(org.semanticwb.model.Resource)
     */
    public void addResource(org.semanticwb.model.Resource value)
    {
        getSemanticObject().addObjectProperty(swb_hasResource, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#removeAllResource()
     */
    public void removeAllResource()
    {
        getSemanticObject().removeProperty(swb_hasResource);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#removeResource(org.semanticwb.model.Resource)
     */
    public void removeResource(org.semanticwb.model.Resource resource)
    {
        getSemanticObject().removeObjectProperty(swb_hasResource,resource.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ResourceableBase#getResource()
     */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasResource);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HiddenableBase#isHidden()
     */
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swb_hidden);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.HiddenableBase#setHidden(boolean)
     */
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_hidden, value);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#isIndexable()
     */
    public boolean isIndexable()
    {
        return getSemanticObject().getBooleanProperty(swb_indexable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.IndexableBase#setIndexable(boolean)
     */
    public void setIndexable(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_indexable, value);
    }

    /**
     * Gets the disk usage.
     * 
     * @return the disk usage
     */
    public long getDiskUsage()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_webPageDiskUsage,false);
    }

    /**
     * Sets the disk usage.
     * 
     * @param value the new disk usage
     */
    public void setDiskUsage(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_webPageDiskUsage, value,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listObjectProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#hasRoleRef(org.semanticwb.model.RoleRef)
     */
    public boolean hasRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        boolean ret=false;
        if(roleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#listInheritRoleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef> listInheritRoleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RoleRef>(getSemanticObject().listInheritProperties(swb_hasRoleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#addRoleRef(org.semanticwb.model.RoleRef)
     */
    public void addRoleRef(org.semanticwb.model.RoleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRoleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeAllRoleRef()
     */
    public void removeAllRoleRef()
    {
        getSemanticObject().removeProperty(swb_hasRoleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#removeRoleRef(org.semanticwb.model.RoleRef)
     */
    public void removeRoleRef(org.semanticwb.model.RoleRef roleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRoleRef,roleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RoleRefableBase#getRoleRef()
     */
    public org.semanticwb.model.RoleRef getRoleRef()
    {
         org.semanticwb.model.RoleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRoleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RoleRef)obj.createGenericInstance();
         }
         return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#getViews()
     */
    public long getViews()
    {
        //Override this method in WebPage object
        return getSemanticObject().getLongProperty(swb_views,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.ViewableBase#setViews(long)
     */
    public void setViews(long value)
    {
        //Override this method in WebPage object
        getSemanticObject().setLongProperty(swb_views, value,false);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listObjectProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#hasRuleRef(org.semanticwb.model.RuleRef)
     */
    public boolean hasRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        boolean ret=false;
        if(ruleref!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#listInheritRuleRefs()
     */
    public org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef> listInheritRuleRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.model.RuleRef>(getSemanticObject().listInheritProperties(swb_hasRuleRef));
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#addRuleRef(org.semanticwb.model.RuleRef)
     */
    public void addRuleRef(org.semanticwb.model.RuleRef value)
    {
        getSemanticObject().addObjectProperty(swb_hasRuleRef, value.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeAllRuleRef()
     */
    public void removeAllRuleRef()
    {
        getSemanticObject().removeProperty(swb_hasRuleRef);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#removeRuleRef(org.semanticwb.model.RuleRef)
     */
    public void removeRuleRef(org.semanticwb.model.RuleRef ruleref)
    {
        getSemanticObject().removeObjectProperty(swb_hasRuleRef,ruleref.getSemanticObject());
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.RuleRefableBase#getRuleRef()
     */
    public org.semanticwb.model.RuleRef getRuleRef()
    {
         org.semanticwb.model.RuleRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_hasRuleRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.RuleRef)obj.createGenericInstance();
         }
         return ret;
    }

    /**
     * Gets the web page url.
     * 
     * @return the web page url
     */
    public String getWebPageURL()
    {
        return getSemanticObject().getProperty(swb_webPageURL);
    }

    /**
     * Sets the web page url.
     * 
     * @param value the new web page url
     */
    public void setWebPageURL(String value)
    {
        getSemanticObject().setProperty(swb_webPageURL, value);
    }

    /**
     * Gets the web site.
     * 
     * @return the web site
     */
    public org.semanticwb.model.WebSite getWebSite()
    {
        return (org.semanticwb.model.WebSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
