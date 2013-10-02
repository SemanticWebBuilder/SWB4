package org.semanticwb.social;

import java.util.Iterator;
import org.semanticwb.model.SWBModel;
import org.semanticwb.platform.SemanticObject;


   /**
   * Clase en la cual se almacenan los usuarios que escriben los PostIn que llegan. El identificador de c/intancia de esta clase es el identificador de un usuarios en una red social. 
   */
public class SocialNetworkUser extends org.semanticwb.social.base.SocialNetworkUserBase 
{
    //USER GENDER
    public static final int USER_GENDER_MALE=1;
    public static final int USER_GENDER_FEMALE=2;
    public static final int USER_GENDER_UNDEFINED=3;
    
    //USER RELATIONSHIP STATUS
    public static final int USER_RELATION_SINGLE=1;
    public static final int USER_RELATION_MARRIED=2;
    public static final int USER_RELATION_DIVORCED=3;
    public static final int USER_RELATION_WIDOWED=4;
    public static final int USER_RELATION_UNDEFINED=5;
    
    //USER EDUCATION
    public static final int USER_EDUCATION_HIGHSCHOOL=1;
    public static final int USER_EDUCATION_COLLEGE=2;
    public static final int USER_EDUCATION_GRADUATE=3;
    public static final int USER_EDUCATION_UNDEFINED=4;
    
    public SocialNetworkUser(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static SocialNetworkUser getSocialNetworkUserbyIDAndSocialNet(String userId, SocialNetwork socialNetwork, SWBModel model)
    {
        //System.out.println("entra getSocialNetworkUserbyIDAndSocialNet-1:"+socialNetwork);
        Iterator<SemanticObject> it=model.getSemanticModel().listSubjects(social_snu_id, userId); //No encuentra
        while(it.hasNext())
        {
            SemanticObject obj=it.next();
            SocialNetworkUser socialNetUser=(SocialNetworkUser)obj.createGenericInstance();
            //System.out.println("GEOOOOORGGGEEE:socialNetUser.getSnu_SocialNetwork().getId():"+socialNetUser.getSnu_SocialNetworkObj().getId());
            //System.out.println("GEOOOOORGGGEEE:socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId():"+socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId());
            if(socialNetUser.getSnu_SocialNetworkObj()!=null && socialNetUser.getSnu_SocialNetworkObj().getId().equals(socialNetwork.getSemanticObject().getSemanticClass().getSemanticObject().getId()));
            {
                  return socialNetUser;
            }
        }
        return null;
    }
}
