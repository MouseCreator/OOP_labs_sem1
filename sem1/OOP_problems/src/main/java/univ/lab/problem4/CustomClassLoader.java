package univ.lab.problem4;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class CustomClassLoader extends ClassLoader{
    @Override
    public Class<?> findClass(String classname) throws ClassNotFoundException {
        byte[] data = loadData(classname);
        if (data == null) {
            throw new ClassNotFoundException("Cannot load class " + classname);
        }
        return defineClass(classname, data, 0, data.length);
    }

    private byte[] loadData(String classname) {
        return  (classname.startsWith("java")) ?
             systemClass(classname) : customClass(classname);
    }

    private byte[] customClass(String classname) {
        String filename = classname.replace('.', File.separatorChar) + ".class";
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                filename)) {
            byte[] buffer;
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            int nextValue;
            if (inputStream == null) {
                return null;
            }
            try {
                while ((nextValue = inputStream.read()) != -1) {
                    byteStream.write(nextValue);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = byteStream.toByteArray();
            return buffer;
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] systemClass(String className) {
        try {
            Class<?> systemClass = Class.forName(className);
            try(InputStream inputStream = systemClass.getResourceAsStream(
                    '/' + className.replace('.', '/') + ".class")) {
                if (inputStream != null) {
                    try (ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {
                        int data;
                        while ((data = inputStream.read()) != -1) {
                            buffer.write(data);
                        }
                        return buffer.toByteArray();
                    }
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            return null;
        }

        return null;
    }
}
