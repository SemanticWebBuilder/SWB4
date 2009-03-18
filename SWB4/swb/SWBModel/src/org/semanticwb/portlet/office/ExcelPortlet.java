package org.semanticwb.portlet.office;

import java.util.HashMap;
import org.semanticwb.platform.SemanticProperty;

public class ExcelPortlet extends org.semanticwb.portlet.office.base.ExcelPortletBase
{
    public static final String WITH = "100%"; // VALUE WIDTH  BY DEFAULT
    public static final String HEIGHT = "500"; // VALUE HEIGHT BY DEFAULT

    public ExcelPortlet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void validatePropertyValue(HashMap<SemanticProperty, Object> values) throws Exception
    {
    }    
}
