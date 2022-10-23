import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import utils.DBUtils;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"stepdefs"},
        plugin = {"pretty", "html:target/cucumber-reports.html"},
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterMethod
    public void cleanDirtyData() {
        System.out.println("After Method Executed");
        DBUtils.delete("delete from working_class_heroes where name = 'dirty data'");
        DBUtils.delete("delete from working_class_heroes where name = 'Longggggggggggggggggggggggggggggggggggggggggggggggggggggggg Nameeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee'");
    }
}
