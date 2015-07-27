package com.tauracs.cubepuzzle.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base test class for setting the Log level to info
 *
 */
public class TestBase {
	
	public TestBase() {
		Logger.getGlobal().setLevel(Level.INFO);
	}

}
