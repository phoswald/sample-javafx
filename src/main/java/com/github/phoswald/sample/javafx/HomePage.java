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
                    <p>Name: <input type="text" id="input"></p>
                    <p><a href="#" onclick="link1.invoke();">Link 1</a></p>
                    <p><a href="#" id="link2">Link 2</a></p>
                    <p>Message: <span id="output">???</span></p>
                  </body>
                </html>
                """, this::onLoaded);
    }

    private void onLoaded() {
        controller.setInputValue("input", "Stranger");
        controller.addWindowMember("link1", this::onLink1);
        controller.addElementEventListener("link2", "click", this::onLink2);
    }

    private void onLink1() {
        controller.setElementInnerText("output", "Hello, " + controller.getInputValue("input") + "!");
    }

    private void onLink2() {
        controller.setElementInnerHtml("output", "Hallo, <b>" + controller.getInputValue("input") + "</b>!");
    }
}
