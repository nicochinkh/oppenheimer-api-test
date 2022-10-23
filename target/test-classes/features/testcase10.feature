Feature: Verify the birthdate with wrong format will be rejected by the hero creation api

  @regression
  Scenario Outline: Verify the birthdate with wrong format will be rejected by the hero creation api
    Given a hero creation request with <invalidBirthDate> format with time will be rejected by the hero creation api
    Then Check hero creation http response with invalid birthdate error message
    Examples:
      | invalidBirthDate     |
      | "1980-01-0109:00:00" |
      | ""                   |
      | " "                  |
