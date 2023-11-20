package univ.lab.problem4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomClassLoaderTest {

    @Test
    void findClass() {
        CustomClassLoader loader = new CustomClassLoader();
        String name = "javax.net.SocketFactory";
        Class<?> aClass;
        try {
            aClass = loader.findClass(name);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(aClass);
        assertEquals(name, aClass.getName());
    }
}