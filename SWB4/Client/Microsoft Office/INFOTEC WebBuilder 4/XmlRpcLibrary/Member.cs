using System;
using System.Collections.Generic;
using System.Linq;
using System.Collections;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{

    public class Member : Value
    {
        [XmlElement("name")]
        public String Name { get; set; }
        [XmlElement("value")]
        public Value Value { get; set; }
        public Member()
        {

        }
        public Member(string name,Value value)
        {
            this.Name = name;
            this.Value = value;
        }
    }
}
