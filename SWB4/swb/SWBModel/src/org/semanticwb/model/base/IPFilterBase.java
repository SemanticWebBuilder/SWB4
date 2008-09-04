package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class IPFilterBase extends GenericObjectBase implements Valueable,Activeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public IPFilterBase(SemanticObject base)
    {
        super(base);
    }

    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(vocabulary.active);
    }

    public void setActive(boolean active)
    {
        getSemanticObject().setBooleanProperty(vocabulary.active, active);
    }

    public String getValue()
    {
        return getSemanticObject().getProperty(vocabulary.value);
    }

    public void setValue(String value)
    {
        getSemanticObject().setProperty(vocabulary.value, value);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
