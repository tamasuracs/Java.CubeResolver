# Java.CubeResolver
#Cubes Puzzle Task

Create a computer program that solves the given cubes puzzles and
prints the solution(s) in ASCII format into a file in unfolded format
as given below as an example.

Have a look at the following link for some background information:
http://www.happycube.com/

Here is an example:
The pieces of the blue cube look as follows:
```sh
  o  o o o  o  
 ooo ooooo oooo
ooooo ooo oooo 
 ooo ooooo oooo
  o  o o o  o  
 o o  o o  o o 
oooo ooooo oooo
 oooo ooo oooo 
oooo ooooo oooo
oo o o o  oo oo
```

One of the solutions in "unfolded form" looks as follows:
```sh
  o    o o o o 
 ooo ooooo ooo 
ooooo ooo ooooo
 ooo ooooo ooo 
  o   o oo  o  
     o o       
     ooooo     
      ooo      
     ooooo     
      o o      
     o o o     
     ooooo     
      ooo      
     ooooo     
     o o o     
      o o      
     oooo      
      oooo     
     oooo      
     oo o      
```
Additional challenge 1 : All unique solutions :
In the base task you are only required to find one solution and to
print it into a file. The additional challenge 1 is to extend your
program to find all unique solutions. A solution is unique if it
cannot be transformed into another solution by rotating or mirroring
in 3 dimensions.

Additional challenge 2 : How many different cubes exist :
In the file that you got together with this description you can see
the pieces of 4 different cubes. The pieces are such that you can plug
them together in a plane in the order:
 1 2 3
 4 5 6
And you can create a cube out of them. How many different sets of
pieces that fulfill these two criteria exist? One set is different
from the other if not all pieces in one set have a corresponding
matching piece in the other set. A corresponding matching piece is a
piece into which you can transform your piece at hand by rotating or
mirroring it in two dimensions. No side of a piece must be "flat",
e.g. a piece that looks like this:
```sh
 ooooo
 ooooo
 ooooo
 ooooo
 ooooo
```
would not be allowed. The piece must be physikal producible, e.g a
piece that has a corner like this is not allowed:
```sh
     o
 oooo 
 oooo 
 oooo 
 oooo 
```
Here the corner would easily break and fall off.
