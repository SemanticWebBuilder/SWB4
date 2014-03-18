package org.semanticwb.bsc;

   /**
   * Interfaz que define las relaciones causa - efecto entre Objetivos y Temas. Se utiliza para mostrarse en el Mapa Estrategico 
   */
public interface Causal extends org.semanticwb.bsc.base.CausalBase
{
    public boolean isValid();
}
