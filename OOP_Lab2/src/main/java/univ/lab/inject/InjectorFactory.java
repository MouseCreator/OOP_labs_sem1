package univ.lab.inject;

import univ.lab.fill.FillableCreator;
import univ.lab.regular.ConfigurationReader;

import javax.xml.parsers.SAXParserFactory;

public class InjectorFactory {
    public Injector fromConfiguration(String filename){
        ConfigurationReader configurationReader = new ConfigurationReader();
        ConfigurationManager manager = configurationReader.parse(filename);
        manager.getInjector().addImplementation(FillableCreator.class, manager.getFillableCreator());
        manager.getInjector().addImplementation(SAXParserFactory.class, SAXParserFactory.newInstance());
        return manager.getInjector();
    }
}
