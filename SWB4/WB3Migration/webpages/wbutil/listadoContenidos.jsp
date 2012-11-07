<%-- 
    Document   : listadoContenidos
    Created on : 31/10/2012, 11:49:05 AM
    Author     : juan.fernandez
--%>

<%@page import="com.infotec.wb.core.db.RecUser"%>
<%@page import="com.infotec.wb.core.db.DBUser"%>
<%@page import="org.w3c.dom.Document"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="com.infotec.appfw.util.AFUtils"%>
<%@page import="com.infotec.wb.core.db.DBResourceType"%>
<%@page import="com.infotec.wb.core.db.RecResourceType"%>
<%@page import="com.infotec.wb.core.db.DBResource"%>
<%@page import="com.infotec.topicmaps.Occurrence"%>
<%@page import="com.infotec.topicmaps.Topic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.infotec.wb.core.db.RecResource"%>
<%@page import="com.infotec.wb.lib.WBResource"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Locale"%>
<%@page import="com.infotec.wb.core.Resource"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.infotec.wb.core.ResourceMgr"%>
<%@page import="java.util.Date"%>
<%@page import="com.infotec.topicmaps.bean.TopicMgr"%>
<%@page import="com.infotec.topicmaps.TopicMap"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
            String act = request.getParameter("act");
            String siteid = request.getParameter("site");

            String fechaini = request.getParameter("fechaini");
            String fechafin = request.getParameter("fechafin");

            if (null == act) {
                act = "";
            }

        %>
        <h1>Listado de Contenidos</h1>
        <script type="text/javascript">
            function valida(forma){

                //if(!forma.site.checked){
                //    alert('Debes de seleccionar por lo menos un sitio.');
                //    return false;
                // }

                var RegExPattern = /[0-9]{2}[/][0-9]{2}[/][0-9]{4}/;
                if(forma.fechaini.value==''||!forma.fechaini.value.match(RegExPattern)){
                    alert('Formato de fecha inválido, ponlo de la forma DD/MM/AAAA');
                    forma.fechaini.focus();
                    return false;
                }
                            
                var RegExPattern = /[0-9]{2}[/][0-9]{2}[/][0-9]{4}/;
                if(forma.fechafin.value==''||!forma.fechafin.value.match(RegExPattern)){
                    alert('Formato de fecha inválido, ponlo de la forma DD/MM/AAAA');
                    forma.fechafin.focus();
                    return false;
                }
                            
                return true;
            }
      
        </script>
        <form name="fstep2" id="fstep2" method="post" action="" onsubmit="return valida(this);">
            <input type="hidden" name="act" value="step1" />

            <fieldset>
                <legend>Selecciona el sitio a consultar:</legend>
                <ul>
                    <%

                        boolean radioChecked = false;
                        String strChecked = "checked";
                        //Lista de sitios existentes
                        Iterator<TopicMap> ittm = TopicMgr.getInstance().getTopicMaps();
                        while (ittm.hasNext()) {
                            TopicMap tm = (TopicMap) ittm.next();

                            if (!tm.getId().equals(TopicMgr.TM_GLOBAL)) {
                                String activo = "<font color=\"green\">Sitio activo</font>";
                                if (tm.getDbdata().getActive() == 0) {
                                    activo = "<font color=\"red\">Sitio inactivo</font>";
                                }

                                if (!radioChecked && siteid == null) {
                                    strChecked = "";
                                } else {
                                    strChecked = siteid != null && siteid.equals(tm.getId()) ? "checked" : "";
                                }

                    %>
                    <li><input type="radio" id="<%=tm.getId()%>" name="site" value="<%=tm.getId()%>" <%= strChecked%> ><label for="<%=tm.getId()%>"><%=tm.getDbdata().getTitle()%>&nbsp;<i><%=activo%></i></label></li>
                        <%
                                    if (!radioChecked) {
                                        radioChecked = true;
                                        strChecked = "";
                                    }
                                }
                            }
                        %>

                </ul>
            </fieldset>
            <fieldset>
                <label for="fechaini">Fecha inicial (DD/MM/AAAA)</label><input type="text" id="fechaini" name="fechaini" value="<%=fechaini != null ? fechaini : ""%>"/><br/> 
                <label for="fechafin">Fecha final   (DD/MM/AAAA)</label><input type="text" id="fechafin" name="fechafin" value="<%=fechafin != null ? fechafin : ""%>"/><br/>
            </fieldset>
            <fieldset>
                <button type="submit" name="bsend" id="bsend">Enviar</button>
            </fieldset>
        </form>
        <%
            if ("step1".equals(act) && siteid != null && null != fechaini && null != fechafin) {

                int yearini = 0;
                int monthini = 0;
                int dateini = 0;
                int yearfin = 0;
                int monthfin = 0;
                int datefin = 0;

                dateini = Integer.parseInt(fechaini.substring(0, fechaini.indexOf("/")));
                monthini = Integer.parseInt(fechaini.substring(fechaini.indexOf("/") + 1, fechaini.lastIndexOf("/")));
                yearini = Integer.parseInt(fechaini.substring(fechaini.lastIndexOf("/") + 1));

                //System.out.println(dateini + "/" + monthini + "/" + yearini);
                HashMap<String, String> hm_priority = doPriorityValues();
                Calendar calendario = Calendar.getInstance();
                calendario.set(yearini, monthini - 1, dateini, 0, 0, 0);


                datefin = Integer.parseInt(fechafin.substring(0, fechafin.indexOf("/")));
                monthfin = Integer.parseInt(fechafin.substring(fechafin.indexOf("/") + 1, fechafin.lastIndexOf("/")));
                yearfin = Integer.parseInt(fechafin.substring(fechafin.lastIndexOf("/") + 1));

                //System.out.println(datefin + "/" + monthfin + "/" + yearfin);

                Calendar calendario2 = Calendar.getInstance();
                calendario2.set(yearfin, monthfin - 1, datefin, 23, 59, 59);

                TopicMap tm = TopicMgr.getInstance().getTopicMap(siteid);
                HashMap hmr = ResourceMgr.getInstance().getResources(tm.getId());
                int typeContent = Resource.ResType_CONTENT;
                int resType = 0;
                String resTypeName = "";

                Calendar dhoy = Calendar.getInstance();

                int finded = 0;
        %>
        <fieldset ><legend>Resultado...</legend>
            <br/><br/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Identificador</th>
                        <th>Contenido</th>
                        <th>Prioritaria</th>
                        <th>Activo</th>
                        <th>Tipo de contenido</th>
                        <th>Ubicación del contenido</th>
                        <th>Dirección WEB</th>
                        <th>Creado</th>
                        <th>Usuario Creador</th>
                    </tr>
                </thead>
                <tbody>


                    <%

                        String str_url = "";
                        String str_active = "";
                        String str_path = "";
                        String tp_url = "";
                        long l_dpp = 0;

                        boolean band = false;
                        Iterator iTp = tm.getHome().getChildAll().iterator();
                        while (iTp.hasNext()) {
                            band = false;
                            Topic tp_child = (Topic) iTp.next();
                            Iterator it_child = tp_child.getOccurrencesOfType("REC_WBContent");
                            while (it_child.hasNext()) {
                                if (!band) {
                                    HashMap arg = new HashMap();
                                    arg.put(new String("separator"), new String("\\"));
                                    arg.put(new String("links"), new String("false"));
                                    str_path = tp_child.getPath(arg);
                                    tp_url = tp_child.getUrl();
                                    band = true;
                                }
                                Occurrence oc_child = (Occurrence) it_child.next();

                                if (oc_child != null) {
                                    l_dpp = Long.parseLong(oc_child.getResourceData());
                                    RecResource recRes = DBResource.getInstance().getResource(tm.getId(), l_dpp);
                                    if (recRes != null) {
                                        resType = recRes.getResourceType().getType();
                                        resTypeName = recRes.getResourceType().getName();

                                        RecResourceType recObj = DBResourceType.getInstance().getResourceType(recRes.getTypeMap(), recRes.getType());

                                        String str_type = recObj.getName();
                                        if (typeContent == resType) {  //str_type.equals(p_content_type)

                                            if (str_type.equals("WBUrlContent") || str_type.equals("FrameContent")) {
                                                Document domxmlRec = AFUtils.getInstance().XmltoDom(recRes.getXml());
                                                if (domxmlRec != null) {
                                                    NodeList archivo = domxmlRec.getElementsByTagName("url");
                                                    if (archivo.getLength() > 0) {
                                                        str_url = archivo.item(0).getChildNodes().item(0).getNodeValue();
                                                    }
                                                }
                                            }

                                            if (recRes.getCreated().getTime() >= calendario.getTimeInMillis() && recRes.getCreated().getTime() <= calendario2.getTimeInMillis()) {
                                                if (typeContent == resType) {
                                                    String str_nombre = recRes.getTitle();
                                                    String str_contentname = AFUtils.decode(str_nombre, "UTF-8");
                                                    String str_contentid = oc_child.getResourceData();
                                                    int i_priority = recRes.getPriority();
                                                    int i_active = oc_child.getDbdata().getActive();
                                                    if (i_active == 1) {
                                                        str_active = "Si";
                                                    } else {
                                                        str_active = "No";
                                                    }

                                                    RecUser rusr = DBUser.getInstance().getUserById(recRes.getIdAdm());

                                                    String usr_name = rusr != null ? rusr.getName() : "N/A";


                                                    System.out.println("Recurso de tipo CONTENT:" + recRes.getId());
                                                    finded++;

                    %>
                    <tr>
                        <td><%=str_contentid%></td>
                        <td><%=str_contentname%></td>
                        <td><%=hm_priority.get("" + recRes.getPriority()) != null ? hm_priority.get("" + recRes.getPriority()) : "N/A"%></td>
                        <td><%=str_active%></td> 
                        <td><%=str_type%></td>
                        <td><%=str_path%></td>
                        <td><%=tp_url%></td>
                        <td><%=sdf.format(recRes.getCreated())%></td>
                        <td><%=usr_name%></td>
                    </tr>


                    <%
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (finded > 0) {
                    %>
                    <tr><td colspan="9"><strong>Se encontraron <%=finded%> contenidos en ese rango de fechas.</strong></td></tr>
                    <%        }


                        if (finded == 0) {
                    %>
                    <tr><td colspan="9"><strong>No se encontraron contenidos en ese rango de fechas.</strong></td></tr>
                    <%        }
                        }
                    %>
                </tbody>
            </table>
        </fieldset>
    </body>
</html>

<%!
    public HashMap doPriorityValues() {
        HashMap hm_values = new HashMap();

        hm_values.put("1", "default");
        hm_values.put("2", "baja");
        hm_values.put("3", "media");
        hm_values.put("4", "alta");
        hm_values.put("5", "prioritaria");

        return hm_values;
    }

%>
