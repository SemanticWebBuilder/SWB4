/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.BSC;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Permite al usuario seleccionar un Scorecard para visualizar en el navegador la información asociada.
 * @author Jose.Jimenez
 */
public class BSCSelector extends GenericResource {

    /**
     * Genera la interface para que el usuario seleccione el Scorecard deseado y se
     * muestre la nueva pestaña de navegador con la información del Scorecard seleccionado.
     * @param request
     * @param response
     * @param paramRequest
     * @throws SWBResourceException
     * @throws IOException 
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(64);
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        //Obtener listado de instancias de Scorecards
        Iterator<BSC> todosBsc = BSC.ClassMgr.listBSCs();
        int bscCount = 0;
        
        //funcion de javascript para mandar la forma que contiene el select
        output.append("<script type=\"text/javascript\">\n");
        output.append("function showScorecard(element) {\n");
	output.append("  var context = \"" + SWBPortal.getDistributorPath() + (SWBPlatform.getContextPath() != "" ? "/" + SWBPlatform.getContextPath() : "") + "\";\n");
	output.append("  var path;\n");
        output.append("  if (element && element[element.selectedIndex].value != \"\") {\n");
        output.append("    var value = element[element.selectedIndex].value.split(',');\n");
        output.append("    path = context + \"/\" + value[0] + \"/\" + value[1];\n");
        output.append("    element.form.action = path;\n");
        output.append("    window.open(path, 'window' + value[0]);\n");
        output.append("  }\n");
        output.append("}\n");
        output.append("</script>\n");
        //armar despliegue del select a mostrar
        output.append("<form name=\"openScorecard\" id=\"openScorecard\" action=\"\" target=\"_blank\">\n");
        output.append("  <select name=\"bscList\" id=\"bscList\" onChange=\"showScorecard(this);\">\n");
        if (todosBsc != null && todosBsc.hasNext()) {
            //Recorrer el listado de Scorecards
            while (todosBsc.hasNext()) {
                BSC nextBsc = (BSC) todosBsc.next();
                if (nextBsc.isActive()) {
                    bscCount++;
                    String optionValue = nextBsc != currentBsc 
                            ? nextBsc.getId() + "," + nextBsc.getHomePage().getId()
                            : "";
                    output.append("    <option value=\"" + optionValue + "\"" +
                            (nextBsc != currentBsc ? "" : " selected") +
                            ">" + nextBsc.getTitle() + "</option>\n");
		}
            }
            if (bscCount == 0) {
		output.append("    <option value=\"\">No hay otros Scorecards</option>\n");
            }
        }
        output.append("  </select>\n");
        output.append("</form>\n");
        
        out.println(output.toString());
    }
    
}
