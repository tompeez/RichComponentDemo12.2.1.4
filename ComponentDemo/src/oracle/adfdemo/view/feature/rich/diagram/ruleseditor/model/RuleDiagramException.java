package oracle.adfdemo.view.feature.rich.diagram.ruleseditor.model;

public class RuleDiagramException extends RuntimeException {
    /*
    public RuleDiagramException(String string, Throwable throwable, boolean b, boolean b1) {
        super(string, throwable, b, b1);
    }
    */
    public RuleDiagramException(Throwable throwable) {
        super(throwable);
    }

    public RuleDiagramException(String string, Throwable throwable) {
        super(string, throwable);
    }

    public RuleDiagramException(String string) {
        super(string);
    }

    public RuleDiagramException() {
        super();
    }
}
