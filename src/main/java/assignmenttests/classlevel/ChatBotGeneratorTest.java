package assignmenttests.classlevel;

import assignmentevaluator.classloader.AssignmentClassLoader;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.lang.reflect.Method;

public class ChatBotGeneratorTest {
    String classFilePath = "C:\\Users\\felix\\Downloads\\Project Src\\ASSIGNMENT 1\\Assignment_1\\ChatBotGenerator.class";
    AssignmentClassLoader testLoader = new AssignmentClassLoader(); //class loader

    boolean correct = true; //to validify method outputs
    int i, marks = 0; //for interation and mark tracking

    @Test
    public void testLoadClass(){
        //checking for successful class loading
        try{
            Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);
            System.out.println("Loading class from path...");

            System.out.println("Class loaded successfully: " + loadedClass.getName());

        }catch(IOException e){
            System.err.println("IOException: Check if the file path is correct.");
        }catch(Exception e){
            System.err.println("An unexpected error occurred.");
        }
    }

    @Test
    public void testLoadMethod(){
        //checking to see if required method exists, has correct name, and has correct return type
        try{
            Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);

            Method method = loadedClass.getDeclaredMethods()[0]; //this was my workaround this method since there's supposed to be only one method in this class

            //checking to see if existing method name matches the expected name
            if(method.getName().equals("generateChatBotLLM")){
                System.out.println("Method name is correct: generateChatBotLLM");
            }else{
                System.out.println("Method name is incorrect. Expected 'generateChatBotLLM', but got: " + method.getName());
                correct = false;
            }

            //checking to see if existing method return type is String
            if(method.getReturnType().toString().equals("class java.lang.String")){
                System.out.println("Return type is correct: String");
            } else{
                System.out.println("Return type is incorrect. Expected String, but got: " + method.getReturnType());
                correct = false;
            }

        } catch(Exception e){
            System.err.println("An unexpected error occurred.");
        }
    }

    protected String invokeGenerateChatBotLLM(Class<?> loadedClass, int input) throws Exception{
        //'helper method' (ty stack overflow) to call generateChatBotLLM method with the required inputs
        Method method = loadedClass.getDeclaredMethods()[0];
        Object instance = loadedClass.getDeclaredConstructor().newInstance(); //creating an instance of the loaded class (again, ty stack overflow)
        return(String) method.invoke(instance, input); //calling the method and returning the result as a string
    }

    //verifying if generateChatBotLLM method produces correct output for each input
    @Test
    public void testGenerateChatBotLLMs_Success()throws Exception{
        Class<?> loadedClass = testLoader.loadClassFromFile(classFilePath);

        String[] expectedLLMs = {"LLaMa", "Mistral7B", "Bard", "Claude", "Solar"};

        //loop to check inputs 1 to 5
        for(i = 1; i <= 5; i++){
            String result = invokeGenerateChatBotLLM(loadedClass, i);
            //if output != expected value, set correct to false
            if(!result.equals(expectedLLMs[i-1])){
                correct = false;
                System.out.println("Input " + i + "isn't correct. Expected " + expectedLLMs[i-1] + ", but got " + result);
                break;
            }
        }

        //also validifying the default cases
        int[] defaultLLMInputs = {0, 6, -1};
        for(int input : defaultLLMInputs){
            String result = invokeGenerateChatBotLLM(loadedClass, input);
            if(!result.equals("ChatGPT-3.5")){
                correct = false;
                System.out.println("Input " + i + " isn't correct. Expected " + expectedLLMs[i-1] + ", but got " + result);
                break;
            }
        }

        //if all tests passed then award full marks
        if(correct){
            marks += 7;
            System.out.println("All LLM names for inputs 1 to 5, and the default cases were correct. Marks awarded: " + marks);
        } else{
            System.out.println("Either some or none of the LLM names were incorrect. L. No marks awarded: " + marks);
        }
    }
}
