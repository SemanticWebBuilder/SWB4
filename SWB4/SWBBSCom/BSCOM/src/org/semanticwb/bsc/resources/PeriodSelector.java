package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;

/**
 * Muestra la interface para que el usuario seleccione el per&iacute;odo deseado a fin de visualizar la informaci&oacute;n.
 * @author Jose.Jimenez
 */
public class PeriodSelector extends GenericResource {

    /**
     * Genera la interface para que el usuario seleccione el per&iacute;odo deseado y se
     * considere &eacute;ste, en los despliegues posteriores de información.
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
        //Obtener el Periodo actual
        String periodId = (String) request.getSession(true).getAttribute(currentBsc.getId());
        Period currentPeriod = Period.ClassMgr.getPeriod(periodId, currentBsc);
        //Obtener listado de instancias de Scorecards
        Iterator<Period> allPeriods = Period.ClassMgr.listPeriods(currentBsc);
        int periodCount = 0;
        User user = paramRequest.getUser();
        String actionUrl = paramRequest.getActionUrl().setAction("setPeriod").toString();
        
        //funcion de javascript para mandar la forma que contiene el select
        output.append("<script type=\"text/javascript\">\n");
        output.append("  function settingPeriod(element) {\n");
        output.append("    if (element && element[element.selectedIndex].value != \"\") {\n");
        output.append("	     element.form.submit();\n");
        output.append("	     location.reload(true);\n");
        output.append("    }\n");
        output.append("  }\n");
        output.append("</script>\n");
        //armar despliegue del select a mostrar
        output.append("<form name=\"selectPeriod\" id=\"selectPeriod\" action=\"");
        output.append(actionUrl);
        output.append("\">\n");
        output.append("  <select name=\"periodId\" id=\"periodId\" onChange=\"settingPeriod(this);\">\n");
        if (allPeriods != null && allPeriods.hasNext()) {
            //Recorrer el listado de Scorecards
            while (allPeriods.hasNext()) {
                Period nextPeriod = allPeriods.next();
                if (nextPeriod.isValid() && user.haveAccess(nextPeriod)) {
                    periodCount++;
                    String optionValue = nextPeriod != currentPeriod ? nextPeriod.getId() : "";
                    output.append("    <option value=\"");
                    output.append(optionValue);
                    output.append("\"");
                    output.append((nextPeriod != currentPeriod ? "" : " selected"));
                    output.append(">");
                    output.append(nextPeriod.getTitle());
                    output.append("</option>\n");
                }
            }
        }
        if (periodCount == 0) {
            output.append("    <option value=\"\">No hay otros Per&iacute;odos</option>\n");
        }
        output.append("  </select>\n");
        output.append("</form>\n");
        out.println(output.toString());
    }

    /**
     * Ejecuta el almacenamiento del identificador del período seleccionado en la sesión del usuario
     * @param request la petici&oacute;n enviada por el cliente
     * @param response la respuesta generada a la petici&oacute;n recibida
     * @throws SWBResourceException si durante la ejecuci&oacute;n no se cuenta con los recursos necesarios
     *          para atender la petici&oacute;n
     * @throws IOException si durante la ejecuci&oacute;n ocurre alg&uacute;n problema 
     *          con la generaci&oacute;n o escritura de la respuesta
     */
    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response) throws SWBResourceException, IOException {
        
        String action = response.getAction();
        
        if (action != null && action.equals("setPeriod")) {
            String periodId = request.getParameter("periodId");
            WebSite website = response.getWebPage().getWebSite();
            if (periodId != null && !periodId.isEmpty()) {
                Period period = Period.ClassMgr.getPeriod(periodId, website);
                if (period != null) {
                    request.getSession(true).setAttribute(website.getId(), period.getId());
                }
            }
        } else {
            super.processAction(request, response);
        }
    }
    
}
