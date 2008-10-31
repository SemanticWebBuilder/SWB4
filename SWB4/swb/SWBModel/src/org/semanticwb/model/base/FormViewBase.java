package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class FormViewBase extends GenericObjectBase implements Descriptiveable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public FormViewBase(SemanticObject base)
    {
        super(base);
    }

    public String getTitle()
    {
        return getSemanticObject().getProperty(vocabulary.title);
    }

    public void setTitle(String title)
    {
        getSemanticObject().setProperty(vocabulary.title, title);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.title, null, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(vocabulary.title, title, lang);
    }

    public String getDescription()
    {
        return getSemanticObject().getProperty(vocabulary.description);
    }

    public void setDescription(String description)
    {
        getSemanticObject().setProperty(vocabulary.description, description);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(vocabulary.description, null, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(vocabulary.description, description, lang);
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listCreatePropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasCreateProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(org.semanticwb.platform.SemanticObject.class, stit);
    }

    public void addCreateProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasCreateProperty, semanticobject);
    }

    public void removeAllCreateProperty()
    {
        getSemanticObject().removeProperty(vocabulary.hasCreateProperty);
    }

    public void removeCreateProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasCreateProperty,semanticobject);
    }

    public SemanticObject getCreateProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.hasCreateProperty);
         return ret;
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listViewPropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasViewProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(org.semanticwb.platform.SemanticObject.class, stit);
    }

    public void addViewProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasViewProperty, semanticobject);
    }

    public void removeAllViewProperty()
    {
        getSemanticObject().removeProperty(vocabulary.hasViewProperty);
    }

    public void removeViewProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasViewProperty,semanticobject);
    }

    public SemanticObject getViewProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.hasViewProperty);
         return ret;
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listEditPropertys()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasEditProperty.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(org.semanticwb.platform.SemanticObject.class, stit);
    }

    public void addEditProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasEditProperty, semanticobject);
    }

    public void removeAllEditProperty()
    {
        getSemanticObject().removeProperty(vocabulary.hasEditProperty);
    }

    public void removeEditProperty(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasEditProperty,semanticobject);
    }

    public SemanticObject getEditProperty()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.hasEditProperty);
         return ret;
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
