package org.semanticwb.social;

import java.util.Iterator;


   /**
   * Clase en la que se guardan datos que sirven para realizar una siguiente busqueda en una determinada red social y en un determinado stream. 
   */
public class SocialNetStreamSearch extends org.semanticwb.social.base.SocialNetStreamSearchBase 
{
    public SocialNetStreamSearch(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    public static SocialNetStreamSearch getSocialNetStreamSearchbyStreamAndSocialNetwork(Stream stream, SocialNetwork socialNetwork)
    {
        Iterator <SocialNetStreamSearch> itSocialNetStreamSearch=SocialNetStreamSearch.ClassMgr.listSocialNetStreamSearchByStream(stream, stream.getSocialSite());
        while(itSocialNetStreamSearch.hasNext())
        {
            SocialNetStreamSearch socialStreamSerch=itSocialNetStreamSearch.next();
            if(socialStreamSerch.getSocialNetwork().getId().equals(socialNetwork.getId()))
            {
               
                return socialStreamSerch;
            }
        }
        //Si no existe la instancia de esta clase SocialNetStreamSearch, para el stream y la red social en conjunto, entonces la crea y la regresa
        SocialNetStreamSearch socialStreamSerch=SocialNetStreamSearch.ClassMgr.createSocialNetStreamSearch(stream.getSocialSite());
        socialStreamSerch.setStream(stream);        
        socialStreamSerch.setSocialNetwork(socialNetwork);    
        
        return socialStreamSerch;
    }
}
