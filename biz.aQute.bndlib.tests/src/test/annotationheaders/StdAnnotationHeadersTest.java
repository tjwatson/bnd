package test.annotationheaders;

import java.util.jar.Attributes;

import aQute.bnd.osgi.Builder;
import aQute.bnd.osgi.Constants;
import aQute.lib.env.Header;
import aQute.lib.env.Props;
import aQute.lib.io.IO;
import junit.framework.TestCase;

/**
 * This class mirrors the testing from {@link AnnotationHeadersTest} but using
 * the OSGi R7 standard annotations. Annotations and annotated types are present
 * in the test.annotationheaders.xxx.std packages
 */
public class StdAnnotationHeadersTest extends TestCase {

	/**
	 * A directly annotated class
	 */

	public void testStdAnnotations() throws Exception {
		try (Builder b = new Builder();) {
			b.addClasspath(IO.getFile("bin"));
			b.setPrivatePackage("test.annotationheaders.multiple.std");
			b.build();
			assertTrue(b.check());
			b.getJar().getManifest().write(System.out);

			Attributes mainAttributes = b.getJar().getManifest().getMainAttributes();

			Header req = Header.parseHeader(mainAttributes.getValue(Constants.REQUIRE_CAPABILITY));
			Props p = req.get("require");
			assertNotNull(p);
			assertTrue(p.containsKey("filter:"));
			assertTrue(p.get("filter:").contains("(a=b)"));
			assertTrue(p.get("filter:").contains("(&(version>=1.0.0)(!(version>=2.0.0)))"));
			assertTrue(p.get("filter:").contains("(require=Required)"));

			p = req.get("require" + Header.DUPLICATE_MARKER);
			assertNotNull(p);
			assertTrue(p.containsKey("filter:"));
			assertTrue(p.get("filter:").contains("(a=b)"));
			assertTrue(p.get("filter:").contains("(&(version>=2.0.0)(!(version>=3.0.0)))"));
			assertTrue(p.get("filter:").contains("(require=Required2)"));

			Header cap = Header.parseHeader(mainAttributes.getValue(Constants.PROVIDE_CAPABILITY));
			p = cap.get("provide");
			assertNotNull(p);
			assertTrue(p.containsKey("provide"));
			assertEquals("Provided", p.get("provide"));

			p = cap.get("provide" + Header.DUPLICATE_MARKER);
			assertNotNull(p);
			assertTrue(p.containsKey("provide"));
			assertEquals("Provided2", p.get("provide"));

			// TODO for some reason these headers aren't included
			// assertEquals("bar", mainAttributes.getValue("Foo"));
			// assertEquals("buzz", mainAttributes.getValue("Fizz"));

		}
	}

	/**
	 * A Meta annotated class
	 * 
	 * @throws Exception
	 */
	public void testStdAnnotationsMetaAnnotated() throws Exception {
		try (Builder b = new Builder();) {
			b.addClasspath(IO.getFile("bin"));
			b.setPrivatePackage("test.annotationheaders.attrs.std");
			b.build();
			assertTrue(b.check());
			b.getJar().getManifest().write(System.out);

			Attributes mainAttributes = b.getJar().getManifest().getMainAttributes();

			Header req = Header.parseHeader(mainAttributes.getValue(Constants.REQUIRE_CAPABILITY));
			Props p = req.get("require");
			assertNotNull(p);
			assertTrue(p.containsKey("filter:"));
			assertTrue(p.get("filter:").contains("(a=b)"));
			assertTrue(p.get("filter:").contains("(&(version>=1.0.0)(!(version>=2.0.0)))"));
			assertTrue(p.get("filter:").contains("(require=Required)"));

			p = req.get("require" + Header.DUPLICATE_MARKER);
			assertNotNull(p);
			assertTrue(p.containsKey("filter:"));
			assertTrue(p.get("filter:").contains("(a=b)"));
			assertTrue(p.get("filter:").contains("(&(version>=2.0.0)(!(version>=3.0.0)))"));
			assertTrue(p.get("filter:").contains("(require=Required2)"));

			Header cap = Header.parseHeader(mainAttributes.getValue(Constants.PROVIDE_CAPABILITY));
			p = cap.get("provide");
			assertNotNull(p);
			assertTrue(p.containsKey("provide"));
			assertEquals("Provided", p.get("provide"));

			p = cap.get("provide" + Header.DUPLICATE_MARKER);
			assertNotNull(p);
			assertTrue(p.containsKey("provide"));
			assertEquals("Provided2", p.get("provide"));

			// TODO for some reason these headers aren't included
			// assertEquals("bar", mainAttributes.getValue("Foo"));
			// assertEquals("buzz", mainAttributes.getValue("Fizz"));

		}
	}

}
