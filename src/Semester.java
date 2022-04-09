public class Semester {
	private int num;
	private String name;
	
	
	/**
	 * 
	 */
	public Semester() {
		super();
	}


	/**
	 * @param num
	 */
	public Semester(int num) {
		super();
		this.num = num;
		int remainder = num % 2;
		if (remainder == 1)
			this.name = "Fall";
		else if (remainder == 0)
			this.name = "Spring";
	}


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return name;
	}
	
	
	
}
