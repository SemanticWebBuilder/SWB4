package org.semanticwb.resource.office.sem;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.office.comunication.OfficeDocument;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.portal.api.*;



public class ExcelResource extends org.semanticwb.resource.office.sem.base.ExcelResourceBase
{
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
        try
        {
            out.write("<iframe frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">" + paramReq.getLocaleString("frameNotsupport") + "</iframe>");
        }
        catch (Exception e)
        {
            out.write("<iframe frameborder=\"0\" scrolling=\"auto\" src=\"" + path + "\">This navigator does not support iframe</iframe>");
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
            if (file != null)
            {

                file = file.replace(".xls", ".html");
                String path = SWBPlatform.getWebWorkPath();
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
                String workpath = SWBPlatform.getWebWorkPath() + getResourceBase().getWorkPath() + "/";
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
}
