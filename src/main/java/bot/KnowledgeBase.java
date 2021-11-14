package bot;


import java.util.*;

// for answers
public class KnowledgeBase {

    private final Map<String,String> answer;
    private final List<String> bored;
    public KnowledgeBase() {
        answer=new HashMap<>();
        answer.put("Who made you?","I was made by a programmer named Mahmud.");
        answer.put("What is your name?","My name is bot");
        answer.put("When is your birthday?","14.11.2021");
        answer.put("What purpose were you made for?","I was created because of the assignment given by IVOICE ASIA");
        answer.put("Do you believe in a god?","I consider the programmer who made me to be a god :)");
        answer.put("How smart are you?","I have enough knowledge to be able to answer only "+(answer.size()+1)+" different questions");
        bored=List.of(
                "I can answer a lot of questions, ask any question : ",
                "Maybe you can ask me a question : ",
                "You can ask me a question : "
        );
    }

    public String getByKey(String key){
        String result = answer.get(key);
        if (result==null) return "Sorry, I don't know enough to answer this question :(";
        return result;
    }

    public String getRandomBored(){
        return bored.get(new Random().nextInt(bored.size()));
    }

    public Map<String, String> getAnswer() {
        return answer;
    }

    public List<String> getBored() {
        return bored;
    }
}
