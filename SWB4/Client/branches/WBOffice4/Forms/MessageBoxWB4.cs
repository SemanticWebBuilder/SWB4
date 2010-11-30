using System;
using System.Drawing;
using System.Collections;
using System.ComponentModel;
using System.Windows.Forms;

namespace WBOffice4.Forms
{
	/// <summary>
	/// Resultado del cuadro de diálogo
	/// </summary>
	public enum DialogResultEx
	{
		/// <summary>
		/// Si sólo uno
		/// </summary>
		YES,
		/// <summary>
		/// Si a todos
		/// </summary>
		YESALL,
		/// <summary>
		/// Cancela la ejecución
		/// </summary>
		CANCEL,		
	};
		
	/// <summary>
	/// Descripción breve de MessageBoxWB3.
	/// </summary>
	public class MessageBoxWB4 : System.Windows.Forms.Form
	{
		private System.Windows.Forms.Label label1;
		DialogResultEx res=DialogResultEx.YES;
		private System.Windows.Forms.Button buttonYesAll;
		private System.Windows.Forms.Button buttonYes;
		private System.Windows.Forms.Button buttonCancel;
		private System.Windows.Forms.PictureBox pictureBox1; System.ComponentModel.Container components = null;
		/// <summary>
		/// Función para mostrar mensaje de dialogo
		/// </summary>
		/// <param name="title">Título de la ventana</param>
		/// <param name="message">Mensaje a despluegar</param>
		/// <param name="form">Forma de la que depende</param>
		/// <returns>Ok, Aplicar a todos, Cancelar</returns>
		public static DialogResultEx Show(String title,string message,Form form)
		{
			MessageBoxWB4 frm=new MessageBoxWB4(title,message);
			frm.ShowDialog(frm);						
			return frm.res;
		}
		/// <summary>
		/// Función para mostrar mensaje de dialogo
		/// </summary>
		/// <param name="title">Título de la ventana</param>
		/// <param name="message">Mensaje a despluegar</param>		
		/// <returns>Ok, Aplicar a todos, Cancelar</returns>
		public static DialogResultEx Show(String title,string message)
		{
			MessageBoxWB4 frm=new MessageBoxWB4(title,message);
			frm.ShowDialog();
			return frm.res;
		}
		/// <summary>
		/// Constructor de clase de mensaje
		/// </summary>
		/// <param name="title">Título de la ventana</param>
		/// <param name="message">Mensaje a despluegar</param>
		private MessageBoxWB4(string title,string message)
		{
			
			//
			// Necesario para admitir el Diseñador de Windows Forms
			//
			InitializeComponent();

			this.label1.Text=message;
			this.Text=title;
		}

		/// <summary>
		/// Limpiar los recursos que se estén utilizando.
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

		#region Código generado por el Diseñador de Windows Forms
		/// <summary>
		/// Método necesario para admitir el Diseñador. No se puede modificar
		/// el contenido del método con el editor de código.
		/// </summary>
		private void InitializeComponent()
		{
			System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(MessageBoxWB4));
			this.buttonYes = new System.Windows.Forms.Button();
			this.buttonYesAll = new System.Windows.Forms.Button();
			this.label1 = new System.Windows.Forms.Label();
			this.buttonCancel = new System.Windows.Forms.Button();
			this.pictureBox1 = new System.Windows.Forms.PictureBox();
			this.SuspendLayout();
			// 
			// buttonYes
			// 
			this.buttonYes.AccessibleDescription = resources.GetString("buttonYes.AccessibleDescription");
			this.buttonYes.AccessibleName = resources.GetString("buttonYes.AccessibleName");
			this.buttonYes.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("buttonYes.Anchor")));
			this.buttonYes.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("buttonYes.BackgroundImage")));
			this.buttonYes.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("buttonYes.Dock")));
			this.buttonYes.Enabled = ((bool)(resources.GetObject("buttonYes.Enabled")));
			this.buttonYes.FlatStyle = ((System.Windows.Forms.FlatStyle)(resources.GetObject("buttonYes.FlatStyle")));
			this.buttonYes.Font = ((System.Drawing.Font)(resources.GetObject("buttonYes.Font")));
			this.buttonYes.Image = ((System.Drawing.Image)(resources.GetObject("buttonYes.Image")));
			this.buttonYes.ImageAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonYes.ImageAlign")));
			this.buttonYes.ImageIndex = ((int)(resources.GetObject("buttonYes.ImageIndex")));
			this.buttonYes.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("buttonYes.ImeMode")));
			this.buttonYes.Location = ((System.Drawing.Point)(resources.GetObject("buttonYes.Location")));
			this.buttonYes.Name = "buttonYes";
			this.buttonYes.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("buttonYes.RightToLeft")));
			this.buttonYes.Size = ((System.Drawing.Size)(resources.GetObject("buttonYes.Size")));
			this.buttonYes.TabIndex = ((int)(resources.GetObject("buttonYes.TabIndex")));
			this.buttonYes.Text = resources.GetString("buttonYes.Text");
			this.buttonYes.TextAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonYes.TextAlign")));
			this.buttonYes.Visible = ((bool)(resources.GetObject("buttonYes.Visible")));
			this.buttonYes.Click += new System.EventHandler(this.buttonYes_Click);
			// 
			// buttonYesAll
			// 
			this.buttonYesAll.AccessibleDescription = resources.GetString("buttonYesAll.AccessibleDescription");
			this.buttonYesAll.AccessibleName = resources.GetString("buttonYesAll.AccessibleName");
			this.buttonYesAll.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("buttonYesAll.Anchor")));
			this.buttonYesAll.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("buttonYesAll.BackgroundImage")));
			this.buttonYesAll.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("buttonYesAll.Dock")));
			this.buttonYesAll.Enabled = ((bool)(resources.GetObject("buttonYesAll.Enabled")));
			this.buttonYesAll.FlatStyle = ((System.Windows.Forms.FlatStyle)(resources.GetObject("buttonYesAll.FlatStyle")));
			this.buttonYesAll.Font = ((System.Drawing.Font)(resources.GetObject("buttonYesAll.Font")));
			this.buttonYesAll.Image = ((System.Drawing.Image)(resources.GetObject("buttonYesAll.Image")));
			this.buttonYesAll.ImageAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonYesAll.ImageAlign")));
			this.buttonYesAll.ImageIndex = ((int)(resources.GetObject("buttonYesAll.ImageIndex")));
			this.buttonYesAll.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("buttonYesAll.ImeMode")));
			this.buttonYesAll.Location = ((System.Drawing.Point)(resources.GetObject("buttonYesAll.Location")));
			this.buttonYesAll.Name = "buttonYesAll";
			this.buttonYesAll.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("buttonYesAll.RightToLeft")));
			this.buttonYesAll.Size = ((System.Drawing.Size)(resources.GetObject("buttonYesAll.Size")));
			this.buttonYesAll.TabIndex = ((int)(resources.GetObject("buttonYesAll.TabIndex")));
			this.buttonYesAll.Text = resources.GetString("buttonYesAll.Text");
			this.buttonYesAll.TextAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonYesAll.TextAlign")));
			this.buttonYesAll.Visible = ((bool)(resources.GetObject("buttonYesAll.Visible")));
			this.buttonYesAll.Click += new System.EventHandler(this.buttonYesAll_Click);
			// 
			// label1
			// 
			this.label1.AccessibleDescription = resources.GetString("label1.AccessibleDescription");
			this.label1.AccessibleName = resources.GetString("label1.AccessibleName");
			this.label1.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("label1.Anchor")));
			this.label1.AutoSize = ((bool)(resources.GetObject("label1.AutoSize")));
			this.label1.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("label1.Dock")));
			this.label1.Enabled = ((bool)(resources.GetObject("label1.Enabled")));
			this.label1.Font = ((System.Drawing.Font)(resources.GetObject("label1.Font")));
			this.label1.Image = ((System.Drawing.Image)(resources.GetObject("label1.Image")));
			this.label1.ImageAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("label1.ImageAlign")));
			this.label1.ImageIndex = ((int)(resources.GetObject("label1.ImageIndex")));
			this.label1.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("label1.ImeMode")));
			this.label1.Location = ((System.Drawing.Point)(resources.GetObject("label1.Location")));
			this.label1.Name = "label1";
			this.label1.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("label1.RightToLeft")));
			this.label1.Size = ((System.Drawing.Size)(resources.GetObject("label1.Size")));
			this.label1.TabIndex = ((int)(resources.GetObject("label1.TabIndex")));
			this.label1.Text = resources.GetString("label1.Text");
			this.label1.TextAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("label1.TextAlign")));
			this.label1.Visible = ((bool)(resources.GetObject("label1.Visible")));
			// 
			// buttonCancel
			// 
			this.buttonCancel.AccessibleDescription = resources.GetString("buttonCancel.AccessibleDescription");
			this.buttonCancel.AccessibleName = resources.GetString("buttonCancel.AccessibleName");
			this.buttonCancel.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("buttonCancel.Anchor")));
			this.buttonCancel.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("buttonCancel.BackgroundImage")));
			this.buttonCancel.DialogResult = System.Windows.Forms.DialogResult.Cancel;
			this.buttonCancel.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("buttonCancel.Dock")));
			this.buttonCancel.Enabled = ((bool)(resources.GetObject("buttonCancel.Enabled")));
			this.buttonCancel.FlatStyle = ((System.Windows.Forms.FlatStyle)(resources.GetObject("buttonCancel.FlatStyle")));
			this.buttonCancel.Font = ((System.Drawing.Font)(resources.GetObject("buttonCancel.Font")));
			this.buttonCancel.Image = ((System.Drawing.Image)(resources.GetObject("buttonCancel.Image")));
			this.buttonCancel.ImageAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonCancel.ImageAlign")));
			this.buttonCancel.ImageIndex = ((int)(resources.GetObject("buttonCancel.ImageIndex")));
			this.buttonCancel.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("buttonCancel.ImeMode")));
			this.buttonCancel.Location = ((System.Drawing.Point)(resources.GetObject("buttonCancel.Location")));
			this.buttonCancel.Name = "buttonCancel";
			this.buttonCancel.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("buttonCancel.RightToLeft")));
			this.buttonCancel.Size = ((System.Drawing.Size)(resources.GetObject("buttonCancel.Size")));
			this.buttonCancel.TabIndex = ((int)(resources.GetObject("buttonCancel.TabIndex")));
			this.buttonCancel.Text = resources.GetString("buttonCancel.Text");
			this.buttonCancel.TextAlign = ((System.Drawing.ContentAlignment)(resources.GetObject("buttonCancel.TextAlign")));
			this.buttonCancel.Visible = ((bool)(resources.GetObject("buttonCancel.Visible")));
			this.buttonCancel.Click += new System.EventHandler(this.buttonCancel_Click);
			// 
			// pictureBox1
			// 
			this.pictureBox1.AccessibleDescription = resources.GetString("pictureBox1.AccessibleDescription");
			this.pictureBox1.AccessibleName = resources.GetString("pictureBox1.AccessibleName");
			this.pictureBox1.Anchor = ((System.Windows.Forms.AnchorStyles)(resources.GetObject("pictureBox1.Anchor")));
			this.pictureBox1.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("pictureBox1.BackgroundImage")));
			this.pictureBox1.Dock = ((System.Windows.Forms.DockStyle)(resources.GetObject("pictureBox1.Dock")));
			this.pictureBox1.Enabled = ((bool)(resources.GetObject("pictureBox1.Enabled")));
			this.pictureBox1.Font = ((System.Drawing.Font)(resources.GetObject("pictureBox1.Font")));
			this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
			this.pictureBox1.ImeMode = ((System.Windows.Forms.ImeMode)(resources.GetObject("pictureBox1.ImeMode")));
			this.pictureBox1.Location = ((System.Drawing.Point)(resources.GetObject("pictureBox1.Location")));
			this.pictureBox1.Name = "pictureBox1";
			this.pictureBox1.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("pictureBox1.RightToLeft")));
			this.pictureBox1.Size = ((System.Drawing.Size)(resources.GetObject("pictureBox1.Size")));
			this.pictureBox1.SizeMode = ((System.Windows.Forms.PictureBoxSizeMode)(resources.GetObject("pictureBox1.SizeMode")));
			this.pictureBox1.TabIndex = ((int)(resources.GetObject("pictureBox1.TabIndex")));
			this.pictureBox1.TabStop = false;
			this.pictureBox1.Text = resources.GetString("pictureBox1.Text");
			this.pictureBox1.Visible = ((bool)(resources.GetObject("pictureBox1.Visible")));
			// 
			// MessageBoxWB3
			// 
			this.AcceptButton = this.buttonYes;
			this.AccessibleDescription = resources.GetString("$this.AccessibleDescription");
			this.AccessibleName = resources.GetString("$this.AccessibleName");
			this.AutoScaleBaseSize = ((System.Drawing.Size)(resources.GetObject("$this.AutoScaleBaseSize")));
			this.AutoScroll = ((bool)(resources.GetObject("$this.AutoScroll")));
			this.AutoScrollMargin = ((System.Drawing.Size)(resources.GetObject("$this.AutoScrollMargin")));
			this.AutoScrollMinSize = ((System.Drawing.Size)(resources.GetObject("$this.AutoScrollMinSize")));
			this.BackgroundImage = ((System.Drawing.Image)(resources.GetObject("$this.BackgroundImage")));
			this.CancelButton = this.buttonCancel;
			this.ClientSize = ((System.Drawing.Size)(resources.GetObject("$this.ClientSize")));
			this.ControlBox = false;
			this.Controls.Add(this.pictureBox1);
			this.Controls.Add(this.buttonCancel);
			this.Controls.Add(this.label1);
			this.Controls.Add(this.buttonYesAll);
			this.Controls.Add(this.buttonYes);
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
			this.Name = "MessageBoxWB3";
			this.RightToLeft = ((System.Windows.Forms.RightToLeft)(resources.GetObject("$this.RightToLeft")));
			this.ShowInTaskbar = false;
			this.StartPosition = ((System.Windows.Forms.FormStartPosition)(resources.GetObject("$this.StartPosition")));
			this.Text = resources.GetString("$this.Text");
			this.ResumeLayout(false);

		}
		#endregion


		private void buttonYesAll_Click(object sender, System.EventArgs e)
		{
			this.res=DialogResultEx.YESALL;
			this.Visible=false;
			this.Close();
			
		}

		private void buttonYes_Click(object sender, System.EventArgs e)
		{
			this.res=DialogResultEx.YES;			
			this.Close();
		}	

		private void buttonCancel_Click(object sender, System.EventArgs e)
		{
			this.res=DialogResultEx.CANCEL;			
			this.Close();
		}
	}
}
