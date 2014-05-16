package org.semanticwb.social.base;


   /**
   * Clase Calendario para SWBSocial 
   */
public abstract class SocialCalendarBase extends org.semanticwb.model.Calendar implements org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Filterable,org.semanticwb.model.FilterableClass,org.semanticwb.model.Descriptiveable,org.semanticwb.model.XMLable,org.semanticwb.model.FilterableNode,org.semanticwb.social.Relationable
{
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
   /**
   * PostOut's que han sido publicados por el calendario. Cuando en esta lista este un calendario, quiere decir que se publicó el postOut porque el mensaje tenia referenciado el Calendario y porque este estaba onSchedule (isOnSchedule=true). Cuando isOnSchedule=false, es decir, cuando haya un momento en el que el calendario no este en su momento, se elimina de esta lista el postOut que se había agregado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasPostOut_published=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasPostOut_published");
   /**
   * Clase Calendario para SWBSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendar");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendar");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialCalendar for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendars(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialCalendar for all models
       * @return Iterator of org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendars()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar>(it, true);
        }

        public static org.semanticwb.social.SocialCalendar createSocialCalendar(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialCalendar.ClassMgr.createSocialCalendar(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialCalendar
       * @param id Identifier for org.semanticwb.social.SocialCalendar
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return A org.semanticwb.social.SocialCalendar
       */
        public static org.semanticwb.social.SocialCalendar getSocialCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialCalendar)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialCalendar
       * @param id Identifier for org.semanticwb.social.SocialCalendar
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return A org.semanticwb.social.SocialCalendar
       */
        public static org.semanticwb.social.SocialCalendar createSocialCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialCalendar)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialCalendar
       * @param id Identifier for org.semanticwb.social.SocialCalendar
       * @param model Model of the org.semanticwb.social.SocialCalendar
       */
        public static void removeSocialCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialCalendar
       * @param id Identifier for org.semanticwb.social.SocialCalendar
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return true if the org.semanticwb.social.SocialCalendar exists, false otherwise
       */

        public static boolean hasSocialCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialCalendar(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined CalendarRefInv
       * @param value CalendarRefInv of the type org.semanticwb.model.CalendarRef
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByCalendarRefInv(org.semanticwb.model.CalendarRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRefInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined CalendarRefInv
       * @param value CalendarRefInv of the type org.semanticwb.model.CalendarRef
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByCalendarRefInv(org.semanticwb.model.CalendarRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_hasCalendarRefInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined PostOut_published
       * @param value PostOut_published of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.SocialCalendar
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByPostOut_published(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOut_published, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendar with a determined PostOut_published
       * @param value PostOut_published of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.SocialCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendar> listSocialCalendarByPostOut_published(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasPostOut_published,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialCalendarBase.ClassMgr getSocialCalendarClassMgr()
    {
        return new SocialCalendarBase.ClassMgr();
    }

   /**
   * Constructs a SocialCalendarBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialCalendar
   */
    public SocialCalendarBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.social.PostOut
   * @return A GenericIterator with all the org.semanticwb.social.PostOut
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut> listPostOut_publisheds()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.PostOut>(getSemanticObject().listObjectProperties(social_hasPostOut_published));
    }

   /**
   * Gets true if has a PostOut_published
   * @param value org.semanticwb.social.PostOut to verify
   * @return true if the org.semanticwb.social.PostOut exists, false otherwise
   */
    public boolean hasPostOut_published(org.semanticwb.social.PostOut value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasPostOut_published,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a PostOut_published
   * @param value org.semanticwb.social.PostOut to add
   */

    public void addPostOut_published(org.semanticwb.social.PostOut value)
    {
        getSemanticObject().addObjectProperty(social_hasPostOut_published, value.getSemanticObject());
    }
   /**
   * Removes all the PostOut_published
   */

    public void removeAllPostOut_published()
    {
        getSemanticObject().removeProperty(social_hasPostOut_published);
    }
   /**
   * Removes a PostOut_published
   * @param value org.semanticwb.social.PostOut to remove
   */

    public void removePostOut_published(org.semanticwb.social.PostOut value)
    {
        getSemanticObject().removeObjectProperty(social_hasPostOut_published,value.getSemanticObject());
    }

   /**
   * Gets the PostOut_published
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPostOut_published()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasPostOut_published);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
}
