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
package org.semanticwb.portal.resources.sem.calendar;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.apache.commons.fileupload.FileItem;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.base.util.ImageResizer;
import org.semanticwb.base.util.SWBMail;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;
import org.semanticwb.servlet.internal.UploadFormElement;

public class Calendar extends org.semanticwb.portal.resources.sem.calendar.base.CalendarBase 
{
    Logger log = SWBUtils.getLogger(Calendar.class);

    public Calendar()
    {
    }

   /**
   * Constructs a Calendar with a SemanticObject
   * @param base The SemanticObject with the properties for the Calendar
   */
    public Calendar(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        String path = "/swbadmin/jsp/calendar/viewGralCalendar.jsp";
        org.semanticwb.portal.resources.sem.calendar.Calendar cal = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();
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

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if(paramRequest.getCallMethod() == SWBParamRequest.Call_STRATEGY){
            doStrategy(request, response, paramRequest);
        } else if("change".equals(mode)) {
            doChangeCalendar(request, response, paramRequest);
        } else if("add".equals(mode)) {
            doEvent(request, response, paramRequest);
        } else if("viewEvts".equals(mode)) {
            doViewEvts(request, response, paramRequest);
        } else if(mode.equals(SWBResourceURL.Mode_EDIT)){
            doEvent(request, response, paramRequest);
        } else if("pagination".equals(mode)) {
            doPagination(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    public void doPagination(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        String page = request.getParameter("pagen");
        
        StringBuffer buf = new StringBuffer();
        SWBResourceURL url = paramRequest.getRenderUrl();

        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        SWBResourceURL url1 = paramRequest.getActionUrl();
        url.setCallMethod(SWBResourceURL.Call_CONTENT);
        org.semanticwb.portal.resources.sem.calendar.Calendar calen = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();
        Iterator it = calen.listEventses();
        ArrayList ordEvts = new ArrayList();
        while(it.hasNext()) {
            Event ev = (Event)it.next();
            ordEvts.add(ev);
        }
        Collections.sort(ordEvts, new Comparator() {
            public int compare(Object o1, Object o2) {
                Event p1 = (Event) o1;
                Event p2 = (Event) o2;
                int i = -(new Long(p1.getUpdated().getTime()).compareTo(new Long(p2.getUpdated().getTime())));
                if (i == 0) {
                    i = -(new Long(p1.getCreated().getTime()).compareTo(new Long(p2.getCreated().getTime())));
                }
                return i;
            }
        });

        it = ordEvts.listIterator();
        int ps = calen.getPaginationEvents() == 0 ? 15 : calen.getPaginationEvents();
        //int l = evts.size();
        int l = ordEvts.size();
        int p = 0;
        if(page != null) {
            p = Integer.parseInt(page);
        }
        int x = 0;
        it = ordEvts.listIterator();
        while (it.hasNext()) {
            Event ev = (Event)it.next();

            //PAGINACION ////////////////////
            if(x < p * ps)
            {
                x++;
                continue;
            }
            if(x == (p * ps + ps) || x == l) {
                break;
            }
            x++;
            /////////////////////////////////
            String title =  ev.getTitle() == null ? "" : ev.getTitle();
            String evInit = ev.getEventInitDate() == null ? " " : ev.getEventInitDate();
            String evEnd = ev.getEventEndDate() == null ? " " : ev.getEventEndDate();
            buf.append("<div class=\"listBoxes\">");
            buf.append("   <div class=\"listBox1\">" + SWBUtils.TEXT.encode(title,SWBUtils.TEXT.CHARSET_UTF8) + "</div>");
            //SWBUtils.TEXT.encode(site.getDisplayTitle(lang), SWBUtils.TEXT.CHARSET_UTF8)
            buf.append("   <div class=\"listBox1\">" + evInit + "</div>");
            buf.append("   <div class=\"listBox1\">" + evEnd + "</div>");
            buf.append("   <div class=\"listBox2\">");
            buf.append("      <div class=\"listBox3\"><a href=\"" + url.setMode(paramRequest.Mode_EDIT).setParameter("uri", ev.getEncodedURI()).setParameter("_mode", "modEdit") + "\">" + paramRequest.getLocaleString("lb_update") + "</a></div>");
            buf.append("      <div class=\"listBox3\"><a href=\"" + url1.setAction(paramRequest.Action_REMOVE).setParameter("uri", ev.getEncodedURI()) + "\">" + paramRequest.getLocaleString("lb_delete") + "</a></div>");
            buf.append("   </div>");
            buf.append("</div>");
	}
        if(p > 0 || x < l) //Requiere paginacion
        {
            buf.append("<div class=\"pagination\">");
            int pages=(int)(l / ps);
            if((l % ps) > 0) pages++;
            for(int z = 0;z < pages;z++) {
                if(z != p) {
                    buf.append("<a href=\"#\" onclick=\"calculateEvents('" + (z) + "');\">"+ (z + 1)+"</a> ");
                }else {
                    buf.append((z + 1)+" ");
                }
            }
            buf.append("</div>");
        }
        out.println(buf.toString());
    }

    public void doStrategy(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/calendar/listEvents.jsp";
        org.semanticwb.portal.resources.sem.calendar.Calendar cal = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();
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

    public void doEvent(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/calendar/actEvents.jsp";
        RequestDispatcher rd1 = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd1.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }

    }

    public void doViewEvts(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String path = "/swbadmin/jsp/calendar/viewEvents.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            rd.include(request, response);
        } catch(Exception e) {
            log.error(e);
        }
    }

    public void doChangeCalendar(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        PrintWriter out = response.getWriter();
        org.semanticwb.portal.resources.sem.calendar.Calendar cal = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();//(org.semanticwb.portal.resources.sem.calendar.Calendar) base.getSemanticObject().createGenericInstance();
        String value = request.getParameter("month");
        int mont = 0, year=0;
        HashMap eventMonth = new HashMap();
        try {
            mont = Integer.parseInt(value);
        } catch(NumberFormatException e) {
            log.error("Error while convert month in ScheduledEvents: " + e);
        }
        value = request.getParameter("year");
        try {
            year = Integer.parseInt(value);
        } catch(NumberFormatException e) {
            log.error("Error while convert year in ScheduledEvents: " + e);
        }
        if(mont > 0 && year > 0) {
            Iterator ist=null;

            ist = cal.listEventses();
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
                        boolean isMonthly = ev.getPeriodicity().equals("monthly") && valueDay <= lastDayOfMonth(monthCurrent, year) ? true : false;//java.util.Calendar.getInstance().DAY_OF_MONTH == valueMonth ? true : false;
                        boolean isWeekly = ev.getPeriodicity().equals("weekly") ? true : false;
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
                    log.error("Error while process events in ScheduledEvents" + e);
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

    private JSONObject getEvents(ArrayList Events,SWBParamRequest paramRequest, org.semanticwb.portal.resources.sem.calendar.Calendar cal)
    {
        JSONObject objJSONEvents = new JSONObject();
        Iterator it = Events.iterator();
        while(it.hasNext()) {
            Event event = (Event)it.next();
            if(paramRequest.getUser().haveAccess(event))
            {
                try {
                    objJSONEvents.put(SWBUtils.TEXT.encode(event.getTitle(paramRequest.getUser().getLanguage())==null?event.getTitle():event.getTitle(paramRequest.getUser().getLanguage()), SWBUtils.TEXT.CHARSET_UTF8), getData(event, paramRequest, cal)); //SWBUtils.TEXT.encode(event.getTitle(),SWBUtils.TEXT.CHARSET_UTF8)
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

    private JSONObject getData(Event event,SWBParamRequest paramRequest, org.semanticwb.portal.resources.sem.calendar.Calendar cal)
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
        if(event.getImage() != null && event.getImage().trim().length() > 0) {
             photo= SWBPortal.getWebWorkPath()+event.getWorkPath()+"/"+event.getImage();
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
            objJSONData.put("title", SWBUtils.TEXT.encode(event.getTitle(paramRequest.getUser().getLanguage())==null?event.getTitle():event.getTitle(paramRequest.getUser().getLanguage()), SWBUtils.TEXT.CHARSET_UTF8));
            objJSONData.put("image", photo);
            String descr = "";
            if(event.getDescription(paramRequest.getUser().getLanguage()) != null) {
                descr = SWBUtils.TEXT.encode(event.getDescription(paramRequest.getUser().getLanguage()),SWBUtils.TEXT.CHARSET_UTF8);
            } else if(event.getDescription() != null) {
                descr = SWBUtils.TEXT.encode(event.getDescription(),SWBUtils.TEXT.CHARSET_UTF8);
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

        if(action.equals(SWBResourceURL.Action_ADD)) {
            response.setMode("add");

            String title = request.getParameter(Event.swb_title.getName());
            if(title != null && title.trim().length() < 1) {
                response.setRenderParameter("msgErr", "Este campo es obligatorio." + Event.swb_title.getDisplayName(user.getLanguage()));
                response.setRenderParameter("_mode", "modAdd");
                return;
            }
            String descr = request.getParameter(Event.swb_description.getName()) == null ? "" : request.getParameter(Event.swb_description.getName());
            String date1 = request.getParameter(Event.cal_eventInitDate.getName());
            if(date1 != null && date1.trim().length() > 1) {
                if(!validateRegExp(date1, "^2[0-9][0-9][0-9]-[\\d]{1,2}-[\\d]{1,2}$")) {
                    response.setRenderParameter("msgErr", "Este campo debe llevar el siguiente formato 'año-mes-día'" + Event.cal_eventInitDate.getDisplayName(user.getLanguage()));
                    response.setRenderParameter("_mode", "modAdd");
                    return;
                }
            }
            String date2 = request.getParameter(Event.cal_eventEndDate.getName()) == null ? "" : request.getParameter(Event.cal_eventEndDate.getName());
            if(date2 != null && date2.trim().length() > 1) {
                if(!validateRegExp(date2, "^2[0-9][0-9][0-9]-[\\d]{1,2}-[\\d]{1,2}$")) {
                    response.setRenderParameter("msgErr", "Este campo debe llevar el siguiente formato 'año-mes-día'" + Event.cal_eventEndDate.getDisplayName(user.getLanguage()));
                    response.setRenderParameter("_mode", "modAdd");
                    return;
                }
            }
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
            Date di, df;
            try {
                di = f1.parse(date1);
                df = f1.parse(date2);
                if(di.after(df)) {
                    response.setRenderParameter("msgErr", "La fecha inicial no puede ser mayor a la final.");
                    response.setRenderParameter("_mode", "modAdd");
                    return;
                }
            }catch(Exception e){
            }

            Event ev = Event.ClassMgr.createEvent(response.getWebPage().getWebSite());
            ev.setTitle(title);
            ev.setDescription(descr);
            if(date1 != null && date1.trim().length() > 0) {
                ev.setEventInitDate(date1);
            }
            if(date2 != null && date2.trim().length() > 0) {
                ev.setEventEndDate(date2);
            }

            if (user != null && user.isSigned()) {
                ev.setCreator(user);
            }
            ev.setCreated(java.util.Calendar.getInstance().getTime());
            if(request.getParameter(Event.cal_image.getName()) != null && request.getParameter(Event.cal_image.getName()).trim().length() > 0) {
                ev.setImage(request.getParameter(Event.cal_image.getName()));
                processFiles(request, ev.getSemanticObject());
            }
            if(request.getParameter(Event.cal_periodicity.getName()) != null && request.getParameter(Event.cal_periodicity.getName()).trim().length() > 0) {
                ev.setPeriodicity(request.getParameter(Event.cal_periodicity.getName()));
            }
            if(request.getParameter(Event.cal_urlInternal.getName()) != null) {
                ev.setUrlInternal(true);
            } else {
                ev.setUrlInternal(false);
            }
            if(request.getParameter(Event.cal_urlExternal.getName()) != null && request.getParameter(Event.cal_urlExternal.getName()).trim().length() > 0) {
                ev.setUrlExternal(request.getParameter(Event.cal_urlExternal.getName()));
            }
            if(request.getParameter(Event.cal_newWindow.getName()) != null && request.getParameter(Event.cal_newWindow.getName()).trim().length() > 0) {
                ev.setNewWindow(true);
            } else {
                ev.setNewWindow(false);
            }
            if(request.getParameter(Event.cal_photoPrincipal.getName()) != null && request.getParameter(Event.cal_photoPrincipal.getName()).trim().length() > 0) {
                ev.setPhotoPrincipal(request.getParameter(Event.cal_photoPrincipal.getName()));
                processFiles(request, ev.getSemanticObject());
            }
            if(request.getParameter(Event.cal_contentEvent.getName()) != null && request.getParameter(Event.cal_contentEvent.getName()).trim().length() > 0) {
                ev.setContentEvent(request.getParameter(Event.cal_contentEvent.getName()));
            }

            org.semanticwb.portal.resources.sem.calendar.Calendar cal = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();//(org.semanticwb.portal.resources.sem.calendar.Calendar) base.getSemanticObject().createGenericInstance();
            cal.addEvents(ev);

            String subjEs = "Hay nuevos eventos en el Calendario";
            String subjEn = "There are new events in Calendar";
            String bodyEs = "Se ha añadido el Evento ";
            String bodyEn = "Added a new events in Calendar ";
            sendMailEvt(ev,subjEs,subjEn, bodyEs, bodyEn);

            response.setMode("viewEvts");

        } else if(action.equals(SWBResourceURL.Action_EDIT)) {
            response.setMode(SWBResourceURL.Mode_EDIT);

            String title = request.getParameter(Event.swb_title.getName());
            if(title != null && title.trim().length() < 1) {
                response.setRenderParameter("msgErr", "Este campo es obligatorio." + Event.swb_title.getDisplayName(user.getLanguage()));
                response.setRenderParameter("_mode", "modEdit");
                response.setRenderParameter("uri", request.getParameter("uri"));
                return;
            }
            String descr = request.getParameter(Event.swb_description.getName()) == null ? "" : request.getParameter(Event.swb_description.getName());
            String date1 = request.getParameter(Event.cal_eventInitDate.getName());
            if(date1 != null && date1.trim().length() > 1) {
                if(!validateRegExp(date1, "^2[0-9][0-9][0-9]-[\\d]{1,2}-[\\d]{1,2}$")) {
                    response.setRenderParameter("msgErr", "Este campo debe llevar el siguiente formato 'año-mes-día'" + Event.cal_eventInitDate.getDisplayName(user.getLanguage()));
                    response.setRenderParameter("_mode", "modEdit");
                    response.setRenderParameter("uri", request.getParameter("uri"));
                    return;
                }
            }
            String date2 = request.getParameter(Event.cal_eventEndDate.getName()) == null ? "" : request.getParameter(Event.cal_eventEndDate.getName());
            if(date2 != null && date2.trim().length() > 1) {
                if(!validateRegExp(date2, "^2[0-9][0-9][0-9]-[\\d]{1,2}-[\\d]{1,2}$")) {
                    response.setRenderParameter("msgErr", "Este campo debe llevar el siguiente formato 'año-mes-día'" + Event.cal_eventEndDate.getDisplayName(user.getLanguage()));
                    response.setRenderParameter("_mode", "modEdit");
                    response.setRenderParameter("uri", request.getParameter("uri"));
                    return;
                }
            }
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
            Date di, df;
            try {
                di = f1.parse(date1);
                df = f1.parse(date2);
                if(di.after(df)) {
                    response.setRenderParameter("msgErr", "La fecha inicial no puede ser mayor a la final.");
                    response.setRenderParameter("_mode", "modEdit");
                    response.setRenderParameter("uri", request.getParameter("uri"));
                    return;
                }
            }catch(Exception e){
            }

            SemanticObject obj = SemanticObject.createSemanticObject(URLDecoder.decode(request.getParameter("uri")));
            Event ev = (Event)obj.createGenericInstance();
            ev.setTitle(title);
            ev.setDescription(descr);
            if(date1 != null && date1.trim().length() > 0) {
                ev.setEventInitDate(date1);
            } else if(ev.getEventInitDate() != null) {
                ev.removeProperty(Event.cal_eventInitDate.getName());
                ev.getSemanticObject().removeProperty(Event.cal_eventInitDate);
            }
            if(date2 != null && date2.trim().length() > 0) {
                ev.setEventEndDate(date2);
            } else if(ev.getEventEndDate() != null) {
                ev.removeProperty(Event.cal_eventEndDate.getName());
                ev.getSemanticObject().removeProperty(Event.cal_eventEndDate);
            }
            if (user != null && user.isSigned()) {
                ev.setModifiedBy(user);
            }
            ev.setUpdated(java.util.Calendar.getInstance().getTime());
            if(request.getParameter(Event.cal_image.getName()) != null && request.getParameter(Event.cal_image.getName()).trim().length() > 0) {
                String basepath = SWBPortal.getWorkPath() + ev.getSemanticObject().getWorkPath() + "/";
                String tmpPhoto = ev.getImage();
                File file = new File(basepath + tmpPhoto);
                file.delete();
                ev.setImage(request.getParameter(Event.cal_image.getName()));
                processFiles(request, ev.getSemanticObject());
            }
            if(request.getParameter(Event.cal_periodicity.getName()) != null && request.getParameter(Event.cal_periodicity.getName()).trim().length() > 0) {
                ev.setPeriodicity(request.getParameter(Event.cal_periodicity.getName()));
            }
            if(request.getParameter(Event.cal_urlInternal.getName()) != null) {
                ev.setUrlInternal(true);
            } else {
                ev.setUrlInternal(false);
            }
            if(request.getParameter(Event.cal_urlExternal.getName()) != null && request.getParameter(Event.cal_urlExternal.getName()).trim().length() > 0) {
                ev.setUrlExternal(request.getParameter(Event.cal_urlExternal.getName()));
            } else if(ev.getUrlExternal() != null) {
                ev.removeProperty(Event.cal_urlExternal.getName());
                ev.getSemanticObject().removeProperty(Event.cal_urlExternal);
            }
            if(request.getParameter(Event.cal_newWindow.getName()) != null && request.getParameter(Event.cal_newWindow.getName()).trim().length() > 0) {
                ev.setNewWindow(true);
            } else {
                ev.setNewWindow(false);
            }
            if(request.getParameter(Event.cal_photoPrincipal.getName()) != null && request.getParameter(Event.cal_photoPrincipal.getName()).trim().length() > 0) {
                String basepath = SWBPortal.getWorkPath() + ev.getSemanticObject().getWorkPath() + "/";
                String tmpPhoto = ev.getPhotoPrincipal();
                File file = new File(basepath + tmpPhoto);
                file.delete();
                ev.setPhotoPrincipal(request.getParameter(Event.cal_photoPrincipal.getName()));
                processFiles(request, ev.getSemanticObject());
            }
            if(request.getParameter(Event.cal_contentEvent.getName()) != null && request.getParameter(Event.cal_contentEvent.getName()).trim().length() > 0) {
                ev.setContentEvent(request.getParameter(Event.cal_contentEvent.getName()));
            } else if(ev.getContentEvent() != null) {
                ev.removeProperty(ev.getContentEvent());
                ev.getSemanticObject().removeProperty(Event.cal_contentEvent);
            }

            String subjEs = "Hay eventos modificados en el Calendario";
            String subjEn = "There are changes in events";
            String bodyEs = "Se ha modificado el Evento ";
            String bodyEn = "Change the event in Calendar ";
            sendMailEvt(ev,subjEs,subjEn, bodyEs, bodyEn);

            response.setMode("viewEvts");
        } else if(action.equals(SWBResourceURL.Action_REMOVE)) {

            String uri = URLDecoder.decode(request.getParameter("uri"));
            SemanticObject obj = SemanticObject.createSemanticObject(uri);
            Event evt = (Event) obj.createGenericInstance();

            String subjEs = "Hay eventos eliminados en el Calendario";
            String subjEn = "There are delete events in Calendar";
            String bodyEs = "Se ha eliminado el Evento ";
            String bodyEn = "The event delete was ";
            sendMailEvt(evt,subjEs,subjEn, bodyEs, bodyEn);

            evt.remove();
            obj.remove();
            org.semanticwb.portal.resources.sem.calendar.Calendar cal = (org.semanticwb.portal.resources.sem.calendar.Calendar)getSemanticObject().createGenericInstance();//(org.semanticwb.portal.resources.sem.calendar.Calendar) base.getSemanticObject().createGenericInstance();
            cal.removeEvents(evt);

            response.setMode("viewEvts");
            response.setCallMethod(response.Call_CONTENT);
        } else if(action.equals("saveSubCal")) {
            Calendar cal = (Calendar)getSemanticObject().createGenericInstance();

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

    private void sendMailEvt(Event ev, String subjEs, String subjEn, String bodyEs, String bodyEn) {
            org.semanticwb.portal.resources.sem.calendar.Calendar cal = (Calendar) getSemanticObject().createGenericInstance();
            Iterator <CalendarSubscription> subs = CalendarSubscription.ClassMgr.listCalendarSubscriptionByCalendarSubscription(cal);
            ArrayList usersCalSubs = new ArrayList();

            while(subs.hasNext()) {
                CalendarSubscription subscription = subs.next();
                if(subscription.getUserCalendarSubscription() != null) {
                    usersCalSubs.add(subscription.getUserCalendarSubscription());
                }
            }

            Iterator<User> us = usersCalSubs.iterator();
            SimpleDateFormat fDate = new SimpleDateFormat("dd/MM/yyyy");
            while(us.hasNext()) {
                User userForMail = us.next();
                Date ds;
                String subject = subjEs;
                String title1 = ev.getTitle("es") == null ? ev.getTitle() : ev.getTitle("es");
                String bodyEmail = bodyEs + title1 ;
                if(ev.getEventInitDate() != null) {
                    try {
                        ds = fDate.parse(ev.getEventInitDate());
                        bodyEmail = bodyEmail + ", con fecha de inicio: " + fDate.format(ds);
                    } catch(Exception e) {}
                }
                if(userForMail.getLanguage() != null ) {
                    if(userForMail.getLanguage().equals("en")) {
                        subject = subjEn;
                        bodyEmail = bodyEn + ev.getTitle(userForMail.getLanguage());
                        if(ev.getEventInitDate() != null) {
                            try {
                                ds = fDate.parse(ev.getEventInitDate());
                                bodyEmail = bodyEmail + " with start date: " + fDate.format(ds);
                            } catch(Exception e) {}
                        }
                    }
                }
                try {
                    SWBMail mail = new SWBMail();
                    mail.setFromEmail(SWBPlatform.getEnv("af/adminEmail"));
                    mail.setSubject(subject);
                    mail.setContentType("text/html");
                    mail.setData(bodyEmail.toString());
                    mail.addAddress(userForMail.getEmail());
                    SWBUtils.EMAIL.sendBGEmail(mail);
                } catch(Exception e) {
                    log.error("error while process mail: " + e);
                }
            }
    }

    private static boolean validateRegExp(String textSource, String regExp) {
        Pattern p = Pattern.compile(regExp);//regular expression
        Matcher m = p.matcher(textSource); // the text source
        return m.find();
    }

    private void processFiles(HttpServletRequest request, SemanticObject sobj) { //WebSite website
        String basepath = SWBPortal.getWorkPath() + sobj.getWorkPath() + "/";
        if (request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED) != null) {
            Iterator itfilesUploaded = ((List) request.getSession().getAttribute(UploadFormElement.FILES_UPLOADED)).iterator();

            while (itfilesUploaded.hasNext()) {
                FileItem item = (FileItem) itfilesUploaded.next();
                if (!item.isFormField()) { //Es un campo de tipo file
                    int fileSize = ((Long) item.getSize()).intValue();
                    String value = item.getName();
                    if (value != null && value.trim().length() > 0) {
                        value = value.replace("\\", "/");
                        int pos = value.lastIndexOf("/");
                        if (pos > -1) {
                            value = value.substring(pos + 1);
                        }
                        File fichero = new File(basepath);
                        if (!fichero.exists()) {
                            fichero.mkdirs();
                        }
                        fichero = new File(basepath + value);

                        String ext = "";
                        pos = -1;
                        pos = value.indexOf(".");
                        if (pos > -1) {
                            ext = value.substring(pos + 1);
                        }


                        Event evt = (Event) sobj.createGenericInstance();
                        if (item.getFieldName().equals(Event.cal_image.getName())) {
                            String tmpPhoto = evt.getImage();
                            evt.setImage(value);
                            File file = new File(basepath + tmpPhoto);
                            file.delete();

                            try {
                                item.write(fichero);
                                ImageResizer.shrinkTo(fichero, 120, 97, fichero, ext);
                                //ImageResizer.resize(fichero, 336, 224, true, fichero, ext);
                                //ImageResizer.shrinkTo(fichero, 281, 187, fichero, ext);
                                //ImageResizer.shrinkTo(fichero, 90, 67, fichero, ext);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.debug(e);
                            }
                        } else if (item.getFieldName().equals(Event.cal_photoPrincipal.getName())) {
                            String tmpPhoto = evt.getPhotoPrincipal();
                            evt.setPhotoPrincipal(value);
                            File file = new File(basepath + tmpPhoto);
                            file.delete();

                            try {
                                item.write(fichero);
                            } catch (Exception e) {
                                e.printStackTrace();
                                log.debug(e);
                            }
                        }
                    }
                }
            }
            request.getSession().setAttribute(UploadFormElement.FILES_UPLOADED, null);
        }
    }
}
