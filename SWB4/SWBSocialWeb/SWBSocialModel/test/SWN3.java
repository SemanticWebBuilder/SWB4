
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jorge.jimenez
 */
public class SWN3 {
    
    
    private String pathToSWN = "c://SocialTmp/SentiWordNet20130122.txt";
    private HashMap<String, Double> _dict;

    public SWN3(){
        System.out.println("Entra a SWN3");
        _dict = new HashMap<String, Double>();
        HashMap<String, Vector<Double>> _temp = new HashMap<String, Vector<Double>>();
        try{
            BufferedReader csv =  new BufferedReader(new FileReader(pathToSWN));
            int lineNumber = 0;
            String line = "";           
            while((line = csv.readLine()) != null)
            {
                lineNumber++;
                if (!line.trim().startsWith("#")) 
                {
                    String[] data = line.split("\t");
                    /*
                    for(int i=0;i<data.length;i++)
                    {
                        System.out.println("data["+i+"]:"+data[i]);
                    }*/
                    // Is it a valid line? Otherwise, through exception.
                    if (data.length != 6) {
                        throw new IllegalArgumentException(
                                "Incorrect tabulation format in file, line: "
                                + lineNumber);
                    }


                    Double score = Double.parseDouble(data[2])-Double.parseDouble(data[3]);
                    String[] words = data[4].split(" ");
                    for(String w:words)
                    {
                        String[] w_n = w.split("#");
                        w_n[0] += "#"+data[0];
                        int index = Integer.parseInt(w_n[1])-1;
                        if(_temp.containsKey(w_n[0]))
                        {
                            Vector<Double> v = _temp.get(w_n[0]);
                            if(index>v.size())
                                for(int i = v.size();i<index; i++)
                                    v.add(0.0);
                            v.add(index, score);
                            _temp.put(w_n[0], v);
                        }
                        else
                        {
                            Vector<Double> v = new Vector<Double>();
                            for(int i = 0;i<index; i++)
                                v.add(0.0);
                            v.add(index, score);
                            _temp.put(w_n[0], v);
                        }
                    }
                }
            }
            Set<String> temp = _temp.keySet();
            for (Iterator<String> iterator = temp.iterator(); iterator.hasNext();) {
                String word = (String) iterator.next();
                Vector<Double> v = _temp.get(word);
                double score = 0.0;
                double sum = 0.0;
                for(int i = 0; i < v.size(); i++)
                    score += ((double)1/(double)(i+1))*v.get(i);
                for(int i = 1; i<=v.size(); i++)
                    sum += (double)1/(double)i;
                score /= sum;
                
                _dict.put(word, Double.valueOf(score));
                /*
                String sent = "";               
                if(score>=0.75)
                    sent = "strong_positive";
                else
                if(score > 0.25 && score<=0.5)
                    sent = "positive";
                else
                if(score > 0 && score>=0.25)
                    sent = "weak_positive";
                else
                if(score < 0 && score>=-0.25)
                    sent = "weak_negative";
                else
                if(score < -0.25 && score>=-0.5)
                    sent = "negative";
                else
                if(score<=-0.75)
                    sent = "strong_negative";
                _dict.put(word, sent);
                * */
            }
        }
        catch(Exception e){e.printStackTrace();}        
    }

    public Double extract(String word)
    {
        Double total = new Double(0);
        if(_dict.get(word+"#n") != null)
             total = _dict.get(word+"#n").doubleValue() + total;
        if(_dict.get(word+"#a") != null)
            total = _dict.get(word+"#a").doubleValue() + total;
        if(_dict.get(word+"#r") != null)
            total = _dict.get(word+"#r").doubleValue() + total;
        if(_dict.get(word+"#v") != null)
            total = _dict.get(word+"#v").doubleValue() + total;
        return total;
    }

    public String classifyText(String msg){
        //String msg="Top bargain destinations for Spring 2014San AntonioForget spring training—this season, try the city of San Antonio travel deals. The city's easy-to-find, easy-to-use SAVE deals offer discounts on hotel stays, activities, dining, and more. And when you combine the easy savings with well-connected air service from all over the U.S. and a ton of things to do, you've got bargain gold.The city's SAVE (San Antonio Vacation Experience) deals include big hotels, little inns, B&Bs, and resorts. Find deals like 15 to 20 percent off rates at the Holiday Inn San Antonio Riverwalk and 25 percent off rooms at the Hotel Valencia Riverwalk, which offers rates from $114 per night.Saving is only part of the equation in San Antonio this spring. Fiesta San Antonio runs from April 10 to 27 and the food festival Culinaria kicks off May 14. The San Antonio Zoo will open a new carousel in March to celebrate its 100th birthday, and in May, SeaWorld will debut a new 13,500-square-foot aviary full of tropical birds.";
        
        String[] words = msg.split("\\s+"); 
        double totalScore = 0, averageScore;
        for(String word : words) {
            word = word.replaceAll("([^a-zA-Z\\s])", "");
            if (extract(word) == null)
                continue;
            totalScore += extract(word);
        }
        averageScore = totalScore;
        
        System.out.println("averageScore:"+averageScore);
        
        if(averageScore>=0.75)
            return "Strong positive";
        else if(averageScore > 0.25 && averageScore<0.5)
            return  "weak_positive";
        else if(averageScore>=0.5)
            return  "positive";
        else if(averageScore < 0 && averageScore>=-0.25)
            return "weak_negative";
        else if(averageScore < -0.25 && averageScore>=-0.5)
            return "negative";
        else if(averageScore<=-0.75)
            return "strong_negative";
        return "neutral";
    }

    public static void main(String[] args) {
        SWN3 swn3=new SWN3();
        //String msg="Top bargain destinations for Spring 2014San AntonioForget spring training—this season, try the city of San Antonio travel deals. The city's easy-to-find, easy-to-use SAVE deals offer discounts on hotel stays, activities, dining, and more. And when you combine the easy savings with well-connected air service from all over the U.S. and a ton of things to do, you've got bargain gold.The city's SAVE (San Antonio Vacation Experience) deals include big hotels, little inns, B&Bs, and resorts. Find deals like 15 to 20 percent off rates at the Holiday Inn San Antonio Riverwalk and 25 percent off rooms at the Hotel Valencia Riverwalk, which offers rates from $114 per night.Saving is only part of the equation in San Antonio this spring. Fiesta San Antonio runs from April 10 to 27 and the food festival Culinaria kicks off May 14. The San Antonio Zoo will open a new carousel in March to celebrate its 100th birthday, and in May, SeaWorld will debut a new 13,500-square-foot aviary full of tropical birds.";
        //String msg="Update on the October event - I have also invited friends from Lincoln in Buenos Aires to join us on this trip. I have reserved a block of 12 rooms at the Holiday Inn Express near Balloon Fiesta Park. There is a free shuttle from an amusement park close by, so no one has to pay for parking (or struggle to get into or out of the park!) There is plenty to do. There are a couple of art museums (both Native American and classical), as well as shopping, hiking, and (my personal favorite) the New Mexico Museum of Natural History. My brother, Mike (Class of '75) is putting together a group tour for whomever wants to go on Friday the 10th. This is fun for parents and kids.Since we are working on this early, there is time to plan for it. Hotel rooms are about $125 per night, starting Thursday the 9th. I will need to know if you are interested no later than the first week of September< so that the hotel can sell any unused rooms and not charge me for them>Espero verlos alli!";
        String msg="Hello,I am 19 years old,I currently work weekends with autistic children, where I help support children aged 5-13 with autism and other learning difficulties. Along side this I am completing an Art Foundation course this June- Afterwards I hope to go onto working with children and becoming an Art Therapist. In the past I have done sign language for both children and the elderly and signed at the Congress Theatre. Sometimes I also help care for a disabled girl with a major disability so much so that she is wheelchair bound and unable to do anything for herself. This has helped me realise just how much working with people, a dream is to me.I am one of 6 children, and despite being only 19, have grown up quickly. I have always been around children, and love it.I babysit for 5 different families, and have recently been made an auntie. Children have, and I am sure, always will be a major part of my life.I have also had experience in retail (Monsoon, Marks and Spencers) and waitressing in The Lamb Inn in Old Town, and Fiesta Bistro in Eastbourne. Thank you in advance for reading this, and I look forward to hearing from you if you've any help or offers,Lydia Harris07784467657";
        String result=swn3.classifyText(msg);
        System.out.println("result:"+result);
    }
    
}
