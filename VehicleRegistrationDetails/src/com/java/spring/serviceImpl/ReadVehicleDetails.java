package com.java.spring.serviceImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.java.spring.output.VehicleType;
import com.java.spring.utils.Constants;

public class ReadVehicleDetails {
	
	/*
	 * This method is used to read the vehicle registration details from the csv file and assert the values
	 * 
	 */
	public static VehicleType[] ReadVehicleRegDetails(String fileName) {
		System.out.println("CSV file to read is : "+fileName);
		ReadVehicleDetails readDetails = new ReadVehicleDetails();
		List<VehicleType> vehTypevalues = readDetails.GetVehicleRegDetails(fileName);
		VehicleType[] type = new VehicleType[vehTypevalues.size()];
		vehTypevalues.toArray(type);		
		return type;
	}
	
	/*
	 * This method is used to retrieve the details from the csv file in the given path
	 * 
	 */
	public List<VehicleType> GetVehicleRegDetails(String csvFile) {
		String[] values = null;
		List<VehicleType> typeList = new ArrayList<VehicleType>();
		try {
			VehicleType type;
			Scanner scanner = new Scanner(new File(Constants.DIRECTORY_PATH+csvFile));
			scanner.next(); // Ignore the column names
			while (scanner.hasNext()) {
				type = new VehicleType();
				String line = (scanner.next());
				values = line.split(Constants.COMMA_DELIMITER);
				type.setRegNo(values[0]);
				type.setModel(values[1]);
				type.setColor(values[2]);
				System.out.println(line);
				typeList.add(type);
			}
			scanner.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return typeList;
	}
}
