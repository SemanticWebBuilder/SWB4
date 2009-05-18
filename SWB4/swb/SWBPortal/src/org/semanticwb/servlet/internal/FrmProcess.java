/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class FrmProcess implements InternalServlet
{
    private static Logger log = SWBUtils.getLogger(FrmProcess.class);

    public void init(ServletContext config)
    {
        log.event("Initializing InternalServlet FrmProcess...");
    }

    boolean isValidId(String id)
    {
        boolean ret=true;
        if(id!=null)
        {
            for(int x=0;x<id.length();x++)
            {
                char ch=id.charAt(x);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_'))
                {
                    ret=false;
                    break;
                }
            }
        }else
        {
            ret=false;
        }
        return ret;
    }


    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
    {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //System.out.println("frmProcess:"+request.getRequestURI());
        
        if(request.getRequestURI().endsWith("canCreate"))
        {
            PrintWriter out=response.getWriter();
            String id=request.getParameter("id");
            String clsid=request.getParameter("clsid");
            String model=request.getParameter("model");
            //System.out.println("id:"+id+" clsid:"+clsid+" model:"+model);
            if(id==null && clsid==null && model!=null)
            {
                if(isValidId(model))
                {
                    SemanticModel m=SWBPlatform.getSemanticMgr().getModel(model);
                    if(m==null)out.println("true");
                    else out.println("false");
                }else
                {
                    out.println("false");
                }
            }else
            {
                if(id==null || id.length()==0 || id.indexOf(' ')>-1 || clsid==null || model==null)
                {
                    out.println("false");
                    //System.out.println("false");
                }else
                {
                    if(isValidId(id))
                    {
                        SemanticModel m=SWBPlatform.getSemanticMgr().getModel(model);
                        SemanticClass scls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
                        String uri=m.getObjectUri(id, scls);
                        //out.println(uri);
                        boolean con=m.getRDFModel().contains(m.getRDFModel().getResource(uri), null);
                        //SemanticObject obj=SemanticObject.createSemanticObject(uri);
                        if(con)
                        {
                            out.println("false");
                            //System.out.println("false");
                        }else
                        {
                            out.println("true");
                            //System.out.println("true");
                        }
                    }else
                    {
                        out.println("false");
                    }
                }
            }
        }else   //Procesar elemento
        {
            String _frmele=request.getParameter("_swb_frmele");
            String _obj=request.getParameter("_swb_obj");
            String _prop=request.getParameter("_swb_prop");
            String _urltp=request.getParameter("_swb_urltp");
            String _codetp=request.getParameter("_swb_codetp");
            String _mode=request.getParameter("_swb_mode");
            String _lang=request.getParameter("_swb_lang");


            log.debug("frmele:"+_frmele);
            log.debug("obj:"+_obj);
            log.debug("prop:"+_prop);
            log.debug("urltp:"+_urltp);

            if(_frmele!=null && _prop!=null && _urltp!=null)
            {
                try
                {
                    String ret="OK";
                    FormElement ele=(FormElement)SemanticObject.createSemanticObject(_frmele).createGenericInstance();
                    SemanticObject obj=null;
                    if(_obj!=null)obj=SemanticObject.createSemanticObject(_obj);
                    SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(_prop);
                    if(_urltp.equals(FormElementURL.URLTYPE_PROCESS))
                    {
                        ele.process(request, obj, prop);
                    }else if(_urltp.equals(FormElementURL.URLTYPE_VALIDATE))
                    {
                        ret="true";
                        try
                        {
                            ele.validate(request, obj, prop);
                        }catch(FormValidateException fe)
                        {
                            ret="false";
                        }
                    }else if(_urltp.equals(FormElementURL.URLTYPE_RENDER))
                    {
                        ret=ele.renderElement(request, obj, prop, _codetp, _mode, _lang);
                    }
                    response.getWriter().print(ret);
                    log.debug("ret:"+ret);
                }catch(Exception e)
                {
                    log.error(e);
                    response.sendError(500,e.getMessage());
                }
            }else
            {
                log.debug("error 500");
                response.sendError(500);
            }
        }
    }

}
