package com.ocs.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ocs.api.model.PayloadObject;
import com.ocs.api.model.ResponseObject;
import com.ocs.model.Location;
import com.ocs.model.Position;
import com.ocs.opr.Executer;

public class ExecuterTest {

	@Test
	void testScenario1() {

		String[][] terrain = { { "Fe", "Fe", "Se" }, { "W", "Si", "Obs" }, { "W", "Obs", "Zn" } };

		String[] commands = { "F", "S", "R", "F", "S", "R", "F", "L", "F", "S" };

		Position initialPosition = new Position() {
			{
				setFacing("East");
				setLocation(new Location(0, 0));
			}
		};

		var jsonObj = new PayloadObject() {
			{
				setTerrain(terrain);
				setBattery(50);
				setCommands(commands);
				setInitialPosition(initialPosition);
			}
		};

		List<Location> visitedCells = new ArrayList<Location>() {
			{
				add(new Location(0, 0));
				add(new Location(1, 0));
				add(new Location(1, 1));
				add(new Location(0, 1));
				add(new Location(0, 2));
			}
		};

		Position finalPosition = new Position() {
			{
				setFacing("South");
				setLocation(new Location(0, 2));
			}
		};

		List<String> samples = new ArrayList<String>() {
			{
				add("Fe");
				add("Si");
				add("W");
			}
		};

		var expectedResponse = new ResponseObject() {
			{
				setVisitedCells(visitedCells);
				setBattery(8);
				setSamplesCollected(samples);
				setFinalPosition(finalPosition);
			}
		};

		var executer = new Executer();

		var response = executer.run(jsonObj);
		
		assertEquals(expectedResponse.getSamplesCollected(), response.getSamplesCollected());
		assertEquals(expectedResponse.getBattery(), response.getBattery());
		assertEquals(expectedResponse.getFinalPosition().getFacing(), response.getFinalPosition().getFacing());
	}


	@Test
	void testScenario2() {
		
		String[][] terrain = { { "Fe", "Fe", "Se" }, { "W", "Si", "Obs" }, { "W", "Obs", "Zn" } };

		String[] commands = { "S", "F", "S", "R", "F", "S", "F", "S" };

		Position initialPosition = new Position() {
			{
				setFacing("East");
				setLocation(new Location(0, 0));
			}
		};

		var jsonObj = new PayloadObject() {
			{
				setTerrain(terrain);
				setBattery(50);
				setCommands(commands);
				setInitialPosition(initialPosition);
			}
		};

		List<Location> visitedCells = new ArrayList<Location>() {
			{
				add(new Location(0, 0));
				add(new Location(1, 0));
				add(new Location(1, 1));
				add(new Location(0, 1));
			}
		};

		Position finalPosition = new Position() {
			{
				setFacing("West");
				setLocation(new Location(0, 1));
			}
		};

		List<String> samples = new ArrayList<String>() {
			{
				add("Fe");
				add("Fe");
				add("Si");
				add("W");
			}
		};

		var expectedResponse = new ResponseObject() {
			{
				setVisitedCells(visitedCells);
				setBattery(14);
				setSamplesCollected(samples);
				setFinalPosition(finalPosition);
			}
		};
		
		var executer = new Executer();

		var response = executer.run(jsonObj);
		
		assertEquals(expectedResponse.getSamplesCollected(), response.getSamplesCollected());
		assertEquals(expectedResponse.getBattery(), response.getBattery());
		
	}
}