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


public abstract class NameBase extends org.semanticwb.model.base.GenericObjectBase implements org.semanticwb.opensocial.model.Formatteable
{
    public static final org.semanticwb.platform.SemanticProperty data_honorificPrefix=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#honorificPrefix");
    public static final org.semanticwb.platform.SemanticProperty data_honorificSuffix=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#honorificSuffix");
    public static final org.semanticwb.platform.SemanticProperty data_middleName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#middleName");
    public static final org.semanticwb.platform.SemanticProperty data_givenName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#givenName");
   /**
   * Indica si el elemento es válido
   */
    public static final org.semanticwb.platform.SemanticProperty swb_valid=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#valid");
    public static final org.semanticwb.platform.SemanticProperty data_familyName=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#familyName");
    public static final org.semanticwb.platform.SemanticClass data_Name=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Name");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/opensocial/socialdata#Name");

    public static class ClassMgr
    {
       /**
       * Returns a list of Name for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.opensocial.model.data.Name
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Name> listNames(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Name>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.opensocial.model.data.Name for all models
       * @return Iterator of org.semanticwb.opensocial.model.data.Name
       */

        public static java.util.Iterator<org.semanticwb.opensocial.model.data.Name> listNames()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.opensocial.model.data.Name>(it, true);
        }

        public static org.semanticwb.opensocial.model.data.Name createName(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.opensocial.model.data.Name.ClassMgr.createName(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.opensocial.model.data.Name
       * @param id Identifier for org.semanticwb.opensocial.model.data.Name
       * @param model Model of the org.semanticwb.opensocial.model.data.Name
       * @return A org.semanticwb.opensocial.model.data.Name
       */
        public static org.semanticwb.opensocial.model.data.Name getName(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Name)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.opensocial.model.data.Name
       * @param id Identifier for org.semanticwb.opensocial.model.data.Name
       * @param model Model of the org.semanticwb.opensocial.model.data.Name
       * @return A org.semanticwb.opensocial.model.data.Name
       */
        public static org.semanticwb.opensocial.model.data.Name createName(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.opensocial.model.data.Name)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.opensocial.model.data.Name
       * @param id Identifier for org.semanticwb.opensocial.model.data.Name
       * @param model Model of the org.semanticwb.opensocial.model.data.Name
       */
        public static void removeName(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.opensocial.model.data.Name
       * @param id Identifier for org.semanticwb.opensocial.model.data.Name
       * @param model Model of the org.semanticwb.opensocial.model.data.Name
       * @return true if the org.semanticwb.opensocial.model.data.Name exists, false otherwise
       */

        public static boolean hasName(String id, org.semanticwb.model.SWBModel model)
        {
            return (getName(id, model)!=null);
        }
    }

   /**
   * Constructs a NameBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Name
   */
    public NameBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the HonorificPrefix property
* @return String with the HonorificPrefix
*/
    public String getHonorificPrefix()
    {
        return getSemanticObject().getProperty(data_honorificPrefix);
    }

/**
* Sets the HonorificPrefix property
* @param value long with the HonorificPrefix
*/
    public void setHonorificPrefix(String value)
    {
        getSemanticObject().setProperty(data_honorificPrefix, value);
    }

/**
* Gets the HonorificSuffix property
* @return String with the HonorificSuffix
*/
    public String getHonorificSuffix()
    {
        return getSemanticObject().getProperty(data_honorificSuffix);
    }

/**
* Sets the HonorificSuffix property
* @param value long with the HonorificSuffix
*/
    public void setHonorificSuffix(String value)
    {
        getSemanticObject().setProperty(data_honorificSuffix, value);
    }

/**
* Gets the MiddleName property
* @return String with the MiddleName
*/
    public String getMiddleName()
    {
        return getSemanticObject().getProperty(data_middleName);
    }

/**
* Sets the MiddleName property
* @param value long with the MiddleName
*/
    public void setMiddleName(String value)
    {
        getSemanticObject().setProperty(data_middleName, value);
    }

/**
* Gets the GivenName property
* @return String with the GivenName
*/
    public String getGivenName()
    {
        return getSemanticObject().getProperty(data_givenName);
    }

/**
* Sets the GivenName property
* @param value long with the GivenName
*/
    public void setGivenName(String value)
    {
        getSemanticObject().setProperty(data_givenName, value);
    }

/**
* Gets the Valid property
* @return boolean with the Valid
*/
    public boolean isValid()
    {
        //Override this method in Name object
        return getSemanticObject().getBooleanProperty(swb_valid,false);
    }

/**
* Sets the Valid property
* @param value long with the Valid
*/
    public void setValid(boolean value)
    {
        //Override this method in Name object
        getSemanticObject().setBooleanProperty(swb_valid, value,false);
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
* Gets the FamilyName property
* @return String with the FamilyName
*/
    public String getFamilyName()
    {
        return getSemanticObject().getProperty(data_familyName);
    }

/**
* Sets the FamilyName property
* @param value long with the FamilyName
*/
    public void setFamilyName(String value)
    {
        getSemanticObject().setProperty(data_familyName, value);
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
