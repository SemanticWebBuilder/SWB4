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
        static TraceEventLogListener()
        {
            try
            {
                if (!EventLog.SourceExists(sourceEvent))
                {
                    try
                    {                        
                        EventLog.CreateEventSource(sourceEvent, eventLogName);
                    }
                    catch (Exception e)
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
                log.ModifyOverflowPolicy(OverflowAction.OverwriteOlder, 5);                
            }
            catch (Exception e)
            {
                Debug.WriteLine(e.Message);
                Debug.WriteLine(e.StackTrace);
            }
        }
        public override void Write(string message)
        {
            try
            {
                log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Information);
            }
            catch (System.ComponentModel.Win32Exception we)
            {
                if (we.Message.Equals("The event log file is full",StringComparison.CurrentCultureIgnoreCase))
                {
                    log.Clear();
                    log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Information);
                }
            }
        }

        public override void WriteLine(string message)
        {
            try
            {
                log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Information);
            }
            catch (System.ComponentModel.Win32Exception we)
            {
                if (we.Message.Equals("The event log file is full",StringComparison.CurrentCultureIgnoreCase))
                {
                    log.Clear();
                    log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Information);
                }
            }
        }
        public void WriteError(Exception e)
        {
            try
            {
                log.WriteEntry(OfficeApplication.m_version + "\r\n\r\n" + e.Message + "\r\n" + e.StackTrace, EventLogEntryType.Error);
            }
            catch (System.ComponentModel.Win32Exception we)
            {
                if (we.Message.Equals("The event log file is full",StringComparison.CurrentCultureIgnoreCase))
                {
                    log.Clear();
                    log.WriteEntry(OfficeApplication.m_version + "\r\n\r\n" + e.Message + "\r\n" + e.StackTrace, EventLogEntryType.Error);
                }
            }
        }
        public void WriteWarning(string message)
        {
            try
            {
                log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Error);
            }
             catch (System.ComponentModel.Win32Exception we)
            {
                if (we.Message.Equals("The event log file is full",StringComparison.CurrentCultureIgnoreCase))
                {
                    log.Clear();
                    log.WriteEntry(OfficeApplication.m_version + "\r\n" + message, EventLogEntryType.Error);
                }
            }
        }
    }
}
