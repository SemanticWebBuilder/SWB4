using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    public sealed class ArrayValue : Value
    {
        [XmlElement("array")]
        public ArrayElement ArrayElement { get; set; }  
    }
}
