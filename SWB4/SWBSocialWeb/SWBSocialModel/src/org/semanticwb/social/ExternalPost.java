package org.semanticwb.social;

import java.util.Date;

/**
 * Represents any message, image or video posted in any of the social networks
 * at which this application searches for information
 * @author jose.jimenez
 */
public class ExternalPost {
    
    
    /**
     * The identification string or number for the message
     */
    private String postId;
    
    /**
     * The message's creator's name
     */
    private String creatorName;
    
    /**
     * The message's creator's identification number or string
     */
    private String creatorId;
    
    /**
     * The message posted
     */
    private String message;
    
    /**
     * The link or URL for a picture related to the message
     */
    private String picture;
    
    /**
     * The link or URL for a video related to the message
     */
    private String video;
    
    /**
     * The category of the message, its very used with categorias for youtube
     */
    private String category;
    
    /**
     * A link related to the message
     */
    private String link;
    
    /**
     * The name with which this message was created
     */
    private String postName;
    
    
    /**
     * The text that titles this message
     */
    private String tags;
    
    /**
     * The text that describes the link related to this message
     */
    private String description;
    
    /**
     * A link to an icon representing the type of this post (in Facebook)
     */
    private String icon;
    
    /**
     * The time the post was initially published
     */
    private String creationTime;
    
    /**
     * The time of the last comment or update to this post
     */
    private String updateTime;
    
    /**
     * Creator's friends number
     */
    private int friendsNumber;
    
    /**
     * Creator's followers number
     */
    private int followers;
    
    /**
     * Number that post has been retweeted
     */
    private int retweets;
    
    /**
     * Device used by the post creator
     */
    private String device;
    
    /**
     * Date when the user was created
     */
    private Date usercreation;
    
    /**
     * Place where the post was originated
     */
    private String place;
    
    /**
     * location where the post was originated
     */
    private String location;
    
    /**
     * Indicates the type of post retrieved from the Social Net
     */
    private String postType;
    

    /**
     * Creates a new instance of ExternalPost
     */
    public ExternalPost() {
    }
    
    /**
     * @return the postId
     */
    public String getPostId() {
        return postId;
    }

    /**
     * @param postId the postId to set
     */
    public void setPostId(String postId) {
        this.postId = postId;
    }

    /**
     * @return the creatorName
     */
    public String getCreatorName() {
        return creatorName;
    }

    /**
     * @param creatorName the creatorName to set
     */
    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    /**
     * @return the creatorId
     */
    public String getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId the creatorId to set
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the picture
     */
    public String getPicture() {
        return picture;
    }
    
    
    
    /**
     * @param picture the picture to set
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }
    
    
     /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }
    
     /**
     * @param tags the Tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    
    /**
     * @return the video
     */
    public String getVideo() {
        return video;
    }
    
    /**
     * @param video the video to set
     */
    public void setVideo(String video) {
        this.video = video;
    }
    
    /**
     * @return the category, most used when is a Video
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
    

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the postName
     */
    public String getPostName() {
        return postName;
    }

    /**
     * @param postName the postName to set
     */
    public void setPostName(String postName) {
        this.postName = postName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the linkDescription to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the creationTime
     */
    public String getCreationTime() {
        return creationTime;
    }

    /**
     * @param creationTime the creationTime to set
     */
    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    /**
     * @return the updateTime
     */
    public String getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the updateTime to set
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return the friendsNumber
     */
    public int getFriendsNumber() {
        return friendsNumber;
    }

    /**
     * @param friendsNumber the friendsNumber to set
     */
    public void setFriendsNumber(int friendsNumber) {
        this.friendsNumber = friendsNumber;
    }

    /**
     * @return the followers
     */
    public int getFollowers() {
        return followers;
    }

    /**
     * @param followers the followers to set
     */
    public void setFollowers(int followers) {
        this.followers = followers;
    }
    
    /**
     * 
     * @return the retweets
     */
    public int getRetweets()
    {
       return retweets; 
    }
    
    
    /**
     * 
     * @param the retweets to set
     */
    public void setRetweets(int retweets)
    {
       this.retweets=retweets;
    }
    
     /**
     * 
     * @return the device
     */
    public String getDevice()
    {
       return device; 
    }
    
    
    /**
     * 
     * @param the device to set
     */
    public void setDevice(String device)
    {
       this.device=device; 
    }
    
    /**
     * 
     * @return usercreation
     */
    public Date getUsercreation()
    {
        return usercreation;
    }
    
    /**
     * 
     * @param usercreation the creation date to set
     */
    public void setUsercreation(Date usercreation)
    {
        this.usercreation=usercreation;
    }
    
    /**
     * 
     * @return the place
     */
    public String getPlace()
    {
        return place;
    }
    
    
    /**
     * 
     * @param place the place to set
     */
    public void setPlace(String place)
    {
        this.place=place;
    }
    
    /**
     * 
     * @return location the location where the post was originated
     */
    public String getLocation()
    {
       return location; 
    }
    
    /**
     * 
     * @param location the location where the post was originated
     * 
     */
    public void setLocation(String location)
    {
        this.location=location;
    }

    /**
     * Returns the type of post retrieved from the social net. Facebook uses one
     * of the following types: {@literal link}, {@literal page}, {@literal video},
     * {@literal status}, {@literal user}, {@literal photo}.
     * @return the postType
     */
    public String getPostType() {
        return postType;
    }

    /**
     * @param postType the postType to set
     */
    public void setPostType(String postType) {
        this.postType = postType;
    }
    
}
