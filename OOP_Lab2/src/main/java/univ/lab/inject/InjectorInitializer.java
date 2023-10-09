package univ.lab.inject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class InjectorInitializer {
    public Injector initialize(String configurationFile) {
        InjectorImpl injector = new InjectorImpl();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(configurationFile));
            NodeList injectionList = document.getElementsByTagName("injection");
            for (int i = 0; i < injectionList.getLength(); i++) {
                Node node = injectionList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String interfacePath = element.getAttribute("interface");
                    String classPath = element.getAttribute("class");

                    Class<?> interfaceClass = Class.forName(interfacePath);
                    Class<?> implementationClass = Class.forName(classPath);
                    injector.addImplementation(interfaceClass, implementationClass);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return injector;
    }
}
