package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class FormViewRefBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public FormViewRefBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public String getFormMode()
    {
        return getSemanticObject().getProperty(vocabulary.formMode);
    }

    public void setFormMode(String formMode)
    {
        getSemanticObject().setProperty(vocabulary.formMode, formMode);
    }

    public void setFormView(org.semanticwb.model.FormView formview)
    {
        getSemanticObject().setObjectProperty(vocabulary.formView, formview.getSemanticObject());
    }

    public void removeFormView()
    {
        getSemanticObject().removeProperty(vocabulary.formView);
    }

    public FormView getFormView()
    {
         FormView ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.formView);
         if(obj!=null)
         {
             ret=(FormView)vocabulary.FormView.newGenericInstance(obj);
         }
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
