package com.java.spring.test;

import org.junit.Test;

import com.java.spring.output.VehicleType;
import com.java.spring.serviceImpl.ReadVehicleDetails;
import com.java.spring.utils.Constants;

import junit.framework.Assert;

public class ReadVehicleDetailsTest {
	@SuppressWarnings("deprecation")
	@Test
	public void ReadVehicleRegDetails_success()
	{
		VehicleType[] type = ReadVehicleDetails.ReadVehicleRegDetails("VehicleRegDetails.csv");
		Assert.assertEquals(Constants.MAKE_AUDI, type[0].getModel());
		Assert.assertEquals(Constants.COLOR_BLUE, type[1].getColor());
	}
}
