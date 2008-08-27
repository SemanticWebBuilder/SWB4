/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semantic.blogger.interfaces;

/**
 *
 * @author victor.lorenzana
 */
public final class UserBlog {
    public String url;
    public boolean isAdmin;
    public String blogid;    
    public String blogName;
    @Override
    public String toString()
    {
        return blogid;
    }
    @Override
    public int hashCode()
    {
        return this.blogid.hashCode();
    }
    @Override
    public boolean equals(Object obj)
    {
        boolean equals=false;
        if(obj instanceof UserBlog)
        {
            equals =((UserBlog)obj).blogid.equals(this.blogid);
        }
        return equals;
    }
}
