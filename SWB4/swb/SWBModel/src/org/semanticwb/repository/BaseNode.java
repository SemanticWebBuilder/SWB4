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
import org.semanticwb.SWBPlatform;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.platform.SemanticLiteral;

public class BaseNode extends BaseNodeBase
{

    private static final String JCR_CHILDDEFINITION_PROPERTY = "jcr:childNodeDefinition";
    private static final String JCR_PROTECTED_PROPERTY = "jcr:protected";
    private static final String JCR_MANDATORY_PROPERTY = "jcr:mandatory";
    private static final String MIX_MIXIN_PROPERTY = "mix:mixin";
    private static final String ONPARENTVERSION_COPY = "COPY";
    private static final String ONPARENTVERSION_VERSION = "VERSION";
    private static final String JCR_SUPERTYPES_PROPERTY = "jcr:supertypes";
    private static final String WAS_NOT_FOUND = " was not found";
    private static final Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public BaseNode(SemanticObject base)
    {
        super(base);

    }

    private BaseNode(SemanticObject base, SemanticClass clazz) throws SWBException
    {
        super(base);
        addPrimaryType(clazz);
        String[] superTypes = getSuperTypes();
        for ( String superType : superTypes )
        {
            SemanticClass superTypeClass = getSemanticClass(superType);
            if ( isMixIn(superTypeClass) )
            {
                addMixin(superType);
            }
        }
    }

    private String getOnParentVersion(BaseNode node) throws SWBException
    {
        String value = ONPARENTVERSION_VERSION;
        SemanticProperty onParentVersionProperty = vocabulary.jcr_onParentVersion;
        Iterator<SemanticLiteral> literals = node.getSemanticObject().getSemanticClass().listRequiredProperties(onParentVersionProperty);
        while (literals.hasNext())
        {
            SemanticLiteral literal = literals.next();
            if ( literal != null && literal.getString() != null )
            {
                value = literal.getString();
                break;
            }
        }
        return value;
    }

    private String getOnParentVersion(SemanticProperty property) throws SWBException
    {
        String value = ONPARENTVERSION_COPY;
        SemanticProperty onParentVersionProperty = vocabulary.jcr_onParentVersion;
        SemanticLiteral literal = property.getRequiredProperty(onParentVersionProperty);
        if ( literal != null )
        {
            value = literal.getString();
        }
        return value;
    }

    public void save() throws SWBException
    {
        checkVersionable();
        checkProperties();
        setModified(false);
        setNew(false);
    }

    private boolean isNodeType(SemanticClass clazz)
    {
        boolean isVersionNode = false;
        Iterator<SemanticClass> classes = this.getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazzNode = classes.next();
            if ( clazzNode.equals(clazz) )
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
        if ( value == null )
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
            catch ( ParseException pse )
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
        if ( lockNode.equals(this) )
        {
            SemanticProperty isDeepProperty = vocabulary.jcr_lockIsDeep;
            SemanticProperty lockownerProperty = vocabulary.jcr_lockOwner;
            lockNode.setPropertyInternal(lockownerProperty, null);
            lockNode.setPropertyInternal(isDeepProperty, null);
            String isdeep = lockNode.getPropertyInternal(isDeepProperty, null);
            String lockOwner = lockNode.getPropertyInternal(lockownerProperty, null);
            if ( isdeep != null || lockOwner != null )
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
        if ( isLockable() && isLocked() )
        {
            String nodeLockOwner = getLockOwner();
            if ( comparator.canUnlockNodeLockedByUser(nodeLockOwner, unlockOwner) )
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
        if ( lockNode != null && isDeepLock(lockNode) )
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
        if ( value != null )
        {
            isDeepLock = Boolean.parseBoolean(value);
        }

        return isDeepLock;
    }

    public String getLockOwner()
    {
        String getLockOwner = null;
        BaseNode lockNode = getLockBaseNode();
        if ( lockNode != null )
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
        if ( lockNode != null )
        {
            if ( lockNode == this || isDeepLock(lockNode) )
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
        if ( value != null )
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
            if ( hasLock(thisNode) )
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
        if ( isLockable() )
        {
            if ( lockOwner == null || lockOwner.trim().equals("") )
            {
                throw new SWBException("The lockOwner is invalid, is null or empty");
            }
            else
            {
                String lockOwnerValue = getLockOwner();
                if ( lockOwnerValue == null )
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

    private SemanticLiteral getPropertyOfProperty(SemanticProperty property, String name) throws SWBException
    {
        SemanticLiteral getPropertyOfProperty = null;
        String uri = getUri(name);
        SemanticProperty propertyRequired = getSemanticObject().getModel().getSemanticProperty(uri);
        if ( propertyRequired != null )
        {
            getPropertyOfProperty = property.getRequiredProperty(propertyRequired);
        }
        return getPropertyOfProperty;
    }

    private boolean isRequired(SemanticProperty property)
    {
        boolean isRequired = false;
        try
        {
            SemanticLiteral literal = getPropertyOfProperty(property, JCR_MANDATORY_PROPERTY);
            if ( literal != null )
            {
                isRequired = literal.getBoolean();
            }
        }
        catch ( SWBException swbe )
        {
            isRequired = false;
        }
        return isRequired;
    }

    public boolean canAddMixin(String mixinName)
    {
        boolean canAddMixin = true;
        try
        {
            SemanticClass mixinClass = getSemanticClass(mixinName);
            for ( SemanticClass clazz : getMixInNodeTypes() )
            {
                if ( clazz.equals(mixinClass) )
                {
                    canAddMixin = false;
                }
                Iterator<SemanticClass> superclasses = clazz.listSuperClasses(true);
                while (superclasses.hasNext())
                {
                    if ( superclasses.next().equals(mixinClass) )
                    {
                        canAddMixin = false;
                    }
                }
            }
        }
        catch ( SWBException swbe )
        {
            canAddMixin = false;
        }
        return canAddMixin;
    }

    public void checkProperties() throws SWBException
    {
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if ( isRequired(property) )
                {
                    if ( property.isObjectProperty() )
                    {
                        SemanticObject objectValue = getSemanticObject().getObjectProperty(property, null);
                        if ( objectValue == null )
                        {
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null for the node " + this.getName() + " of type " + this.getSemanticObject().getSemanticClass().getPrefix() + ":" + this.getSemanticObject().getSemanticClass().getName());
                        }
                        else
                        {
                            BaseNode nodeChild = new BaseNode(objectValue);
                            nodeChild.checkProperties();
                        }
                    }
                    else
                    {
                        String value = getPropertyInternal(property, null);
                        if ( value == null )
                        {
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null for the node " + this.getName() + " of type " + this.getSemanticObject().getSemanticClass().getPrefix() + ":" + this.getSemanticObject().getSemanticClass().getName());
                        }
                    }
                }
            }
        }
    }

    private void setPropertyInternal(SemanticProperty property, String value)
    {
        if ( value == null )
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
        if ( isProtected(property) )
        {
            throw new SWBException("The property " + property.getPrefix() + ":" + property.getName() + " is protected");
        }
        else
        {
            if ( property.isObjectProperty() )
            {
                throw new SWBException("The property " + property.getPrefix() + ":" + property.getName() + " is not a datatype property");
            }
            if ( value == null )
            {
                getSemanticObject().removeProperty(property);
            }
            else
            {
                getSemanticObject().setProperty(property, value);
            }
        }
    }

    private String getPropertyInternal(SemanticProperty property, String value)
    {
        return getSemanticObject().getProperty(property, value);
    }

    public String getProperty(SemanticProperty property, String value) throws SWBException
    {
        if ( isProtected(property) )
        {
            throw new SWBException("The property " + property.getPrefix() + ":" + property.getName() + " is protected");
        }
        if ( property.isObjectProperty() )
        {
            throw new SWBException("The property " + property.getPrefix() + ":" + property.getName() + " is not datatype");
        }
        return getSemanticObject().getProperty(property, value);
    }

    public String getProperty(SemanticProperty property) throws SWBException
    {
        if ( isProtected(property) )
        {
            throw new SWBException("the property " + property.getPrefix() + ":" + property.getName() + " is protected");
        }
        return getSemanticObject().getProperty(property);
    }

    public final Iterator<SemanticProperty> listSemanticProperties()
    {
        ArrayList<SemanticProperty> propertiesToReturn = new ArrayList<SemanticProperty>();
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                if ( !isProtected(property) )
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
        if ( value != null )
        {
            return value;
        }
        else
        {
            throw new SWBException("The property UUID is not supported");
        }

    }

    public static final SemanticClass getSemanticClass(String nodeType) throws SWBException
    {
        String uri = getUri(nodeType);
        SemanticClass clazz = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uri);
        if ( clazz == null )
        {
            throw new SWBException("The nodeType " + nodeType + WAS_NOT_FOUND);
        }
        return clazz;
    }

    private final void addPrimaryType(SemanticClass clazz)
    {
        SemanticProperty jcr_primaryType = vocabulary.jcr_primaryType;
        if ( jcr_primaryType != null )
        {
            setPropertyInternal(jcr_primaryType, clazz.getPrefix() + ":" + clazz.getName());
        }
    }

    private final boolean isProtected(SemanticProperty property)
    {
        boolean isProtected = false;
        try
        {
            SemanticLiteral literal = getPropertyOfProperty(property, JCR_PROTECTED_PROPERTY);
            if ( literal != null )
            {
                isProtected = literal.getBoolean();
            }
        }
        catch ( Exception e )
        {
            isProtected = false;
        }
        return isProtected;
    }

    private final void addUUID()
    {
        if ( isReferenceable() || isVersionable() )
        {
            SemanticProperty jcr_uuid = vocabulary.jcr_uuid;
            String value = getPropertyInternal(jcr_uuid, null);
            if ( value == null )
            {
                setPropertyInternal(jcr_uuid, UUID.randomUUID().toString());
            }
        }
    }

    public final String[] getSuperTypes() throws SWBException
    {
        HashSet<String> superTypes = new HashSet<String>();
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticLiteral> literals = listSemanticLiterals(clazz, JCR_SUPERTYPES_PROPERTY);
            while (literals.hasNext())
            {
                SemanticLiteral literal = literals.next();
                if ( literal != null && literal.getString() != null )
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
            if ( isMixIn(clazz) )
            {
                classes.add(clazz);
            }
        }
        return classes.toArray(new SemanticClass[classes.size()]);
    }

    private final SemanticLiteral getSemanticLiteral(SemanticClass clazz, String name) throws SWBException
    {
        SemanticLiteral literal = null;
        String uri = getUri(name);
        SemanticProperty property = getSemanticObject().getModel().getSemanticProperty(uri);
        if ( property != null )
        {
            literal = clazz.getRequiredProperty(property);
        }
        return literal;
    }

    private final Iterator<SemanticLiteral> listSemanticLiterals(SemanticClass clazz, String name) throws SWBException
    {
        String uri = getUri(name);
        SemanticProperty property = getSemanticObject().getModel().getSemanticProperty(uri);
        if ( property != null )
        {
            return clazz.listRequiredProperties(property);
        }
        else
        {
            throw new SWBException("The property " + name + WAS_NOT_FOUND);
        }
    }

    public final boolean isMixIn(SemanticClass clazz)
    {
        boolean isMixIn = false;
        try
        {
            SemanticLiteral literal = getSemanticLiteral(clazz, MIX_MIXIN_PROPERTY);
            if ( literal != null )
            {
                isMixIn = literal.getBoolean();
            }
        }
        catch ( SWBException swe )
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

    private BaseNode addNodeToProperty(SemanticProperty property, String nodeType, String name) throws SWBException
    {
        if ( property.isObjectProperty() )
        {
            SemanticObject value = getSemanticObject().getObjectProperty(property, null);
            if ( value == null )
            {
                SemanticClass clazz = getSemanticClass(nodeType);
                SemanticClass clazzDomain = property.getRangeClass();
                if ( clazzDomain.equals(clazz) || clazzDomain.isSuperClass(clazz) )
                {
                    value = getSemanticObject().getModel().createSemanticObject(name, clazz);
                    BaseNode valueNode=new BaseNode(value,clazz);
                    valueNode.setName(name);
                    getSemanticObject().setObjectProperty(property, value);                    
                }
                else
                {
                    throw new SWBException("The property has a diferent nodeType (" + clazzDomain.getPrefix() + ":" + clazzDomain.getName() + ") of the NodeType(" + clazz.getPrefix() + ":" + clazz.getName() + ") to create.");
                }
            }
            BaseNode newNode = new BaseNode(value);
            String[] superTypes = newNode.getSuperTypes();
            for ( String superType : superTypes )
            {
                SemanticClass superTypeClass = getSemanticClass(superType);
                if ( isMixIn(superTypeClass) )
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
        if ( frozenNode.isFrozenNode() )
        {
            GenericIterator<BaseNode> nodes = this.listNodes();
            while (nodes.hasNext())
            {
                BaseNode childNode = nodes.next();
                String onParentVersion = getOnParentVersion(childNode);
                if ( onParentVersion.equals(ONPARENTVERSION_COPY) )
                {
                    BaseNode frozenChild=frozenNode.createNodeBase(vocabulary.nt_FrozenNode);                                        
                    childNode.copyPropertiesToFrozenNode(frozenChild);
                    childNode.addFrozenProperties(frozenChild.getSemanticObject());                    
                    copyNodesToFrozenNode(frozenChild);
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
        if ( frozenNode.isFrozenNode() )
        {

            Iterator<SemanticProperty> properties = this.getSemanticObject().getSemanticClass().listProperties();
            boolean abort = false;
            GenericIterator<BaseNode> nodes = this.listNodes();
            while (nodes.hasNext())
            {
                BaseNode childNode = nodes.next();
                String onParentVersion = getOnParentVersion(childNode);
                if ( onParentVersion.equals("ABORT") )
                {
                    abort = true;
                    break;
                }
            }
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                String onParentVersion = getOnParentVersion(property);
                if ( onParentVersion.equals("ABORT") )
                {
                    abort = true;
                    break;
                }
            }
            if ( !abort )
            {
                properties = this.getSemanticObject().getSemanticClass().listProperties();
                while (properties.hasNext())
                {
                    SemanticProperty property = properties.next();
                    String onParentVersion = getOnParentVersion(property);
                    if ( property.isDataTypeProperty() && onParentVersion.equals(ONPARENTVERSION_COPY) )
                    {
                        frozenNode.setPropertyInternal(property, getProperty(property));
                    }
                    if ( property.isObjectProperty() && onParentVersion.equals(ONPARENTVERSION_COPY) )
                    {
                        frozenNode.getSemanticObject().setObjectProperty(property, this.getSemanticObject().getObjectProperty(property));
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
            if ( literal != null && literal.getString() != null )
            {
                frozenNode.setProperty(frozenMixinTypes, literal.getString());
            }
        }

    }

    private void addRootNodeToHistory(BaseNode historyNode) throws SWBException
    {
        if ( historyNode.isVersionHistoryNode() )
        {
            SemanticProperty jcr_root = vocabulary.jcr_rootVersion;
            SemanticObject value = historyNode.getSemanticObject().getObjectProperty(jcr_root, null);
            if ( value == null )
            {
                SemanticProperty jcr_baseVersion = vocabulary.jcr_baseVersion;                
                BaseNode ntVersion=historyNode.createNodeBase(vocabulary.nt_Version);
                BaseNode ntFrozenNode=ntVersion.createNodeBase(vocabulary.nt_FrozenNode);
                addFrozenProperties(ntFrozenNode.getSemanticObject());                        
                historyNode.getSemanticObject().setObjectProperty(jcr_root, ntVersion.getSemanticObject());
                this.getSemanticObject().setObjectProperty(jcr_baseVersion, ntVersion.getSemanticObject());

            }
        }
    }

    private void createVersionHistoryNode() throws SWBException
    {
        if ( isVersionable() )
        {
            SemanticProperty property = vocabulary.jcr_versionHistory;
            BaseNode historyNode = addNodeToProperty(property, vocabulary.nt_VersionHistory.getPrefix() + ":" + vocabulary.nt_VersionHistory.getName(), UUID.randomUUID().toString());
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
        if ( property.isObjectProperty() )
        {
            SemanticObject versionHistory = getSemanticObject().getObjectProperty(property);
            if ( versionHistory != null )
            {
                getHistoryNode = new BaseNode(versionHistory);
            }
        }
        return getHistoryNode;
    }

    private BaseNode getBaseNode(SemanticProperty property)
    {
        BaseNode getBaseNode = null;
        if ( property.isObjectProperty() )
        {
            SemanticObject object = getSemanticObject().getObjectProperty(property);
            if ( object != null )
            {
                getBaseNode = new BaseNode(object);
            }
        }
        return getBaseNode;
    }

    private boolean isCheckedoutByParents()
    {
        boolean isCheckedoutByParents = false;
        BaseNode parent = this.getParent();
        if ( parent != null )
        {
            if ( parent.isChekedOut() )
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

    public boolean isChekedOut()
    {
        boolean isChekedOut = false;

        SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
        String value = getPropertyInternal(isCheckoutPropety, null);
        if ( value != null )
        {
            isChekedOut = Boolean.parseBoolean(value);
        }
        if ( !isVersionable() && isCheckedoutByParents() )
        {
            isChekedOut = true;
        }
        return isChekedOut;
    }

    public boolean isPendingChanges()
    {
        boolean isPendingChanges = false;
        if ( this.isModified() || this.isNew() )
        {
            isPendingChanges = true;
        }
        else
        {
            GenericIterator<BaseNode> childs = listNodes();
            while (childs.hasNext())
            {
                BaseNode child = childs.next();
                if ( child.isPendingChanges() )
                {
                    isPendingChanges = true;
                    break;
                }
            }
        }
        return isPendingChanges;
    }

    public BaseNode checkin() throws SWBException
    {
        if ( this.isVersionable() && isChekedOut() )
        {
            if ( this.isPendingChanges() )
            {
                throw new SWBException("The node must be saved before");
            }
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
        if ( isChekedOut() )
        {
            throw new SWBException("The node is chekedout");
        }
        SemanticProperty isCheckoutPropety = vocabulary.jcr_isCheckedOut;
        setPropertyInternal(isCheckoutPropety, "true");
    }

    private BaseNode addVersionToHistoryNode() throws SWBException
    {
        BaseNode addVersionToHistoryNode = null;
        if ( isVersionable() )
        {
            BaseNode versionHistory = getHistoryNode();
            if ( versionHistory != null )
            {                
                BaseNode ntVersion=versionHistory.createNodeBase(vocabulary.nt_Version);
                BaseNode ntFrozenNode=ntVersion.createNodeBase(vocabulary.nt_FrozenNode);                                
                copyPropertiesToFrozenNode(ntFrozenNode);
                addFrozenProperties(ntFrozenNode.getSemanticObject());                
                addVersionToHistoryNode = ntVersion;
            }
        }
        return addVersionToHistoryNode;
    }

    private BaseNode getVersionHistory() throws SWBException
    {
        BaseNode getVersionHistory = null;
        SemanticProperty propVersionHistory = vocabulary.jcr_versionHistory;
        SemanticObject object = getSemanticObject().getObjectProperty(propVersionHistory);
        if ( object != null )
        {
            getVersionHistory = new BaseNode(object);
        }
        return getVersionHistory;
    }

    private void checkVersionable() throws SWBException
    {
        if ( isVersionable() )
        {
            BaseNode history = getHistoryNode();
            if ( history == null )
            {
                createVersionHistoryNode();
            }
        }
    }

    private void addIsCheckedOut() throws SWBException
    {
        SemanticProperty property = vocabulary.jcr_isCheckedOut;
        setPropertyInternal(property, "false");
    }

    public final void addMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        SemanticProperty property = vocabulary.jcr_mixinTypes;
        setPropertyInternal(property, mixinName);
        this.getSemanticObject().addSemanticClass(clazz);
        addVersionableUuid();
        addIsCheckedOut();
        addUUID();
        addCreatedProperty();
    }

    private String[] getChildNodeDefinition(SemanticClass clazz)
    {
        ArrayList<String> getChildNodeDefinition = new ArrayList<String>();
        try
        {
            SemanticProperty property = getSemanticProperty(JCR_CHILDDEFINITION_PROPERTY);
            if ( property != null )
            {
                Iterator<SemanticLiteral> literals = clazz.listRequiredProperties(property);
                while (literals.hasNext())
                {
                    SemanticLiteral literal = literals.next();
                    if ( literal != null && literal.getString() != null && !literal.getString().trim().equals("") )
                    {
                        getChildNodeDefinition.add(literal.getString());
                    }
                }
            }
        }
        catch ( SWBException swbe )
        {
        }
        return getChildNodeDefinition.toArray(new String[getChildNodeDefinition.size()]);
    }

    public boolean canAddNode(String name) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(name);
        return canAddNode(clazz);
    }

    private boolean canAddNode(SemanticClass clazzSurce, SemanticClass clazzDest)
    {
        boolean canAddNode = false;
        String[] childDefinitions = getChildNodeDefinition(clazzDest);
        if ( childDefinitions.length > 0 )
        {
            for ( String childDefinition : childDefinitions )
            {
                if ( childDefinition.equals("*") )
                {
                    canAddNode = true;
                    break;
                }
                else
                {
                    try
                    {
                        SemanticClass clazzChild = getSemanticClass(childDefinition);
                        if ( clazzChild != null )
                        {
                            if ( clazzChild.equals(clazzSurce) )
                            {
                                canAddNode = true;
                                break;
                            }
                        }
                    }
                    catch ( SWBException swbe )
                    {
                        canAddNode = false;
                    }
                }
            }
        }
        else
        {
            canAddNode = true;
        }
        return canAddNode;
    }

    private void addCreatedProperty() throws SWBException
    {
        if ( isVersionNode() )
        {
            SemanticProperty jcr_created = vocabulary.jcr_created;
            if ( jcr_created != null )
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

    private final BaseNode createNodeBase(SemanticClass clazz) throws SWBException
    {
        return createNodeBase(UUID.randomUUID().toString(),clazz);
    }
    public final BaseNode createNodeBase(String name, SemanticClass clazz) throws SWBException
    {
        if ( canAddNode(clazz) )
        {
            String uri = getSemanticObject().getModel().getObjectUri(name, clazz);
            SemanticObject object = getSemanticObject().getModel().createSemanticObject(uri, clazz);
            BaseNode newBaseNode = new BaseNode(object,clazz);            
            newBaseNode.setName(name);
            newBaseNode.setParent(this);
            return newBaseNode;
        }
        else
        {
            throw new SWBException("The nodetype " + clazz.getPrefix()+":"+clazz.getName() + " is not possible to add");
        }
    }
    public final BaseNode createNodeBase(String name, String primaryNodeTypeName) throws SWBException
    {
        if ( primaryNodeTypeName == null )
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
        if ( value == null )
        {
            String uuid = UUID.randomUUID().toString();
            setPropertyInternal(property, uuid);

        }
    }

    public final void removeMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        if ( clazz != null )
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
                if ( classes.next().equals(clazz) )
                {
                    classes.remove();
                }
            }
        }
    }

    public final boolean existsProperty(String propertyName)
    {
        boolean existsProperty = false;
        Iterator<SemanticClass> classes = getSemanticObject().listSemanticClasses();
        while (classes.hasNext())
        {
            SemanticClass clazz = classes.next();
            Iterator<SemanticProperty> properties = clazz.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty property = properties.next();
                String myProperty = property.getPrefix() + ":" + property.getName();
                if ( myProperty.equals(propertyName) )
                {
                    existsProperty = true;
                    break;
                }
            }
        }
        return existsProperty;
    }

    public static String getUri(String name) throws SWBException
    {
        String uri;
        String[] values = name.split(":");
        if ( values.length == 2 )
        {
            String namespace = vocabulary.listUris().get(values[0]);
            if ( namespace == null )
            {
                throw new SWBException("The namespace for the prefix " + values[0] + WAS_NOT_FOUND);
            }
            else
            {
                uri = namespace + values[1];
            }
        }
        else
        {
            throw new SWBException("The name is incorrect, the prefix was not found");
        }
        return uri;
    }

    public final SemanticProperty getSemanticProperty(String name) throws SWBException
    {
        String uri = getUri(name);
        return getSemanticObject().getModel().getSemanticProperty(uri);
    }

    public BaseNode[] getVersions()
    {
        ArrayList<BaseNode> version = new ArrayList<BaseNode>();
        return version.toArray(new BaseNode[version.size()]);
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
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if ( obj instanceof BaseNode )
        {
            equals = (( BaseNode ) obj).getSemanticObject().equals(getSemanticObject());
        }
        return equals;
    }

    @Override
    public int hashCode()
    {
        return getSemanticObject().hashCode();
    }
}
