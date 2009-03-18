package org.semanticwb.portlet.office;

import java.util.HashMap;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticProperty;

public class WordPortlet extends org.semanticwb.portlet.office.base.WordPortletBase
{
    private static Logger log = SWBUtils.getLogger(WordPortlet.class);
    public WordPortlet(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void validatePropertyValue(HashMap<SemanticProperty, Object> values) throws Exception
    {
    }

}
