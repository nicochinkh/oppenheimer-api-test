Feature: Verify the gender input not in "MALE" or "FEMALE"

  @regression
  Scenario Outline: Verify the gender input not in "MALE" or "FEMALE"
    Given a hero creation request with <invalidGender> will be rejected by the hero creation api
    Then Check hero creation http response with Invalid gender error message
    Examples:
      | invalidGender |
      | "MALEEEEEE"   |
      | "12312313"    |

  Scenario Outline: Verify the blank gender input will be rejected by the hero creation api
    Given a hero creation request with blank gender <blankGender> will be rejected by the hero creation api
    Then Check hero creation http response with blank gender error message
    Examples:
      | blankGender |
      | ""          |
      | " "         |

