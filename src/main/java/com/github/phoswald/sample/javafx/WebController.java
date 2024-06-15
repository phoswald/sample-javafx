package com.github.phoswald.sample.javafx;

import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logger.info("init controller...");

        webView.setContextMenuEnabled(false);
        webEngine = webView.getEngine();
        webEngine.getLoadWorker().stateProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                logger.info("init document...");
                addWindowMember("doIt1", this::onDoIt1);
                addEventListener("doIt2", "click", this::onDoIt2);
                logger.info("init document done.");
            }
        });

        String html = """
                <html>
                  <body>
                    <h1>sample-javafx</h1>
                    <p><a href="http://google.com">Google</a></p>
                    <p><a href="#" onclick="doIt1.invoke();">Click 1</a></p>
                    <p><a href="#" id="doIt2">Click 2</a></p>
                  </body>
                </html>
                """;
        webEngine.loadContent(html, "text/html");
        // webEngine.load("http://google.com");

        logger.info("init controller done.");
    }

    private void addEventListener(String elementId, String event, EventListener listener) {
        ((EventTarget) webEngine.getDocument().getElementById(elementId)).addEventListener(event, listener, false);
    }

    private void addWindowMember(String name, Runnable callback) {
        ((JSObject) webEngine.executeScript("window")).setMember(name, new InvokeRunnable(callback));
    }

    public void onDoIt1() {
        logger.info("doIt1() called");
    }

    public void onDoIt2(Event evt) {
        logger.info("doIt2() called");
    }

    public static record InvokeRunnable(Runnable runnable) {

        public void invoke() {
            runnable.run();
        }
    }
}
