# Kalaha

A simple Spring boot API which supports following endpoints

```
- GET /game  		- Returns the current state of game
- GET /game/winner 	- returns the winner of the game if game is finished, else throws an error
- PUT /game 		- Allows you to enter the index of pit for current player
```

## Improvements
* Though I have tried not to mutate a lot, but couldn't eliminate it completely. Also the current implementation is very procedural with a lot of moving parts, which makes it harder to control mutation. 
A better way would be more functional focusing on what to do instead of how to do; and ofcourse with minimum mutation.