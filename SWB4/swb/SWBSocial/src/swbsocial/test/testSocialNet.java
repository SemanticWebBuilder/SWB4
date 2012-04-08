/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package swbsocial.test;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.social.Twitter;

/**
 *
 * @author jorge.jimenez
 */
public class testSocialNet {


    private static Logger log = SWBUtils.getLogger(testSocialNet.class);


    public static void main(String[] args)
    {

        String msg="Este es un mensaje de paz mundial...";

        Twitter.ClassMgr.getTwitter("twitter1", null);




    }

}