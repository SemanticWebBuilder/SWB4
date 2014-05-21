/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Permite definir que un elemento podr&aacute; ser exportado.
 *
 * @author Martha Elvia Jim&eacute;nez Salgado
 * @version %I%, %G%
 * @since 1.0
 */
public interface PDFExportable {

    public static final String Mode_StreamPDF = "pdf";
    public static final String Mode_StreamPNG = "png";

    /**
     * Interface que permite obtener la url para redireccionar al recurso que
     * contega la exportaci&oacute;n a PDF del elemento correspondiente.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con la liga
     * y el icono correspondiente al elemento a exportar.
     * @throws SWBResourceException SWBResourceException Excepti&oacute;n
     * utilizada para recursos de SWB
     * @throws java.io.IOException Excepti&oacute;n de IO
     */
    public String doIconExportPDF(HttpServletRequest request, SWBParamRequest paramRequest)
            throws SWBResourceException, java.io.IOException;
    
    
}
