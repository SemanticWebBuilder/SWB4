package org.semanticwb.portal.community.base;


public class NewsElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Viewable,org.semanticwb.model.Rankable
{
    public static final org.semanticwb.platform.SemanticProperty swbcomm_citation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#citation");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsPicture=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsPicture");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#author");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_newsWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsWebPage");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_abstr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abstr");
    public static final org.semanticwb.platform.SemanticProperty swbcomm_fullText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#fullText");
    public static final org.semanticwb.platform.SemanticClass swbcomm_NewsElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");

    public NewsElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElements(org.semanticwb.model.SWBModel model)
    {
        java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(it, true);
    }

    public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElements()
    {
        java.util.Iterator it=sclass.listInstances();
        return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement>(it, true);
    }

    public static org.semanticwb.portal.community.NewsElement createNewsElement(org.semanticwb.model.SWBModel model)
    {
        long id=model.getSemanticObject().getModel().getCounter(sclass);
        return org.semanticwb.portal.community.NewsElement.createNewsElement(String.valueOf(id), model);
    }

    public static org.semanticwb.portal.community.NewsElement getNewsElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.NewsElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
    }

    public static org.semanticwb.portal.community.NewsElement createNewsElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (org.semanticwb.portal.community.NewsElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
    }

    public static void removeNewsElement(String id, org.semanticwb.model.SWBModel model)
    {
        model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
    }

    public static boolean hasNewsElement(String id, org.semanticwb.model.SWBModel model)
    {
        return (getNewsElement(id, model)!=null);
    }

    public String getCitation()
    {
        return getSemanticObject().getProperty(swbcomm_citation);
    }

    public void setCitation(String value)
    {
        getSemanticObject().setProperty(swbcomm_citation, value);
    }

    public String getNewsPicture()
    {
        return getSemanticObject().getProperty(swbcomm_newsPicture);
    }

    public void setNewsPicture(String value)
    {
        getSemanticObject().setProperty(swbcomm_newsPicture, value);
    }

    public String getAuthor()
    {
        return getSemanticObject().getProperty(swbcomm_author);
    }

    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(swbcomm_author, value);
    }

    public void setNewsWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_newsWebPage, value.getSemanticObject());
    }

    public void removeNewsWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_newsWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_newsWebPage, newswebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(newswebpage.getSemanticObject().getModel().listSubjects(swbcomm_newsWebPage,newswebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getNewsWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_newsWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getAbstr()
    {
        return getSemanticObject().getProperty(swbcomm_abstr);
    }

    public void setAbstr(String value)
    {
        getSemanticObject().setProperty(swbcomm_abstr, value);
    }

    public String getFullText()
    {
        return getSemanticObject().getProperty(swbcomm_fullText);
    }

    public void setFullText(String value)
    {
        getSemanticObject().setProperty(swbcomm_fullText, value);
    }
}
