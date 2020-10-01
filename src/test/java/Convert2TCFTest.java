import junit.framework.TestCase;
import org.w3c.dom.Node;
import org.xmlunit.builder.Input;
import org.xmlunit.xpath.JAXPXPathEngine;

import javax.xml.transform.Source;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Convert2TCFTest extends TestCase {

    private Convert2TCF converter;
    private Map<String, String> namespaces;

    public void setUp() {
        converter = new Convert2TCF();
        namespaces = new HashMap<>();
        namespaces.put("tc", "http://www.dspin.de/data/textcorpus");
        namespaces.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
    }

    // check test.conllu; contains all annotations for all layers
    public void testConvert() throws Exception{
        converter.convert("src/test/resources/test.conllu",
                new FileOutputStream("src/test/resources/test_output.xml"),
                "de");

        Source source = Input.fromFile("src/test/resources/test_output.xml").build();

        checkXMLContents(source, true, true, true, true, true, true, true);


        // delete created test file
        File f = new File("src/test/resources/test_output.xml");
        f.delete();
    }

    // check test_noPOS.conllu; no POS layer
    public void testConvertNoPOS() throws Exception{
        converter.convert("src/test/resources/test_noPOS.conllu",
                new FileOutputStream("src/test/resources/test_noPOS_output.xml"),
                "de");

        Source source = Input.fromFile("src/test/resources/test_noPOS_output.xml").build();

        checkXMLContents(source, true, true, true, false, true, true, true);

        // delete created test file
        File f = new File("src/test/resources/test_noPOS_output.xml");
        f.delete();
    }

    public void testConvertNoLemma() throws Exception{
        converter.convert("src/test/resources/test_noLemma.conllu",
                new FileOutputStream("src/test/resources/test_noLemma_output.xml"),
                "de");

        Source source = Input.fromFile("src/test/resources/test_noLemma_output.xml").build();

        checkXMLContents(source, true, true, false, true, true, true, true);

        // delete created test file
        File f = new File("src/test/resources/test_noLemma_output.xml");
        f.delete();
    }

    public void testConvertNoDep() throws Exception{
        converter.convert("src/test/resources/test_noDep.conllu",
                new FileOutputStream("src/test/resources/test_noDep_output.xml"),
                "de");

        Source source = Input.fromFile("src/test/resources/test_noDep_output.xml").build();

        checkXMLContents(source, true, true, true, true, false, true, true);

        // delete created test file
        File f = new File("src/test/resources/test_noDep_output.xml");
        f.delete();
    }

    public void testConvertNoMorph() throws Exception{
        converter.convert("src/test/resources/test_noMorph.conllu",
                new FileOutputStream("src/test/resources/test_noMorph_output.xml"),
                "de");

        Source source = Input.fromFile("src/test/resources/test_noMorph_output.xml").build();

        checkXMLContents(source, true, true, true, true, true, false, true);

        // delete created test file
        File f = new File("src/test/resources/test_noMorph_output.xml");
        f.delete();
    }

    private void checkXMLContents(Source source, boolean token, boolean sentence,
                                  boolean lemma, boolean pos, boolean dep, boolean morph, boolean text) {

        JAXPXPathEngine engine = new JAXPXPathEngine();
        engine.setNamespaceContext(namespaces);

        // check token number

        Iterable<Node> i = engine.selectNodes("//tc:token", source);
        assertNotNull(i);
        int count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:token", node.getNodeName());
        }
        if (token){
            assertEquals(18, count);
        }else{
            assertEquals(0, count);
        }

        // check sentence number
        i = engine.selectNodes("//tc:sentence", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:sentence", node.getNodeName());
        }
            if (sentence){
                assertEquals(2, count);
            }else{
                assertEquals(0, count);
            }

        // check lemma
        i = engine.selectNodes("//tc:lemma", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:lemma", node.getNodeName());
        }
        if (lemma){
            assertEquals(18, count);
        }else{
            assertEquals(0, count);
        }

        // check POS Tags
        i = engine.selectNodes("//tc:POStags/tc:tag", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:tag", node.getNodeName());
        }
        if (pos){
            assertEquals(18, count);
        }else{
            assertEquals(0, count);
        }

        // check if two dependency parses were created
        i = engine.selectNodes("//tc:parse", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:parse", node.getNodeName());
        }
        if (dep){
            assertEquals(2, count);
        }else{
            assertEquals(0, count);
        }


        // check for morphological layer
        i = engine.selectNodes("//tc:analysis", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:analysis", node.getNodeName());
        }
        if (morph){
            assertEquals(14, count);
        }else{
            assertEquals(0, count);
        }

        // check if text layer exists
        i = engine.selectNodes("//tc:text", source);
        assertNotNull(i);
        count = 0;
        for (Node node : i) {
            count++;
            assertEquals("tc:text", node.getNodeName());
        }
        if (text){
            assertEquals(1, count);
        }else{
            assertEquals(0, count);
        }

    }
}