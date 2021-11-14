import akka.actor.testkit.typed.CapturedLogEvent;
import akka.actor.testkit.typed.javadsl.BehaviorTestKit;
import bot.Bot;
import bot.KnowledgeBase;
import command.*;


import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class Test {

    // Question answer tests
    @org.junit.jupiter.api.Test
    void messageTest() {
        KnowledgeBase base=new KnowledgeBase();
        BehaviorTestKit<Command> testKitBot = BehaviorTestKit.create(Bot.create());
        List<CapturedLogEvent> logEvents;
        testKitBot.run(new StartCommand());
        testKitBot.run(new MessageCommand("What is your name?"));
        testKitBot.run(new AnsweringCommand("What is your name?"));
        testKitBot.run(new BoredCommand());
        logEvents=testKitBot.getAllLogEntries();
        assertEquals("Waiting input.......",logEvents.get(0).message());
        assertEquals("Answering.......",logEvents.get(1).message());
        assertEquals(base.getByKey("What is your name?"),logEvents.get(2).message());
        assertEquals("Bot is bored",logEvents.get(3).message());
        System.out.println(testKitBot.getAllLogEntries());
    }


}
