package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class TextAreaBase extends SWBFormElement 
{

    public TextAreaBase(SemanticObject base)
    {
        super(base);
    }

    public int getRows()
    {
        return getSemanticObject().getIntProperty(vocabulary.swbxf_textAreaRows);
    }

    public void setRows(int textAreaRows)
    {
        getSemanticObject().setLongProperty(vocabulary.swbxf_textAreaRows, textAreaRows);
    }

    public int getCols()
    {
        return getSemanticObject().getIntProperty(vocabulary.swbxf_textAreaCols);
    }

    public void setCols(int textAreaCols)
    {
        getSemanticObject().setLongProperty(vocabulary.swbxf_textAreaCols, textAreaCols);
    }
}
