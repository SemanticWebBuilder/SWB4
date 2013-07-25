/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.model.Documentation;
import org.semanticwb.process.model.ProcessElement;

/**
 *
 * @author Hasdai Pacheco <ebenezer.sanchez@infotec.com.mx>
 */
public class ProcessDocumentationResource extends GenericAdmResource {
    public static String ACT_UPDATETEXT = "updateText";
    public static String PARAM_TEXT = "txt";
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        String suri = request.getParameter("suri");
        
        if (suri != null) {
            ProcessElement pe = (ProcessElement)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                if (ACT_UPDATETEXT.equals(action)) {
//                    String txt = request.getParameter(PARAM_TEXT);
//                    //TODO: Actualizar el texto del richText
//                    
//                    //Si no existe documentación, crearla
//                    Documentation doc = null;
//                    
//                    if (pe.listDocumentations().hasNext()) {
//                        doc = pe.getDocumentation();
//                    } else {
//                        doc = Documentation.ClassMgr.createDocumentation(response.getWebPage().getWebSite());
//                    }
//                    
//                    //Guardar el texto de la documentación
//                    if (doc != null) {
//                        doc.setText(txt);
//                        doc.setTextFormat("text/html");
//                    }
//                    
//                    //Agregar la documentación al elemento
//                    pe.addDocumentation(doc);
                } else {
                    super.processAction(request, response);
                }
            }
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException {
        String suri = request.getParameter("suri");
        
        if (suri != null) {
            ProcessElement pe = (ProcessElement)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
            if (pe != null) {
                //TODO: Desplegar editor
                
                //TODO: Llamar a la acción para actualizar el texto pasandole el texto y el suri
//                SWBResourceURL actUrl = paramsRequest.getActionUrl().setAction(ACT_UPDATETEXT);
//                actUrl.setParameter("suri", suri);
//                actUrl.setParameter("text", txt);
            }
        }
    }
}
