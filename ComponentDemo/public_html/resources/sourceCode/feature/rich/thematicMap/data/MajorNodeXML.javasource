package oracle.adfdemo.view.feature.rich.thematicMap.data;

import java.awt.Color;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;

import java.util.Set;

import oracle.adfdemo.view.feature.rich.thematicMap.XMLTreeProvider;

import oracle.adfdemo.view.feature.rich.hv.util.HVUtils;

import oracle.xml.parser.v2.XMLElement;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public abstract class MajorNodeXML extends MajorNode<MajorNodeXML> {

    XMLTreeProvider _xmlProvider;
    XMLElement _ele;


    public MajorNodeXML(XMLTreeProvider xmlProvider, XMLElement ele) {
        _xmlProvider = xmlProvider;
        _ele = ele;
    }

    public MajorNodeXML(XMLTreeProvider xmlProvider, XMLElement ele,
                        boolean isAnchor) {
        _xmlProvider = xmlProvider;
        _ele = ele;
        _isAnchor = isAnchor;
    }

    public MajorNodeXML fetchParent() {


        switch (_xmlProvider.getType()) {
        case STRUCTURED:
            XMLElement parentElement = _xmlProvider.getParentElement(_ele);
            if (parentElement != null)
                return createNew(_xmlProvider, parentElement);
            else
                return null;
        case FLAT:
            return null;
        case GROUP:
            parentElement = _xmlProvider.getParentElement(_ele);
            if (parentElement != null)
                return createNew(_xmlProvider, parentElement);
            else
                return null;
        }
        return null;

    }

    public void constructChildNodes() {
        NodeList nodelist = _ele.getChildNodes();

        majorChildElements = new ArrayList<MajorNodeXML>();
        Node node;
        int nodeListLengh = nodelist == null ? 0 : nodelist.getLength();
        for (int i = 0; i < nodeListLengh; i++) {
            node = nodelist.item(i);
            if (node instanceof XMLElement) {
                majorChildElements.add(createNew(_xmlProvider,
                                                 (XMLElement)node, false));
            }
        }

        //For anchor node, we need to add it's real parent to it's children
        if (this._isAnchor) {
            XMLElement parent = _xmlProvider.getParentElement(_ele);
            if (parent != null) {
                MajorNodeXML em = createNew(_xmlProvider, parent);
                em.setAncestorNode(true);
                majorChildElements.add(em);
            }
        }

    }

    @Override
    public boolean isTopNode() {

        switch (_xmlProvider.getType()) {
        case STRUCTURED:
            XMLElement parent = _xmlProvider.getParentElement(_ele);
            if (parent != null) {
                return false;
            } else
                return true;
        case FLAT:
            return this._isAnchor;
        case GROUP:
            parent = _xmlProvider.getParentElement(_ele);
            if (parent != null) {
                return false;
            } else
                return true;
        }
        return true;
    }

    public void generateMajorChildren() {
        switch (_xmlProvider.getType()) {
        case STRUCTURED:

            if (majorChildElements != null)
                return;

            if (this._isAncestorNode) {
                //For ancestor node, we only return it's real parent as it's only child
                if (this.isTopNode())
                    return;
                else {
                    majorChildElements = new ArrayList<MajorNodeXML>();
                    MajorNodeXML em =
                        createNew(_xmlProvider, _xmlProvider.getParentElement(_ele));
                    em.setAncestorNode(true);
                    majorChildElements.add(em);
                    return;
                }
            }

            constructChildNodes();
            

            break;
        case FLAT:

            if (majorChildElements != null)
                return;
            int myId = Integer.parseInt(getNodeId());
            List<Link> xmlRels = _xmlProvider.getRelationships(myId);
            List<Link> xmlGroups = _xmlProvider.getGroups(myId);

            assignRelationshipColors(xmlRels);
            assignRelationshipColors(xmlGroups);


            majorChildElements = new ArrayList<MajorNodeXML>();
            Map<Integer, List<Link>> relationships =
                new HashMap<Integer, List<Link>>();

            for (Link rel : xmlRels) {
                int otherId = rel.getId();
                if (relationships.containsKey(otherId)) {
                    relationships.get(otherId).add(rel);
                } else {
                    List<Link> list = new ArrayList<Link>();
                    list.add(rel);
                    relationships.put(otherId, list);
                }
            }

            for (Link group : xmlGroups) {
                int otherId = group.getId();
                if (relationships.containsKey(otherId)) {
                    relationships.get(otherId).add(group);
                } else {
                    List<Link> list = new ArrayList<Link>();
                    list.add(group);
                    relationships.put(otherId, list);
                }

            }

            for (Integer pid : relationships.keySet()) {
                XMLElement otherPerson = _xmlProvider.getPersonById(pid);
                MajorNodeXML node = createNew(_xmlProvider, otherPerson);
                for (Link l : relationships.get(pid)) {
                    if (l.getType().equals(Link.REL)) {
                        node.addDependantRel(l.getRel1(), this);
                        _relationshipOptions.add(l.getRel1());
                    } else {
                        node.addDependantGroup(l.getRel1(), this);
                        _groupsOptions.add(l.getRel1());
                    }
                }

                majorChildElements.add(node);
            }

            break;
        case GROUP:
            if (majorChildElements != null)
                return;

            if (this._isAncestorNode) {
                //For ancestor node, we only return it's real parent as it's only child
                if (this.isTopNode())
                    return;
                else {
                    majorChildElements = new ArrayList<MajorNodeXML>();
                    MajorNodeXML em =
                        createNew(_xmlProvider, _xmlProvider.getParentElement(_ele));
                    em.setAncestorNode(true);
                    majorChildElements.add(em);
                    return;
                }
            }

            constructChildNodes();
            break;
        }


    }

    public abstract MajorNodeXML createNew(XMLTreeProvider xmlProvider,
                                           XMLElement elem);

    public abstract MajorNodeXML createNew(XMLTreeProvider xmlProvider,
                                           XMLElement ele, boolean isAnchor);

    @Override
    public String getAttribute(String p) {
        return _ele.getAttribute(p);
    }

    public abstract String getNodeId();

    private void addDependantRel(String rel, MajorNodeXML n) {
        this._parentElement = n;
        this._relationshipDepOptions.add(rel);
    }

    private void addDependantGroup(String group, MajorNodeXML n) {
        this._parentElement = n;
        this._groupsDepOptions.add(group);
    }

    private void assignRelationshipColors(List<Link> links) {
        Set<String> relationships = new HashSet<String>();
        for (Link l : links) {
            relationships.add(l.getRel1());
        }
        Map<String, Color> colorMapping = getColorMapping();
        if (colorMapping == null) {
            colorMapping = new HashMap<String, Color>();
            options.put(COLOR_MAP, colorMapping);
        }
        for (String g : relationships) {
            if (!colorMapping.containsKey(g)) {
                Color c = HVUtils.colors[colorMapping.keySet().size()];
                colorMapping.put(g, c);
            }
        }
    }

}
