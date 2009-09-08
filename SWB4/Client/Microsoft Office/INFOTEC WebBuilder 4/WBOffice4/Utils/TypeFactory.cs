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
*  http://www.webbuilder.org.mx 
**/ 
 
﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;
using System.Windows.Forms;
using System.Reflection;
using System.Reflection.Emit;
using System.ComponentModel;
using WBOffice4.Interfaces;
using System.Drawing.Design;
namespace WBOffice4.Utils
{
    public sealed class TypeFactory
    {
        public static Object getObject(WBOffice4.Interfaces.PropertyInfo[] properties,String title)
        {
            Dictionary<WBOffice4.Interfaces.PropertyInfo, Type> propertiesToCreate = new Dictionary<WBOffice4.Interfaces.PropertyInfo, Type>();
            foreach(WBOffice4.Interfaces.PropertyInfo prop in properties)
            {
                Type type = typeof(string);
                if(prop.type.Equals("integer",StringComparison.InvariantCultureIgnoreCase))
                {
                    type = typeof(int);
                }
                else if(prop.type.Equals("boolean",StringComparison.InvariantCultureIgnoreCase))
                {
                    type = typeof(bool);
                }
                else if (prop.type.Equals("datetime", StringComparison.InvariantCultureIgnoreCase))
                {
                    type = typeof(DateTime);
                }
                else if (prop.type.Equals("decimal", StringComparison.InvariantCultureIgnoreCase))
                {
                    type = typeof(float);
                }                
                propertiesToCreate.Add(prop,type);
            }
            Type typeToEdit = Create(propertiesToCreate, title);
            Object obj = Activator.CreateInstance(typeToEdit);
            return obj;
        }
        public static Object getObject(Dictionary<WBOffice4.Interfaces.PropertyInfo, Type> properties, String title)
        {
            Type type = Create(properties,title);
            Object obj = Activator.CreateInstance(type);
            return obj;
        }

        public static void setValue(WBOffice4.Interfaces.PropertyInfo prop, Object obj,String value)
        {
            Type type = obj.GetType();
            String name = prop.id;
            int pos = name.IndexOf("#");
            if (pos != -1)
            {
                name = name.Substring(pos + 1);
            }
            name = name.Replace(':', '_');

            System.Reflection.PropertyInfo info = type.GetProperty(name);
            object[] index = { };
            if(info.PropertyType.Name.Equals("Int32",StringComparison.InvariantCultureIgnoreCase))
            {
                try
                {
                    Int32 ivalue=Int32.Parse(value);
                    info.SetValue(obj, ivalue, index);
                }
                catch(Exception e)
                {
                    OfficeApplication.WriteError(e);
                }
            }
            else if(info.PropertyType.Name.Equals("Boolean",StringComparison.InvariantCultureIgnoreCase))
            {
                try
                {
                    bool bvalue=bool.Parse(value);
                    info.SetValue(obj, bvalue, index);
                }
                catch(Exception e)
                {
                    OfficeApplication.WriteError(e);
                }
            }
            else
            {
                info.SetValue(obj, value, index);
            }
        }
        public static String[] getValues(WBOffice4.Interfaces.PropertyInfo[] properties,Object obj)
        {
            String[] getValues = new String[properties.Length];
            Type type=obj.GetType();
            int i = 0;
            foreach(WBOffice4.Interfaces.PropertyInfo prop in properties)
            {

                String name = prop.id;
                int pos = name.IndexOf("#");
                if (pos != -1)
                {
                    name = name.Substring(pos + 1);
                }
                name = name.Replace(':', '_');
               
                System.Reflection.PropertyInfo info=type.GetProperty(name);
                object[] index={};
                Object value=info.GetValue(obj, index);
                if (value != null)
                {
                    getValues[i] = value.ToString();
                }
                else
                {
                    getValues[i] = "";
                }
                i++;
            }
            return getValues;
        }
        public static Type Create(Dictionary<WBOffice4.Interfaces.PropertyInfo, Type> properties,String title)
        {
            Guid guid = Guid.NewGuid();
            AssemblyName assName = new AssemblyName();
            string assemblyName = "TypeFactory" + guid.ToString();
            string moduleName = "TypeFactory" + guid.ToString() + ".dll";
            string typeName = "TypeFactory" + guid.ToString();
            assName.Name = assemblyName;
            assName.Version = new Version("1.0.0.0");
            AssemblyBuilder assBldr = AppDomain.CurrentDomain.DefineDynamicAssembly(
             assName, AssemblyBuilderAccess.RunAndSave);
            ModuleBuilder modBldr = assBldr.DefineDynamicModule(assName.Name, moduleName);
            TypeBuilder typeBldr = modBldr.DefineType(typeName, TypeAttributes.Class | TypeAttributes.Sealed | TypeAttributes.Public, typeof(Component), new Type[] { });




            ConstructorBuilder ctor0 = typeBldr.DefineConstructor(
            MethodAttributes.Public,
            CallingConventions.Standard,
            Type.EmptyTypes);

            ILGenerator ctor0IL = ctor0.GetILGenerator();
            ctor0IL.Emit(OpCodes.Ret);

            foreach (WBOffice4.Interfaces.PropertyInfo prop in properties.Keys)
            {
                String name = prop.id;                
                int pos = name.IndexOf("#");
                if (pos != -1)
                {
                    name = name.Substring(pos + 1);
                }
                name = name.Replace(':', '_');                
                FieldBuilder fbNumber = typeBldr.DefineField(name, properties[prop], FieldAttributes.Private);
                PropertyBuilder pbNumber = typeBldr.DefineProperty(name, PropertyAttributes.HasDefault, properties[prop], null);
                
                

                Type description = typeof(System.ComponentModel.DescriptionAttribute);
                Type[] ctorParams = new Type[] { typeof(string) };


                ConstructorInfo classCtorInfo = description.GetConstructor(ctorParams);
                CustomAttributeBuilder myCABuilder = new CustomAttributeBuilder(
                        classCtorInfo,
                        new object[] { prop.title });
                pbNumber.SetCustomAttribute(myCABuilder);


                Type categoryAttribute = typeof(System.ComponentModel.CategoryAttribute);

                classCtorInfo = categoryAttribute.GetConstructor(ctorParams);
                myCABuilder = new CustomAttributeBuilder(
                        classCtorInfo,
                        new object[] { title });
                pbNumber.SetCustomAttribute(myCABuilder);


                if (prop.values != null && prop.values.Length > 0)
                {
                    Type type = properties[prop];

                    Type editorAttribute = typeof(EditorAttribute);
                    classCtorInfo = editorAttribute.GetConstructor(ctorParams);                    
                    myCABuilder = new CustomAttributeBuilder(
                        classCtorInfo,
                        new object[] { typeof(MultivalueEditor), typeof(UITypeEditor) });
                    pbNumber.SetCustomAttribute(myCABuilder);


                    Type typeConverterAttribute = typeof(TypeConverterAttribute);
                    classCtorInfo = typeConverterAttribute.GetConstructor(ctorParams);
                    myCABuilder = new CustomAttributeBuilder(
                        classCtorInfo,
                        new object[] { typeof(MultivalueEditor)});
                    pbNumber.SetCustomAttribute(myCABuilder);
                }

                MethodAttributes getSetAttr = MethodAttributes.Public |
                MethodAttributes.SpecialName | MethodAttributes.HideBySig;


                MethodBuilder mbNumberGetAccessor = typeBldr.DefineMethod(
                "get_" + name,
                getSetAttr,
                properties[prop],
                Type.EmptyTypes);

                ILGenerator numberGetIL = mbNumberGetAccessor.GetILGenerator();
                // For an instance property, argument zero is the instance. Load the 
                // instance, then load the private field and return, leaving the
                // field value on the stack.
                numberGetIL.Emit(OpCodes.Ldarg_0);
                numberGetIL.Emit(OpCodes.Ldfld, fbNumber);
                numberGetIL.Emit(OpCodes.Ret);


                MethodBuilder mbNumberSetAccessor = typeBldr.DefineMethod(
                "set_" + name,
                getSetAttr,
                null,
                new Type[] { properties[prop] });

                ILGenerator numberSetIL = mbNumberSetAccessor.GetILGenerator();
                // Load the instance and then the numeric argument, then store the
                // argument in the field.
                numberSetIL.Emit(OpCodes.Ldarg_0);
                numberSetIL.Emit(OpCodes.Ldarg_1);
                numberSetIL.Emit(OpCodes.Stfld, fbNumber);
                numberSetIL.Emit(OpCodes.Ret);

                // Last, map the "get" and "set" accessor methods to the 
                // PropertyBuilder. The property is now complete. 
                pbNumber.SetGetMethod(mbNumberGetAccessor);
                pbNumber.SetSetMethod(mbNumberSetAccessor);
            }


            return typeBldr.CreateType();

        }
    }
}
