import controller.Controller;
import model.Model;
import view.View;

public class Runner {

    public static void main(String[] args) {

        Controller controller = new Controller();
        View view = new View(controller);
        Model model = new Model();

        controller.setView(view);
       // controller.setModel(model);
        model.setView(view);
    }

}
