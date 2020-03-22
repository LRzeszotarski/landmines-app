# landmines-app
Landmines App

Description of the Problem:
---------------------------
We want to send a soldier into a mine field with a directional metal detector. The field is an n x n grid, and the soldier will move horizontally (east or west) or vertically (north or south), never leaving the field. His metal detector can be pointed in any of the cardinal directions (east, north, west, or south) and will beep if it senses any mine in that direction, no matter how far away the mine is. Only mines that are exactly in the row or column down which the sensor is pointed are sensed.
The soldier can move one square at a time in any of the four directions, pointing his sensor in various directions. But he will not move onto a square if his sensor beeps when pointed in that direction, unless he has previously visited that square. He will continue this process, visiting and revisiting squares as needed, until he is sure that he has visited every square that he can.
We want to see how well we will be able to do using this type of sensor. Create a class LandMines that contains a method numClear that takes the actual layout of mines in a minefield as input and that returns the number of squares that the soldier can safely visit.
The layout shows the mine field as a String[], starting with the top row. '-' denotes a square with no mine and 'M' denotes a square that contains a mine. The soldier will always start in the northwest corner (the leftmost square in the top row), which will never contain a mine.

Definition:
-----------
Class: LandMines / LandMinesObj
Method: numClear
Parameters: String []
Returns: int
Method signature: int numClear(String layout[])

Constraints:
------------
- Class may have no constructor except the default constructor
- layout contains between 2 and 50 elements inclusive.
- The length of each element of layout equals the number of elements in layout. - Each character in layout will be a hyphen ('-') or uppercase 'M'.
- The first character in the first element of layout will be a hyphen.

Examples:
----------
["-M-", "---", "M--"]
Returns: 1
The soldier cannot safely move in either direction from his initial position.
        
["-M-", "-M-", "--M"]
Returns: 3
The soldier can safely go south from his initial position, but cannot head east from any of those locations.
        
["--M-", "-MM-", "----", "----"]
Returns: 12
The soldier can go south, and then can move east along both of the bottom rows. From the easternmost location on one of the bottom rows, he can safely move north. But the second square in the top row can never safely be visited.
       
["-----" , "--M-M", "-----", "-M---", "---M-"]
Returns: 21
All of the squares without mines can safely be visited. For example, a way to visit the second square on the bottom row is as follows: go south 2, go east 2. From this location it is safe to go south since the detector indicates no mines in that direction; go south 2. Then go west 1.
