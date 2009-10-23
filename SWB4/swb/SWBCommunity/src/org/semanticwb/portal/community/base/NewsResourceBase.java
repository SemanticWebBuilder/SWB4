package org.semanticwb.portal.community.base;


public class NewsResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticClass swbcomm_NewsElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasNews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasNews");
       public static final org.semanticwb.platform.SemanticClass swbcomm_NewsResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsResource");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsResource");
    }

    public NewsResourceBase()
    {
    }

    public NewsResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> listNewses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(getSemanticObject().listObjectProperties(ClassMgr.swbcomm_hasNews));
    }

    public boolean hasNews(org.semanticwb.portal.community.NewsElement newselement)
    {
        if(newselement==null)return false;
        return getSemanticObject().hasObjectProperty(ClassMgr.swbcomm_hasNews,newselement.getSemanticObject());
    }

    public void addNews(org.semanticwb.portal.community.NewsElement value)
    {
        getSemanticObject().addObjectProperty(ClassMgr.swbcomm_hasNews, value.getSemanticObject());
    }

    public void removeAllNews()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_hasNews);
    }

    public void removeNews(org.semanticwb.portal.community.NewsElement newselement)
    {
        getSemanticObject().removeObjectProperty(ClassMgr.swbcomm_hasNews,newselement.getSemanticObject());
    }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsResource> listNewsResourceByNews(org.semanticwb.portal.community.NewsElement hasnews,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsResource> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasNews, hasnews.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsResource> listNewsResourceByNews(org.semanticwb.portal.community.NewsElement hasnews)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsResource> it=new org.semanticwb.model.GenericIterator(hasnews.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_hasNews,hasnews.getSemanticObject()));
       return it;
   }

    public org.semanticwb.portal.community.NewsElement getNews()
    {
         org.semanticwb.portal.community.NewsElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_hasNews);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.NewsElement)obj.createGenericInstance();
         }
         return ret;
    }
}
