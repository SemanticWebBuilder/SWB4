
package org.semanticwb.process.resources;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.semanticwb.process.Activity;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.kpi.CaseCountSys;
import org.semanticwb.process.utils.Restriction;
//import org.semanticwb.process.bpms.CaseCountSys;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBResourceException;
/**
 *
 * @author Sergio Téllez
 */
public class SystemCase extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        CaseCountSys sys = new CaseCountSys();
        response.getWriter().print("<div class=\"swbform\">\n");
        response.getWriter().print("  <fieldset>\n");
        response.getWriter().println("      <ul><li>Número total de instancias de procesos: " + sys.totalProcessInstance()+"</li>");
        sys.addRestriction(new Restriction(CaseCountSys.STATUS,String.valueOf(Instance.STATUS_PROCESSING),null));
        response.getWriter().println("      <li>Número total de instancias de procesos en ejecución: " + sys.totalProcessInstance()+"</li>");
        sys.clear();
        sys.addRestriction(new Restriction(CaseCountSys.USER,"admin",null));
        response.getWriter().println("     <li>Número total de instancias de procesos del usuario admin: " + sys.totalProcessInstance()+"</li></ul>");
        response.getWriter().print("  </fieldset>\n");
        response.getWriter().print("</div>\n");
    }
}