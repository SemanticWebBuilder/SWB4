using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
namespace WBOffice4.Utils
{
    public class TraceEventLogListener : TraceListener
    {
        public static readonly String eventLogName = "SemanticWebBuilder 4.0";
        public static readonly String sourceEvent = "WBOffice4";
        public static readonly EventLog log = new EventLog(eventLogName);
        public TraceEventLogListener()
        {
            if (!EventLog.SourceExists(sourceEvent))
            {
                EventLog.CreateEventSource(sourceEvent, eventLogName);
            }
            log.Source = sourceEvent;
            string logname=EventLog.LogNameFromSourceName(sourceEvent,".");
            if (logname != null)
            {
                log.Log = logname;
            }
        }
        public override void Write(string message)
        {
            log.WriteEntry(OfficeApplication.m_version+ "\r\n"+message, EventLogEntryType.Information);            
        }

        public override void WriteLine(string message)
        {
            log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Information);
        }
        public void WriteError(Exception e)
        {
            log.WriteEntry(OfficeApplication.m_version + "\r\n\r\n" + e.Message + "\r\n" + e.StackTrace, EventLogEntryType.Error);
        }
        public void WriteWarning(string message)
        {
            log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Error);
        }
    }
}
