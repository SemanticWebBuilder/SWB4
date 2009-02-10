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

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramReq) throws SWBResourceException, IOException
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
                PrintWriter out = response.getWriter();
                String file = document.getContentFile(repositoryName, contentId, version);
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
                    StringBuffer html = new StringBuffer();
                    File filecontent = new File(path);
                    if (filecontent.exists())
                    {
                        FileInputStream in = new FileInputStream(path);
                        byte[] buffer = new byte[2048];
                        int read = in.read(buffer);
                        while (read != -1)
                        {
                            html.append(new String(buffer, 0, read));
                            read = in.read(buffer);
                        }
                        String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
                        String htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                        out.write(htmlOut);
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
