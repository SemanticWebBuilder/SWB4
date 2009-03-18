using System;

namespace TSWizards
{
	/// <summary>
	///		The possible layouts for the wizard
	/// </summary>
	public enum PageLayout
	{
		/// <summary>
		///		A thin white strip along the top with a smallish bitmap
		///		at the right edge of the strip
		/// </summary>
		InteriorPage,
		/// <summary>
		///		A white background wizard with a 
		///		large bitmap on the left side
		/// </summary>
		ExteriorPage,
		/// <summary>
		///		No layout changes will take place
		/// </summary>
		None
	}
}
