package ch.hsr.votegame.domain;

public class HistoryEntry<K, V> {
	 
	  private final K key;
	  private final V value;
	 
	  public HistoryEntry(K k,V v) {  
	    key = k;
	    value = v;   
	  }
	 
	  public K getKey() {
	    return key;
	  }
	 
	  public V getValue() {
	    return value;
	  }
	 
	  public String toString() { 
	    return "(" + key + ", " + value + ")";  
	  }
	}
