Note: someone else already had the same idea but did it (mostly) better. I made this anyway for fun. You can find their mod [here](https://modrinth.com/mod/highspeed-rail)
The extra control for deceleration of top speed is unique to this iteration, as far as I can tell.

# Multi-Speed Rail

Server-side mod for different speeds for rail by putting different blocks underneath the track.
This allows fast rail transport to be developed, but keeping a lower speed for contraptions.

Speed changes by block Tags:
- `multispeedrail:high_speed_rail` (default: concrete)
   Sets speed to `high_rail_speed` metres/second
- `multispeedrail:medium_speed_rail` (default: gravel, powdered concrete)
   Sets speed to `medium_rail_speed` metres/second

All other blocks make minecarts go at the regular speed

## Config

### medium_rail_speed
The minecart speed (m/s) when on medium speed rail blocks

### high_rail_speed
The minecraft speed (m/s) when on high speed rail blocks

### deceleration
The maximum amount (m/s) to lower minecart top speed each second when moving to a lower speed region. The actual value updates each tick.
