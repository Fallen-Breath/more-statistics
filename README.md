More Statistics
-----------

A light-weight mod to add custom statistics to the game

Install it on your fabric server and enjoy new statistics and more

If you have also installed it on your client you can query the custom statistics in the statistics screen too


# Statistics List

## break_bedrock

When a bedrock is deleted by a piston or a sticky piston, the player who placed a piston block at that position in the previous gametick increases this stat by 1

Criteria: `minecraft.custom:minecraft.break_bedrock`

## firework_boost

Increases when a player uses a firework rocket to speed up in elytra flight

Criteria: `minecraft.custom:minecraft.firework_boost`

## ender_pearl_one_cm

When the player uses an ender pearl to teleport, the value of this statistic will increase the distance between the teleportation's origin position and destination position

Criteria: `minecraft.custom:minecraft.firework_boost`


# Scoreboard Criterion

## blockPlacedCount

Triggers when a player places a block

Technically the time it gets triggered is right after a player uses a `BlockItem` successfully

