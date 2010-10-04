package org.semanticwb.model.base;


   /**
   * Objeto utilizado para definir caracteristicas visuales de una propiedad dentro de la administracion de SWB 
   */
public abstract class DisplayPropertyBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
   /**
   * Valores posibles a seleccionar ejemplo: 1:baja|2:media|3:alta
   */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propSelectValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propSelectValues");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propDisabled=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propDisabled");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propHidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propHidden");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propInvalidMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propInvalidMessage");
   /**
   * Superclase de los elementos de formulario usados en la creación de formularios de vistas para las propiedades de los objetos en SemanticWebBuilder
   */
    public static final org.semanticwb.platform.SemanticClass swb_SWBFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBFormElement");
    public static final org.semanticwb.platform.SemanticProperty swbxf_formElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement");
   /**
   * Objeto utiizado para definir un grupo de propiedades dentro de la administración de SWB (fieldset)
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propPromptMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propPromptMessage");
   /**
   * Objeto utilizado para definir caracteristicas visuales de una propiedad dentro de la administracion de SWB
   */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");

    public static class ClassMgr
    {
       /**
       * Returns a list of DisplayProperty for a model
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
   * Constructs a DisplayPropertyBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DisplayProperty
   */
    public DisplayPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the SelectValues property
* @return String with the SelectValues
*/
    public String getSelectValues()
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues);
    }

/**
* Sets the SelectValues property
* @param value long with the SelectValues
*/
    public void setSelectValues(String value)
    {
        getSemanticObject().setProperty(swbxf_propSelectValues, value);
    }

    public String getSelectValues(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues, null, lang);
    }

    public String getDisplaySelectValues(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propSelectValues, lang);
    }

    public void setSelectValues(String propSelectValues, String lang)
    {
        getSemanticObject().setProperty(swbxf_propSelectValues, propSelectValues, lang);
    }

/**
* Gets the Disabled property
* @return boolean with the Disabled
*/
    public boolean isDisabled()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propDisabled);
    }

/**
* Sets the Disabled property
* @param value long with the Disabled
*/
    public void setDisabled(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propDisabled, value);
    }

/**
* Gets the Hidden property
* @return boolean with the Hidden
*/
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propHidden);
    }

/**
* Sets the Hidden property
* @param value long with the Hidden
*/
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propHidden, value);
    }

/**
* Gets the InvalidMessage property
* @return String with the InvalidMessage
*/
    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage);
    }

/**
* Sets the InvalidMessage property
* @param value long with the InvalidMessage
*/
    public void setInvalidMessage(String value)
    {
        getSemanticObject().setProperty(swbxf_propInvalidMessage, value);
    }

    public String getInvalidMessage(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage, null, lang);
    }

    public String getDisplayInvalidMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propInvalidMessage, lang);
    }

    public void setInvalidMessage(String propInvalidMessage, String lang)
    {
        getSemanticObject().setProperty(swbxf_propInvalidMessage, propInvalidMessage, lang);
    }

    public void setFormElement(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swbxf_formElement, value);
    }

    public void removeFormElement()
    {
        getSemanticObject().removeProperty(swbxf_formElement);
    }

/**
* Gets the FormElement property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getFormElement()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_formElement);
         return ret;
    }
   /**
   * Sets the value for the property Group
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
   * Remove the value for Group property
   */

    public void removeGroup()
    {
        getSemanticObject().removeProperty(swbxf_propGroup);
    }

   /**
   * Gets the Group
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
* Gets the PromptMessage property
* @return String with the PromptMessage
*/
    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage);
    }

/**
* Sets the PromptMessage property
* @param value long with the PromptMessage
*/
    public void setPromptMessage(String value)
    {
        getSemanticObject().setProperty(swbxf_propPromptMessage, value);
    }

    public String getPromptMessage(String lang)
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage, null, lang);
    }

    public String getDisplayPromptMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(swbxf_propPromptMessage, lang);
    }

    public void setPromptMessage(String propPromptMessage, String lang)
    {
        getSemanticObject().setProperty(swbxf_propPromptMessage, propPromptMessage, lang);
    }
}
