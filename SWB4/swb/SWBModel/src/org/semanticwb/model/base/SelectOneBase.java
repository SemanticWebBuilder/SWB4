package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class SelectOneBase extends SWBFormElement 
{

    public SelectOneBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isBlankSuport()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_selectOneBlankSuport);
    }

    public void setBlankSuport(boolean selectOneBlankSuport)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_selectOneBlankSuport, selectOneBlankSuport);
    }
}
