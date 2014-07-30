package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        StringBuilder output = new StringBuilder(128);
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        //Obtener listado de instancias de Scorecards
        Iterator<BSC> todosBsc = BSC.ClassMgr.listBSCs();
        int bscCount = 0;
        User user = paramRequest.getUser();
        StringBuilder aux = new StringBuilder(64);
        
        if (todosBsc != null && todosBsc.hasNext()) {
            //Recorrer el listado de Scorecards
            while (todosBsc.hasNext()) {
                BSC nextBsc = (BSC) todosBsc.next();
                if( nextBsc.isValid() && user.haveAccess(nextBsc) && user.getUserRepository()==nextBsc.getUserRepository() ) {
                    boolean nextIsNotCurrent = !(nextBsc == currentBsc);
                    String optionValue = nextIsNotCurrent
                            ? nextBsc.getId() + "," + nextBsc.getHomePage().getId()
                            : "";
                    if (nextIsNotCurrent) {
                        bscCount++;
                        aux.append("      <li><a href=\"#\" class=\"dropdown-menu-item\"");
                        aux.append(" onclick=\"showScorecard('");
                        aux.append(optionValue);
                        aux.append("');\">");
                        aux.append(nextBsc.getTitle());
                        aux.append("</a></li>\n");
                    }
		}
            }
        }
        
        output.append("  <li class=\"dropdown\">\n");
        output.append("<script type=\"text/javascript\">\n");
        output.append("function showScorecard(value) {\n");
        output.append("  var thisBsc = \"");
        output.append(currentBsc.getTitle());
        output.append("\";\n");
	output.append("  var path = location.pathname.substring(0, location.pathname.indexOf(thisBsc));\n");
        output.append("  if (value && value != \"\") {\n");
        output.append("    var elements = value.split(',');\n");
        output.append("    path += elements[0] + \"/\" + elements[1];\n");
        output.append("    window.open(path, 'window' + elements[0]);\n");
        output.append("  }\n");
        output.append("}\n");
        output.append("</script>\n");
        output.append("    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\"><span class=\"hidden-xs\">");
        output.append(paramRequest.getLocaleString("lbl_title"));
        output.append(":&nbsp;</span>");
        output.append(currentBsc.getTitle());
        if (bscCount > 0) {
            output.append("<span class=\"caret\"></span>");
        }
        output.append("</a>\n");
        if(!aux.toString().isEmpty()) {        
            output.append("    <ul class=\"dropdown-menu\" role=\"menu\">\n");
            //se incluye la lista de scorecards encontrados
            output.append(aux);
            output.append("    </ul>\n");
        }
        output.append("  </li>\n");
        out.println(output.toString());
    }
    
}
