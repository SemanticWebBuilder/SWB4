package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class PermissionBase extends SemanticObject implements WebSiteable
{
    public PermissionBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }
}
