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

public class FormViewBase extends GenericObjectBase implements Descriptiveable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public FormViewBase(SemanticObject base)
    {
        super(base);
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

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listCreatePropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.swb_hasCreateProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addCreateProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasCreateProperty, semanticobject);
    }

    public void removeAllCreateProperty()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasCreateProperty);
    }

    public void removeCreateProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasCreateProperty,semanticobject);
    }

    public SemanticObject getCreateProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swb_hasCreateProperty);
         return ret;
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listViewPropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.swb_hasViewProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addViewProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasViewProperty, semanticobject);
    }

    public void removeAllViewProperty()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasViewProperty);
    }

    public void removeViewProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasViewProperty,semanticobject);
    }

    public SemanticObject getViewProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swb_hasViewProperty);
         return ret;
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listEditPropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.swb_hasEditProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addEditProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasEditProperty, semanticobject);
    }

    public void removeAllEditProperty()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasEditProperty);
    }

    public void removeEditProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasEditProperty,semanticobject);
    }

    public SemanticObject getEditProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.swb_hasEditProperty);
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
