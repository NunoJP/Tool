package general.strutures;

import java.util.ArrayList;

public class SfNode {


    private int position;
    private String string;
    private ArrayList<SfNode> childNodes;

    public SfNode(int position, String string) {
        this.position = position;
        this.string = string;
        this.childNodes = new ArrayList<>();
    }

    public int getPosition() {
        return position;
    }

    public String getString() {
        return string;
    }

    public ArrayList<SfNode> getChildNodes() {
        return childNodes;
    }

    public void addChildNode(SfNode sfNode) {
        this.childNodes.add(sfNode);
    }

    public void setString(String substring) {
        this.string = substring;
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public void setChildren(ArrayList<SfNode> nodes) {
        this.childNodes = nodes;
    }

    public void addChildNodeIfNotExists(SfNode sfNode) {
        for (SfNode childNode : childNodes) {
            if(childNode.getString().equalsIgnoreCase(sfNode.getString())) {
                return;
            }
        }
        addChildNode(sfNode);
    }
}
