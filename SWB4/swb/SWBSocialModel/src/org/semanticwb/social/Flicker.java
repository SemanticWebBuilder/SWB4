package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;


public class Flicker extends org.semanticwb.social.base.FlickerBase 
{
    public Flicker(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
