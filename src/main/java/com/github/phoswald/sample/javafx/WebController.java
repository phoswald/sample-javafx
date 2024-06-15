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

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
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

        logger.info("configuring...");

        webEngine = webView.getEngine();
        webView.setContextMenuEnabled(false);

        webEngine.getLoadWorker().stateProperty().addListener(new MyStageChangeListener());

        // webEngine.load("http://google.com");
        
        String html = """
                <html>
                  <body>
                    <h1>sample-javafx</h1>
                    <p><a href="http://google.com">Google</a></p>
                    <p><a href="#" onclick="myCallbackObject.doIt1();">Click 1</a></p>
                    <p><a href="#" id="doIt2">Click 2</a></p>
                  </body>
                </html>
                """;
        logger.info("loading HTML");
        webEngine.loadContent(html, "text/html");

        logger.info("configuring done.");
    }

    public class MyStageChangeListener implements ChangeListener<Worker.State> {

        @Override
        public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
            logger.info("state is {}", newValue);
            if (newValue != Worker.State.SUCCEEDED) {
                return;
            } else {
                logger.info("configuring myCallbackObject");
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("myCallbackObject", new MyCallbackObject());
                
                logger.info("configuring doIt2");
                ((EventTarget) webEngine.getDocument().getElementById("doIt2"))
                    .addEventListener("click", new MyEventListener(), false);
            }
        }

    }

    public class MyCallbackObject {

        public void doIt1() {
            logger.info("doIt1() called");
        }
    }
    
    public class MyEventListener implements EventListener {

        @Override
        public void handleEvent(Event evt) {
            logger.info("doIt2() called");
        }
    }
}
