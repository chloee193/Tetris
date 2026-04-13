Tetris README

Overview: I designed a tetris with 20 rows and 10 columns for the main playable board. There are
7 different types of pieces that can randomly spawn, and they can be rotated, moved left/right/down,
and dropped to the bottom. If more than one row is cleared in a turn, the player gets extra points,
and even more for a tetris. There is a color blind and normal version, as well as a score label,
level label and a quit button. Levels basically mean how fast the piece is moving down the screen,
and level is determined by how many rows have been cleared in a given game. The player wins the game
at 100k, which is also an src functionality to help combat the possibly addictive quality of tetris.

Design Choices: The game has a feature that the player can switch to a color blind friendly mode,
which is intended to make the game accessible for anyone to be able to see the distinctions of the
pieces.
I picked the BorderPane layout for the label pane because it allowed placement of the labels at
either edge of the screen, which was good for layout and visually. I thought it kept it readable and
was a clean way to get both the labels on the screen and in the same "row" but separated.
For row clearing, the grid takes the occupied status and color of the row above, and copies it to
the row below. I thought this way made the most sense because it gives the visual and effect of the
squares "moving down" but less complicated than just moving all the squares.
The game updates with a timeline, which the keyframes are deleted and recreated when the duration
changes. This felt like the best way to do it because it's an easy way to reset the keyframes when
the level (and duration) changes or resets to it's initial value.

Known Bugs: N/A

Extra Credit: - I included a score counter in the top right that adds 100 every time a row is
removed, 400 for two rows, 900 for three rows and 2000 for 4 rows (a tetris). I did that math so
that it was 100 points x the number of rows cleared x the number of rows cleared, so the additional
points increase more for getting more lines at a time. And 4 rows is that (1600) plus a little more
for tetris.
- Restart method when you press the "R" key. It resets the timeline, level, score, deletes all the
pieces off the screen etc.
- Pieces adjust to move away from the left/right side if it can't turn next to the sides. Makes it
so that the pieces can rotate even if they are next to the sides and it would go off board without
adjustment.
- Energy Consumption:
    Game 1. 199.494 watt seconds/joules
    Game 2. 97.313 watt seconds/joules
    Game 3. 294.444 watt seconds/joules
    Average energy consumption: 591.251/3 = 197.084 watt seconds/joules

    A standard incandescent is about 60 watts, so it would use 60 joules per second. So with the
    average game being 197.084 watt seconds/joules, the average tetris game takes approximately the
    same amount of power as 3.285 seconds of powering a 60 watt lightbulb. The games took more
    energy longer depending on how long I survived, ranging from 1.622 seconds to 4.907 seconds
    of energy powering a 60 watt lighbulb (for the games).
- Accessibility consideration for colorblind people: when the "C" key is clicked, the game restarts and
switches to generating more color-blind friendly colored pieces. The colors of the colorblind
friendly pieces fall under the Color Universal Design (CUD) palette, which is meant to be
distinguishable by most types of colorblindness. The "C" key allows the user to go back and forth
between the two different color schemes easily.
- Every 5 rows the player clears, the duration of the timeline decreases a little bit, so the piece
  falls slightly quicker (leveling up) the more rows the player clears, making the game get harder as
  time passes.
- A label in the top left corner of the screen displays the current level of the player's game.
- SRC Consideration: When the player reaches 100k score, the game automatically ends to help
mitigate the addictive nature of tetris.


Hours: 30
