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
        return ret;
    }

    public String getParsedURL()
    {
        String ret=super.getURL();
        if(ret!=null)ret=SWBUtils.TEXT.replaceAll(ret, "{contextPath}", SWBPlatform.getContextPath());
        return ret;
    }



}
