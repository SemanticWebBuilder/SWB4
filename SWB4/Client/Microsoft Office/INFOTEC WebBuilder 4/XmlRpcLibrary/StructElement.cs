using System;
using System.Collections.Generic;
using System.Collections;
using System.Linq;
using System.Text;
using System.Reflection;
using System.Xml;
using System.Xml.Serialization;
namespace XmlRpcLibrary
{
    public class StructElement
    {
        private ArrayList _values = new ArrayList();
        public StructElement()
        {
        }
        public StructElement(Object obj)
        {
            foreach(FieldInfo fieldInfo in obj.GetType().GetFields())
            {
                object value=fieldInfo.GetValue(obj);
                if (value != null)
                {
                    Value ovalue = XmlRpcClient.GetParameter(value);
                    Member member = new Member(fieldInfo.Name, ovalue);
                    _values.Add(member);
                }
            }
        }        
        [XmlArrayItem("member", Type = typeof(Value))]
        public ArrayList Values
        {
            get
            {
                return _values;
            }
        } 
    }
}
