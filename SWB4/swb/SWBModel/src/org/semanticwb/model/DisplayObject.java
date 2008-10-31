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
    
    public FormElement getFormElementInstance()
    {
        FormElement ret=null;
        SemanticObject obj=getFormElement();
        if(obj!=null)
        {
            SemanticClass cls=obj.getSemanticClass();
            ret=(FormElement)cls.newGenericInstance(obj);
        }
        return ret;
    }
}
