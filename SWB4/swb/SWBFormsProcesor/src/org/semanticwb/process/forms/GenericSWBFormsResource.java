/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.ProcessObject;
import org.semanticwb.process.model.SWBProcessFormMgr;

/**
 *
 * @author jorge.jimenez
 */
public class GenericSWBFormsResource extends GenericResource{

    private static Logger log = SWBUtils.getLogger(GenericSWBFormsResource.class);

    static Templates plt;
    XmlBundle bundle=null;
    static Hashtable bundles=new Hashtable();
    WBAdmResourceUtils adResUtils=new WBAdmResourceUtils();
    String xml=null;
    private final String saveOK="Save_OK";
    private final String removeOK="Remove_OK";
    private final String saveError="Save_Error";


    /**
     * Sets the resource base.
     *
     * @param base the new resource base
     * @throws SWBResourceException the sWB resource exception
     */
    @Override
    public void setResourceBase(Resource base) throws SWBResourceException
    {
        super.setResourceBase(base);
        String name=getClass().getName();
        bundle=(XmlBundle)bundles.get(name);
        if(bundle==null)
        {
            bundle= new XmlBundle(name,getClass().getName());
            bundles.put(name, bundle);
        }
    }
    
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        PrintWriter out=response.getWriter();
        Resource base=getResourceBase();
        User user=paramRequest.getUser();
        StringBuilder ret = new StringBuilder("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramRequest.getAction();

        if(request.getParameter("msg")!=null){
            String msg=request.getParameter("msg");

            if(msg.equals(saveOK)) msg="Procesado...";
            else if(msg.equals(removeOK)) msg="Eliminado Ok en el lenguaje del usuario";
            ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n");
        }

        String suri=request.getParameter("suri");
        System.out.println("suri k llega en DoView:"+suri);

        out.println("<a href=\""+paramRequest.getRenderUrl().setMode(SWBResourceURL.Mode_EDIT).setParameter("suri", suri)+"\">[editar]</a>");

        SWBResourceURL urlAction = paramRequest.getActionUrl();
        if(action.equals("add") || action.equals("edit"))
        {
            urlAction.setAction("update");
            urlAction.setParameter("suri", suri);
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(user.getLanguage()));
            if(xml!=null && xml.trim().length()>0) {
                SWBFormMgrLayer swbFormMgrLayer=new SWBFormMgrLayer(xml, paramRequest, request);
                String html=swbFormMgrLayer.getHtml();
                //System.out.println("html final:"+html);
                ret.append(html);
            }
        }
        out.println(ret.toString());
    }


    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        Resource base=getResourceBase();

         String suri=request.getParameter("suri");
        if(suri==null)
        {
            return;
        }
        FlowNodeInstance foi = (FlowNodeInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        if("savecnf".equals(response.getAction()))
        {
            String data="";
            Iterator<ProcessObject> it=foi.listHeraquicalProcessObjects().iterator();
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
        }else if(action!=null && action.equals("update"))
        {
            if(action.equals("update"))
            {   //Add or update resource.
                try
                {
                    SemanticObject newSemObj=SWBFormMgrLayer.update2DB(request, response, foi);
                    //if(newSemObj!=null){
                    //    base.setAttribute("objInst", newSemObj.getURI());
                    //    base.updateAttributesToDB();
                        response.setRenderParameter("msg", saveOK);
                    //}else{
                    //    response.setRenderParameter("msg", saveError);
                    //}
                    response.setAction(response.Action_EDIT);
                }
                catch(Exception e) { log.error(e); }
            }
            else if(action.equals("remove"))
            {
                    response.setAction(response.Action_EDIT);
                    response.setRenderParameter("msg", saveOK);
            }
        }
        response.setRenderParameter("suri", suri);
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
        FlowNodeInstance foi = (FlowNodeInstance)SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);

        out.println("<form action=\""+paramRequest.getActionUrl().setAction("savecnf")+"\" method=\"post\">");
        out.println("<input type=\"hidden\" name=\"suri\" value=\""+suri+"\">");
        Iterator<ProcessObject> it=foi.listHeraquicalProcessObjects().iterator();
        while(it.hasNext())
        {
            ProcessObject obj=it.next();
            System.out.println("inst:"+foi+" obj:"+obj);
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

}
