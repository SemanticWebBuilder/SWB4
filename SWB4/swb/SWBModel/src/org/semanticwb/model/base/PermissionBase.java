package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PermissionBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PermissionBase(SemanticObject base)
    {
        super(base);
    }

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
