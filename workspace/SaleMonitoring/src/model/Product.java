package model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/*
 * Lớp Sản phẩm: gồm thông tin về ngày thang bắt đầu, số lượng mặt hàng, giá
 */
public class Product implements Comparator<Product>,Serializable {
	public static int GENDER = 31;
	public static int ID = 18;
	public static int NAME = ID + 1;
	public static int QUANTITY = ID + 2;
	public static int VALUE = ID + 3;
	public static int AMOUNT = VALUE + 3 - 1;
	public static int CATEGORY = 43 - 1;
	public static int SALEOFF = AMOUNT + 1 - 1;
	public static int START_DATE = 8;
	public static int END_DATE = 14;
	public static int category = GENDER + 12;
	public static int[] index = { GENDER, ID, NAME, QUANTITY, VALUE, AMOUNT };
	public static int[] indexAttributes = { GENDER, GENDER + 1, GENDER + 2,
			GENDER + 3, GENDER + 4, GENDER + 5, GENDER + 7, GENDER + 8,
			GENDER + 9, GENDER + 10 };
	public static final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";// pattern cho date time
	public static final String pattern1 = "yyyy-MM-dd HH:mm:ss";
	private static final String NULLSTRING = "NULL";
	private static SimpleDateFormat formatter,formatter1;
	private Date notDate;
	public static final String regex=",";
	public Product() {
		setAttributes(new String[10]);
		formatter = new SimpleDateFormat(pattern);
		formatter1 = new SimpleDateFormat(pattern1);
		notDate = new Date(1900, 1, 1, 0, 0, 0);
		this._start_date = notDate;
		this._end_date = notDate;
		this._category = "0";
		this.id = "0";
		this.quantity = 0;
		this.value = 0;
		this.revenue = 0;
	}

	private Date _start_date;
	private Date _end_date;
	private String _category;
	private String[] attributes;
	private String id;
	private long quantity;
	private long value;
	private long revenue;

	public Date getStartDate() {
		return _start_date;
	}

	public Date getEndDate() {
		return _end_date;
	}

	public void setStartDate(String date) {
		Date dates = new Date();
		if (date.compareTo(NULLSTRING)!=0) {
			try {
				dates = formatter.parse(date);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			this._start_date = dates;
		}
	}

	public void setEndDate(String date) {
		Date dates = new Date();
		if (date.compareTo(NULLSTRING)!=0) {
			try {
				dates = formatter1.parse(date);
			} catch (ParseException e) {
				this._end_date = notDate;
				e.printStackTrace();
			}
			this._end_date = dates;
		}
	}

	public String getCategory() {
		return _category;
	}

	public void setCategory(String _category) {
		if (_category.compareTo(NULLSTRING)!=0)
			this._category = _category;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

	@Override
	public int compare(Product arg0, Product arg1) {
		return arg0.id.compareTo(arg1.id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (id.compareTo(NULLSTRING)!=0)
			this.id = id;
	}

	public long getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantitys) {
		if (quantitys.compareTo(NULLSTRING)!=0) {
			try {
				this.quantity = (long)Double.parseDouble(quantitys);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public long getValue() {
		return value;
	}

	public void setValue(String value) {
		if (value.compareTo(NULLSTRING)!=0)
			try {
				this.value = Long.parseLong(value);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	public long getRevenue() {
		return revenue;
	}

	public void setRevenue(long revenue) {
		this.revenue = revenue;
	}
}
