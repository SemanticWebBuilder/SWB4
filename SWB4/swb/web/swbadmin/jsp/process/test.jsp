<%@page import="org.semanticwb.platform.SemanticObject"%>
<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public void printActivityInstance(FlowNodeInstance ai, JspWriter out) throws IOException
    {
        out.println("<li>");
        out.println("Activity: "+ai.getFlowNodeType().getTitle()+" "+ai.getId());
        out.println("Status:"+ai.getStatus());
        out.println("Action:"+ai.getAction());
        out.println("</li>");
        if(ai instanceof SubProcessInstance)
        {
            SubProcessInstance pi=(SubProcessInstance)ai;
            Iterator<FlowNodeInstance> acit=pi.listFlowNodeInstances();
            if(acit.hasNext())
            {
                out.println("<ul>");
                while(acit.hasNext())
                {
                    FlowNodeInstance actinst =  acit.next();
                    printActivityInstance(actinst,out);
                }
                out.println("</ul>");
            }
        }
    }
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">
            @import "/swbadmin/js/dojo/dojo/resources/dojo.css";
            @import "/swbadmin/js/dojo/dijit/themes/soria/soria.css";
            @import "/swbadmin/css/swb.css";
        </style>

        <script type="text/javascript" src="/swbadmin/js/dojo/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false, locale: 'es'" ></script>
        <script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb.js" ></script>
        <!--script type="text/javascript" charset="utf-8" src="/swbadmin/js/swb_admin.js" ></script-->
    </head>
    <body class="soria">
        <h1>Test de Procesos</h1>
<%
        User user=SWBContext.getAdminUser();
        String login=request.getParameter("user");
        if(login!=null)
        {
            user=SWBContext.getAdminRepository().getUserByLogin(login);
        }
        if(user==null)
        {
            login="admin";
            user=SWBContext.getAdminRepository().getUserByLogin(login);
        }
        String lang=user.getLanguage();

        ProcessSite site=ProcessSite.ClassMgr.getProcessSite("process");
        org.semanticwb.process.model.Process process=null;

        Iterator<org.semanticwb.process.model.Process> psit=SWBComparator.sortByDisplayName(site.listProcesses(),lang);
        while (psit.hasNext())
        {
            Object elem = psit.next();
            process=(org.semanticwb.process.model.Process)elem;
            if(!process.isValid())continue;
%>
        <a href="?act=cpi&id=<%=process.getEncodedURI()%>">Crear Instancia del Proceso (<%=process.getDisplayTitle(lang)%>)</a><br/>
<%
        }
%>

<%
        String act=request.getParameter("act");
        if(act!=null)
        {
            if(act.equals("rpi"))
            {
                String id=request.getParameter("id");
                ProcessInstance inst=ProcessInstance.ClassMgr.getProcessInstance(id, site);
                inst.remove();
            }
            if(act.equals("cpi"))
            {
                String id=request.getParameter("id");
                process=(org.semanticwb.process.model.Process)SemanticObject.createSemanticObject(id).createGenericInstance();
                SWBProcessMgr.createProcessInstance(process, user);
            }
            if(act.equals("accept") || act.equals("reject"))
            {
                String id=request.getParameter("id");
                FlowNodeInstance inst=FlowNodeInstance.ClassMgr.getFlowNodeInstance(id, site);
                inst.close(user,act);
            }
        }

        //Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        Iterator<ProcessInstance> it=site.listProcessInstances();
        while(it.hasNext())
        {
            ProcessInstance pi =  it.next();
%>
            <h2>Proceso Instance: <%=pi.getProcessType().getTitle()%> <%=pi.getId()%> <a href="?act=rpi&id=<%=pi.getId()%>">remove</a></h2>
<%
            user=SWBContext.getAdminRepository().getUserByLogin("admin");
%>
            <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
<%
            out.println("<ul>");
            Iterator<FlowNodeInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                FlowNodeInstance tkinst =  utkit.next();
    %>
                <li>User Task: <%=tkinst.getFlowNodeType().getTitle()%> <%=tkinst.getId()%> Status:<%=tkinst.getStatus()%> <a href="?id=<%=tkinst.getId()%>&act=accept&user=<%=user.getLogin()%>">accept</a> <a href="?id=<%=tkinst.getId()%>&act=reject&user=<%=user.getLogin()%>">reject</a></li>
    <%
            }
            out.println("</ul>");
            user=SWBContext.getAdminRepository().getUserByLogin("admin");
%>

        <h3>Artefactos</h3>
<%
            out.println("<ul>");
            Iterator<ProcessObject> objit=pi.listAllProcessObjects();
            while(objit.hasNext())
            {
                ProcessObject obj =  objit.next();
    %>
                <li>Object Instance:<%=obj.getURI()%></li>
    <%
                //SWBFormMgr mgr=new SWBFormMgr(obj.getSemanticObject(),null,SWBFormMgr.MODE_EDIT);
                //out.println(mgr.renderForm(request));
            }
            out.println("</ul>");

%>
        <h3>Detalle de Proceso</h3>
<%
            out.println("<ul>");
            Iterator<FlowNodeInstance> actit=pi.listFlowNodeInstances();
            while(actit.hasNext())
            {
                FlowNodeInstance obj =  actit.next();
                printActivityInstance(obj, out);
            }
            out.println("</ul>");
        }
%>
    </body>
</html>
