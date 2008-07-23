package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class SWBModelBase extends SemanticObject implements Valueable
{
    public SWBModelBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
    public String getValue()
    {
        return getProperty(Vocabulary.value);
    }
    public void setValue(String value)
    {
        setProperty(Vocabulary.value, value);
    }
}
