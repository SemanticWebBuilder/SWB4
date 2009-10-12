<%@page import="org.semanticwb.process.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="java.util.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
<%
        User user=SWBContext.getSessionUser();
%>
        User:<%=user.getFullName()%><br/>
<%

        ProcessSite site=ProcessSite.getProcessSite("oqp");

        org.semanticwb.process.Process process=site.getProcess("1");
        if(process==null)
        {
            process=site.createProcess();
            process.setTitle("Plan Estrategico");

            Activity act=site.createActivity();
            process.addActivity(act);
            process.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanEstrategico").getSemanticObject());
            act.setTitle("Plan Estrategico");

            Task mision=site.createTask();
            act.addTask(mision);
            mision.setTitle("Misión");

            Task vision=site.createTask();
            act.addTask(vision);
            vision.setTitle("Visión");

            Task valores=site.createTask();
            act.addTask(valores);
            valores.setTitle("Valores");

            Task objetives=site.createTask();
            act.addTask(objetives);
            objetives.setTitle("Valores");
            objetives.addDependence(mision);
            objetives.addDependence(vision);
            objetives.addDependence(valores);
        }

        String act=request.getParameter("act");
        if(act!=null && act.equals("cpi"))
        {
            SWBProcessMgr.createProcessInstance(site, process);
        }

        Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        while(it.hasNext())
        {
            ProcessInstance pi =  it.next();
%>
            Proceso Instance:<%=pi.getId()%> status:<%=pi.getStatus()%><br/>
<%

            Iterator<TaskInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                TaskInstance tkinst =  utkit.next();
    %>
                User Task Instance:<%=tkinst.getId()%> status:<%=tkinst.getStatus()%><br/>
    <%
            }


            Iterator<ActivityInstance> acit=pi.listActivityInstances();
            while(acit.hasNext())
            {
                ActivityInstance actinst =  acit.next();
    %>
                --> Activity Instance:<%=actinst.getId()%> status:<%=actinst.getStatus()%><br/>
    <%
                Iterator<TaskInstance> tkit=actinst.listTaskinstances();
                while(tkit.hasNext())
                {
                    TaskInstance tkinst =  tkit.next();
        %>
                    ----> Task Instance:<%=tkinst.getId()%> status:<%=tkinst.getStatus()%><br/>
        <%
                }
            }


        }

%>

        <a href="?act=cpi">Crear Instancia de Proceso</a>
    </body>
</html>
