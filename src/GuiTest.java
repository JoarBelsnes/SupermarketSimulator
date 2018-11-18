import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.jupiter.api.Assertions.*;


public class GuiTest {


   /** Fixture initialization (common initialization
    *  for all tests). **/
   @BeforeAll
   public void setUp() {
   }


   /** A test that always fails. **/
   @Test public void defaultTest() {
      assertEquals(0, 1);
   }
}
