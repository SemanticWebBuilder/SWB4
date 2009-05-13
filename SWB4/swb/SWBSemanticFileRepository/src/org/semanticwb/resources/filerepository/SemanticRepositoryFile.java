package org.semanticwb.resources.filerepository;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.*;
import org.semanticwb.portal.api.*;

public class SemanticRepositoryFile extends org.semanticwb.resources.filerepository.base.SemanticRepositoryFileBase 
{

    public SemanticRepositoryFile()
    {
    }

    public SemanticRepositoryFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException
    {
        PrintWriter out=response.getWriter();
        out.println("Hello SemanticRepositoryFile...");    }

}
