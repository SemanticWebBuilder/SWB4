package org.semanticwb.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import org.semanticwb.SWBException;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.repository.base.*;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import java.util.UUID;
import org.semanticwb.SWBPlatform;
import org.semanticwb.platform.SemanticLiteral;

public class BaseNode extends BaseNodeBase
{

    private static final String JCR_ISCHECKEDOUT_PROPERTY = "jcr:isCheckedOut";
    private static final String JCR_ROOTVERSION_PROPERTY = "jcr:rootVersion";
    private static final String NT_VERSION_NODE = "nt:version";
    private static final String JCR_CREATED_PROPERTY = "jcr:created";
    private static final String JCR_CHILDDEFINITION_PROPERTY = "jcr:childNodeDefinition";
    private static final String NT_VERSIONHISTORY_NODE = "nt:versionHistory";
    private static final String JCR_VERSIONHISTORY_PROPERTY = "jcr:versionHistory";
    private static final String JCR_VERSIONABLEUUID_PROPERTY = "jcr:versionableUuid";
    private static final String JCR_LOCKISDEEP_PROPERTY = "jcr:lockIsDeep";
    private static final String JCR_LOCKOWNER_PROPERTY = "jcr:lockOwner";
    private static final String JCR_PROTECTED_PROPERTY = "jcr:protected";
    private static final String JCR_PRIMARYTYPE_PROPERTY = "jcr:primaryType";
    private static final String JCR_MANDATORY_PROPERTY = "jcr:mandatory";
    private static final String MIX_MIXIN_PROPERTY = "mix:mixin";
    private static final String JCR_MIXINTYPES_PROPERTY = "jcr:mixinTypes";
    private static final String JCR_UUID_PROPERTY = "jcr:uuid";
    private static final String VERSIONABLE_NAME = "mix:versionable";
    private static final String REFERENCEABLE_NAME = "mix:referenceable";
    private static final String LOCKABLE_NAME = "mix:lockable";
    private static final String JCR_SUPERTYPES_PROPERTY = "jcr:supertypes";
    private static final String WAS_NOT_FOUND = " was not found";
    private static final Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat iso8601dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public BaseNode(SemanticObject base)
    {
        super(base);

    }
    private boolean isNodeType(String name)
    {
        boolean isVersionNode = false;
        try
        {
            SemanticClass clazz = getSemanticClass(name);
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
        }
        catch ( SWBException swbe )
        {
            isVersionNode = false;
        }
        return isVersionNode;
    }
     public boolean isVersionHistoryNode()
    {
        return isNodeType(NT_VERSIONHISTORY_NODE);
    }
    public boolean isVersionNode()
    {
        return isNodeType(NT_VERSION_NODE);
    }

    private void unLock() throws SWBException
    {
        BaseNode lockNode = getLockBaseNode();
        if ( lockNode.equals(this) )
        {
            SemanticProperty isDeepProperty = getSemanticProperty(JCR_LOCKISDEEP_PROPERTY);
            SemanticProperty lockownerProperty = getSemanticProperty(JCR_LOCKOWNER_PROPERTY);
            lockNode.setProperty(lockownerProperty, null);
            lockNode.setProperty(isDeepProperty, null);
            String isdeep = lockNode.getProperty(isDeepProperty);
            String lockOwner = lockNode.getProperty(lockownerProperty);
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
        throw new SWBException("The node is not lockable or is not lockable");
    }

    public void unLock(String lockOwner) throws SWBException
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
    }

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
        try
        {
            SemanticProperty isDeepProperty = getSemanticProperty(JCR_LOCKISDEEP_PROPERTY);
            String value = node.getProperty(isDeepProperty, "false");
            if ( value != null )
            {
                isDeepLock = Boolean.parseBoolean(value);
            }
        }
        catch ( SWBException swbe )
        {
            isDeepLock = false;
        }
        return isDeepLock;
    }

    public String getLockOwner()
    {
        String getLockOwner = null;
        BaseNode lockNode = getLockBaseNode();
        if ( lockNode != null )
        {
            try
            {
                SemanticProperty lockownerProperty = getSemanticProperty(JCR_LOCKOWNER_PROPERTY);
                getLockOwner = lockNode.getProperty(lockownerProperty, null);
            }
            catch ( SWBException swbe )
            {
                getLockOwner = null;
            }
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
        try
        {
            SemanticProperty lockownerProperty = getSemanticProperty(JCR_LOCKOWNER_PROPERTY);
            String value = node.getProperty(lockownerProperty, null);
            if ( value != null )
            {
                hasLock = true;
            }
        }
        catch ( SWBException swbe )
        {
            hasLock = false;
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
                    SemanticProperty propertyLockOwner = getSemanticProperty(JCR_LOCKOWNER_PROPERTY);
                    SemanticProperty propertyIsDeep = getSemanticProperty(JCR_LOCKISDEEP_PROPERTY);
                    setProperty(propertyLockOwner, lockOwner);
                    setProperty(propertyIsDeep, new Boolean(lockIsDeep).toString());
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
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null");
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
                            throw new SWBException("The value for the property " + property.getPrefix() + ":" + property.getName() + " is null");
                        }
                    }
                }
            }
        }
    }

    private void setPropertyInternal(SemanticProperty property, String value)
    {
        getSemanticObject().setProperty(property, value);
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
        SemanticProperty property = getSemanticProperty(JCR_UUID_PROPERTY);
        if ( property != null )
        {
            String value = getPropertyInternal(property, null);
            if ( value != null )
            {
                return value;
            }
        }

        throw new SWBException("The property " + JCR_UUID_PROPERTY + " is not supported for this object");
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

    private final void addPrimaryType(SemanticClass clazz) throws SWBException
    {
        if ( existsProperty(JCR_PRIMARYTYPE_PROPERTY) )
        {
            SemanticProperty jcr_primaryType = getSemanticProperty(JCR_PRIMARYTYPE_PROPERTY);
            if ( jcr_primaryType != null )
            {
                setPropertyInternal(jcr_primaryType, clazz.getPrefix() + ":" + clazz.getName());
            }
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

    private final void addUUID() throws SWBException
    {
        if ( existsProperty(JCR_UUID_PROPERTY) )
        {
            SemanticProperty jcr_uuid = getSemanticProperty(JCR_UUID_PROPERTY);
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

    private BaseNode addNodeToProperty(SemanticProperty property, String nodeType) throws SWBException
    {
        if ( property.isObjectProperty() )
        {
            SemanticObject value = getSemanticObject().getObjectProperty(property, null);
            if ( value == null )
            {
                SemanticClass clazz = getSemanticClass(nodeType);
                SemanticClass clazzDomain = property.getDomainClass();
                if ( clazzDomain.equals(clazz) || clazzDomain.isSuperClass(clazz) )
                {
                    String name = property.getURI();
                    value = getSemanticObject().getModel().createSemanticObject(name, clazz);
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

    private BaseNode cloneNode() throws SWBException
    {
        SemanticObject cloneNode = getSemanticObject().getModel().createSemanticObject(this.getId(), this.getSemanticObject().getSemanticClass());
        Iterator<SemanticProperty> properties = this.getSemanticObject().getSemanticClass().listProperties();
        while (properties.hasNext())
        {
            SemanticProperty property = properties.next();
            if ( property.isDataTypeProperty() )
            {
                cloneNode.setProperty(property, getProperty(property));
            }
            if ( property.isObjectProperty() )
            {
                cloneNode.setObjectProperty(property, this.getSemanticObject().getObjectProperty(property));
            }
        }
        return new BaseNode(cloneNode);

    }

    private void createVersionHistoryNode() throws SWBException
    {
        if ( isVersionNode() )
        {
            SemanticProperty property = getSemanticProperty(JCR_VERSIONHISTORY_PROPERTY);
            if ( property == null )
            {
                throw new SWBException("The property " + JCR_VERSIONHISTORY_PROPERTY + WAS_NOT_FOUND);
            }
            addNodeToProperty(property, NT_VERSIONHISTORY_NODE);
        }
    }

    private BaseNode getHistoryNode() throws SWBException
    {
        BaseNode getHistoryNode = null;
        SemanticProperty property = getSemanticProperty(JCR_VERSIONHISTORY_PROPERTY);
        if ( property.isObjectProperty() )
        {
            SemanticObject versionHistory = getSemanticObject().getObjectProperty(property);
            if ( versionHistory == null )
            {
                createVersionHistoryNode();
                versionHistory = getSemanticObject().getObjectProperty(property);
            }
            getHistoryNode = new BaseNode(versionHistory);
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

    public boolean isChekedOut()
    {
        boolean isChekedOut = false;
        try
        {
            SemanticProperty isCheckoutPropety = getSemanticProperty(JCR_ISCHECKEDOUT_PROPERTY);
            String value = getProperty(isCheckoutPropety, null);
            if ( value != null )
            {
                isChekedOut = Boolean.parseBoolean(value);
            }
        }
        catch ( SWBException swbe )
        {
            isChekedOut = false;
        }
        return isChekedOut;
    }
    private void propageCheckin(BaseNode node) throws SWBException
    {
        if(node.isVersionable() && node.isChekedOut())
        {
            SemanticProperty isCheckoutPropety = getSemanticProperty(JCR_ISCHECKEDOUT_PROPERTY);
            node.setProperty(isCheckoutPropety, null);
            Iterator<BaseNode> childs=node.listNodes();
            while(childs.hasNext())
            {
                propageCheckin(childs.next());
            }
        }        
    }
    private void propageCheckout(BaseNode node) throws SWBException
    {
        if(node.isVersionable())
        {
            SemanticProperty isCheckoutPropety = getSemanticProperty(JCR_ISCHECKEDOUT_PROPERTY);
            node.setProperty(isCheckoutPropety, "true");
            Iterator<BaseNode> childs=node.listNodes();
            while(childs.hasNext())
            {
                propageCheckout(childs.next());
            }
        }        
        
    }
    public BaseNode checkin() throws SWBException
    {
        propageCheckin(this);
        BaseNode versionNode=addVersionToHistoryNode();        
        return versionNode;
    }
    public void checkout() throws SWBException
    {
        if ( isChekedOut() )
        {
            throw new SWBException("The node is chekedout");
        }
        propageCheckout(this);
    }

    private BaseNode addVersionToHistoryNode() throws SWBException
    {
        if ( isVersionable() )
        {
            BaseNode versionHistory = getHistoryNode();
            if ( versionHistory != null )
            {
                BaseNode version = cloneNode();
                return version;
            }
        }
        throw new SWBException("The node is not versionable");
    }

    private void checkVersionable() throws SWBException
    {
        if ( isVersionable() )
        {
            createVersionHistoryNode();
        }
    }

    public final void addMixin(String mixinName) throws SWBException
    {
        SemanticClass clazz = getSemanticClass(mixinName);
        SemanticProperty property = getSemanticProperty(JCR_MIXINTYPES_PROPERTY);
        if ( property == null )
        {
            throw new SWBException("The property " + JCR_MIXINTYPES_PROPERTY + WAS_NOT_FOUND);
        }
        setPropertyInternal(property, mixinName);
        this.getSemanticObject().addSemanticClass(clazz);
        addVersionableUuid();
        addUUID();
        checkVersionable();
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
            SemanticProperty jcr_created = getSemanticProperty(JCR_CREATED_PROPERTY);
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

    public final BaseNode createNodeBase(String name, String primaryNodeTypeName) throws SWBException
    {
        if ( primaryNodeTypeName == null )
        {
            throw new SWBException("The primaryNodeTypeName is null");
        }
        SemanticClass clazz = getSemanticClass(primaryNodeTypeName);
        if ( canAddNode(clazz) )
        {
            String uri = getSemanticObject().getModel().getObjectUri(name, clazz);
            SemanticObject object = getSemanticObject().getModel().createSemanticObject(uri, clazz);
            BaseNode newBaseNode = new BaseNode(object);
            newBaseNode.addPrimaryType(clazz);
            String[] superTypes = newBaseNode.getSuperTypes();
            for ( String superType : superTypes )
            {
                SemanticClass superTypeClass = getSemanticClass(superType);
                if ( isMixIn(superTypeClass) )
                {
                    newBaseNode.addMixin(superType);
                }
            }
            newBaseNode.setName(name);
            newBaseNode.setParent(this);
            return newBaseNode;
        }
        else
        {
            throw new SWBException("The nodetype " + primaryNodeTypeName + " is not possible to add");
        }
    }

    private void addVersionableUuid() throws SWBException
    {
        SemanticProperty property = getSemanticProperty(JCR_VERSIONABLEUUID_PROPERTY);
        if ( property != null )
        {
            String value = getProperty(property, null);
            if ( value == null )
            {
                String uuid = UUID.randomUUID().toString();
                setProperty(property, uuid);

            }
        }
        else
        {
            throw new SWBException("The property " + JCR_VERSIONABLEUUID_PROPERTY + WAS_NOT_FOUND);
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
        ArrayList<BaseNode> version=new ArrayList<BaseNode>();
        return version.toArray(new BaseNode[version.size()]);
    }
    public final boolean isLockable()
    {
        return isNodeType(LOCKABLE_NAME);
    }
    public final boolean isReferenceable()
    {
        return (isNodeType(REFERENCEABLE_NAME) || isVersionable());
    }

    public final boolean isVersionable()
    {
        return isNodeType(VERSIONABLE_NAME);
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
