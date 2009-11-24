/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.Activity;
import org.semanticwb.process.FlowObjectInstance;
import org.semanticwb.process.ProcessObject;
import org.semanticwb.process.SWBProcessFormMgr;

/**
 *
 * @author javier.solis
 */
public class ProcessForm extends GenericResource
{
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException 
    {
        PrintWriter out=response.getWriter();
        String lang=paramRequest.getUser().getLanguage();

        String suri=request.getParameter("suri");
        if(suri==null)
        {
            out.println("Parámetro no difinido...");
            return;
        }

        out.println("<a href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setParameter("suri", suri)+"\">[editar]</a>");

        FlowObjectInstance foi = (FlowObjectInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        SWBProcessFormMgr mgr=new SWBProcessFormMgr(foi);
        mgr.setAction(paramRequest.getActionUrl().setAction("process").toString());
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
        SWBFormButton bt=new SWBFormButton().setTitle("Concluir Tarea","es").setAttribute("type", "submit").setBusyButton(true);
        bt.setAttribute("name", "accept");
        mgr.addButton(bt);
        SWBFormButton rej=new SWBFormButton().setTitle("Rechazar Tarea","es").setAttribute("type", "submit").setBusyButton(true);
        rej.setAttribute("name", "reject");
        mgr.addButton(rej);
        SWBFormButton ret=new SWBFormButton().setTitle("Regersar","es");
        ret.setAttribute("onclick", "window.location='"+foi.getProcessWebPage().getUrl()+"'");
        mgr.addButton(ret);

        out.println(mgr.renderForm(request));
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException 
    {
        String suri=request.getParameter("suri");
        if(suri==null)
        {
            return;
        }
        FlowObjectInstance foi = (FlowObjectInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        if("savecnf".equals(response.getAction()))
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
            response.getResourceBase().setData(response.getWebPage(), data);
            response.setMode(response.Mode_VIEW);
        }else if("process".equals(response.getAction()))
        {
            //System.out.println("Processing action... :"+foi);
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
                    if(isViewProperty(response, cls, prop))
                    {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_VIEW);
                    }else if(isEditProperty(response, cls, prop))
                    {
                        mgr.addProperty(prop, cls, SWBFormMgr.MODE_EDIT);
                    }
                }
            }
            try
            {
                mgr.processForm(request);
                if(request.getParameter("accept")!=null)
                {
                    foi.close(response.getUser(),Activity.ACTION_ACCEPT);
                    response.sendRedirect(foi.getProcessWebPage().getUrl());
                }else if(request.getParameter("reject")!=null)
                {
                    foi.close(response.getUser(),Activity.ACTION_REJECT);
                    response.sendRedirect(foi.getProcessWebPage().getUrl());
                }

            }catch(Exception e)
            {
                response.setRenderParameter("err", "invalidForm");
            }

        }
        response.setRenderParameter("suri", suri);
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        String lang=paramRequest.getUser().getLanguage();

        String suri=request.getParameter("suri");
        if(suri==null)
        {
            out.println("Parámetro no difinido...");
            return;
        }
        FlowObjectInstance foi = (FlowObjectInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        out.println("<form action=\""+paramRequest.getActionUrl().setAction("savecnf")+"\" method=\"post\">");
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
                out.println("<BR>");
            }
        }
        out.println("<input type=\"submit\" value=\"Guardar\">");
        out.println("<input type=\"button\" value=\"Cancelar\" onclick=\"window.location='"+paramRequest.getRenderUrl().setMode(paramRequest.Mode_VIEW)+"'\">");
        out.println("</form>");
    }

    public boolean hasProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId())>-1)return ret=true;
        return ret;
    }

    public boolean isViewProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId()+"|view")>-1)return ret=true;
        return ret;
    }

    public boolean isEditProperty(SWBParameters paramRequest, SemanticClass cls, SemanticProperty prop)
    {
        boolean ret=false;
        String data=paramRequest.getResourceBase().getData(paramRequest.getWebPage());
        if(data!=null && data.indexOf(cls.getClassId()+"|"+prop.getPropId()+"|edit")>-1)return ret=true;
        return ret;
    }
}
