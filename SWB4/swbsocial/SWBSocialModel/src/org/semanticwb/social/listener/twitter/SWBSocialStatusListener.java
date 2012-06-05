/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.listener.twitter;

import java.util.Date;
import org.semanticwb.model.SWBModel;
import org.semanticwb.social.MessageIn;
import org.semanticwb.social.SocialNetwork;
import org.semanticwb.social.SocialNetworkUser;
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
            
            System.out.println();
            System.out.println("------------");
            System.out.println(status.getUser().getName() + " : " + status.getText() + " : " + status.getGeoLocation());
            System.out.println(status.getCreatedAt());
            
            //Persistencia del mensaje
            MessageIn mesagge=MessageIn.ClassMgr.createMessageIn(String.valueOf(status.getId()), model);
            mesagge.setMsg_Text(status.getText());
            mesagge.setPostInSocialNetwork(socialNetwork);
            
            if(status.getUser()!=null)
            {
                long userId=status.getUser().getId();
                String name=status.getUser().getScreenName();
                Date userNetworkCreatedDate=status.getUser().getCreatedAt();
                int followers=status.getUser().getFollowersCount();
                int friends=status.getUser().getFriendsCount();
                SocialNetworkUser socialNetUser=SocialNetworkUser.getSocialNetworkUserbyIDAndSocialNet(""+userId, socialNetwork, model);
                if(socialNetUser==null)//
                {
                    //Si no existe el id del usuario para esa red social, lo crea.
                    socialNetUser=SocialNetworkUser.ClassMgr.createSocialNetworkUser(model);
                    socialNetUser.setSnu_id(""+userId);
                    socialNetUser.setSnu_name(name);
                    socialNetUser.setSnu_SocialNetwork(socialNetwork);
                    socialNetUser.setCreated(userNetworkCreatedDate);
                    //System.out.println("SocialNetworkUser Creado:"+socialNetUser.getSnu_id());
                }else{
                    //System.out.println("SocialNetworkUser Actualizado:"+socialNetUser.getSnu_id());
                    socialNetUser.setUpdated(new Date());
                }
                socialNetUser.setFollowers(followers);
                socialNetUser.setFriends(friends);
                //int listedCount=status.getUser().getListedCount();
                //System.out.println("userId:"+userId+", created:"+socialNetUser.getCreated()+", followers:"+socialNetUser.getFollowers()+", friends:"+socialNetUser.getFriends()+", name:"+socialNetUser.getSnu_name());
                if(socialNetUser!=null)
                {
                    mesagge.setPostInSocialNetworkUser(socialNetUser);
                }
            }
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
