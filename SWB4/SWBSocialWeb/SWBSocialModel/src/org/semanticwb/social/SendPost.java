package org.semanticwb.social;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Acción específica mediante la cual se envía un mensaje por defecto a una o varias redes sociales seleccionadas 
   */
public class SendPost extends org.semanticwb.social.base.SendPostBase 
{
    private static Logger log = SWBUtils.getLogger(SendPost.class);
    
    public SendPost(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public static void sendPost(Action action, PostIn postIn)
    {
        if(!(action instanceof SendPost)) return;
        try
        {
            SendPost sendPost=(SendPost)action;
            if(sendPost.getMsg_Text()!=null && sendPost.listSocialNetworkses().hasNext())
            {
                //TODO:Implementar
                //String message=SWBSocialUtil.Util.replaceTags(sendEmail.getActionMsg(), postIn, stream.getSocialSite(), stream, socialNetwork);
                //SWBUtils.EMAIL.sendBGEmail(sendEmail.getEmail(), "Acción - Mensaje de entrada", message);
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}
