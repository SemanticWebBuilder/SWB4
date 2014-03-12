package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticObserver;


public class PostOut extends org.semanticwb.social.base.PostOutBase 
{
    public PostOut(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static PostOut getPostOutbySocialMsgId(SWBModel model, String socialMsgId)
    {
        //System.out.println("PostIn/getPostInbySocialMsgId-LLega:"+socialMsgId);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(PostOutNet.social_po_socialNetMsgID, socialMsgId);
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            //System.out.println("objJorge:"+obj);
            //System.out.println("objJorge GenInst:"+obj.createGenericInstance());
            if(obj.createGenericInstance() instanceof PostOutNet)
            {
                PostOutNet postOutNet=(PostOutNet)obj.createGenericInstance();
                //System.out.println("postOutNetGeoge:"+postOutNet.getPo_socialNetMsgID());
                //System.out.println("postOutGeoge:"+postOutNet.getSocialPost());
                //return (PostIn)obj.createGenericInstance();
                return postOutNet.getSocialPost();
            }
        }
        return null;
    }    
}
