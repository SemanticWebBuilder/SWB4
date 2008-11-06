package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class TextAreaBase extends FormElementBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public TextAreaBase(SemanticObject base)
    {
        super(base);
    }

    public String getRows()
    {
        return getSemanticObject().getProperty(vocabulary.frmTextAreaRows);
    }

    public void setRows(String frmTextAreaRows)
    {
        getSemanticObject().setProperty(vocabulary.frmTextAreaRows, frmTextAreaRows);
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
