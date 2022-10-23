Feature: Verify the blank salary will be rejected by the hero creation api

  @regression
  Scenario Outline: Verify the blank salary will be rejected by the hero creation api
    Given a hero creation request with the <blankSalary> blank salary will be rejected by the hero creation api
    Then Check hero creation http response with blank salary error message
    Examples:
      | blankSalary |
      | ""          |
      | " "         |

