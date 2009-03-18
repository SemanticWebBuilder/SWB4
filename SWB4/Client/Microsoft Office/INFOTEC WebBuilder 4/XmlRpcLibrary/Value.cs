using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    [XmlRoot("value")]
    [XmlInclude(typeof(IntValue))]
    [XmlInclude(typeof(DoubleValue))]
    [XmlInclude(typeof(DateTimeValue))]
    [XmlInclude(typeof(BooleanValue))]
    [XmlInclude(typeof(StringValue))]
    [XmlInclude(typeof(FloatValue))]
    [XmlInclude(typeof(ArrayValue))]
    [XmlInclude(typeof(StructValue))]
    [XmlInclude(typeof(Member))]
    public class Value
    {
        public Value()
        {
        }
    }
}
