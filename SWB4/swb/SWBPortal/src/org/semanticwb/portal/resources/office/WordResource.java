/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.office;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.portal.api.*;
import org.semanticwb.portlet.office.WordPortlet;

/**
 *
 * @author victor.lorenzana
 */
public class WordResource extends GenericAdmResource
{

    private static Logger log = SWBUtils.getLogger(WordResource.class);

    protected void beforePrintDocument(WordPortlet porlet, PrintWriter out)
    {
        porlet.beforePrintDocument(out);
    }

    protected void afterPrintDocument(WordPortlet porlet, PrintWriter out)
    {
        porlet.afterPrintDocument(out);
    }

    protected void printDocument(WordPortlet porlet, PrintWriter out, String path, String workpath,String html)
    {
        porlet.printDocument(out, path, workpath,html);
    }

    @Override
    public final void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
    {
        if (this.getResourceBase() instanceof WordPortlet)
        {
            WordPortlet portlet = (WordPortlet) this.getResourceBase();
            String version = portlet.getVersionToShow();
            String contentId = portlet.getContent();
            String repositoryName = portlet.getRepositoryName();
            OfficeDocument document = new OfficeDocument();
            try
            {
                User user = paramReq.getUser();
                String file = document.getContentFile(repositoryName, contentId, version, user);
                if (file != null)
                {

                    file = file.replace(".doc", ".html");
                    String path = SWBPlatform.getWorkPath();
                    if (path.endsWith("/"))
                    {
                        path = path.substring(0, path.length() - 1);
                        path += getResourceBase().getWorkPath() + "\\" + file;
                    }
                    else
                    {
                        path += getResourceBase().getWorkPath() + "\\" + file;
                    }

                    File filecontent = new File(path);
                    if (filecontent.exists())
                    {
                        String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                        StringBuffer html = new StringBuffer();
                        try
                        {
                            FileInputStream in = new FileInputStream(path);
                            byte[] buffer = new byte[2048];
                            int read = in.read(buffer);
                            while (read != -1)
                            {
                                html.append(new String(buffer, 0, read));
                                read = in.read(buffer);
                            }
                            String htmlOut = null;
                            if (portlet.isPaginated() && portlet.getNumberOfPages() > 0)
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath, portlet.getNumberOfPages());
                            }
                            else
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                            }
                            PrintWriter out = response.getWriter();
                            beforePrintDocument(portlet, out);
                            printDocument(portlet, out, path, workpath,htmlOut);
                            afterPrintDocument(portlet, out);
                            out.close();
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }

                    }
                    else
                    {
                        log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + portlet.getContent() + "@" + portlet.getRepositoryName());
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
        }
    }
}
