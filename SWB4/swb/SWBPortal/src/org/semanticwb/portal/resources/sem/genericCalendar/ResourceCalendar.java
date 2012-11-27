/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.portal.resources.sem.genericCalendar;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class ResourceCalendar extends org.semanticwb.portal.resources.sem.genericCalendar.base.ResourceCalendarBase 
{

    Logger log = SWBUtils.getLogger(ResourceCalendar.class);

    public ResourceCalendar()
    {
    }

   /**
   * Constructs a ResourceCalendar with a SemanticObject
   * @param base The SemanticObject with the properties for the ResourceCalendar
   */
    public ResourceCalendar(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/genericCalendar/viewGralCalendar.jsp";
        ResourceCalendar cal = (ResourceCalendar) getSemanticObject().createGenericInstance();
        if(cal.getJspView() != null) {
            if(cal.getJspView().indexOf(".jsp") > 0) {
                path = cal.getJspView();
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }
    }

    public void doStrategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/genericCalendar/listEvents.jsp";
        ResourceCalendar cal = (ResourceCalendar) getSemanticObject().createGenericInstance();
        if(cal.getJspStrategy() != null ) {
            if(cal.getJspStrategy().lastIndexOf(".jsp") > 0) {
                path = cal.getJspStrategy();
            }
        }
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY){
            doStrategy(request, response, paramRequest);
        } else if("change".equals(mode)) {
            doChangeCalendar(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }



    public void doChangeCalendar(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        ResourceCalendar cal = (ResourceCalendar)getSemanticObject().createGenericInstance();
        String value = request.getParameter("month");
        int mont = 0, year=0;
        HashMap eventMonth = new HashMap();
        try {
            mont = Integer.parseInt(value);
        } catch(NumberFormatException e) {
            log.error("Error while convert month in Calendar: " + e);
        }
        value = request.getParameter("year");
        try {
            year = Integer.parseInt(value);
        } catch(NumberFormatException e) {
            log.error("Error while convert year in Calendar: " + e);
        }

        if(mont > 0 && year > 0) {
            Iterator istEvts = listEvtTypes();
            Iterator ist = null;
            ArrayList allEvts = new ArrayList();
            if(istEvts.hasNext()) {
                while(istEvts.hasNext()) {
                    EventType type =  (EventType)istEvts.next();
                    ist = Event.ClassMgr.listEventByEvType(type, paramRequest.getWebPage().getWebSite());
                    while(ist.hasNext()) {
                        allEvts.add(ist.next());
                    }
                }
                if(!allEvts.isEmpty()) {
                    ist = allEvts.iterator();
                }
            } else {
                ist = Event.ClassMgr.listEvents(paramRequest.getWebPage().getWebSite());
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while(ist.hasNext()) {
                Event ev = (Event)ist.next();
                try {
                    if(ev.getEventInitDate() != null) {
                        int valueMonth=sdf.parse(ev.getEventInitDate()).getMonth()+1;
                        int valueYear = sdf.parse(ev.getEventInitDate()).getYear();
                        int year1 = (valueYear< 1000) ? valueYear + 1900 : valueYear;
                        int valueDay = sdf.parse(ev.getEventInitDate()).getDate();

                        boolean isYearly = (mont == valueMonth && ev.getPeriodicity().equals("yearly")) ? true : false;
                        int monthCurrent = java.util.Calendar.getInstance().MONTH + 1;
                        boolean isMonthly = (ev.getPeriodicity() != null && ev.getPeriodicity().equals("monthly")) && valueDay <= lastDayOfMonth(monthCurrent, year) ? true : false;//java.util.Calendar.getInstance().DAY_OF_MONTH == valueMonth ? true : false;
                        boolean isWeekly = (ev.getPeriodicity() != null && ev.getPeriodicity().equals("weekly")) ? true : false;
                        int valMonthEndEvt, valYearEndEvt, year2;
                        valMonthEndEvt = valYearEndEvt = year2 = 0;
                        if(ev.getEventEndDate() != null ) {
                            valMonthEndEvt = sdf.parse(ev.getEventEndDate()).getMonth()+1;
                        }
                        if(ev.getEventEndDate() != null) {
                            valYearEndEvt = sdf.parse(ev.getEventEndDate()).getYear();
                            year2 = (valYearEndEvt < 1000) ? valYearEndEvt + 1900 : valYearEndEvt;
                        }

                        if(((mont == valueMonth) && (year == year1)) || (isYearly && year >= year2) || (isMonthly && year >= year2 ) || (isWeekly && year >= year2)) {
                            ArrayList listEvents = new ArrayList();
                            if((!isWeekly && year <= year2 && mont <= valMonthEndEvt && mont >= valueMonth && year >= year1) || (!isWeekly && mont >= valueMonth && year >= year1 && ev.getEventEndDate() == null)) {
                                if(eventMonth.containsKey(valueDay)) {
                                    listEvents = (ArrayList)eventMonth.get(valueDay);
                                    listEvents.add(ev);
                                    eventMonth.remove(valueDay);
                                } else {
                                    listEvents.add(ev);
                                }
                                eventMonth.put(valueDay, listEvents);
                            } else if((year <= year2 && mont <= valMonthEndEvt && mont >= valueMonth) || (mont >= valueMonth && year >= year1 && ev.getEventEndDate() == null)) {
                                ArrayList week = getWeekly(ev, mont-1, year);
                                Iterator it = week.iterator();
                                while(it.hasNext()) {
                                    int day = Integer.parseInt(it.next().toString());
                                    if(eventMonth.containsKey(day)) {
                                        listEvents = (ArrayList)eventMonth.get(day);
                                        listEvents.add(ev);
                                        eventMonth.remove(day);
                                    } else {
                                        listEvents.add(ev);
                                    }
                                    eventMonth.put(day, listEvents);
                                }
                            }

                        }
                    }
                } catch(Exception e) {
                    log.error("Error while process events in Calendar" + e);
                }
            }
        }

        JSONObject objEventMonth = new JSONObject();
        Iterator it = eventMonth.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry e = (Map.Entry)it.next();
            String day = e.getKey().toString();
            ArrayList eventos = (ArrayList)e.getValue();
            try {
                JSONObject getEv = getEvents(eventos, paramRequest, cal);
                objEventMonth.put(day, getEv);
            } catch(Exception e1) {
                log.error("Error while build the events for day: " + e1);
            }
        }
        out.print(objEventMonth);
    }

    private int lastDayOfMonth(int month,int year) {
        int day = 0;
        switch(month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                day = 30;
                break;
            case 2:
                if((year % 4 == 0)||((year % 100 == 0) && (year % 400 == 0))) {
                    day = 29;
                } else {
                    day = 28;
                }
        }
        return day;
    }

    private JSONObject getEvents(ArrayList Events,SWBParamRequest paramRequest, ResourceCalendar cal)
    {
        JSONObject objJSONEvents = new JSONObject();
        Iterator it = Events.iterator();
        while(it.hasNext()) {
            Event event = (Event)it.next();
            if(paramRequest.getUser().haveAccess(event))
            {
                try {
                    objJSONEvents.put(event.getTitle(paramRequest.getUser().getLanguage())==null?event.getTitle():event.getTitle(paramRequest.getUser().getLanguage()), getData(event, paramRequest, cal)); //SWBUtils.TEXT.encode(event.getTitle(),SWBUtils.TEXT.CHARSET_UTF8)
                }catch(Exception e) {
                    log.error("Error while build properties in Events: "+e);
                }
            }
        }
        return objJSONEvents;
    }

    private ArrayList getWeekly(Event event, int month, int year){
        ArrayList daysWeekly = new ArrayList();
        int dayInEvt, dayInMonth = 0;
        if(event.getPeriodicity().equals("weekly")) {
            try {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                dayInEvt =  sdf1.parse(event.getEventInitDate()).getDay();
                int daysMonthSel = lastDayOfMonth(month + 1, year);
                Date date = new Date();
                date.setMonth(month);
                date.setDate(1);
                date.setYear(year);
                dayInMonth =  date.getDay();
                int yearInEvt = sdf1.parse(event.getEventInitDate()).getYear() + 1900;

                if((month >= sdf1.parse(event.getEventInitDate()).getMonth() && yearInEvt == year) || (year > yearInEvt)) {
                    for(int i = 1; i <= daysMonthSel; i++) {
                        Date date1 = new Date();
                        date1.setDate(i);
                        date1.setMonth(month);
                        date1.setYear(year-1900);
                        if((month == sdf1.parse(event.getEventInitDate()).getMonth() && date1.getDate() >= sdf1.parse(event.getEventInitDate()).getDate() && date1.getDay() == dayInEvt) || (month > sdf1.parse(event.getEventInitDate()).getMonth() && date1.getDay() == dayInEvt)) {
                            if(event.getEventEndDate() != null) {
                                if((month == sdf1.parse(event.getEventEndDate()).getMonth() && date1.getDate() <= sdf1.parse(event.getEventEndDate()).getDate()) || month < sdf1.parse(event.getEventEndDate()).getMonth()) {
                                    daysWeekly.add(i);
                                    i = i + 5;
                                }
                            } else {
                                daysWeekly.add(i);
                            }
                        }
                    }
                }
            } catch(Exception e) {
                log.error("Error in getWeekly: " + e);
            }
        }
        return daysWeekly;
    }

    private JSONObject getData(Event event,SWBParamRequest paramRequest, ResourceCalendar cal)
    {
        JSONObject objJSONData = new JSONObject();
        String url, photo, target;
        photo = url= target = "";
        if(event.getUrlExternal() != null) {
            url = event.getUrlExternal();
        } else if(event.isUrlInternal()) {
            WebPage wp;
            if(cal.getIdPage() != null) {
                wp = paramRequest.getWebPage().getWebSite().getWebPage(cal.getIdPage());
                if(wp != null && wp.isActive()) {
                   url = wp.getUrl() + "?id=" + event.getId();
                }
            }
        }
        if(event.isNewWindow()) {
            target = "_black";
        }
        if(event.getTooltipImage() != null && event.getTooltipImage().trim().length() > 0) {
             photo= SWBPortal.getWebWorkPath() + event.getWorkPath() + "/" + Event.genCal_tooltipImage.getName() +
                     "_" + event.getId() + "_" +event.getTooltipImage();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");

        if(event.getPeriodicity().equals("yearly")) {
            sd = new SimpleDateFormat("dd/MM");
        }
        String dates = "";
        if(!event.getPeriodicity().equals("weekly") && !event.getPeriodicity().equals("monthly")) {
           if(event.getEventInitDate() != null && event.getEventInitDate().length() > 1) {
                try{
                    Date ds = sdf.parse(event.getEventInitDate());
                    dates = dates + sd.format(ds);
                }catch(Exception e)
                {
                }
            }

            if(event.getEventEndDate() != null && event.getEventEndDate().length() > 1) {
                try {
                    Date de = sdf.parse(event.getEventEndDate());
                    if(dates.length()>1) {
                        dates = dates + " - ";
                    }
                    dates = dates +sd.format(de);
                } catch(Exception e) {
                }
            }
        }

        try {
            objJSONData.put("target", target);
            objJSONData.put("url", url);
            objJSONData.put("title", event.getTitle(paramRequest.getUser().getLanguage())==null?event.getTitle():event.getTitle(paramRequest.getUser().getLanguage()));
            objJSONData.put("image", photo);
            String descr = "";
            if(event.getDescription(paramRequest.getUser().getLanguage()) != null) {
                descr = event.getDescription(paramRequest.getUser().getLanguage());
            } else if(event.getDescription() != null) {
                descr = event.getDescription();
            }
            objJSONData.put("description", descr);
            objJSONData.put("rdates", dates);
        } catch(Exception e) {
            log.error("Error while add the properties to Events: " + e);
        }
        return objJSONData;
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        User user = response.getUser();
        
        if(action.equals("saveSubCal")) {
            ResourceCalendar cal = (ResourceCalendar)getSemanticObject().createGenericInstance();

            if(request.getParameter("uriCancel") != null) {
                String uri = URLDecoder.decode(request.getParameter("uriCancel"));
                SemanticObject obj = SemanticObject.createSemanticObject(uri);
                CalendarSubscription subscription = (CalendarSubscription) obj.createGenericInstance();
                subscription.remove();
            } else if(user.isSigned() && user.haveAccess(cal)) {
                CalendarSubscription subs = CalendarSubscription.ClassMgr.createCalendarSubscription(response.getWebPage().getWebSite());
                subs.setCalendarSubscription(cal);
                subs.setUserCalendarSubscription(user);
            }

            response.setMode(SWBResourceURL.Mode_VIEW);
        }
    }

}
