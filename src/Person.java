import java.util.Date;

public class Person {
	private String ssn;
	private String name;
	private String lastname;
	private String email;
	private int birthDate;
	private int age;
	

	public Person() {
		super();
	}

	public Person(String ssn, String name, String lastname, String email, int birthDate, int age) {
		super();
		this.ssn = ssn;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.birthDate = birthDate;
		this.age = age;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(int birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [ssn=" + ssn + ", name=" + name + ", lastname=" + lastname + ", email=" + email + ", birthDate="
				+ birthDate + ", age=" + age + "]";
	}

	
	
	

}
