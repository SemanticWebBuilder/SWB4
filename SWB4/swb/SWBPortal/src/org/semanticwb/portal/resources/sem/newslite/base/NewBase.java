package org.semanticwb.portal.resources.sem.newslite.base;


public abstract class NewBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty news_created=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#created");
    public static final org.semanticwb.platform.SemanticProperty news_newsThumbnail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#newsThumbnail");
    public static final org.semanticwb.platform.SemanticProperty news_expiration=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#expiration");
    public static final org.semanticwb.platform.SemanticProperty news_image=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#image");
    public static final org.semanticwb.platform.SemanticProperty news_body=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#body");
    public static final org.semanticwb.platform.SemanticClass news_Category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#Category");
    public static final org.semanticwb.platform.SemanticProperty news_category=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#category");
    public static final org.semanticwb.platform.SemanticClass swb_User=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#User");
    public static final org.semanticwb.platform.SemanticProperty news_creator=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#creator");
    public static final org.semanticwb.platform.SemanticProperty news_source=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#source");
    public static final org.semanticwb.platform.SemanticProperty news_author=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/newslite#author");
    public static final org.semanticwb.platform.SemanticClass news_New=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#New");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/newslite#New");

    public static class ClassMgr
    {

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNews(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New>(it, true);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNews()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.newslite.New createNew(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.newslite.New.ClassMgr.createNew(String.valueOf(id), model);
        }

        public static org.semanticwb.portal.resources.sem.newslite.New getNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.New)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        public static org.semanticwb.portal.resources.sem.newslite.New createNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.newslite.New)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        public static void removeNew(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        public static boolean hasNew(String id, org.semanticwb.model.SWBModel model)
        {
            return (getNew(id, model)!=null);
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCategory(org.semanticwb.portal.resources.sem.newslite.Category value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(news_category, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCategory(org.semanticwb.portal.resources.sem.newslite.Category value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(news_category,value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(news_creator, value.getSemanticObject(),sclass));
            return it;
        }

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.newslite.New> listNewByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.newslite.New> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(news_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public NewBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(news_created);
    }

    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(news_created, value);
    }

    public String getNewsThumbnail()
    {
        return getSemanticObject().getProperty(news_newsThumbnail);
    }

    public void setNewsThumbnail(String value)
    {
        getSemanticObject().setProperty(news_newsThumbnail, value);
    }

    public java.util.Date getExpiration()
    {
        return getSemanticObject().getDateProperty(news_expiration);
    }

    public void setExpiration(java.util.Date value)
    {
        getSemanticObject().setDateProperty(news_expiration, value);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }

    public String getImage()
    {
        return getSemanticObject().getProperty(news_image);
    }

    public void setImage(String value)
    {
        getSemanticObject().setProperty(news_image, value);
    }

    public String getBody()
    {
        return getSemanticObject().getProperty(news_body);
    }

    public void setBody(String value)
    {
        getSemanticObject().setProperty(news_body, value);
    }

    public void setCategory(org.semanticwb.portal.resources.sem.newslite.Category value)
    {
        getSemanticObject().setObjectProperty(news_category, value.getSemanticObject());
    }

    public void removeCategory()
    {
        getSemanticObject().removeProperty(news_category);
    }

    public org.semanticwb.portal.resources.sem.newslite.Category getCategory()
    {
         org.semanticwb.portal.resources.sem.newslite.Category ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(news_category);
         if(obj!=null)
         {
             ret=(org.semanticwb.portal.resources.sem.newslite.Category)obj.createGenericInstance();
         }
         return ret;
    }

    public void setCreator(org.semanticwb.model.User value)
    {
        getSemanticObject().setObjectProperty(news_creator, value.getSemanticObject());
    }

    public void removeCreator()
    {
        getSemanticObject().removeProperty(news_creator);
    }

    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(news_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

    public String getSource()
    {
        return getSemanticObject().getProperty(news_source);
    }

    public void setSource(String value)
    {
        getSemanticObject().setProperty(news_source, value);
    }

    public String getAuthor()
    {
        return getSemanticObject().getProperty(news_author);
    }

    public void setAuthor(String value)
    {
        getSemanticObject().setProperty(news_author, value);
    }
}
