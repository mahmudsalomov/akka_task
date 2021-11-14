import akka.actor.typed.ActorSystem;
import command.Command;
import command.MessageCommand;
import command.StartCommand;
import controller.Controller;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        ActorSystem<Command> actorSystem=ActorSystem.create(Controller.create(),"Controller");
        actorSystem.tell(new StartCommand());
        while (true){
            String message = scanner.nextLine();
            actorSystem.tell(new MessageCommand(message));
        }

    }
}
