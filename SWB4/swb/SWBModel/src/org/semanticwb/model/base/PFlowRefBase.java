package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PFlowRefBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PFlowRefBase(SemanticObject base)
    {
        super(base);
    }
}
