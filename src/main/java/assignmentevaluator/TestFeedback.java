package assignmentevaluator;

import assignmenttests.TestLevels;
import assignmenttests.TestType;

public interface TestFeedback {
    String getTestType(); //gets the type of Test
    String getTestLevel();
    void setTestLevel(TestLevels testLevel);
    void setTestType(TestType testType);
}
