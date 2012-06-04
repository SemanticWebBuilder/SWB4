/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener.twitter;

import org.semanticwb.model.SWBModel;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.listener.Classifier;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;

/**
 *
 * @author jorge.jimenez
 */
public class SWBSocialStatusListener implements twitter4j.StatusListener {

    SWBModel model=null;
    SocialNetwork socialNetwork=null;

    public SWBSocialStatusListener(SWBModel model, SocialNetwork socialNetwork)
    {
        this.model=model;
        this.socialNetwork=socialNetwork;
    }

    @Override
    public void onStatus(Status status) {
        //if (status.getGeoLocation() != null)
        //if(status.getText().indexOf("android")>-1 || status.getText().indexOf("ipad")>-1 || status.getText().indexOf("iphone")>-1 || status.getText().indexOf("tarea")>-1)
        {
            /*
            System.out.println();
            System.out.println("------------");
            System.out.println(status.getUser().getName() + " : " + status.getText() + " : " + status.getGeoLocation());
            System.out.println(status.getCreatedAt());
             * 
             */

            //Persistencia del mensaje
            MessageIn mesagge=MessageIn.ClassMgr.createMessageIn(String.valueOf(status.getId()), model);
            mesagge.setMsg_Text(status.getText());
            mesagge.setPostInSocialNetwork(socialNetwork);
            socialNetwork.addReceivedPost(mesagge, String.valueOf(status.getId()), socialNetwork);
            new Classifier(mesagge, model);
        }
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
    }

    @Override
    public void onTrackLimitationNotice(int i) {
    }

    @Override
    public void onScrubGeo(long l, long l1) {
    }

    @Override
    public void onException(Exception excptn) {
    }



}
