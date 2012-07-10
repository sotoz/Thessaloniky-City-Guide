package com.medcollege.thesscityguide;

import android.annotation.SuppressLint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MonumentList {
	ArrayList<Monument> monuments;
	@SuppressLint("UseSparseArrays")
	Map<Integer, Monument> mml = new HashMap<Integer, Monument>();
		
	public MonumentList(){
		monuments = new ArrayList<Monument>();
	}
	public Monument getMonument(int i){
		return monuments.get(i);
	}
	public void addMonument(Monument addMon){
		monuments.add(addMon);
	}
	public int getSize()
	{
		return monuments.size();
	}	
	public void sortMonuments(){
		Collections.sort(monuments, new CustomComparator());
	}
	public void deleteList(){
		monuments.clear();
	}
	public void addToMap(int monId, Monument mon){
		mml.put(monId, mon);	
	}
	public Monument returnMonFromMap(int monId){
		return mml.get(monId);
	}
}
class CustomComparator implements Comparator<Monument> {
    @Override
    public int compare(Monument o1, Monument o2) {
        return o1.getDistanceFromCurPos().compareTo(o2.getDistanceFromCurPos());
    }
}