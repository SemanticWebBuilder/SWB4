/*	INFOTEC WebBuilder es una herramienta para el desarrollo de portales de conocimiento, colaboración e integración para Internet, la cual, es una creación original del Fondo de Información y Documentación para la Industria INFOTEC, misma que se encuentra debidamente registrada ante el Registro Público del Derecho de Autor de los Estados Unidos Mexicanos con el No. 03-2002-052312015400-14, para la versión 1; No. 03-2003-012112473900 para la versión 2, y No. 03-2006-012012004000-01 para la versión 3, respectivamente. 
	INFOTEC pone a su disposición la herramienta INFOTEC WebBuilder a través de su licenciamiento abierto al público (‘open source’), en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición; aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software, todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización de INFOTEC WebBuilder 3.2
	INFOTEC no otorga garantía sobre INFOTEC WebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita, siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar de la misma.
	Si usted tiene cualquier duda o comentario sobre INFOTEC WebBuilder, INFOTEC pone a su disposición la siguiente dirección electrónica: http://www.webbuilder.org.mx	
*/
using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;
using System.IO;
using System.Net;
using System.Diagnostics;
namespace WBOffice4.Forms
{
	/// <summary>Forma que muestra un resumen de información del contenido</summary>
	public class FrmDetalleDoc : System.Windows.Forms.Form
	{		
		private System.Windows.Forms.TabControl tabControl1;
		private System.Windows.Forms.TabPage tabPage1;
		private System.Windows.Forms.TabPage tabPage2;
		private System.Windows.Forms.TabPage tabPage3;
		private System.Windows.Forms.ListView listView1;
		private System.Windows.Forms.ColumnHeader columnHeader1;
		private System.Windows.Forms.ColumnHeader columnHeader2;
		private System.Windows.Forms.ListView listView2;
		private System.Windows.Forms.ColumnHeader columnHeader3;
		private System.Windows.Forms.ColumnHeader columnHeader4;
		private System.Windows.Forms.ColumnHeader columnHeader5;
		private System.Windows.Forms.ColumnHeader columnHeader6;
		private System.Windows.Forms.ColumnHeader columnHeader7;
		private System.Windows.Forms.ListView listView3;
		private System.Windows.Forms.ColumnHeader columnHeader8;
		private System.Windows.Forms.ColumnHeader columnHeader9;
		private System.Windows.Forms.ImageList imageList1;
		private System.ComponentModel.IContainer components;

		/// <summary>Crea una forma que muestra información detalle del contenido</summary>
        public FrmDetalleDoc(OfficeDocument document)
		{
			//
			// Required for Windows Form Designer support
			//
			InitializeComponent();

			//
			// TODO: Add any constructor code after InitializeComponent call
			//
			this.Cursor=Cursors.WaitCursor;  
			string[] itemsdoc=new string[2];
            FileInfo fdocword = document.FilePath;

            itemsdoc[0] = "Nombre del archivo";
            itemsdoc[1] = document.FilePath.Name;
			System.Windows.Forms.ListViewItem itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);

            itemsdoc[0] = "Ubicación";
			itemsdoc[1]=fdocword.Directory.FullName ; 
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);


            /*itemsdoc[0] = "Palabras";
			itemsdoc[1]=""+CWebBuilder.doc.Words.Count  ; 
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);*/

            itemsdoc[0] = "Imagenes y Dibujos";
			itemsdoc[1]=""+document.Images ; 
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);


            itemsdoc[0] = "Ligas";
			itemsdoc[1]=""+document.Links.Length;  
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);


            itemsdoc[0] = "Tamaño del archivo";
            if (fdocword.Exists)
            {
                itemsdoc[1] = fdocword.Length + " bytes";
            }
            else
            {
                itemsdoc[1] = "Documento no esta guardado";
            }
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);

            itemsdoc[0] = "Observación";
			itemsdoc[1]=this.ValidaNombre(fdocword);
			itemdoc=new ListViewItem(itemsdoc);
			this.listView1.Items.Add(itemdoc);
            if (itemsdoc[1] != "El nombre del archivo es correcto")
			{
				itemdoc.ImageIndex=0;
			}
			else
			{
				itemdoc.ImageIndex=1;
			}



            foreach (String archivo in document.Links)
			{				
				
					if(archivo!=null)
					{
						try
						{
							System.Uri basepath=new System.Uri(document.FilePath.FullName);
							System.Uri filepath=new System.Uri(basepath,archivo); 
							if(!filepath.IsFile )
							{
								bool existe=false;
								try
								{
									System.Uri uripagina= new Uri(archivo);							
									System.Net.HttpWebRequest  wbpagina=(HttpWebRequest)System.Net.HttpWebRequest.Create(uripagina);
                                    SWBConfiguration configuration = new SWBConfiguration();

                                    if (configuration.ProxyServer != null && configuration.ProxyServer != "")
									{
										// por probar
                                        wbpagina.Proxy = new WebProxy(new Uri(configuration.ProxyServer)+":"+int.Parse(configuration.ProxyPort, System.Globalization.CultureInfo.InvariantCulture));
									}
									System.Net.HttpWebResponse res=(HttpWebResponse)wbpagina.GetResponse(); 
									if(wbpagina.HaveResponse && res.StatusCode==System.Net.HttpStatusCode.OK)
									{								
										existe=true;
										
									}							
									
								}
								catch(Exception e)
								{
									System.Console.WriteLine(e.Message);  
								}
								string[] itemspag=new string[2];
								itemspag[0]=archivo;
								if(existe)
								{
                                    itemspag[1] = "Sí";
								}
								else
								{
									itemspag[1]="No";
								}
								System.Windows.Forms.ListViewItem itempag= new ListViewItem(itemspag);
								if(itemspag[1]=="Sí")
								{
									itempag.ImageIndex=1;
								}
								else
								{
									itempag.ImageIndex=0;
								}
								this.listView3.Items.Add(itempag);   
							}
						}
						catch(Exception ue)
						{
							Debug.WriteLine(ue.Message); 
						}
					}
				
			}
            foreach (String archivo in document.Links)
			{
				try
				{					
					if(archivo!=null)
					{
						try
						{
							System.Uri basepath=new System.Uri(document.FilePath.FullName);
							System.Uri filepath=new System.Uri(basepath,archivo);
							if(filepath.IsFile)
							{
								FileInfo farchivo=new FileInfo(filepath.LocalPath);							
								if(farchivo.Exists)
								{
									long tam=(farchivo.Length/1024); 
									if(tam==0)
									{
										tam++;
									}
									String stam=tam+" KB";
									string[] items=new string[5];			
									items[0]=farchivo.Name; 
									items[1]=farchivo.Directory.FullName;  
									items[2]=stam;
									items[3]="Sí";
									items[4]=ValidaNombre(farchivo); 
									System.Windows.Forms.ListViewItem item= new ListViewItem(items);   
									item.ImageIndex=1;
									if(items[4]!="El nombre del archivo es correcto")
									{
										item.ImageIndex=0;
									}								
									this.listView2.Items.Add(item);
										
								}
								else
								{
									int tam=0;
									String stam=tam+" KB";
									string[] items=new string[5];			
									items[0]=farchivo.Name; 
									items[1]=farchivo.Directory.FullName;
									items[2]=stam;
									items[3]="No";
									items[4]=ValidaNombre(farchivo); 
									System.Windows.Forms.ListViewItem item= new ListViewItem(items);   
									item.ImageIndex=0;											
									this.listView2.Items.Add(item);

								}
								
							}
						}
						catch(Exception ue)
						{
							Debug.WriteLine(ue.Message); 
						}
					}
				}
				catch{}
			}
			this.Cursor=Cursors.Default;
		}

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		protected override void Dispose( bool disposing )
		{
			if( disposing )
			{
				if(components != null)
				{
					components.Dispose();
				}
			}
			base.Dispose( disposing );
		}
		private String ValidaNombre(FileInfo archivo)
		{
			String ext=archivo.Extension;
			int pos=archivo.Name.LastIndexOf(ext);
			String nombre=archivo.Name.Substring(0,pos);  
			if(nombre.Length>40)
			{
                return "El Nombre del archivo es mayor de 40 caracteres";				
			}
			char[] letras=nombre.ToCharArray(); 
			for(int i=0;i<letras.Length;i++)
			{
				char letra=letras[i];
				if(Char.IsPunctuation(letra))
				{
                    return "El nombre tiene caracteres de puntuación" + ": " + letra + "";
					
				}
				else if(Char.IsWhiteSpace(letra)) 
				{
                    return "El nombre tiene espacios";
					
				}
				else if (!Char.IsLetterOrDigit(letra))
				{
                    return "El nombre tiene caracteres no válidos" + ": " + letra + "";
					
					
				}	
				else if(letra>123)
				{
                    return "El nombre tiene caracteres no válidos" + ": " + letra + "";
					
				
				}
			}
            return "El nombre del archivo es correcto";
		}


		#region Windows Form Designer generated code
		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			this.components = new System.ComponentModel.Container();
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(FrmDetalleDoc));
			this.tabControl1 = new System.Windows.Forms.TabControl();
			this.tabPage1 = new System.Windows.Forms.TabPage();
			this.listView1 = new System.Windows.Forms.ListView();
			this.columnHeader1 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader2 = new System.Windows.Forms.ColumnHeader();
			this.imageList1 = new System.Windows.Forms.ImageList(this.components);
			this.tabPage2 = new System.Windows.Forms.TabPage();
			this.listView2 = new System.Windows.Forms.ListView();
			this.columnHeader3 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader4 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader5 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader6 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader7 = new System.Windows.Forms.ColumnHeader();
			this.tabPage3 = new System.Windows.Forms.TabPage();
			this.listView3 = new System.Windows.Forms.ListView();
			this.columnHeader8 = new System.Windows.Forms.ColumnHeader();
			this.columnHeader9 = new System.Windows.Forms.ColumnHeader();
			this.tabControl1.SuspendLayout();
			this.tabPage1.SuspendLayout();
			this.tabPage2.SuspendLayout();
			this.tabPage3.SuspendLayout();
			this.SuspendLayout();
			// 
			// tabControl1
			// 
			this.tabControl1.AccessibleDescription = resources.GetString("tabControl1.AccessibleDescription");
			this.tabControl1.AccessibleName = resources.GetString("tabControl1.AccessibleName");
			this.tabControl1.Alignment = ((System.Windows.Forms.TabAlignment)(resources.GetObject("tabControl1.Alignment")));
			this.tabControl1.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("tabControl1.Anchor")));
			this.tabControl1.Appearance = ((System.Windows.Forms.TabAppearance)(resources.GetObject("tabControl1.Appearance")));
			this.tabControl1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("tabControl1.BackgroundImage")));
			this.tabControl1.Controls.Add(this.tabPage1);
			this.tabControl1.Controls.Add(this.tabPage2);
			this.tabControl1.Controls.Add(this.tabPage3);
			this.tabControl1.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("tabControl1.Dock")));
			this.tabControl1.Enabled = ((bool)(resources.GetObject("tabControl1.Enabled")));
			this.tabControl1.Font = ((System.Drawing.Font)(resources.GetObject("tabControl1.Font")));
			this.tabControl1.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("tabControl1.ImeMode")));
			this.tabControl1.ItemSize = ((System.Drawing.Size)(resources.GetObject("tabControl1.ItemSize")));
			this.tabControl1.Location = ((System.Drawing.Point)(resources.GetObject("tabControl1.Location")));
			this.tabControl1.Name = "tabControl1";
			this.tabControl1.Padding = ((System.Drawing.Point)(resources.GetObject("tabControl1.Padding")));
			this.tabControl1.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("tabControl1.RightToLeft")));
			this.tabControl1.SelectedIndex = 0;
			this.tabControl1.ShowToolTips = ((bool)(resources.GetObject("tabControl1.ShowToolTips")));
			this.tabControl1.Size = ((System.Drawing.Size)(resources.GetObject("tabControl1.Size")));
			this.tabControl1.TabIndex = ((int)(resources.GetObject("tabControl1.TabIndex")));
			this.tabControl1.Text = resources.GetString("tabControl1.Text");
			this.tabControl1.Visible = ((bool)(resources.GetObject("tabControl1.Visible")));
			// 
			// tabPage1
			// 
			this.tabPage1.AccessibleDescription = resources.GetString("tabPage1.AccessibleDescription");
			this.tabPage1.AccessibleName = resources.GetString("tabPage1.AccessibleName");
			this.tabPage1.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("tabPage1.Anchor")));
			this.tabPage1.AutoScroll = ((bool)(resources.GetObject("tabPage1.AutoScroll")));
			this.tabPage1.AutoScrollMargin = ((System.Drawing.Size)(resources.GetObject("tabPage1.AutoScrollMargin")));
			this.tabPage1.AutoScrollMinSize = ((System.Drawing.Size)(resources.GetObject("tabPage1.AutoScrollMinSize")));
			this.tabPage1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("tabPage1.BackgroundImage")));
			this.tabPage1.Controls.Add(this.listView1);
			this.tabPage1.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("tabPage1.Dock")));
			this.tabPage1.Enabled = ((bool)(resources.GetObject("tabPage1.Enabled")));
			this.tabPage1.Font = ((System.Drawing.Font)(resources.GetObject("tabPage1.Font")));
			this.tabPage1.ImageIndex = ((int)(resources.GetObject("tabPage1.ImageIndex")));
			this.tabPage1.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("tabPage1.ImeMode")));
			this.tabPage1.Location = ((System.Drawing.Point)(resources.GetObject("tabPage1.Location")));
			this.tabPage1.Name = "tabPage1";
			this.tabPage1.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("tabPage1.RightToLeft")));
			this.tabPage1.Size = ((System.Drawing.Size)(resources.GetObject("tabPage1.Size")));
			this.tabPage1.TabIndex = ((int)(resources.GetObject("tabPage1.TabIndex")));
			this.tabPage1.Text = resources.GetString("tabPage1.Text");
			this.tabPage1.ToolTipText = resources.GetString("tabPage1.ToolTipText");
			this.tabPage1.Visible = ((bool)(resources.GetObject("tabPage1.Visible")));
			// 
			// listView1
			// 
			this.listView1.AccessibleDescription = resources.GetString("listView1.AccessibleDescription");
			this.listView1.AccessibleName = resources.GetString("listView1.AccessibleName");
			this.listView1.Alignment = ((System.Windows.Forms.ListViewAlignment)(resources.GetObject("listView1.Alignment")));
			this.listView1.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("listView1.Anchor")));
			this.listView1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("listView1.BackgroundImage")));
			this.listView1.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						this.columnHeader1,
																						this.columnHeader2});
			this.listView1.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("listView1.Dock")));
			this.listView1.Enabled = ((bool)(resources.GetObject("listView1.Enabled")));
			this.listView1.Font = ((System.Drawing.Font)(resources.GetObject("listView1.Font")));
			this.listView1.FullRowSelect = true;
			this.listView1.GridLines = true;
			this.listView1.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.None;
			this.listView1.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("listView1.ImeMode")));
			this.listView1.LabelWrap = ((bool)(resources.GetObject("listView1.LabelWrap")));
			this.listView1.LargeImageList = this.imageList1;
			this.listView1.Location = ((System.Drawing.Point)(resources.GetObject("listView1.Location")));
			this.listView1.Name = "listView1";
			this.listView1.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("listView1.RightToLeft")));
			this.listView1.Size = ((System.Drawing.Size)(resources.GetObject("listView1.Size")));
			this.listView1.SmallImageList = this.imageList1;
			this.listView1.TabIndex = ((int)(resources.GetObject("listView1.TabIndex")));
			this.listView1.Text = resources.GetString("listView1.Text");
			this.listView1.View = System.Windows.Forms.View.Details;
			this.listView1.Visible = ((bool)(resources.GetObject("listView1.Visible")));
			// 
			// columnHeader1
			// 
			this.columnHeader1.Text = resources.GetString("columnHeader1.Text");
			this.columnHeader1.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader1.TextAlign")));
			this.columnHeader1.Width = ((int)(resources.GetObject("columnHeader1.Width")));
			// 
			// columnHeader2
			// 
			this.columnHeader2.Text = resources.GetString("columnHeader2.Text");
			this.columnHeader2.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader2.TextAlign")));
			this.columnHeader2.Width = ((int)(resources.GetObject("columnHeader2.Width")));
			// 
			// imageList1
			// 
			this.imageList1.ImageSize = ((System.Drawing.Size)(resources.GetObject("imageList1.ImageSize")));
			this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
			this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
			// 
			// tabPage2
			// 
			this.tabPage2.AccessibleDescription = resources.GetString("tabPage2.AccessibleDescription");
			this.tabPage2.AccessibleName = resources.GetString("tabPage2.AccessibleName");
			this.tabPage2.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("tabPage2.Anchor")));
			this.tabPage2.AutoScroll = ((bool)(resources.GetObject("tabPage2.AutoScroll")));
			this.tabPage2.AutoScrollMargin = ((System.Drawing.Size)(resources.GetObject("tabPage2.AutoScrollMargin")));
			this.tabPage2.AutoScrollMinSize = ((System.Drawing.Size)(resources.GetObject("tabPage2.AutoScrollMinSize")));
			this.tabPage2.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("tabPage2.BackgroundImage")));
			this.tabPage2.Controls.Add(this.listView2);
			this.tabPage2.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("tabPage2.Dock")));
			this.tabPage2.Enabled = ((bool)(resources.GetObject("tabPage2.Enabled")));
			this.tabPage2.Font = ((System.Drawing.Font)(resources.GetObject("tabPage2.Font")));
			this.tabPage2.ImageIndex = ((int)(resources.GetObject("tabPage2.ImageIndex")));
			this.tabPage2.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("tabPage2.ImeMode")));
			this.tabPage2.Location = ((System.Drawing.Point)(resources.GetObject("tabPage2.Location")));
			this.tabPage2.Name = "tabPage2";
			this.tabPage2.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("tabPage2.RightToLeft")));
			this.tabPage2.Size = ((System.Drawing.Size)(resources.GetObject("tabPage2.Size")));
			this.tabPage2.TabIndex = ((int)(resources.GetObject("tabPage2.TabIndex")));
			this.tabPage2.Text = resources.GetString("tabPage2.Text");
			this.tabPage2.ToolTipText = resources.GetString("tabPage2.ToolTipText");
			this.tabPage2.Visible = ((bool)(resources.GetObject("tabPage2.Visible")));
			// 
			// listView2
			// 
			this.listView2.AccessibleDescription = resources.GetString("listView2.AccessibleDescription");
			this.listView2.AccessibleName = resources.GetString("listView2.AccessibleName");
			this.listView2.Alignment = ((System.Windows.Forms.ListViewAlignment)(resources.GetObject("listView2.Alignment")));
			this.listView2.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("listView2.Anchor")));
			this.listView2.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("listView2.BackgroundImage")));
			this.listView2.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						this.columnHeader3,
																						this.columnHeader4,
																						this.columnHeader5,
																						this.columnHeader6,
																						this.columnHeader7});
			this.listView2.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("listView2.Dock")));
			this.listView2.Enabled = ((bool)(resources.GetObject("listView2.Enabled")));
			this.listView2.Font = ((System.Drawing.Font)(resources.GetObject("listView2.Font")));
			this.listView2.FullRowSelect = true;
			this.listView2.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("listView2.ImeMode")));
			this.listView2.LabelWrap = ((bool)(resources.GetObject("listView2.LabelWrap")));
			this.listView2.LargeImageList = this.imageList1;
			this.listView2.Location = ((System.Drawing.Point)(resources.GetObject("listView2.Location")));
			this.listView2.Name = "listView2";
			this.listView2.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("listView2.RightToLeft")));
			this.listView2.Size = ((System.Drawing.Size)(resources.GetObject("listView2.Size")));
			this.listView2.SmallImageList = this.imageList1;
			this.listView2.TabIndex = ((int)(resources.GetObject("listView2.TabIndex")));
			this.listView2.Text = resources.GetString("listView2.Text");
			this.listView2.View = System.Windows.Forms.View.Details;
			this.listView2.Visible = ((bool)(resources.GetObject("listView2.Visible")));
			// 
			// columnHeader3
			// 
			this.columnHeader3.Text = resources.GetString("columnHeader3.Text");
			this.columnHeader3.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader3.TextAlign")));
			this.columnHeader3.Width = ((int)(resources.GetObject("columnHeader3.Width")));
			// 
			// columnHeader4
			// 
			this.columnHeader4.Text = resources.GetString("columnHeader4.Text");
			this.columnHeader4.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader4.TextAlign")));
			this.columnHeader4.Width = ((int)(resources.GetObject("columnHeader4.Width")));
			// 
			// columnHeader5
			// 
			this.columnHeader5.Text = resources.GetString("columnHeader5.Text");
			this.columnHeader5.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader5.TextAlign")));
			this.columnHeader5.Width = ((int)(resources.GetObject("columnHeader5.Width")));
			// 
			// columnHeader6
			// 
			this.columnHeader6.Text = resources.GetString("columnHeader6.Text");
			this.columnHeader6.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader6.TextAlign")));
			this.columnHeader6.Width = ((int)(resources.GetObject("columnHeader6.Width")));
			// 
			// columnHeader7
			// 
			this.columnHeader7.Text = resources.GetString("columnHeader7.Text");
			this.columnHeader7.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader7.TextAlign")));
			this.columnHeader7.Width = ((int)(resources.GetObject("columnHeader7.Width")));
			// 
			// tabPage3
			// 
			this.tabPage3.AccessibleDescription = resources.GetString("tabPage3.AccessibleDescription");
			this.tabPage3.AccessibleName = resources.GetString("tabPage3.AccessibleName");
			this.tabPage3.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("tabPage3.Anchor")));
			this.tabPage3.AutoScroll = ((bool)(resources.GetObject("tabPage3.AutoScroll")));
			this.tabPage3.AutoScrollMargin = ((System.Drawing.Size)(resources.GetObject("tabPage3.AutoScrollMargin")));
			this.tabPage3.AutoScrollMinSize = ((System.Drawing.Size)(resources.GetObject("tabPage3.AutoScrollMinSize")));
			this.tabPage3.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("tabPage3.BackgroundImage")));
			this.tabPage3.Controls.Add(this.listView3);
			this.tabPage3.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("tabPage3.Dock")));
			this.tabPage3.Enabled = ((bool)(resources.GetObject("tabPage3.Enabled")));
			this.tabPage3.Font = ((System.Drawing.Font)(resources.GetObject("tabPage3.Font")));
			this.tabPage3.ImageIndex = ((int)(resources.GetObject("tabPage3.ImageIndex")));
			this.tabPage3.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("tabPage3.ImeMode")));
			this.tabPage3.Location = ((System.Drawing.Point)(resources.GetObject("tabPage3.Location")));
			this.tabPage3.Name = "tabPage3";
			this.tabPage3.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("tabPage3.RightToLeft")));
			this.tabPage3.Size = ((System.Drawing.Size)(resources.GetObject("tabPage3.Size")));
			this.tabPage3.TabIndex = ((int)(resources.GetObject("tabPage3.TabIndex")));
			this.tabPage3.Text = resources.GetString("tabPage3.Text");
			this.tabPage3.ToolTipText = resources.GetString("tabPage3.ToolTipText");
			this.tabPage3.Visible = ((bool)(resources.GetObject("tabPage3.Visible")));
			// 
			// listView3
			// 
			this.listView3.AccessibleDescription = resources.GetString("listView3.AccessibleDescription");
			this.listView3.AccessibleName = resources.GetString("listView3.AccessibleName");
			this.listView3.Alignment = ((System.Windows.Forms.ListViewAlignment)(resources.GetObject("listView3.Alignment")));
			this.listView3.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("listView3.Anchor")));
			this.listView3.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("listView3.BackgroundImage")));
			this.listView3.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
																						this.columnHeader8,
																						this.columnHeader9});
			this.listView3.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("listView3.Dock")));
			this.listView3.Enabled = ((bool)(resources.GetObject("listView3.Enabled")));
			this.listView3.Font = ((System.Drawing.Font)(resources.GetObject("listView3.Font")));
			this.listView3.FullRowSelect = true;
			this.listView3.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("listView3.ImeMode")));
			this.listView3.LabelWrap = ((bool)(resources.GetObject("listView3.LabelWrap")));
			this.listView3.LargeImageList = this.imageList1;
			this.listView3.Location = ((System.Drawing.Point)(resources.GetObject("listView3.Location")));
			this.listView3.Name = "listView3";
			this.listView3.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("listView3.RightToLeft")));
			this.listView3.Size = ((System.Drawing.Size)(resources.GetObject("listView3.Size")));
			this.listView3.SmallImageList = this.imageList1;
			this.listView3.TabIndex = ((int)(resources.GetObject("listView3.TabIndex")));
			this.listView3.Text = resources.GetString("listView3.Text");
			this.listView3.View = System.Windows.Forms.View.Details;
			this.listView3.Visible = ((bool)(resources.GetObject("listView3.Visible")));
			// 
			// columnHeader8
			// 
			this.columnHeader8.Text = resources.GetString("columnHeader8.Text");
			this.columnHeader8.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader8.TextAlign")));
			this.columnHeader8.Width = ((int)(resources.GetObject("columnHeader8.Width")));
			// 
			// columnHeader9
			// 
			this.columnHeader9.Text = resources.GetString("columnHeader9.Text");
			this.columnHeader9.TextAlign = ((System.Windows.Forms.HorizontalAlignment)(resources.GetObject("columnHeader9.TextAlign")));
			this.columnHeader9.Width = ((int)(resources.GetObject("columnHeader9.Width")));
			// 
			// FrmDetalleDoc
			// 
			this.AccessibleDescription = resources.GetString("$this.AccessibleDescription");
			this.AccessibleName = resources.GetString("$this.AccessibleName");
			this.AutoScaleBaseSize = ((System.Drawing.Size)(resources.GetObject("$this.AutoScaleBaseSize")));
			this.AutoScroll = ((bool)(resources.GetObject("$this.AutoScroll")));
			this.AutoScrollMargin = ((System.Drawing.Size)(resources.GetObject("$this.AutoScrollMargin")));
			this.AutoScrollMinSize = ((System.Drawing.Size)(resources.GetObject("$this.AutoScrollMinSize")));
			this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
			this.ClientSize = ((System.Drawing.Size)(resources.GetObject("$this.ClientSize")));
			this.Controls.Add(this.tabControl1);
			this.Enabled = ((bool)(resources.GetObject("$this.Enabled")));
			this.Font = ((System.Drawing.Font)(resources.GetObject("$this.Font")));
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("$this.ImeMode")));
			this.Location = ((System.Drawing.Point)(resources.GetObject("$this.Location")));
			this.MaximizeBox = false;
			this.MaximumSize = ((System.Drawing.Size)(resources.GetObject("$this.MaximumSize")));
			this.MinimizeBox = false;
			this.MinimumSize = ((System.Drawing.Size)(resources.GetObject("$this.MinimumSize")));
			this.Name = "FrmDetalleDoc";
			this.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("$this.RightToLeft")));
			this.ShowInTaskbar = false;
			this.StartPosition = ((System.Windows.Forms.FormStartPosition)(resources.GetObject("$this.StartPosition")));
			this.Text = resources.GetString("$this.Text");
			this.tabControl1.ResumeLayout(false);
			this.tabPage1.ResumeLayout(false);
			this.tabPage2.ResumeLayout(false);
			this.tabPage3.ResumeLayout(false);
			this.ResumeLayout(false);

		}
		#endregion
	}
}
