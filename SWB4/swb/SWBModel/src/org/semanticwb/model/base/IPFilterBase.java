package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class IPFilterBase extends SemanticObject implements Statusable,Valueable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public IPFilterBase(com.hp.hpl.jena.rdf.model.Resource res)
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
