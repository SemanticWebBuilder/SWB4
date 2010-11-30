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
using System.Xml;
using System.Xml.XPath;
using System.IO;
using System.Net;
using System.Globalization;
using System.Reflection;
using System.Xml.Serialization;
using System.Diagnostics;


namespace XmlRpcLibrary
{
    internal sealed class XmlRpcClient
    {
        
        
        public static readonly String SUFIX_GATEWAY = "gtw";
        private static string iso8601dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
        private static string boundary = "gc0p4Jq0M2Yt08jU534c0p";
        private XmlRpcClientConfig config;
        
        public XmlRpcClient(XmlRpcClientConfig config)
        {
            this.config = config;
        }
        
        public T Execute<T>(String methodName, Object[] parameters, ICollection<Attachment> attachments, ref HashSet<Part> responseParts)
        {
            foreach (Attachment attachment in attachments)
            {
                if (!attachment.File.Exists)
                {
                    throw new XmlRpcException("The attachment '" + attachment.Name + "' does not exist");
                }
            }
            MethodRequest request = new MethodRequest(GetParameters(parameters));
            request.MethodName = methodName;
            XmlRpcResponse response = Request(request, attachments);
            T objectToReturn = Deserialize<T>(response.getXmlDocumentResponse());
            responseParts = response.GetResponseParts();
            //objectToReturn.ResponseParts = response.GetResponseParts();
            return objectToReturn;
        }
        private static ArrayList GetParametersValues(Object[] parameters)
        {
            List<Value> parametersToSend = new List<Value>();
            foreach (object obj in parameters)
            {
                Array objArray = obj as Array;
                if (obj is int)
                {

                    parametersToSend.Add(new IntValue((int)obj));
                }
                else if (objArray != null)
                {
                    ArrayValue arrayValue = new ArrayValue();
                    arrayValue.ArrayElement = new ArrayElement(GetParametersValues(objArray.OfType<object>().ToArray<object>()));
                    parametersToSend.Add(arrayValue);
                }
                else if (obj is double)
                {
                    parametersToSend.Add(new DoubleValue((double)obj));
                }
                else if (obj is DateTime)
                {

                    parametersToSend.Add(new DateTimeValue((DateTime)obj));
                }
                else if (obj is bool)
                {

                    parametersToSend.Add(new BooleanValue((bool)obj));
                }
                else if (obj is string)
                {

                    parametersToSend.Add(new StringValue(obj.ToString()));
                }
                else if (obj is float)
                {

                    parametersToSend.Add(new FloatValue((float)obj));
                }
                else
                {
                    Debug.Assert(obj != null, "Objeto nulo");
                    parametersToSend.Add(new StructValue(obj));
                }
            }
            //return parametersToSend.ToArray<Parameter>();            
            return new ArrayList(parametersToSend);
            //return parametersToSend;
        }
        public static Value GetParameter(Object obj)
        {
            Array objArray = obj as Array;
            Parameter parameter = new Parameter();
            if (obj is int)
            {

                return new IntValue((int)obj);
            }
            else if (objArray != null)
            {
                ArrayValue arrayValue = new ArrayValue();
                arrayValue.ArrayElement = new ArrayElement(GetParametersValues(objArray.OfType<object>().ToArray<object>()));
                return arrayValue;
            }
            else if (obj is double)
            {
                return new DoubleValue((double)obj);
            }
            else if (obj is DateTime)
            {

                return new DateTimeValue((DateTime)obj);
            }
            else if (obj is bool)
            {

                return new BooleanValue((bool)obj);
            }
            else if (obj is string)
            {

                return new StringValue(obj.ToString());
            }
            else if (obj is float)
            {

                return new FloatValue((float)obj);
            }
            else
            {
                Debug.Assert(obj != null, "A value is null");             
                return new StructValue(obj);
            }


        }
        private static ArrayList GetParameters(Object[] parameters)
        {
            //Parameters parametersToSend = new Parameters();            
            List<Parameter> parametersToSend = new List<Parameter>();
            foreach (object obj in parameters)
            {
                Array objArray = obj as Array;
                Parameter parameter = new Parameter();
                if (obj is int)
                {

                    parameter.Value = new IntValue((int)obj);
                }
                else if (objArray != null)
                {
                    ArrayValue arrayValue = new ArrayValue();
                    arrayValue.ArrayElement = new ArrayElement(GetParametersValues(objArray.OfType<object>().ToArray<object>()));
                    parameter.Value = arrayValue;
                }
                else if (obj is double)
                {
                    parameter.Value = new DoubleValue((double)obj);
                }
                else if (obj is DateTime)
                {

                    parameter.Value = new DateTimeValue((DateTime)obj);
                }
                else if (obj is bool)
                {

                    parameter.Value = new BooleanValue((bool)obj);
                }
                else if (obj is string)
                {
                    Debug.Assert(obj != null, "Objeto nulo");
                    parameter.Value = new StringValue(obj.ToString());
                }
                else if (obj is float)
                {

                    parameter.Value = new FloatValue((float)obj);
                }
                else
                {
                    Debug.Assert(obj != null, "Objeto nulo");
                    parameter.Value = new StructValue(obj);
                }
                parametersToSend.Add(parameter);
            }
            return new ArrayList(parametersToSend);
        }
        private T Deserialize<T>(XmlDocument requestDocument)
        {

            XmlElement param = (XmlElement)requestDocument.SelectSingleNode("/methodResponse/params/param");
            if (param == null)
            {

                XmlElement eName = (XmlElement)requestDocument.SelectSingleNode("/methodResponse/fault/value/struct/member[2]/name");
                if (eName == null)
                {
                    //throw new XmlRpcException("XMLRPC response was not found");
                    return default(T);
                }
                else
                {
                    if (eName.InnerText.Equals("faultString"))
                    {
                        XmlElement eValue = (XmlElement)requestDocument.SelectSingleNode("/methodResponse/fault/value/struct/member[2]/value/string");
                        throw new XmlRpcException("Error in XmlRpcClient serverUri: " + config.ServerUri + " " + eValue.InnerText);
                    }
                    else
                    {
                        throw new XmlRpcException("XMLRPC response was not found");
                    }
                }
            }
            else
            {
                return GetParameter<T>(param);
            }

        }
        private XmlRpcResponse Request(MethodRequest requestDoc, ICollection<Attachment> attachments)
        {
            
            Stream @out = null;
            try
            {
                String url = this.config.ServerUri.ToString();
                if (!url.EndsWith(SUFIX_GATEWAY))
                {
                    if (!url.EndsWith("/"))
                    {
                        url += "/";
                    }
                    url += SUFIX_GATEWAY;
                }
                HttpWebRequest connection = (HttpWebRequest)HttpWebRequest.Create(url);
                if (config.UsesProxy)
                {
                    connection.Proxy = new WebProxy(config.ProxyServer.Host, config.ProxyPort);
                }
                if (config.HasUserName)
                {
                    connection.Credentials = new NetworkCredential(config.UserName, config.Password);
                }
                connection.Method = "POST";
                @out = SendHeaders(connection);
                SendXmlDocumentPart(requestDoc, @out);
                foreach (Attachment attachment in attachments)
                {
                    FileInfo file = attachment.File;
                    String name = attachment.Name;
                    SendFile(file, name, @out);
                }
                WriteEnd(@out);
                @out.Close();
                try
                {
                    WebResponse webResponse = connection.GetResponse();
                    if (connection.HaveResponse)
                    {
                        HttpWebResponse response = (HttpWebResponse)webResponse;
                        String contentType = response.ContentType;
                        Stream @in = response.GetResponseStream();
                        switch (response.StatusCode)
                        {
                            case HttpStatusCode.OK:
                                return GetResponse(@in, contentType);
                            default:
                                throw new HttpException(response.StatusDescription, response.StatusCode, GetDetail(@in, response.CharacterSet));
                        }
                    }
                    else
                    {
                        throw new HttpException("Is not possible to get a response from the uri " + connection.RequestUri, HttpStatusCode.NotFound, "");
                    }
                }
                catch (WebException e)
                {
                    HttpWebResponse webResponse = (HttpWebResponse)e.Response;
                    Stream @in = webResponse.GetResponseStream();
                    throw new HttpException(e.Message, webResponse.StatusCode, GetDetail(@in, webResponse.CharacterSet));
                }

            }
            catch (IOException ioe)
            {
                throw new XmlRpcException(ioe.Message, ioe);
            }
            finally
            {
                if (@out != null)
                {
                    @out.Close();
                }
            }

        }
        private static string GetDetail(Stream @in, String charset)
        {
            Encoding encoding = Encoding.Default;
            StringBuilder sb = new StringBuilder();
            if (!String.IsNullOrEmpty(charset))
            {
                encoding = Encoding.GetEncoding(charset);
            }
            byte[] buffer = new byte[1024];
            int read = @in.Read(buffer, 0, buffer.Length);
            while (read > 0)
            {
                String text = encoding.GetString(buffer, 0, buffer.Length);
                sb.Append(text);
                read = @in.Read(buffer, 0, buffer.Length);
            }
            @in.Close();
            return sb.ToString();
        }
        private static XmlRpcResponse GetResponse(Stream @in, String contentType)
        {
            if (contentType.StartsWith("text/xml", StringComparison.Ordinal))
            {
                XmlDocument doc = GetDocument(@in);
                @in.Close();
                return new XmlRpcResponse(doc);
            }
            if (contentType.StartsWith("multipart/form-data", StringComparison.Ordinal))
            {
                WBFileUpload upload = new WBFileUpload();
                try
                {
                    upload.getFiles(@in, contentType);
                    Stream inDocument = upload.getFileInputStream("xmlrpc");
                    XmlDocument doc = GetDocument(inDocument);
                    try
                    {
                        @in.Close();
                    }
                    catch (IOException ioe)
                    {
                        throw new XmlRpcException("Error getting the response document", ioe);
                    }
                    HashSet<Part> parts = new HashSet<Part>();
                    foreach (String name in upload.getFileNames())
                    {
                        if (!name.Equals("xmlrpc"))
                        {
                            byte[] content = upload.getFileData(name);
                            Part part = new Part(content, name, upload.getFileName(name));
                            parts.Add(part);
                        }
                    }
                    XmlRpcResponse response = new XmlRpcResponse(doc, parts);
                    return response;
                }
                catch (Exception e)
                {
                    throw new XmlRpcException(e.Message);
                }
            }
            else
            {
                throw new XmlRpcException("The response is not a xml document");
            }
        }
        /*private static void AddParameter(XmlElement param, Object value)
        {            
            XmlElement eValue = param.OwnerDocument.CreateElement("value");
            String type = "string";
            String svalue = "";
            if (value is int)
            {
                type = "i4";
                svalue = value.ToString();
            }
            else
            {
                if (value is string)
                {
                    type = "string";
                    svalue = value.ToString();
                }
                else
                {
                    if (value is bool)
                    {
                        type = "boolean";
                        svalue = (bool)value ? "1" : "0";
                    }
                    else
                    {
                        if (value is DateTime)
                        {
                            type = "dateTime.iso8601";
                            svalue = ((DateTime)value).ToString(iso8601dateFormat,CultureInfo.InvariantCulture);
                        }
                        else
                        {
                            if (value is double || value is float)
                            {
                                type = "double";
                                svalue = value.ToString();
                            }
                            else
                            {
                                throw new XmlRpcException("This kind of convertion is not possible");
                            }
                        }
                    }
                }
            }
            XmlElement elementType = param.OwnerDocument.CreateElement(type);
            elementType.InnerText = svalue;
            eValue.AppendChild(elementType);
            param.AppendChild(eValue);
        }*/
        private static T Convert<T>(Object value)
        {
            T t = (T)value;
            return t;
        }
        private static T GetParameter<T>(XmlElement param)
        {

            foreach (XmlNode node in param.ChildNodes)
            {
                XmlElement elementValue = (XmlElement)node;
                if (elementValue.Name.Equals("value"))
                {
                    foreach (XmlNode NodeType in elementValue.ChildNodes)
                    {
                        XmlElement type = (XmlElement)NodeType;
                        Object value = GetValue<T>(type);
                        return Convert<T>(value);
                    }
                }
            }
            throw new XmlRpcException("The tag value was not found");
        }
        private static XmlDocument GetDocument(Stream @in)
        {
            XmlTextReader reader = new XmlTextReader(@in);
            XmlDocument doc = new XmlDocument();
            doc.Load(reader);
            return doc;
        }
        private static T ConvertData<T>(object value)
        {
            //T valueToReturn = value is T;
            if (!(value is T))
            {
                throw new XmlRpcException("The value is incompatible");
            }
            return (T)value;
        }
        private static T GetValue<T>(XmlElement type)
        {
            T result = default(T);
            String sType = type.Name;
            switch (sType)
            {
                case "string":
                    result = ConvertData<T>(type.InnerText);
                    break;
                case "i4":
                case "int":
                    result = ConvertData<T>(int.Parse(type.InnerText, CultureInfo.InvariantCulture));
                    break;
                case "boolean":
                    result = ConvertData<T>(type.InnerText.Equals("1") ? true : false);
                    break;
                case "double":
                    result = ConvertData<T>(double.Parse(type.InnerText, CultureInfo.InvariantCulture));
                    break;
                case "dateTime.iso8601":
                    result = ConvertData<T>(DateTime.ParseExact(type.InnerText, iso8601dateFormat, CultureInfo.InvariantCulture));
                    break;
                case "array":
                    result = ConvertData<T>(GetArray<T>(type));
                    break;
                case "struct":
                    result = ConvertData<T>(GetStruct<T>(type));
                    break;
                case "nil":
                    result = default(T);
                    break;

                default:
                    throw new XmlRpcException("It was not posible to get the value for " + type.InnerText);
            }
            return result;
        }
        private static T GetStruct<T>(XmlElement etype)
        {
            Type type = typeof(T);
            XmlNodeList list = etype.SelectNodes("./member");
            T t = Activator.CreateInstance<T>();
            foreach (XmlElement e in list)
            {
                XmlElement eName = (XmlElement)e.SelectSingleNode("./name");
                XmlElement eValue = (XmlElement)e.SelectSingleNode("./value");
                FieldInfo field = type.GetField(eName.InnerText);
                Debug.Assert(field != null, "A field was not found", "The field " + eName.InnerText + " was not found in " + type.Name);
                System.Reflection.MethodInfo mi = typeof(XmlRpcClient).GetMethod("GetValue", BindingFlags.Static | BindingFlags.NonPublic);
                Type[] _params = { field.FieldType };
                mi = mi.MakeGenericMethod(_params);
                object[] @params = { eValue.ChildNodes[0] };
                object value = mi.Invoke(null, @params);
                field.SetValue(t, value);
            }
            return t;

        }
        private static T GetArray<T>(XmlElement etype)
        {

            Type type = typeof(T);
            if (type.IsArray)
            {
                XmlNodeList nodes = etype.SelectNodes("./data/value");
                Type typeElements = type.GetElementType();
                Array arrayToReturn = Array.CreateInstance(typeElements, nodes.Count);
                int index = 0;
                foreach (XmlNode valueNode in nodes)
                {
                    foreach (XmlNode realvalue in valueNode.ChildNodes)
                    {
                        System.Reflection.MethodInfo mi = typeof(XmlRpcClient).GetMethod("GetValue", BindingFlags.Static | BindingFlags.NonPublic);
                        mi = mi.MakeGenericMethod(typeElements);
                        object[] @params = { realvalue };
                        object value = mi.Invoke(null, @params);
                        arrayToReturn.SetValue(value, index);
                        index++;
                    }
                }
                return (T)System.Convert.ChangeType(arrayToReturn, type);
            }
            else
            {
                throw new XmlRpcException("The value is incompatible");
            }
        }
        private static Stream SendHeaders(HttpWebRequest connection)
        {
            connection.ContentType = "multipart/form-data; boundary=" + boundary;
            Stream @out = connection.GetRequestStream();
            return @out;
        }
        private static void Write(String dato, Stream @out)
        {

            byte[] byteString = Encoding.Default.GetBytes(dato);
            byteString = Encoding.Convert(Encoding.Default, Encoding.UTF8, byteString);
            String xml = Encoding.UTF8.GetString(byteString);
            byteString = Encoding.Default.GetBytes(xml);
            @out.Write(byteString, 0, byteString.Length);
        }
        private static void SendFile(FileInfo file, String name, Stream @out)
        {
            String newBoundary = "\r\n--" + boundary + "\r\n";
            String contentDisposition = "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + file.Name + "\"\r\n\r\n";
            Write(newBoundary, @out);
            Write(contentDisposition, @out);
            Stream @in = file.OpenRead();
            byte[] buffer = new byte[2048];
            int read = @in.Read(buffer, 0, buffer.Length);
            while (read != 0)
            {
                @out.Write(buffer, 0, read);
                read = @in.Read(buffer, 0, buffer.Length);
            }
            @in.Close();
        }
        private static void SendXmlDocumentPart(MethodRequest requestDoc, Stream @out)
        {
            String newBoundary = "--" + boundary + "\r\n";
            String contentDisposition = "Content-Disposition: form-data; name=\"xmlrpc\"; filename=\"xmlrpc.xml\"\r\n\r\n";
            Write(newBoundary, @out);
            Write(contentDisposition, @out);
            XmlSerializerFactory factory = new XmlSerializerFactory();
            XmlSerializer serializer = factory.CreateSerializer(requestDoc.GetType());
#if DEBUG
            MemoryStream memoryStream = new MemoryStream();
            XmlTextWriter xmlTextWriter2 = new XmlTextWriter(memoryStream, Encoding.UTF8);
            serializer.Serialize(xmlTextWriter2, requestDoc);
            String xml = Encoding.Default.GetString(memoryStream.ToArray());
            Debug.WriteLine(xml);
#endif
            XmlTextWriter xmlTextWriter = new XmlTextWriter(@out, Encoding.Default);
            serializer.Serialize(xmlTextWriter, requestDoc);

            //Write(requestDoc.OuterXml,@out);
        }
        private static void WriteEnd(Stream @out)
        {
            String newBoundary = "\r\n--" + boundary + "\r\n";
            Write(newBoundary, @out);
        }

    }
}
