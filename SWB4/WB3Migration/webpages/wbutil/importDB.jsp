<%@page contentType="text/html"%>
<%@page import="com.infotec.wb.core.db.*"%>
<%@page import="com.infotec.wb.core.db.RecAdmFilter"%>
<%@page import="com.infotec.wb.core.*"%>
<%@page import="com.infotec.wb.util.*"%>
<%@page import="com.infotec.topicmaps.*"%>
<%@page import="com.infotec.topicmaps.bean.*"%>
<%@page import="com.infotec.appfw.lib.DBPool.*"%>
<%@page import="com.infotec.appfw.util.*"%>
<%@page import="com.infotec.wb.services.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.infotec.topicmaps.db.*"%>
<%@page import="org.w3c.dom.*"%>

<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>

<html>
    <head><title>Importar tablas de WB2 a WB3</title></head>
    <body>

        <%-- <jsp:useBean id="beanInstanceName" scope="session" class="package.class" /> --%>
        <%-- <jsp:getProperty name="beanInstanceName"  property="propertyName" /> --%>
        <font size=1 face="verdana,arial">
            <table cellpadding=1 cellspacing=0 border=1>
                <tr><th colspan=2 > Importación de base de datos WB2 a WB3</th></tr>
                <tr><td colspan=2 align=center>

                    <%

                        /*
                         * Utilería que ayuda la importación de un sitio de WB ver 2.0 a WB ver 3.0
                         * Indicando la ruta en donde se encuentran los archivos y los nombres de
                         * las conexiones a las base de datos (origen y destino)
                         *
                         * Esta herramienta copia la información encontrada en la base de datos de
                         * la versión 2.0 y la copia a la base de datos de la versión 3.0
                         */

                        String con_name_1 = request.getParameter("conexion1");
                        String con_name_2 = request.getParameter("conexion2");
                        String installPath = request.getParameter("installPath");
                        String webworkPath = request.getParameter("webworkPath");
                        String tm = request.getParameter("tm");
                        String repo = request.getParameter("repo");
                        if(repo==null) repo="wb";
                        if(tm==null) tm="WBAGlobal";
                        boolean con1OK=false;
                        boolean con2OK=false;

                        if(installPath==null) installPath="c:\\wb2";
                        if(webworkPath==null) webworkPath="/work";

                        HashMap hmavailable = (HashMap) request.getSession().getAttribute("Disponible");
                        HashMap hmimported = (HashMap) request.getSession().getAttribute("Importado");
                        HashMap hmTM = (HashMap) request.getSession().getAttribute("topicmap");
                        HashMap hmResource = (HashMap) request.getSession().getAttribute("Resource");
                        HashMap hmDevice = (HashMap) request.getSession().getAttribute("Device");
                        HashMap hmDNS = (HashMap) request.getSession().getAttribute("DNS");

                        String act = request.getParameter("act");
                        if(act==null) act="";


                        if(con_name_1!=null&&con_name_1.trim().length()>0) con1OK=true;
                        else con_name_1="";
                        if(con_name_2!=null&&con_name_2.trim().length()>0) con2OK=true;
                        else con_name_2="";

                        if(!con1OK||!con2OK) {
                            out.println("<form name=\"frm\" method=post>");
                            out.println("<table cellpadding=1 cellspacing=0 border=1 width='100%'>");
                            out.println("<tr><td>Nombre de conexión a DB origen (WB2):</td>");
                            out.println("<td><input type=text name=conexion1 value=\""+con_name_1+"\"></td></tr>");
                            out.println("<tr><td>Nombre de conexión a DB destino (WB3):</td>");
                            out.println("<td><input type=text name=conexion2 value=\""+con_name_2+"\"></td></tr>");
                            out.println("<tr><td>Ruta \"work\" en donde está instalado (WB2):</td>");
                            out.println("<td><input type=text name=installPath value=\""+installPath+"\"> * agregar diagonal al final</td></tr>");
                            out.println("<tr><td>Ruta (Web WORK path):</td>");
                            out.println("<td><input type=text name=webworkPath value=\""+webworkPath+"\"></td></tr>");
                            out.println("<tr><td colspan=2 align=center><input type=submit value=\"enviar\"></td></tr>");
                            out.println("</table>");
                            out.println("</form>");
                        } else{
                            if(act.equals("")){
                                if(hmavailable==null){
                                    hmavailable=loadSourceRecords(con_name_1);
                                    request.getSession().setAttribute("Disponible",hmavailable);
                                }
                                if(hmimported==null){
                                    hmimported=loadDestinationRecords(con_name_2);
                                    request.getSession().setAttribute("Importado",hmimported);
                                }
                                if(hmTM==null){
                                    hmTM=loadTM(con_name_1);
                                    request.getSession().setAttribute("topicmap",hmTM);
                                }
                                if(hmResource==null){
                                    hmResource=loadResource();
                                    request.getSession().setAttribute("Resource",hmResource);
                                }
                                if(hmDevice==null){
                                    hmDevice=loadDevice();
                                    request.getSession().setAttribute("Device",hmDevice);
                                }
                                //if(hmDNS==null){
                                hmDNS=loadDNS(con_name_2);
                                request.getSession().setAttribute("DNS",hmDNS);
                                //}
                                String valor="";
                                out.println("<form name=\"frmPass\" method=post >");
                                out.println("<input type=hidden name=act value=import>");
                                out.println("<input type=hidden name=conexion1 value=\""+con_name_1+"\">");
                                out.println("<input type=hidden name=conexion2 value=\""+con_name_2+"\">");
                                out.println("<input type=hidden name=installPath value=\""+installPath+"\">");
                                out.println("<input type=hidden name=webworkPath value=\""+webworkPath+"\">");
                                out.println("<table cellpadding=1 cellspacing=0 border=1>");
                                out.println("<tr><td colspan=3 align=center>Selecciona a donde se migrar&aacute;n los cat&aacute;logos:</td></tr>");

                                Iterator iteTM = hmTM.keySet().iterator();
                                String strSelect = "";
                                //String TMselected = tm;
                                //if(TMselected==null) TMselected = "WBAGlobal";
                                if(tm.equals("WBAGlobal")) strSelect = "checked";
                                out.println("<tr><td colspan=3><input type=radio name=tm value=\"WBAGlobal\" "+strSelect+">&nbsp;Global</td></tr>");

                                while(iteTM.hasNext()) {
                                    String strTM = (String)iteTM.next();
                                    strSelect="";
                                    if(tm.equals(strTM)) strSelect="checked";
                                    out.println("<tr><td colspan=3><input type=radio name=tm value=\""+strTM+"\" "+strSelect+">&nbsp;"+hmTM.get(strTM)+"</td></tr>");
                                }
                                HashMap hmRepo = loadRepositories();
                                out.println("<tr><td colspan=2 align=center>Selecciona el repositorio en donde se migrar&aacute;n los usuarios:</td><td><select name=repo>");

                                Iterator iteRepo = hmRepo.keySet().iterator();
                                strSelect = "";

                                while(iteRepo.hasNext()) {
                                    String strRepo = (String)iteRepo.next();
                                    strSelect="";
                                    if(repo.equals(strRepo)) strSelect="checked";
                                    out.println("<option value=\""+strRepo+"\" "+strSelect+">"+strRepo+"</option>");
                                }
                                out.println("</select></td></tr>");
                                out.println("<tr><th colspan=3 align=center><hr noshade size=1>Seleccionar las tablas a importar</th></tr>");
                                out.println("<tr><th>");
                                out.println("Tabla");
                                out.println("</th><th>");
                                out.println("A importar");
                                out.println("</th><th>");
                                out.println("Importados");
                                out.println("</th></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbadmlog\">&nbsp;wbadmlog<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbadmlog")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbadmlog")!=null)) ? (String)hmimported.get("wbadmlog"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbassociation\">&nbsp;wbassociation<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbassociation")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbassociation")!=null)) ? (String)hmimported.get("wbassociation"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbcamp\">&nbsp;wbcamp<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbcamp")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbcamp")!=null)) ? (String)hmimported.get("wbcamp"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                //out.println("<tr><td>");
                                //out.println("<input type=checkbox name=tb value=\"wbdbsync\">&nbsp;wbdbsync<br>");
                                //out.println("</td><td>");
                                //out.println("("+hmavailable.get("wbdbsync")+")");
                                //out.println("</td><td>");
                                //valor=((hmimported!=null) && (hmimported.get("wbdbsync")!=null)) ? (String)hmimported.get("wbdbsync"):"0";
                                //out.println("("+valor+")");
                                //out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbdevice\">&nbsp;wbdevice<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbdevice")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbdevice")!=null)) ? (String)hmimported.get("wbdevice"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbdns\">&nbsp;wbdns<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbdns")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbdns")!=null)) ? (String)hmimported.get("wbdns"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbgrptemplate\">&nbsp;wbgrptemplate<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbgrptemplate")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbgrptemplate")!=null)) ? (String)hmimported.get("wbgrptemplate"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbipfilter\">&nbsp;wbipfilter<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbipfilter")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbipfilter")!=null)) ? (String)hmimported.get("wbipfilter"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wblanguage\">&nbsp;wblanguage<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wblanguage")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wblanguage")!=null)) ? (String)hmimported.get("wblanguage"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wboccurrence\">&nbsp;wboccurrence<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wboccurrence")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wboccurrence")!=null)) ? (String)hmimported.get("wboccurrence"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbpflow\">&nbsp;wbpflow");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbpflow")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbpflow")!=null)) ? (String)hmimported.get("wbpflow"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbreshits\">&nbsp;wbreshits<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbreshits")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbreshits")!=null)) ? (String)hmimported.get("wbreshits"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbresource\">&nbsp;wbresource&nbsp;<input type=checkbox name=\"resetcontent\" value=\"1\">&nbsp;reinicio versiones<br>");
                                //out.println("<input type=checkbox name=tb value=\"wbresource\">&nbsp;wbresource<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbresource")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbresource")!=null)) ? (String)hmimported.get("wbresource"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbresourcedata\">&nbsp;wbresourcedata<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbresourcedata")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbresourcedata")!=null)) ? (String)hmimported.get("wbresourcedata"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbresourcetype\">&nbsp;wbresourcetype<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbresourcetype")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbresourcetype")!=null)) ? (String)hmimported.get("wbresourcetype"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbrule\">&nbsp;wbrule<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbrule")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbrule")!=null)) ? (String)hmimported.get("wbrule"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbsubtype\">&nbsp;wbsubtype<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbsubtype")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbsubtype")!=null)) ? (String)hmimported.get("wbsubtype"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbtemplate\">&nbsp;wbtemplate&nbsp;<input type=checkbox name=\"resettemplate\" value=\"1\">&nbsp;reinicio versiones<br>");
                                //out.println("<input type=checkbox name=tb value=\"wbtemplate\">&nbsp;wbtemplate<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbtemplate")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbtemplate")!=null)) ? (String)hmimported.get("wbtemplate"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbtopic\">&nbsp;wbtopic<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbtopic")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbtopic")!=null)) ? (String)hmimported.get("wbtopic"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbtopicmap\">&nbsp;wbtopicmap<br>");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbtopicmap")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbtopicmap")!=null)) ? (String)hmimported.get("wbtopicmap"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td>");
                                out.println("<input type=checkbox name=tb value=\"wbuser\">&nbsp;wbuser");
                                out.println("<input type=checkbox name=tb value=\"wbadmuser\">&nbsp;wbadmuser");
                                out.println("</td><td>");
                                out.println("("+hmavailable.get("wbuser")+")");
                                out.println("</td><td>");
                                valor=((hmimported!=null) && (hmimported.get("wbuser")!=null)) ? (String)hmimported.get("wbuser"):"0";
                                out.println("("+valor+")");
                                out.println("</td></tr>");
                                out.println("<tr><td colspan=3 align=center><input type=submit value=\"Importar\"></td></tr>");
                                out.println("</table>");
                                out.println("</form>");
                            } else if(act.equals("import")) {
                                out.println("ORIGEN -> "+request.getParameter("conexion1")+" a DESTINO -> "+ request.getParameter("conexion2")+"<br><br>");
                                out.println("Tablas a migrar ... <br>");
                                String tabla="";
                                int num_reg=0;
                                try {
                                    Connection conn1 = AFUtils.getInstance().getDBConnection(con_name_1,"Conexión origen WB2 migración");
                                    Connection conn2 = AFUtils.getInstance().getDBConnection(con_name_2,"Conexión destino WB3 migración");
                                    if(hmimported==null) hmimported = new HashMap();
                                    String[] tbs = request.getParameterValues("tb");
                                    for(int i=0; i<tbs.length; i++) {
                                        tabla = tbs[i].trim();
                                        out.println("&nbsp;&nbsp;&nbsp;-&nbsp;"+tabla);
                                        if("wbadmlog".equals(tabla)) {
                                            String strSQL = "select * from wbadmlog where topicmapid=?";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbadmlog";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbadmlog (wbuser, action, dbobject, dbobjid, topicmapid, topicid, description, wbdate) values (?,?,?,?,?,?,?,?)");
                                                pst2.setString(1,rs1.getLong("wbuser")+"_"+"wb");
                                                pst2.setString(2,rs1.getString("action"));
                                                pst2.setString(3,rs1.getString("dbobject"));
                                                pst2.setLong(4,rs1.getLong("dbobjid"));
                                                pst2.setString(5,rs1.getString("topicmapid"));
                                                pst2.setString(6,rs1.getString("topicid"));
                                                pst2.setString(7,rs1.getString("description"));
                                                pst2.setTimestamp(8,rs1.getTimestamp("wbdate"));
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;

                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbassociation".equals(tabla)) {
                                            String strSQL = "select * from wbassociation where idtm=?";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbassociation";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbassociation (id, idtm, xml, lastupdate) values (?,?,?,?)");
                                                pst2.setString(1,rs1.getString("id"));
                                                pst2.setString(2,rs1.getString("idtm"));
                                                String strxml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1"); //rs1.getString("xml");
                                                if(strxml==null)
                                                    pst2.setString(3,null);
                                                else
                                                    pst2.setAsciiStream(3,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                                                pst2.setTimestamp(4,rs1.getTimestamp("lastupdate"));
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbdbsync".equals(tabla)) {
                                            PreparedStatement pst1 = conn1.prepareStatement("select * from wbdbsync");
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbdbsync (dbtable, action, idint, idstr, wbdate) values (?,?,?,?,?)");
                                                pst2.setString(1,rs1.getString("dbtable"));
                                                pst2.setString(2,rs1.getString("action"));
                                                pst2.setLong(3,rs1.getLong("idint"));
                                                pst2.setString(4,rs1.getString("idstr"));
                                                pst2.setTimestamp(5,rs1.getTimestamp("wbdate"));
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbdevice".equals(tabla)) {
                                            PreparedStatement pst1 = conn1.prepareStatement("select * from wbdevice");
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                int id = rs1.getInt("id");
                                                if(hmDevice.get(Integer.toString(id))==null){
                                                    PreparedStatement pst2 = conn2.prepareStatement("insert into wbdevice (id, name, strmatch, description, lastupdate) values (?,?,?,?,?)");
                                                    pst2.setInt(1,id);
                                                    pst2.setString(2,rs1.getString("name"));
                                                    pst2.setString(3,rs1.getString("strmatch"));
                                                    pst2.setString(4,rs1.getString("description"));
                                                    pst2.setTimestamp(5,rs1.getTimestamp("lastupdate"));
                                                    pst2.executeUpdate();
                                                    pst2.close();
                                                    pst2=null;
                                                    num_reg++;
                                                }
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbreshits".equals(tabla)) {
                                            String strSQL = "select * from wbreshits where topicmap=? order by wbdate asc";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbreshits";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbreshits (wbdate, topicmap, idaux, type, hits) values (?,?,?,?,?)");
                                                pst2.setTimestamp(1,rs1.getTimestamp("wbdate"));
                                                pst2.setString(2,rs1.getString("topicmap"));
                                                pst2.setString(3,rs1.getString("idaux"));
                                                pst2.setInt(4,rs1.getInt("type"));
                                                pst2.setLong(5,rs1.getLong("hits"));
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbdns".equals(tabla)) {
                                            String strSQL = "select * from wbdns where topicmap=?";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbdns";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                String str_dns = rs1.getString("dns");
                                                if(hmDNS.get(str_dns.trim())==null) {
                                                    PreparedStatement pst2 = conn2.prepareStatement("insert into wbdns (id, idtm, dns, topicmap, topic, isdefault, lastupdate) values (?,?,?,?,?,?,?)");
                                                    pst2.setInt(1,rs1.getInt("id"));
                                                    pst2.setString(2,tm);
                                                    pst2.setString(3,str_dns);
                                                    pst2.setString(4,rs1.getString("topicmap"));
                                                    pst2.setString(5,rs1.getString("topic"));
                                                    pst2.setInt(6,rs1.getInt("isdefault"));
                                                    pst2.setTimestamp(7,rs1.getTimestamp("lastupdate"));
                                                    pst2.executeUpdate();
                                                    pst2.close();
                                                    pst2=null;
                                                    num_reg++;
                                                    // falta ir agregando cada dns insertado
                                                }
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbtopicmap".equals(tabla)) {
                                            String strSQL = "select * from wbtopicmap where id=?";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbtopicmap";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbtopicmap (id, idadm, title, home, lang, description, active, xml, created, lastupdate, deleted, system, repository,indexer) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                pst2.setString(1,rs1.getString("id"));
                                                pst2.setString(2,rs1.getLong("idadm")+"_"+repo.trim());
                                                pst2.setString(3,rs1.getString("title"));
                                                pst2.setString(4,rs1.getString("home"));
                                                pst2.setString(5,rs1.getString("lang"));
                                                pst2.setString(6,rs1.getString("description"));
                                                pst2.setInt(7,rs1.getInt("active"));
                                                String strxml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1"); //rs1.getString("xml");
                                                if(strxml==null)
                                                    pst2.setString(8,null);
                                                else
                                                    pst2.setAsciiStream(8,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                                                pst2.setTimestamp(9,rs1.getTimestamp("created"));
                                                pst2.setTimestamp(10,rs1.getTimestamp("lastupdate"));
                                                pst2.setInt(11,rs1.getInt("borrado"));
                                                pst2.setInt(12,1);
                                                pst2.setString(13,repo.trim());
                                                pst2.setString(14,"wb");
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbtopic".equals(tabla)) {
                                            String strSQL = "select * from wbtopic where idtm=?";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wbtopic";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                //PreparedStatement pst2 = conn2.prepareStatement("insert into wbtopic (id, idtm, idadm, active, xml, xmlconf, created, system, lastupdate, deleted, views,virtual,indexable,hidden) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbtopic (id, idtm, idadm, active, xml, xmlconf, created, system, lastupdate, deleted, views,indexable,hidden) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                                pst2.setString(1,rs1.getString("id"));
                                                pst2.setString(2,rs1.getString("idtm"));
                                                pst2.setString(3,rs1.getLong("idadm")+"_"+repo.trim());
                                                pst2.setInt(4,rs1.getInt("active"));
                                                String strxml = null;
                                                try{
                                                    strxml =  AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1"); //rs1.getString("xml");
                                                }catch(Exception excp_ion){strxml=null;}
                                                if(strxml==null)
                                                    pst2.setString(5,null);
                                                else
                                                    pst2.setAsciiStream(5,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());

                                                String xml_conf=null;
                                                try{
                                                        xml_conf = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xmlconf"));
                                                }catch(Exception excep){ xml_conf=null;}

                                                if(null==xml_conf)pst2.setString(6,null);
                                                else pst2.setAsciiStream(6,AFUtils.getInstance().getStreamFromString(xml_conf),xml_conf.length());
                                                pst2.setTimestamp(7,rs1.getTimestamp("created"));
                                                pst2.setInt(8,0);
                                                pst2.setTimestamp(9,rs1.getTimestamp("lastupdate"));
                                                pst2.setInt(10,rs1.getInt("borrado"));
                                                pst2.setLong(11,rs1.getLong("views"));
                                                //pst2.setInt(12,0);
                                                pst2.setInt(12,1);
                                                pst2.setInt(13,0);
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wboccurrence".equals(tabla)) {
                                            String strSQL = "select * from wboccurrence where idtm=? order by lastupdate desc";
                                            if("WBAGlobal".equals(tm)) strSQL = "select * from wboccurrence";
                                            PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                                            if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;

                                            while(rs1.next()) {
                                                String strxml = null;
                                                try{
                                                    strxml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml")); // rs1.getString("xml");
                                                }catch(Exception exocc){strxml = null;}
                                                java.sql.Date hoy = new java.sql.Date(System.currentTimeMillis());
                                                if(strxml!=null&&strxml.indexOf("_parent")==-1) {
                                                    PreparedStatement pst2 = conn2.prepareStatement("insert into wboccurrence (id, idtm, idtp, active, xml, lastupdate, deleted, flow, step, status, flowtime, priority) values (?,?,?,?,?,?,?,?,?,?,?,?)");
                                                    pst2.setString(1,rs1.getString("id"));
                                                    pst2.setString(2,rs1.getString("idtm"));
                                                    pst2.setString(3,rs1.getString("idtp"));
                                                    pst2.setInt(4,rs1.getInt("active"));
                                                    if(strxml==null)
                                                        pst2.setString(5,null);
                                                    else {
                                                        if("WBAGlobal".equals(tm)) {
                                                            if(strxml.indexOf("REC_WBContent")!=-1 || strxml.indexOf("CNF_WBTemplate")!=-1 || strxml.indexOf("CNF_WBRule")!=-1)
                                                                strxml = strxml.substring(0,strxml.indexOf("</resourceData>"))+"|WBAGlobal"+strxml.substring(strxml.indexOf("</resourceData>"));
                                                        }
                                                        pst2.setAsciiStream(5,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                                                    }
                                                    pst2.setTimestamp(6,rs1.getTimestamp("lastupdate"));
                                                    pst2.setInt(7,rs1.getInt("borrado"));
                                                    pst2.setString(8,null);
                                                    pst2.setString(9,null);
                                                    pst2.setInt(10,0);
                                                    pst2.setDate(11,hoy);
                                                    pst2.setInt(12,3); //1
                                                    pst2.executeUpdate();
                                                    pst2.close();
                                                    pst2=null;
                                                    num_reg++;
                                                }

                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wblanguage".equals(tabla)) {
                                            PreparedStatement pst1 = conn1.prepareStatement("select * from wblanguage");
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wblanguage (id, idtm, lang, title, lastupdate) values (?,?,?,?,?)");
                                                pst2.setInt(1,num_reg+1);
                                                pst2.setString(2,tm);
                                                pst2.setString(3,rs1.getString("id"));
                                                pst2.setString(4,rs1.getString("title"));
                                                pst2.setTimestamp(5,rs1.getTimestamp("lastupdate"));
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbrule".equals(tabla)) {
                                            PreparedStatement pst1 = conn1.prepareStatement("select * from wbrule");
                                            ResultSet rs1=pst1.executeQuery();
                                            num_reg=0;
                                            while(rs1.next()) {
                                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbrule (id, idtm, title, description, created, xml, lastupdate, idadm) values (?,?,?,?,?,?,?,?)");
                                                pst2.setLong(1,rs1.getLong("id"));
                                                pst2.setString(2,tm);
                                                pst2.setString(3,rs1.getString("title"));
                                                pst2.setString(4,rs1.getString("description"));
                                                pst2.setTimestamp(5,rs1.getTimestamp("created"));
                                                String strxml = null;
                                                try{
                                                    strxml=AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1"); //rs1.getString("xml"); //xml
                                                }catch(Exception e20){strxml = null;}
                                                if(strxml==null) {
                                                    pst2.setString(6,null);
                                                } else {
                                                    strxml = strxml.replaceAll("language","LANGUAGE");
                                                    strxml = strxml.replaceAll("TIPOUSU","usertype");
                                                    pst2.setAsciiStream(6,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                                                }
                                                pst2.setTimestamp(7,rs1.getTimestamp("lastupdate"));
                                                pst2.setString(8,""+rs1.getInt("idadm")+"_"+repo.trim());
                                                pst2.executeUpdate();
                                                pst2.close();
                                                pst2=null;
                                                num_reg++;
                                            }
                                            rs1.close();
                                            pst1.close();
                                            rs1=null;
                                            pst1=null;
                                            out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                                        } else if("wbpflow".equals(tabla)) {
                        %>
                        <jsp:include page="migrateworkflow.jsp"/>
                        <%
                        /*PreparedStatement pst1 = conn1.prepareStatement("select * from wbpflow");
                        ResultSet rs1=pst1.executeQuery();
                        num_reg=0;
                        while(rs1.next())
                        {
                        // Falta cambiar el xml a la nueva version de WB3

                        PreparedStatement pst2 = conn2.prepareStatement("insert into wbpflow (id, idtm, title, description, created, xml, lastupdate, idadm) values (?,?,?,?,?,?,?,?)");
                        pst2.setLong(1,rs1.getLong("id"));
                        pst2.setString(2,tm);
                        pst2.setString(3,rs1.getString("title"));
                        pst2.setString(4,rs1.getString("description"));
                        pst2.setTimestamp(5,rs1.getTimestamp("created"));
                        String strxml = rs1.getString("xml"); //xml
                        if(strxml==null)
                        pst2.setString(6,null);
                        else
                        pst2.setAsciiStream(6,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                        pst2.setTimestamp(7,rs1.getTimestamp("lastupdate"));
                        pst2.setString(8,""+rs1.getInt("idadm")+"_wb");
                        pst2.executeUpdate();
                        pst2.close();
                        pst2=null;
                        num_reg++;
                        }
                        rs1.close();
                        pst1.close();
                        rs1=null;
                        pst1=null;
                        out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                        */
                        }
                        else if("wbtemplate".equals(tabla))
                        {
                            boolean reinicioversiontemplate = false;
                            if(request.getParameter("resettemplate")!=null&&request.getParameter("resettemplate").equals("1")) reinicioversiontemplate = true;
                            PreparedStatement pst1 = conn1.prepareStatement("select * from wbtemplate order by id asc ");
                            ResultSet rs1=pst1.executeQuery();
                            num_reg=0;
                            while(rs1.next())
                            {
                                PreparedStatement pst2 = conn2.prepareStatement("insert into wbtemplate (id, idtm, title, description, filename, created, active, grpid, actualversion, lastversion, xml, lastupdate, deleted, idadm) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                long id = rs1.getLong("id");
                                pst2.setLong(1,id);
                                pst2.setString(2,tm);
                                pst2.setString(3,rs1.getString("title"));
                                pst2.setString(4,rs1.getString("description"));
                                pst2.setString(5,rs1.getString("filename"));
                                pst2.setTimestamp(6,rs1.getTimestamp("created"));
                                pst2.setInt(7,rs1.getInt("active"));
                                pst2.setLong(8,rs1.getLong("grpid"));


                                int versionactual = rs1.getInt("actualversion");
                                int ultimaversion = rs1.getInt("lastversion");

                                int versionactualtmp = versionactual;
                                int ultimaversiontmp = versionactual;

                                if(reinicioversiontemplate)
                                {
                                    // para reinicio de versiones del template
                                    if(versionactual>1)
                                    {
                                        versionactualtmp = 2;
                                        ultimaversiontmp = 2;
                                    }
                                    else
                                    {
                                        ultimaversiontmp = ultimaversion;
                                    }
                                }
                                else
                                {
                                    versionactualtmp=versionactual;
                                    ultimaversiontmp=ultimaversion;
                                }
                                pst2.setInt(9,versionactualtmp);
                                pst2.setInt(10,ultimaversiontmp);

                                String strxml =null;
                                try{
                                    strxml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1"); //xml
                                }   catch(Exception etemp){strxml=null;}
                                if(strxml==null)
                                    pst2.setString(11,null);
                                else
                                    pst2.setAsciiStream(11,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                                pst2.setTimestamp(12,rs1.getTimestamp("lastupdate"));
                                pst2.setInt(13,rs1.getInt("borrado"));
                                pst2.setString(14,""+rs1.getInt("idadm")+"_"+repo.trim());
                                pst2.executeUpdate();
                                pst2.close();
                                pst2=null;
                                num_reg++;

                            // falta llamar al servicio para pasar archivos de WB2 a WB3 referente a los templates
                            // revisar código de reinicio de versiones

                            if(installPath!=null&&webworkPath!=null&&installPath.trim().length()>0&&webworkPath.trim().length()>0&&reinicioversiontemplate)
                            {
                                MigrationWB2_WB3  mwb2wb3 = new MigrationWB2_WB3();
                                // copiando version actual
                                if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/templates/"+id+"/"+versionactual+"/","/sites/"+tm+"/templates/"+id+"/"+versionactualtmp+"/"))
                                {
                                    //System.out.println("No se pudieron copiar archivos del template con id: "+id);
                                }
                                // copiando la penúltima versión si existe
                                if(versionactualtmp==2)
                                {
                                    if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/templates/"+id+"/"+(versionactual-1)+"/","/sites/"+tm+"/templates/"+id+"/1/"))
                                    {
                                    //System.out.println("No se pudieron copiar archivos del template con id: "+id);
                                    }
                                }
                            }
                            else
                            {
                                //  VERSION ANTERIOR SIN REINICIO DE VERSIONES
                                if(installPath!=null&&webworkPath!=null&&installPath.trim().length()>0&&webworkPath.trim().length()>0)
                                {
                                    MigrationWB2_WB3  mwb2wb3 = new MigrationWB2_WB3();
                                    if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/templates/"+id+"/","/sites/"+tm+"/templates/"+id+"/"))
                                    {
                                        //System.out.println("No se pudieron copiar archivos del template con id: "+id);
                                        //mwb2wb3.migrateFiles(sourceWorkPath,sourceWebWorkPath,sourceDirectory,targetDirectory);
                                    }
                                }
                            }
                        }

                        rs1.close();
                        pst1.close();
                        rs1=null;
                        pst1=null;
                        out.println(" ... ok -> "+num_reg+" registros copiados<br>");

                    }
                    else if("wbresource".equals(tabla))
                    {
                        boolean reinicioversioncontent = false;
                        if(request.getParameter("resetcontent")!=null&&request.getParameter("resetcontent").equals("1")) reinicioversioncontent = true;
                        PreparedStatement pstArray = conn1.prepareStatement("select id from wbresource");
                        ResultSet rsArray=pstArray.executeQuery();
                        ArrayList arrayRes = new ArrayList();
                        while(rsArray.next())
                        {
                            long arrID = rsArray.getLong("id");
                            arrayRes.add(new Long(arrID));
                        }
                        rsArray.close();
                        pstArray.close();

                        PreparedStatement pstXML = conn1.prepareStatement("select xml from wboccurrence where idtm<>?");
                        pstXML.setString(1,tm);
                        ResultSet rsXML=pstXML.executeQuery();
                        while(rsXML.next())
                        {
                            try{
                                String strxml = AFUtils.getInstance().readInputStream(rsXML.getAsciiStream("xml")); //rsXML.getString("xml");
                                if(strxml.indexOf("REC_WBContent")!=-1)
                                {
                                    String resid = strxml.substring(strxml.indexOf("<resourceData>")+14,strxml.indexOf("</resourceData>"));
                                    arrayRes.remove(new Long(resid));
                                }
                            }
                        catch(Exception e){AFUtils.log(e,"Error al eliminar un valor del arraylist - migracion wb2 a wb3 tabla WBRESOURCE");}
                    }
                    rsXML.close();
                    pstXML.close();
                    // se agregó la parte de contenidos activos únicamente
                    //PreparedStatement pst1 = conn1.prepareStatement("select * from wbresource where active=? order by id asc");
                    //pst1.setInt(1,1);
                    // para que pase todos los contenidos, activos e inactivos utilizar
                    PreparedStatement pst1 = conn1.prepareStatement("select * from wbresource order by id asc");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    String strTypeMap=null;
                    while(rs1.next())
                    {
                    long id =rs1.getLong("id");
                    boolean esContenido=false;
                    if(arrayRes.contains(new Long(id)))
                    {

                    String strxmlconf =  null;
                    String strxml = null;
                    try{
                        strxmlconf =  AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xmlconf")); //rs1.getString("xmlconf");
                    }catch(Exception e11){strxmlconf=null;}
                    try{
                        strxml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml")); // rs1.getString("xml"); //xml
                    }catch(Exception e12){strxml=null;}
                    // Si el recurso es de estrategia poner WBAGlobal en typemap y idsubtypemap, si es de tipo contenido poner el valor de tm

                    //Si el parámetro tm = WBAGlobal poner ese valor
                    String tmSubTypeMap = tm;
                    String objName = "";
                    String objClass ="";
                    long idType=0;
                    PreparedStatement pstRType = conn1.prepareStatement("select name,type,objclass from wbobject where id=?");
                    pstRType.setLong(1,rs1.getLong("type"));
                    ResultSet rsType = pstRType.executeQuery();
                    if(rsType.next())
                    {
                    idType=rsType.getLong("type");
                    objName=rsType.getString("name").trim();
                    objClass=rsType.getString("objclass").trim();
                    }
                    rsType.close();
                    pstRType.close();
                    rsType=null;
                    pstRType=null;

                    //if(objName.equals("RelatedTopics")) idType = 20; // comentado para pasar todo al topicMap seleccionado
                    //if(objName.equals("FullTextSearch")) idType = 21;
                    //if(objName.equals("FullTextIndexer")) idType = 22;

                    if(objClass.equals("infotec.wb2.resources.Content"))
                    {
                        esContenido=true;
                    }
                    long ltype = rs1.getLong("type");

                    strTypeMap = tm;

                    // comentado para pasar todo al topicMap seleccionado
                    //if(hmResource.get(Long.toString(ltype))!=null)
                    //strTypeMap = "WBAGlobal";

                    String sql = "insert into wbresource " +
                    "(  id, idtm, title, description, created, active, actualversion, lastversion, xmlconf, xml, type, typemap, "+
                    "deleted, idcamp, idsubtype, idsubtypemap, idadm, priority, cache, views, hits, lastupdate,hitlog) "+
                    " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst2 = conn2.prepareStatement(sql);

                    pst2.setLong(1,id);
                    pst2.setString(2,tm); //idtm
                    pst2.setString(3,rs1.getString("title"));
                    pst2.setString(4,rs1.getString("description"));;
                    pst2.setTimestamp(5,rs1.getTimestamp("created"));
                    pst2.setInt(6,rs1.getInt("active"));

                    int versionactual = rs1.getInt("actualversion");
                    int ultimaversion = rs1.getInt("lastversion");

                    int versionactualtmp = versionactual;
                    int ultimaversiontmp = versionactual;
                    /*
                    if(versionactual>1 && esContenido)
                    {
                        versionactualtmp = 2;
                        ultimaversiontmp = 2;
                    }
                    else
                        ultimaversiontmp = ultimaversion;
                    */
                    if(reinicioversioncontent)
                    {
                    // para reinicio de versiones del contenido
                        if(versionactual>1 && esContenido)
                        {
                            versionactualtmp = 2;
                            ultimaversiontmp = 2;
                        }
                        else
                        {
                            ultimaversiontmp = ultimaversion;
                        }
                    }
                    else
                    {
                        versionactualtmp=versionactual;
                        ultimaversiontmp=ultimaversion;
                    }

                    pst2.setInt(7,versionactualtmp);
                    pst2.setInt(8,ultimaversiontmp);
                    if(null==strxmlconf)
                        pst2.setString(9,strxmlconf);
                    else
                        {
                            if(strxmlconf.trim().length()>0&&strxmlconf.indexOf("<interval>")!=-1)
                                {
                                String hoylong = Long.toString(System.currentTimeMillis()).trim();
                                String reemplazar = "<schedule><interval>";
                                reemplazar += "<title>titulo</title>";
                                reemplazar += "<active>"+hoylong+"</active>";
                                reemplazar += "<createdate>"+hoylong+"</createdate>";
                                reemplazar += "<usercreate>1_wb</usercreate>";
                                reemplazar += "<moddate>"+hoylong+"</moddate>";
                                reemplazar += "<usermod>1_wb</usermod>";
                                strxmlconf = strxmlconf.replaceAll("<interval>",reemplazar);
                                strxmlconf = strxmlconf.replaceAll("<inidate>/","<inidate>");
                                strxmlconf = strxmlconf.replaceAll("<enddate>/","<enddate>");
                                strxmlconf = strxmlconf.replaceAll("</interval>","</interval></schedule>");
                                if(strxmlconf.indexOf("<inidate>")!=-1)
                                    {
                                String fechainidate = strxmlconf.substring(strxmlconf.indexOf("<inidate>")+9,strxmlconf.indexOf("</inidate>"));
                                String[] afecha = fechainidate.split("/");
                                if(afecha.length>1)
                                    {
                                        fechainidate = afecha[1]+"/"+afecha[0]+"/"+afecha[2];
                                    }
                                    strxmlconf=strxmlconf.substring(0, strxmlconf.indexOf("<inidate>")+9)+fechainidate+ strxmlconf.substring(strxmlconf.indexOf("</inidate>"));
                                    }
                               if(strxmlconf.indexOf("<enddate>")!=-1)
                               {
                                String fechaenddate = strxmlconf.substring(strxmlconf.indexOf("<enddate>")+9,strxmlconf.indexOf("</enddate>"));
                                String[] afecha2 = fechaenddate.split("/");
                                if(afecha2.length>1)
                                    {
                                        fechaenddate = afecha2[1]+"/"+afecha2[0]+"/"+afecha2[2];
                                    }

                                strxmlconf=strxmlconf.substring(0, strxmlconf.indexOf("<enddate>")+9)+fechaenddate+ strxmlconf.substring(strxmlconf.indexOf("</enddate>"));
                               }
                                 if(strxmlconf.indexOf("<day>")!=-1)
                               {
                                    HashMap dias = new HashMap();
                                    dias.put("do","0");
                                    dias.put("lu","1");
                                    dias.put("ma","2");
                                    dias.put("mi","3");
                                    dias.put("ju","4");
                                    dias.put("vi","5");
                                    dias.put("sa","6");

                                String dia = strxmlconf.substring(strxmlconf.indexOf("<day>")+5,strxmlconf.indexOf("</day>"));
                                if(dias.get(dia)==null) dia="lu";

                                strxmlconf=strxmlconf.substring(0, strxmlconf.indexOf("<day>")+9)+dias.get(dia)+ strxmlconf.substring(strxmlconf.indexOf("</day>"));
                               }
                            }
                            pst2.setAsciiStream(9,AFUtils.getInstance().getStreamFromString(strxmlconf),strxmlconf.length());
                        }
                    if(strxml==null)
                        pst2.setString(10,null);
                    else
                    {
                        if(esContenido&&reinicioversioncontent)
                        {
                            String fileultimo=null, filepenultimo=null;
                            if(versionactual>1)
                            {
                                String arc=strxml;
                                if(arc.indexOf("<url"+versionactual+">")>-1)
                                {
                                    fileultimo =arc.substring(arc.indexOf("<url"+versionactual+">")+4+String.valueOf(versionactual).length()+1,arc.indexOf("</url"+versionactual+">"));
                                   System.out.println("fileultimo: "+fileultimo);
                                }
                                if(arc.indexOf("<url"+(versionactual-1)+">")>-1)
                                {
                                    filepenultimo =arc.substring(arc.indexOf("<url"+(versionactual-1)+">")+4+String.valueOf((versionactual-1)).length()+1,arc.indexOf("</url"+(versionactual-1)+">"));
                                    System.out.println("filepenultimo "+filepenultimo);
                                }
                                int pos=-1,pos1=-1;
                                pos=arc.indexOf("<url2>");
                                pos1=arc.indexOf("</url2>");
                                if(pos>-1 && pos1>-1){
                                    arc=arc.substring(0,pos+6)+fileultimo+arc.substring(pos1);
                                }
                                else
                                {
                                    int posini = -1;
                                    posini =  arc.indexOf("</resource>");
                                    if(posini>-1)
                                    {
                                        arc=arc.substring(0,posini)+"<url2>"+fileultimo+"</url2>"+arc.substring(posini);
                                    }
                                }
                                System.out.println("arc ... url2: "+arc);
                                pos=-1;
                                pos1=-1;
                                pos=arc.indexOf("<url1>");
                                pos1=arc.indexOf("</url1>");
                                if(pos>-1 && pos1>-1){
                                    arc=arc.substring(0,pos+6)+filepenultimo+arc.substring(pos1);
                                }
                                else
                                {
                                    int posini = -1;
                                    posini =  arc.indexOf("</resource>");
                                    if(posini>-1)
                                    {
                                        arc=arc.substring(0,posini)+"<url1>"+fileultimo+"</url1>"+arc.substring(posini);
                                    }
                                }
                                System.out.println("arc ... url1: "+arc);
                                strxml=arc;
                                Document domTmp=AFUtils.getInstance().getNewDocument();
                                Node nResource=domTmp.createElement("resource");
                                domTmp.appendChild(nResource);

                                Document dom=AFUtils.getInstance().XmltoDom(strxml);
                                NodeList nElements=dom.getFirstChild().getChildNodes();
                                for(int k=0;k<nElements.getLength();k++)
                                {
                                    if((!nElements.item(k).getNodeName().startsWith("url")) || nElements.item(k).getNodeName().equals("url1") || nElements.item(k).getNodeName().equals("url2"))
                                    {
                                        Node node = domTmp.importNode(nElements.item(k), true);
                                        nResource.appendChild(node);
                                    }
                                }
                                String tmpXML = AFUtils.getInstance().DomtoXml(domTmp);
                                System.out.println("tmpXML ... url1: "+tmpXML);
                                pst2.setAsciiStream(10,AFUtils.getInstance().getStreamFromString(tmpXML),tmpXML.length());
                            }
                            else //if(versionactual==1)
                            {
                                pst2.setAsciiStream(10,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                            }

                        }
                        else
                        {
                            pst2.setAsciiStream(10,AFUtils.getInstance().getStreamFromString(strxml),strxml.length());
                        }
                    }

                    pst2.setLong(11,ltype);
                    //pst2.setLong(11,idType);
                    pst2.setString(12,strTypeMap); //typemap
                    pst2.setInt(13,rs1.getInt("borrado"));
                    pst2.setLong(14,rs1.getLong("idcamp"));
                    pst2.setLong(15,rs1.getLong("idsubtype"));
                    pst2.setString(16,tmSubTypeMap); //idsubtypemap
                    pst2.setString(17,""+rs1.getInt("idadm")+"_"+repo.trim());
                    pst2.setInt(18,rs1.getInt("priority")); //rs1.getInt("priority")
                    pst2.setLong(19,0); //cache
                    pst2.setLong(20,rs1.getLong("views"));
                    pst2.setLong(21,rs1.getLong("hits"));
                    pst2.setTimestamp(22,rs1.getTimestamp("lastupdate"));
                    pst2.setInt(23,0);
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;

                    // llamar al servicio de mover archivos de origen(wb2) a destino(wb3)

                    if(installPath!=null&&webworkPath!=null&&installPath.trim().length()>0&&webworkPath.trim().length()>0&&objName.length()>0)
                    {
                    MigrationWB2_WB3  mwb2wb3 = new MigrationWB2_WB3();
                    if(esContenido && reinicioversioncontent)
                    {
                    // copiando version actual
                    if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/resources/"+objName+"/"+id+"/"+versionactual+"/","/sites/"+tm+"/resources/"+objName+"/"+id+"/"+versionactualtmp+"/"))
                    {
                    System.out.println("No se encontraron archivos del Contenido por copiar del recurso: "+objName+", con id: "+id);
                    }
                    // copiando la penúltima versión si existe
                    if(versionactualtmp==2)
                    {
                    if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/resources/"+objName+"/"+id+"/"+(versionactual-1)+"/","/sites/"+tm+"/resources/"+objName+"/"+id+"/1/"))
                    {
                    System.out.println("No se encontraron archivos del Contenido por copiar del recurso: "+objName+", con id: "+id);
                    }
                    }
                    }
                    else
                    {
                    if(!mwb2wb3.migrateFiles(installPath, webworkPath, "/resources/"+objName+"/"+id+"/","/sites/"+tm+"/resources/"+objName+"/"+id+"/"))
                    {
                    System.out.println("No se encontraron archivos por copiar del recurso: "+objName+", con id: "+id);
                    }
                    }
                    }

                    }
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbuser".equals(tabla))
                    {
                    long l_tot = 0;
                    Timestamp tm_now = new Timestamp(new java.util.Date().getTime());
                    String s_repository = repo.trim();
                    String s_language = "es";
                    int i_active = 1;
                    String s_confirmval = "";
                    String sql = "insert into wbuser (usrId, usrRepository, usrLogin, usrPassword, usrFirstName, usrLastName, usrMiddleName, usrLanguage, usrEmail, usrActive, usrLastLogin, usrPasswordChanged, usrConfirmValue, usrCreated, usrHome, usrXML, lastupdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    // copia wbuser
                    //pst1 = conn1.prepareStatement("select fname, lname, mname, language, email, password, xml, lastupdate from wbuser");
                    PreparedStatement pst3 = conn2.prepareStatement("select max(usrid) as id from wbuser where usrRepository=?");
                    pst3.setString(1,s_repository);
                    ResultSet rs3=pst3.executeQuery();

                    long id_usuario = 2;
                    if(rs3.next()) id_usuario = rs3.getLong("id")+1;
                    rs3.close();
                    pst3.close();
                    rs3=null;
                    pst3=null;
                    //pst1 = conn1.prepareStatement("select * from wbuser");
                    //rs1=pst1.executeQuery();
                    //l_tot = id_usuario;

                    PreparedStatement pst1 = conn1.prepareStatement("select * from wbuser");
                    ResultSet rs1=pst1.executeQuery();

                    while(rs1.next())
                    {
                    l_tot++;
                    String s_middlename = "";
                    String s_xml = null;
                    try{
                        s_xml=AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"),"ISO-8859-1");//rs1.getString("xml");
                    } catch(Exception exUSR){s_xml = null;}
                    String s_email = rs1.getString("email");
                    String s_password = rs1.getString("password");
                    String s_fname=rs1.getString("fname");
                    String s_lname=rs1.getString("lname");
                    if(rs1.getString("mname") != null){
                    s_middlename = rs1.getString("mname");
                    }
                    //if(l_tot==1) l_tot++;
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    //System.out.println("wbuser (usrId, usrRepository, usrLogin, usrPassword, usrFirstName, usrLastName, usrMiddleName, usrLanguage, usrEmail, usrActive, usrLastLogin, usrPasswordChanged, usrConfirmValue, usrCreated, usrHome, usrXML, lastupdate)");
                    //System.out.println("("+l_tot+", "+s_repository+", "+s_email+","+s_password+", "+s_fname+","+s_lname+","+s_middlename+","+s_language+","+s_email+","+i_active+", usrLastLogin, usrPasswordChanged, usrConfirmValue, usrCreated, usrHome, usrXML, lastupdate)");
                    pst2.setLong(1,id_usuario++);
                    pst2.setString(2,s_repository);
                    pst2.setString(3,s_email);
                    pst2.setString(4,s_password);
                    pst2.setString(5,s_fname);
                    pst2.setString(6,s_lname);
                    pst2.setString(7,s_middlename);
                    pst2.setString(8,s_language);
                    pst2.setString(9,s_email);
                    pst2.setInt(10,i_active);
                    pst2.setTimestamp(11,tm_now);
                    pst2.setTimestamp(12,tm_now);
                    pst2.setString(13,s_confirmval);
                    pst2.setTimestamp(14,tm_now);
                    pst2.setString(15,tm);
                    // reemplazar cadena TIPOUSU por USERTYPE
                    if(s_xml == null){
                    pst2.setString(16,null);
                    }
                    else{
                        s_xml=s_xml.replaceAll("TIPOUSU","USERTYPE");
                        pst2.setAsciiStream(16,AFUtils.getInstance().getStreamFromString(s_xml),s_xml.length());
                    }
                    pst2.setTimestamp(17,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;


                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+l_tot+" registros copiados<br>");
                    num_reg = Integer.parseInt(Long.toString(l_tot));

                    PreparedStatement pstCount = conn2.prepareStatement("select max(usrid) as id from wbuser where usrRepository=?");
                    pstCount.setString(1,s_repository);
                    ResultSet rsCount=pstCount.executeQuery();

                    long lcounter = 0;
                    if(rsCount.next()) lcounter = rsCount.getLong("id");
                    rsCount.close();
                    pstCount.close();
                    rsCount=null;
                    pstCount=null;

                    pstCount = conn2.prepareStatement("select * from wbcounter where name=?");
                    pstCount.setString(1,"wbuser_"+s_repository);
                    rsCount=pstCount.executeQuery();
                    boolean bandera = false;
                    if(rsCount.next()) bandera = true;
                    rsCount.close();
                    pstCount.close();
                    rsCount=null;
                    pstCount=null;

                    if(bandera)
                    {
                    // actualiza registro
                    pstCount = conn2.prepareStatement("update wbcounter set count=? where name=?");
                    pstCount.setLong(1,lcounter);
                    pstCount.setString(2,"wbuser_"+s_repository);
                    pstCount.executeUpdate();
                    pstCount.close();
                    pstCount=null;
                    }
                    else
                    {
                    // crea registro
                    pstCount = conn2.prepareStatement("insert into wbcounter (name,count) values (?,?)");
                    pstCount.setLong(1,lcounter);
                    pstCount.setString(2,"wbuser_"+s_repository);
                    pstCount.executeUpdate();
                    pstCount.close();
                    pstCount=null;
                    }
                    }
                    else if("wbadmuser".equals(tabla))
                    {
                    long l_tot = 0;
                    Timestamp tm_now = new Timestamp(new java.util.Date().getTime());
                    String s_repository = repo.trim();
                    String s_language = "es";
                    int i_active = 1;
                    String s_confirmval = "";
                    String sql = "insert into wbuser (usrId, usrRepository, usrLogin, usrPassword, usrFirstName, usrLastName, usrMiddleName, usrLanguage, usrEmail, usrActive, usrLastLogin, usrPasswordChanged, usrConfirmValue, usrCreated, usrHome, usrXML, lastupdate) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, admtype, login, password, name, email, area, phone, xml, lastupdate from wbadmuser order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    String s_name = rs1.getString("name");
                    String s_lastname = "";
                    String s_middlename = "";
                    //String s_xml = rs1.getString("xml");
                    String s_xml = null;
                    try{
                        s_xml=AFUtils.getInstance().readInputStream(rs1.getAsciiStream("xml"));//rs1.getString("xml");
                    } catch(Exception exADUSR){s_xml = null;}
                    long iduser = rs1.getLong("id");
                    if(iduser!=1)
                    {
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("id"));
                    pst2.setString(2,s_repository);
                    pst2.setString(3,rs1.getString("login"));
                    pst2.setString(4,rs1.getString("password"));
                    pst2.setString(5,s_name);
                    pst2.setString(6,s_lastname);
                    pst2.setString(7,s_middlename);
                    pst2.setString(8,s_language);
                    pst2.setString(9,rs1.getString("email"));
                    pst2.setInt(10,i_active);
                    pst2.setTimestamp(11,tm_now);
                    pst2.setTimestamp(12,tm_now);
                    pst2.setString(13,s_confirmval);
                    pst2.setTimestamp(14,tm_now);
                    pst2.setString(15,tm);
                    if(s_xml == null){
                        pst2.setString(16,null);
                    }
                    else{
                        s_xml=s_xml.replaceAll("TIPOUSU","USERTYPE");
                        pst2.setAsciiStream(16,AFUtils.getInstance().getStreamFromString(s_xml),s_xml.length());
                    }
                    pst2.setTimestamp(17,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    l_tot++;
                    }
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;

                    out.println(" ... ok -> "+l_tot+" registros copiados<br>");
                    num_reg = Integer.parseInt(Long.toString(l_tot));

                    PreparedStatement pstCount = conn2.prepareStatement("select max(usrid) as id from wbuser where usrRepository=?");
                    pstCount.setString(1,s_repository);
                    ResultSet rsCount=pstCount.executeQuery();

                    long lcounter = 0;
                    if(rsCount.next()) lcounter = rsCount.getLong("id")+1;
                    rsCount.close();
                    pstCount.close();
                    rsCount=null;
                    pstCount=null;

                    pstCount = conn2.prepareStatement("select * from wbcounter where name=?");
                    pstCount.setString(1,"wbuser_"+s_repository);
                    rsCount=pstCount.executeQuery();
                    boolean bandera = false;
                    if(rsCount.next()) bandera = true;
                    rsCount.close();
                    pstCount.close();
                    rsCount=null;
                    pstCount=null;

                    if(bandera)
                    {
                    // actualiza registro
                    pstCount = conn2.prepareStatement("update wbcounter set count=? where name=?");
                    pstCount.setLong(1,lcounter);
                    pstCount.setString(2,"wbuser_"+s_repository);
                    pstCount.executeUpdate();
                    pstCount.close();
                    pstCount=null;
                    }
                    else
                    {
                    // crea registro
                    pstCount = conn2.prepareStatement("insert into wbcounter (name,count) values (?,?)");
                    pstCount.setLong(1,lcounter);
                    pstCount.setString(2,"wbuser_"+s_repository);
                    pstCount.executeUpdate();
                    pstCount.close();
                    pstCount=null;
                    }
                    }
                    else if("wbcamp".equals(tabla))
                    {
                    String s_xml = "";
                    int i_deleted = 0;
                    String sql = "insert into wbcamp (id, idtm, title, description, xml, active, deleted, lastupdate) values(?,?,?,?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, title, description, active, lastupdate from wbcamp order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("id"));
                    pst2.setString(2,tm);
                    pst2.setString(3,rs1.getString("title"));
                    pst2.setString(4,rs1.getString("description"));
                    pst2.setString(5,null);
                    pst2.setInt(6,rs1.getInt("active"));
                    pst2.setInt(7,i_deleted);
                    pst2.setTimestamp(8,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbgrptemplate".equals(tabla))
                    {
                    String sql = "insert into wbgrptemplate (id, idtm, title, description, lastupdate) values(?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, title, description, lastupdate from wbgrptemplate order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("id"));
                    pst2.setString(2,tm);
                    pst2.setString(3,rs1.getString("title"));
                    pst2.setString(4,rs1.getString("description"));
                    pst2.setTimestamp(5,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbipfilter".equals(tabla))
                    {
                    String sql = "insert into wbipfilter (id, idtm, ip, description, action, lastupdate) values(?,?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, ip, description, action, lastupdate from wbipfilter order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("id"));
                    pst2.setString(2,tm);
                    pst2.setString(3,rs1.getString("ip"));
                    pst2.setString(4,rs1.getString("description"));
                    pst2.setInt(5,rs1.getInt("action"));
                    pst2.setTimestamp(6,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbresourcetype".equals(tabla))
                    {
                    String s_xml = null;
                    String sql = "insert into wbresourcetype (id, idtm, name, displayname, objclass, description, xml, type, bundle, cache, lastupdate) values(?,?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, name, objclass, description, lastupdate, type, cache from wbobject order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    long l_id = rs1.getLong("id");
                    String s_name = rs1.getString("name");
                    String s_objclass = rs1.getString("objclass");
                    String str_objname = s_objclass.substring(s_objclass.lastIndexOf(".")+1);
                    //if(hmResource.get(Long.toString(l_id)) == null&&!str_objname.equals("EgobRelatedTopics")&&!str_objname.equals("FullTextIndexer")&&!str_objname.equals("FullTextSearch")){
                    //if(hmResource.get(Long.toString(l_id)) == null&&!str_objname.equals("FullTextIndexer")&&!str_objname.equals("FullTextSearch"))
                    if(s_objclass.equals("infotec.wb2.resources.FullTextIndexer"))
                    {
                    s_objclass="com.infotec.wb.resources.FullTextIndexer";
                    }
                    if(s_objclass.equals("infotec.wb2.resources.FullTextSearch"))
                    {
                    s_objclass="com.infotec.wb.resources.FullTextSearch";
                    }
                    //if(hmResource.get(Long.toString(l_id)) == null){  // Descomentar para no copiar los primeros tipos de tipos de recursos
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,l_id);
                    pst2.setString(2,tm);
                    pst2.setString(3,s_name);
                    pst2.setString(4,s_name);
                    //if(s_objclass.indexOf("infotec.wb2.resources.")!=-1)
                    //{
                    //s_objclass="com.infotec.wb.resources."+str_objname;
                    //}
                    pst2.setString(5,s_objclass);
                    pst2.setString(6,rs1.getString("description"));
                    pst2.setString(7,s_xml);
                    pst2.setInt(8,rs1.getInt("type"));
                    pst2.setString(9,s_objclass);
                    pst2.setInt(10,rs1.getInt("cache"));
                    pst2.setTimestamp(11,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    //}         // Descomentar para no copiar los primeros tipos de tipos de recursos
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbsubtype".equals(tabla))
                    {
                    String sql = "insert into wbsubtype (id, idtm, type, typemap, title, description, lastupdate) values(?,?,?,?,?,?,?)";
                    PreparedStatement pst1 = conn1.prepareStatement("select id, title, description, lastupdate, idobj from wbsubtype order by id");
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    String strTypeMap = null;
                    while(rs1.next())
                    {
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("id"));
                    pst2.setString(2,tm);
                    int type = rs1.getInt("idobj");
                    pst2.setInt(3,type);
                    strTypeMap = tm;
                    //if(hmResource.get(Integer.toString(type))!=null)
                    //strTypeMap = "WBAGlobal";
                    pst2.setString(4,strTypeMap);
                    pst2.setString(5,rs1.getString("title"));
                    pst2.setString(6,rs1.getString("description"));
                    pst2.setTimestamp(7,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    else if("wbresourcedata".equals(tabla))
                    {
                    String sql = "insert into wbresourcedata (resid, residtm, topicmapid, topicid, userid, usertp, data, lastupdate) values(?,?,?,?,?,?,?,?)";

                    //String strSQL = "select resid, topicmapid, topicid, userid, usertp, data, lastupdate from wbresourcedata where topicmapid=? order by resid";
                    String strSQL = "select resid, topicmapid, topicid, userid, usertp, data, lastupdate from wbresourcedata  order by resid";
                    //if("WBAGlobal".equals(tm)) strSQL = "select resid, topicmapid, topicid, userid, usertp, data, lastupdate from wbresourcedata order by resid";
                    PreparedStatement pst1 = conn1.prepareStatement(strSQL);
                    //if(!"WBAGlobal".equals(tm)) pst1.setString(1,tm);
                    ResultSet rs1=pst1.executeQuery();
                    num_reg=0;
                    while(rs1.next())
                    {
                    //String s_xml = AFUtils.getInstance().readInputStream(rs1.getAsciiStream("data"),"ISO-8859-1"); //rs1.getString("data");
                    String s_xml = null;
                    try{
                        s_xml=AFUtils.getInstance().readInputStream(rs1.getAsciiStream("data")); //rs1.getString("data");
                    } catch(Exception exUSR){s_xml = null;}
                    PreparedStatement pst2 = conn2.prepareStatement(sql);
                    pst2.setLong(1,rs1.getLong("resid"));
                    //pst2.setString(2,tm);
                    pst2.setString(2,tm);
                    pst2.setString(3,rs1.getString("topicmapid"));
                    pst2.setString(4,rs1.getString("topicid"));
                    pst2.setString(5,rs1.getString("userid"));
                    pst2.setString(6,rs1.getString("usertp"));
                    if(s_xml == null){
                    pst2.setString(7,null);
                    }
                    else{
                    pst2.setAsciiStream(7,AFUtils.getInstance().getStreamFromString(s_xml),s_xml.length());
                    }
                    pst2.setTimestamp(8,rs1.getTimestamp("lastupdate"));
                    pst2.executeUpdate();
                    pst2.close();
                    pst2=null;
                    num_reg++;
                    }
                    rs1.close();
                    pst1.close();
                    rs1=null;
                    pst1=null;
                    out.println(" ... ok -> "+num_reg+" registros copiados<br>");
                    }
                    if(tabla.equals("wbadmuser")) tabla = "wbuser";
                    if(hmimported.get(tabla)!=null)
                    num_reg += Integer.parseInt((String)hmimported.get(tabla));
                    hmimported.put(tabla,Integer.toString(num_reg));

                    }
                    if(hmimported!=null) request.getSession().setAttribute("Importado",hmimported);
                    conn1.close();
                    conn2.close();
                    conn1=null;
                    conn2=null;
                    }
                    catch(Exception e)
                    {
                    AFUtils.log(e,"Error while trying to import DB from WB2 to WB3 table:"+tabla,true);
                    }

                    out.println("<form name=\"frmPass\" method=post >");
                    out.println("<input type=hidden name=act value=\"\">");
                    out.println("<input type=hidden name=conexion1 value=\""+con_name_1+"\">");
                    out.println("<input type=hidden name=conexion2 value=\""+con_name_2+"\">");
                    out.println("<input type=hidden name=tm value=\""+tm+"\">");
                    out.println("<input type=hidden name=installPath value=\""+installPath+"\">");
                    out.println("<input type=hidden name=webworkPath value=\""+webworkPath+"\">");
                    out.println("<table><tr><td align=center><input type=submit value=\"Regresar\"></td></tr>");
                    out.println("</table>");
                    out.println("</form>");

                    }
                    }
                    %>

                </td>
                </tr>
            </table>
        </font>

    </body>
</html>

<%!

/*
*   Carga en un HashMap los registros existentes en las tablas de la base de datos origen
* @param conexion1 nombre de la conexión origen
*/

public HashMap loadSourceRecords(String conexion1)
{
    HashMap hmsource = new HashMap();
    try{

            Connection conn1 = AFUtils.getInstance().getDBConnection(conexion1);
            PreparedStatement pst1 = conn1.prepareStatement("select count(*) as num from wbadmlog");
            ResultSet rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbadmlog",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbassociation");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbassociation",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbcamp");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbcamp",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbdbsync");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbdbsync",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbdevice");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbdevice",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbdns");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbdns",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbgrptemplate");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbgrptemplate",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbipfilter");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbipfilter",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wblanguage");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wblanguage",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wboccurrence");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wboccurrence",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbpflow");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbpflow",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbreshits");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbreshits",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbresource");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbresource",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbresourcedata");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbresourcedata",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbobject");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbresourcetype",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbrule");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbrule",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbsubtype");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbsubtype",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbtemplate");
            rs1 = pst1.executeQuery();
            if(rs1.next())
            {
                hmsource.put("wbtemplate",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbtopic");
            rs1 = pst1.executeQuery();

            if(rs1.next())
            {
                hmsource.put("wbtopic",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbtopicmap");
            rs1 = pst1.executeQuery();

            if(rs1.next())
            {
                hmsource.put("wbtopicmap",Integer.toString(rs1.getInt(1)));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;
            pst1 = conn1.prepareStatement("select count(*) as num from wbuser");
            rs1 = pst1.executeQuery();
            PreparedStatement pst2 = conn1.prepareStatement("select count(*) as num from wbadmuser");
            ResultSet rs2 = pst2.executeQuery();
            int users = 0;
            if(rs2.next())
            {
                users=rs2.getInt(1);
            }
            rs2.close();
            pst2.close();
            rs2=null;
            pst2=null;

            if(rs1.next())
            {
                users+=rs1.getInt(1);
                hmsource.put("wbuser",Integer.toString(users));
            }
            rs1.close();
            pst1.close();
            rs1=null;
            pst1=null;


            conn1.close();
            conn1=null;

        }
        catch(Exception e)
        {
            AFUtils.log(e,"Error al contar el número de registros a importar",true);
        }
        return hmsource;
}

/*
*   Carga en un HashMap los registros existentes en todas las tablas de la base de datos destino
* @param conexion2 nombre de la conexión destino
*/

public HashMap loadDestinationRecords(String conexion2)
{
    HashMap hmdestino = new HashMap();
    try{
        Connection conn1 = AFUtils.getInstance().getDBConnection(conexion2);
        PreparedStatement pst1 = conn1.prepareStatement("select count(*) as num from wbadmlog");
        ResultSet rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbadmlog",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbassociation");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbassociation",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbcamp");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbcamp",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        //pst1 = conn1.prepareStatement("select count(*) as num from wbdbsync");
        //rs1 = pst1.executeQuery();
        //if(rs1.next())
        //{
        //    hmdestino.put("wbdbsync",Integer.toString(rs1.getInt(1)));
        //}
        //rs1.close();
        //pst1.close();
        //rs1=null;
        //pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbdevice");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbdevice",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbdns");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbdns",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbgrptemplate");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbgrptemplate",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbipfilter");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbipfilter",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wblanguage");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wblanguage",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wboccurrence");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wboccurrence",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbpflow");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbpflow",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbreshits");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbreshits",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbresource");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbresource",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbresourcedata");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbresourcedata",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbresourcetype");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbresourcetype",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbrule");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbrule",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbsubtype");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbsubtype",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbtemplate");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbtemplate",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbtopic");
        rs1 = pst1.executeQuery();

        if(rs1.next())
        {
            hmdestino.put("wbtopic",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbtopicmap");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbtopicmap",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;
        pst1 = conn1.prepareStatement("select count(*) as num from wbuser");
        rs1 = pst1.executeQuery();
        if(rs1.next())
        {
            hmdestino.put("wbuser",Integer.toString(rs1.getInt(1)));
        }
        rs1.close();
        pst1.close();
        rs1=null;
        pst1=null;


        conn1.close();
        conn1=null;

    }
    catch(Exception e)
    {
        AFUtils.log(e,"Error al contar el número de registros a importar",true);
    }
    return hmdestino;
}

/*
*   Carga en un HashMap los Mapas de tópicos definidos en la base de datos origen
* @param conexion1 nombre de la conexión origen
*/

public HashMap loadTM(String conexion1)
{
    HashMap hmret = new HashMap();
    try{
        Connection conn1 = AFUtils.getInstance().getDBConnection(conexion1);
        PreparedStatement pstTM = conn1.prepareStatement("select * from wbtopicmap where borrado=?");
        pstTM.setInt(1,0);
        ResultSet rsTM = pstTM.executeQuery();
        while(rsTM.next())
        {
            hmret.put(rsTM.getString("id"),rsTM.getString("title"));
        }
        rsTM.close();
        pstTM.close();
        rsTM=null;
        pstTM=null;
        conn1.close();
        conn1=null;
     }
     catch(Exception e)
     {
        System.out.println("Error al cargar los TopicMaps de la DB origen. "+e);
     }
    return hmret;
}

/*
*   Carga todos los recursos por defecto de WebBuilder en un HashMap
*/

public HashMap loadResource()
{
    HashMap hmret = new HashMap();
    try{
        hmret.put("1","com.infotec.wb.resources.Banner");
        hmret.put("2","com.infotec.wb.resources.Promo");
        hmret.put("3","com.infotec.wb.resources.Encuesta");
        hmret.put("4","com.infotec.wb.resources.Ventana");
        hmret.put("5","com.infotec.wb.resources.Search");
        hmret.put("6","com.infotec.wb.resources.UserRegistration");
        hmret.put("7","com.infotec.wb.resources.Login");
        hmret.put("8","com.infotec.wb.resources.WBMenu");
        hmret.put("9","com.infotec.wb.resources.Content");
        hmret.put("10","com.infotec.wb.resources.WBUrlContent");
        hmret.put("11","com.infotec.wb.resources.FrameContent");
        hmret.put("12","com.infotec.wb.resources.Imprimir");
        hmret.put("13","com.infotec.wb.resources.Recomendar");
        hmret.put("14","com.infotec.wb.resources.WBSiteMap");
        hmret.put("15","com.infotec.wb.resources.Comentario");
        hmret.put("16","com.infotec.wb.resources.StaticText");
        hmret.put("17","com.infotec.wb.resources.JSPResource");
        hmret.put("18","com.infotec.wb.resources.PPTContent");
        hmret.put("19","com.infotec.wb.resources.ExcelContent");
        hmret.put("20","com.infotec.wb.resources.RelatedTopics");
        hmret.put("21","com.infotec.wb.resources.FullTextSearch");
        hmret.put("22","com.infotec.wb.resources.FullTextIndexer");
        hmret.put("100","com.infotec.wb.resources.WBDate");
     }
     catch(Exception e)
     {
        System.out.println("Error al cargar el hashmap de recursos iniciales "+e);
     }
    return hmret;
}

/*
*   Carga los dispositivos por defecto definidos en un HashMap
*/

public HashMap loadDevice()
{
    HashMap hmret = new HashMap();
    try{
        hmret.put("1","web");
        hmret.put("2","wap");
        hmret.put("3","IMODE");
        hmret.put("4","AvantGo");
        hmret.put("5","Safari");

     }
     catch(Exception e)
     {
        System.out.println("Error al cargar el hashmap de dispositivos iniciales "+e);
     }
    return hmret;
}

/*
*   Carga en un HashMap los DNS definidos en la base de datos origen
* @param conexion1 nombre de la conexión origen
*/

public HashMap loadDNS(String conexion1)
{
    HashMap hmret = new HashMap();
    try{
        Connection conn1 = AFUtils.getInstance().getDBConnection(conexion1);
        PreparedStatement pstTM = conn1.prepareStatement("select * from wbdns");
        ResultSet rsTM = pstTM.executeQuery();
        while(rsTM.next())
        {
            hmret.put(rsTM.getString("dns"),rsTM.getString("dns"));
        }
        rsTM.close();
        pstTM.close();
        rsTM=null;
        pstTM=null;
        conn1.close();
        conn1=null;
     }
     catch(Exception e)
     {
        System.out.println("Error al cargar los dns existentes. "+e);
     }
    return hmret;
}


/*
* Carga en un HashMap todos los repositorio de usuarios definidos en el archivo user.properties
*/
public HashMap loadRepositories()
{
    HashMap ret = new HashMap();
    Iterator iteRepo = DBUser.getInstance().getRepositories();
    while(iteRepo.hasNext())
    {
        DBUserRepository repo = (DBUserRepository) iteRepo.next();

        if(repo!=null) ret.put(repo.getName(),repo);
    }
    return ret;
}

%>