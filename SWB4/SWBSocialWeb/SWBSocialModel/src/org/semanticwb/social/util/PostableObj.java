/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semanticwb.social.util;

import javax.servlet.http.HttpServletRequest;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.social.Post;
import org.semanticwb.social.SocialNetPostable;

/**
 *
 * @author jorge.jimenez
 */

/*
 * Clase de tipo pojo que sirve en la clasificación de mensajes provenientes del listener
 */

public class PostableObj {

    SocialNetPostable postAble=null;
    Post post=null;
    //String action=null;

    public PostableObj(SocialNetPostable postAble,  Post post)
    {
        this.postAble=postAble;
        this.post=post;
        //this.action=action;
    }

    public SocialNetPostable getPostable()
    {
        return postAble;
    }

    public Post getPost()
    {
        return post;
    }

    /*
    public String getAction()
    {
        return action;
    }*/
    /*
    public HttpServletRequest getRequest()
    {
        return request;
    }

    public SWBActionResponse getResponse()
    {
        return response;
    }
    * */
}
