package org.semanticwb.portal.community;

import java.net.URLEncoder;
import java.util.Iterator;
import org.semanticwb.model.WebPage;

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
        url+="?act=detail&uri="+URLEncoder.encode(this.getURI());
        return url;
    }
    @Override
    public WebPage getWebPage()
    {
        return this.getWebPage();
    }
}
