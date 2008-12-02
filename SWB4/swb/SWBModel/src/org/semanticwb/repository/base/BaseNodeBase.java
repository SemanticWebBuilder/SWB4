package org.semanticwb.repository.base;

import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import org.semanticwb.model.base.GenericObjectBase;
import org.semanticwb.model.SWBVocabulary;
import org.semanticwb.model.SWBContext;
import org.semanticwb.model.GenericObject;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.repository.*;
import com.hp.hpl.jena.rdf.model.*;
import org.semanticwb.*;
import org.semanticwb.platform.*;

public class BaseNodeBase extends GenericObjectBase 
{
    public static SWBVocabulary vocabulary=SWBContext.getVocabulary();

    public BaseNodeBase(SemanticObject base)
    {
        super(base);
    }

    public void setParent(org.semanticwb.repository.BaseNode basenode)
    {
        getSemanticObject().setObjectProperty(vocabulary.swbrep_parentNode, basenode.getSemanticObject());
    }

    public void removeParent()
    {
        getSemanticObject().removeProperty(vocabulary.swbrep_parentNode);
    }

    public BaseNode getParent()
    {
         BaseNode ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbrep_parentNode);
         if(obj!=null)
         {
             ret=(BaseNode)vocabulary.nt_BaseNode.newGenericInstance(obj);
         }
         return ret;
    }

    public String getPrimaryType()
    {
        return getSemanticObject().getProperty(vocabulary.jcr_primaryType);
    }

    public void setPrimaryType(String primaryType)
    {
        getSemanticObject().setProperty(vocabulary.jcr_primaryType, primaryType);
    }

    public GenericIterator<org.semanticwb.repository.BaseNode> listNodes()
    {
        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary.swbrep_hasNodes.getInverse().getRDFProperty(), getSemanticObject().getRDFResource());
        return new GenericIterator<org.semanticwb.repository.BaseNode>(org.semanticwb.repository.BaseNode.class, stit,true);
    }

    public BaseNode getNode()
    {
         BaseNode ret=null;
         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary.swbrep_hasNodes);
         if(obj!=null)
         {
             ret=(BaseNode)vocabulary.nt_BaseNode.newGenericInstance(obj);
         }
         return ret;
    }

    public Iterator<String> listMixinTypes()
    {
        ArrayList<String> values=new ArrayList<String>();
        Iterator<SemanticLiteral> it=getSemanticObject().listLiteralProperties(vocabulary.jcr_mixinTypes);
        while(it.hasNext())
        {
                SemanticLiteral literal=it.next();
                values.add(literal.getString());
        }
        return values.iterator();
    }

    public void addMixinType(String mixintype)
    {
        getSemanticObject().setProperty(vocabulary.jcr_mixinTypes, mixintype);
    }

    public void removeAllMixinType()
    {
        getSemanticObject().removeProperty(vocabulary.jcr_mixinTypes);
    }

    public void removeMixinType(String mixintype)
    {
        getSemanticObject().removeProperty(vocabulary.jcr_mixinTypes,mixintype);
    }

    public String getName()
    {
        return getSemanticObject().getProperty(vocabulary.swbrep_name);
    }

    public void setName(String name)
    {
        getSemanticObject().setProperty(vocabulary.swbrep_name, name);
    }

    public void remove()
    {
        getSemanticObject().remove();
    }

    public Iterator<GenericObject> listRelatedObjects()
    {
        return new GenericIterator((SemanticClass)null, getSemanticObject().listRelatedObjects(),true);
    }

    public Workspace getWorkspace()
    {
        return new Workspace(getSemanticObject().getModel().getModelObject());
    }
}
