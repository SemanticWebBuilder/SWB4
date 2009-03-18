using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{

    public sealed class Parameter
    {
        [XmlElement("value")]
        public Value Value { get; set; }
        public Parameter()
        {

        }
    }
}
