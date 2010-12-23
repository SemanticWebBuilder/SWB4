package org.semanticwb.opensocial.model.data.base;


public abstract class AddressBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.opensocial.model.Formatteable,org.semanticwb.model.Geolocalizable
{
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#country");
    public static final org.semanticwb.platform.SemanticClass data_Address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Address");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Address");

    public static class ClassMgr
    {
       /**
       * Returns a list of Address for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.Address
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Address> listAddresses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Address>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.Address for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.Address
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Address> listAddresses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Address>(it, true);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.Address
       * @param id Identifier for org.semanticwb.opensocial.model.data.Address
       * @param model Model of the org.semanticwb.opensocial.model.data.Address
       * @return A org.semanticwb.opensocial.model.data.Address
       */
        public static org.semanticwb.opensocial.model.data.Address getAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Address)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.Address
       * @param id Identifier for org.semanticwb.opensocial.model.data.Address
       * @param model Model of the org.semanticwb.opensocial.model.data.Address
       * @return A org.semanticwb.opensocial.model.data.Address
       */
        public static org.semanticwb.opensocial.model.data.Address createAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Address)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.Address
       * @param id Identifier for org.semanticwb.opensocial.model.data.Address
       * @param model Model of the org.semanticwb.opensocial.model.data.Address
       */
        public static void removeAddress(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.Address
       * @param id Identifier for org.semanticwb.opensocial.model.data.Address
       * @param model Model of the org.semanticwb.opensocial.model.data.Address
       * @return true if the org.semanticwb.opensocial.model.data.Address exists, false otherwise
       */

        public static boolean hasAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAddress(id, model)!=null);
        }
    }

   /**
   * Constructs a AddressBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Address
   */
    public AddressBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Longitude property
* @return double with the Longitude
*/
    public double getLongitude()
    {
        return getSemanticObject().getDoubleProperty(swb_longitude);
    }

/**
* Sets the Longitude property
* @param value long with the Longitude
*/
    public void setLongitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_longitude, value);
    }

/**
* Gets the Step property
* @return int with the Step
*/
    public int getStep()
    {
        return getSemanticObject().getIntProperty(swb_geoStep);
    }

/**
* Sets the Step property
* @param value long with the Step
*/
    public void setStep(int value)
    {
        getSemanticObject().setIntProperty(swb_geoStep, value);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in Address object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in Address object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
    }

/**
* Gets the Country property
* @return String with the Country
*/
    public String getCountry()
    {
        return getSemanticObject().getProperty(data_country);
    }

/**
* Sets the Country property
* @param value long with the Country
*/
    public void setCountry(String value)
    {
        getSemanticObject().setProperty(data_country, value);
    }

/**
* Gets the Formatted property
* @return String with the Formatted
*/
    public String getFormatted()
    {
        return getSemanticObject().getProperty(data_formatted);
    }

/**
* Sets the Formatted property
* @param value long with the Formatted
*/
    public void setFormatted(String value)
    {
        getSemanticObject().setProperty(data_formatted, value);
    }

/**
* Gets the Latitude property
* @return double with the Latitude
*/
    public double getLatitude()
    {
        return getSemanticObject().getDoubleProperty(swb_latitude);
    }

/**
* Sets the Latitude property
* @param value long with the Latitude
*/
    public void setLatitude(double value)
    {
        getSemanticObject().setDoubleProperty(swb_latitude, value);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()
    {
        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);
    }
}
