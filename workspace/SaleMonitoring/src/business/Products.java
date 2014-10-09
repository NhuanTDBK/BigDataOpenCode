package business;

import org.apache.spark.api.java.function.PairFunction;

import model.Product;

import org.apache.spark.api.java.function.*;

import java.io.Serializable;
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
	public Date Date;
	public Products() {
		one = new Integer(1);
	}

	public Products(String ID) {
		one = new Integer(1);
		this.ID = ID;
		Date = new Date();
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
			if (temp[Product.GENDER].compareTo(Gender) == 0)
				return true;
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
	public Function<String, Boolean> filterByDate = new Function<String, Boolean>() {

		@Override
		public Boolean call(String arg0) throws Exception {
			// TODO Auto-generated method stub
			return null;
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
