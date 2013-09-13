package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Operation;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * Se encarga de mostrar un formulario para definir las reglas de evaluación, de
 * una serie dada, para cada estado disponible en el indicador.
 * 
 * @author carlos.ramos
 * @version %I%, %G%
 * @since 1.0
 */
public class EvaluationRulesManager extends GenericResource {

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html,UTF-8");
        
        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
         
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        StringBuilder htm = new StringBuilder();
        String lang = user.getLanguage();
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        Series series = (Series)obj.createGenericInstance();
        Indicator indicator = series.getIndicator();
        Iterator<State> states = indicator.listStates();
        List<Series> serieses = SWBUtils.Collections.copyIterator(indicator.listSerieses());
        serieses.remove(series);        
        List<Operation> operations = SWBUtils.Collections.copyIterator(Operation.ClassMgr.listOperations(getResourceBase().getWebSite()));
        
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">\n");
        out.println("  dojo.require('dojo.parser');\n");
        out.println("  dojo.require('dijit.layout.ContentPane');\n");
        out.println("  dojo.require('dijit.form.Form');\n");
        out.println("  dojo.require('dijit.form.CheckBox');\n");
        out.println("</script>\n");
        
        if(states.hasNext())
        {
            SWBResourceURL url = paramRequest.getActionUrl();
            out.println("<form method=\"post\" id=\"rulessetup\" action=\""+url+"\" class=\"swbform\" type=\"dijit.form.Form\" onsubmit=\"submitForm('rulessetup');return false;\">");
            out.println("<table width=\"50%\" border=\"1\">");
            while(states.hasNext())
            {
                State state = states.next();
                out.println("  <tr>");
                out.println("    <td width=\"5%\"><input type=\"checkbox\" name=\"rc1\" value=\"state1\" /></td>");
                out.println("    <td width=\"15%\">State1<input name=\"rc2_state1\" type=\"hidden\" value=\"state1\" /></td>");
                out.println("    <td width=\"15%\">");
                
                //htm.append(renderSelect(state.getId(), operations.iterator()));
                out.println("    <select name=\"rc3_state1\">");
                out.println("      <option value=\"oper1\">&gt;=</option>");
                out.println("      <option value=\"oper2\">&gt;</option>");
                out.println("      <option value=\"oper3\">&lt;=</option>");
                out.println("      <option value=\"oper4\">&lt;</option>");
                out.println("      <option value=\"oper5\">=</option>");
                out.println("      <option value=\"oper6\">!=</option>");
                out.println("      <option value=\"oper7\">rango</option>");
                out.println("      <option value=\"oper8\">vacío</option>");
                out.println("    </select>");
                out.println("    </td>");
                out.println("    <td width=\"25%\">");
                out.println("     <select name=\"rc4_state1\">");
                out.println("      <option value=\"serie1\">Serie 1</option>");
                out.println("      <option value=\"serie2\">Serie 2</option>");
                out.println("      <option value=\"serie3\">Serie 3</option>");
                out.println("      <option value=\"serie4\">Serie 4</option>");
                out.println("    </select>");
                out.println("    </td>");
                out.println("    <td width=\"40%\"><input name=\"rc5_state1\" type=\"text\" /></td>");
                out.println("  </tr>");
            }
            out.println("</table>");
            out.println("<input type=\"submit\" value=\"Enviar\" />");
            out.println("</form>");
        }
        
        if(request.getParameter("statusMsg")!=null) {
            out.println("<div dojoType=\"dojox.layout.ContentPane\">");
            out.println("<script type=\"dojo/method\">");
            out.println("showStatus('" + request.getParameter("statusMsg") + "');\n");
            out.println("</script>\n");
            out.println("</div>");
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        super.doEdit(request, response, paramRequest);
    }
    
    private String renderSelect(final String name, Iterator<SWBClass> iterator)
    {
        StringBuilder select = null;
        if(iterator!=null && iterator.hasNext())
        {
            select = new StringBuilder("<select name=\"\">");
            while(iterator.hasNext())
            {
//                SWBClass object = objects.next();
            }
            select.append("</select>");
        }
        return select.toString();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
