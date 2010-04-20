package org.semanticwb.portal.resources.sem.blog;

import org.semanticwb.model.GenericIterator;


public class Post extends org.semanticwb.portal.resources.sem.blog.base.PostBase 
{
    public Post(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public int getNumberOfComments()
    {
        int i=0;
        GenericIterator<Comment> comments=listComments();
        while(comments.hasNext())
        {
            comments.next();
            i++;
        }
        return i;
    }
}
