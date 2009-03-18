using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
namespace XmlRpcLibrary
{
    public class XmlRpcResponse
    {
        private XmlDocument response;
        HashSet<Part> parts = new HashSet<Part>();
        public XmlRpcResponse(XmlDocument response, HashSet<Part> parts)
        {
            this.response = response;
            this.parts = parts;
        }
        public XmlRpcResponse(XmlDocument response)
        {
            this.response = response;
        }
        public HashSet<Part> GetResponseParts()
        {
            return parts;
        }
        public XmlDocument getXmlDocumentResponse()
        {
            return response;
        }
    }
}
