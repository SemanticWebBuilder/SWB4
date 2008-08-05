package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ValueableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ValueableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public String getValue()
    {
        return getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        setProperty(vocabulary.value, value);
    }
}
