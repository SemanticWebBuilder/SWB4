package org.semanticwb.portal.community.base;


public abstract class DocumentElementBase extends org.semanticwb.portal.community.MicroSiteElement implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Viewable,org.semanticwb.model.Tagable,org.semanticwb.model.Searchable
{
       public static final org.semanticwb.platform.SemanticProperty swbcomm_documentURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#documentURL");
       public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
       public static final org.semanticwb.platform.SemanticProperty swbcomm_documentWebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#documentWebPage");
       public static final org.semanticwb.platform.SemanticClass swbcomm_DocumentElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentElement");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#DocumentElement");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElements(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElements()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement>(it, true);
       }

       public static org.semanticwb.portal.community.DocumentElement createDocumentElement(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.DocumentElement.ClassMgr.createDocumentElement(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.DocumentElement getDocumentElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.DocumentElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.DocumentElement createDocumentElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.DocumentElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeDocumentElement(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasDocumentElement(String id, org.semanticwb.model.SWBModel model)
       {
           return (getDocumentElement(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByDocumentWebPage(org.semanticwb.model.WebPage documentwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_documentWebPage, documentwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByDocumentWebPage(org.semanticwb.model.WebPage documentwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(documentwebpage.getSemanticObject().getModel().listSubjects(swbcomm_documentWebPage,documentwebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.DocumentElement> listDocumentElementByComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.DocumentElement> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }
    }

    public DocumentElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public String getDocumentURL()
    {
        return getSemanticObject().getProperty(swbcomm_documentURL);
    }

    public void setDocumentURL(String value)
    {
        getSemanticObject().setProperty(swbcomm_documentURL, value);
    }

    public void setDocumentWebPage(org.semanticwb.model.WebPage value)
    {
        getSemanticObject().setObjectProperty(swbcomm_documentWebPage, value.getSemanticObject());
    }

    public void removeDocumentWebPage()
    {
        getSemanticObject().removeProperty(swbcomm_documentWebPage);
    }


    public org.semanticwb.model.WebPage getDocumentWebPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbcomm_documentWebPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
