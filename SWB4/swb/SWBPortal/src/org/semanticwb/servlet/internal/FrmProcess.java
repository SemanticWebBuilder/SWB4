/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.servlet.internal;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormElementURL;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class FrmProcess.
 * 
 * @author Sergio Martínez  (sergio.martinez@acm.org)
 */
public class FrmProcess implements InternalServlet
{

    /** The log. */
    private static Logger log = SWBUtils.getLogger(FrmProcess.class);

    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#init(javax.servlet.ServletContext)
     */
    public void init(ServletContext config)
    {
        log.event("Initializing InternalServlet FrmProcess...");
    }

    /**
     * Checks if is valid id.
     * 
     * @param id the id
     * @return true, if is valid id
     */
    boolean isValidId(String id)
    {
        boolean ret = true;
        if (id != null)
        {
            for (int x = 0; x < id.length(); x++)
            {
                char ch = id.charAt(x);
                if (!((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == '_' || ch == '-'))
                {
                    ret = false;
                    break;
                }
            }
        }
        else
        {
            ret = false;
        }
        return ret;
    }


    /* (non-Javadoc)
     * @see org.semanticwb.servlet.internal.InternalServlet#doProcess(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.semanticwb.servlet.internal.DistributorParams)
     */
    public void doProcess(HttpServletRequest request, HttpServletResponse response, DistributorParams dparams) throws IOException, ServletException
    {
        String _contentType = request.getParameter("_swb_contentType");
        String _characterEncoding = request.getParameter("_swb_characterEncoding");
        
        //System.out.print(response.getClass());
        //System.out.print(_contentType);
        //System.out.print(_characterEncoding);
        
        if(_contentType!=null)response.setContentType(_contentType);
        try
        {
            if(_characterEncoding!=null)response.setCharacterEncoding(_characterEncoding);
        }catch(NoSuchMethodError noe){}
        
        
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        //System.out.println("frmProcess:"+request.getRequestURI());
        if (request.getRequestURI().endsWith("requestCaptcha"))
        {
            SWBPortal.UTIL.sendValidateImage(request, response, "captchaCad", 6, null);
        } 
        else if (request.getRequestURI().endsWith("requestSoundCaptcha"))
        {
            SWBPortal.UTIL.sendValidateSound(request, response, "captchaNum");
        }
        else if (request.getRequestURI().endsWith("validCaptcha"))
        {
            if ((null!=request.getSession(true).getAttribute("captchaCad") && 
                    ((String) request.getSession(true).getAttribute("captchaCad")).
                            equalsIgnoreCase(request.getParameter("frmCaptchaValue")))
                    || (null!=request.getSession(true).getAttribute("captchaNum") && 
                    ((String) request.getSession(true).getAttribute("captchaNum")).
                            equalsIgnoreCase(request.getParameter("frmCaptchaValue"))))
            {
                response.getWriter().println("true");
            } 
            else
            {
                response.getWriter().println("false");
            }
        }
        else if (request.getRequestURI().endsWith("canCreate"))
        {
            PrintWriter out = response.getWriter();
            String id = request.getParameter("id");
            String clsid = request.getParameter("clsid");
            String model = request.getParameter("model");
            //System.out.println("id:"+id+" clsid:"+clsid+" model:"+model);
            if (id == null && clsid == null && model != null)
            {
                if (isValidId(model))
                {
                    SemanticModel m = SWBPlatform.getSemanticMgr().getModel(model);
                    if (m == null)
                    {
                        out.println("true");
                    } else
                    {
                        out.println("false");
                    }
                }
                else
                {
                    out.println("false");
                }
            }
            else
            {
                if (id == null || id.length() == 0 || id.indexOf(' ') > -1 || clsid == null || model == null)
                {
                    out.println("false");
                    //System.out.println("false");
                }
                else
                {
                    SemanticModel m = SWBPlatform.getSemanticMgr().getModel(model);
                    if(clsid.equals("rdfs:Class"))
                    {
                        //TODO:validar prefijoa
                        String uri=id;

                        boolean con = m.getRDFModel().contains(m.getRDFModel().getResource(uri), null);
                        if (con)
                        {
                            out.println("false");
                        }
                        else
                        {
                            out.println("true");
                        }
                    }else if (isValidId(id))
                    {
                        SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClassById(clsid);
                        String uri = m.getObjectUri(id, scls);
                        //out.println(uri);
                        boolean con = m.getRDFModel().contains(m.getRDFModel().getResource(uri), null);
                        //SemanticObject obj=SemanticObject.createSemanticObject(uri);
                        if (con)
                        {
                            out.println("false");
                            //System.out.println("false");
                        }
                        else
                        {
                            out.println("true");
                            //System.out.println("true");
                        }
                    }
                    else
                    {
                        out.println("false");
                    }
                }
            }
        }
        else   //Procesar elemento
        {
            String _frmele = request.getParameter("_swb_frmele");
            String _obj = request.getParameter("_swb_obj");
            String _model = request.getParameter("_swb_model");
            String _cls = request.getParameter("_swb_cls");
            String _prop = request.getParameter("_swb_prop");
            String _urltp = request.getParameter("_swb_urltp");
            String _codetp = request.getParameter("_swb_codetp");
            String _mode = request.getParameter("_swb_mode");
            String _lang = request.getParameter("_swb_lang");
            
            log.debug("frmele:" + _frmele);
            log.debug("obj:" + _obj);
            log.debug("model:" + _model);
            log.debug("cls:" + _cls);
            log.debug("prop:" + _prop);
            log.debug("urltp:" + _urltp);
            
            //System.out.println(_obj+" "+_model);

            if (_frmele != null && _prop != null && _urltp != null)
            {
                try
                {
                    String ret = "OK";
                    FormElement ele = (FormElement) SemanticObject.createSemanticObject(_frmele).createGenericInstance();
                    SemanticObject obj = null;
                    if (_obj != null)
                    {
                        obj = SemanticObject.createSemanticObject(_obj);
                    }else if (_model != null)
                    {
                        SemanticClass cls=null;
                        if(_cls!=null)cls=SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(_cls);
                        obj=new SemanticObject(SWBPlatform.getSemanticMgr().getModel(_model),cls);
                    }
                    SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(_prop);
                    if (_urltp.equals(FormElementURL.URLTYPE_PROCESS))
                    {
                        ele.process(request, obj, prop);
                    } 
                    else if (_urltp.equals(FormElementURL.URLTYPE_VALIDATE))
                    {
                        ret = "true";
                        try
                        {
                            ele.validate(request, obj, prop);
                        } catch (FormValidateException fe)
                        {
                            ret = "false";
                        }
                    } 
                    else if (_urltp.equals(FormElementURL.URLTYPE_RENDER))
                    {
                        ret = ele.renderElement(request, obj, prop, _codetp, _mode, _lang);
                    }
                    response.getWriter().print(ret);
                    log.debug("ret:" + ret);
                } catch (Exception e)
                {
                    log.error(e);
                    response.sendError(500, e.getMessage());
                }
            }
            else
            {
                log.debug("error 500");
                response.sendError(500);
            }
        }
    }
}
