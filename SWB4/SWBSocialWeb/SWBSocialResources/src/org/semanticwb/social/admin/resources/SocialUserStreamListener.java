/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.social.admin.resources;

import java.util.ArrayList;
import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamListener;

/**
 *
 * @author francisco.jimenez
 */
public class SocialUserStreamListener implements UserStreamListener {
    private ArrayList<Status> homeStatus;           //ArrayList of Status
    private ArrayList<Status> favoritesStatus;      //List of received favorites
    private ArrayList<Status> mentionsStatus;       //List of received mentions
    private ArrayList<DirectMessage> directMStatus; //List of received social Direct Messages

    private TwitterStream twitterStream;       //Reference to twitterStream to stop the listener
    private Long startTime;                    //Time when the listener was started
    private Long totalRunningTime;             //Total time of execution of the listener
    private boolean streamActive = true;       //When the listener is halted the value changes    
    private boolean processing = false;
    private long currentUser = 0L;
    
    public SocialUserStreamListener(){
        this.homeStatus = new ArrayList<Status>();
        this.favoritesStatus = new ArrayList<Status>();
    }
    public SocialUserStreamListener(TwitterStream twitterStream){
        this.startTime = System.currentTimeMillis();
        this.twitterStream = twitterStream;
        this.homeStatus = new ArrayList<Status>();
        this.favoritesStatus = new ArrayList<Status>();
        this.mentionsStatus = new ArrayList<Status>();
        this.directMStatus = new ArrayList<DirectMessage>();
    }   
    
    @Override
    public void onStatus(Status status) {
        //status.
        System.out.println("\t\tEl mensaje es RT:" + status.isRetweet());
        System.out.println("\t\tEl id del mensaje es: " + status.getId());
        System.out.println("\t\tEl mensaje es MI RT:" + status.isRetweetedByMe());
        
        if(isAMention(status)){// Is the current user mentioned in a Tweet
            System.out.println("\n\n++++++++++++++\nTenemos una nueva mention: " + status.getText());
            this.mentionsStatus.add(status);
        }
        
        //status.isRetweet() && currentUser == status.getUser().getId()
        if(currentUser != status.getUser().getId() || (!status.isRetweet() && status.getUser().getId() == currentUser) ){
            this.homeStatus.add(status);
            this.totalRunningTime = System.currentTimeMillis() - this.startTime;
            System.out.println("difference: " + this.totalRunningTime);
            if (this.totalRunningTime > 60000*30){//Has been alive for 15 min since last interaction
               System.out.println("15 minutes of inactivity. Time to stop the thread!");
               this.twitterStream.cleanUp();
               this.twitterStream.shutdown();
               this.twitterStream = null;
               this.streamActive = false;
            }else{
                System.out.println("New status: @" + status.getUser().getScreenName());
            }
        }
    }

    public boolean stopListener(){
        boolean isStoped = false;
        try{
            this.twitterStream.cleanUp();
            this.twitterStream.shutdown();
            this.twitterStream = null;
            this.streamActive = false;
            isStoped = true;
        }catch(Exception e){
            System.out.println("Error in stopListener()");
        }
        return isStoped;
    }
    //We need to define actions for all the other events.
    @Override
    public void onDeletionNotice(long directMessageId, long userId) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onFriendList(long[] friendIds) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onFavorite(User source, User target, Status favoritedStatus) {
        favoritesStatus.add(favoritedStatus);
    }

    @Override
    public void onUnfavorite(User source, User target, Status unfavoritedStatus) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onFollow(User source, User followedUser) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDirectMessage(DirectMessage directMessage) {
        directMStatus.add(directMessage);
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListMemberAddition(User addedMember, User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListMemberDeletion(User deletedMember, User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListSubscription(User subscriber, User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListUnsubscription(User subscriber, User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListCreation(User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListUpdate(User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserListDeletion(User listOwner, UserList list) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUserProfileUpdate(User updatedUser) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onBlock(User source, User blockedUser) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onUnblock(User source, User unblockedUser) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onStallWarning(StallWarning warning) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onException(Exception ex) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private boolean isAMention(Status status){
        boolean isUserMentioned = false;
        UserMentionEntity usrEnts[] = status.getUserMentionEntities();
        //Looking for user entities
        if(usrEnts!=null && usrEnts.length >0){
            for(UserMentionEntity usrEnt: usrEnts){
                if(usrEnt.getId() == currentUser){
                    isUserMentioned = true;
                    break;
                }
            }
        }
        return isUserMentioned;
    }
    
    public void setCurrentUser(long currentUser){
        this.currentUser = currentUser;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }
    
    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
    
    public boolean isStreamActive() {
        return streamActive;
    }

    public void setStreamActive(boolean streamActive) {
        this.streamActive = streamActive;
    }
    
    public void clearHomeStatus(){
        System.out.println("Cleaning Home statuses");
        System.out.println("favs: " + getFavoritesSize());
        System.out.println("ment: " + getMentionsSize());
        System.out.println("dmsgs: " + getDirectMSize());
        System.out.println("home: " +  getHomeStatusSize());
        this.homeStatus.clear();
    }
    public void clearFavoritesStatus(){
        this.favoritesStatus.clear();
    }
    public void clearMentionsStatus(){
        this.mentionsStatus.clear();
    }
    public void clearDirectMStatus(){
        this.directMStatus.clear();
    }
    
    public Status getHomeStatus(int index){
        return this.homeStatus.get(index);
    }
    
    public Status getFavoritesStatus(int index){
        return this.favoritesStatus.get(index);
    }
    
    public Status getMentionsStatus(int index){
        return this.mentionsStatus.get(index);
    }
    
    public DirectMessage getDirectMStatus(int index){
        return this.directMStatus.get(index);
    }
    
    public int getHomeStatusSize(){
        return this.homeStatus.size();
    }
    
    public int getFavoritesSize(){
        return this.favoritesStatus.size();
    }
    
    public int getMentionsSize(){
        return this.mentionsStatus.size();
    }
    
    public int getDirectMSize(){
        return this.directMStatus.size();
    }
}
