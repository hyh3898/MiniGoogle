

//Yehui Huang
//cs112
//11/25//2014

public class ArticleTable {
   
   public static class Node {
      public Article data;
      public Node next;
      
      public Node(Article data, Node n) {
         this.data = data;
         this.next = n;
      }
      
      public Node(Article data) {
         this(data, null);
      }
   }
   
   public static Node root = null;
   public static Node p = null;
   
   public static void initialize(Article[] A) {
      for(int i = 0; i < A.length; ++i) 
         add(A[i]); 
   }
   
   public static void add(Article a) {
     // T[hash(a.getTitle(), SIZE)] = new Node(a, T[hash(a.getTitle(), SIZE)]); 
     insert(a);
   } 
   
   
   public static boolean member(Article a) {
      return (lookup(T[hash(a.getTitle(), SIZE)],a.getTitle()) != null); 
   }
   
   public boolean member(String title) {
      return (lookup(T[hash(title, SIZE)],title) != null); 
   }
   // Look up
   //***************************************************************
   public static Article lookup(String title) {
      Node n = lookup(T[hash(title, SIZE)],title); 
      if(n != null)
         return n.data; 
      return null; 
   }
   
   private static Node lookup(Node t, String key) {
      if (t == null)
         return null;
      else if (key.compareTo(t.data.getTitle()) == 0) {
         return t;
      } else 
         return lookup(t.next,key); 
   }
   
   public static Node lookup(Node t, Article a) {
      if (t == null)
         return null;
      else if (a.getTitle().compareTo(t.data.getTitle()) == 0) {
         return t;
      } else 
         return lookup(t.next, a);
   }
   
   //using delete
   public static void remove(String t) {
      delete(t); 
   }
  
   public int length() {

         return length(root); 
   }
   public int length(Node t) {
      if(t == null)
         return 0;
      else
         return 1 + length(t.next); 
   }
   
   // Recursively reconstructs tree without the key n in it
   //delete
   //*****************************************************************
   public static Node delete(String title, Node t) {
    if (t == null)                             // Case 1: tree is null
      return t;
    else if (title.compareTo(t.data.getTitle()) == 0)  
      return t.next; 
    else {
      t.next = delete(title, t.next); 
      return t;
    }
  }
   
   public static Node deleteHelper(String title, Node t) {
    if (t == null)                             // Case 1: tree is null
      return t;
    else if (title.compareTo(t.data.getTitle()) == 0)  
      return t.next; 
    else {
      t.next = delete(title, t.next); 
      return t;
    }
  }
  
  public static void delete(String title) {
     T[hash(title, SIZE)] =  deleteHelper(title, T[hash(title,SIZE)]);
  }
   
  
  
  //Creat the hash table
  //****************************************************************
   private static final int SIZE = 6; 
  
  private static Node [] T = new Node[SIZE];
  
  private static int hash(String x, int M) {
    char ch[];
    ch = x.toCharArray();
    int xlength = x.length();
    
    int i, sum;
    for (sum=0, i=0; i < x.length(); i++)
      sum += ch[i];
    return sum % M;
  }
  //insert
  //**********************************************************
  public static void insert(Article a)  {    // wrapper method for recursive insert
    String k = a.getTitle();
    T[hash(k, SIZE)] = insertHelper(a, T[hash(k, SIZE)]); 
  }
  
  private static Node insertHelper(Article k, Node p) {            // inserts a new node into the list, and returns the result 
    if (p == null)
      return new Node(k, null);
    else {
      p.next = insertHelper(k, p.next);
      return p;
    }
    
    
    
    
    
    
  }
  

  
  //has next
  //please check here;
  //***********************************************************
  public static int I = 0;
  
  public static void reset() {                      
        p = T[0];
        I = 0;
      }
      
      public static boolean hasNext() {
        if (p != null) {
          return true;
        }
        else {
          ++I;
          for (; I<T.length&&T[I]==null; ++I) {
            ;
        }
          if (I >= T.length) 
            return false;
          else {
            p = T[I];
            return true;
        }
        }
      }
      
     public static Article next() {
         Article temp = p.data;
         p = p.next;
        // ++I;
        return temp;
      }
     
     //print
     private void printHashTable() {
       for (int i = 0; i<T.length; ++i) {
         System.out.print("["+i+"] -> ");
         printList(T[i]);
       }
     }
     
     private static void printList(Node p) {
       if (p == null)
         System.out.println("null");
       else if (p.next == null) {
         System.out.println(p.data.getTitle());
       }
       else {
         System.out.print(p.data.getTitle() + " -> ");
         printList(p.next);
       }
     }
       
     
     
     
     public static void main(String[] args) {
       ArticleTable T = new ArticleTable();
       System.out.println("Test the add(insert):");
       T.add(new Article("A", "haha"));
       T.add(new Article("B", "xixi"));
       T.add(new Article("C", "hehe"));
       T.add(new Article("D", "hehe"));
       T.add(new Article("E", "heihei"));
       T.add(new Article("F", "hoho"));
       T.add(new Article("Aaron", "nihaoa"));
       System.out.println("printout everything in the talbe with iterator:");
       T.printHashTable();
       System.out.println();
       T.reset();
       while(T.hasNext()) {
         Article a = T.next();
         System.out.println(a);
       }
       T.reset();
       
       System.out.println("remove A B C D from the hash table: ");
       System.out.println("remove(delete) A:");
       T.remove("A");
       System.out.println("remove(delete) B:");
       T.remove("B");
       System.out.println("remove(delete) C:");
       T.delete("C");
       System.out.println("remove(delete) D:");
       T.delete("D");
       System.out.println();
       System.out.println("printout everything in the talbe with iterator after removing:");
       T.printHashTable();
       System.out.println();
       T.reset();
       while(T.hasNext()) {
         Article a = T.next();
         System.out.println(a);
       }
       
       System.out.println("Test if the member F in the table: (check look up)");
       if (T.member("F")) {
         System.out.println("YES");
       }
       else 
         System.out.println("No");
       System.out.println();
       System.out.println("Test if the member B in the table: (check look up)");
        if (T.member("B")) {
         System.out.println("YES");
       }
       else 
         System.out.println("No");
     }
     
    
   
 
   
   
}
