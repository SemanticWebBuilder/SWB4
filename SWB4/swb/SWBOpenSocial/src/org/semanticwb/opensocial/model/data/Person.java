package org.semanticwb.opensocial.model.data;

import java.util.Iterator;
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericObject;
import org.semanticwb.opensocial.util.Scape;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;


public class Person extends org.semanticwb.opensocial.model.data.base.PersonBase 
{
    public Person(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    public Object getValueFromField(String field,Scape scape)
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
                    String value=this.getSemanticObject().getProperty(prop);
                    return scape.scape(value);
                }
                else
                {
                    SemanticObject value=this.getSemanticObject().getObjectProperty(prop);
                    if(value==null)
                    {
                        SemanticProperty default_value= SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_PROP_DEFAULTVALUE);
                        SemanticLiteral literal=this.getSemanticObject().getSemanticClass().getRequiredProperty(default_value);
                        if(literal==null || literal.getString()==null)
                        {
                            return "";
                        }
                        else
                        {
                            return literal.getString();
                        }
                    }
                    else
                    {
                        GenericObject go=value.createGenericInstance();
                        if(go instanceof JSONConvertible)
                        {
                            return ((JSONConvertible)go).toJSONObject(scape);
                        }
                    }
                }
            }
        }
        return null;
    }
}
