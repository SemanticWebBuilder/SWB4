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
package org.semanticwb.portal.resources;

import java.io.FileInputStream;
import java.io.InputStream;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Administra la descarga de contenidos, en forma de archivos de file system,
 * incrementando un contador de las descargas realizadas del archivo asociado.
 * @author Jorge Alberto Jiménez
 */
public class FileDownload extends GenericAdmResource {


    javax.xml.transform.Templates tpl;

    private static Logger log = SWBUtils.getLogger(FileDownload.class);

    
    @Override
    public void setResourceBase(Resource base) {
        try {
            super.setResourceBase(base);
        } catch (Exception e) {
            log.error("Error while setting resource base: " + base.getId() + "-" + base.getTitle(), e);
        }
        if (!"".equals(base.getAttribute("template", "").trim())) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
            } catch (Exception e) {
                log.error("Error while loading resource template: " + base.getId(), e);
            }
        }
        //System.out.println("template: " + tpl);
        if (tpl == null) {
            try {
                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getAdminFileStream("/swbadmin/xsl/FileDownload/WBFileDownload.xslt"));
                //System.out.println("template por defecto: " + tpl);
            }  catch (Exception e) {
                log.error("Error while loading default resource template: " + base.getId(), e);
            }
        }
    }

    //@Override
    public void doView(javax.servlet.http.HttpServletRequest request,
                       javax.servlet.http.HttpServletResponse response,
                       SWBParamRequest reqParams) throws SWBResourceException, java.io.IOException {

        Resource base = this.getResourceBase();
        try {
            String fileName = base.getAttribute("fileName", "").trim();
            if (reqParams.getAction() == null || !reqParams.getAction().equals("download")) {
                SWBResourceURL wburl = reqParams.getRenderUrl();
                wburl.setCallMethod(wburl.Call_DIRECT);
                wburl.setAction("download");

                String text = base.getAttribute("text", "");
                String architecture = base.getAttribute("architecture", "");
                String historyUrl = base.getAttribute("historyUrl", "");
                fileName = SWBPortal.getWorkPath() + base.getWorkPath() + "/" + fileName;
                java.io.File file=new java.io.File(fileName);
                if (file != null) {
                    int pos = fileName.lastIndexOf("/");
                    if (pos >- 1) {
                        fileName = fileName.substring(pos + 1);
                    }
                    String ext = "";
                    pos = -1;
                    pos = fileName.indexOf(".");
                    if (pos > -1) {
                        ext = fileName.substring(pos);
                    }
                    Document  dom = SWBUtils.XML.getNewDocument();
                    Element el = dom.createElement("FileDownload");
                    el.setAttribute("text", text);
                    el.setAttribute("name", fileName);
                    el.setAttribute("path", wburl.toString() + "/doc/" + base.getAttribute("fileName", "").trim());
                    el.setAttribute("length", "" + file.length());
                    el.setAttribute("nHits", "" + base.getHits());
                    el.setAttribute("url", historyUrl);
                    el.setAttribute("tArchitecture", architecture);
                    el.setAttribute("textension", ext);
                    dom.appendChild(el);
                    //System.out.println("El DOM a utilizar: " + SWBUtils.XML.domToXml(dom));
                    /** Por ahora **/
                    if (tpl == null) {

                        if (!"".equals(base.getAttribute("template", "").trim())) {
                            try {
                                tpl = SWBUtils.XML.loadTemplateXSLT(SWBPortal.getFileFromWorkPath(base.getWorkPath() + "/" + base.getAttribute("template").trim()));
                            } catch (Exception e) {
                                log.error("Error while loading resource template: " + base.getId(), e);
                            }
                        } else {
                            //System.out.println("template: " + tpl);
                            try {
                                InputStream input = SWBPortal.getAdminFileStream("/swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                if (input != null) {
                                    tpl = SWBUtils.XML.loadTemplateXSLT(input);
                                //} else {
                                //    System.out.println("\nInputStream NULO!, no encuentra: /swbadmin/xsl/FileDownload/WBFileDownload.xslt");
                                }
                            }  catch (Exception e) {
                                log.error("Error while loading default resource template: " + base.getId(), e);
                            }
                        }
                    }

                    response.getWriter().print(SWBUtils.XML.transformDom(tpl, dom));
                }
            } else {
                int pos = request.getRequestURI().indexOf("/doc/");
                if (pos > -1) {
                    fileName = request.getRequestURI().substring(pos + 5);
                }
                fileName = SWBPortal.getWorkPath() + base.getWorkPath() + "/" + fileName;
                java.io.File file = new java.io.File(fileName);
                response.setHeader("Content-Length", file.length() + "");
                response.setContentType("application/binary");
                base.addHit(request, reqParams.getUser(), reqParams.getWebPage());
                SWBUtils.IO.copyStream(new FileInputStream(fileName), response.getOutputStream());
            }
        } catch (Exception e) {
            log.error("At responding file download request", e);
        }
    }

}
