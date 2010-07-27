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
 
package org.semanticwb.portal.resources.sem.events;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
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
    private static Logger log = SWBUtils.getLogger(Events.class);

    private static final String Action_ENROLL = "enroll";
    /**
     * Instantiates a new events.
     */
    public Events()
    {
    }

    /**
     * Instantiates a new events.
     * 
     * @param base the base
     */
    public Events(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }
        
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        System.out.println("\n\n********************************\ndoView Events.....");
        out.println(renderListEvents(paramRequest));

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

        out.println("function validaAddEvent(frm) {");
        out.println("  frm.submit();");
        out.println("}");
        
        out.println("function validateRemoveEventElement(url) {");
        out.println("  if(confirm('¿Eliminar el evento?')) {");
        out.println("    window.location.href=url;");
        out.println("  }");
        out.println("}");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"\">");
        out.println("  <div class=\"adminTools\">");
        out.println("    <a class=\"adminTool\" onclick=\"validaAddEvent(document.frmAddEvent)\" href=\"#\">Guardar</a>");
        out.println("    <a class=\"adminTool\" href=\"<%=paramRequest.getRenderUrl()%>\">Cancelar</a>");
        out.println("  </div>");
        out.println("  <form name=\"frmAddEvent\" id=\"frmAddEvent\" method=\"post\" action=\""+addURL+"\">");
        out.println("    <div>");
        out.println("      <fieldset>");
        out.println("        <legend>Agregar evento</legend>");
        out.println("        <div>");
        out.println("          <p>");
        out.println("            <label for=\"title\">Título:</label><br />");
        out.println("            <input type=\"text\" name=\"title\" id=\"title\" maxsize=\"30\"/>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"desc\">Descripción:</label><br />");
        out.println("            <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\"></textarea>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"place\">Lugar:</label><br />");
        out.println("            <input type=\"text\" id=\"place\" name=\"place\" maxlength=\"60\" />");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"edate\">Fecha:</label><br />");
        out.println("            <input type=\"text\" name=\"edate\" id=\"edate\" dojoType=\"dijit.form.DateTextBox\" required=\"false\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" />");
        out.println("          </p>");
        out.println("<p>");
        out.println("<label for=\"etime\">Hora:</label><br />");
        out.println("<input type=\"text\" name=\"etime\" id=\"etime\" size=\"6\" style=\"width:40px;\" />");
        out.println("</p>");
        out.println("          <p>");
        out.println("            <label for=\"duration\">Duración (en minutos):</label><br />");
        out.println("            <input type=\"text\" id=\"duration\" name=\"duration\" maxlength=\"3\" />");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <input type=\"submit\" value=\"Aceptar\" />");
        out.println("            <input type=\"reset\" value=\"Limpiar\" />");
        out.println("          </p>");
        out.println("        </div>");
        out.println("      </fieldset>");
        out.println("    </div>");
        out.println("  </form>");
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

    private String renderCalendarEvents(SWBParamRequest paramRequest) throws SWBResourceException {
        StringBuilder html = new StringBuilder();

//        Iterator<Event> it = Event.ClassMgr.(paramRequest.getWebPage().getWebSite());
//        Event event = it.next();
//        event.getCreated()

        return html.toString();
    }

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
                html.append("<p>Ubicación: "+event.getPlace()+"</p>");
                html.append("<p>Cita: "+sdf.format(new Date(event.getDate()))+"</p>");
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

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out = response.getWriter();
        SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
        Event event = (Event)semObject.createGenericInstance();

        System.out.println("\n\n********************************\ndoEdit Events.....");

        SWBResourceURL editURL = paramRequest.getActionUrl().setAction(paramRequest.Action_EDIT).setParameter("uri", event.getURI());
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
        out.println("  frm.submit();");
        out.println("}");
        out.println("-->");
        out.println("</script>");

        out.println("<div class=\"\">");
        out.println("  <div class=\"adminTools\">");
        out.println("    <a class=\"adminTool\" onclick=\"validaEvent(document.frmEditEvent)\" href=\"#\">Guardar</a>");
        out.println("    <a class=\"adminTool\" href=\""+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"\">Cancelar</a>");
        out.println("  </div>");
        out.println("  <form name=\"frmEditEvent\" id=\"frmEditEvent\" method=\"post\" action=\""+editURL+"\">");
        out.println("    <div>");
        out.println("      <fieldset>");
        out.println("        <legend>Agregar evento</legend>");
        out.println("        <div>");
        out.println("          <p>");
        out.println("            <label for=\"title\">Título:</label><br />");
        out.println("            <input type=\"text\" name=\"title\" id=\"title\" maxsize=\"30\" value=\""+event.getTitle()+"\"/>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"desc\">Descripción:</label><br />");
        out.println("            <textarea id=\"description\" cols=\"45\" rows=\"3\" name=\"description\">"+event.getDescription()+"</textarea>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"place\">Lugar:</label><br />");
        out.println("            <input type=\"text\" id=\"place\" name=\"place\" maxlength=\"60\" value=\""+event.getPlace()+"\"/>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <label for=\"edate\">Fecha:</label><br />");
        out.println("            <input type=\"text\" name=\"edate\" id=\"edate\" dojoType=\"dijit.form.DateTextBox\" required=\"false\" constraints=\"{datePattern:'dd/MM/yyyy'}\" maxlength=\"10\" style=\"width:110px;\" />");
        out.println("          </p>");
        out.println("<p>");
        out.println("<label for=\"etime\">Hora:</label><br />");
        out.println("<input type=\"text\" name=\"etime\" id=\"etime\" size=\"6\" style=\"width:40px;\" />");
        out.println("</p>");
        out.println("          <p>");
        out.println("            <label for=\"duration\">Duración (en minutos):</label><br />");
        out.println("            <input type=\"text\" id=\"duration\" name=\"duration\" maxlength=\"3\" value=\""+event.getDuration()+"\"/>");
        out.println("          </p>");
        out.println("          <p>");
        out.println("            <input type=\"submit\" value=\"Aceptar\" />");
        out.println("            <input type=\"reset\" value=\"Limpiar\" />");
        out.println("          </p>");
        out.println("        </div>");
        out.println("      </fieldset>");
        out.println("    </div>");
        out.println("  </form>");
        out.println("</div>");
    }
    
    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        Resource base = this.getResourceBase();
        String action = response.getAction();
        System.out.println("+++processAction....");

        System.out.println("total de parametros="+request.getParameterMap().size());
        Enumeration<String> en = request.getParameterNames();
        while(en.hasMoreElements()) {
            String p=en.nextElement();
            System.out.println(p+"="+request.getParameter(p));
        }

        if(action.equalsIgnoreCase(response.Action_ADD)) {
            Event event = Event.ClassMgr.createEvent(getResourceBase().getWebSite());
            try {
                String title;
                if( (title=request.getParameter("title").trim()).equals("") ) {
                    response.setRenderParameter("msgErrTitle", "El título es requerido.");
                    log.error("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setTitle(title);
            }catch(Exception e) {}

            try {
                String desc;
                if( (desc=request.getParameter("description").trim()).equals("") ) {
                    response.setRenderParameter("msgErrDesc", "La descripción es requerida.");
                    log.error("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setDescription(desc);
            }catch(Exception e) {}
            
            try {
                String place;
                if( (place=request.getParameter("place")).equals("") ) {
                    response.setRenderParameter("msgErrPlace", "El lugar del evento es requerido.");
                    log.error("El lugar del evento es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("El lugar del evento es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setPlace(place);
            }catch(Exception e) {}

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = request.getParameter("edate");
                String time = request.getParameter("time").replaceFirst("T", " ");
                System.out.println("cita="+date+time);
                Date d = sdf.parse(date+time);
                event.setDate(d.getTime());
            }catch(Exception pe) {
                response.setRenderParameter("msgErrDate", "La fecha del evento es requerida.");
                log.error("La cita del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), pe);
                System.out.println("La cita del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                return;
            }
            
            try {
                int duration = Integer.parseInt(request.getParameter("duration"));
                event.setDuration(duration);
            }catch(NumberFormatException e) {
                response.setRenderParameter("msgErrDuration", "La duración del evento es requerida. Puede ser un tiempo aproximado.");
                log.error("La duración del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), e);
                System.out.println("La duración del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                return;
            }
            addEvent(event);
        }else if(action.equalsIgnoreCase(Action_ENROLL)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Event event = (Event)semObject.createGenericInstance();
            event.addAssistant(response.getUser());
        }else if(action.equalsIgnoreCase(response.Action_EDIT)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Event event = (Event)semObject.createGenericInstance();
            try {
                String title;
                if( (title=request.getParameter("title").trim()).equals("") ) {
                    response.setRenderParameter("msgErrTitle", "El título es requerido.");
                    log.error("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("El título es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setTitle(title);
            }catch(Exception e) {}

            try {
                String desc;
                if( (desc=request.getParameter("description").trim()).equals("") ) {
                    response.setRenderParameter("msgErrDesc", "La descripción es requerida.");
                    log.error("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("La descripción es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setDescription(desc);
            }catch(Exception e) {}

            try {
                String place;
                if( (place=request.getParameter("place")).equals("") ) {
                    response.setRenderParameter("msgErrPlace", "El lugar del evento es requerido.");
                    log.error("El lugar del evento es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    System.out.println("El lugar del evento es requerido. Resource "+base.getTitle()+" with id "+base.getId());
                    return;
                }
                event.setPlace(place);
            }catch(Exception e) {}

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String date = request.getParameter("edate");
                String time = request.getParameter("time").replaceFirst("T", " ");
                Date d = sdf.parse(date+time);
                event.setDate(d.getTime());
            }catch(Exception pe) {
                response.setRenderParameter("msgErrDate", "La fecha del evento es requerida.");
                log.error("La fecha del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), pe);
                System.out.println("La fecha del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                return;
            }

            try {
                int duration = Integer.parseInt(request.getParameter("duration"));
                event.setDuration(duration);
            }catch(NumberFormatException e) {
                response.setRenderParameter("msgErrDuration", "La duración del evento es requerida. Puede ser un tiempo aproximado.");
                log.error("La duración del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId(), e);
                System.out.println("La duración del evento es requerida. Resource "+base.getTitle()+" with id "+base.getId());
                return;
            }
            response.setMode(response.Mode_VIEW);
        }else if(action.equalsIgnoreCase(response.Action_REMOVE)) {
            SemanticObject semObject = SemanticObject.createSemanticObject(request.getParameter("uri"));
            Event event = (Event)semObject.createGenericInstance();
            removeEvent(event);
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

}
