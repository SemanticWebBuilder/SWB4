/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

import java.util.ArrayList;
import java.util.Iterator;
import org.swb.social.lib.Exception.SWBSocialException;
import org.swb.social.lib.SWBSocialMgr;
import org.swb.social.lib.SWBSocialNetWork;

/**
 *
 * @author jorge.jimenez
 */
public class testSWBSocialMgr {

    static String twitterFQN="org.semanticwb.social.networks.SWBSTwitterImp";
    static String facebookFQN="org.semanticwb.social.networks.SWBSFacebookImp";

    public static void main(String[] args)
    {

        ArrayList aClasses=new ArrayList();
        aClasses.add(twitterFQN);
        aClasses.add(facebookFQN);

        Iterator <String> itClasses=aClasses.iterator();

        while(itClasses.hasNext())
        {
            String sClass=itClasses.next();
            Object obj=null;
            try{
                Class classInstance=Class.forName(sClass);
                obj=classInstance.newInstance();
                send2Network(obj);
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        
    }


    private static void send2Network(Object obj)
    {
        try{
            //Se configura una red social
            SWBSocialNetWork swbSocialObj=(SWBSocialNetWork) obj;
            swbSocialObj.connect();
            swbSocialObj.setMsg("Hola Mundo este es un mensaje de pazz..");
            //Se le pasa la red social al manager
            SWBSocialMgr socialMgr=new SWBSocialMgr(swbSocialObj);
            socialMgr.sendPost();
            //Se desconecta la red social
            swbSocialObj.disConnect();
        }catch(SWBSocialException e)
        {
            e.printStackTrace();
        }
    }

}
