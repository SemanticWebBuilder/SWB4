package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;

public interface Photoable extends org.semanticwb.social.base.PhotoableBase
{
    public void postPhoto(Photo photo, HttpServletRequest request, SWBActionResponse response);
}
