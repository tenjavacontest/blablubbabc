Bukkit version 1.6.4 R1/2
BookCommand contains the submission for the UTC 0800 contest.
This plugin simple executes commands, line by line, from (command-)books.
Usage: an admin (or player with the 'commandbooks.edit' permission) can create "CommandBooks"
by simply writing in the first line of a book "Command Book".
The next line(s) are checked for additional data like:
* a book-specific permission: the line has to start with "Perm: " followed by the permission node after the colons.
* the amount of uses: the line has to start with "Uses: " followed by the amount of uses (this feature is currently not active)

All lines starting with "#" are comments and get ignored.
All other (non-empty) lines are executed:
* lines starting with "@console " will make the command being run from the console
* all other lines will be used as raw input for the players chat (the player will be forced to "chat" those lines;
accordingly: lines starting with "/" will then trigger the player executing the followed command)
* {player} will be repalced with the name of the executing player

The intent of the plugin was to give admins the ability to give pre-written books out to their players,
maybe even automatically via commands, which could be used by the players (a limited amount of times).

Or it can be used to easily deinfe something like a "command-makro" to execute multiple commands over and over again.


However, the seconds project in the "BatAttack" folder is the submission for the UTC 1600 contest.
The plugins adds two simply abilities related to damaging bats, which get trigger by two wands:
* bat-shoot-wand: let's you shoot straight flying bats which deal slight damage to living entities in their way
* swarm-spawn-wand: let's you spawn a swarm of bats around you, which also damage near living entities

Those wand can be given in-game via the command: /batwand <shoot|swarm>

permissions:

  batmagic.swarm:
  
    description: Allows the player to summon a swarm of aggressive bats with the swarm wand.
    
    default: op
    
  batmagic.shoot:
  
    description: Allows the player to shoot damaging bats with the shoot wand.
    
    default: op
    
  batmagic.wand.swarm.create:
  
    description: Allows the player to spawn in a swarm want.
    
    default: op
    
  batmagic.wand.shoot.create:
  
    description: Allows the player to spawn in a shoot want.
    
    default: op
    
Default configuration:

BAT_LIFETIME_TICKS: 400

BAT_ATTACK_RANGE: 2.5

BAT_ATTACK_DAMAGE: 3.0

TARGET_PRIORITY_BOOST_PER_ATTACK: 50

BAT_FLY_SPEED: 1.5

SWARM_BAT_AMOUNT: 10

SWARM_COOLDOWN_SECONDS: 100

SWARM_WAND:

  ==: org.bukkit.inventory.ItemStack
  
  type: BLAZE_ROD
  
  meta:
  
    ==: ItemMeta
    
    meta-type: UNSPECIFIC
    
    display-name: §6Bat Swarm Wand
    
    lore:
    
    - §3Summons a swarm
    
    - §3of aggressive bats
    
    - §3around you!
    
SHOOT_WAND:

  ==: org.bukkit.inventory.ItemStack
  
  type: STICK
  
  meta:
  
    ==: ItemMeta
    
    meta-type: UNSPECIFIC
    
    display-name: §6Bat Shoot Wand
    
    lore:
    
    - §3Summons an aggressive
    
    - §3bat, which flies
    
    - §3in the direction
    
    - §3you are looking!
