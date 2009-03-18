using System;

namespace TSWizards
{
	/// <summary>
	/// Summary description for ShowStepEvent.
	/// </summary>
	public class ShowStepEventArgs : EventArgs
	{
		private BaseWizard.StepDirection direction;

		public ShowStepEventArgs(BaseWizard.StepDirection direction)
		{
			this.direction = direction;
		}

		public BaseWizard.StepDirection Direction
		{
			get
			{
				return direction;
			}
		}
	}

	public delegate void ShowStepEventHandler(object sender, ShowStepEventArgs e);
}
