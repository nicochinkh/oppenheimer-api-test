Feature: Verify the blank name will be rejected by the hero creation api

  @regression
  Scenario Outline: Verify the blank name will be rejected by the hero creation api
    Given A hero creation request with <blankName>
    Then Check hero creation http response with <blankNameErrorMessage>
    Examples:
      | blankName | blankNameErrorMessage             |
      | ""        | "Invalid name,Name cannot be blank" |
      | " "       | "Invalid name,Name cannot be blank" |