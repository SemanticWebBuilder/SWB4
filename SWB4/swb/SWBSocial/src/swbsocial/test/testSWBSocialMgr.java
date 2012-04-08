/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

import java.util.ArrayList;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.networks.SWBSFacebookImp;
import org.semanticwb.social.networks.SWBSTwitterImp;

/**
 *
 * @author jorge.jimenez
 */
public class testSWBSocialMgr {

    private static Logger log = SWBUtils.getLogger(testSWBSocialMgr.class);

    static String twitterFQN="org.semanticwb.social.networks.SWBSTwitterImp";
    static String facebookFQN="org.semanticwb.social.networks.SWBSFacebookImp";

    public static void main(String[] args)
    {
        ArrayList aClasses=new ArrayList();
        aClasses.add(twitterFQN);
        aClasses.add(facebookFQN);

        String msg="Este es el mensaje de paz...";
        // Para el uso con un wrapper
        /*
        Iterator <String> itClasses=aClasses.iterator();
        while(itClasses.hasNext())
        {
           try{
                SWBSocialMgr swbSocialNetWork=new SWBSocialMgr(itClasses.next());
                swbSocialNetWork.connect();
                swbSocialNetWork.setMsg(msg);
                swbSocialNetWork.sendPost();
                swbSocialNetWork.disConnect();
            }catch (Exception e){
                log.error(e);
            }
        }
        */
        /////////.......................................................///////////////

        //Para el uso de una determinada red social directamente
        //WebSite wsite=WebSite.ClassMgr.getWebSite("visit");
        /*
        SWBSTwitterImp swbTwitter=new SWBSTwitterImp(null);
        try
        {
            //swbTwitter.setMsg(msg);
            swbTwitter.sendPost();
        }catch(Exception e)
        {
         log.error(e);
        }

        SWBSFacebookImp swbFacebook=new SWBSFacebookImp(null);
        try
        {
            swbFacebook.connect();
            //swbFacebook.setMsg(msg);
            swbFacebook.sendPost();
            swbFacebook.disConnect();
        }catch(Exception e)
        {
         log.error(e);
        }
        */
    }

}
