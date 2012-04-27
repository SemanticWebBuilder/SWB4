/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.social.Post;
import org.semanticwb.social.Postable;

/**
 *
 * @author jorge.jimenez
 */
public class PostableObj {

    Postable postAble=null;
    HttpServletRequest request=null;
    SWBActionResponse response=null;
    Post post=null;

    public PostableObj(Postable postAble,  Post post, HttpServletRequest request, SWBActionResponse response)
    {
        this.postAble=postAble;
        this.request=request;
        this.response=response;
        this.post=post;
    }

    public Postable getPostable()
    {
        return postAble;
    }

    public Post getPost()
    {
        return post;
    }

    public HttpServletRequest getRequest()
    {
        return request;
    }

    public SWBActionResponse getResponse()
    {
        return response;
    }

}
