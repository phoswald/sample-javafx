package com.github.phoswald.sample.javafx;

import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import com.github.phoswald.sample.Application;
import com.github.phoswald.sample.ApplicationModule;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class WebController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Application application = ApplicationModule.instance().getApplication();

    private Page page = new HomePage(this);

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private Document documentNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("init controller...");
        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                logger.info("init document...");
                documentNode = webEngine.getDocument();
                page.onLoaded();
                logger.info("init document done.");
            }
        });
        logger.info("init controller done.");
        
        page.load();
    }
    
    void loadHtml(String html) {
        webEngine.loadContent(html, "text/html");
    }
    
    void loadUrl(String url) {
        webEngine.load(url);
    }

    void addWindowMember(String name, Runnable runnable) {
        Object windowObject = webEngine.executeScript("window");
        if (windowObject instanceof JSObject windowJSObject) {
            windowJSObject.setMember(name, new Invoker(runnable));
        }
    }

    void addElementEventListener(String elementId, String event, Runnable runnable) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode instanceof EventTarget eventTarget) {
            eventTarget.addEventListener(event, e -> runnable.run(), false);
        }
    }

    void setElementInnerText(String elementId, String text) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode != null) {
            Node childNode;
            while ((childNode = elementNode.getFirstChild()) != null) {
                elementNode.removeChild(childNode);
            }
            elementNode.appendChild(documentNode.createTextNode(text));
        }
    }

//  void setElementInnerHtml(String elementId, String html) {
//      Element elementNode = documentNode.getElementById(elementId);
//      if(elementNode instanceof ElementImpl elementNodeImpl) { // com.sun.webkit.dom.ElementImpl
//          elementNodeImpl.setInnerHTML(html); 
//      }
//  }

    void setElementInnerHtml(String elementId, String html) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode != null) {
            Node childNode;
            while ((childNode = elementNode.getFirstChild()) != null) {
                elementNode.removeChild(childNode);
            }
            Node innerNode = parseHtml(html);
            while ((childNode = innerNode.getFirstChild()) != null) {
                innerNode.removeChild(childNode);
                elementNode.appendChild(childNode);
            }
        }
    }

    String getInputValue(String elementId) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode instanceof HTMLInputElement inputElementNode) {
            return inputElementNode.getValue();
        } else {
            return null;
        }
    }

    private Node parseHtml(String html) {
        try {
//          DOMImplementation domImpl = documentNode.getImplementation();
            DOMImplementation domImpl = DOMImplementationRegistry.newInstance().getDOMImplementation("");
            DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature("LS", "3.0");
            LSParser parser = domImplLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
            LSInput input = domImplLS.createLSInput();
            input.setCharacterStream(
                    new StringReader("<html xmlns='http://www.w3.org/1999/xhtml'><body>" + html + "</body></html>"));
            Document innerDocument = parser.parse(input);
            return cloneNode(innerDocument.getElementsByTagName("body").item(0));

        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    private Node cloneNode(Node inputNode) {
        switch (inputNode.getNodeType()) {
        case Node.ELEMENT_NODE:
            Node outputNode = documentNode.createElementNS(inputNode.getNamespaceURI(), inputNode.getLocalName());
            for (int item = 0; item < inputNode.getChildNodes().getLength(); item++) {
                outputNode.appendChild(cloneNode(inputNode.getChildNodes().item(item)));
            }
            return outputNode;
        case Node.TEXT_NODE:
            return documentNode.createTextNode(inputNode.getTextContent());
        default:
            throw new UnsupportedOperationException();
        }
    }

    public static record Invoker(Runnable runnable) {
        public void invoke() {
            runnable.run();
        }
    }
}
