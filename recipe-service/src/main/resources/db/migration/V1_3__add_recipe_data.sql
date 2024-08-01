--
insert into RECIPE (NAME, RECIPE_TYPE, SERVINGS)
values ('Recipe 1', 'OTHER', 4);

--

insert into INGREDIENT (NAME, RECIPE_ID)
values ('4 Apples', 1)
     , ('1 1/2 tablespoons of Cinnamon', 1)
     , ('1/2 part of salmon', 1)
     , ('2 big potatoes', 1);

insert into INSTRUCTION (STEP, DESCRIPTION, RECIPE_ID)
values (1, 'Mix ingredients together except the raisins.', 1),
       (2, 'Place in baking dish and in oven at 350 Degrees for 30 minutes.', 1),
       (3, 'Add raisins the last 5 minutes of baking in the oven.', 1),
       (4, 'Serve and enjoy! Use Organic Ingredients if Available', 1);

--
insert into RECIPE (NAME, RECIPE_TYPE, SERVINGS)
values ('Recipe 2', 'VEGETARIAN', 1);

insert into INGREDIENT (NAME, RECIPE_ID)
values ('Beans', 2)
     , ('1 1/2 tablespoons of Curry powder', 2)
     , ('1 big spoon of vegetable oil', 2)
     , ('Coriander', 2);

insert into INSTRUCTION (STEP, DESCRIPTION, RECIPE_ID)
values (1, 'Boil the Beans', 2),
       (2, 'In a pan take oil', 2),
       (3, 'Add curry powder', 2),
       (4, 'Add boiled beans', 2),
       (5, 'Server after 10 mins', 2);

