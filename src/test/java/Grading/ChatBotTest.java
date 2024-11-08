package Grading;//change this

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ChatBot;

public class ChatBotTest{
    private ChatBot chatBot;
    private static List<String> messages;
    private Field[] fieldNames;
    private static double totalScore=0.0;


    @BeforeClass
    public static void checkClassExists() {
        try {
            Class.forName("ChatBot");//change this to the correct name
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
   
        String find = "chatBotName"; 
        // Loop through the fields in the class using the fieldNames array
        for (Field field : fieldNames) {

            if (field.getName().equals(find)) {

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


    //METHOD TESTING
    @Test//test to check the
    public void  testChatBotConstructor(){//chat Bot contructor testing.....

        try {
        Class<?> chatBotClass= Class.forName("ChatBot");
        // CHANGE THIS LATER ON!!!!! FOR TESTING
        Constructor<?> chatBotConstructor=chatBotClass.getDeclaredConstructor();
        assertNotNull("Default constructor should exist",chatBotConstructor);
        
        Object chatBotInstace=chatBotConstructor.newInstance();

        if(chatBotInstace!=null){

            System.out.println("Chat Bot Default Constructor exists !");
            System.out.println("Assigning Full Marks! - 3 marks.");

            totalScore+=3;
        }

        }catch (ClassNotFoundException e) {
            fail("Class ChatBot not found");
        }catch (NoSuchMethodException e) {
           fail("Default constructor not found in ChatBot class");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail("Constructor did not work as expected: " + e.getMessage());
        }
    
    }

    @Test
    public void testChatBotOverloadedConstructor() {
        try {
            Class<?> chatBotClass = Class.forName("ChatBot");

            Constructor<?> overloadedConstructor = chatBotClass.getDeclaredConstructor(int.class);
            assertNotNull("Overloaded constructor with (String, int) should exist",overloadedConstructor);

            Object chatBotInstace=overloadedConstructor.newInstance(5);

            if(chatBotInstace!=null){

            System.out.println("Chat Bot Default Constructor exists !");
            System.out.println("Assigning Full Marks! - 3 marks.");

            totalScore+=3;
            }

        }catch (ClassNotFoundException e) {
            fail("Class ChatBot not found");
        }catch (NoSuchMethodException e) {
           fail("Overloaded constructor not found in ChatBot class");
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            fail("Overloaded Constructor did not work as expected: " + e.getMessage());
        }
    }


    @Test
    public void testGetChatBotName(){
        System.out.println("----------------------------------------------------------\n");
        String chatBotName=chatBot.getChatBotName();
        if(chatBotName instanceof String){
            System.out.println("Method getChatBotName Works Correctly- 1 marks\n");
            totalScore+=1;
        }else{
            messages.add("getChatBotName does not return a string");
            System.out.println("getChatBotName does not return a string- 0 marks\n");
        }
        System.out.println("----------------------------------------------------------\n");

    }


    @Test
    public void testgetNumResponsesGenerated(){

        System.out.println("----------------------------------------------------------\n");

        Object number=chatBot.getNumResponsesGenerated();

        if(number instanceof Integer){//Supposed to return 0;
            System.out.println("Method getNumResponsesGenerated Works Correctly- 1 marks\n");
            totalScore+=1;
        }else{
            messages.add("Method getNumResponsesGenerated  does not return an integer");
            System.out.println("Method getNumResponsesGenerated does not return an integer- 0 marks\n");
        }
        System.out.println("----------------------------------------------------------\n");



    }

    @Test
    public void testgetTotalNumResponsesGenerated(){

        System.out.println("----------------------------------------------------------\n");

        Object number=chatBot.getTotalNumResponsesGenerated();

        if(number instanceof Integer){//Supposed to return 0;
            System.out.println("Method getTotalNumResponsesGenerated() Works Correctly- 2 marks\n");
            totalScore+=2;
        }else{
            messages.add("Method getTotalNumResponsesGenerated()  does not return an integer");
            System.out.println("Method getTotalNumResponsesGenerated() does not return an integer- 0 marks\n");
        }
        System.out.println("----------------------------------------------------------\n");


    }


    
    @Test
    public void testgetTotalNumMessagesRemaining(){

        System.out.println("----------------------------------------------------------\n");

        Object number=chatBot.getTotalNumMessagesRemaining();

        if(number instanceof Integer){//if number is an integer
        try {
            Integer num = (Integer)number;
            Integer result=10-chatBot.getNumResponsesGenerated();

            assertEquals(result, num);//check to see if it is correct
            System.out.println("The Method getNumResponsesGenerated() Works Correctly- 3 marks " );
            totalScore+=3;

            }catch (AssertionError e) {
                System.out.println("The Method getNumResponsesGenerated() Does not return the correct output- 0 marks" + e.getMessage());
                messages.add("The Method getNumResponsesGenerated() Does not return the correct output- 0 marks");
            }

        messages.add("The Method getNumResponsesGenerated() Does not return an integer- 0 marks");
        }
    }

    @AfterClass
    public static void printTotalScore() {
        System.out.println("Total score after all tests: " + totalScore);
    }
}



