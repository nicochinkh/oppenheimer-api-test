Feature: Verify the deathDate with wrong format will be rejected by the hero creation api

  @regression
  Scenario Outline: Verify the deathDate with wrong format will be rejected by the hero creation api
    Given a hero creation request with <invalidDeathDate> invalid deathDate format with time will be rejected by the hero creation api
    Then Check hero creation http response with invalid deathDate error message
    Examples:
      | invalidDeathDate     |
      | "2020-01-0109:00:00" |
      | ""                   |
      | " "                  |
