package org.semanticwb.resource.office;

import java.io.PrintWriter;
import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticProperty;


public class WordResource extends org.semanticwb.resource.office.base.WordResourceBase
{
    private static Logger log = SWBUtils.getLogger(WordResource.class);
    public WordResource(org.semanticwb.platform.SemanticObject base)
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
        out.write(html);
    }
}
