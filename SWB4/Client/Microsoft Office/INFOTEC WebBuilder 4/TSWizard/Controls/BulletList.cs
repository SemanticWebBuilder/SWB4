/**  
* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración, 
* colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de 
* información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes 
* fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y 
* procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación 
* para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite. 
* 
* INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público (‘open source’), 
* en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; 
* aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, 
* todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización 
* del SemanticWebBuilder 4.0. 
* 
* INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, 
* siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar 
* de la misma. 
* 
* Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente 
* dirección electrónica: 
*  http://www.webbuilder.org.mx 
**/ 
 
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
		char bulletCharacter = '�';
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
		[DefaultValue('�')]
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
