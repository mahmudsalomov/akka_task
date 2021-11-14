package controller;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import bot.Bot;
import command.*;


public class Controller extends AbstractBehavior<Command> {

    private ActorRef<Command> bot;

    private Controller(ActorContext<Command> context) {
        super(context);
    }

    public static Behavior<Command> create(){
        return Behaviors.setup(Controller::new);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(StartCommand.class,command->{
                    bot = getContext().spawn(Bot.create(),"bot");
                    bot.tell(command);
                    return Behaviors.same();
                })
                .onMessage(MessageCommand.class, command->{
                    bot.tell(command);
                    return Behaviors.same();
                })
                .build();
    }

    public ActorRef<Command> getBot() {
        return bot;
    }
}
