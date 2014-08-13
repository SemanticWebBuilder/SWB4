package org.semanticwb.social.base;


public abstract class SocialCalendarRefBase extends org.semanticwb.model.Reference implements org.semanticwb.model.Activeable
{
   /**
   * Clase Calendario para SWBSocial
   */
    public static final org.semanticwb.platform.SemanticClass social_SocialCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendar");
    public static final org.semanticwb.platform.SemanticProperty social_socialCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#socialCalendar");
    public static final org.semanticwb.platform.SemanticClass social_SocialCalendarRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendarRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#SocialCalendarRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of SocialCalendarRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.SocialCalendarRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendarRef> listSocialCalendarRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendarRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.SocialCalendarRef for all models
       * @return Iterator of org.semanticwb.social.SocialCalendarRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendarRef> listSocialCalendarRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendarRef>(it, true);
        }

        public static org.semanticwb.social.SocialCalendarRef createSocialCalendarRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.SocialCalendarRef.ClassMgr.createSocialCalendarRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.SocialCalendarRef
       * @param id Identifier for org.semanticwb.social.SocialCalendarRef
       * @param model Model of the org.semanticwb.social.SocialCalendarRef
       * @return A org.semanticwb.social.SocialCalendarRef
       */
        public static org.semanticwb.social.SocialCalendarRef getSocialCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialCalendarRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.SocialCalendarRef
       * @param id Identifier for org.semanticwb.social.SocialCalendarRef
       * @param model Model of the org.semanticwb.social.SocialCalendarRef
       * @return A org.semanticwb.social.SocialCalendarRef
       */
        public static org.semanticwb.social.SocialCalendarRef createSocialCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.SocialCalendarRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.SocialCalendarRef
       * @param id Identifier for org.semanticwb.social.SocialCalendarRef
       * @param model Model of the org.semanticwb.social.SocialCalendarRef
       */
        public static void removeSocialCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.SocialCalendarRef
       * @param id Identifier for org.semanticwb.social.SocialCalendarRef
       * @param model Model of the org.semanticwb.social.SocialCalendarRef
       * @return true if the org.semanticwb.social.SocialCalendarRef exists, false otherwise
       */

        public static boolean hasSocialCalendarRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSocialCalendarRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendarRef with a determined SocialCalendar
       * @param value SocialCalendar of the type org.semanticwb.social.SocialCalendar
       * @param model Model of the org.semanticwb.social.SocialCalendarRef
       * @return Iterator with all the org.semanticwb.social.SocialCalendarRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendarRef> listSocialCalendarRefBySocialCalendar(org.semanticwb.social.SocialCalendar value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendarRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_socialCalendar, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.SocialCalendarRef with a determined SocialCalendar
       * @param value SocialCalendar of the type org.semanticwb.social.SocialCalendar
       * @return Iterator with all the org.semanticwb.social.SocialCalendarRef
       */

        public static java.util.Iterator<org.semanticwb.social.SocialCalendarRef> listSocialCalendarRefBySocialCalendar(org.semanticwb.social.SocialCalendar value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.SocialCalendarRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_socialCalendar,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SocialCalendarRefBase.ClassMgr getSocialCalendarRefClassMgr()
    {
        return new SocialCalendarRefBase.ClassMgr();
    }

   /**
   * Constructs a SocialCalendarRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SocialCalendarRef
   */
    public SocialCalendarRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SocialCalendar
   * @param value SocialCalendar to set
   */

    public void setSocialCalendar(org.semanticwb.social.SocialCalendar value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_socialCalendar, value.getSemanticObject());
        }else
        {
            removeSocialCalendar();
        }
    }
   /**
   * Remove the value for SocialCalendar property
   */

    public void removeSocialCalendar()
    {
        getSemanticObject().removeProperty(social_socialCalendar);
    }

   /**
   * Gets the SocialCalendar
   * @return a org.semanticwb.social.SocialCalendar
   */
    public org.semanticwb.social.SocialCalendar getSocialCalendar()
    {
         org.semanticwb.social.SocialCalendar ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_socialCalendar);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.SocialCalendar)obj.createGenericInstance();
         }
         return ret;
    }
}
