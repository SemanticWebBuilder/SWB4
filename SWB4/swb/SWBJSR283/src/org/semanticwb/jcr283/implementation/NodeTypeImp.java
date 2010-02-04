/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr283.implementation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.jcr.NamespaceRegistry;
import javax.jcr.Property;
import javax.jcr.Value;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.jcr.nodetype.PropertyDefinition;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr283.repository.model.Base;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

/**
 *
 * @author victor.lorenzana
 */
public class NodeTypeImp implements NodeType
{

    private static final SemanticProperty childNodeDefinitionProperty = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(NamespaceRegistry.NAMESPACE_JCR + "#childNodeDefinition");
    private static final SemanticProperty propertyDefinition = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(NamespaceRegistry.NAMESPACE_JCR + "#propertyDefinition");
    private static final String uriDataTypePropertyDefiniton = "http://www.jcp.org/jcr/nt/1.0#DataTypePropertyDefinition";
    private static final String uriObjectTypePropertyDefiniton = "http://www.jcp.org/jcr/nt/1.0#ObjectPropertyDefinition";
    public static final SemanticClass dataClazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uriDataTypePropertyDefiniton);
    public static final SemanticClass objectClazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uriObjectTypePropertyDefiniton);
    private static final String ALL = "*";
    private static final String ISQUERYABLE = NamespaceRegistry.NAMESPACE_JCR + "#isQueryable";
    private static Logger log = SWBUtils.getLogger(NodeTypeImp.class);
    private final SemanticClass clazz;
    private final HashMap<String, PropertyDefinitionImp> propertyDefinitions = new HashMap<String, PropertyDefinitionImp>();
    private final HashMap<String, NodeDefinitionImp> childnodeDefinitions = new HashMap<String, NodeDefinitionImp>();
    private final HashSet<NodeTypeImp> supertypes = new HashSet<NodeTypeImp>();
    private final HashSet<NodeTypeImp> aditionalSuperTypes = new HashSet<NodeTypeImp>();
    private final HashSet<NodeTypeImp> subtypes = new HashSet<NodeTypeImp>();
    private final boolean isMixin;
    private final boolean isQueryable;
    private final boolean isAbstract;
    private final boolean hasOrderableChildNodes;
    private final String primaryItemName;
    

    public NodeTypeImp(SemanticClass clazz)
    {
        this(clazz, new HashSet<NodeTypeImp>());
    }

    public NodeTypeImp(SemanticClass clazz, HashSet<NodeTypeImp> aditionalSuperTypes)
    {
        if (aditionalSuperTypes != null)
        {
            aditionalSuperTypes.addAll(aditionalSuperTypes);
        }        
        this.clazz = clazz;
        SemanticProperty prop = getSemanticProperty(Property.JCR_IS_MIXIN);
        SemanticLiteral value = clazz.getRequiredProperty(prop);
        if (value == null)
        {
            isMixin = false;
        }
        else
        {
            isMixin = value.getBoolean();
        }
        prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(ISQUERYABLE);
        value = clazz.getRequiredProperty(prop);
        if (value == null)
        {
            isQueryable = false;
        }
        else
        {
            isQueryable = value.getBoolean();
        }
        prop = getSemanticProperty(Property.JCR_IS_ABSTRACT);
        value = clazz.getRequiredProperty(prop);
        if (value == null)
        {
            isAbstract = false;
        }
        else
        {
            isAbstract = value.getBoolean();
        }
        prop = getSemanticProperty(Property.JCR_HAS_ORDERABLE_CHILD_NODES);
        value = clazz.getRequiredProperty(prop);
        if (value == null)
        {
            hasOrderableChildNodes = false;
        }
        else
        {
            hasOrderableChildNodes = value.getBoolean();
        }

        prop = getSemanticProperty(Property.JCR_PRIMARY_ITEM_NAME);
        value = clazz.getRequiredProperty(prop);
        if (value == null)
        {
            primaryItemName = null;
        }
        else
        {
            primaryItemName = value.getString();
        }

    }

    public static SemanticProperty getSemanticProperty(String name)
    {
        name = name.replace("{", "");
        name = name.replace("}", "#");
        return SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(name);
    }

    public void loadSuperTypes()
    {
        Iterator<SemanticClass> classes = clazz.listSuperClasses();
        while (classes.hasNext())
        {
            SemanticClass superClazz = classes.next();
            if (superClazz.isSubClass(Base.sclass))
            {
                NodeTypeImp superNodeType = NodeTypeManagerImp.loadNodeType(superClazz);
                log.trace("loading superType " + superNodeType.getName() + " for nodeType " + this.getName());
                supertypes.add(superNodeType);
            }
        }

    }

    private void loadPropertyDefinitions(SemanticClass oclazz)
    {
        Iterator<SemanticObject> values = oclazz.listObjectRequiredProperties(propertyDefinition);
        while (values.hasNext())
        {
            SemanticObject propertyDefinitionObj = values.next();
            PropertyDefinitionImp pd = new PropertyDefinitionImp(propertyDefinitionObj, this);
            if (!propertyDefinitions.containsKey(pd.getName()))
            {
                //log.trace("loading propertyDefinition " + pd.getName() + " for node " + this.getName());
                propertyDefinitions.put(pd.getName(), pd);
            }
        }
        try
        {
            String className = clazz.getClassName();
            Class clazzJava = Class.forName(className);
            for (Field field : clazzJava.getFields())
            {
                if (field.getType().equals(SemanticProperty.class) && Modifier.isPublic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()))
                {
                    try
                    {
                        Object obj = field.get(null);
                        if (obj != null)
                        {
                            SemanticProperty semanticProperty = (SemanticProperty) obj;
                            SemanticClass clazzProperty = semanticProperty.getSemanticObject().getSemanticClass();
                            if (semanticProperty.getDomainClass() == null)
                            {
                                if (clazzProperty.equals(dataClazz) || clazzProperty.equals(objectClazz))
                                {
                                    NodeTypeImp nodeType = NodeTypeManagerImp.loadNodeType(clazz);
                                    PropertyDefinitionImp pd = new PropertyDefinitionImp(semanticProperty, nodeType);
                                    if (!propertyDefinitions.containsKey(pd.getName()))
                                    {
                                        //log.trace("loading propertyDefinition " + pd.getName() + " for node " + this.getName());
                                        propertyDefinitions.put(pd.getName(), pd);
                                    }
                                }
                            }
                            else
                            {
                                if (clazzProperty.equals(dataClazz) || clazzProperty.equals(objectClazz))
                                {
                                    PropertyDefinitionImp pd = new PropertyDefinitionImp(semanticProperty);
                                    if (!propertyDefinitions.containsKey(pd.getName()))
                                    {
                                        //log.trace("loading propertyDefinition " + pd.getName() + " for node " + this.getName());
                                        propertyDefinitions.put(pd.getName(), pd);
                                    }
                                }
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        log.error(e);
                    }
                }
            }
        }
        catch (ClassNotFoundException cnfe)
        {
            log.error(cnfe);
        }
        Iterator<SemanticProperty> props = oclazz.listProperties();
        while (props.hasNext())
        {
            SemanticProperty semanticProperty = props.next();
            SemanticClass clazzProperty = semanticProperty.getSemanticObject().getSemanticClass();
            if (clazzProperty.equals(dataClazz) || clazzProperty.equals(objectClazz))
            {

                if (!propertyDefinitions.containsKey(semanticProperty.getName() + ":" + semanticProperty.getName()))
                {
                    PropertyDefinitionImp pd = new PropertyDefinitionImp(semanticProperty);
                    propertyDefinitions.put(pd.getName(), pd);
                }
            }
        }
    }

    public void loadPropertyDefinitions()
    {
        loadPropertyDefinitions(clazz);
        Iterator<SemanticClass> suprclasses = clazz.listSuperClasses();
        while (suprclasses.hasNext())
        {
            SemanticClass superClazz = suprclasses.next();
            loadPropertyDefinitions(superClazz);
        }
    }

    public void loadChildNodeDefinitions(SemanticClass oclazz)
    {
        Iterator<SemanticObject> values = oclazz.listObjectRequiredProperties(childNodeDefinitionProperty);
        while (values.hasNext())
        {
            SemanticObject childDefinition = values.next();
            SemanticProperty jcr_name = NodeTypeImp.getSemanticProperty(Property.JCR_NAME);
            String name = null;
            SemanticLiteral value = childDefinition.getLiteralProperty(jcr_name);
            if (value != null)
            {
                name = value.getString();
            }
            else
            {
                name = childDefinition.getPrefix() + ":" + childDefinition.getRDFName();
            }
            if (!childnodeDefinitions.containsKey(name))
            {
                NodeDefinitionImp nodeDefinitionImp = new NodeDefinitionImp(childDefinition, this);
                log.trace("loading nodeDefinition " + name + " for node " + this.getName());
                childnodeDefinitions.put(nodeDefinitionImp.getName(), nodeDefinitionImp);
            }
        }
    }

    public void loadChildNodeDefinitions()
    {

        loadChildNodeDefinitions(clazz);
        Iterator<SemanticClass> superClasses = clazz.listSuperClasses();
        while (superClasses.hasNext())
        {
            SemanticClass superClazz = superClasses.next();
            loadChildNodeDefinitions(superClazz);

        }

    }

    public NodeType[] getSupertypes()
    {
        HashSet<NodeTypeImp> getSupertypes = new HashSet<NodeTypeImp>();
        getSupertypes.addAll(this.supertypes);
        getSupertypes.addAll(this.aditionalSuperTypes);
        return getSupertypes.toArray(new NodeType[getSupertypes.size()]);
    }

    public void loadSubTypes()
    {

        Iterator<SemanticClass> classes = SWBPlatform.getSemanticMgr().getVocabulary().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass oclazz = classes.next();
            if (oclazz.isSubClass(clazz))
            {
                NodeTypeImp nodeType = NodeTypeManagerImp.loadNodeType(clazz);
                log.trace("loading subtype " + nodeType.getName() + " for nodeType " + this.getName());
                subtypes.add(nodeType);
            }
        }
    }

    public SemanticClass getSemanticClass()
    {
        return clazz;
    }

    public NodeType[] getDeclaredSupertypes()
    {
        return this.supertypes.toArray(new NodeType[this.supertypes.size()]);
    }

    public NodeTypeIterator getSubtypes()
    {
        return new NodeTypeIteratorImp(subtypes);
    }

    public NodeTypeIterator getDeclaredSubtypes()
    {
        return new NodeTypeIteratorImp(subtypes);
    }

    public boolean isNodeType(String nodeTypeName)
    {
        try
        {
            NodeTypeImp nodeType = new NodeTypeManagerImp().getNodeTypeImp(nodeTypeName);
            if (nodeType.getSemanticClass().equals(clazz) || nodeType.getSemanticClass().isSuperClass(clazz))
            {
                return true;
            }
            if (nodeType == null)
            {
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }
        return false;
    }

    public PropertyDefinition[] getPropertyDefinitions()
    {
        return propertyDefinitions.values().toArray(new PropertyDefinition[propertyDefinitions.size()]);
    }

    public PropertyDefinitionImp[] getPropertyDefinitionsImp()
    {
        return propertyDefinitions.values().toArray(new PropertyDefinitionImp[propertyDefinitions.size()]);
    }

    public NodeDefinition[] getChildNodeDefinitions()
    {
        return childnodeDefinitions.values().toArray(new NodeDefinition[childnodeDefinitions.size()]);
    }

    public NodeDefinitionImp[] getChildNodeDefinitionsImp()
    {
        return childnodeDefinitions.values().toArray(new NodeDefinitionImp[childnodeDefinitions.size()]);
    }

    public boolean canSetProperty(String propertyName, Value value)
    {
        return canSetProperty(propertyName, new Value[]
                {
                    value
                });
    }

    public boolean canSetProperty(String propertyName, Value[] values)
    {
        PropertyDefinitionImp definition = null;
        for (PropertyDefinitionImp propertyDefinitionImp : propertyDefinitions.values())
        {
            if (propertyDefinitionImp.getName().equals(propertyName))
            {
                definition = propertyDefinitionImp;
            }
        }
        if (definition == null)
        {
            for (PropertyDefinitionImp propertyDefinitionImp : propertyDefinitions.values())
            {
                if (propertyDefinitionImp.getName().equals(ALL))
                {
                    definition = propertyDefinitionImp;
                }
            }
        }
        if (definition == null)
        {
            return false;
        }
        if (values.length > 1 && !definition.isMultiple())
        {
            return false;
        }
        for (Value value : values)
        {
            if (!isCompatibleValue(definition.getRequiredType(), value))
            {
                return false;
            }

        }
        return true;
    }

    private boolean isCompatibleValue(int requiredType, Value value)
    {
        boolean isCompatibleValue = false;
        if (requiredType == value.getType())
        {
            return true;
        }
        //TODO: revisar con que otros datos es compatible
        try
        {
            PropertyImp.transformValue(value, requiredType);
            isCompatibleValue = true;
        }
        catch (Exception e)
        {
            isCompatibleValue = false;
        }
        return isCompatibleValue;
    }

    public boolean canAddChildNode(String childNodeName)
    {
        return canAddChildNode(childNodeName, null);
    }

    public boolean canAddChildNode(String childNodeName, String nodeTypeName)
    {
        NodeDefinitionImp childDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : this.childnodeDefinitions.values())
        {
            if (childNodeDefinition.getName().equals(childNodeName))
            {
                if (nodeTypeName == null)
                {
                    nodeTypeName = childNodeDefinition.getDeclaringNodeType().getName();
                }
                childDefinition = childNodeDefinition;
                break;
            }
        }
        if (childDefinition == null)
        {
            for (NodeDefinitionImp childNodeDefinition : this.childnodeDefinitions.values())
            {
                if (childNodeDefinition.getName().equals(ALL))
                {
                    if (nodeTypeName == null)
                    {
                        nodeTypeName = childNodeDefinition.getDeclaringNodeType().getName();
                    }
                    childDefinition = childNodeDefinition;
                    break;

                }
            }
        }
        if (childDefinition == null)
        {
            return false;
        }
        try
        {
            NodeType nodeType = SWBRepository.getNodeTypeManagerImp().getNodeType(nodeTypeName);
            boolean isConformToRequired = false;
            for (NodeType required : childDefinition.getRequiredPrimaryTypes())
            {
                if (required.equals(nodeType) || nodeType.isNodeType(required.getName()))
                {
                    isConformToRequired = true;
                    break;
                }
            }
            return isConformToRequired;
        }
        catch (Exception e)
        {
            log.debug(e);
            return false;
        }

    }

    @Override
    public String toString()
    {
        return this.getName();
    }

    @Deprecated
    public boolean canRemoveItem(String itemName)
    {
        ItemDefinitionImp itemDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : this.childnodeDefinitions.values())
        {
            if (childNodeDefinition.getName().equals(itemName))
            {
                itemDefinition = childNodeDefinition;
                break;
            }
        }
        for (PropertyDefinitionImp propertyDefinitionImp : propertyDefinitions.values())
        {
            if (propertyDefinitionImp.getName().equals(itemName))
            {
                itemDefinition = propertyDefinitionImp;
                break;
            }
        }
        if (itemDefinition == null)
        {
            return true;
        }
        if (itemDefinition.isProtected())
        {
            return false;
        }
        return true;
    }

    public boolean canRemoveNode(String nodeName)
    {
        ItemDefinitionImp itemDefinition = null;
        for (NodeDefinitionImp childNodeDefinition : this.childnodeDefinitions.values())
        {
            if (childNodeDefinition.getName().equals(nodeName))
            {
                itemDefinition = childNodeDefinition;
                break;
            }
        }
        for (NodeDefinitionImp childNodeDefinition : this.childnodeDefinitions.values())
        {
            if (childNodeDefinition.getName().equals(ALL))
            {
                itemDefinition = childNodeDefinition;
                break;
            }
        }
        if (itemDefinition == null)
        {
            return true;
        }
        if (itemDefinition.isProtected())
        {
            return false;
        }
        return true;
    }

    public boolean canRemoveProperty(String propertyName)
    {
        for (PropertyDefinitionImp propertyDefinitionImp : propertyDefinitions.values())
        {
            if (propertyDefinitionImp.getName().equals(propertyName))
            {
                if (propertyDefinitionImp.isProtected())
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        for (PropertyDefinitionImp propertyDefinitionImp : propertyDefinitions.values())
        {
            if (propertyDefinitionImp.getName().equals(ALL))
            {
                if (propertyDefinitionImp.isProtected())
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
        return true;
    }

    public String getName()
    {
        return clazz.getPrefix() + ":" + clazz.getName();
    }

    public String[] getDeclaredSupertypeNames()
    {
        HashSet<String> names = new HashSet<String>();
        NodeTypeIterator nodeTypeIteratorImp = this.getDeclaredSubtypes();
        while (nodeTypeIteratorImp.hasNext())
        {
            names.add(nodeTypeIteratorImp.nextNodeType().getName());
        }
        return names.toArray(new String[names.size()]);
    }

    public boolean isAbstract()
    {
        return isAbstract;
    }

    public boolean isMixin()
    {
        return isMixin;
    }

    public boolean hasOrderableChildNodes()
    {
        return hasOrderableChildNodes;
    }

    public boolean isQueryable()
    {
        return isQueryable;
    }

    public String getPrimaryItemName()
    {
        return primaryItemName;
    }

    public PropertyDefinition[] getDeclaredPropertyDefinitions()
    {
        // TODO: revisar
        return this.getPropertyDefinitions();
    }

    public NodeDefinition[] getDeclaredChildNodeDefinitions()
    {
        // TODO: revisar
        return this.getChildNodeDefinitions();
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
        final NodeTypeImp other = (NodeTypeImp) obj;
        if ((this.clazz == null || !this.clazz.equals(other.clazz)))
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 89 * hash + (this.clazz != null ? this.clazz.hashCode() : 0);
        return hash;
    }
}
