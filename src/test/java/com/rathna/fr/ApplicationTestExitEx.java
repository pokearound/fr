package com.rathna.fr;

import static org.junit.Assert.assertTrue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.Assertion;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

public class ApplicationTestExitEx {
	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	@Test()
	public void testMain() {
		exit.expectSystemExitWithStatus(1);
		exit.checkAssertionAfterwards(new Assertion() {
			public void checkAssertion() {
				assertTrue("System.exit expected", true);
			}
		});
		Application.main(new String[] { "-blah", ".foo", "-bar" });
	}
}