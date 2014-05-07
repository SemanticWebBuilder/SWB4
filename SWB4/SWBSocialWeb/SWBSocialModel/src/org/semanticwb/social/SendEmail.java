package org.semanticwb.social;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.listener.SentimentalDataClassifier;
import org.semanticwb.social.util.SWBSocialUtil;


   /**
   * Acción específica mediante la cual se envía un correo electrónico 
   */
public class SendEmail extends org.semanticwb.social.base.SendEmailBase 
{
    
    private static Logger log = SWBUtils.getLogger(SendEmail.class);
    
    public SendEmail(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public static void sendEmail(Action action, PostIn postIn, Stream stream, SocialNetwork socialNetwork)
    {
        if(!(action instanceof SendEmail)) return;
        try
        {
            SendEmail sendEmail=(SendEmail)action;
            if(sendEmail.getEmail()!=null && sendEmail.getActionMsg_Text()!=null)
            {
                String message=SWBSocialUtil.Util.replaceTags(sendEmail.getActionMsg_Text(), postIn, stream.getSocialSite(), stream, socialNetwork);
                SWBUtils.EMAIL.sendBGEmail(sendEmail.getEmail(), "Acción - Mensaje de entrada", message);
            }
        }catch(Exception e)
        {
            log.error(e);
        }
    }
}
