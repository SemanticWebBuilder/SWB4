package org.semanticwb.process.model;

import bsh.Interpreter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.Logger;
import org.semanticwb.SWBUtils;
import org.semanticwb.codegen.CodeGeneratorException;
import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.script.util.MemoryClassLoader;
import static org.semanticwb.SWBUtils.TEXT.*;

public class ScriptTask extends org.semanticwb.process.model.base.ScriptTaskBase 
{
    private static final String SEMANTIC_CLASS_FULL_NAME="org.semanticwb.platform.SemanticClass";
     /** The Constant CLOSE_BLOCK. */
    private static final String CLOSE_BLOCK = "    }";
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
    /** The Constant OPEN_BLOCK. */
    private static final String OPEN_BLOCK = "    {";
    private static final String GLOBAL_CLASS_NAME = "ClassMgr";
    public static Logger log=SWBUtils.getLogger(ProcessRule.class);
    private static final String ENTER = "\r\n";
    public ScriptTask(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    private String createSemanticClass(SemanticClass clazz,String name) throws Exception
    {
        return createClassBase(clazz,name);
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

            javaClassContent.append("    public java.util.Iterator<" + tpc.getCanonicalName() + "> list" + tpc.getNameInPlural() + "()" + ENTER);
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
    private String createClassBase(SemanticClass tpc,String className) throws CodeGeneratorException
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
        
        StringBuilder javaClassContent = new StringBuilder();
        if (tpc.getCodePackage()!=null && !tpc.getCodePackage().equals(""))
        {
            javaClassContent.append("package " + tpc.getCodePackage() + ".base;" + ENTER);
            javaClassContent.append("" + ENTER);
        }
        javaClassContent.append(ENTER);
        //javaClassContent.append("public abstract class " + tpc.getUpperClassName() + "Base extends " + exts + " " + getInterfacesAsString(tpc, false) + "" + ENTER);
        javaClassContent.append("public class " + className+ENTER);//+"extends " + exts + " " + getInterfacesAsString(tpc, false) + "" + ENTER);
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
                        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + range.getPrefix() + "_" + range.getUpperClassName() + "=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(\"" + range.getURI() + "\");" + ENTER);
                        staticClasses.add(range);
                    }
                }

                if (!staticProperties.contains(tpp))
                {
                    javaClassContent.append("    public static final org.semanticwb.platform.SemanticProperty " + tpp.getPrefix() + "_" + tpp.getName() + "=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(\"" + tpp.getURI() + "\");" + ENTER);
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
                        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + clazzOfModel.getPrefix() + "_" + clazzOfModel.getUpperClassName() + "=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(\"" + clazzOfModel.getURI() + "\");" + ENTER);
                        staticClasses.add(clazzOfModel);
                    }
                }
            }
        }
        if (!staticClasses.contains(tpc))
        {
            javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" " + tpc.getPrefix() + "_" + tpc.getUpperClassName() + "=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(\"" + tpc.getURI() + "\");" + ENTER);
            staticClasses.add(tpc);
        }

        javaClassContent.append("    public static final "+SEMANTIC_CLASS_FULL_NAME+" sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(\"" + tpc.getURI() + "\");" + ENTER);

        javaClassContent.append(ENTER);
        javaClassContent.append("    public static class " + GLOBAL_CLASS_NAME + ENTER);
        javaClassContent.append("    {" + ENTER);


        String fullpathClass = tpc.getCanonicalName();



        javaClassContent.append(ENTER);
        javaClassContent.append("        public static java.util.Iterator<" + fullpathClass + "> list" + tpc.getNameInPlural() + "(org.semanticwb.model.SWBModel model)" + ENTER);
        javaClassContent.append("        {" + ENTER);
        javaClassContent.append("            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);" + ENTER);
        javaClassContent.append("            return new org.semanticwb.model.GenericIterator<" + fullpathClass + ">(it, true);" + ENTER);
        javaClassContent.append("        }" + ENTER);

        javaClassContent.append(ENTER);
        javaClassContent.append("        public static java.util.Iterator<" + fullpathClass + "> list" + tpc.getNameInPlural() + "()" + ENTER);
        javaClassContent.append("        {" + ENTER);
        javaClassContent.append("            java.util.Iterator it=sclass.listInstances();" + ENTER);
        javaClassContent.append("            return new org.semanticwb.model.GenericIterator<" + fullpathClass + ">(it, true);" + ENTER);
        javaClassContent.append("        }" + ENTER);



        if (tpc.isAutogenId())
        {
            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " create" + tpc.getUpperClassName() + "(org.semanticwb.model.SWBModel model)" + ENTER);
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
            javaClassContent.append("            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();" + ENTER);
            javaClassContent.append("            " + fullpathClass + " ret=null;" + ENTER);
            javaClassContent.append("            org.semanticwb.platform.SemanticModel model=mgr.getModel(id);" + ENTER);
            javaClassContent.append("            if(model!=null)" + ENTER);
            javaClassContent.append("            {" + ENTER);
            javaClassContent.append("                org.semanticwb.platform.SemanticObject obj=model.getSemanticObject(model.getObjectUri(id,sclass));" + ENTER);
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
            javaClassContent.append("            org.semanticwb.platform.SemanticMgr mgr=org.semanticwb.SWBPlatform.getSemanticMgr();" + ENTER);
            javaClassContent.append("            org.semanticwb.platform.SemanticModel model=mgr.createModel(id, namespace);" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")model.createGenericObject(model.getObjectUri(id, sclass), sclass);" + ENTER);
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
            javaClassContent.append("        public static " + fullpathClass + " get" + tpc.getUpperClassName() + "(String id, org.semanticwb.model.SWBModel model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static " + fullpathClass + " create" + tpc.getUpperClassName() + "(String id, org.semanticwb.model.SWBModel model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            return (" + fullpathClass + ")model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static void remove" + tpc.getUpperClassName() + "(String id, org.semanticwb.model.SWBModel model)" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));" + ENTER);
            javaClassContent.append("        }" + ENTER);

            javaClassContent.append(ENTER);
            javaClassContent.append("        public static boolean has" + tpc.getUpperClassName() + "(String id, org.semanticwb.model.SWBModel model)" + ENTER);
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
                    javaClassContent.append("        public static java.util.Iterator<" + tpc.getCanonicalName() + "> list" +tpc.getUpperClassName() + "By" + toUpperCase(nameList) + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ",org.semanticwb.model.SWBModel model)" + ENTER);
                    javaClassContent.append("        {" + ENTER);
                    javaClassContent.append("            org.semanticwb.model.GenericIterator<" + tpc.getCanonicalName() + "> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ".getSemanticObject(),sclass));" + ENTER);
                    javaClassContent.append("            return it;" + ENTER);
                    javaClassContent.append("        }" + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("        public static java.util.Iterator<" + tpc.getCanonicalName()+ "> list" + tpc.getUpperClassName() + "By" + toUpperCase(nameList) + "(" + tpcToReturn.getCanonicalName() + " " + "value" + ")" + ENTER);
                    javaClassContent.append("        {" + ENTER);
                    javaClassContent.append("            org.semanticwb.model.GenericIterator<" + tpc.getCanonicalName() + "> it=new org.semanticwb.model.GenericIterator(" + "value" + ".getSemanticObject().getModel().listSubjectsByClass(" + tpp.getPrefix() + "_" + tpp.getName() + "," + "value" + ".getSemanticObject(),sclass));" + ENTER);
                    javaClassContent.append("            return it;" + ENTER);
                    javaClassContent.append("        }" + ENTER);
                }
                //}
            }
        }

        javaClassContent.append("    }" + ENTER); // ennd ClassMgr

        javaClassContent.append(ENTER);
        javaClassContent.append(PUBLIC + tpc.getUpperClassName() + "Base(org.semanticwb.platform.SemanticObject base)" + ENTER);
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
                javaClassContent.append("    public java.util.Iterator<org.semanticwb.model.GenericObject> listRelatedObjects()" + ENTER);
                javaClassContent.append("    {" + ENTER);

                javaClassContent.append("        return new org.semanticwb.model.GenericIterator(getSemanticObject().listRelatedObjects(),true);" + ENTER);
                javaClassContent.append("    }" + ENTER);
            }
            insertLinkToClass4Model(tpc, javaClassContent, parent);
        }
        javaClassContent.append("}" + ENTER);
        return javaClassContent.toString();
        /*File fileClass = new File(dir.getPath() + File.separatorChar + tpc.getUpperClassName() + "Base.java");
        try
        {
            System.out.println("Creando clase "+fileClass.getCanonicalPath());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        saveFile(fileClass, javaClassContent.toString());
        createClass(tpc, parent, pDirectory);*/
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

            javaClassContent.append("    public java.util.Iterator<" + type + "> list" + getPlural(objectName) + "()" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);


            javaClassContent.append("        java.util.ArrayList<" + type + "> values=new java.util.ArrayList<" + type + ">();" + ENTER);
            javaClassContent.append("        java.util.Iterator<org.semanticwb.platform.SemanticLiteral> it=getSemanticObject().listLiteralProperties(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            javaClassContent.append("        while(it.hasNext())" + ENTER);
            javaClassContent.append("        {" + ENTER);
            javaClassContent.append("                org.semanticwb.platform.SemanticLiteral literal=it.next();" + ENTER);
            javaClassContent.append("                values.add(literal.getString());" + ENTER);
            javaClassContent.append("        }" + ENTER);
            javaClassContent.append("        return values.iterator();" + ENTER);

            javaClassContent.append(CLOSE_BLOCK + ENTER);

            if (!tpp.hasInverse())
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void add" + objectName + "(" + type + " " + objectName.toLowerCase() + ")" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().set" + (type.equals("String") ? "" : type) + "Property(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + objectName.toLowerCase() + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                javaClassContent.append(CLOSE_BLOCK + ENTER);
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void remove" + objectName + "(" + type + " " + objectName.toLowerCase() + ")" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + "," + objectName.toLowerCase() + ");" + ENTER);
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
                javaClassContent.append("    public org.semanticwb.model.GenericIterator<" + tpcToReturn.getCanonicalName() + "> list" + getPlural(objectName) + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
                javaClassContent.append("        return new org.semanticwb.model.GenericIterator<" + tpcToReturn.getCanonicalName() + ">(getSemanticObject().listObjectProperties(" + tpp.getPrefix() + "_" + tpp.getName() + "));" + ENTER);
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
                    javaClassContent.append("    public org.semanticwb.model.GenericIterator<" + tpcToReturn.getCanonicalName()+ "> listInherit" + getPlural(objectName) + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        return new org.semanticwb.model.GenericIterator<" + tpcToReturn.getCanonicalName() + ">(getSemanticObject().listInheritProperties(" + tpp.getPrefix() + "_" + tpp.getName() + "));" + ENTER);
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
                javaClassContent.append("        get" + semanticObject + "().setObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ".getSemanticObject());" + ENTER);
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
            javaClassContent.append("         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
            javaClassContent.append("         if(obj!=null)" + ENTER);
            javaClassContent.append("         {" + ENTER);
            javaClassContent.append("             ret=(" + tpcToReturn.getCanonicalName() + ")obj.createGenericInstance();" + ENTER);
            javaClassContent.append("         }" + ENTER);
            javaClassContent.append("         return ret;" + ENTER);
            javaClassContent.append(CLOSE_BLOCK + ENTER);
        }
        else if (tpp.getRange() != null)
        {
            String pack = "org.semanticwb.platform";
            String valueToReturn = "SemanticObject";
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
                javaClassContent.append("    public org.semanticwb.platform.SemanticIterator<" + pack + "." + valueToReturn + "> list" + getPlural(objectName) + "()" + ENTER);
                javaClassContent.append(OPEN_BLOCK + ENTER);
//                
                javaClassContent.append("        com.hp.hpl.jena.rdf.model.StmtIterator stit=getSemanticObject().getRDFResource().listProperties(" + tpp.getPrefix() + "_" + tpp.getName() + ".getRDFProperty());" + ENTER);
                javaClassContent.append("        return new org.semanticwb.platform.SemanticIterator<" + pack + "." + valueToReturn + ">(stit);" + ENTER);
//               
                javaClassContent.append(CLOSE_BLOCK + ENTER);

                if (!tpp.hasInverse())
                {
                    
                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void add" + objectName + "(" + pack + "." + valueToReturn + " " + "value" + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().addObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ", " + "value" + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void removeAll" + objectName + "()" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeProperty(" + tpp.getPrefix() + "_" + tpp.getName() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);

                    javaClassContent.append(ENTER);
                    javaClassContent.append("    public void remove" + objectName + "(" + pack + "." + toUpperCase(valueToReturn) + " " + valueToReturn.toLowerCase() + ")" + ENTER);
                    javaClassContent.append(OPEN_BLOCK + ENTER);
                    javaClassContent.append("        get" + semanticObject + "().removeObjectProperty(" + tpp.getPrefix() + "_" + tpp.getName() + "," + valueToReturn.toLowerCase() + ");" + ENTER);
                    javaClassContent.append(CLOSE_BLOCK + ENTER);
                }
            }
            else
            {
                javaClassContent.append(ENTER);
                javaClassContent.append("    public void set" + objectName + "(" + pack + "." + toUpperCase(valueToReturn) + " " + "value" + ")" + ENTER);
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
            javaClassContent.append(PUBLIC + pack + "." + valueToReturn + " get" + objectName + "()" + ENTER);
            javaClassContent.append(OPEN_BLOCK + ENTER);
            javaClassContent.append("         " + pack + "." + valueToReturn + " ret=null;" + ENTER);
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

    

    private String getClassName(SemanticClass clazz)
    {
        /*String uuid=UUID.randomUUID().toString();
        uuid=uuid.replace('-', '_');
        return "_"+uuid+clazz.getName();*/
        return clazz.getUpperClassName();
    }
    private void addSemanticClass(String clasName,SemanticClass clazz,MemoryClassLoader mls) throws Exception
    {        
        String createdClass=createSemanticClass(clazz,clasName);        
        mls.add(clasName, createdClass);
    }
    private void addSemanticObject(Interpreter i,SemanticObject object) throws Exception
    {        
        SemanticClass clazz=object.getSemanticClass();
        String clasName=getClassName(clazz);
        String varname=object.getSemanticClass().getName().toLowerCase();
        i.set(clasName+" "+varname+"=", object);
    }
    private void addSemanticClasses(SemanticClass clazz,MemoryClassLoader mcls) throws Exception
    {        
        String clasName=getClassName(clazz);     
        addSemanticClass(clasName,clazz,mcls);
    }
    private MemoryClassLoader loadClasses(FlowNodeInstance instance) throws Exception
    {
        MemoryClassLoader mcls=new MemoryClassLoader();
        List<ProcessObject> processObjects=instance.getProcessInstance().listHeraquicalProcessObjects();
        for(ProcessObject po : processObjects)
        {
            addSemanticClasses(po.getSemanticObject().getSemanticClass(),mcls);
        }
        return mcls;
    }
    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        super.execute(instance, user);
        String code=getScriptCode();

        try
        {
            long ini=System.currentTimeMillis();
            Interpreter i = new Interpreter();  // Construct an interpreter
            i.setClassLoader(loadClasses(instance));
            //i.set("this",this);
            i.set("instance", instance);
            //i.set("target", targetInstance);
            i.set("user", user);
            if(instance!=null)
            {
                i.set("accepted", Instance.ACTION_ACCEPT.equals(instance.getAction()));
                i.set("rejected", Instance.ACTION_REJECT.equals(instance.getAction()));
                i.set("canceled", Instance.ACTION_CANCEL.equals(instance.getAction()));
            }
            i.eval("import org.semanticwb.process.model.*");
            
            List<ProcessObject> processObjects=instance.getProcessInstance().listHeraquicalProcessObjects();
            for(ProcessObject po : processObjects)
            {
                addSemanticObject(i,po.getSemanticObject());
            }

            Object ret=i.eval(code);
            System.out.println("ret:"+ret);
            System.out.println("time:"+ (System.currentTimeMillis()-ini ));

        }catch(Exception e)
        {
            log.error(e);

            Iterator<GraphicalElement> it=listChilds();
            while (it.hasNext())
            {
                GraphicalElement graphicalElement = it.next();
                if(graphicalElement instanceof ErrorIntermediateCatchEvent)
                {
                    ErrorIntermediateCatchEvent event=(ErrorIntermediateCatchEvent)graphicalElement;
                    //TODO:Validar excepciones
                    //String c1=event.getActionCode();
                    //String c2=((Event)instance.getFlowNodeType()).getActionCode();
                    //if((c1!=null && c1.equals(c2)) || c1==null && c2==null)
                    {
                        FlowNodeInstance source=(FlowNodeInstance)instance;
                        source.close(user, Instance.STATUS_ABORTED, Instance.ACTION_EVENT, false);

                        FlowNodeInstance fn=((FlowNodeInstance)instance).getRelatedFlowNodeInstance(event);
                        fn.setSourceInstance(instance);
                        event.notifyEvent(fn, instance);
                        return;
                    }
                }
            }

        }
        instance.close(user);

    }


}
