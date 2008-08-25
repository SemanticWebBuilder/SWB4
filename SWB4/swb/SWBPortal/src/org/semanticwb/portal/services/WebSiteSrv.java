
/* To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.services;

import java.util.Iterator;
import org.semanticwb.model.*;
import org.semanticwb.SWBUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPortal;

/**
 *
 * @author jorge.jimenez
 */
public class WebSiteSrv {

    private static Logger log = SWBUtils.getLogger(WebSiteSrv.class);

    public WebSite createWebSite(String name, String nsp, String title, String description, Language language, UserRepository userrepository, String homeTitle, String homeDescription, User user) throws SWBException 
    {

        WebSite website = SWBContext.createWebSite(name, nsp);
        website.setTitle(title);
        website.setDescription(description);
        website.setLanguage(language);
        website.setUserRepository(userrepository);
        WebPage wp = website.createWebPage(homeTitle);
        wp.setDescription(homeDescription);
        website.setHomePage(wp);

        website.setCreator(user);        
        
        GroupSrv groupSrv=new GroupSrv();
        groupSrv.createGroup(website, "default", "Grupo de templates default para sitio", "tpl", user);

        //crea campañas defecto y prioritaria para el topicmap
        CampSrv capSrv=new CampSrv();
        capSrv.createCamp(website, "default", "Grupo de templates default para sitio", user);
        capSrv.createCamp(website,"Priority","Priority",user);

        java.io.File dir = new java.io.File(SWBPortal.getWorkPath() + "/sites/" + website.getId() + "/templates");
        dir.mkdirs();
        dir = new java.io.File(SWBPortal.getWorkPath() + "/sites/" + website.getId() + "/resources");
        dir.mkdirs();

        SWBPortal.createInstance().log(user.getURI(), "create", website.getURI(), website.getId(), "create website", null);
        return website;
    }

    public boolean removeWebSite(String uri, User user) throws SWBException {
        WebSite webSite = SWBContext.getWebSite(uri);

        //Elimina templates, TODO: Asumiendo q el sitio solo tendra grupos de templates, ESTO NO ES CORRECTO
        //Me imagino q los subtipos de recursos seran ahora grupos
        Iterator<ObjectGroup> itObjGroups = webSite.listObjectGroups();
        while (itObjGroups.hasNext()) {
            ObjectGroup objGroup = itObjGroups.next();
            if(objGroup.getProperty("type")!=null && objGroup.getProperty("type").equalsIgnoreCase("tpl")){
                SWBPortal.createInstance().getTemplateSrv().removeTemplateGroup(webSite, objGroup.getId(), user);
            }
        }
        //Elimina reglas
        Iterator<Rule> itRules = webSite.listRules();
        while (itRules.hasNext()) {
            Rule rule = itRules.next();
            SWBPortal.createInstance().getRuleSrv().removeRule(webSite, rule.getId(), user);
        }
        //Elimina lenguages
        Iterator<Language> itLanguages = webSite.listLanguages();
        while (itLanguages.hasNext()) {
            Language lang = itLanguages.next();
            SWBPortal.createInstance().getLanguageSrv().removeLanguage(webSite, lang.getId(), user);
        }
        //Elimina Campañas
        Iterator<Camp> itCamps = webSite.listCamps();
        while (itCamps.hasNext()) {
            Camp camp = itCamps.next();
            SWBPortal.createInstance().getCampSrv().removeCamp(webSite, camp.getId(), user);
        }
        //TODO:
        //Elimina recursos, ver si tengo q eliminar por cada tipo o lo puedo hacer todos juntos
        //Antes se mandaba llamar el metodo remove de cada recurso y ese sabía lo q hacia, ver si se hace lo mismo
        //para llamandolos de manera individual (ApplicationPortlet, SytemPortlet,etc)
        Iterator <PortletClass> itPortlets=webSite.listPortletClasss();
        while(itPortlets.hasNext()){
            PortletClass pClass=itPortlets.next();
            //Cuando exista un metodo a llamar en esta clase para remover todo lo del portlet, hacerlo.
        }

        //TODO: Revisar como se eliminarían lo q anteriormente eran los tipos de recursos

        //TODO:Eliminar filtros de administración (vistas)

        //Elimina Flujos
        Iterator<PFlow> itPflow = webSite.listPFlows();
        while (itPflow.hasNext()) {
            PFlow pflow = itPflow.next();
            SWBPortal.createInstance().getPFlowSrv().removePFlow(webSite, pflow.getId(), user);
        }
        //Elimina filtros de IP
        Iterator<IPFilter> itIPFilters = webSite.listIPFilters();
        while (itIPFilters.hasNext()) {
            IPFilter ipFilter = itIPFilters.next();
            SWBPortal.createInstance().getIPFilterSrv().removeIPFilter(webSite, ipFilter.getId(), user);
        }

        //TODO: Eliminar tablas de metadatos

        //TODO:Eliminar ruta del sitio
        SWBUtils.IO.removeDirectory(SWBPortal.getWorkPath() + "/sites/" + webSite.getId());

        //TODO:Revisar q más se va ha borrar de BD (hits al sitio, etc)

        SWBContext.removeWebSite(uri);

        SWBPortal.createInstance().log(user.getURI(), "remove", uri, uri, "remove website", null);

        return true;
    }

    public boolean updateWebSite(WebSite webSite, String title, String description, User user) throws SWBException {

        if (title != null) {
            webSite.setTitle(title);
        }
        if (description != null) {
            webSite.setDescription(description);
        }
        webSite.setModifiedBy(user);

        SWBPortal.createInstance().log(user.getURI(), "update", webSite.getURI(), webSite.getId(), "update website", null);

        return true;
    }
}