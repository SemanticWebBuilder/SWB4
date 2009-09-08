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
using System.Collections;
using System.Linq;
using System.Text;
using System.Reflection;
using System.Reflection.Emit;
namespace XmlRpcLibrary
{
    public sealed class XmlRpcProxyFactory
    {
        private XmlRpcProxyFactory()
        {
        }
        static Hashtable _types = new Hashtable();
        public static T Create<T>() where T : IXmlRpcProxy
        {
            return (T)Create(typeof(T));
        }
        public static object Create(Type itf)
        {
            Type proxyType;            
            lock (_types)
            {
                proxyType = (Type)_types[itf];
                if (proxyType == null)
                {
                    Guid guid = Guid.NewGuid();
                    string assemblyName = "XmlRpcProxyFactory" + guid.ToString();
                    string moduleName = "XmlRpcProxyFactory" + guid.ToString() + ".dll";
                    string typeName = "XmlRpcProxyFactory" + guid.ToString();
                    AssemblyBuilder assBldr = BuildAssembly(itf, assemblyName,
                      moduleName, typeName);
                    proxyType = assBldr.GetType(typeName);
                    _types.Add(itf, proxyType);
                }
            }
            try
            {
                object ret = Activator.CreateInstance(proxyType);
                return ret;
            }
            catch (Exception e)
            {
                XmlRpcTraceEventLogListener.WriteError(e);
            }
            return null;
        }
        static AssemblyBuilder BuildAssembly(Type itf,string assemblyName,string moduleName,string typeName)
        {
            //string urlString = GetXmlRpcUrl(itf);
            ArrayList methods = GetXmlRpcMethods(itf);                        
            AssemblyName assName = new AssemblyName();
            assName.Name = assemblyName;
            assName.Version = itf.Assembly.GetName().Version;
            AssemblyBuilder assBldr = AppDomain.CurrentDomain.DefineDynamicAssembly(
              assName, AssemblyBuilderAccess.RunAndSave);
            ModuleBuilder modBldr = assBldr.DefineDynamicModule(assName.Name,moduleName);
            TypeBuilder typeBldr = modBldr.DefineType(typeName,TypeAttributes.Class | TypeAttributes.Sealed | TypeAttributes.Public,typeof(XmlRpcClientProtocol),new Type[] { itf });
            BuildConstructor(typeBldr, typeof(XmlRpcClientProtocol));
            BuildMethods(typeBldr, methods);            
            typeBldr.CreateType();
            return assBldr;
        }
        static void BuildMethod(TypeBuilder tb,string methodName,string rpcMethodName,Type[] argTypes,bool paramsMethod,Type returnType)        
        {
            MethodBuilder mthdBldr = tb.DefineMethod(methodName,MethodAttributes.Public | MethodAttributes.Virtual,returnType, argTypes);
            // add attribute to method
            Type[] oneString = new Type[1] { typeof(string) };
            Type methodAttr = typeof(XmlRpcMethodAttribute);
            ConstructorInfo ci = methodAttr.GetConstructor(oneString);
            CustomAttributeBuilder cab =
              new CustomAttributeBuilder(ci, new object[] { rpcMethodName });
            mthdBldr.SetCustomAttribute(cab);
            // possibly add ParamArrayAttribute to final parameter
            if (paramsMethod)
            {
                ParameterBuilder paramBldr = mthdBldr.DefineParameter(argTypes.Length,
                  ParameterAttributes.In, "args");
                ConstructorInfo ctorInfo = typeof(ParamArrayAttribute).GetConstructor(
                  new Type[0]);
                CustomAttributeBuilder attrBldr =
                  new CustomAttributeBuilder(ctorInfo, new object[0]);
                paramBldr.SetCustomAttribute(attrBldr);
            }
            // generate IL
            ILGenerator ilgen = mthdBldr.GetILGenerator();
            // if non-void return, declared locals for processing return value
            LocalBuilder retVal = null;
            LocalBuilder tempRetVal = null;
            if (typeof(void) != returnType)
            {
                tempRetVal = ilgen.DeclareLocal(typeof(System.Object));
                retVal = ilgen.DeclareLocal(returnType);
            }
            // declare variable to store method args and emit code to populate ut
            LocalBuilder argValues = ilgen.DeclareLocal(typeof(System.Object[]));
            ilgen.Emit(OpCodes.Ldc_I4, argTypes.Length);
            ilgen.Emit(OpCodes.Newarr, typeof(System.Object));
            ilgen.Emit(OpCodes.Stloc, argValues);
            for (int argLoad = 0; argLoad < argTypes.Length; argLoad++)
            {
                ilgen.Emit(OpCodes.Ldloc, argValues);
                ilgen.Emit(OpCodes.Ldc_I4, argLoad);
                ilgen.Emit(OpCodes.Ldarg, argLoad + 1);
                if (argTypes[argLoad].IsValueType)
                {
                    ilgen.Emit(OpCodes.Box, argTypes[argLoad]);
                }
                ilgen.Emit(OpCodes.Stelem_Ref);
            }
            // call Invoke on base class
            Type[] invokeTypes = new Type[] { typeof(MethodInfo), typeof(object[]) };
            MethodInfo invokeMethod = typeof(XmlRpcClientProtocol).GetMethod("Invoke", invokeTypes);
            ilgen.Emit(OpCodes.Ldarg_0);
            ilgen.Emit(OpCodes.Call, typeof(MethodBase).GetMethod("GetCurrentMethod"));
            ilgen.Emit(OpCodes.Castclass, typeof(System.Reflection.MethodInfo));
            ilgen.Emit(OpCodes.Ldloc, argValues);
            ilgen.Emit(OpCodes.Call, invokeMethod);
            //  if non-void return prepare return value, otherwise pop to discard 
            if (typeof(void) != returnType)
            {
                // if return value is null, don't cast it to required type
                Label retIsNull = ilgen.DefineLabel();
                ilgen.Emit(OpCodes.Stloc, tempRetVal);
                ilgen.Emit(OpCodes.Ldloc, tempRetVal);
                ilgen.Emit(OpCodes.Brfalse, retIsNull);
                ilgen.Emit(OpCodes.Ldloc, tempRetVal);
                if (true == returnType.IsValueType)
                {
                    ilgen.Emit(OpCodes.Unbox, returnType);
                    ilgen.Emit(OpCodes.Ldobj, returnType);
                }
                else
                {
                    ilgen.Emit(OpCodes.Castclass, returnType);
                }
                ilgen.Emit(OpCodes.Stloc, retVal);
                ilgen.MarkLabel(retIsNull);
                ilgen.Emit(OpCodes.Ldloc, retVal);
            }
            else
            {
                ilgen.Emit(OpCodes.Pop);
            }
            ilgen.Emit(OpCodes.Ret);
        }
        static void BuildMethods(TypeBuilder tb, ArrayList methods)
        {
            foreach (MethodData mthdData in methods)
            {
                MethodInfo mi = mthdData.mi;
                Type[] argTypes = new Type[mi.GetParameters().Length];
                for (int i = 0; i < mi.GetParameters().Length; i++)
                {
                    argTypes[i] = mi.GetParameters()[i].ParameterType;
                }
                BuildMethod(tb, mi.Name, mthdData.xmlRpcName, argTypes,
                  mthdData.paramsMethod, mi.ReturnType);
            }
        }
        private static void BuildConstructor(TypeBuilder typeBldr,Type baseType)
        {
            ConstructorBuilder ctorBldr = typeBldr.DefineConstructor(MethodAttributes.Public | MethodAttributes.SpecialName | MethodAttributes.RTSpecialName | MethodAttributes.HideBySig,CallingConventions.Standard,Type.EmptyTypes);            
            ILGenerator ilgen = ctorBldr.GetILGenerator();
            //  Call the base constructor.
            ilgen.Emit(OpCodes.Ldarg_0);
            ConstructorInfo ctorInfo = baseType.GetConstructor(System.Type.EmptyTypes);
            ilgen.Emit(OpCodes.Call, ctorInfo);
            ilgen.Emit(OpCodes.Ret);
        }
        private static string GetXmlRpcMethodName(MethodInfo mi)
        {
            Attribute attr = Attribute.GetCustomAttribute(mi,
              typeof(XmlRpcMethodAttribute));
            if (attr == null)
                return null;
            XmlRpcMethodAttribute xrmAttr = attr as XmlRpcMethodAttribute;
            string rpcMethod = xrmAttr.Method;
            if (String.IsNullOrEmpty(rpcMethod))
            {
                rpcMethod = mi.Name;
            }
            return rpcMethod;
        }
        private static MethodInfo[] GetMethods(Type type)
        {
            MethodInfo[] methods = type.GetMethods();
            if (!type.IsInterface)
            {
                return methods;
            }

            Type[] interfaces = type.GetInterfaces();
            if (interfaces.Length == 0)
            {
                return methods;
            }

            ArrayList result = new ArrayList(methods);
            foreach (Type itf in type.GetInterfaces())
            {
                result.AddRange(itf.GetMethods());
            }
            return (MethodInfo[])result.ToArray(typeof(MethodInfo));
        }
        private static ArrayList GetXmlRpcMethods(Type itf)
        {
            ArrayList ret = new ArrayList();
            if (!itf.IsInterface)
                throw new XmlRpcException("type not interface");
            foreach (MethodInfo mi in GetMethods(itf))
            {
                string xmlRpcName = GetXmlRpcMethodName(mi);
                if (xmlRpcName == null)
                    continue;
                ParameterInfo[] pis = mi.GetParameters();
                bool paramsMethod = pis.Length > 0 && Attribute.IsDefined(
                  pis[pis.Length - 1], typeof(ParamArrayAttribute));
                ret.Add(new MethodData(mi, xmlRpcName, paramsMethod));
            }
            return ret;
        }
    }
    class MethodData
    {
        public MethodData(MethodInfo Mi, string XmlRpcName, bool ParamsMethod)
        {
            mi = Mi;
            xmlRpcName = XmlRpcName;
            paramsMethod = ParamsMethod;            
        }        
        public MethodInfo mi;
        public string xmlRpcName;

        public bool paramsMethod;
    }
}
