package stepdefs;

import com.google.gson.Gson;
import common.Constants;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import module.HeroTable;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.asserts.SoftAssert;
import request.CreateHeroRequest;
import response.CreateHeroErrorResponse;
import response.CreateHeroResponse;
import utils.DBUtils;
import utils.HttpUtils;
import utils.TestUtils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Testcases extends BaseTest {

    private CloseableHttpResponse response = null;
    private String newNatid;
    private String name;
    private String gender;
    private String birthDate;
    private String deathDate;
    private String browniePoints;
    private String salary;
    private String taxPaid;

    @Given("^A request with correct fields$")
    public void sendCorrectRequest() throws IOException, SQLException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check the correct hero data http response")
    public void checkTheCorrectHeroDataHttpResponse() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 200, "response http code is not 200");
        String result = EntityUtils.toString(response.getEntity());
        System.out.println(">>>>>>>> successful creation response is: " + result);
        CreateHeroResponse createHeroResponse = new Gson().fromJson(result, CreateHeroResponse.class);
        softAssert.assertNotNull(createHeroResponse.getTimestamp(), "timestamp is null");
        softAssert.assertAll();
    }

    @And("Verify the new created correct hero from the working class heroes table record")
    public void verifyTheNewCreatedCorrectHeroFromTheWorkingClassHeroesTableRecord() throws SQLException {
        SoftAssert softAssert = new SoftAssert();
        ResultSet resultSet = DBUtils.getLastHero();
        while (resultSet.next()) {
            softAssert.assertEquals(resultSet.getString(HeroTable.NATID), newNatid, "natid is wrong.");
            softAssert.assertEquals(resultSet.getString(HeroTable.NAME), name, "name is wrong.");
            softAssert.assertEquals(resultSet.getString(HeroTable.BIRTH_DATE).substring(0, 10), birthDate, "birthDate is wrong.");
            if (deathDate != null) {
                softAssert.assertEquals(resultSet.getString(HeroTable.DEATH_DATE).substring(0, 10), deathDate, "deathDate is wrong.");
            } else {
                softAssert.assertNull(resultSet.getString(HeroTable.DEATH_DATE), "deathDate is not null.");
            }
            softAssert.assertEquals(resultSet.getString(HeroTable.SALARY), salary, "salary is wrong.");
            softAssert.assertEquals(resultSet.getString(HeroTable.TAX_PAID), taxPaid, "taxPaid is wrong.");
            if (browniePoints != null) {
                softAssert.assertEquals(resultSet.getString(HeroTable.BROWNIE_POINTS), browniePoints, "browniePoints is wrong.");
            } else {
                softAssert.assertNull(resultSet.getString(HeroTable.BROWNIE_POINTS), "browniePoints is not null.");
            }
        }
        softAssert.assertAll();
    }

    @Given("A request with correct fields with null deathDate and browniePoints")
    public void aRequestWithCorrectFieldsWithNullDeathDateAndBrowniePoints() throws IOException, SQLException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setDeathDate(null);
        createHeroRequest.setBrowniePoints(null);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("A request with natid is less than {int}")
    public void aRequestWithNatidIsLessThan(int arg0) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setNatid("natid-" + -1);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    private CreateHeroRequest buildDefaultCreateHeroReq() throws SQLException {
        ResultSet resultSet = DBUtils.getLastHero();
        String lastNatid = "";
        while (resultSet.next()) {
            lastNatid = resultSet.getString(HeroTable.NATID);
        }
        String[] natidArr = lastNatid.split("-");
        newNatid = "natid-" + (Integer.parseInt(natidArr[1]) + 1);
        name = TestUtils.getRandomHeroName(5) + " " + TestUtils.getRandomHeroName(5);
        gender = TestUtils.getRandomGender();
        birthDate = "1930-01-01";
        deathDate = null;
        browniePoints = null;
        salary = "10000";
        taxPaid = "1000";
        CreateHeroRequest createHeroRequest = new CreateHeroRequest(newNatid, name, gender, birthDate, deathDate,
                browniePoints, salary, taxPaid);
        return createHeroRequest;
    }

    @Then("Check hero creation http response with error natid message")
    public void checkHeroCreationHttpResponseWithErrorNatidMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid natid", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("A request with natid is greater than {int}")
    public void aRequestWithNatidIsGreaterThan(int arg0) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setNatid("natid-" + (9999999 + 1));
        createHeroRequest.setName("dirty data");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("A request with last natid")
    public void aRequestWithLastNatid() throws IOException, SQLException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        ResultSet resultSet = DBUtils.getLastHero();
        String lastNatid = "";
        while (resultSet.next()) {
            lastNatid = resultSet.getString(HeroTable.NATID);
        }
        String[] natidArr = lastNatid.split("-");
        newNatid = "natid-" + (Integer.parseInt(natidArr[1]));
        createHeroRequest.setNatid(newNatid);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with repeated natid error message")
    public void checkHeroCreationHttpResponseWithRepeatedNatidErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Working Class Hero of natid: " + newNatid + " already exists!", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("A hero creation request with {string}")
    public void aHeroCreationRequestWithBlankName(String blankName) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setName(blankName);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with {string}")
    public void checkHeroCreationHttpResponseWithBlankNameErrorMessage(String errorMsg) throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), errorMsg, "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("Send the name with {int} alphabets and spaces characters will be rejected by the hero creation api")
    public void verifyTheNameWithAlphabetsAndSpacesCharactersWillBeRejectedByTheHeroCreationApi(int arg0) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setName(Constants.LONG_HERO_NAME);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with Invalid name error message")
    public void checkHeroCreationHttpResponseWithInvalidNameErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid name", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("Send the name with numbers and spaces characters will be rejected by the hero creation api")
    public void verifyTheNameWithNumbersAndSpacesCharactersWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setName("123 456");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with {string} will be rejected by the hero creation api")
    public void aHeroCreationRequestWithInvalidGenderWillBeRejectedByTheHeroCreationApi(String invalidGender) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setGender("invalidGender");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with Invalid gender error message")
    public void checkHeroCreationHttpResponseWithInvalidGenderErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid gender", "Error Message is wrong.");
        softAssert.assertAll();
    }


    @Given("a hero creation request with blank gender {string} will be rejected by the hero creation api")
    public void aHeroCreationRequestWithBlankGenderWillBeRejectedByTheHeroCreationApi(String blankGender) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setGender(blankGender);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with blank gender error message")
    public void checkHeroCreationHttpResponseWithBlankGenderErrorMessage2() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid gender,Gender cannot be blank", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with a birthdate with time will be accepted by the hero creation api")
    public void aHeroCreationRequestWithABirthdateWithTimeWillBeAcceptedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setBirthDate("1980-01-01T09:00:00");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with {string} format with time will be rejected by the hero creation api")
    public void aHeroCreationRequestWithInvalidBirthdateFormatWithTimeWillBeRejectedByTheHeroCreationApi(String invalidBirthDate) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setBirthDate(invalidBirthDate);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid birthdate error message")
    public void checkHeroCreationHttpResponseWithInvalidBirthdateErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid birthdate format", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with birthdate which is a future date will be rejected by the hero creation api")
    public void aHeroCreationRequestWithBirthdateWhichIsAFutureDateWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setBirthDate("2050-01-01");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with {string} invalid deathDate format with time will be rejected by the hero creation api")
    public void aHeroCreationRequestWithInvalidDeathDateInvalidDeathDateFormatWithTimeWillBeRejectedByTheHeroCreationApi(String invalidDeathDate) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setDeathDate(invalidDeathDate);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid deathDate error message")
    public void checkHeroCreationHttpResponseWithInvalidDeathDateErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid death date format", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the deathDate with time will be accepted by the hero creation api")
    public void aHeroCreationRequestWithTheDeathDateWithTimeWillBeAcceptedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setDeathDate("2020-01-01T09:00:00");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with a future date will be rejected by the hero creation api")
    public void aHeroCreationRequestWithAFutureDateWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setDeathDate("2050-01-01");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with the wrong format salary will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheWrongFormatSalaryWillBeRejectedByTheHeroCreationApi() throws IOException, SQLException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setSalary("asdfasdf");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid format salary error message")
    public void checkHeroCreationHttpResponseWithInvalidFormatSalaryErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid salary format", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the {string} blank salary will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheBlankSalaryBlankSalaryWillBeRejectedByTheHeroCreationApi(String blankSalary) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setSalary(blankSalary);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with blank salary error message")
    public void checkHeroCreationHttpResponseWithBlankSalaryErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Salary cannot be blank", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the salary is a negative number will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheSalaryIsANegativeNumberWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setSalary("-100000");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with negative salary error message")
    public void checkHeroCreationHttpResponseWithNegativeSalaryErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Salary must be greater than or equals to zero", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with with long decimal digits salary will be rejected by the hero creation api")
    public void aHeroCreationRequestWithWithLongDecimalDigitsWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setSalary("100000.123");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid salary error message")
    public void checkHeroCreationHttpResponseWithInvalidSalaryErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid salary precision", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the wrong format taxPaid will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheWrongFormatTaxPaidWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setTaxPaid("abdafasdf");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid format taxPaid error message")
    public void checkHeroCreationHttpResponseWithInvalidFormatTaxPaidErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid format tax paid", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the {string} blank taxPaid will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheBlankTaxPaidBlankTaxPaidWillBeRejectedByTheHeroCreationApi(String blankTaxPaid) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setTaxPaid(blankTaxPaid);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with blank taxPaid error message")
    public void checkHeroCreationHttpResponseWithBlankTaxPaidErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "TaxPaid cannot be blank", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with the taxPaid is a negative number will be rejected by the hero creation api")
    public void aHeroCreationRequestWithTheTaxPaidIsANegativeNumberWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setTaxPaid("-1000");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with negative taxPaid error message")
    public void checkHeroCreationHttpResponseWithNegativeTaxPaidErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "must be greater than or equal to 0", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with long decimal digits taxPaid will be rejected by the hero creation api")
    public void aHeroCreationRequestWithLongDecimalDigitsTaxPaidWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setSalary("1000.123");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with invalid taxPaid error message")
    public void checkHeroCreationHttpResponseWithInvalidTaxPaidErrorMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid taxPaid precision", "Error Message is wrong.");
        softAssert.assertAll();
    }

    @Given("a hero creation request with Verify blank browniePoints {string} will be accepted by the hero creation api")
    public void aHeroCreationRequestWithVerifyBlankBrowniePointsBlankBrowniePointsWillBeAcceptedByTheHeroCreationApi(String browniePoints) throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setBrowniePoints(browniePoints);
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Given("a hero creation request with wrong format browniePoints will be rejected by the hero creation api")
    public void aHeroCreationRequestWithWrongFormatBrowniePointsWillBeRejectedByTheHeroCreationApi() throws SQLException, IOException {
        CreateHeroRequest createHeroRequest = buildDefaultCreateHeroReq();
        createHeroRequest.setBrowniePoints("asdfasdfasdf");
        response = HttpUtils.sendPOST(CREATE_HERO_REQ_URL, createHeroRequest);
    }

    @Then("Check hero creation http response with wrong format browniePoints message")
    public void checkHeroCreationHttpResponseWithWrongFormatBrowniePointsMessage() throws IOException {
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusLine().getStatusCode(), 400, "response http code is not 400");
        String result = EntityUtils.toString(response.getEntity());
        CreateHeroErrorResponse createHeroErrorResponse = new Gson().fromJson(result, CreateHeroErrorResponse.class);
        softAssert.assertNotNull(createHeroErrorResponse.getTimestamp(), "timestamp is null");
        softAssert.assertEquals(createHeroErrorResponse.getErrorMsg(), "Invalid browniePoints format", "Error Message is wrong.");
        softAssert.assertAll();
    }
}
