package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class ReferenceBase extends SemanticObject implements Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ReferenceBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }
}
