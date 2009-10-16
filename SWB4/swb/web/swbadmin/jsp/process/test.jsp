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
        UserRepository urep=SWBContext.getAdminRepository();

        org.semanticwb.process.Process process=site.getProcess("8");
        if(process==null)
        {
            process=site.createProcess();
            process.setTitle("Gestion de Negocios 2");
            process.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanEstrategico").getSemanticObject());

            Activity act=site.createActivity();
            process.addActivity(act);
            act.setTitle("Plan Estrategico");

            Task mision=site.createTask();
            act.addTask(mision);
            mision.setTitle("Misión");
            mision.addUserGroup(urep.getUserGroup("Directivos"));

            Task vision=site.createTask();
            act.addTask(vision);
            vision.setTitle("Visión");
            vision.addUserGroup(urep.getUserGroup("Directivos"));

            Task valores=site.createTask();
            act.addTask(valores);
            valores.setTitle("Valores");
            valores.addUserGroup(urep.getUserGroup("Directivos"));

            Task objetives=site.createTask();
            act.addTask(objetives);
            objetives.setTitle("Objetivos");
            objetives.addDependence(mision);
            objetives.addDependence(vision);
            objetives.addDependence(valores);
            objetives.addUserGroup(urep.getUserGroup("Gerentes"));

            Task endt=site.createTask();
            act.addTask(endt);
            endt.setTitle("Fin");
            endt.addDependence(objetives);
            endt.addRole(urep.getRole("Cordinador"));

            Activity act2=site.createActivity();
            act2.addDependence(act);
            process.addActivity(act2);
            act2.setTitle("Plan Comercial");

            Task comer1=site.createTask();
            act2.addTask(comer1);
            comer1.setTitle("Comer 1");
            comer1.addUserGroup(urep.getUserGroup("Gerentes"));

            Task comer2=site.createTask();
            act2.addTask(comer2);
            comer2.setTitle("Comer 2");
            comer2.addDependence(comer1);
            comer2.addUserGroup(urep.getUserGroup("Gerentes"));
            comer2.addRole(urep.getRole("Cordinador"));

            Task comer21=site.createTask();
            act2.addTask(comer21);
            comer21.setTitle("Comer 2.1");
            comer21.addDependence(comer1);
            comer21.addUserGroup(urep.getUserGroup("Gerentes"));

            Task comer3=site.createTask();
            act2.addTask(comer3);
            comer3.setTitle("Comer 3");
            comer3.addDependence(comer2);
            comer3.addRole(urep.getRole("Cordinador"));
        }

        String act=request.getParameter("act");
        if(act!=null)
        {
            if(act.equals("cpi"))
            {
                SWBProcessMgr.createProcessInstance(site, process);
            }
            if(act.equals("accept"))
            {
                String id=request.getParameter("id");
                TaskInstance.getTaskInstance(id, site).accept(user);
            }
        }

        Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        while(it.hasNext())
        {
            ProcessInstance pi =  it.next();
%>
            <br/>Proceso Instance:<%=pi.getId()%> <%=pi.getProcessType().getTitle()%> status:<%=pi.getStatus()%><br/>
<%

            Iterator<TaskInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                TaskInstance tkinst =  utkit.next();
    %>
                >User Task Instance:<%=tkinst.getId()%> <%=tkinst.getTaskType().getTitle()%> status:<%=tkinst.getStatus()%><a href="?id=<%=tkinst.getId()%>&act=accept">accept</a><br/>
    <%
            }

            Iterator<ProcessObject> objit=pi.listProcessObjects();
            while(objit.hasNext())
            {
                ProcessObject obj =  objit.next();
    %>
                >Object Instance:<%=obj.getURI()%> <br/>
    <%
            }



            Iterator<ActivityInstance> acit=pi.listActivityInstances();
            while(acit.hasNext())
            {
                ActivityInstance actinst =  acit.next();
    %>
                --> Activity Instance:<%=actinst.getId()%> <%=actinst.getActivityType().getTitle()%> status:<%=actinst.getStatus()%><br/>
    <%
                Iterator<TaskInstance> tkit=actinst.listTaskinstances();
                while(tkit.hasNext())
                {
                    TaskInstance tkinst =  tkit.next();
        %>
                    ----> Task Instance:<%=tkinst.getId()%> <%=tkinst.getTaskType().getTitle()%> status:<%=tkinst.getStatus()%><br/>
        <%
                }
            }


        }

%>

        <a href="?act=cpi">Crear Instancia de Proceso</a>
    </body>
</html>
