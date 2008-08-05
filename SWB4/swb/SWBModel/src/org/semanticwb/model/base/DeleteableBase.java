package org.semanticwb.model.base;

import java.util.Date;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import org.semanticwb.platform.SemanticIterator;

public class DeleteableBase extends SemanticObject 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DeleteableBase(com.hp.hpl.jena.rdf.model.Resource res)
    {
        super(res);
    }

    public boolean isDeleted()
    {
        return getBooleanProperty(vocabulary.deleted);
    }

    public void setDeleted(boolean deleted)
    {
        setBooleanProperty(vocabulary.deleted, deleted);
    }
}
