package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class ipFilterBase extends SemanticObject implements Statusable,WebSiteable,Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public ipFilterBase(com.hp.hpl.jena.rdf.model.Resource res)
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

    public int getStatus()
    {
        return getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        setLongProperty(vocabulary.status, status);
    }
}
