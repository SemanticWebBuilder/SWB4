/**  
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
**/ 
 
package org.semanticwb.resource.office.sem;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class PPTResource extends org.semanticwb.resource.office.sem.base.PPTResourceBase
{
    public PPTResource()
    {
        super();
    }
    public PPTResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(PPTResource.class);

    protected void beforePrintDocument(PrintWriter out)
    {
    }

    protected void afterPrintDocument(PrintWriter out)
    {
    }

    protected void printDocument(PrintWriter out, String path, String workpath, String html, SWBParamRequest paramReq,String resourcewebworkpath,String fileppt)
    {
        try
        {
            out.write("<div id=\""+  PPTResource.class.getName() +"\"><iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">" + paramReq.getLocaleString("frameNotsupport") + "</iframe><br>");
            if(this.isShowDownload())
            {                
                String pptpath=resourcewebworkpath+"/"+fileppt;
                out.write("<p><a href=\""+ pptpath +"\">"+paramReq.getLocaleString("download")+"</a></p>");
            }
        }
        catch (Exception e)
        {
            out.write("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">This navigator does not support iframe</iframe></div>");
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {

        String version = getVersionToShow();
        String contentId = getContent();
        String repositoryName = getRepositoryName();
        OfficeDocument document = new OfficeDocument();
        try
        {
            User user = paramRequest.getUser();
            String file = document.getContentFile(repositoryName, contentId, version, user);
            String resourceWebWorkpath=SWBPortal.getWebWorkPath();
            if (file != null)
            {
                String path = SWBPortal.getWebWorkPath();
                if (path.endsWith("/"))
                {
                    path = path.substring(0, path.length() - 1);
                    path += getResourceBase().getWorkPath() + "/" + "frame.html";
                    resourceWebWorkpath+=getResourceBase().getWorkPath();
                }
                else
                {
                    path += getResourceBase().getWorkPath() + "/" + "frame.html";
                    resourceWebWorkpath+=getResourceBase().getWorkPath();
                }
                PrintWriter out = response.getWriter();
                beforePrintDocument(out);
                String workpath = SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                printDocument(out, path, workpath, "", paramRequest,resourceWebWorkpath,file);
                afterPrintDocument(out);
                out.close();
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    public static org.semanticwb.resource.office.sem.PPTResource createPPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.PPTResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }
}
