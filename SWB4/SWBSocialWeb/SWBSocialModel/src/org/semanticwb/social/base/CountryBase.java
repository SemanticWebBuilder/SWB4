package org.semanticwb.social.base;


public abstract class CountryBase extends org.semanticwb.social.GeoCatalog implements org.semanticwb.social.GeoMapable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass social_CountryState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#CountryState");
    public static final org.semanticwb.platform.SemanticProperty social_hasStatesInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasStatesInv");
    public static final org.semanticwb.platform.SemanticClass social_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Country");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Country");

    public static class ClassMgr
    {
       /**
       * Returns a list of Country for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.Country
       */

        public static java.util.Iterator<org.semanticwb.social.Country> listCountries(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Country>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.Country for all models
       * @return Iterator of org.semanticwb.social.Country
       */

        public static java.util.Iterator<org.semanticwb.social.Country> listCountries()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Country>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.Country
       * @param id Identifier for org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.Country
       * @return A org.semanticwb.social.Country
       */
        public static org.semanticwb.social.Country getCountry(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Country)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.Country
       * @param id Identifier for org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.Country
       * @return A org.semanticwb.social.Country
       */
        public static org.semanticwb.social.Country createCountry(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.Country)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.Country
       * @param id Identifier for org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.Country
       */
        public static void removeCountry(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.Country
       * @param id Identifier for org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.Country
       * @return true if the org.semanticwb.social.Country exists, false otherwise
       */

        public static boolean hasCountry(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCountry(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.Country with a determined StatesInv
       * @param value StatesInv of the type org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.Country
       * @return Iterator with all the org.semanticwb.social.Country
       */

        public static java.util.Iterator<org.semanticwb.social.Country> listCountryByStatesInv(org.semanticwb.social.CountryState value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Country> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasStatesInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.Country with a determined StatesInv
       * @param value StatesInv of the type org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.Country
       */

        public static java.util.Iterator<org.semanticwb.social.Country> listCountryByStatesInv(org.semanticwb.social.CountryState value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.Country> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasStatesInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CountryBase.ClassMgr getCountryClassMgr()
    {
        return new CountryBase.ClassMgr();
    }

   /**
   * Constructs a CountryBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Country
   */
    public CountryBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
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

/**
* Gets the West property
* @return float with the West
*/
    public float getWest()
    {
        return getSemanticObject().getFloatProperty(social_west);
    }

/**
* Sets the West property
* @param value long with the West
*/
    public void setWest(float value)
    {
        getSemanticObject().setFloatProperty(social_west, value);
    }

/**
* Gets the North property
* @return float with the North
*/
    public float getNorth()
    {
        return getSemanticObject().getFloatProperty(social_north);
    }

/**
* Sets the North property
* @param value long with the North
*/
    public void setNorth(float value)
    {
        getSemanticObject().setFloatProperty(social_north, value);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
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

/**
* Gets the East property
* @return float with the East
*/
    public float getEast()
    {
        return getSemanticObject().getFloatProperty(social_east);
    }

/**
* Sets the East property
* @param value long with the East
*/
    public void setEast(float value)
    {
        getSemanticObject().setFloatProperty(social_east, value);
    }
   /**
   * Gets all the org.semanticwb.social.CountryState
   * @return A GenericIterator with all the org.semanticwb.social.CountryState
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState> listStatesInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState>(getSemanticObject().listObjectProperties(social_hasStatesInv));
    }

   /**
   * Gets true if has a StatesInv
   * @param value org.semanticwb.social.CountryState to verify
   * @return true if the org.semanticwb.social.CountryState exists, false otherwise
   */
    public boolean hasStatesInv(org.semanticwb.social.CountryState value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasStatesInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the StatesInv
   * @return a org.semanticwb.social.CountryState
   */
    public org.semanticwb.social.CountryState getStatesInv()
    {
         org.semanticwb.social.CountryState ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasStatesInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.CountryState)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the South property
* @return float with the South
*/
    public float getSouth()
    {
        return getSemanticObject().getFloatProperty(social_south);
    }

/**
* Sets the South property
* @param value long with the South
*/
    public void setSouth(float value)
    {
        getSemanticObject().setFloatProperty(social_south, value);
    }
}
