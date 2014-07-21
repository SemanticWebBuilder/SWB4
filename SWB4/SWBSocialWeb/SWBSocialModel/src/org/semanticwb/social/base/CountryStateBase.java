package org.semanticwb.social.base;


public abstract class CountryStateBase extends org.semanticwb.social.GeoCatalog implements org.semanticwb.social.GeoMapable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass social_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Country");
    public static final org.semanticwb.platform.SemanticProperty social_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#country");
   /**
   * Clase a Cambiar despues por "Relacional", esta y todas sus hijas. Clase que comprende todos los tipos de Post que pueden ir siendo creados en la herramienta..
   */
    public static final org.semanticwb.platform.SemanticClass social_Post=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#Post");
   /**
   * Obtiene todos los postIn de un estado.
   */
    public static final org.semanticwb.platform.SemanticProperty social_hasCountryStatePostInInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/social#hasCountryStatePostInInv");
    public static final org.semanticwb.platform.SemanticClass social_CountryState=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#CountryState");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/social#CountryState");

    public static class ClassMgr
    {
       /**
       * Returns a list of CountryState for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.social.CountryState for all models
       * @return Iterator of org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState>(it, true);
        }
       /**
       * Gets a org.semanticwb.social.CountryState
       * @param id Identifier for org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.CountryState
       * @return A org.semanticwb.social.CountryState
       */
        public static org.semanticwb.social.CountryState getCountryState(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.CountryState)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.social.CountryState
       * @param id Identifier for org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.CountryState
       * @return A org.semanticwb.social.CountryState
       */
        public static org.semanticwb.social.CountryState createCountryState(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.social.CountryState)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.social.CountryState
       * @param id Identifier for org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.CountryState
       */
        public static void removeCountryState(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.social.CountryState
       * @param id Identifier for org.semanticwb.social.CountryState
       * @param model Model of the org.semanticwb.social.CountryState
       * @return true if the org.semanticwb.social.CountryState exists, false otherwise
       */

        public static boolean hasCountryState(String id, org.semanticwb.model.SWBModel model)
        {
            return (getCountryState(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.social.CountryState with a determined Country
       * @param value Country of the type org.semanticwb.social.Country
       * @param model Model of the org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStateByCountry(org.semanticwb.social.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.CountryState with a determined Country
       * @param value Country of the type org.semanticwb.social.Country
       * @return Iterator with all the org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStateByCountry(org.semanticwb.social.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_country,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.CountryState with a determined CountryStatePostInInv
       * @param value CountryStatePostInInv of the type org.semanticwb.social.Post
       * @param model Model of the org.semanticwb.social.CountryState
       * @return Iterator with all the org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStateByCountryStatePostInInv(org.semanticwb.social.Post value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(social_hasCountryStatePostInInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.social.CountryState with a determined CountryStatePostInInv
       * @param value CountryStatePostInInv of the type org.semanticwb.social.Post
       * @return Iterator with all the org.semanticwb.social.CountryState
       */

        public static java.util.Iterator<org.semanticwb.social.CountryState> listCountryStateByCountryStatePostInInv(org.semanticwb.social.Post value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.social.CountryState> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(social_hasCountryStatePostInInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static CountryStateBase.ClassMgr getCountryStateClassMgr()
    {
        return new CountryStateBase.ClassMgr();
    }

   /**
   * Constructs a CountryStateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the CountryState
   */
    public CountryStateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the CapitalLatitude property
* @return float with the CapitalLatitude
*/
    public float getCapitalLatitude()
    {
        return getSemanticObject().getFloatProperty(social_capitalLatitude);
    }

/**
* Sets the CapitalLatitude property
* @param value long with the CapitalLatitude
*/
    public void setCapitalLatitude(float value)
    {
        getSemanticObject().setFloatProperty(social_capitalLatitude, value);
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
   * Sets the value for the property Country
   * @param value Country to set
   */

    public void setCountry(org.semanticwb.social.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(social_country, value.getSemanticObject());
        }else
        {
            removeCountry();
        }
    }
   /**
   * Remove the value for Country property
   */

    public void removeCountry()
    {
        getSemanticObject().removeProperty(social_country);
    }

   /**
   * Gets the Country
   * @return a org.semanticwb.social.Country
   */
    public org.semanticwb.social.Country getCountry()
    {
         org.semanticwb.social.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Country)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.social.Post
   * @return A GenericIterator with all the org.semanticwb.social.Post
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.social.Post> listCountryStatePostInInvs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.social.Post>(getSemanticObject().listObjectProperties(social_hasCountryStatePostInInv));
    }

   /**
   * Gets true if has a CountryStatePostInInv
   * @param value org.semanticwb.social.Post to verify
   * @return true if the org.semanticwb.social.Post exists, false otherwise
   */
    public boolean hasCountryStatePostInInv(org.semanticwb.social.Post value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(social_hasCountryStatePostInInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the CountryStatePostInInv
   * @return a org.semanticwb.social.Post
   */
    public org.semanticwb.social.Post getCountryStatePostInInv()
    {
         org.semanticwb.social.Post ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(social_hasCountryStatePostInInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.social.Post)obj.createGenericInstance();
         }
         return ret;
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
* Gets the CapitalLongitude property
* @return float with the CapitalLongitude
*/
    public float getCapitalLongitude()
    {
        return getSemanticObject().getFloatProperty(social_capitalLongitude);
    }

/**
* Sets the CapitalLongitude property
* @param value long with the CapitalLongitude
*/
    public void setCapitalLongitude(float value)
    {
        getSemanticObject().setFloatProperty(social_capitalLongitude, value);
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
