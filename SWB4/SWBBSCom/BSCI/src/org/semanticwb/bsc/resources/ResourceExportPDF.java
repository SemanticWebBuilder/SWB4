
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
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
public class ResourceExportPDF extends GenericResource
{
    /**
     * Realiza operaciones en la bitacora de eventos.
     */
    private static final Logger log = SWBUtils.getLogger(ResourceExportPDF.class);

    /**
     * Permite redireccionar a la exportaci&oacute;n del elemento 
     * actual. Los elementos que puedan ser administrados por el recurso 
     * deber&aacute;n implementar la interface PDFExportable.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param response Proporciona funcionalidad especifica HTTP para
     * envi&oacute; en la respuesta
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        PrintWriter out = response.getWriter();
        
        WebPage wp = paramRequest.getWebPage();
        GenericIterator<Resource> listRes = wp.listResources();
        
        while (listRes.hasNext()) {
            Resource resource = listRes.next();
            SWBResource base = SWBPortal.getResourceMgr().getResource(resource.getURI());
            try {
                PDFExportable pdf = (PDFExportable) base;
                out.println(pdf.doIconExportPDF(request, paramRequest));
            }catch(ClassCastException cce) {
            }catch(NullPointerException npe) {
                log.error(npe);
            }
        }
    }
}
