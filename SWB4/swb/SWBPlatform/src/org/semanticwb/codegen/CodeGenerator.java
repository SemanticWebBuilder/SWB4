/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticMgr;

/**
 *
 * @author Victor Lorenzana
 */
public class CodeGenerator
{

    private static final String CLOSE_BLOCK = "    }";
    private static final String END_OF_METHOD = "();";
    private static final String ENTER = "\r\n";
    private static final String OPEN_BLOCK = "    {";
    private static final String PUBLIC = "    public ";
    private static final String TYPE_BOOLEAN = "boolean";
    private static final String TYPE_BYTE = "byte";
    private static final String TYPE_DOUBLE = "double";
    private static final String TYPE_FLOAT = "float";
    private static final String TYPE_INT = "int";
    private static final String TYPE_LONG = "long";
    private static final String TYPE_SHORT = "short";
    private static final String TYPE_VOID = "void";
    private static Logger log = SWBUtils.getLogger(SemanticObject.class);
    private File m_Directory;
    private String m_Package;

    public CodeGenerator(File pDirectory, String pPackage)
    {
        this(pDirectory);
        this.m_Package = pPackage;
    }

    public CodeGenerator(File pDirectory)
    {
        if ( pDirectory.exists() && pDirectory.isFile() )
        {
            throw new IllegalArgumentException("The path " + pDirectory.getPath() + " is not a directory");
        }
        this.m_Directory = pDirectory;
    }

    private String getPackage(SemanticClass tpc)
    {
        StringBuilder sPackage = new StringBuilder();
        try
        {
            URI uri = new URI(tpc.getURI());
            sPackage.append(uri.getHost());
        }
        catch ( URISyntaxException use )
        {
            log.error(use);
        }
        return sPackage.toString();
    }

    private File createPackage() throws CodeGeneratorException
    {
        File createPackage = this.m_Directory;
        if ( !this.m_Package.equals("") )
        {
            File dir = new File(this.m_Directory.getPath() + File.separatorChar + this.m_Package.replace('.', File.separatorChar));
            if ( !dir.exists() && !dir.mkdirs() )
            {
                throw new CodeGeneratorException("The directory " + dir.getPath() + " was not possible to create");
            }
            createPackage = dir;
        }
        return createPackage;
    }

    private void saveFile(File file, String code) throws CodeGeneratorException
    {
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            out.write(code.getBytes());
            out.close();
        }
        catch ( IOException ioe )
        {
            throw new CodeGeneratorException("The File " + file.getPath() + " was not possible to create", ioe);
        }
    }

    private static String toUpperCase(String data)
    {
        String letter = data.substring(0, 1);
        return letter.toUpperCase() + data.substring(1);
    }

    public void generateCode() throws CodeGeneratorException
    {
        if ( m_Directory.exists() && m_Directory.isFile() )
        {
            throw new CodeGeneratorException("The path " + m_Directory.getPath() + " is not a directory");
        }
        if ( !m_Directory.exists() )
        {
            if ( !m_Directory.mkdirs() )
            {
                throw new CodeGeneratorException("The path " + m_Directory.getPath() + " was not possible to create");
            }
        }
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            //System.out.println("tpc: " + tpc.toString() + " isSWBClass: " + tpc.isSWBClass()+ " isSWBFormElement: " + tpc.isSWBFormElement());
            if ( tpc.isSWBInterface() )
            {
                //System.out.println("tpc: " + tpc.toString() + " isSWBInterface: " + tpc.isSWBInterface());
                createInterface(tpc);
            }
            else if ( tpc.isSWBClass() || tpc.isSWBModel() || tpc.isSWBFormElement())
            {
                //System.out.println("tpc: " + tpc.toString() + " isSWBClass: " + tpc.isSWBClass()+ " isSWBFormElement: " + tpc.isSWBFormElement());
                createClassBase(tpc);
            }
        }
        //System.out.println("Creating Vocabulary");
        createVocabulary();
        createSWBContextBase();
        createSWBContext();
    }

    private static String getInterfaces(SemanticClass tpc)
    {
        StringBuilder interfaces = new StringBuilder();
        Iterator<SemanticClass> it = tpc.listSuperClasses();
        while (it.hasNext())
        {
            SemanticClass clazz = it.next();
            if ( clazz.isSWBInterface() )
            {
                interfaces.append(clazz.getName() + ",");
            }
        }
        if ( interfaces.length() > 0 )
        {
            interfaces.insert(0, "implements ");
            interfaces.deleteCharAt(interfaces.length() - 1);
        }
        return interfaces.toString();
    }

    private void createSWBContext() throws CodeGeneratorException
    {
        // si existe no debe reemplazarlo
        String ClassName = "SWBContext";
        File dir = createPackage();
        dir = new File(dir.getPath() + File.separatorChar);
        File fileClass = new File(dir.getPath() + File.separatorChar + ClassName + ".java");
        if ( !fileClass.exists() )
        {
            StringBuilder javaClassContent = new StringBuilder();
            if ( !m_Package.equals("") )
            {
                javaClassContent.append("package " + m_Package + ";" + ENTER);
                javaClassContent.append("" + ENTER);
            }
            javaClassContent.append("import org.semanticwb.Logger;" + ENTER);
            javaClassContent.append("import org.semanticwb.SWBUtils;" + ENTER);
            javaClassContent.append("import org.semanticwb.model.base.SWBContextBase;" + ENTER + ENTER);
            javaClassContent.append("public class SWBContext extends SWBContextBase" + ENTER);
            javaClassContent.append("{" + ENTER);

            javaClassContent.append("    private static Logger log=SWBUtils.getLogger(SWBContext.class);" + ENTER);
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

    private void createSWBContextBase() throws CodeGeneratorException
    {
        // Debe reemplazarlo siempre
        String ClassName = "SWBContextBase";
        File dir = createPackage();
        dir = new File(dir.getPath() + File.separatorChar + "base");
        StringBuilder javaClassContent = new StringBuilder();
        File fileClass = new File(dir.getPath() + File.separatorChar + ClassName + ".java");
        if ( !m_Package.equals("") )
        {
            javaClassContent.append("package " + m_Package + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append("import java.util.Iterator;" + ENTER);
        javaClassContent.append("import org.semanticwb.model.*;" + ENTER);
        javaClassContent.append("import org.semanticwb.SWBPlatform;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticMgr;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticModel;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticObject;" + ENTER);

        javaClassContent.append("public class SWBContextBase" + ENTER);
        javaClassContent.append("{" + ENTER);

        javaClassContent.append("    private static SWBVocabulary vocabulary=new SWBVocabulary();" + ENTER);
        javaClassContent.append("    private static SemanticMgr mgr=SWBPlatform.getSemanticMgr();" + ENTER);

        javaClassContent.append("    public static SWBVocabulary getVocabulary()" + ENTER);
        javaClassContent.append("    {" + ENTER);
        javaClassContent.append("        return vocabulary;" + ENTER);
        javaClassContent.append("    }" + ENTER);


        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            
            if(tpc.isSWBModel())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static " + tpc.getName() + " get" + tpc.getName() + "(String uri)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        " + tpc.getName() + " ret=null;" + ENTER);
                javaClassContent.append("        SemanticModel model=mgr.getModel(uri);" + ENTER);
                javaClassContent.append("        if(model!=null)" + ENTER);
                javaClassContent.append("        {" + ENTER);
                javaClassContent.append("            SemanticObject obj=model.getSemanticObject(uri);" + ENTER);
                javaClassContent.append("            if(obj!=null)" + ENTER);
                javaClassContent.append("            {" + ENTER);
                javaClassContent.append("                ret=(" + tpc.getName() + ")new " + tpc.getName() + "(obj);" + ENTER);
                javaClassContent.append("            }" + ENTER);
                javaClassContent.append("        }" + ENTER);
                javaClassContent.append("        return ret;" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static Iterator<org.semanticwb.model." + tpc.getName() + "> list" + tpc.getName() + "s()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        return (Iterator<org.semanticwb.model." + tpc.getName() + ">)vocabulary." + tpc.getName() + ".listGenericInstances();" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static void remove" + tpc.getName() + "(String uri)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        mgr.removeModel(uri);" + ENTER);
                javaClassContent.append("    }" + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public static " + tpc.getName() + " create" + tpc.getName() + "(String name, String namespace)" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        SemanticModel model=mgr.createModel(name, namespace);" + ENTER);
                javaClassContent.append("        return (" + tpc.getName() + ")model.createGenericObject(name, vocabulary." + tpc.getName() + ");" + ENTER);
                javaClassContent.append("    }" + ENTER);
            }
        }
        javaClassContent.append("}" + ENTER);

        saveFile(fileClass, javaClassContent.toString());
    }

    private void createClassBase(SemanticClass tpc) throws CodeGeneratorException
    {
        if ( m_Package == null )
        {
            m_Package = getPackage(tpc);
        }
        File dir = createPackage();
        dir = new File(dir.getPath() + File.separatorChar + "base");
        if ( !dir.exists() )
        {
            dir.mkdirs();
        }
        StringBuilder javaClassContent = new StringBuilder();
        if ( !m_Package.equals("") )
        {
            javaClassContent.append("package " + m_Package + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        
        javaClassContent.append("import java.util.Date;" + ENTER);
        javaClassContent.append("import java.util.Iterator;" + ENTER);
        javaClassContent.append("import " + m_Package + ".*;" + ENTER);
        javaClassContent.append("import com.hp.hpl.jena.rdf.model.*;" + ENTER);
        javaClassContent.append("import org.semanticwb.*;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.*;" + ENTER);

        String exts="GenericObjectBase";
        if(tpc.isSWBFormElement())exts="FormElementBase";
        
        javaClassContent.append(ENTER);
        javaClassContent.append("public class " + tpc.getName() + "Base extends "+exts+" " + getInterfaces(tpc) + "" + ENTER);
        javaClassContent.append("{" + ENTER);
        javaClassContent.append("    SWBVocabulary vocabulary=SWBContext.getVocabulary();" + ENTER);
        javaClassContent.append(ENTER);
        javaClassContent.append(PUBLIC + tpc.getName() + "Base(SemanticObject base)" + ENTER);
        javaClassContent.append(OPEN_BLOCK + ENTER);
        javaClassContent.append("        super(base);" + ENTER);
        javaClassContent.append(CLOSE_BLOCK + ENTER);
        insertPropertiesToClass(tpc, javaClassContent);
        if(tpc.isSWBModel())
        {
            insertPropertiesToModel(tpc,javaClassContent);
        }else
        {
            javaClassContent.append(ENTER);
            javaClassContent.append("    public void remove()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        getSemanticObject().remove();" + ENTER);
            javaClassContent.append("    }" + ENTER);     
            
            javaClassContent.append(ENTER);
            javaClassContent.append("    public Iterator<GenericObject> listRelatedObjects()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, null, getSemanticObject().getRDFResource());" + ENTER);
            javaClassContent.append("        return new GenericIterator((SemanticClass)null, stit,true);" + ENTER);
            javaClassContent.append("    }" + ENTER);
            
            insertLinkToClass4Model(tpc,javaClassContent);
        }
        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getName() + "Base.java");
        saveFile(fileClass, javaClassContent.toString());
        createClass(tpc);
    }

    private void createClass(SemanticClass tpc) throws CodeGeneratorException
    {
        if ( m_Package == null )
        {
            m_Package = getPackage(tpc);
        }
        File dir = createPackage();
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getName() + ".java");
        if ( !fileClass.exists() )
        {
            StringBuilder javaClassContent = new StringBuilder();
            if ( !m_Package.equals("") )
            {
                javaClassContent.append("package " + m_Package + ";" + ENTER);
                javaClassContent.append("" + ENTER);
            }
            javaClassContent.append("import " + m_Package + ".base.*;" + ENTER);
            javaClassContent.append("import org.semanticwb.platform.SemanticObject;" + ENTER);
            javaClassContent.append(ENTER);
            javaClassContent.append("public class " + tpc.getName() + " extends " + tpc.getName() + "Base " + ENTER);
            javaClassContent.append("{" + ENTER);
            javaClassContent.append(PUBLIC + tpc.getName() + "(SemanticObject base)" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);
            javaClassContent.append("        super(base);" + ENTER);
            javaClassContent.append(CLOSE_BLOCK + ENTER);
            //insertPropertiesToClass(tpc, javaClassContent);
            javaClassContent.append("}" + ENTER);
            saveFile(fileClass, javaClassContent.toString());
        }
    }

    private void createInterface(SemanticClass tpc) throws CodeGeneratorException
    {
        if ( m_Package == null )
        {
            m_Package = getPackage(tpc);
        }
        File dir = createPackage();
        StringBuilder javaClassContent = new StringBuilder();
        if ( !m_Package.equals("") )
        {
            javaClassContent.append("package " + m_Package + ";" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append("import org.semanticwb.model.GenericIterator;" + ENTER);
        javaClassContent.append("import java.util.Date;" + ENTER);
        javaClassContent.append("public interface " + tpc.getName() + " extends GenericObject" + ENTER);
        javaClassContent.append("{" + ENTER);
        insertPropertiesToInterface(tpc, javaClassContent);
        javaClassContent.append("}" + ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getName() + ".java");
        saveFile(fileClass, javaClassContent.toString());
    }

    private void insertPropertiesToInterface(SemanticClass tpc, StringBuilder javaClassContent)
    {
        Iterator<SemanticProperty> tppit = tpc.listProperties();
//        HashSet<String> methods = new HashSet<String>();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            if ( tpp.isObjectProperty() )
            {
                if ( tpp.getRangeClass() != null && tpp.getRangeClass().getURI() != null )
                {
                    try
                    {
                        URI uri = new URI(tpp.getRangeClass().getURI());
                        String objectName = tpp.getLabel();
                        if(objectName==null)objectName=tpp.getName();
                        objectName = toUpperCase(objectName);
                        String valueToReturn = uri.getFragment();
//                        if ( !methods.contains(objectName) )
                        {
//                            methods.add(objectName);
                            if ( objectName.toLowerCase().startsWith("has") )
                            {
                                // son varios
                                objectName = objectName.substring(3);
                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public GenericIterator<" + m_Package + "." + valueToReturn + "> list" + objectName + "s();" + ENTER);
                                if(!tpp.hasInverse())
                                {
                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void add" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ");" + ENTER);
                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void removeAll" + objectName + "();" + ENTER);
                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void remove" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ");" + ENTER);
                                    javaClassContent.append(ENTER);
                                    javaClassContent.append(PUBLIC + valueToReturn + " get" + objectName + "();" + ENTER);
                                }
                            }
                            else
                            {
                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public void set" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ");" + ENTER);
                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public void remove" + objectName + "();" + ENTER);
                                javaClassContent.append(ENTER);
                                javaClassContent.append(PUBLIC + valueToReturn + " get" + objectName + "();" + ENTER);
                            }
                        }
                    }
                    catch ( URISyntaxException usie )
                    {
                        log.error(usie);
                    }
                }
            }
            else if ( tpp.isDataTypeProperty() )
            {
                try
                {
                    URI uri = new URI(tpp.getRange().getURI());
                    String type = TYPE_VOID;
                    String prefix = "get";
                    if ( uri.getFragment().equals("string") )
                    {
                        type = "String";
                    }
                    else if ( uri.getFragment().equals(TYPE_INT) )
                    {
                        type = TYPE_INT;

                    }
                    else if ( uri.getFragment().equals(TYPE_FLOAT) )
                    {
                        type = TYPE_FLOAT;

                    }
                    else if ( uri.getFragment().equals(TYPE_DOUBLE) )
                    {
                        type = TYPE_DOUBLE;

                    }
                    else if ( uri.getFragment().equals(TYPE_LONG) )
                    {
                        type = TYPE_LONG;

                    }
                    else if ( uri.getFragment().equals(TYPE_BYTE) )
                    {
                        type = TYPE_BYTE;

                    }
                    else if ( uri.getFragment().equals(TYPE_SHORT) )
                    {
                        type = TYPE_SHORT;

                    }
                    else if ( uri.getFragment().equals(TYPE_BOOLEAN) )
                    {
                        type = TYPE_BOOLEAN;
                        prefix = "is";
                    }
                    else if ( uri.getFragment().equals("dateTime") )
                    {
                        type = "Date";
                    }
                    else
                    {
                        type = TYPE_VOID;
                    }
                    String label=tpp.getLabel();
                    if(label==null)label=tpp.getName();
                    String methodName = toUpperCase(label);
                    javaClassContent.append(PUBLIC + type + " " + prefix + methodName + END_OF_METHOD + ENTER);
                    javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ");" + ENTER);
                    if(tpp.isLocaleable())
                    {
                        javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "(String lang);" + ENTER);
                        javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ", String lang);" + ENTER);
                    }
                }
                catch ( URISyntaxException usie )
                {
                    log.error(usie);
                }
            }
        }
    }
    
    private void insertLinkToClass4Model(SemanticClass tpcls, StringBuilder javaClassContent)
    {
        Iterator<SemanticClass> tpcit = tpcls.listOwnerModels();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            //System.out.println("LinkToClass4Model:"+tpc);
            javaClassContent.append(ENTER);
            javaClassContent.append("    public " + tpc.getName() + " get" + tpc.getName() + "()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return new " + tpc.getName() + "(getSemanticObject().getModel().getModelObject());" + ENTER);
            javaClassContent.append("    }" + ENTER);
        }
    }
            
    
    private void insertPropertiesToModel(SemanticClass tpcls, StringBuilder javaClassContent)
    {
        Iterator<SemanticClass> tpcit = tpcls.listModelClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            
            javaClassContent.append(ENTER);
            javaClassContent.append("    public " + tpc.getName() + " get" + tpc.getName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return (" + tpc.getName() + ")getSemanticObject().getModel().getGenericObject(getSemanticObject().getModel().getObjectUri(id,vocabulary." + tpc.getName() + "),vocabulary." + tpc.getName() + ");" + ENTER);
            javaClassContent.append("    }" + ENTER);
            javaClassContent.append(ENTER);
            javaClassContent.append("    public Iterator<" + tpc.getName() + "> list" + tpc.getName() + "s()" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        Property rdf=getSemanticObject().getModel().getRDFModel().getProperty(SemanticVocabulary.RDF_TYPE);" + ENTER);
            javaClassContent.append("        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, rdf, vocabulary." + tpc.getName() + ".getOntClass());" + ENTER);
            javaClassContent.append("        return new GenericIterator<" + tpc.getName() + ">(" + tpc.getName() + ".class, stit, true);" + ENTER);
            javaClassContent.append("    }" + ENTER);
            javaClassContent.append(ENTER);
            javaClassContent.append("    public " + tpc.getName() + " create" + tpc.getName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return (" + tpc.getName() + ")getSemanticObject().getModel().createGenericObject(getSemanticObject().getModel().getObjectUri(id, vocabulary." + tpc.getName() + "), vocabulary." + tpc.getName() + ");" + ENTER);
            javaClassContent.append("    }" + ENTER);
            if(tpc.isAutogenId())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public " + tpc.getName() + " create" + tpc.getName() + "()" + ENTER);
                javaClassContent.append("    {" + ENTER);
                javaClassContent.append("        long id=SWBPlatform.getSemanticMgr().getCounter(getSemanticObject().getModel().getName()+\"/\"+vocabulary." + tpc.getName() + ".getName());" + ENTER);
                javaClassContent.append("        return create" + tpc.getName() + "(\"\"+id);" + ENTER);
                javaClassContent.append("    } " + ENTER);
            }
            javaClassContent.append(ENTER);
            javaClassContent.append("    public void remove" + tpc.getName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        getSemanticObject().getModel().removeSemanticObject(getSemanticObject().getModel().getObjectUri(id,vocabulary." + tpc.getName() + "));" + ENTER);
            javaClassContent.append("    }" + ENTER);
            
            javaClassContent.append("    public boolean has" + tpc.getName() + "(String id)" + ENTER);
            javaClassContent.append("    {" + ENTER);
            javaClassContent.append("        return (get" + tpc.getName() + "(id)!=null);" + ENTER);
            javaClassContent.append("    }" + ENTER);
        }
    }

    private void insertPropertiesToClass(SemanticClass tpc, StringBuilder javaClassContent)
    {
        Iterator<SemanticProperty> tppit = tpc.listProperties();
//        HashSet<String> methods = new HashSet<String>();
        while (tppit.hasNext())
        {
            SemanticProperty tpp = tppit.next();
            if ( tpp.isObjectProperty() )
            {
                if ( tpp.getRangeClass() != null && tpp.getRangeClass().getURI() != null )
                {
                    try
                    {
                        URI uri = new URI(tpp.getRangeClass().getURI());
                        String objectName = tpp.getLabel();
                        if(objectName==null)objectName=tpp.getName();
                        objectName = toUpperCase(objectName);
                        String valueToReturn = uri.getFragment();
//                        if ( !methods.contains(objectName) )
                        {
//                            methods.add(objectName);
                            if ( objectName.toLowerCase().startsWith("has") )
                            {
                                // son varios
                                objectName = objectName.substring(3);
                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public GenericIterator<" + m_Package + "." + valueToReturn + "> list" + objectName + "s()" + ENTER);
                                javaClassContent.append(OPEN_BLOCK + ENTER);
                                if(!tpp.hasInverse())
                                {                                
                                    javaClassContent.append("        StmtIterator stit=getSemanticObject().getRDFResource().listProperties(vocabulary." + tpp.getName() + ".getRDFProperty());" + ENTER);
                                    javaClassContent.append("        return new GenericIterator<" + m_Package + "." + valueToReturn + ">(" + m_Package + "." + valueToReturn + ".class, stit);" + ENTER);
                                }else
                                {
                                    javaClassContent.append("        StmtIterator stit=getSemanticObject().getModel().getRDFModel().listStatements(null, vocabulary." + tpp.getName() + ".getInverse().getRDFProperty(), getSemanticObject().getRDFResource());" + ENTER);
                                    javaClassContent.append("        return new GenericIterator<" + m_Package + "." + valueToReturn + ">(" + m_Package + "." + valueToReturn + ".class, stit,true);" + ENTER);
                                }
                                javaClassContent.append(CLOSE_BLOCK + ENTER);

                                if(!tpp.hasInverse())
                                {
                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void add" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ")" + ENTER);
                                    javaClassContent.append(OPEN_BLOCK + ENTER);
                                    javaClassContent.append("        getSemanticObject().addObjectProperty(vocabulary." + tpp.getName() + ", " + valueToReturn.toLowerCase() + ".getSemanticObject());" + ENTER);
                                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                                    javaClassContent.append(OPEN_BLOCK + ENTER);
                                    javaClassContent.append("        getSemanticObject().removeProperty(vocabulary." + tpp.getName() + ");" + ENTER);
                                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                                    javaClassContent.append(ENTER);
                                    javaClassContent.append("    public void remove" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ")" + ENTER);
                                    javaClassContent.append(OPEN_BLOCK + ENTER);
                                    javaClassContent.append("        getSemanticObject().removeObjectProperty(vocabulary." + tpp.getName() + "," + valueToReturn.toLowerCase() + ".getSemanticObject());" + ENTER);
                                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                                }
                            }
                            else
                            {
                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public void set" + objectName + "(" + m_Package + "." + valueToReturn + " " + valueToReturn.toLowerCase() + ")" + ENTER);
                                javaClassContent.append(OPEN_BLOCK + ENTER);
                                javaClassContent.append("        getSemanticObject().setObjectProperty(vocabulary." + tpp.getName() + ", " + valueToReturn.toLowerCase() + ".getSemanticObject());" + ENTER);
                                javaClassContent.append(CLOSE_BLOCK + ENTER);

                                javaClassContent.append(ENTER);
                                javaClassContent.append("    public void remove" + objectName + "()" + ENTER);
                                javaClassContent.append(OPEN_BLOCK + ENTER);
                                javaClassContent.append("        getSemanticObject().removeProperty(vocabulary." + tpp.getName() + ");" + ENTER);
                                javaClassContent.append(CLOSE_BLOCK + ENTER);
                            }
                            
                            javaClassContent.append(ENTER);
                            javaClassContent.append(PUBLIC + valueToReturn + " get" + objectName + "()" + ENTER);
                            javaClassContent.append(OPEN_BLOCK + ENTER);
                            javaClassContent.append("         "+valueToReturn+" ret=null;" + ENTER);
                            javaClassContent.append("         SemanticObject obj=getSemanticObject().getObjectProperty(vocabulary." + tpp.getName() + ");" + ENTER);
                            javaClassContent.append("         if(obj!=null)" + ENTER);
                            javaClassContent.append("         {" + ENTER);
                            javaClassContent.append("             ret=("+valueToReturn+")vocabulary."+valueToReturn+".newGenericInstance(obj);" + ENTER);
                            javaClassContent.append("         }" + ENTER);
                            javaClassContent.append("         return ret;" + ENTER);                           
                            javaClassContent.append(CLOSE_BLOCK + ENTER);
                        }
                    }
                    catch ( URISyntaxException usie )
                    {
                        log.error(usie);
                    }
                }
            }
            else if ( tpp.isDataTypeProperty() )
            {
                try
                {
                    URI uri = new URI(tpp.getRange().getURI());
                    String type = TYPE_VOID;
                    String prefix = "get";
                    String getMethod = "getProperty";
                    String setMethod = "setProperty";
                    if ( uri.getFragment().equals("string") )
                    {
                        type = "String";
                        getMethod = "getProperty";
                        setMethod = "setProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_INT) )
                    {
                        type = TYPE_INT;
                        getMethod = "getIntProperty";
                        setMethod = "setLongProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_FLOAT) )
                    {
                        type = TYPE_FLOAT;
                        getMethod = "getFloatProperty";
                        setMethod = "setFloatProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_DOUBLE) )
                    {
                        type = TYPE_DOUBLE;
                        getMethod = "getDoubleProperty";
                        setMethod = "setDoubleProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_LONG) )
                    {
                        type = TYPE_LONG;
                        getMethod = "getLongProperty";
                        setMethod = "setLongProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_BYTE) )
                    {
                        type = TYPE_BYTE;
                        getMethod = "getByteProperty";
                        setMethod = "setByteProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_SHORT) )
                    {
                        type = TYPE_SHORT;
                        getMethod = "getShortProperty";
                        setMethod = "setShortProperty";
                    }
                    else if ( uri.getFragment().equals(TYPE_BOOLEAN) )
                    {
                        type = TYPE_BOOLEAN;
                        prefix = "is";
                        getMethod = "getBooleanProperty";
                        setMethod = "setBooleanProperty";
                    }
                    else if ( uri.getFragment().equals("dateTime") )
                    {
                        type = "Date";
                        getMethod = "getDateProperty";
                        setMethod = "setDateProperty";
                    }
                    else
                    {
                        type = TYPE_VOID;
                    }
                    
                    getMethod="getSemanticObject()."+getMethod;
                    setMethod="getSemanticObject()."+setMethod;
                    
                    String label=tpp.getLabel();
                    if(label==null)label=tpp.getName();
                    String methodName = toUpperCase(label);

                    javaClassContent.append(ENTER);
                    javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        return " + getMethod + "(vocabulary." + tpp.getName() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        " + setMethod + "(vocabulary." + tpp.getName() + ", " + tpp.getName() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    if(tpp.isLocaleable())
                    {
                        javaClassContent.append(ENTER);
                        javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "(String lang)" + ENTER);
                        javaClassContent.append(OPEN_BLOCK + ENTER);
                        javaClassContent.append("        return " + getMethod + "(vocabulary." + tpp.getName() + ", lang);" + ENTER);
                        javaClassContent.append(CLOSE_BLOCK + ENTER);

                        javaClassContent.append(ENTER);
                        javaClassContent.append(PUBLIC + "void set" + methodName + "(" + type + " " + tpp.getName() + ", String lang)" + ENTER);
                        javaClassContent.append(OPEN_BLOCK + ENTER);
                        javaClassContent.append("        " + setMethod + "(vocabulary." + tpp.getName() + ", " + tpp.getName() + ", lang);" + ENTER);
                        javaClassContent.append(CLOSE_BLOCK + ENTER);
                    }
                }
                catch ( URISyntaxException usie )
                {
                    log.error(usie);
                }
            }
        }
    }

    private void createVocabulary() throws CodeGeneratorException
    {
        StringBuilder javaClassContent = new StringBuilder();
        javaClassContent.append("package " + m_Package + ";\r\n" + ENTER);
        javaClassContent.append("import org.semanticwb.SWBPlatform;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticVocabulary;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticClass;" + ENTER);
        javaClassContent.append("import org.semanticwb.platform.SemanticProperty;" + ENTER);
        javaClassContent.append("import static org.semanticwb.platform.SemanticVocabulary.URI;\r\n" + ENTER);
        javaClassContent.append("public class SWBVocabulary" + ENTER);
        javaClassContent.append("{" + ENTER);
        javaClassContent.append("\r\n\r\n    //Classes" + ENTER);
        SemanticMgr mgr = SWBPlatform.getSemanticMgr();
        Iterator<SemanticClass> tpcit = mgr.getVocabulary().listSemanticClasses();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            //System.out.println("tpc:" + tpc);
            javaClassContent.append("    public final SemanticClass " + tpc.getName() + ";" + ENTER);
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
                if ( !properties.contains(tpp.getName()) )
                {
                    properties.add(tpp.getName());
                    javaClassContent.append("    public final SemanticProperty " + tpp.getName() + ";" + ENTER);
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
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            javaClassContent.append("        " + tpc.getName() + "=vocabulary.getSemanticClass(URI+\"" + tpc.getName() + "\");" + ENTER);
        }

        javaClassContent.append("\r\n\r\n\r\n        //Properties" + ENTER);
        tpcit = mgr.getVocabulary().listSemanticClasses();
        properties = new HashSet<String>();
        while (tpcit.hasNext())
        {
            SemanticClass tpc = tpcit.next();
            Iterator<SemanticProperty> tppit = tpc.listProperties();
            while (tppit.hasNext())
            {
                SemanticProperty tpp = tppit.next();
                if ( !properties.contains(tpp.getName()) )
                {
                    properties.add(tpp.getName());
                    javaClassContent.append("        " + tpp.getName() + "=vocabulary.getSemanticProperty(URI+\"" + tpp.getName() + "\");" + ENTER);
                }
            }
        }
        javaClassContent.append(CLOSE_BLOCK + ENTER);
        javaClassContent.append("}" + ENTER);
        File dir = createPackage();
        File fileClass = new File(dir.getPath() + File.separatorChar + "SWBVocabulary.java");
        saveFile(fileClass, javaClassContent.toString());
    }
}
