/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.semantic.blogger.interfaces;

import java.util.Date;

/**
 *
 * @author victor.lorenzana
 */
public final class Post implements Comparable<Post>
{

    public int compareTo(Post o)
    {
        return o.dateCreated.compareTo(this.dateCreated);
    }
    public String title;
    public String link;
    public String description;
    public String[] categories;
    public Date dateCreated;
    public String postid;
    public String userid;
}
