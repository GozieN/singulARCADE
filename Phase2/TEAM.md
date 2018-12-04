TEAM CONTRIBUTIONS:
Jingfei Tan:
    - Wrote the Scoreboard and ScoreboardActivity classes
    - Wrote the banner code in Peg Solitaire, and Snackbar code in Scoreboard
    - Pair programming with Stacey to implement Undo for Sliding Tiles and Peg Solitaire
        (Pushing moves onto a stack, popping them off when undo is clicked)
    - Pair programming with Stacey to create Peg Solitaire Game (in particular, wrote PegSolitaireManager),
    - Refactoring of SetUp Activity so that there's one set up activity that covers the set up of all the games
    - Refactoring of Starting Activity so that all games share the activity for "New Game" and "Load Saved Game"

Stacey Thomas:
    - Made the User class
    - Wrote the Game Setup Activity and made the corresponding xml
    - Created unittests for PegSolitaireTile, PegSolitaireBoard, and PegSolitaireManager
    - Pair programming with Faye to create Peg Solitaire Game (wrote PegSolitaireTile and PegSolitaireBoard)
    - Fixed issues with display of PegSolitaireGame
    - Pair programming with Faye to implement Undo properly (pushing moves onto a stack) for Sliding
        Tiles and Peg Solitaire
    - Made remaining Tile images and PegSolitaireTile images


Dianna McAllister:
    - User Account screen
    - Game interface
    - Made all Controller classes
    - Made saveAndLoad class
    - Made methods like numberOfMatches and resetToWhite in MemoryBoardManager
    - Made methods like setEmptyStackOfGameStates, get / set RecentManagerOfBoard, get / set numberOfUndos in User
    - Made method lastAddedUser in UserManager
    - Added visual on Play_____Activity to see the number of moves and number of undos (if applicable to the game)
    - Added method to make sure that every sliding tiles game would give you a solvable board
    - Made save game and load new game work for each game
    - Created Unittests for Board, Controllers, Scoreboard, SlidingTileBoard, SlidingTileManager, User, GameLauncher, UserManager
    - Helped create README file for Phase 2

Siyue Chen:
    - GameLauncher class
    - Made the MemoryPuzzleTile class that implements Comparable<MemoryPuzzleTile>
    - Added all memory_tile images into drawable
    - Made the Tile class abstract and refactored the MemoryPuzzleTile and PegSolitaireTile to the Tile class
    - Refactored the whole bunch of switch cases inside the tile constructor
    - Made methods like greyOut, flipTile, flipBack, numTileFlipped inside MemoryBoardManager
    - Made the googlePlay Store link button

Gozie Nwaka:
    - logo screen
    - Sign in screen
    - Sign up screen
    - Game centre screen
    - User Manager class
    - Memory puzzle board manager
    - Memory puzzle boards display
    - Memory puzzle unittests - board, boardmanager and tile
    - Linking of screens in memory puzzle tile
    - Memory puzzle specifications - drop down bar for size of the board
    - Phase 1 - readme file
    - Number of moves display when playing memory puzzle game


TEAM MEETING 1: DECIDING ON DESIGN
    - Everyone in attendance
    - 3-4 hours @ Bahen on October 21/2018
    - Worked through software design and delegate tasks
    - Decided to use Stack for undo's
    - Decided to use Hashmap for ScoreBoard
    - Made flow chart of which activities connect to which activities

TEAM MEETING 2: MAJOR PUSH TO COMPLETE PHASE 1
    - Everyone in attendance (Siyue was away on holiday for Reading Week but was present via facetime)
    - 6 hours @ EJ Pratt on October 5/2018
    - Every team member explained what they had done so far
    - In places were we were stuck or there was a bug, consulting of other team members for help
    - Added final javadoc, comments, etc. and prepare for final push for phase 1 deadline

TEAM MEETING 3: DECISIONS FOR PHASE 2
    - Everyone in attendance
    - 4 hours @ EJ Pratt on November 17/2018
    - Talked about what games we should make for Phase 2 and decided on Peg Solitaire and Memory Puzzle
    - Delegated tasks for phase 2

TEAM MEETING 3: MAJOR PUSH TO COMPLETE PHASE 2
    - Everyone in attendance
    - 8+ hours @ EJ Pratt on November 29/2018
    - Every team member explained what they had done so far
    - User testing/unittesting each other's code
    - Added final javadoc, comments, etc. and prepare for final push

TEAM CONTRACT:
 - Meetings: How often should we have them?
    - Every weekend
 - I will get my allotted work done on time.
 - I will attend every team meeting and actively participate.
 - Should an emergency arise that prevents me from attending a team meeting, I will notify my team immediately.
 - The work will be divided roughly equally among all team members.
 - I will help my team to understand every concept in the project.
 - If I do not understand a concept or code, I will immediately ask my team for help.

TEAM CONTACT INFORMATION:

Name: Jingfei Tan
Git log name: Jingfei Tan
utorid: tanjingf
Email: jingfei.tan@mail.utoronto.ca
Phone: (639) 317 - 8252

Name: Stacey Thomas
Git log name: Stacey T
utorid: thoma501
Email: stacey.thomas@mail.utoronto.ca
Phone: (647) 303 - 6151

Name: Dianna McAllister
Git log name: diannamcallister
utorid: mcalli42
Email: dianna.mcallister@mail.utoronto.ca
Phone: (613) 406 - 6811

Name: Siyue Chen
Git log name: AllegraChen
utorid: chensi91
Email: allegra.chen@mail.utoronto.ca
Phone: (647) 939 - 6517

Name: Gozie Nwaka
Git log name: GozieNwaka
utorid: nwakagoz
Email: gozie.nwaka@mail.utoronto.ca
Phone: (647) 916 - 2060


