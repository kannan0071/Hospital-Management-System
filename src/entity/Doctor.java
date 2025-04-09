package entity;

public class Doctor {
	
	private int doctorId;
    private String firstName;
    private String lastName;
    private String specialization;
    private String contactNumber;
    
	public Doctor() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Doctor(int doctorId, String firstName, String lastName, String specialization, String contactNumber) {
		super();
		this.doctorId = doctorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.specialization = specialization;
		this.contactNumber = contactNumber;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	
	@Override
	public String toString() {
		return "Doctor{" +"doctorId=" + doctorId +", firstName='" + firstName + '\'' +", lastName='" + lastName + '\'' +
                ", specialization='" + specialization + '\'' +", contactNumber='" + contactNumber + '\'' +'}';
	}
	
//	public void printDetails() {
//        System.out.println("Doctor ID: " + doctorId);
//        System.out.println("Name: " + firstName + " " + lastName);
//        System.out.println("Specialization: " + specialization);
//        System.out.println("Contact: " + contactNumber);
//    }
}
