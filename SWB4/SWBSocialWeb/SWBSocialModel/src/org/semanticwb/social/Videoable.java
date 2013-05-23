package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;

public interface Videoable extends org.semanticwb.social.base.VideoableBase
{
    public void postVideo(Video video);
}
