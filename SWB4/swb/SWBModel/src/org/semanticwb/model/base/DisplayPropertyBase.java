package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DisplayPropertyBase extends GenericObjectBase implements Sortable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DisplayPropertyBase(SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(vocabulary.index, index);
    }

    public boolean isRequired()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.propRequired);
    }

    public void setRequired(boolean propRequired)
    {
        getSemanticObject().setBooleanProperty(vocabulary.propRequired, propRequired);
    }

    public String getSelectValues()
    {
        return getSemanticObject().getProperty(vocabulary.propSelectValues);
    }

    public void setSelectValues(String propSelectValues)
    {
        getSemanticObject().setProperty(vocabulary.propSelectValues, propSelectValues);
    }

    public String getSelectValues(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.propSelectValues, null, lang);
    }

    public void setSelectValues(String propSelectValues, String lang)
    {
        getSemanticObject().setProperty(vocabulary.propSelectValues, propSelectValues, lang);
    }

    public boolean isHidden()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.propHidden);
    }

    public void setHidden(boolean propHidden)
    {
        getSemanticObject().setBooleanProperty(vocabulary.propHidden, propHidden);
    }

    public String getGroup()
    {
        return getSemanticObject().getProperty(vocabulary.propGroup);
    }

    public void setGroup(String propGroup)
    {
        getSemanticObject().setProperty(vocabulary.propGroup, propGroup);
    }

    public String getPromptMessage()
    {
        return getSemanticObject().getProperty(vocabulary.propPromptMessage);
    }

    public void setPromptMessage(String propPromptMessage)
    {
        getSemanticObject().setProperty(vocabulary.propPromptMessage, propPromptMessage);
    }

    public String getPromptMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.propPromptMessage, null, lang);
    }

    public void setPromptMessage(String propPromptMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.propPromptMessage, propPromptMessage, lang);
    }

    public String getInvalidMessage()
    {
        return getSemanticObject().getProperty(vocabulary.propInvalidMessage);
    }

    public void setInvalidMessage(String propInvalidMessage)
    {
        getSemanticObject().setProperty(vocabulary.propInvalidMessage, propInvalidMessage);
    }

    public String getInvalidMessage(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.propInvalidMessage, null, lang);
    }

    public void setInvalidMessage(String propInvalidMessage, String lang)
    {
        getSemanticObject().setProperty(vocabulary.propInvalidMessage, propInvalidMessage, lang);
    }

    public void setFormElement(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.formElement, semanticobject);
    }

    public void removeFormElement()
    {
        getSemanticObject().removeProperty(vocabulary.formElement);
    }

    public SemanticObject getFormElement()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.formElement);
         return ret;
    }

    public boolean isEditable()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.propEditable);
    }

    public void setEditable(boolean propEditable)
    {
        getSemanticObject().setBooleanProperty(vocabulary.propEditable, propEditable);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());
        return new GenericIterator((SemanticClass)null, stit,true);
    }
}
