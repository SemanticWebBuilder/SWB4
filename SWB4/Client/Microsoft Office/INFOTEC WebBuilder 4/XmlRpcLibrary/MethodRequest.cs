using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("methodCall")]    
    [XmlInclude(typeof(Parameter))]
    public sealed class MethodRequest
    {
        private ArrayList _XmlRpcParameters;
        [XmlElement("methodName")]
        public string MethodName { get; set; }
        public MethodRequest()
        {
            
        }
        public MethodRequest(ArrayList xmlRpcParameters)
        {
            this._XmlRpcParameters = xmlRpcParameters;
        }
        [XmlArray("params")]
        [XmlArrayItem("param",Type=typeof(Parameter))]        
        public ArrayList XmlRpcParameters 
        {
            get
            {
                return _XmlRpcParameters;
            }
            
        }

    }
}
