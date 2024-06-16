package com.github.phoswald.sample.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLInputElement;

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
                addWindowMember("link1", this::onLink1);
                addElementEventListener("link2", "click", this::onLink2);
                logger.info("init document done.");
            }
        });

        String html = """
                <html>
                  <body>
                    <h1>sample-javafx</h1>
                    <p><a href="http://google.com">Google</a></p>
                    <p>Name: <input type="text" id="input"></p>
                    <p><a href="#" onclick="link1.invoke();">Link 1</a></p>
                    <p><a href="#" id="link2">Link 2</a></p>
                    <p>Message: <span id="output">???</span</p>
                  </body>
                </html>
                """;
        webEngine.loadContent(html, "text/html");
        // webEngine.load("http://google.com");

        logger.info("init controller done.");
    }

    private void addWindowMember(String name, Runnable runnable) {
        Object windowObject = webEngine.executeScript("window");
        if (windowObject instanceof JSObject windowJSObject) {
            windowJSObject.setMember(name, new InvokeRunnable(runnable));
        }
    }

    private void addElementEventListener(String elementId, String event, EventListener listener) {
        Element elementNode = documentNode.getElementById(elementId);
        if (elementNode instanceof EventTarget eventTarget) {
            eventTarget.addEventListener(event, listener, false);
        }
    }

    private void setElementInnerText(String elementId, String text) {
        Element elementNode = documentNode.getElementById(elementId);
        if(elementNode != null) {
            Node childNode;
            while ((childNode = elementNode.getFirstChild()) != null) {
                elementNode.removeChild(childNode);
            }
            elementNode.appendChild(documentNode.createTextNode(text));
        }
    }
    
    private String getInputValue(String elementId) {
        Element elementNode = documentNode.getElementById(elementId);
        if(elementNode instanceof HTMLInputElement inputElementNode) {
            return inputElementNode.getValue();
        } else {
            return null;
        }
    }

    public void onLink1() {
        setElementInnerText("output", "Hello, " + getInputValue("input") + "!");
    }

    public void onLink2(Event evt) {
        setElementInnerText("output", "Hallo, " + getInputValue("input") + "!");
    }

    public static record InvokeRunnable(Runnable runnable) {

        public void invoke() {
            runnable.run();
        }
    }
}
