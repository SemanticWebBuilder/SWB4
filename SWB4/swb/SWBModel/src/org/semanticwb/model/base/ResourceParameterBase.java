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

public class ResourceParameterBase extends GenericObjectBase 
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ResourceParameterBase(SemanticObject base)
    {
        super(base);
    }

    public void setValue(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_resParamValue, semanticobject);
    }

    public void removeValue()
    {
        getSemanticObject().removeProperty(vocabulary.swb_resParamValue);
    }

    public SemanticObject getValue()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swb_resParamValue);
         return ret;
    }

    public String getName()
    {
        return getSemanticObject().getProperty(vocabulary.swbxf_resParamName);
    }

    public void setName(String resParamName)
    {
        getSemanticObject().setProperty(vocabulary.swbxf_resParamName, resParamName);
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
