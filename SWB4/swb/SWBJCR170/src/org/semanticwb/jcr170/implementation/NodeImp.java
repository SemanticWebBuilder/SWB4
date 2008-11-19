/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import javax.jcr.AccessDeniedException;
import javax.jcr.InvalidItemStateException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.ItemVisitor;
import javax.jcr.MergeException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.ReferentialIntegrityException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.Lock;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.version.Version;
import javax.jcr.version.VersionException;
import javax.jcr.version.VersionHistory;
import org.semanticwb.SWBException;
import org.semanticwb.model.GenericIterator;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.repository.BaseNode;

/**
 *
 * @author victor.lorenzana
 */
public class NodeImp implements Node
{

    private static final ValueFactoryImp factory = new ValueFactoryImp();
    private static final String DEFAULT_PRIMARY_NODE_TYPE_NAME = "nt:unstructured";
    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private static final String WAS_NOT_FOUND = " was not found";
    private static final String PATH_SEPARATOR = "/";
    protected final BaseNode node;
    protected final SessionImp session;
    private final int index;

    NodeImp(BaseNode node, SessionImp session)
    {
        this(node, session, 0);
    }

    NodeImp(BaseNode node, SessionImp session, int index)
    {
        if ( node == null || session == null )
        {
            throw new IllegalArgumentException();
        }
        this.node = node;
        this.session = session;
        this.index = index;
    }

    BaseNode getBaseNode()
    {
        return node;
    }

    public Node addNode(String relPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        return addNode(relPath, null);
    }

    String getNodeName(String relPath) throws PathNotFoundException
    {
        String[] values = relPath.split(PATH_SEPARATOR);
        return values[values.length - 1];
    }

    String getParentPath(String relPath) throws RepositoryException
    {
        if ( isRelative(relPath) )
        {
            relPath = normalize(relPath);
        }
        StringBuilder getParentPath = new StringBuilder();
        String[] values = relPath.split(PATH_SEPARATOR);
        for ( int i = 0; i < values.length - 1; i++ )
        {
            String value = values[i];
            getParentPath.append(value + PATH_SEPARATOR);
        }
        String path = getParentPath.toString();
        if ( !path.equals(PATH_SEPARATOR) && path.endsWith(PATH_SEPARATOR) )
        {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    BaseNode getBaseNode(String relPath) throws PathNotFoundException, RepositoryException
    {
        BaseNode getBaseNode = null;
        if ( isRelative(relPath) )
        {
            relPath = normalize(relPath);
        }
        String[] values = relPath.split(PATH_SEPARATOR);
        getBaseNode = getBaseNodeFromAbsolutePath(values);
        return getBaseNode;
    }

    static BaseNode getChildBaseNode(String name, BaseNode parent)
    {
        BaseNode getChildBaseNode = null;
        GenericIterator<BaseNode> childs = parent.listNodes();
        while (childs.hasNext())
        {
            BaseNode child = childs.next();
            if ( child.getName().equals(name) )
            {
                getChildBaseNode = child;
                break;
            }
        }
        return getChildBaseNode;
    }

    private BaseNode getBaseNodeFromAbsolutePath(String[] absolutePath) throws PathNotFoundException, RepositoryException
    {
        BaseNode getBaseNodeFromAbsolutePath = getBaseNodeFromAbsolutePath = session.getRootBaseNode();
        for ( String value : absolutePath )
        {
            if ( value.equals("") )
            {
                getBaseNodeFromAbsolutePath = session.getRootBaseNode();
            }
            else
            {
                getBaseNodeFromAbsolutePath = getChildBaseNode(value, getBaseNodeFromAbsolutePath);
                if ( getBaseNodeFromAbsolutePath == null )
                {
                    throw new PathNotFoundException();
                }
            }
        }
        if ( getBaseNodeFromAbsolutePath == null )
        {
            throw new PathNotFoundException();
        }
        return getBaseNodeFromAbsolutePath;
    }

    private boolean existsBaseNode(String relPath)
    {
        boolean existsNode = true;
        try
        {
            BaseNode baseNode = getBaseNode(relPath);
            if ( baseNode == null )
            {
                existsNode = false;
            }
        }
        catch ( Exception pnfe )
        {
            existsNode = false;
        }
        return existsNode;
    }

    private String normalize(String relPath) throws RepositoryException
    {
        String normalize = relPath;
        if ( isRelative(relPath) )
        {
            String thisPath = this.getPath();
            if ( thisPath.endsWith(PATH_SEPARATOR) )
            {
                normalize = thisPath + relPath;
            }
            else
            {
                normalize = thisPath + PATH_SEPARATOR + relPath;
            }

        }
        return normalize;
    }

    private void checksLock() throws LockException, VersionException
    {
        if ( node.isLocked() && !node.getLockOwner().equals(session.getUserID()) )
        {
            throw new LockException("The node is locked by the user " + node.getLockOwner());
        }        
    }

    

    public Node addNode(String relPath, String primaryNodeTypeName) throws ItemExistsException, PathNotFoundException, NoSuchNodeTypeException, LockException, VersionException, ConstraintViolationException, RepositoryException
    {
        checksLock();
        if(node.isChekedin())
        {
            throw new RepositoryException("The node can not add a child in check-in mode");
        }
        checkRelPath(relPath);
        NodeImp newNode = null;
        if ( primaryNodeTypeName == null )
        {
            primaryNodeTypeName = DEFAULT_PRIMARY_NODE_TYPE_NAME;
        }
        if ( existsBaseNode(relPath) )
        {
            throw new ItemExistsException("The item " + relPath + " already exists");
        }
        String name = getNodeName(relPath);
        String parentPath = getParentPath(relPath);
        BaseNode parent = getBaseNode(parentPath);
        try
        {
            if ( parent.canAddNode(primaryNodeTypeName) )
            {
                BaseNode newBaseNode = parent.createNodeBase(name, primaryNodeTypeName);
                newNode = new NodeImp(newBaseNode, session);
                newBaseNode.setNew(true);
                newBaseNode.setModified(true);
                node.setModified(true);
                return newNode;
            }
            else
            {
                throw new ConstraintViolationException("The nodetype " + primaryNodeTypeName + " can not be added to the nodetype " + node.getSemanticObject().getSemanticClass().getPrefix() + ":" + node.getSemanticObject().getSemanticClass().getName());
            }
        }
        catch ( SWBException swe )
        {
            throw new NoSuchNodeTypeException(swe);
        }
    }

    public static boolean isAbsolute(String relPath)
    {
        boolean isAbsolute = false;
        if ( relPath.equals(PATH_SEPARATOR) || (relPath.startsWith("") && relPath.length() > 1 && isRelative(relPath.substring(1))) )
        {
            isAbsolute = true;
        }
        return isAbsolute;
    }

    public static boolean isRelative(String relPath)
    {

        boolean isRelative = false;
        if ( isPathElement(relPath) )
        {
            isRelative = true;
        }
        else
        {
            int pos = relPath.lastIndexOf(PATH_SEPARATOR);
            if ( pos > 0 )
            {
                String pathElement = relPath.substring(pos + 1);
                relPath = relPath.substring(0, pos);
                if ( isRelative(relPath) && isPathElement(pathElement) )
                {
                    isRelative = true;
                }
            }
            if ( relPath.startsWith("/") )
            {
                isRelative = false;
            }
        }
        return isRelative;
    }

    public static boolean isName(String relPath)
    {
        boolean isName = false;
        int pos = relPath.indexOf(":");
        String prefix = null;
        String simpleName = null;
        if ( pos != -1 )
        {
            prefix = relPath.substring(0, pos).trim();
            simpleName = relPath.substring(pos + 1);
        }
        else
        {
            simpleName = relPath;
        }
        boolean prefixValid = false;
        if ( prefix == null )
        {
            prefixValid = true;
        }
        else
        {
            if ( prefix.length() <= 3 && prefix.length() > 0 )
            {
                prefixValid = true;
            }
        }
        if ( prefixValid && isSimpleName(simpleName) )
        {
            isName = true;
        }
        return isName;
    }

    public static boolean isSimpleName(String relPath)
    {
        boolean isSimpleName = false;
        if ( isOnecharsimplename(relPath) || isTwocharsimplename(relPath) || isThreeormorecharname(relPath) )
        {
            isSimpleName = true;
        }
        return isSimpleName;
    }

    public static boolean isOnecharsimplename(String relPath)
    {
        boolean isOnecharsimplename = true;
        if ( relPath.indexOf('.') != -1 || relPath.indexOf('/') != -1 || relPath.indexOf(':') != -1 || relPath.indexOf('[') != -1 || relPath.indexOf(']') != -1 || relPath.indexOf('*') != -1 || relPath.indexOf('\'') != -1 || relPath.indexOf('\"') != -1 || relPath.indexOf('|') != -1 || relPath.indexOf(' ') != -1 )
        {
            isOnecharsimplename = false;
        }
        return isOnecharsimplename;
    }

    public static boolean isTwocharsimplename(String relPath)
    {
        boolean isTwocharsimplename = false;
        String[] values = relPath.split(".");
        for ( String value : values )
        {
            if ( isOnecharsimplename(value) )
            {
                isTwocharsimplename = true;
                break;
            }
        }
        return isTwocharsimplename;
    }

    public static boolean isThreeormorecharname(String relPath)
    {
        boolean isThreeormorecharname = true;
        if ( relPath.startsWith(" ") || relPath.endsWith(" ") )
        {
            isThreeormorecharname = false;
        }
        return isThreeormorecharname;
    }

    public static boolean isPathElement(String relPath)
    {
        boolean isPathElement = false;
        if ( relPath.equals(".") || relPath.equals("..") || isName(relPath) )
        {
            isPathElement = true;
        }
        int pos = relPath.lastIndexOf("[");
        if ( pos != -1 )
        {
            String name = relPath.substring(0, pos);
            relPath = relPath.substring(pos + 1);
            if ( relPath.endsWith("]") )
            {
                String number = relPath.substring(0, relPath.length() - 2);
                try
                {
                    int inumber = Integer.parseInt(number);
                    if ( inumber > 0 && isName(name) )
                    {
                        isPathElement = true;
                    }
                }
                catch ( NumberFormatException nfe )
                {
                    isPathElement = false;
                }
            }
        }
        return isPathElement;
    }

    public static void checkRelPath(String relPath) throws PathNotFoundException
    {
        if ( !(isRelative(relPath) || isAbsolute(relPath)) )
        {
            throw new PathNotFoundException("The relpath is incorrect");
        }
    }

    public void orderBefore(String srcName, String destName) throws UnsupportedRepositoryOperationException, VersionException, ConstraintViolationException, ItemNotFoundException, LockException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Property setProperty(String name, Value value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public Property setProperty(String name, Value value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {value};
        return setProperty(name, values, arg2);
    }

    public Property setProperty(String name, Value[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public Property setProperty(String name, Value[] value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        checksLock();
        if(node.isChekedin())
        {
            throw new RepositoryException("The node can not modify properties in check-in mode");
        }
        PropertyImp propertyToReturn = null;
        for ( Value ovalue : value )
        {
            try
            {
                SemanticProperty property = node.getSemanticProperty(name);
                node.setProperty(property, ovalue.getString());
                node.setModified(true);
                propertyToReturn = new PropertyImp(this, property);
            }
            catch ( SWBException swe )
            {
                throw new RepositoryException(swe);
            }

        }



        if ( propertyToReturn == null )
        {
            throw new RepositoryException("The property " + name + WAS_NOT_FOUND);
        }
        return propertyToReturn;
    }

    public Property setProperty(String name, String[] value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        return setProperty(name, value, 0);
    }

    public Property setProperty(String name, String[] value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = new Value[value.length];
        int i = 0;
        for ( String oValue : value )
        {
            values[i] = factory.createValue(oValue);
            i++;
        }
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, String value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, String value, int arg2) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, InputStream value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Property setProperty(String name, boolean value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, double value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, long value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, Calendar value) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        Value[] values = {factory.createValue(value)};
        return setProperty(name, values, 0);
    }

    public Property setProperty(String name, Node node) throws ValueFormatException, VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getNode(String relPath) throws PathNotFoundException, RepositoryException
    {
        checkRelPath(relPath);
        relPath = normalize(relPath);
        BaseNode baseNode = getBaseNode(relPath);
        return new NodeImp(baseNode, session);
    }

    public NodeIterator getNodes() throws RepositoryException
    {
        int size = 0;
        GenericIterator<BaseNode> it = node.listNodes();
        while (it.hasNext())
        {
            size++;
            it.next();
        }
        return new NodeIteratorImp(session, this, size);
    }

    public NodeIterator getNodes(String namePattern) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Property getProperty(String relPath) throws PathNotFoundException, RepositoryException
    {
        checkRelPath(relPath);
        if ( isName(relPath) && node.existsProperty(relPath) )
        {
            try
            {
                SemanticProperty prop = node.getSemanticProperty(relPath);
                if ( prop != null )
                {
                    return new PropertyImp(this, prop);
                }
            }
            catch ( SWBException swbe )
            {
                throw new RepositoryException(swbe);
            }
        }
        throw new PathNotFoundException("The property " + relPath + WAS_NOT_FOUND);
    }

    public PropertyIterator getProperties() throws RepositoryException
    {
        return new PropertyIteratorImp(this);
    }

    private static final String getPrefix(String name)
    {
        String namePrefix = null;
        int pos = name.indexOf(":");
        if ( pos != -1 )
        {
            namePrefix = name.substring(0, pos);
            if ( namePrefix.trim().equals("") )
            {
                namePrefix = null;
            }
        }
        return namePrefix;
    }

    private static final String getNameProperty(String name)
    {
        String nameProperty = null;
        int pos = name.indexOf(":");
        if ( pos != -1 )
        {
            nameProperty = name.substring(pos + 1);
        }
        else
        {
            nameProperty = name;
        }
        return nameProperty;
    }

    public PropertyIterator getProperties(String namePattern) throws RepositoryException
    {
        ArrayList<Property> propeties = new ArrayList<Property>();
        String[] names = namePattern.split("|");
        for ( String name : names )
        {
            if ( name != null && !name.trim().equals("") )
            {
                PropertyIterator myProperties = this.getProperties();
                while (myProperties.hasNext())
                {
                    Property prop = myProperties.nextProperty();
                    String namePrefix = getPrefix(name);
                    String namePropertyToFind = getNameProperty(name);
                    String propPrefix = getPrefix(prop.getName());
                    String propName = getNodeName(prop.getName());
                    boolean prefixEquals = false;
                    boolean nameEquals = false;
                    if ( namePrefix != null && propPrefix != null )
                    {
                        if ( namePrefix.equals("*") || namePrefix.equals(propPrefix) )
                        {
                            prefixEquals = true;
                        }
                    }
                    if ( namePropertyToFind != null && propName != null )
                    {
                        if ( namePropertyToFind.equals("*") || namePropertyToFind.equals(propName) )
                        {
                            prefixEquals = true;
                        }
                    }
                    if ( prefixEquals && nameEquals )
                    {
                        propeties.add(prop);
                    }
                }
            }
        }
        return new PropertyIteratorImp(null);
    }

    public Item getPrimaryItem() throws ItemNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String getUUID() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        try
        {
            return node.getUUID();
        }
        catch ( SWBException sqe )
        {
            throw new UnsupportedRepositoryOperationException(sqe);
        }
    }

    public int getIndex() throws RepositoryException
    {
        return index;
    }

    public PropertyIterator getReferences() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public boolean hasNode(String relPath) throws RepositoryException
    {
        boolean hasNode = false;
        checkRelPath(relPath);
        Node nodeChild = getNode(relPath);
        if ( nodeChild != null )
        {
            hasNode = true;
        }
        return hasNode;
    }

    public boolean hasProperty(String relPath) throws RepositoryException
    {
        checkRelPath(relPath);
        return node.existsProperty(relPath);
    }

    public boolean hasNodes() throws RepositoryException
    {
        return node.listNodes().hasNext();
    }

    public boolean hasProperties() throws RepositoryException
    {
        return false;
    }

    public NodeType getPrimaryNodeType() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public NodeType[] getMixinNodeTypes() throws RepositoryException
    {
        ArrayList<NodeType> mininNodeTypes = new ArrayList<NodeType>();
        for ( SemanticClass clazz : node.getMixInNodeTypes() )
        {
            NodeTypeImp nodeType = new NodeTypeImp(node, clazz);
            mininNodeTypes.add(nodeType);
        }
        return mininNodeTypes.toArray(new NodeType[mininNodeTypes.size()]);
    }

    public boolean isNodeType(String arg0) throws RepositoryException
    {
        return false;
    }

    public void addMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        checksLock();
        try
        {
            node.addMixin(mixinName);
            node.setModified(true);
        }
        catch ( SWBException swe )
        {
            throw new NoSuchNodeTypeException(swe);
        }
    }

    public void removeMixin(String mixinName) throws NoSuchNodeTypeException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        checksLock();
        try
        {
            node.removeMixin(mixinName);
        }
        catch ( SWBException swbe )
        {
            throw new NoSuchNodeTypeException(swbe);
        }
    }

    public boolean canAddMixin(String mixinName) throws NoSuchNodeTypeException, RepositoryException
    {
        return node.canAddMixin(mixinName);
    }

    public NodeDefinition getDefinition() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Version checkin() throws VersionException, UnsupportedRepositoryOperationException, InvalidItemStateException, LockException, RepositoryException
    {
        checksLock();
        if ( node.isVersionable() )
        {
            if ( node.isChekedOut() )
            {
                if ( node.isPendingChanges() )
                {
                    throw new InvalidItemStateException("The node must be saved before");
                }
                try
                {
                    BaseNode versionNode = node.checkin();
                    SWBContext.listWorkspaces().next().toXML();
                    return new VersionImp(versionNode, node.getHistoryNode(), session);
                }
                catch ( SWBException swbe )
                {
                    throw new RepositoryException(swbe);
                }
            }
            else
            {
                throw new InvalidItemStateException("The node is not checkcout");
            }
        }
        else
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }
    }

    public void checkout() throws UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        SWBContext.listWorkspaces().next().toXML();
        checksLock();
        if ( node.isVersionable() )
        {
            if ( node.isPendingChanges() )
            {
                throw new UnsupportedRepositoryOperationException("The node must be saved before, because has changes or is new");
            }
            try
            {
                node.checkout();
                return;
            }
            catch ( SWBException swbe )
            {
                throw new RepositoryException(swbe);
            }
        }
        else
        {
            throw new UnsupportedRepositoryOperationException("The node is not versionable");
        }

    }

    public void doneMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void cancelMerge(Version version) throws VersionException, InvalidItemStateException, UnsupportedRepositoryOperationException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void update(String srcWorkspaceName) throws NoSuchWorkspaceException, AccessDeniedException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public NodeIterator merge(String srcWorkspace, boolean bestEffort) throws NoSuchWorkspaceException, AccessDeniedException, MergeException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String getCorrespondingNodePath(String workspaceName) throws ItemNotFoundException, NoSuchWorkspaceException, AccessDeniedException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public boolean isCheckedOut() throws RepositoryException
    {
        if ( node.isVersionable() )
        {

        }
        return false;
    }

    public void restore(String versionName, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public void restore(Version version, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, RepositoryException
    {
        restore(version, null, arg1);
    }

    public void restore(Version version, String relPath, boolean arg2) throws PathNotFoundException, ItemExistsException, VersionException, ConstraintViolationException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        if ( node.isVersionable() )
        {

        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public void restoreByLabel(String label, boolean arg1) throws VersionException, ItemExistsException, UnsupportedRepositoryOperationException, LockException, InvalidItemStateException, RepositoryException
    {
        if ( node.isVersionable() )
        {

        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public VersionHistory getVersionHistory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if ( node.isVersionable() )
        {

        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Version getBaseVersion() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        if ( node.isVersionable() )
        {

        }
        throw new UnsupportedRepositoryOperationException(NOT_SUPPORTED_YET);
    }

    public Lock lock(boolean isDeep, boolean isSessionScoped) throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        if ( !node.isLockable() )
        {
            throw new UnsupportedRepositoryOperationException("The node can not be lockable");
        }
        if ( node.isLocked() )
        {
            throw new LockException("The node is already locked");
        }
        try
        {
            node.lock(getSession().getUserID(), isDeep);
            LockImp lock = new LockImp(node, session, isSessionScoped);
            if ( isSessionScoped )
            {
                session.addLockSession(lock);
            }
            return lock;
        }
        catch ( SWBException swbe )
        {
            throw new LockException(swbe);
        }
    }

    public Lock getLock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, RepositoryException
    {
        if ( node.isLockable() && node.isLocked() )
        {
            Lock lock = getLockImp();
            if ( lock == null )
            {
                if ( node.isLocked() )
                {
                    lock = new LockImp(node, session);
                }
                else
                {
                    throw new LockException("The node is not locked");
                }
            }
            return lock;
        }
        throw new UnsupportedRepositoryOperationException("The node " + node.getName() + " is not lockable or is not locked");
    }

    private LockImp getLockImp()
    {
        return session.getLock(this);
    }

    public void unlock() throws UnsupportedRepositoryOperationException, LockException, AccessDeniedException, InvalidItemStateException, RepositoryException
    {
        if ( node.isLockable() && node.isLocked() )
        {
            try
            {
                // por el momento sólo puede desbloquear el mismo usuario, deberia verse como un super usurio lo puede desbloquear
                LockImp lock = getLockImp();
                node.unLock(session.getUserID(), session.getLockUserComparator());
                if ( lock != null && lock.isSessionScoped() )
                {
                    session.removeLockSession(lock);
                }
                return;
            }
            catch ( SWBException e )
            {
                throw new LockException(e);
            }
        }
        else
        {
            throw new UnsupportedRepositoryOperationException("The node " + node.getName() + " is not lockable or is not locked");
        }
    }

    public boolean holdsLock() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public boolean isLocked() throws RepositoryException
    {
        boolean isLocked = false;
        if ( node.isLockable() )
        {
            isLocked = node.isLocked();
        }
        return isLocked;
    }

    public String getPath() throws RepositoryException
    {
        String path = PATH_SEPARATOR;
        BaseNode parent = node.getParent();
        if ( parent != null )
        {
            NodeImp parentNode = new NodeImp(parent, session);
            String pathParent = parentNode.getPath();
            if ( pathParent.endsWith(PATH_SEPARATOR) )
            {
                path = parentNode.getPath() + getName();
            }
            else
            {
                path = parentNode.getPath() + PATH_SEPARATOR + getName();
            }
        }
        return path;
    }

    public String getName() throws RepositoryException
    {
        return node.getName();
    }

    public Item getAncestor(int depth) throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        Item ancestor = null;
        int i = 0;
        NodeImp parent = this;
        while (i < depth)
        {
            if ( parent.node.getParent() != null )
            {
                parent = new NodeImp(parent.node.getParent(), session);
                ancestor = parent;
            }
            else
            {
                ancestor = null;
                break;
            }
            i++;
        }
        return ancestor;
    }

    public Node getParent() throws ItemNotFoundException, AccessDeniedException, RepositoryException
    {
        NodeImp parent = null;
        if ( node.getParent() != null )
        {
            parent = new NodeImp(node.getParent(), session);
        }
        return parent;
    }

    public int getDepth() throws RepositoryException
    {
        int depth = 0;
        BaseNode thisNode = this.node;
        BaseNode parent = this.node.getParent();
        while (parent == null)
        {
            thisNode = parent;
            parent = thisNode.getParent();
            depth++;
        }
        return depth;
    }

    public Session getSession() throws RepositoryException
    {
        return session;
    }

    public boolean isNode()
    {
        return true;
    }

    public boolean isNew()
    {
        return node.isNew();
    }

    public boolean isModified()
    {
        return node.isModified();
    }

    public boolean isSame(Item otherItem) throws RepositoryException
    {
        return false;
    }

    public void accept(ItemVisitor visitor) throws RepositoryException
    {

    }

    private boolean isParentCheckOut()
    {
        boolean isParentCheckOut = false;
        if ( node.getParent() != null )
        {
            NodeImp parentNode = new NodeImp(node.getParent(), session);
            try
            {
                if ( parentNode.isCheckedOut() )
                {
                    isParentCheckOut = true;
                }
            }
            catch ( Exception e )
            {

            }
            if(parentNode.isParentCheckOut())
            {
                isParentCheckOut = true;
            }
        }
        return isParentCheckOut;
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, ReferentialIntegrityException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        if(node.isNew())
        {
            throw new RepositoryException("Can not save a new item Node:"+this.getPath());
        }        
        try
        {
            node.save();            
        }
        catch ( SWBException swe )
        {
            throw new ConstraintViolationException(swe);
        }
    }

    public void refresh(boolean keepChanges) throws InvalidItemStateException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void remove() throws VersionException, LockException, ConstraintViolationException, RepositoryException
    {
        checksLock();
        if ( node != session.getRootBaseNode() )
        {
            node.remove();
        }
    }

    SessionImp getSessionImp()
    {
        return session;
    }

    @Override
    public String toString()
    {
        return node.getName();
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean equals = false;
        if ( obj instanceof NodeImp )
        {
            equals = (( NodeImp ) obj).node.equals(this.node);
        }
        return equals;
    }

    @Override
    public int hashCode()
    {
        return node.hashCode();
    }
}
