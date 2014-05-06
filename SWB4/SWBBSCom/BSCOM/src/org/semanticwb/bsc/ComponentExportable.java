/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Interface que permite definir que un recurso o componente puede ser agregado
 * a una exportaci&oacute;n (PDF)
 *
 * @author martha.jimenez
 */
public interface ComponentExportable {

    /**
     * Permite generar el c&oacute;digo HTML usado en la exportaci&oacute;n a PDF.
     *
     * @param request Proporciona informaci&oacute;n de petici&oacute;n HTTP
     * @param paramRequest Objeto con el cual se acceden a los objetos de SWB
     * @return el objeto String que representa el c&oacute;digo HTML con los
     * datos a exportar(Gr&aacute;fica de la tabla de datos de un indicador)
     * @throws SWBResourceException SWBResourceException SWBResourceException
     * Excepti&oacute;n utilizada para recursos de SWB
     * @throws IOException Excepti&oacute;n de IO
     *
     */
    public String doComponentExport(HttpServletRequest request,
            SWBParamRequest paramRequest)
            throws SWBResourceException, java.io.IOException;
}
