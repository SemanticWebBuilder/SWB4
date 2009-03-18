using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;

using System.Windows.Forms;


namespace TSWizards
{
	public class BaseExteriorStep : TSWizards.BaseStep
	{
		private System.ComponentModel.IContainer components = null;

		public BaseExteriorStep()
		{
			// This call is required by the Windows Form Designer.
			InitializeComponent();

			// TODO: Add any initialization after the InitializeComponent call
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if (components != null) 
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}

		#region Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.SuspendLayout();
			// 
			// Description
			// 
			this.Description.BackColor = System.Drawing.Color.White;
			this.Description.Size = new System.Drawing.Size(308, 48);
			// 
			// BaseExteriorStep
			// 
			this.BackColor = System.Drawing.Color.White;
			this.Controls.AddRange(new System.Windows.Forms.Control[] {
																		  this.Description});
			this.Name = "BaseExteriorStep";
			this.PageLayout = TSWizards.PageLayout.ExteriorPage;
			this.Size = new System.Drawing.Size(324, 313);
			this.ResumeLayout(false);

		}
		#endregion
	}
}

