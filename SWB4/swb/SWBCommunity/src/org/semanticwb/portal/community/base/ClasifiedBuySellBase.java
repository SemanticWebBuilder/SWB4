package org.semanticwb.portal.community.base;


public abstract class ClasifiedBuySellBase extends org.semanticwb.portal.community.Clasified implements org.semanticwb.model.Rankable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Searchable,org.semanticwb.portal.community.Contactable
{
       public static final org.semanticwb.platform.SemanticProperty swbcomm_Price=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/community#Price");
       public static final org.semanticwb.platform.SemanticClass swbcomm_ClasifiedBuySell=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedBuySell");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#ClasifiedBuySell");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySells(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySells()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell>(it, true);
       }

       public static org.semanticwb.portal.community.ClasifiedBuySell createClasifiedBuySell(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.ClasifiedBuySell.ClassMgr.createClasifiedBuySell(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.ClasifiedBuySell getClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.ClasifiedBuySell)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.ClasifiedBuySell createClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.ClasifiedBuySell)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasClasifiedBuySell(String id, org.semanticwb.model.SWBModel model)
       {
           return (getClasifiedBuySell(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource, directoryresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(directoryresource.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource,directoryresource.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage, hasdirprofilewebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(hasdirprofilewebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage,hasdirprofilewebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByWebPage(org.semanticwb.model.WebPage dirwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage, dirwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByWebPage(org.semanticwb.model.WebPage dirwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(dirwebpage.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage,dirwebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage, hasdirtopicwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(hasdirtopicwebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage,hasdirtopicwebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.ClasifiedBuySell> listClasifiedBuySellByComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.ClasifiedBuySell> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }
    }

    public ClasifiedBuySellBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public float getPrice()
    {
        return getSemanticObject().getFloatProperty(swbcomm_Price);
    }

    public void setPrice(float value)
    {
        getSemanticObject().setFloatProperty(swbcomm_Price, value);
    }
}
