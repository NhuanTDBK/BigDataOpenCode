package business;

import java.util.Comparator;

import scala.Tuple2;
/*
 * Sắp xếp giảm dần
 */
public class TupleComparator implements Comparator<Tuple2<String, Integer>>{

	@Override
	public int compare(Tuple2<String, Integer> s1,
			Tuple2<String, Integer> s2) {
		return 0-Integer.compare(s1._2(), s2._2());
		
	}
	
}
