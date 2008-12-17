package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;
import org.semanticwb.model.GenericIterator;

public class DisplayObjectBase extends SWBClass implements Sortable
{

    public DisplayObjectBase(SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.swb_index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(vocabulary.swb_index, index);
    }

    public GenericIterator<org.semanticwb.model.ObjectAction> listObjectActions()
    {
        return new GenericIterator<org.semanticwb.model.ObjectAction>(org.semanticwb.model.ObjectAction.class, getSemanticObject().listObjectProperties(vocabulary.swbxf_hasObjectAction));    }

    public void addObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().addObjectProperty(vocabulary.swbxf_hasObjectAction, objectaction.getSemanticObject());
    }

    public void removeAllObjectAction()
    {
        getSemanticObject().removeProperty(vocabulary.swbxf_hasObjectAction);
    }

    public void removeObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swbxf_hasObjectAction,objectaction.getSemanticObject());
    }

    public ObjectAction getObjectAction()
    {
         ObjectAction ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_hasObjectAction);
         if(obj!=null)
         {
             ret=(ObjectAction)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.ObjectBehavior> listBehaviors()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.swbxf_hasBehavior.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.ObjectBehavior>(org.semanticwb.model.ObjectBehavior.class, stit,true);
    }

    public ObjectBehavior getBehavior()
    {
         ObjectBehavior ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbxf_hasBehavior);
         if(obj!=null)
         {
             ret=(ObjectBehavior)obj.getSemanticClass().newGenericInstance(obj);
         }
         return ret;
    }
}
