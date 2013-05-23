package org.semanticwb.social;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;

public interface Messageable extends org.semanticwb.social.base.MessageableBase
{
     public void postMsg(Message message);
}
