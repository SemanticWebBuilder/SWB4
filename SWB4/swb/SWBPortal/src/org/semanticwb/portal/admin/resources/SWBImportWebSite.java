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
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Device;
import org.semanticwb.model.Language;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticMgr;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.repository.Workspace;

// TODO: Auto-generated Javadoc
/**
 * The Class SWBImportWebSite.
 * 
 * @author jorge.jimenez
 */
public class SWBImportWebSite extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.portal.api.SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        try {
            User user = paramRequest.getUser();
            PrintWriter out = response.getWriter();
            String action = paramRequest.getAction();
            SWBResourceURL url = paramRequest.getRenderUrl();
            if (action != null && action.trim().equals("step2")) {
                String title = request.getParameter("wstitle");
                String id = request.getParameter("wsid");
                String usrRep = request.getParameter("wsrepository");
                String wstype = request.getParameter("wstype");

                WebSite site = null;
                String ns="http://www." + id + ".swb#";
                if(wstype==null)
                {
                    site = SWBContext.createWebSite(id, ns);
                }else
                {
                    SemanticMgr mgr=SWBPlatform.getSemanticMgr();
                    SemanticClass sclass=mgr.getVocabulary().getSemanticClassById(wstype);
                    if(sclass!=null)
                    {
                        SemanticModel model=mgr.createModel(id, ns);
                        site=(WebSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
                    }
                }

                site.getSemanticObject().getModel().setTraceable(false);
                //site.setCreated(new java.util.Date(System.currentTimeMillis()));
                site.setTitle(title);
                //Crea repositorio de usuarios para el nuevo sitio
                UserRepository newUsrRep = null;
                if (usrRep != null) {
                    if (usrRep.equals("0")) { //Utilizara un repositorio exclusivo
                        newUsrRep = SWBContext.createUserRepository(id + "_usr", "http://user." + id + ".swb#");
                        newUsrRep.getSemanticObject().getModel().setTraceable(false);
                        newUsrRep.setTitle("Repositorio de usuarios(" + id + ")");
                        newUsrRep.setTitle("Repositorio de usuarios(" + id + ")", "es");
                        newUsrRep.setTitle("Users Repository(" + id + ")", "en");
                        newUsrRep.setUndeleteable(true);

                        //MAPS74 - Cambiado a semantic prop
                        newUsrRep.setAuthMethod("FORM");
                        newUsrRep.setLoginContext("swb4TripleStoreModule");
                        newUsrRep.setCallBackHandlerClassName("org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");
                        if (user != null && user.isRegistered()) {
                            newUsrRep.setCreator(user);
                        }
                        site.setUserRepository(newUsrRep);
                        site.addSubModel(newUsrRep);
                    } else { //Utilizara un repositorio existente
                        UserRepository exitUsrRep = SWBContext.getUserRepository(usrRep);
                        site.setUserRepository(exitUsrRep);
                    }
                }
                if(newUsrRep!=null)newUsrRep.getSemanticObject().getModel().setTraceable(true);

                //creación de repositorio de documentoss
                Workspace workspace = SWBContext.createWorkspace(id + "_rep", "http://repository." + id + ".swb#");
                workspace.getSemanticObject().getModel().setTraceable(false);
                workspace.setTitle("Repositorio de documentos(" + title + ")");
                workspace.setTitle("Repositorio de documentos(" + title + ")", "es");
                workspace.setTitle("Documents Repository(" + title + ")", "en");
                //TODO: undeleted repository
                //workspace.setUn
                site.addSubModel(workspace);
                workspace.getSemanticObject().getModel().setTraceable(true);

                WebPage home = site.createWebPage("home");
                site.setHomePage(home);
                home.setUndeleteable(true);
                home.setTitle("Home");
                home.setSortName("z");

                //Crea grupo de templates de defecto
                TemplateGroup grp = site.createTemplateGroup();
                grp.setTitle("Default");

                //Crea recursos de defecto
                if (site.getResourceType("ExcelContent") == null) {
                    ResourceType ptype = site.createResourceType("ExcelContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.ExcelResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.ExcelResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("ExcelContent");
                }

                if (site.getResourceType("WordContent") == null) {
                    ResourceType ptype = site.createResourceType("WordContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.WordResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.WordResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("WordContent");
                }

                if (site.getResourceType("PPTContent") == null) {
                    ResourceType ptype = site.createResourceType("PPTContent");
                    ptype.setResourceClassName("org.semanticwb.resource.office.sem.PPTResource");
                    ptype.setResourceBundle("org.semanticwb.resource.office.sem.PPTResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("PPTContent");
                }

                if (site.getResourceType("Banner") == null) {
                    ResourceType ptype = site.createResourceType("Banner");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Banner");
                }

                if (site.getResourceType("HTMLContent") == null) {
                    ResourceType ptype = site.createResourceType("HTMLContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.HTMLContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.HTMLContent");
                    ptype.setResourceMode(1);
                    //ptype.setResourceCache(600);
                    ptype.setTitle("HTMLContent");
                }

                if (site.getResourceType("Promo") == null) {
                    ResourceType ptype = site.createResourceType("Promo");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Promo");
                }

                if (site.getResourceType("AdvancedSearch") == null) {
                    ResourceType ptype = site.createResourceType("AdvancedSearch");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.AdvancedSearch");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.AdvancedSearch");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("AdvancedSearch");
                }

                if (site.getResourceType("Print") == null) {
                    ResourceType ptype = site.createResourceType("Print");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Print");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Print");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Print");
                }

                if (site.getResourceType("Window") == null) {
                    ResourceType ptype = site.createResourceType("Window");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Window");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Window");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Window");
                }

                if (site.getResourceType("StaticText") == null) {
                    ResourceType ptype = site.createResourceType("StaticText");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceMode(2);
                    ptype.setTitle("StaticText");
                }

                if (site.getResourceType("SiteMap") == null) {
                    ResourceType ptype = site.createResourceType("SiteMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceMode(3);
                    ptype.setTitle("SiteMap");
                }

                if (site.getResourceType("Recommend") == null) {

                    ResourceType ptype = site.createResourceType("Recommend");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Recommend");
                }

                if (site.getResourceType("QueryResource") == null) {

                    ResourceType ptype = site.createResourceType("QueryResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("QueryResource");
                }

                if (site.getResourceType("VirtualResource") == null) {

                    ResourceType ptype = site.createResourceType("VirtualResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.VirtualResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.VirtualResource");
                    ptype.setResourceMode(1);
                    ptype.setTitle("VirtualResource");
                }

                if (site.getResourceType("RecommendSwf") == null) {

                    ResourceType ptype = site.createResourceType("RecommendSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("RecommendSwf");
                }

                if (site.getResourceType("Poll") == null) {

                    ResourceType ptype = site.createResourceType("Poll");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Poll");
                }

                if (site.getResourceType("Menu") == null) {
                    ResourceType ptype = site.createResourceType("Menu");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenu");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenu");
                    ptype.setResourceMode(ptype.MODE_STRATEGY);
                    ptype.setTitle("Menu");
                }

                if (site.getResourceType("MenuMap") == null) {

                    ResourceType ptype = site.createResourceType("MenuMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceMode(2);
                    ptype.setTitle("MenuMap");
                }

                if (site.getResourceType("MenuNivel") == null) {
                    ResourceType ptype = site.createResourceType("MenuNivel");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceMode(2);
                    ptype.setTitle("MenuNivel");
                }

                if (site.getResourceType("JSPResource") == null) {

                    ResourceType ptype = site.createResourceType("JSPResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("JSPResource");
                }

                if (site.getResourceType("PHPResource") == null) {

                    ResourceType ptype = site.createResourceType("PHPResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PHPResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PHPResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PHPResource");
                }

                if (site.getResourceType("PythonResource") == null) {

                    ResourceType ptype = site.createResourceType("PythonResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PythonResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PythonResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PythonResource");
                }

                if (site.getResourceType("GroovyResource") == null) {

                    ResourceType ptype = site.createResourceType("GroovyResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("GroovyResource");
                }

                if (site.getResourceType("GroovyEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("GroovyEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("GroovyEditor");
                }

                if (site.getResourceType("JSPEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("JSPEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.JSPEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.JSPEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("JSPEditor");
                }

                if (site.getResourceType("PythonEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("PythonEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PythonEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PythonEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PythonEditor");
                }

                if (site.getResourceType("PHPEditor") == null)
                {
                    ResourceType ptype = site.createResourceType("PHPEditor");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PHPEditor");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PHPEditor");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("PHPEditor");
                }

                if (site.getResourceType("Language") == null)
                {
                    ResourceType ptype = site.createResourceType("Language");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Language");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Language");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Language");
                }

                if (site.getResourceType("TematicIndexXSL") == null) {
                    ResourceType ptype = site.createResourceType("TematicIndexXSL");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceMode(1);
                    ptype.setTitle("TematicIndexXSL");
                }


                if (site.getResourceType("FrameContent") == null) {
                    ResourceType ptype = site.createResourceType("FrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("FrameContent");
                }

                if (site.getResourceType("IFrameContent") == null) {
                    ResourceType ptype = site.createResourceType("IFrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("IFrameContent");
                }

                if (site.getResourceType("CommentSwf") == null) {
                    ResourceType ptype = site.createResourceType("CommentSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("CommentSwf");
                }

                if (site.getResourceType("Comment") == null) {
                    ResourceType ptype = site.createResourceType("Comment");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Comment");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Comment");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Comment");
                }

                if (site.getResourceType("RemoteWebApp") == null) {
                    ResourceType ptype = site.createResourceType("RemoteWebApp");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceMode(3);
                    ptype.setTitle("RemoteWebApp");
                }

                if (site.getResourceType("UrlContent") == null) {
                    ResourceType ptype = site.createResourceType("UrlContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceMode(3);
                    ptype.setTitle("UrlContent");
                }

                if (site.getResourceType("Search") == null) {
                    ResourceType ptype = site.createResourceType("Search");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBSearch");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBSearch");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Search");
                }

                if (site.getResourceType("GoogleGadget") == null) {
                    ResourceType ptype = site.createResourceType("GoogleGadget");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                    ptype.setResourceMode(3);
                    ptype.setTitle("GoogleGadget");
                }

                if (site.getResourceType("SparQLQueryResource") == null) {
                    ResourceType ptype = site.createResourceType("SparQLQueryResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.SparqlQueryResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.SparqlQueryResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("SparQLQueryResource");
                }

                if (site.getResourceType("Wiki") == null) {
                    ResourceType ptype = site.createResourceType("Wiki");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.wiki.Wiki");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.wiki.Wiki");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Wiki");
                }
                
                if (site.getResourceType("PDFContent") == null) {
                    ResourceType ptype = site.createResourceType("PDFContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.PDFContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.PDFContent");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("PDFContent");
                }

                if (site.getResourceType("Login") == null) {
                    ResourceType ptype = site.createResourceType("Login");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Login");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Login");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("Login");
                }

                if (site.getResourceType("UserRegistration") == null) {
                    ResourceType ptype = site.createResourceType("UserRegistration");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.UserRegistration");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.UserRegistration");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("UserRegistration");
                }

                if (site.getResourceType("RSSResource") == null) {
                    ResourceType ptype = site.createResourceType("RSSResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("RSSResource");
                }

                if (site.getResourceType("DataBaseResource") == null) {
                    ResourceType ptype = site.createResourceType("DataBaseResource");
                    ptype.setResourceClassName("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceBundle("com.infotec.wb.resources.database.DataBaseResource");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("DataBaseResource");
                }

                if (site.getResourceType("SWBBlog") == null) {
                    ResourceType ptype = site.createResourceType("SWBBlog");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Blog");
                }

                if (site.getResourceType("WebPageComments") == null) {
                    ResourceType ptype = site.createResourceType("WebPageComments");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.SWBComments");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WebPageComments");
                }

                if (site.getResourceType("Forum") == null) {
                    ResourceType ptype = site.createResourceType("Forum");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("Forum");
                }

                if (site.getResourceType("RankWebPage") == null) {
                    ResourceType ptype = site.createResourceType("RankWebPage");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("RankWebPage");
                }

//                if (site.getResourceType("Directory") == null) {
//                    ResourceType ptype = site.createResourceType("Directory");
//                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.directory.Directory");
//                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.directory.Directory");
//                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.directory.Directory");
//                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
//                    ptype.setTitle("Directory");
//                }

//                if (site.getResourceType("Events") == null) {
//                    ResourceType ptype = site.createResourceType("Events");
//                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.events.Events");
//                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.events.Events");
//                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.events.Events");
//                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
//                    ptype.setTitle("Events");
//                }

                if (site.getResourceType("WebPageOnLineCreate") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WebPageOnLineCreate");
                }

                if (site.getResourceType("WebPageOnLineCreate") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                    ptype.setTitle("WebPageOnLineCreate");
                }

                if (site.getResourceType("RSSResource") == null) {
                    ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("RSSResource");
                }

                if (site.getResourceType("InlineEdit") == null) {
                    ResourceType ptype = site.createResourceType("InlineEdit");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.InlineEdit");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.InlineEdit");
                    ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                    ptype.setTitle("InlineEdit");
                }

                if (site.getResourceType("ImageGallery") == null) {
                    ResourceType ptype = site.createResourceType("ImageGallery");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.ImageGallery");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.ImageGallery");
                    ptype.setResourceMode(ResourceType.MODE_CONTENT);
                    ptype.setTitle("ImageGallery");
                }


                //Crea dispositivos de defecto
                if (!site.hasDevice("pc")) {
                    Device dev = site.createDevice("pc");
                    dev.setTitle("PC");
                    dev.setUserAgent("Mozilla");
                    Device dev2 = site.createDevice("iexplorer");
                    dev2.setTitle("Internet Explorer");
                    dev2.setUserAgent("Mozilla MSIE");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("firefox");
                    dev2.setTitle("Mozilla FireFox");
                    dev2.setUserAgent("Mozilla Firefox");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("safari");
                    dev2.setTitle("Safari");
                    dev2.setUserAgent("Mozilla Safari");
                    dev2.setParent(dev);

                    dev = site.createDevice("pda");
                    dev.setTitle("PDA");
                    dev2 = site.createDevice("blackberry");
                    dev2.setTitle("BlackBerry");
                    dev2.setUserAgent("BlackBerry");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("iphone");
                    dev2.setTitle("iPhone");
                    dev2.setUserAgent("Mozilla iPhone Safari");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("wince");
                    dev2.setTitle("Windows CE");
                    dev2.setUserAgent("Mozilla Windows CE");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("palmos");
                    dev2.setTitle("PalmOS");
                    dev2.setUserAgent("Mozilla PalmOS");
                    dev2.setParent(dev);
                    Device dev3 = site.createDevice("avantgo");
                    dev3.setTitle("AvantGo");
                    dev3.setUserAgent("Mozilla AvantGo");
                    dev3.setParent(dev2);
                    dev3 = site.createDevice("eudoraweb");
                    dev3.setTitle("EudoraWeb");
                    dev3.setUserAgent("Mozilla PalmOS EudoraWeb");
                    dev3.setParent(dev2);

                    dev = site.createDevice("phone");
                    dev.setTitle("Phone");
                    dev2 = site.createDevice("midp");
                    dev2.setTitle("MIDP");
                    dev2.setUserAgent("MIDP");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("mmp");
                    dev2.setTitle("MMP");
                    dev2.setUserAgent("MMP");
                    dev2.setParent(dev);
                    dev2 = site.createDevice("opera");
                    dev2.setTitle("Opera Mobi");
                    dev2.setUserAgent("Opera Mobi");
                    dev2.setParent(dev);
                }

                //Crear lenguajes por defecto
                Language lang = site.createLanguage("es");
                lang.setTitle("Español");
                lang.setTitle("Español", "es");
                lang.setTitle("Spanish", "en");
                lang = site.createLanguage("en");
                lang.setTitle("Ingles");
                lang.setTitle("Ingles", "es");
                lang.setTitle("English", "en");
                //Envia estatus a pantalla
                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                out.println("addItemByURI(mtreeStore, null, '" + site.getURI() + "');");
                out.println("showStatus('Sitio Creado');");
                out.println("</script>");

                site.getSemanticObject().getModel().setTraceable(false);
            } else { //Forma de entrada(Datos iniciales)
                url.setAction("step2");
                getStep1(out, url, paramRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.debug(e);
        }
    }

    /**
     * Gets the step1.
     * 
     * @param out the out
     * @param url the url
     * @param paramRequest the param request
     * @return the step1
     */
    private void getStep1(PrintWriter out, SWBResourceURL url, SWBParamRequest paramRequest) 
    {
        String lang=paramRequest.getUser().getLanguage();
        try {

            out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){};return false;\" method=\"post\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("msgwsTitle")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Titulo.\" invalidMessage=\"Titulo es requerido.\" onkeyup=\"dojo.byId('swb_create_id').value=replaceChars4Id(this.textbox.value);dijit.byId('swb_create_id').validate()\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("msgwsID")+" <em>*</em>");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" id=\"swb_create_id\" name=\"wsid\" dojoType=\"dijit.form.ValidationTextBox\" required=\"true\" promptMessage=\"Captura Identificador.\" isValid=\"return canCreateSemanticObject(this.textbox.value);\" invalidMessage=\"Identificador invalido.\" trim=\"true\" >");
            out.println("</td>");
            out.append("</tr>");
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("usrRep")+" <em>*</em>");
            out.println("</td><td>");
            out.println("<select name=\"wsrepository\">");
            out.println("<option value=\"0\">" + paramRequest.getLocaleString("Exclusive") + "</option>");
            Iterator<UserRepository> itUsrReps = SWBContext.listUserRepositories();
            while (itUsrReps.hasNext()) {
                UserRepository usrRep = itUsrReps.next();
                out.println("<option value=\"" + usrRep.getId() + "\">" + usrRep.getDisplayTitle(lang) + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

            Iterator<SemanticClass> itcls = WebSite.sclass.listSubClasses();
            if(itcls.hasNext())
            {
                out.append("<tr><td align=\"right\">");
                out.println(paramRequest.getLocaleString("wstype")+" <em>*</em>");
                out.println("</td><td>");
                out.println("<select name=\"wstype\">");
                out.println("<option value=\""+WebSite.sclass.getClassId()+"\">" + WebSite.sclass.getDisplayName(lang) + "</option>");
                while (itcls.hasNext()) {
                    SemanticClass cls = itcls.next();
                    out.println("<option value=\"" + cls.getClassId() + "\">" + cls.getDisplayName(lang) + "</option>");
                }
                out.println("</select>");
                out.println("</td></tr>");
            }

            out.println("<td colspan=\"2\" align=\"center\">");
            out.println("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleString("save")+"</button>");
            out.println("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">"+paramRequest.getLocaleString("cancel")+"</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
        } catch (Exception e) {
            log.debug(e);
        }
    }
}
