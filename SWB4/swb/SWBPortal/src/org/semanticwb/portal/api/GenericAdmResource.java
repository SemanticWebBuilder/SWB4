/*
 * INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet,
 * la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra
 * debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el
 * No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01
 * para la versión 3, respectivamente.
 *
 * INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * de INFOTEC WebBuilder 3.2.
 *
 * INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *
 *                                          http://www.webbuilder.org.mx
 */


/*
 * GernericAdmResource.java
 *
 * Created on 11 de octubre de 2004, 06:07 PM
 */
package org.semanticwb.portal.api;

import java.io.IOException;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import org.semanticwb.portal.admin.admresources.db.AdmDBConnMgr ;
import org.semanticwb.portal.admin.admresources.util.WBAdmResourceUtils;
import org.semanticwb.portal.admin.admresources.util.XmlBundle;
import java.util.Locale;
import java.util.Hashtable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Portlet;

/** Objeto: Implementa una administración genérica de recursos bajo el API de administración de
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
        catch(Exception e) {log.error("Error while loading GenericAdmResource template: ", e);}
    }
    
    /**
     * @param base
     * @throws AFException
     */    
    @Override
    public void setResourceBase(Portlet base) throws SWBResourceException
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
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        SWBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");        
        if(action.equals("add") || action.equals("edit")) 
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser(), xml, url.toString(),base, plt));
        }
        else  if(action.equals("update") || action.equals("remove"))
        {
            String applet="";
            String msg=SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.GenericAdmResource","msgUndefinedOperation", new Locale(paramsRequest.getUser().getLanguage()));
            if(action.equals("update"))
            {   // Add or update resource.
                try
                {
                    AdmDBConnMgr admdbconnmgr=new AdmDBConnMgr(request,base);
                    applet=admdbconnmgr.update2DB(paramsRequest.getUser());
                    msg=SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.GenericAdmResource","msgOkUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId();
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
                catch(Exception e) { log.error(e); msg=SWBUtils.TEXT.getLocaleString("com.infotec.wb.resources.GenericAdmResource","msgErrUpdateResource", new Locale(paramsRequest.getUser().getLanguage())) +" "+ base.getId(); }
            }
            else if(action.equals("remove")) 
            {
                msg=adResUtils.removeResource(base);
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
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        Portlet base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        SWBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");        
        if(action.equals("add") || action.equals("edit")) 
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser(), xml, url.toString(),base, plt));
        }
        else  if(action.equals("update") || action.equals("remove"))
        {
            String applet="";
            String msg=SWBUtils.TEXT.getLocaleString("org.semanticwb.portal.api.GenericAdmResource","msgUndefinedOperation", new Locale(paramsRequest.getUser().getLanguage()));
            if(action.equals("update"))
            {   // Add or update resource.
                try
                {
                    AdmDBConnMgr admdbconnmgr=new AdmDBConnMgr(request,base);
                    applet=admdbconnmgr.update2DB(paramsRequest.getUser());
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
                msg=adResUtils.removeResource(base);
                ret.append(
                    "<script language=\"JavaScript\">\n"+
                    "   alert('"+msg+"');\n"+
                    "</script>\n"); 
            }
        }
        response.getWriter().print(ret.toString());
    }    
}
