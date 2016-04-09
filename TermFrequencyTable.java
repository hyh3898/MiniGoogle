//Yehui Huang
//cs112
//11/25/2014


public class TermFrequencyTable {
    
    public double AB = 0;
    public double AA = 0;
    public double BB = 0;
    public class Node {
        String term;
        int counter1;
        int counter2;
        int[] termFreq = new int[2];
        Node next;
        
        public Node(String term, int i, Node p) {
          this.term = term;
          this.termFreq[i] = 1;
          this.next = p;
        }
        
        public Node(String term, Node p) {
          this.term = term;
          this.next = p;
        }
        
    }
   
    private final int SIZE = 2503;
    
    private Node [] T = new Node[SIZE];
    
    private static int hash(String x, int M) {
        char ch[];
        ch = x.toCharArray();
        int xlength = x.length();
        
        int i, sum;
        for (sum=0, i=0; i<x.length(); i++)
            sum+=ch[i];
        return sum % M;
    }
    
    //insert
    //***********************************************************
    public void insert(String term, int docNum) {
      String doc = term;
      String[] docArr = doc.split(" "); 


      //make all the words to lower case
//      for (int z = 0; z<docArr.length; ++z) {
//        docArr[z] = docArr[z].toLowerCase();
//      }
      
      for (int i = 0; i<docArr.length; ++i) {
        //for (int z = 0; z<blackList.length;++z) {
       // if (docArr[i].equals("the") || docArr[i].equals("and")) {
          //if (!docArr[i].equals(blackList[z])){
           T[hash(docArr[i], SIZE)] = insertHelper(docArr[i], docNum, T[hash(docArr[i], SIZE)]);//T[hash(docArr[i], SIZE)] = insertHelper(docArr[i], docNum, T[hash(docArr[i], SIZE)]);
        //}
        //} 
            //T[hash(docArr[i], SIZE)] = insertHelper(docArr[i], docNum, T[hash(docArr[i], SIZE)]);
       
      }
    }
     // }
      //*************************************
      //T[hash(term, SIZE)] = insertHelper(term, docNum, T[hash(term, SIZE)]);
   // }
    
    //insertHelper
    public Node insertHelper(String term, int docNum, Node t) {
      if (t == null)
        return new Node(term, docNum, null);
      else if (t.term.equals(term)) {
        t.termFreq[docNum] += 1.0;
        return t;
      }
      else {
        t.next = insertHelper(term, docNum, t.next);
        return t;
      }
    }
    
    //Similarity
    //***********************************************************
   public double cosineSimilarity() {
        for (int i = 0; i < T.length; ++i) {
            for (Node p = T[i]; p!=null; p=p.next) {
                AB += (p.termFreq[0] * p.termFreq[1]);       
                AA += (p.termFreq[0] * p.termFreq[0]);
                BB += (p.termFreq[1] * p.termFreq[1]);
            }
        }
       return (AB)/(Math.sqrt(AA)*Math.sqrt(BB));
    }
   
   //reset the has table
   //*******************************************************
   public void reset() {
     T = new Node[SIZE];
   }
   
   //blacklist
   //*************************************************************
     private final static String [] blackList = { "the", "of", "and", "a", "to", "in", "is", 
    "you", "that", "it", "he", "was", "for", "on", "are", "as", "with", 
    "his", "they", "i", "at", "be", "this", "have", "from", "or", "one", 
    "had", "by", "word", "but", "not", "what", "all", "were", "we", "when", 
    "your", "can", "said", "there", "use", "an", "each", "which", "she", 
    "do", "how", "their", "if", "will", "up", "other", "about", "out", "many", 
    "then", "them", "these", "so", "some", "her", "would", "make", "like", 
    "him", "into", "time", "has", "look", "two", "more", "write", "go", "see", 
    "number", "no", "way", "could", "people",  "my", "than", "first", "water", 
    "been", "call", "who", "oil", "its", "now", "find", "long", "down", "day", 
    "did", "get", "come", "made", "may", "part" }; 
     
   //unit test
   //*************************************************************
   public static void main(String[] args) {
      System.out.println("This is the test:");
      System.out.println("Test two same sentances : \"a b c d\"");
      TermFrequencyTable term1 = new TermFrequencyTable();
      term1.insert("a b c d", 0);
      term1.insert("a b c d", 1);
      double sim = term1.cosineSimilarity();
      System.out.println(sim);
      System.out.println();
       
      System.out.println("Test two totatlly different sentances : \"a b c d\" and \"l k j h\"");
      TermFrequencyTable term2 = new TermFrequencyTable();
      term2.insert("a b c d", 0);
      term2.insert("l k j h", 1);
      System.out.println(term2.cosineSimilarity());
      System.out.println();
      
       System.out.println("Test two sentance that have sim: \"He likes computer science\" and \"He likes computer game\"");
      TermFrequencyTable term3 = new TermFrequencyTable();
      term3.insert("He likes computer science", 0);
      term3.insert("He likes computer game", 1);
      System.out.println(term3.cosineSimilarity());
    }

}