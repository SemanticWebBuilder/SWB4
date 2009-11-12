<%@page import="org.semanticwb.process.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public void printActivityInstance(FlowObjectInstance ai, JspWriter out) throws IOException
    {
        out.println("<li>");
        out.println("Activity: "+ai.getFlowObjectType().getTitle()+" "+ai.getId());
        out.println("Status:"+ai.getStatus());
        out.println("</li>");
        if(ai instanceof ProcessInstance)
        {
            ProcessInstance pi=(ProcessInstance)ai;
            Iterator<FlowObjectInstance> acit=pi.listFlowObjectInstances();
            if(acit.hasNext())
            {
                out.println("<ul>");
                while(acit.hasNext())
                {
                    FlowObjectInstance actinst =  acit.next();
                    printActivityInstance(actinst,out);
                }
                out.println("</ul>");
            }
        }
    }

    public org.semanticwb.process.Process createSubProcess(org.semanticwb.process.Process process,String name)
    {
        org.semanticwb.process.Process sps=process.getProcessSite().createProcess();
        sps.setTitle(name);
        process.addFlowObject(sps);
        return sps;
    }

    public UserTask createTask(org.semanticwb.process.Process process,String name)
    {
        UserTask task=process.getProcessSite().createUserTask(SWBPlatform.getIDGenerator().getID(name, null, true));
        task.setTitle(name);
        task.setActive(true);
        process.addFlowObject(task);
        Resource res=task.getWebSite().createResource();
        res.setTitle(name);
        res.setActive(true);
        res.setResourceType(task.getWebSite().getResourceType("ProcessForm"));
        task.addResource(res);
        return task;
    }

    public SequenceFlow linkObject(FlowObject from, FlowObject to)
    {
        SequenceFlow seq=from.getProcessSite().createSequenceFlow();
        from.addToConnectionObject(seq);
        seq.setToFlowObject(to);
        return seq;
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
        <a href="?act=cpi">Crear Instancia de Proceso</a>
<%
        String login=request.getParameter("user");
        if(login==null)login="admin";
        User user=SWBContext.getAdminRepository().getUserByLogin(login);
%>

<%
        ProcessSite site=ProcessSite.ClassMgr.getProcessSite("oqp");
        UserRepository urep=SWBContext.getAdminRepository();

        org.semanticwb.process.Process process=null;
        Iterator<org.semanticwb.process.Process> psit=site.listProcesses();
        if(psit.hasNext())process=psit.next();
        if(process==null)
        {
            process=site.createProcess();
            process.setTitle("Gestion de Negocios");
            process.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#GestionNegocios").getSemanticObject());

            InitEvent ini=site.createInitEvent();
            process.addFlowObject(ini);

            org.semanticwb.process.Process plan=createSubProcess(process, "Plan Estrategico");
            plan.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanEstrategico").getSemanticObject());
            org.semanticwb.process.Process com=createSubProcess(process, "Plan Comunicación");
            com.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanComunicacion").getSemanticObject());

            EndEvent end=site.createEndEvent();
            process.addFlowObject(end);

            linkObject(ini, plan);
            linkObject(plan, com);
            linkObject(com, end);

            //*******************************************************************

            InitEvent pini=site.createInitEvent();
            plan.addFlowObject(pini);

            GateWay gtw1=site.createANDGateWay();
            GateWay gtw2=site.createANDGateWay();

            UserTask mision=createTask(plan, "Misión");
            FormView view=site.createFormView();
            view.addEditProperty(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/oqp#peMision").getSemanticObject());
            mision.setFormView(view);

            Task vision=createTask(plan, "Vision");
            Task valores=createTask(plan, "Valores");
            Task objetivos=createTask(plan, "Objetivos");
            Task fin=createTask(plan, "Fin");

            EndEvent pend=site.createEndEvent();
            plan.addFlowObject(pend);

            linkObject(pini, gtw1);
            linkObject(gtw1, mision);
            linkObject(gtw1, vision);
            linkObject(gtw1, valores);

            linkObject(mision, gtw2);
            linkObject(vision, gtw2);
            linkObject(valores, gtw2);

            linkObject(gtw2, objetivos);
            linkObject(objetivos, fin);
            linkObject(fin, pend);

            //*******************************************************************

            pini=site.createInitEvent();
            com.addFlowObject(pini);

            GateWay gtw3=site.createORGateWay();
            GateWay gtw4=site.createORGateWay();

            Task t1=createTask(com, "Tarea 1");
            Task t2=createTask(com, "Tarea 2");
            Task t3=createTask(com, "Tarea 3");
            Task t4=createTask(com, "Tarea 4");
            Task t5=createTask(com, "Tarea 5");
            Task t6=createTask(com, "Tarea 6");
            Task t7=createTask(com, "Tarea 7");
            Task t8=createTask(com, "Tarea 8");

            pend=site.createEndEvent();
            com.addFlowObject(pend);

            linkObject(pini, t1);
            linkObject(t1,gtw3);
            linkObject(gtw3,t2);
            linkObject(gtw3,t3);
            linkObject(gtw3,t4);
            linkObject(t2,t5);
            linkObject(t3,t6);
            linkObject(t4,gtw4);
            linkObject(t5,gtw4);
            linkObject(t6,gtw4);
            linkObject(gtw4,t7);
            linkObject(t7,t8);
            linkObject(t8, pend);
            //objetives.addUserGroup(urep.getUserGroup("Gerentes"));
        }

        String act=request.getParameter("act");
        if(act!=null)
        {
            if(act.equals("cpi"))
            {
                SWBProcessMgr.createProcessInstance(site, process, user);
            }
            if(act.equals("accept"))
            {
                String id=request.getParameter("id");
                FlowObjectInstance inst=FlowObjectInstance.ClassMgr.getFlowObjectInstance(id, site);
                //inst.getFlowObjectType()
                //inst.getParentProcessInstance();
                inst.close(user);
            }
        }

        Iterator<ProcessInstance> it=SWBProcessMgr.getActiveProcessInstance(site, process).iterator();
        while(it.hasNext())
        {
            ProcessInstance pi =  it.next();
%>
            <h2>Proceso Instance: <%=pi.getFlowObjectType().getTitle()%> <%=pi.getId()%></h2>
<%
            user=SWBContext.getAdminRepository().getUserByLogin("admin");
%>
            <h3>Tareas del usuario (<%=user.getFullName()%>)</h3>
<%
            out.println("<ul>");
            Iterator<FlowObjectInstance> utkit=SWBProcessMgr.getUserTaskInstances(pi, user).iterator();
            while(utkit.hasNext())
            {
                FlowObjectInstance tkinst =  utkit.next();
    %>
                <li>User Task: <%=tkinst.getFlowObjectType().getTitle()%> <%=tkinst.getId()%> Status:<%=tkinst.getStatus()%> <a href="?id=<%=tkinst.getId()%>&act=accept&user=<%=user.getLogin()%>">accept</a></li>
    <%
            }
            out.println("</ul>");
            user=SWBContext.getAdminRepository().getUserByLogin("admin");
%>

        <h3>Artefactos</h3>
<%
            out.println("<ul>");
            Iterator<ProcessObject> objit=pi.getAllProcessObjects().iterator();
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
            Iterator<FlowObjectInstance> actit=pi.listFlowObjectInstances();
            while(actit.hasNext())
            {
                FlowObjectInstance obj =  actit.next();
                printActivityInstance(obj, out);
            }
            out.println("</ul>");
        }
%>
    </body>
</html>
