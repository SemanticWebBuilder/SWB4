package org.semanticwb.portal.community.base;


public abstract class NewsResourceBase extends org.semanticwb.portal.community.CommunityResource 
{
    public static final org.semanticwb.platform.SemanticClass swbcomm_NewsElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_hasNews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasNews");
    public static final org.semanticwb.platform.SemanticClass swbcomm_NewsResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsResource");

    public NewsResourceBase()
    {
    }

    public NewsResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> listNewses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(getSemanticObject().listObjectProperties(swbcomm_hasNews));
    }

    public boolean hasNews(org.semanticwb.portal.community.NewsElement newselement)
    {
        boolean ret=false;
        if(newselement!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swbcomm_hasNews,newselement.getSemanticObject());
        }
        return ret;
    }

    public void addNews(org.semanticwb.portal.community.NewsElement value)
    {
        getSemanticObject().addObjectProperty(swbcomm_hasNews, value.getSemanticObject());
    }

    public void removeAllNews()
    {
        getSemanticObject().removeProperty(swbcomm_hasNews);
    }

    public void removeNews(org.semanticwb.portal.community.NewsElement newselement)
    {
        getSemanticObject().removeObjectProperty(swbcomm_hasNews,newselement.getSemanticObject());
    }

    public org.semanticwb.portal.community.NewsElement getNews()
    {
         org.semanticwb.portal.community.NewsElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_hasNews);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.community.NewsElement)obj.createGenericInstance();
         }
         return ret;
    }
}
