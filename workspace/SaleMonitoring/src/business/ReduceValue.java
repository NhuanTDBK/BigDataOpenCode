package business;

import org.apache.spark.api.java.function.Function2;



public class ReduceValue implements Function2<Integer,Integer,Integer> {

	@Override
	public Integer call(Integer arg0, Integer arg1) throws Exception {
		// TODO Auto-generated method stub
		return arg0 + arg1;
	}

}
