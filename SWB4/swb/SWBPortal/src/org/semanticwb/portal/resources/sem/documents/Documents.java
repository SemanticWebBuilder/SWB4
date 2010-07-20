package org.semanticwb.portal.resources.sem.documents;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class Documents extends org.semanticwb.portal.resources.sem.documents.base.DocumentsBase 
{

    public Documents()
    {
    }

    public Documents(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello Documents...");    }

}
