package org.semanticwb.resource.office.sem;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;

public class WordResource extends org.semanticwb.resource.office.sem.base.WordResourceBase 
{
    public WordResource()
    {
        super();
    }
    public WordResource(SemanticObject obj)
    {
        super(obj);
    }
    private static Logger log = SWBUtils.getLogger(WordResource.class);
    protected void beforePrintDocument(PrintWriter out)
    {

    }

    protected void afterPrintDocument(PrintWriter out)
    {

    }

    protected void printDocument(PrintWriter out, String path, String workpath,String html)
    {
        out.write(html);
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
                            if (isPaginated() && getNumberOfPages() > 0)
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath, getNumberOfPages());
                            }
                            else
                            {
                                htmlOut = SWBPortal.UTIL.parseHTML(html.toString(), workpath);
                            }
                            PrintWriter out = response.getWriter();
                            beforePrintDocument( out);
                            printDocument(out, path, workpath,htmlOut);
                            afterPrintDocument(out);
                            out.close();
                        }
                        catch (Exception e)
                        {
                            log.error(e);
                        }

                    }
                    else
                    {
                        log.error("Contenido no encontrado en ruta: " + filecontent.getAbsolutePath() + ": " + getContent() + "@" + getRepositoryName());
                    }
                }
            }
            catch (Exception e)
            {
                log.error(e);
            }
    }

}
