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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;



public class ExcelResource extends org.semanticwb.resource.office.sem.base.ExcelResourceBase
{
    private static final OfficeDocument document = new OfficeDocument();
    public ExcelResource()
    {
        super();
    }
    public ExcelResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(ExcelResource.class);

    protected void beforePrintDocument(PrintWriter out)
    {
    }

    protected void afterPrintDocument(PrintWriter out)
    {
    }

    protected void printDocument(PrintWriter out, String path, String workpath, String html, SWBParamRequest paramReq)
    {
        out.write("<div id=\""+ ExcelResource.class.getName() +"\">");
        try
        {
            out.write("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">" + paramReq.getLocaleString("frameNotsupport") + "</iframe>");
        }
        catch (Exception e)
        {
            out.write("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">This navigator does not support iframe</iframe>");
        }
        out.write("</div>");
    }


    @Override
    public void loadContent(InputStream in,User user)
    {
        clean();
        File zipFile = null;
        try
        {
            if (in != null)
            {
                String name = UUID.randomUUID().toString() + ".zip";
                SWBPortal.writeFileToWorkPath(getResourceBase().getWorkPath() + "/" + name, in, user);
                zipFile = new File(SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/" + name);
                ZipFile zip = new ZipFile(zipFile);
                Enumeration entries = zip.entries();
                while (entries.hasMoreElements())
                {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (!entry.isDirectory())
                    {
                        InputStream inEntry = zip.getInputStream(entry);
                        String file=entry.getName();
                        /*int pos=file.lastIndexOf("/");
                        if(pos!=-1)
                        {
                            file=file.substring(pos+1);
                        }*/
                        SWBPortal.writeFileToWorkPath(getResourceBase().getWorkPath() + "/" + file, inEntry, user);
                    }
                }
                zip.close();
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
        finally
        {
            if (zipFile != null && zipFile.exists())
            {
                zipFile.delete();
            }
        }

    }



    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {        
        String version = getVersionToShow();
        String contentId = getContent();
        String repositoryName = getRepositoryName();
        
        try
        {
            User user = paramRequest.getUser();
            String file = document.getContentFile(repositoryName, contentId, version, user);
            if (file != null)
            {

                file = file.replace(".xls", ".html");
                String path = SWBPortal.getWebWorkPath();
                if (path.endsWith("/"))
                {
                    path = path.substring(0, path.length() - 1);
                    path += getResourceBase().getWorkPath() + "/" + file;
                }
                else
                {
                    path += getResourceBase().getWorkPath() + "/" + file;
                }
                PrintWriter out = response.getWriter();
                beforePrintDocument(out);
                String workpath = SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                printDocument(out, path, workpath, "", paramRequest);
                afterPrintDocument(out);
                out.close();
            }
        }
        catch (Exception e)
        {
            log.error(e);
        }
    }
    public static org.semanticwb.resource.office.sem.ExcelResource createExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.ExcelResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }
}
