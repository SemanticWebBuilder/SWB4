
package org.semanticwb.bsc.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.Resource;
import org.semanticwb.model.Role;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author carlos.ramos
 */
public class DataTableResource extends GenericResource {
    private static Logger log = SWBUtils.getLogger(DataTableResource.class);

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        User user = paramRequest.getUser();
        if(user==null || !user.isSigned())
        {
            response.sendError(403);
            return;
        }
        final String lang = user.getLanguage();
         
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject sobj = ont.getSemanticObject(suri);
        if(!(sobj.getGenericInstance() instanceof Indicator)) {
            response.getWriter().println("No hay indicador");
            return;
        }
        Indicator indicator = (Indicator)sobj.getGenericInstance();
        Series star = indicator.getStar();
        if(star == null) {
            response.getWriter().println("No hay STAR");
            return;
        }
        
        final String prx = getResourceBase().getWebSite().getId() + "_"; 
        
        List<Series> serieses = indicator.listValidSerieses();
        Collections.sort(serieses);
        PrintWriter out = response.getWriter();
        
        Iterator<Period> periods;
        try {
            periods = indicator.listMeasurablesPeriods();
        }catch(UndefinedFrequencyException | InappropriateFrequencyException e) {
            List<Period> lperiods = indicator.listValidPeriods();
            Collections.sort(lperiods);
            periods = lperiods.iterator();
        }
        out.println("<div class=\"swbform\">");
        out.println("<fieldset>");
        out.println("<table border=\"1\" width=\"98%\">"); 
        out.println("<thead>");
        out.println("<tr>");
        out.println("<th>Periodo</th>");
        out.println("<th>Estado</th>");
        for(Series series:serieses) {
            out.println("<th>"+(series.getTitle(lang)==null?series.getTitle():series.getTitle(lang))+"</th>");
        }
        out.println("</tr>");
        out.println("</thead>");
        out.println("<tbody>");
        while(periods.hasNext()) {
            Period period = periods.next();
            boolean inTime = isInMeasurementTime(period);
            out.println("<tr>");
            out.println("<td>");
            out.println(period.getTitle());            
            out.println("</td>");
            out.println("<td>");
            try {
                State state = star.getMeasure(period).getEvaluation().getStatus();
                String title = state.getTitle(lang)==null?state.getTitle():state.getTitle(lang);
                out.println("<span class=\""+(state.getIconClass()==null?"state-undefined":state.getIconClass())+"\">"+title+"</span>");
            }catch(Exception e) {
                out.println("--");
            }
            out.println("</td>");
            for(Series series:serieses) {
                out.println("<td>");
                String value = series.getMeasure(period)==null?"--":series.getFormatter().format(series.getMeasure(period).getValue());
                if(inTime && !series.isReadOnly() && userCanEdit()) {
                    SWBResourceURL url = paramRequest.getActionUrl();
                    url.setAction(SWBResourceURL.Action_EDIT);
                    url.setParameter("pid", period.getId());
                    url.setParameter("sid", series.getId());
                    out.println(renderUpdateInline(prx+series.getId(), url.toString(), value, false, "dijit.form.TextBox", "swb-ile"));
                    //out.print("<input type=\"text\" value=\""+value+"\" name=\"a\" onchange=\"\" />");
                }else {                    
                    out.print(value);
                }
                out.println("</td>");
            }
            out.println("</tr>");
        }
        out.println("</tbody>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("</div>");
    }
    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        Resource base = getResourceBase();
        PrintWriter out = response.getWriter();
        User user = paramRequest.getUser();

        String resourceUpdatedMessage = paramRequest.getLocaleString("usrmsg_Inline_doAdmin_msgRecursoActualizado");
        String fieldsetText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_fieldsetMessage");
        String rolesNotFoundText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_rolesNotFoundText");
        String listMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_listMessage");
        String rolesMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_rolesMessage");
        String userGroupsMessage = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_userGroupsMessage");
        String staticText1 = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_staticText1");
        String staticText2 = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_staticText2");
        String buttonText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_buttonText");
        String buttonResetText = paramRequest.getLocaleString("usrmsg_StaticText_doAdmin_buttonResetText");
        String lang = user.getLanguage();

        String action  = paramRequest.getAction();
        if(SWBParamRequest.Action_ADD.equals(action)) {
            out.println("<script type=\"text/javascript\">");
            out.println("<!--");
            out.println("   alert('" + resourceUpdatedMessage + " " + base.getId() + "');");
            out.println("   location='" + paramRequest.getRenderUrl().setAction(paramRequest.Action_EDIT).toString() + "';");
            out.println("-->");
            out.println("</script>");
        }
        
        WebSite wsite = base.getWebSite();

        String str_role = base.getAttribute("editRole", "0");

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        urlAction.setAction(SWBParamRequest.Action_EDIT);

        out.println("<div class=\"swbform\">");
        out.println("<form id=\"" + base.getId() + "/InLineEditRes\" name=\"" + getResourceBase().getId() + "/InLineEditRes\" action=\""+urlAction+"\" method=\"post\" >");
        out.println("<fieldset>");
        out.println("<legend>");
        out.println(fieldsetText);
        out.println("</legend>");
        out.println("<ul class=\"swbform-ul\">");
        String strTemp = "<option value=\"-1\">" + rolesNotFoundText + "</option>"; //Texto mostrado en la lista en caso de no existir roles
        
        String selected = str_role.equals("0")?" selected=\"selected\" ":"";
       
        StringBuilder strRules = new StringBuilder();
        strRules.append("\n<option value=\"0\" ");
        strRules.append(selected);
        strRules.append(">");
        strRules.append(listMessage);
        strRules.append("</option>");
        strRules.append("\n<optgroup label=\"");
        strRules.append(rolesMessage);
        strRules.append("\">");
        Iterator<Role> iRoles = wsite.getUserRepository().listRoles();
        while (iRoles.hasNext()) {
            Role oRole = iRoles.next();
            selected = str_role.equals(oRole.getURI())?" selected=\"selected\" ":"";
            strRules.append("\n<option value=\"");
            strRules.append(oRole.getURI());
            strRules.append("\"");
            strRules.append(selected);
            strRules.append(">");
            strRules.append(oRole.getDisplayTitle(lang));
            strRules.append("</option>");
        }
        strRules.append("\n</optgroup>");
        strRules.append("\n<optgroup label=\"");
        strRules.append(userGroupsMessage );
        strRules.append("\">");
        Iterator<UserGroup> iugroups = wsite.getUserRepository().listUserGroups();
        while (iugroups.hasNext()) {
            UserGroup oUG = iugroups.next();
            selected = str_role.equals(oUG.getURI())?" selected=\"selected\" ":"";
            strRules.append("\n<option value=\"");
            strRules.append(oUG.getURI());
            strRules.append("\"");
            strRules.append(selected);
            strRules.append(">");
            strRules.append(oUG.getDisplayTitle(lang));
            strRules.append("</option>");
        }
        strRules.append("\n</optgroup>");
//        if (strRules.toString().length() > 0) {
//            strTemp = strRules.toString();
//        }
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"leyenda1\" class=\"swbform-label\">"+staticText1+"</label>");
        out.print("     </li>");
        
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"editar\" class=\"swbform-label\">"+staticText2+"</label>");
        out.println("       <select name=\"editar\">" + strRules + "</select>");
        out.print("     </li>");
        
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"autosave\" class=\"swbform-label\">Guardar automáticamente</label>");
        out.println("       <input type=\"checkbox\" name=\"autosave\" id=\"autosave\" value=\"true\" "+(Boolean.parseBoolean(base.getAttribute("autosave"))?"checked=\"checked\"":"")+" dojoType=\"dijit.form.CheckBox\" promptMessage=\"Se guardan los datos al abandonar el campo de texto o mostrar controles para guardar y cancelar\" />");
        out.print("     </li>");
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"left\" class=\"swbform-label\">Tiempo antes</label>");
        out.println("       <input type=\"text\" name=\"left\" id=\"left\" maxlength=\"2\" value=\""+base.getAttribute("left","")+"\" regExp=\"\\d{1,2}\" dojoType=\"dijit.form.ValidationTextBox\" invalidMessage=\"Tamaño incorrecto\"  promptMessage=\"Tiempo antes de que termine el período para empezar a capturar mediciones\" />");
        out.print("     </li>");
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"right\" class=\"swbform-label\">Tiempo después</label>");
        out.println("       <input type=\"text\" name=\"right\" id=\"right\" maxlength=\"2\" value=\""+base.getAttribute("right","")+"\" regExp=\"\\d{1,2}\" dojoType=\"dijit.form.ValidationTextBox\" invalidMessage=\"Tamaño incorrecto\"  promptMessage=\"Tiempo después de que termine el período para empezar a capturar mediciones\" />");
        out.print("     </li>");
        out.println("   <li class=\"swbform-li\">");
        out.println("       <label for=\"time\" class=\"swbform-label\">Tiempo</label>");
        out.println("       <select name=\"time\" dojoType=\"dijit.form.FilteringSelect\">");
        out.println("         <option "+(base.getAttribute("time","").equals(Integer.toString(Calendar.DATE))?"selected=\"selectted\"":"")+" value=\""+Calendar.DATE+"\">Día</option>");
        out.println("         <option "+(base.getAttribute("time","").equals(Integer.toString(Calendar.WEEK_OF_MONTH))?"selected=\"selectted\"":"")+" value=\""+Calendar.WEEK_OF_MONTH+"\">Semana del mes</option>");
        out.println("         <option "+(base.getAttribute("time","").equals(Integer.toString(Calendar.WEEK_OF_YEAR))?"selected=\"selectted\"":"")+" value=\""+Calendar.WEEK_OF_YEAR+"\">Semana del año</option>");
        out.println("         <option "+(base.getAttribute("time","").equals(Integer.toString(Calendar.MONTH))?"selected=\"selectted\"":"")+" value=\""+Calendar.MONTH+"\">Mes</option>");
        out.println("         <option "+(base.getAttribute("time","").equals(Integer.toString(Calendar.YEAR))?"selected=\"selectted\"":"")+" value=\""+Calendar.YEAR+"\">Año</option>");
        out.println("       </select>");
        out.print("     </li>");
        out.print("</ul>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"submit\" name=\"btn\" >" + buttonText + "</button>");
        out.println("<button dojoType=\"dijit.form.Button\" type=\"reset\" name=\"btn\" >" + buttonResetText + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = getResourceBase();
        String action = response.getAction();
        
        if(SWBActionResponse.Action_EDIT.equals(action)) {
            String attr = request.getParameter("editar");
            if(attr != null) {
                base.setAttribute("editRole", attr);                
            }
            
            attr = request.getParameter("autosave")==null?"false":"true";
            base.setAttribute("autosave", attr);
            
            attr = request.getParameter("left");
            try {
                Integer.parseInt(attr);
                base.setAttribute("left", attr);
            }catch(NumberFormatException nfe) {
                base.setAttribute("left", "10");
            }
            
            attr = request.getParameter("right");
            try {
                Integer.parseInt(attr);
                base.setAttribute("right", attr);
            }catch(NumberFormatException nfe) {
                base.setAttribute("right", "10");
            }
            
            attr = request.getParameter("time");
            base.setAttribute("time", attr);
            
            try {
                getResourceBase().updateAttributesToDB();
            }catch(Exception e) {
                log.error("Error al guardar Role/UserGroup para acceso al InlineEdit.",e);
            }finally {
                response.setAction(response.Action_ADD);
            }
        }
        
        
        String data = request.getParameter("value");
        
        WebSite model = base.getWebSite();
        String pid = request.getParameter("pid");
        String sid = request.getParameter("sid");
        if(Period.ClassMgr.hasPeriod(pid, model) && Series.ClassMgr.hasSeries(sid, model)) {
            Period period = Period.ClassMgr.getPeriod(pid, model);
            Series series = Series.ClassMgr.getSeries(sid, model);
            Measure measure = series.getMeasure(period);
            if(measure == null) {
                measure = Measure.ClassMgr.createMeasure(model);
                PeriodStatus ps = PeriodStatus.ClassMgr.createPeriodStatus(model);
                ps.setPeriod(period);
                measure.setEvaluation(ps);
                series.addMeasure(measure);
            }
            
            float value = 0;
            try {
                value = Float.parseFloat(data);
            }catch(NumberFormatException nfe) {
                try {
                    Number number = series.getFormatter().parse(data);
                    value = number.floatValue();
                }catch(ParseException pe) {
                    value = 0;
                }catch(Exception e) {
                    value = 0;
                }
            }finally {
                measure.setValue(value);
                measure.evaluate();
                series.getIndicator().getObjective().updateAppraisal(period);
            }
        }else {
            
        }
        
    }
    
    private boolean isInMeasurementTime(final Period period) {
        int tleft, tright, type;
        try {
            tleft = Integer.parseInt(getResourceBase().getAttribute("left", "0"));
        }catch(NumberFormatException e) {
            tleft = 0;
        }
        try {
            tright = Integer.parseInt(getResourceBase().getAttribute("right", "0"));
        }catch(NumberFormatException e) {
            tright = 0;
        }
        String time = getResourceBase().getAttribute("time");
        switch(time) {
            case "DATE":
                type = Calendar.DATE;
                break;
            case "WEEK_OF_MONTH":
                type = Calendar.WEEK_OF_MONTH;
                break;
            case "WEEK_OF_YEAR":
                type = Calendar.WEEK_OF_YEAR;
                break;
            case "MONTH":
                type = Calendar.MONTH;
                break;
            case "YEAR":
                type = Calendar.YEAR;
                break;
            default:
                type = Calendar.DATE;
                break;
        }
        type = Calendar.DATE;
        
        GregorianCalendar current = new GregorianCalendar();
        Date end = period.getEnd();
        if(end==null) {
            return false;
        }
        GregorianCalendar left = new GregorianCalendar();
        left.setTime(end);
        left.add(type, -tleft);
        GregorianCalendar right = new GregorianCalendar();
        right.setTime(end);
        right.add(type, tright);
        return current.compareTo(left)>=0 && current.compareTo(right)<=0;
    }
    
    private boolean userCanEdit() {
        boolean access = false;
        String str_role = getResourceBase().getAttribute("editRole", "0");
        final User user = SWBContext.getSessionUser();
        
        if(user!=null && str_role!=null)
        {
            SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
            GenericObject gobj = null;
            try {
                gobj = ont.getGenericObject(str_role);
            }catch(Exception e) {
                log.error("Errror InlineEdit.userCanEdit()", e);
                return Boolean.FALSE;
            }

            UserGroup ugrp = null;
            Role urole = null;

            if(gobj != null) {
                if(gobj instanceof UserGroup) {
                    ugrp = (UserGroup) gobj;
                    if(user.hasUserGroup(ugrp)) {
                        access = true;
                    }
                }else if(gobj instanceof Role) {
                    urole = (Role) gobj;
                    if(user.hasRole(urole)) {
                        access = true;
                    }
                }
            }else {
                access = true;
            }
        }
        return access;
    }
    
    private String renderUpdateInline(final String objId, final String url, final String data, final boolean autosave, final String editor, final String cssClass) {
        StringBuilder script = new StringBuilder();
        script.append("<script type=\"text/javascript\">\n");
        script.append("<!--\n");
        script.append("var iledit_" + objId + ";");
        script.append("dojo.addOnLoad( function() {");
        script.append("    iledit_" + objId + " = new dijit.InlineEditBox({");
        script.append("    id: \"ile_" + objId + "\",");
        script.append("    autoSave: "+autosave+",");
        script.append("    editor: \""+editor+"\",");
        script.append("    editorParams: {trim:true, required:true},");
        script.append("    width: '80px',");
        script.append("    value: 'hola',");
        script.append("    onChange: function(value){");
        script.append("           getSyncHtml('" + url + (url.contains("?")?"&":"?") + "value='+value);");
        script.append("      }");
        script.append("    }, 'eb_" + objId + "');");
        script.append("  }");
        script.append("); iledit_"+objId+".startup();\n");
        script.append("-->\n");
        script.append("</script>\n");
        //script.append("<span id=\"eb_" + objId + "\" class=\""+cssClass+"\">" + data + "</span>");
        script.append("<span id=\"eb_" + objId + "\" class=\""+cssClass+"\"></span>");
        return script.toString();
    }
}
