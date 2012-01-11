package org.semanticwb.portal.resources.sem;

import java.util.Comparator;


public class AudioFile extends org.semanticwb.portal.resources.sem.base.AudioFileBase 
{
    public AudioFile(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
     public static class SortByReviews implements Comparator<AudioFile>{
        @Override
        public int compare(AudioFile audio1, AudioFile audio2) {
            return new Long(audio1.getReviews()).compareTo(new Long(audio2.getReviews()));
        }
    }
     
    public static class SortByRanking implements Comparator<AudioFile>{
        @Override
        public int compare(AudioFile audio1, AudioFile audio2) {
            return new Double(audio1.getRank()).compareTo(new Double(audio2.getRank()));
        }
    } 
}
