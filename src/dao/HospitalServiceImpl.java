package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import entity.Appointment;
import myexceptions.PatientNumberNotFoundException;
import util.DBConnection;

public class HospitalServiceImpl implements IHospitalService{
	
	private Connection con;
	
	public HospitalServiceImpl() {
		// TODO Auto-generated constructor stub
		con = DBConnection.getConnection();
	}

	@Override
	public Appointment getAppointmentById(int appointmentId) {
		// TODO Auto-generated method stub
		Appointment appointment = null;
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from appointment where appointmentId=?");
			pstmt.setInt(1, appointmentId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				appointment = new Appointment(rs.getInt("appointmentId"),rs.getInt("patientId"),rs.getInt("doctorId"),rs.getDate("appointmentDate").toLocalDate(),rs.getString("description"));
			}
		}
		catch(SQLException e) {
			System.err.println("Error getting appointment by id: " + e.getMessage());
		}
		return appointment;
	}

	@Override
	public List<Appointment> getAppointmentsForPatient(int patientId) throws PatientNumberNotFoundException {
		// TODO Auto-generated method stub
		List<Appointment> appointments = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from appointment where patientId = ?");
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
            	appointments.add(new Appointment(rs.getInt("appointmentId"),rs.getInt("patientId"),rs.getInt("doctorId"),rs.getDate("appointmentDate").toLocalDate(),rs.getString("description")));
            }
            if (appointments.isEmpty()) {
                throw new PatientNumberNotFoundException("No appointments found for patient ID: " + patientId);
            }
		}
		catch(SQLException e) {
			System.err.println("Error getting appointment for patient: " + e.getMessage());
		}
		return appointments;
	}

	@Override
	public List<Appointment> getAppointmentsForDoctor(int doctorId) {
		// TODO Auto-generated method stub
		List<Appointment> appointments = new ArrayList<>();
		try {
			PreparedStatement pstmt = con.prepareStatement("select * from appointment where doctorId = ?");
            pstmt.setInt(1, doctorId);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
            	appointments.add(new Appointment(rs.getInt("appointmentId"),rs.getInt("patientId"),rs.getInt("doctorId"),rs.getDate("appointmentDate").toLocalDate(),rs.getString("description")));
            }
		}
		catch(SQLException e) {
			System.err.println("Error getting appointment for doctor: " + e.getMessage());
		}
		return appointments;
	}

	@Override
	public boolean scheduleAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into appointment (patientId, doctorId, appointmentDate, description) VALUES (?, ?, ?, ?)");
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            pstmt.setString(4, appointment.getDescription());
            pstmt.executeUpdate();
            System.out.println("Appointment scheduled successfully.");
            return true;
		}
		catch(SQLException e) {
			System.err.println("Error scheduling appointment: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateAppointment(Appointment appointment) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = con.prepareStatement("update appointment set patientId = ?, doctorId = ?, appointmentDate = ?, description = ? where appointmentId = ?");
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, Date.valueOf(appointment.getAppointmentDate()));
            pstmt.setString(4, appointment.getDescription());
            pstmt.setInt(5, appointment.getAppointmentId());
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                //System.out.println("Appointment updated successfully.");
                return true;
            }
		}
		catch (SQLException e) {
            System.err.println("Error updating appointment: " + e.getMessage());
        }
		return false;
	}

	@Override
	public boolean cancelAppointment(int appointmentId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = con.prepareStatement("delete from appointment where appointmentId = ?");
            pstmt.setInt(1, appointmentId);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                //System.out.println("Appointment cancelled successfully.");
                return true;
            }
		}
		catch (SQLException e) {
            System.err.println("Error cancelling appointment: " + e.getMessage());
        }
		return false;
	}
}
