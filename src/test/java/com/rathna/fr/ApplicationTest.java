package com.rathna.fr;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ApplicationTestSimple.class, ApplicationTestRtEx.class, ApplicationTestExitEx.class })
public class ApplicationTest {
}