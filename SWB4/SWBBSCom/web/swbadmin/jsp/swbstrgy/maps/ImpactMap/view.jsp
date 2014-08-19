

<%@page import="java.io.IOException"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.NoSuchElementException"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpServletResponse"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@page import="javax.xml.xpath.XPath"%>
<%@page import="javax.xml.xpath.XPathConstants"%>
<%@page import="javax.xml.xpath.XPathExpressionException"%>
<%@page import="javax.xml.xpath.XPathFactory"%>

<%@page import="org.semanticwb.Logger"%>
<%@page import="org.semanticwb.SWBException"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.SWBUtils"%>

<%@page import="org.semanticwb.bsc.BSC"%>
<%@page import="org.semanticwb.bsc.Theme"%>
<%@page import="org.semanticwb.bsc.Perspective"%> 
<%@page import="org.semanticwb.bsc.accessory.Period"%>
<%@page import="org.semanticwb.bsc.accessory.StateGroup"%>
<%@page import="org.semanticwb.bsc.accessory.State"%>
<%@page import="org.semanticwb.bsc.element.Objective"%>
<%@page import="org.semanticwb.bsc.element.Initiative"%>
<%@page import="org.semanticwb.bsc.resources.maps.ImpactMap"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="org.semanticwb.model.SWBContext"%>
<%@page import="org.semanticwb.model.User"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.WebSite"%>
<%@page import="org.semanticwb.portal.api.GenericResource"%>
<%@page import="org.semanticwb.portal.api.SWBActionResponse"%>
<%@page import="org.semanticwb.portal.api.SWBResourceException"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>


<%@page import="static org.semanticwb.bsc.PDFExportable.Mode_StreamPDF"%>
<%@page import="static org.semanticwb.bsc.PDFExportable.Mode_StreamPNG"%>
<%--@page import="static com.infotec.cvi.swb.resources.AreasTalentoResource.Mode_TLNT"--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="paramRequest" scope="request" type="org.semanticwb.portal.api.SWBParamRequest" />
<%--jsp:useBean id="this" scope="request" type="org.semanticwb.bsc.resources.maps.ImpactMap" /--%>
<%
    //ImpactMap this_ = (ImpactMap)request.getAttribute("this_");
    //Resource base = this_.getResourceBase();
    //final BSC scorecard = (BSC)base.getWebSite();
    final BSC scorecard = (BSC)paramRequest.getWebPage().getWebSite();
    Period period;
    Object periodId = request.getSession(true).getAttribute(scorecard.getId());
    if(periodId!=null && Period.ClassMgr.hasPeriod(periodId.toString(), scorecard)) {
        period = Period.ClassMgr.getPeriod(periodId.toString(), scorecard);
    }else {
        out.print("<p>No hay periodo definido.</p>");
        out.flush();
        out.close();
        return;
    }

    User user = paramRequest.getUser();
    String lang = user.getLanguage();
    String bundle = getClass().getName();
    Locale locale = new Locale(lang);
    
    StringBuilder table = new StringBuilder();
    
    List<Initiative> initiatives = scorecard.listValidInitiatives();
    
    State minimum;
    StateGroup sg;
    try {
        minimum = initiatives.get(0).getMinimumState();
        sg = minimum.getStateGroup();
    }catch(Exception e) {
        out.print("<p>No hay iniciativas definidas o algunas iniciativas no tienen estados asignados</p>");
        out.flush();
        out.close();
        return;
    }
    List<State>states = sg.listValidStates();
    out.append("<table width=\"200\">");
    for(State state:states) {
        out.append("<tr>");
        out.append("<td style=\"background-color:").append(state.getColorHex()).append("\">").append("&nbsp;</td>");
        out.append("<td>").append(state.getDisplayTitle(lang)==null?state.getTitle():state.getDisplayTitle(lang)).append("</td>");
        out.append("</tr>");
    }
    out.append("<tr>");
    out.append("<td style=\"background-color:#FFFFFF\">&nbsp;</td>");
    out.append("<td>Sin impacto</td>");
    out.append("</tr>");
    out.append("</table>");
    
    
    
    table.append("<h2>Análisis del impacto de las iniciativas en los objetivos estratégicos</h2>").append("\n");
    table.append("<table border=\"1\">").append("\n");
    table.append("<tr>").append("\n");
    table.append("<td></td>").append("\n");//perspectiva
    table.append("<td></td>").append("\n");//objetivo
    for(Initiative initiative:initiatives) {
        table.append("<td class=\"swbstgy-imap-initheader\">");
        table.append(initiative.getTitle());
        table.append("</td>").append("\n");
    }
    table.append("</tr>").append("\n");
    
    Objective obj;
    List<Perspective> perspectives = scorecard.listValidPerspectives();
    Collections.sort(perspectives);
    for(Perspective p:perspectives) {
        String pTitle = p.getDisplayTitle(lang)==null?(p.getTitle()==null?"Desconocido":p.getTitle().replaceAll("['\n]", "")):p.getDisplayTitle(lang).replaceAll("['\n]", "");
        
        
        List<Objective> objectives = p.listValidObjectives(period);
        int rowspan = objectives.size();
        Iterator<Objective>it = objectives.iterator();
        if(!it.hasNext()) {
            continue;
        }
        obj = it.next();
        table.append("<tr class=\"swbstgy-imap-p").append(p.getId()).append("\">").append("\n");
        table.append("<td rowspan=\"").append(rowspan).append("\" class=\"swbstgy-imap-ptitle\">");
        table.append(pTitle);
        table.append("</td>").append("\n");
        table.append("<td class=\"swbstgy-imap-otitle\">");
        table.append(obj.getDisplayTitle(lang)==null?obj.getTitle():obj.getDisplayTitle(lang));
        table.append("</td>").append("\n");
        for(Initiative initiative:initiatives) {
            if(obj.hasInitiative(initiative)) {
                table.append("<td style=\"background-color:");
                try {
                    table.append(initiative.getPeriodStatus(period).getStatus().getColorHex());
                }catch(Exception e) {
                    table.append("#FFFFFF");
                }
                table.append("\">");
                table.append("&nbsp;");
                table.append("</td>");
            }else {
                table.append("<td style=\"background-color:#FFFFFF\">&nbsp;</td>");
            }
            table.append("\n");
        }
        table.append("</tr>").append("\n");
        
        while(it.hasNext()) {
            obj = it.next();
            table.append("<tr class=\"swbstgy-imap-p").append(p.getId()).append("\">").append("\n");
            table.append("<td class=\"swbstgy-imap-otitle\">");
            table.append(obj.getDisplayTitle(lang)==null?obj.getTitle():obj.getDisplayTitle(lang));
            table.append("</td>").append("\n");
            for(Initiative initiative:initiatives) {
                if(obj.hasInitiative(initiative)) {
                    table.append("<td style=\"background-color:");
                    try {
                        table.append(initiative.getPeriodStatus(period).getStatus().getColorHex());
                    }catch(Exception e) {
                        table.append("#FFFFFF");
                    }
                    table.append("\">");
                    table.append("&nbsp;");
                    table.append("</td>");
                }else {
                    table.append("<td style=\"background-color:#FFFFFF\">&nbsp;</td>");
                }
                table.append("\n");
            }
            table.append("</tr>").append("\n");
        }
        
         
        
        
        /*List<Theme> themes = p.listValidThemes();
        Collections.sort(themes);
        for(Theme t:themes) {
            List<Objective> objectives = t.listValidObjectives();
            Collections.sort(objectives);
            for(Objective obj:objectives) {
                table.append("<tr class=\"swbstgy-imap-p").append(p.getId()).append("\">").append("\n");
                table.append("<td class=\"swbstgy-imap-ptitle\">");
                table.append(title);
                table.append("</td>").append("\n");
                
                
                table.append("<td class=\"swbstgy-imap-otitle\">");
                table.append(obj.getTitle());
                table.append("</td>").append("\n");
                for(Initiative initiative:initiatives) {
                    if(obj.hasInitiative(initiative)) {
                        table.append("<td style=\"background-color:");
                        try {
                            table.append(initiative.getPeriodStatus(period).getStatus().getColorHex());
                        }catch(Exception e) {
                            table.append("#FFFFFF");
                        }
                        table.append("\">");
                        table.append("&nbsp;");
                        table.append("</td>");
                    }else {
                        table.append("<td style=\"background-color:#FFFFFF\">&nbsp;</td>");
                    }
                    table.append("\n");
                }
                table.append("</tr>").append("\n");
            }
        }*/
    }
    table.append("</table>");
    out.print(table.toString());
%><p>&nbsp;</p>