package org.semanticwb.social;

   /**
   * Interface que se les agrega a las redes sociales a las cuales se pueda leer el klout de sus usuarios. Segun Klout, en este momento solo soporta twitter y google+ 
   */
public interface Kloutable extends org.semanticwb.social.base.KloutableBase
{
    
    /**
     *
     * @param twitterUserID
     * @return
     */
    public double getUserKlout(String twitterUserID);
}
