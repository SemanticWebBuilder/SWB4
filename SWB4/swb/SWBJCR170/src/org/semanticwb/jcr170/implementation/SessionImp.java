/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.jcr170.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import javax.jcr.AccessDeniedException;
import javax.jcr.Credentials;
import javax.jcr.InvalidItemStateException;
import javax.jcr.InvalidSerializedDataException;
import javax.jcr.Item;
import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.LoginException;
import javax.jcr.NamespaceException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.ValueFactory;
import javax.jcr.Workspace;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.semanticwb.Logger;
import org.semanticwb.SWBException;
import org.semanticwb.SWBUtils;
import org.semanticwb.jcr170.implementation.util.NCName;
import org.semanticwb.model.SWBContext;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.repository.BaseNode;
import org.semanticwb.repository.LockUserComparator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author victor.lorenzana
 */
public class SessionImp implements Session
{
    static Logger log=SWBUtils.getLogger(SessionImp.class);
    static public final String NL = System.getProperty("line.separator");
    private static NamespaceRegistryImp registry = new NamespaceRegistryImp();
    private static final String SYSTEM_VIEW_NAMESPACE = "http://www.jcp.org/jcr/sv/1.0";
    private static final String SYSTEM_VIEW_PREFIX = "sv";
    private static final Namespace NAMESPACE = Namespace.getNamespace(SYSTEM_VIEW_PREFIX, SYSTEM_VIEW_NAMESPACE);
    private static final String PATH_SEPARATOR = "/";
    private static final String NOT_SUPPORTED_YET = "Not supported yet.";
    private final RepositoryImp repository;
    private final WorkspaceImp workspace;
    private final SimpleCredentials credentials;
    private final String workspaceName;
    private final Hashtable<Node, LockImp> locksSessions = new Hashtable<Node, LockImp>();
    private SimpleLockUserComparator simpleLockUserComparator = new SimpleLockUserComparator();
    private final SimpleNode root;

    SessionImp(RepositoryImp repository, String workspaceName, SimpleCredentials credentials) throws RepositoryException
    {
        if (repository == null || workspaceName == null || credentials == null)
        {
            throw new IllegalArgumentException("The repository is null or workspace is null or credentials is null");
        }
        this.repository = repository;
        this.workspaceName = workspaceName;
        this.credentials = credentials;
        this.workspace = new WorkspaceImp(this, workspaceName);
        BaseNode rootBaseNode = SWBContext.getWorkspace(this.workspace.getName()).getRoot();
        root = new SimpleNode(rootBaseNode, this, null, rootBaseNode.getId());
    }

    public SimpleNode getSimpleRootNode()
    {
        return root;
    }

    public Document getDocumentView() throws RepositoryException
    {
        Document document = new Document();
        SimpleNode root = this.root;
        appendNodeInternalView(root, document, false);
        return document;
    }

    static void appendNode(SimpleNode node, Element parent) throws RepositoryException
    {
        SemanticClass clazz = node.clazz;
        Namespace ns = Namespace.getNamespace(clazz.getPrefix(), clazz.getURI());
        Element element = new Element(clazz.getName(), ns);
        appendPropertiesToNode(node, element);
        parent.addContent(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNode(child, element);
        }

    }

    public SimpleNode[] getSimpleNodeByPath(String path, SimpleNode parent, boolean all) throws RepositoryException
    {

        ArrayList<SimpleNode> nodes = new ArrayList<SimpleNode>();
        String[] values = path.split("/");
        if (values.length == 0)
        {
            nodes.add(root);
        }
        else
        {
            int depth = 0;
            for (String value : values)
            {
                SimpleNode[] parents =
                {
                    parent
                };
                depth++;
                if (value.equals("") || values.equals("/"))
                {
                    parent = root;
                }
                else
                {
                    String name = value;
                    int pos = name.indexOf("[");
                    if (pos != -1)
                    {
                        name = name.substring(0, pos);
                    }
                    for (SimpleNode parentNode : parents)
                    {
                        SimpleNode[] childs = parentNode.getSimpleNodeByName(value);
                        if (depth != values.length)
                        {
                            parents = childs;
                        }
                        else
                        {
                            for (SimpleNode child : childs)
                            {
                                nodes.add(child);
                            }
                        }
                    }
                }

            }
        }
        return nodes.toArray(new SimpleNode[nodes.size()]);
    }

    private static String getPrefix(String name)
    {
        String getPrefix = null;
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            getPrefix = name.substring(0, pos);
        }
        return getPrefix;
    }

    private static void appendPropertiesToNode(SimpleNode node, Element nodeElement) throws RepositoryException
    {
        PropertyIterator properties = node.getProperties();
        while (properties.hasNext())
        {
            Property property = properties.nextProperty();
            if (property != null)
            {
                if (property.isNode())
                {
                    Node object = property.getNode();
                    if (object instanceof SimpleNode)
                    {
                        if (object != null)
                        {
                            appendNode((SimpleNode) object, nodeElement);
                        }
                    }
                }
                else
                {
                    String prefix = getPrefix(property.getName());
                    if (prefix != null)
                    {

                        Namespace ns = Namespace.getNamespace(prefix, registry.getURI(prefix));
                        try
                        {
                            for (Value value : property.getValues())
                            {
                                nodeElement.setAttribute(property.getName(), value.getString(), ns);
                            }
                        }
                        catch (Throwable ex)
                        {
                            ex.printStackTrace(System.out);
                        }
                    }
                }

            }
        }
    }

    private static void appendNode(SimpleNode node, Document document) throws RepositoryException
    {
        Element element = new Element(getName(node.getName()));
        appendPropertiesToNode(node, element);
        document.setRootElement(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNode(child, element);
        }
    }

    public Document toXML() throws RepositoryException
    {
        Document document = new Document();
        appendNode(root, document);
        try
        {
            XMLOutputter out = new XMLOutputter();
            out.output(document, System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        return document;
    }

    public Document getDocumentInternalView() throws RepositoryException
    {
        Document document = new Document();
        appendNodeInternalView(root, document, true);
        try
        {
            XMLOutputter out = new XMLOutputter();
            out.output(document, System.out);
        }
        catch (Exception e)
        {
            e.printStackTrace(System.out);
        }
        return document;
    }

    private static void appendPropertiesToNodeInternalView(SimpleNode node, Element nodeElement) throws RepositoryException
    {
        PropertyIterator properties = node.getProperties();
        while (properties.hasNext())
        {
            Property property = properties.nextProperty();

            if (!property.isNode())
            {
                String prefix = getPrefix(property.getName());
                if (prefix != null)
                {
                    Namespace ns = Namespace.getNamespace(prefix, registry.getURI(prefix));
                    try
                    {
                        String value = property.getString();
                        if (value != null)
                        {
                            nodeElement.setAttribute(property.getName(), value, ns);
                        }
                    }
                    catch (Exception e)
                    {
                    }


                }
            }
        }
    }

    static void appendNodeInternalView(SimpleNode node, Element parent, boolean internal) throws RepositoryException
    {
        Element element = new Element(node.getName());
        if (internal)
        {
            element.setAttribute("path", node.getPath());
        }
        appendPropertiesToNodeInternalView(node, element);
        parent.addContent(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNodeInternalView(child, element, internal);
        }
    }

    private static void appendNodeInternalView(SimpleNode node, Document document, boolean internal) throws RepositoryException
    {
        Element element = new Element(getName(node.getName()));
        if (internal)
        {
            element.setAttribute("path", node.getPath());
        }
        appendPropertiesToNodeInternalView(node, element);
        document.addContent(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNodeInternalView(child, element, internal);
        }
    }

    private static String getName(String name)
    {
        int pos = name.indexOf(":");
        if (pos != -1)
        {
            name = name.substring(pos + 1);
        }
        return name;
    }

    private static void appendPropertiesToNodeSystemView(SimpleNode node, Element nodeElement) throws RepositoryException
    {
        PropertyIterator properties = node.getProperties();
        while (properties.hasNext())
        {
            Property property = properties.nextProperty();
            Element propertyElement = new Element("property", NAMESPACE);
            nodeElement.addContent(propertyElement);
            propertyElement.setAttribute(new Attribute("name", property.getName(), NAMESPACE));
            String value = node.getProperty(property.getName()).getString();
            Element eValue = new Element("value", NAMESPACE);
            propertyElement.addContent(eValue);
            eValue.setText(value);
        }
    }

    private static void appendNodeSystemView(SimpleNode node, Element parent) throws RepositoryException
    {
        Element element = new Element("node", NAMESPACE);
        element.setAttribute(new Attribute("name", node.getName(), NAMESPACE));
        appendPropertiesToNodeSystemView(node, element);
        parent.addContent(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNodeSystemView(child, element);
        }

    }

    private static void appendNodeSystemView(SimpleNode node, Document document) throws RepositoryException
    {
        Element element = new Element("node", NAMESPACE);
        element.setAttribute(new Attribute("name", node.getName(), NAMESPACE));
        appendPropertiesToNodeSystemView(node, element);
        document.addContent(element);
        for (SimpleNode child : node.getSimpleNodes())
        {
            appendNodeSystemView(child, element);
        }
    }

    public Document getSystemView() throws RepositoryException
    {
        Document document = new Document();
        appendNodeSystemView(root, document);
        return document;
    }

    public BaseNode getBaseNodeFromType(String id, String type) throws SWBException
    {
        SemanticClass clazz = this.root.getBaseNode().getSemanticClass(type);
        String uri = this.root.getBaseNode().getSemanticObject().getModel().getObjectUri(id, clazz);
        SemanticObject object = this.root.getBaseNode().getSemanticObject().getModel().getSemanticObject(uri);
        return new BaseNode(object);
    }

    /*SimpleNode createNode(SimpleNode parent, String name, SemanticClass clazz) throws ConstraintViolationException, RepositoryException
    {
    SimpleNode tmp = new SimpleNode(this, name, clazz, parent, 0);        
    return tmp;
    BaseNode newBaseNode = parent.createNodeBase(name, primaryNodeTypeName);
    newNode = new SemanticNode(newBaseNode, session);
    newBaseNode.setNew(true);
    newBaseNode.setModified(true);
    node.setModified(true);
    return newNode;
    }*/
    public LockUserComparator getLockUserComparator()
    {
        return simpleLockUserComparator;
    }

    public static String getNodeName(String relPath) throws PathNotFoundException
    {
        String[] values = relPath.split(PATH_SEPARATOR);
        return values[values.length - 1];
    }

    public static boolean isAbsolute(String relPath) throws RepositoryException
    {
        boolean isAbsolute = false;
        if (relPath.equals(PATH_SEPARATOR) || (relPath.startsWith("") && relPath.length() > 1 && isRelative(relPath.substring(1))))
        {
            isAbsolute = true;
        }
        return isAbsolute;
    }

    public static boolean isRelative(String relPath) throws RepositoryException
    {
        if (relPath.startsWith("/"))
        {
            return false;
        }
        boolean isRelative = false;
        if (isPathElement(relPath))
        {
            isRelative = true;
        }
        else
        {
            int pos = relPath.lastIndexOf(PATH_SEPARATOR);
            if (pos > 0)
            {
                String pathElement = relPath.substring(pos + 1);
                relPath = relPath.substring(0, pos);
                if (isRelative(relPath) && isPathElement(pathElement))
                {
                    isRelative = true;
                }
            }
            if (relPath.startsWith("/"))
            {
                isRelative = false;
            }
        }
        return isRelative;

    }

    public static boolean isName(String relPath) throws RepositoryException
    {
        boolean isName = false;
        int pos = relPath.indexOf(":");
        String prefix = null;
        String simpleName = null;
        if (pos != -1)
        {
            prefix = relPath.substring(0, pos).trim();
            simpleName = relPath.substring(pos + 1);
        }
        else
        {
            simpleName = relPath;
        }
        if (prefix != null)
        {
            NCName ncname = new NCName();
            if (!ncname.isValid(prefix))
            {
                throw new RepositoryException("The prefix is invalid");
            }
        }
        if (isSimpleName(simpleName))
        {
            isName = true;
        }
        return isName;
    }

    public static boolean isSimpleName(String relPath)
    {
        boolean isSimpleName = false;
        if (isOnecharsimplename(relPath) || isTwocharsimplename(relPath) || isThreeormorecharname(relPath))
        {
            isSimpleName = true;
        }
        return isSimpleName;
    }

    public static boolean isOnecharsimplename(String relPath)
    {
        boolean isOnecharsimplename = true;
        if (relPath.indexOf('.') != -1 || relPath.indexOf('/') != -1 || relPath.indexOf(':') != -1 || relPath.indexOf('[') != -1 || relPath.indexOf(']') != -1 || relPath.indexOf('*') != -1 || relPath.indexOf('\'') != -1 || relPath.indexOf('\"') != -1 || relPath.indexOf('|') != -1 || relPath.indexOf(' ') != -1)
        {
            isOnecharsimplename = false;
        }
        return isOnecharsimplename;
    }

    public static boolean isTwocharsimplename(String relPath)
    {
        boolean isTwocharsimplename = false;
        String[] values = relPath.split(".");
        for (String value : values)
        {
            if (isOnecharsimplename(value))
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
        if (relPath.startsWith(" ") || relPath.endsWith(" "))
        {
            isThreeormorecharname = false;
        }
        return isThreeormorecharname;
    }

    public static boolean isPathElement(String relPath) throws RepositoryException
    {
        boolean isPathElement = false;
        if (relPath.equals(".") || relPath.equals("..") || isName(relPath))
        {
            isPathElement = true;
        }
        int pos = relPath.lastIndexOf("[");
        if (pos != -1)
        {
            String name = relPath.substring(0, pos);
            relPath = relPath.substring(pos + 1);
            if (relPath.endsWith("]"))
            {
                String number = relPath.substring(0, relPath.length() - 2);
                try
                {
                    int inumber = Integer.parseInt(number);
                    if (inumber > 0 && isName(name))
                    {
                        isPathElement = true;
                    }
                }
                catch (NumberFormatException nfe)
                {
                    isPathElement = false;
                }
            }
        }
        return isPathElement;
    }

    public static void checkRelPath(String relPath) throws RepositoryException
    {
        if (!(isRelative(relPath) || isAbsolute(relPath)))
        {
            throw new RepositoryException("The relpath is incorrect");
        }
    }

    public static String normalize(String relPath, Node node) throws RepositoryException
    {
        String normalize = relPath;
        if (isRelative(relPath))
        {
            String thisPath = node.getPath();
            if (thisPath.endsWith(PATH_SEPARATOR))
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

    public static String getParentPath(String relPath, Node node) throws RepositoryException
    {
        if (isRelative(relPath))
        {
            relPath = SessionImp.normalize(relPath, node);
        }
        StringBuilder getParentPath = new StringBuilder();
        String[] values = relPath.split(PATH_SEPARATOR);
        for (int i = 0; i < values.length - 1; i++)
        {
            String value = values[i];
            getParentPath.append(value + PATH_SEPARATOR);
        }
        String path = getParentPath.toString();
        if (!path.equals(PATH_SEPARATOR) && path.endsWith(PATH_SEPARATOR))
        {
            path = path.substring(0, path.length() - 1);
        }
        return path;
    }

    void addLockSession(LockImp lock)
    {
        locksSessions.put(lock.getNode(), lock);
    }

    void removeLockSession(LockImp lock)
    {
        if (lock != null)
        {
            locksSessions.remove(lock.getNode());
        }
    }

    LockImp getLock(Node node)
    {
        LockImp getLock = null;
        if (node != null)
        {
            getLock = locksSessions.get(node);
        }
        return getLock;
    }

    public Repository getRepository()
    {
        return repository;
    }

    public String getUserID()
    {
        return credentials.getUserID();
    }

    public Object getAttribute(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String[] getAttributeNames()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Workspace getWorkspace()
    {
        return this.workspace;
    }

    public Session impersonate(Credentials credentials) throws LoginException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public Node getRootNode() throws RepositoryException
    {
        return root;
    }

    public Node getNodeByUUID(String uuid) throws ItemNotFoundException, RepositoryException
    {
        Iterator<SemanticObject> it = SWBContext.getWorkspace(workspaceName).getSemanticObject().getModel().listSubjects(BaseNode.vocabulary.jcr_uuid, uuid);
        while (it.hasNext())
        {
            String path = it.next().getProperty(BaseNode.vocabulary.swbrep_path);
            Item item = this.getItem(path.toString());
            if (item.isNode())
            {
                SimpleNode node = (SimpleNode) item;
                if (!node.isDeleted())
                {
                    return node;
                }
            }
        }
        throw new ItemNotFoundException("The node with the uuid " + uuid + " was not found");
    }

    void checksLock(Node node) throws LockException, VersionException, RepositoryException
    {
        if (node.isLocked() && !node.getLock().getLockOwner().equals(this.getUserID()))
        {
            throw new LockException("The node is locked by the user " + node.getLock().getLockOwner());
        }
    }

    public Item getItem(String absPath) throws PathNotFoundException, RepositoryException
    {
        // TODO, no trae propiedades y la ruta puede indicar el segundo o el primero
        // ejemplo /Categorty[0]/Deportes[1]
        SimpleNode[] nodes = getSimpleNodeByPath(absPath, root, false);
        if (nodes.length == 0)
        {
            throw new PathNotFoundException();
        }
        return nodes[0];

    }

    public boolean itemExists(String abspath) throws RepositoryException
    {
        boolean itemExists = false;
        try
        {
            Item item = this.getItem(abspath);
            if (item != null)
            {
                itemExists = true;
            }
        }
        catch (PathNotFoundException pnfe)
        {
            itemExists = false;
        }
        return itemExists;
    }

    public void move(String srcAbsPath, String destAbsPath) throws ItemExistsException, PathNotFoundException, VersionException, ConstraintViolationException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void save() throws AccessDeniedException, ItemExistsException, ConstraintViolationException, InvalidItemStateException, VersionException, LockException, NoSuchNodeTypeException, RepositoryException
    {
        getRootNode().save();
    }

    public void refresh(boolean keepChanges) throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public boolean hasPendingChanges() throws RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public ValueFactory getValueFactory() throws UnsupportedRepositoryOperationException, RepositoryException
    {
        return new ValueFactoryImp();
    }

    public void checkPermission(String arg0, String arg1) throws AccessControlException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public ContentHandler getImportContentHandler(String parentAbsPath, int arg1) throws PathNotFoundException, ConstraintViolationException, VersionException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void importXML(String parentAbsPath, InputStream in, int arg2) throws IOException, PathNotFoundException, ItemExistsException, ConstraintViolationException, VersionException, InvalidSerializedDataException, LockException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportSystemView(String absPath, ContentHandler contentHandler, boolean binaryAsLink, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportSystemView(String absPath, OutputStream out, boolean binaryAsLink, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportDocumentView(String absPath, ContentHandler contentHandler, boolean binaryAsLink, boolean noRecurse) throws PathNotFoundException, SAXException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void exportDocumentView(String absPath, OutputStream out, boolean binaryAsLink, boolean noRecurse) throws IOException, PathNotFoundException, RepositoryException
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void setNamespacePrefix(String prefix, String uri) throws NamespaceException, RepositoryException
    {
        throw new NamespaceException("This operation is not supported");
    }

    public String[] getNamespacePrefixes() throws RepositoryException
    {
        return registry.getPrefixes();
    }

    public String getNamespaceURI(String prefix) throws NamespaceException, RepositoryException
    {
        return registry.getURI(prefix);
    }

    public String getNamespacePrefix(String uri) throws NamespaceException, RepositoryException
    {
        NamespaceRegistryImp registry = new NamespaceRegistryImp();
        return registry.getPrefix(uri);
    }

    private void releaseLockSessions()
    {
        for (LockImp lock : locksSessions.values())
        {
            if (lock.isSessionScoped())
            {
                try
                {
                    lock.getLockBaseNode().unlock();
                }
                catch (Exception e)
                {
                }
            }
        }
    }

    public void logout()
    {
        releaseLockSessions();
    }

    public boolean isLive()
    {
        return true;
    }

    public void addLockToken(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public String[] getLockTokens()
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    public void removeLockToken(String arg0)
    {
        throw new UnsupportedOperationException(NOT_SUPPORTED_YET);
    }

    BaseNode getRootBaseNode()
    {
        return root.getBaseNode();
    }
}
