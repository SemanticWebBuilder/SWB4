/**  
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
**/ 
 
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.PropertyType;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.version.VersionException;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public final class PropertyImp implements Property
{

    static Logger log = SWBUtils.getLogger(PropertyImp.class);
    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private boolean isNew = true;
    private boolean isModified = true;
    private static final ValueFactoryImp factory = new ValueFactoryImp();
    private final String name;
    private final PropertyDefinitionImp propertyDefinition;
    private ArrayList<Value> values = new ArrayList<Value>();
    private String path;
    private final SimpleNode parent;
    private final SemanticClass clazz;
    private boolean isNode=false;
    private SessionImp session;

    PropertyImp(SimpleNode parent, SemanticClass clazz, String name, PropertyDefinitionImp propertyDefinition,boolean isNode,SessionImp session)
    {
        if (name == null)
        {
            throw new IllegalArgumentException();
        }
        this.session=session;
        this.name = name;
        this.propertyDefinition = propertyDefinition;
        if(parent.toString().endsWith("/"))
        {
            path = parent.toString()+ name;
        }
        else
        {
            path = parent.toString() + "/" + name;
        }
        this.parent = parent;
        this.clazz = clazz;
        this.isNode=isNode;
    }

    public SemanticClass getSemanticClass()
    {
        return clazz;
    }

    public void setValue(Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] valuesToInsert =
        {
            value
        };
        setValue(valuesToInsert);

    }

    public void setValueInternal(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        this.values.clear();
        this.values.add(factory.createValue(value));
    }

    public void setValueInternal(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        this.values.clear();
        this.values.add(factory.createValue(value));
    }

    public void setValue(Value[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        if (!getDefinition().isMultiple() && value.length > 1)
        {
            throw new ValueFormatException("The property " + this.name + "  is not multiple");
        }
        else if (getDefinition().isProtected())
        {
            throw new ValueFormatException("The property " + this.name + " is protected");
        }
        else
        {
            int type = PropertyType.UNDEFINED;
            int requiredType = getDefinition().getRequiredType();
            boolean errorRequiredType = false;
            for (Value ovalue : value)
            {
                type = ovalue.getType();
                if (requiredType == 0)
                {
                    type = requiredType;
                }
                if (type != requiredType)
                {
                    errorRequiredType = true;
                    break;
                }
            }
            if (errorRequiredType)
            {
                throw new ValueFormatException("A value is " + PropertyType.nameFromValue(type) + " and the property is defined as " + PropertyType.nameFromValue(requiredType)+" for the property "+this.name);
            }
            this.values.clear();
            if (getDefinition().isMultiple())
            {
                for (Value ovalue : value)
                {
                    this.values.add(ovalue);
                }
            }
            else
            {
                this.values.add(value[0]);
            }
            isModified = true;
        }
    }

    public void setValue(String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(String[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        for (String svalue : value)
        {
            setValue(factory.createValue(svalue));
        }
    }

    public void setValue(InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(value));
    }

    public void setValue(Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        setValue(factory.createValue(node));
    }

    public Value getValue() throws ValueFormatException, RepositoryException
    {
        if (values.size() == 0 && parent.node != null)
        {
            try
            {
                loadPropertiesFromDataBase();
            }
            catch (SWBException swbe)
            {
                throw new RepositoryException(swbe);
            }
        }
        if (values.size() > 0)
        {
            return values.get(0);
        }
        return null;
    }

    private void loadPropertiesFromDataBase() throws SWBException
    {
        BaseNode node = parent.node;
        if (node.existsProperty(clazz, name))
        {
            SemanticProperty property = node.getSemanticProperty(name, clazz);
            if (property == null)
            {
                String uri=node.getUri(name);
                property = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
            }
            if (property != null)
            {
                if (property.isDataTypeProperty())
                {
                    if (property.isBinary())
                    {
                        values.add(factory.createValue(node.getSemanticObject().getInputStreamProperty(property)));
                    }
                    else
                    {
                        int type=PropertyType.UNDEFINED;
                        if(property.isBoolean())
                        {
                            type=PropertyType.BOOLEAN;
                        }
                        else if(property.isDate() || property.isDateTime())
                        {
                            type=PropertyType.DATE;
                        }
                        else if(property.isDouble())
                        {
                            type=PropertyType.DOUBLE;
                        }
                        else if(property.isFloat())
                        {
                            type=PropertyType.DOUBLE;
                        }
                        else if(property.isInt() || property.isLong())
                        {
                            type=PropertyType.LONG;
                        }
                        else if(property.isShort())
                        {
                            type=PropertyType.LONG;
                        }
                        else
                        {
                            type=PropertyType.STRING;
                        }
                        Iterator<SemanticLiteral> literals = node.getSemanticObject().listLiteralProperties(property);
                        while (literals.hasNext())
                        {
                            try
                            {
                                values.add(factory.createValue(literals.next().getString(),type));
                            }
                            catch(ValueFormatException e)
                            {
                                log.debug(e);
                                throw new SWBException("The value can not be converted", e);
                            }
                        }
                    }
                }
                else
                {
                    Iterator<SemanticObject> ovalues = node.getSemanticObject().listObjectProperties(property);
                    while(ovalues.hasNext())
                    {
                        SemanticObject obj=ovalues.next();
                        try
                        {
                            values.add(factory.createValue(new SimpleNode(node, session)));
                        }
                        catch(RepositoryException re)
                        {
                            log.debug(re);
                            throw new SWBException("Error trying to get the Node of the property "+this.name, re);
                        }
                    }
                
                }
            }

        }
    }

    public Value[] getValues() throws ValueFormatException, RepositoryException
    {
        if (values.size() == 0 && parent.node != null)
        {
            try
            {
                loadPropertiesFromDataBase();
            }
            catch (SWBException e)
            {
                throw new RepositoryException(e);
            }
        }
        return values.toArray(new Value[values.size()]);
    }

    public String getString() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value != null)
        {
            return value.getString();
        }
        return null;
    }

    public InputStream getStream() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value == null)
        {
            throw new ValueFormatException();
        }
        return value.getStream();
    }

    public long getLong() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value == null)
        {
            throw new ValueFormatException();
        }
        return value.getLong();
    }

    public double getDouble() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value == null)
        {
            throw new ValueFormatException();
        }
        return value.getDouble();
    }

    public Calendar getDate() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value == null)
        {
            return null;
        }
        return value.getDate();
    }

    public boolean getBoolean() throws ValueFormatException, RepositoryException
    {
        Value value = getValue();
        if (value == null)
        {
            throw new ValueFormatException();
        }
        return value.getBoolean();
    }

    public Node getNode() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public long getLength() throws ValueFormatException, RepositoryException
    {
        return values.size();
    }

    public long[] getLengths() throws ValueFormatException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public PropertyDefinition getDefinition() throws RepositoryException
    {
        return propertyDefinition;
    }

    public int getType() throws RepositoryException
    {
        return getDefinition().getRequiredType();
    }

    public String getPath() throws RepositoryException
    {
        return path;
    }

    public String getName() throws RepositoryException
    {
        return name;
    }

    public Item getAncestor(int arg0) throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        return parent;
    }

    public int getDepth() throws RepositoryException
    {
        int depth = 0;
        Item thisNode = this;
        Item parentNode = this.getParent();
        while (parentNode == null)
        {
            thisNode = parentNode;
            parentNode = thisNode.getParent();
            depth++;
        }
        return depth;
    }

    public Session getSession() throws RepositoryException
    {
        return parent.getSession();
    }

    public boolean isNode()
    {
        return isNode;
    }

    public void setNew(boolean isNew)
    {
        this.isNew = isNew;
    }

    public void setModified(boolean isModified)
    {
        this.isModified = isModified;
    }

    public boolean isNew()
    {
        return isNew;
    }

    public boolean isModified()
    {
        return isModified;
    }

    public boolean isSame(Item arg0) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void accept(ItemVisitor arg0) throws RepositoryException
    {
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        parent.save();
    }

    public void refresh(boolean arg0) throws InvalidItemStateException, RepositoryException
    {
        values.clear();
        try
        {
            loadPropertiesFromDataBase();
        }
        catch (SWBException e)
        {
            throw new RepositoryException(e);
        }
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        System.out.println("Removiendo propiedad "+this.getPath());
        if(parent.getBaseNode()!=null)
        {
            parent.getBaseNode().removeProperty(this.name);
        }
    }

    @Override
    public String toString()
    {
        return name;
    }
}
