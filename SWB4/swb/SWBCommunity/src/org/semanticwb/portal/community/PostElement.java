package org.semanticwb.portal.community;

import java.util.Iterator;

public class PostElement extends org.semanticwb.portal.community.base.PostElementBase
{

    public PostElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public String getURL()
    {
        String url = "#";
        Iterator<Blog> blogs = Blog.listBlogByPostElement(this);
        if (blogs.hasNext())
        {
            url = blogs.next().getWebPage().getUrl();
        }
        return url;
    }
}
