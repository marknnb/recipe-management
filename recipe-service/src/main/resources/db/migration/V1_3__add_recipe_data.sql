--#### Recipe Table Insert Statements

INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Spaghetti Bolognese', 'OTHER', 4);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Vegetarian Lasagna', 'VEGETARIAN', 6);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Chicken Curry', 'OTHER', 4);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Vegetable Stir Fry', 'VEGETARIAN', 2);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Beef Tacos', 'OTHER', 5);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Margherita Pizza', 'VEGETARIAN', 2);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Grilled Salmon', 'OTHER', 1);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Vegetable Soup', 'VEGETARIAN', 8);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Beef Stew', 'OTHER', 6);
INSERT INTO recipe (id, name, recipe_type, servings) VALUES (nextval('recipe_id_seq'), 'Vegetarian Chili', 'VEGETARIAN', 4);


--#### Ingredient Table Insert Statements

-- Ingredients for Spaghetti Bolognese
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 1, 'Spaghetti');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 1, 'Ground Beef');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 1, 'Tomato Sauce');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 1, 'Onion');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 1, 'Garlic');

-- Ingredients for Vegetarian Lasagna
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 2, 'Lasagna Noodles');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 2, 'Ricotta Cheese');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 2, 'Spinach');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 2, 'Marinara Sauce');

-- Ingredients for Chicken Curry
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Chicken');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Curry Powder');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Coconut Milk');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Onions');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Garlic');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 3, 'Ginger');

-- Ingredients for Vegetable Stir Fry
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 4, 'Broccoli');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 4, 'Bell Peppers');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 4, 'Carrots');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 4, 'Soy Sauce');

-- Ingredients for Beef Tacos
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Ground Beef');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Taco Shells');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Cheddar Cheese');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Lettuce');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Salsa');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 5, 'Sour Cream');

-- Ingredients for Margherita Pizza
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 6, 'Pizza Dough');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 6, 'Tomato Sauce');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 6, 'Mozzarella Cheese');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 6, 'Basil Leaves');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 6, 'Olive Oil');

-- Ingredients for Grilled Salmon
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 7, 'Salmon Fillet');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 7, 'Lemon');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 7, 'Olive Oil');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 7, 'Garlic');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 7, 'Dill');

-- Ingredients for Vegetable Soup
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Carrots');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Potatoes');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Celery');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Onions');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Garlic');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 8, 'Tomatoes');

-- Ingredients for Beef Stew
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Beef');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Carrots');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Potatoes');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Onions');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Beef Broth');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 9, 'Tomato Paste');

-- Ingredients for Vegetarian Chili
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Kidney Beans');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Tomatoes');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Bell Peppers');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Onions');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Garlic');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Chili Powder');
INSERT INTO ingredient (id, recipe_id, name) VALUES (nextval('ingredient_id_seq'), 10, 'Cumin');


-- Instructions for Spaghetti Bolognese
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 1, 1, 'Boil the spaghetti according to package instructions.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 1, 2, 'In a pan, heat oil and cook the ground beef until browned.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 1, 3, 'Add chopped onion and garlic, sauté until soft.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 1, 4, 'Pour in the tomato sauce, season with salt and pepper, and let it simmer for 15 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 1, 5, 'Serve the sauce over the spaghetti and garnish with Parmesan cheese.');

-- Instructions for Vegetarian Lasagna
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 1, 'Preheat the oven to 375°F (190°C).');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 2, 'Cook lasagna noodles according to package directions.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 3, 'In a bowl, mix ricotta cheese with chopped spinach.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 4, 'Layer the noodles, ricotta mixture, and marinara sauce in a baking dish.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 5, 'Repeat the layers and top with mozzarella cheese.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 2, 6, 'Bake for 45 minutes, until bubbly and golden.');

-- Instructions for Chicken Curry
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 1, 'Heat oil in a large pot, add chopped onions and cook until translucent.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 2, 'Add minced garlic and ginger, cook for 2 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 3, 'Add the chicken pieces and cook until browned.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 4, 'Stir in curry powder and cook for another 2 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 5, 'Pour in the coconut milk and bring to a simmer.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 6, 'Cook for 20 minutes, stirring occasionally, until the chicken is cooked through.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 3, 7, 'Garnish with fresh cilantro and serve with rice.');

-- Instructions for Vegetable Stir Fry
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 4, 1, 'Heat oil in a wok or large frying pan over medium-high heat.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 4, 2, 'Add broccoli, bell peppers, and carrots, and stir fry for 5-7 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 4, 3, 'Add soy sauce and stir for another 2 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 4, 4, 'Serve over rice or noodles, and garnish with sesame seeds.');

-- Instructions for Beef Tacos
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 5, 1, 'Cook ground beef in a skillet over medium heat until browned.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 5, 2, 'Drain excess fat, then add taco seasoning and water, and simmer for 5 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 5, 3, 'Warm taco shells in the oven or microwave.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 5, 4, 'Fill each shell with beef, then add cheddar cheese, lettuce, salsa, and sour cream.');

-- Instructions for Margherita Pizza
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 6, 1, 'Preheat the oven to 475°F (245°C) and place a pizza stone or baking sheet in the oven.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 6, 2, 'Roll out the pizza dough on a floured surface to your desired thickness.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 6, 3, 'Spread tomato sauce evenly over the dough.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 6, 4, 'Top with sliced mozzarella cheese and fresh basil leaves.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 6, 5, 'Drizzle with olive oil and bake for 10-12 minutes until the crust is golden and the cheese is bubbly.');

-- Instructions for Grilled Salmon
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 7, 1, 'Preheat the grill to medium-high heat.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 7, 2, 'Season the salmon fillet with salt, pepper, and garlic.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 7, 3, 'Brush the grill with olive oil to prevent sticking.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 7, 4, 'Grill the salmon for 4-5 minutes on each side, or until it flakes easily with a fork.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 7, 5, 'Squeeze fresh lemon juice over the salmon before serving and garnish with dill.');

-- Instructions for Vegetable Soup
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 8, 1, 'In a large pot, heat oil over medium heat and sauté onions, carrots, and celery until soft.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 8, 2, 'Add minced garlic and cook for another minute.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 8, 3, 'Pour in vegetable broth, then add potatoes and tomatoes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 8, 4, 'Bring the soup to a boil, then reduce heat and simmer for 20-25 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 8, 5, 'Season with salt and pepper to taste and serve hot.');

-- Instructions for Beef Stew
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 1, 'In a large pot, brown the beef in batches, setting aside after each batch.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 2, 'In the same pot, sauté onions, carrots, and garlic until soft.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 3, 'Return the beef to the pot and add beef broth and tomato paste.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 4, 'Bring to a boil, then reduce the heat and simmer for 1.5 to 2 hours until the beef is tender.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 5, 'Add chopped potatoes and continue to simmer until potatoes are tender, about 30 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 9, 6, 'Season with salt and pepper to taste, and serve hot with fresh parsley on top.');


-- Instructions for Vegetarian Chili
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 1, 'Heat oil in a large pot over medium heat.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 2, 'Add chopped onions, bell peppers, and garlic, and sauté until softened, about 5 minutes.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 3, 'Stir in chili powder, cumin, and other spices, cooking for another minute to toast the spices.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 4, 'Add diced tomatoes, kidney beans, and vegetable broth, and bring the mixture to a boil.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 5, 'Reduce heat and simmer uncovered for 30-40 minutes, stirring occasionally.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 6, 'Adjust seasoning with salt and pepper, and continue to simmer until the chili reaches your desired thickness.');
INSERT INTO instruction (id, recipe_id, step, description) VALUES (nextval('instruction_id_seq'), 10, 7, 'Serve hot, garnished with fresh cilantro, sour cream, and shredded cheese, if desired.');