package com.stl.pg;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class Test {

	public static void main(String[] args) {
		String hashData = "" ;
		HashMap<String,String> hm = new HashMap<String, String>();
		hm.put("Bat","Benti");
		hm.put("Amazon","Ranvir");
		hm.put("Bou", "Papu");
		hm.put("Abcd","Ranjan");
		
		Set<String> beforeSet = hm.keySet() ;
		Iterator<String> itr = beforeSet.iterator() ;
		String key = "" ;
		while(itr.hasNext()){
			if(hm.containsKey(key = itr.next())){
				System.out.println(hm.get(key));
			}
		}
		
		/*Set<String> beforeSet = hm.keySet() ;
		System.out.println(beforeSet);
		Collection<String> hmValues = (Collection<String>) hm.values() ;
		for (String value : hmValues) {
			if(value.length() > 0){
				hashData += '|' + value ;
			}
		}
		System.out.println(hashData);
		hashData = "" ;
		TreeMap<String,String> tm = new TreeMap<String, String>(hm) ;
		Set<String> afterSet = tm.keySet() ;
		System.out.println(afterSet);
		Collection<String> tmValues = (Collection<String>) tm.values() ;
		for (String value : tmValues) {
			if(value.length() > 0){
				hashData += '|' + value ;
			}
		}
		System.out.println(hashData);*/
	}

}
