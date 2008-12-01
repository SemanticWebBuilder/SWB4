package org.semanticwb.model.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class DisplayObjectBase extends GenericObjectBase implements Sortable
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

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
        return new GenericIterator<org.semanticwb.model.ObjectAction>(org.semanticwb.model.ObjectAction.class, getSemanticObject().listObjectProperties(vocabulary.swb_hasObjectAction));    }

    public void addObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().addObjectProperty(vocabulary.swb_hasObjectAction, objectaction.getSemanticObject());
    }

    public void removeAllObjectAction()
    {
        getSemanticObject().removeProperty(vocabulary.swb_hasObjectAction);
    }

    public void removeObjectAction(org.semanticwb.model.ObjectAction objectaction)
    {
        getSemanticObject().removeObjectProperty(vocabulary.swb_hasObjectAction,objectaction.getSemanticObject());
    }

    public ObjectAction getObjectAction()
    {
         ObjectAction ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasObjectAction);
         if(obj!=null)
         {
             ret=(ObjectAction)vocabulary.swbxf_ObjectAction.newGenericInstance(obj);
         }
         return ret;
    }

    public GenericIterator<org.semanticwb.model.ObjectBehavior> listBehaviors()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.swb_hasBehavior.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.model.ObjectBehavior>(org.semanticwb.model.ObjectBehavior.class, stit,true);
    }

    public ObjectBehavior getBehavior()
    {
         ObjectBehavior ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swb_hasBehavior);
         if(obj!=null)
         {
             ret=(ObjectBehavior)vocabulary.swbxf_ObjectBehavior.newGenericInstance(obj);
         }
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
