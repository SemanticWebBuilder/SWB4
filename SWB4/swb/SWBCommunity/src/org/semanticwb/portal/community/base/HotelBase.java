package org.semanticwb.portal.community.base;


public abstract class HotelBase extends org.semanticwb.portal.community.Commerce implements org.semanticwb.portal.community.Claimable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Traceable,org.semanticwb.portal.community.FacilitiesEnable,org.semanticwb.model.Geolocalizable,org.semanticwb.model.Rankable,org.semanticwb.portal.community.Addressable,org.semanticwb.portal.community.Interactiveable,org.semanticwb.model.Tagable,org.semanticwb.model.Searchable,org.semanticwb.portal.community.Contactable
{
       public static final org.semanticwb.platform.SemanticClass swbcomm_Hotel=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Hotel");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/community#Hotel");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotels(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotels()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel>(it, true);
       }

       public static org.semanticwb.portal.community.Hotel createHotel(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.portal.community.Hotel.ClassMgr.createHotel(String.valueOf(id), model);
       }

       public static org.semanticwb.portal.community.Hotel getHotel(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Hotel)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.portal.community.Hotel createHotel(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.portal.community.Hotel)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeHotel(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasHotel(String id, org.semanticwb.model.SWBModel model)
       {
           return (getHotel(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource, directoryresource.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByDirectoryResource(org.semanticwb.portal.community.DirectoryResource directoryresource)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(directoryresource.getSemanticObject().getModel().listSubjects(swbcomm_directoryResource,directoryresource.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage, hasdirprofilewebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByProfile(org.semanticwb.model.WebPage hasdirprofilewebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(hasdirprofilewebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirProfileWebPage,hasdirprofilewebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByModifiedBy(org.semanticwb.model.User modifiedby,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_modifiedBy, modifiedby.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByModifiedBy(org.semanticwb.model.User modifiedby)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(modifiedby.getSemanticObject().getModel().listSubjects(swb_modifiedBy,modifiedby.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByWebPage(org.semanticwb.model.WebPage dirwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage, dirwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByWebPage(org.semanticwb.model.WebPage dirwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(dirwebpage.getSemanticObject().getModel().listSubjects(swbcomm_dirWebPage,dirwebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage, hasdirtopicwebpage.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByTopicWebPage(org.semanticwb.model.WebPage hasdirtopicwebpage)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(hasdirtopicwebpage.getSemanticObject().getModel().listSubjects(swbcomm_hasDirTopicWebPage,hasdirtopicwebpage.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByCreator(org.semanticwb.model.User creator,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swb_creator, creator.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByCreator(org.semanticwb.model.User creator)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(creator.getSemanticObject().getModel().listSubjects(swb_creator,creator.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByComment(org.semanticwb.portal.community.Comment hascomment,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_hasComment, hascomment.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByComment(org.semanticwb.portal.community.Comment hascomment)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(hascomment.getSemanticObject().getModel().listSubjects(swbcomm_hasComment,hascomment.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByClaimer(org.semanticwb.model.User claimer,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbcomm_claimer, claimer.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.portal.community.Hotel> listHotelByClaimer(org.semanticwb.model.User claimer)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.portal.community.Hotel> it=new org.semanticwb.model.GenericIterator(claimer.getSemanticObject().getModel().listSubjects(swbcomm_claimer,claimer.getSemanticObject()));
       return it;
   }
    }

    public HotelBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
