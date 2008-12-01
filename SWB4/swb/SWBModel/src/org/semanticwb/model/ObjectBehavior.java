package org.semanticwb.model;

import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticObject;

public class ObjectBehavior extends ObjectBehaviorBase 
{
    public ObjectBehavior(SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getURL()
    {
        String ret=super.getURL();
        ret=SWBUtils.TEXT.replaceAll(ret, "{contextPath}", SWBPlatform.getContextPath());
        return ret;
    }


}
