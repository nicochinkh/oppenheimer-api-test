Feature: Verify the blank taxPaid will be rejected by the hero creation api

  @regression
  Scenario Outline: Verify the blank taxPaid will be rejected by the hero creation api
    Given a hero creation request with the <blankTaxPaid> blank taxPaid will be rejected by the hero creation api
    Then Check hero creation http response with blank taxPaid error message
    Examples:
      | blankTaxPaid |
      | ""          |
      | " "         |

