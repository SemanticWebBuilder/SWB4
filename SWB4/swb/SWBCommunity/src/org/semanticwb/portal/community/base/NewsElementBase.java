package org.semanticwb.portal.community.base;


public class NewsElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Viewable,org.semanticwb.model.Rankable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable
{
    public static class ClassMgr
    {
       public static final org.semanticwb.platform.SemanticProperty swbcomm_newsImage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsImage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_visibility=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#visibility");
       public static final org.semanticwb.platform.SemanticProperty swb_reviews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#reviews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_citation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#citation");
       public static final org.semanticwb.platform.SemanticProperty swb_rank=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#rank");
       public static final org.semanticwb.platform.SemanticProperty swb_tags=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#tags");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abused=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abused");
       public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
       public static final org.semanticwb.platform.SemanticProperty swb_maxViews=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#maxViews");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#author");
       public static final org.semanticwb.platform.SemanticProperty swb_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#created");
       public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
       public static final org.semanticwb.platform.SemanticProperty swb_modifiedBy=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#modifiedBy");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_newsThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsThumbnail");
       public static final org.semanticwb.platform.SemanticProperty swb_title=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#title");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_newsWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsWebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_newsplace=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#newsplace");
       public static final org.semanticwb.platform.SemanticProperty swb_updated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#updated");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_infoLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#infoLink");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_abstr=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#abstr");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_fullText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#fullText");
       public static final org.semanticwb.platform.SemanticProperty swb_views=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#views");
       public static final org.semanticwb.platform.SemanticProperty swb_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#creator");
       public static final org.semanticwb.platform.SemanticClass swbcomm_Comment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Comment");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_hasComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#hasComment");
       public static final org.semanticwb.platform.SemanticProperty swb_description=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#description");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_subtitle=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#subtitle");
       public static final org.semanticwb.platform.SemanticClass swbcomm_NewsElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#NewsElement");

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
           return org.semanticwb.portal.community.NewsElement.ClassMgr.createNewsElement(String.valueOf(id), model);
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
    }

    public NewsElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getNewsImage()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_newsImage);
    }

    public void setNewsImage(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_newsImage, value);
    }

    public String getCitation()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_citation);
    }

    public void setCitation(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_citation, value);
    }

    public String getAuthor()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_author);
    }

    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_author, value);
    }

    public String getNewsThumbnail()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_newsThumbnail);
    }

    public void setNewsThumbnail(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_newsThumbnail, value);
    }

    public void setNewsWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(ClassMgr.swbcomm_newsWebPage, value.getSemanticObject());
    }

    public void removeNewsWebPage()
    {
        getSemanticObject().removeProperty(ClassMgr.swbcomm_newsWebPage);
    }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_newsWebPage, newswebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.NewsElement> listNewsElementByNewsWebPage(org.semanticwb.model.WebPage newswebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.NewsElement> it=new org.semanticwb.model.GenericIterator(newswebpage.getSemanticObject().getModel().listSubjects(ClassMgr.swbcomm_newsWebPage,newswebpage.getSemanticObject()));
       return it;
   }

    public org.semanticwb.model.WebPage getNewsWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(ClassMgr.swbcomm_newsWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }

    public String getNewsplace()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_newsplace);
    }

    public void setNewsplace(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_newsplace, value);
    }

    public String getInfoLink()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_infoLink);
    }

    public void setInfoLink(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_infoLink, value);
    }

    public String getAbstr()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_abstr);
    }

    public void setAbstr(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_abstr, value);
    }

    public String getFullText()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_fullText);
    }

    public void setFullText(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_fullText, value);
    }

    public String getSubtitle()
    {
        return getSemanticObject().getProperty(ClassMgr.swbcomm_subtitle);
    }

    public void setSubtitle(String value)
    {
        getSemanticObject().setProperty(ClassMgr.swbcomm_subtitle, value);
    }
}
