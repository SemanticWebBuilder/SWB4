package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.model.SWBContext;
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
    
    private Period getNearestPeriod(final List<Period> periods) {
        User user = SWBContext.getSessionUser();
        GregorianCalendar left;
        GregorianCalendar right;        
        GregorianCalendar current = new GregorianCalendar(TimeZone.getDefault(),
                new Locale(user.getLanguage() == null ? "es" : user.getLanguage(),
                user.getCountry() == null ? "MX" : user.getCountry()));
        for (Period period : periods) {
            left = new GregorianCalendar();
            left.setTime(period.getStart());
            right = new GregorianCalendar();
            right.setTime(period.getEnd());
            if (current.compareTo(left) >= 0 && current.compareTo(right) <= 0) {
                return period;
            }
        }
        try {
            BSC bsc = (BSC)getResourceBase().getWebSite();
            Period period = bsc.listValidPeriods().iterator().next();
            return period;
        }catch(NoSuchElementException nse) {
        }
        return null;
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response,
                SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        
        User user = paramRequest.getUser();
        //Validar usuario autorizado
        if (!user.isSigned()) {
            response.flushBuffer();
        }
        
        PrintWriter out = response.getWriter();
        StringBuilder output = new StringBuilder(64);
        //Obtener el Scorecard actual
        BSC currentBsc = (BSC) paramRequest.getWebPage().getWebSite();
        
        //Obtener listado de instancias de Periodos en el Scorecard
        Iterator<Period> allPeriods = Period.ClassMgr.listPeriods(currentBsc);
        List<Period> periods = SWBUtils.Collections.filterIterator(allPeriods, new GenericFilterRule<Period>() {
                @Override
                public boolean filter(Period s) {
                    User user = SWBContext.getSessionUser();
                    return !s.isValid() || !user.haveAccess(s);
                }
            });
        
        
        //Obtener el Periodo actual
        String periodId = (String) request.getSession().getAttribute(currentBsc.getId());
        //Verificar que exista el periodo válido en sesión. Si no existe, ponerlo
        Period nearestPeriod = null;
        if (!Period.ClassMgr.hasPeriod(periodId, currentBsc)) {
            nearestPeriod = getNearestPeriod(periods);
            if (nearestPeriod != null) {
                periodId = nearestPeriod.getId();
                request.getSession(true).setAttribute(currentBsc.getId(), nearestPeriod.getId());
            }
        } else {
            nearestPeriod = Period.ClassMgr.getPeriod(periodId, currentBsc);
        }

        int periodCount = 0;
        String actionUrl = paramRequest.getActionUrl().setAction("setPeriod").toString();
        
        //funcion de javascript para mandar la forma que contiene el select
        output.append("<script type=\"text/javascript\">\n");
        output.append("  function settingPeriod(element) {\n");
        output.append("      if (element && element[element.selectedIndex].value != \"\") {\n");
        output.append("        var urlToGo = element.form.action;\n");
        output.append("        urlToGo += ('?periodId=' + element[element.selectedIndex].value);\n");
        output.append("        var xmlhttp;\n");
        output.append("        if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari\n");
        output.append("          xmlhttp=new XMLHttpRequest();\n");
        output.append("        } else {// code for IE6, IE5\n");
        output.append("          xmlhttp=new ActiveXObject(\"Microsoft.XMLHTTP\");\n");
        output.append("        }\n");
        output.append("        xmlhttp.onreadystatechange=function() {\n");
        output.append("          if (xmlhttp.readyState==4 && xmlhttp.status==200) {\n");
        output.append("            location.reload(true);\n");
        output.append("          }\n");
        output.append("        }\n");
        output.append("        xmlhttp.open(\"GET\", urlToGo, true);\n");
        output.append("        xmlhttp.send();\n");
        output.append("      }\n");
        output.append("  }\n");
        output.append("</script>\n");
        //armar despliegue del select a mostrar
        output.append("<form method=\"post\" action=\"" + actionUrl + "\">\n");
        output.append("  <select name=\"periodId\" onChange=\"settingPeriod(this);\">\n");
        if (!periods.isEmpty()) {
        //Recorrer el listado de Scorecards
            for(Period nextPeriod:periods) {
                periodCount++;
                String optionValue = nextPeriod != nearestPeriod ? nextPeriod.getId() : "";
                output.append("    <option value=\"");
                output.append(optionValue);
                output.append("\"");
                output.append((nextPeriod != nearestPeriod ? "" : " selected=\"selected\""));
                output.append(">");
                output.append(nextPeriod.getTitle());
                output.append("</option>\n");
            }
        } else {
            output.append("    <option value=\"\">No hay otros Per&iacute;odos</option>\n");
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
