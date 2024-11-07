package Grading;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;//change this

public class ChatBotTest{
    private ChatBot chatBot;
    private static List<String> messages;
    private Field[] fieldNames;
    private static double totalScore=0.0;


    @BeforeClass
    public static void checkClassExists() {
        try {
            Class.forName("test2.ChatBot");//change this to the correct name
            boolean classExists = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Class ChatBot does not exist. Skipping all tests.");
            boolean classExists = false;
            // Assume class exists to conditionally ignore tests
            Assume.assumeTrue("Class ChatBot is required for these tests.", classExists);
            messages.add("ChatBot class does not exist");
            totalScore=0.0;
        }
    }

    @Before
    public void setUp() {
        chatBot = new ChatBot();
        messages = new ArrayList<>();
        fieldNames = chatBot.getClass().getDeclaredFields();  // Get the fields of ChatBot class
    }


//attribute testing
    @Test
    public void testChatBotName() {
        // Test if the ChatBot name is not null
        // System.out.println("--Testing if Chat Bot name exists\n");
        String find = "chatBotName"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {

            if (field.getName().equals(find)) {

                fieldExists = true;
                System.out.println("--Chat Bot Name Attribute exists\n");

                if(field.getType().equals(String.class)){
                    System.out.println("--Chat Bot Name is of correct Type.\n");
                    System.out.println("Allocating full marks!\n");
                    totalScore++;

                    return;

                }

                totalScore=totalScore+0.5;
                messages.add("--numResponsesGenerated Attribute is not of correct type");//adding a message
                return;
            }
        }
        messages.add("--Chat Bot Name Attribute does not exist");
    }

    @Test
    public void testnumResponsesGenerated(){
        String find = "numResponsesGenerated"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if wer find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--numResponsesGenerated Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--numResponsesGenerated is of correct Type.\n");
                    System.out.println("Allocating full marks!\n");

                    totalScore++;//allocate the full marks
                    return;

                }
                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the attribute

                messages.add("--numResponsesGenerated Attribute is not of correct type");
                return;
            }
        }
        messages.add("--numResponsesGenerated Attribute is not static ");
    } 


    @Test
    public void testMessageLimit(){
        String find = "messageLimit"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if we find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--messageLimit Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--messageLimit is of correct Type.\n");

                    if (Modifier.isStatic(field.getModifiers())){
                        System.out.println("messageLimit attribute is static.");//test if it is static
                        System.out.println("Allocating full marks!\n");
                        totalScore+=2;//allocate full marks
                        return;

                    }

                    System.out.println("messageLimit attribute is not static.");//test if it is static
                    messages.add("--messageLimit Attribute is not static");
                    totalScore+=1;//allocate something but not full marks
                    return;

                }

                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the type
                messages.add("--messageLimit Attribute is not of correct type");
                return;
            }
        }
        messages.add("--messageLimit Attribute does not exist ");
    }
    
    
    @Test
    public void testMessageNumber(){
        String find = "messageNumber"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if we find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--messageNumber Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--messageNumber is of correct Type.\n");

                    if (Modifier.isStatic(field.getModifiers())){
                        System.out.println("messageNumber attribute is static.");//test if it is static
                        System.out.println("Allocating full marks!\n");
                        totalScore+=3;//allocate full marks
                        return;

                    }

                    System.out.println("messageNumber attribute is not static.");//test if it is static
                    messages.add("--messageNumber Attribute is not of correct type");
                    totalScore+=2;//allocate something but not full marks
                    return;

                }

                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the type
                messages.add("--messageNumber Attribute is not of correct type");
                return;
            }
        }
        messages.add("--messageNumber Attribute does not exist ");

    }

    @AfterClass
    public static void printTotalScore() {
        System.out.println("Total score after all tests: " + totalScore);
    }
}





public class ChatBotTest{
    private ChatBot chatBot;
    private List<String> messages;
    private Field[] fieldNames;
    private static double totalScore=0.0;


    @BeforeClass
    public static void checkClassExists() {
        try {
            Class.forName("ChatBot");
            boolean classExists = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Class ChatBot does not exist. Skipping all tests.");
            boolean classExists = false;
            // Assume class exists to conditionally ignore tests
            Assume.assumeTrue("Class ChatBot is required for these tests.", classExists);
            messages.add("ChatBot class does not exist");
            totalScore=0.0;
        }
    }

    @Before
    public void setUp() {
        chatBot = new ChatBot();
        messages = new ArrayList<>();
        fieldNames = chatBot.getClass().getDeclaredFields();  // Get the fields of ChatBot class
    }


//attribute testing
    @Test
    public void testChatBotName() {
        // Test if the ChatBot name is not null
        // System.out.println("--Testing if Chat Bot name exists\n");
        String find = "chatBotName"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {

            if (field.getName().equals(find)) {

                fieldExists = true;
                System.out.println("--Chat Bot Name Attribute exists\n");

                if(field.getType().equals(String.class)){
                    System.out.println("--Chat Bot Name is of correct Type.\n");
                    System.out.println("Allocating full marks!\n");
                    totalScore++;

                    return;

                }

                totalScore=totalScore+0.5;
                messages.add("--numResponsesGenerated Attribute is not of correct type");//adding a message
                return;
            }
        }
        messages.add("--Chat Bot Name Attribute does not exist");
    }

    @Test
    public void testnumResponsesGenerated(){
        String find = "numResponsesGenerated"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if wer find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--numResponsesGenerated Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--numResponsesGenerated is of correct Type.\n");
                    System.out.println("Allocating full marks!\n");

                    totalScore++;//allocate the full marks
                    return;

                }
                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the attribute

                messages.add("--numResponsesGenerated Attribute is not of correct type");
                return;
            }
        }
        messages.add("--numResponsesGenerated Attribute is not static ");
    } 


    @Test
    public void testMessageLimit(){
        String find = "messageLimit"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if we find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--messageLimit Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--messageLimit is of correct Type.\n");

                    if (Modifier.isStatic(field.getModifiers())){
                        System.out.println("messageLimit attribute is static.");//test if it is static
                        System.out.println("Allocating full marks!\n");
                        totalScore+=2;//allocate full marks
                        return;

                    }

                    System.out.println("messageLimit attribute is not static.");//test if it is static
                    messages.add("--messageLimit Attribute is not static");
                    totalScore+=1;//allocate something but not full marks
                    return;

                }

                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the type
                messages.add("--messageLimit Attribute is not of correct type");
                return;
            }
        }
        messages.add("--messageLimit Attribute does not exist ");
    }
    
    
    @Test
    public void testMessageNumber(){
        String find = "messageNumber"; 
        boolean fieldExists = false;

        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {
            if (field.getName().equals(find)) {//if we find the name of the attribute within the fields of the class
                fieldExists = true;
                System.out.println("--messageNumber Attribute exists.\n");

                if(field.getType().equals(int.class)){//check to see if it is of correct type
                    
                    System.out.println("--messageNumber is of correct Type.\n");

                    if (Modifier.isStatic(field.getModifiers())){
                        System.out.println("messageNumber attribute is static.");//test if it is static
                        System.out.println("Allocating full marks!\n");
                        totalScore+=3;//allocate full marks
                        return;

                    }

                    System.out.println("messageNumber attribute is not static.");//test if it is static
                    messages.add("--messageNumber Attribute is not of correct type");
                    totalScore+=2;//allocate something but not full marks
                    return;

                }

                totalScore=totalScore+0.5;//allocate half of the marks since the name is correct but not the type
                messages.add("--messageNumber Attribute is not of correct type");
                return;
            }
        }
        messages.add("--messageNumber Attribute does not exist ");

    }

    @AfterClass
    public static void printTotalScore() {
        System.out.println("Total score after all tests: " + totalScore);
    }
}



