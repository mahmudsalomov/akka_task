package bot;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import command.*;

import java.time.Duration;

public class Bot extends AbstractBehavior<Command> {

    // Bot bored time
    private final int defaultBoringTime=30;
    private final KnowledgeBase base=new KnowledgeBase();
    private final String TIMER_KEY="boredTime";
    private Bot(ActorContext<Command> context) {
        super(context);
    }

    // Creating a new behavior
    public static Behavior<Command> create(){
        return Behaviors.setup(Bot::new);
    }


    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class, command-> waiting())
                .onMessage(MessageCommand.class,command-> messaging(command.getMessage()))
                .onMessage(AnsweringCommand.class,command-> answering(command.getMessage()))
                .onMessage(BoredCommand.class,command-> bored())
                .build();
    }


    // Waiting state for input
    public Behavior<Command> waiting(){
        getContext().getLog().info("Waiting input.......");
        System.out.println("Please, ask any questions : ");
        return boring();
    }

    // When a question is entered, search for the answer
    public Behavior<Command> messaging(String message){
        return Behaviors.withTimers(timer->{
            System.out.println();
            getContext().getLog().info("Answering.......");
            timer.cancelAll();
            timer.startSingleTimer(new AnsweringCommand(message), Duration.ofSeconds(1));
            return Behaviors.same();
        });
    }

    // Answer
    public Behavior<Command> answering(String message){
        String answer = base.getByKey(message);
        getContext().getLog().info(answer);
        System.out.println(answer);
        System.out.println();
        System.out.println("Please, ask any questions : ");
        return boring();
    }

    // When the bot is bored
    public Behavior<Command> bored(){
//        System.out.println();
        getContext().getLog().info("Bot is bored");
        System.out.println(base.getRandomBored());
        return waiting();
    }

    // Helper method
    private Behavior<Command> boring(){
        return Behaviors.withTimers(timer->{
            timer.startTimerAtFixedRate(TIMER_KEY,new BoredCommand(), Duration.ofSeconds(defaultBoringTime));
            return Behaviors.same();
        });
    }

}
