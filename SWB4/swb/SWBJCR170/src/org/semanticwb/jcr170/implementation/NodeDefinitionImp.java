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

import java.util.ArrayList;
import java.util.Iterator;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.OnParentVersionAction;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.ChildNodeDefinition;

/**
 *
 * @author victor.lorenzana
 */
public class NodeDefinitionImp implements NodeDefinition
{

    private ArrayList<NodeType> requiredPrimaryTypes = new ArrayList<NodeType>();
    private NodeTypeImp defaultPrimaryType = null;
    private boolean allowsSameNameSiblings = true;
    private NodeTypeImp declaringNodeType;
    private String name = "*";
    private int onParentVerion = OnParentVersionAction.VERSION;
    private boolean autocreated,  mandatory,  isProtected;

    NodeDefinitionImp(SemanticObject object, SessionImp session)
    {
        BaseNode node = new BaseNode(object);

        declaringNodeType = new NodeTypeImp(object.getSemanticClass(), session);
        name = node.getName();
        if (node.getParent() != null)
        {
            BaseNode parent = node.getParent();
            SemanticObject childNodeDefinition = BaseNode.getChildNodeDefinition(parent.getSemanticObject().getSemanticClass(), node.getName());
            if (childNodeDefinition != null)
            {
                String sdefaultPrimaryType = childNodeDefinition.getProperty(ChildNodeDefinition.jcr_defaultPrimaryType);
                if (sdefaultPrimaryType != null)
                {
                    try
                    {
                        SemanticClass classdefaultPrimaryType = parent.getSemanticClass(sdefaultPrimaryType);
                        defaultPrimaryType = new NodeTypeImp(classdefaultPrimaryType, session);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                autocreated = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_autoCreated, false);
                mandatory = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_mandatory, false);
                String sOnParentVersion = childNodeDefinition.getProperty(ChildNodeDefinition.jcr_onParentVersion);
                if (sOnParentVersion != null)
                {
                    onParentVerion = OnParentVersionAction.valueFromName(sOnParentVersion);
                }
                isProtected = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_protected, false);
                allowsSameNameSiblings = childNodeDefinition.getBooleanProperty(ChildNodeDefinition.jcr_sameNameSiblings, false);
                Iterator<SemanticLiteral> values = childNodeDefinition.getSemanticClass().listRequiredProperties(ChildNodeDefinition.jcr_requiredPrimaryTypes);

                while (values.hasNext())
                {
                    String value = values.next().getString();
                    try
                    {
                        SemanticClass clazz = node.getSemanticClass(value);
                        requiredPrimaryTypes.add(new NodeTypeImp(clazz, session));
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
    }

    public NodeType[] getRequiredPrimaryTypes()
    {
        return requiredPrimaryTypes.toArray(new NodeTypeImp[requiredPrimaryTypes.size()]);
    }

    public NodeType getDefaultPrimaryType()
    {
        return defaultPrimaryType;
    }

    public boolean allowsSameNameSiblings()
    {
        return allowsSameNameSiblings;
    }

    public NodeType getDeclaringNodeType()
    {
        return declaringNodeType;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return name;
    }

    public boolean isAutoCreated()
    {
        return autocreated;
    }

    public boolean isMandatory()
    {
        return mandatory;
    }

    public int getOnParentVersion()
    {
        return onParentVerion;
    }

    public boolean isProtected()
    {
        return isProtected;
    }
}
