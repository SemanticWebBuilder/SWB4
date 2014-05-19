package org.semanticwb.social.base;


   /**
   * Clase que se encarga de administrar un calendario para un mensaje de Salida (PostOut). Un PostOut puede tener solo un calendario. 
   */
public abstract class FastCalendarBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Fecha
   */
    public static final org.semanticwb.platform.SemanticProperty social_fc_date=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#fc_date");
   /**
   * Clase que comprende todos los tipos de Post de Salida que pueden ir siendo creados en la herramienta y que seran publicados a partir de esto en las diferentes redes sociales. Esta clase no se relaciona con una red social (con la clase SocialNetwork) porque un post de salida (desde la herramienta) podría ser enviado a diferentes redes sociales, sin embargo, es el mismo post de salida. Donde esta a que red social se envía esta en las instancias de la clase PostContainer.
   */
    public static final org.semanticwb.platform.SemanticClass social_PostOut=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#PostOut");
    public static final org.semanticwb.platform.SemanticProperty social_PostOutFastCalendarInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#PostOutFastCalendarInv");
   /**
   * Clase que se encarga de administrar un calendario para un mensaje de Salida (PostOut). Un PostOut puede tener solo un calendario.
   */
    public static final org.semanticwb.platform.SemanticClass social_FastCalendar=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FastCalendar");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#FastCalendar");

    public static class ClassMgr
    {
       /**
       * Returns a list of FastCalendar for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.FastCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.FastCalendar> listFastCalendars(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FastCalendar>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.FastCalendar for all models
       * @return Iterator of org.semanticwb.social.FastCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.FastCalendar> listFastCalendars()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.FastCalendar>(it, true);
        }

        public static org.semanticwb.social.FastCalendar createFastCalendar(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.social.FastCalendar.ClassMgr.createFastCalendar(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.social.FastCalendar
       * @param id Identifier for org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.FastCalendar
       * @return A org.semanticwb.social.FastCalendar
       */
        public static org.semanticwb.social.FastCalendar getFastCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FastCalendar)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.FastCalendar
       * @param id Identifier for org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.FastCalendar
       * @return A org.semanticwb.social.FastCalendar
       */
        public static org.semanticwb.social.FastCalendar createFastCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.FastCalendar)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.FastCalendar
       * @param id Identifier for org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.FastCalendar
       */
        public static void removeFastCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.FastCalendar
       * @param id Identifier for org.semanticwb.social.FastCalendar
       * @param model Model of the org.semanticwb.social.FastCalendar
       * @return true if the org.semanticwb.social.FastCalendar exists, false otherwise
       */

        public static boolean hasFastCalendar(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFastCalendar(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.FastCalendar with a determined PostOutFastCalendarInv
       * @param value PostOutFastCalendarInv of the type org.semanticwb.social.PostOut
       * @param model Model of the org.semanticwb.social.FastCalendar
       * @return Iterator with all the org.semanticwb.social.FastCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.FastCalendar> listFastCalendarByPostOutFastCalendarInv(org.semanticwb.social.PostOut value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FastCalendar> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_PostOutFastCalendarInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.FastCalendar with a determined PostOutFastCalendarInv
       * @param value PostOutFastCalendarInv of the type org.semanticwb.social.PostOut
       * @return Iterator with all the org.semanticwb.social.FastCalendar
       */

        public static java.util.Iterator<org.semanticwb.social.FastCalendar> listFastCalendarByPostOutFastCalendarInv(org.semanticwb.social.PostOut value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.FastCalendar> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_PostOutFastCalendarInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FastCalendarBase.ClassMgr getFastCalendarClassMgr()
    {
        return new FastCalendarBase.ClassMgr();
    }

   /**
   * Constructs a FastCalendarBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FastCalendar
   */
    public FastCalendarBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Fc_date property
* @return java.util.Date with the Fc_date
*/
    public java.util.Date getFc_date()
    {
        return getSemanticObject().getDateProperty(social_fc_date);
    }

/**
* Sets the Fc_date property
* @param value long with the Fc_date
*/
    public void setFc_date(java.util.Date value)
    {
        getSemanticObject().setDateProperty(social_fc_date, value);
    }
   /**
   * Sets the value for the property PostOutFastCalendarInv
   * @param value PostOutFastCalendarInv to set
   */

    public void setPostOutFastCalendarInv(org.semanticwb.social.PostOut value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_PostOutFastCalendarInv, value.getSemanticObject());
        }else
        {
            removePostOutFastCalendarInv();
        }
    }
   /**
   * Remove the value for PostOutFastCalendarInv property
   */

    public void removePostOutFastCalendarInv()
    {
        getSemanticObject().removeProperty(social_PostOutFastCalendarInv);
    }

   /**
   * Gets the PostOutFastCalendarInv
   * @return a org.semanticwb.social.PostOut
   */
    public org.semanticwb.social.PostOut getPostOutFastCalendarInv()
    {
         org.semanticwb.social.PostOut ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_PostOutFastCalendarInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.PostOut)obj.createGenericInstance();
         }
         return ret;
    }
}
