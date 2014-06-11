package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.Measurable;
import org.semanticwb.bsc.SM;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.catalogs.Format;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.bsc.utils.InappropriateFrequencyException;
import org.semanticwb.bsc.utils.UndefinedFrequencyException;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 * El recurso MeasuresManager permite a un administrador capturar los valores de
 * las mediciones de una serie. Para tal efecto, la serie debe tener definidos 
 * períodos y frecuencia de medición o periodicidad de medición. El recurso 
 * muestra una tabla con los períodos correspondientes a mediciones de acuerdo a 
 * la frecuencia de medición a partir del período más antiguo asignado.
 * @author carlos.ramos
 */
public class MeasuresManager extends GenericAdmResource {
    public static final String Default_FORMAT_PATTERN = "#,##0.0#";

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        User user = paramRequest.getUser();
        if(user==null || !user.isSigned()) {
            response.sendError(403);
            return;
        }
         
        final String suri = request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            response.flushBuffer();
            return;
        }
        
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        if (request.getParameter("statmsg") != null && !request.getParameter("statmsg").isEmpty()) {
            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
        }
        out.println("</script>");
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semObj = ont.getSemanticObject(suri);
        Measurable measurable = (Measurable)semObj.getGenericInstance();
//        if(measurable instanceof Series) {
            doEditSeries(request, response, paramRequest);
//        }else if(measurable instanceof Deliverable) {
//            doEditDeliverable(request, response, paramRequest);
//        }
    }
    
    private void doEditSeries(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ClassCastException
    {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        User user = paramRequest.getUser();
        
        SemanticObject semObj = SemanticObject.createSemanticObject(suri);
System.out.println("\n\ndoEditSeries()....");
System.out.println("semObj="+semObj);
//semObj.remove();
        Series series = (Series)semObj.getGenericInstance();
        final SM sm = series.getSm();
        Iterator<Period> measurablesPeriods = null;
        try
        {
            //measurablesPeriods = ((Indicator)series.getSm()).listMeasurablesPeriods();
            measurablesPeriods = sm.listMeasurablesPeriods();
        }catch(UndefinedFrequencyException e) {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("</fieldset>");
            out.println("<p>" + paramRequest.getLocaleString("msgUndefinedFrequencyException") + "</p>");
            out.println("</div>");
            out.flush();
            out.close();
        }catch(InappropriateFrequencyException e) {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("</fieldset>");
            out.println("<p>" + paramRequest.getLocaleString("msgInappropriateFrequencyException") + "</p>");
            out.println("</div>");
            out.flush();
            out.close();
        }   
        if(measurablesPeriods!=null && measurablesPeriods.hasNext())
        {
            SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
            String data = semObj.getSemanticClass().getName() + semObj.getId();

            out.println("<script type=\"text/javascript\">");
            out.println("  dojo.require('dojo.parser');");
            out.println("  dojo.require('dijit.layout.ContentPane');");
            out.println("  dojo.require('dijit.form.Form');");
            out.println("  dojo.require('dijit.form.TextBox');");
            out.println("  dojo.require('dijit.form.Button');");
            out.println("</script>");

            out.println("<div class=\"swbform\">");
            out.println("<form method=\"post\" id=\"frmAdd" + data + "\" action=\" " + url + "\" class=\"swbform\" dojoType=\"dijit.form.Form\" onsubmit=\"" + "submitForm('frmAdd" + data + "');return false;\">");
            out.println("<fieldset>");
            out.println("<input type=\"hidden\" name=\"suri\" value=\"" + semObj.getURI() + "\">");                    
            out.println("<table width=\"75%\">");
            out.println(" <thead>");
            out.println("  <tr>");
            out.println("   <th>"+paramRequest.getLocaleString("lblAction")+"</th>");
            out.println("   <th>"+paramRequest.getLocaleString("lblPeriod")+"</th>");
            out.println("   <th>"+paramRequest.getLocaleString("lblMeasure")+"</th>");            
            out.println("   <th>"+paramRequest.getLocaleString("lblStatus")+"</th>");            
            out.println("  </tr>");
            out.println(" </thead>");
            out.println(" <tbody>");
            Format format = series.getFormat();
            Locale locale;
            try {
                locale = new Locale(format.getLanguage().getId().toLowerCase(), format.getCountry().getId().toUpperCase());
            }catch(Exception e) {
                locale = new Locale("es","MX");
            }
            NumberFormat numFormat = NumberFormat.getNumberInstance(locale);
            DecimalFormat formatter = (DecimalFormat)numFormat;
            try {
                formatter.applyPattern(format.getFormatPattern());
            }catch(Exception iae) {
                formatter.applyPattern(getResourceBase().getAttribute("defaultFormatPattern", Default_FORMAT_PATTERN));
            }
            Period period;
            while(measurablesPeriods.hasNext())
            {
                period = measurablesPeriods.next();

                Measure measure = series.getMeasure(period);
                if(measure == null) {
                    measure = Measure.ClassMgr.createMeasure(period.getBSC());
                    series.addMeasure(measure);
                    PeriodStatus ps = PeriodStatus.ClassMgr.createPeriodStatus(period.getBSC());
                    ps.setPeriod(period);
                    ps.setStatus(sm.getMinimumState());
                    measure.setEvaluation(ps);
                    measure.setValue(0);
                }else {
                    // Valida que el estado asignado a la medición aún este asignado al indicador. Sino, lo elimina de la medición.
                    if(!sm.hasState(measure.getEvaluation().getStatus())) {
                        measure.getEvaluation().removeStatus();
                    }
                }

                String value = measure.getValue()==0?"":formatter.format(measure.getValue());
                String iconClass, statusTitle;

                try {
                    statusTitle = measure.getEvaluation().getStatus().getTitle(user.getLanguage())==null?measure.getEvaluation().getStatus().getTitle():measure.getEvaluation().getStatus().getTitle(user.getLanguage());
                    try {
                        iconClass = measure.getEvaluation().getStatus().getIconClass().trim();
                    }catch(Exception e) {
                        iconClass = "noStatus";
                    }
                }catch(Exception e) {
                    statusTitle = "Not set";
                    iconClass = "noStatus";
                }

                String title = period.getTitle(user.getLanguage()) == null ? period.getTitle() : period.getTitle(user.getLanguage());
                title = title.replaceAll("'", "");
                out.println("<tr>");

                // Acción
                // Eliminar regla
                out.println("<td>");
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", suri);
                urlr.setParameter("sval", measure.getURI());
                urlr.setAction(SWBResourceURL.Action_REMOVE);
                out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("queryRemove")                                        
                        + "')){submitUrl('" + urlr + "',this);} else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
                out.println("</td>");

                // Período
                out.print("<td>");
                out.print("<a href=\"#\" onclick=\"addNewTab('" + period.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + title + "');return false;\" title=\""+paramRequest.getLocaleString("lblViewDetails") +"\" >" + title + "</a>");
                out.println("</td>");

                // Valor de la medición
                out.println("<td>");
                out.println("<input type=\"text\" dojoType=\"dijit.form.TextBox\" name=\"" + period.getId() + "\" value=\""+value+"\" />");
                out.println("</td>");

                // Estatus
                out.println("<td><span class=\""+iconClass+"\">&nbsp;</span><span>"+statusTitle+"</span></td>");
                out.println("</tr>");
            }
            out.println(" </tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            out.println("<fieldset>");
            out.println(" <button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("lblSave") + "</button>");
            out.println(" <button dojoType=\"dijit.form.Button\" type=\"button\" " + "onClick=\"reloadTab('" + semObj.getURI() + "');\">" + paramRequest.getLocaleString("lblCancel") + "</button>");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("</div>");
        }else {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("</fieldset>");
            out.println("<p>" + paramRequest.getLocaleString("msgNoSuchPeriods") + "</p>");
            out.println("</div>");
        }

//        if(request.getParameter("statusMsg") != null && !request.getParameter("statusMsg").isEmpty())
//        {
//            out.println("<div dojoType=\"dojox.layout.ContentPane\">");
//            out.println("<script type=\"dojo/method\">");
//            out.println("showStatus('" + request.getParameter("statusMsg") + "');\n");
//            out.println("</script>\n");
//            out.println("</div>");
//        }
    }
    
    /*private void doEditDeliverable(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException, ClassCastException
    {
        PrintWriter out = response.getWriter();
        String suri = request.getParameter("suri");
        User user = paramRequest.getUser();
        
        SemanticObject semObj = SemanticObject.createSemanticObject(suri);
        Deliverable deliverable = (Deliverable)semObj.getGenericInstance();
        
        SWBResourceURL url = paramRequest.getActionUrl().setAction(SWBResourceURL.Action_ADD);
        String data = semObj.getSemanticClass().getName() + semObj.getId();

        out.println("<script type=\"text/javascript\">");
        out.println("  dojo.require('dojo.parser');");
        out.println("  dojo.require('dijit.layout.ContentPane');");
        out.println("  dojo.require('dijit.form.Form');");
        out.println("  dojo.require('dijit.form.TextBox');");
        out.println("  dojo.require('dijit.form.Button');");
        out.println("</script>");

        out.println("<div class=\"swbform\">");
        out.println("<form method=\"post\" id=\"frmAdd" + data + "\" action=\" " + url + "\" class=\"swbform\" dojoType=\"dijit.form.Form\" onsubmit=\"" + "submitForm('frmAdd" + data + "');return false;\">");
        out.println("<fieldset>");
        out.println("<input type=\"hidden\" name=\"suri\" value=\"" + semObj.getURI() + "\">");                    
        out.println("<table width=\"75%\">");
        out.println(" <thead>");
        out.println("  <tr>");
//        out.println("   <th>"+paramRequest.getLocaleString("lblAction")+"</th>");
//        out.println("   <th>"+paramRequest.getLocaleString("lblPeriod")+"</th>");
        out.println("   <th>"+paramRequest.getLocaleString("lblMeasure")+"</th>");            
        out.println("   <th>"+paramRequest.getLocaleString("lblStatus")+"</th>");            
        out.println("  </tr>");
        out.println(" </thead>");
        out.println(" <tbody>");
        
        
//        Format format = series.getFormat();
//        Locale locale;
//        try {
//            locale = new Locale(format.getLanguage().getId().toLowerCase(), format.getCountry().getId().toUpperCase());
//        }catch(Exception e) {
//            locale = new Locale("es","MX");
//        }
//        NumberFormat numFormat = NumberFormat.getNumberInstance(locale);
//        DecimalFormat formatter = (DecimalFormat)numFormat;
//        try {
//            formatter.applyPattern(format.getFormatPattern());
//        }catch(Exception iae) {
//            formatter.applyPattern(getResourceBase().getAttribute("defaultFormatPattern", Default_FORMAT_PATTERN));
//        }
//        Period period;
//        while(measurablesPeriods.hasNext())
//        {
//            period = measurablesPeriods.next();

//            Measure measure = series.getMeasure(period);
//            if(measure == null) {
//                measure = Measure.ClassMgr.createMeasure(period.getBSC());
//                series.addMeasure(measure);
//                PeriodStatus ps = PeriodStatus.ClassMgr.createPeriodStatus(period.getBSC());
//                ps.setPeriod(period);
//                ps.setStatus(objective.getMinimumState());
//                measure.setEvaluation(ps);
//                measure.setValue(0);
//            }else {
//                // Valida que el estado asignado a la medición aún este asignado al indicador. Sino, lo elimina de la medición.
//                if(!objective.hasState(measure.getEvaluation().getStatus())) {
//                    measure.getEvaluation().removeStatus();
//                }
//            }

//            String value = measure.getValue()==0?"":formatter.format(measure.getValue());
        float value = deliverable.getProgress();
        String iconClass, statusTitle;

        try {
            statusTitle = deliverable.getAutoStatus().getDisplayTitle(user.getLanguage())==null?deliverable.getAutoStatus().getTitle():deliverable.getAutoStatus().getDisplayTitle(user.getLanguage());
            try {
                iconClass = deliverable.getAutoStatus().getIconClass().trim();
            }catch(Exception e) {
                iconClass = "noStatus";
            }
        }catch(Exception e) {
            statusTitle = "Not set";
            iconClass = "noStatus";
        }

//            String title = period.getTitle(user.getLanguage()) == null ? period.getTitle() : period.getTitle(user.getLanguage());
//            title = title.replaceAll("'", "");
            out.println("<tr>");

            // Acción
            // Eliminar regla
            
//            out.println("<td>");
//            SWBResourceURL urlr = paramRequest.getActionUrl();
//            urlr.setParameter("suri", suri);
//            urlr.setParameter("sval", measure.getURI());
//            urlr.setAction(SWBResourceURL.Action_REMOVE);
//            out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("queryRemove")                                        
//                    + "')){submitUrl('" + urlr + "',this);} else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
//            out.println("</td>");

            // Período
//            out.print("<td>");
//            out.print("<a href=\"#\" onclick=\"addNewTab('" + period.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + title + "');return false;\" title=\""+paramRequest.getLocaleString("lblViewDetails") +"\" >" + title + "</a>");
//            out.println("</td>");

            // Valor de la medición
            out.println("<td>");
            out.println("<input type=\"text\" dojoType=\"dijit.form.TextBox\" name=\"" + deliverable.getId() + "\" value=\""+value+"\" />");
            out.println("</td>");

            // Estatus
            out.println("<td><span class=\""+iconClass+"\">&nbsp;</span><span>"+statusTitle+"</span></td>");
            out.println("</tr>");
//        }
        out.println(" </tbody>");
        out.println("</table>");
        out.println("</fieldset>");
        out.println("<fieldset>");
        out.println(" <button dojoType=\"dijit.form.Button\" type=\"submit\">" + paramRequest.getLocaleString("lblSave") + "</button>");
        out.println(" <button dojoType=\"dijit.form.Button\" type=\"button\" " + "onClick=\"reloadTab('" + semObj.getURI() + "');\">" + paramRequest.getLocaleString("lblCancel") + "</button>");
        out.println("</fieldset>");
        out.println("</form>");
        out.println("</div>");
    }*/
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        final String suri = request.getParameter("suri");
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        if(obj==null) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            return;
        }
        
        User user = response.getUser();
        Measurable measurable = (Measurable)obj.getGenericInstance();
        if(!user.isSigned() || !user.haveAccess(measurable)) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgUnauthorizedUser"));
            return;
        }

//        if(measurable instanceof Series) {
            processObjectiveRuleAction(request, response);
//        }else if(measurable instanceof Deliverable) {
//            processInitiativeRuleAction(request, response);
//        }
        
        response.setAction(SWBResourceURL.Action_EDIT);
        response.setRenderParameter("suri", suri);
        
        
//        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
//        SemanticObject semanticObj = ont.getSemanticObject(suri);
//        if(semanticObj==null) {
//            response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
//            return;
//        }
        
//        GenericObject genericObj = semanticObj.getGenericInstance();
//        if(genericObj instanceof Series)
//        {
//            Series series = (Series)genericObj;
//
//            if(SWBResourceURL.Action_REMOVE.equalsIgnoreCase(action))
//            {
//                SemanticObject objMeasure = ont.getSemanticObject(request.getParameter("sval"));
//                if(objMeasure!=null) {
//                    Measure measure = (Measure)objMeasure.getGenericInstance();
//                    if(series.hasMeasure(measure)) {
//                        series.removeMeasure(measure);
//                        response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveOk"));
//                    }else {
//                        response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveError"));
//                    }
//                }else {
//                    response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
//                }
//            }
//            else
//            {
//                Format format = series.getFormat();
//                Locale locale;
//                try {
//                    locale = new Locale(format.getLanguage().getId().toLowerCase(), format.getCountry().getId().toUpperCase());
//                }catch(Exception e) {
//                    locale = new Locale("es","MX");
//                }
//                NumberFormat numFormat = NumberFormat.getNumberInstance(locale);
//                DecimalFormat formatter = (DecimalFormat)numFormat;
//                try {
//                    formatter.applyPattern(format.getFormatPattern());
//                }catch(Exception iae) {
//                    formatter.applyPattern(getResourceBase().getAttribute("defaultFormatPattern", Default_FORMAT_PATTERN));
//                }
//
//                String pid, val;
//                BSC bsc = (BSC) semanticObj.getModel().getModelObject().getGenericInstance();
//                Enumeration<String> e = request.getParameterNames();
//                if(e.hasMoreElements())
//                {
//                    while(e.hasMoreElements()) {
//                        pid = e.nextElement();
//                        val = request.getParameter(pid)==null?"":request.getParameter(pid);
//                        if(Period.ClassMgr.hasPeriod(pid, bsc))
//                        {
//                            Period period = Period.ClassMgr.getPeriod(pid, bsc);
//                            Measure measure = series.getMeasure(period);
//                            PeriodStatus ps;
//                            if(measure == null) {
//                                measure = Measure.ClassMgr.createMeasure(bsc);
//                                series.addMeasure(measure);
//                                ps = PeriodStatus.ClassMgr.createPeriodStatus(bsc);
//                                ps.setPeriod(period);
//                                measure.setEvaluation(ps);
//                            }
//                            if(val.isEmpty()) {
//                                measure.setValue(0);
//                                measure.getEvaluation().setStatus(null);
//                                continue;
//                            }
//                            try {
//                                float value = Float.parseFloat(val);
//                                measure.setValue(value);
//                            }catch(NumberFormatException nfe) {
//                                try {
//                                    Number value = formatter.parse(val);
//                                    measure.setValue(value.floatValue());
//                                }catch(ParseException pe) {
//                                    measure.setValue(0);
//                                }
//                            }finally {
//                                measure.evaluate();
//                                series.getIndicator().getObjective().updateAppraisal(period);
//                            }
//                        }
//                    } //while
//                    response.setRenderParameter("statmsg", response.getLocaleString("msgUpdtOk"));
//                } //if
//            } //else
//        }
    }
    
    private void processObjectiveRuleAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        response.setRenderParameter("suri", suri);
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semanticObj = ont.getSemanticObject(suri);
        GenericObject genericObj = semanticObj.getGenericInstance();
        Series series = (Series)genericObj;
        if(SWBResourceURL.Action_REMOVE.equalsIgnoreCase(action))
        {
            SemanticObject objMeasure = ont.getSemanticObject(request.getParameter("sval"));
            if(objMeasure!=null) {
                Measure measure = (Measure)objMeasure.getGenericInstance();
                if(series.hasMeasure(measure)) {
                    series.removeMeasure(measure);
                    response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveOk"));
                }else {
                    response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveError"));
                }
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            }
        }
        else
        {
            Format format = series.getFormat();
            Locale locale;
            try {
                locale = new Locale(format.getLanguage().getId().toLowerCase(), format.getCountry().getId().toUpperCase());
            }catch(Exception e) {
                locale = new Locale("es","MX");
            }
            NumberFormat numFormat = NumberFormat.getNumberInstance(locale);
            DecimalFormat formatter = (DecimalFormat)numFormat;
            try {
                formatter.applyPattern(format.getFormatPattern());
            }catch(Exception iae) {
                formatter.applyPattern(getResourceBase().getAttribute("defaultFormatPattern", Default_FORMAT_PATTERN));
            }

            String pid, val;
            BSC bsc = (BSC) semanticObj.getModel().getModelObject().getGenericInstance();
            Enumeration<String> e = request.getParameterNames();
            if(e.hasMoreElements())
            {
                while(e.hasMoreElements()) {
                    pid = e.nextElement();
                    val = request.getParameter(pid)==null?"":request.getParameter(pid);
                    if(Period.ClassMgr.hasPeriod(pid, bsc))
                    {
                        Period period = Period.ClassMgr.getPeriod(pid, bsc);
                        Measure measure = series.getMeasure(period);
                        PeriodStatus ps;
                        if(measure == null) {
                            measure = Measure.ClassMgr.createMeasure(bsc);
                            series.addMeasure(measure);
                            ps = PeriodStatus.ClassMgr.createPeriodStatus(bsc);
                            ps.setPeriod(period);
                            measure.setEvaluation(ps);
                        }
                        if(val.isEmpty()) {
                            measure.setValue(0);
                            measure.getEvaluation().setStatus(null);
                            continue;
                        }
                        try {
                            float value = Float.parseFloat(val);
                            measure.setValue(value);
                        }catch(NumberFormatException nfe) {
                            try {
                                Number value = formatter.parse(val);
                                measure.setValue(value.floatValue());
                            }catch(ParseException pe) {
                                measure.setValue(0);
                            }
                        }finally {
                            measure.evaluate();
                            //((Indicator)series.getSm()).getObjective().updateAppraisal(period);
                            series.getSm().updateAppraisal(period);
                        }
                    }
                } //while
                response.setRenderParameter("statmsg", response.getLocaleString("msgUpdtOk"));
            } //if
        } //else
    }
    
    /*private void processInitiativeRuleAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException
    {
System.out.println("\n\nprocessInitiativeRuleAction().....");
//        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        response.setRenderParameter("suri", suri);
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject semanticObj = ont.getSemanticObject(suri);
        GenericObject genericObj = semanticObj.getGenericInstance();
        Deliverable deliverable = (Deliverable)genericObj;
        int value;
        try {
            value = Integer.parseInt(request.getParameter(deliverable.getId()));
        }catch(NumberFormatException nfe) {
            value = 0;
        }
        deliverable.setProgress(value);
    }*/
}
