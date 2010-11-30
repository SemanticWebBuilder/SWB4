using System;

namespace TSWizards
{
	public enum AllowClose
	{
		Ask,
		[Obsolete("Use AllowClose.Ask instead")]
		AskIfNotFinish,
		AlwaysAllow
	}
}
