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
 
package org.semanticwb.repository;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import org.semanticwb.SWBException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.repository.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import java.util.UUID;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticLiteral;
import org.semanticwb.platform.SemanticVocabulary;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseNode.
 */
public class BaseNode extends BaseNodeBase
{

    /** The namespaces. */
    private static Hashtable<String, String> namespaces = new Hashtable<String, String>();
    
    /** The log. */
    static Logger log = SWBUtils.getLogger(BaseNode.class);
    
    /** The Constant JCR_FROZENNODE_NAME. */
    private static final String JCR_FROZENNODE_NAME = "jcr:frozenNode";
    
    /** The Constant JCR_ROOTVERSION. */
    public static final String JCR_ROOTVERSION = "jcr:rootVersion";
    
    /** The Constant JCR_VERSIONLABELS_NAME. */
    private static final String JCR_VERSIONLABELS_NAME = "jcr:versionLabels";
    
    /** The Constant ONPARENTVERSION_COPY. */
    private static final String ONPARENTVERSION_COPY = "COPY";
    
    /** The Constant ONPARENTVERSION_VERSION. */
    private static final String ONPARENTVERSION_VERSION = "VERSION";
    
    /** The Constant WAS_NOT_FOUND. */
    private static final String WAS_NOT_FOUND = " was not found";    


    static
    {
        namespaces.put("mix", "http://www.jcp.org/jcr/mix/1.0");
        namespaces.put("jcr", "http://www.jcp.org/jcr/1.0");
        namespaces.put("nt", "http://www.jcp.org/jcr/nt/1.0");
        namespaces.put("cm", "http://www.semanticwb.org.mx/swb4/content");
        namespaces.put("swbrep", "http://www.semanticwebbuilder.org/swb4/repository");
        namespaces.put("swb", "http://www.semanticwebbuilder.org/swb4/ontology");
        namespaces.put("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns");
        namespaces.put("owl", "http://www.w3.org/2002/07/owl");
        namespaces.put("xsd", "http://www.w3.org/2001/XMLSchema");
        namespaces.put("rdfs", "http://www.w3.org/2000/01/rdf-schema");
        namespaces.put("swbxf", "http://www.semanticwebbuilder.org/swb4/xforms/ontology");
    }

    /**
     * Instantiates a new base node.
     * 
     * @param base the base
     */
    public BaseNode(SemanticObject base)
    {
        super(base);
    //addPrimaryType(getSemanticObject().getSemanticClass());
    }

    /**
     * Instantiates a new base node.
     * 
     * @param base the base
     * @param clazz the clazz
     * @throws SWBException the sWB exception
     */
    private BaseNode(SemanticObject base, SemanticClass clazz) throws SWBException
    {
        super(base);
        addPrimaryType(clazz);
        String[] superTypes = getSuperTypes();
        for (String superType : superTypes)
        {
            SemanticClass superTypeClass = getSemanticClass(superType);
            if (isMixIn(superTypeClass))
            {
                addMixin(superType);
            }
        }
    }

    /**
     * Gets the primary item name.
     * 
     * @param clazz the clazz
     * @return the primary item name
     */
    public String getPrimaryItemName(SemanticClass clazz)
    {
        String getPrimaryItemName = null;
        SemanticLiteral literal = clazz.getRequiredProperty(ClassDefinition.jcr_primaryItemName);
        if (literal != null && literal.getString() != null)
        {
            getPrimaryItemName = literal.getString();
        }
        return getPrimaryItemName;
    }

    /**
     * Register class.
     * 
     * @param clazz the clazz
     */
    public void registerClass(SemanticClass clazz)
    {
        boolean hasClass = false;
        Iterator<SemanticClass> classes = this.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            if (classes.next().equals(clazz))
            {
                hasClass = true;
                break;
            }
        }
        if (!hasClass)
        {
            this.getSemanticObject().addSemanticClass(clazz);
        }
    }

    /**
     * Checks for orderable child nodes.
     * 
     * @param clazz the clazz
     * @return true, if successful
     */
    public boolean hasOrderableChildNodes(SemanticClass clazz)
    {
        boolean hasOrderableChildNodes = false;
        SemanticLiteral literal = clazz.getRequiredProperty(ClassDefinition.jcr_orderableChildNodes);
        if (literal != null && literal.getString() != null)
        {
            hasOrderableChildNodes = Boolean.parseBoolean(literal.getString());
        }
        return hasOrderableChildNodes;
    }

    /**
     * Gets the on parent version.
     * 
     * @param node the node
     * @param parent the parent
     * @return the on parent version
     */
    public static String getOnParentVersion(BaseNode node, BaseNode parent)
    {
        SemanticObject nodeDefinition = BaseNode.getChildNodeDefinition(parent.getSemanticObject().getSemanticClass(), node.getName());
        String value = ONPARENTVERSION_VERSION;
        if (nodeDefinition != null)
        {
            value = nodeDefinition.getProperty(CommonPropertiesforDefinition.jcr_onParentVersion);
            if (value != null)
            {
                return value;
            }
            else
            {
                value = ONPARENTVERSION_VERSION;
            }
        }
        return value;
    }

    /**
     * Gets the on parent version.
     * 
     * @param property the property
     * @return the on parent version
     */
    public static String getOnParentVersion(SemanticProperty property)
    {
        String value = ONPARENTVERSION_COPY;
        SemanticProperty onParentVersionProperty = CommonPropertiesforDefinition.jcr_onParentVersion;
        SemanticLiteral literal = property.getRequiredProperty(onParentVersionProperty);
        if (literal != null)
        {
            value = literal.getString();
        }
        return value;
    }

    /*public void save() throws SWBException
    {
    GenericIterator<BaseNode> childs = listNodes();
    while (childs.hasNext())
    {
    BaseNode child = childs.next();
    child.save();
    }
    checkVersionable();
    checkSave();


    }*/
    /**
     * Checks if is node type.
     * 
     * @param clazz the clazz
     * @return true, if is node type
     */
    public boolean isNodeType(SemanticClass clazz)
    {
        boolean isVersionNode = false;
        Iterator<SemanticClass> classes = this.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazzNode = classes.next();
            if (clazzNode.equals(clazz) || clazzNode.isSubClass(clazz))
            {
                isVersionNode = true;
                break;
            }
        }
        return isVersionNode;
    }

    /*public Calendar getCreated() throws SWBException
    {
    SemanticProperty jcr_created = jcr_created;
    String value = getPropertyInternal(jcr_created, null);
    if (value == null)
    {
    throw new SWBException("The poperty jcr:created was not found");
    }
    else
    {
    try
    {
    Date date = iso8601dateFormat.parse(value);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal;
    }
    catch (ParseException pse)
    {
    throw new SWBException(pse.getMessage());
    }
    }

    }*/
    /**
     * Checks if is version history node.
     * 
     * @return true, if is version history node
     */
    public boolean isVersionHistoryNode()
    {
        return isNodeType(VersionHistory.nt_VersionHistory);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.BaseNodeBase#remove()
     */
    @Override
    public void remove()
    {
        GenericIterator<BaseNode> childs = listNodes();
        while (childs.hasNext())
        {
            BaseNode child = childs.next();
            child.remove();
        }
        super.remove();
    }

    /**
     * Checks if is version node.
     * 
     * @return true, if is version node
     */
    public boolean isVersionNode()
    {
        return isNodeType(Version.nt_Version);
    }

    /**
     * Un lock.
     * 
     * @throws SWBException the sWB exception
     */
    private void unLock() throws SWBException
    {
        BaseNode lockNode = getLockBaseNode();
        if (lockNode.equals(this))
        {
            SemanticProperty isDeepProperty = Lockable.jcr_lockIsDeep;
            SemanticProperty lockownerProperty = Lockable.jcr_lockOwner;
            lockNode.setPropertyInternal(lockownerProperty, null);
            lockNode.setPropertyInternal(isDeepProperty, null);
            String isdeep = lockNode.getPropertyInternal(isDeepProperty, null);
            String lockOwner = lockNode.getPropertyInternal(lockownerProperty, null);
            if (isdeep != null || lockOwner != null)
            {
                throw new SWBException("The node can not be unlocked");
            }
        }
        else
        {
            throw new SWBException("The node can not be unlocked, because is locked by other node");
        }
    }

    /**
     * Un lock.
     * 
     * @param unlockOwner the unlock owner
     * @param comparator the comparator
     * @throws SWBException the sWB exception
     */
    public void unLock(String unlockOwner, LockUserComparator comparator) throws SWBException
    {
        if (isLockable() && isLocked())
        {
            String nodeLockOwner = getLockOwner();
            if (comparator.canUnlockNodeLockedByUser(nodeLockOwner, unlockOwner))
            {
                unLock();
            }
            else
            {
                throw new SWBException("The node can not by unlock by the user " + unlockOwner);
            }
        }
        else
        {
            throw new SWBException("The node is not lockable or is not lockable");
        }
    }

    /*private void unLock(String lockOwner) throws SWBException
    {
    if ( isLockable() && isLocked() )
    {
    String value = getLockOwner();
    if ( value != null && value.equals(lockOwner) )
    {
    unLock();
    return;
    }
    }
    throw new SWBException("The node is not lockable or is not locked");
    }*/
    /**
     * Checks if is deep lock.
     * 
     * @return true, if is deep lock
     */
    public boolean isDeepLock()
    {
        boolean isDeepLock = false;
        BaseNode lockNode = getLockBaseNode();
        if (lockNode != null && isDeepLock(lockNode))
        {
            isDeepLock = true;
        }
        return isDeepLock;
    }

    /**
     * Checks if is deep lock.
     * 
     * @param node the node
     * @return true, if is deep lock
     */
    private boolean isDeepLock(BaseNode node)
    {
        boolean isDeepLock = false;

        SemanticProperty isDeepProperty = Lockable.jcr_lockIsDeep;
        String value = node.getPropertyInternal(isDeepProperty, "false");
        if (value != null)
        {
            isDeepLock = Boolean.parseBoolean(value);
        }

        return isDeepLock;
    }

    /**
     * Gets the lock owner.
     * 
     * @return the lock owner
     */
    public String getLockOwner()
    {
        String getLockOwner = null;
        BaseNode lockNode = getLockBaseNode();
        if (lockNode != null)
        {

            SemanticProperty lockownerProperty = Lockable.jcr_lockOwner;
            getLockOwner = lockNode.getPropertyInternal(lockownerProperty, null);

        }
        return getLockOwner;
    }

    /**
     * Checks if is locked.
     * 
     * @return true, if is locked
     */
    public boolean isLocked()
    {
        boolean islocked = false;
        BaseNode lockNode = getLockBaseNode();
        if (lockNode != null)
        {
            if (lockNode == this || isDeepLock(lockNode))
            {
                islocked = true;
            }
        }
        return islocked;
    }

    /**
     * Checks for lock.
     * 
     * @param node the node
     * @return true, if successful
     */
    private boolean hasLock(BaseNode node)
    {
        boolean hasLock = false;

        SemanticProperty lockownerProperty = Lockable.jcr_lockOwner;
        String value = node.getPropertyInternal(lockownerProperty, null);
        if (value != null)
        {
            hasLock = true;
        }

        return hasLock;
    }

    /**
     * Gets the lock base node.
     * 
     * @return the lock base node
     */
    public BaseNode getLockBaseNode()
    {
        BaseNode thisNode = this;
        while (thisNode != null)
        {
            if (hasLock(thisNode))
            {
                break;
            }
            else
            {
                thisNode = thisNode.getParent();
            }
        }
        return thisNode;
    }

    /**
     * Lock.
     * 
     * @param lockOwner the lock owner
     * @param lockIsDeep the lock is deep
     * @throws SWBException the sWB exception
     */
    public void lock(String lockOwner, boolean lockIsDeep) throws SWBException
    {
        if (isLockable())
        {
            if (lockOwner == null || lockOwner.trim().equals(""))
            {
                throw new SWBException("The lockOwner is invalid, is null or empty");
            }
            else
            {
                String lockOwnerValue = getLockOwner();
                if (lockOwnerValue == null)
                {
                    SemanticProperty propertyLockOwner = Lockable.jcr_lockOwner;
                    SemanticProperty propertyIsDeep = Lockable.jcr_lockIsDeep;
                    setPropertyInternal(propertyLockOwner, lockOwner);
                    setPropertyInternal(propertyIsDeep, ""+lockIsDeep);
                }
            }
        }
        else
        {
            throw new SWBException("The node " + this.getName() + " is not lockable");
        }
    }

    /**
     * Can add same name siblings.
     * 
     * @param name the name
     * @return true, if successful
     */
    public boolean canAddSameNameSiblings(String name)
    {
        boolean canAddSameNameSiblings = true;
        try
        {
            SemanticClass clazz = getSemanticClass(name);
            canAddSameNameSiblings = canAddSameNameSiblings(clazz, name);
        }
        catch (SWBException swbe)
        {
            canAddSameNameSiblings = true;
        }
        return canAddSameNameSiblings;
    }

    /**
     * Can add same name siblings.
     * 
     * @param clazz the clazz
     * @param nameToCreate the name to create
     * @return true, if successful
     */
    private boolean canAddSameNameSiblings(SemanticClass clazz, String nameToCreate)
    {
        return allowsSameNameSiblings(this.getSemanticObject().getSemanticClass(), clazz, nameToCreate);
    }

    /**
     * Checks if is mandatory.
     * 
     * @param property the property
     * @return true, if is mandatory
     */
    public static boolean isMandatory(SemanticProperty property)
    {
        boolean isMandatory = false;
        SemanticLiteral literal = property.getRequiredProperty(CommonPropertiesforDefinition.jcr_mandatory);
        if (literal != null && literal.getString() != null)
        {
            isMandatory = Boolean.parseBoolean(literal.getString());
        }
        return isMandatory;
    }

    /**
     * Checks if is mandatory.
     * 
     * @return true, if is mandatory
     */
    public boolean isMandatory()
    {
        boolean isMandatory = false;
        SemanticLiteral literal = getSemanticObject().getSemanticClass().getRequiredProperty(CommonPropertiesforDefinition.jcr_mandatory);
        if (literal != null && literal.getString() != null)
        {
            isMandatory = Boolean.parseBoolean(literal.getString());
        }
        return isMandatory;
    }

    /**
     * Checks if is mandatory.
     * 
     * @param clazz the clazz
     * @return true, if is mandatory
     */
    public boolean isMandatory(SemanticClass clazz)
    {
        boolean isRequired = false;
        SemanticLiteral literal = clazz.getRequiredProperty(CommonPropertiesforDefinition.jcr_mandatory);
        if (literal != null)
        {
            isRequired = literal.getBoolean();
        }
        return isRequired;
    }

    /**
     * Can add mixin.
     * 
     * @param mixinName the mixin name
     * @return true, if successful
     */
    public boolean canAddMixin(String mixinName)
    {
        boolean canAddMixin = true;
        try
        {
            SemanticClass mixinClass = getSemanticClass(mixinName);
            for (SemanticClass clazz : getMixInNodeTypes())
            {
                if (clazz.equals(mixinClass))
                {
                    canAddMixin = false;
                }
                Iterator<SemanticClass> superclasses = clazz.listSuperClasses(true);
                while (superclasses.hasNext())
                {
                    if (superclasses.next().equals(mixinClass))
                    {
                        canAddMixin = false;
                    }
                }
            }
        }
        catch (SWBException swbe)
        {
            canAddMixin = false;
        }
        return canAddMixin;
    }

    /**
     * Sets the property internal.
     * 
     * @param property the property
     * @param value the value
     */
    private void setPropertyInternal(SemanticProperty property, String value)
    {
        if (value == null)
        {
            getSemanticObject().removeProperty(property);
        }
        else
        {
            if (property.isBoolean())
            {
                getSemanticObject().setBooleanProperty(property, Boolean.parseBoolean(value));
            }
            else if (property.isLong())
            {
                getSemanticObject().setLongProperty(property, Long.parseLong(value));
            }
            else if (property.isInt())
            {
                getSemanticObject().setIntProperty(property, Integer.parseInt(value));
            }
            else if (property.isDouble())
            {
                getSemanticObject().setDoubleProperty(property, Double.parseDouble(value));
            }
            else if (property.isFloat())
            {
                getSemanticObject().setDoubleProperty(property, Float.parseFloat(value));
            }
            else if (property.isShort())
            {
                getSemanticObject().setIntProperty(property, Short.parseShort(value));
            }
            else if (property.isDate() || property.isDateTime())
            {
                try
                {
                    log.trace("Property value to set a date is "+value);
                    Date date = SWBUtils.TEXT.iso8601DateParse(value);
                    getSemanticObject().setDateProperty(property, date);
                }
                catch (Exception e)
                {
                    log.error(e);
                }
            }
            else
            {
                getSemanticObject().setProperty(property, value);
            }
        }
    }

    /**
     * Sets the input stream property internal.
     * 
     * @param property the property
     * @param value the value
     * @throws SWBException the sWB exception
     */
    private void setInputStreamPropertyInternal(SemanticProperty property, InputStream value) throws SWBException
    {
        if (value == null)
        {
            getSemanticObject().removeProperty(property);
        }
        else
        {
            String name = UUID.randomUUID().toString();
            getSemanticObject().setInputStreamProperty(property, value, name);
        }
    }

    /**
     * Save property to db.
     * 
     * @param property the property
     * @param value the value
     * @throws SWBException the sWB exception
     */
    private void savePropertyToDB(SemanticProperty property, String value) throws SWBException
    {
        if (value == null)
        {
            getSemanticObject().removeProperty(property);
        }
        else
        {
            getSemanticObject().setProperty(property, value);
        }
    }

    /**
     * Save property to db.
     * 
     * @param property the property
     * @param value the value
     * @throws SWBException the sWB exception
     */
    private void savePropertyToDB(SemanticProperty property, InputStream value) throws SWBException
    {
        if (value == null)
        {
            getSemanticObject().removeProperty(property);
        }
        else
        {
            getSemanticObject().removeProperty(property);
            String fileName = UUID.randomUUID().toString();
            getSemanticObject().setInputStreamProperty(property, value, fileName);
        }
    }

    /**
     * Sets the property.
     * 
     * @param property the property
     * @param value the value
     * @throws SWBException the sWB exception
     */
    public void setProperty(SemanticProperty property, String value) throws SWBException
    {
        String name = property.getPrefix() + ":" + property.getName();
        if (isInternal(property))
        {
            throw new SWBException("The property " + name + " is internal and can not be modified");
        }
        else
        {
            if (property.isObjectProperty())
            {
                throw new SWBException("The property " + name + " is not a datatype property");
            }
            savePropertyToDB(property, value);
        }
    }

    /**
     * Sets the property.
     * 
     * @param property the property
     * @param value the value
     * @throws SWBException the sWB exception
     */
    public void setProperty(SemanticProperty property, InputStream value) throws SWBException
    {
        String name = property.getPrefix() + ":" + property.getName();
        if (isInternal(property))
        {
            throw new SWBException("The property " + name + " is internal and can not be modified");
        }
        else
        {
            if (property.isObjectProperty())
            {
                throw new SWBException("The property " + name + " is not a datatype property");
            }
            savePropertyToDB(property, value);
        }
    }

    /**
     * Gets the input stream property.
     * 
     * @param property the property
     * @return the input stream property
     * @throws SWBException the sWB exception
     */
    public InputStream getInputStreamProperty(SemanticProperty property) throws SWBException
    {
        InputStream getProperty = null;
        if (isInternal(property))
        {
            throw new SWBException("the property " + property.getPrefix() + ":" + property.getName() + " is internal");
        }
        getProperty = getSemanticObject().getInputStreamProperty(property);
        return getProperty;
    }

    /**
     * Gets the property internal.
     * 
     * @param property the property
     * @param value the value
     * @return the property internal
     */
    private String getPropertyInternal(SemanticProperty property, String value)
    {
        return getSemanticObject().getProperty(property, value);
    }

    /**
     * Gets the property.
     * 
     * @param property the property
     * @return the property
     * @throws SWBException the sWB exception
     */
    public String getProperty(SemanticProperty property) throws SWBException
    {
        String getProperty = null;
        if (isInternal(property))
        {
            throw new SWBException("the property " + property.getPrefix() + ":" + property.getName() + " is internal");
        }
        if(property.isDataTypeProperty())
        {
            if(property.isDate() || property.isDateTime())
            {
                Date date= getSemanticObject().getDateProperty(property);
                getProperty=SWBUtils.TEXT.iso8601DateFormat(date);
                log.trace("Property value of date is "+getProperty);
            }
            else
            {
                getProperty = getSemanticObject().getProperty(property);
            }

        }
        else
        {
            SWBException swbe=new SWBException("The property is an object property and can not be transform to String");
            log.trace(swbe);
            throw swbe;
        }
        return getProperty;
    }

    /**
     * List semantic properties.
     * 
     * @param clazz the clazz
     * @return the iterator
     */
    public final Iterator<SemanticProperty> listSemanticProperties(SemanticClass clazz)
    {
        HashSet<SemanticProperty> propertiesToReturn = new HashSet<SemanticProperty>();

        Iterator<SemanticProperty> properties = clazz.listProperties();
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            if (!isInternal(property))
            {
                propertiesToReturn.add(property);
            }
        }

        return propertiesToReturn.iterator();
    }

    /**
     * List semantic properties.
     * 
     * @return the iterator
     */
    public final Iterator<SemanticProperty> listSemanticProperties()
    {
        HashSet<SemanticProperty> propertiesToReturn = new HashSet<SemanticProperty>();
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if (!isInternal(property))
                {
                    propertiesToReturn.add(property);
                }
            }
        }
        return propertiesToReturn.iterator();
    }

    /**
     * Gets the uUID.
     * 
     * @return the uUID
     * @throws SWBException the sWB exception
     */
    public final String getUUID() throws SWBException
    {
        SemanticProperty property = Referenceable.jcr_uuid;
        String value = getPropertyInternal(property, null);
        if (value != null)
        {
            return value;
        }
        else
        {
            throw new SWBException("The property UUID is not supported");
        }

    }

    /**
     * Gets the semantic class.
     * 
     * @param nodeType the node type
     * @return the semantic class
     * @throws SWBException the sWB exception
     */
    public final SemanticClass getSemanticClass(String nodeType) throws SWBException
    {
        String uri = getUri(nodeType);
        SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        if (clazz == null)
        {
            throw new SWBException("The nodeType " + nodeType + WAS_NOT_FOUND);
        }
        return clazz;
    }

    /**
     * Adds the primary type.
     * 
     * @param clazz the clazz
     */
    private final void addPrimaryType(SemanticClass clazz)
    {
        if (getPropertyInternal(jcr_primaryType, null) == null)
        {
            setPropertyInternal(jcr_primaryType, clazz.getPrefix() + ":" + clazz.getName());
        }
    }

    /**
     * Checks if is autocreated.
     * 
     * @param clazz the clazz
     * @return true, if is autocreated
     */
    public final boolean isAutocreated(SemanticClass clazz)
    {
        boolean isAutocreated = false;
        SemanticLiteral value = clazz.getRequiredProperty(CommonPropertiesforDefinition.jcr_autoCreated);
        if (value != null && value.getString() != null)
        {
            isAutocreated = Boolean.parseBoolean(value.getString());
        }
        return isAutocreated;
    }

    /**
     * Checks if is autocreated.
     * 
     * @param property the property
     * @return true, if is autocreated
     */
    public final static boolean isAutocreated(SemanticProperty property)
    {
        boolean isAutocreated = false;
        SemanticLiteral value = property.getRequiredProperty(CommonPropertiesforDefinition.jcr_autoCreated);
        if (value != null && value.getString() != null)
        {
            isAutocreated = Boolean.parseBoolean(value.getString());
        }
        return isAutocreated;
    }

    /**
     * Checks if is protected.
     * 
     * @param property the property
     * @return true, if is protected
     */
    public static final boolean isProtected(SemanticProperty property)
    {
        boolean isProtected = false;
        try
        {
            SemanticLiteral literal = property.getRequiredProperty(CommonPropertiesforDefinition.jcr_protected);
            if (literal != null)
            {
                isProtected = literal.getBoolean();
            }
        }
        catch (Exception e)
        {
            isProtected = false;
        }
        return isProtected;
    }

    /**
     * Gets the child node definition.
     * 
     * @param clazz the clazz
     * @param nodeName the node name
     * @return the child node definition
     */
    public static SemanticObject getChildNodeDefinition(SemanticClass clazz, String nodeName)
    {
        SemanticObject getChildNodeDefinition = null;
        for (SemanticObject nodeDefinition : getChildNodeDefinition(clazz))
        {
            String name = nodeDefinition.getProperty(ChildNodeDefinition.jcr_name);
            if (name != null && name.equals(nodeName))
            {
                return nodeDefinition;
            }
        }
        for (SemanticObject nodeDefinition : getChildNodeDefinition(clazz))
        {
            String name = nodeDefinition.getProperty(ChildNodeDefinition.jcr_name);
            if (name != null && name.equals("*"))
            {
                return nodeDefinition;
            }
        }
        Iterator<SemanticClass> classes = clazz.listSuperClasses(true);
        while (classes.hasNext())
        {
            SemanticClass superclazz = classes.next();
            for (SemanticObject nodeDefinition : getChildNodeDefinition(superclazz))
            {
                String name = nodeDefinition.getProperty(ChildNodeDefinition.jcr_name);
                if (name != null && nodeName != null && name.equals(nodeName))
                {
                    return nodeDefinition;
                }
            }
            for (SemanticObject nodeDefinition : getChildNodeDefinition(superclazz))
            {
                String name = nodeDefinition.getProperty(ChildNodeDefinition.jcr_name);
                if (name != null && name.equals("*"))
                {
                    return nodeDefinition;
                }
            }

        }
        return getChildNodeDefinition;
    }

    /**
     * Allows same name siblings.
     * 
     * @param clazz the clazz
     * @param clazzToCreate the clazz to create
     * @param nameTocreate the name tocreate
     * @return true, if successful
     */
    public final boolean allowsSameNameSiblings(SemanticClass clazz, SemanticClass clazzToCreate, String nameTocreate)
    {
        boolean allowsSameNameSiblings = false;
        for (SemanticObject nodeDefinition : getChildNodeDefinition(clazz))
        {
            String name = nodeDefinition.getProperty(Nameable.jcr_name);
            String requiredType = nodeDefinition.getProperty(ChildNodeDefinition.jcr_requiredPrimaryTypes);
            if (name != null && name.equals(nameTocreate) && requiredType != null)
            {
                try
                {
                    SemanticClass clazzAllowed = getSemanticClass(requiredType);
                    if (clazzToCreate.equals(clazzAllowed) || clazzToCreate.isSubClass(clazzAllowed))
                    {
                        if (nodeDefinition.getProperty(ChildNodeDefinition.jcr_sameNameSiblings) != null && nodeDefinition.getProperty(ChildNodeDefinition.jcr_sameNameSiblings).equals("true"))
                        {
                            allowsSameNameSiblings = true;
                            break;
                        }
                    }
                }
                catch (SWBException e)
                {
                    log.error(e);
                }
            }
            if (!allowsSameNameSiblings)
            {
                if (name != null && name.equals("*") && requiredType != null)
                {
                    try
                    {
                        SemanticClass clazzAllowed = getSemanticClass(requiredType);
                        if (clazzToCreate.equals(clazzAllowed) || clazzToCreate.isSubClass(clazzAllowed))
                        {
                            if (nodeDefinition.getProperty(ChildNodeDefinition.jcr_sameNameSiblings) != null && nodeDefinition.getProperty(ChildNodeDefinition.jcr_sameNameSiblings).equals("true"))
                            {
                                allowsSameNameSiblings = true;
                            }
                        }
                    }
                    catch (SWBException e)
                    {
                        log.error(e);
                    }
                }
            }
        }

        return allowsSameNameSiblings;
    }

    /**
     * Checks if is protected.
     * 
     * @param clazz the clazz
     * @return true, if is protected
     */
    public final boolean isProtected(SemanticClass clazz)
    {
        boolean isProtected = false;
        try
        {
            SemanticLiteral literal = clazz.getRequiredProperty(ChildNodeDefinition.jcr_protected);
            if (literal != null)
            {
                isProtected = literal.getBoolean();
            }
        }
        catch (Exception e)
        {
            isProtected = false;
        }
        return isProtected;
    }

    /**
     * Checks if is internal.
     * 
     * @param property the property
     * @return true, if is internal
     */
    public final boolean isInternal(SemanticProperty property)
    {
        boolean isInternal = false;
        try
        {
            SemanticProperty internal = PropertyDefinition.swbrep_internal;
            SemanticLiteral literal = property.getRequiredProperty(internal);
            if (literal != null)
            {
                isInternal = literal.getBoolean();
            }
        }
        catch (Exception e)
        {
            isInternal = false;
        }
        return isInternal;
    }

    /**
     * Adds the uuid.
     */
    private final void addUUID()
    {
        if (isReferenceable() || isVersionable())
        {
            SemanticProperty jcr_uuid = Referenceable.jcr_uuid;
            String value = getPropertyInternal(jcr_uuid, null);
            if (value == null)
            {
                setPropertyInternal(jcr_uuid, UUID.randomUUID().toString());
            }
        }
    }

    /**
     * Gets the super types.
     * 
     * @param clazz the clazz
     * @return the super types
     */
    public final Set<String> getSuperTypes(SemanticClass clazz)
    {
        HashSet<String> superTypes = new HashSet<String>();
        Iterator<SemanticLiteral> literals = listSemanticLiterals(clazz, ClassDefinition.jcr_supertypes);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if (literal != null && literal.getString() != null)
            {
                superTypes.add(literal.getString());
            }
        }
        Iterator<SemanticClass> superClasses = clazz.listSuperClasses();
        while (superClasses.hasNext())
        {
            SemanticClass superClazz = superClasses.next();
            if (!(superClazz.isSWBInterface() || superClazz.isSWBModel()))
            {
                superTypes.addAll(getSuperTypes(superClazz));
            }
        }
        return superTypes;
    }

    /**
     * Gets the super types.
     * 
     * @return the super types
     * @throws SWBException the sWB exception
     */
    public final String[] getSuperTypes() throws SWBException
    {
        HashSet<String> superTypes = new HashSet<String>();
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticLiteral> literals = listSemanticLiterals(clazz, ClassDefinition.jcr_supertypes);
            while (literals.hasNext())
            {
                SemanticLiteral literal = literals.next();
                if (literal != null && literal.getString() != null)
                {
                    superTypes.add(literal.getString());
                }
            }
        }
        return superTypes.toArray(new String[superTypes.size()]);
    }

    /**
     * Checks if is mix in.
     * 
     * @return true, if is mix in
     */
    public final boolean isMixIn()
    {
        return isMixIn(getSemanticObject().getSemanticClass());
    }

    /**
     * Gets the mix in node types.
     * 
     * @return the mix in node types
     */
    public final SemanticClass[] getMixInNodeTypes()
    {
        ArrayList<SemanticClass> classes = new ArrayList<SemanticClass>();
        Iterator<SemanticClass> itclasses = getSemanticObject().listSemanticClasses();
        while (itclasses.hasNext())
        {
            SemanticClass clazz = itclasses.next();
            if (isMixIn(clazz))
            {
                classes.add(clazz);
            }
        }
        return classes.toArray(new SemanticClass[classes.size()]);
    }

    /**
     * Gets the semantic literal.
     * 
     * @param clazz the clazz
     * @param property the property
     * @return the semantic literal
     * @throws SWBException the sWB exception
     */
    private final SemanticLiteral getSemanticLiteral(SemanticClass clazz, SemanticProperty property) throws SWBException
    {
        SemanticLiteral literal = null;
        if (property != null)
        {
            literal = clazz.getRequiredProperty(property);
        }
        return literal;
    }

    /**
     * List semantic literals.
     * 
     * @param clazz the clazz
     * @param property the property
     * @return the iterator
     */
    private final Iterator<SemanticLiteral> listSemanticLiterals(SemanticClass clazz, SemanticProperty property)
    {
        return clazz.listRequiredProperties(property);
    }

    /**
     * Checks if is mix in.
     * 
     * @param clazz the clazz
     * @return true, if is mix in
     */
    public final boolean isMixIn(SemanticClass clazz)
    {
        boolean isMixIn = false;
        try
        {
            SemanticLiteral literal = getSemanticLiteral(clazz, ClassDefinition.mix_mixin);
            if (literal != null)
            {
                isMixIn = literal.getBoolean();
            }
        }
        catch (SWBException swe)
        {
            isMixIn = false;
        }
        return isMixIn;
    }

    /**
     * Checks if is mix in.
     * 
     * @param nodeType the node type
     * @return true, if is mix in
     * @throws SWBException the sWB exception
     */
    public final boolean isMixIn(String nodeType) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(nodeType);
        return isMixIn(clazz);
    }

    /**
     * Adds the node to property.
     * 
     * @param property the property
     * @param clazz the clazz
     * @param name the name
     * @param path the path
     * @return the base node
     * @throws SWBException the sWB exception
     */
    private BaseNode addNodeToProperty(SemanticProperty property, SemanticClass clazz, String name, String path) throws SWBException
    {
        if (property.isObjectProperty())
        {
            SemanticObject value = getSemanticObject().getObjectProperty(property, null);
            if (value == null)
            {
                SemanticClass clazzDomain = property.getRangeClass();
                if (clazzDomain.equals(clazz) || clazzDomain.isSuperClass(clazz))
                {
                    String uri = getSemanticObject().getModel().getObjectUri(UUID.randomUUID().toString(), clazz);
                    value = getSemanticObject().getModel().createSemanticObject(uri, clazz);
                    BaseNode valueNode = new BaseNode(value, clazz);
                    valueNode.setName(name);
                    valueNode.setPath(path);
                    getSemanticObject().setObjectProperty(property, value);
                }
                else
                {
                    throw new SWBException("The property has a diferent nodeType (" + clazzDomain.getPrefix() + ":" + clazzDomain.getName() + ") of the NodeType(" + clazz.getPrefix() + ":" + clazz.getName() + ") to create.");
                }
            }
            BaseNode newNode = new BaseNode(value);
            String[] superTypes = newNode.getSuperTypes();
            for (String superType : superTypes)
            {
                SemanticClass superTypeClass = getSemanticClass(superType);
                if (isMixIn(superTypeClass))
                {
                    newNode.addMixin(superType);
                }
            }
            return newNode;
        }
        else
        {
            throw new SWBException("The property is not defined as an objectType");
        }
    }

    /**
     * Checks if is frozen node.
     * 
     * @return true, if is frozen node
     */
    private boolean isFrozenNode()
    {
        return isNodeType(FrozenNode.nt_FrozenNode);
    }

    /**
     * Do copy.
     * 
     * @param targetNode the target node
     * @param destNode the dest node
     * @throws SWBException the sWB exception
     */
    private void doCopy(BaseNode targetNode, BaseNode destNode) throws SWBException
    {
        GenericIterator<BaseNode> nodes = targetNode.listNodes();
        while (nodes.hasNext())
        {
            BaseNode childNode = nodes.next();
            BaseNode copyChild = destNode.createNodeBase(childNode.getName(), childNode.getSemanticObject().getSemanticClass());
            childNode.doCopy(copyChild);
        }
    }

    /**
     * Do copy.
     * 
     * @param copyChild the copy child
     * @throws SWBException the sWB exception
     */
    private void doCopy(BaseNode copyChild) throws SWBException
    {
        doCopy(this, copyChild);
        Iterator<SemanticProperty> properties = this.getSemanticObject().getSemanticClass().listProperties();
        properties = this.getSemanticObject().getSemanticClass().listProperties();
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            if (!isInternal(property))
            {
                String onParentVersion = getOnParentVersion(property);
                if (property.isDataTypeProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                {
                    if (property.isBinary())
                    {
                        copyChild.setInputStreamPropertyInternal(property, getInputStreamProperty(property));
                    }
                    else
                    {
                        copyChild.setPropertyInternal(property, getProperty(property));
                    }
                }
                if (property.isObjectProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                {
                    copyChild.getSemanticObject().setObjectProperty(property, this.getSemanticObject().getObjectProperty(property));
                }
            }
        }
    }

    /**
     * Do copy to frozen node.
     * 
     * @param frozenNode the frozen node
     * @throws SWBException the sWB exception
     */
    private void doCopyToFrozenNode(BaseNode frozenNode) throws SWBException
    {
        log.trace("Copiando propiedades a Nodo FrozenNode con UDDI ..." + frozenNode.getUUID());
        if (frozenNode.isFrozenNode())
        {
            log.trace("El nodo " + frozenNode.getName() + " es un frozenNode");
            initializeFrozenProperties(frozenNode.getSemanticObject());
            doCopy(this, frozenNode);
            Iterator<SemanticProperty> properties = this.getSemanticObject().getSemanticClass().listProperties();
            properties = this.getSemanticObject().getSemanticClass().listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if (!isInternal(property))
                {
                    String onParentVersion = getOnParentVersion(property);
                    if (property.isDataTypeProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                    {
                        if (property.isBinary())
                        {
                            log.trace("Copiando propiedad " + property.getURI());
                            frozenNode.setInputStreamPropertyInternal(property, getInputStreamProperty(property));
                        }
                        else
                        {
                            log.trace("Copiando propiedad " + property.getURI());
                            frozenNode.setPropertyInternal(property, getProperty(property));

                        }
                    }
                    else if (property.isObjectProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                    {
                        log.trace("Copiando propiedad " + property.getURI());
                        frozenNode.getSemanticObject().setObjectProperty(property, this.getSemanticObject().getObjectProperty(property));
                    }
                    else
                    {
                        log.trace("La propiedad " + property.getURI() + " no es DataTypeProperty ó ObjectProperty");
                    }
                }
                else
                {
                    log.trace("La propiedad " + property.getURI() + " es internal y no puede ser copiada");
                }
            }
        }
        else
        {
            log.trace("El nodo " + frozenNode.getName() + " no es un frozenNode");
            throw new SWBException("The node is not a frozen node");
        }
    }

    /**
     * Initialize frozen properties.
     * 
     * @param frozenNode the frozen node
     * @throws SWBException the sWB exception
     */
    private void initializeFrozenProperties(SemanticObject frozenNode) throws SWBException
    {
        SemanticProperty jcr_frozenUuid = FrozenNode.jcr_frozenUuid;
        SemanticProperty jcr_frozenPrimaryType = FrozenNode.jcr_frozenPrimaryType;
        SemanticProperty frozenMixinTypes = FrozenNode.jcr_frozenMixinTypes;
        String uudi = this.getUUID();
        frozenNode.setProperty(jcr_frozenUuid, uudi);
        frozenNode.setProperty(jcr_frozenPrimaryType, this.getPrimaryType());
        Iterator<SemanticLiteral> literals = getSemanticObject().listLiteralProperties(frozenMixinTypes);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if (literal != null && literal.getString() != null)
            {
                frozenNode.setProperty(frozenMixinTypes, literal.getString());
            }
        }

    }

    /**
     * Adds the root node to history.
     * 
     * @param historyNode the history node
     * @throws SWBException the sWB exception
     */
    private void addRootNodeToHistory(BaseNode historyNode) throws SWBException
    {
        if (historyNode.isVersionHistoryNode())
        {
            BaseNode ntVersion = historyNode.createNodeBase(JCR_ROOTVERSION, Version.nt_Version);
//            BaseNode versionLabels = historyNode.createNodeBase(JCR_VERSIONLABELS_NAME, VersionLabels.nt_VersionLabels);
            BaseNode ntFrozenNode = ntVersion.createNodeBase(JCR_FROZENNODE_NAME, FrozenNode.nt_FrozenNode);
            initializeFrozenProperties(ntFrozenNode.getSemanticObject());
            this.getSemanticObject().setObjectProperty(Versionable.jcr_baseVersion, ntVersion.getSemanticObject());
        }
    }

    /**
     * Creates the version history node.
     * 
     * @throws SWBException the sWB exception
     */
    private void createVersionHistoryNode() throws SWBException
    {
        if (isVersionable())
        {
            SemanticProperty property = Versionable.jcr_versionHistory;
            String path = this.getPath() + "/" + property.getPrefix() + ":" + property.getName();
            BaseNode historyNode = addNodeToProperty(property, VersionHistory.nt_VersionHistory, "jcr:versionHistory", path);
            historyNode.setPropertyInternal(Referenceable.jcr_uuid, UUID.randomUUID().toString());
            addRootNodeToHistory(historyNode);
        }
    }

    /**
     * Gets the base successors.
     * 
     * @return the base successors
     * @throws SWBException the sWB exception
     */
    public BaseNode[] getBaseSuccessors() throws SWBException
    {
        ArrayList<BaseNode> successors = new ArrayList<BaseNode>();
        Iterator<SemanticObject> objs = this.getSemanticObject().listObjectProperties(Version.jcr_successors);
        while (objs.hasNext())
        {
            successors.add(new BaseNode(objs.next()));
        }
        return successors.toArray(new BaseNode[successors.size()]);
    }

    /**
     * Gets the base predecessors.
     * 
     * @return the base predecessors
     * @throws SWBException the sWB exception
     */
    public BaseNode[] getBasePredecessors() throws SWBException
    {
        ArrayList<BaseNode> predecessors = new ArrayList<BaseNode>();
        Iterator<SemanticObject> objs = this.getSemanticObject().listObjectProperties(Version.jcr_predecessors);
        while (objs.hasNext())
        {
            predecessors.add(new BaseNode(objs.next()));
        }
        return predecessors.toArray(new BaseNode[predecessors.size()]);
    }

    /**
     * Gets the history node.
     * 
     * @return the history node
     * @throws SWBException the sWB exception
     */
    public BaseNode getHistoryNode() throws SWBException
    {
        BaseNode getHistoryNode = null;
        SemanticProperty property = Versionable.jcr_versionHistory;
        if (property.isObjectProperty())
        {
            SemanticObject versionHistory = getSemanticObject().getObjectProperty(property);
            if (versionHistory != null)
            {
                getHistoryNode = new BaseNode(versionHistory);
            }
        }
        return getHistoryNode;
    }

    /**
     * Checks if is checkedout by parents.
     * 
     * @return true, if is checkedout by parents
     */
    private boolean isCheckedoutByParents()
    {
        boolean isCheckedoutByParents = false;
        BaseNode parent = this.getParent();
        if (parent != null)
        {
            if (parent.isChekedOut())
            {
                isCheckedoutByParents = true;
            }
            else
            {
                isCheckedoutByParents = parent.isCheckedoutByParents();
            }
        }
        return isCheckedoutByParents;
    }

    /**
     * Checks if is chekedin.
     * 
     * @return true, if is chekedin
     */
    public boolean isChekedin()
    {
        boolean isChekedin = false;
        if (isVersionable())
        {
            SemanticProperty isCheckoutPropety = Versionable.jcr_isCheckedOut;
            String value = getPropertyInternal(isCheckoutPropety, null);
            if (value != null)
            {
                isChekedin = !Boolean.parseBoolean(value);
            }
        }
        return isChekedin;
    }

    /**
     * Checks if is cheked out.
     * 
     * @return true, if is cheked out
     */
    public boolean isChekedOut()
    {
        boolean isChekedOut = false;

        SemanticProperty isCheckoutPropety = Versionable.jcr_isCheckedOut;
        String value = getPropertyInternal(isCheckoutPropety, null);
        if (value != null)
        {
            isChekedOut = Boolean.parseBoolean(value);
        }
        if (!isVersionable() && isCheckedoutByParents())
        {
            isChekedOut = true;
        }
        return isChekedOut;
    }

    /**
     * Checkin.
     * 
     * @return the base node
     * @throws SWBException the sWB exception
     */
    public BaseNode checkin() throws SWBException
    {
        if (!this.isVersionable())
        {
            throw new SWBException("The node is not versionable");
        }
        // TODO: pro revisar si la propiedad debe ser a nivel semantico o no
        SemanticProperty isCheckoutPropety = Versionable.jcr_isCheckedOut;
        setPropertyInternal(isCheckoutPropety, "false");
        BaseNode versionNode = addVersionToHistoryNode();
        log.trace("Version created " + versionNode.getName());
        return versionNode;

    /*if (isChekedOut())
    {
    SemanticProperty isCheckoutPropety = jcr_isCheckedOut;
    setPropertyInternal(isCheckoutPropety, "false");
    BaseNode versionNode = addVersionToHistoryNode();
    log.debug("Version created " + versionNode.getName());
    return versionNode;
    }
    else
    {
    throw new SWBException("The node is not checkedout");
    }*/
    }

    /**
     * Checkout.
     * 
     * @throws SWBException the sWB exception
     */
    public void checkout() throws SWBException
    {
        SemanticProperty isCheckoutPropety = Versionable.jcr_isCheckedOut;
        setPropertyInternal(isCheckoutPropety, "true");
    }

    /**
     * Gets the base version.
     * 
     * @return the base version
     */
    public BaseNode getBaseVersion()
    {
        BaseNode getBaseVersion = null;
        if (isVersionable())
        {
            SemanticObject baseVersion = getSemanticObject().getObjectProperty(Versionable.jcr_baseVersion);
            if (baseVersion != null)
            {
                getBaseVersion = new BaseNode(baseVersion);
            }
        }
        return getBaseVersion;
    }

    /**
     * Adds the version to history node.
     * 
     * @return the base node
     * @throws SWBException the sWB exception
     */
    private BaseNode addVersionToHistoryNode() throws SWBException
    {
        BaseNode addVersionToHistoryNode = null;
        if (isVersionable())
        {
            BaseNode versionHistory = getHistoryNode();
            if (versionHistory != null)
            {
                BaseNode[] predecessors = versionHistory.getVersions();
                String nameBaseVersion = this.getBaseVersion().getName();
                String nextVersion = "1.0";
                if (!nameBaseVersion.equals(JCR_ROOTVERSION))
                {
                    try
                    {
                        Float version = new Float(nameBaseVersion);
                        DecimalFormat format = new DecimalFormat("#.0");
                        nextVersion = format.format(version.floatValue() + .1f);
                    }
                    catch (NumberFormatException nfe)
                    {
                        log.error(nfe);
                    }
                }
                BaseNode ntVersion = versionHistory.createNodeBase(nextVersion, Version.nt_Version);
                // TODO: DEBE AGREGAR REFERENCIA A LAS PREDECEDORAS, PERO FALTA METODO PARA AGREGAR
                for (BaseNode predecessor : predecessors)
                {
                    predecessor.getSemanticObject().addObjectProperty(Version.jcr_successors, ntVersion.getSemanticObject());
                    ntVersion.getSemanticObject().addObjectProperty(Version.jcr_predecessors, predecessor.getSemanticObject());
                }
                BaseNode ntFrozenNode = ntVersion.createNodeBase(JCR_FROZENNODE_NAME, FrozenNode.nt_FrozenNode);
                doCopyToFrozenNode(ntFrozenNode);
                addVersionToHistoryNode = ntVersion;
                //update the BaseVersion of this node
                this.getSemanticObject().setObjectProperty(Versionable.jcr_baseVersion, ntVersion.getSemanticObject());

            }
        }
        return addVersionToHistoryNode;
    }

    /**
     * Check versionable.
     * 
     * @throws SWBException the sWB exception
     */
    public void checkVersionable() throws SWBException
    {
        if (isVersionable())
        {
            BaseNode history = getHistoryNode();
            if (history == null)
            {
                createVersionHistoryNode();
                checkout();
            }
        }
    }

    /**
     * Adds the mixin.
     * 
     * @param mixinName the mixin name
     * @throws SWBException the sWB exception
     */
    public final void addMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        SemanticProperty property = jcr_mixinTypes;
        setPropertyInternal(property, mixinName);
        this.getSemanticObject().addSemanticClass(clazz);
        addVersionableUuid();
        addUUID();
        addCreatedProperty();
    }

    /**
     * Gets the child node definition.
     * 
     * @param clazz the clazz
     * @return the child node definition
     */
    public static SemanticObject[] getChildNodeDefinition(SemanticClass clazz)
    {
        ArrayList<SemanticObject> getChildNodeDefinition = new ArrayList<SemanticObject>();
        SemanticProperty property = ClassDefinition.jcr_childNodeDefinition;
        Iterator<SemanticObject> definitions = clazz.listObjectRequiredProperties(property);
        while (definitions.hasNext())
        {
            SemanticObject object = definitions.next();
            getChildNodeDefinition.add(object);
        }
        return getChildNodeDefinition.toArray(new SemanticObject[getChildNodeDefinition.size()]);
    }

    /**
     * Can add node.
     * 
     * @param nodeType the node type
     * @return true, if successful
     */
    public boolean canAddNode(String nodeType)
    {
        boolean canAddNode = false;
        try
        {
            SemanticClass clazz = getSemanticClass(nodeType);
            canAddNode = canAddNode(clazz);
        }
        catch (SWBException swbe)
        {
            canAddNode = false;
        }
        return canAddNode;

    }

    /**
     * Can add node.
     * 
     * @param clazzSurce the clazz surce
     * @param clazzDest the clazz dest
     * @return true, if successful
     */
    public boolean canAddNode(SemanticClass clazzSurce, SemanticClass clazzDest)
    {
        boolean canAddNode = false;
        SemanticObject[] childDefinitions = getChildNodeDefinition(clazzDest);
        if (childDefinitions.length > 0)
        {
            for (SemanticObject childDefinition : childDefinitions)
            {
                String jcr_requiredType = childDefinition.getProperty(ChildNodeDefinition.jcr_requiredPrimaryTypes);
                if (jcr_requiredType != null)
                {
                    try
                    {
                        SemanticClass class_requiredType = getSemanticClass(jcr_requiredType);
                        if (class_requiredType != null)
                        {
                            if (class_requiredType.equals(clazzSurce) || clazzSurce.isSubClass(class_requiredType))
                            {
                                canAddNode = true;
                                break;
                            }
                        }
                    }
                    catch (SWBException swbe)
                    {
                        canAddNode = false;
                    }
                }

            }
        }
        else
        {
            Iterator<SemanticClass> classes = clazzDest.listSuperClasses();
            while (classes.hasNext())
            {
                SemanticClass clazz = classes.next();
                canAddNode = canAddNode(clazzSurce, clazz);
                if (canAddNode)
                {
                    break;
                }
            }
            if (!canAddNode)
            {
                canAddNode = false;
            }

        }
        return canAddNode;
    }

    /**
     * Adds the created property.
     * 
     * @throws SWBException the sWB exception
     */
    private void addCreatedProperty() throws SWBException
    {
        if (isVersionNode())
        {
            SemanticProperty jcr_created = Traceable.jcr_created;
            if (jcr_created != null)
            {
                String value = SWBUtils.TEXT.iso8601DateFormat(Calendar.getInstance().getTime());
                setPropertyInternal(jcr_created, value);
            }
        }
    }

    /**
     * Can add node.
     * 
     * @param clazzSurce the clazz surce
     * @return true, if successful
     */
    private boolean canAddNode(SemanticClass clazzSurce)
    {
        return canAddNode(clazzSurce, getSemanticObject().getSemanticClass());
    }

    /**
     * Can add property.
     * 
     * @param name the name
     * @param clazz the clazz
     * @return true, if successful
     */
    public boolean canAddProperty(String name, SemanticClass clazz)
    {
        boolean canAddProperty = false;
        return canAddProperty;
    }

    /**
     * Can add any property.
     * 
     * @param clazz the clazz
     * @return true, if successful
     */
    public boolean canAddAnyProperty(SemanticClass clazz)
    {
        boolean canAddAnyProperty = false;

        for (SemanticObject propertyDefinition : BaseNode.getPropertyDefinition(clazz))
        {
            String name = propertyDefinition.getProperty(Nameable.jcr_name);
            if (name != null)
            {
                if (name.equals("*"))
                {
                    canAddAnyProperty = true;
                    break;
                }
            }
        }
        return canAddAnyProperty;

    }

    /**
     * Gets the required property.
     * 
     * @param object the object
     * @param prop the prop
     * @return the required property
     */
    public static String getRequiredProperty(SemanticObject object, SemanticProperty prop)
    {
        if (object.getSemanticClass().getRequiredProperty(prop) != null)
        {
            return object.getSemanticClass().getRequiredProperty(prop).getString();
        }
        else
        {
            return null;
        }
    }

    /**
     * Gets the property definition.
     * 
     * @param clazz the clazz
     * @return the property definition
     */
    public static SemanticObject[] getPropertyDefinition(SemanticClass clazz)
    {
        ArrayList<SemanticObject> propertyDefinitions = new ArrayList<SemanticObject>();
        Iterator<SemanticObject> objects = clazz.listObjectRequiredProperties(ClassDefinition.jcr_propertyDefinition);
        while (objects.hasNext())
        {
            propertyDefinitions.add(objects.next());
        }
        return propertyDefinitions.toArray(new SemanticObject[propertyDefinitions.size()]);
    }

    /**
     * Checks if is protected.
     * 
     * @return true, if is protected
     */
    public boolean isProtected()
    {
        boolean isProtected = false;
        Iterator<SemanticLiteral> literals = getSemanticObject().getSemanticClass().listRequiredProperties(CommonPropertiesforDefinition.jcr_protected);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if (literal != null && literal.getString() != null)
            {
                String value = literal.getString();
                isProtected = Boolean.parseBoolean(value);
                break;
            }
        }
        return isProtected;
    }

    /**
     * Exists same node.
     * 
     * @param name the name
     * @return true, if successful
     */
    public boolean existsSameNode(String name)
    {
        boolean existsNodeName = false;
        GenericIterator<BaseNode> nodes = listNodes();
        while (nodes.hasNext())
        {
            BaseNode child = nodes.next();
            if (child.getName().equals(name))
            {
                existsNodeName = true;
                break;

            }
        }
        return existsNodeName;
    }

    /**
     * Checks if is multiple.
     * 
     * @param property the property
     * @return true, if is multiple
     */
    public static boolean isMultiple(SemanticProperty property)
    {
        boolean isMultiple = false;
        SemanticLiteral literal = property.getRequiredProperty(PropertyDefinition.jcr_multiple);
        if (literal != null && literal.getString() != null)
        {
            isMultiple = Boolean.parseBoolean(literal.getString());
        }
        return isMultiple;
    }

    /**
     * Creates the node base.
     * 
     * @param name the name
     * @param clazz the clazz
     * @return the base node
     * @throws SWBException the sWB exception
     */
    public final BaseNode createNodeBase(String name, SemanticClass clazz) throws SWBException
    {
        if (canAddNode(clazz))
        {
            boolean hasOtherObjectWithSameName = false;
            if (!canAddSameNameSiblings(clazz, name))
            {
                hasOtherObjectWithSameName = existsSameNode(name);
            }
            if (hasOtherObjectWithSameName)
            {
                throw new SWBException("Already exists a node with the same name NodeName: " + name);
            }
            BaseNode newBaseNode;
            String uri = getSemanticObject().getModel().getObjectUri(UUID.randomUUID().toString(), clazz);
            SemanticObject object = getSemanticObject().getModel().createSemanticObject(uri, clazz);
            addPrimaryType(object.getSemanticClass());
            newBaseNode = new BaseNode(object, clazz);
            newBaseNode.setName(name);
            String path = getPath();
            if (path.equals("/"))
            {
                path += name;
            }
            else
            {
                path += "/" + name;
            }
            newBaseNode.setPath(path);
            newBaseNode.setParent(this);
            return newBaseNode;
        }
        else
        {
            throw new SWBException("The nodetype " + clazz.getPrefix() + ":" + clazz.getName() + " is not possible to add");
        }
    }

    /**
     * Creates the node base.
     * 
     * @param name the name
     * @param primaryNodeTypeName the primary node type name
     * @return the base node
     * @throws SWBException the sWB exception
     */
    public final BaseNode createNodeBase(String name, String primaryNodeTypeName) throws SWBException
    {
        if (primaryNodeTypeName == null)
        {
            throw new SWBException("The primaryNodeTypeName is null");
        }
        SemanticClass clazz = getSemanticClass(primaryNodeTypeName);
        return createNodeBase(name, clazz);
    }

    /**
     * Adds the versionable uuid.
     * 
     * @throws SWBException the sWB exception
     */
    private void addVersionableUuid() throws SWBException
    {
        SemanticProperty property = VersionHistory.jcr_versionableUuid;
        String value = getPropertyInternal(property, null);
        if (value == null)
        {
            String uuid = UUID.randomUUID().toString();
            setPropertyInternal(property, uuid);

        }
    }

    /**
     * Removes the mixin.
     * 
     * @param mixinName the mixin name
     * @throws SWBException the sWB exception
     */
    public final void removeMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        if (clazz != null)
        {
            Iterator<SemanticProperty> propeties = clazz.listProperties();
            while (propeties.hasNext())
            {
                SemanticProperty property = propeties.next();
                getSemanticObject().removeProperty(property);
            }
            Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
            while (classes.hasNext())
            {
                if (classes.next().equals(clazz))
                {
                    classes.remove();
                }
            }
        }
    }

    /**
     * Gets the semantic property.
     * 
     * @param propertyName the property name
     * @param clazz the clazz
     * @return the semantic property
     */
    public final SemanticProperty getSemanticProperty(String propertyName, SemanticClass clazz)
    {
        SemanticProperty getSemanticProperty = null;
        try
        {
            String uri = getUri(propertyName);
            getSemanticProperty = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
//            Iterator<SemanticProperty> properties = clazz.listProperties();
//            while (properties.hasNext())
//            {
//                SemanticProperty property = properties.next();
//                if (property.getURI().equals(uri))
//                {
//                    getSemanticProperty = property;
//                    break;
//                }
//            }
        }
        catch (SWBException e)
        {
            log.debug(e);
            getSemanticProperty = null;
        }
        return getSemanticProperty;
    }

    /**
     * Exists property.
     * 
     * @param propertyName the property name
     * @param clazz the clazz
     * @return true, if successful
     */
    public final boolean existsProperty(String propertyName, SemanticClass clazz)
    {
        boolean existsProperty = false;
        SemanticProperty property = getSemanticProperty(propertyName, clazz);
        if (property != null)
        {
            existsProperty = true;
        }
        return existsProperty;
    }

    /**
     * Exists property.
     * 
     * @param clazz the clazz
     * @param propertyName the property name
     * @return true, if successful
     */
    public final boolean existsProperty(SemanticClass clazz, String propertyName)
    {
        boolean existsProperty = false;
        try
        {
            String uri = getUri(propertyName);
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                if (properties.next().getURI().equals(uri))
                {
                    existsProperty = true;
                    break;
                }
            }
            if (!existsProperty)
            {
                SemanticProperty prop =null;
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
                                    if(semanticProperty.getName().equals(propertyName))
                                    {
                                        prop=semanticProperty;
                                        break;
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
                if(prop==null)
                {
                    prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(uri);
                }
                if (prop != null)
                {
                    if (prop.isDataTypeProperty())
                    {
                        String value = getSemanticObject().getProperty(prop);
                        if (value != null)
                        {
                            existsProperty = true;
                        }
                    }
                    else
                    {
                        SemanticObject value=getSemanticObject().getObjectProperty(prop);
                        if (value != null)
                        {
                            existsProperty = true;
                        }
                    }
                }
            }
        }
        catch (SWBException e)
        {
            log.debug(e);
            existsProperty = false;
        }
        return existsProperty;
    }

    /**
     * Gets the uri.
     * 
     * @param name the name
     * @return the uri
     * @throws SWBException the sWB exception
     */
    public String getUri(String name) throws SWBException
    {
        String uri;
        String[] values = name.split(":");
        switch (values.length)
        {
            case 0:
                throw new SWBException("Invalid Name");
            case 1:
                uri = getSemanticObject().getURI() + name;
                break;
            case 2:
                if (values[0].trim().equals(""))
                {
                    uri = this.getURI().replaceAll("#", "/") + "#" + name;
                }
                else
                {                    
                    String namespace = listUris().get(values[0]);
                    if (namespace == null)
                    {
                        log.trace("The uri for the prefix "+values[0]+" was not found");
                        throw new SWBException("The namespace for the prefix " + values[0] + WAS_NOT_FOUND);
                    }
                    else
                    {
                        if (namespace.endsWith("#"))
                        {
                            uri = namespace + values[1];
                        }
                        else
                        {
                            uri = namespace + "#" + values[1];
                        }
                    }
                }
                break;
            default:
                throw new SWBException("Invalid Name");

        }
        return uri;
    }

    /**
     * Register custom property.
     * 
     * @param name the name
     * @param type the type
     * @param clazz the clazz
     * @return the semantic property
     * @throws SWBException the sWB exception
     */
    public SemanticProperty registerCustomProperty(String name, String type, SemanticClass clazz) throws SWBException
    {
        if (!existsProperty(name, clazz))
        {
            String uri = this.getURI().replaceAll("#", "/") + "#" + name;
            SemanticProperty prop = getSemanticObject().getModel().createSemanticProperty(uri, this.getSemanticObject().getSemanticClass(), SemanticVocabulary.OWL_DATATYPEPROPERTY, type);
            return prop;
        }
        else
        {
            throw new SWBException("the property already exists");
        }
    }

    /**
     * Gets the versions.
     * 
     * @return the versions
     * @throws SWBException the sWB exception
     */
    public BaseNode[] getVersions() throws SWBException
    {
        if (this.isVersionHistoryNode())
        {
            ArrayList<BaseNode> versions = new ArrayList<BaseNode>();
            GenericIterator<BaseNode> nodes = this.listNodes();
            while (nodes.hasNext())
            {
                BaseNode node = nodes.next();
                if (node.isVersionNode())
                {
                    versions.add(node);
                }
            }
            return versions.toArray(new BaseNode[versions.size()]);
        }
        else
        {
            throw new SWBException("The node is not a version history");
        }

    }

    /**
     * Checks if is lockable.
     * 
     * @return true, if is lockable
     */
    public final boolean isLockable()
    {
        return isNodeType(Lockable.mix_Lockable);
    }

    /**
     * List uris.
     * 
     * @return the hashtable
     */
    public static Hashtable<String, String> listUris()
    {
        Iterator<SemanticClass> tpcit = nt_BaseNode.listSubClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            // solo agregar espacios de nombre de clases abajo de base
            if (tpc.getPrefix() != null)
            {
                if (!namespaces.containsKey(tpc.getPrefix()))
                {
                    int pos = tpc.getURI().indexOf("#");
                    String uri = tpc.getURI();
                    if (pos != -1)
                    {
                        uri = uri.substring(0, pos + 1);
                    }
                    namespaces.put(tpc.getPrefix(), uri);
                }
                Iterator<SemanticProperty> tppit = tpc.listProperties();
                while (tppit.hasNext())
                {
                    SemanticProperty tpp = tppit.next();
                    if (tpp.getPrefix() != null)
                    {
                        if (!namespaces.containsKey(tpp.getPrefix()))
                        {
                            String uri = tpp.getURI();
                            int pos = tpp.getURI().indexOf("#");
                            if (pos != -1)
                            {
                                uri = uri.substring(0, pos + 1);
                            }
                            namespaces.put(tpp.getPrefix(), uri);
                        }
                    }
                }
            }
        }
        return namespaces;
    }

    /**
     * Checks if is referenceable.
     * 
     * @return true, if is referenceable
     */
    public final boolean isReferenceable()
    {
        return (isNodeType(Referenceable.mix_Referenceable) || isVersionable());
    }

    /**
     * Checks if is versionable.
     * 
     * @return true, if is versionable
     */
    public final boolean isVersionable()
    {
        return isNodeType(Versionable.mix_Versionable);
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.GenericObjectBase#toString()
     */
    @Override
    public String toString()
    {
        return this.getName();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.GenericObjectBase#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if (obj instanceof BaseNode)
        {
            equals = ((BaseNode) obj).getSemanticObject().equals(getSemanticObject());
        }
        return equals;
    }

    /* (non-Javadoc)
     * @see org.semanticwb.model.base.GenericObjectBase#hashCode()
     */
    @Override
    public int hashCode()
    {
        return getSemanticObject().hashCode();
    }

    /* (non-Javadoc)
     * @see org.semanticwb.repository.base.BaseNodeBase#getParent()
     */
    @Override
    public org.semanticwb.repository.BaseNode getParent()
    {
        try
        {
            return super.getParent();
        }
        catch (Throwable e)
        {
            org.semanticwb.platform.SemanticObject obj = getSemanticObject().getObjectProperty(swbrep_parentNode);
            if (obj != null)
            {
                return new BaseNode(obj);
            }
            else
            {
                return null;
            }

        }
    }
}
