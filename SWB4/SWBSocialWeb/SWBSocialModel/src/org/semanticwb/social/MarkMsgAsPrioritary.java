package org.semanticwb.social;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;


   /**
   * Acción específica mediante la cual se marca un mensaje como prioritario. Esto en la propiedad "IsPrioritary" de un mensaje (Post). 
   */
public class MarkMsgAsPrioritary extends org.semanticwb.social.base.MarkMsgAsPrioritaryBase 
{
    private static Logger log = SWBUtils.getLogger(MarkMsgAsPrioritary.class);
    
    public MarkMsgAsPrioritary(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public static void markMsgAsPrioritary(Action action, PostIn postIn)
    {
        if(!(action instanceof MarkMsgAsPrioritary)) return;
        try
        {
            postIn.setIsPrioritary(true);
        }catch(Exception e)
        {
            log.error(e);
        }
    }
    
}
