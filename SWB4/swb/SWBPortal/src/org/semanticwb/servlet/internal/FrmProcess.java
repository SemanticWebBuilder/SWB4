/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
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

    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException
    {
        String _frmele=request.getParameter("_swb_frmele");
        String _obj=request.getParameter("_swb_obj");
        String _prop=request.getParameter("_swb_prop");
        String _urltp=request.getParameter("_swb_urltp");
        String _codetp=request.getParameter("_swb_codetp");
        String _mode=request.getParameter("_swb_mode");
        String _lang=request.getParameter("_swb_lang");


        System.out.println("frmele:"+_frmele);
        System.out.println("obj:"+_obj);
        System.out.println("prop:"+_prop);
        System.out.println("urltp:"+_urltp);

        if(_frmele!=null && _obj!=null && _prop!=null && _urltp!=null)
        {
            try
            {
                String ret="OK";
                FormElement ele=(FormElement)Class.forName(_frmele).newInstance();
                SemanticObject obj=SemanticObject.createSemanticObject(_obj);
                SemanticProperty prop=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(_prop);
                if(_urltp.equals(FormElementURL.URLTYPE_PROCESS))
                {
                    ele.process(request, obj, prop);
                }else if(_urltp.equals(FormElementURL.URLTYPE_VALIDATE))
                {
                    ele.validate(request, obj, prop);
                }else if(_urltp.equals(FormElementURL.URLTYPE_RENDER))
                {
                    ret=ele.renderElement(request, obj, prop, _codetp, _mode, _lang);
                }
                response.getWriter().print(ret);
            }catch(Exception e){response.sendError(500,e.getMessage());}
        }else
        {
            response.sendError(500);
        }
    }

}
