/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semantic.blogger.interfaces;

import org.semanticwb.xmlrpc.XmlRpcMethod;

/**
 *
 * @author victor.lorenzana
 */
public interface MetaWeblog
{

    @XmlRpcMethod(methodName = "Blogger.newPost")
    public String newPost(String blogid, String userid, String password, Post post, boolean publish) throws Exception;

    @XmlRpcMethod(methodName = "Blogger.editPost")
    public boolean editPost(String postid, String userid, String password, Post post, boolean publish) throws Exception;

    @XmlRpcMethod(methodName = "Blogger.getPost")
    public Post getPost(String postid, String userid, String password) throws Exception;

    @XmlRpcMethod(methodName = "Blogger.getCategories")
    public CategoryInfo[] getCategories(String blogid, String username, String password) throws Exception;

    @XmlRpcMethod(methodName = "Blogger.getRecentPosts")
    public Post[] getRecentPosts(String blogid, String username, String password, int numberOfPosts) throws Exception;

    @XmlRpcMethod(methodName = "Blogger.getUsersBlogs")
    public UserBlog[] getUsersBlogs(String appkey, String username, String password) throws Exception;
}
