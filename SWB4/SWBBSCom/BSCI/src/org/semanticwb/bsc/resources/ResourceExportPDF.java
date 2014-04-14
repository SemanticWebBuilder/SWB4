/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.PDFExportable;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResource;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Recurso que consiste en la redirecci&oacute;n de la exprtaci&oacute;n de un 
 * elemento del BSC.
 * 
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public class ResourceExportPDF extends GenericResource {

    /**
     * Permite redireccionar a la exportaci&oacute;n del elemento 
     * actual. Los elementos que puedan ser administrados por el recurso 
     * deber&aacute;n implementar la interface PDFExportable.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con la liga
     * y el icono correspondiente al elemento a exportar.
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        WebPage wp = paramRequest.getWebPage();
        GenericIterator<Resource> listRes = wp.listResources();
        PrintWriter out = response.getWriter();
        StringBuilder icon = new StringBuilder();
        String webWorkPath = SWBPlatform.getContextPath() + "/swbadmin/icons/";
        String image = "pdfOffline.jpg";
        String alt = paramRequest.getLocaleString("alt");
        icon.append("<a href=\"#\" class=\"export-stgy\" title=\"");
        icon.append(alt);
        icon.append("\" target=\"_blank\">");
        icon.append("<img src=\"");
        icon.append(webWorkPath);
        icon.append(image);
        icon.append("\" alt=\"");
        icon.append(alt);
        icon.append("\" class=\"toolbar-img\" />");
        icon.append("</a>");
        String iconString = icon.toString();
        if (listRes != null) {
            while (listRes.hasNext()) {
                Resource resource = listRes.next();
                SWBResource base = SWBPortal.getResourceMgr().getResource(resource.getURI());
                if(base != null) {
                    if (base instanceof PDFExportable) {
                        PDFExportable pdf = (PDFExportable) base;
                        iconString = pdf.doIconExportPDF(request, response, paramRequest);
                        break;
                    }
                }
            }
            if (iconString.length() < 1) {
                iconString = icon.toString();
            }
        }
        out.println(iconString);
    }
}
