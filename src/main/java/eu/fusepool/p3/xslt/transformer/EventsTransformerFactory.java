/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.fusepool.p3.events.transformer;

import eu.fusepool.p3.transformer.Transformer;
import eu.fusepool.p3.transformer.TransformerFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author reto
 */
public class EventsTransformerFactory implements TransformerFactory {

    private final Map<String, Transformer> data2Transformer = 
            new HashMap<>();
    private final XsltProcessor processor;

    public EventsTransformerFactory() throws IOException {
        this.processor = new XsltProcessorImpl();
    }
    
    @Override
    public Transformer getTransformer(HttpServletRequest request) {
        final String xsltUri = request.getParameter("xslt");
        return getTransfomerFor(xsltUri);
    }

    private synchronized Transformer getTransfomerFor(String xsltUri) {
        if (data2Transformer.containsKey(xsltUri)) {
            return data2Transformer.get(xsltUri);
        }
        final Transformer newTransformer = new EventsTransformer(processor, xsltUri);
        data2Transformer.put(xsltUri, newTransformer);
        return newTransformer;
    }
    
}