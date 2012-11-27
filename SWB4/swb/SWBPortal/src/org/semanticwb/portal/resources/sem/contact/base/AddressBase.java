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
package org.semanticwb.portal.resources.sem.contact.base;


   /**
   * Dirección 
   */
public abstract class AddressBase extends org.semanticwb.model.SWBClass 
{
   /**
   * ¿Esta dirección, es para facturar?
   */
    public static final org.semanticwb.platform.SemanticProperty contact_isToCheck=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#isToCheck");
   /**
   * Calle y número
   */
    public static final org.semanticwb.platform.SemanticProperty contact_street=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#street");
   /**
   * State
   */
    public static final org.semanticwb.platform.SemanticProperty contact_state=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#state");
   /**
   * City
   */
    public static final org.semanticwb.platform.SemanticProperty contact_city=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#city");
   /**
   * County
   */
    public static final org.semanticwb.platform.SemanticProperty contact_suburb=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#suburb");
   /**
   * This address, is to mail?
   */
    public static final org.semanticwb.platform.SemanticProperty contact_isToMail=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#isToMail");
   /**
   * Catalogo de paises
   */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
   /**
   * País
   */
    public static final org.semanticwb.platform.SemanticProperty contact_country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#country");
   /**
   * Código postal
   */
    public static final org.semanticwb.platform.SemanticProperty contact_cp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#cp");
   /**
   * Dirección
   */
    public static final org.semanticwb.platform.SemanticClass contact_Address=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Address");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Address");

    public static class ClassMgr
    {
       /**
       * Returns a list of Address for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Address
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Address> listAddresses(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Address>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.contact.Address for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Address
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Address> listAddresses()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Address>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.contact.Address createAddress(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.contact.Address.ClassMgr.createAddress(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.contact.Address
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Address
       * @return A org.semanticwb.portal.resources.sem.contact.Address
       */
        public static org.semanticwb.portal.resources.sem.contact.Address getAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Address)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.contact.Address
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Address
       * @return A org.semanticwb.portal.resources.sem.contact.Address
       */
        public static org.semanticwb.portal.resources.sem.contact.Address createAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Address)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.contact.Address
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Address
       */
        public static void removeAddress(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.contact.Address
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Address
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Address
       * @return true if the org.semanticwb.portal.resources.sem.contact.Address exists, false otherwise
       */

        public static boolean hasAddress(String id, org.semanticwb.model.SWBModel model)
        {
            return (getAddress(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Address with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Address
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Address
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Address> listAddressByCountry(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Address> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_country, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Address with a determined Country
       * @param value Country of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Address
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Address> listAddressByCountry(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Address> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_country,value.getSemanticObject(),sclass));
            return it;
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
* Gets the IsToCheck property
* @return boolean with the IsToCheck
*/
    public boolean isIsToCheck()
    {
        return getSemanticObject().getBooleanProperty(contact_isToCheck);
    }

/**
* Sets the IsToCheck property
* @param value long with the IsToCheck
*/
    public void setIsToCheck(boolean value)
    {
        getSemanticObject().setBooleanProperty(contact_isToCheck, value);
    }

/**
* Gets the Street property
* @return String with the Street
*/
    public String getStreet()
    {
        return getSemanticObject().getProperty(contact_street);
    }

/**
* Sets the Street property
* @param value long with the Street
*/
    public void setStreet(String value)
    {
        getSemanticObject().setProperty(contact_street, value);
    }

/**
* Gets the State property
* @return String with the State
*/
    public String getState()
    {
        return getSemanticObject().getProperty(contact_state);
    }

/**
* Sets the State property
* @param value long with the State
*/
    public void setState(String value)
    {
        getSemanticObject().setProperty(contact_state, value);
    }

/**
* Gets the City property
* @return String with the City
*/
    public String getCity()
    {
        return getSemanticObject().getProperty(contact_city);
    }

/**
* Sets the City property
* @param value long with the City
*/
    public void setCity(String value)
    {
        getSemanticObject().setProperty(contact_city, value);
    }

/**
* Gets the Suburb property
* @return String with the Suburb
*/
    public String getSuburb()
    {
        return getSemanticObject().getProperty(contact_suburb);
    }

/**
* Sets the Suburb property
* @param value long with the Suburb
*/
    public void setSuburb(String value)
    {
        getSemanticObject().setProperty(contact_suburb, value);
    }

/**
* Gets the IsToMail property
* @return boolean with the IsToMail
*/
    public boolean isIsToMail()
    {
        return getSemanticObject().getBooleanProperty(contact_isToMail);
    }

/**
* Sets the IsToMail property
* @param value long with the IsToMail
*/
    public void setIsToMail(boolean value)
    {
        getSemanticObject().setBooleanProperty(contact_isToMail, value);
    }
   /**
   * Sets the value for the property Country
   * @param value Country to set
   */

    public void setCountry(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_country, value.getSemanticObject());
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
        getSemanticObject().removeProperty(contact_country);
    }

   /**
   * Gets the Country
   * @return a org.semanticwb.model.Country
   */
    public org.semanticwb.model.Country getCountry()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_country);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Cp property
* @return String with the Cp
*/
    public String getCp()
    {
        return getSemanticObject().getProperty(contact_cp);
    }

/**
* Sets the Cp property
* @param value long with the Cp
*/
    public void setCp(String value)
    {
        getSemanticObject().setProperty(contact_cp, value);
    }
}
