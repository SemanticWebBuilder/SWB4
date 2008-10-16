package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DisplayObjectBase extends GenericObjectBase 
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DisplayObjectBase(SemanticObject base)
    {
        super(base);
    }

    public String getPropGroup()
    {
        return getSemanticObject().getProperty(vocabulary.propGroup);
    }

    public void setPropGroup(String propGroup)
    {
        getSemanticObject().setProperty(vocabulary.propGroup, propGroup);
    }

    public int getPropIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.propIndex);
    }

    public void setPropIndex(int propIndex)
    {
        getSemanticObject().setLongProperty(vocabulary.propIndex, propIndex);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());
        return new GenericIterator((SemanticClass)null, stit,true);
    }
}
