/**
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
 **/
package org.semanticwb.portal.community;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

 /** @author Hasdai Pacheco {haxdai@gmail.com} */
public class EventResource extends org.semanticwb.portal.community.base.EventResourceBase {

    private static Logger log = SWBUtils.getLogger(EventResource.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public EventResource() {
    }

    public EventResource(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = request.getParameter("act");
        WebPage page = response.getWebPage();
        Member mem = Member.getMember(response.getUser(), page);
        if (!mem.canView()) {
            return;                                       //si el usuario no pertenece a la red sale;
        }

        //Gather event data
        String title = request.getParameter("event_title");
        title = (title==null?"":title);
        String desc = request.getParameter("event_description");
        desc = (desc==null?"":desc);
        String aud = request.getParameter("event_audience");
        aud = (aud==null?"":aud);
        String startDate = request.getParameter("event_startDate");
        startDate = (startDate==null?"":startDate);
        String endDate = request.getParameter("event_endDate");
        endDate = (endDate==null?"":endDate);
        String startTime = request.getParameter("event_startTime");
        startTime = (startTime==null?"":startTime.replace("T", "").trim());
        String endTime = request.getParameter("event_endTime");
        endTime = (endTime==null?"":endTime.replace("T", "").trim());
        String place = request.getParameter("event_place");
        place = (place==null?"":place);
        String tags = request.getParameter("event_tags");
        tags = (tags==null?"":tags);
        
        if (action.equals("add") && mem.canAdd()) {
            //Create event object
            EventElement rec = EventElement.createEventElement(getResourceBase().getWebSite());

            //Set event properties
            rec.setTitle(title);
            rec.setDescription(desc);
            rec.setAudienceType(aud);

            try {
                rec.setStartDate(dateFormat.parse(startDate.trim()));
                rec.setEndDate(dateFormat.parse(endDate.trim()));
                rec.setStartTime(new Timestamp(timeFormat.parse(startTime).getTime()));
                rec.setEndTime(new Timestamp(timeFormat.parse(endTime).getTime()));
            } catch (Exception e) {
                log.error(e);
            }
            rec.setEventWebPage(page);
            rec.setPlace(place);
            rec.setTags(tags);

            //Set render parameters
            try {
                response.setRenderParameter("act", "edit");
                response.setRenderParameter("uri", rec.getURI());
            } catch (Exception e) {
                log.error(e);
                response.setRenderParameter("act", "add");               //regresa a agregar codigo
                response.setRenderParameter("err", "true");              //envia parametro de error
            }
        } else if (action.equals("edit")) {
            //Get event object
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();
            
            if (rec != null && rec.canModify(mem)) {
                //Set new event properties
                rec.setTitle(title);
                rec.setDescription(desc);
                rec.setAudienceType(aud);

                try {
                    rec.setStartDate(dateFormat.parse(startDate));
                    rec.setEndDate(dateFormat.parse(endDate));
                    rec.setStartTime(new Timestamp(timeFormat.parse(startTime).getTime()));
                    rec.setEndTime(new Timestamp(timeFormat.parse(endTime).getTime()));
                } catch (Exception e) {
                    log.error(e);
                }
                rec.setPlace(place);
                rec.setTags(tags);
                rec.setVisibility(Integer.parseInt(request.getParameter("level")));   //hace convercion a int en automatico

                if (page instanceof MicroSiteWebPageUtil) {
                    ((MicroSiteWebPageUtil) page).sendNotification(rec);
                }
            }
        } else if (action.equals("remove")) {
            //Get event object
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            //Remove event object
            if (rec != null && rec.canModify(mem)) {
                rec.remove();                                       //elimina el registro
            }
        } else if (action.equals("attend")) {
            //Get event object
            String uri = request.getParameter("uri");
            EventElement rec = (EventElement) SemanticObject.createSemanticObject(uri).createGenericInstance();

            //Add attendant member
            if (rec != null && rec.canModify(mem)) {
                rec.addAttendant(mem.getUser());
            }
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String action = request.getParameter("act");
        action = (action == null ? "view" : action);

        String path = "/swbadmin/jsp/microsite/EventResource/eventView.jsp";
        if (action.equals("add")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventAdd.jsp";
        } else if (action.equals("edit")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventEdit.jsp";
        } else if (action.equals("detail")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventDetail.jsp";
        } else if (action.equals("daily")) {
            path = "/swbadmin/jsp/microsite/EventResource/eventDailyView.jsp";
        }

        RequestDispatcher dis = request.getRequestDispatcher(path);
        try {
            request.setAttribute("paramRequest", paramRequest);
            dis.include(request, response);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public Iterator<EventElement> listEventElementsByDate(Date date) {
        ArrayList<EventElement> res = new ArrayList<EventElement>();
        Iterator <EventElement> eit = listEvents();
        while (eit.hasNext()) {
            EventElement ev = eit.next();
            //If event starts at, is carried out, or ends in date, add it to the list
            if (ev.getStartDate().equals(date) || ev.getEndDate().equals(date)
                    || (ev.getStartDate().after(date) && ev.getEndDate().before(date))) {
                res.add(ev);
            }
        }

        return res.iterator();
    }

    public String renderCalendar(Date current, SWBParamRequest paramRequest) {
        StringBuffer sbf = new StringBuffer();
        
        //If no date specified, get current date
        if (current == null) current = new Date(System.currentTimeMillis());
        
        int day = current.getDate();
        int month = current.getMonth();
        int year = current.getYear();

        String [] months = {"Enero", "Febrero", "Marzo", "Abril", 
                            "Mayo", "Junio", "Julio", "Agosto",
                            "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        Date thisMonth = new Date(year, month, 1);
        Date nextMonth = new Date(year, month + 1, 1);
        
        //Find out when this mont starts and ends
        int firstWeekDay = thisMonth.getDay();
        long daysInMonth = Math.round((nextMonth.getTime() - thisMonth.getTime()) / (1000 * 60 * 60 * 24));

        sbf.append("<table class=\"calendar\">\n" +
                "  <tr>\n" +
                "    <td class=\"calendar-head\" colspan=\"7\">" +
                months[month] + " " + year +
                "</td>\n  </tr>\n" +
                "  <tr>\n");

        //Fill the first week in the month with the appropiate day offset
        for (int i = 0; i < firstWeekDay; i++) {
            sbf.append("    <td class=\"calendar-empty\"> </td>\n");
        }

        int weekDay = firstWeekDay;
        for (int i = 1; i <= daysInMonth; i++) {
            weekDay %= 7;
            if (weekDay == 0) {
                sbf.append("</tr><tr>");
            }
            
            //Today?
            if (day == i) {
                //Are there events today?
                if (listEventElementsByDate(new Date(year, month, i)).hasNext()) {
                    SWBResourceURL viewUrl = paramRequest.getRenderUrl().setParameter("act", "daily");
                    viewUrl.setParameter("year", String.valueOf(year));
                    viewUrl.setParameter("month", String.valueOf(month));
                    viewUrl.setParameter("day", String.valueOf(day));
                    sbf.append("    <td class=\"calendar-dated\" onclick=\"window.location='" + viewUrl + "';\">\n" +
                            "<div class=\"calendar-daylabel\">" + i + "</div>\n" +
                            "    </td>\n");
                }  else {
                    //There aren't events today
                    sbf.append("    <td class=\"calendar-today\" >\n" +
                            "<div class=\"calendar-daylabel\">" + i + "</div>\n" +
                            "    </td>\n");
                }
            } else {
                //Not today
                sbf.append("    <td class=\"calendar-day\" >\n" +
                            "<div class=\"calendar-daylabel\">" + i + "</div>\n" +
                            "    </td>\n");
            }
            weekDay++;
        }
        sbf.append("  </tr>\n" +
                "</table>");

        return sbf.toString();
    }
}