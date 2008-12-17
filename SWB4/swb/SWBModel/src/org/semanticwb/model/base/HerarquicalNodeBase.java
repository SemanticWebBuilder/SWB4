package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class HerarquicalNodeBase extends SWBClass implements Sortable,Iconable,Descriptiveable
{

    public HerarquicalNodeBase(SemanticObject base)
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

    public void setModel(org.semanticwb.model.SWBModel swbmodel)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_heModel, swbmodel.getSemanticObject());
    }

    public void removeModel()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_heModel);
    }

    public SWBModel getModel()
    {
         SWBModel ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_heModel);
         if(obj!=null)
         {
             ret=(SWBModel)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public String getIconClass()
    {
        return getSemanticObject().getProperty(vocabulary.swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(vocabulary.swb_iconClass, iconClass);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.swb_title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_title, title, lang);
    }

    public void setHClass(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_heClass, semanticobject);
    }

    public void removeHClass()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_heClass);
    }

    public SemanticObject getHClass()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swbxf_heClass);
         return ret;
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.swb_description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(vocabulary.swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.swb_description, description, lang);
    }
}
