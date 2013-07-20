/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
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

/**
 *
 * @author jorge.jimenez
 */
public class NewBrand extends GenericResource {

    /** The log. */
    private static Logger log = SWBUtils.getLogger(NewBrand.class);

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
                
                SemanticMgr mgr=SWBPlatform.getSemanticMgr();
                SemanticClass sclass=mgr.getVocabulary().getSemanticClassById("social:SocialSite");
                if(sclass!=null)
                {
                    SemanticModel model=mgr.createModel(id, ns);
                    site=(WebSite)model.createGenericObject(model.getObjectUri(id, sclass), sclass);
                }
                if(site==null) return;

                site.getSemanticObject().getModel().setTraceable(false);
                try
                {
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
                    
                    //PARA SWBSOCIAL SITES NO EXISTIRA UNA SECCIÓN HOME.
                    /*
                    WebPage home = site.createWebPage("home");
                    site.setHomePage(home);
                    home.setUndeleteable(true);
                    home.setTitle("Home");
                    home.setSortName("z");
                    * */
                    //Crea grupo de templates de defecto
                    TemplateGroup grp = site.createTemplateGroup();
                    grp.setTitle("Default");

                    //Crea recursos de defecto
                    if (site.getResourceType("ExcelContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("ExcelContent");
                        ptype.setResourceBundle("org.semanticwb.resource.office.sem.ExcelResource");
                        ptype.setResourceMode(1);
                        ptype.setTitle("ExcelContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.resource.office.sem.ExcelResource");
                    }

                    if (site.getResourceType("WordContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("WordContent");
                        ptype.setResourceBundle("org.semanticwb.resource.office.sem.WordResource");
                        ptype.setResourceMode(1);
                        ptype.setTitle("WordContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.resource.office.sem.WordResource");
                    }

                    if (site.getResourceType("PPTContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PPTContent");
                        ptype.setResourceBundle("org.semanticwb.resource.office.sem.PPTResource");
                        ptype.setResourceMode(1);
                        ptype.setTitle("PPTContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.resource.office.sem.PPTResource");
                    }

                    if (site.getResourceType("HTMLContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("HTMLContent");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.sem.HTMLContent");
                        ptype.setResourceMode(1);
                        //ptype.setResourceCache(600);
                        ptype.setTitle("HTMLContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.sem.HTMLContent");
                    }
                    if (site.getResourceType("JSPResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("JSPResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.JSPResource");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("JSPResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.JSPResource");
                    }
                    /* Ver si aplicaran mas adelante (a futuro)
                    if (site.getResourceType("Banner") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Banner");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Banner");
                        ptype.setResourceMode(2);
                        ptype.setTitle("Banner");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Banner");
                    }

                    if (site.getResourceType("Promo") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Promo");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Promo");
                        ptype.setResourceMode(2);
                        ptype.setTitle("Promo");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Promo");
                    }

                    if (site.getResourceType("AdvancedSearch") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("AdvancedSearch");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.AdvancedSearch");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("AdvancedSearch");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.AdvancedSearch");
                    }

                    if (site.getResourceType("Print") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Print");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Print");
                        ptype.setResourceMode(2);
                        ptype.setTitle("Print");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Print");
                    }

                    if (site.getResourceType("Window") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Window");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Window");
                        ptype.setResourceMode(2);
                        ptype.setTitle("Window");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Window");
                    }

                    if (site.getResourceType("StaticText") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("StaticText");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.StaticText");
                        ptype.setResourceMode(2);
                        ptype.setTitle("StaticText");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.StaticText");
                    }

                    if (site.getResourceType("SiteMap") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("SiteMap");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBSiteMap");
                        ptype.setResourceMode(3);
                        ptype.setTitle("SiteMap");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBSiteMap");
                    }

                    if (site.getResourceType("Recommend") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Recommend");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Recommend");
                        ptype.setResourceMode(3);
                        ptype.setTitle("Recommend");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Recommend");
                    }

                    if (site.getResourceType("QueryResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("QueryResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.QueryResource");
                        ptype.setResourceMode(3);
                        ptype.setTitle("QueryResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.QueryResource");
                    }

                    if (site.getResourceType("VirtualResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("VirtualResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.VirtualResource");
                        ptype.setResourceMode(1);
                        ptype.setTitle("VirtualResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.VirtualResource");
                    }

                    if (site.getResourceType("RecommendSwf") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("RecommendSwf");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.RecommendSwf");
                        ptype.setResourceMode(2);
                        ptype.setTitle("RecommendSwf");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.RecommendSwf");
                    }

                    if (site.getResourceType("Poll") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Poll");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Poll");
                        ptype.setResourceMode(2);
                        ptype.setTitle("Poll");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Poll");
                    }

                    if (site.getResourceType("Menu") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Menu");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenu");
                        ptype.setResourceMode(ptype.MODE_STRATEGY);
                        ptype.setTitle("Menu");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenu");
                    }

                    if (site.getResourceType("MenuMap") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("MenuMap");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuMap");
                        ptype.setResourceMode(2);
                        ptype.setTitle("MenuMap");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuMap");
                    }

                    if (site.getResourceType("MenuNivel") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("MenuNivel");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuNivel");
                        ptype.setResourceMode(2);
                        ptype.setTitle("MenuNivel");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuNivel");
                    }

                    if (site.getResourceType("PHPResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PHPResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.PHPResource");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("PHPResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.PHPResource");
                    }

                    if (site.getResourceType("PythonResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PythonResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.PythonResource");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("PythonResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.PythonResource");
                    }

                    if (site.getResourceType("GroovyResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("GroovyResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyResource");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("GroovyResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyResource");
                    }

                    if (site.getResourceType("GroovyEditor") == null)
                    {
                        site.begin();
                        ResourceType ptype = site.createResourceType("GroovyEditor");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.GroovyEditor");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("GroovyEditor");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.GroovyEditor");
                    }

                    if (site.getResourceType("JSPEditor") == null)
                    {
                        site.begin();
                        ResourceType ptype = site.createResourceType("JSPEditor");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.JSPEditor");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("JSPEditor");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.JSPEditor");
                    }

                    if (site.getResourceType("PythonEditor") == null)
                    {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PythonEditor");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.PythonEditor");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("PythonEditor");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.PythonEditor");
                    }

                    if (site.getResourceType("PHPEditor") == null)
                    {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PHPEditor");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.PHPEditor");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("PHPEditor");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.PHPEditor");
                    }

                    if (site.getResourceType("Language") == null)
                    {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Language");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Language");
                        ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                        ptype.setTitle("Language");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Language");
                    }

                    if (site.getResourceType("TematicIndexXSL") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("TematicIndexXSL");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.TematicIndexXSL");
                        ptype.setResourceMode(1);
                        ptype.setTitle("TematicIndexXSL");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.TematicIndexXSL");
                    }


                    if (site.getResourceType("FrameContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("FrameContent");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.FrameContent");
                        ptype.setResourceMode(1);
                        ptype.setTitle("FrameContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.FrameContent");
                    }

                    if (site.getResourceType("IFrameContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("IFrameContent");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.IFrameContent");
                        ptype.setResourceMode(1);
                        ptype.setTitle("IFrameContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.IFrameContent");
                    }

                    if (site.getResourceType("CommentSwf") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("CommentSwf");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.CommentSwf");
                        ptype.setResourceMode(2);
                        ptype.setTitle("CommentSwf");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.CommentSwf");
                    }

                    if (site.getResourceType("Comment") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Comment");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Comment");
                        ptype.setResourceMode(3);
                        ptype.setTitle("Comment");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Comment");
                    }

                    if (site.getResourceType("RemoteWebApp") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("RemoteWebApp");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.RemoteWebApp");
                        ptype.setResourceMode(3);
                        ptype.setTitle("RemoteWebApp");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.RemoteWebApp");
                    }

                    if (site.getResourceType("UrlContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("UrlContent");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBUrlContent");
                        ptype.setResourceMode(3);
                        ptype.setTitle("UrlContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBUrlContent");
                    }

                    if (site.getResourceType("Search") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Search");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WBSearch");
                        ptype.setResourceMode(3);
                        ptype.setTitle("Search");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WBSearch");
                    }

                    if (site.getResourceType("GoogleGadget") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("GoogleGadget");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                        ptype.setResourceMode(3);
                        ptype.setTitle("GoogleGadget");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.googlegadgets.GoogleGadget");
                    }

                    if (site.getResourceType("SparQLQueryResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("SparQLQueryResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.SparqlQueryResource");
                        ptype.setResourceMode(3);
                        ptype.setTitle("SparQLQueryResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.SparqlQueryResource");
                    }

                    if (site.getResourceType("Wiki") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Wiki");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.wiki.Wiki");
                        ptype.setResourceMode(3);
                        ptype.setTitle("Wiki");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.wiki.Wiki");
                    }

                    if (site.getResourceType("PDFContent") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("PDFContent");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.PDFContent");
                        ptype.setResourceMode(ResourceType.MODE_CONTENT);
                        ptype.setTitle("PDFContent");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.PDFContent");
                    }

                    if (site.getResourceType("Login") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Login");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.Login");
                        ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                        ptype.setTitle("Login");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.Login");
                    }

                    if (site.getResourceType("UserRegistration") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("UserRegistration");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.UserRegistration");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("UserRegistration");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.UserRegistration");
                    }

                    if (site.getResourceType("RSSResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("RSSResource");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                        ptype.setResourceMode(ResourceType.MODE_CONTENT);
                        ptype.setTitle("RSSResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    }

                    if (site.getResourceType("DataBaseResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("DataBaseResource");
                        ptype.setResourceBundle("com.infotec.wb.resources.database.DataBaseResource");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("DataBaseResource");
                        site.commit();
                        ptype.setResourceClassName("com.infotec.wb.resources.database.DataBaseResource");
                    }

                    if (site.getResourceType("SWBBlog") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("SWBBlog");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                        ptype.setResourceMode(3);
                        ptype.setTitle("Blog");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                        ptype.setResourceOWL("org.semanticwb.portal.resources.sem.blog.SWBBlog");
                    }

                    if (site.getResourceType("WebPageComments") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("WebPageComments");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.sem.SWBComments");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("WebPageComments");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.sem.SWBComments");
                        ptype.setResourceOWL("org.semanticwb.portal.resources.sem.SWBComments");
                    }

                    if (site.getResourceType("Forum") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("Forum");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.sem.forum.SWBForum");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("Forum");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.sem.forum.SWBForum");
                        ptype.setResourceOWL("org.semanticwb.portal.resources.sem.forum.SWBForum");
                    }

                    if (site.getResourceType("RankWebPage") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("RankWebPage");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("RankWebPage");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                        ptype.setResourceOWL("org.semanticwb.portal.resources.sem.SWBRankWebPage");
                    }

    //                if (site.getResourceType("Directory") == null) {
    //                    site.begin();
    //                    ResourceType ptype = site.createResourceType("Directory");
    //                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.directory.Directory");
    //                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
    //                    ptype.setTitle("Directory");
    //                    site.commit();
    //                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.directory.Directory");
    //                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.directory.Directory");
    //                }

    //                if (site.getResourceType("Events") == null) {
    //                    site.begin();
    //                    ResourceType ptype = site.createResourceType("Events");
    //                    ptype.setResourceBundle("org.semanticwb.portal.resources.sem.events.Events");
    //                    ptype.setResourceMode(ResourceType.MODE_SYSTEM);
    //                    ptype.setTitle("Events");
    //                    site.commit();
    //                    ptype.setResourceClassName("org.semanticwb.portal.resources.sem.events.Events");
    //                    ptype.setResourceOWL("org.semanticwb.portal.resources.sem.events.Events");
    //                }

                    if (site.getResourceType("WebPageOnLineCreate") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("WebPageOnLineCreate");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    }

                    if (site.getResourceType("WebPageOnLineCreate") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.WebPageOnLineCreate");
                        ptype.setResourceMode(ResourceType.MODE_SYSTEM);
                        ptype.setTitle("WebPageOnLineCreate");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.WebPageOnLineCreate");
                    }

                    if (site.getResourceType("RSSResource") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("WebPageOnLineCreate");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.RSSResource");
                        ptype.setResourceMode(ResourceType.MODE_CONTENT);
                        ptype.setTitle("RSSResource");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.RSSResource");
                    }

                    if (site.getResourceType("InlineEdit") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("InlineEdit");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.InlineEdit");
                        ptype.setResourceMode(ResourceType.MODE_STRATEGY);
                        ptype.setTitle("InlineEdit");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.InlineEdit");
                    }

                    if (site.getResourceType("ImageGallery") == null) {
                        site.begin();
                        ResourceType ptype = site.createResourceType("ImageGallery");
                        ptype.setResourceBundle("org.semanticwb.portal.resources.ImageGallery");
                        ptype.setResourceMode(ResourceType.MODE_CONTENT);
                        ptype.setTitle("ImageGallery");
                        site.commit();
                        ptype.setResourceClassName("org.semanticwb.portal.resources.ImageGallery");
                    }
                    */
                    site.begin();

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
                    lang.setTitle("Inglés");
                    lang.setTitle("Inglés", "es");
                    lang.setTitle("English", "en");
                    //Envia estatus a pantalla
                    
                    out.println("<script type=\"text/javascript\">");
                    out.println("hideDialog();");
                    out.println("addItemByURI(mtreeStore, null, '" + site.getURI() + "');");
                    out.println("showStatus('Sitio Creado');");
                    out.println("</script>");

                    site.commit();
                    site.getSemanticObject().getModel().setTraceable(false);

                    //Refrescar el árbol (Zkoss)
                    //Tree tree=(Tree)request.getSession().getAttribute("tree");
                    //System.out.println(tree);
                    //tree.setModel(tree.getModel());
                    //Refrescar nodo del árbol...
                    //SWBSocialResourceUtils.Resources.createNewBrandNode(request, paramRequest, site);
                    //SWBSocialResourceUtils.Resources.createNewBrandNode(request, paramRequest.getUser(), site);
                    //SWBResourceURL urlRedirect = paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT);                    
                    //response.sendRedirect(urlRedirect.toString() +"?id=" + id);
                }catch(Exception e)
                {
                    site.abort();
                    log.error(e);
                }
            } else { //Forma de entrada(Datos iniciales)
                url.setAction("step2");
                getStep1(out, url, paramRequest);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            log.error(e);
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
            out.println("<div dojoType=\"dijit.layout.ContentPane\" style=\"border:0px; width:100%; height:100%\">");
            out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){};return false;\" method=\"post\">");
            //out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1'); return false;\" method=\"post\">");
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
            out.println("<input type=\"hidden\" name=\"wsrepository\" value=\"uradm\"/>"); 
            /*
            out.append("<tr><td align=\"right\">");
            out.println(paramRequest.getLocaleString("usrRep")+" <em>*</em>");
            out.println("</td><td>");
            out.println("<select name=\"wsrepository\">");
            out.println("<option value=\"0\">" + paramRequest.getLocaleString("Exclusive") + "</option>");
            Iterator<UserRepository> itUsrReps = SWBContext.listUserRepositories();
            while (itUsrReps.hasNext()) {
                UserRepository usrRep = itUsrReps.next();
                System.out.println("usrRep.getId():"+usrRep.getId());
                out.println("<option value=\"" + usrRep.getId() + "\">" + usrRep.getDisplayTitle(lang) + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");
            * */
            /*
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
            */
            out.println("<td colspan=\"2\" align=\"center\">");
            out.println("<button dojoType='dijit.form.Button' type=\"submit\">"+paramRequest.getLocaleString("save")+"</button>");
            out.println("<button dojoType='dijit.form.Button' onclick=\"dijit.byId('swbDialog').hide();\">"+paramRequest.getLocaleString("cancel")+"</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");            
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/loading.gif\"/></span>");
            out.println("</div>");
        } catch (Exception e) {
            log.debug(e);
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String jspResponse = "/swbadmin/jsp/social/objectTab.jsp";
        try {
            RequestDispatcher dis = request.getRequestDispatcher(jspResponse);
            String id = request.getParameter("id") == null ? "" : request.getParameter("id");
            //request.setAttribute("suri", URLEncoder.encode("http://www." + id + ".swb#" + id));
            request.setAttribute("suri", "http://www." + id + ".swb#" + id);
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }    
    }
}