//resizing 
//Yehui Huang
//partner: Tianyang Zhong


public class ResizingHashTable {
  
  /*
   * Link List part
   */
  public static class Node {
    int key;
    Node next;
    
    public Node(int k, Node p) {
       key = k;
       next = p;
    }
  }
  
  public Node head = null;
  
  
  
  
  /*\
   * Hash Table part
   */

  
  static int M = 5;
  static int MNew = 0;
  private static Node [] T = new Node[M];
  static Node [] TNew = new Node[MNew];
  static boolean resizingMode = false;
  static double resizingthreshold = 2.0;

  //Hash
  //********************************************
  private static int hash(int k) {
    if (resizingMode) {
    return (k*7369)% MNew;
    } else {
      return (k*7369)%M;
    }
  }
  
//  public void insert(int k) {
//    T[hash(k)] = insertHelper(k, T[hash(k)]);
//  }
  
  private static Node insertHelper(int k, Node p) {
    if (p == null)
      return new Node(k, null);
    else {
      p.next = insertHelper(k, p.next);
      return p;
    }
  }
  
  private static int delete( Node p) {
      int deletes = p.key;
      p = p.next;
      return deletes;
  }
  

  private static int length( Node p ) {                   
       if (p == null) 
          return 0;
       else
          return 1 + length( p.next ); 
  }
  
  private static boolean isEmpty(Node[] k, int i) {
    for (int c = 0; c < i; ++c) {
      if (k[c] != null)
        return false;
    }
    return true;
  }
  
  /*\
   * Incremental Resizing
   */
  
  
  
  public static void insert(int k) {
    if (resizingMode) {
      //Insert the key k into the new hash table
      TNew[hash(k)] = insertHelper(k, TNew[hash(k)]);
      if (isEmpty(T, T.length)) {
        //Replace old hash table with new one (now there is only one);
        T = TNew;
        M = MNew;
        resizingMode = false;
      }
      else {
          //Remove one key from the old table, and insert it into the new table;
        for (int z = 0; z<T.length; ++z) {
          if (T[z] != null) {
            int temp = T[z].key;
            T[z] = T[z].next;
            TNew[hash(temp)] = insertHelper(temp, TNew[hash(temp)]);
            break;
          }
        }
        
      }
    }
    else {
      //Insert key k into the table (there is only one);
      T[hash(k)] = insertHelper(k, T[hash(k)]);
      float count = 0;
      for (int i = 0; i<T.length; ++i) {
          count += length(T[i]);
      }
      //If the load factor is larger than the resizingThreshold
      if (count/T.length > resizingthreshold) {
          //Create a new hash table of size MNew, where MNew is the smallest
          MNew = 2*M + 1;
          TNew = new Node[MNew];
        resizingMode = true;
      }
    }
  }
  
  
  /***********************************************************************************************************/
  //we need to print out the whole table to check if the resizing hashtable is working.
  public static void printTable(){
     System.out.println("M = "+M+"N = "+MNew);//"Load Factor ="+(count/size));
    if(resizingMode){
      System.out.println("T");
      for(int i=0; i < T.length;++i){
        if(T[i] != null){
         System.out.print("["+i+"]-->"); 
         printList(T[i]);
        }else
          System.out.println("["+i+"]");
      }
      System.out.println("TNew");
      for(int i= 0; i <TNew.length;++i){
        if(TNew[i] != null){
          System.out.print("["+i+"]-->"); 
         printList(TNew[i]);
        }else
          System.out.println("["+i+"]");
      }
    }else{
       System.out.println("T");
        for(int i=0; i < T.length;++i){
          if(T[i] != null){
         System.out.print("["+i+"]-->"); 
         printList(T[i]);
          }else
          System.out.println("["+i+"]");
      }
         System.out.println("TNew :");
        System.out.println("Table is null");
    }
  }
        
  //we need a printlist() to print out the list. this is useful in the unit test.
  public static void printList(Node q){
        if(q!=null){
            System.out.print(q.key+"-->");
            printList(q.next);
        }else if (q==null)
                System.out.println("null");
    }
  /***********************************************************************************************************/
  //unit test
  public static void main(String[] args){
    printTable();
    insert(2);
    printTable();
    for (int i = 0; i<9; ++i) {
       insert(i);
    }
    printTable();
    insert(24);
    printTable();
    insert(14);
    printTable();
    for (int z = 15; z<27;++z) {
        insert(z);
    }
    printTable();
    for(int i =27; i <53;++i){
      insert(i);
    }
    printTable();
  }
  
  
}

