package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

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
