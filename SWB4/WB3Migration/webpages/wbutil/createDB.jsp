<%@page contentType="text/html"%> 
<%@page import="com.infotec.wb.core.db.*"%> 
<%@page import="com.infotec.wb.core.db.RecAdmFilter"%>
<%@page import="com.infotec.wb.core.*"%> 
<%@page import="com.infotec.wb.util.*"%>
<%@page import="com.infotec.topicmaps.*"%>
<%@page import="com.infotec.topicmaps.bean.*"%>
<%@page import="com.infotec.appfw.lib.DBPool.*"%>
<%@page import="com.infotec.appfw.util.*"%>
<%@page import="java.sql.*"%>
<%@page import="com.infotec.topicmaps.db.*"%>

<%@page import="java.io.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%
    WBLoader.getInstance().init();

    DBConnectionManager mgr2=DBConnectionManager.getInstance();
    String gc=request.getParameter("gc");
    String clear=request.getParameter("clear");
    String ptype=request.getParameter("type");
    if(gc!=null)
    {
        System.gc();
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL='"+request.getRequestURI()+"'\">");
        return;
    }

    if(clear!=null)
    {
        //System.out.println(clear.substring(6));
        DBConnectionPool pool=(DBConnectionPool)mgr2.getPools().get(clear.substring(6));
        pool.release();
        out.println("<META HTTP-EQUIV=\"Refresh\" CONTENT=\"0; URL='"+request.getRequestURI()+"'\">");
        return;
    }

    String dbtype=request.getParameter("dbtype");
    if(dbtype!=null)
    {
        String file=AFUtils.getInstance().readInputStream(WBUtils.getInstance().getAdminFileStream("/wbadmin/sql/wb3_script_"+dbtype+".sql"));

        if(file!=null)
        {

            DBConnectionManager mgr=DBConnectionManager.getInstance();
            Connection con=mgr.getConnection((String)AFUtils.getInstance().getEnv("wb/db/nameconn"));
            if(con!=null)
            {

                try
                {
                    StringTokenizer sto=new StringTokenizer(file.trim(),";");
                    Statement st=con.createStatement();
                    while(sto.hasMoreTokens())
                    {
                        String query=sto.nextToken();
                        try
                        {
                            int x=st.executeUpdate(query);

                        }catch(Exception e){out.println("<BR>"+e+"<br>"+query);}
                    }
                    st.close();
                }catch(Exception e){AFUtils.log(e,"Error al crear tablas iniciales de la base de datos",true);}

            }
            con.close();
            mgr.release();

            out.println("");
    // AdminFilter

            try
            {
                RecAdmFilter filter = new RecAdmFilter(1,"WBAdmin","administrador general","Administrador General","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<filter id=\"1\" name=\"Administrador General\" topicmap=\"WBAdmin\"><description>Administrador General</description><elements><node icon=\"\" id=\"WBAd_System\" path=\"WBAd_System\" reload=\"getTopic.WBAdmin.WBAd_System\" topicmap=\"WBAdmin\"/></elements><menus><node icon=\"\" id=\"WBAd_Menus\" path=\"WBAd_Menus\" reload=\"getTopic.WBAdmin.WBAd_Menus\" topicmap=\"WBAdmin\"/></menus><sites><node icon=\"root\" id=\"server\" path=\"server\" reload=\"getServer\" topicmap=\"server\"/></sites></filter>", null);
                filter.create();
            }catch(Exception e){out.println("RecAdmFilter:"+e);}

    // Dumping data for table 'wbcamp'

            try
            {
                RecCamp camp;
                camp=new RecCamp(1,"WBAGlobal","Defecto","Defecto", null, 1, 0, null);
                camp.create();
                camp=new RecCamp(2,"WBAGlobal","Prioritaria","Prioritaria", null, 1, 0, null);
                camp.create();
            }catch(Exception e){out.println("RecCamp:"+e);}



    // Dumping data for table 'wbdevice'

            try
            {
                RecDevice obj;
                obj=new RecDevice(1,"web","Navegador","Mozilla",null);
                obj.create();
                obj=new RecDevice(2,"wap","wap","UP",null);
                obj.create();
                obj=new RecDevice(3,"IMODE","IMODE","Pixo",null);
                obj.create();
                obj=new RecDevice(4,"AvantGo","AvantGo","AvantGo",null);
                obj.create();
                obj=new RecDevice(5,"Safari","Safari","Safari",null);
                obj.create();

            }catch(Exception e){out.println("RecDevice:"+e);}

    // Dumping data for table 'wbdns'

            try
            {
                RecDns obj;
                obj=new RecDns(1,"WBAGlobal","localhost","WBAdmin","WBAd_Home",1,null);
                obj.create();
            }catch(Exception e){out.println("RecDns:"+e);}

    // Dumping data for table 'wbgrptemplate'

            try
            {
                RecGrpTemplate obj;
                obj=new RecGrpTemplate(1,"WBAGlobal","default","default",null);
                obj.create();
            }catch(Exception e){out.println("RecGrpTemplate:"+e);}

    //Dumping data for table 'wblanguage'

            try
            {
                RecLanguage obj;
                obj=new RecLanguage(1,"WBAGlobal","es","Español",null);
                obj.create();
                obj=new RecLanguage(2,"WBAGlobal","en","Inglés",null);
                obj.create();
            }catch(Exception e){out.println("RecLanguage:"+e);}

    //Dumping data for table 'wbresourcetype'

            try
            {
                RecResourceType obj;
                obj=new RecResourceType(1,"WBAGlobal","Banner","Banner","com.infotec.wb.resources.Banner","Banner","",2,"com.infotec.wb.resources.Banner",0,null);
                obj.create();
                obj=new RecResourceType(2,"WBAGlobal","Promo","Promo","com.infotec.wb.resources.Promo","Promo","",2,"com.infotec.wb.resources.Promo",0,null);
                obj.create();
                obj=new RecResourceType(3,"WBAGlobal","Poll","Poll","com.infotec.wb.resources.Poll","Poll","",2,"com.infotec.wb.resources.Poll",0,null);
                obj.create();
                obj=new RecResourceType(4,"WBAGlobal","Window","Window","com.infotec.wb.resources.Window","Window pop-up","",2,"com.infotec.wb.resources.Window",0,null);
                obj.create();
                obj=new RecResourceType(5,"WBAGlobal","Search","Search","com.infotec.wb.resources.WBSearch","Search","",3,"com.infotec.wb.resources.WBSearch",0,null);
                obj.create();
                obj=new RecResourceType(6,"WBAGlobal","Registry","Registry","com.infotec.wb.resources.UserRegistration","User Registry","",3,"com.infotec.wb.resources.UserRegistration",0,null);
                obj.create();
                obj=new RecResourceType(7,"WBAGlobal","Login","Login","com.infotec.wb.resources.Login","Login","",2,"com.infotec.wb.resources.Login",0,null);
                obj.create();
                obj=new RecResourceType(8,"WBAGlobal","Menu","Menu","com.infotec.wb.resources.WBMenu","Menu","",2,"com.infotec.wb.resources.WBMenu",0,null);
                obj.create();
                obj=new RecResourceType(9,"WBAGlobal","LocalContent","LocalContent","com.infotec.wb.resources.Content","HTML Local Content","",1,"com.infotec.wb.resources.Content",600,null);
                obj.create();
                obj=new RecResourceType(10,"WBAGlobal","ExternalContent","ExternalContent","com.infotec.wb.resources.WBUrlContent","URL External Content","",1,"com.infotec.wb.resources.WBUrlContent",600,null);
                obj.create();
                obj=new RecResourceType(11,"WBAGlobal","FrameContent","FrameContent","com.infotec.wb.resources.FrameContent","Frame Content","",1,"com.infotec.wb.resources.FrameContent",0,null);
                obj.create();
                obj=new RecResourceType(12,"WBAGlobal","Print","Print","com.infotec.wb.resources.Print","Print Section","",2,"com.infotec.wb.resources.Print",0,null);
                obj.create();
                obj=new RecResourceType(13,"WBAGlobal","Recommend","Recommend","com.infotec.wb.resources.Recommend","Recommend Section","",2,"com.infotec.wb.resources.Recommend",0,null);
                obj.create();
                obj=new RecResourceType(14,"WBAGlobal","WBSiteMap","SiteMap","com.infotec.wb.resources.WBSiteMap","Site Map","",3,"com.infotec.wb.resources.WBSiteMap",0,null);
                obj.create();
                obj=new RecResourceType(15,"WBAGlobal","Comment","Comment","com.infotec.wb.resources.Comment","Comment","",2,"com.infotec.wb.resources.Comment",0,null);
                obj.create();
                obj=new RecResourceType(16,"WBAGlobal","StaticText","StaticText","com.infotec.wb.resources.StaticText","Static Text","",2,"com.infotec.wb.resources.StaticText",0,null);
                obj.create();
                obj=new RecResourceType(17,"WBAGlobal","JSPResource","JSPResource","com.infotec.wb.resources.JSPResource","JSPResource","",3,"com.infotec.wb.resources.JSPResource",0,null);
                obj.create();
                obj=new RecResourceType(18,"WBAGlobal","PPTContent","PPTContent","com.infotec.wb.resources.PPTContent","PPTContent","",1,"com.infotec.wb.resources.PPTContent",0,null);
                obj.create();
                obj=new RecResourceType(19,"WBAGlobal","ExcelContent","ExcelContent","com.infotec.wb.resources.ExcelContent","ExcelContent","",1,"com.infotec.wb.resources.ExcelContent",0,null);
                obj.create();
                obj=new RecResourceType(20,"WBAGlobal","RelatedTopics","RelatedTopics","com.infotec.wb.resources.RelatedTopics","RelatedTopics","",3,"com.infotec.wb.resources.RelatedTopics",0,null);
                obj.create();
                //obj=new RecResourceType(21,"WBAGlobal","FullTextSearch","FullTextSearch","com.infotec.wb.resources.FullTextSearch","FullTextSearch","",3,"com.infotec.wb.resources.FullTextSearch",0,null);
                //obj.create();
                //obj=new RecResourceType(22,"WBAGlobal","FullTextIndexer","FullTextIndexer","com.infotec.wb.resources.FullTextIndexer","FullTextIndexer","",3,"com.infotec.wb.resources.FullTextIndexer",0,null);
                //obj.create();
                obj=new RecResourceType(100,"WBAGlobal","Date","Date","com.infotec.wb.resources.WBDate","Obtiene la fecha de carga del template","",4,"com.infotec.wb.resources.WBDate",0,null);
                obj.create();
            }catch(Exception e){out.println("RecResourceType:"+e);}

    // Dumping data for table 'wbrole'

            try
            {
                RecRole obj;
                obj=new RecRole(1,"wb","administrador","Rol del Administrador","<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<permiss><topic id=\"WBAd_per_Global\" topicmap=\"WBAdmin\"/><topic id=\"WBAd_per_Administrator\" topicmap=\"WBAdmin\"/><topic id=\"WBAd_per_TopicMap\" topicmap=\"WBAdmin\"/></permiss>",null);
                obj.create();
            }catch(Exception e){out.println("RecRole:"+e);}


    //Dumping data for table 'wbtopicmap'

            try
            {
                RecTopicMap tm=new RecTopicMap("WBAGlobal","1_wb","Global","WBAG_home","IDM_WBes","Global de Configuracion",1,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topicMap xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\"/>\r\n",null,null,0,1,"wb");
                tm.create();
            }catch(Exception e){out.println("RecTopicMap:"+e);}

    //Dumping data for table 'wbtopic'

            try
            {
                RecTopic tp;
                tp=new RecTopic("WBAG_home","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"WBAG_home\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <baseName>\r\n    <baseNameString>home</baseNameString>\r\n    <scope>\r\n      <topicRef xlink:href=\"#IDM_WBes\"/>\r\n    </scope>\r\n  </baseName>\r\n</topic>\r\n","",null,0,null,0,0);
                tp.create();

    /*
                tp=new RecTopic("CNF_WBRule","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBRule\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder rules</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("REC_WBContent","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"REC_WBContent\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder Content</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBSortName","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBSortName\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder SortName</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBPFlow","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBPFlow\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder Publish Flow</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBTemplate","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBTemplate\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder Template</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBCamp","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBCamp\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>Webbuilder Camp</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBConfig","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBConfig\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <baseName>\r\n    <baseNameString>Webbuilder Config</baseNameString>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
    */

                tp=new RecTopic("IDM_WBes","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"IDM_WBes\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n<instanceOf>\r\n<topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n</instanceOf>\r\n<baseName>\r\n<baseNameString>Espa&#241;ol</baseNameString>\r\n<scope>\r\n<topicRef xlink:href=\"#IDM_WBes\"/>\r\n</scope>\r\n</baseName>\r\n<baseName>\r\n<baseNameString>Spanish</baseNameString>\r\n<scope>\r\n<topicRef xlink:href=\"#IDM_WBen\"/>\r\n</scope>\r\n</baseName>\r\n</topic>","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("IDM_WBen","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"IDM_WBen\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n<instanceOf>\r\n<topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n</instanceOf>\r\n<baseName>\r\n<baseNameString>Ingl&#233;s</baseNameString>\r\n<scope>\r\n<topicRef xlink:href=\"#IDM_WBes\"/>\r\n</scope>\r\n</baseName>\r\n<baseName>\r\n<baseNameString>English</baseNameString>\r\n<scope>\r\n<topicRef xlink:href=\"#IDM_WBen\"/>\r\n</scope>\r\n</baseName>\r\n</topic>","",null,1,null,0,0);
                tp.create();
    /*
                tp=new RecTopic("CNF_WBRole","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBRole\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>WebBuilder Roles</baseNameString>\r\n    <scope>\r\n      <topicRef xlink:href=\"#IDM_WBes\"/>\r\n    </scope>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
                tp=new RecTopic("CNF_WBPermission","WBAGlobal","1_wb",0,"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<topic id=\"CNF_WBPermission\" xmlns=\"http://www.topicmaps.org/xtm/1.0/\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\r\n  <instanceOf>\r\n    <topicRef xlink:href=\"#CNF_WBConfig\"/>\r\n  </instanceOf>\r\n  <baseName>\r\n    <baseNameString>WebBuilder Permissions</baseNameString>\r\n    <scope>\r\n      <topicRef xlink:href=\"#IDM_WBes\"/>\r\n    </scope>\r\n  </baseName>\r\n</topic>\r\n","",null,1,null,0,0);
                tp.create();
    */
            }catch(Exception e){out.println("RecTopic:"+e);}

    //Dumping data for table 'wbuser', 'wbuserfilter', 'wbuserrole'

            try
            {
                RecUserWB usr=new RecUserWB("wb",0,"admin","webbuilder","Administrador","General","","es","webbuilder@infotec.com.mx",1,null,null,"",null,"WBAd_Home","",null);
                usr.create();
                usr.addAdmFilter("WBAdmin",1);
                usr.addRole(1);
            }catch(Exception e){out.println("RecUser:"+e);}

        }

%>
<html>
<head><title>WebBuilder Installer</title></head>
<body>
<form action="">
<font face="arial" size=1>
<table width="100%" border=1>
    <tr bgcolor="#8080c0"><td align=center colspan=4>
        <font color="#ffffff"><font size="+1"><B>WebBuilder DataBase Installer</B></font></font>
    </td></tr>
</table>
    <BR><font size=3>
        <b>Congratulations</b><BR>The installation finish, you can start <a href="<%=request.getRequestURI()%>">here</a>... 
    </font>
</body>
</html>
<%
        WBLoader.getInstance().init();
        mgr2.release();
        return;
    }
%>

<%
    long max=Runtime.getRuntime().maxMemory();
    long total=Runtime.getRuntime().totalMemory();
    long free=Runtime.getRuntime().freeMemory();
    long used=total-free;
%>
<html>
<head><title>WebBuilder Installer</title></head>
<body>

<form action="">
<font face="arial" size=1>
<table width="100%" border=1>
    <tr bgcolor="#8080c0"><td align=center colspan=4>
        <font color="#ffffff"><font size="+1"><B>WebBuilder DataBase Installer</B></font></font>
    </td></tr>
    <tr bgcolor="#a0a0f0"><td align=center colspan=4>
        <font color="#ffffff"><font size="-1"><B>
            <input type=submit name="gc" value="Garbage Collector">
            <input type=submit name="reload" value="Reload">
        </B></font></font>
    </td></tr>
    <tr bgcolor="#ffffff"><td colspan=4 height=10> </td></tr>
    <tr bgcolor="#8080c0"><td colspan=4><B>Memory</B></td></tr>
    <tr><td>Total:</td><td><%=total%></td>
        <td>Used:</td><td><%=used%></td></tr>
    <tr><td>Max:</td><td><%=max%></td>
        <td>Free:</td><td><%=free%></td></tr>
    <tr bgcolor="#ffffff"><td colspan=4 height=10> </td></tr>
<%
    PoolConnectionTimeLock timelock=mgr2.getTimeLock();
    Hashtable pools=mgr2.getPools();
    Enumeration en=pools.keys();
    while(en.hasMoreElements())
    {
        DBConnectionPool pool=(DBConnectionPool)pools.get(en.nextElement());
        String dbname="-";
        String dbversion="-";
        String drivername="-";
        String driverversion="-";
        try
        {
            Connection con=mgr2.getConnection(pool.getName(),"createDB.jsp");
            if(con!=null)
            {
                java.sql.DatabaseMetaData md=con.getMetaData();
                dbname=md.getDatabaseProductName();
                dbversion=md.getDatabaseProductVersion();
                drivername=md.getDriverName();
                driverversion=md.getDriverVersion();
                con.close();
            }
        }catch(Exception e){}
%>
    <tr bgcolor="#8080c0"><td colspan=4>
        <table border="0" width="100%" cellpadding=0 cellspacing=0><tr><td><B>ConnectionPool <%=pool.getName()%><%
    if(dbname.equals("-"))
    {
%>, 
<font color="#ff8080" size=-1><b>Not connected</b> (you can configure this using the file <b>[wb path]/WEB-INF/db.properties</b>)</font>
<%
    }
%>
            </B></td><td align=right><input type=submit name="clear" value="Clear <%=pool.getName()%>"></td></tr></table>
    </td></tr>
    <tr><td>DBName:</td><td><%=dbname%> (<%=dbversion%>)</td>
        <td>Total:</td><td><%=mgr2.getConnections(pool.getName())+mgr2.getFreeConnections(pool.getName())%></td></tr>
    <tr><td>Driver:</td><td><%=drivername%> (<%=driverversion%>)</td>
       <td>Used:</td><td><%=mgr2.getConnections(pool.getName())%></td></tr>
    <tr><td>URL:</td><td><%=pool.getURL()%></td>
        <td>Free:</td><td><%=mgr2.getFreeConnections(pool.getName())%></td></tr>
    <tr><td>User:</td><td><%=pool.getUser()%></td>
        <td>Max:</td><td><%=pool.getMaxConn()%></td></tr>
      <%
        HashMap timepool=(HashMap)timelock.getPools().get(pool.getName());
        if(timepool!=null)
        {
            HashMap pool2=new HashMap(timepool);
            int ps=pool2.size();
            if(ps>0)
            {
      %>
    <tr bgcolor="#a0a0e0"><td colspan=4><B>ConnectionDetails</B></td></tr>
    <tr bgcolor="#a0a0e0"><td colspan=4>
      <table width="100%" border=1>
        <tr bgcolor="#9090d0"><td>ID</td><td>Description</td><td>Time</td></tr>
      <%
            long actual=System.currentTimeMillis();
            Iterator it2=pool2.keySet().iterator();
            while(it2.hasNext())
            {
                Long time=(Long)it2.next();
                String desc=(String)pool2.get(time);
                long seg=(actual-time.longValue())/1000;
      %>
        <tr <%if((time.longValue()+300000L)<actual)out.print("bgcolor=\"#f05050\"");%>><td><%=time%></td><td><%=desc%></td><td><%=seg%>s</td></tr>
      <%
            }
      %> 
      </table>
    </td></tr>
<%
            }
        }
    }
    mgr2.release();
%>
</table>
<br>
<%
    if(WBLoader.getInstance().haveDB() && !WBLoader.getInstance().haveDBTables())
    {
%>
<table width="100%" border=1>
    <tr bgcolor="#8080c0"><td colspan=4><B>DataBases Supported, actual (<%=WBUtils.getDBName()%>)</B></td></tr>
    <tr bgcolor="#a0a0f0"><td>Install</td><td>DataBase</td><td>Tested</td><td>Prerequisites</td></tr>
    <tr><td><a href="?dbtype=oracle">install</A></td><td>Oracle</td><td>A</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=mysql">install</A></td><td>MySql</td><td>A</td><td>_</td></tr>
    <tr><td><a href="?dbtype=informix">install</A></td><td>Informix</td><td>B</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=pointbase">install</A></td><td>Pointbase</td><td>C</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=sybase">install</A></td><td>Sybase</td><td>B</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=sqlserver">install</A></td><td>SqlServer</td><td>B</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=postgres">install</A></td><td>postgres</td><td>B</td><td>Check JDBC Driver</td></tr>
    <tr><td><a href="?dbtype=hsql">install</A></td><td>hsql</td><td>B</td><td>_</td></tr>
    <tr bgcolor="#ffffff"><td colspan=4 height=10> </td></tr>
</table>

    <BR><font size=3>
    The "Tested" column indicates how extensively the code has been tested and used. <BR>
    A = well tested and used by many people.<BR>
    B = tested and usable, but some features might not be implemented.<BR>
    C = user contributed or experimental driver. Might not fully support all of the latest features. <BR>
    </font>
<%
    }else if(!WBLoader.getInstance().haveDB())
    {
%>
    <BR><font size=3 color="#ff8080">
        Note: First you need to create a database and configure the <b>wb</b> connection pool, using the file <b>db.properties</b> in the <b>[wb path]/WEB-INF/classes</b> path
    </font>
<%
    }else
    {
%>
    <BR><font size=3 color="#ff8080">
        WebBuilder was already installed...
    </font>
<%        
    }
%>
</font>
</form>
</body>
</html>