 Feature: Update Station

  Scenario: Operator updates the availability of a charger at a station
    Given the operator is on the Operator List page
    When they click on the operator "Galp"
    And they select the station "ChargeUnity Lisboa"
    And a list of chargers for that station is displayed
    And they click on the charger number "1"
    And they change its status to "Unavailable" using the dropdown
    Then the charger's status should be updated successfully
    And the updated status should be reflected in the charger list
