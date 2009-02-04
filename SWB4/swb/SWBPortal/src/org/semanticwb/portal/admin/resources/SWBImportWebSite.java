/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
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

//    @Override
//    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
//        if (paramRequest.getMode().equals("editSite")) {
//            doEditWebSite(request, response, paramRequest);
//        }else {
//            super.processRequest(request, response, paramRequest);
//        }
//    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        String action = paramRequest.getAction();
        SWBResourceURL url = paramRequest.getRenderUrl();
        if (action != null && action.trim().equals("step2") && request.getParameter("wstype") != null) {
            String wstype = request.getParameter("wstype");
            if (wstype.equals("1")) { //creación de sitio nuevo
                WebSite site = SWBContext.createWebSite(request.getParameter("wsname"), request.getParameter("wsns"));
                site.setCreated(new java.util.Date(System.currentTimeMillis()));
                site.setTitle(request.getParameter("wstitle"));
                site.setHomePage(site.createWebPage("home"));

                SemanticObject semObject = SemanticObject.createSemanticObject(site.getURI());
                SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
                mgr.setLang(paramRequest.getUser().getLanguage());
                mgr.setType(mgr.TYPE_XHTML);
                SWBResourceURL urlAction = paramRequest.getActionUrl();
                urlAction.setParameter("webSiteUri", semObject.getURI());
                mgr.setAction(urlAction.toString());
                out.println(mgr.renderForm());
            } else { //creación de sitio mediante template                
                out.println(directoryList(paramRequest, request.getParameter("wsname"), request.getParameter("wsns"), request.getParameter("wstitle")));
            }
        } else if (action != null && action.trim().equals("step3")) { //creación de sitio mediante template
            if (createWebSite(response, request.getParameter("zipName"), request.getParameter("wsname"), request.getParameter("wsns"))) {
                out.println("<script language=\"JavaScript\">");
                out.println("  showStatus('" + paramRequest.getLocaleLogString("sitecreated") + "')");
                out.println("</script>");
            } else {
                out.println(paramRequest.getLocaleLogString("sitenotcreated"));
            }
        } else { //Forma de entrada(Datos iniciales)
            url.setAction("step2");
            getStep1(out, url, paramRequest);
        }
    }

    private String directoryList(SWBParamRequest paramRequest, String wsname, String wsns, String wstitle) {
        StringBuffer strbf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();
        url.setAction("step3");
        url.setParameter("wsname", wsname);
        url.setParameter("wsns", wsns);
        url.setParameter("wstitle", wstitle);
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

    private boolean createWebSite(HttpServletResponse response, String name, String newName, String newNS) {
        try {
            //Substituir x ruta dinamica
            String path = SWBPlatform.getWorkPath() + "/";
            String models = path + "models/";
            String zipdirectory = path + "sitetemplates/";
            File zip = new File(zipdirectory + name + ".zip");
            java.io.File extractTo = new File(models + newName);
            //Descomprimir zip
            org.semanticwb.SWBUtils.IO.unzip(zip, extractTo);

            FileInputStream frdfio = new FileInputStream(models + newName + "/" + name + ".rdf");
            String rdfcontent = SWBUtils.IO.readInputStream(frdfio);
            FileInputStream fxmlio = new FileInputStream(models + newName + "/siteInfo.xml");
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
            rdfcontent = rdfcontent.replaceAll(oldNamespace, newNS); //Reempplazar namespace anterior x nuevo
            //rdfcontent = SWBUtils.TEXT.replaceAllIgnoreCase(rdfcontent, oldName, newName); //Reemplazar nombre anterior x nuevo nombre
            rdfcontent = parseRdfContent(rdfcontent, oldName, newName, newNS);

            //Mediante inputStream creado generar sitio
            InputStream io = SWBUtils.IO.getStreamFromString(rdfcontent);
            SWBPlatform.getSemanticMgr().createModelByRDF(newName, newNS, io);
            WebSite website=SWBContext.getWebSite(newName);
            website.setDescription(olDescription);
            

            //Eliminar archivo rdf y archivo xml
            new File(models + newName + "/" + name + ".rdf").delete();
            new File(models + newName + "/siteInfo.xml").delete();

            PrintWriter out=response.getWriter();
            out.println("<script type=\"text/javascript\">");
            out.println("hideDialog();");
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
            
            out.println("<form class=\"swbform\" name=\"frmImport1\" action=\"" + url.toString() + "\" dojoType=\"dijit.form.Form\" onSubmit=\"submitForm('frmImport1');return false;\" method=\"post\">");
            //out.println("<form  class=\"swbform\" name=\"frmImport1\"  method=\"post\">");
            out.println("<fieldset>");
            out.println("<table>");
            out.append("<tr>");
            out.append("<td>");
            out.println(paramRequest.getLocaleLogString("msgwsName"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wsname\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwsNameSpace"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wsns\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwsTitle"));
            out.println("</td>");
            out.append("<td>");
            out.println("<input type=\"text\" name=\"wstitle\" size=\"30\">");
            out.println("</td>");
            out.append("</tr>");

            out.append("<tr><td>");
            out.println(paramRequest.getLocaleLogString("msgwstype"));
            out.println("</td><td>");
            out.println("<select name=\"wstype\">");
            out.println("<option value=\"1\">" + paramRequest.getLocaleLogString("msgwstype1") + "</option>");
            out.println("<option value=\"2\">" + paramRequest.getLocaleLogString("msgwstype2") + "</option>");
            out.println("</select>");
            out.println("</td></tr>");
            out.println("<tr><td><input type=\"button\" onclick=\"send(this.form)\" value=\"Enviar\"></td>");
            //out.println("<td><button dojoType='dijit.form.Button' onclick=\"send(this.form)\">Enviar</button>");
            out.println("</td></tr>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("</form>");
           
            //out.println("</div>");

            out.println("<script type=\"text/javascript\">");
            out.println("function send(forma){");
            out.println("if(forma.wstype.value==1){");
            out.println("hideDialog();");
            out.println("addNewTab('prueba','"+url.toString()+"');");
            out.println("}else{");
            out.println("forma.submit();");
            out.println("}");
            out.println("}");
            out.println("</script>");

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
