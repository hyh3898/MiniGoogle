//Yehui Huang
//cs112
//11/25/2014


import java.util.*;


public class MiniGoogle {
  
  private static Article[] getArticleList(DatabaseIterator db) {
    
    // count how many articles are in the directory
    int count = db.getNumArticles(); 
    
    // now create array
    Article[] list = new Article[count];
    for(int i = 0; i < count; ++i)
      list[i] = db.next();
    
    return list; 
  }
  
  private static DatabaseIterator setupDatabase(String path) {
    return new DatabaseIterator(path);
  }
  
  //add article
  //************************************************************
  private static void addArticle(Scanner s, ArticleTable D) {
    System.out.println();
    System.out.println("Add an article");
    System.out.println("==============");
    
    System.out.print("Enter article title: ");
    String title = s.nextLine();
    
    System.out.println("You may now enter the body of the article.");
    System.out.println("Press return two times when you are done.");
    
    String body = "";
    String line = "";
    do {
      line = s.nextLine();
      body += line + "\n";
    } while (!line.equals(""));
    
    D.add(new Article(title, body));
  }
  
  //remove article
  //************************************************************
  private static void removeArticle(Scanner s, ArticleTable D) {
    System.out.println();
    System.out.println("Remove an article");
    
    System.out.println("=================");
    
    System.out.print("Enter article title: ");
    String title = s.nextLine();
    
    
    D.remove(title);
  }
  
  //title search
  //************************************************************
  private static void titleSearch(Scanner s, ArticleTable D) {
    System.out.println();
    System.out.println("Search by article title");
    System.out.println("=======================");
    
    System.out.print("Enter article title: ");
    String title = s.nextLine();
    
    Article a = D.lookup(title);
    if(a != null)
      System.out.println(a);
    else {
      System.out.println("Article not found!"); 
      return; 
    }
    
    System.out.println("Press return when finished reading.");
    s.nextLine();
  }
  
  
  //Mini google
  //************************************************************
  public static void miniGoogleSearch(Scanner s, TermFrequencyTable T, ArticleTable D) {

    System.out.println();
    System.out.println("Search by MiniGoogle");
    System.out.println("==========================");
    
    System.out.print("Enter pharse: ");
    String pharse = s.nextLine();
    D.reset();
    while(D.hasNext()) {
      Article next = D.next();
      next.setCosineSim(getCosineSimilarity(pharse, next.getBody())); 
      if (next.getCosineSim() >0.0) {
        insert(next, pharse);
      }
    }
    System.out.println("The result: \n");
    if (A[0]==null) {
      System.out.println("Article Not Found!");
    }
    else {
    Article a = getMax();
    Article b = getMax();
    Article c = getMax();
//    if (a==null) {
//      System.out.println("Article Not Found!");
//    } 
    
    System.out.println(a);
    System.out.println("   Sim: " + a.getCosineSim());
    System.out.println();
    System.out.println(b);
    System.out.println("   Sim: " + b.getCosineSim());
    System.out.println();
    System.out.println(c);
    System.out.println("   Sim: " + c.getCosineSim());
    System.out.println();
  
    }

    
  }
  

  //main method
  //************************************************************
  public static void main(String[] args) {
    TermFrequencyTable Ftable = new TermFrequencyTable();
   //ArticleTable Atable = new ArticleTable();
  
    
    Scanner user = new Scanner(System.in);
    
    String dbPath = "articles/";
    
    DatabaseIterator db = setupDatabase(dbPath);
    
    System.out.println("Read " + db.getNumArticles() + 
                       " articles from disk.");
    
    ArticleTable L = new ArticleTable(); 
    Article[] A = getArticleList(db);
    L.initialize(A);
    
    int choice = -1;
    do {
      System.out.println();
      System.out.println("Welcome to MiniGoogle!");
      System.out.println("=====================");
      System.out.println("Make a selection from the " +
                         "following options:");
      System.out.println();
      System.out.println("Manipulating the database");
      System.out.println("-------------------------");
      System.out.println("    1. add a new article");
      System.out.println("    2. remove an article");
      System.out.println();
      System.out.println("Searching the database");
      System.out.println("----------------------");
      System.out.println("    3. search by exact article title");
      System.out.println("    4. search for a phrase(MiniGoogle)");
      System.out.println();
      
      System.out.print("Enter a selection (1-4, or 0 to quit): ");
      
//      String s = "now";
//      if (blacklisted(s)) {
//      System.out.println("True");
//      }

      
      
      choice = user.nextInt();
      user.nextLine();
      
      switch (choice) {
        case 0:
          return;
          
        case 1:
          addArticle(user, L);
          break;
          
        case 2:
          removeArticle(user, L);
          break;
          
        case 3:
          titleSearch(user, L);
          break;
          
        case 4:
          //titleSearch(user, L);
          miniGoogleSearch(user, Ftable, L );
          A = resetA;
         Ftable.reset();

          
        default:
          break;
      }
      
      choice = -1;
      
    } while (choice < 0 || choice > 4);
    
  }
  
  
  
  //Preprocess
  //******************************************************************************
  public static String preprocess(String s) {
      String ss = "";
      String l = "";
      l = s.toLowerCase();
      for (int i = 0; i<l.length(); ++i) {
        if ("abcdefghijklmnopqrstuvwxyz1234567890 ".indexOf(l.charAt(i))!=-1 ) {    //reconstruct the sentance
          ss+=l.charAt(i);
        }
      } return ss;
     
     
  }
  

  //Blacklist
  //******************************************************************************
  public static boolean blacklisted(String s) {               // determine if the string s is a member of the blacklist (given at the bottom of this assignment)
    for (int i = 0; i<blackList.length; ++i) {
      if (s.equals(blackList[i])) {
        return true;                               
      }
    } return false;
  }
  
  private static final String [] blackList = { "the", "of", "and", "a", "to", "in", "is", 
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
  
  
  
  //getCosineSimilarity
  //**********************************************************************************
  public static double getCosineSimilarity(String s, String t) {                           

    TermFrequencyTable Ftable = new TermFrequencyTable();
    String ss = preprocess(s); 
    String tt = preprocess(t);
    String[] sArr = ss.split(" ");
    String[] tArr = tt.split(" ");
    for (int i = 0; i<sArr.length; ++i) {
      if (!blacklisted(sArr[i])) {
        Ftable.insert(sArr[i],0);
      }
    }
    for (int z = 0; z<tArr.length; ++z) {
      if (!blacklisted(tArr[z])) {
        Ftable.insert(tArr[z],1);
      }
    }
    
//    Ftable.insert(ss,0);
//    Ftable.insert(tt,1);
    return Ftable.cosineSimilarity();

  }
  

  
  
  
  //Heap
  //*******************************************************************************

   
   private static final int SIZE = 10;       // initial length of array
   private static int next = 0;              // limit of elements in array
   private static Article[] A = new Article[SIZE];    // implements tree by storing elements in level order
   private static Article[] resetA = new Article[SIZE];
   
   // standard resize to avoid overflow
   
   private static void resize() {
      Article[] B = new Article[A.length*2];
      for(int i = 0; i < A.length; ++i)
         B[i] = A[i];
      A = B; 
   }
   
   // methods to move up and down tree as array
   
   private static int parent(int i) { return (i-1) / 2; }
   private static int lchild(int i) { return 2 * i + 1; }
   private static int rchild(int i) { return 2 * i + 2; }
   
   private static boolean isLeaf(int i) { return (lchild(i) >= next); }
   private static boolean isRoot(int i) { return i == 0; }
   
   // standard swap, using indices in array
   private static void swap(int i, int j) {
      Article temp = A[i];
      A[i] = A[j];
      A[j] = temp;
   }
   
   // basic data structure methods
   
   public static boolean isEmpty() {
      return (next == 0);
   }
   
   public static int size() {
      return (next);
   }
   
   // insert an integer into array at next available location
   //    and fix any violations of heap property on path up to root
   
   public static void insert(Article k, String pharse) {
      if(size() == A.length) resize(); 
      A[next] = k; 
      //A[next].sim = getCosineSimilarity(pharse, k.getBody());
      //System.out.println(A[next].sim);
      int i = next;
      int p = parent(i); 
      while(!isRoot(i) && (A[i].getCosineSim()> A[p].getCosineSim())) {
         swap(i,p);
         i = p;
         p = parent(i); 
      }
      
      ++next;
   }
   
   
   // Remove top (maximum) element, and replace with last element in level
   //    order; fix any violations of heap property on a path downwards
   
   public static Article getMax() {
      --next;
      swap(0,next);                // swap root with last element
      int i = 0;                   // i is location of new key as it moves down tree
 
      // while there is a maximum child and element out of order, swap with max child
      int mc = maxChild(i); 
      while(!isLeaf(i) && A[i].getCosineSim()< A[mc].getCosineSim()) { 
         swap(i,mc);
         i = mc; 
         mc = maxChild(i);
      }
      
 ///     printHeapAsTree(); 
      
      return A[next];
   }
   
   // return index of maximum child of i or -1 if i is a leaf node (no children)
   
   public static int maxChild(int i) {
     if(lchild(i) >= next) {
         return -1;
   }
      if(rchild(i) >= next)
         return lchild(i);
      else if(A[lchild(i)].getCosineSim() > A[rchild(i)].getCosineSim())
         return lchild(i);
   
      else
         return rchild(i); 
   }
   
   // Apply heapsort to the array A. To use, fill A with keys and then call heapsort
   
//   public static void heapSort() {
//      next = 0;
//      for(int i = 0; i < A.length; ++i)      // turn A into a heap
//         insert(A[i], );
//      for(int i = 0; i < A.length; ++i)      // delete root A.length times, which swaps max into
//         getMax();                           //  right side of the array
//   }
   
   // debug method
   
   private static void printHeap() {
      for(int i = 0; i < A.length; ++i)
         System.out.print(A[i] + " ");
      System.out.println("\t next = " + next);
   }
   
   private static void printHeapAsTree() {
      printHeapTreeHelper(0, ""); 
      System.out.println(); 
   }
   
   private static void printHeapTreeHelper(int i, String indent) {
      if(i < next) {
         
         printHeapTreeHelper(rchild(i), indent + "   "); 
         System.out.println(indent + A[i]);
         printHeapTreeHelper(lchild(i), indent + "   "); 
      }
   }
 
   

//   //Node class
//   //*******************************************************************
//   public static class Node {
//        Article a;
//        double sim;
//
//        
//        public Node(Article term, double i) {
//          this.a = term;
//
//          this.sim = i;
//        }
//        
////        public Node(String term, Node p) {
////          this.term = term;
////          this.next = p;
////        }
//        
//    }
   
 
  
  
}
