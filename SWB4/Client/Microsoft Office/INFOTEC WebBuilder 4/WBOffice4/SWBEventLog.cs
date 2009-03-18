using System;
using System.Collections.Generic;
using System.Linq;
using System.Diagnostics;
using System.Text;
using System.Reflection;
namespace WBOffice4
{
    public sealed class SwbEventLog
    {
        private const string lineReturn = "\r\n";
        private const string EventSource = "Semantic INFOTEC WebBuilder Office 4";
        private const string LogSource = "Application";
        private static EventLog eventlog = new EventLog();
        private SwbEventLog()
        {

        }
        static SwbEventLog()
        {
            if (!EventLog.SourceExists(EventSource))
            {
                EventLog.CreateEventSource(EventSource, LogSource);
            }
            eventlog.Source = EventSource;
        }
        public static void Log(string message)
        {
            Debug.WriteLine(message);
            eventlog.WriteEntry(message);
        }
        public static void Log(string message, EventLogEntryType type)
        {
            Debug.WriteLine(message);
            eventlog.WriteEntry(message, type);
        }
        public static void Log(Exception exception, EventLogEntryType type)
        {
            StringBuilder message = new StringBuilder(exception.Message);


            message.Append(lineReturn);
            message.Append(exception.StackTrace);

            message.Append(lineReturn);
            message.Append(exception.Source);

            if (exception.InnerException != null)
            {
                message.Append(lineReturn);
                message.Append(exception.InnerException);
            }
            message.Append(lineReturn);
            message.Append("Assembly Name: " + Assembly.GetExecutingAssembly().GetName());
            if (exception.HelpLink != null)
            {
                message.Append(lineReturn);
                message.Append("HelpLink: " + exception.HelpLink);
            }
            eventlog.WriteEntry(message.ToString(), type);
        }
        public static void Log(Exception exception)
        {
            Log(exception, EventLogEntryType.Error);
        }

    }
}
