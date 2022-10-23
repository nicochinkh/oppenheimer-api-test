Feature: Verify the name with numbers will be rejected by the hero creation api

  @regression
  Scenario: Verify the name with numbers will be rejected by the hero creation api
    Given Send the name with numbers and spaces characters will be rejected by the hero creation api
    Then Check hero creation http response with Invalid name error message