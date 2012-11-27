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
   * Teléfono 
   */
public abstract class PhoneBase extends org.semanticwb.model.SWBClass 
{
   /**
   * Catalogo de paises
   */
    public static final org.semanticwb.platform.SemanticClass swb_Country=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Country");
   /**
   * País o región
   */
    public static final org.semanticwb.platform.SemanticProperty contact_region=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#region");
   /**
   * Extensión
   */
    public static final org.semanticwb.platform.SemanticProperty contact_ext=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#ext");
   /**
   * Número local
   */
    public static final org.semanticwb.platform.SemanticProperty contact_localNumber=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#localNumber");
   /**
   * Código de área
   */
    public static final org.semanticwb.platform.SemanticProperty contact_areaCode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/portal/resources/Contact#areaCode");
   /**
   * Teléfono
   */
    public static final org.semanticwb.platform.SemanticClass contact_Phone=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Phone");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/portal/resources/Contact#Phone");

    public static class ClassMgr
    {
       /**
       * Returns a list of Phone for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Phone
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Phone> listPhones(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Phone>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.portal.resources.sem.contact.Phone for all models
       * @return Iterator of org.semanticwb.portal.resources.sem.contact.Phone
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Phone> listPhones()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Phone>(it, true);
        }

        public static org.semanticwb.portal.resources.sem.contact.Phone createPhone(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.portal.resources.sem.contact.Phone.ClassMgr.createPhone(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.portal.resources.sem.contact.Phone
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Phone
       * @return A org.semanticwb.portal.resources.sem.contact.Phone
       */
        public static org.semanticwb.portal.resources.sem.contact.Phone getPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Phone)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.portal.resources.sem.contact.Phone
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Phone
       * @return A org.semanticwb.portal.resources.sem.contact.Phone
       */
        public static org.semanticwb.portal.resources.sem.contact.Phone createPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.portal.resources.sem.contact.Phone)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.portal.resources.sem.contact.Phone
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Phone
       */
        public static void removePhone(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.portal.resources.sem.contact.Phone
       * @param id Identifier for org.semanticwb.portal.resources.sem.contact.Phone
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Phone
       * @return true if the org.semanticwb.portal.resources.sem.contact.Phone exists, false otherwise
       */

        public static boolean hasPhone(String id, org.semanticwb.model.SWBModel model)
        {
            return (getPhone(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Phone with a determined Region
       * @param value Region of the type org.semanticwb.model.Country
       * @param model Model of the org.semanticwb.portal.resources.sem.contact.Phone
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Phone
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Phone> listPhoneByRegion(org.semanticwb.model.Country value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Phone> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(contact_region, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.portal.resources.sem.contact.Phone with a determined Region
       * @param value Region of the type org.semanticwb.model.Country
       * @return Iterator with all the org.semanticwb.portal.resources.sem.contact.Phone
       */

        public static java.util.Iterator<org.semanticwb.portal.resources.sem.contact.Phone> listPhoneByRegion(org.semanticwb.model.Country value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.portal.resources.sem.contact.Phone> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(contact_region,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
   * Constructs a PhoneBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Phone
   */
    public PhoneBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property Region
   * @param value Region to set
   */

    public void setRegion(org.semanticwb.model.Country value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(contact_region, value.getSemanticObject());
        }else
        {
            removeRegion();
        }
    }
   /**
   * Remove the value for Region property
   */

    public void removeRegion()
    {
        getSemanticObject().removeProperty(contact_region);
    }

   /**
   * Gets the Region
   * @return a org.semanticwb.model.Country
   */
    public org.semanticwb.model.Country getRegion()
    {
         org.semanticwb.model.Country ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(contact_region);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Country)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Ext property
* @return int with the Ext
*/
    public int getExt()
    {
        return getSemanticObject().getIntProperty(contact_ext);
    }

/**
* Sets the Ext property
* @param value long with the Ext
*/
    public void setExt(int value)
    {
        getSemanticObject().setIntProperty(contact_ext, value);
    }

/**
* Gets the LocalNumber property
* @return int with the LocalNumber
*/
    public int getLocalNumber()
    {
        return getSemanticObject().getIntProperty(contact_localNumber);
    }

/**
* Sets the LocalNumber property
* @param value long with the LocalNumber
*/
    public void setLocalNumber(int value)
    {
        getSemanticObject().setIntProperty(contact_localNumber, value);
    }

/**
* Gets the AreaCode property
* @return int with the AreaCode
*/
    public int getAreaCode()
    {
        return getSemanticObject().getIntProperty(contact_areaCode);
    }

/**
* Sets the AreaCode property
* @param value long with the AreaCode
*/
    public void setAreaCode(int value)
    {
        getSemanticObject().setIntProperty(contact_areaCode, value);
    }
}
