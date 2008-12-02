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

public class ObjectBehaviorBase extends GenericObjectBase implements Descriptiveable,Iconable,Sortable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ObjectBehaviorBase(SemanticObject base)
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

    public void setDisplayObject(org.semanticwb.model.DisplayObject displayobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_displayObject, displayobject.getSemanticObject());
    }

    public void removeDisplayObject()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_displayObject);
    }

    public DisplayObject getDisplayObject()
    {
         DisplayObject ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_displayObject);
         if(obj!=null)
         {
             ret=(DisplayObject)vocabulary.swbxf_DisplayObject.newGenericInstance(obj);
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

    public boolean isRefreshOnShow()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_behaviorRefreshOnShow);
    }

    public void setRefreshOnShow(boolean behaviorRefreshOnShow)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_behaviorRefreshOnShow, behaviorRefreshOnShow);
    }

    public void setInterface(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbxf_interface, semanticobject);
    }

    public void removeInterface()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_interface);
    }

    public SemanticObject getInterface()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swbxf_interface);
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

    public String getURL()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_behaviorURL);
    }

    public void setURL(String behaviorURL)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_behaviorURL, behaviorURL);
    }

    public GenericIterator<org.semanticwb.model.ResourceParameter> listParams()
    {
        return new GenericIterator<org.semanticwb.model.ResourceParameter>(org.semanticwb.model.ResourceParameter.class, getSemanticObject().listObjectProperties(vocabulary.swbxf_hasResourceParam));    }

    public void addParam(org.semanticwb.model.ResourceParameter resourceparameter)
    {
        getSemanticObject().addObjectProperty(vocabulary.swbxf_hasResourceParam, resourceparameter.getSemanticObject());
    }

    public void removeAllParam()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_hasResourceParam);
    }

    public void removeParam(org.semanticwb.model.ResourceParameter resourceparameter)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swbxf_hasResourceParam,resourceparameter.getSemanticObject());
    }

    public ResourceParameter getParam()
    {
         ResourceParameter ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_hasResourceParam);
         if(obj!=null)
         {
             ret=(ResourceParameter)vocabulary.swbxf_ResourceParameter.newGenericInstance(obj);
         }
         return ret;
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
