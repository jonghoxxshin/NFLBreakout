game
====

This project implements the game of app.Breakout.

Name: Ryan Bloom, Ed Jongho Shin

### Timeline

Start Date: January 28, 2019

End Date: February 11, 2019

Hours Spent: ~55+

### Project Roles:

Ryan Bloom: Responsible for creating Brick.java (with three types of bricks), powerUps.java (including abstractions and all 4 subclasses), CollisionHandler.java methods, DataHandler.java (to read in level formation text files as well as test text files) methods, Ball.java, some methods in Paddle.java that handle powerUps (speedUp(), stretch(), timeOut(), updateLives()), parts of Game.java that add brick ImageViews to the scene in createGame() and loseLifeCheck(), brickCollision(), powerHelper() helper methods below step function in Game.java.  Managed pausing and restarting of animation upon losses and wins as well as moving from one level to the next upon winning and resetting SplashPage upon losing or completing a test.  Created handleAlert() and resetGame() helper methods in Breakout.java.  Found images used in gameplay and created test.txt files as well as level.txt files. Added comments and javaDoc for the classes and methods listed above.

Jongho Shin: Responsible for creating SplashPage.java and linking SplashPage to gameplay, Breakout.java, Game.java (setup, paddleCollision(), step), Paddle.java (paddle movement and finding paddle parts for collision detection with ball to allow ball to bounce more interestingly off the paddle, keyHandler for movement and cheat key usage), MusicPlayer.java (extra credit adds music to game), TestGame.java (handles test key inputs and uses DataHandler to read in initial ball and paddle conditions based on test ",", ".", "/"), game status displays (level, score, lives, high score).

### Resources Used:  
Online: 
1) Abstractions for powerUp classes: https://compsci.capture.duke.edu/Panopto/Pages/Viewer.aspx?id=91e6f809-e9e4-4bd2-8210-a9eb000bc5c6
2) timeOut() methods in Ball.java and Paddle.java to handle powerUps: https://stackoverflow.com/questions/35512648/adding-a-timer-to-my-program-javafx
3) Handling key inputs in handleKeyPressed in Paddle.java: https://docs.oracle.com/javafx/2/api/javafx/scene/input/KeyCode.html
4) Displaying alerts: https://stackoverflow.com/questions/28937392/javafx-alerts-and-their-size
5) Handling alerts: https://stackoverflow.com/questions/44742134/animationtimer-showandwait-is-not-allowed-during-animation-processing

People:
1) Consulted student (Sydney Hochberg) about strategies for reading and formatting level layout text files and test text files.
1) Consulted student (Sydney Hochberg) about strategies for reading and formatting level layout text files and test text files.

### Running the Program

Main class: Breakout.java contains the main method that launches the program.

Data files needed: All images and text files needed are found in the resources directory.

### Program Use:
1) Start: 
To start the normal breakout game, either click enter, space bar, or the "start" button on the Splash Screen.  Click "1", "2", or "3" plus "," "." or "/" when on the Splash Screen in order to launch the three different test cases.  

2) GamePlay: 
Use the left and right arrow keys in order to move your paddle along the bottom of the screen and bounce the ball towards the bricks.  Catch various falling powerUps to help you advance (extra life, speed up, stretch, big ball).  You start with 4 lives (or downs) and lose after failing to hit the ball a fourth time.  

3) Levels:
There are 3 levels, each progressing in difficulty.  The first level has the fewest blocks, and the blocks are placed highest up on the screen, allowing for more time to catch the ball.  Furthermore, level 1 only contains "redskins" blocks, which require 1 hit to break.  Level 2 has more blocks that start further down the screen.  It also contains "giants" blocks, which require 2 hits to break.  After being hit once, the "giants" blocks then turn into the level 1 "redskins" blocks.  Level three contains the most blocks that start the furthest down the screen.  Furthermore, each of the level 3 blocks is an "eagles" block, which takes 3 hits to break.  

4) Cheat Keys: 
"R" - Resets ball and paddle to starting position.

"L" - Increases user's lives by 1 every time it is pressed.

"B" - Makes the ball big for 5 seconds.  Breaks any brick with one hit and does not bounce off the bricks but continues through them.

"S" - Stretches paddle for 5 seconds.

"F" - Increases paddle speed for 5 seconds.

5) Restarting game: upon losing, completing a test, or beating a level, closing the alert window will prompt one of 2 things.  Either the splash screen will reload allowing the user to restart the game or test (which happens upon losing, completing a test, or beating the final level), or the next level will automatically load and the game will start again (upon beating one of the first two levels).

6) Testing: 

### Assumptions and Simplifications
The animation.pause() method did not seem to be working upon setting alerts, so we added a "gamePaused" boolean which must be set to false in order for the step method to run.  This is a more manual way of pausing the animation but it works.  Made the assumption that 2 of the test cases could be run in the same manner for each level.  Original positions and velocities did not have to be altered in order to have the ball hit the corner or have the user lose a life.  

### Known Bugs
One known issue occurs when detecting a ball and brick collision.  We detect whether the ball and brick have a side collision or top-bottom collision in order to allow the ball to bounce more naturally (solely negating x velocities if the ball hits the side of the brick, and solely negating y velocities if the ball hits the top or bottom of the brick).  This however is done in CollisionHandler.java in such a way that 2 potential bugs emerged.  First, if the ball hits the top or bottom corner of one of the blocks but still hits a side such that the top part of the ball is above the top part of the block or the bottom part of the ball is below the bottom part of the block, it registers as a top-bottom collision instead of a side collision.  This therefore changes the y direction, which causes the ball to still remain in contact with the block leading to more detected collisions.  Second, if the ball hits between 2 blocks, touching both at the same time, then the directions switch twice, negating each change.  Therefore, the ball continues to move in the same original direction destroying both bricks instead of bouncing back.

### Notes
We made the entire game football themed to differentiate from other breakout games.  Additionally, we added the nfl theme music to play while the game is being played in order to add more feeling to the game scene. 

### Impression
I enjoyed this assignment for several reasons.  It was fun implementing an actual game as opposed to some other, more mundane computer science assignments that I have had in the past.  Furthermore, this assignment really forced me to think about how to divide my program into many classes in order to keep things organized.  I had never before created a program with this many different classes working together in order to create the final project.  Additionally, this was the first time I had ever worked with abstractions, and although slightly confusing, forcing myself to understand their purpose and usefulness for future assignments was extremely helpful and rewarding.  I also thought that this was a good first project to work with a partner on.  We definitely ran into some conflicts when using git, but the fact that it was only 2 people working on it made resolving and working through these conflicts far easier than had it been a larger team project.    
