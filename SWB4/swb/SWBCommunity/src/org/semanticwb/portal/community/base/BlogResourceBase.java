package org.semanticwb.portal.community.base;


public class BlogResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swbcomm_Blog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Blog");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasBlog=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasBlog");
       public static final org.semanticwb.platform.SemanticClass swbcomm_BlogResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#BlogResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#BlogResource");
    }

    public BlogResourceBase()
    {
    }

    public BlogResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Blog> listBlogs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Blog>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasBlog));
    }

    public boolean hasBlog(org.semanticwb.portal.community.Blog blog)
    {
        if(blog==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasBlog,blog.getSemanticObject());
    }

    public void addBlog(org.semanticwb.portal.community.Blog value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasBlog, value.getSemanticObject());
    }

    public void removeAllBlog()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasBlog);
    }

    public void removeBlog(org.semanticwb.portal.community.Blog blog)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasBlog,blog.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.BlogResource> listBlogResourceByBlog(org.semanticwb.portal.community.Blog hasblog,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.BlogResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasBlog, hasblog.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.BlogResource> listBlogResourceByBlog(org.semanticwb.portal.community.Blog hasblog)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.BlogResource> it=new org.semanticwb.model.GenericIterator(hasblog.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasBlog,hasblog.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.Blog getBlog()
    {
         org.semanticwb.portal.community.Blog ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasBlog);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.Blog)obj.createGenericInstance();
         }
         return ret;
    }
}
