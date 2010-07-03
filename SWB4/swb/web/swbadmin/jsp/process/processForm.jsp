<%@page import="org.semanticwb.process.model.*"%>
<%@page import="org.semanticwb.*"%>
<%@page import="org.semanticwb.platform.*"%>
<%@page import="org.semanticwb.portal.*"%>
<%@page import="org.semanticwb.portal.api.*"%>
<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="org.semanticwb.model.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    public boolean hasProperty(SWBParamRequest paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId())>-1)return ret=true;
        return ret;
    }

    public boolean isViewProperty(SWBParamRequest paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId()+"|view")>-1)return ret=true;
        return ret;
    }

    public boolean isEditProperty(SWBParamRequest paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId()+"|edit")>-1)return ret=true;
        return ret;
    }

%>
<%
    SWBParamRequest paramRequest=(SWBParamRequest)request.getAttribute("paramRequest");
    User user=paramRequest.getUser();
    String lang=user.getLanguage();
    WebPage topic=paramRequest.getWebPage();
    ProcessSite site=(ProcessSite)paramRequest.getWebPage().getWebSite();
    org.semanticwb.process.model.Process process=SWBProcessMgr.getProcess(topic);

    String suri=request.getParameter("suri");
    if(suri==null)
    {
        out.println("Parámetro no difinido...");
        return;
    }
    FlowNodeInstance foi = (FlowNodeInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);


    if("savecnf".equals(paramRequest.getAction()))
    {
        String data="";
        Iterator<ProcessObject> it=foi.getAllProcessObjects().iterator();
        while(it.hasNext())
        {
            ProcessObject obj=it.next();
            SemanticClass cls=obj.getSemanticObject().getSemanticClass();
            Iterator<SemanticProperty> itp=cls.listProperties();
            while(itp.hasNext())
            {
                SemanticProperty prop=itp.next();
                String name=cls.getClassId()+"|"+prop.getPropId();
                if(request.getParameter(name)!=null)
                {
                    data+=name;
                    data+="|"+request.getParameter(name+"|s");
                    data+="\n";
                }
            }
        }
        out.println("datos:"+data);
        paramRequest.getResourceBase().setData(topic, data);
    }

    {
        out.println("<form action=\""+paramRequest.getRenderUrl().setAction("savecnf")+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+suri+"\">");
        Iterator<ProcessObject> it=foi.getAllProcessObjects().iterator();
        while(it.hasNext())
        {
            ProcessObject obj=it.next();
            SemanticClass cls=obj.getSemanticObject().getSemanticClass();
            out.println("<h3>"+cls.getDisplayName(lang)+"</h3>");
            Iterator<SemanticProperty> itp=cls.listProperties();
            while(itp.hasNext())
            {
                SemanticProperty prop=itp.next();
                String name=cls.getClassId()+"|"+prop.getPropId();
                out.print("<input type=\"checkbox\" name=\""+name+"\"");
                if(hasProperty(paramRequest, cls, prop))out.print(" checked");
                out.println(">");
                out.println("<select name=\""+name+"|s\">");
                out.println("<option value=\"edit\"");
                if(isEditProperty(paramRequest, cls, prop))out.print(" selected");
                out.println(">Edit</option>");
                out.println("<option value=\"view\"");
                if(isViewProperty(paramRequest, cls, prop))out.print(" selected");
                out.println(">View</option>");
                out.println("</select>");
                out.println(prop.getDisplayName(lang));
                out.println("<br/>");
            }
            out.println("<input type=\"submit\" value=\"Guardar\">");
        }
        out.println("</form>");
    }
    
    {
        SWBProcessFormMgr mgr=new SWBProcessFormMgr(foi);
        mgr.clearProperties();

        Iterator<ProcessObject> it=foi.getAllProcessObjects().iterator();
        while(it.hasNext())
        {
            ProcessObject obj=it.next();
            SemanticClass cls=obj.getSemanticObject().getSemanticClass();
            Iterator<SemanticProperty> itp=cls.listProperties();
            while(itp.hasNext())
            {
                SemanticProperty prop=itp.next();
                if(isViewProperty(paramRequest, cls, prop))
                {
                    mgr.addProperty(prop, cls, SWBFormMgr.MODE_VIEW);
                }else if(isEditProperty(paramRequest, cls, prop))
                {
                    mgr.addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
                }
            }
        }
        mgr.addButton(SWBFormButton.newSaveButton());
        mgr.addButton(SWBFormButton.newCloseButton());
        mgr.addButton(SWBFormButton.newCancelButton());
        out.println(mgr.renderForm(request));
    }


%>
