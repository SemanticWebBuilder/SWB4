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
 * The Class DisplayPropertyBase.
 */
public abstract class DisplayPropertyBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
    
    /** The Constant swbxf_propSelectValues. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propSelectValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propSelectValues");
    
    /** The Constant swbxf_propDisabled. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propDisabled=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propDisabled");
    
    /** The Constant swbxf_propHidden. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propHidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propHidden");
    
    /** The Constant swbxf_propInvalidMessage. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propInvalidMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propInvalidMessage");
    
    /** The Constant swb_SWBFormElement. */
    public static final org.semanticwb.platform.SemanticClass swb_SWBFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBFormElement");
    
    /** The Constant swbxf_formElement. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_formElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement");
    
    /** The Constant swbxf_PropertyGroup. */
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    
    /** The Constant swbxf_propGroup. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propGroup");
    
    /** The Constant swbxf_propPromptMessage. */
    public static final org.semanticwb.platform.SemanticProperty swbxf_propPromptMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propPromptMessage");
    
    /** The Constant swbxf_DisplayProperty. */
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");
    
    /** The Constant sclass. */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");

    /**
     * The Class ClassMgr.
     */
    public static class ClassMgr
    {

        /**
         * List display properties.
         * 
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayProperties(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty>(it, true);
        }

        /**
         * List display properties.
         * 
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayProperties()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty>(it, true);
        }

        /**
         * Gets the display property.
         * 
         * @param id the id
         * @param model the model
         * @return the display property
         */
        public static org.semanticwb.model.DisplayProperty getDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayProperty)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }

        /**
         * Creates the display property.
         * 
         * @param id the id
         * @param model the model
         * @return the org.semanticwb.model. display property
         */
        public static org.semanticwb.model.DisplayProperty createDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.model.DisplayProperty)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
        }

        /**
         * Removes the display property.
         * 
         * @param id the id
         * @param model the model
         */
        public static void removeDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }

        /**
         * Checks for display property.
         * 
         * @param id the id
         * @param model the model
         * @return true, if successful
         */
        public static boolean hasDisplayProperty(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDisplayProperty(id, model)!=null);
        }

        /**
         * List display property by group.
         * 
         * @param propgroup the propgroup
         * @param model the model
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayPropertyByGroup(org.semanticwb.model.PropertyGroup propgroup,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swbxf_propGroup, propgroup.getSemanticObject()));
            return it;
        }

        /**
         * List display property by group.
         * 
         * @param propgroup the propgroup
         * @return the java.util. iterator
         */
        public static java.util.Iterator<org.semanticwb.model.DisplayProperty> listDisplayPropertyByGroup(org.semanticwb.model.PropertyGroup propgroup)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.model.DisplayProperty> it=new org.semanticwb.model.GenericIterator(propgroup.getSemanticObject().getModel().listSubjects(swbxf_propGroup,propgroup.getSemanticObject()));
            return it;
        }
    }

    /**
     * Instantiates a new display property base.
     * 
     * @param base the base
     */
    public DisplayPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#getIndex()
     */
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.SortableBase#setIndex(int)
     */
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

    /**
     * Gets the select values.
     * 
     * @return the select values
     */
    public String getSelectValues()
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues);
    }

    /**
     * Sets the select values.
     * 
     * @param value the new select values
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
     * Checks if is disabled.
     * 
     * @return true, if is disabled
     */
    public boolean isDisabled()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propDisabled);
    }

    /**
     * Sets the disabled.
     * 
     * @param value the new disabled
     */
    public void setDisabled(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propDisabled, value);
    }

    /**
     * Checks if is hidden.
     * 
     * @return true, if is hidden
     */
    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propHidden);
    }

    /**
     * Sets the hidden.
     * 
     * @param value the new hidden
     */
    public void setHidden(boolean value)
    {
        getSemanticObject().setBooleanProperty(swbxf_propHidden, value);
    }

    /**
     * Gets the invalid message.
     * 
     * @return the invalid message
     */
    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage);
    }

    /**
     * Sets the invalid message.
     * 
     * @param value the new invalid message
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
     * Gets the form element.
     * 
     * @return the form element
     */
    public org.semanticwb.platform.SemanticObject getFormElement()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_formElement);
         return ret;
    }

    /**
     * Sets the group.
     * 
     * @param value the new group
     */
    public void setGroup(org.semanticwb.model.PropertyGroup value)
    {
        getSemanticObject().setObjectProperty(swbxf_propGroup, value.getSemanticObject());
    }

    /**
     * Removes the group.
     */
    public void removeGroup()
    {
        getSemanticObject().removeProperty(swbxf_propGroup);
    }

    /**
     * Gets the group.
     * 
     * @return the group
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
     * Gets the prompt message.
     * 
     * @return the prompt message
     */
    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage);
    }

    /**
     * Sets the prompt message.
     * 
     * @param value the new prompt message
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
