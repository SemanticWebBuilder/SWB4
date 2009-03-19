/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.admin.resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.Template;
import org.semanticwb.model.TemplateGroup;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
//import org.semanticwb.portal.services.TemplateSrv;

/**
 *
 * @author juan.fernandez
 */
public class SWBATemplate extends GenericResource {

    private Logger log = SWBUtils.getLogger(SWBATemplate.class);
    private boolean debugVar = false;

    /** Creates a new instance of WBATemplates */
    public SWBATemplate() {
    }

    /** User view, show the template information
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an AF Exception
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        //Resource base = getResourceBase();
        PrintWriter out = response.getWriter();

        if (debugVar) {
            System.out.println("entro a doView");
        }
        String strTm = request.getParameter("tm");
        if ((request.getParameter("act") != null && request.getParameter("act").equals("add")) || (request.getParameter("act") == null && request.getParameter("id") != null) || ((request.getParameter("act") != null && request.getParameter("act").equals("edit")) && request.getParameter("id") != null)) {
            doEdit(request, response, paramRequest);
        } else {
            if (request.getParameter("act") != null && request.getParameter("act").equals("preview")) {
                StringBuffer out1 = new StringBuffer();
                String id = request.getParameter("id");
                String version = request.getParameter("version");
                Template tpl = SWBContext.getWebSite(strTm).getTemplate(id);
                VersionInfo tvi = getVersionNum(tpl.getActualVersion(), Integer.parseInt(version));
                String fileName = tvi.getVersionFile(); // temporal
                String workpath = SWBContext.getWebSite(strTm).getTemplate(id).getWorkPath() + "/" + version; //+"/sites/"+strTm+"/templates/"+id+"/"+version;
                String file = null;
                try {
                    file = SWBUtils.IO.readInputStream(new FileInputStream(workpath + "/" + fileName));
                    if (file.equals(null)) {
                        file = paramRequest.getLocaleString("msgContentNotDefined");
                    }
                } catch (Exception e) {
                    log.error(e);
                }
                out1.append(file);
                out.println(out1.toString());
            } else {
                String id = "0";
                if (request.getParameter("act") != null && request.getParameter("act").equals("remove")) {
                    try {
                        if (request.getParameter("id") != null) {
                            id = request.getParameter("id");
                        }
                        Template rTemplate = SWBContext.getWebSite(strTm).getTemplate(id);
                        rTemplate.setDeleted(true);
                        User user = paramRequest.getUser();
                        rTemplate.setModifiedBy(user);
                        //if(user==null) user = new WBUser(paramRequest.getTopic().getMap().getDbdata().getRepository());
                        //rTemplate.update(user.getId(),paramRequest.getLocaleString("msgStatusTemplateDeletedID")+": "+id);
                        //rTemplate=null;
                        out.println("<script>wbTree_remove();</script>");
                        out.println(paramRequest.getLocaleString("msgStatusTemplateDeletedID"));
                    } catch (Exception e) {
                        log.error(paramRequest.getLocaleString("msgStatusTemplateDeletedID"));
                        out.println("<font color=red>" + paramRequest.getLocaleString("msgErrorDeleteTemplateID") + ": " + id + "</font>");
                    }
                } else if (request.getParameter("act") != null && request.getParameter("act").equals("copy")) {
                    try {
                        if (request.getParameter("id") != null) {
                            id = request.getParameter("id");
                        }
                        String version = request.getParameter("version");
                        Template rTemplate = SWBContext.getWebSite(strTm).getTemplate(id);
                        User user = paramRequest.getUser();
                        //if(user==null) user = new WBUser(paramRequest.getTopic().getMap().getDbdata().getRepository());
                        //TemplateSrv tplSrv=new TemplateSrv();
                        //TODO: copyTemplate()
                        //Template recTpl=tplSrv.copyTemplate(rTemplate.getTopicMapId(),rTemplate.getId(),paramRequest.getLocaleString("msgCopyTpl")+" "+rTemplate.getTitle(),user.getId());
                        Template recTpl = SWBContext.getWebSite(strTm).createTemplate();
                        recTpl.setTitle(rTemplate.getTitle());
                        recTpl.setDescription(rTemplate.getDescription());
                        recTpl.setDeleted(rTemplate.isDeleted());
                        recTpl.setActive(rTemplate.isActive());
                        recTpl.setCreator(user);
                        recTpl.setGroup(rTemplate.getGroup());
                        
                        VersionInfo vil = rTemplate.getLastVersion();
                        VersionInfo nvi = recTpl.getWebSite().createVersionInfo();
                        VersionInfo vis = getVersionNum(rTemplate.getActualVersion(),Integer.parseInt(version));
                        nvi.setVersionFile(vis.getVersionFile());
                        vil.setNextVersion(nvi);
                        nvi.setPreviousVersion(vil);
                        nvi.setVersionNumber(vil.getVersionNumber()+1);
                        recTpl.setLastVersion(nvi);

                        //TODO: Revisar paths al hacer pruebas de funcionamiento
                        //Falta generar los paths origen y destino para copiar archivos del filesystem
                          
                        String ruta_origen="/"+SWBPlatform.getWorkPath()+"/"+recTpl.getWorkPath()+"/"+recTpl.getId()+"/"+vis.getVersionNumber();
                        String ruta_destino="/"+SWBPlatform.getWorkPath()+"/"+recTpl.getWorkPath()+"/"+recTpl.getId()+"/"+nvi.getVersionNumber();
                                
                        SWBUtils.IO.copyStructure(ruta_origen, ruta_destino);

                        out.println("<script>wbTree_remove();</script>");
                        out.println(paramRequest.getLocaleString("msgStatusTemplateReplicated"));
                        if (recTpl != null) {
                            out.println("<script>wbTree_executeAction('gotopath." + recTpl.getWebSiteId() + ".templates');wbTree_reload();wbTree_executeAction('gotopath." + recTpl.getWebSiteId() + ".templates." + recTpl.getGroup().getId() + "." + recTpl.getId() + "');</script>");
                        } else {
                            out.println("<font color=red>" + paramRequest.getLocaleString("msgErrorCopyTemplate") + ": " + id + "</font>");
                        }
                    } catch (Exception e) {
                        log.error(paramRequest.getLocaleString("msgStatusTemplateReplicated"));
                        out.println("<font color=red>" + paramRequest.getLocaleString("msgErrorCopyTemplate") + ": " + id + "</font>");
                    }
                } else {
                    if (request.getParameter("act") != null && (request.getParameter("act").equals("active") || request.getParameter("act").equals("unactive"))) {
                        boolean active = false;
                        String strActive = paramRequest.getLocaleString("msgUnActivated");
                        if (request.getParameter("id") != null) {
                            id = request.getParameter("id");
                        }
                        try {
                            if (request.getParameter("act").equals("active")) {
                                active = true;
                                strActive = paramRequest.getLocaleString("msgActivated");
                                out.println(paramRequest.getLocaleString("msgSActive"));
                            } else {
                                out.println(paramRequest.getLocaleString("msgSUnactive"));
                            }
                            Template rTemplate = SWBContext.getWebSite(strTm).getTemplate(id);
                            rTemplate.setActive(active);
                            User user = paramRequest.getUser();
                            rTemplate.setModifiedBy(user);
                            //if(user==null) user = new WBUser(paramRequest.getTopic().getMap().getDbdata().getRepository());
                            //rTemplate.update(user.getId(),paramRequest.getLocaleString("msgTemplateID")+": "+id+" "+paramRequest.getLocaleString("msgWas")+" "+strActive);
                            //rTemplate=null;
                            out.println("<script>wbTree_reload();</script>");
                        } catch (Exception e) {
                            log.error(paramRequest.getLocaleString("ErrorChangeActiveStatusTemplateID") + ": " + id);
                            out.println(paramRequest.getLocaleString("ErrorChangeActiveStatusTemplateID") + ": " + id);
                        }
                    } else {
                        Iterator<Template> enuTemp = SWBContext.getWebSite(strTm).listTemplates();
                        if (enuTemp.hasNext()) {
                            out.println("<table border=0  cellpadding=\"5\" cellspacing=\"0\" width=100%>");
                            out.println("<tr ><td class=\"tabla\">" + paramRequest.getLocaleString("msgHeaderID") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgHeaderTitle") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgHeaderLastUpdate") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgHeaderGroup") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgHeaderDelete") + "</td></tr>");
                        }
                        while (enuTemp.hasNext()) {
                            Template rTemp = enuTemp.next();
                            if (!rTemp.isDeleted()) {
                                SWBResourceURL url = paramRequest.getActionUrl();
                                url.setAction("remove");
                                url.setParameter("id", rTemp.getId());
                                SWBResourceURL urlEdit = paramRequest.getRenderUrl();
                                urlEdit.setMode(paramRequest.Mode_EDIT);
                                urlEdit.setParameter("act", "info");
                                urlEdit.setParameter("id", rTemp.getId());
                                out.println("<tr ><td><a href=\"" + urlEdit.toString() + "\" title=\"" + paramRequest.getLocaleString("msgTitleEditTemplate") + "\">" + rTemp.getId() + "</a></td><td>" + rTemp.getTitle() + "</td><td>" + rTemp.getUpdated().toString() + "</td><td>" + rTemp.getGroup().getId() + "</td><td><a href=\"" + url.toString() + "\" title=\"" + paramRequest.getLocaleString("msgTitleDeleteTemplate") + "\">" + paramRequest.getLocaleString("msgDelete") + "</a></td></tr>");
                            }
                        }
                        out.println("</table>");
                    }

                }
            }
        }

    }

    /** Edit view, show edit template view
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        String act = null;
        String id = request.getParameter("id");
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();
        if (debugVar) {
            System.out.println("entro a doEdit");
            System.out.println(paramRequest.getMode());
        }

        String accion = paramRequest.getAction();
        if (accion != null && accion.trim().equalsIgnoreCase("agregar")) {
            String idgrp = "0";
            String idTemp = "0";
            String tmsid = "";
            StringBuffer stcambios = new StringBuffer("");
            boolean nuevo = false;
            try {

                String filename = "";
                String content = "";
                String titulo = "";
                String descr = "";
                String nuevoarchivo = "1";
                
                if (request.getParameter("title")!=null&&!request.getParameter("title").trim().equals("")) {
                    titulo = request.getParameter("title");
                }
                if (request.getParameter("description")!=null&&!request.getParameter("description").trim().equals("")) {
                    descr = request.getParameter("description");
                }
                if (request.getParameter("group")!=null&&!request.getParameter("group").trim().equals("")) {
                    idgrp = request.getParameter("group");
                }
                if (request.getParameter("tmsid")!=null&&!request.getParameter("tmsid").trim().equals("")) {
                    tmsid = request.getParameter("tmsid");                
                }
                
                WebSite ws = SWBContext.getWebSite(tmsid);
                Template rTemp = ws.createTemplate();
                rTemp.setTitle(titulo);
                rTemp.setDescription(descr);
                TemplateGroup tpg = ws.getTemplateGroup(idgrp);
                rTemp.setGroup(tpg);
                rTemp.setCreator(user);

                idTemp = rTemp.getId();

                if (nuevoarchivo.equals("1")) {
                    nuevo = true;
                    filename = "template.html";
                    content = "<html>\n <head>\n  <title><TOPIC METHOD=\"getDisplayName\" LANGUAGE=\"{user@getLanguage}\"/></title>\n </head>\n <body>\n   <p style=\"margin-top: 0\">\n   <Content></Content>\n  </p>\n </body>\n</html>";
                }

                
                
                String workpath = ws.getWorkPath();
                String contenido = null;
                String version = request.getParameter("version");
                if (version == null) {
                    version = "1";
                }
                
                VersionInfo vi = ws.createVersionInfo();
                vi.setVersionNumber(Integer.parseInt(version));
                vi.setVersionFile(filename);
                
                rTemp.setActualVersion(vi);
                rTemp.setLastVersion(vi);
                
                try {

                    String RutaContenido = workpath + "/templates/" + idTemp + "/" + version + "/" + filename;

                    File images = new File(workpath + "/templates/" + idTemp);
                    if (!images.exists()) {
                        images.mkdir();
                    }
                    images = new File(workpath + "/templates/" + idTemp + "/" + version);
                    if (!images.exists()) {
                        images.mkdir();
                    }
                    images = new File(workpath + "/templates/" + idTemp + "/" + version + "/images");
                    if (!images.exists()) {
                        images.mkdir();
                    }
                    String fileName = filename;
                    if (fileName != null) {
                        if (fileName.lastIndexOf("\\") != -1) {
                            fileName = fileName.substring(fileName.lastIndexOf("\\", fileName.length()));
                        } else if (fileName.lastIndexOf("/") != -1) {
                            fileName = fileName.substring(fileName.lastIndexOf("/", fileName.length()));                        //contenido = WBUtils.getInstance().parseHTML(content,WBUtils.getInstance().getAppPath()+"/work/"+tmsid+"/templates/"+idTemp+"/"+version+"/images/");
                        //TODO: ParseHTML
                        }
                        //contenido = SWBUtils.TEXT.parseHTML(content, "images/");
                        File f = null;
                        f = new File(RutaContenido);
                        java.io.FileWriter outfile = new java.io.FileWriter(f);
                        outfile.write(contenido);
                        outfile.close();
                    }
                } catch (Exception e) {
                    log.error(paramRequest.getLocaleString("msgErrorHTMLParser"), e);
                }


            
            } catch (Exception e) {
                log.error(paramRequest.getLocaleString("msgErrorTemplateCreation") + " WBATemplates.doEdit.agregar", e);
            }

            if (nuevo) {
                WebPage tpurl = SWBContext.getAdminWebSite().getWebPage("WBAd_sysi_TemplatesEdit");
                out.println("<script>wbTree_executeAction('gotopath." + tmsid + ".templates');wbTree_reload();wbTree_executeAction('gotopath." + tmsid + ".templates." + idgrp + "." + idTemp + "');</script>");
                out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + tpurl.getUrl() + "?tm=" + tmsid + "&id=" + idTemp + "\">");
            } else {
                SWBResourceURL reloadURL = paramRequest.getRenderUrl();
                reloadURL.setMode(paramRequest.Mode_EDIT);
                reloadURL.setParameter("id", idTemp);
                reloadURL.setParameter("grpid", idgrp);
                reloadURL.setParameter("tmsid", tmsid);
                reloadURL.setParameter("tm", tmsid);
                reloadURL.setParameter("flag", "add");
                reloadURL.setParameter("tree", "reload");

                if (stcambios.toString().length() == 0) {
                    out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL=" + reloadURL.toString() + "\">");
                } else {
                    out.println("<script>wbTree_executeAction('gotopath." + tmsid + ".templates');wbTree_reload();wbTree_executeAction('gotopath." + tmsid + ".templates." + idgrp + "." + idTemp + "');</script>");
                    out.println("<div class=\"box\">");
                    out.println("<p align=center class=\"datos\">" + paramRequest.getLocaleString("msgTemplateUploadedSuccesfully") + " <a class=\"link\" href=\"" + reloadURL.toString() + "\">" + paramRequest.getLocaleString("msgFinish") + "</a></p>");
                    out.println("</div>");
                    out.println(stcambios.toString());

                }
            }
        } /////////////////////////////////////////////
        else {

            String onlyinfo = "";
            String strTm = request.getParameter("tm");
            if (strTm == null) {
                strTm = request.getParameter("tmsid");
            }
            if (request.getParameter("act") != null) {
                act = request.getParameter("act");
            } else {
                if (base.getProperty("view") != null) {
                    act = base.getProperty("view");
                }
                onlyinfo = "view";
            }
            if (request.getParameter("tree") != null && request.getParameter("tree").equals("reload")) {
                if (request.getParameter("flag") != null) {

                    if (request.getParameter("flag").equals("add")) {
                        out.println("<script>wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".templates');wbTree_reload();wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".templates." + request.getParameter("grpid") + "." + request.getParameter("id") + "');</script>");
                    }
                    if (request.getParameter("flag").equals("update")) {
                        Template rTemp = SWBContext.getWebSite(request.getParameter("tmsid")).getTemplate(request.getParameter("id"));
                        strTm = request.getParameter("tmsid");
                        out.println("<script>wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".templates');wbTree_reload();wbTree_executeAction('gotopath." + request.getParameter("tmsid") + ".templates." + rTemp.getGroup().getId() + "." + request.getParameter("id") + "');wbTree_reload();</script>");
                    }
                }
            }
            out.println("<script language=javascript>");
            out.println("  function valida(forma) {");
            out.println("      var tempo = forma.title.value;");
            out.println("      trim(forma.title);");
            out.println("      if(forma.title.value==''){");
            out.println("          alert('" + paramRequest.getLocaleString("msgAlertTemplateTitleMissed") + "');");
            out.println("          return (false);");
            out.println("      }");
            out.println("      forma.title.value=tempo;");
            out.println("      tempo=forma.description.value;");
            out.println("      trim(forma.description);");
            out.println("      if(forma.description.value==''){");
            out.println("          alert('" + paramRequest.getLocaleString("msgAlertTemplateDescMissed") + "');");
            out.println("          return (false);");
            out.println("      }");
            out.println("      forma.description.value=tempo;");
            out.println("          return (true);");
            out.println("}");
            out.println("");
            out.println("    function trim(field) {     ");
            out.println("         var retVal = '';     ");
            out.println("         var start = 0;     ");
            out.println("         while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {     ");
            out.println("              ++start;     ");
            out.println("         }     ");
            out.println("         var end = field.value.length;     ");
            out.println("         while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {     ");
            out.println("              --end;     ");
            out.println("         }     ");
            out.println("         retVal = field.value.substring(start, end);     ");
            out.println("         if (end == 0)     ");
            out.println("              field.value='';     ");
            out.println("         else     ");
            out.println("              field.value=retVal;          ");
            out.println("    }              ");
            out.println("");
            out.println("         function ventana(accion,sizze){    ");
            out.println("             ");
            out.println("              window.open(accion,\"tinyWindow\",sizze);    ");
            out.println("         }    ");
            out.println("");
            out.println("         function cambio(control,temporal){    ");
            out.println("             if(control.checked==true ) temporal.value=1; ");
            out.println("             else temporal.value=0;     ");
            out.println("         }    ");
            out.println("   function goto(forma,action){");
            out.println("       forma.action=action;");
            out.println("       forma.submit();");
            out.println("");
            out.println("");
            out.println("   }");
            out.println("</script>");

            if (act != null && act.equals("add")) {
                String tmsid = null;
                String sgrpid = request.getParameter("grpid");
                if (request.getParameter("tm") != null) {
                    tmsid = request.getParameter("tm");
                }
                SWBResourceURL agregarURL = paramRequest.getRenderUrl();
                agregarURL.setMode(paramRequest.Mode_EDIT);
                agregarURL.setAction("agregar");  // paramRequest.getActionUrl().setAction("add").toString()
                //out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\" enctype=\"multipart/form-data\" action=\"" + agregarURL.toString() + "\" method=\"post\" class=\"box\">");
                out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\" action=\"" + agregarURL.toString() + "\" method=\"post\" class=\"box\">");

                out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                out.println("<tr ><td colspan=2 class=\"tabla\">" + paramRequest.getLocaleString("msgAddTemplate") + "</td></tr>");
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + ":</td><td class=\"valores\"><input type=text class=\"campos\" value=\"\" name=title></td></tr>");
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + ":</td><td class=\"valores\"><input type=text class=\"campos\" name=description value=\"\"></td></tr>");
                out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgGroup") + ":</td><td class=\"valores\"><select class=\"campos\" name=group>");
                Iterator<TemplateGroup> enu = SWBContext.getWebSite(tmsid).listTemplateGroups();
                String selecciona = "";
                String grpid = "1";
                if (request.getParameter("grpid") != null) {
                    grpid = request.getParameter("grpid");
                }
                while (enu.hasNext()) {
                    TemplateGroup rTmp = enu.next();
                    String thisTM = "";

                    WebSite tmgr = SWBContext.getWebSite(rTmp.getWebSite().getId());
                    thisTM = tmgr.getId();


                    if (tmsid.equals(thisTM)) {
                        selecciona = "";
                        if (grpid.equals(rTmp.getId())) {
                            selecciona = " selected ";
                        }
                        out.println("<option value=\"" + rTmp.getId() + "\" " + selecciona + ">" + rTmp.getTitle() + "</option>");
                    }
                }
                out.println("</select></td></tr>");
                out.println("<tr ><td colspan=2 class=\"valores\" align=right><HR size=\"1\" noshade><input type=submit class=\"boton\" value=\"" + paramRequest.getLocaleString("msgButtonAdd") + "\" ><input type=hidden name=tmsid value=\"" + tmsid + "\"></td></tr>");
                out.println("</TABLE></P>");
                out.println("<input type=hidden name=\"newFile\" value=\"1\" >");
                out.println("</form>");
            }

            if ((act != null && (act.equals("info") || act.equals("edit"))) || (act == null && id != null)) {
                if (request.getParameter("id") != null) {
                    if (onlyinfo.equals("")) {
                        id = request.getParameter("id");
                        SWBResourceURL urlSection = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                        urlSection.setParameter("act", "section");
                        urlSection.setParameter("id", id);
                        SWBResourceURL urlEditor = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                        urlEditor.setParameter("act", "edit");
                        urlEditor.setParameter("id", id);
                        out.println("<p align=center class=\"box\">");
                        out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                        out.println("<TR>");
                        out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> " + paramRequest.getLocaleString("msgIdentifier") + "</TD>");
                        out.println("<TD class=\"valores\">");
                        out.println(id);
                        out.println("</TD>");
                        out.println("</TR>");
                        out.println("</TABLE></P>");
                        Template rTemp = SWBContext.getWebSite(strTm).getTemplate(id);
                        out.println("<form name=\"forma\" onSubmit=\"return (valida(forma));\"  action=\"" + paramRequest.getActionUrl().setAction("update").toString() + "\" method=\"post\" class=\"box\">");
                        out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"10\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + ":</td><td align=\"left\" class=\"valores\"><input type=text class=\"campos\" value=\"" + rTemp.getTitle() + "\" name=title></td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + ":</td><td align=\"left\" class=\"valores\"><input type=text class=\"campos\" name=description value=\"" + rTemp.getDescription() + "\"></td></tr>");
                        //TODO: dateFormat()
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgCreationDate") + ":</td><td align=\"left\" class=\"valores\">" + rTemp.getCreated().toString() + "</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgLastModification") + ":</td><td align=\"left\" class=\"valores\">" + rTemp.getUpdated().toString() + "</td></tr>");
                        String strYes = "";
                        String strNo = "";
                        if (rTemp.isActive()) {
                            strYes = "checked";
                        } else {
                            strNo = "checked";
                        }
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActive") + ":</td><td align=\"left\" class=\"valores\">" + paramRequest.getLocaleString("msgYes") + "&nbsp;<input type=radio name=active value=\"1\"  " + strYes + ">&nbsp;&nbsp;" + paramRequest.getLocaleString("msgNo") + "&nbsp;<input type=radio name=active value=\"0\"  " + strNo + "></td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgGroup") + ":</td><td align=\"left\" class=\"valores\"><select class=\"campos\" name=group>");
                        Iterator<TemplateGroup> enu = SWBContext.getWebSite(strTm).listTemplateGroups();
                        TemplateGroup rgt = SWBContext.getWebSite(strTm).getTemplateGroup(rTemp.getGroup().getId());

                        String GrpTMID = "";
                        GrpTMID = rgt.getWebSite().getId();

                        String strSelect = "";
                        while (enu.hasNext()) {
                            TemplateGroup rTmp = enu.next();
                            String thisTM = "";//"global";
                            WebSite tmgr = rTmp.getWebSite();
                            thisTM = tmgr.getId();

                            if (GrpTMID.equals(thisTM)) {
                                strSelect = "";
                                if (rTmp.getId().equals(rTemp.getGroup().getId())) {
                                    strSelect = "selected";
                                }
                                out.println("<option value=\"" + rTmp.getId() + "\"  " + strSelect + " >" + rTmp.getTitle() + "</option>");
                            }
                        }
                        out.println("</select></td></tr>");
                        String strFileName = SWBContext.getWebSite(strTm).getTemplate(id).getActualVersion().getVersionFile(); //TemplateMgr.getInstance().getTemplate(strTm,Integer.parseInt(id)).getFileName(rTemp.getActualversion());
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActualFile") + " (html, htm)" + ":</td><td align=\"left\" class=\"valores\">" + strFileName + "<input type=hidden  name=tmpFile value=\"" + strFileName + "\" ><input type=hidden name=seleccionado value=\"0\"></td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActualVersion") + ":</td><td align=\"left\" class=\"valores\"><select class=\"campos\" name=\"aversion\">");
                        String strSelected = null;
                        for (int y = 1; y <= rTemp.getLastVersion().getVersionNumber(); y++) {
                            strSelected = "";
                            if (y == rTemp.getActualVersion().getVersionNumber()) {
                                strSelected = "selected";
                            }
                            out.println("<option value=\"" + y + "\" " + strSelected + ">" + y + "</option>");
                        }
                        out.println("</select></td></tr>");

                        SWBResourceURL urlReset = paramRequest.getActionUrl();
                        urlReset.setAction("reset");
                        urlReset.setParameter("id", rTemp.getId());
                        out.println("<tr ><td width=100% colspan=2 align=\"right\" class=\"datos\"><HR size=\"1\" noshade><input type=button class=boton value=\"" + paramRequest.getLocaleString("msgButtonResetTemplateVer") + "\" title=\"" + paramRequest.getLocaleString("msgTitleResetVer") + "\" onclick=\"javascript:goto(forma,'" + urlReset.toString() + "');\">&nbsp;<input type=submit class=\"boton\" value=\"" + paramRequest.getLocaleString("msgButtonUpdate") + "\" ><input type=hidden name=tmsid value=\"" + GrpTMID + "\"></td></tr>");
                        out.println("</table>");
                        out.println("<input type=hidden name=tmid value=\"" + strTm + "\"><input type=hidden name=\"id\" value=\"" + id + "\">");
                        out.println("</form>");
                    } else {
                        id = request.getParameter("id");
                        SWBResourceURL urlSection = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                        urlSection.setParameter("act", "section");
                        urlSection.setParameter("id", id);
                        SWBResourceURL urlEditor = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
                        urlEditor.setParameter("act", "edit");
                        urlEditor.setParameter("id", id);
                        out.println("<p align=center class=\"box\">");
                        out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                        out.println("<TR>");
                        out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> " + paramRequest.getLocaleString("msgIdentifier") + "</TD>");
                        out.println("<TD class=\"valores\">");
                        out.println(id);
                        out.println("</TD>");
                        out.println("</TR>");
                        out.println("</TABLE></P>");
                        Template rTemp = SWBContext.getWebSite(strTm).getTemplate(id);
                        out.println("<p align=center class=\"box\">");
                        out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                        out.println("<tr ><td colspan=2><input type=hidden name=\"id\" value=\"" + id + "\"></td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgTitle") + ":</td><td class=\"valores\">" + rTemp.getTitle() + "</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgDescription") + ":</td><td class=\"valores\">" + rTemp.getDescription() + "</td></tr>");
                        //TODO: dateFormat
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgCreationDate") + ":</td><td class=\"valores\">" + rTemp.getActualVersion().getCreated().toString() + "</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgLastModification") + ":</td><td class=\"valores\">" + rTemp.getActualVersion().getUpdated().toString() + "</td></tr>");
                        String strYes = paramRequest.getLocaleString("msgNo"); ////////////////////////////
                        if (rTemp.isActive()) {
                            strYes = paramRequest.getLocaleString("msgYes");  ////////////////////////////
                        }
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActive") + ":</td><td class=\"valores\">" + strYes + "</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgGroup") + ":</td><td class=\"valores\">");
                        Iterator<TemplateGroup> enu = SWBContext.getWebSite(strTm).listTemplateGroups();
                        while (enu.hasNext()) {
                            TemplateGroup rTmp = enu.next();
                            if (rTmp.getId().equals(rTemp.getGroup().getId())) {
                                out.println(rTmp.getTitle());
                            }
                        }
                        out.println("</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActualFile") + " (html, htm)" + ":</td><td class=\"valores\">" + rTemp.getActualVersion().getVersionFile() + "</td></tr>");
                        out.println("<tr ><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgActualVersion") + ":</td><td class=\"valores\">" + rTemp.getActualVersion().getVersionNumber() + "</td></tr>");
                        out.println("</TABLE></P>");

                    }
                }
            }

            if (act != null && act.equals("section")) {
                if (request.getParameter("id") != null) {
                    id = request.getParameter("id");
                    //TemplateSrv tserv = new TemplateSrv();
                    String tmid = request.getParameter("tm");
                    // TODO: revisar que trae este método
                    //HashMap hmtps = tserv.getTopicsOfTemplate(tmid,Integer.parseInt(id));
                    Template tpl = SWBContext.getWebSite(tmid).getTemplate(id);
                    Iterator<GenericObject> itt = tpl.listRelatedObjects();//hmtps.keySet().iterator();

                    out.println("<p align=center class=\"box\">");
                    out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr ><td class=\"tabla\">" + paramRequest.getLocaleString("msgAction") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgSection") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgSite") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgIdentifier") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgCreationDate") + "</td><td class=\"tabla\">" + paramRequest.getLocaleString("msgLastModification") + "</td><td class=\"tabla\" align=center>" + paramRequest.getLocaleString("msgStatus") + "<br>" + paramRequest.getLocaleString("msgSection") + " / " + paramRequest.getLocaleString("msgTemplate") + "</td></tr>");
                    String rowColor = "";
                    boolean cambiaColor = true;
                    while (itt.hasNext()) {
                        Object obj = itt.next();
                        if (obj instanceof WebPage) {
                            WebPage tp = (WebPage) obj;
                            //String tp_active = (String) hmtps.get(tp);
                            boolean tp_active = tp.isActive();
                            WebSite tm = tp.getWebSite();
                            //System.out.println("template activo: "+tp_active);
                            String strActive = "<font color=\"green\">" + paramRequest.getLocaleString("msgOnLine") + "</font>";
                            if (!tp.isActive()) {
                                strActive = "<font color=\"red\">" + paramRequest.getLocaleString("msgOffLine") + "</font>";
                            }
                            String strActiveTpl = "<font color=\"green\">" + paramRequest.getLocaleString("msgOnLine") + "</font>";
                            if (!tp_active) {
                                strActiveTpl = "<font color=\"red\">" + paramRequest.getLocaleString("msgOffLine") + "</font>";
                            }
                            rowColor = "#EFEDEC";
                            if (!cambiaColor) {
                                rowColor = "#FFFFFF";
                            }
                            cambiaColor = !(cambiaColor);

                            SWBResourceURL url = paramRequest.getActionUrl();
                            url.setAction("removeTopicTpl");
                            url.setParameter("id", id);
                            url.setParameter("tmid", tmid);
                            url.setParameter("tpid", tp.getId());

                            out.println("<tr bgcolor=\"" + rowColor + "\">");
                            out.println("<td  class=\"valores\"><a href=\"" + url.toString() + "\" onclick=\"javascript:if(confirm('" + paramRequest.getLocaleString("msgAlertRemove") + "?'))return true; else return false;\"><img border=0 src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/eliminar.gif\" alt=\"" + paramRequest.getLocaleString("msgDelete") + "\"></a></td>");
                            out.println("<td  class=\"valores\">" + tp.getTitle(paramRequest.getUser().getLanguage()) + "</td>");
                            out.println("<td class=\"valores\">" + tm.getTitle() + "</td>");

                            WebPage adm = SWBContext.getAdminWebSite().getWebPage("WBAd_sysi_TopicsInfo");  //WBAd_sysi_TopicsInfo

                            out.println("<td class=\"valores\"><a href=\"" + adm.getUrl(tp) + "?WBAGotoTree=true\">" + tp.getId() + "</a></td>");
                            //TODO: dateformat()
                            out.println("<td class=\"valores\">" + tp.getCreated().toString() + "</td>");
                            out.println("<td class=\"valores\">" + tp.getUpdated().toString() + "</td>");
                            out.println("<td class=\"valores\" align=center>" + strActive + " / " + strActiveTpl + "</td>");
                            out.println("</tr>");
                        }
                    }
                    out.println("</TABLE></P>");
                }
            }
            if (act != null && act.equals("version")) {
                if (request.getParameter("id") != null) {
                    id = request.getParameter("id");
                    //TemplateSrv tserv = new TemplateSrv();
                    out.println("<p align=left class=\"box\">");
                    out.println("<TABLE border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
                    out.println("<tr><td class=\"tabla\" width=100 align=center>" + paramRequest.getLocaleString("msgVersions") + "</td><td class=\"tabla\" colspan=3>" + paramRequest.getLocaleString("msgPreview") + "</td><td></td></tr>");
                    Template tmp = SWBContext.getWebSite(strTm).getTemplate(id);
                    String appPath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + SWBPlatform.getWebWorkPath() + "/sites/" + strTm + "/templates/" + id + "/";
                    Template tmpT = SWBContext.getWebSite(strTm).getTemplate(id);
                    String rowColor = "";
                    boolean cambiaColor = true;
                    
                    VersionInfo vi = tmp.getActualVersion();
                    VersionInfo v1;
                    if(vi.getVersionNumber()==1)
                    {
                        v1=vi;
                    }
                    else
                    {
                        v1 = getVersionInicial(vi);
                    }

                    for (int i = 1; i <= tmp.getLastVersion().getVersionNumber(); i++) {
                        
                        if(v1.getVersionNumber()==i)
                        {
                            SWBResourceURL urlWin = paramRequest.getRenderUrl();
                            urlWin.setMode(paramRequest.Mode_VIEW);
                            urlWin.setParameter("id", id);
                            urlWin.setParameter("version", Integer.toString(i));
                            urlWin.setParameter("act", "preview");
                            urlWin.setCallMethod(paramRequest.Call_DIRECT);
                            WebPage tpurl = SWBContext.getAdminWebSite().getWebPage("WBAd_sysi_TemplatesEdit");
                            rowColor = "#EFEDEC";
                            if (!cambiaColor) {
                                rowColor = "#FFFFFF";
                            }
                            cambiaColor = !(cambiaColor);
                            out.println("<tr bgcolor=\"" + rowColor + "\"><td  class=\"valores\" align=center>");
                            out.println("  <a href=\"" + tpurl.getUrl() + "?id=" + id + "&version=" + i + "&tm=" + strTm + "&act=editor\" class=\"link\">" + i + "</a>");
                            out.println("</td><td class=\"valores\">");
                            out.println("  <a href=\"javascript:ventana('" + appPath + i + "/" + v1.getVersionFile() + "','width=550, height=550, scrollbars, resizable');\" class=\"link\" title=\"" + paramRequest.getLocaleString("msgLinkPreview") + "\">");
                            out.println("    <img src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/b_editar2.gif\" border=0>");
                            out.println("  </a>");
                            out.println("</td><td class=\"valores\">");
                            out.println("  <a href=\"javascript:ventana('" + appPath + i + "/" + v1.getVersionFile() + "','width=550, height=550, scrollbars, resizable');\" class=\"link\" title=\"" + paramRequest.getLocaleString("msgLinkPreview") + "\">");
                            out.println("    <img src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/copiar.gif\" border=0>");
                            out.println("  </a>");
                            out.println("</td><td class=\"valores\">");
                            out.println("  <a href=\"javascript:ventana('" + appPath + i + "/" + v1.getVersionFile() + "','width=550, height=550, scrollbars, resizable');\" class=\"link\" title=\"" + paramRequest.getLocaleString("msgLinkPreview") + "\">");
                            out.println("    <img src=\"" + SWBPlatform.getContextPath() + "wbadmin/images/preview.gif\" border=0>");
                            out.println("  </a>");
                            out.println("</td><td class=\"valores\">");
                            out.println("</td></tr>");
                        }
                        if(v1.getNextVersion()!=null) v1 = v1.getNextVersion();
                    }
                    out.println("</table>");
                    out.println("</P>");
                }
            }
            SWBResourceURL urlConfig = paramRequest.getRenderUrl();
            urlConfig.setMode(paramRequest.Mode_ADMIN);
            urlConfig.setAction("config");
            if (id != null) {
                urlConfig.setParameter("id", id);
            }
            if (request.getParameter("tm") != null) {
                urlConfig.setParameter("tm", request.getParameter("tm"));
            //out.println("<a href=\""+urlConfig.toString()+"\" style=\"text-decoration:none;\"  class=\"link\">"+"config view"+"</a>");
            }
        }

    }

    /** Administrator view of the WBATemplates resource
     * @param request input parameters
     * @param response an answer to the request
     * @param paramRequest list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        if (debugVar) {
            System.out.println("entro a doAdmin");
        //String accion = paramRequest.getAction();
        }
        String id = request.getParameter("id");
        //String version = request.getParameter("version");
        String strTm = request.getParameter("tm");

        if (strTm == null) {
            strTm = paramRequest.getTopic().getWebSiteId();
        }
        //TODO: ver si va a existir algo similar en la nueva version de WebBuilder 
        //WBResourceUtils.getInstance().loadHtmlStyles()
        //out.println(WBResourceUtils.getInstance().loadHtmlStyles());
        out.println("<script language=javascript>");
        out.println("      function valida(){");
        out.println("          var tmpTitle = document.myForm.titulo.value;     ");
        out.println("          trim(document.myForm.titulo);     ");
        out.println("          if(document.myForm.titulo.value!=''){");
        out.println("              return(true);");
        out.println("          }");
        out.println("          else{");
        out.println("              alert('" + paramRequest.getLocaleString("msgAlertTitleRequired") + "');");
        out.println("              document.myForm.titulo.value=tmpTitle;");
        out.println("              document.myForm.titulo.focus();");
        out.println("              document.myForm.titulo.select();");
        out.println("              return(false);");
        out.println("          }");
        out.println("      }");
        out.println("    function trim(field) {     ");
        out.println("         var retVal = '';     ");
        out.println("         var start = 0;     ");
        out.println("         while ((start < field.value.length) && (field.value.charAt(start) == ' ')) {     ");
        out.println("              ++start;     ");
        out.println("         }     ");
        out.println("         var end = field.value.length;     ");
        out.println("         while ((end > 0) && (field.value.charAt(end - 1) == ' ')) {     ");
        out.println("              --end;     ");
        out.println("         }     ");
        out.println("         retVal = field.value.substring(start, end);     ");
        out.println("         if (end == 0)     ");
        out.println("              field.value='';     ");
        out.println("         else     ");
        out.println("              field.value=retVal;          ");
        out.println("    }              ");
        out.println("        ");
        out.println("</script>");
        String strTemp = "";
        if (id != null) {
            out.println("<p align=center class=\"box\">");
            out.println("<TABLE width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
            out.println("<TR>");
            out.println("<TD width=\"150\" align=\"right\" class=\"datos\"> " + paramRequest.getLocaleString("msgIdentifier") + "</TD>");
            out.println("<TD class=\"valores\">");
            out.println(id);
            out.println("</TD>");
            out.println("</TR>");
            out.println("</TABLE>");
            out.println("</P>");
        }
        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setAction("uptAttr");
        out.println("<form name=thisForm method=\"post\" action=\"" + urlAction.toString() + "\" class=\"box\">");

        out.println("<table width=\"100%\"  border=\"0\" cellpadding=\"5\" cellspacing=\"0\" bgcolor=\"#FFFFFF\">");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        out.println("<tr><td width=\"150\" align=\"right\" class=\"datos\">" + paramRequest.getLocaleString("msgViewType") + "</td>");
        out.println("<td class=\"valores\"><select name=\"act\" class=\"campos\">");

        String vista = "info";
        if (base.getProperty("view") != null) {
            vista = base.getProperty("view");
        }
        String selected = "";
        if (vista.equals("info")) {
            selected = " selected ";
        }
        out.println("<option value=\"info\" " + selected + ">" + paramRequest.getLocaleString("msgInformation") + "</option>");
        selected = "";
        if (vista.equals("version")) {
            selected = " selected ";
        }
        out.println("<option value=\"version\" " + selected + ">" + paramRequest.getLocaleString("msgVersion") + "</option>");
        selected = "";
        if (vista.equals("section")) {
            selected = " selected ";
        }
        out.println("<option value=\"section\" " + selected + ">" + paramRequest.getLocaleString("msgSection") + "</option>");
        out.println("</select><input type=\"hidden\" name=\"tm\" value=\"" + strTm + "\">");
        if (id != null) {
            out.println("<input type=\"hidden\" name=\"id\" value=\"" + id + "\">");
        }
        out.println("</td></tr>");
        out.println("<tr><td colspan=2 align=right><HR size=\"1\" noshade><INPUT type=\"submit\"  class=\"boton\" value=\"" + paramRequest.getLocaleString("msgButtonSend") + "\" id=btnEnviar name=btnEnviar >   ");
        //out.println("<tr><td colspan=2 align=left>");
        SWBResourceURL urlRet = paramRequest.getRenderUrl();
        urlRet.setMode(paramRequest.Mode_EDIT);
        urlRet.setCallMethod(paramRequest.Call_CONTENT);
        if (id != null) {
            urlRet.setParameter("id", id);
        }
        urlRet.setParameter("act", "version");
        if (strTm != null) {
            urlRet.setParameter("tm", strTm);
        //out.println("<a href=\""+urlRet.toString()+"\" style=\"text-decoration: none; font-family:verdana; font-size:10px;\">"+"Return"+"</a>");
        //out.println("</td></tr>");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        }
        out.println("</table>");
        out.println("</form>");

    }

    /** Add, update, remove topic map templates
     * @param request input parameters
     * @param response list of objects (user, topic, action, ...)
     * @throws AFException an AF Exception
     * @throws IOException an IO Exception
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {

        Resource base = getResourceBase();
        String accion = response.getAction();
        String id = request.getParameter("id");
        User user = response.getUser();
        String strTm = request.getParameter("tmsid");
        String tmid = request.getParameter("tmid");
        String tpid = request.getParameter("tpid");
        if (debugVar) {
            System.out.println("entro a processAction");
        }
        try {

            //Actualiza la información del template

            if (accion.equals("update")) {
                Template rTemplate = SWBContext.getWebSite(request.getParameter("tmsid")).getTemplate(id);
                rTemplate.setTitle(request.getParameter("title"));
                rTemplate.setDescription(request.getParameter("description"));
                TemplateGroup tgrp = rTemplate.getWebSite().getTemplateGroup(request.getParameter("group"));
                rTemplate.setGroup(tgrp);
                boolean bact = request.getParameter("active") != null&&request.getParameter("active").equals("1")?true:false;
                rTemplate.setActive(bact);
                int tplversion = Integer.parseInt(request.getParameter("aversion"));
                VersionInfo aversion = getVersionNum(rTemplate.getActualVersion(),tplversion);
                rTemplate.setActualVersion(aversion);
                rTemplate.setModifiedBy(user);
                //rTemplate.update(response.getUser().getId(), response.getLocaleString("msgLogTemplateUpdatedID") + ": " + id);
                rTemplate = null;
                response.setRenderParameter("act", "info");

                response.setMode(response.Mode_EDIT);
                response.setRenderParameter("id", id);
                response.setRenderParameter("tmsid", request.getParameter("tmsid"));
                if (request.getParameter("tmsid") != null) {
                    response.setRenderParameter("tm", request.getParameter("tmsid"));
                } else if (request.getParameter("tm") != null) {
                    response.setRenderParameter("tm", request.getParameter("tm"));
                }
                response.setRenderParameter("flag", "update");
                response.setRenderParameter("tree", "reload");
            }

            
            // TODO: Reinicio de versiones
            // para elliminar versiones anteriores; o bien como quedaría
            // REINICIA LAS VERSIONES DEL TEMPLATE

            if (accion.equals("reset")) {
                
                WebSite ws = SWBContext.getWebSite(tmid);
                Template tpl = ws.getTemplate(id);

                //tsrv.resetVersions(tmid, Integer.parseInt(id), response.getUser().getId());
                //boolean resettpl = TemplateSrv.resetTemplates(ws, tpl, response.getUser()); //Temporal
                if (request.getParameter("act") != null) {
                    response.setRenderParameter("act", "info");
                }
                response.setRenderParameter("id", id);
                if (tmid != null) {
                    response.setRenderParameter("tm", tmid);
                }
                response.setMode(response.Mode_EDIT);
            }

            // ACTUALIZA LA VISTA DEL RECURSO

            if (accion.equals("uptAttr")) {
                if (request.getParameter("act") != null) {
                    base.setProperty("view", request.getParameter("act"));
                    //base.updateAttributesToDB(user.getId(), response.getLocaleString("msgLogTemplateViewAttributeUpdated"));
                }
                response.setMode(response.Mode_ADMIN);
                if (request.getParameter("act") != null) {
                    response.setRenderParameter("act", request.getParameter("act"));
                }
                if (id != null) {
                    response.setRenderParameter("id", id);
                }
                if (strTm != null) {
                    response.setRenderParameter("tm", strTm);
                }
            }

            // quita relación de la plantilla con el tópico
            // id: template id
            if (tmid != null && tpid != null && id != null) {
                //TopicSrv topicsrv = new TopicSrv();
                WebSite tm = SWBContext.getWebSite(tmid);
                WebPage tp = tm.getWebPage(tpid);
                if (accion.equals("removeTopicTpl")) { //otra cosa que no sea cambiar el estatus del template
                    try {
                        
                        tp.removeTemplateRef(tm.getTemplateRef(id));
                        tp.setModifiedBy(user);
                        //topicsrv.removeTemplateFromTopic(id, tp, user.getId());
                    } catch (Exception e) {
                        log.error("Error while unassign template width id:" + id + ", to topic:" + tp.getId(),e);
                    }
                }
                if (id != null) {
                    response.setRenderParameter("id", id);
                }
                if (tmid != null) {
                    response.setRenderParameter("tm", tmid);
                }
                if (request.getParameter("act") != null) {
                    response.setRenderParameter("act", request.getParameter("act"));
                }
                response.setMode(response.Mode_EDIT);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
    
    private VersionInfo getVersionInicial(VersionInfo vi) // Se recibe la version actual del template.
    {
        VersionInfo viret=null;
        if(vi.getVersionNumber()==1)
            viret = vi;
        else
            if(vi.getVersionNumber()>1&&vi.getPreviousVersion()!=null)
                viret = getVersionInicial(vi.getPreviousVersion());
        return viret;       
    }
    
    private VersionInfo getVersionNum(VersionInfo vi, int num) // Se recibe la version actual del template.
    {
        VersionInfo viret=getVersionInicial(vi);
        return findVersionNum(viret,num);      
    }
    
    private VersionInfo findVersionNum(VersionInfo vi, int num)
    {
        VersionInfo viret=null;
        if(vi.getVersionNumber()==num)
            viret = vi;
        else
            if(vi.getVersionNumber()>1&&vi.getNextVersion()!=null)
                viret = findVersionNum(vi.getNextVersion(),num);
        return viret; 
    }
}
