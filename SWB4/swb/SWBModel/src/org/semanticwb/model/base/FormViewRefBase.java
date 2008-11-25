package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class FormViewRefBase extends Reference 
{

    public FormViewRefBase(SemanticObject base)
    {
        super(base);
    }

    public String getFormMode()
    {
        return getSemanticObject().getProperty(vocabulary.swb_formMode);
    }

    public void setFormMode(String formMode)
    {
        getSemanticObject().setProperty(vocabulary.swb_formMode, formMode);
    }

    public void setFormView(org.semanticwb.model.FormView formview)
    {
        getSemanticObject().setObjectProperty(vocabulary.swb_formView, formview.getSemanticObject());
    }

    public void removeFormView()
    {
        getSemanticObject().removeProperty(vocabulary.swb_formView);
    }

    public FormView getFormView()
    {
         FormView ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_formView);
         if(obj!=null)
         {
             ret=(FormView)vocabulary.swbxf_FormView.newGenericInstance(obj);
         }
         return ret;
    }
}
