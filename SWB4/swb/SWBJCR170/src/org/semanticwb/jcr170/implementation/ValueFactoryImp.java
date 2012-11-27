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

import java.io.InputStream;
import java.util.Calendar;
import javax.jcr.Node;
import javax.jcr.PropertyType;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.ValueFormatException;

/**
 *
 * @author victor.lorenzana
 */
public class ValueFactoryImp implements ValueFactory
{

    public Value createValue(String value)
    {
        return new ValueImp(value, PropertyType.STRING);
    }

    public Value createValue(String value, int type) throws ValueFormatException
    {
        return new ValueImp(value,type);
    }

    public Value createValue(long value)
    {
        return new ValueImp(value, PropertyType.LONG);
    }

    public Value createValue(double value)
    {
        return new ValueImp(value, PropertyType.DOUBLE);
    }

    public Value createValue(boolean value)
    {
        return new ValueImp(value, PropertyType.BOOLEAN);
    }

    public Value createValue(Calendar value)
    {
        return new ValueImp(value, PropertyType.DATE);
    }

    public Value createValue(InputStream value)
    {
        return new ValueImp(value, PropertyType.BINARY);
    }

    public Value createValue(Node value) throws RepositoryException
    {
        return new ValueImp(value, PropertyType.REFERENCE);
    }
}
