package avltree;
import java.util.*;
class Doctor {
	private String name;
	private String specialization;
	private String address;
	private String contactNo;
	ArrayList<Appointment> appointments=new ArrayList<>();
	ArrayList<Appointment> specializations=new ArrayList<>();
	
	public  Doctor(String name, String specialization,String address,String contactNo) {
		this.name = name;
		this.specialization = specialization;
		this.address=address;
		this.contactNo=contactNo;
	}

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String getName() {
		return name;
	}
	public String getSpecialization() {
		return specialization;
	}
	@Override
	public String toString() {
		return name + " (" + specialization + ")"+ "\nAddress:"+address+"\nContact No:"+contactNo ;
	}

	public void addAppointment(Appointment appointment) {
        
        appointments.add(appointment);
    }
}
class Appointment {
	private Doctor doctor;
	private String patientName;
	private String date;
	private String time;
	private String details;


	//medical history
	public Appointment(Doctor doctor, String patientName, String date, String time, String details) {
		this.doctor = doctor;
		this.patientName = patientName;
		this.date = date;
		this.time = time;
		this.details = details;
	}
	public Doctor getDoctor() {
		return doctor;
	}
	public String getPatientName() {
		return patientName;
	}
	public String getDate() {
		return date;
	}
	public String getTime() {
		return time;
	}
	public String getDetails() {
		return details;
	}
	


	    
	public String toString() {
		return "Patient: " + patientName + "\nDoctor: " + doctor.getName() + "\nDate: " + date + "\nTime: " + time + "\nDetails: " + details + "\n";
	}
}
class AVLNode {
	private List<Doctor> doctors;
	private AVLNode left;
	private AVLNode right;
	private int height;
	public AVLNode(Doctor doctor) {
		this.doctors = new ArrayList<>();
		this.doctors.add(doctor);
		this.left = null;
		this.right = null;
		this.height = 1;
	}
	public List<Doctor> getDoctors() {
		return doctors;
	}
	public AVLNode getLeft() {
		return left;
	}
	public void setLeft(AVLNode left) {
		this.left = left;
	}
	public AVLNode getRight() {
		return right;
	}
	public void setRight(AVLNode right) {
		this.right = right;
	}
	public int getHeight() {
		return height;
	}
	public void updateHeight() {
		height = 1 + Math.max((left != null ? left.getHeight() : 0), (right != null ? right.getHeight() : 0));
	}
	public int getBalanceFactor() {
		return (left != null ? left.getHeight() : 0) - (right != null ? right.getHeight() : 0);
	}
}
class AVLTree {
	private static AVLNode root;
	public AVLTree() {
		root = null;
	}
	// Helper method for left rotation
	private AVLNode rotateLeft(AVLNode y) {
		AVLNode x = y.getRight();
		AVLNode T2 = x.getLeft();
		x.setLeft(y);
		y.setRight(T2);
		y.updateHeight();
		x.updateHeight();
		return x;
	}
	// Helper method for right rotation
	private AVLNode rotateRight(AVLNode x) {
		AVLNode y = x.getLeft();
		AVLNode T2 = y.getRight();
		y.setRight(x);
		x.setLeft(T2);
		x.updateHeight();
		y.updateHeight();
		return y;
	}
	// Helper method for inserting a doctor into the AVL tree based on specialization
	private AVLNode insert(AVLNode node, Doctor doctor) {
		if (node == null) {
			return new AVLNode(doctor);
		}
		int compareResult = doctor.getSpecialization().compareTo(node.getDoctors().get(0).getSpecialization());
		if (compareResult < 0) {
			node.setLeft(insert(node.getLeft(), doctor));
		} else if (compareResult > 0) {
			node.setRight(insert(node.getRight(), doctor));
		} else {
			// Specialization already exists; add the doctor to the list
			node.getDoctors().add(doctor);
		}
		node.updateHeight();
		int balance = node.getBalanceFactor();
		// Left heavy
		if (balance > 1) {
			if (compareResult < 0) {
				return rotateRight(node);
			} else {
				node.setLeft(rotateLeft(node.getLeft()));
				return rotateRight(node);
			}
		}
		// Right heavy
		if (balance < -1) {
			if (compareResult > 0) {
				return rotateLeft(node);
			} else {
				node.setRight(rotateRight(node.getRight()));
				return rotateLeft(node);
			}
		}
		return node;
	}
	// Insert a doctor into the AVL tree based on specialization
	public void insert(Doctor doctor) {
		root = insert(root, doctor);
	}
	// Inorder traversal to print doctors by specialization
	public void printDoctorsBySpecialization() {
		printInorder(root);
	}
	private void printInorder(AVLNode node) {
		if (node != null) {
			printInorder(node.getLeft());
			
			List<Doctor> doctors = node.getDoctors();
			for (Doctor doctor : doctors) {
				System.out.println(doctor);
			}
			printInorder(node.getRight());
		}
	}

		
	

	public static AVLNode getRoot() {
		return root;
	}
	public static Doctor findDoctorByName(AVLNode root, String doctorName) {
		if (root == null) {
			return null;
		}
		List<Doctor> doctors = root.getDoctors();
		for (Doctor doctor : doctors) {
			if (doctor.getName().equals(doctorName)) {
				return doctor;
			}
		}
		int compareResult = doctorName.compareTo(root.getDoctors().get(0).getName());
		if (compareResult < 0) {
			return findDoctorByName(root.getLeft(), doctorName);
		} else {
			return findDoctorByName(root.getRight(), doctorName);
		}
	
	}
	
	
	public static void appointmentView(AVLNode root) {
		Scanner scanner=new Scanner(System.in);
		System.out.print("Enter doctor's name: ");
		String docName = scanner.nextLine();
		Doctor selectedDoctor = findDoctorByName(getRoot(), docName);
		if(selectedDoctor!=null)
		{
                if(!selectedDoctor.getAppointments().isEmpty()) {
                System.out.println("Appointments for " + selectedDoctor.getName() + ":");
                for (Appointment apt : selectedDoctor.getAppointments()) {
                    System.out.println("Patient: " + apt.getPatientName() + ", Date: " + apt.getDate() + ", Time: " + apt.getTime());
                }
                }else {
                    System.out.println("No Scheduled Appointments.");
            } 
		}else {
			System.out.println("Invalid Doctor");
		}
            
}
}






public class DoctorAppointmentSystem{
	public static void main(String[] args) {
		int k;
		AVLTree avlTree = new AVLTree();
		Scanner scanner=new Scanner(System.in);

		Doctor d1=new Doctor("Dr Kanak","Orthopedics", "Dr Kanak's Skincare 360 Destination Centre office -13 bwing ,Magarpatta Pune-411028","02046010421");
		avlTree.insert(d1);
		Doctor d2=new Doctor("Dr Rohan Anand","Orthopedics","Add-Skin World Clinic,2nd Floor,Ganga Commerce,Lane 5 corner, N Main Rd, Koregaon Park, Pune, Maharashtra 411001","07720000666");
		avlTree.insert(d2);
		Doctor d3=new Doctor("Dr Amey kelkar","Orthopedics","Shree Niwas Apartments, Dermatologist. Flat No 30, Samarth Rd, opposite Bandhu Mithaiwale, Karve Nagar, Pune, Maharashtra 411052","9170586765");
		avlTree.insert(d3);
		Doctor d4=new Doctor("Dr.Jagjeet Deshmukh","Cardiologist","Sahyadri Super Specialty Hospital Hadapsar SN 163, Bhosale Garden Rd, beside Bhosale Nagar, Hadapsar, Pune, Maharashtra 411028","09099983589");
		avlTree.insert(d4);
		Doctor d5=new Doctor("Dr.Naresh D.Munot","Cardiologist","Plot No. 23, Sanjeevan Hospital, off, Karve Rd, Erandwane, Pune, Maharashtra 411004","09822491953");
		avlTree.insert(d5);
		Doctor d6=new Doctor("Dr Sudipto Dasgupta","Cardiologist","Aurora Towers, 104, West Wing, MG Road, Camp, Pune, Maharashtra 411001","02026140204");
		avlTree.insert(d6);
		Doctor d7=new Doctor("Dr Anup Bende","Nuerologist","Shivam complex, shop no : 5, ground floor, Solapur - Pune Hwy, below sadhana sahakari bank, Hadapsar, Pune, Maharashtra 411028","9156765343");
		avlTree.insert(d7);
		Doctor d8=new Doctor("Dr. BHUSHAN JOSHI","Nuerologist","POONA SUPER SPECIALITY CLINIC* 105,First floor, New Airport Rd, above Dorabjee,Townsqare Shopping Centre, Pune","08484845944");
		avlTree.insert(d8);
		Doctor d9=new Doctor("Dr Nilesh Bhandari","Nuerologist","BHANDARI SPECIALITY CLINIC, Dandekar Pool Signal, 104 : Vrundavan Apartments, Near Golden Wheel Hotel , Indian Oil Petrol Pump Lane, Navi Peth, Off, Pune, Maharashtra 411030","097645 55565");
		avlTree.insert(d9);
		Doctor d10=new Doctor("Dr Apoorva Sahu","Dentist","SIYONA HEIGHTS, Shree Pandharinath Hole Rd, opposite BLOCK-B, Bhosale Nagar, Hadapsar, Pune, Maharashtra 411028","08087726279");
		avlTree.insert(d10);
		Doctor d11=new Doctor("Dr. A M Shaikh","Dentist","95, Ganesh peth, Dulya Maruti Chowk, opp. Ganesh peth police chowky, Laxmi, road, Ganesh Peth, Pune, Maharashtra 411002","020 2634 9267");
		avlTree.insert(d11);
		Doctor d12=new Doctor("Dr Rahul Shah ","Dentist","Ecstasy, Karishma road, above IndusInd Bank, Kothrud, Pune, Maharashtra 411038","09420481441");
		avlTree.insert(d12);
		do {
		System.out.println("Who are you?\n1.Doctor\n2.User");
		int ch=scanner.nextInt();
		switch(ch)
		{
		case 1:
			
				System.out.println("\nDoctor Scheduling System");
				System.out.println("1. Add Doctor");
				System.out.println("2. View Appointment");
				System.out.println("3. View Doctors by Specialization");
				System.out.println("4. Exit");
				System.out.print("Enter your choice: ");
				int choice = scanner.nextInt();
				scanner.nextLine(); // Consume the newline character
				switch (choice) {
				case 1:
					System.out.print("Enter doctor's name: ");
					String doctorName = scanner.nextLine();
					System.out.print("Enter doctor's specialization: ");
					String specialization = scanner.nextLine();
					System.out.print("Enter Address: ");
					String address = scanner.nextLine();
					System.out.print("Enter contact number: ");
					String contactNo = scanner.nextLine();
					Doctor doctor = new Doctor(doctorName, specialization, address,contactNo);
					avlTree.insert(doctor);
					System.out.println(doctor + " has been added.");
					break;
				case 2:

					avlTree.appointmentView(avlTree.getRoot());
					break;
					
				case 3:
					System.out.println("Doctors by Specialization:");
					avlTree.printDoctorsBySpecialization();
					break;
				case 4:
					System.out.println("Exiting the system.");
					scanner.close();
					System.exit(0);
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
				}
				break;


		case 2:
			System.out.println("1. Schedule Appointment");
			System.out.println("2. View Doctors by Specialization");
			System.out.println("3.Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine(); // Consume the newline character
			String patientName;
			String date;
			switch (choice) {


			case 1:
				System.out.print("Enter doctor's name: ");
				String docName = scanner.nextLine();
				Doctor selectedDoctor = avlTree.findDoctorByName(avlTree.getRoot(), docName);
				if (selectedDoctor != null) {
					System.out.print("Enter patient's name: ");
					patientName = scanner.nextLine();
					System.out.print("Enter appointment date (YYYY-MM-DD): ");
					date = scanner.nextLine();
					System.out.println("Prefferable time:");
					String time = scanner.nextLine();
					System.out.print("Enter appointment details: ");
					String details = scanner.nextLine();
				
				Appointment appointment = new Appointment(selectedDoctor, patientName, date, time, details);
                selectedDoctor.addAppointment(appointment);
					System.out.println("Appointment with " + patientName + " scheduled for Dr. " + docName);
				} else {
					System.out.println("Doctor not found.");
				}
				break;
			case 2:
				System.out.println("Doctors by Specialization:");
				avlTree.printDoctorsBySpecialization();
				break;

			}


		}
	
		System.out.println("Enter 1 to go back to Main menu");
		k=scanner.nextInt();
	}while(k==1);
	}
	
}



