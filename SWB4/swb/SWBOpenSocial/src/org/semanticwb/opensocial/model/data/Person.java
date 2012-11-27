/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
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
