using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;

namespace XmlRpcLibrary
{   
    [XmlRoot("value")]
    public sealed class IntValue : Value
    {
        [XmlElement("int")]       
        public int Value { get; set;}
        public IntValue()
        {            
        }
        public IntValue(int value)
        {
            this.Value=value;
        }
    }
}
