<%-- 
    Document   : Calendar2Expiration
    Created on : 24/08/2010, 01:04:29 PM
    Author     : juan.fernandez
--%>

<%@page import="org.w3c.dom.NodeList"%>
<%@page import="org.w3c.dom.Document"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.semanticwb.resource.office.sem.WordResource"%>
<%@page import="org.semanticwb.resource.office.sem.OfficeResource"%>
<%@page import="org.semanticwb.repository.office.OfficeContent"%>
<%@page import="org.semanticwb.model.*"%>
<%@page import="org.semanticwb.model.Calendar"%>
<%@page import="org.semanticwb.model.CalendarRef"%>
<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.servlet.SWBHttpServletResponseWrapper"%>
<%@page import="org.semanticwb.portal.api.SWBResource"%>
<%@page import="org.semanticwb.portal.api.SWBResourceURL"%>
<%@page import="org.semanticwb.SWBPortal"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="org.semanticwb.model.Resource"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.WebPage"%>
<%@page import="org.semanticwb.model.User"%>

<%!

private Date cambiaFormato(String fecha, int formato) {

        String nf = fecha;
        String y = "";
        String m = "";
        String d = "";
        if (formato == 1) {
            StringTokenizer st = new StringTokenizer(fecha, "-");
            if (st.hasMoreTokens()) {
                y = st.nextToken();
                if (st.hasMoreTokens()) {
                    m = st.nextToken();
                }
                if (st.hasMoreTokens()) {
                    d = st.nextToken();
                }
                nf = m + "/" + d + "/" + y;
            }
        } else if (formato == 2) {
            StringTokenizer st = new StringTokenizer(fecha, "/");
            if (st.hasMoreTokens()) {
                m = st.nextToken();
                if (st.hasMoreTokens()) {
                    d = st.nextToken();
                }
                if (st.hasMoreTokens()) {
                    y = st.nextToken();
                }
                nf = y + "-" + m + "-" + d;
            }
        }

        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(Integer.parseInt(y), Integer.parseInt(m)-1, Integer.parseInt(d));

        return cal.getTime();
    }
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cambia calendario a Fecha de expiración</title>
    </head>
    <body>
        <h1>Cambia Calendario por fecha de expiración si este aplica en WebPages</h1>
        <%
    int icalendars=0;
    int iwebpagesupd=0;
    int numcalrem = 0, numcalreview = 0;
    int numref = 0;
    if(request.getParameter("site")!=null)
    {
	    String id=request.getParameter("site");
	    WebSite site=WebSite.ClassMgr.getWebSite(id);
	    if(site!=null)
	    {
                HashMap<String,org.semanticwb.model.Calendar> hmCalRemove = new HashMap();
                HashMap<String,org.semanticwb.model.GenericObject> hmCalRefRemove = new HashMap();
		Iterator<org.semanticwb.model.Calendar> calendars=site.listCalendars();
		while(calendars.hasNext())
		{                  
		    org.semanticwb.model.Calendar cal=calendars.next();
                    String xml = cal.getXml();
                    try
                    {
                        Document dom = SWBUtils.XML.xmlToDom(xml);
                        String inidate=SWBUtils.XML.getAttribute(dom, "inidate", null);
                        String enddate=SWBUtils.XML.getAttribute(dom, "enddate", null);
                        String starthour=SWBUtils.XML.getAttribute(dom, "starthour", null);
                        String endhour=SWBUtils.XML.getAttribute(dom, "endhour", null);
                        //validación si el calendario se verifica
                        if(enddate==null || starthour!=null || endhour!=null) continue;

                        //Paso verificación anterio, revisando periodicidad
                        NodeList nl = dom.getElementsByTagName("iterations");
                        //validación si el calendario se verifica
                        if(nl!=null&&nl.getLength()>0) continue;
                            
                        icalendars++;
                        // Revisar elementos asociados al calendario

                        Iterator<GenericObject> itgo = cal.listRelatedObjects();
                        while (itgo.hasNext())
                        {
                            GenericObject elem = itgo.next();
                            if(elem instanceof CalendarRef)
                            {
                                System.out.println("CalendarRef");
                                CalendarRef calref = (CalendarRef) elem;
                                boolean calremovewp = false;
                                boolean calremovecal = false;

                                Iterator<GenericObject> itref = calref.listRelatedObjects();
                                while (itref.hasNext())
                                {
                                    GenericObject goref = itref.next();
                                    if(goref instanceof WebPage)
                                    {

                                        WebPage wp = (WebPage)goref;

                                        wp.setExpiration(cambiaFormato(enddate,2));
                                        calremovewp = true;
                                        iwebpagesupd++; 
                                    }
                                    if(goref instanceof Calendar)
                                        calremovecal = true;

                                }
                               if(calremovewp && calremovecal)
                                {
                                   //elementos a eliminar
                                   hmCalRefRemove.put(elem.getURI(), elem);
                                   hmCalRemove.put(cal.getURI(),cal);
                                }
                                
                            }
                            else
                                System.out.println("Ref: "+elem.getURI());

                        }


                        out.println("<div><pre>"+xml+"</pre></div><br/>");
                    }
                    catch(Exception e)
                    {
                        System.out.println("Error en la definición del XML.");

                    }
                    
		}

                //Eliminando referencias a calendarios
                Iterator<GenericObject> itgo = hmCalRefRemove.values().iterator();

                while (itgo.hasNext())
                {
                    System.out.println("Eliminando referencias....");
                    GenericObject go = itgo.next();
                    go.getSemanticObject().remove();
                    numref++;
                }

                //Revisando calendarios para eliminarlos
                Iterator<org.semanticwb.model.Calendar> itcal = hmCalRemove.values().iterator();

                while (itcal.hasNext())
                {
                    org.semanticwb.model.Calendar calrem = itcal.next();
                    numcalreview++;
                    if(calrem.listRelatedObjects().hasNext())
                    {
                        //No se puede borrar, aún existen referencias a este
                        continue;
                    }
                    else
                    {
                        calrem.getSemanticObject().remove();
                        numcalrem++;
                    }
                }

	    }
    }
%>

        <h2>Calendarios revisados <%=icalendars%></h2>
        <ul>
            <li>Actualización de WebPages la fecha de expiración: <%=iwebpagesupd%></li>
            <li>Referencias de WebPages a calendarios eliminadas: <%=numref%></li>
            <li>Número de calendarios eliminados: <%=numcalrem%></li>
        </ul>
    </body>
</html>




