package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class ReferenceBase extends SWBClass implements Activeable
{

    public ReferenceBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.swb_active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.swb_active, active);
    }
}
