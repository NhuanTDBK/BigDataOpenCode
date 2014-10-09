package view;
import business.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Product;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;
public class ProductView {
	public static void main(String [] args)
	{
		SparkConf conf = new SparkConf().setAppName("Sales");
		JavaSparkContext ctx = new JavaSparkContext(conf);
		JavaRDD<String>lines = ctx.textFile(args[0]);
		business.Products p = new business.Products();
		JavaRDD<String> lineFilter = lines.filter(new Function<String,Boolean>()
				{

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public Boolean call(String arg0) throws Exception {
						// TODO Auto-generated method stub
						String[] col = arg0.split(Product.regex);
						String id = col[Product.ID];
						boolean check = (id.compareTo("NULL")==0?false:true);
						return check&&!arg0.contains("CampName");
					}
				
				});
		JavaPairRDD<String,Integer> productsPair = lineFilter.mapToPair(p.productPairByID);
		JavaPairRDD<String,Integer> products = productsPair.reduceByKey(new ReduceValue());
		
		List<Tuple2<String,Integer>> list = products.collect();
		Collections.sort(list,new TupleComparator());
	/*	Collections.max(list,new Comparator<Tuple2<String,Integer>>()
				{

			@Override
			public int compare(Tuple2<String, Integer> arg0,
					Tuple2<String, Integer> arg1) {
				return 0-Integer.compare(arg0._2(),arg1._2());
			}
	
				});*/
		Tuple2<String, Integer> maxID = list.get(0);
		final String maxID1 = maxID._1();
		for(Tuple2<String,Integer> one: list)
		{
			System.out.println(one._1()+", "+one._2());
		}
		System.out.println(maxID1);
		p.ID=maxID1;
		JavaRDD<String> getProduct = lineFilter.filter(p.filterByID);
		JavaRDD<String> getAttributes = getProduct.map(new Function<String,String>(){

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String call(String line) throws Exception {
				// TODO Auto-generated method stub
				StringBuilder builder = new StringBuilder();
				String [] col = line.split(",");
				builder.append(col[Product.END_DATE]+" ");
				for(int i = 0;i<Product.indexAttributes.length;i++)
				{
					builder.append(col[Product.indexAttributes[i]]+" ");
					
				}
				return builder.toString();
			
			}
		});
		List<String> lists= getAttributes.collect();
		Set<String> set = new HashSet<String>(lists);
		for(String temp:set)
		{
			System.out.println(temp);
		}
	}
}