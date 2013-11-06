package org.semanticwb.bsc.admin.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.bsc.BSC;
import org.semanticwb.bsc.accessory.Period;
import org.semanticwb.bsc.element.Indicator;
import org.semanticwb.bsc.element.Objective;
import org.semanticwb.bsc.tracing.Measure;
import org.semanticwb.bsc.tracing.PeriodStatus;
import org.semanticwb.bsc.tracing.Series;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;

/**
 *
 * @author carlos.ramos
 */
public class DataTableProto extends GenericAdmResource {
    public static final String Mode_CHANGE = "chng";
    public static final String Mode_DATA = "dt";
    
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        
    }
    

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        final String mode = paramRequest.getMode();
System.out.println("");
System.out.println("mode="+mode);
        if(Mode_CHANGE.equals(mode)) {
            doChangeIndicator(request, response, paramRequest);
        }else if(Mode_DATA.equals(mode)) {
            doViewDataTable(request, response, paramRequest);
        }else {
            super.processRequest(request, response, paramRequest);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        
        
        PrintWriter out = response.getWriter();
        String modelId = "DAC";
        WebSite ws = WebSite.ClassMgr.getWebSite(modelId);
        if(ws==null) {
            out.println("sitio "+modelId+" es nulo");
            return;
        }
        if(!(ws instanceof BSC)) {
            out.println("sitio "+modelId+" no es un BSC");
            return;
        }
        BSC bsc = (BSC)ws;
        
        SWBResourceURL url = paramRequest.getRenderUrl();
        
        String objId = request.getParameter("obj");
        Objective myObj;
        if(Objective.ClassMgr.hasObjective(objId, bsc)) {
            myObj = Objective.ClassMgr.getObjective(objId, bsc);
        }else {
            myObj = null;
        }
        Iterator<Objective> objcs = bsc.listObjectives();
        if(objcs.hasNext()) {
            out.println("<select name=\"obj\" onchange=\"postHtml('"+url.setMode(Mode_CHANGE)+"'+'?obj='+this.value,'sind');\">");
            out.println("<option></option>");
            while(objcs.hasNext()) {
                Objective obj = objcs.next();
                out.print("<option value=\""+obj.getId()+"\"");
                out.print(obj.equals(myObj)?" selected=\"selected\"":"");
                out.print(">");
                out.println(obj.getTitle()+"</option>");
            }
            out.println("</select>");
        }else {
            out.println("sitio "+modelId+" no tiene objetivos directos");
        }
        
        if(myObj!=null) {
            String indId = request.getParameter("ind");
            Indicator myInd;
            if(Indicator.ClassMgr.hasIndicator(indId, bsc)) {
                myInd = Indicator.ClassMgr.getIndicator(indId, bsc);
            }else {
                myInd = null;
            }
            Iterator<Indicator> inds = myObj.listIndicators();
            if(inds.hasNext()) {
                out.println("<div id=\"sind\">");
                out.println("<select name=\"ind\" onchange=\"postHtml('"+url.setMode(Mode_DATA)+"'+'?ind='+this.value,'datatable');\">");
                out.println("<option></option>");
                while(inds.hasNext()) {
                    Indicator ind = inds.next();
                    out.print("<option value=\""+ind.getId()+"\"");
                    out.print(ind.equals(myInd)?" selected=\"selected\"":"");
                    out.print(">");
                    out.println(ind.getTitle()+"</option>");
                }
                out.println("</select>");
                out.println("</div>");
            }else {
                out.println("objetivo "+myObj+" no tiene indicadores directos");
            }
        }else {
            out.println("<div id=\"sind\">");
            out.println("<select name=\"ind\">");
            out.println("<option></option>");
            out.println("</select>");
            out.println("</div>");
        }
        
        out.println("<div id=\"datatable\">");
        out.println("</div>");
    }
    
    public void doChangeIndicator(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        PrintWriter out = response.getWriter();
        
        WebSite bsc = getResourceBase().getWebSite();
        
        SWBResourceURL url = paramRequest.getRenderUrl();
        
System.out.println("objId="+request.getParameter("obj"));
        String objId = request.getParameter("obj");
        Objective myObj;
        if(Objective.ClassMgr.hasObjective(objId, bsc)) {
            myObj = Objective.ClassMgr.getObjective(objId, bsc);
            bsc = myObj.getBSC();
        }else {
            myObj = null;
        }
System.out.println("myObj="+myObj);
        if(myObj!=null && bsc!=null) {
            Iterator<Indicator> inds = myObj.listIndicators();
System.out.println("inds="+inds);
            if(inds.hasNext()) {
                out.println("<select name=\"ind\" onchange=\"getHtml('"+url.setMode(Mode_DATA)+"'+'?ind='+this.value,'datatable');\">");
                out.println("<option></option>");
                while(inds.hasNext()) {
                    Indicator ind = inds.next();
                    out.print("<option value=\""+ind.getId()+"\"");
//                    out.print(ind.equals(myInd)?" selected=\"selected\"":"");
                    out.print(">");
                    out.println(ind.getTitle()+"</option>");
                }
                out.println("</select>");
            }else {
                out.println("objetivo "+myObj+" no tiene indicadores directos");
            }
        }else {
            out.println("no hay indicadores");
        }
        out.flush();
        out.close();
    }
    
    public void doViewDataTable(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html;charset=iso-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        
        PrintWriter out = response.getWriter();
        
        WebSite bsc = getResourceBase().getWebSite();
        String indId = request.getParameter("ind");
        Indicator myInd;
        if(Indicator.ClassMgr.hasIndicator(indId, bsc)) {
            myInd = Indicator.ClassMgr.getIndicator(indId, bsc);
            bsc = myInd.getBSC();
        }else {
            myInd = null;
        }

        if(myInd!=null && bsc!=null) {
            out.println("<table border=\"1\">");
            out.println("  <caption>tabla de datos</caption>");
            try
            {
                Iterator<Period> measurablesPeriods = myInd.listMeasurablesPeriods(false);
                if(measurablesPeriods.hasNext())
                {
    //                List<Series> serieses = SWBUtils.Collections.copyIterator(myInd.listSerieses());
                    List<Series> serieses = myInd.listValidSerieses();
                    if(!serieses.isEmpty())
                    {
                        final User user = paramRequest.getUser();
                        Collections.sort(serieses);

                        out.println("<tr>");
                        out.println("  <th>período</th>");
                        out.println("  <th>estado</th>");
                        Iterator<Series> it = serieses.iterator();
                        while(it.hasNext()) {
                            Series s = it.next();
    //                        if(!s.isValid() || !user.haveAccess(s)) {
    //                            it.remove();
    //                            continue;
    //                        }
                            out.println("  <th title=\""+s.getDescription()+"\">"+s.getTitle()+"</th>");
                        }

                        out.println("</tr>");

                        Period period;
                        Measure measure;
                        Series s;
                        String value;
                        Series star = myInd.getStar();
                        if(star!=null)
                        {
                            while(measurablesPeriods.hasNext())
                            {
                                out.println("<tr>");
                                period = measurablesPeriods.next();
                                out.println("<td>"+period.getTitle()+"</td>");
                                measure = star.getMeasure(period);
                                try {
                                    value = "<td>"+measure.getEvaluation().getStatus().getTitle()+"</td>";
                                }catch(Exception e) {
                                    value = "<td>-----</td>";
                                }
                                out.println(value);

                                for(int i=0; i<serieses.size(); i++)
                                {
                                    s = serieses.get(i);
                                    measure = s.getMeasure(period);
    //                                value = measure==null?"--":formatters[i].format(measure.getValue());
                                    value = measure==null?"--":s.getFormatter().format(measure.getValue());
                                    if(s.isReadOnly()) {
                                        out.println("<td>"+value+"</td>");
                                    }else {
                                        if(measure==null) {
    System.out.println("la medicion para la serie "+s+", es nula, se creara una nueva...");
                                            measure = Measure.ClassMgr.createMeasure(bsc);
                                            PeriodStatus ps = PeriodStatus.ClassMgr.createPeriodStatus(bsc);
                                            ps.setPeriod(period);
                                            measure.setEvaluation(ps);
                                            s.addMeasure(measure);
                                        }
                                        out.println("<td><input type=\"text\" name=\""+myInd.getId()+"_"+s.getId()+"_"+measure.getId()+"\" value=\""+value+"\" /></td>");
                                    }
                                }
                                out.println("</tr>");
                            }
                        }else {
                            out.println("no hay STAR definida");
                        }
                    }
                }else {
                    out.println("no hay datos");
                }
        }
        catch(Exception e)
        {
            out.println("hay problemas para mostrar los datos");
        }
            out.println("</table>");
        }else {
            out.println("no hay indicadores");
        }
        out.flush();
        out.close();
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        super.processAction(request, response);
    }
}
