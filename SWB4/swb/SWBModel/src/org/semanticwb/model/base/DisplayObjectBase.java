package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DisplayObjectBase extends GenericObjectBase implements Sortable
{
    SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public DisplayObjectBase(SemanticObject base)
    {
        super(base);
    }

    public int getIndex()
    {
        return getSemanticObject().getIntProperty(vocabulary.index);
    }

    public void setIndex(int index)
    {
        getSemanticObject().setLongProperty(vocabulary.index, index);
    }

    public GenericIterator<org.semanticwb.model.ObjectAction> listObjectActions()
    {
        return new GenericIterator<org.semanticwb.model.ObjectAction>(org.semanticwb.model.ObjectAction.class, getSemanticObject().listObjectProperties(vocabulary.hasObjectAction));    }

    public void addObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasObjectAction, objectaction.getSemanticObject());
    }

    public void removeAllObjectAction()
    {
        getSemanticObject().removeProperty(vocabulary.hasObjectAction);
    }

    public void removeObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasObjectAction,objectaction.getSemanticObject());
    }

    public ObjectAction getObjectAction()
    {
         ObjectAction ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.hasObjectAction);
         if(obj!=null)
         {
             ret=(ObjectAction)vocabulary.swb_ObjectAction.newGenericInstance(obj);
         }
         return ret;
    }

    public SemanticIterator<org.semanticwb.platform.SemanticObject> listBehaviors()
    {
        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary.hasBehavior.getRDFProperty());
        return new SemanticIterator<org.semanticwb.platform.SemanticObject>(stit);
    }

    public void addBehavior(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().addObjectProperty(vocabulary.hasBehavior, semanticobject);
    }

    public void removeAllBehavior()
    {
        getSemanticObject().removeProperty(vocabulary.hasBehavior);
    }

    public void removeBehavior(org.semanticwb.platform.SemanticObject semanticobject)
    {
        getSemanticObject().removeObjectProperty(vocabulary.hasBehavior,semanticobject);
    }

    public SemanticObject getBehavior()
    {
         SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(vocabulary.hasBehavior);
         return ret;
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }
}
