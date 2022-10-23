Feature: Verify the natid out of valid range will get creation error and won't create a new single workingclass hero

  @regression
  Scenario: natid's number is less than 0
    Given A request with natid is less than 0
    Then Check hero creation http response with error natid message

  @regression
  Scenario: natid's number is greater than 9999999
    Given A request with natid is greater than 9999999
    Then Check hero creation http response with error natid message
