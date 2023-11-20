package univ.lab.problem4;

public class Main {
    public static void main(String[] args) {
        ClassDescriptor descriptor = new ClassDescriptor();
        System.out.println(descriptor.getClass().getClassLoader());
    }
}
