2D Car Racing Game
A simple 2D car racing game built in Java using Swing and AWT. This project implements a basic car game where players control a car to avoid obstacles and earn points by avoiding collisions.

Features
Player Control: Move the player car left, right, up, and down with the arrow keys.
Dynamic Obstacles: Avoid enemy cars randomly placed on the road.
Infinite Scrolling Road: The road scrolls continuously to create a feeling of motion.
Score Tracking: Scores are incremented based on the player's survival.
Game Over and Restart: Game over message with option to restart by pressing Enter.
How to Play
Run the game: Execute the main method in the CarGame class to start the game.
Control the car:
Left Arrow: Move left
Right Arrow: Move right
Up Arrow: Move forward
Down Arrow: Move backward
Objective: Avoid enemy cars and keep the player car from colliding.
Installation and Setup
Clone the Repository:

git clone https://github.com/AtharvVichare/CarGame.git
cd CarGame
Dependencies: Ensure you have Java Development Kit (JDK) 8 or above installed.

Run the Game:

Open the project in an IDE like IntelliJ or Eclipse.
Ensure the image files are placed in the correct directory: src/.
Run the CarGame class.
Code Structure
CarGame: Main game class that handles game logic, rendering, and key events.
Car: Represents the player and enemy cars with movement and collision detection.
Road: Handles scrolling of road segments.
LinkedList and Node: Custom linked list implementation to manage lists of cars and road segments.
Image Assets
Place the following images in the src folder:

player_car.png – Player car
enemy_car1.png, enemy_car2.png, enemy_car3.png – Enemy cars
road.png – Road background
Example
Gameplay Screen
Include a screenshot of the game screen.

Future Enhancements
Potential features to add:

Difficulty levels
Scoreboard for high scores
Additional car models and animations
License
This project is open-source. You are free to modify and distribute it as per the terms outlined in the LICENSE file.
