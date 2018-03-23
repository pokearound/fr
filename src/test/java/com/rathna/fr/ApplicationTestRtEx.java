package com.rathna.fr;

import org.junit.Test;

public class ApplicationTestRtEx {
	@Test(expected = RuntimeException.class)
	public void testMain() {
		Application.main(new String[] { "-a", ".jpg", "-b", "_M.jpg", "-c", "-d", "some_crazy_invalid_dir" });
	}
}