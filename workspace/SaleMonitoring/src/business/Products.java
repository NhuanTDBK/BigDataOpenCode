package business;

import org.apache.spark.api.java.function.PairFunction;

import model.Product;

import org.apache.spark.api.java.function.*;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import scala.Tuple2;

public class Products implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer one;
	public String ID;
	public String Gender;
	public Date beginDate;
	public Date endDate;
	public Products() {
		one = new Integer(1);
		beginDate = new Date(2014-1900,00,01);//mung 1, thang 1, 2014
		endDate = new Date(2014-1900,11,31);//ngay 31, thang 12, 2014
	}

	public Products(String ID) {
		one = new Integer(1);
		this.ID = ID;
	
	}

	public Products(String ID, String Gender) {
		this(ID);
		this.Gender = Gender;
	}

	public Function<String, Boolean> filterByGender = new Function<String, Boolean>() {

		@Override
		public Boolean call(String arg0) throws Exception {
			// TODO Auto-generated method stub
			String[] temp = arg0.split(Product.regex);
			String gender = temp[Product.GENDER];
			int check = gender.compareTo(Gender)*gender.compareTo("UNISEX");
			if (check == 0)
				return true;
			return false;
		}

	};
	public Function<String,Boolean> filterByDate = new Function<String,Boolean>()
			{

				@Override
				public Boolean call(String line) throws Exception {
					String [] temp = line.split(",");
					SimpleDateFormat formatter = new SimpleDateFormat(Product.patternWithoutMili);
					Date time = new Date();
					try
					{
						time = formatter.parse(temp[Product.END_DATE]);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					int check = time.compareTo(beginDate)*time.compareTo(endDate);
					if(check<=0) return true;
					return false;
				}
				
			};
	public Function<String, Boolean> filterByID = new Function<String, Boolean>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Boolean call(String lines) throws Exception {
			String[] temp = lines.split(Product.regex);
			if (temp[Product.ID].compareTo(ID) == 0)
				return true;
			return false;
		}
	};
	public FlatMapFunction<String, String> flatMapProduct = new FlatMapFunction<String, String>() {
		
		@Override
		public Iterable<String> call(String line) throws Exception {
			String [] temp = line.split(",");
			String [] attr = new String[10];
			for(int i = 0;i<Product.indexAttributes.length;i++)
			{
				attr[i] = temp[Product.indexAttributes[i]];
			}
			return Arrays.asList(attr);
		}
	};
	public PairFunction<String,String,Integer> ProductAttributePair = new PairFunction<String, String, Integer>() {

		@Override
		public Tuple2<String, Integer> call(String attribute) throws Exception {
			return new Tuple2<String,Integer>(attribute,1);
		}
	};
	public PairFunction<String, String, Integer> productPairByID = new PairFunction<String, String, Integer>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Tuple2<String, Integer> call(String line) throws Exception {
			String id;
			String[] columns = line.split(",");
			id = columns[Product.ID];
			return new Tuple2<String, Integer>(id, one);
		}
	};
}
