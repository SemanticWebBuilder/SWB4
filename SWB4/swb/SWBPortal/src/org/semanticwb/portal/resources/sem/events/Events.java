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
package org.semanticwb.portal.resources.sem.events;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.UserGroup;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

// TODO: Auto-generated Javadoc
/**
 * Object that manage events.
 * 
 * @author jorge.jimenez
 */
public class Events extends org.semanticwb.portal.resources.sem.events.base.EventsBase {
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(Events.class);

    /** The Constant Action_ENROLL. */
    private static final String Action_ENROLL = "enroll";

//    private LinkedHashSet<Date> setDates;
    /**
     * Instantiates a new events.
     */
    public Events() {
    }

    /**
     * Instantiates a new events.
     * 
     * @param base the base
     */
    public Events(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#setResourceBase(Resource)
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException {
        super.setResourceBase(base);
        
//        setDates = new LinkedHashSet();
//        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//        Iterator<Event> itEvents = SWBComparator.sortByCreated(listEvents());
//        while(itEvents.hasNext()) {
//            Event event = itEvents.next();
//            Date date = new Date(event.getDate());
//            try {
//                setDates.add( sdf.parse(sdf.format(date)) );
//            }catch(ParseException pe) {
//            }
//        }
    }

    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        
        SWBResourceURL addURL = paramRequest.getActionUrl().setAction(paramRequest.Action_ADD);
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("dojo.require(\"dijit.form.DateTextBox\");");
        out.println("dojo.require(\"dijit.form.TimeTextBox\");");

        out.println("dojo.addOnLoad(function() {");
        out.println("   var t = new Date();");
        out.println("   var t1 = new dijit.form.TimeTextBox({name:'time', value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), 8, 0),");
        out.println("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
        out.println("   }, 'etime');");
        out.println("});");

        out.println("function validaEvent(frm) {");
        out.println("  var msg = new Array();");
        out.println("  if( isEmpty(frm.title.value) )");
        out.println("      msg.push('El título del evento es requerido');");
        out.println("  if( isEmpty(frm.description.value) )");
        out.println("      msg.push('La descripción del evento es requerida');");
        out.println("  if( isEmpty(frm.place.value) )");
        out.println("      msg.push('El lugar del evento es requerido');");
        out.println("  if( isEmpty(frm.edate.value) )");
        out.println("      msg.push('La fecha del evento es requerida');");
        out.println("  if( isEmpty(frm.etime.value) )");
        out.println("      msg.push('La hora del evento es requerida');");
        out.println("  if( !isDigit(frm.duration.value,1,3) )");
        out.println("      msg.push('La duración del evento es requerida');");
        out.println("  if(msg.length>0) {");
        out.println("      alert(msg.join('\\n'));");
        out.println("      return false;");
        out.println("  }else");
        out.println("      return true;");
        out.println("}");
        
        out.println("function validateRemoveEventElement(url) {");
        out.println("  if(confirm('¿Eliminar el evento?')) {");
        out.println("    window.location.href=url;");
        out.println("  }");
        out.println("}");

        out.println("function forward(cbmth) {");
        out.println("  if( (cbmth.selectedIndex+1)==cbmth.options.length )");
        out.println("    cbmth.selectedIndex = 0;");
        out.println("  else");
        out.println("    cbmth.selectedIndex = cbmth.selectedIndex+1;");
        out.println("  return true;");
        out.println("}");

        out.println("function rewind(cbmth) {");
        out.println("  if( (cbmth.selectedIndex-1)<0 )");
        out.println("    cbmth.selectedIndex = cbmth.options.length-1;");
        out.println("  else");
        out.println("    cbmth.selectedIndex = cbmth.selectedIndex-1;");
        out.println("  return true;");
        out.println("}");


        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"swb-events\">");
        out.println(renderCalendarEvents(request, paramRequest));

        if( request.getParameter("db")!=null && request.getParameter("da")!=null ) {
            Date dateBefore = new Date(Long.parseLong(request.getParameter("db")));
            Date dateAfter = new Date(Long.parseLong(request.getParameter("da")));
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(dateBefore);

            SWBResourceURL url = paramRequest.getRenderUrl();
            url.setParameter("month", Integer.toString(cal.get(GregorianCalendar.MONTH)));
            url.setParameter("year", Integer.toString(cal.get(GregorianCalendar.YEAR)));

            out.println("<div class=\"swb-events-list\">");
            out.println("<p><a href=\""+url+"\">Cerrar lista</a></p>");
            out.println(renderListEvents(paramRequest, dateBefore, dateAfter));
            out.println("<p><a href=\""+url+"\">Cerrar lista</a></p>");
            out.println("</div>");
        }

        out.println("<div class=\"swb-events-add\">");
//        out.println(" <div class=\"adminTools\">");
//        out.println("  <a class=\"adminTool\" onclick=\"if(validaEvent(document.frmEvent)) document.frmEvent.submit();\" href=\"#\">Guardar</a>");
//        out.println("  <a class=\"adminTool\" href=\""+paramRequest.getRenderUrl()+"\">Cancelar</a>");
//        out.println(" </div>");
        out.println(" <form name=\"frmEvent\" id=\"frmEvent\" method=\"post\" action=\""+addURL+"\">");
        out.println("  <div>");
        out.println("  <fieldset>");
        out.println("   <legend>Agregar evento</legend>");
        out.println("  <div>");
        out.println("   <p>");
        out.println("    <label for=\"title\">Título:</label><br />");
        out.println("    <input type=\"text\" name=\"title\" id=\"title\" maxlength=\"30\"/>");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrTitle")==null?"":request.getParameter("msgErrTitle"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <label for=\"desc\">Descripción:</label><br />");
        out.println("    <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\"></textarea>");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDesc")==null?"":request.getParameter("msgErrDesc"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <label for=\"place\">Lugar:</label><br />");
        out.println("    <input type=\"text\" id=\"place\" name=\"place\" maxlength=\"60\" />");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrPlace")==null?"":request.getParameter("msgErrPlace"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <label for=\"edate\">Fecha:</label><br />");
        out.println("    <input type=\"text\" name=\"edate\" id=\"edate\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" />");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDate")==null?"":request.getParameter("msgErrDate"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <label for=\"etime\">Hora:</label><br />");
        out.println("    <input type=\"text\" name=\"etime\" id=\"etime\" size=\"6\" style=\"width:40px;\" />");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDate")==null?"":request.getParameter("msgErrDate"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <label for=\"duration\">Duración (en minutos):</label><br />");
        out.println("    <input type=\"text\" id=\"duration\" name=\"duration\" maxlength=\"3\" />");
        out.println("    <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDuration")==null?"":request.getParameter("msgErrDuration"))+"</span>");
        out.println("   </p>");
        out.println("   <p>");
        out.println("    <input type=\"submit\" value=\"Aceptar\" onclick=\"return validaEvent(this.form)\" />");
        out.println("    <input type=\"reset\" value=\"Restablecer\" />");
        out.println("   </p>");
        out.println("  </div>");
        out.println("  </fieldset>");
        out.println("  </div>");
        out.println(" </form>");
        out.println("</div>");

        out.println("</div>");
    
//        WebSite site = paramRequest.getWebPage().getWebSite();
//        User user = paramRequest.getUser();
//        String lang = user.getLanguage();
//        System.out.println("uri de Event="+Event.sclass.getURI());
//        SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(Event.sclass.getURI());
//        System.out.println("cls="+cls);
//        SWBFormMgr mgr = new SWBFormMgr(cls, site.getSemanticObject(), null);
//        mgr.setFilterRequired(false);
//        mgr.setLang(lang);
//        mgr.setSubmitByAjax(false);
//        mgr.setType(mgr.TYPE_DOJO);
//        SWBResourceURL url = paramRequest.getActionUrl();
//        url.setParameter("uri", cls.getURI());
//        url.setAction(url.Action_ADD);
//        mgr.setAction(url.toString());
//        request.setAttribute("formName", mgr.getFormName());
//        mgr.addButton(SWBFormButton.newSaveButton());
//        mgr.addButton(SWBFormButton.newBackButton());
//        out.println(mgr.renderForm(request));

//        Event event = Event.createEvent(null);
//        Event.sclass.getURI();
//        event.sclass.getURI();
//        event.getSemanticObject().getSemanticClass().getURI();
    }

    /**
     * Render calendar events.
     * 
     * @param request the request
     * @param paramRequest the param request
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     */
    private String renderCalendarEvents(HttpServletRequest request, SWBParamRequest paramRequest) throws SWBResourceException {
        Resource base = getResourceBase();
        StringBuilder html = new StringBuilder();

        User user = paramRequest.getUser();
        UserGroup ug = user.getUserGroup();
        Locale locale = new Locale(user.getLanguage());

        LinkedHashSet<Date> setDates = new LinkedHashSet();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Iterator<Event> itEvents = SWBComparator.sortByCreated(listEvents());
        while(itEvents.hasNext()) {
            Event event = itEvents.next();
            if( !event.getCreator().getUserGroup().equals(ug) )
                continue;
            Date date = new Date(event.getDate());
            try {
                setDates.add( sdf.parse(sdf.format(date)) );
            }catch(ParseException pe) {
                log.error("La fecha del evento no se pudo parsear. Resource "+base.getTitle()+" with id "+base.getId());
            }
        }


        GregorianCalendar today = new GregorianCalendar(locale);

        int cmonth, cyear;
        try {
            cmonth= Integer.parseInt(request.getParameter("month"));
        }catch(Exception e) {
            cmonth = today.get(GregorianCalendar.MONTH) ;
        }
        try {
            cyear = Integer.parseInt(request.getParameter("year"));
        }catch(Exception e) {
            cyear = today.get(GregorianCalendar.YEAR) ;
        }

        GregorianCalendar ci = new GregorianCalendar(cyear, cmonth, 1, 0,0);
        int daysInMonth = ci.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        GregorianCalendar cf = new GregorianCalendar(cyear, cmonth, daysInMonth, 23,59);
        //SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        sdf = new SimpleDateFormat("MMMM");

        SWBResourceURL url = paramRequest.getRenderUrl();

        int dayCounter = 1;
        int loopCounter = 1;
        int First_Day = ci.get(GregorianCalendar.DAY_OF_WEEK);
        html.append("<div class=\"swb-events-cal\"> \n");
        html.append("<form method=\"post\" action=\""+url+"\"> \n");
        html.append(" <table> \n");
        html.append(" <tr> \n");
        html.append("  <td><input type=\"submit\" value=\"<--\" onclick=\"return rewind(dojo.byId('month'))\" /></td> \n");
        html.append("  <td>&nbsp;</td> \n");
        html.append("  <td> \n");
        html.append("   <select name=\"month\" id=\"month\" onchange=\"this.form.submit()\"> \n");
        {
            GregorianCalendar aux = new GregorianCalendar(2010,0,1);            
            for(int i=0; i<12; i++) {
                html.append("<option value=\"").append(i).append("\" ");
                if( aux.get(GregorianCalendar.MONTH)==ci.get(GregorianCalendar.MONTH) )
                    html.append("selected=\"selected\" ");
                html.append(">");
                html.append(sdf.format(aux.getTime()));
                html.append("</option> \n");
                aux.add(GregorianCalendar.MONTH, 1);
            }
        }
        html.append("   </select> \n");
        html.append("  </td> \n");
        html.append("  <td><input type=\"text\" name=\"year\" value=\"").append(ci.get(GregorianCalendar.YEAR)).append("\" size=\"4\" maxlength=\"4\" onkeyup=\"if(isDigit(this.value,4,4))this.form.submit()\" /></td> \n");
        html.append("  <td>&nbsp;</td> \n");
        html.append("  <td><input type=\"submit\" value=\"-->\" onclick=\"return forward(dojo.byId('month'))\" /></td> \n");
        html.append(" </tr> \n");
        html.append(" </table> \n");
        html.append(" </form> \n");
        
        html.append("<table> \n");
        html.append("<tr><td class=\"dow we\">Dom</td><td class=\"dow\">Lun</td><td class=\"dow\">Mar</td><td class=\"dow\">Mié</td><td class=\"dow\">Jue</td><td class=\"dow\">Vie</td><td class=\"dow we\">Sáb</td></tr>");
        
        url.setParameter("db", Long.toString(ci.getTimeInMillis()));
        url.setParameter("da", Long.toString(cf.getTimeInMillis()));
        url.setParameter("month", Integer.toString(ci.get(GregorianCalendar.MONTH)));
        url.setParameter("year", Integer.toString(ci.get(GregorianCalendar.YEAR)));
        sdf = new SimpleDateFormat("dd");
        for(int i=1; i<=6; i++) {
            html.append("  <tr> \n");
            for(int j=1; j<8; j++) {
                html.append("<td ");
                if( loopCounter>=First_Day && dayCounter<=daysInMonth ) {
                    if( ci.equals(today) )
                        html.append("class=\"today\"> \n");
                    else
                        html.append("class=\"date\"> \n");
                    if(setDates.contains(ci.getTime())) {
                        html.append("<a href=\""+url+"\" title=\"Eventos del mes\">");
                    }
                    html.append(sdf.format(ci.getTime()));
                    if(setDates.contains(ci.getTime())) {
                        html.append("</a> \n");
                    }
                    ci.add(GregorianCalendar.DAY_OF_MONTH, 1);
                    dayCounter++;
                }else {
                    html.append("class=\"none\">&nbsp;");
                }
                loopCounter++;
                html.append("</td> \n");
            }
            html.append("  </tr> \n");
        }
        html.append(" </table> \n");
//        html.append(" </form> \n");
        html.append("</div> \n");

        return html.toString();
    }

    /**
     * Render list events.
     * 
     * @param paramRequest the param request
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     */
    private String renderListEvents(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy | HH:mm");

        SWBResourceURL editURL = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        SWBResourceURL url = paramRequest.getActionUrl();

        System.out.println("renderListEvents....");

        html.append("<div class=\"swb-events-lista\">");        

        Iterator<Event> itEvents = SWBComparator.sortByCreated(listEvents(),false);
        if(itEvents.hasNext()) {
            html.append("<h2>eventos</h2>");
            html.append("<ol>");
            User user = paramRequest.getUser();
            UserGroup ug = user.getUserGroup();
            while(itEvents.hasNext()) {
                Event event = itEvents.next();
                if(event.getCreator()==null)
                    continue;
                if( !event.getCreator().getUserGroup().equals(ug) )
                    continue;
                html.append("<li>");
                html.append("<p>"+event.getTitle()+"</p>");
                html.append("<p>"+event.getDescription()+"</p>");
                html.append("<p>Lugar: "+event.getPlace()+"</p>");
                html.append("<p>Día: "+sdf.format(new Date(event.getDate()))+"</p>");
                html.append("<p>Duración: "+event.getDuration()+" mins.</p>");

                Iterator<User> itAssistants = event.listAssistants();
                boolean hasEnroll = false;
                if( itAssistants.hasNext() ) {
                    html.append("<p>Asistentes:</p>");
                    html.append("<ol>");
                    while( itAssistants.hasNext() ) {
                        User usr = itAssistants.next();
                        html.append("<li>");
                        html.append(usr.getFullName());
                        html.append("</li>");
                        if( usr.equals(user) )
                            hasEnroll = true;
                    }
                    html.append("</ol>");
                }

                url.setParameter("uri",event.getURI());

                if( !hasEnroll )
                    html.append("<p><a href=\""+url.setAction(Action_ENROLL)+"\">Asistir al evento</a></p>");

                if( event.getCreator().equals(user) ) {
                    html.append("<p><a href=\""+editURL.setParameter("uri", event.getURI())+"\">Editar</a>&nbsp;");
                    html.append("<a onclick=\"validateRemoveEventElement('"+url.setAction(paramRequest.Action_REMOVE)+"')\" href=\"#\">Eliminar</a></p>");
                }else
                    html.append("<p>Autor: "+(event.getCreator()==null?"Anónimo":event.getCreator().getFullName())+". "+sdf.format(event.getCreated())+"</p>");
                html.append("<hr/>");
                html.append("</li>");
            }
            html.append("</ol>");
        }else
            html.append("<!-- no hay eventos -->");
        html.append("</div>");
        return html.toString();
    }

    /**
     * Render list events.
     * 
     * @param paramRequest the param request
     * @param dateBefore the date before
     * @param dateAfter the date after
     * @return the string
     * @throws SWBResourceException the sWB resource exception
     */
    private String renderListEvents(SWBParamRequest paramRequest, final Date dateBefore, final Date dateAfter) throws SWBResourceException {
        StringBuilder html = new StringBuilder();
        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy | HH:mm");

        SWBResourceURL editURL = paramRequest.getRenderUrl().setMode(paramRequest.Mode_EDIT);
        SWBResourceURL url = paramRequest.getActionUrl();
        url.setParameter("db", Long.toString(dateBefore.getTime()));
        System.out.println("renderListEvents....");

        html.append("<div class=\"swb-events-lista\">");

        Iterator<Event> itEvents = SWBComparator.sortByCreated(listEvents(),false);
        if(itEvents.hasNext()) {
            html.append("<h2>eventos</h2>");
            html.append("<ol>");
            User user = paramRequest.getUser();
            UserGroup ug = user.getUserGroup();
            while(itEvents.hasNext()) {
                Event event = itEvents.next();
                if(event.getCreator()==null)
                    continue;
                if( !event.getCreator().getUserGroup().equals(ug) )
                    continue;

                Date d = new Date(event.getDate());
                if( d.before(dateBefore) || d.after(dateAfter) )
                    continue;

                html.append("<li>");
                html.append("<p>"+event.getTitle()+"</p>");
                html.append("<p>"+event.getDescription()+"</p>");
                html.append("<p>Lugar: "+event.getPlace()+"</p>");
                html.append("<p>Día: "+sdf.format(new Date(event.getDate()))+"</p>");
                html.append("<p>Duración: "+event.getDuration()+" mins.</p>");

                Iterator<User> itAssistants = event.listAssistants();
                boolean hasEnroll = false;
                if( itAssistants.hasNext() ) {
                    html.append("<p>Asistentes:</p>");
                    html.append("<ol>");
                    while( itAssistants.hasNext() ) {
                        User usr = itAssistants.next();
                        html.append("<li>");
                        html.append(usr.getFullName());
                        html.append("</li>");
                        if( usr.equals(user) )
                            hasEnroll = true;
                    }
                    html.append("</ol>");
                }

                url.setParameter("uri",event.getURI());
                if( !hasEnroll )
                    html.append("<p><a href=\""+url.setAction(Action_ENROLL)+"\">Asistir al evento</a></p>");

                if( event.getCreator().equals(user) ) {
                    html.append("<p><a href=\""+editURL.setParameter("uri", event.getURI())+"\">Editar</a>&nbsp;");
                    html.append("<a onclick=\"validateRemoveEventElement('"+url.setAction(paramRequest.Action_REMOVE)+"')\" href=\"#\">Eliminar</a></p>");
                }else
                    html.append("<p>Autor: "+(event.getCreator()==null?"Anónimo":event.getCreator().getFullName())+". "+sdf.format(event.getCreated())+"</p>");
                html.append("<hr/>");
                html.append("</li>");
            }
            html.append("</ol>");
        }else
            html.append("<!-- no hay eventos -->");
        html.append("</div>");
        return html.toString();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doEdit(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        Event event = (Event)semObject.createGenericInstance();

        Date date = new Date(event.getDate());
        SimpleDateFormat sdf = new SimpleDateFormat("H");
        String hour = sdf.format(date);
        sdf = new SimpleDateFormat("m");
        String minute = sdf.format(date);
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        String citation = sdf.format(date);

        SWBResourceURL editURL = paramRequest.getActionUrl().setAction(paramRequest.Action_EDIT).setParameter("uri", event.getURI());
        out.println("<script type=\"text/javascript\">");
        out.println("<!--");
        out.println("dojo.require(\"dijit.form.DateTextBox\");");
        out.println("dojo.require(\"dijit.form.TimeTextBox\");");

        out.println("dojo.addOnLoad(function() {");
        out.println("   var t = new Date();");
        out.println("   var t1 = new dijit.form.TimeTextBox({name:'time', value:new Date(t.getFullYear(), t.getMonth(), t.getDate(), "+hour+", "+minute+"),");
        out.println("                constraints:{timePattern:'HH:mm', clickableIncrement:'T00:15:00', visibleIncrement:'T00:15:00', visibleRange:'T02:00:00'}");
        out.println("   }, 'etime');");
        out.println("});");

        out.println("function validaEvent(frm) {");
        out.println("  var msg = new Array();");
        out.println("  if( isEmpty(frm.title.value) )");
        out.println("      msg.push('El título del evento es requerido');");
        out.println("  if( isEmpty(frm.description.value) )");
        out.println("      msg.push('La descripción del evento es requerida');");
        out.println("  if( isEmpty(frm.place.value) )");
        out.println("      msg.push('El lugar del evento es requerido');");
        out.println("  if( isEmpty(frm.edate.value) )");
        out.println("      msg.push('La fecha del evento es requerida');");
        out.println("  if( isEmpty(frm.etime.value) )");
        out.println("      msg.push('La hora del evento es requerida');");
        out.println("  if( !isDigit(frm.duration.value,1,3) )");
        out.println("      msg.push('La duración del evento es requerida');");
        out.println("  if(msg.length>0) {");
        out.println("      alert(msg.join('\\n'));");
        out.println("      return false;");
        out.println("  }else");
        out.println("      return true;");
        out.println("}");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"swb-events\">");
        out.println(" <div class=\"adminTools\">");
        out.println("  <a class=\"adminTool\" onclick=\"if(validaEvent(document.frmEvent)) document.frmEvent.submit();\" href=\"#\">Guardar</a>");
        out.println("  <a class=\"adminTool\" href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\">Cancelar</a>");
        out.println(" </div>");
        out.println(" <form name=\"frmEvent\" id=\"frmEvent\" method=\"post\" action=\""+editURL+"\">");
        out.println("  <div>");
        out.println("  <fieldset>");
        out.println("   <legend>Agregar evento</legend>");
        out.println("   <div>");
        out.println("    <p>");
        out.println("     <label for=\"title\">Título:</label><br />");
        out.println("     <input type=\"text\" name=\"title\" id=\"title\" maxlength=\"30\" value=\""+event.getTitle()+"\"/>");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrTitle")==null?"":request.getParameter("msgErrTitle"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"description\">Descripción:</label><br />");
        out.println("     <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\">"+event.getDescription()+"</textarea>");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDesc")==null?"":request.getParameter("msgErrDesc"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"place\">Lugar:</label><br />");
        out.println("     <input type=\"text\" id=\"place\" name=\"place\" maxlength=\"60\" value=\""+event.getPlace()+"\"/>");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrPlace")==null?"":request.getParameter("msgErrPlace"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"edate\">Fecha:</label><br />");
        out.println("     <input type=\"text\" name=\"edate\" id=\"edate\" value=\""+citation+"\" dojoType=\"dijit.form.DateTextBox\" required=\"true\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" />");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDate")==null?"":request.getParameter("msgErrDate"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"etime\">Hora:</label><br />");
        out.println("     <input type=\"text\" name=\"etime\" id=\"etime\" style=\"width:110px;\" />");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDate")==null?"":request.getParameter("msgErrDate"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <label for=\"duration\">Duración (en minutos):</label><br />");
        out.println("     <input type=\"text\" id=\"duration\" name=\"duration\" maxlength=\"3\" value=\""+event.getDuration()+"\"/>");
        out.println("     <span class=\"swb-events-warn\">"+(request.getParameter("msgErrDuration")==null?"":request.getParameter("msgErrDuration"))+"</span>");
        out.println("    </p>");
        out.println("    <p>");
        out.println("     <input type=\"submit\" value=\"Aceptar\" onclick=\"return validaEvent(this.form)\" />");
        out.println("     <input type=\"reset\" value=\"Restablecer\" />");
        out.println("    </p>");
        out.println("   </div>");
        out.println("  </fieldset>");
        out.println("  </div>");
        out.println(" </form>");
        out.println("</div>");
    }
    
    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericSemResource#processAction(HttpServletRequest, SWBActionResponse)
     */
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        User user = response.getUser();
        if( !user.isSigned() ) {
            response.setMode(response.Mode_HELP);
            return;
        }
        
        Resource base = this.getResourceBase();
        String action = response.getAction();

        if( action.equals(response.Action_ADD)||action.equals(response.Action_EDIT) ) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date ed = null;

            Event event = null;
            if( action.equals(response.Action_ADD) )
                event = Event.ClassMgr.createEvent(getResourceBase().getWebSite());
            else {
                SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
                event = (Event)semObject.createGenericInstance();
//                try {
//                    ed = sdf.parse( sdf.format(new Date(event.getDate())) );
//                }catch(ParseException e) {
//                    log.error("La fecha original no se puedo parsear. Resource "+base.getTitle()+" with id "+base.getId());
//                    return;
//                }
            }

            try {
                String title = request.getParameter("title").trim();
                if( title.equals("") ) {
                    throw new Exception("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                }
                event.setTitle(title);
            }catch(Exception e) {
                response.setRenderParameter("msgErrTitle", "El título es requerido.");
                log.error("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                return;
            }

            try {
                String desc;
                if( (desc=request.getParameter("description").trim()).equals("") ) {
                    response.setRenderParameter("msgErrDesc", "La descripción es requerida.");
                    log.error("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setDescription(desc);
            }catch(Exception e) {}
            
            try {
                String place;
                if( (place=request.getParameter("place")).equals("") ) {
                    response.setRenderParameter("msgErrPlace", "El lugar del evento es requerido.");
                    log.error("El lugar del evento es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setPlace(place);
            }catch(Exception e) {}
            
            Date newd;
            try {
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = request.getParameter("edate");
                String time = request.getParameter("time").replaceFirst("T", " ");
                Date d = sdf.parse(date+time);
                event.setDate(d.getTime());
                
                newd = sdf.parse(date+" 00:00");
                System.out.println("nueva fecha="+newd);
            }catch(Exception pe) {
                response.setRenderParameter("msgErrDate", "La fecha del evento es requerida.");
                log.error("La fecha y hora del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), pe);
                System.out.println("Error.... "+pe);
                return;
            }
            
            try {
                int duration = Integer.parseInt(request.getParameter("duration"));
                event.setDuration(duration);
            }catch(NumberFormatException e) {
                response.setRenderParameter("msgErrDuration", "La duración del evento es requerida. Puede ser un tiempo aproximado.");
                log.error("La duración del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), e);
                return;
            }
            if( action.equals(response.Action_ADD) ) {
                addEvent(event);
//                addEventToSet(newd);
            }else {
                response.setMode(response.Mode_VIEW);
//                if(removeEventToSet(ed))
//                    addEventToSet(newd);
            }
            GregorianCalendar c = new GregorianCalendar(new Locale(user.getLanguage()));
            c.setTime(newd);
            response.setRenderParameter("month", Integer.toString(c.get(GregorianCalendar.MONTH)));
            response.setRenderParameter("year", Integer.toString(c.get(GregorianCalendar.YEAR)));
        }else if(action.equalsIgnoreCase(Action_ENROLL)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Event event = (Event)semObject.createGenericInstance();
            event.addAssistant(response.getUser());

            GregorianCalendar ci = new GregorianCalendar(new Locale(user.getLanguage()));
            ci.setTime(new Date(Long.parseLong(request.getParameter("db"))));
            int daysInMonth = ci.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            GregorianCalendar cf = new GregorianCalendar(ci.get(GregorianCalendar.YEAR), ci.get(GregorianCalendar.MONTH), daysInMonth, 23,59);
            response.setRenderParameter("db", Long.toString(ci.getTimeInMillis()));
            response.setRenderParameter("da", Long.toString(cf.getTimeInMillis()));
            response.setRenderParameter("month", Integer.toString(ci.get(GregorianCalendar.MONTH)));
            response.setRenderParameter("year", Integer.toString(ci.get(GregorianCalendar.YEAR)));
        }else if(action.equalsIgnoreCase(response.Action_REMOVE)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Event event = (Event)semObject.createGenericInstance();
            removeEvent(event);

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            Date ed = null;
//            try {
//                ed = sdf.parse( sdf.format(new Date(event.getDate())) );
//                removeEventToSet(ed);
//            }catch(ParseException e) {
//                log.error("La fecha original no se puedo parsear. Resource "+base.getTitle()+" with id "+base.getId());
//                return;
//            }
        }
        
//        String action=response.getAction();
//        if(action.equals(response.Action_EDIT)){
//            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
//            SWBFormMgr mgr = new SWBFormMgr(semObject, null, SWBFormMgr.MODE_EDIT);
//            try{
//                mgr.processForm(request);
//            }catch(Exception e){
//
//            }
//        }else if(action.equals(response.Action_REMOVE)){
//            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("EventUri"));
//            semObject.remove();
//        }else if(action.equals(response.Action_ADD) ) {
//            //SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(request.getParameter("uri"));
//            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(Event.sclass.getURI());
//            SWBFormMgr mgr = new SWBFormMgr(cls, response.getWebPage().getWebSite().getSemanticObject(), null);
//            mgr.setFilterRequired(false);
//            try {
//                SemanticObject sobj = mgr.processForm(request);
//                Event event = (Event)sobj.createGenericInstance();
//                addEvent(event);
//                //event.listUsers();
//                response.setRenderParameter("uri", event.getURI());
//            }catch(FormValidateException e) {
//
//            }
//        }
//        response.setMode(response.Mode_VIEW);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.portal.api.GenericResource#doHelp(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doHelp(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=ISO-8859-1");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        PrintWriter out = response.getWriter();
        out.println("El usuario no tiene privilegios para realizar esta tarea.");
    }

//    private boolean addEventToSet(Date date) {
//        return setDates.add(date);
//    }

//    private boolean removeEventToSet(Date date) {
//        return setDates.remove(date);
//    }

}
