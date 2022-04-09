public class StudentID {
	public static int order = 0;
	private String id;
	private String departmentCode = "1501";
	private int year;
	/**
	 * @param year
	 */
	public StudentID(int year) {
		super();
		this.id = generateRandomId(year);
		this.year = year;
	}

	public static int getOrder() {
		return order;
	}

	public static void setOrder(int order) {
		StudentID.order = order;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public static String generateRandomId (int year) {
		String stringYear = Integer.toString(year);
		String yearCode = stringYear.substring(2,4);
		//int ran = 100 + (int) (Math.random() * (999 - 100) );
		String orderString = Integer.toString(order);
		while(orderString.length() < 3){
			orderString = "0" +orderString ;
		}
		String id = "1501" + yearCode + orderString;
		order++;
		return id;
	}
}
