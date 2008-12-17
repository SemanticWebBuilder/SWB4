package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class DisplayPropertyBase extends SWBClass implements Sortable
{

    public DisplayPropertyBase(SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_index, index);
    }

    public String getSelectValues()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propSelectValues);
    }

    public void setSelectValues(String propSelectValues)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propSelectValues, propSelectValues);
    }

    public String getSelectValues(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propSelectValues, null, lang);
    }

    public String getDisplaySelectValues(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swbxf_propSelectValues, lang);
    }

    public void setSelectValues(String propSelectValues, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propSelectValues, propSelectValues, lang);
    }

    public void setGroup(org.semanticwb.model.PropertyGroup propertygroup)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_propGroup, propertygroup.getSemanticObject());
    }

    public void removeGroup()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_propGroup);
    }

    public PropertyGroup getGroup()
    {
         PropertyGroup ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_propGroup);
         if(obj!=null)
         {
             ret=(PropertyGroup)vocabulary.swbxf_PropertyGroup.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_propHidden);
    }

    public void setHidden(boolean propHidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_propHidden, propHidden);
    }

    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propPromptMessage);
    }

    public void setPromptMessage(String propPromptMessage)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propPromptMessage, propPromptMessage);
    }

    public String getPromptMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propPromptMessage, null, lang);
    }

    public String getDisplayPromptMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swbxf_propPromptMessage, lang);
    }

    public void setPromptMessage(String propPromptMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propPromptMessage, propPromptMessage, lang);
    }

    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propInvalidMessage);
    }

    public void setInvalidMessage(String propInvalidMessage)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propInvalidMessage, propInvalidMessage);
    }

    public String getInvalidMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_propInvalidMessage, null, lang);
    }

    public String getDisplayInvalidMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swbxf_propInvalidMessage, lang);
    }

    public void setInvalidMessage(String propInvalidMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_propInvalidMessage, propInvalidMessage, lang);
    }

    public void setFormElement(org.semanticwb.model.SWBFormElement swbformelement)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_formElement, swbformelement.getSemanticObject());
    }

    public void removeFormElement()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_formElement);
    }

    public SWBFormElement getFormElement()
    {
         SWBFormElement ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_formElement);
         if(obj!=null)
         {
             ret=(SWBFormElement)vocabulary.swb_SWBFormElement.newGenericInstance(obj);
         }
         return ret;
    }

    public boolean isEditable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_propEditable);
    }

    public void setEditable(boolean propEditable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_propEditable, propEditable);
    }
}
