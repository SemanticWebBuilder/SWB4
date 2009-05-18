/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.xmlrpc;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.List;
import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import static org.semanticwb.xmlrpc.Base64.encode;
import static java.net.HttpURLConnection.*;
import static org.semanticwb.xmlrpc.XmlRpcSerializer.*;

/**
 *
 * @author victor.lorenzana
 */
class XmlRpcClient
{

    public static final String SUFIX_GATEWAY = "gtw";
    private Map<String, List<String>> responseProperties = new HashMap<String, List<String>>();
    private static String boundary = "gc0p4Jq0M2Yt08jU534c0p";
    private XmlRpcClientConfig config;

    public XmlRpcClient(XmlRpcClientConfig config)
    {
        this.config = config;
    }

    public void setConfig(XmlRpcClientConfig config)
    {
        this.config = config;
    }

    public XmlRpcClientConfig getClientConfig()
    {
        return config;
    }

    public Map<String, List<String>> getResponseProperties()
    {
        return responseProperties;
    }

    public Response execute(Class clazz, String methodName, Object[] parameters, Set<Attachment> attachments) throws XmlRpcException, HttpException,ConnectException
    {
        for (Attachment attachment : attachments)
        {
            if (attachment.getFile().isDirectory())
            {
                throw new XmlRpcException("The attachment '" + attachment.getName() + "' is a directory");
            }
            if (!attachment.getFile().exists())
            {
                throw new XmlRpcException("The attachment '" + attachment.getName() + "' does not exist");
            }
        }
        Document requestDoc = serializeRequest(methodName, parameters);
        XmlResponse response = request(requestDoc, attachments);        
        try
        {

            Object obj = deserializeResponse(clazz, response.getDocument());
            Response responseToReturn = new Response(obj, response.getResponseParts());


            return responseToReturn;
        }
        catch (ParseException pe)
        {
            throw new XmlRpcException(pe.getMessage(), pe);
        }
    }

    private static Document getDocument(InputStream in) throws XmlRpcException
    {


        SAXBuilder builder = new SAXBuilder();
        try
        {
            Document doc = builder.build(in);
            /*Format format = org.jdom.output.Format.getCompactFormat();
            format.setEncoding("utf-8");
            XMLOutputter xMLOutputter = new XMLOutputter(format);
            String xml = xMLOutputter.outputString(doc);*/
            return doc;
        }
        catch (Exception jde)
        {
            throw new XmlRpcException("It was not possible to contruct the document from the InputStream");
        }
    }

    private static OutputStream sendHeaders(HttpURLConnection connection) throws IOException
    {
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        OutputStream out = connection.getOutputStream();
        return out;
    }

    private static void sendFile(File file, String name, OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + file.getName() + "\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        FileInputStream in = new FileInputStream(file);
        byte[] buffer = new byte[2048];
        int read = in.read(buffer);
        while (read != -1)
        {
            out.write(buffer, 0, read);
            read = in.read(buffer);
        }
        in.close();
    }

    private static void writeEnd(OutputStream out) throws IOException
    {
        String newBoundary = "\r\n--" + boundary + "\r\n";
        out.write(newBoundary.getBytes());
    }

    private static void sendXmlDocumentPart(Document requestDoc, OutputStream out) throws IOException
    {
        String newBoundary = "--" + boundary + "\r\n";
        String contentDisposition = "Content-Disposition: form-data; name=\"xmlrpc\"; filename=\"xmlrpc.xml\"\r\n\r\n";
        out.write(newBoundary.getBytes());
        out.write(contentDisposition.getBytes());
        Format format = Format.getPrettyFormat();
        format.setEncoding("UTF-8");
        XMLOutputter outp = new XMLOutputter(format);
        outp.output(requestDoc, out);
    }

    private String getUserPassWordEncoded()
    {
        String userPassword = config.getUserName() + ":" + config.getPassword();
        String encoded = new String(encode(userPassword.getBytes()));
        return encoded;
    }

    private XmlResponse request(Document requestDoc, Set<Attachment> attachments) throws XmlRpcException, HttpException,ConnectException
    {
        OutputStream out = null;
        try
        {
            Proxy proxy;
            if (config.usesProxyServer())
            {
                proxy = new Proxy(Type.HTTP, new InetSocketAddress(config.getProxyServer().toString(), config.getProxyPort()));
            }
            else
            {
                proxy = Proxy.NO_PROXY;
            }
            String url = config.getWebAddress().toURL().toString();
            if (!url.endsWith(SUFIX_GATEWAY))
            {
                if (!url.endsWith("/"))
                {
                    url += "/";
                }
                url += SUFIX_GATEWAY;
            }
            URL urlToConnect = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlToConnect.openConnection(proxy);
            HttpURLConnection.setFollowRedirects(true);
            if (config.hasUserPassWord())
            {
                connection.setRequestProperty("Authorization", "Basic " + getUserPassWordEncoded());
            }
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            out = sendHeaders(connection);
            sendXmlDocumentPart(requestDoc, out);
            for (Attachment attachment : attachments)
            {
                File file = attachment.getFile();
                String name = attachment.getName();
                sendFile(file, name, out);
            }
            writeEnd(out);
            out.close();
            int responseCode = connection.getResponseCode();
            String contentType = connection.getHeaderField("Content-Type");
            InputStream error = connection.getErrorStream();
            switch (responseCode)
            {
                case HTTP_OK:
                    this.responseProperties = connection.getHeaderFields();
                    return getResponse(connection.getInputStream(), contentType);
                case HTTP_NOT_FOUND:
                    throw new HttpException("The path " + connection.getURL() + " was not found", HTTP_NOT_FOUND, getDetail(error, contentType));
                case HTTP_FORBIDDEN:
                    throw new HttpException("Clave o contraseña incorrecta", HTTP_FORBIDDEN, getDetail(error, contentType));
                default:
                    throw new HttpException(connection.getResponseMessage(), responseCode, getDetail(error, contentType));
            }
        }
        catch (MalformedURLException mfe)
        {
            throw new XmlRpcException(mfe);
        }
        catch (ConnectException ce)
        {
            throw ce;
        }
        catch (IOException ioe)
        {
            throw new XmlRpcException(ioe);
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException ioe)
                {
                    throw new XmlRpcException(ioe);
                }
            }
        }
    }

    private static String getDetail(InputStream in, String contentType) throws XmlRpcException
    {
        StringBuilder sb = new StringBuilder();

        String charSet = "utf-8";
        if (contentType != null)
        {
            int posInit = contentType.indexOf("charset=");
            if (posInit != -1)
            {
                charSet = contentType.substring(posInit + 8);
            }
        }
        try
        {

            byte[] buffer = new byte[2048];
            int read = in.read(buffer);
            while (read != -1)
            {
                sb.append(new String(buffer, 0, read, charSet));
                read = in.read(buffer);
            }
        }
        catch (IOException ioe)
        {
            throw new XmlRpcException("Error gettting the detail of response", ioe);
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException ioe)
                {
                    throw new XmlRpcException("Error closing conexión in the detail of response", ioe);
                }
            }
        }
        return sb.toString();
    }

    private static XmlResponse getResponse(InputStream in, String contentType) throws XmlRpcException
    {
        if (contentType == null)
        {
            throw new XmlRpcException("The content-Type is not valid (is null)");
        }
        else if (contentType.startsWith("multipart/form-data"))
        {
            WBFileUpload upload = new WBFileUpload();
            try
            {
                upload.getFiles(in, contentType);
                InputStream inDocument = upload.getFileInputStream("xmlrpc");
                Document doc = getDocument(inDocument);
                try
                {
                    in.close();
                }
                catch (IOException ioe)
                {
                    throw new XmlRpcException("Error getting the response document", ioe);
                }
                HashSet<Part> parts = new HashSet<Part>();
                for (String name : upload.getFileNames())
                {
                    if (!name.equals("xmlrpc"))
                    {
                        byte[] content = upload.getFileData(name);
                        Part part = new Part(content, name, upload.getFileName(name));
                        parts.add(part);
                    }
                }
                XmlResponse response = new XmlResponse(doc, parts);
                return response;
            }
            catch (Exception e)
            {
                throw new XmlRpcException(e);
            }
        }
        else if (contentType.startsWith("text/xml"))
        {
            Document doc = getDocument(in);
            try
            {
                in.close();
            }
            catch (IOException ioe)
            {
                throw new XmlRpcException("Error getting the response document", ioe);
            }
            return new XmlResponse(doc);
        }
        else
        {
            throw new XmlRpcException("The content-Type is invalid (" + contentType + ")");
        }

    }
}
