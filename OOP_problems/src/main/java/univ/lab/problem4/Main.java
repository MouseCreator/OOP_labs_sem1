package univ.lab.problem4;

public class Main {
    public static void main(String[] args) {
        Class<?> birdClass;
        CustomClassLoader loader = new CustomClassLoader();
        try {
            birdClass = loader.findClass("univ.lab.problem4.model.Bird");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        CustomClassDescriptor descriptor = new CustomClassDescriptor();
        String description = descriptor.describe(birdClass);
        System.out.println(description);
    }
}
