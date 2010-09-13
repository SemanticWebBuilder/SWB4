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



// TODO: Auto-generated Javadoc
/**
 * The Class ExcelResource.
 */
public class ExcelResource extends org.semanticwb.resource.office.sem.base.ExcelResourceBase
{    
    
    /**
     * Instantiates a new excel resource.
     */
    public ExcelResource()
    {
        super();
    }
    
    /**
     * Instantiates a new excel resource.
     * 
     * @param obj the obj
     */
    public ExcelResource(SemanticObject obj)
    {
        super(obj);
    }
    
    /** The log. */
    private static Logger log = SWBUtils.getLogger(ExcelResource.class);

    /**
     * Before print document.
     * 
     * @param out the out
     */
    protected void beforePrintDocument(PrintWriter out)
    {
    }

    /**
     * After print document.
     * 
     * @param out the out
     */
    protected void afterPrintDocument(PrintWriter out)
    {
    }
    
    /**
     * Gets the hTML.
     * 
     * @param file the file
     * @return the hTML
     */
    public static String getHTML(File file)
    {
        StringBuilder html=new StringBuilder();
        String name = file.getName().replace(".xls", ".html");
        try
        {
            name = java.net.URLDecoder.decode(name, "utf-8");
            file=new File(file.getParentFile().getAbsolutePath()+"/"+name);
            String workpath=file.getAbsolutePath().replace('\\', '/');
            String applicationpath=SWBUtils.getApplicationPath();
            if(workpath.toLowerCase().startsWith(applicationpath.toLowerCase()))
            {
                workpath=workpath.substring(0,applicationpath.length());
                workpath=SWBPortal.getContextPath()+workpath;
            }            
            html.append("<div id=\""+ ExcelResource.class.getName() +"\">");
            try
            {
                html.append("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + workpath + "\"></iframe>");
            }
            catch (Exception e)
            {
                html.append("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + workpath + "\">This navigator does not support iframe</iframe>");
            }
            html.append("</div>");
        }
        catch(Exception ue)
        {
            log.error(ue);
        }
        return html.toString();
    }
    
    /**
     * Prints the document.
     * 
     * @param out the out
     * @param path the path
     * @param workpath the workpath
     * @param html the html
     * @param paramReq the param req
     */
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


    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.OfficeResource#loadContent(InputStream, User)
     */
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
        updateFileCache(user);
    }

    

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.OfficeResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {   try
        {
            User user = paramRequest.getUser();
            String file=null;
            if(this.getResourceBase().getAttribute(OfficeDocument.FILE_HTML)==null)
            {
                updateFileCache(user);
            }
            else
            {
                file=this.getResourceBase().getAttribute(OfficeDocument.FILE_HTML);
            }
            if (file != null)
            {
                file = file.replace(".xls", ".html");
                file = java.net.URLDecoder.decode(file, "utf-8");
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
    
    /**
     * Creates the excel resource.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.resource.office.sem. excel resource
     */
    public static org.semanticwb.resource.office.sem.ExcelResource createExcelResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.ExcelResource)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }
}
