using System;
using System.Collections.Generic;
using System.Collections;
using System.Reflection;
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
    public sealed class StructValue : Value
    {
        [XmlArray("struct")]
        [XmlArrayItem("member", Type = typeof(Value))]
        public ArrayList structElement { get; set; }

        public StructValue()
        {
            structElement = new ArrayList();
        }
        public StructValue(object obj)
        {
            structElement = new ArrayList();
            foreach(FieldInfo fieldInfo in obj.GetType().GetFields())
            {
                object value=fieldInfo.GetValue(obj);
                if (value != null)
                {
                    Value ovalue = XmlRpcClient.GetParameter(value);
                    Member member = new Member(fieldInfo.Name, ovalue);
                    structElement.Add(member);
                }
            }
        }
    }
}
