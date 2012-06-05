package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social. 
   */
public class SocialNetworkUser extends org.semanticwb.social.base.SocialNetworkUserBase 
{
    public SocialNetworkUser(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static SocialNetworkUser getSocialNetworkUserbyIDAndSocialNet(String userId, SocialNetwork socialNetwork, SWBModel model)
    {
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_snu_id, userId); //No encuentra
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            SocialNetworkUser socialNetUser=(SocialNetworkUser)obj.createGenericInstance();
            if(socialNetUser.getSnu_SocialNetwork()!=null && socialNetUser.getSnu_SocialNetwork().getId().equals(socialNetwork.getId()));
            {
                  return socialNetUser;
            }
        }
        return null;
    }
}
