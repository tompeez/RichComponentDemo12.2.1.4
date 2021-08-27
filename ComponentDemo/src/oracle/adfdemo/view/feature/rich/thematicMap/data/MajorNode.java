package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import oracle.adfdemo.view.feature.rich.hv.util.HVUtils;

public abstract class MajorNode<T extends MajorNode> extends AbstractNode {

    protected List<T> majorChildElements = null;


    @Override
    public String getType() {
        if (isNotGrouping()) {
            return TYPE_MAJOR_NODE_STANDARD;
        } else {
            return TYPE_MAJOR_NODE_SUMMARY;
        }
    }

    @Override
    public void generateChildren() {
        if (isNotGrouping()) {
            generateStandardChildren();
        } else {
            generateGroupedChildren();
        }
    }

    public abstract String getAttribute(String p);

    public void generateStandardChildren() {
        generateMajorChildren();
        childElements = new ArrayList<AbstractNode>();
        childElements.addAll(majorChildElements);
    }

    public abstract void generateMajorChildren();

    public void generateGroupedChildren() {
        generateMajorChildren();
        if (majorChildElements != null) {

            Map<String, List<MajorNode>> m =
                new HashMap<String, List<MajorNode>>();
            for (T e : majorChildElements) {

                if (e.isAncestorNode()) {
                    continue;
                }

                String value = e.getAttribute(getGroupBy());

                if (m.containsKey(value)) {
                    m.get(value).add(e);
                } else {
                    List<MajorNode> l = new ArrayList<MajorNode>();
                    l.add(e);
                    m.put(value, l);
                }

            }

            List<GroupNode> groupNodes = new ArrayList<GroupNode>();
            for (String k : m.keySet()) {
                List<MajorNode> children = m.get(k);
                GroupNode gn =
                    new GroupNode(getGroupBy(), k);
                gn.setGroupElements(children);
                groupNodes.add(gn);
            }

            assignColors(groupNodes);
            childElements = new ArrayList<AbstractNode>();
            childElements.addAll(groupNodes);
        }

    }

    public int getMajorChildCount() {
        return majorChildElements.size();
    }

    public abstract Set<String> getAttributeListing();
    
    private void assignColors(List<GroupNode> children) {
        Set<String> groups = new HashSet<String>();
        for (GroupNode e : children) {
            groups.add(e.getGroupName());
        }
        Map<String, Color> colorMapping = getColorMapping();
        if (colorMapping == null) {
            colorMapping = new HashMap<String, Color>();
            options.put(COLOR_MAP, colorMapping);
        }
        for (String g : groups) {
            if (!colorMapping.containsKey(g)) {
                Color c = HVUtils.colors[colorMapping.keySet().size()];
                colorMapping.put(g, c);
            }
        }
    }
}