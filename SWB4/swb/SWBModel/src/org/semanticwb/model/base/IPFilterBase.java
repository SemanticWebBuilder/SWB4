package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class IPFilterBase extends GenericObjectBase implements Valueable,Statusable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public IPFilterBase(SemanticObject base)
    {
        super(base);
    }

    public String getValue()
    {
        return getSemanticObject().getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        getSemanticObject().setProperty(vocabulary.value, value);
    }

    public int getStatus()
    {
        return getSemanticObject().getIntProperty(vocabulary.status);
    }

    public void setStatus(int status)
    {
        getSemanticObject().setLongProperty(vocabulary.status, status);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
