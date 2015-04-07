package sk.stuba.fiit.ms.input;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

final class Util {

    private Util() {}

    public static boolean isNode(final Node node, final String name) {
        return node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(name);
    }

    public static String getAttrValue(final Node node, final String attr) {
        return node.getAttributes().getNamedItem(attr).getTextContent();
    }

    public static String getChildValue(final Node node, final String name) {
        final NodeList childNodes = node.getChildNodes();

        String value = "";

        for (int i = 0; i < childNodes.getLength(); i++) {
            if (isNode(childNodes.item(i), name)) {
                value = childNodes.item(i).getTextContent();
                break;
            }
        }

        return value;
    }

}
