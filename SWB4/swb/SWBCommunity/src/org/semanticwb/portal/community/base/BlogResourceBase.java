package org.semanticwb.portal.community.base;


public class BlogResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_Blog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Blog");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasBlog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasBlog");
    public static final org.semanticwb.platform.SemanticClass swbcomm_BlogResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#BlogResource");

    public BlogResourceBase()
    {
    }

    public BlogResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#BlogResource");

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Blog> listBlogs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Blog>(getSemanticObject().listObjectProperties(swbcomm_hasBlog));
    }

    public boolean hasBlog(org.semanticwb.portal.community.Blog blog)
    {
        if(blog==null)return false;
        return getSemanticObject().hasObjectProperty(swbcomm_hasBlog,blog.getSemanticObject());
    }

    public void addBlog(org.semanticwb.portal.community.Blog blog)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasBlog, blog.getSemanticObject());
    }

    public void removeAllBlog()
    {
        getSemanticObject().removeProperty(swbcomm_hasBlog);
    }

    public void removeBlog(org.semanticwb.portal.community.Blog blog)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasBlog,blog.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.BlogResource> listBlogResourceByHasBlog(org.semanticwb.portal.community.Blog hasblog,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.BlogResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasBlog, hasblog.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.BlogResource> listBlogResourceByHasBlog(org.semanticwb.portal.community.Blog hasblog)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.BlogResource> it=new org.semanticwb.model.GenericIterator(hasblog.getSemanticObject().getModel().listSubjects(swbcomm_hasBlog,hasblog.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Blog getBlog()
    {
         org.semanticwb.portal.community.Blog ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasBlog);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Blog)obj.createGenericInstance();
         }
         return ret;
    }
}
