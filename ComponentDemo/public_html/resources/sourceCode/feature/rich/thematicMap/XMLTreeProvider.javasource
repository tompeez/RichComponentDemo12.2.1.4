package oracle.adfdemo.view.feature.rich.thematicMap;


import java.io.IOException;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;

import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;


import oracle.adfdemo.view.feature.rich.thematicMap.data.Link;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;


public class XMLTreeProvider {

    private XMLTreeProvider.Type findType(XMLElement root) {
        String rootTagName = root.getNodeName();
        if (rootTagName.equals("employee")) {
            return Type.STRUCTURED;
        } else if (rootTagName.equals("data")) {
            return Type.FLAT;
        } else if (rootTagName.equals("group")) {
            return Type.GROUP;
        }
        return Type.STRUCTURED;
    }

    public enum Type { STRUCTURED, FLAT, GROUP };

    XMLElement xmlRoot = null;

    private Type type;

    XMLTreeProvider(String xmlFile) {
        xmlRoot = getRootElement(xmlFile);
        type = findType(xmlRoot);
    }

    public XMLElement getRoot() {
        return xmlRoot;
    }

    private XMLDocument getDocument(String xmlFile) {

        XMLDocument doc = null;

        try {
            DOMParser parser = new DOMParser();

            parser.showWarnings(true);

            InputStream is = null;
            Object externalContext =
                FacesContext.getCurrentInstance().getExternalContext().getContext();
            if (externalContext instanceof ServletContext) {
                is = this.getClass().getResourceAsStream(xmlFile);
                if (is != null) {
                    parser.parse(is);
                    // Obtain the document
                    doc = parser.getDocument();
                }
            }

        } catch (IOException ioe) {

            ioe.printStackTrace();

        } catch (SAXException saxe) {

            saxe.printStackTrace();

        }

        return doc;

    }

    private XMLElement getRootElement(String xmlFile) {
        XMLDocument xmldoc = getDocument(xmlFile);
        if (xmldoc != null) {
            return (XMLElement)xmldoc.getDocumentElement();
        }
        return null;
    }

    public XMLElement getPersonById(int id) {

        //System.out.println("NODE1: "+getRoot().getChildrenByTagName("employees").item(0).getNodeName());
        //System.out.println("NODE2: "+((XMLElement) getRoot().getChildrenByTagName("employees").item(0)).getChildrenByTagName("employee").getLength());

        NodeList people =((XMLElement) getRoot().getChildrenByTagName("employees").item(0)).getChildrenByTagName("employee");
        Node node;
        int nodeListLengh = people == null ? 0 : people.getLength();
        for (int i = 0; i < nodeListLengh; i++) {
            node = people.item(i);
            //System.out.println("NODE: "+node);
            if (node instanceof XMLElement) {
                int pid =
                    Integer.parseInt(((XMLElement)node).getAttribute("PersonId"));
                if (pid == id) {
                    return (XMLElement)node;
                }
            }
        }
        return null;
    }

    public List<Link> getGroups(int id) {
        List<Link> xmlGroups = new ArrayList<Link>();
        NodeList groups = ((XMLElement) getRoot().getChildrenByTagName("groups").item(0)).getChildrenByTagName("group");
        
        int groupsCount = groups == null ? 0 : groups.getLength();
        for (int i = 0; i < groupsCount; i++) {
            Node group = groups.item(i);
            NodeList members = ((XMLElement) group).getElementsByTagName("member");
            String groupName = ((XMLElement) group).getAttribute("name");
            int membersCount = members == null ? 0 : members.getLength();
                boolean isMember = false;

            List<Link> groupMembers = new ArrayList<Link>();
            for (int j = 0; j < membersCount; j++) {
                Node member = members.item(j);
                if (member instanceof XMLElement) {
                    XMLElement memberNode = (XMLElement) member;
                    int pid = Integer.parseInt(memberNode.getAttribute("id"));
                    if (pid == id) {
                        isMember = true;
                    } else {
                        groupMembers.add(new Link(Link.GROUP, pid, groupName, groupName));
                    }
                }
            }
            if (isMember) {
                xmlGroups.addAll(groupMembers);
            }
            
        }
        return xmlGroups;
    }
    
    public List<Link> getRelationships(int id) {
        List<Link> xmlRelationships = new ArrayList<Link>();
        NodeList relGroups = ((XMLElement) getRoot().getChildrenByTagName("relationships").item(0)).getChildrenByTagName("rel-group");
        
        int groupsCount = relGroups == null ? 0 : relGroups.getLength();
        for (int i = 0; i < groupsCount; i++) {
            Node relGroup = relGroups.item(i);
            int owner = Integer.parseInt(((XMLElement)relGroup).getAttribute("owner"));
            String childIs = ((XMLElement)relGroup).getAttribute("childIs");
            String ownerIs = ((XMLElement)relGroup).getAttribute("ownerIs");

            NodeList people = ((XMLElement) relGroup).getElementsByTagName("person");

            int membersCount = people == null ? 0 : people.getLength();
            for (int j = 0; j < membersCount; j++) {
                Node member = people.item(j);
                if (member instanceof XMLElement) {
                    XMLElement memberNode = (XMLElement) member;
                    int pid = Integer.parseInt(memberNode.getAttribute("id"));
                    if (owner == id) {
                        xmlRelationships.add(new Link(Link.REL, pid, childIs, ownerIs));
                    }
                    
                    if (pid == id) {
                        xmlRelationships.add(new Link(Link.REL, owner, ownerIs, childIs));
                        break;
                    }
                }
            }
        }
        return xmlRelationships;
    }
    
    public XMLElement getParentElement(XMLElement ele) {
        Node parentNode = ele.getParentNode();

        if (parentNode == null)
            return null;

        if (parentNode instanceof XMLDocument)
            return null;

        if (parentNode instanceof XMLElement) {
            XMLElement parentElement = (XMLElement)parentNode;
            return parentElement;
        }

        return null;
    }
    

    public XMLTreeProvider.Type getType() {
        return type;
    }

}


