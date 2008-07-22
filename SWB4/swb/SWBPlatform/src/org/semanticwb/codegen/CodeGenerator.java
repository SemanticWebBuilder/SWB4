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
import org.semanticwb.SWBContext;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Topic;
import org.semanticwb.model.TopicClass;
import org.semanticwb.model.TopicProperty;
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

    private static Logger log = SWBUtils.getLogger(Topic.class);
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

    private String getPackage(TopicClass tpc)
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
        File createPackage=this.m_Directory;
        if ( !this.m_Package.equals("") )
        {
            File dir = new File(this.m_Directory.getPath() + File.separatorChar + this.m_Package.replace('.', File.separatorChar));
            if ( !dir.exists() && !dir.mkdirs() )
            {
                throw new CodeGeneratorException("The directory " + dir.getPath() + " was not possible to create");
            }
            createPackage=dir;
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
        SemanticMgr mgr = SWBContext.getSemanticMgr();
        Iterator<TopicClass> tpcit = mgr.getVocabulary().listTopicClasses();
        while (tpcit.hasNext())
        {
            TopicClass tpc = tpcit.next(); 
            if(tpc.isSWBInterface())
            {
                createInterface(tpc);
                //System.out.println("tpc: "+tpc.toString()+" isSWBInterface: "+tpc.isSWBInterface());
            }
            else if(tpc.isSWBClass())
            {
                //System.out.println("tpc: "+tpc.toString()+" isSWBClass: "+tpc.isSWBClass());
                createClass(tpc);
            }
            
        }
        createVocabulary();
    }
    private static String getInterfaces(TopicClass tpc)
    {
        StringBuilder interfaces=new StringBuilder();        
        Iterator<TopicClass> it=tpc.listSuperClasses();
        while(it.hasNext())
        {
            TopicClass clazz=it.next();
            if(clazz.isSWBInterface())
            {
                interfaces.append(clazz.getName()+",");
            }
        }
        if(interfaces.length()>0)
        {            
            interfaces.insert(0, "implements ");            
            interfaces.deleteCharAt(interfaces.length()-1);
        }
        return interfaces.toString();
    }
    private void createClass(TopicClass tpc) throws CodeGeneratorException
    {
        if ( m_Package == null )
        {
            m_Package = getPackage(tpc);
        }
        File dir = createPackage();
        StringBuilder javaClassContent = new StringBuilder();
        if ( !m_Package.equals("") )
        {
            javaClassContent.append("package " + m_Package + ";"+ENTER);
            javaClassContent.append(""+ENTER);
        }
        javaClassContent.append("import java.util.Date;"+ENTER);
        if ( !this.m_Package.equals("org.semanticwb.model") )
        {
            javaClassContent.append("import org.semanticwb.model.Topic;"+ENTER);
        }
        javaClassContent.append("import com.hp.hpl.jena.rdf.model.StmtIterator;"+ENTER);
        javaClassContent.append("import org.semanticwb.model.GenericIterator;"+ENTER);        
        javaClassContent.append(ENTER);
        javaClassContent.append("public class " + tpc.getName() + " extends Topic "+  getInterfaces(tpc) +""+ENTER);
        javaClassContent.append("{"+ENTER);
        javaClassContent.append(PUBLIC + tpc.getName() + "(com.hp.hpl.jena.rdf.model.Resource res)"+ENTER);
        javaClassContent.append(OPEN_BLOCK+ENTER);
        javaClassContent.append("        super(res);"+ENTER);
        javaClassContent.append(CLOSE_BLOCK+ENTER);
        insertPropertiesToClass(tpc, javaClassContent);
        javaClassContent.append("}"+ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getName() + ".java");
        saveFile(fileClass, javaClassContent.toString());

    }
    private void createInterface(TopicClass tpc) throws CodeGeneratorException
    {
        if ( m_Package == null )
        {
            m_Package = getPackage(tpc);
        }
        File dir = createPackage();
        StringBuilder javaClassContent = new StringBuilder();
        if ( !m_Package.equals("") )
        {
            javaClassContent.append("package " + m_Package + ";"+ENTER);
            javaClassContent.append(""+ENTER);
        }
        javaClassContent.append("import org.semanticwb.model.GenericIterator;"+ENTER);
        javaClassContent.append("import java.util.Date;"+ENTER);
        javaClassContent.append("public interface " + tpc.getName() + " "+ENTER);
        javaClassContent.append("{"+ENTER);
        insertPropertiesToInterface(tpc, javaClassContent);
        javaClassContent.append("}"+ENTER);
        File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getName() + ".java");
        saveFile(fileClass, javaClassContent.toString());
    }

    
    private void insertPropertiesToInterface(TopicClass tpc, StringBuilder javaClassContent)
    {
        Iterator<TopicProperty> tppit = tpc.listProperties();
        HashSet<String> methods = new HashSet<String>();
        while (tppit.hasNext())
        {
            TopicProperty tpp = tppit.next();
            if ( tpp.isObjectProperty() )
            {
                if ( tpp.getRangeClass() != null && tpp.getRangeClass().getURI() != null )
                {
                    try
                    {
                        URI uri = new URI(tpp.getRangeClass().getURI());
                        String objectName = uri.getFragment();
                        objectName = toUpperCase(objectName);
                        if ( !methods.contains(objectName) )
                        {
                            methods.add(objectName);
                            javaClassContent.append("    public GenericIterator<" + m_Package+"."+objectName + "> list" + objectName + END_OF_METHOD+ENTER);                         
                            javaClassContent.append("    public void add" + objectName + "(" + m_Package + "." + objectName + " " + objectName.toLowerCase() + ");"+ENTER);                            
                            javaClassContent.append("    public void removeAll" + objectName + END_OF_METHOD+ENTER);                           
                            javaClassContent.append(PUBLIC + objectName + " get" + objectName + END_OF_METHOD+ENTER);                            
                            
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
                    URI uri = new URI(tpp.getRangeDataType().getURI());
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
                    String methodName = toUpperCase(tpp.getName());

                    javaClassContent.append(PUBLIC + type + " " + prefix + methodName + END_OF_METHOD+ENTER);
                    /*javaClassContent.append("    {"+ENTER);
                    javaClassContent.append("        return " + getMethod + "(Vocabulary." + tpp.getName() + ");"+ENTER);
                    javaClassContent.append("    }"+ENTER);*/

                    javaClassContent.append(PUBLIC + tpc.getName() + " set" + methodName + "(" + type + " " + tpp.getName() + ");"+ENTER);
                    /*javaClassContent.append("    {"+ENTER);
                    javaClassContent.append("        " + setMethod + "(Vocabulary." + tpp.getName() + ", " + tpp.getName() + ");"+ENTER);
                    javaClassContent.append("        return this;"+ENTER);
                    javaClassContent.append("    }"+ENTER);*/
                
                }
                catch ( URISyntaxException usie )
                {
                    log.error(usie);
                }
            }
        }
    }
    private void insertPropertiesToClass(TopicClass tpc, StringBuilder javaClassContent)
    {
        Iterator<TopicProperty> tppit = tpc.listProperties();
        HashSet<String> methods = new HashSet<String>();
        while (tppit.hasNext())
        {
            TopicProperty tpp = tppit.next();
            if ( tpp.isObjectProperty() )
            {
                if ( tpp.getRangeClass() != null && tpp.getRangeClass().getURI() != null )
                {
                    try
                    {
                        URI uri = new URI(tpp.getRangeClass().getURI());
                        String objectName = uri.getFragment();
                        objectName = toUpperCase(objectName);
                        if ( !methods.contains(objectName) )
                        {
                            methods.add(objectName);
                            javaClassContent.append("    public GenericIterator<" + m_Package + "."+objectName + "> list" + objectName + "()"+ENTER);
                            javaClassContent.append(OPEN_BLOCK+ENTER);
                            javaClassContent.append("        StmtIterator stit=getRDFResource().listProperties(Vocabulary." + tpp.getName() + ".getRDFProperty());"+ENTER);
                            javaClassContent.append("        return new GenericIterator<" + m_Package + "." + objectName + ">(" + m_Package + "." + objectName + ".class, stit);"+ENTER);
                            javaClassContent.append(CLOSE_BLOCK+ENTER);


                            javaClassContent.append("    public void add" + objectName + "(" + m_Package + "." + objectName + " " + objectName.toLowerCase() + ")"+ENTER);
                            javaClassContent.append(OPEN_BLOCK+ENTER);
                            javaClassContent.append("        addObjectProperty(Vocabulary." + tpp.getName() + ", " + objectName.toLowerCase() + ");"+ENTER);
                            javaClassContent.append(CLOSE_BLOCK+ENTER);

                            javaClassContent.append("    public void removeAll" + objectName + "()"+ENTER);
                            javaClassContent.append(OPEN_BLOCK+ENTER);
                            javaClassContent.append("        getRDFResource().removeAll(Vocabulary." + tpp.getName() + ".getRDFProperty());"+ENTER);
                            javaClassContent.append(CLOSE_BLOCK+ENTER);

                            javaClassContent.append(PUBLIC + objectName + " get" + objectName + "()"+ENTER);
                            javaClassContent.append(OPEN_BLOCK+ENTER);
                            javaClassContent.append("         StmtIterator stit=getRDFResource().listProperties(Vocabulary." + tpp.getName() + ".getRDFProperty());"+ENTER);
                            javaClassContent.append("         GenericIterator<" + m_Package + "." + objectName + "> it=new GenericIterator<" + m_Package + "." + objectName + ">(" + objectName + ".class, stit);"+ENTER);
                            javaClassContent.append("         return it.next();"+ENTER);
                            javaClassContent.append(CLOSE_BLOCK+ENTER);
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
                    URI uri = new URI(tpp.getRangeDataType().getURI());
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
                    String methodName = toUpperCase(tpp.getName());

                    javaClassContent.append(PUBLIC + type + " " + prefix + methodName + "()"+ENTER);
                    javaClassContent.append(OPEN_BLOCK+ENTER);
                    javaClassContent.append("        return " + getMethod + "(Vocabulary." + tpp.getName() + ");"+ENTER);
                    javaClassContent.append(CLOSE_BLOCK+ENTER);

                    javaClassContent.append(PUBLIC + tpc.getName() + " set" + methodName + "(" + type + " " + tpp.getName() + ")"+ENTER);
                    javaClassContent.append(OPEN_BLOCK+ENTER);
                    javaClassContent.append("        " + setMethod + "(Vocabulary." + tpp.getName() + ", " + tpp.getName() + ");"+ENTER);
                    javaClassContent.append("        return this;"+ENTER);
                    javaClassContent.append(CLOSE_BLOCK+ENTER);
                
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
        javaClassContent.append("package " + m_Package + ";\r\n"+ENTER);
        javaClassContent.append("import org.semanticwb.SWBContext;"+ENTER);
        javaClassContent.append("import org.semanticwb.platform.SWBVocabulary;"+ENTER);
        javaClassContent.append("import org.semanticwb.model.TopicClass;"+ENTER);
        javaClassContent.append("import org.semanticwb.model.TopicProperty;"+ENTER);
        javaClassContent.append("import static org.semanticwb.platform.SWBVocabulary.URI;\r\n"+ENTER);
        javaClassContent.append("public class Vocabulary"+ENTER);
        javaClassContent.append("{"+ENTER);
        javaClassContent.append("\r\n\r\n    //Classes"+ENTER);
        SemanticMgr mgr = SWBContext.getSemanticMgr();
        Iterator<TopicClass> tpcit = mgr.getVocabulary().listTopicClasses();
        while (tpcit.hasNext())
        {
            TopicClass tpc = tpcit.next();
            javaClassContent.append("    public static final TopicClass " + tpc.getName() + ";"+ENTER);
        }


        javaClassContent.append("\r\n\r\n\r\n    //Properties"+ENTER);
        HashSet<String> properties = new HashSet<String>();
        tpcit = mgr.getVocabulary().listTopicClasses();
        while (tpcit.hasNext())
        {
            TopicClass tpc = tpcit.next();
            Iterator<TopicProperty> tppit = tpc.listProperties();
            while (tppit.hasNext())
            {
                TopicProperty tpp = tppit.next();
                if ( !properties.contains(tpp.getName()) )
                {
                    properties.add(tpp.getName());
                    javaClassContent.append("    public static final TopicProperty " + tpp.getName() + ";"+ENTER);
                }
            }
        }
        javaClassContent.append("\r\n"+ENTER);
        //javaClassContent.append("    @Override"+ENTER);
        javaClassContent.append("    static"+ENTER);
        javaClassContent.append("    {\r\n"+ENTER);
        javaClassContent.append("         SWBVocabulary vocabulary=SWBContext.getSemanticMgr().getVocabulary();"+ENTER);
        javaClassContent.append("        // Classes"+ENTER);
        tpcit = mgr.getVocabulary().listTopicClasses();
        while (tpcit.hasNext())
        {
            TopicClass tpc = tpcit.next();
            javaClassContent.append("        " + tpc.getName() + "=vocabulary.getTopicClass(URI+\"" + tpc.getName() + "\");"+ENTER);
        }

        javaClassContent.append("\r\n\r\n\r\n        //Properties"+ENTER);
        tpcit = mgr.getVocabulary().listTopicClasses();
        properties = new HashSet<String>();
        while (tpcit.hasNext())
        {
            TopicClass tpc = tpcit.next();
            Iterator<TopicProperty> tppit = tpc.listProperties();
            while (tppit.hasNext())
            {
                TopicProperty tpp = tppit.next();
                if ( !properties.contains(tpp.getName()) )
                {
                    properties.add(tpp.getName());
                    javaClassContent.append("        " + tpp.getName() + "=vocabulary.getTopicProperty(URI+\"" + tpp.getName() + "\");"+ENTER);
                }
            }
        }
        javaClassContent.append(CLOSE_BLOCK+ENTER);        
        javaClassContent.append("}"+ENTER);
        File dir = createPackage();
        File fileClass = new File(dir.getPath() + File.separatorChar + "Vocabulary.java");
        saveFile(fileClass, javaClassContent.toString());
    }
    
}
