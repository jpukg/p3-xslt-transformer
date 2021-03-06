package eu.fusepool.p3.xslt.transformer.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Iterator;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.clerezza.rdf.core.Graph;
import org.apache.clerezza.rdf.core.Triple;
import org.apache.clerezza.rdf.core.TripleCollection;
import org.apache.clerezza.rdf.core.serializedform.Parser;
import org.apache.clerezza.rdf.core.serializedform.SupportedFormat;
import org.apache.clerezza.rdf.ontologies.RDFS;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.fusepool.p3.xslt.transformer.XsltProcessor;
import eu.fusepool.p3.xslt.transformer.XsltProcessorImpl;

public class XsltProcessorImplTest {
	
	XsltProcessor processor;
	
	private static final Logger log = LoggerFactory.getLogger(XsltProcessorImplTest.class);
	private String locationHeader = "http://localhost:7002"; // location header sent by the client to be used as base uri in triples

	@Before
	public void setUp() throws Exception {
		processor = new XsltProcessorImpl();
	}

	@Test
	public void testProcessTrentinoXml() throws TransformerConfigurationException, FileNotFoundException, 
	                                  TransformerException, IOException {
		InputStream xmlIn = getClass().getResourceAsStream("eventi.xml");
		String xslUrl = getClass().getResource("events-vt.xsl").getFile();
		InputStream rdfIn = processor.processXml(xslUrl, xmlIn, locationHeader);
		Graph graph = Parser.getInstance().parse(rdfIn, SupportedFormat.TURTLE);
		Iterator<Triple> tripleIter = graph.filter(null, RDFS.label, null);
		Assert.assertTrue(tripleIter.hasNext());
		
	}
	
	/**
     * Prints an input stream..
     * @param in
     */
    private void printIntupStream(InputStream in) {
    	StringBuilder text = new StringBuilder();
    	try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	        String line;
	        while((line = reader.readLine()) != null){
	            text.append(line + "\n");
	        }	
    	}
    	catch(IOException ioe){
    		ioe.printStackTrace();
    	}
    	
      	System.out.println(text);
    	
    }
    /**
     * Prints a graph..
     * @param graph
     */
    private void printTripleCollection(TripleCollection graph){
    	if(graph != null & ! graph.isEmpty()) {
	    	int graphSize = graph.size();
	    	Iterator<Triple> graphIter = graph.filter(null, null, null);
	    	while(graphIter.hasNext()){
	    		log.debug(graphIter.next().toString());
	    	}
    	}
    	else {
    		log.debug("The graph doesn't exist or is empty");
    	}
    }
    
    

}
