/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.repository.Workspace;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author jorge.jimenez
 */
public class SWBImportWebSite extends GenericResource {

    private static Logger log = SWBUtils.getLogger(SWBImportWebSite.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        User user = paramRequest.getUser();
        PrintWriter out = response.getWriter();
        String action = paramRequest.getAction();
        SWBResourceURL url = paramRequest.getRenderUrl();
        if (action != null && action.trim().equals("step2") && request.getParameter("wstype") != null) {
            String wstype = request.getParameter("wstype");
            String title = request.getParameter("wstitle");
            String id = request.getParameter("wsid");
            String usrRep = request.getParameter("wsrepository");
            if (wstype.equals("1")) { //creación de sitio nuevo
                WebSite site = SWBContext.createWebSite(id, "http://www." + id + ".swb#");
                //site.setCreated(new java.util.Date(System.currentTimeMillis()));
                site.setTitle(title);

                UserRepository newUsrRep = null;
                if (usrRep != null) {
                    if (usrRep.equals("0")) { //Utilizara un repositorio exclusivo
                        newUsrRep = SWBContext.createUserRepository(id+"_usr", "http://user." + id + ".swb#");
                        newUsrRep.setTitle("Repositorio de usuarios("+title+")","es");
                        newUsrRep.setTitle("Users Repository("+title+")","en");
                        newUsrRep.setUndeleteable(true);
                        //TODO: cambiar a semantic prop
                        newUsrRep.setProperty(UserRepository.SWBUR_AuthMethod, "FORM"); //BASIC
                        newUsrRep.setProperty(UserRepository.SWBUR_LoginContext, "swb4TripleStoreModule");
                        newUsrRep.setProperty(UserRepository.SWBUR_CallBackHandlerClassName, "org.semanticwb.security.auth.SWB4CallbackHandlerLoginPasswordImp");

                        if (user != null && user.isRegistered()) {
                            newUsrRep.setCreator(user);
                        }
                        site.setUserRepository(newUsrRep);
                        site.addSubModel(newUsrRep.getSemanticObject());
                    } else { //Utilizara un repositorio existente
                        UserRepository exitUsrRep = SWBContext.getUserRepository(usrRep);
                        site.setUserRepository(exitUsrRep);
                    }
                }

//                //creación de repositorio de documentoss
//                Workspace workspace = SWBContext.createWorkspace(id+"_rep", "http://repository." + id + ".swb#");
//                workspace.setTitle("Repositorio de documentos("+title+")", "es");
//                workspace.setTitle("Documents Repository("+title+")", "en");
//                //TODO: undeleted repository
//                //workspace.setUn
//                site.addSubModel(workspace.getSemanticObject());

                site.setHomePage(site.createWebPage("home"));
                site.getHomePage().setUndeleteable(true);

                if(site.getResourceType("Banner")==null)
                {
                    ResourceType ptype=site.createResourceType("Banner");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Banner");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Banner");
                }

                if(site.getResourceType("Promo")==null)
                {
                    ResourceType ptype=site.createResourceType("Promo");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Promo");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Promo");
                }

                if(site.getResourceType("Print")==null)
                {
                    ResourceType ptype=site.createResourceType("Print");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Print");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Print");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Print");
                }

                if(site.getResourceType("Window")==null)
                {
                    ResourceType ptype=site.createResourceType("Window");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Window");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Window");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Window");
                }

                if(site.getResourceType("StaticText")==null)
                {
                    ResourceType ptype=site.createResourceType("StaticText");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.StaticText");
                    ptype.setResourceMode(2);
                    ptype.setTitle("StaticText");
                }

                if(site.getResourceType("WBSiteMap")==null)
                {
                    ResourceType ptype=site.createResourceType("WBSiteMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBSiteMap");
                    ptype.setResourceMode(3);
                    ptype.setTitle("WBSiteMap");
                }

                if(site.getResourceType("Recommend")==null)
                {

                    ResourceType ptype=site.createResourceType("Recommend");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Recommend");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Recommend");
                }

                if(site.getResourceType("QueryResource")==null)
                {

                    ResourceType ptype=site.createResourceType("QueryResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.QueryResource");
                    ptype.setResourceMode(2);
                    ptype.setTitle("QueryResource");
                }

                if(site.getResourceType("RecommendSwf")==null)
                {

                    ResourceType ptype=site.createResourceType("RecommendSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RecommendSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("RecommendSwf");
                }

                if(site.getResourceType("Poll")==null)
                {

                    ResourceType ptype=site.createResourceType("Poll");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Poll");
                    ptype.setResourceMode(2);
                    ptype.setTitle("Poll");
                }

                if(site.getResourceType("WBMenuMap")==null)
                {

                    ResourceType ptype=site.createResourceType("WBMenuMap");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuMap");
                    ptype.setResourceMode(2);
                    ptype.setTitle("WBMenuMap");
                }

                if(site.getResourceType("WBMenuNivel")==null)
                {
                    ResourceType ptype=site.createResourceType("WBMenuNivel");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBMenuNivel");
                    ptype.setResourceMode(2);
                    ptype.setTitle("WBMenuNivel");
                }

                if(site.getResourceType("JSPResource")==null)
                {

                    ResourceType ptype=site.createResourceType("JSPResource");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.JSPResource");
                    ptype.setResourceMode(3);
                    ptype.setTitle("JSPResource");
                }

                if(site.getResourceType("TematicIndexXSL")==null)
                {
                    ResourceType ptype=site.createResourceType("TematicIndexXSL");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.TematicIndexXSL");
                    ptype.setResourceMode(1);
                    ptype.setTitle("TematicIndexXSL");
                }


                if(site.getResourceType("FrameContent")==null)
                {
                    ResourceType ptype=site.createResourceType("FrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.FrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("FrameContent");
                }

                if(site.getResourceType("IFrameContent")==null)
                {
                    ResourceType ptype=site.createResourceType("IFrameContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.IFrameContent");
                    ptype.setResourceMode(1);
                    ptype.setTitle("IFrameContent");
                }

                if (site.getResourceType("CommentSwf") == null)
                {
                    ResourceType ptype = site.createResourceType("CommentSwf");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.CommentSwf");
                    ptype.setResourceMode(2);
                    ptype.setTitle("CommentSwf");
                }

                if (site.getResourceType("RemoteWebApp") == null)
                {
                    ResourceType ptype = site.createResourceType("RemoteWebApp");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.RemoteWebApp");
                    ptype.setResourceMode(3);
                    ptype.setTitle("RemoteWebApp");
                }

                if (site.getResourceType("WBUrlContent") == null)
                {
                    ResourceType ptype = site.createResourceType("WBUrlContent");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.WBUrlContent");
                    ptype.setResourceMode(3);
                    ptype.setTitle("WBUrlContent");
                }

                if (site.getResourceType("Serch") == null)
                {
                    ResourceType ptype = site.createResourceType("Serch");
                    ptype.setResourceClassName("org.semanticwb.portal.resources.Serch");
                    ptype.setResourceBundle("org.semanticwb.portal.resources.Serch");
                    ptype.setResourceMode(3);
                    ptype.setTitle("Serch");
                }

                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                //out.println("addItemByURI(mdocStore, null, '" + workspace.getURI() + "');");
                out.println("addItemByURI(mtreeStore, null, '" + site.getURI() + "');");
                out.println("showStatus('Sitio Creado');");
                out.println("</script>");
            } else { //creación de sitio mediante template                
                out.println(directoryList(paramRequest, title, id, usrRep));
            }
        } else if (action != null && action.trim().equals("step3")) { //creación de sitio mediante template
            if (!createWebSite(response, request.getParameter("zipName"), request.getParameter("wstitle"), request.getParameter("wsid"), request.getParameter("repository"))) {
                out.println("<script type=\"text/javascript\">");
                out.println("hideDialog();");
                out.println("showError('" + paramRequest.getLocaleLogString("sitenotcreated") + "');");
                out.println("</script>");
            }
        } else { //Forma de entrada(Datos iniciales)
            url.setAction("step2");
            getStep1(out, url, paramRequest);
        }
    }

    private String directoryList(SWBParamRequest paramRequest, String wstitle, String wsid, String repository) {
        StringBuffer strbf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setAction("step3");
        url.setParameter("wstitle", wstitle);
        url.setParameter("wsid", wsid);
        url.setParameter("repository", repository);
        File file = new File(SWBPlatform.getWorkPath() + "/sitetemplates/");
        File[] files = file.listFiles();
        strbf.append("<div class=\"swbform\">");
        strbf.append("<table>");
        strbf.append("<form>");
        strbf.append("<tr>");
        strbf.append("<td colspan=\"2\">");
        strbf.append("<fieldset>");
        strbf.append("<table>");
        strbf.append("<tr>");
        strbf.append("<td>Template</td>");
        strbf.append("<td>Tamaño</td>");
        strbf.append("</tr>");
        for (int i = 0; i < files.length; i++) {
            File filex = files[i];
            String fileName = filex.getName();
            if (filex.isFile() && fileName.endsWith(".zip")) {
                int pos = fileName.lastIndexOf(".");
                if (pos > -1) {
                    fileName = fileName.substring(0, pos);
                }
                strbf.append("<tr><td>");
                url.setParameter("zipName", fileName);
                strbf.append("<a href=\"" + url.toString() + "\" onclick=\"submitUrl('" + url.toString() + "',this);return false;\">" + fileName + "</a>");
                strbf.append("</td><td>");
                strbf.append(filex.length() + " bytes");
                strbf.append("</td></tr>");
            }
        }
        strbf.append("</table>");
        strbf.append("</fieldset>");
        strbf.append("</td></tr>");
        strbf.append("</form>");
        strbf.append("</table>");
        strbf.append("</div>");
        return strbf.toString();
    }

    private boolean createWebSite(HttpServletResponse response, String name, String newTitle, String newId, String repository) {
        try {
            //Substituir x ruta dinamica
            String path = SWBPlatform.getWorkPath() + "/";
            String models = path + "models/";
            String zipdirectory = path + "sitetemplates/";
            File zip = new File(zipdirectory + name + ".zip");
            java.io.File extractTo = new File(models + newTitle);
            //Descomprimir zip
            org.semanticwb.SWBUtils.IO.unzip(zip, extractTo);

            FileInputStream frdfio = new FileInputStream(models + newTitle + "/" + name + ".rdf");
            String rdfcontent = SWBUtils.IO.readInputStream(frdfio);
            FileInputStream fxmlio = new FileInputStream(models + newTitle + "/siteInfo.xml");
            Document dom = SWBUtils.XML.xmlToDom(fxmlio);
            String oldName = null;
            String oldNamespace = null;
            String oldTitle = null;
            String olDescription = null;

            if (dom.getElementsByTagName("name").getLength() > 0) {
                oldName = dom.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
            }
            if (dom.getElementsByTagName("namespace").getLength() > 0) {
                oldNamespace = dom.getElementsByTagName("namespace").item(0).getFirstChild().getNodeValue();
            }
            if (dom.getElementsByTagName("title").getLength() > 0) {
                oldTitle = dom.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
            }
            if (dom.getElementsByTagName("description").getLength() > 0) {
                olDescription = dom.getElementsByTagName("description").item(0).getFirstChild().getNodeValue();
            }

            //Parseo de nombre de NameSpace anteriores por nuevos
            String newNs = "http://www." + newId + ".swb#";
            rdfcontent = rdfcontent.replaceAll(oldNamespace, newNs); //Reempplazar namespace anterior x nuevo
            //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
            rdfcontent = parseRdfContent(rdfcontent, oldName, newTitle, newNs);

            //Mediante inputStream creado generar sitio
            InputStream io = SWBUtils.IO.getStreamFromString(rdfcontent);
            SWBPlatform.getSemanticMgr().createModelByRDF(newTitle, newNs, io);
            WebSite website = SWBContext.getWebSite(newTitle);
            website.setDescription(olDescription);
/*
            //Crea repositorio de usuarios para el nuevo sitio
            UserRepository newUsrRep = null;
            if (repository != null) {
                if (repository.equals("0")) { //Utilizara un repositorio exclusivo
                    newUsrRep = SWBContext.createUserRepository(newTitle, "http://users." + newId + "_usr.swb#");
                    newUsrRep.setTitle("Repositorio de usuarios("+newTitle+")","es");
                    newUsrRep.setTitle("Users Repository("+newTitle+")","en");
                    website.addSubModel(newUsrRep.getSemanticObject());
                    website.setUserRepository(newUsrRep);
                } else { //Utilizara un repositorio existente
                    UserRepository existUsrRep = SWBContext.getUserRepository(repository);
                    website.setUserRepository(existUsrRep);
                }
            }
            //Crea repositorio de documentos para el nuevo sitio
            Workspace workspace = SWBContext.createWorkspace(newTitle, "http://repository." + newId + "_rep.swb#");
            workspace.setTitle("Repositorio de documentos("+newTitle+")", "es");
            workspace.setTitle("Documents Repository("+newTitle+")", "en");
            website.addSubModel(workspace.getSemanticObject());
*/
            //Eliminar archivo rdf y archivo xml
            new File(models + newTitle + "/" + name + ".rdf").delete();
            new File(models + newTitle + "/siteInfo.xml").delete();

            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("hideDialog();");
//            out.println("addItemByURI(mdocStore, null, '" + workspace.getURI() + "');");
            out.println("addItemByURI(mtreeStore, null, '" + website.getURI() + "');");
            out.println("showStatus('Sitio Creado');");
            out.println("</script>");

            return true;
        } catch (Exception e) {
            log.debug(e);
        }
        return false;
    }

    private String parseRdfContent(String rdfcontent, String oldName, String newName, String newNS) {
        Document dom = null;
        try {
            dom = SWBUtils.XML.xmlToDom(rdfcontent);
            NodeList nodeList = dom.getElementsByTagName("rdf:Description");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node nodeDescr = nodeList.item(i);
                NamedNodeMap nodeMap = nodeDescr.getAttributes();
                for (int j = 0; j < nodeMap.getLength(); j++) {
                    String nvalue = nodeMap.item(j).getNodeValue();
                    if (nvalue != null && nvalue.equalsIgnoreCase(newNS + "#" + oldName)) {
                        nodeMap.item(j).setNodeValue(newNS + "#" + newName); //ver como tengo que poner el newName, si debe ser con minusculas
                        NodeList nlist = nodeDescr.getChildNodes();
                        for (int k = 0; k < nlist.getLength(); k++) {
                            if (nlist.item(k).getNodeName().endsWith("title")) {
                                nlist.item(k).getFirstChild().setNodeValue(newName);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SWBUtils.TEXT.replaceFirstIgnoreCase(SWBUtils.XML.domToXml(dom), "xmlns:" + oldName, "xmlns:" + newName);
    }

    private void getStep1(PrintWriter out, SWBResourceURL url, SWBParamRequest paramRequest) {
        try {

            out.println("<form class=\"swbform\" id=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');try{document.getElementById('csLoading').style.display='inline';}catch(noe){}return false;\" method=\"post\">");
            out.println("<fieldset>");
            out.println("<table>");

//            out.append("<tr>");
//            out.append("<td>");
//            out.println(paramRequest.getLocaleLogString("msgwsName"));
//            out.println("</td>");
//            out.append("<td>");
//            out.println("<input type=\"text\" name=\"wsname\" size=\"30\">");
//            out.println("</td>");
//            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwsTitle"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println("ID:");
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wsid\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("usrRep"));
            out.println("</td><td>");
            out.println("<select name=\"wsrepository\">");
            out.println("<option value=\"0\">" + paramRequest.getLocaleLogString("Exclusive") + "</option>");
            Iterator<UserRepository> itUsrReps = SWBContext.listUserRepositorys();
            while (itUsrReps.hasNext()) {
                UserRepository usrRep = itUsrReps.next();
                out.println("<option value=\"" + usrRep.getId() + "\">" + usrRep.getTitle() + "</option>");
            }
            out.println("</select>");
            out.println("</td></tr>");

//            out.append("<tr><td>");
//            out.println(paramRequest.getLocaleLogString("msgwsNameSpace"));
//            out.println("</td>");
//            out.append("<td>");
//            out.println("<input type=\"text\" name=\"wsns\" size=\"30\">");
//            out.println("</td>");
//            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwstype"));
            out.println("</td><td>");
            out.println("<select name=\"wstype\">");
            out.println("<option value=\"1\">" + paramRequest.getLocaleLogString("msgwstype1") + "</option>");
            out.println("<option value=\"2\">" + paramRequest.getLocaleLogString("msgwstype2") + "</option>");
            out.println("</select>");
            out.println("</td></tr>");
            out.println("<td><button dojoType='dijit.form.Button' type=\"submit\">Enviar</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<span id=\"csLoading\" style=\"width: 100px; display: none\" align=\"center\">&nbsp;&nbsp;&nbsp;<img src=\""+SWBPlatform.getContextPath()+"/swbadmin/images/loading.gif\"/></span>");

        } catch (Exception e) {
            log.debug(e);
        }
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("webSiteUri"));
        SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
        mgr.processForm(request);
    }
}
