using System;
using System.Collections;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace TSWizards.Controls
{
	/// <summary>
	/// Summary description for BulletList.
	/// </summary>
	public class BulletList : Label
	{
		char bulletCharacter = '•';
		StringCollection items;

		public BulletList()
		{
			items = new StringCollection();
		}

		[Browsable(true)]
		[Category("Appearance")]
		[Description("A collection of items to appear with a bullet")]
		[Editor("System.Windows.Forms.Design.StringCollectionEditor, System.Design",
		"System.Drawing.Design.UITypeEditor, System.Drawing")]
		[DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
		public virtual StringCollection Items
		{
			get
			{
				return items;
			}
		}

		[Browsable(true)]
		[Category("Appearance")]
		[Description("Gets/Sets the character that will be displayed before each bulleted item")]
		[DefaultValue('•')]
		public virtual char BulletCharacter
		{
			get
			{
				return bulletCharacter;
			}
			set
			{
				bulletCharacter = value;
			}
		}

		protected override void OnPaint(PaintEventArgs e)
		{
			Graphics g = e.Graphics;
			
			PointF drawPoint = new PointF(0.0f, 0.0f);

			using( Brush brush = new SolidBrush(ForeColor) )
			{
				StringFormat format = StringFormat.GenericTypographic;
				SizeF size = g.MeasureString(Text, Font);
				g.DrawString( Text, Font, brush, drawPoint, format );

				foreach(string item in items)
				{
					string str = BulletCharacter + " " + item;
					drawPoint.Y += size.Height;// + (float) Font.FontFamily.GetLineSpacing(Font.Style);
					size = g.MeasureString(str, Font);
					System.Diagnostics.Trace.WriteLine(drawPoint.ToString());
					g.DrawString( str, Font, brush, drawPoint, format);
				}
			}
		}
	}
}
