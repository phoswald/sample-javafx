package com.github.phoswald.sample.javafx;

class HomePage implements Page {

    private final WebController controller;

    HomePage(WebController controller) {
        this.controller = controller;
    }
    
    @Override
    public void load() {
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
                """);
    }

//    public void load() {
//        controller.loadUrl("https://google.ch/");        
//    }

    @Override
    public void onLoaded() {
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
