package mouse.project.lib.web.tomcat;

public class TomcatT {
    public static void main(String[] args) {
        TomcatLauncher launcher = new TomcatLauncher();
        try {
            launcher.launch(TomcatT.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
