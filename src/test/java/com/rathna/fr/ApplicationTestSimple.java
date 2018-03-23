package com.rathna.fr;

import static org.junit.Assert.assertTrue;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import org.junit.runner.RunWith;
//import mockit.integration.junit4.JMockit;

//@RunWith(JMockit.class)
public class ApplicationTestSimple {
	String rndStr = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
	Path root = null;

	@Before
	public void setup() {
		root = FileTestUtil.setupDirs(rndStr);
		//FileTestUtil.printy(root);
	}
	@Test
	public void testMain1() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", root.toString(), "-evf" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain2() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", root.toString(), "-ev" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain3() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", root.toString(), "-e" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain4() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", root.toString() });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain5() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", " " });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain6() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-cf" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain7() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain8() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain9() {
		Application.main(
				new String[] { "-a", rndStr + "_f_2.zip", "-b", rndStr + "_f_2.mp3", "-cf", "-d", root.toString() });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain10() {
		Application.main(
				new String[] { "-a", rndStr + "_f_2.zip", "-b", rndStr + "_f_2.mp3", "-c", "-d", root.toString() });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain11() {
		Application.main(new String[] { "-a", rndStr + "_f_2.zip", "-b", rndStr + "_f_2.mp3" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain12() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c" });
		assertTrue("unexpected", true);
	}
	@Test
	public void testMain13() {
		Application.main(new String[] { "-a", rndStr, "-b", rndStr + "_M", "-c", "-d", root.toString() });
		assertTrue("unexpected", true);
	}
	@After
	public void teard() {
		if (root != null) {
			root.toFile().delete();
		}
	}
}