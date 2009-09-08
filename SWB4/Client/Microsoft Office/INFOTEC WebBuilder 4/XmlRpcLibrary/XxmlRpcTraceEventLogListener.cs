using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
namespace XmlRpcLibrary
{
    public class XxmlRpcTraceEventLogListener : TraceListener
    {
        public static readonly String eventLogName = "SemanticWebBuilder 4.0";
        public static readonly String sourceEvent= "XmlRpcLibrary ";
        public static readonly EventLog log = new EventLog(eventLogName);
        public XxmlRpcTraceEventLogListener()
        {
            //sourceEvent="XmlRpcLibrary " + GetType().Assembly.GetName().Version.ToString();
            if (!EventLog.SourceExists(sourceEvent))
            {
                EventLog.CreateEventSource(sourceEvent, eventLogName);
            }
            log.Source = sourceEvent;
        }
        public override void Write(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Information);            
        }

        public override void WriteLine(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Information);
        }
        public void WriteError(Exception e)
        {
            log.WriteEntry(e.Message + "\r\n"+e.StackTrace, EventLogEntryType.Error);
        }
        public void WriteWarning(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Error);
        }
    }
}
