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

    public boolean isGlobalScope()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_so_globalScope);
    }

    public void setGlobalScope(boolean so_globalScope)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_so_globalScope, so_globalScope);
    }

    public boolean isBlankSuport()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swbxf_so_blankSuport);
    }

    public void setBlankSuport(boolean so_blankSuport)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swbxf_so_blankSuport, so_blankSuport);
    }
}
