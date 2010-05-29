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

    public SubProcess createSubProcess(Containerable process,String name)
    {
        SubProcess sps=process.getProcessSite().createSubProcess();
        sps.setTitle(name);
        sps.setContainer(process);
        return sps;
    }

    public UserTask createTask(Containerable process, String name)
    {
        UserTask task=process.getProcessSite().createUserTask();
        task.setTitle(name);
        //task.setActive(true);
        //process.addFlowObject(task);
        task.setContainer(process);
        //Resource res=task.getWebSite().createResource();
        //res.setTitle(name);
        //res.setActive(true);
        //res.setResourceType(task.getWebSite().getResourceType("ProcessForm"));
        //task.addResource(res);
        return task;
    }

    public SequenceFlow linkNode(GraphicalElement source, GraphicalElement target)
    {
        SequenceFlow seq=source.getProcessSite().createSequenceFlow();
        seq.setSource(source);
        seq.setTarget(target);
        return seq;
    }

    public SequenceFlow linkConditionNode(GraphicalElement source, GraphicalElement target, String condition)
    {
        ConditionalFlow seq=source.getProcessSite().createConditionalFlow();
        seq.setSource(source);
        seq.setTarget(target);
        seq.setFlowCondition(condition);
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
        //UserRepository urep=SWBContext.getAdminRepository();

        if(process==null)
        {
            process=site.createProcess();
            process.setTitle("Gestion de Negocios");
            //process.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#GestionNegocios").getSemanticObject());

            StartEvent ini=site.createStartEvent();
            ini.setContainer(process);

            SubProcess plan=createSubProcess(process, "Plan Estrategico");
            //plan.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanEstrategico").getSemanticObject());
            SubProcess com=createSubProcess(process, "Plan Comunicación");
            //com.addProcessClass(SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/oqp#PlanComunicacion").getSemanticObject());

            EndEvent end=site.createEndEvent();
            end.setContainer(process);

            linkNode(ini, plan);
            linkNode(plan, com);
            linkNode(com, end);

            //*******************************************************************

            {
                StartEvent pini=site.createStartEvent();
                pini.setContainer(plan);

                Gateway gtw1=site.createParallelGateway();
                gtw1.setContainer(plan);
                Gateway gtw2=site.createParallelGateway();
                gtw2.setContainer(plan);

                Task mision=createTask(plan, "Misión");
                Task vision=createTask(plan, "Vision");
                Task valores=createTask(plan, "Valores");
                Task objetivos=createTask(plan, "Objetivos");
                Task fin=createTask(plan, "Fin");

                EndEvent pend=site.createEndEvent();
                pend.setContainer(plan);

                linkNode(pini, gtw1);
                linkNode(gtw1, mision);
                linkNode(gtw1, vision);
                linkNode(gtw1, valores);

                linkNode(mision, gtw2);
                linkNode(vision, gtw2);
                linkNode(valores, gtw2);

                linkNode(gtw2, objetivos);
                linkNode(objetivos, fin);
                linkNode(fin, pend);
            }

            //*******************************************************************

            {
                StartEvent pini2=site.createStartEvent();
                pini2.setContainer(com);

                Gateway gtw3=site.createInclusiveGateway();
                gtw3.setContainer(com);
                Gateway gtw4=site.createInclusiveGateway();
                gtw4.setContainer(com);

                Task t1=createTask(com, "Tarea 1");
                Task t2=createTask(com, "Tarea 2");
                Task t3=createTask(com, "Tarea 3");
                Task t4=createTask(com, "Tarea 4");
                Task t5=createTask(com, "Tarea 5");
                Task t6=createTask(com, "Tarea 6");
                Task t7=createTask(com, "Tarea 7");
                Task t8=createTask(com, "Tarea 8");

                EndEvent pend2=site.createEndEvent();
                pend2.setContainer(com);

                linkNode(pini2, t1);
                linkNode(t1,gtw3);
                linkNode(gtw3,t2);
                linkNode(gtw3,t3);
                linkNode(gtw3,t4);
                linkNode(t2,t5);
                linkNode(t3,t6);
                linkNode(t4,gtw4);
                linkNode(t5,gtw4);
                linkNode(t6,gtw4);
                linkNode(gtw4,t7);
                linkConditionNode(t7,t1,Instance.ACTION_REJECT);
                linkConditionNode(t7,t8,Instance.ACTION_ACCEPT);
                linkNode(t8, pend2);
                //objetives.addUserGroup(urep.getUserGroup("Gerentes"));
            }
        }

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
            Iterator<ProcessObject> objit=pi.listProcessObjects();
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
