package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class ObjectBehaviorBase extends WebPage implements Sortable,Iconable
{

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

    public String getIconClass()
    {
        return getSemanticObject().getProperty(vocabulary.swb_iconClass);
    }

    public void setIconClass(String iconClass)
    {
        getSemanticObject().setProperty(vocabulary.swb_iconClass, iconClass);
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
             ret=(ResourceParameter)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }
}
