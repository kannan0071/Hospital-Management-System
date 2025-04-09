package main;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import dao.HospitalServiceImpl;
import entity.Appointment;
import myexceptions.PatientNumberNotFoundException;
import util.DBConnection;

public class MainModule {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Connection con = DBConnection.getConnection();
//		if(con != null) {
//			System.out.println(con);
//			System.out.println("Connected!");
//		}
//		else {
//			System.out.println("Failed to connect");
//		}
		
		Scanner sc = new Scanner(System.in);
		
		HospitalServiceImpl service = new HospitalServiceImpl();
		
		System.out.println("1. Schedule Appointment");
        System.out.println("2. Get Appointment By ID");
        System.out.println("3. Get Appointments For Patient");
        System.out.println("4. Get Appointments For Doctor");
        System.out.println("5. Update Appointment");
        System.out.println("6. Cancel Appointment");
        System.out.print("Provide Option :\t");
        int choice = sc.nextInt();
        switch(choice) {
        	case 1 ->{
        		Appointment appointment = new Appointment();
                System.out.print("Enter Patient ID: ");
                appointment.setPatientId(sc.nextInt());
                System.out.print("Enter Doctor ID: ");
                appointment.setDoctorId(sc.nextInt());
                sc.nextLine(); 
                System.out.print("Enter Appointment Date (yyyy-mm-dd): ");
                appointment.setAppointmentDate(LocalDate.parse(sc.nextLine()));
                System.out.print("Enter Description: ");
                appointment.setDescription(sc.nextLine());

                if (service.scheduleAppointment(appointment))
                    System.out.println("Appointment scheduled successfully.");
                else
                    System.out.println("Failed to schedule appointment.");
        	}
        	case 2 -> {
                try {
                    System.out.print("Enter Appointment ID to find: ");
                    int id = sc.nextInt();
                    Appointment app = service.getAppointmentById(id);
                    if (app != null)
                        System.out.println(app);
                    else
                        System.out.println("Appointment not found.");
                } 
                catch (InputMismatchException ime) {
                    System.out.println("Invalid input for appointment ID.");
                }
            }
        	case 3 -> {
        	    try {
        	        System.out.print("Enter Patient ID: ");
        	        int patId = sc.nextInt();
        	        List<Appointment> appointments = service.getAppointmentsForPatient(patId);
        	        for (Appointment a : appointments) {
        	        	System.out.println(a);
        	        }   
        	    } 
        	    catch (PatientNumberNotFoundException e) {
        	        System.err.println("Patient number not found: " + e.getMessage());
        	    } 
        	    catch (InputMismatchException ime) {
        	        System.err.println("Invalid input for patient ID.");
        	    }
        	}
        	case 4 -> {
                System.out.print("Enter Doctor ID: ");
                int docId = sc.nextInt();
                List<Appointment> appointments = service.getAppointmentsForDoctor(docId);
                for (Appointment a : appointments)
                {
                	System.out.println(a);
                }     
            }
        	case 5 -> {
                Appointment updatedAppointment = new Appointment();
                System.out.print("Enter Appointment ID to update: ");
                updatedAppointment.setAppointmentId(sc.nextInt());
                System.out.print("Enter new Patient ID: ");
                updatedAppointment.setPatientId(sc.nextInt());
                System.out.print("Enter new Doctor ID: ");
                updatedAppointment.setDoctorId(sc.nextInt());
                sc.nextLine(); 
                System.out.print("Enter new Appointment Date (yyyy-mm-dd): ");
                updatedAppointment.setAppointmentDate(LocalDate.parse(sc.nextLine()));
                System.out.print("Enter new Description: ");
                updatedAppointment.setDescription(sc.nextLine());

                if (service.updateAppointment(updatedAppointment)) {
                	System.out.println("Appointment updated successfully.");
                }   
                else
                    System.out.println("Failed to update appointment.");
            }
        	case 6 -> {
                System.out.print("Enter Appointment ID to cancel: ");
                int cancelId = sc.nextInt();
                if (service.cancelAppointment(cancelId)) {
                	System.out.println("Appointment cancelled successfully.");
                }
                else
                    System.out.println("Failed to cancel appointment.");
            }
        	default -> throw new IllegalArgumentException("Invalid Option: " + choice);
        }
	}

}
