package oracle.adfdemo.view.feature.rich.thematicMap.data;

public class Link {
    
    public static String REL = "rel";
    public static String GROUP = "group";
    
    private String type;
    private int id;
    private String rel1;
    private String rel2;

    public Link(String type, int id, String rel1, String rel2) {
        super();
        this.type = type;
        this.id = id;
        this.rel1 = rel1;
        this.rel2 = rel2;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setRel1(String rel1) {
        this.rel1 = rel1;
    }

    public String getRel1() {
        return rel1;
    }

    public void setRel2(String rel2) {
        this.rel2 = rel2;
    }

    public String getRel2() {
        return rel2;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
