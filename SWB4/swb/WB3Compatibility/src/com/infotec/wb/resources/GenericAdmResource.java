/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboraci�n e integraci�n para Internet,
 * la cual, es una creaci�n original del Fondo de Informaci�n y Documentaci�n para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro P�blico del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versi�n 1; No. 03-2003-012112473900 para la versi�n 2, y No. 03-2006-012012004000-01
 * para la versi�n 3, respectivamente.
 *
 * INFOTEC pone a su disposici�n la herramienta INFOTEC WebBuilder a trav�s de su licenciamiento abierto al p�blico (�open source�),
 * en virtud del cual, usted podr� usarlo en las mismas condiciones con que INFOTEC lo ha dise�ado y puesto a su disposici�n;
 * aprender de �l; distribuirlo a terceros; acceder a su c�digo fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los t�rminos y condiciones de la LICENCIA ABIERTA AL P�BLICO que otorga INFOTEC para la utilizaci�n
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garant�a sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni impl�cita ni expl�cita,
 * siendo usted completamente responsable de la utilizaci�n que le d� y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposici�n la siguiente
 * direcci�n electr�nica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * GernericAdmResource.java
 *
 * Created on 11 de octubre de 2004, 06:07 PM
 */
package com.infotec.wb.resources;

import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import com.infotec.appfw.util.AFUtils;
import com.infotec.wb.core.Resource;
import com.infotec.wb.lib.WBResourceURL;
import java.util.Locale;
import java.util.Hashtable;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;

/** Objeto: Implementa una administraci�n gen�rica de recursos bajo el API de administraci�n de
 * recursos de Infotec WebBuilder.
 *
 * Object: A generic administration of resources under the API of administration of
 * resources of Infotec WebBuilder.
 *
 * @author Javier Solis Gonzalez
 * @version 1.0
 * @see com.infotec.wb.admin.admresources
 */
public class GenericAdmResource extends GenericResource
{
    private static Logger log = SWBUtils.getLogger(GenericAdmResource.class);

    static Templates plt;
    static Hashtable bundles=new Hashtable();
    XmlBundle bundle=null;
    WBAdmResourceUtils adResUtils=new WBAdmResourceUtils();

    String xml=null;
    
    /** Creates a new instance of GernericAdmResource */
    public GenericAdmResource()
    {
    }
    
    public static void reload(String className)
    {
        bundles.remove(className);
    }
    
    static
    {
        try {
            plt=TransformerFactory.newInstance().newTemplates(new javax.xml.transform.stream.StreamSource(SWBPortal.getAdminFileStream("/swbadmin/resources/GenericAdmResource/admresource.xslt")));
        }
        catch(Exception e) {log.error("Error while loading GenericAdmResource template: ", e);}    }
    
    /**
     * @param base
     * @throws AFException
     */    
    @Override
    public void setResourceBase(com.infotec.wb.core.Resource base) throws com.infotec.appfw.exception.AFException
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
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doAdmin(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, com.infotec.wb.lib.WBParamRequest paramsRequest) throws com.infotec.appfw.exception.AFException, java.io.IOException 
    {
        Resource base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        WBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");
        if(action.equals("add") || action.equals("edit"))
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser().getNative(), xml, url.toString(),base.getNative(), plt, request));
        }
        else  if(action.equals("update") || action.equals("remove"))
        {
            String applet="";
            String msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgUndefinedOperation", new Locale(paramsRequest.getUser().getLanguage()));
            if(action.equals("update"))
            {   // Add or update resource.
                try
                {
                    AdmDBConnMgr admdbconnmgr=new AdmDBConnMgr(request,base.getNative());
                    applet=admdbconnmgr.update2DB(paramsRequest.getUser().getNative());
                    msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgOkUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId();
                    ret.append(
                        "<script language=\"JavaScript\">\n"+
                        "   alert('"+msg+"');\n"+
                        "</script>\n");
                    if(applet!=null && !applet.trim().equals("")) ret.append(applet);
                    else
                    {
                        ret.append(
                            "<script language=\"JavaScript\">\n"+
                            "   location='"+url.toString()+"';\n"+
                            "</script>\n");
                    }
                }
                catch(Exception e) { log.error(e); msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgErrUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId(); }
            }
            else if(action.equals("remove"))
            {
                msg=adResUtils.removeResource(base.getNative());
                ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n");
            }
        }
        response.getWriter().print(ret.toString());
    }
    
    /**
     * @param request
     * @param response
     * @param paramsRequest
     * @throws AFException
     * @throws IOException
     */    
    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, com.infotec.wb.lib.WBParamRequest paramsRequest) throws com.infotec.appfw.exception.AFException, java.io.IOException
    {
        Resource base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        WBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");
        if(action.equals("add") || action.equals("edit"))
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser().getNative(), xml, url.toString(),base.getNative(), plt, request));
        }
        else  if(action.equals("update") || action.equals("remove"))
        {
            String applet="";
            String msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgUndefinedOperation", new Locale(paramsRequest.getUser().getLanguage()));
            if(action.equals("update"))
            {   // Add or update resource.
                try
                {
                    AdmDBConnMgr admdbconnmgr=new AdmDBConnMgr(request,base.getNative());
                    applet=admdbconnmgr.update2DB(paramsRequest.getUser().getNative());
                    msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgOkUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId();
                    ret.append(
                        "<script language=\"JavaScript\">\n"+
                        "   alert('"+msg+"');\n"+
                        "</script>\n");
                    if(applet!=null && !applet.trim().equals("")) ret.append(applet);
                    else
                    {
                        ret.append(
                            "<script language=\"JavaScript\">\n"+
                            "   location='"+url.toString()+"';\n"+
                            "</script>\n");
                    }
                }
                catch(Exception e) { log.error(e); msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgErrUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId(); }
            }
            else if(action.equals("remove"))
            {
                msg=adResUtils.removeResource(base.getNative());
                ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n");
            }
        }
        response.getWriter().print(ret.toString());
    }    
}
