/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.opensocial.model.data.base;


public abstract class AddressBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.opensocial.model.Formatteable,org.semanticwb.model.Geolocalizable
{
    public static final org.semanticwb.platform.SemanticProperty data_region=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#region");
    public static final org.semanticwb.platform.SemanticProperty data_postalCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#postalCode");
    public static final org.semanticwb.platform.SemanticProperty data_locality=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#locality");
    public static final org.semanticwb.platform.SemanticProperty data_type=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#type");
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#country");
    public static final org.semanticwb.platform.SemanticProperty data_streetAddress=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#streetAddress");
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
* Gets the Region property
* @return String with the Region
*/
    public String getRegion()
    {
        return getSemanticObject().getProperty(data_region);
    }

/**
* Sets the Region property
* @param value long with the Region
*/
    public void setRegion(String value)
    {
        getSemanticObject().setProperty(data_region, value);
    }

/**
* Gets the PostalCode property
* @return String with the PostalCode
*/
    public String getPostalCode()
    {
        return getSemanticObject().getProperty(data_postalCode);
    }

/**
* Sets the PostalCode property
* @param value long with the PostalCode
*/
    public void setPostalCode(String value)
    {
        getSemanticObject().setProperty(data_postalCode, value);
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
* Gets the Locality property
* @return String with the Locality
*/
    public String getLocality()
    {
        return getSemanticObject().getProperty(data_locality);
    }

/**
* Sets the Locality property
* @param value long with the Locality
*/
    public void setLocality(String value)
    {
        getSemanticObject().setProperty(data_locality, value);
    }

/**
* Gets the Type property
* @return String with the Type
*/
    public String getType()
    {
        return getSemanticObject().getProperty(data_type);
    }

/**
* Sets the Type property
* @param value long with the Type
*/
    public void setType(String value)
    {
        getSemanticObject().setProperty(data_type, value);
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
* Gets the StreetAddress property
* @return String with the StreetAddress
*/
    public String getStreetAddress()
    {
        return getSemanticObject().getProperty(data_streetAddress);
    }

/**
* Sets the StreetAddress property
* @param value long with the StreetAddress
*/
    public void setStreetAddress(String value)
    {
        getSemanticObject().setProperty(data_streetAddress, value);
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
