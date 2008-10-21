package org.semanticwb.model;

import org.semanticwb.model.base.*;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;

public class DisplayObject extends DisplayObjectBase 
{
    public DisplayObject(SemanticObject base)
    {
        super(base);
    }
    
    public FormElement getViewElement()
    {
        FormElement ret=null;
        SemanticObject obj=this.getSemanticObject().getObjectProperty(SWBContext.getVocabulary().viewElement);
        if(obj!=null)
        {
            SemanticClass cls=obj.getSemanticClass();
            ret=(FormElement)cls.newGenericInstance(obj);
        }
        return ret;
    }
    
    public FormElement getEditElement()
    {
        FormElement ret=null;
        SemanticObject obj=this.getSemanticObject().getObjectProperty(SWBContext.getVocabulary().editElement);
        if(obj!=null)
        {
            SemanticClass cls=obj.getSemanticClass();
            ret=(FormElement)cls.newGenericInstance(obj);
        }
        return ret;
    }   
    
    public FormElement getCreateElement()
    {
        FormElement ret=null;
        SemanticObject obj=this.getSemanticObject().getObjectProperty(SWBContext.getVocabulary().createElement);
        if(obj!=null)
        {
            SemanticClass cls=obj.getSemanticClass();
            ret=(FormElement)cls.newGenericInstance(obj);
        }
        return ret;
    }       
}
