package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.bsc.BSC;
import org.semanticwb.model.User;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Permite al usuario seleccionar un Scorecard para visualizar en el navegador la informaci&oacute;n asociada.
 * @author Jose.Jimenez
 */
public class BSCSelector extends GenericResource {

    /**
     * Genera la interface para que el usuario seleccione el Scorecard deseado y se
     * muestre la nueva pesta&ntilde;a de navegador con la informaci&oacute;n del Scorecard seleccionado.
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @param paramRequest un objeto de la plataforma de SWB con datos adicionales de la petici&oacute;n
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
            SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(64);
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        //Obtener listado de instancias de Scorecards
        Iterator<BSC> todosBsc = BSC.ClassMgr.listBSCs();
        int bscCount = 0;
        User user = paramRequest.getUser();
        
        //funcion de javascript para mandar la forma que contiene el select
        output.append("<script type=\"text/javascript\">\n");
        output.append("function showScorecard(element) {\n");
	output.append("  var context = \"");
        output.append(SWBPortal.getDistributorPath());
        output.append((SWBPlatform.getContextPath() != "" ? "/" + SWBPlatform.getContextPath() : ""));
        output.append("\";\n");
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
                if (nextBsc.isActive() && user.haveAccess(nextBsc)) {
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
