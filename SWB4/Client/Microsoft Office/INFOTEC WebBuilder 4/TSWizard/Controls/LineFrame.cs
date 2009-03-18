using System;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace TSWizards.Controls
{
	/// <summary>
	/// Summary description for LineFrame.
	/// </summary>
	public class EtchedLine : System.Windows.Forms.Control
	{
		public EtchedLine()
		{
		}

		protected override void OnPaint(PaintEventArgs pe)
		{
			// TODO: Add custom paint code here
			Graphics g = pe.Graphics;
			using(Pen p = new Pen(Color.FromArgb(128, 128, 128)))
			{
				g.DrawLine(p, new Point(0, 0), new Point(Width, 0));
			}
			g.DrawLine(Pens.White, new Point(0, 1), new Point(Width, 1));

			// Calling the base class OnPaint
			base.OnPaint(pe);
		}

		protected override Size DefaultSize
		{
			get
			{
				return new Size(75, 2);
			}
		}

		[Browsable(true)]
		[Category("Layout")]
		[Description("Gets/Sets the width of the line")]
		public new int Width
		{
			get
			{
				return Size.Width;
			}
			set
			{
				Size = new Size(value, 2);
			}
		}
	}
}
