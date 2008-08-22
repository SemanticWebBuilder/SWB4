package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class ReferenceBase extends GenericObjectBase implements Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ReferenceBase(SemanticObject base)
    {
        super(base);
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }
}
