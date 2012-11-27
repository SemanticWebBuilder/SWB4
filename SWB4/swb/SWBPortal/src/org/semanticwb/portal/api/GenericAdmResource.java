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
import org.semanticwb.model.Resource;

// TODO: Auto-generated Javadoc
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
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(GenericAdmResource.class);
    
    /** The plt. */
    static Templates plt; 
    
    /** The bundles. */
    static Hashtable bundles=new Hashtable();
    
    /** The bundle. */
    XmlBundle bundle=null;
    
    /** The ad res utils. */
    WBAdmResourceUtils adResUtils=new WBAdmResourceUtils();

    /** The xml. */
    String xml=null;
    
    /**
     * Creates a new instance of GernericAdmResource.
     */
    public GenericAdmResource()
    {
    }
    
    /**
     * Reload.
     * 
     * @param className the class name
     */
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
    
    /**
     * Do admin.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        Resource base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        SWBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");        
        if(action.equals("add") || action.equals("edit")) 
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser(), xml, url.toString(),base, plt, request));
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
    
    /**
     * Do view.
     * 
     * @param request the request
     * @param response the response
     * @param paramsRequest the params request
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws SWBResourceException the sWB resource exception
     */    
    @Override
    public void doView(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, SWBParamRequest paramsRequest) throws SWBResourceException, IOException
    {
        Resource base=getResourceBase();
        StringBuffer ret = new StringBuffer("");
        String action =  request.getParameter("act");
        if(action==null || (action!=null && action.trim().equals(""))) action=paramsRequest.getAction();

        SWBResourceURL url = paramsRequest.getRenderUrl().setAction("edit");        
        if(action.equals("add") || action.equals("edit")) 
        {
            url.setAction("update");
            xml=bundle.getBundle(getClass().getName(), new java.util.Locale(paramsRequest.getUser().getLanguage()));
            if(xml!=null && xml.trim().length()>0) ret.append(adResUtils.transformAdmResourceByXml(paramsRequest.getUser(), xml, url.toString(),base, plt, request));
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
