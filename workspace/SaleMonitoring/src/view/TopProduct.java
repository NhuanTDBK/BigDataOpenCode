package view;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Product;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import business.ReduceValue;
import business.TupleComparator;
import scala.Tuple2;
public class TopProduct {
	public static void main(String[] args) {
		//Usage: <input file> <Gender>
		SparkConf conf = new SparkConf().setAppName("Sales");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		JavaRDD<String> lines = ctx.textFile(args[0]);
		String gender = args[1];
		business.Products p = new business.Products();
		JavaRDD<String> lineFilter = lines
				.filter(new Function<String, Boolean>() {

					/**
				 * 
				 */
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean call(String arg0) throws Exception {
						// TODO Auto-generated method stub
						String[] col = arg0.split(Product.regex);
						String id = col[Product.ID];
						boolean check = (id.compareTo("NULL") == 0 ? false
								: true);
						return check && !arg0.contains("CampName");
					}

				});
		JavaRDD<String> lineFilterByGender = lineFilter.filter(p.filterByGender);
		
		JavaPairRDD<String, Integer> productsPair = lineFilter
				.mapToPair(p.productPairByID);
		JavaPairRDD<String, Integer> products = productsPair
				.reduceByKey(new ReduceValue());
		List<Tuple2<String, Integer>> list = products.collect();
		Collections.sort(list, new TupleComparator());
		int i = 5;
		for(Tuple2<String,Integer>temp:list)
		{
			if(i==0) break;
			System.out.println("San pham voi ma ID: "+temp._1()+", "+temp._2());
			--i;
		}
	}
}
