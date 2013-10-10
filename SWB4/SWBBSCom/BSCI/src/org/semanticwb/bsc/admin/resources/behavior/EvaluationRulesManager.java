package org.semanticwb.bsc.admin.resources.behavior;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.GenericFilterRule;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.State;
import org.semanticwb.bsc.catalogs.Operation;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.tracing.EvaluationRule;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.SWBModel;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.portal.api.GenericAdmResource;
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
public class EvaluationRulesManager extends GenericAdmResource {
    public static final String Action_UPDT_SERIES = "updsrs";
    public static final String Action_UPDT_OPER = "updopr";
    public static final String Action_UPDT_FACTOR = "updftr";
    public static final String Action_UPDT_ACTIVE = "updactv";
    public static final String Action_ACTIVE_ALL = "actall";
    public static final String Action_DEACTIVE_ALL = "deactall";
    public static final String Action_DELETE_ALL = "delall";
    public static final String Default_FORMAT_PATTERN = "(([\\*\\+-])(0|\\d*\\.?\\d+),)*(([\\*\\+-])(0|\\d*\\.?\\d+))";

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
         
        final String suri=request.getParameter("suri");
        if(suri==null) {
            response.getWriter().println("No se detect&oacute ning&uacute;n objeto sem&aacute;ntico!");
            return;
        }
        
//        PrintWriter out = response.getWriter();
//        out.println("<script type=\"text/javascript\">");
//        if(request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
//            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
//            out.println("updateTreeNodeByURI('" + obj.getURI() + "');");
//            String icon = SWBContext.UTILS.getIconClass(obj);
//            out.println("setTabTitle('" + obj.getURI() + "','" + obj.getDisplayName(user.getLanguage()) + "','" + icon + "');");
//        }
//        if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
//            out.println("   closeTab('" + request.getParameter("closetab") + "');");
//        }
//        out.println("</script>");
       
        doEdit(request, response, paramRequest);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
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

        final String lang = user.getLanguage();
        
        boolean hasRules = Boolean.FALSE;
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject obj = ont.getSemanticObject(suri);
        Series series;
        
        PrintWriter out = response.getWriter();
        
        out.println("<script type=\"text/javascript\">");
        if (request.getParameter("statmsg") != null && request.getParameter("statmsg").trim().length() > 0) {
            out.println("   showStatus('" + request.getParameter("statmsg") + "');");
            out.println("updateTreeNodeByURI('" + obj.getURI() + "');");
            String icon = SWBContext.UTILS.getIconClass(obj);
            out.println("setTabTitle('" + obj.getURI() + "','" + obj.getDisplayName(user.getLanguage()) + "','" + icon + "');");
        }
        if (request.getParameter("closetab") != null && request.getParameter("closetab").trim().length() > 0) {
            out.println("   closeTab('" + request.getParameter("closetab") + "');");
        }
        out.println("</script>");
        
        
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setAction(SWBResourceURL.Action_ADD);
        String action = paramRequest.getAction();
        if(SWBResourceURL.Action_EDIT.equalsIgnoreCase(action))
        {
            out.println("<div class=\"swbform\">");
            out.println("<fieldset>");
            out.println("<table width=\"98%\">"); 
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>"+paramRequest.getLocaleString("lblAction")+"</th>");
            out.println("<th>"+paramRequest.getLocaleString("lblStatus")+"</th>");            
            out.println("<th>"+paramRequest.getLocaleString("lblStatusGroup")+"</th>");            
            out.println("<th>"+paramRequest.getLocaleString("lblOperation")+"</th>");            
            out.println("<th>"+paramRequest.getLocaleString("lblSeries")+"</th>");            
            out.println("<th>"+paramRequest.getLocaleString("lblFactor")+"</th>");            
            out.println("<th>"+paramRequest.getLocaleString("lblActive")+"</th>");            
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");
            
            series = (Series)obj.createGenericInstance();
            Indicator indicator = series.getIndicator();
            BSC bsc = series.getIndicator().getObjective().getTheme().getPerspective().getBSC();

            // Obtener la lista de operaciones válidas
            List<Operation> operations = SWBUtils.Collections.filterIterator(Operation.ClassMgr.listOperations(bsc),
                                    new GenericFilterRule<Operation>(){
                                        @Override
                                        public boolean filter(Operation o) {
                                            User user = SWBContext.getSessionUser();
                                            return !o.isValid() || !user.haveAccess(o);
                                        } 
                                    });

            // Otener la lista de series para contraparte
            List<Series> siblingSerieses = indicator.listValidSerieses();
            siblingSerieses.remove(series);

            // Crear el conjunto de reglas, recordemos que si 
            // una regla ya está en el conjunto no se agrega de nuevo 
            HashSet<State> configuredStates = new HashSet<State>();            
            Iterator<EvaluationRule> rules = series.listEvaluationRules();
            hasRules = rules.hasNext();
            while(rules.hasNext()) {
                EvaluationRule rule = rules.next();
                out.println("  <tr>");                
                
                // Eliminar regla
                out.println("<td>");
                SWBResourceURL urlr = paramRequest.getActionUrl();
                urlr.setParameter("suri", suri);
                urlr.setParameter("sval", rule.getURI());
                urlr.setAction(SWBResourceURL.Action_REMOVE);
                out.println("<a href=\"#\" onclick=\"if(confirm('" + paramRequest.getLocaleString("queryRemove") + " " 
                        + (rule.getTitle(lang)==null?(rule.getTitle()==null?"Sin título":rule.getTitle().replaceAll("'","")):rule.getTitle(lang).replaceAll("'","")) 
                        + "?')){submitUrl('" + urlr + "',this);} else { return false;}\"><img src=\"" + SWBPlatform.getContextPath() + "/swbadmin/images/delete.gif\" border=0></a>");
                out.println("</td>");
                
                // Estado
                SWBResourceURL urlchoose = paramRequest.getRenderUrl();
                urlchoose.setParameter("suri", suri);
                urlchoose.setParameter("sval", rule.getURI());
                out.println("   <td>");
                out.println("<a href=\"#\" onclick=\"addNewTab('" + rule.getAppraisal().getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (rule.getAppraisal().getTitle(lang)==null?(rule.getAppraisal().getTitle()==null?"Sin título":rule.getAppraisal().getTitle().replaceAll("'","")):rule.getAppraisal().getTitle(lang).replaceAll("'","")) + "');return false;\" >" + (rule.getAppraisal().getTitle(lang)==null?(rule.getAppraisal().getTitle()==null?"Sin título":rule.getAppraisal().getTitle().replaceAll("'","")):rule.getAppraisal().getTitle(lang).replaceAll("'","")) + "</a>");
                out.println("   </td>");
                
                // Grupo del estado
                out.println("   <td>");
                out.println((rule.getAppraisal().getStateGroup().getTitle(lang)==null?(rule.getAppraisal().getStateGroup().getTitle()==null?"Sin título":rule.getAppraisal().getStateGroup().getTitle().replaceAll("'","")):rule.getAppraisal().getStateGroup().getTitle(lang).replaceAll("'","")));
                out.println("   </td>");
                
                // Lista de operadores
                SWBResourceURL urlopr = paramRequest.getActionUrl();
                urlopr.setParameter("suri", suri);
                urlopr.setParameter("sval", rule.getURI());
                urlopr.setAction(Action_UPDT_OPER);
                out.println("   <td>");
                out.println("    <select name=\"operId\" onchange=\"submitUrl('" + urlopr + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\">");
                out.println("     <option value=\"\"></option>");
                for(Operation operation:operations) {
                    out.println("     <option value=\""+operation.getId()+"\"");
                    out.println(operation.getId().equals(rule.getOperationId())?" selected=\"selected\"":"");
                    out.println("      >"+(operation.getTitle(lang)==null?(operation.getTitle()==null?"Sin título":operation.getTitle().replaceAll("'","")):operation.getTitle(lang).replaceAll("'",""))+"</option>");
                }            
                out.println("    </select>");
                out.println("   </td>");
                
                // Lista de series hermanas
                SWBResourceURL urluinh = paramRequest.getActionUrl();
                urluinh.setParameter("suri", suri);
                urluinh.setParameter("sval", rule.getURI());
                urluinh.setAction(Action_UPDT_SERIES);
                out.println("   <td>");
                out.println("    <select name=\"ssId\" onchange=\"submitUrl('" + urluinh + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\">");
                out.println("     <option value=\"\"></option>");
                for(Series s:siblingSerieses) {
                    out.println("     <option value=\""+s.getId()+"\"");
                    out.println(s.equals(rule.getAnotherSeries())?" selected=\"selected\"":"");
                    out.println("      >"+(s.getTitle(lang)==null?s.getTitle():s.getTitle(lang))+"</option>");
                }
                out.println("    </select>");
                out.println("   </td>");
                
                // Factor
                SWBResourceURL urlfctr = paramRequest.getActionUrl();
                urlfctr.setParameter("suri", suri);
                urlfctr.setParameter("sval", rule.getURI());
                urlfctr.setAction(Action_UPDT_FACTOR);
                out.println("   <td><input type=\"text\" name=\"fctr\" onchange=\"submitUrl('" + urlfctr + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.TextBox\" value=\""+(rule.getFactor()==null?"":rule.getFactor())+"\" /></td>");
                
                // Activo?
                SWBResourceURL urlactv = paramRequest.getActionUrl();
                urlactv.setParameter("suri", suri);
                urlactv.setParameter("sval", rule.getURI());
                urlactv.setAction(Action_UPDT_ACTIVE);
                out.println("   <td align=\"center\"><input type=\"checkbox\" name=\"act\" onchange=\"submitUrl('" + urlactv + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.CheckBox\" value=\""+rule.getId()+"\" "+(rule.isActive()?"checked=\"checked\"":"")+" /></td>");
                
                out.println("  </tr>");
                configuredStates.add(rule.getAppraisal());
            }
            
            List<State> validSates = indicator.listValidStates();
            if(!validSates.isEmpty())
            {
                for(State state:validSates)
                {
                    if( configuredStates.add(state) ) {
                        out.println("  <tr>");
                        // Columna vacía
                        out.println("   <td>&nbsp;</td>");
                        
                        // Estado
                        out.println("   <td>");
                        out.println("<a href=\"#\"  onclick=\"addNewTab('" + state.getURI() + "','" + SWBPlatform.getContextPath() + "/swbadmin/jsp/objectTab.jsp" + "','" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle()):state.getTitle(lang)) + "');return false;\" >" + (state.getTitle(lang)==null?(state.getTitle()==null?"Sin título":state.getTitle()):state.getTitle(lang)) + "</a>");
                        out.println("   </td>");
                        
                        // Grupo del estado
                        out.println("   <td>");
                        out.println((state.getStateGroup().getTitle(lang)==null?(state.getStateGroup().getTitle()==null?"Sin título":state.getStateGroup().getTitle()):state.getStateGroup().getTitle(lang)));
                        out.println("   </td>");
                        
                        // Lista de operadores
                        SWBResourceURL urlopr = paramRequest.getActionUrl();
                        urlopr.setParameter("suri", suri);
                        urlopr.setParameter("stateId", state.getId());
                        urlopr.setAction(Action_UPDT_OPER);
                        out.println("   <td>");
                        out.println("    <select name=\"operId\" onchange=\"submitUrl('" + urlopr + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\">");
                        out.println("     <option value=\"\"></option>");
                        for(Operation operation:operations) {
                            out.println("     <option value=\""+operation.getId()+"\">"+(operation.getTitle(lang)==null?(operation.getTitle()==null?"Sin título":operation.getTitle()):operation.getTitle(lang))+"</option>");
                        }            
                        out.println("    </select>");
                        out.println("    </td>");
                        
                        // Lista de series
                        SWBResourceURL urluinh = paramRequest.getActionUrl();
                        urluinh.setParameter("suri", suri);
                        urluinh.setParameter("stateId", state.getId());
                        urluinh.setAction(Action_UPDT_SERIES);
                        out.println("   <td>");
                        out.println("    <select name=\"ssId\" onchange=\"submitUrl('" + urluinh + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.FilteringSelect\" autocomplete=\"false\" hasDownArrow=\"true\" style=\"width:120px;\">");
                        out.println("     <option value=\"\"></option>");
                        for(Series s:siblingSerieses) {
                            out.println(" <option value=\""+s.getId()+"\">"+(s.getTitle(lang)==null?(s.getTitle()==null?"Sin título":s.getTitle()):s.getTitle(lang))+"</option>");
                        }
                        out.println("    </select>");
                        out.println("   </td>");
                        
                        // Factor
                        SWBResourceURL urlfctr = paramRequest.getActionUrl();
                        urlfctr.setParameter("suri", suri);
                        urlfctr.setParameter("stateId", state.getId());
                        urlfctr.setAction(Action_UPDT_FACTOR);
                        out.println("   <td><input type=\"text\" name=\"fctr\" onchange=\"submitUrl('" + urlfctr + "&'+this.attr('name')+'='+this.attr('value'),this.domNode)\" dojoType=\"dijit.form.TextBox\" value=\"\" /></td>");
                        
                        // Columna vacía
                        out.println("   <td>&nbsp;</td>");

                        out.println("  </tr>");
                    }
                }
            }
            else
            {
                out.println("no hay estados asignados al indicador");
            }
            out.println("</tbody>");
            out.println("</table>");
            out.println("</fieldset>");
            if(hasRules)
            {
                out.println("<fieldset>");
                SWBResourceURL urlAll = paramRequest.getActionUrl();
                urlAll.setParameter("suri", suri);
                
                urlAll.setAction(Action_ACTIVE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblActiveAll") + "</button>");
                
                urlAll.setAction(Action_DEACTIVE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"submitUrl('" + urlAll + "',this.domNode); return false;\">" + paramRequest.getLocaleString("lblDeactiveAll") + "</button>");
                
                urlAll.setAction(Action_DELETE_ALL);
                out.println("<button dojoType=\"dijit.form.Button\" onclick=\"if(confirm('"+paramRequest.getLocaleString("queryRemoveAll")+"?')){submitUrl('" + urlAll + "',this.domNode);} return false;\">" + paramRequest.getLocaleString("lblRemoveAll") + "</button>");
                out.println("</fieldset>");
            }
            out.println("</div>");
        }
        
    }
    
    /*
     * Valida el formato del factor del criterio de evaluación
     * @param factor un {@code String} que indica los factores para comparar las series. Por ejemplo: *0.3,*05
     * @return Si el factor cumple con el formato entonces cierto, de lo contrario falso.
     */
    private boolean validateFactor(String factor) {
        Pattern pattern;
        String regexp = getResourceBase().getAttribute("defaultFormatPattern", Default_FORMAT_PATTERN);
        try{
            pattern = Pattern.compile(regexp);
        }catch(PatternSyntaxException pse) {
            pattern = Pattern.compile(Default_FORMAT_PATTERN);
        }            
        Matcher matcher = pattern.matcher(factor);
        return matcher.matches();
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        final String action = response.getAction();
        final String suri = request.getParameter("suri");
        
        response.setAction(SWBResourceURL.Action_EDIT);
        response.setRenderParameter("suri", suri);
        
        SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
        SemanticObject objSeries = ont.getSemanticObject(suri);
        if(objSeries==null) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            return;
        }
        
        User user = response.getUser();
        if(!user.isSigned() || !user.haveAccess(objSeries.getGenericInstance())) {
            response.setRenderParameter("statmsg", response.getLocaleString("msgUnauthorizedUser"));
            return;
        }

        if(Action_UPDT_OPER.equalsIgnoreCase(action))
        {
            SemanticObject objRule = ont.getSemanticObject(request.getParameter("sval"));
            SWBModel model = (SWBModel)objSeries.getModel().getModelObject().createGenericInstance();
            Series series = (Series)objSeries.getGenericInstance();
            String operId = request.getParameter("operId");
            EvaluationRule rule;

            if(Operation.ClassMgr.hasOperation(operId, model)) {
                if(objRule==null) {
                    rule = EvaluationRule.ClassMgr.createEvaluationRule(model);
                    rule.setTitle(rule.getId());
                    rule.setTitle(rule.getId(), user.getLanguage());
                    if(State.ClassMgr.hasState(request.getParameter("stateId"), SWBContext.getAdminWebSite())) {
                        State state = State.ClassMgr.getState(request.getParameter("stateId"), SWBContext.getAdminWebSite());
                        rule.setAppraisal(state);
                    }
                    series.addEvaluationRule(rule);
                }else {
                    rule = (EvaluationRule)objRule.getGenericInstance();
                }
                rule.setOperationId(operId);
                response.setRenderParameter("statmsg", response.getLocaleString("msgUpdtOperatorOk"));
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchOperation"));
            }
        }
        else if(Action_UPDT_SERIES.equalsIgnoreCase(action))
        {
            SemanticObject objRule = ont.getSemanticObject(request.getParameter("sval"));
            SWBModel model = (SWBModel)objSeries.getModel().getModelObject().createGenericInstance();
            Series series = (Series)objSeries.getGenericInstance();
            String siblingId = request.getParameter("ssId");
            EvaluationRule rule;
            
            if(Series.ClassMgr.hasSeries(siblingId, model)) {
                Series sibling = Series.ClassMgr.getSeries(siblingId, model);
                if(series.getIndicator().hasSeries(sibling))
                {
                    if(objRule==null) {
                        rule = EvaluationRule.ClassMgr.createEvaluationRule(model);
                        rule.setTitle(rule.getId());
                        rule.setTitle(rule.getId(), user.getLanguage());
                        if(State.ClassMgr.hasState(request.getParameter("stateId"), SWBContext.getAdminWebSite())) {
                            State state = State.ClassMgr.getState(request.getParameter("stateId"), SWBContext.getAdminWebSite());
                            rule.setAppraisal(state);
                        }
                        series.addEvaluationRule(rule);
                    }else {
                        rule = (EvaluationRule)objRule.getGenericInstance();
                    }
                    rule.setAnotherSeries(sibling);
                    response.setRenderParameter("statmsg", response.getLocaleString("msgUpdtSeriesOk"));
                }
                else
                {
                    response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSeries"));
                }
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSeries"));
            }
        }
        else if(Action_UPDT_FACTOR.equalsIgnoreCase(action))
        {
            SemanticObject objRule = ont.getSemanticObject(request.getParameter("sval"));
            SWBModel model = (SWBModel)objSeries.getModel().getModelObject().createGenericInstance();
            Series series = (Series)objSeries.getGenericInstance();
            String factor = request.getParameter("fctr")==null?"":request.getParameter("fctr");
            EvaluationRule rule;
            
            if(validateFactor(factor)) {
                if(objRule==null) {
                    rule = EvaluationRule.ClassMgr.createEvaluationRule(model);
                    rule.setTitle(rule.getId());
                    rule.setTitle(rule.getId(), user.getLanguage());
                    if(State.ClassMgr.hasState(request.getParameter("stateId"), SWBContext.getAdminWebSite())) {
                        State state = State.ClassMgr.getState(request.getParameter("stateId"), SWBContext.getAdminWebSite());
                        rule.setAppraisal(state);
                    }
                    series.addEvaluationRule(rule);
                }else {
                    rule = (EvaluationRule)objRule.getGenericInstance();
                }
                rule.setFactor(factor);
                response.setRenderParameter("statmsg", response.getLocaleString("msgUpdtFactorOk"));
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgFactorBadFormat"));
            }
        }
        else if(Action_UPDT_ACTIVE.equalsIgnoreCase(action))
        {
            SemanticObject objRule = ont.getSemanticObject(request.getParameter("sval"));
            if(objSeries!=null && objRule!=null) {
                EvaluationRule rule = (EvaluationRule)objRule.getGenericInstance();
                Series series = (Series)objSeries.getGenericInstance();
                if(series.hasEvaluationRule(rule)) {
                    rule.setActive(!rule.isActive());                    
                    response.setRenderParameter("statmsg", (rule.isActive()?response.getLocaleString("msgUpdtActiveOk"):response.getLocaleString("msgUpdtDeactiveOk")));
                }else {
                    response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveError"));
                }
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            }
        }
        else if(Action_ACTIVE_ALL.equalsIgnoreCase(action))
        {
            if(objSeries!=null) {
                Series series = (Series)objSeries.getGenericInstance();                
                Iterator<EvaluationRule> rules = series.listEvaluationRules();
                while(rules.hasNext()) {
                    rules.next().setActive(Boolean.TRUE);
                }
            }
        }
        else if(Action_DEACTIVE_ALL.equalsIgnoreCase(action))
        {
            if(objSeries!=null) {
                Series series = (Series)objSeries.getGenericInstance();                
                Iterator<EvaluationRule> rules = series.listEvaluationRules();
                while(rules.hasNext()) {
                    rules.next().setActive(Boolean.FALSE);
                }
            }
        }
        else if(SWBResourceURL.Action_REMOVE.equalsIgnoreCase(action))
        {
            SemanticObject objRule = ont.getSemanticObject(request.getParameter("sval"));
            if(objSeries!=null && objRule!=null) {
                EvaluationRule rule = (EvaluationRule)objRule.getGenericInstance();
                Series series = (Series)objSeries.getGenericInstance();
                if(series.hasEvaluationRule(rule)) {
                    series.removeEvaluationRule(rule);
                    response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveOk"));
                }else {
                    response.setRenderParameter("statmsg", response.getLocaleString("msgRemoveError"));
                }
            }else {
                response.setRenderParameter("statmsg", response.getLocaleString("msgNoSuchSemanticElement"));
            }
        }
        else if(Action_DELETE_ALL.equalsIgnoreCase(action))
        {
            if(objSeries!=null) {
                Series series = (Series)objSeries.getGenericInstance();                
                series.removeAllEvaluationRule();
            }
        }
    }
}
