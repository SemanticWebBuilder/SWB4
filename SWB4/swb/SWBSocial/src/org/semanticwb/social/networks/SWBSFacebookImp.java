/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.networks;

import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.swb.social.lib.Exception.SWBSocialNetworkException;
import org.swb.social.lib.SWBSocialNetWork;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSFacebookImp extends SWBSocialNetWork
{

    private static Logger log = SWBUtils.getLogger(SWBSFacebookImp.class);

    public void connect() throws SWBSocialNetworkException
    {
        System.out.println("Se conecta a la red social");
    }

    public void sendPost() throws SWBSocialNetworkException
    {
        System.out.println("Enviando Mensaje a publicar en SWBSFacebookImp:"+msg);
    }

    public void disConnect() throws SWBSocialNetworkException
    {
         System.out.println("Se desconecta de la red social");
    }

}
