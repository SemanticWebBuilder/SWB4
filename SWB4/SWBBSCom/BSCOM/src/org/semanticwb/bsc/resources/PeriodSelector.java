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
 * Muestra la interface para que el usuario seleccione el período deseado a fin de visualizar la información.
 * @author Jose.Jimenez
 */
public class PeriodSelector extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
                SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(64);
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        //Obtener el Periodo actual
        String periodId = (String) request.getSession().getAttribute(currentBsc.getId());
        Period currentPeriod = Period.ClassMgr.getPeriod(periodId, currentBsc);
        //Obtener listado de instancias de Scorecards
        Iterator<Period> allPeriods = Period.ClassMgr.listPeriods(currentBsc);
        int periodCount = 0;
        User user = paramRequest.getUser();
        String actionUrl = paramRequest.getActionUrl().setAction("setPeriod").toString();
        System.out.println("PeriodoSesion: " + ((String) request.getSession().getAttribute(currentBsc.getId())));
        
        //funcion de javascript para mandar la forma que contiene el select
        output.append("<script type=\"text/javascript\">\n");
        output.append("  function settingPeriod(element) {\n");
        output.append("      if (element && element[element.selectedIndex].value != \"\") {\n");
        output.append("	       element.form.submit();\n");
        output.append("	       location.reload(true);\n");
        output.append("      }\n");
        output.append("  }\n");
        output.append("</script>\n");
        //armar despliegue del select a mostrar
        output.append("<form name=\"selectPeriod\" id=\"selectPeriod\" action=\"" + actionUrl + "\">\n");
        output.append("  <select name=\"periodId\" id=\"periodId\" onChange=\"settingPeriod(this);\">\n");
        if (allPeriods != null && allPeriods.hasNext()) {
        //Recorrer el listado de Scorecards
            while (allPeriods.hasNext()) {
                Period nextPeriod = (Period) allPeriods.next();
                if (nextPeriod.isValid() && user.haveAccess(nextPeriod)) {
                    periodCount++;
                    String optionValue = nextPeriod != currentPeriod ? nextPeriod.getId() : "";
                    output.append("    <option value=\"" + optionValue + "\"" + (nextPeriod != currentPeriod ? "" : " selected") + ">" + nextPeriod.getTitle() + "</option>\n");
                }
            }
            if (periodCount == 0) {
                output.append("    <option value=\"\">No hay otros Per&iacute;odos</option>\n");
            }
        }
        output.append("  </select>\n");
        output.append("</form>\n");
        out.println(output.toString());
    }

    @Override
    public void processAction(HttpServletRequest request,
            SWBActionResponse response) throws SWBResourceException, IOException {
        
        String action = response.getAction();
        
        if (action != null && action.equals("setPeriod")) {
            String periodId = request.getParameter("periodId");
            WebSite website = response.getWebPage().getWebSite();
            if (periodId != null) {
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
