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
package org.semanticwb.resource.office.sem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

// TODO: Auto-generated Javadoc
/**
 * The Class PPTResource.
 */
public class PPTResource extends org.semanticwb.resource.office.sem.base.PPTResourceBase
{

    /**
     * Instantiates a new pPT resource.
     */
    public PPTResource()
    {
        super();
    }

    /**
     * Instantiates a new pPT resource.
     * 
     * @param obj the obj
     */
    public PPTResource(SemanticObject obj)
    {
        super(obj);
    }
    /** The log. */
    private static Logger log = SWBUtils.getLogger(PPTResource.class);

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

    public static String getHTML(File file)
    {
        StringBuilder html = new StringBuilder();
        String workpath = file.getAbsolutePath().replace('\\', '/');
        file = new File(file.getParentFile().getPath() + "/" + "frame.html");

        String applicationpath = SWBUtils.getApplicationPath();
        if (workpath.toLowerCase().startsWith(applicationpath.toLowerCase()))
        {
            workpath = workpath.substring(0, applicationpath.length());
            workpath = SWBPortal.getContextPath() + workpath;
        }
        try
        {
            html.append("<div id=\"").append(PPTResource.class.getName()).append("\"><iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"").append(workpath).append("\"></iframe><br>");

        }
        catch (Exception e)
        {
            html.append("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"").append(workpath).append("\">This navigator does not support iframe</iframe></div>");
        }
        return html.toString();
    }

    /**
     * Gets the hTML.
     * 
     * @param file the file
     * @return the hTML
     */
    public static String getHTML(File file, String contentFile)
    {
        StringBuilder html = new StringBuilder();
        String workpath = file.getAbsolutePath().replace('\\', '/');
        file = new File(file.getParentFile().getPath() + "/" + "frame.html");
        if (!file.exists())
        {
            contentFile = contentFile.replace(".pptx", ".html");
            contentFile = contentFile.replace(".ppt", ".html");
            file = new File(file.getParentFile().getPath() + "/" + contentFile);
        }
        String applicationpath = SWBUtils.getApplicationPath();
        if (workpath.toLowerCase().startsWith(applicationpath.toLowerCase()))
        {
            workpath = workpath.substring(0, applicationpath.length());
            workpath = SWBPortal.getContextPath() + workpath;
        }
        try
        {
            html.append("<div id=\"").append(PPTResource.class.getName()).append("\"><iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"").append(workpath).append("\"></iframe><br>");

        }
        catch (Exception e)
        {
            html.append("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"").append(workpath).append("\">This navigator does not support iframe</iframe></div>");
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
     * @param resourcewebworkpath the resourcewebworkpath
     * @param fileppt the fileppt
     */
    protected void printDocument(PrintWriter out, String path, String workpath, String html, SWBParamRequest paramReq, String resourcewebworkpath, String fileppt)
    {
        try
        {
            out.write("<div id=\"" + PPTResource.class.getName() + "\"><iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">" + paramReq.getLocaleString("frameNotsupport") + "</iframe><br>");
            if (this.isShowDownload())
            {
                String pptpath = resourcewebworkpath + "/" + fileppt;
                out.write("<p><a href=\"" + pptpath + "\">" + paramReq.getLocaleString("download") + "</a></p>");
            }
        }
        catch (Exception e)
        {
            out.write("<iframe width='100%' height='500' frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">This navigator does not support iframe</iframe></div>");
        }
    }

    protected void v3()
    {
        String prefix = "slide";

        String path = SWBPortal.getWorkPath() + getResourceBase().getWorkPath();
        File dir = new File(path);
        File[] files = dir.listFiles(new FileFilter()
        {

            @Override
            public boolean accept(File pathname)
            {
                String prefix = "slide";
                //String prefix_v3 = "v3_";
                if (pathname.getName().startsWith(prefix) && pathname.getName().endsWith(".html"))
                {
                    return true;
                }
                return false;
            }
        });
        String prefix_v3 = "v3_";
        if (files != null && files.length > 0)
        {
            for (File f : files)
            {
                if (f.exists())
                {
                    String oldpath = f.getAbsolutePath();
                    String newName = f.getAbsolutePath() + ".bk";
                    String fileToCopy = f.getParentFile().getAbsolutePath() + "/" + prefix_v3 + f.getName();
                    File f_v3 = new File(fileToCopy);
                    if (f_v3.exists())
                    {
                        f.renameTo(new File(newName));
                        try
                        {
                            String content = SWBUtils.IO.readInputStream(new FileInputStream(f_v3));
                            FileOutputStream out = new FileOutputStream(new File(oldpath));
                            out.write(content.getBytes());
                            out.flush();
                            out.close();
                        }
                        catch (Exception fnfe)
                        {
                            log.error(fnfe);

                        }
                    }
                }


            }
        }


    }

    /* (non-Javadoc)
     * @see org.semanticwb.resource.office.sem.OfficeResource#doView(HttpServletRequest, HttpServletResponse, SWBParamRequest)
     */
    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        try
        {
            String file = null;
            User user = paramRequest.getUser();





            if (paramRequest.getResourceBase().getAttribute(OfficeDocument.FILE_HTML) == null)
            {
                updateFileCache(user, this.getSemanticObject());
            }
            else
            {
                file = this.getResourceBase().getAttribute(OfficeDocument.FILE_HTML);
            }

            if (paramRequest.getResourceBase().getAttribute("v3") == null)
            {
                v3();
                paramRequest.getResourceBase().setAttribute("v3", "true");
            }
            String resourceWebWorkpath = SWBPortal.getWebWorkPath();
            if (file != null)
            {

                String pathFilePPT = SWBPortal.getWorkPath();
                if (pathFilePPT.endsWith("/"))
                {
                    pathFilePPT = pathFilePPT.substring(0, pathFilePPT.length() - 1);
                    pathFilePPT += getResourceBase().getWorkPath() + "frame.html";
                }
                else
                {
                    pathFilePPT += getResourceBase().getWorkPath() + "frame.html";
                }
                String path = null;
                File filecontent = new File(pathFilePPT);
                if (filecontent.exists())
                {
                    path = SWBPortal.getWebWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "/" + "frame.html";
                        resourceWebWorkpath += getResourceBase().getWorkPath();
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "/" + "frame.html";
                        resourceWebWorkpath += getResourceBase().getWorkPath();
                    }
                }
                else
                {
                    file = this.getResourceBase().getAttribute(OfficeDocument.FILE_HTML);
                    file = file.replace(".pptx", ".html");
                    file = file.replace(".ppt", ".html");
                    file = java.net.URLDecoder.decode(file, "utf-8");

                    pathFilePPT = SWBPortal.getWorkPath();
                    if (pathFilePPT.endsWith("/"))
                    {
                        pathFilePPT = pathFilePPT.substring(0, pathFilePPT.length() - 1);
                        pathFilePPT += getResourceBase().getWorkPath() + "/"+file;
                    }
                    else
                    {
                        pathFilePPT += getResourceBase().getWorkPath() + "/"+file;
                    }

                    String content = SWBUtils.IO.readInputStream(new FileInputStream(pathFilePPT));
                    String frag1 = "window.location.replace( \"";
                    String frag2 = "\"+document.location.hash";
                    int pos = content.indexOf(frag1);
                    if (pos != -1)
                    {
                        int pos2 = content.indexOf(frag2, pos + 10);
                        if (pos2 != -1)
                        {
                            String fragment = content.substring(pos + frag1.length(), pos2);
                            int pos3 = fragment.indexOf("/");
                            if (pos3 != -1)
                            {
                                fragment = fragment.substring(pos3 + 1);
                            }
                            StringBuilder sb = new StringBuilder(content.substring(0, pos+frag1.length()));
                            sb.append(fragment);
                            sb.append(content.substring(pos2));

                            SWBPortal.writeFileToWorkPath(getResourceBase().getWorkPath() + "/" + file, new ByteArrayInputStream(sb.toString().getBytes()), user);
                        }
                    }
                    content = SWBUtils.IO.readInputStream(new FileInputStream(pathFilePPT));
                    frag1 = "var path = \"";
                    frag2 = "\"";
                    pos = content.indexOf(frag1);
                    if (pos != -1)
                    {
                        int pos2 = content.indexOf(frag2, pos + frag1.length() + 1);
                        if (pos2 != -1)
                        {
                            String fragment = content.substring(pos + frag1.length(), pos2);
                            int pos3 = fragment.indexOf("/");
                            if (pos3 != -1)
                            {
                                fragment = fragment.substring(pos3 + 1);
                            }
                            StringBuilder sb = new StringBuilder(content.substring(0, pos+frag1.length()));
                            sb.append(fragment);
                            sb.append(content.substring(pos2));

                            SWBPortal.writeFileToWorkPath(getResourceBase().getWorkPath() + "/" + file, new ByteArrayInputStream(sb.toString().getBytes()), user);
                        }
                    }

                    path = SWBPortal.getWebWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "/" + file;
                        resourceWebWorkpath += getResourceBase().getWorkPath();
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "/" + file;
                        resourceWebWorkpath += getResourceBase().getWorkPath();
                    }
                }


                PrintWriter out = response.getWriter();
                beforePrintDocument(out);
                String workpath = SWBPortal.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                printDocument(out, path, workpath, "", paramRequest, resourceWebWorkpath, file);
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
     * Creates the ppt resource.
     * 
     * @param id the id
     * @param model the model
     * @return the org.semanticwb.resource.office.sem. ppt resource
     */
    public static org.semanticwb.resource.office.sem.PPTResource createPPTResource(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.resource.office.sem.PPTResource) model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }
}
