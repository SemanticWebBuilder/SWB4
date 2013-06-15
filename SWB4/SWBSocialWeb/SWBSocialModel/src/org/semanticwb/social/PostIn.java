package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


public class PostIn extends org.semanticwb.social.base.PostInBase 
{
    public PostIn(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
     /*
     * Busqueda de aseguradora por nombre de clase de implementación de webservice de cotización
     */
    public static PostIn getPostInbySocialMsgId(SWBModel model, String socialMsgId)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(PostIn.social_socialNetMsgId, socialMsgId);
        if(it.hasNext())
        {
            SemanticObject obj=it.next();
            return (PostIn)obj.createGenericInstance();
        }
        return null;
    }
}
