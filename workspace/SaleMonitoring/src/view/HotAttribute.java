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
public class HotAttribute {
	public static void main(String[] args) {
		//Usage: <input file> <Gender> <Time:Month>
		SparkConf conf = new SparkConf().setAppName("Sales");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		JavaRDD<String> lines = ctx.textFile(args[0]);
		business.Products p = new business.Products();
		String gender = args[1];
		String month_g = args[2];
		String month_e;
		if(args.length==3)
		{
			p.beginDate.setMonth(Integer.parseInt(month_g)-1);
			p.endDate.setMonth(p.beginDate.getMonth());
		
		}
		if(args.length==4) 
		{
				month_e = args[3]; 
				p.beginDate.setMonth(Integer.parseInt(month_g)-1);
				p.endDate.setMonth(Integer.parseInt(month_e)-1);
		}
		
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
		JavaRDD<String> lineFilterByGenderAndDate = lineFilter.filter(p.filterByDate);
		JavaRDD<String> flatMapProduct = lineFilterByGenderAndDate.flatMap(p.flatMapProduct);
		JavaPairRDD<String,Integer> pairProductAttribute = flatMapProduct.mapToPair(p.ProductAttributePair);
		JavaPairRDD<String,Integer> reduceProductAttribute = pairProductAttribute.reduceByKey(new ReduceValue());
		
		List<Tuple2<String,Integer>> list = pairProductAttribute.collect();
		Collections.sort(list,new TupleComparator());
		Tuple2<String,Integer> top = list.get(0);
		System.out.println("Thuoc tinh hot nhat trong thang "+(p.beginDate.getMonth()+1)+" "+top._1());
		ctx.stop();
		
	}
}
