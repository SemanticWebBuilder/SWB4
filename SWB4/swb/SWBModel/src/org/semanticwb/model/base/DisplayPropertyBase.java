package org.semanticwb.model.base;


public class DisplayPropertyBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Sortable
{
    public static final org.semanticwb.platform.SemanticProperty swb_index=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#index");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propSelectValues=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propSelectValues");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propHidden=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propHidden");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propInvalidMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propInvalidMessage");
    public static final org.semanticwb.platform.SemanticClass swb_SWBFormElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#SWBFormElement");
    public static final org.semanticwb.platform.SemanticProperty swbxf_formElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#formElement");
    public static final org.semanticwb.platform.SemanticClass swbxf_PropertyGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#PropertyGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propGroup");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propPromptMessage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propPromptMessage");
    public static final org.semanticwb.platform.SemanticProperty swbxf_propEditable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/xforms/ontology#propEditable");
    public static final org.semanticwb.platform.SemanticClass swbxf_DisplayProperty=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/xforms/ontology#DisplayProperty");

    public DisplayPropertyBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(swb_index, index);
    }

    public String getSelectValues()
    {
        return getSemanticObject().getProperty(swbxf_propSelectValues);
    }

    public void setSelectValues(String propSelectValues)
    {
        getSemanticObject().setProperty(swbxf_propSelectValues, propSelectValues);
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

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propHidden);
    }

    public void setHidden(boolean propHidden)
    {
        getSemanticObject().setBooleanProperty(swbxf_propHidden, propHidden);
    }

    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(swbxf_propInvalidMessage);
    }

    public void setInvalidMessage(String propInvalidMessage)
    {
        getSemanticObject().setProperty(swbxf_propInvalidMessage, propInvalidMessage);
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

    public void setFormElement(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(swbxf_formElement, semanticobject);
    }

    public void removeFormElement()
    {
        getSemanticObject().removeProperty(swbxf_formElement);
    }

    public org.semanticwb.platform.SemanticObject getFormElement()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swbxf_formElement);
         return ret;
    }

    public void setGroup(org.semanticwb.model.PropertyGroup propertygroup)
    {
        getSemanticObject().setObjectProperty(swbxf_propGroup, propertygroup.getSemanticObject());
    }

    public void removeGroup()
    {
        getSemanticObject().removeProperty(swbxf_propGroup);
    }

    public org.semanticwb.model.PropertyGroup getGroup()
    {
         org.semanticwb.model.PropertyGroup ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swbxf_propGroup);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.PropertyGroup)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(swbxf_propPromptMessage);
    }

    public void setPromptMessage(String propPromptMessage)
    {
        getSemanticObject().setProperty(swbxf_propPromptMessage, propPromptMessage);
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

    public boolean isEditable()
    {
        return getSemanticObject().getBooleanProperty(swbxf_propEditable);
    }

    public void setEditable(boolean propEditable)
    {
        getSemanticObject().setBooleanProperty(swbxf_propEditable, propEditable);
    }
}
