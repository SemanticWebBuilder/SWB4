package org.semanticwb.opensocial.model.data;

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericObject;
import org.semanticwb.opensocial.util.Scape;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;


public class Person extends org.semanticwb.opensocial.model.data.base.PersonBase 
{
    private static final Logger log = SWBUtils.getLogger(Person.class);
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
                else if(prop.isObjectProperty())
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

    public JSONObject toJSONObject()
    {
        JSONObject person=new JSONObject();
        try
        {
            person.put("id", this.getId());
        }
        catch(JSONException e)
        {
            log.debug(e);
        }
        return person;
    }
}
