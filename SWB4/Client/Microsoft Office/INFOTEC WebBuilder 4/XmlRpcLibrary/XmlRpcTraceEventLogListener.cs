using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
namespace XmlRpcLibrary
{
    public class XmlRpcTraceEventLogListener : TraceListener
    {
        public static readonly XmlRpcTraceEventLogListener listener = new XmlRpcTraceEventLogListener();        
        public static readonly String eventLogName = "SemanticWebBuilder 4.0";
        public static readonly String sourceEvent= "XmlRpcLibrary";
        public static readonly EventLog log = new EventLog(eventLogName);        
        static XmlRpcTraceEventLogListener()
        {
            try
            {
                if (!EventLog.SourceExists(sourceEvent))
                {
                    try
                    {
                        EventLog.CreateEventSource(sourceEvent, eventLogName);
                    }
                    catch(Exception e)
                    {
                        Debug.WriteLine(e.Message);
                        Debug.WriteLine(e.StackTrace);
                    }
                }
                log.Source = sourceEvent;
                string logname = EventLog.LogNameFromSourceName(sourceEvent, ".");
                if (logname != null)
                {
                    log.Log = logname;
                }
                Debug.Listeners.Add(listener);
                Trace.Listeners.Add(listener);
            }
            catch(Exception e) {
                Debug.WriteLine(e.Message);
                Debug.WriteLine(e.StackTrace);
            }
        }
        public static void WriteError(Exception e)
        {
            log.WriteEntry(e.Message + "\r\n\r\n" + e.StackTrace, EventLogEntryType.Error);
        }
        public override void Write(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Information);            
        }

        public override void WriteLine(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Information);
        }
        
        public void WriteWarning(string message)
        {
            log.WriteEntry(message, EventLogEntryType.Error);
        }
    }
}
