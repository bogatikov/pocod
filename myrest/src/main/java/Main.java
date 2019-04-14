import base.DBServiceInterface;
import db.DBService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.ArticleServlet;

public class Main {
    public static void main(String[] args) {
        DBServiceInterface dbService = new DBService();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ArticleServlet(dbService)), ArticleServlet.PATH);

        Server server = new Server(8080);
        server.setHandler(context);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
