package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DisplayPropertyBase extends GenericObjectBase implements Sortable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

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

    public boolean isRequired()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_propRequired);
    }

    public void setRequired(boolean propRequired)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_propRequired, propRequired);
    }

    public String getSelectValues()
    {
        return getSemanticObject().getProperty(vocabulary.swb_propSelectValues);
    }

    public void setSelectValues(String propSelectValues)
    {
        getSemanticObject().setProperty(vocabulary.swb_propSelectValues, propSelectValues);
    }

    public String getSelectValues(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_propSelectValues, null, lang);
    }

    public String getDisplaySelectValues(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_propSelectValues, lang);
    }

    public void setSelectValues(String propSelectValues, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_propSelectValues, propSelectValues, lang);
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_propHidden);
    }

    public void setHidden(boolean propHidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_propHidden, propHidden);
    }

    public String getGroup()
    {
        return getSemanticObject().getProperty(vocabulary.swb_propGroup);
    }

    public void setGroup(String propGroup)
    {
        getSemanticObject().setProperty(vocabulary.swb_propGroup, propGroup);
    }

    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(vocabulary.swb_propPromptMessage);
    }

    public void setPromptMessage(String propPromptMessage)
    {
        getSemanticObject().setProperty(vocabulary.swb_propPromptMessage, propPromptMessage);
    }

    public String getPromptMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_propPromptMessage, null, lang);
    }

    public String getDisplayPromptMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_propPromptMessage, lang);
    }

    public void setPromptMessage(String propPromptMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_propPromptMessage, propPromptMessage, lang);
    }

    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(vocabulary.swb_propInvalidMessage);
    }

    public void setInvalidMessage(String propInvalidMessage)
    {
        getSemanticObject().setProperty(vocabulary.swb_propInvalidMessage, propInvalidMessage);
    }

    public String getInvalidMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_propInvalidMessage, null, lang);
    }

    public String getDisplayInvalidMessage(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_propInvalidMessage, lang);
    }

    public void setInvalidMessage(String propInvalidMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_propInvalidMessage, propInvalidMessage, lang);
    }

    public void setFormElement(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_formElement, semanticobject);
    }

    public void removeFormElement()
    {
        getSemanticObject().removeProperty(vocabulary.swb_formElement);
    }

    public SemanticObject getFormElement()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swb_formElement);
         return ret;
    }

    public boolean isEditable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_propEditable);
    }

    public void setEditable(boolean propEditable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_propEditable, propEditable);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
