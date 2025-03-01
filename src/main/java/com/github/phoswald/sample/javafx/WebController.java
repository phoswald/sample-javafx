package com.github.phoswald.sample.javafx;

import java.io.StringReader;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import com.github.phoswald.sample.Application;
import com.github.phoswald.sample.ApplicationModule;

import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class WebController implements Initializable {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Application application = ApplicationModule.instance().getApplication();

    @FXML
    private Button homeButton;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private Document documentNode;
    
    private Runnable loadCallback;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homeButton.setOnAction(this::onHome);
        logger.info("Initializing...");
        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                documentNode = webEngine.getDocument();
                loadCallback.run();
                logger.info("Loading done.");
            }
        });
        logger.info("Initializing done.");
        ApplicationModule.instance().getDefaultPage().accept(this); // expected to call loadHtml() or loadUrl()
    }

    private void onHome(ActionEvent event) {
        logger.info("Home button fired.");
        application.updateScene("/fxml/home.fxml");
    }
    
    void loadHtml(String html, Runnable callback) {
        logger.info("Loading HTML (size {})...", html.length());
        loadCallback = callback;
        webEngine.loadContent(html, "text/html");
    }
    
    void loadUrl(String url, Runnable callback) {
        logger.info("Loading URL={} ...", url);
        loadCallback = callback;
        webEngine.load(url);
    }

    void addWindowMember(String name, Runnable callback) {
        Object windowObject = webEngine.executeScript("window");
        if (windowObject instanceof JSObject windowJSObject) {
            windowJSObject.setMember(name, new Invoker(callback));
        }
    }

    void addElementEventListener(String elementId, String event, Runnable callback) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode instanceof EventTarget eventTarget) {
            eventTarget.addEventListener(event, e -> callback.run(), false);
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

    void setInputValue(String elementId, String value) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode instanceof HTMLInputElement inputElementNode) {
            inputElementNode.setValue(value);
        }
    }

    private Node parseHtml(String html) {
        try {
//          DOMImplementation domImpl = documentNode.getImplementation();
            DOMImplementation domImpl = DOMImplementationRegistry.newInstance().getDOMImplementation("");
            DOMImplementationLS domImplLS = (DOMImplementationLS) domImpl.getFeature("LS", "3.0");
            LSInput input = domImplLS.createLSInput();
            input.setCharacterStream(new StringReader("<html xmlns='http://www.w3.org/1999/xhtml'><body>" + html + "</body></html>"));
            LSParser parser = domImplLS.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
            Document innerDocument = parser.parse(input);
            return cloneNode(innerDocument.getElementsByTagName("body").item(0));

        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    private Node cloneNode(Node node) {
        switch (node.getNodeType()) {
        case Node.ELEMENT_NODE:
            Element result = documentNode.createElementNS(node.getNamespaceURI(), node.getLocalName());
            NamedNodeMap attributes = node.getAttributes();
            for (int item = 0; item < attributes.getLength(); item++) {
                Node attribute = attributes.item(item);
                result.setAttributeNS(attribute.getNamespaceURI(), attribute.getLocalName(), attribute.getNodeValue());
            }
            NodeList childNodes = node.getChildNodes();
            for (int item = 0; item < childNodes.getLength(); item++) {
                result.appendChild(cloneNode(node.getChildNodes().item(item)));
            }
            return result;
        case Node.TEXT_NODE:
            return documentNode.createTextNode(node.getTextContent());
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
