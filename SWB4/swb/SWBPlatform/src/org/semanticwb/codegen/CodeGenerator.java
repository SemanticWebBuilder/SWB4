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
package org.semanticwb.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticMgr;
import static org.semanticwb.SWBUtils.TEXT.*;
// TODO: Auto-generated Javadoc
/**
 * The Class CodeGenerator.
 * 
 * @author Victor Lorenzana
 */
public class CodeGenerator
{
    private static final String SEMANTIC_ITERATOR_FULL_NAME="org.semanticwb.platform.SemanticIterator";
    private static final String MODEL_FULL_NAME = "org.semanticwb.platform.SemanticModel";
    private static final String SEMANTIC_MANANGER_FULL_NAME = "org.semanticwb.platform.SemanticMgr";
    private static final String SEMANTIC_MODEL_FULL_NAME = "org.semanticwb.model.SWBModel";
    private static final String SEMANTIC_PROPERTY_FULL_NAME = "org.semanticwb.platform.SemanticProperty";
    private static final String GET_SEMANTIC_CLASS = ".getSemanticMgr().getVocabulary().getSemanticClass(\"";
    private static final String SEMANTIC_PLATFORM_FULL_NAME="org.semanticwb.SWBPlatform";
    private static final String GENERIC_ITERATOR_FULL_NAME="org.semanticwb.model.GenericIterator";
    private static final String SEMANTIC_LITERAL_FULL_NAME="org.semanticwb.platform.SemanticLiteral";

    private static final String UTIL_ITERATOR_FULL_NAME="java.util.Iterator";
    private static final String GENERIC_OBJECT_FULL_NAME="org.semanticwb.model.GenericObject";
    private static final String JENA_ITERATOR_FULL_NAME="com.hp.hpl.jena.rdf.model.StmtIterator";
    private static final String SEMANTIC_OBJECT_FULL_NAME="org.semanticwb.platform.SemanticObject";
    private static final String SEMANTIC_CLASS_FULL_NAME="org.semanticwb.platform.SemanticClass";

    /** The Constant GLOBAL_CLASS_NAME. */
    private static final String GLOBAL_CLASS_NAME = "ClassMgr";
    /** The Constant CLOSE_BLOCK. */
    private static final String CLOSE_BLOCK = "    }";
    /** The Constant END_OF_METHOD. */
    private static final String END_OF_METHOD = "();";
    /** The Constant ENTER. */
    private static final String ENTER = "\r\n";
    /** The Constant OPEN_BLOCK. */
    private static final String OPEN_BLOCK = "    {";
    /** The Constant PUBLIC. */
    private static final String PUBLIC = "    public ";
    /** The Constant TYPE_BOOLEAN. */
    private static final String TYPE_BOOLEAN = "boolean";
    /** The Constant TYPE_BYTE. */
    private static final String TYPE_BYTE = "byte";
    /** The Constant TYPE_DATE_TIME. */
    private static final String TYPE_DATE_TIME = "dateTime";
    /** The Constant TYPE_BINARY. */
    private static final String TYPE_BINARY = "base64Binary";
    /** The Constant TYPE_DATE. */
    private static final String TYPE_DATE = "date";
    /** The Constant TYPE_DOUBLE. */
    private static final String TYPE_DOUBLE = "double";
    /** The Constant TYPE_FLOAT. */
    private static final String TYPE_FLOAT = "float";
    /** The Constant TYPE_INT. */
    private static final String TYPE_INT = "int";
    /** The Constant TYPE_LONG. */
    private static final String TYPE_LONG = "long";
    /** The Constant TYPE_SHORT. */
    private static final String TYPE_SHORT = "short";
    /** The Constant TYPE_VOID. */
    private static final String TYPE_VOID = "void";
    /** The log. */
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);


    /*public CodeGenerator(File pDirectory, String pPackage)
    {
    this(pDirectory);
    this.m_Package = pPackage;
    }*/
    /**
     * Instantiates a new code generator.
     */
    public CodeGenerator()
    {
    }

    /**
     * Creates the package.
     * 
     * @param spackage the spackage
     * @param pDirectory the directory
     * @return the file
     * @throws CodeGeneratorException the code generator exception
     */
    private File createPackage(String spackage, File pDirectory) throws CodeGeneratorException
    {
        File createPackage = pDirectory;
        if (spackage != null && !spackage.equals(""))
        {
            File dir = new File(pDirectory.getPath() + File.separatorChar + spackage.replace('.', File.separatorChar));
            if (!dir.exists() && !dir.mkdirs())
            {
                throw new CodeGeneratorException("The directory " + dir.getPath() + " was not possible to create");
            }
            createPackage = dir;
        }
        return createPackage;
    }

    /**
     * Save file.
     * 
     * @param file the file
     * @param code the code
     * @throws CodeGeneratorException the code generator exception
     */
    private void saveFile(File file, String code) throws CodeGeneratorException
    {
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            out.write(code.getBytes());
            out.close();
        }
        catch (IOException ioe)
        {
            throw new CodeGeneratorException("The File " + file.getPath() + " was not possible to create", ioe);
        }
    }
    

    /**
     * Generate code by namespace.
     * 
     * @param namespace the namespace
     * @param createSWBcontent the create sw bcontent
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    public void generateCodeByNamespace(String namespace, boolean createSWBcontent, File pDirectory) throws CodeGeneratorException
    {
        String prefix = null;
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            boolean create = false;
            if (namespace == null)
            {
                create = true;
            }
            else
            {
                if (tpc.getURI() != null)
                {
                    String strnamespace = namespace;
                    if (!strnamespace.endsWith("#"))
                    {
                        strnamespace += "#";
                    }
                    strnamespace += tpc.getName();
                    if (tpc.getURI().equals(strnamespace.toString()))
                    {
                        prefix = tpc.getPrefix();
                        create = true;
                    }
                }

            }
            if (create)
            {
                if (pDirectory.exists() && pDirectory.isFile())
                {
                    throw new CodeGeneratorException("The path " + pDirectory.getPath() + " is not a directory");
                }
                if (!pDirectory.exists())
                {
                    if (!pDirectory.mkdirs())
                    {
                        throw new CodeGeneratorException("The path " + pDirectory.getPath() + " was not possible to create");
                    }
                }
                if (tpc.isSWBInterface())
                {
                    createInterfaceBase(tpc, pDirectory);
                }
                else if (tpc.isSWBSemanticResource())
                {
                    createSemanticResourceBase(tpc, pDirectory);
                }
                else if (tpc.isSWBClass() || tpc.isSWBModel() || tpc.isSWBFormElement())
                {
                    createClassBase(tpc, pDirectory);
                }
            }
        }
        if (createSWBcontent)
        {
            if (prefix != null)
            {
                createSWBContextBase(prefix, pDirectory);
                createSWBContext(prefix, pDirectory);
            }
        }
    }

    /*public void generateCode(URI namespace, boolean createSWBContext,File pDirectory) throws CodeGeneratorException
    {        
    SemanticMgr mgr = SWBPlatform.getSemanticMgr();
    Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
    while (tpcit.hasNext())
    {
    SemanticClass tpc = tpcit.next();
    boolean create = false;
    if (namespace == null)
    {
    create = true;
    }
    else
    {
    try
    {
    URI tpcNamespace = new URI(tpc.getURI());
    String strnamespace = namespace.toString();
    if (!strnamespace.endsWith("#"))
    {
    strnamespace += "#";
    }
    strnamespace += tpc.getName();
    if (tpcNamespace.toString().equals(strnamespace.toString()))
    {
    create = true;
    }
    }
    catch (Exception e)
    {
    e.printStackTrace();
    create = false;
    }
    }
    if (create)
    {
    if (pDirectory.exists() && pDirectory.isFile())
    {
    throw new CodeGeneratorException("The path " + pDirectory.getPath() + " is not a directory");
    }
    if (!pDirectory.exists())
    {
    if (!pDirectory.mkdirs())
    {
    throw new CodeGeneratorException("The path " + pDirectory.getPath() + " was not possible to create");
    }
    }
    if (tpc.isSWBInterface())
    {
    createInterface(tpc, pDirectory);
    }
    else if (tpc.isSWBSemanticResource())
    {
    createSemanticResourceBase(tpc, pDirectory);
    }
    else if (tpc.isSWBClass() || tpc.isSWBModel() || tpc.isSWBFormElement())
    {
    createClassBase(tpc, pDirectory);
    }
    }
    }
    if (createSWBContext)
    {
    createSWBContextBase("", pDirectory);
    createSWBContext("", pDirectory);
    }
    }*/
    private void checkClass(SemanticClass clazz) throws CodeGeneratorException
    {
        if(clazz.getCodePackage()==null)
        {
            throw new CodeGeneratorException("The code package for Semantic class " + clazz.getURI() + " is not defined\r\n");
        }
        Iterator<SemanticClass> tpcit = clazz.listSuperClasses(true);
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            if (clazz.isSWBInterface() && tpc.isSWBClass())
            {
                throw new CodeGeneratorException("The Semantic class " + clazz.getURI() + " is an interface and its parent is a class\r\n");
            }
            /*if(clazz.isSWBClass() && tpc.isSWBInterface())
            {
            throw new CodeGeneratorException("The semantic class "+ clazz.getURI() +" is a class and its parent is an interface");
            }*/
        }
        Iterator<SemanticProperty> properties=clazz.listProperties();
        while(properties.hasNext())
        {
            SemanticProperty tpp=properties.next();
            if(tpp.isObjectProperty())
            {
                try
                {
                    if (tpp.isString() || tpp.isXML() || tpp.isInt() || tpp.isFloat() || tpp.isDouble() || tpp.isLong() || tpp.isByte() || tpp.isShort() || tpp.isBoolean() || tpp.isDateTime() || tpp.isDate())
                    {
                        throw new CodeGeneratorException("The property "+tpp.getURI()+" for semantic class " + clazz.getURI() + " is defined as Object Property, but the type is "+ tpp.getRange().getURI() +" \r\n");
                    }
                }
                catch(Exception e)
                {
                    throw new CodeGeneratorException("The property "+tpp.getURI()+" has an error\r\n",e);
                }


            }
            if(tpp.isDataTypeProperty())
            {
                if (!(tpp.isString() || tpp.isXML() || tpp.isInt() || tpp.isFloat() || tpp.isDouble() || tpp.isLong() || tpp.isByte() || tpp.isShort() || tpp.isBoolean() || tpp.isDateTime() || tpp.isDate()))
                {
                    throw new CodeGeneratorException("The property "+tpp.getURI()+" for semantic class " + clazz.getURI() + " is defined as DataType Property, but the type is "+ tpp.getRange().getURI() +" \r\n");
                }
            }
        }
    }

    /**
     * Generate code.
     * 
     * @param prefix the prefix
     * @param createSWBContext the create swb context
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    public void generateCode(String prefix, boolean createSWBContext, File pDirectory) throws CodeGeneratorException
    {

        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            if (prefix == null || (tpc.getPrefix().equals(prefix)))
            {
                checkClass(tpc);
            }
        }
        tpcit = mgr.getVocabulary().listSemanticClasses();        
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();            
            boolean create = false;
            if (prefix == null)
            {
                create = true;
            }
            else
            {
                if (tpc.getPrefix().equals(prefix))
                {
                    create = true;
                }
            }
            if (create)
            {
                if (pDirectory.exists() && pDirectory.isFile())
                {
                    throw new CodeGeneratorException("The path " + pDirectory.getPath() + " is not a directory");
                }
                if (!pDirectory.exists())
                {
                    if (!pDirectory.mkdirs())
                    {
                        throw new CodeGeneratorException("The path " + pDirectory.getPath() + " was not possible to create");
                    }
                }
                if (tpc.isSWBInterface())
                {
                    createInterfaceBase(tpc, pDirectory);
                }
                else if (tpc.isSWBSemanticResource())
                {
                    createSemanticResourceBase(tpc, pDirectory);
                }
                else if (tpc.isSWBClass() || tpc.isSWBModel() || tpc.isSWBFormElement())
                {
                    createClassBase(tpc, pDirectory);
                }
            }
        }
        if (createSWBContext)
        {
            createSWBContextBase(prefix, pDirectory);
            createSWBContext(prefix, pDirectory);
        }
    }

    /**
     * Gets the interfaces.
     * 
     * @param tpc the tpc
     * @return the interfaces
     */
    private HashSet<SemanticClass> getInterfaces(SemanticClass tpc)
    {
        HashSet<SemanticClass> interfaces = new HashSet<SemanticClass>();
        Iterator<SemanticClass> it = tpc.listSuperClasses();
        while (it.hasNext())
        {
            SemanticClass clazz = it.next();
            if (clazz.isSWBInterface())
            {
                interfaces.add(clazz);
            }
        }
        return interfaces;
    }

    /**
     * Gets the interfaces as string.
     * 
     * @param tpc the tpc
     * @param isextends the isextends
     * @return the interfaces as string
     */
    private String getInterfacesAsString(SemanticClass tpc, boolean isextends)
    {
        StringBuilder interfaces = new StringBuilder();
        Iterator<SemanticClass> it = tpc.listSuperClasses();
        while (it.hasNext())
        {
            SemanticClass clazz = it.next();
            if (clazz.isSWBInterface())
            {
                interfaces.append(clazz.getCanonicalName() + ",");
            }
        }
        if (interfaces.length() > 0)
        {
            if (isextends)
            {
                interfaces.insert(0, "extends ");
            }
            else
            {
                interfaces.insert(0, "implements ");
            }
            interfaces.deleteCharAt(interfaces.length() - 1);
        }
        return interfaces.toString();
    }

    /**
     * List interfaces.
     * 
     * @param tpc the tpc
     * @return the hash set
     */
    private static HashSet<SemanticClass> listInterfaces(SemanticClass tpc)
    {
        HashSet<SemanticClass> interfaces = new HashSet<SemanticClass>();
        Iterator<SemanticClass> it = tpc.listSuperClasses();
        while (it.hasNext())
        {
            SemanticClass clazz = it.next();
            if (clazz.isSWBInterface())
            {
                interfaces.add(clazz);
            }
        }
        return interfaces;
    }

    /**
     * Creates the swb context.
     * 
     * @param prefix the prefix
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createSWBContext(String prefix, File pDirectory) throws CodeGeneratorException
    {
        // si existe no debe reemplazarlo
        String SWBContextClsName = "SWBContext";
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        String spackage = mgr.getCodePackage().getPackage(prefix);
        File dir = createPackage(spackage, pDirectory);
        dir = new File(dir.getPath() + File.separatorChar);
        File fileClass = new File(dir.getPath() + File.separatorChar + SWBContextClsName + ".java");
        if (!fileClass.exists())
        {
            StringBuilder javaClassContent = new StringBuilder();
            if (!spackage.equals(""))
            {
                javaClassContent.append("package " + spackage + ";" + ENTER);
                javaClassContent.append("" + ENTER);
            }            
            javaClassContent.append("import org.semanticwb.SWBUtils;" + ENTER);
            javaClassContent.append("import org.semanticwb.model.base.SWBContextBase;" + ENTER + ENTER);
            javaClassContent.append("public class SWBContext extends SWBContextBase" + ENTER);
            javaClassContent.append("{" + ENTER);

            javaClassContent.append("    private static org.semanticwb.Logger log=SWBUtils.getLogger(SWBContext.class);" + ENTER);
            javaClassContent.append("    private static SWBContext instance=null;" + ENTER);

            javaClassContent.append("    static public synchronized SWBContext createInstance()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        if (instance == null)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            instance = new SWBContext();" + ENTER);
            javaClassContent.append("        }" + ENTER);
            javaClassContent.append("        return instance;" + ENTER);
            javaClassContent.append("    }" + ENTER);

            javaClassContent.append("    private SWBContext()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        log.event(\"Initializing SemanticWebBuilder Context...\");" + ENTER);
            javaClassContent.append("    }" + ENTER);

            javaClassContent.append("}");
            saveFile(fileClass, javaClassContent.toString());
        }
    }

    /**
     * Creates the swb context base.
     * 
     * @param prefix the prefix
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createSWBContextBase(String prefix, File pDirectory) throws CodeGeneratorException
    {
        // Debe reemplazarlo siempre
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        String spackage = mgr.getCodePackage().getPackage(prefix);
        String ClsName = "SWBContextBase";
        File dir = createPackage(spackage, pDirectory);
        dir = new File(dir.getPath() + File.separatorChar + "base");
        StringBuilder javaClassContent = new StringBuilder();
        File fileClass = new File(dir.getPath() + File.separatorChar + ClsName + ".java");
        if (!spackage.equals(""))
        {
            javaClassContent.append("package " + spackage + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append("import "+UTIL_ITERATOR_FULL_NAME+";" + ENTER);
        javaClassContent.append("import org.semanticwb.model.*;" + ENTER);
        javaClassContent.append("import "+SEMANTIC_PLATFORM_FULL_NAME+";" + ENTER);
        javaClassContent.append("import "+SEMANTIC_MANANGER_FULL_NAME+";" + ENTER);
        javaClassContent.append("import "+MODEL_FULL_NAME+";" + ENTER);
        javaClassContent.append("import "+SEMANTIC_OBJECT_FULL_NAME+";" + ENTER);
        javaClassContent.append("import "+SEMANTIC_CLASS_FULL_NAME+";" + ENTER);


        javaClassContent.append("public class SWBContextBase" + ENTER);
        javaClassContent.append("{" + ENTER);

        javaClassContent.append("    private static "+SEMANTIC_MANANGER_FULL_NAME+" mgr="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr();" + ENTER);

        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();

            if (tpc.isSWBModel())
            {
                javaClassContent.append("    public static final SemanticClass " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static " + tpc.getCanonicalName()+ " get" + tpc.getUpperClassName() + "(String name)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".get" + tpc.getUpperClassName() + "(name);" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static "+UTIL_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> list" + tpc.getNameInPlural() + "()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        return ("+UTIL_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + ">)" + tpc.getPrefix() + "_" + tpc.getUpperClassName() + ".listGenericInstances();" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static void remove" + tpc.getUpperClassName() + "(String name)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".remove" + tpc.getUpperClassName() + "(name);" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static " + tpc.getCanonicalName() + " create" + tpc.getUpperClassName() + "(String name, String namespace)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("       return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".create" + tpc.getUpperClassName() + "(name, namespace);" + ENTER);
                javaClassContent.append("    }" + ENTER);
            }
        }
        javaClassContent.append("}" + ENTER);

        saveFile(fileClass, javaClassContent.toString());
    }

    /**
     * Creates the semantic resource.
     * 
     * @param tpc the tpc
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createSemanticResource(SemanticClass tpc, File pDirectory) throws CodeGeneratorException
    {

        File dir = createPackage(tpc.getCodePackage(), pDirectory);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + ".java");
        if (!fileClass.exists())
        {
            StringBuilder javaClassContent = new StringBuilder();
            if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
            {
                javaClassContent.append("package " + tpc.getCodePackage() + ";" + ENTER);
                javaClassContent.append("" + ENTER);
            }
            javaClassContent.append(ENTER);
            javaClassContent.append("import java.io.IOException;" + ENTER);
            javaClassContent.append("import java.io.PrintWriter;" + ENTER);
            javaClassContent.append("import javax.servlet.http.*;" + ENTER);
            javaClassContent.append("import org.semanticwb.portal.api.*;" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("public class " + tpc.getUpperClassName() + " extends " + tpc.getCodePackage() + ".base." + tpc.getUpperClassName() + "Base " + ENTER);
            javaClassContent.append("{" + ENTER);

            javaClassContent.append(ENTER);

            javaClassContent.append("    public " + tpc.getUpperClassName() + "()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("    }" + ENTER);

            javaClassContent.append(ENTER);

            javaClassContent.append("    public " + tpc.getUpperClassName() + "("+SEMANTIC_OBJECT_FULL_NAME+" base)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        super(base);" + ENTER);
            javaClassContent.append("    }" + ENTER);


            javaClassContent.append(ENTER);
            javaClassContent.append("    @Override" + ENTER);
            javaClassContent.append("    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        PrintWriter out=response.getWriter();" + ENTER);
            javaClassContent.append("        out.println(\"Hello " + tpc.getUpperClassName() + "...\");");
            javaClassContent.append("    }" + ENTER);
            javaClassContent.append(ENTER);
            javaClassContent.append("}" + ENTER);
            saveFile(fileClass, javaClassContent.toString());
        }
    }

    /**
     * Creates the semantic resource base.
     * 
     * @param tpc the tpc
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createSemanticResourceBase(SemanticClass tpc, File pDirectory) throws CodeGeneratorException
    {
        String exts = "org.semanticwb.portal.api.GenericSemResource";
        File dir = createPackage(tpc.getCodePackage(), pDirectory);
        SemanticClass parent = null;
        Iterator<SemanticClass> it = tpc.listSuperClasses(true);
        while (it.hasNext())
        {
            parent = it.next();
            if (parent.isSWBSemanticResource())
            {
                exts = parent.getCanonicalName();
                break;
            }
            else
            {
                parent = null;
            }
        }
        dir = new File(dir.getPath() + File.separatorChar + "base");
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        StringBuilder javaClassContent = new StringBuilder();
        if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
        {
            javaClassContent.append("package " + tpc.getCodePackage() + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append(ENTER);
        javaClassContent.append("public abstract class " + tpc.getUpperClassName() + "Base extends " + exts + " " + getInterfacesAsString(tpc, false) + "" + ENTER);
        javaClassContent.append("{" + ENTER);
        HashSet<SemanticClass> staticClasses = new HashSet<SemanticClass>();
        HashSet<SemanticProperty> staticProperties = new HashSet<SemanticProperty>();
        Iterator<SemanticProperty> properties = tpc.listProperties();

        //javaClassContent.append("    public static class " + GLOBAL_CLASS_NAME + ENTER);
        //javaClassContent.append("    {" + ENTER);

        while (properties.hasNext())
        {
            SemanticProperty tpp = properties.next();
            boolean isInClass = false;
            Iterator<SemanticClass> classes = tpc.listSuperClasses(true);
            while (classes.hasNext())
            {
                SemanticClass superclass = classes.next();
                if (superclass.isSWBClass() || superclass.isSWBModel() || superclass.isSWBFormElement())
                {
                    Iterator<SemanticProperty> propInterfaces = superclass.listProperties();
                    while (propInterfaces.hasNext())
                    {
                        SemanticProperty propSuperClass = propInterfaces.next();
                        if (propSuperClass.equals(tpp))
                        {
                            isInClass = true;
                            break;
                        }
                    }
                    if (isInClass)
                    {
                        break;
                    }
                }
            }
            if (!isInClass)
            {
                SemanticClass range = tpp.getRangeClass();
                if (range != null)
                {
                    if (!staticClasses.contains(range))
                    {
                        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + range.getPrefix() + "_" + range.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + range.getURI() + "\");" + ENTER);
                        staticClasses.add(range);
                    }
                }

                if (!staticProperties.contains(tpp))
                {
                    javaClassContent.append("    public static final "+SEMANTIC_PROPERTY_FULL_NAME+" " + tpp.getPrefix() + "_" + tpp.getName() + "="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr().getVocabulary().getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
                    //staticProperties.add(tpp);
                }
            }
        }

        if (!staticClasses.contains(tpc))
        {
            javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);
            staticClasses.add(tpc);
        }

        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" sclass="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);

        //javaClassContent.append("    }" + ENTER);

        javaClassContent.append(ENTER);

        javaClassContent.append("    public " + tpc.getUpperClassName() + "Base()" + ENTER);
        javaClassContent.append("    {" + ENTER);
        javaClassContent.append("    }" + ENTER);

        javaClassContent.append(ENTER);

        javaClassContent.append("    public " + tpc.getUpperClassName() + "Base("+SEMANTIC_OBJECT_FULL_NAME+" base)" + ENTER);
        javaClassContent.append("    {" + ENTER);
        javaClassContent.append("        super(base);" + ENTER);
        javaClassContent.append("    }" + ENTER);


        insertPropertiesToClass(tpc, javaClassContent, null, "SemanticObject");

        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + "Base.java");
        saveFile(fileClass, javaClassContent.toString());
        createSemanticResource(tpc, pDirectory);
    }

    /**
     * Creates the class base.
     * 
     * @param tpc the tpc
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createClassBase(SemanticClass tpc, File pDirectory) throws CodeGeneratorException
    {

        String exts = "org.semanticwb.model.base.GenericObjectBase";
        if (tpc.isSWBFormElement())
        {
            exts = "org.semanticwb.model.base.FormElementBase";
        }
        SemanticClass parent = null;
        Iterator<SemanticClass> it = tpc.listSuperClasses(true);
        while (it.hasNext())
        {
            parent = it.next();
            if (parent.isSWBClass() || parent.isSWBModel() || parent.isSWBFormElement())
            {
                exts = parent.getCanonicalName();
                break;
            }
            else
            {
                parent = null;
            }
        }
        File dir = createPackage(tpc.getCodePackage(), pDirectory);
        dir = new File(dir.getPath() + File.separatorChar + "base");

        if (!dir.exists())
        {
            dir.mkdirs();
        }
        StringBuilder javaClassContent = new StringBuilder();
        if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
        {
            javaClassContent.append("package " + tpc.getCodePackage() + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append(ENTER);
        javaClassContent.append("public abstract class " + tpc.getUpperClassName() + "Base extends " + exts + " " + getInterfacesAsString(tpc, false) + "" + ENTER);
        javaClassContent.append("{" + ENTER);
        HashSet<SemanticClass> staticClasses = new HashSet<SemanticClass>();
        HashSet<SemanticProperty> staticProperties = new HashSet<SemanticProperty>();
        Iterator<SemanticProperty> properties = tpc.listProperties();
        HashSet<SemanticClass> interfaces = getInterfaces(tpc);

        while (properties.hasNext())
        {
            SemanticProperty tpp = properties.next();

            boolean isInClass = false;
            Iterator<SemanticClass> classes = tpc.listSuperClasses(true);
            while (classes.hasNext())
            {
                SemanticClass superclass = classes.next();
                if (superclass.isSWBClass() || superclass.isSWBModel() || superclass.isSWBFormElement())
                {
                    Iterator<SemanticProperty> propInterfaces = superclass.listProperties();
                    while (propInterfaces.hasNext())
                    {
                        SemanticProperty propSuperClass = propInterfaces.next();
                        if (propSuperClass.equals(tpp))
                        {
                            isInClass = true;
                            break;
                        }
                    }
                    if (isInClass)
                    {
                        break;
                    }
                }
            }
            if (!isInClass)
            {
                for (SemanticClass ointerface : interfaces)
                {
                    Iterator<SemanticProperty> propInterfaces = ointerface.listProperties();
                    while (propInterfaces.hasNext())
                    {
                        SemanticProperty propSuperClass = propInterfaces.next();
                        if (propSuperClass.equals(tpp))
                        {
                            isInClass = true;
                            break;
                        }
                    }
                    if (isInClass)
                    {
                        break;
                    }
                }
            }


            if (!isInClass)
            {
                SemanticClass range = tpp.getRangeClass();
                if (range != null)
                {
                    if (!staticClasses.contains(range))
                    {
                        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + range.getPrefix() + "_" + range.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + range.getURI() + "\");" + ENTER);
                        staticClasses.add(range);
                    }
                }

                if (!staticProperties.contains(tpp))
                {
                    javaClassContent.append("    public static final "+SEMANTIC_PROPERTY_FULL_NAME+" " + tpp.getPrefix() + "_" + tpp.getName() + "="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr().getVocabulary().getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
                    staticProperties.add(tpp);
                }
            }
        }




        if (tpc.isSWBModel())
        {
            Iterator<SemanticClass> classesOfModel = tpc.listModelClasses();
            while (classesOfModel.hasNext())
            {
                SemanticClass clazzOfModel = classesOfModel.next();
                if (clazzOfModel != null)
                {
                    if (!staticClasses.contains(clazzOfModel))
                    {
                        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + clazzOfModel.getPrefix() + "_" + clazzOfModel.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + clazzOfModel.getURI() + "\");" + ENTER);
                        staticClasses.add(clazzOfModel);
                    }
                }
            }
        }
        if (!staticClasses.contains(tpc))
        {
            javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);
            staticClasses.add(tpc);
        }

        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" sclass="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);

        javaClassContent.append(ENTER);
        javaClassContent.append("    public static class " + GLOBAL_CLASS_NAME + ENTER);
        javaClassContent.append("    {" + ENTER);


        String fullpathClass = tpc.getCanonicalName();



        javaClassContent.append(ENTER);
        javaClassContent.append("        public static "+UTIL_ITERATOR_FULL_NAME+"<" + fullpathClass + "> list" + tpc.getNameInPlural() + "("+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
        javaClassContent.append("        {" + ENTER);
        javaClassContent.append("            "+UTIL_ITERATOR_FULL_NAME+" it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);" + ENTER);
        javaClassContent.append("            return new "+GENERIC_ITERATOR_FULL_NAME+"<" + fullpathClass + ">(it, true);" + ENTER);
        javaClassContent.append("        }" + ENTER);

        javaClassContent.append(ENTER);
        javaClassContent.append("        public static "+UTIL_ITERATOR_FULL_NAME+"<" + fullpathClass + "> list" + tpc.getNameInPlural() + "()" + ENTER);
        javaClassContent.append("        {" + ENTER);
        javaClassContent.append("            "+UTIL_ITERATOR_FULL_NAME+" it=sclass.listInstances();" + ENTER);
        javaClassContent.append("            return new "+GENERIC_ITERATOR_FULL_NAME+"<" + fullpathClass + ">(it, true);" + ENTER);
        javaClassContent.append("        }" + ENTER);



        if (tpc.isAutogenId())
        {
            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " create" + tpc.getUpperClassName() + "("+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            long id=model.getSemanticObject().getModel().getCounter(sclass);" + ENTER);
            javaClassContent.append("            return " + fullpathClass + "." + GLOBAL_CLASS_NAME + ".create" + tpc.getUpperClassName() + "(String.valueOf(id), model);" + ENTER);
            javaClassContent.append("        }" + ENTER);

        }
        if (tpc.isSWBModel())
        {
            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " get" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            "+SEMANTIC_MANANGER_FULL_NAME+" mgr="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr();" + ENTER);
            javaClassContent.append("            " + fullpathClass + " ret=null;" + ENTER);
            javaClassContent.append("            "+MODEL_FULL_NAME+" model=mgr.getModel(id);" + ENTER);
            javaClassContent.append("            if(model!=null)" + ENTER);
            javaClassContent.append("            {" + ENTER);
            javaClassContent.append("                "+SEMANTIC_OBJECT_FULL_NAME+" obj=model.getSemanticObject(model.getObjectUri(id,sclass));" + ENTER);
            javaClassContent.append("                if(obj!=null)" + ENTER);
            javaClassContent.append("                {" + ENTER);
            javaClassContent.append("                    ret=(" + fullpathClass + ")obj.createGenericInstance();" + ENTER);
            javaClassContent.append("                }" + ENTER);
            javaClassContent.append("            }" + ENTER);
            javaClassContent.append("            return ret;" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " create" + tpc.getUpperClassName() + "(String id, String namespace)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            "+SEMANTIC_MANANGER_FULL_NAME+" mgr="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr();" + ENTER);
            javaClassContent.append("            "+MODEL_FULL_NAME+" model=mgr.createModel(id, namespace);" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")model.createGenericObject(model.getObjectUri(id,sclass),sclass);" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static void remove" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            " + fullpathClass + " obj=get" + tpc.getUpperClassName() + "(id);" + ENTER);
            javaClassContent.append("            if(obj!=null)" + ENTER);
            javaClassContent.append("            {" + ENTER);
            javaClassContent.append("                obj.remove();" + ENTER);
            javaClassContent.append("            }" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static boolean has" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (get" + tpc.getUpperClassName() + "(id)!=null);" + ENTER);
            javaClassContent.append("        }" + ENTER);
        }
        else
        {
            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " get" + tpc.getUpperClassName() + "(String id, "+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")"+"model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " create" + tpc.getUpperClassName() + "(String id, "+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")"+"model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static void remove" + tpc.getUpperClassName() + "(String id, "+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            "+"model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static boolean has" + tpc.getUpperClassName() + "(String id, "+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (get" + tpc.getUpperClassName() + "(id, model)!=null);" + ENTER);
            javaClassContent.append("        }" + ENTER);
        }

        Iterator<SemanticProperty> tppit = tpc.listProperties();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            if (tpp.isObjectProperty() && !tpp.isNotCodeGeneration())
            {
                //if (!isPropertyOfParent(tpp, parent))
                //{
                SemanticClass tpcToReturn = tpp.getRangeClass();
                if (tpcToReturn != null && tpcToReturn.getURI() != null && tpcToReturn.isSWB())
                {
                    String nameList = tpp.getPropertyCodeName();
                    if (nameList.startsWith("has"))
                    {
                        nameList = nameList.substring(3);
                    }
                    javaClassContent.append(ENTER);
                    javaClassContent.append("        public static "+UTIL_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> list" + tpc.getUpperClassName() + "By" + toUpperCase(nameList) + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ","+SEMANTIC_MODEL_FULL_NAME+" model)" + ENTER);
                    javaClassContent.append("        {" + ENTER);
                    javaClassContent.append("            "+GENERIC_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> it=new "+GENERIC_ITERATOR_FULL_NAME+"(model.getSemanticObject().getModel().listSubjectsByClass(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ".getSemanticObject(),sclass));" + ENTER);
                    javaClassContent.append("            return it;" + ENTER);
                    javaClassContent.append("        }" + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("        public static "+UTIL_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> list" + tpc.getUpperClassName() + "By" + toUpperCase(nameList) + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                    javaClassContent.append("        {" + ENTER);
                    javaClassContent.append("            "+GENERIC_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> it=new "+GENERIC_ITERATOR_FULL_NAME+"(" + "value" + ".getSemanticObject().getModel().listSubjectsByClass(" + tpp.getPrefix() + "_" + tpp.getName() + "," + "value" + ".getSemanticObject(),sclass));" + ENTER);
                    javaClassContent.append("            return it;" + ENTER);
                    javaClassContent.append("        }" + ENTER);
                }
                //}
            }
        }

        javaClassContent.append("    }" + ENTER); // ennd ClassMgr

        javaClassContent.append(ENTER);
        javaClassContent.append(PUBLIC + tpc.getUpperClassName() + "Base("+SEMANTIC_OBJECT_FULL_NAME+" base)" + ENTER);
        javaClassContent.append(OPEN_BLOCK + ENTER);
        javaClassContent.append("        super(base);" + ENTER);
        javaClassContent.append(CLOSE_BLOCK + ENTER);

        insertPropertiesToClass(tpc, javaClassContent, parent);



        if (tpc.isSWBModel())
        {
            insertPropertiesToModel(tpc, javaClassContent);
        }
        else
        {
            if (parent == null)
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void remove()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        getSemanticObject().remove();" + ENTER);
                javaClassContent.append("    }" + ENTER);

                javaClassContent.append(ENTER);
                javaClassContent.append("    public "+UTIL_ITERATOR_FULL_NAME+"<"+GENERIC_OBJECT_FULL_NAME+"> listRelatedObjects()" + ENTER);
                javaClassContent.append("    {" + ENTER);

                javaClassContent.append("        return new "+GENERIC_ITERATOR_FULL_NAME+"(getSemanticObject().listRelatedObjects(),true);" + ENTER);
                javaClassContent.append("    }" + ENTER);
            }
            insertLinkToClass4Model(tpc, javaClassContent, parent);
        }
        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + "Base.java");
        try
        {
            System.out.println("Creando clase "+fileClass.getCanonicalPath());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        saveFile(fileClass, javaClassContent.toString());
        createClass(tpc, parent, pDirectory);
    }

    /**
     * Creates the class.
     * 
     * @param tpc the tpc
     * @param parent the parent
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createClass(SemanticClass tpc, SemanticClass parent, File pDirectory) throws CodeGeneratorException
    {
        String sPackage = tpc.getCodePackage();
        File dir = createPackage(sPackage, pDirectory);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + ".java");
        if (!fileClass.exists())
        {
            StringBuilder javaClassContent = new StringBuilder();
            if (!sPackage.equals(""))
            {
                javaClassContent.append("package " + sPackage + ";" + ENTER);
                javaClassContent.append("" + ENTER);
            }

            javaClassContent.append(ENTER);
            javaClassContent.append("public class " + tpc.getUpperClassName() + " extends " + tpc.getCodePackage() + ".base." + tpc.getUpperClassName() + "Base " + ENTER);



            javaClassContent.append("{" + ENTER);
            javaClassContent.append(PUBLIC + tpc.getUpperClassName() + "("+SEMANTIC_OBJECT_FULL_NAME+" base)" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);
            javaClassContent.append("        super(base);" + ENTER);
            javaClassContent.append(CLOSE_BLOCK + ENTER);
            //insertPropertiesToClass(tpc, javaClassContent);
            javaClassContent.append("}" + ENTER);
            saveFile(fileClass, javaClassContent.toString());
        }
    }

    /**
     * Creates the interface.
     * 
     * @param tpc the tpc
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createInterface(SemanticClass tpc, File pDirectory) throws CodeGeneratorException
    {
        File dir = createPackage(tpc.getCodePackage(), pDirectory);
        StringBuilder javaClassContent = new StringBuilder();

        if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
        {
            javaClassContent.append("package " + tpc.getCodePackage() + ";" + ENTER);
            javaClassContent.append("" + ENTER);
        }

        javaClassContent.append("public interface " + tpc.getUpperClassName() + " extends " + tpc.getCodePackage() + ".base." + tpc.getUpperClassName() + "Base" + ENTER);
        javaClassContent.append("{" + ENTER);
        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + ".java");
        boolean exists = false;
        if (fileClass.exists())
        {
            exists = true;
        }
        //exists=false;
        if (!exists)
        {
            saveFile(fileClass, javaClassContent.toString());
        }
    }

    /**
     * Creates the interface base.
     * 
     * @param tpc the tpc
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    private void createInterfaceBase(SemanticClass tpc, File pDirectory) throws CodeGeneratorException
    {
        File dir = createPackage(tpc.getCodePackage() + ".base", pDirectory);
        StringBuilder javaClassContent = new StringBuilder();
        if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
        {
            javaClassContent.append("package " + tpc.getCodePackage() + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        HashSet<SemanticClass> interfaces = getInterfaces(tpc);
        if (interfaces.isEmpty())
        {
            javaClassContent.append("public interface " + tpc.getUpperClassName() + "Base extends "+GENERIC_OBJECT_FULL_NAME+ ENTER);
        }
        else
        {
            javaClassContent.append("public interface " + tpc.getUpperClassName() + "Base " + getInterfacesAsString(tpc, true) + ENTER);
        }
        javaClassContent.append("{" + ENTER);
        HashSet<SemanticClass> staticClasses = new HashSet<SemanticClass>();
        Iterator<SemanticProperty> properties = tpc.listProperties();
        HashSet<String> rangeNames = new HashSet<String>();
        while (properties.hasNext())
        {
            SemanticProperty tpp = properties.next();
            SemanticClass range = tpp.getRangeClass();
            if (range != null)
            {
                if (!staticClasses.contains(range))
                {
                    boolean isInSuperInterface = false;
                    for (SemanticClass cInterface : interfaces)
                    {
                        Iterator<SemanticProperty> propertiesInterface = cInterface.listProperties();
                        while (propertiesInterface.hasNext())
                        {
                            SemanticProperty tppInterface = propertiesInterface.next();
                            SemanticClass rangeInterface = tppInterface.getRangeClass();
                            if (rangeInterface != null && rangeInterface.equals(range))
                            {
                                isInSuperInterface = true;
                                break;
                            }
                        }
                        if (isInSuperInterface)
                        {
                            break;
                        }
                    }
                    if (!isInSuperInterface)
                    {
                        String name = range.getPrefix() + "_" + range.getUpperClassName();
                        if (!rangeNames.contains(name))
                        {
                            javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + name + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + range.getURI() + "\");" + ENTER);
                            rangeNames.add(name);
                        }
                    }

                    staticClasses.add(range);
                }
            }
            boolean isInSuperInterface = false;
            for (SemanticClass cInterface : interfaces)
            {
                Iterator<SemanticProperty> propertiesInterface = cInterface.listProperties();
                while (propertiesInterface.hasNext())
                {
                    SemanticProperty tppInterface = propertiesInterface.next();
                    if (tppInterface.equals(tpp))
                    {
                        isInSuperInterface = true;
                        break;
                    }
                }
                if (isInSuperInterface)
                {
                    break;
                }
            }
            if (!isInSuperInterface)
            {
                String name = tpp.getPrefix() + "_" + tpp.getName();
                if (!rangeNames.contains(name))
                {
                    javaClassContent.append("    public static final "+SEMANTIC_PROPERTY_FULL_NAME+" " + name + "="+SEMANTIC_PLATFORM_FULL_NAME+".getSemanticMgr().getVocabulary().getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
                    rangeNames.add(name);
                }

            }
        }
        String name = tpc.getPrefix() + "_" + tpc.getUpperClassName();
        if (!rangeNames.contains(name))
        {
            javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + name + "="+SEMANTIC_PLATFORM_FULL_NAME+GET_SEMANTIC_CLASS + tpc.getURI() + "\");" + ENTER);
            rangeNames.add(name);
        }
        insertPropertiesToInterface(tpc, javaClassContent);
        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + "Base" + ".java");
        try
        {
            System.out.println("Creando interface "+fileClass.getCanonicalPath());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        saveFile(fileClass, javaClassContent.toString());
        createInterface(tpc, pDirectory);
    }

    /**
     * Insert properties to interface.
     * 
     * @param tpc the tpc
     * @param javaClassContent the java class content
     */
    private void insertPropertiesToInterface(SemanticClass tpc, StringBuilder javaClassContent)
    {
        Iterator<SemanticProperty> tppit = tpc.listProperties();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            boolean isInClass = false;
            Iterator<SemanticClass> classes = tpc.listSuperClasses(true);
            while (classes.hasNext())
            {
                SemanticClass superclass = classes.next();
                if (superclass.isSWBInterface())
                {
                    Iterator<SemanticProperty> propInterfaces = superclass.listProperties();
                    while (propInterfaces.hasNext())
                    {
                        SemanticProperty propSuperClass = propInterfaces.next();
                        if (propSuperClass.equals(tpp))
                        {
                            isInClass = true;
                            break;
                        }
                    }
                    if (isInClass)
                    {
                        break;
                    }
                }
            }
            if (!isInClass)
            {

                if (tpp.isObjectProperty())
                {                    
                    String classToReturn=null;
                    SemanticClass cls = tpp.getRangeClass();
                    if (cls != null && cls.isSWB())
                    {
                        SemanticClass tpcToReturn = tpp.getRangeClass();
                        classToReturn=tpcToReturn.getCanonicalName();                        
                    }
                    else if (tpp.getRange() != null)
                    {
                        classToReturn=SEMANTIC_OBJECT_FULL_NAME;
                    }
                    String objectName = tpp.getPropertyCodeName(); //tpp.getLabel();
                    if (objectName == null)
                    {
                        objectName = tpp.getName();
                    }
                    objectName = toUpperCase(objectName);

                    if (objectName.toLowerCase().startsWith("has"))
                    {
                       SemanticClass clsrange = tpp.getRangeClass();
                        if (clsrange != null && clsrange.getURI() != null && clsrange.isSWB())
                        {
                            // son varios
                            objectName = objectName.substring(3);
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public "+GENERIC_ITERATOR_FULL_NAME+"<" + classToReturn + "> list" + getPlural(objectName) + "();" + ENTER);
                            javaClassContent.append("    public boolean has" + objectName + "(" + classToReturn + " " + "value" + ");" + ENTER);
                            if (tpp.isInheritProperty())
                            {
                                javaClassContent.append("    public "+GENERIC_ITERATOR_FULL_NAME+"<" + classToReturn + "> listInherit" + getPlural(objectName) + "();" + ENTER);
                            }
                        }
                        else
                        {
                            // son varios
                            objectName = objectName.substring(3);
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public "+SEMANTIC_ITERATOR_FULL_NAME+"<" + classToReturn + "> list" + getPlural(objectName) + "();" + ENTER);
                            //TODO
                            //javaClassContent.append("    public boolean has" + objectName + "(" + pack + "." + valueToReturn + " " + "value" + ");" + ENTER);
                            if (tpp.isInheritProperty())
                            {
                                javaClassContent.append("    public "+SEMANTIC_ITERATOR_FULL_NAME+"<" + classToReturn + "> listInherit" + getPlural(objectName) + "();" + ENTER);
                            }
                        }

                        if (!tpp.hasInverse())
                        {
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public void add" + objectName + "(" + classToReturn + " " + "value" + ");" + ENTER);
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public void removeAll" + objectName + "();" + ENTER);
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public void remove" + objectName + "(" + classToReturn + " " + "value" + ");" + ENTER);
                            javaClassContent.append(ENTER);
                            javaClassContent.append(PUBLIC + classToReturn + " get" + objectName + "();" + ENTER);
                        }
                    }
                    else
                    {
                        javaClassContent.append(ENTER);
                        String parameterName = "value";                        
                        javaClassContent.append("    public void set" + objectName + "(" + classToReturn + " " + parameterName + ");" + ENTER);
                        javaClassContent.append(ENTER);
                        javaClassContent.append("    public void remove" + objectName + "();" + ENTER);
                        javaClassContent.append(ENTER);
                        javaClassContent.append(PUBLIC + classToReturn + " get" + objectName + "();" + ENTER);
                    }

                }
                else if (tpp.isDataTypeProperty())
                {


                    String objectName = tpp.getPropertyCodeName(); //tpp.getLabel();
                    if (objectName == null)
                    {
                        objectName = tpp.getName();
                    }
                    objectName = toUpperCase(objectName);
                    if (objectName.toLowerCase().startsWith("has"))
                    {
                        String type = TYPE_VOID;
                        if (tpp.isString() || tpp.isXML())
                        {
                            type = "String";
                        }
                        else if (tpp.isInt())
                        {
                            type = TYPE_INT;
                        }
                        else if (tpp.isFloat())
                        {
                            type = TYPE_FLOAT;
                        }
                        else if (tpp.isDouble())
                        {
                            type = TYPE_DOUBLE;
                        }
                        else if (tpp.isLong())
                        {
                            type = TYPE_LONG;
                        }
                        else if (tpp.isByte())
                        {
                            type = TYPE_BYTE;
                        }
                        else if (tpp.isShort())
                        {
                            type = TYPE_SHORT;
                        }
                        else if (tpp.isBoolean())
                        {
                            type = TYPE_BOOLEAN;
                        }
                        else if (tpp.isDateTime() || tpp.isDate())
                        {
                            type = "java.util.Date";
                        }
                        else
                        {
                            throw new IllegalArgumentException("Data type '" + tpp.getRange() + "' is no supported");
                        }
                        // son varios

                        objectName = objectName.substring(3);
                        javaClassContent.append(ENTER);

                        javaClassContent.append("    public "+UTIL_ITERATOR_FULL_NAME+"<" + type + "> list" + getPlural(objectName) + "();" + ENTER);


                        if (!tpp.hasInverse())
                        {
                            javaClassContent.append(ENTER);
                            javaClassContent.append("    public void add" + objectName + "(" + type + " value);" + ENTER);
                            javaClassContent.append("    public void removeAll" + objectName + "();" + ENTER);
                            javaClassContent.append("    public void remove" + objectName + "(" + type + " value);" + ENTER);
                        }
                    }
                    else
                    {
                        String type = TYPE_VOID;
                        String prefix = "get";
                        String getMethod = "getProperty";
                        String setMethod = "setProperty";
                        if (tpp.isString() || tpp.isXML())
                        {
                            type = "String";
                            getMethod = "getProperty";
                            setMethod = "setProperty";
                        }
                        else if (tpp.isInt())
                        {
                            type = TYPE_INT;
                            getMethod = "getIntProperty";
                            setMethod = "setIntProperty";
                        }
                        else if (tpp.isFloat())
                        {
                            type = TYPE_FLOAT;
                            getMethod = "getFloatProperty";
                            setMethod = "setFloatProperty";
                        }
                        else if (tpp.isDouble())
                        {
                            type = TYPE_DOUBLE;
                            getMethod = "getDoubleProperty";
                            setMethod = "setDoubleProperty";
                        }
                        else if (tpp.isLong())
                        {
                            type = TYPE_LONG;
                            getMethod = "getLongProperty";
                            setMethod = "setLongProperty";
                        }
                        else if (tpp.isByte())
                        {
                            type = TYPE_BYTE;
                            getMethod = "getByteProperty";
                            setMethod = "setByteProperty";
                        }
                        else if (tpp.isShort())
                        {
                            type = TYPE_SHORT;
                            getMethod = "getShortProperty";
                            setMethod = "setShortProperty";
                        }
                        else if (tpp.isBoolean())
                        {
                            type = TYPE_BOOLEAN;
                            prefix = "is";
                            getMethod = "getBooleanProperty";
                            setMethod = "setBooleanProperty";
                        }
                        else if (tpp.isDateTime() || tpp.isDate())
                        {
                            type = "java.util.Date";
                            getMethod = "getDateProperty";
                            setMethod = "setDateProperty";
                        }
                        else if (tpp.isBinary())
                        {
                            type = "java.io.InputStream";
                            getMethod = "getInputStreamProperty";
                            setMethod = "setInputStreamProperty";
                        }
                        else
                        {
                            throw new IllegalArgumentException("Data type '" + tpp.getRange() + "' is no supported");
                        }

                        getMethod = "get" + "SemanticObject" + "()." + getMethod;
                        setMethod = "get" + "SemanticObject" + "()." + setMethod;

                        String label = tpp.getPropertyCodeName(); //tpp.getLabel();
                        if (label == null)
                        {
                            label = tpp.getName();
                        }
                        String methodName = toUpperCase(label);
                        String propertyName = tpp.getName();
                        if (propertyName.equals("protected"))
                        {
                            propertyName = "_" + propertyName;
                        }
                        javaClassContent.append(ENTER);
                        if (type.equals("java.io.InputStream"))
                        {
                            javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "() throws Exception;" + ENTER);
                        }
                        else
                        {
                            javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "();" + ENTER);
                        }
                        /*javaClassContent.append(OPEN_BLOCK + ENTER);
                        if (tpp.isExternalInvocation())
                        {
                        javaClassContent.append("        //Override this method in " + toUpperCase(tpc.getClassCodeName()) + " object" + ENTER);
                        javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ",false);" + ENTER);
                        }
                        else
                        {
                        javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                        }
                        javaClassContent.append(CLOSE_BLOCK + ENTER);*/

                        javaClassContent.append(ENTER);
                        if (type.equals("java.io.InputStream"))
                        {
                            javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + "value" + ",String name) throws Exception;" + ENTER);
                        }
                        else
                        {
                            javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + "value" + ");" + ENTER);
                        }
                        /*javaClassContent.append(OPEN_BLOCK + ENTER);
                        if (tpp.isExternalInvocation())
                        {
                        javaClassContent.append("        //Override this method in " + toUpperCase(tpc.getClassCodeName()) + " object" + ENTER);
                        if (type.equals("java.io.InputStream"))
                        {
                        javaClassContent.append("        throw new org.semanticwb.SWBMethodImplementationRequiredException();" + ENTER);
                        }
                        else
                        {
                        javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ",false);" + ENTER);
                        }
                        }
                        else
                        {
                        if (type.equals("java.io.InputStream"))
                        {
                        javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ",name);" + ENTER);
                        }
                        else
                        {
                        javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ");" + ENTER);
                        }
                        }
                        javaClassContent.append(CLOSE_BLOCK + ENTER);*/

                        if (tpp.isLocaleable())
                        {
                            javaClassContent.append(ENTER);
                            javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "(String lang);" + ENTER);
                            if (type.equals("String"))
                            {
                                javaClassContent.append(ENTER);
                                javaClassContent.append(PUBLIC + type + " " + prefix + "Display" + methodName + "(String lang);" + ENTER);
                            }

                            javaClassContent.append(ENTER);
                            javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ", String lang);" + ENTER);

                        }
                    }
                }
            }
        }
    }

    /**
     * Insert link to class4 model.
     * 
     * @param tpcls the tpcls
     * @param javaClassContent the java class content
     * @param parent the parent
     */
    private void insertLinkToClass4Model(SemanticClass tpcls, StringBuilder javaClassContent, SemanticClass parent)
    {
        //if ( parent == null ) // Already exists this method in the parent class
        {
            Iterator<SemanticClass> tpcit = tpcls.listOwnerModels();
            while (tpcit.hasNext())
            {
                SemanticClass tpc = tpcit.next();
                javaClassContent.append(ENTER);
                javaClassContent.append("    public " + tpc.getCanonicalName() + " get" + tpc.getUpperClassName() + "()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        return (" + tpc.getCanonicalName() + ")getSemanticObject().getModel().getModelObject().createGenericInstance();" + ENTER);
                javaClassContent.append("    }" + ENTER);
            }
        }
    }

    

    /**
     * Insert properties to model.
     * 
     * @param tpcls the tpcls
     * @param javaClassContent the java class content
     */
    private void insertPropertiesToModel(SemanticClass tpcls, StringBuilder javaClassContent)
    {

        Iterator<SemanticClass> tpcit = tpcls.listModelClasses();
        while (tpcit.hasNext())
        {

            SemanticClass tpc = tpcit.next();
            javaClassContent.append(ENTER);

            javaClassContent.append("    public " + tpc.getCanonicalName() + " get" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".get" + tpc.getUpperClassName() + "(id, this);" + ENTER);
            javaClassContent.append("    }" + ENTER);
            javaClassContent.append(ENTER);

            javaClassContent.append("    public "+ UTIL_ITERATOR_FULL_NAME+"<" + tpc.getCanonicalName() + "> list" + tpc.getNameInPlural() + "()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".list" + tpc.getNameInPlural() + "(this);" + ENTER);
            javaClassContent.append("    }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("    public " + tpc.getCanonicalName() + " create" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".create" + tpc.getUpperClassName() + "(id,this);" + ENTER);
            javaClassContent.append("    }" + ENTER);

            if (tpc.isAutogenId())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public " + tpc.getCanonicalName() + " create" + tpc.getUpperClassName() + "()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        long id=getSemanticObject().getModel().getCounter(" + tpc.getPrefix() + "_" + tpc.getUpperClassName() + ");" + ENTER);
                javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".create" + tpc.getUpperClassName() + "(String.valueOf(id),this);" + ENTER);
                javaClassContent.append("    } " + ENTER);



            }
            javaClassContent.append(ENTER);
            javaClassContent.append("    public void remove" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".remove" + tpc.getUpperClassName() + "(id, this);" + ENTER);
            javaClassContent.append("    }" + ENTER);

            javaClassContent.append("    public boolean has" + tpc.getUpperClassName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return " + tpc.getCanonicalName() + "." + GLOBAL_CLASS_NAME + ".has" + tpc.getUpperClassName() + "(id, this);" + ENTER);
            javaClassContent.append("    }" + ENTER);
        }

    }

    /**
     * Checks if is property of parent.
     * 
     * @param tpp the tpp
     * @param parent the parent
     * @return true, if is property of parent
     */
    private boolean isPropertyOfParent(SemanticProperty tpp, SemanticClass parent)
    {
        boolean isPropertyOfParent = false;
        if (parent != null)
        {
            Iterator<SemanticProperty> properties = parent.listProperties();
            while (properties.hasNext())
            {
                SemanticProperty propertyOfParent = properties.next();
                if (getNameOfProperty(propertyOfParent).equals(getNameOfProperty(tpp)))
                {
                    isPropertyOfParent = true;
                    break;
                }
            }
        }
        return isPropertyOfParent;
    }

    /**
     * Gets the name of property.
     * 
     * @param tpp the tpp
     * @return the name of property
     */
    private String getNameOfProperty(SemanticProperty tpp)
    {
        String objectName = tpp.getPropertyCodeName();//tpp.getLabel();
        if (objectName == null)
        {
            objectName = tpp.getName();
        }
        return objectName;
    }

    /**
     * Insert data type property.
     * 
     * @param tpc the tpc
     * @param tpp the tpp
     * @param javaClassContent the java class content
     * @param semanticObject the semantic object
     */
    private void insertDataTypeProperty(SemanticClass tpc, SemanticProperty tpp, StringBuilder javaClassContent, String semanticObject)
    {
        String objectName = tpp.getPropertyCodeName();
        if (objectName == null)
        {
            objectName = tpp.getName();
        }
        objectName = toUpperCase(objectName);

        if (objectName.toLowerCase().startsWith("has"))
        {
            String type = TYPE_VOID;
            if (tpp.isString() || tpp.isXML())
            {
                type = "String";
            }
            else if (tpp.isInt())
            {
                type = TYPE_INT;
            }
            else if (tpp.isFloat())
            {
                type = TYPE_FLOAT;
            }
            else if (tpp.isDouble())
            {
                type = TYPE_DOUBLE;
            }
            else if (tpp.isLong())
            {
                type = TYPE_LONG;
            }
            else if (tpp.isByte())
            {
                type = TYPE_BYTE;
            }
            else if (tpp.isShort())
            {
                type = TYPE_SHORT;
            }
            else if (tpp.isBoolean())
            {
                type = TYPE_BOOLEAN;
            }
            else if (tpp.isDateTime() || tpp.isDate())
            {
                type = "java.util.Date";
            }
            else
            {
                throw new IllegalArgumentException("Data type '" + tpp.getRange() + "' is no supported");
            }
            // son varios

            objectName = objectName.substring(3);
            javaClassContent.append(ENTER);

            javaClassContent.append("    public "+UTIL_ITERATOR_FULL_NAME+"<" + type + "> list" + getPlural(objectName) + "()" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);


            javaClassContent.append("        java.util.ArrayList<" + type + "> values=new java.util.ArrayList<" + type + ">();" + ENTER);
            javaClassContent.append("        "+UTIL_ITERATOR_FULL_NAME+"<"+SEMANTIC_LITERAL_FULL_NAME+"> it=getSemanticObject().listLiteralProperties(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            javaClassContent.append("        while(it.hasNext())" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("                "+SEMANTIC_LITERAL_FULL_NAME+" literal=it.next();" + ENTER);
            javaClassContent.append("                values.add(literal.getString());" + ENTER);
            javaClassContent.append("        }" + ENTER);
            javaClassContent.append("        return values.iterator();" + ENTER);

            javaClassContent.append(CLOSE_BLOCK + ENTER);

            if (!tpp.hasInverse())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void add" + objectName + "(" + type + " value)" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().set" + (type.equals("String") ? "" : type) + "Property(" + tpp.getPrefix() + "_" + tpp.getName() + ", value);" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void remove" + objectName + "(" + type + " value)" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ",value);" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
            }

        }
        else
        {
            String type = TYPE_VOID;
            String prefix = "get";
            String getMethod = "getProperty";
            String setMethod = "setProperty";
            if (tpp.isString() || tpp.isXML())
            {
                type = "String";
                getMethod = "getProperty";
                setMethod = "setProperty";
            }
            else if (tpp.isInt())
            {
                type = TYPE_INT;
                getMethod = "getIntProperty";
                setMethod = "setIntProperty";
            }
            else if (tpp.isFloat())
            {
                type = TYPE_FLOAT;
                getMethod = "getFloatProperty";
                setMethod = "setFloatProperty";
            }
            else if (tpp.isDouble())
            {
                type = TYPE_DOUBLE;
                getMethod = "getDoubleProperty";
                setMethod = "setDoubleProperty";
            }
            else if (tpp.isLong())
            {
                type = TYPE_LONG;
                getMethod = "getLongProperty";
                setMethod = "setLongProperty";
            }
            else if (tpp.isByte())
            {
                type = TYPE_BYTE;
                getMethod = "getByteProperty";
                setMethod = "setByteProperty";
            }
            else if (tpp.isShort())
            {
                type = TYPE_SHORT;
                getMethod = "getShortProperty";
                setMethod = "setShortProperty";
            }
            else if (tpp.isBoolean())
            {
                type = TYPE_BOOLEAN;
                prefix = "is";
                getMethod = "getBooleanProperty";
                setMethod = "setBooleanProperty";
            }
            else if (tpp.isDateTime() || tpp.isDate())
            {
                type = "java.util.Date";
                getMethod = "getDateProperty";
                setMethod = "setDateProperty";
            }
            else if (tpp.isBinary())
            {
                type = "java.io.InputStream";
                getMethod = "getInputStreamProperty";
                setMethod = "setInputStreamProperty";
            }
            else
            {
                throw new IllegalArgumentException("Data type '" + tpp.getRange() + "' is no supported, from class:" + tpc + ", property:" + tpp);
            }

            getMethod = "get" + semanticObject + "()." + getMethod;
            setMethod = "get" + semanticObject + "()." + setMethod;

            String label = tpp.getPropertyCodeName(); //tpp.getLabel();
            if (label == null)
            {
                label = tpp.getName();
            }
            String methodName = toUpperCase(label);
            String propertyName = tpp.getName();
            if (propertyName.equals("protected"))
            {
                propertyName = "_" + propertyName;
            }
            javaClassContent.append(ENTER);
            if (type.equals("java.io.InputStream"))
            {
                javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "() throws Exception" + ENTER);
            }
            else
            {
                javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "()" + ENTER);
            }
            javaClassContent.append(OPEN_BLOCK + ENTER);
            if (tpp.isExternalInvocation())
            {
                javaClassContent.append("        //Override this method in " + tpc.getUpperClassName() + " object" + ENTER);
                javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ",false);" + ENTER);
            }
            else
            {
                javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            }
            javaClassContent.append(CLOSE_BLOCK + ENTER);

            javaClassContent.append(ENTER);
            if (type.equals("java.io.InputStream"))
            {
                javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + "value" + ",String name) throws Exception" + ENTER);
            }
            else
            {
                javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + "value" + ")" + ENTER);
            }
            javaClassContent.append(OPEN_BLOCK + ENTER);
            if (tpp.isExternalInvocation())
            {
                javaClassContent.append("        //Override this method in " + tpc.getUpperClassName() + " object" + ENTER);
                if (type.equals("java.io.InputStream"))
                {
                    javaClassContent.append("        throw new org.semanticwb.SWBMethodImplementationRequiredException();" + ENTER);
                }
                else
                {
                    javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ",false);" + ENTER);
                }
            }
            else
            {
                if (type.equals("java.io.InputStream"))
                {
                    javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ",name);" + ENTER);
                }
                else
                {
                    javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ");" + ENTER);
                }
            }
            javaClassContent.append(CLOSE_BLOCK + ENTER);

            if (tpp.isLocaleable())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "(String lang)" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                if (tpp.isExternalInvocation())
                {
                    javaClassContent.append("        //Override this method in " + tpc.getUpperClassName() + " object" + ENTER);
                    javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", null, lang,false);" + ENTER);

                }
                else
                {
                    javaClassContent.append("        return " + getMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", null, lang);" + ENTER);
                }
                javaClassContent.append(CLOSE_BLOCK + ENTER);

                if (type.equals("String"))
                {
                    javaClassContent.append(ENTER);
                    javaClassContent.append(PUBLIC + type + " " + prefix + "Display" + methodName + "(String lang)" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        return " + "get" + semanticObject + "().getLocaleProperty" + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", lang);" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                }

                javaClassContent.append(ENTER);
                javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ", String lang)" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                if (tpp.isExternalInvocation())
                {

                    javaClassContent.append("        //Override this method in " + tpc.getUpperClassName() + " object" + ENTER);
                    javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + tpp.getName() + ", lang,false);" + ENTER);

                }
                else
                {
                    javaClassContent.append("        " + setMethod + "(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + tpp.getName() + ", lang);" + ENTER);
                }
                javaClassContent.append(CLOSE_BLOCK + ENTER);
            }
        }

    }

    /**
     * Insert object property.
     * 
     * @param tpc the tpc
     * @param tpp the tpp
     * @param javaClassContent the java class content
     * @param semanticObject the semantic object
     */
    private void insertObjectProperty(SemanticClass tpc, SemanticProperty tpp, StringBuilder javaClassContent, String semanticObject)
    {
        SemanticClass cls = tpp.getRangeClass();
        if (cls != null && cls.getURI() != null && cls.isSWB())
        {

            String objectName = tpp.getPropertyCodeName();
            if (objectName == null)
            {
                objectName = tpp.getName();
            }
            objectName = toUpperCase(objectName);
            SemanticClass tpcToReturn = tpp.getRangeClass();
            if (objectName.toLowerCase().startsWith("has"))
            {
                // son varios
                objectName = objectName.substring(3);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public "+GENERIC_ITERATOR_FULL_NAME+"<" + tpcToReturn.getCanonicalName() + "> list" + getPlural(objectName) + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        return new "+GENERIC_ITERATOR_FULL_NAME+"<" + tpcToReturn.getCanonicalName() + ">(getSemanticObject().listObjectProperties(" + tpp.getPrefix() + "_" + tpp.getName() + "));" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public boolean has" + objectName + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        boolean ret=false;" + ENTER);
                javaClassContent.append("        if(" + "value" + "!=null)" + ENTER);
                javaClassContent.append("        {" + ENTER);
                javaClassContent.append("           ret=get" + semanticObject + "().hasObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + "," + "value" + ".getSemanticObject());" + ENTER);
                javaClassContent.append("        }" + ENTER);
                javaClassContent.append("        return ret;" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                if (tpp.isInheritProperty())
                {
                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public "+GENERIC_ITERATOR_FULL_NAME+"<" + tpcToReturn.getCanonicalName() + "> listInherit" + getPlural(objectName) + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        return new "+GENERIC_ITERATOR_FULL_NAME+"<" + tpcToReturn.getCanonicalName() + ">(getSemanticObject().listInheritProperties(" + tpp.getPrefix() + "_" + tpp.getName() + "));" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                }

                if (!tpp.hasInverse())
                {
                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void add" + objectName + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().addObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ".getSemanticObject());" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void remove" + objectName + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + "," + "value" + ".getSemanticObject());" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                }
            }
            else
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void set" + objectName + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        if(value!=null)" + ENTER);
                javaClassContent.append("        {" + ENTER);
                javaClassContent.append("            get" + semanticObject + "().setObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ".getSemanticObject());" + ENTER);
                javaClassContent.append("        }else" + ENTER);
                javaClassContent.append("        {" + ENTER);
                javaClassContent.append("            remove" + objectName + "();" + ENTER);
                javaClassContent.append("        }" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);

                javaClassContent.append(ENTER);
                javaClassContent.append("    public void remove" + objectName + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
            }

            String nameList = tpp.getPropertyCodeName();
            if (nameList.startsWith("has"))
            {
                nameList = nameList.substring(3);
            }

            javaClassContent.append(ENTER);
            javaClassContent.append(PUBLIC + tpcToReturn.getCanonicalName() + " get" + objectName + "()" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);
            javaClassContent.append("         " + tpcToReturn.getCanonicalName() + " ret=null;" + ENTER);
            javaClassContent.append("         "+SEMANTIC_OBJECT_FULL_NAME+" obj=getSemanticObject().getObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            javaClassContent.append("         if(obj!=null)" + ENTER);
            javaClassContent.append("         {" + ENTER);
            javaClassContent.append("             ret=(" + tpcToReturn.getCanonicalName() + ")obj.createGenericInstance();" + ENTER);
            javaClassContent.append("         }" + ENTER);
            javaClassContent.append("         return ret;" + ENTER);
            javaClassContent.append(CLOSE_BLOCK + ENTER);
        }
        else if (tpp.getRange() != null)
        {
            String classToReturn=SEMANTIC_OBJECT_FULL_NAME;
            String objectName = tpp.getPropertyCodeName(); //tpp.getLabel();
            if (objectName == null)
            {
                objectName = tpp.getName();
            }
            objectName = toUpperCase(objectName);

            if (objectName.toLowerCase().startsWith("has"))
            {
                // son varios
                objectName = objectName.substring(3);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public "+SEMANTIC_ITERATOR_FULL_NAME+"<" + classToReturn + "> list" + getPlural(objectName) + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
//                
                javaClassContent.append("        "+JENA_ITERATOR_FULL_NAME+" stit=getSemanticObject().getRDFResource().listProperties(" + tpp.getPrefix() + "_" + tpp.getName() + ".getRDFProperty());" + ENTER);
                javaClassContent.append("        return new "+SEMANTIC_ITERATOR_FULL_NAME+"<" + classToReturn + ">(stit);" + ENTER);
//               
                javaClassContent.append(CLOSE_BLOCK + ENTER);

                if (!tpp.hasInverse())
                {
                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void add" + objectName + "(" + classToReturn + " " + "value" + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().addObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void remove" + objectName + "(" + classToReturn + " value)" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ",value);" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                }
            }
            else
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void set" + objectName + "(" + classToReturn + " " + "value" + ")" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().setObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);

                javaClassContent.append(ENTER);
                javaClassContent.append("    public void remove" + objectName + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
            }

            javaClassContent.append(ENTER);
            javaClassContent.append(PUBLIC + classToReturn + " get" + objectName + "()" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);
            javaClassContent.append("         " + classToReturn + " ret=null;" + ENTER);
            javaClassContent.append("         ret=get" + semanticObject + "().getObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            javaClassContent.append("         return ret;" + ENTER);
            javaClassContent.append(CLOSE_BLOCK + ENTER);
        }
    }

    /**
     * Insert properties to class.
     * 
     * @param tpc the tpc
     * @param javaClassContent the java class content
     * @param parent the parent
     */
    private void insertPropertiesToClass(SemanticClass tpc, StringBuilder javaClassContent, SemanticClass parent)
    {
        insertPropertiesToClass(tpc, javaClassContent, parent, null);
    }

    /**
     * Insert properties to class.
     * 
     * @param tpc the tpc
     * @param javaClassContent the java class content
     * @param parent the parent
     * @param semanticObject the semantic object
     */
    private void insertPropertiesToClass(SemanticClass tpc, StringBuilder javaClassContent, SemanticClass parent, String semanticObject)
    {
        if (semanticObject == null)
        {
            semanticObject = "SemanticObject";
        }
        Iterator<SemanticProperty> tppit = tpc.listProperties();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            if (!isPropertyOfParent(tpp, parent))
            {
                if (tpp.isObjectProperty())
                {
                    insertObjectProperty(tpc, tpp, javaClassContent, semanticObject);
                }
                else if (tpp.isDataTypeProperty())
                {
                    insertDataTypeProperty(tpc, tpp, javaClassContent, semanticObject);
                }
            }
        }
    }

    /**
     * Creates the vocabulary.
     * 
     * @param pPackage the package
     * @param pDirectory the directory
     * @throws CodeGeneratorException the code generator exception
     */
    public void createVocabulary(String pPackage, File pDirectory) throws CodeGeneratorException
    {
        StringBuilder javaClassContent = new StringBuilder();
        String sPackage = pPackage;
        javaClassContent.append("package " + sPackage + ";\r\n" + ENTER);
        javaClassContent.append("import "+SEMANTIC_PLATFORM_FULL_NAME+";" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticVocabulary;" + ENTER);
        javaClassContent.append("import "+SEMANTIC_CLASS_FULL_NAME+";" + ENTER);
        javaClassContent.append("import "+SEMANTIC_PROPERTY_FULL_NAME+";" + ENTER);
        javaClassContent.append("import java.util.Hashtable;" + ENTER);
        //javaClassContent.append("import static org.semanticwb.platform.SemanticVocabulary.URI;\r\n" + ENTER);
        javaClassContent.append("public class SWBVocabulary" + ENTER);
        javaClassContent.append("{" + ENTER);
        javaClassContent.append("\r\n\r\n    //Classes" + ENTER);
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            javaClassContent.append("    public final SemanticClass " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + ";" + ENTER);
        }

        javaClassContent.append("\r\n\r\n\r\n    //Properties" + ENTER);
        HashSet<String> properties = new HashSet<String>();
        tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            Iterator<SemanticProperty> tppit = tpc.listProperties();
            while (tppit.hasNext())
            {
                SemanticProperty tpp = tppit.next();
                if (!properties.contains(tpp.getPrefix() + "_" + tpp.getName()))
                {
                    properties.add(tpp.getPrefix() + "_" + tpp.getName());
                    javaClassContent.append("    public final "+SEMANTIC_PROPERTY_FULL_NAME+" " + tpp.getPrefix() + "_" + tpp.getName() + ";" + ENTER);
                }
            }
        }
        javaClassContent.append("\r\n" + ENTER);
        //javaClassContent.append("    @Override"+ENTER);
        javaClassContent.append("    public SWBVocabulary()" + ENTER);
        javaClassContent.append("    {\r\n" + ENTER);
        javaClassContent.append("         SemanticVocabulary vocabulary=SWBPlatform.getSemanticMgr().getVocabulary();" + ENTER);
        javaClassContent.append("        // Classes" + ENTER);
        tpcit = mgr.getVocabulary().listSemanticClasses();
        Hashtable<String, String> namespaces = new Hashtable<String, String>();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            int pos = tpc.getURI().indexOf("#");
            String uri = tpc.getURI();
            if (pos != -1)
            {
                uri = uri.substring(0, pos + 1);
            }
            namespaces.put(tpc.getPrefix(), uri);
            javaClassContent.append("        " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + "=vocabulary.getSemanticClass(\"" + tpc.getURI() + "\");" + ENTER);
        }


        javaClassContent.append("\r\n\r\n\r\n        //Properties" + ENTER);
        tpcit = mgr.getVocabulary().listSemanticClasses();
        properties = new HashSet<String>();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            /*Iterator<SemanticProperty> requiredProperties=tpc.listRequiredProperties();
            while(requiredProperties.hasNext())
            {
            SemanticProperty tpp = requiredProperties.next();
            String propertyName=tpp.getPrefix() + "_" + tpp.getName();
            if ( !properties.contains(propertyName) )
            {
            properties.add(propertyName);
            if ( !tpp.getURI().equals("#") )
            {
            int pos = tpp.getURI().indexOf("#");
            String uri = tpp.getURI();
            if ( pos != -1 )
            {
            uri = uri.substring(0, pos+1);
            }
            namespaces.put(tpp.getPrefix(), uri);
            }
            javaClassContent.append("        " + propertyName + "=vocabulary.getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
            }
            }*/
            Iterator<SemanticProperty> tppit = tpc.listProperties();
            while (tppit.hasNext())
            {
                SemanticProperty tpp = tppit.next();
                String propertyName = tpp.getPrefix() + "_" + tpp.getName();
                if (!properties.contains(propertyName))
                {
                    properties.add(propertyName);
                    if (!tpp.getURI().equals("#"))
                    {
                        int pos = tpp.getURI().indexOf("#");
                        String uri = tpp.getURI();
                        if (pos != -1)
                        {
                            uri = uri.substring(0, pos + 1);
                        }
                        namespaces.put(tpp.getPrefix(), uri);
                    }
                    javaClassContent.append("        " + propertyName + "=vocabulary.getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
                }
            }
        }
        javaClassContent.append(CLOSE_BLOCK + ENTER);
        javaClassContent.append("\r\n\r\n\r\n        //ListUris" + ENTER);
        javaClassContent.append("              public Hashtable<String,String> listUris()" + ENTER);
        javaClassContent.append("              {" + ENTER);
        javaClassContent.append("                     Hashtable<String,String> namespaces=new Hashtable<String, String>();" + ENTER);
        for (String prefix : namespaces.keySet())
        {
            String uri = namespaces.get(prefix);
            javaClassContent.append("                 namespaces.put(\"" + prefix + "\",\"" + uri + "\");" + ENTER);
        }
        javaClassContent.append("                     return namespaces;" + ENTER);
        javaClassContent.append("              }" + ENTER);
        javaClassContent.append("}" + ENTER);

        File dir = createPackage(sPackage, pDirectory);
        File fileClass = new File(dir.getPath() + File.separatorChar + "SWBVocabulary.java");
        saveFile(fileClass, javaClassContent.toString());
    }
}
