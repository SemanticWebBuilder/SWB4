package org.semanticwb.opensocial.model.data;

import java.util.Iterator;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;


public class Person extends org.semanticwb.opensocial.model.data.base.PersonBase 
{
    public Person(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public Object getValueFromField(String field)
    {
        if(field.equals("id"))
        {
            return this.getId();
        }
        Iterator<SemanticProperty> properties=this.getSemanticObject().getSemanticClass().listProperties();
        while(properties.hasNext())
        {
            SemanticProperty prop=properties.next();
            if(prop.getName().equalsIgnoreCase(field))
            {
                if(prop.isDataTypeProperty())
                {
                    return this.getSemanticObject().getProperty(prop);
                }
                else
                {
                    SemanticObject value=this.getSemanticObject().getObjectProperty(prop);
                    GenericObject go=value.createGenericInstance();
                    if(go instanceof JSONConvertible)
                    {
                        return ((JSONConvertible)go).toJSONObject();
                    }
                }
            }
        }
        return null;
    }
}
