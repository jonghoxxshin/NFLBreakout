game
====

This project implements the game of app.Breakout.

Name: Ryan Bloom, Ed Jongho Shin

### Timeline

Start Date: January 28, 2019

End Date: February 11, 2019

Hours Spent: ~55+

### Project Roles:

Ryan Bloom: Responsible for creating Brick.java (with three types of bricks), powerUps.java (including abstractions and all 4 subclasses), CollisionHandler.java methods, DataHandler.java (to read in level formation text files as well as test text files) methods, wrote level config and test text files, Ball.java, some methods in Paddle.java that handle powerUps (speedUp(), stretch(), timeOut(), updateLives()), parts of Game.java that add brick ImageViews to the scene in createGame() and loseLifeCheck(), brickCollision(), powerHelper() helper methods below step function in Game.java.  Managed pausing and restarting of animation upon losses and wins as well as moving from one level to the next upon winning and resetting SplashPage upon losing or completing a test.  Created handleAlert() and resetGame() helper methods in Breakout.java.  Found images used in gameplay.  Wrote level test files and refactored TestGame.java to become abstract class.  Wrote TestLev1.java, TestLev2.java, TestLev3.java that extend TestGame.java abstract class. Added comments and javaDoc for the classes and methods listed above.

Jongho Shin: Responsible for creating SplashPage.java and linking SplashPage to gameplay, Breakout.java, Game.java (setup, paddleCollision(), step), Paddle.java (paddle movement and finding paddle parts for collision detection with ball to allow ball to bounce more interestingly off the paddle, keyHandler for movement and cheat key usage), MusicPlayer.java (extra credit adds music to game).  Wrote initial TestGame.java (handled test key inputs through splash page and uses DataHandler to read in initial ball and paddle conditions based on test ",", ".", "/"), game and status displays (background image, level, score, lives, high score displays).

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
To start the normal breakout game, either click enter, space bar, or the "start" button on the Splash Screen.  Click "," "." or "/" when on the Splash Screen in order to launch the three different test cases.  Test cases are run for the level that is to be played next.  For example, if you have just started the program, test cases will be level 1 specific.  If you just beat level 1, test cases will be level 2 specific, and if you have just beaten level 2, test cases will be level 3 specific. All test cases are initialized from splash screen that is displayed between levels.

2) GamePlay: 
Use the left and right arrow keys in order to move your paddle along the bottom of the screen and bounce the ball towards the bricks.  Catch various falling powerUps to help you advance (extra life, speed up, stretch, big ball).  You start with 4 lives and lose after failing to hit the ball a fourth time.  After beating a level, you are redirected to the splash screen.  From here, you can either start the next level by clicking anywhere (or clicking the space bar or enter key), or you can run this level's tests by clicking ",", ".", or "/".  

3) Levels:
There are 3 levels, each progressing in difficulty.  The first level has the fewest blocks, and the blocks are placed highest up on the screen, allowing for more time to catch the ball.  Furthermore, level 1 only contains "Redskins" blocks, which require 1 hit to break.  Level 2 has more blocks that start further down the screen.  It also contains "Giants" blocks, which require 2 hits to break.  After being hit once, the "Giants" blocks then turn into the level 1 "Redskins" blocks.  Level three contains the most blocks and these blocks start the furthest down the screen.  Furthermore, each of the level 3 blocks is an "Eagles" block, which takes 3 hits to break, turning from an "Eagles" block, to a "Giants" block, to a "Redskins" block.  

4) Cheat Keys: 
"R" - Resets ball and paddle to starting position.

"L" - Increases user's lives by 1 every time it is pressed.

"B" - Makes the ball big for 5 seconds.  Breaks any brick with one hit and does not bounce off the bricks but continues through them.

"S" - Stretches paddle for 5 seconds.

"F" - Increases paddle speed for 5 seconds.

5) Restarting game: upon losing, completing a test, or beating a level, closing the alert window that is displayed will return the user to the splash screen.  If the user has just lost a game, then this splash screen will restart at level one upon starting the game, and will run level 1 tests upon receiving any of the test key inputs.  If the user had just beaten level 1, then level 2 will start upon enter/space/click and level 2 tests will run upon clicking a test input key.  If level 2 was just beaten, then level 3 game and test cases will run.  If level 3 was just beaten, then the game will loop back down to running level 1 gameplay and test cases.  Finally, if a user is returned to the splash screen after completing a test, then the same level will be run upon clicking another test input key or any of the start buttons.

6) Testing: Each level contains 3 different tests that can be run from the splash screen using the keys ",", ".", and "/".  Each test has a corresponding text file found in resources with name "lv#_test#.txt".  These files are read in by DataHandler.java based on the level and test input received.  Furthermore, in order to run each test successfully, do not move the paddle or touch any additional keys after selecting your test input and starting the test.  The initial positions and velocities are set specifically to run each test.

Level 1 Test 1 (","): tests if a ball heading towards a corner accurately bounces back in the exact opposite direction.

Level 1 Test 2 ("."): tests that a ball hitting a redskin brick breaks it.

Level 1 Test 3 ("/"): tests that a ball traveling to the bottom of the screen and not being caught by the paddle forces the user to lose a life.

Level 2 Test 1(","): tests that catching an "extra life" powerUp does indeed increment user's lives by 1.

Level 2 Test 2 ("."): tests that a level 2 brick ("Giants") turns into a level 1 brick and then breaks after 2 hits by the ball.

Level 2 Test 3 ("/"): tests that catching an "air pump" powerUp does increase the size of the ball and that said ball is able to break level 2 blocks with one hit.

Level 3 Test 1 (","): tests that catching a "stretcher" powerUp increases the width of user's paddle.

Level 3 Test 2 ("."): tests that catching a "gatorade" powerUp increases the speed of the paddle.

Level 3 Test 3 ("/"): tests that a ball that bounces on the edge of the paddle changes its x velocity accordingly.

### Assumptions and Simplifications
The animation.pause() method did not seem to be working upon setting alerts, so we added a "gamePaused" boolean which must be set to false in order for the step method to run.  This is a more manual way of pausing the animation but it works.  Assume that user will not move the paddle or alter the game scene upon running a test and will simply let the test play out with the desired initial conditions set in the text files.

### Known Bugs
One known issue occurs when detecting a ball and brick collision.  We detect whether the ball and brick have a side-side collision or top-bottom collision in order to allow the ball to bounce more naturally (solely negating x velocities if the ball hits the side of the brick, and solely negating y velocities if the ball hits the top or bottom of the brick).  This however is done in CollisionHandler.java in such a way that 2 potential bugs emerged.  First, if the ball hits near the top or bottom corner of one of the blocks but still hits a side such that the top part of the ball is above the top part of the block or the bottom part of the ball is below the bottom part of the block, it registers this as a top-bottom collision instead of a side collision.  This therefore changes the y direction as opposed to the x direction, which causes the ball to still remain in contact with the block leading to more detected collisions and eventually a block destruction as opposed to simply a bounce back.  Second, if the ball hits between 2 blocks, touching both at the same time, then the directions switch twice, negating each change.  Therefore, the ball continues to move in the same original direction destroying both bricks instead of bouncing back.

### Notes
We made the entire game football themed to differentiate from other breakout games.  Additionally, we added the nfl theme music to play while the game is being played in order to add more feeling to the game scene.  Creating the two abstractions that were created (for TestGame and powerUp classes) did lead to fewer if/else if statement in those classes, but did not do away with all of these statements.  This is due to the fact that these statements were still needed in order to determine which specific subclass should be initialized based on the randomly generated powerUp type in Brick.java and based on the current level in breakout.java.  I believe there might be a way to avoid these statements, but was unsure of how to do so overall.   

### Impression
I enjoyed this assignment for several reasons.  It was fun implementing an actual game as opposed to some other, more mundane computer science assignments that I have had in the past.  Furthermore, this assignment really forced me to think about how to divide my program into many classes in order to keep things organized.  I had never before created a program with this many different classes working together in order to create the final project.  Additionally, this was the first time I had ever worked with abstractions, and although slightly confusing, forcing myself to understand their purpose and usefulness for future assignments was extremely helpful and rewarding.  I also thought that this was a good first project to work with a partner on.  We definitely ran into some conflicts when using git, but the fact that it was only 2 people working on it made resolving and working through these conflicts far easier than had it been a larger team project.    
