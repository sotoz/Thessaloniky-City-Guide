package medcollege.namespace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MonumentList {
	ArrayList<Monument> monuments;
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
	
}
class CustomComparator implements Comparator<Monument> {
    @Override
    public int compare(Monument o1, Monument o2) {
        return o1.getDistanceFromCurPos().compareTo(o2.getDistanceFromCurPos());
    }
}