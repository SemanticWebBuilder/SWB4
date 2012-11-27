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
package org.semanticwb.jcr170.implementation;

import javax.jcr.PropertyType;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.OnParentVersionAction;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class PropertyDefinitionImp implements PropertyDefinition
{
    static Logger log=SWBUtils.getLogger(PropertyDefinitionImp.class);
    private final String name;
    private int requiredType = PropertyType.UNDEFINED;
    private boolean multiple = true;
    private boolean isProtected = false;
    private boolean mandatory = false,autocreated=false;
    private int onParentVersion=OnParentVersionAction.COPY;
    PropertyDefinitionImp(String name)
    {
        this.name = name;
    }
    
    PropertyDefinitionImp(String name,int requiredType)
    {
        this.name = name;
        this.requiredType=requiredType;
    }
   
    PropertyDefinitionImp(SessionImp session, SemanticProperty property)
    {
        this.name = property.getPrefix() + ":" + property.getName();
        multiple = BaseNode.isMultiple(property);
        int type = PropertyType.UNDEFINED;
        if ( property.isObjectProperty() )
        {
            type = PropertyType.REFERENCE;
        }
        else
        {
            if ( property.isBoolean() )
            {
                type = PropertyType.BOOLEAN;
            }
            else if ( property.isDate() || property.isDateTime() )
            {
                type = PropertyType.DATE;
            }
            else if ( property.isLong() || property.isInt() )
            {
                type = PropertyType.LONG;
            }
            else if ( property.isString() )
            {
                type = PropertyType.STRING;
            }
            else if ( property.isFloat() )
            {
                type = PropertyType.DOUBLE;
            }
            else if ( property.isBinary() )
            {
                type=PropertyType.BINARY;
            }
        }
        this.requiredType = type;
        mandatory=BaseNode.isMandatory(property);
        autocreated=BaseNode.isAutocreated(property);
        String svalue=BaseNode.getOnParentVersion(property);
        if(svalue!=null)
        {
            try
            {
                onParentVersion=OnParentVersionAction.valueFromName(svalue);
            }
            catch(Exception e)
            {
                log.debug("Error to get the OnParentVersionAction for the property "+this.name, e);
            }
        }
        isProtected=BaseNode.isProtected(property);
    }

    public void setRequiredType(int requiredType)
    {
        this.requiredType = requiredType;
    }

    public int getRequiredType()
    {
        return requiredType;
    }

    public void setMultiple(boolean multiple)
    {
        this.multiple = multiple;
    }

    @Override
    public String[] getValueConstraints()
    {
        return null;
    }

    @Override
    public Value[] getDefaultValues()
    {
        return null;
    }

    @Override
    public boolean isMultiple()
    {
        return multiple;
    }

    @Override
    public NodeType getDeclaringNodeType()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isAutoCreated()
    {
        return autocreated;
    }

    @Override
    public boolean isMandatory()
    {
        return mandatory;
    }

    @Override
    public int getOnParentVersion()
    {
        return onParentVersion;
    }

    @Override
    public boolean isProtected()
    {
        return isProtected;
    }
    @Override
    public String toString()
    {
        return name;
    }
   
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final PropertyDefinitionImp other = (PropertyDefinitionImp) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name))
        {
            return false;
        }
        return true;
    }
}
