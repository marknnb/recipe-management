package nl.abnamro.management.recipe.end_to_end.controller;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerTestConstants {
    String VALID_CREATE_RECIPE_REQUEST = """
            {
                 "name":"Khichadi",
                 "type": "VEGETARIAN",
                 "numberOfServings":10,
                 "ingredients":[
                      "Rice",
                      "lentils",
                      "Onions",
                      "Tomato",
                      "Oil",
                      "curry powder"
                 ],
                 "instructions":[
                      "Wash Rice",
                      "cut onions and tomatoes",
                      "pour oli and cook onions and tomatoes",
                      "after 5 miniatures add curry powder",
                      "add rinsed rice and 2 cup water",
                      "cook for 15 mins"
                 ]
            }
            """;

    String INVALID_CREATE_RECIPE_REQUEST = """
            {
                 "name":"Khichadi",
                 "type": "VEGETARIAN",
                 "numberOfServings":10,
                 "ingredients":[
                      "Rice",
                      "lentils",
                      "Onions",
                      "Tomato",
                      "Oil",
                      "curry powder"
                 ],
                 "instruction":[
                      "Wash Rice",
                      "cut onions and tomatoes",
                      "pour oli and cook onions and tomatoes",
                      "after 5 miniatures add curry powder",
                      "add rinsed rice and 2 cup water",
                      "cook for 15 minutes"
                 ]
            }
            """;

    String VALID_UPDATE_RECIPE_REQUEST = """
            {
                 "name":"Khichadi",
                 "type": "VEGETARIAN",
                 "numberOfServings":5,
                 "ingredients":[
                      "Rice",
                      "lentils",
                      "Onions",
                      "Tomato"
                 ],
                 "instructions":[
                      "Wash Rice",
                      "cut onions and tomatoes",
                      "pour oli and cook onions and tomatoes",
                      "after 5 miniatures add curry powder"
                 ]
            }
            """;

}
