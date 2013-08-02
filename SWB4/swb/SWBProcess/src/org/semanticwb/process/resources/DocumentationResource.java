/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.Documentation;
import org.semanticwb.process.model.ProcessElement;

/**
 *
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class DocumentationResource extends GenericAdmResource {

    public static String MOD_UPDATETEXT = "updateText";
    public static String PARAM_TEXT = "txt";
    private static Logger log = SWBUtils.getLogger(DocumentationResource.class);

    void doUpdate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        String idDocumentation = request.getParameter("idDocumentation");
        if (suri != null) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                String txt = request.getParameter(PARAM_TEXT);
                request.setAttribute("suri", suri);
                //TODO: Actualizar el texto del richText
                Documentation doc = Documentation.ClassMgr.getDocumentation(idDocumentation, paramRequest.getWebPage().getWebSite());
                //Guardar el texto de la documentación
                if (doc != null) {
                    doc.setText(txt);
                    doc.setTextFormat("text/html");
                }
            }
        }
        doView(request, response, paramRequest);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        PrintWriter out = response.getWriter();
        if (suri != null) {
            ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                //TODO: Desplegar editor
                SWBResourceURL url = paramRequest.getRenderUrl();
                url.setMode("documentation");
                url.setCallMethod(SWBResourceURL.Call_DIRECT);
                url.setParameter("suri", request.getParameter("suri"));
                out.println("<iframe dojoType_=\"dijit.layout.ContentPane\" src=\"" + url + "\" style=\"width:100%; height:100%;\" frameborder=\"0\" scrolling=\"yes\"></iframe>");
            }
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if ("documentation".equals(mode)) {
            doProcessDocumentation(request, response, paramRequest);
        } else if (MOD_UPDATETEXT.equals(mode)) {
            doUpdate(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doProcessDocumentation(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/process/documentation/DocumentationResource.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        String suri = request.getParameter("suri");
        response.setContentType("text/html; charset=UTF-8");
        try {
            request.setAttribute("paramRequest", paramRequest);
            request.setAttribute("suri", request.getParameter("suri"));
            if (suri != null) {
                ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
                Documentation doc = null;
                if (pe.listDocumentations().hasNext()) {
                    doc = pe.getDocumentation();
                } else {
                    //Si no existe documentación, crearla
                    doc = Documentation.ClassMgr.createDocumentation(paramRequest.getWebPage().getWebSite());
                    //Agregar la documentación al elemento
                    pe.addDocumentation(doc);
                }
                request.setAttribute("idDocumentation", doc.getId());
            }
            rd.include(request, response);
        } catch (Exception ex) {
            log.error("Error on doView - doProcessDocumentattion.jsp, " + ex.getMessage());
        }
    }
}
