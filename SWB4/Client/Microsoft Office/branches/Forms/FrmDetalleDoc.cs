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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(FrmDetalleDoc));
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
            this.tabControl1.Controls.Add(this.tabPage1);
            this.tabControl1.Controls.Add(this.tabPage2);
            this.tabControl1.Controls.Add(this.tabPage3);
            resources.ApplyResources(this.tabControl1, "tabControl1");
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            // 
            // tabPage1
            // 
            this.tabPage1.Controls.Add(this.listView1);
            resources.ApplyResources(this.tabPage1, "tabPage1");
            this.tabPage1.Name = "tabPage1";
            // 
            // listView1
            // 
            this.listView1.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader1,
            this.columnHeader2});
            resources.ApplyResources(this.listView1, "listView1");
            this.listView1.FullRowSelect = true;
            this.listView1.GridLines = true;
            this.listView1.HeaderStyle = System.Windows.Forms.ColumnHeaderStyle.None;
            this.listView1.LargeImageList = this.imageList1;
            this.listView1.Name = "listView1";
            this.listView1.SmallImageList = this.imageList1;
            this.listView1.UseCompatibleStateImageBehavior = false;
            this.listView1.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader1
            // 
            resources.ApplyResources(this.columnHeader1, "columnHeader1");
            // 
            // columnHeader2
            // 
            resources.ApplyResources(this.columnHeader2, "columnHeader2");
            // 
            // imageList1
            // 
            this.imageList1.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imageList1.ImageStream")));
            this.imageList1.TransparentColor = System.Drawing.Color.Transparent;
            this.imageList1.Images.SetKeyName(0, "");
            this.imageList1.Images.SetKeyName(1, "");
            // 
            // tabPage2
            // 
            this.tabPage2.Controls.Add(this.listView2);
            resources.ApplyResources(this.tabPage2, "tabPage2");
            this.tabPage2.Name = "tabPage2";
            // 
            // listView2
            // 
            this.listView2.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader3,
            this.columnHeader4,
            this.columnHeader5,
            this.columnHeader6,
            this.columnHeader7});
            resources.ApplyResources(this.listView2, "listView2");
            this.listView2.FullRowSelect = true;
            this.listView2.LargeImageList = this.imageList1;
            this.listView2.Name = "listView2";
            this.listView2.SmallImageList = this.imageList1;
            this.listView2.UseCompatibleStateImageBehavior = false;
            this.listView2.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader3
            // 
            resources.ApplyResources(this.columnHeader3, "columnHeader3");
            // 
            // columnHeader4
            // 
            resources.ApplyResources(this.columnHeader4, "columnHeader4");
            // 
            // columnHeader5
            // 
            resources.ApplyResources(this.columnHeader5, "columnHeader5");
            // 
            // columnHeader6
            // 
            resources.ApplyResources(this.columnHeader6, "columnHeader6");
            // 
            // columnHeader7
            // 
            resources.ApplyResources(this.columnHeader7, "columnHeader7");
            // 
            // tabPage3
            // 
            this.tabPage3.Controls.Add(this.listView3);
            resources.ApplyResources(this.tabPage3, "tabPage3");
            this.tabPage3.Name = "tabPage3";
            // 
            // listView3
            // 
            this.listView3.Columns.AddRange(new System.Windows.Forms.ColumnHeader[] {
            this.columnHeader8,
            this.columnHeader9});
            resources.ApplyResources(this.listView3, "listView3");
            this.listView3.FullRowSelect = true;
            this.listView3.LargeImageList = this.imageList1;
            this.listView3.Name = "listView3";
            this.listView3.SmallImageList = this.imageList1;
            this.listView3.UseCompatibleStateImageBehavior = false;
            this.listView3.View = System.Windows.Forms.View.Details;
            // 
            // columnHeader8
            // 
            resources.ApplyResources(this.columnHeader8, "columnHeader8");
            // 
            // columnHeader9
            // 
            resources.ApplyResources(this.columnHeader9, "columnHeader9");
            // 
            // FrmDetalleDoc
            // 
            resources.ApplyResources(this, "$this");
            this.Controls.Add(this.tabControl1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "FrmDetalleDoc";
            this.ShowInTaskbar = false;
            this.tabControl1.ResumeLayout(false);
            this.tabPage1.ResumeLayout(false);
            this.tabPage2.ResumeLayout(false);
            this.tabPage3.ResumeLayout(false);
            this.ResumeLayout(false);

		}
		#endregion
	}
}
