/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 *
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class ProcessDocumentationResource extends GenericAdmResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        super.doView(request, response, paramsRequest); //To change body of generated methods, choose Tools | Templates.
    }
    
}
