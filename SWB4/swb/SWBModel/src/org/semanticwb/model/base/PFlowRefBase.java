package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class PFlowRefBase extends GenericObjectBase implements Activeable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public PFlowRefBase(SemanticObject base)
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

    public void setPflow(org.semanticwb.model.PFlow pflow)
    {
        getSemanticObject().setObjectProperty(vocabulary.pflow, pflow.getSemanticObject());
    }

    public void removePflow()
    {
        getSemanticObject().removeProperty(vocabulary.pflow);
    }

    public PFlow getPflow()
    {
         PFlow ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.pflow);
         if(obj!=null)
         {
             ret=(PFlow)vocabulary.PFlow.newGenericInstance(obj);
         }
         return ret;
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

    public WebSite getWebSite()
    {
        return new WebSite(getSemanticObject().getModel().getModelObject());
    }
}
