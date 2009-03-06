package org.semanticwb.portlet.office;

import java.io.PrintWriter;
import java.util.HashMap;
import org.semanticwb.platform.SemanticProperty;

public class PPTPortlet extends org.semanticwb.portlet.office.base.PPTPortletBase
{

    public static final String WITH = "100%"; // VALUE WIDTH  BY DEFAULT
    public static final String HEIGHT = "500"; // VALUE HEIGHT BY DEFAULT

    public PPTPortlet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void validatePropertyValue(HashMap<SemanticProperty, Object> values) throws Exception
    {
    }

    @Override
    public void printDocument(PrintWriter out, String path,String workpath,String html)
    {
        String with = WITH;
        String height = HEIGHT;
        out.write("<iframe frameborder=\"0\" src=\"" + path + "\" width=\"" + with + "\" height=\"" + height + "\">Este navegador no soporta iframe</iframe>");
    }
}
