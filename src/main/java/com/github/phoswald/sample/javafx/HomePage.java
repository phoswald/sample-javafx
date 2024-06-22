package com.github.phoswald.sample.javafx;

public class HomePage {

    private final WebController controller;

    public HomePage(WebController controller) {
        this.controller = controller;
        
        // controller.loadUrl("https://google.ch/", this::onLoaded);
        controller.loadHtml("""
                <html>
                  <body>
                    <h1>sample-javafx</h1>
                    <p><a href="http://google.com">Google</a></p>
                    <p>Name: <input type="text" id="inputName"></p>
                    <p>HTML: <input type="text" id="inputHtml"></p>
                    <p><a href="#" onclick="processName.invoke();">Link 1</a></p>
                    <p><a href="#" id="processHtml">Link 2</a></p>
                    <p>Message: <span id="output">???</span></p>
                  </body>
                </html>
                """, this::onLoaded);
    }

    private void onLoaded() {
        controller.setInputValue("inputName", "Stranger");
        controller.setInputValue("inputHtml", "<b>Hello, <u>World</u> and <a href=\"http://google.com\">Google</a>!</b>");
        controller.addWindowMember("processName", this::onProcessName);
        controller.addElementEventListener("processHtml", "click", this::onProcessHtml);
    }

    private void onProcessName() {
        controller.setElementInnerText("output", "Hello, " + controller.getInputValue("inputName") + "!");
    }

    private void onProcessHtml() {
        controller.setElementInnerHtml("output", controller.getInputValue("inputHtml"));
    }
}
