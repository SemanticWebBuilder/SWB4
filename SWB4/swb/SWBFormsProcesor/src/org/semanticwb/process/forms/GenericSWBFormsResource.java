/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.process.forms;

import java.io.IOException;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Resource;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;

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
        
    }

    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        Resource base=getResourceBase();
        User user=paramRequest.getUser();
        StringBuilder ret = new StringBuilder("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramRequest.getAction();
        
        if(request.getParameter("msg")!=null){
            String msg=request.getParameter("msg");

            if(msg.equals(saveOK)) msg="Grabado Ok en el lenguaje del usuario";
            else if(msg.equals(removeOK)) msg="Eliminado Ok en el lenguaje del usuario";
            ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n");
        }

        SWBResourceURL url = paramRequest.getRenderUrl().setAction("edit");
        if(action.equals("add") || action.equals("edit"))
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(user.getLanguage()));
            if(xml!=null && xml.trim().length()>0) {
                SWBFormMgrLayer swbFormMgrLayer=new SWBFormMgrLayer(xml, paramRequest, request);
                String html=swbFormMgrLayer.getHtml();
                //System.out.println("html final:"+html);
                //ret.append(xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?><admresource>" + html + "</admresource>");
                ret.append(html);
            }
        }
        response.getWriter().print(ret.toString());
    }

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action=response.getAction();
        Resource base=getResourceBase();
        if(action!=null && action.equals("update"))
        {
            if(action.equals("update"))
            {   //Add or update resource.
                try
                {
                    SemanticObject newSemObj=SWBFormMgrLayer.update2DB(request, response);
                    if(newSemObj!=null){
                        base.setAttribute("objInst", newSemObj.getURI());
                        base.updateAttributesToDB();
                        response.setRenderParameter("msg", saveOK);
                    }else{
                        response.setRenderParameter("msg", saveError);
                    }
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
    }
}
