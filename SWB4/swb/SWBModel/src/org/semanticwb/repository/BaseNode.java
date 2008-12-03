package org.semanticwb.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
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

public class BaseNode extends BaseNodeBase
{

    static Logger log = SWBUtils.getLogger(BaseNode.class);
    private static final String JCR_FROZENNODE_NAME = "jcr:frozenNode";
    private static final String JCR_VERSIONLABELS_NAME = "jcr:versionLabels";
    private static final String JCR_VERSION_NAME = "jcr:version";
    private static final String ONPARENTVERSION_COPY = "COPY";
    private static final String ONPARENTVERSION_VERSION = "VERSION";
    private static final String WAS_NOT_FOUND = " was not found";
    private static final Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public BaseNode(SemanticObject base)
    {
        super(base);
        addPrimaryType(getSemanticObject().getSemanticClass());
    }

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

    public String getPrimaryItemName(SemanticClass clazz)
    {
        String getPrimaryItemName = null;
        SemanticLiteral literal = clazz.getRequiredProperty(vocabulary.jcr_primaryItemName);
        if (literal != null && literal.getString() != null)
        {
            getPrimaryItemName = literal.getString();
        }
        return getPrimaryItemName;
    }

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

    public boolean hasOrderableChildNodes(SemanticClass clazz)
    {
        boolean hasOrderableChildNodes = false;
        SemanticLiteral literal = clazz.getRequiredProperty(vocabulary.jcr_orderableChildNodes);
        if (literal != null && literal.getString() != null)
        {
            hasOrderableChildNodes = Boolean.parseBoolean(literal.getString());
        }
        return hasOrderableChildNodes;
    }

    public String getOnParentVersion(BaseNode node)
    {
        String value = ONPARENTVERSION_VERSION;
        SemanticProperty onParentVersionProperty = vocabulary.jcr_onParentVersion;
        Iterator<SemanticLiteral> literals = node.getSemanticObject().getSemanticClass().listRequiredProperties(onParentVersionProperty);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if (literal != null && literal.getString() != null)
            {
                value = literal.getString();
                break;
            }
        }
        return value;
    }

    public String getOnParentVersion(SemanticProperty property)
    {
        String value = ONPARENTVERSION_COPY;
        SemanticProperty onParentVersionProperty = vocabulary.jcr_onParentVersion;
        SemanticLiteral literal = property.getRequiredProperty(onParentVersionProperty);
        if (literal != null)
        {
            value = literal.getString();
        }
        return value;
    }

    public void save() throws SWBException
    {
        GenericIterator<BaseNode> childs = listNodes();
        while (childs.hasNext())
        {
            BaseNode child = childs.next();
            child.save();
        }
        checkVersionable();
        checkSave();


    }

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

    public Calendar getCreated() throws SWBException
    {
        SemanticProperty jcr_created = vocabulary.jcr_created;
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

    }

    public boolean isVersionHistoryNode()
    {
        return isNodeType(vocabulary.nt_VersionHistory);
    }

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

    public boolean isVersionNode()
    {
        return isNodeType(vocabulary.nt_Version);
    }

    private void unLock() throws SWBException
    {
        BaseNode lockNode = getLockBaseNode();
        if (lockNode.equals(this))
        {
            SemanticProperty isDeepProperty = vocabulary.jcr_lockIsDeep;
            SemanticProperty lockownerProperty = vocabulary.jcr_lockOwner;
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

    private boolean isDeepLock(BaseNode node)
    {
        boolean isDeepLock = false;

        SemanticProperty isDeepProperty = vocabulary.jcr_lockIsDeep;
        String value = node.getPropertyInternal(isDeepProperty, "false");
        if (value != null)
        {
            isDeepLock = Boolean.parseBoolean(value);
        }

        return isDeepLock;
    }

    public String getLockOwner()
    {
        String getLockOwner = null;
        BaseNode lockNode = getLockBaseNode();
        if (lockNode != null)
        {

            SemanticProperty lockownerProperty = vocabulary.jcr_lockOwner;
            getLockOwner = lockNode.getPropertyInternal(lockownerProperty, null);

        }
        return getLockOwner;
    }

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

    private boolean hasLock(BaseNode node)
    {
        boolean hasLock = false;

        SemanticProperty lockownerProperty = vocabulary.jcr_lockOwner;
        String value = node.getPropertyInternal(lockownerProperty, null);
        if (value != null)
        {
            hasLock = true;
        }

        return hasLock;
    }

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
                    SemanticProperty propertyLockOwner = vocabulary.jcr_lockOwner;
                    SemanticProperty propertyIsDeep = vocabulary.jcr_lockIsDeep;
                    setPropertyInternal(propertyLockOwner, lockOwner);
                    setPropertyInternal(propertyIsDeep, new Boolean(lockIsDeep).toString());
                }
            }
        }
        else
        {
            throw new SWBException("The node " + this.getName() + " is not lockable");
        }
    }

    public boolean canAddSameNameSiblings(String name)
    {
        boolean canAddSameNameSiblings = true;
        try
        {
            SemanticClass clazz = getSemanticClass(name);
            canAddSameNameSiblings = canAddSameNameSiblings(clazz);
        }
        catch (SWBException swbe)
        {
            canAddSameNameSiblings = true;
        }
        return canAddSameNameSiblings;
    }

    private boolean canAddSameNameSiblings(SemanticClass clazz)
    {
        boolean canAddSameNameSiblings = true;
        SemanticProperty prop = vocabulary.jcr_sameNameSiblings;
        SemanticLiteral literal = clazz.getRequiredProperty(prop);
        if (literal != null && literal.getString() != null)
        {
            canAddSameNameSiblings = Boolean.parseBoolean(literal.getString());
        }
        return canAddSameNameSiblings;
    }

    public boolean isMandatory(SemanticProperty property)
    {
        boolean isMandatory = false;
        SemanticLiteral literal = property.getRequiredProperty(vocabulary.jcr_mandatory);
        if (literal != null && literal.getString() != null)
        {
            isMandatory = Boolean.parseBoolean(literal.getString());
        }
        return isMandatory;
    }

    public boolean isMandatory()
    {
        boolean isMandatory = false;
        SemanticLiteral literal = getSemanticObject().getSemanticClass().getRequiredProperty(vocabulary.jcr_mandatory);
        if (literal != null && literal.getString() != null)
        {
            isMandatory = Boolean.parseBoolean(literal.getString());
        }
        return isMandatory;
    }

    public boolean isMandatory(SemanticClass clazz)
    {
        boolean isRequired = false;
        SemanticLiteral literal = clazz.getRequiredProperty(vocabulary.jcr_mandatory);
        if (literal != null)
        {
            isRequired = literal.getBoolean();
        }
        return isRequired;
    }

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

    public void checkRequiredProperties() throws SWBException
    {
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if (isMandatory(property))
                {
                    if (property.isObjectProperty())
                    {
                        SemanticObject objectValue = getSemanticObject().getObjectProperty(property, null);
                        if (objectValue == null)
                        {
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null for the node " + this.getName() + " of type " + this.getSemanticObject().getSemanticClass().getPrefix() + ":" + this.getSemanticObject().getSemanticClass().getName());
                        }
                        else
                        {
                            BaseNode nodeChild = new BaseNode(objectValue);
                            nodeChild.checkRequiredProperties();
                        }
                    }
                    else
                    {
                        String value = getPropertyInternal(property, null);
                        if (value == null)
                        {
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null for the node " + this.getName() + " of type " + this.getSemanticObject().getSemanticClass().getPrefix() + ":" + this.getSemanticObject().getSemanticClass().getName());
                        }
                    }
                }
            }
        }
    }

    public void checkSave() throws SWBException
    {
        checkRequiredProperties();
        checkRequiredNodes();

    }

    private void checkRequiredNodes() throws SWBException
    {
        for (SemanticObject childDefinition : getChildNodeDefinition(this.getSemanticObject().getSemanticClass()))
        {
            String value = childDefinition.getProperty(vocabulary.jcr_mandatory);
            if (value != null)
            {
                boolean mandatory = Boolean.parseBoolean(value);
                if (mandatory)
                {
                    String name = childDefinition.getProperty(vocabulary.jcr_name);
                    if (name != null && !name.equals("*"))
                    {
                        BaseNode node = findNode(name);
                        if (node == null)
                        {
                            throw new SWBException("The node with name " + name + WAS_NOT_FOUND + " and is required by the node " + this.getName());
                        }
                    }
                }
            }
        }
        GenericIterator<BaseNode> nodes = listNodes();
        while (nodes.hasNext())
        {
            BaseNode node = nodes.next();
            node.checkRequiredNodes();
        }
    }

    private BaseNode findNode(String name)
    {
        BaseNode findNode = null;
        GenericIterator<BaseNode> nodes = listNodes();
        while (nodes.hasNext())
        {
            BaseNode node = nodes.next();
            String nodeName = node.getName();
            if (nodeName != null && nodeName.equals(name))
            {
                findNode = node;
                break;
            }
        }
        return findNode;
    }

    private void setPropertyInternal(SemanticProperty property, String value)
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

    private String getPropertyInternal(SemanticProperty property, String value)
    {
        return getSemanticObject().getProperty(property, value);
    }

    public String getProperty(SemanticProperty property) throws SWBException
    {
        String getProperty = null;
        if (isInternal(property))
        {
            throw new SWBException("the property " + property.getPrefix() + ":" + property.getName() + " is internal");
        }
        getProperty = getSemanticObject().getProperty(property);
        return getProperty;
    }

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

    public final String getUUID() throws SWBException
    {
        SemanticProperty property = vocabulary.jcr_uuid;
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

    private final void addPrimaryType(SemanticClass clazz)
    {
        SemanticProperty jcr_primaryType = vocabulary.jcr_primaryType;
        if (jcr_primaryType != null)
        {
            if (getPropertyInternal(jcr_primaryType, null) == null)
            {
                setPropertyInternal(jcr_primaryType, clazz.getPrefix() + ":" + clazz.getName());
            }
        }
    }

    public final boolean isAutocreated(SemanticClass clazz)
    {
        boolean isAutocreated = false;
        SemanticLiteral value = clazz.getRequiredProperty(vocabulary.jcr_autoCreated);
        if (value != null && value.getString() != null)
        {
            isAutocreated = Boolean.parseBoolean(value.getString());
        }
        return isAutocreated;
    }

    public final boolean isAutocreated(SemanticProperty property)
    {
        boolean isAutocreated = false;
        SemanticLiteral value = property.getRequiredProperty(vocabulary.jcr_autoCreated);
        if (value != null && value.getString() != null)
        {
            isAutocreated = Boolean.parseBoolean(value.getString());
        }
        return isAutocreated;
    }

    public final boolean isProtected(SemanticProperty property)
    {
        boolean isProtected = false;
        try
        {
            SemanticLiteral literal = property.getRequiredProperty(vocabulary.jcr_protected);
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

    public final boolean allowsSameNameSiblings(SemanticClass clazz)
    {
        boolean allowsSameNameSiblings = false;
        try
        {
            SemanticLiteral literal = clazz.getRequiredProperty(vocabulary.jcr_sameNameSiblings);
            if (literal != null)
            {
                allowsSameNameSiblings = literal.getBoolean();
            }
        }
        catch (Exception e)
        {
            allowsSameNameSiblings = false;
        }
        return allowsSameNameSiblings;
    }

    public final boolean isProtected(SemanticClass clazz)
    {
        boolean isProtected = false;
        try
        {
            SemanticLiteral literal = clazz.getRequiredProperty(vocabulary.jcr_protected);
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

    public final boolean isInternal(SemanticProperty property)
    {
        boolean isInternal = false;
        try
        {
            SemanticProperty internal = vocabulary.swbrep_internal;
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

    private final void addUUID()
    {
        if (isReferenceable() || isVersionable())
        {
            SemanticProperty jcr_uuid = vocabulary.jcr_uuid;
            String value = getPropertyInternal(jcr_uuid, null);
            if (value == null)
            {
                setPropertyInternal(jcr_uuid, UUID.randomUUID().toString());
            }
        }
    }

    public final String[] getSuperTypes(SemanticClass clazz)
    {
        HashSet<String> superTypes = new HashSet<String>();
        Iterator<SemanticLiteral> literals = listSemanticLiterals(clazz, vocabulary.jcr_supertypes);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if (literal != null && literal.getString() != null)
            {
                superTypes.add(literal.getString());
            }
        }

        return superTypes.toArray(new String[superTypes.size()]);
    }

    public final String[] getSuperTypes() throws SWBException
    {
        HashSet<String> superTypes = new HashSet<String>();
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticLiteral> literals = listSemanticLiterals(clazz, vocabulary.jcr_supertypes);
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

    public final boolean isMixIn()
    {
        return isMixIn(getSemanticObject().getSemanticClass());
    }

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

    private final SemanticLiteral getSemanticLiteral(SemanticClass clazz, SemanticProperty property) throws SWBException
    {
        SemanticLiteral literal = null;
        if (property != null)
        {
            literal = clazz.getRequiredProperty(property);
        }
        return literal;
    }

    private final Iterator<SemanticLiteral> listSemanticLiterals(SemanticClass clazz, SemanticProperty property)
    {
        return clazz.listRequiredProperties(property);
    }

    public final boolean isMixIn(SemanticClass clazz)
    {
        boolean isMixIn = false;
        try
        {
            SemanticLiteral literal = getSemanticLiteral(clazz, vocabulary.mix_mixin);
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

    public final boolean isMixIn(String nodeType) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(nodeType);
        return isMixIn(clazz);
    }

    private BaseNode addNodeToProperty(SemanticProperty property, SemanticClass clazz, String name,String path) throws SWBException
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

    private boolean isFrozenNode()
    {
        return isNodeType(vocabulary.nt_FrozenNode);
    }

    private void copyNodesToFrozenNode(BaseNode frozenNode) throws SWBException
    {
        if (frozenNode.isFrozenNode())
        {
            GenericIterator<BaseNode> nodes = this.listNodes();
            while (nodes.hasNext())
            {
                BaseNode childNode = nodes.next();
                String onParentVersion = getOnParentVersion(childNode);
                if (onParentVersion.equals(ONPARENTVERSION_COPY))
                {
                    BaseNode frozenChild = frozenNode.createNodeBase(JCR_FROZENNODE_NAME, vocabulary.nt_FrozenNode);
                    childNode.copyPropertiesToFrozenNode(frozenChild);
                    childNode.addFrozenProperties(frozenChild.getSemanticObject());
                    childNode.copyPropertiesToFrozenNode(frozenChild);
                }
            }
        }
        else
        {
            throw new SWBException("The node is not a frozen node");
        }
    }

    private void copyPropertiesToFrozenNode(BaseNode frozenNode) throws SWBException
    {
        if (frozenNode.isFrozenNode())
        {

            Iterator<SemanticProperty> properties = this.getSemanticObject().getSemanticClass().listProperties();
            boolean abort = false;
            GenericIterator<BaseNode> nodes = this.listNodes();
            while (nodes.hasNext())
            {
                BaseNode childNode = nodes.next();
                String onParentVersion = getOnParentVersion(childNode);
                if (onParentVersion.equals("ABORT"))
                {
                    abort = true;
                    break;
                }
            }
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                String onParentVersion = getOnParentVersion(property);
                if (onParentVersion.equals("ABORT"))
                {
                    abort = true;
                    break;
                }
            }
            if (!abort)
            {
                properties = this.getSemanticObject().getSemanticClass().listProperties();
                while (properties.hasNext())
                {
                    SemanticProperty property = properties.next();
                    if (!isInternal(property))
                    {
                        String onParentVersion = getOnParentVersion(property);
                        if (property.isDataTypeProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                        {
                            frozenNode.setPropertyInternal(property, getProperty(property));
                        }
                        if (property.isObjectProperty() && onParentVersion.equals(ONPARENTVERSION_COPY))
                        {
                            frozenNode.getSemanticObject().setObjectProperty(property, this.getSemanticObject().getObjectProperty(property));
                        }
                    }
                }
                copyNodesToFrozenNode(frozenNode);
            }
            else
            {
                throw new SWBException("A property has the onparentversion equals to abort");
            }
        }
        else
        {
            throw new SWBException("The node is not a frozen node");
        }
    }

    private void addFrozenProperties(SemanticObject frozenNode) throws SWBException
    {
        SemanticProperty jcr_frozenUuid = vocabulary.jcr_frozenUuid;
        SemanticProperty jcr_frozenPrimaryType = vocabulary.jcr_frozenPrimaryType;
        SemanticProperty frozenMixinTypes = vocabulary.jcr_frozenMixinTypes;
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

    private void addRootNodeToHistory(BaseNode historyNode) throws SWBException
    {
        if (historyNode.isVersionHistoryNode())
        {            
            BaseNode ntVersion = historyNode.createNodeBase(JCR_VERSION_NAME, vocabulary.nt_Version);
            BaseNode versionLabels = historyNode.createNodeBase(JCR_VERSIONLABELS_NAME, vocabulary.nt_versionLabels);
            BaseNode ntFrozenNode = ntVersion.createNodeBase(JCR_FROZENNODE_NAME, vocabulary.nt_FrozenNode);
            addFrozenProperties(ntFrozenNode.getSemanticObject());
            this.getSemanticObject().setObjectProperty(vocabulary.jcr_baseVersion, ntVersion.getSemanticObject());
        }
    }

    private void createVersionHistoryNode() throws SWBException
    {
        if (isVersionable())
        {
            SemanticProperty property = vocabulary.jcr_versionHistory;
            String path=this.getPath()+"/"+property.getPrefix()+":"+property.getName();
            BaseNode historyNode = addNodeToProperty(property, vocabulary.nt_VersionHistory, UUID.randomUUID().toString(),path);
            historyNode.setPropertyInternal(vocabulary.jcr_uuid, UUID.randomUUID().toString());
            addRootNodeToHistory(historyNode);
        }
    }

    public BaseNode[] getSuccessors() throws SWBException
    {
        return null;
    }

    public BaseNode[] getPredecessors() throws SWBException
    {
        return null;
    }

    public BaseNode getHistoryNode() throws SWBException
    {
        BaseNode getHistoryNode = null;
        SemanticProperty property = vocabulary.jcr_versionHistory;
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

    public boolean isChekedin()
    {
        boolean isChekedin = false;
        if (isVersionable())
        {
            SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
            String value = getPropertyInternal(isCheckoutPropety, null);
            if (value != null)
            {
                isChekedin = !Boolean.parseBoolean(value);
            }
        }
        return isChekedin;
    }

    public boolean isChekedOut()
    {
        boolean isChekedOut = false;

        SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
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

    public BaseNode checkin() throws SWBException
    {
        if (this.isVersionable() && isChekedOut())
        {
            SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
            setPropertyInternal(isCheckoutPropety, null);
            BaseNode versionNode = addVersionToHistoryNode();
            return versionNode;
        }
        else
        {
            throw new SWBException("The node is not versionable or not is checkedout");
        }
    }

    public void checkout() throws SWBException
    {
        SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
        setPropertyInternal(isCheckoutPropety, "true");
    }

    public BaseNode getBaseVersion()
    {
        if (isVersionable())
        {
           SemanticObject baseVersion=getSemanticObject().getObjectProperty(vocabulary.jcr_baseVersion);
           if(baseVersion!=null)
           {
               return new BaseNode(baseVersion);
           }
        }
        return null;
    }
    private BaseNode addVersionToHistoryNode() throws SWBException
    {
        BaseNode addVersionToHistoryNode = null;
        if (isVersionable())
        {
            BaseNode versionHistory = getHistoryNode();
            if (versionHistory != null)
            {
                BaseNode[] predecessors = versionHistory.getVersions();
                BaseNode ntVersion = versionHistory.createNodeBase(JCR_VERSION_NAME, vocabulary.nt_Version);
                // TODO: DEBE AGREGAR REFERENCIA A LAS PREDECEDORAS, PERO FALTA METODO PARA AGREGAR
                for (BaseNode predecessor : predecessors)
                {
                    predecessor.getSemanticObject().setObjectProperty(vocabulary.jcr_successors, ntVersion.getSemanticObject());
                    ntVersion.getSemanticObject().setObjectProperty(vocabulary.jcr_predecessors, predecessor.getSemanticObject());
                }
                BaseNode ntFrozenNode = ntVersion.createNodeBase(JCR_FROZENNODE_NAME, vocabulary.nt_FrozenNode);
                copyPropertiesToFrozenNode(ntFrozenNode);
                addFrozenProperties(ntFrozenNode.getSemanticObject());
                addVersionToHistoryNode = ntVersion;
                //update the BaseVersion of this node
                this.getSemanticObject().setObjectProperty(vocabulary.jcr_baseVersion, ntVersion.getSemanticObject());

            }
        }
        return addVersionToHistoryNode;
    }

    private void checkVersionable() throws SWBException
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

    public final void addMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        SemanticProperty property = vocabulary.jcr_mixinTypes;
        setPropertyInternal(property, mixinName);
        this.getSemanticObject().addSemanticClass(clazz);
        addVersionableUuid();
        addUUID();
        addCreatedProperty();
    }

    public SemanticObject[] getChildNodeDefinition(SemanticClass clazz)
    {
        ArrayList<SemanticObject> getChildNodeDefinition = new ArrayList<SemanticObject>();
        SemanticProperty property = vocabulary.jcr_childNodeDefinition;
        Iterator<SemanticObject> definitions = clazz.listObjectRequiredProperties(property);
        while (definitions.hasNext())
        {
            SemanticObject object = definitions.next();
            getChildNodeDefinition.add(object);
        }
        return getChildNodeDefinition.toArray(new SemanticObject[getChildNodeDefinition.size()]);
    }

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

    public boolean canAddNode(SemanticClass clazzSurce, SemanticClass clazzDest)
    {
        boolean canAddNode = false;
        SemanticObject[] childDefinitions = getChildNodeDefinition(clazzDest);
        if (childDefinitions.length > 0)
        {
            for (SemanticObject childDefinition : childDefinitions)
            {
                String jcr_requiredType = childDefinition.getProperty(vocabulary.jcr_requiredPrimaryTypes);
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
            canAddNode = false;
        }
        return canAddNode;
    }

    private void addCreatedProperty() throws SWBException
    {
        if (isVersionNode())
        {
            SemanticProperty jcr_created = vocabulary.jcr_created;
            if (jcr_created != null)
            {
                String value = iso8601dateFormat.format(calendar.getTime());
                setPropertyInternal(jcr_created, value);
            }
        }
    }

    private boolean canAddNode(SemanticClass clazzSurce)
    {
        return canAddNode(clazzSurce, getSemanticObject().getSemanticClass());
    }

    public boolean canAddProperty(String name, SemanticClass clazz)
    {
        boolean canAddProperty = false;
        return canAddProperty;
    }

    public boolean canAddAnyProperty(SemanticClass clazz)
    {
        boolean canAddAnyProperty = false;

        for (SemanticObject propertyDefinition : this.getPropertyDefinition(clazz))
        {
            String name = propertyDefinition.getProperty(vocabulary.jcr_name);
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

    private SemanticObject[] getPropertyDefinition(SemanticClass clazz)
    {
        ArrayList<SemanticObject> propertyDefinitions = new ArrayList<SemanticObject>();
        Iterator<SemanticObject> objects = clazz.listObjectRequiredProperties(vocabulary.jcr_propertyDefinition);
        while (objects.hasNext())
        {
            propertyDefinitions.add(objects.next());
        }
        return propertyDefinitions.toArray(new SemanticObject[propertyDefinitions.size()]);
    }

    public boolean isProtected()
    {
        boolean isProtected = false;
        Iterator<SemanticLiteral> literals = getSemanticObject().getSemanticClass().listRequiredProperties(vocabulary.jcr_protected);
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

    public boolean isMultiple(SemanticProperty property)
    {
        boolean isMultiple = false;
        SemanticLiteral literal = property.getRequiredProperty(vocabulary.jcr_multiple);
        if (literal != null && literal.getString() != null)
        {
            isMultiple = Boolean.parseBoolean(literal.getString());
        }
        return isMultiple;
    }

    public final BaseNode createNodeBase(String name, SemanticClass clazz) throws SWBException
    {
        if (canAddNode(clazz))
        {
            boolean hasOtherObjectWithSameName = false;
            if (!canAddSameNameSiblings(clazz))
            {
                hasOtherObjectWithSameName = existsSameNode(name);
            }
            if (hasOtherObjectWithSameName)
            {
                throw new SWBException("Already exists a node with the same name");
            }
            BaseNode newBaseNode;
            String uri = getSemanticObject().getModel().getObjectUri(UUID.randomUUID().toString(), clazz);
            SemanticObject object = getSemanticObject().getModel().createSemanticObject(uri, clazz);
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

    public final BaseNode createNodeBase(String name, String primaryNodeTypeName) throws SWBException
    {
        if (primaryNodeTypeName == null)
        {
            throw new SWBException("The primaryNodeTypeName is null");
        }
        SemanticClass clazz = getSemanticClass(primaryNodeTypeName);
        return createNodeBase(name, clazz);
    }

    private void addVersionableUuid() throws SWBException
    {
        SemanticProperty property = vocabulary.jcr_versionableUuid;
        String value = getPropertyInternal(property, null);
        if (value == null)
        {
            String uuid = UUID.randomUUID().toString();
            setPropertyInternal(property, uuid);

        }
    }

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

    public final SemanticProperty getSemanticProperty(String propertyName, SemanticClass clazz)
    {
        SemanticProperty getSemanticProperty = null;
        try
        {
            String uri = getUri(propertyName);
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if (property.getURI().equals(uri))
                {
                    getSemanticProperty = property;
                    break;
                }
            }
        }
        catch (SWBException e)
        {
            log.debug(e);
            getSemanticProperty = null;
        }
        return getSemanticProperty;
    }

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
        }
        catch (SWBException e)
        {
            log.debug(e);
            existsProperty = false;
        }
        return existsProperty;
    }

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
                    String namespace = vocabulary.listUris().get(values[0]);
                    if (namespace == null)
                    {
                        throw new SWBException("The namespace for the prefix " + values[0] + WAS_NOT_FOUND);
                    }
                    else
                    {
                        uri = namespace + values[1];
                    }
                }
                break;
            default:
                throw new SWBException("Invalid Name");

        }
        return uri;
    }

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

    public final boolean isLockable()
    {
        return isNodeType(vocabulary.mix_Lockable);
    }

    public final boolean isReferenceable()
    {
        return (isNodeType(vocabulary.mix_Referenceable) || isVersionable());
    }

    public final boolean isVersionable()
    {
        return isNodeType(vocabulary.mix_Versionable);
    }

    @Override
    public String toString()
    {
        return this.getName();
    }

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

    @Override
    public int hashCode()
    {
        return getSemanticObject().hashCode();
    }
}
