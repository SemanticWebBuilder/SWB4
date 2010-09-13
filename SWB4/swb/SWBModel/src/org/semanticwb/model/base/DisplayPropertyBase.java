/**  
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
 **/
package org.semanticwb.model.base;


   // TODO: Auto-generated Javadoc
/**
    * Objeto utilizado para definir caracteristicas visuales de una propiedad dentro de la administracion de SWB.
    */
public abstract class DisplayPropertyBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
   
   /** Valores posibles a seleccionar ejemplo: 1:baja|2:media|3:alta. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propSelectValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propSelectValues");
    
    /** The Constant swbxf_propDisabled. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propDisabled=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propDisabled");
    
    /** The Constant swbxf_propHidden. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propHidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propHidden");
    
    /** The Constant swbxf_propInvalidMessage. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propInvalidMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propInvalidMessage");
   
   /** Superclase de los elementos de formulario usados en la creación de formularios de vistas para las propiedades de los objetos en SemanticWebBuilder. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBFormElement");
    
    /** The Constant swbxf_formElement. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_formElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement");
   
   /** Objeto utiizado para definir un grupo de propiedades dentro de la administración de SWB (fieldset). */
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    
    /** The Constant swbxf_propGroup. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propGroup");
    
    /** The Constant swbxf_propPromptMessage. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propPromptMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propPromptMessage");
   
   /** Objeto utilizado para definir caracteristicas visuales de una propiedad dentro de la administracion de SWB. */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");
   
   /** The semantic class that represents the currentObject. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {
       
       /**
        * Returns a list of DisplayProperty for a model.
        * 
        * @param model Model to find
        * @return Iterator of org.semanticwb.model.DisplayProperty
        */

        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayProperties(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.model.DisplayProperty for all models
       * @return Iterator of org.semanticwb.model.DisplayProperty
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayProperties()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty>(it, true);
        }
       /**
       * Gets a org.semanticwb.model.DisplayProperty
       * @param id Identifier for org.semanticwb.model.DisplayProperty
       * @param model Model of the org.semanticwb.model.DisplayProperty
       * @return A org.semanticwb.model.DisplayProperty
       */
        public static org.semanticwb.model.DisplayProperty getDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayProperty)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.model.DisplayProperty
       * @param id Identifier for org.semanticwb.model.DisplayProperty
       * @param model Model of the org.semanticwb.model.DisplayProperty
       * @return A org.semanticwb.model.DisplayProperty
       */
        public static org.semanticwb.model.DisplayProperty createDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayProperty)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.model.DisplayProperty
       * @param id Identifier for org.semanticwb.model.DisplayProperty
       * @param model Model of the org.semanticwb.model.DisplayProperty
       */
        public static void removeDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.model.DisplayProperty
       * @param id Identifier for org.semanticwb.model.DisplayProperty
       * @param model Model of the org.semanticwb.model.DisplayProperty
       * @return true if the org.semanticwb.model.DisplayProperty exists, false otherwise
       */

        public static boolean hasDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDisplayProperty(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.model.DisplayProperty with a determined Group
       * @param value Group of the type org.semanticwb.model.PropertyGroup
       * @param model Model of the org.semanticwb.model.DisplayProperty
       * @return Iterator with all the org.semanticwb.model.DisplayProperty
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayPropertyByGroup(org.semanticwb.model.PropertyGroup value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swbxf_propGroup, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.model.DisplayProperty with a determined Group
       * @param value Group of the type org.semanticwb.model.PropertyGroup
       * @return Iterator with all the org.semanticwb.model.DisplayProperty
       */

        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayPropertyByGroup(org.semanticwb.model.PropertyGroup value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swbxf_propGroup,value.getSemanticObject(),sclass));
            return it;
        }
    }

   /**
    * Constructs a DisplayPropertyBase with a SemanticObject.
    * 
    * @param base The SemanticObject with the properties for the DisplayProperty
    */
    public DisplayPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
 * Gets the Index property.
 * 
 * @return int with the Index
 */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
 * Sets the Index property.
 * 
 * @param value long with the Index
 */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
 * Gets the SelectValues property.
 * 
 * @return String with the SelectValues
 */
    public String getSelectValues()
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues);
    }

/**
 * Sets the SelectValues property.
 * 
 * @param value long with the SelectValues
 */
    public void setSelectValues(String value)
    {
        getSemanticObject().setProperty(swbxf_propSelectValues, value);
    }

    /**
     * Gets the select values.
     * 
     * @param lang the lang
     * @return the select values
     */
    public String getSelectValues(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues, null, lang);
    }

    /**
     * Gets the display select values.
     * 
     * @param lang the lang
     * @return the display select values
     */
    public String getDisplaySelectValues(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propSelectValues, lang);
    }

    /**
     * Sets the select values.
     * 
     * @param propSelectValues the prop select values
     * @param lang the lang
     */
    public void setSelectValues(String propSelectValues, String lang)
    {
        getSemanticObject().setProperty(swbxf_propSelectValues, propSelectValues, lang);
    }

/**
 * Gets the Disabled property.
 * 
 * @return boolean with the Disabled
 */
    public boolean isDisabled()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propDisabled);
    }

/**
 * Sets the Disabled property.
 * 
 * @param value long with the Disabled
 */
    public void setDisabled(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propDisabled, value);
    }

/**
 * Gets the Hidden property.
 * 
 * @return boolean with the Hidden
 */
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propHidden);
    }

/**
 * Sets the Hidden property.
 * 
 * @param value long with the Hidden
 */
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propHidden, value);
    }

/**
 * Gets the InvalidMessage property.
 * 
 * @return String with the InvalidMessage
 */
    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage);
    }

/**
 * Sets the InvalidMessage property.
 * 
 * @param value long with the InvalidMessage
 */
    public void setInvalidMessage(String value)
    {
        getSemanticObject().setProperty(swbxf_propInvalidMessage, value);
    }

    /**
     * Gets the invalid message.
     * 
     * @param lang the lang
     * @return the invalid message
     */
    public String getInvalidMessage(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage, null, lang);
    }

    /**
     * Gets the display invalid message.
     * 
     * @param lang the lang
     * @return the display invalid message
     */
    public String getDisplayInvalidMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propInvalidMessage, lang);
    }

    /**
     * Sets the invalid message.
     * 
     * @param propInvalidMessage the prop invalid message
     * @param lang the lang
     */
    public void setInvalidMessage(String propInvalidMessage, String lang)
    {
        getSemanticObject().setProperty(swbxf_propInvalidMessage, propInvalidMessage, lang);
    }

    /**
     * Sets the form element.
     * 
     * @param value the new form element
     */
    public void setFormElement(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbxf_formElement, value);
    }

    /**
     * Removes the form element.
     */
    public void removeFormElement()
    {
        getSemanticObject().removeProperty(swbxf_formElement);
    }

/**
 * Gets the FormElement property.
 * 
 * @return the value for the property as org.semanticwb.platform.SemanticObject
 */
    public org.semanticwb.platform.SemanticObject getFormElement()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_formElement);
         return ret;
    }
   
   /**
    * Sets the value for the property Group.
    * 
    * @param value Group to set
    */

    public void setGroup(org.semanticwb.model.PropertyGroup value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swbxf_propGroup, value.getSemanticObject());
        }else
        {
            removeGroup();
        }
    }
   
   /**
    * Remove the value for Group property.
    */

    public void removeGroup()
    {
        getSemanticObject().removeProperty(swbxf_propGroup);
    }

   /**
    * Gets the Group.
    * 
    * @return a org.semanticwb.model.PropertyGroup
    */
    public org.semanticwb.model.PropertyGroup getGroup()
    {
         org.semanticwb.model.PropertyGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_propGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PropertyGroup)obj.createGenericInstance();
         }
         return ret;
    }

/**
 * Gets the PromptMessage property.
 * 
 * @return String with the PromptMessage
 */
    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage);
    }

/**
 * Sets the PromptMessage property.
 * 
 * @param value long with the PromptMessage
 */
    public void setPromptMessage(String value)
    {
        getSemanticObject().setProperty(swbxf_propPromptMessage, value);
    }

    /**
     * Gets the prompt message.
     * 
     * @param lang the lang
     * @return the prompt message
     */
    public String getPromptMessage(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage, null, lang);
    }

    /**
     * Gets the display prompt message.
     * 
     * @param lang the lang
     * @return the display prompt message
     */
    public String getDisplayPromptMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propPromptMessage, lang);
    }

    /**
     * Sets the prompt message.
     * 
     * @param propPromptMessage the prop prompt message
     * @param lang the lang
     */
    public void setPromptMessage(String propPromptMessage, String lang)
    {
        getSemanticObject().setProperty(swbxf_propPromptMessage, propPromptMessage, lang);
    }
}
