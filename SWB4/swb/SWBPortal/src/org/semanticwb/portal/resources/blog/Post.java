/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.portal.resources.blog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.semanticwb.model.UserRepository;

/**
 *
 * @author victor.lorenzana
 */
public class Post
{

    private Logger log=SWBUtils.getLogger(Post.class);
    private String title;
    private String description;
    private Timestamp fec_alta;
    public long postID;
    public long blogID;
    public String user;
    public String email;
    private int comments = 0;

    public Post(long postID, long blogID, UserRepository userRep)
    {
        this.postID = postID;
        this.blogID = blogID;
        
        Connection con = SWBUtils.DB.getDefaultConnection();
        if ( con == null )
        {
            throw new IllegalArgumentException("No se puede conectar a la base de datos con pool denominado " + SWBUtils.DB.getDatabaseName());
        }
        try
        {
            PreparedStatement pt = con.prepareStatement("select * from wbblogpost where blogid=" + blogID + " and postId=" + postID);
            ResultSet rs = pt.executeQuery();
            if ( rs.next() )
            {
                title = rs.getString("title");
                description = rs.getString("description").replaceAll("\r\n", "</br>");
                fec_alta = rs.getTimestamp("fec_alta");
                String userid = rs.getString("userid");
                String uid = userid.substring(0,userid.indexOf("_"));
                String repid = userid.substring(userid.indexOf("_")+1);
                User recuser =UserRepository.getUserRepository(repid).getUser(uid);
                System.out.println("userrep -> "+recuser);
                StringBuffer name = new StringBuffer("");
                if ( recuser == null )
                {
                    name.append("Anonimo");
                }
                else
                {
                    name.append(recuser.getFullName());
                }
                this.user = name.toString().trim();
            }
            rs.close();
            pt.close();

            pt = con.prepareStatement("select count(commentid) as total from wbblogcomments where blogid=" + blogID + " and postid=" + this.postID);
            rs = pt.executeQuery();
            while (rs.next())
            {
                this.comments = rs.getInt("total");
            }
            rs.close();
            pt.close();
        }
        catch ( SQLException sqle )
        {
            throw new IllegalArgumentException(sqle.getMessage());
        }
        finally
        {
            try
            {
                con.close();
            }
            catch ( SQLException sqle )
            {
                log.error(sqle);
            }
        }
    }

    public int getNumberOfComments()
    {
        return comments;
    }

    public String getUser()
    {
        return user;
    }

    public String getEmail()
    {
        return email;
    }

    public Timestamp getDate()
    {
        return fec_alta;
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public long getPostID()
    {
        return postID;
    }

    public long getBlogID()
    {
        return blogID;
    }
}
