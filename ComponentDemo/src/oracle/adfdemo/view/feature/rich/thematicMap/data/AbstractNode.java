package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.awt.Color;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;

import java.util.Set;

import oracle.adf.view.faces.bi.component.hierarchyViewer.Node;

import oracle.xml.parser.v2.XMLElement;

public abstract class AbstractNode implements Comparable {


    public static String TYPE_MAJOR_NODE_STANDARD = "MajorNodeStandard";
    public static String TYPE_MAJOR_NODE_SUMMARY = "MajorNodeSummary";
    public static String TYPE_GROUP_NODE = "GroupNode";
    public static String TYPE_FIXED_NODE = "FixedNode";
    public static String TYPE_SUMMARY_GROUP_NODE = "SummaryGroupNode";
    public static String TYPE_SUMMARY_ITEM_NODE = "SummaryItemNode";

    public static String GROUP_BY = "groupBy";
    public static String COLOR_MAP = "colorMap";
    public static String LEVEL = "level";

    public static String SLICE_LABEL = "sliceLabel";
    public static String SUMMARY_TYPE = "summaryType";
    public static String GROUP_FILTER = "groupOptions";
    public static String RELATIONSHIP_FILTER = "relationshipOptions";
    public static String HIGHLIGHT_OPTIONS = "highlightOptions";

    protected List<AbstractNode> childElements = null;

    protected AbstractNode _parentElement = null;

    private int level = 0;

    public abstract boolean isTopNode();

    public abstract String getType();

    private String stampType = "Default";

    public String getStampType() {
        return stampType;
    }

    public void setStampType(String stampType) {
        this.stampType = stampType;
    }

    public void setHighlightOptions(HighlightOptions ho) {
        options.put(HIGHLIGHT_OPTIONS, ho);
    }

    public HighlightOptions getHighlightOptions() {
        HighlightOptions b = (HighlightOptions)options.get(HIGHLIGHT_OPTIONS);
        if (b == null) {
            HighlightOptions ops = new HighlightOptions();
            setHighlightOptions(ops);
            b = ops;
        }
        return b;
    }

    public abstract boolean isHighlighted();


    public int getLevelVal() {
        return level;
    }

    public int getLevel() {
        int l = 0;
        if (_parentElement != null && !_isAnchor) {
            l = _parentElement.getLevelVal() + 1;
        }
        setLevel(l);
        return l;
    }


    public void setLevel(int level) {
        this.level = level;
    }

    public Color getColorFor(String key) {
        return getColorMapping().get(key);
    }

    public Map<String, Color> getColorMapping() {
        return ((Map<String, Color>)options.get(COLOR_MAP));
    }

    public AbstractNode getParent() {
        if (_parentElement != null) {
            return _parentElement;
        }
        return fetchParent();
    }

    private void setParent(AbstractNode n) {
        this._parentElement = n;
    }

    public abstract AbstractNode fetchParent();

    public void createStructure(int levels) {
        getChildElements();
        int l = levels - 1;
        if (l == 0) {
            return;
        } else {
            if (childElements == null)
                return;
            for (AbstractNode n : childElements) {
                n.createStructure(l);
            }
        }
    }

    public void createStructure() {
        getChildElements();
        if (childElements == null)
            return;
        for (AbstractNode n : childElements) {
            n.createStructure();
        }
    }

    public List getChildElements() {
        if (childElements == null) {
            createChildren();
        }

        return childElements;
    }

    protected boolean isNotGrouping() {
        String groupBy = getGroupBy();
        return (groupBy == null);
    }

    protected String getGroupBy() {
        return (String)options.get(GROUP_BY);
    }

    protected boolean isGrouping() {
        return !isNotGrouping();
    }

    public void createChildren() {
        generateChildren();

        if (childElements != null) {
            for (AbstractNode n : childElements) {
                n.setOptions(this.options);
                n.setParent(this);
                n.setAnchor(false);
                if (isGrouping() && n instanceof MajorNode) {
                    n.createChildren();
                    //((ISummaryNode)n).generateSummary();
                }
            }
        }
        //if (isGrouping() && this instanceof ISummaryNode) {
        //    ((ISummaryNode)this).generateSummary();
        //}

    }

    public abstract void generateChildren();

    public void clearChildElements() {
        childElements = null;
    }

    boolean _isAncestorNode = false;

    public boolean isAncestorNode() {
        return _isAncestorNode;
    }

    public void setAncestorNode(boolean bValue) {
        _isAncestorNode = bValue;
    }

    boolean _isAnchor = false;

    public void setAnchor(boolean b) {
        _isAnchor = b;
        _isAncestorNode = false;
        if (_isAnchor) {
            setLevel(0);
            childElements = null;
        }
    }

    protected Map<String, Object> options = new HashMap<String, Object>();

    public void setSliceLabel(String label) {
        options.put(SLICE_LABEL, label);
    }


    public String getSliceLabel() {
       
        return (String) options.get(SLICE_LABEL);
    }
    

    public void setGroupBy(String groupBy) {
        options.put(GROUP_BY, groupBy);
    }

    public void setNoGrouping() {
        options.put(GROUP_BY, null);
    }

    protected void setOptions(Map<String, Object> options) {
        this.options = options;
    }

    public void addOption(String key, Object value) {
        options.put(key, value);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public boolean isHasChildren() {
        getChildElements();
        return (childElements != null) && childElements.size() > 0;
    }


    public int getChildCount() {
        if (childElements == null)
            getChildElements();
        return childElements.size();
    }
    
    public int getFullChildCount() {
        if (childElements == null) {
            return 0;
        } else {
            int total = childElements.size();
            for (AbstractNode n : childElements) {
                total += n.getFullChildCount();
            }
            return total;
        }
    }

    public void addToGroupFilter(List<String> filter) {

        if (getGroupFilter() != null) {
            getGroupFilter().addAll(filter);
        } else {
            Set<String> set = new HashSet<String>();
            set.addAll(filter);
            setGroupFilter(set);
        }
    }

    public void removeFromGroupFilter(List<String> toRemove) {
        if (getGroupFilter() != null) {
            getGroupFilter().removeAll(toRemove);
        }
    }

    public void addToRelFilter(List<String> filter) {

        if (getRelationshipFilter() != null) {
            getRelationshipFilter().addAll(filter);
        } else {
            Set<String> set = new HashSet<String>();
            set.addAll(filter);
            setRelationshipFilter(set);
        }
    }

    public void removeFromRelFilter(List<String> toRemove) {

        if (getRelationshipFilter() != null) {
            getRelationshipFilter().removeAll(toRemove);
        }
    }


    protected Set<String> getRelationshipFilter() {
        Set<String> filter = (Set<String>)options.get(RELATIONSHIP_FILTER);
        if (filter == null) {
            Set<String> empty = new HashSet<String>();
            setRelationshipFilter(empty);
            filter = empty;
        }
        return filter;
    }

    protected Set<String> getGroupFilter() {
        Set<String> filter = (Set<String>)options.get(GROUP_FILTER);
        if (filter == null) {
            Set<String> empty = new HashSet<String>();
            setGroupFilter(empty);
            filter = empty;
        }
        return filter;
    }

    public Set<String> retrieveGroupFilter() {
        Set<String> groupFilter = getGroupFilter();
        Set<String> groupOptions = getGroupsOptions();
        if (groupOptions != null) {
            List<String> toRemove = new ArrayList<String>();
            for (String s : groupFilter) {
                if (!groupOptions.contains(s)) {
                    toRemove.add(s);
                }
            }
            removeFromGroupFilter(toRemove);
//            groupFilter.removeAll(toRemove);
        }
        return groupFilter;
    }

    public Set<String> retrieveRelationshipFilter() {
        Set<String> relationshipFilter = getRelationshipFilter();
        Set<String> groupOptions = getRelationshipOptions();
        if (groupOptions != null) {
            List<String> toRemove = new ArrayList<String>();
            for (String s : relationshipFilter) {
                if (!groupOptions.contains(s)) {
                    toRemove.add(s);
                }
            }
            removeFromRelFilter(toRemove);
//            relationshipFilter.removeAll(toRemove);
        }
        return relationshipFilter;
    }

    private void setGroupFilter(Set<String> set) {
        options.put(GROUP_FILTER, set);
    }

    private void setRelationshipFilter(Set<String> set) {
        options.put(RELATIONSHIP_FILTER, set);
    }

    protected Set<String> _relationshipOptions = new HashSet<String>();
    protected Set<String> _groupsOptions = new HashSet<String>();

    protected Set<String> _relationshipDepOptions = new HashSet<String>();
    protected Set<String> _groupsDepOptions = new HashSet<String>();


    public Set<String> getGroupsOptions() {
        return _groupsOptions;
    }

    public Set<String> getRelationshipOptions() {
        return _relationshipOptions;
    }


    public int getDepCount() {
        return _groupsDepOptions.size() + _relationshipDepOptions.size();
    }

    public int getRelationshipMatchCount() {
        int count = 0;

        Set<String> linkFilter = getGroupFilter();
        for (String rel : linkFilter) {
            if (_groupsDepOptions.contains(rel)) {
                count++;
            }
        }
        linkFilter = getRelationshipFilter();
        for (String rel : linkFilter) {
            if (_relationshipDepOptions.contains(rel)) {
                count++;
            }
        }

        return count;
    }

    public int getFilterCount() {
        return getGroupFilter().size() + getRelationshipFilter().size();
    }

    public Collection<AbstractNode> retrieveAllCurrentChildren(boolean recursive) {
        Collection<AbstractNode> results = new ArrayList<AbstractNode>();
        if (hasCurrentChildren()) {
            results.addAll(childElements);
            
            if (recursive) {
                for (AbstractNode e : childElements) {
                    results.addAll(e.retrieveAllCurrentChildren(recursive));
                }
            }

        }
        return results;
    }

    public boolean hasCurrentChildren() {
        return (childElements != null && childElements.size() > 0);
    }

    public void setColorMapping(Map<String, Color> colorMapping) {
        options.put(COLOR_MAP, colorMapping);
    }


    public Set<String> getDependantOptions() {
        Set<String> results = new HashSet<String>();
        results.addAll(_relationshipDepOptions);
        results.addAll(_groupsDepOptions);
        return results;

    }

    public Set<String> getFilterItems() {
        Set<String> results = new HashSet<String>();
        results.addAll(retrieveRelationshipFilter());
        results.addAll(retrieveGroupFilter());
        return results;
    }

}
