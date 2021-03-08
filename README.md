# EndlessOnline
Open Source alternative to the Endless Online client (http://endless-online.com, http://game.eoserv.net) to allow for full customization of assets, code, and remove any hard-coded restrictions the original game client had in place.  Designed to work with EOServ (www.eoserv.net) in it's vanilla state, requiring no coding on the server side (unless of course you want custom features!)

## Plans
First and foremost, packet handling will be the primary objective.  As of 3/07/2020 Packets can be created, read, modified, and will receive correct responses from the server.  Testing has been with auth, login, account/character creation.

I **WILL NOT** be "recreating" the broken client we all know and love.  Some EO Assets will be used for testing, but no UI will resemble the original game client.  Finished result will include black and white assets to be used as a "Skeleton" to create your own.  Expect black and white, "no effort" UI solely for getting an idea of how you will be creating your own UI Assets.

Current .EGF files are.. crap to say the least, and can be problematic in my opinion.  I have no solid plan on how I will manage assets, but it will be likely based on a suggestion by Apollo and be some form of .zip file.

### Support Platforms
To start, this will likely only support Windows.  I will be using LibGDX (https://libgdx.com) as the game's framework, thus allowing for ports to Linux, MacOS, Android, iOS and even perhaps browser support.

## Task list
Below is a list of tasks to give myself an idea of where to go to next.  Incomplete tasks are roughly an IDEA of what I will be doing next.  I will update the list with progress as I move along.  Once we reach the end of this task list, a new one will be created with areas I need to improve upon, and bugs that need to be fixed.

- [x] Decoding of packets
- [x] Encoding of packets
- [x] Reading/Writing packets
- [x] Server authentication
- [x] Reading pub files
- [x] Writing pub files
- [ ] Receiving pub file data from server
- [ ] Reading map files
- [ ] Rendering map files
- [ ] EOFile Editor (cross-platform "editpub" and "eomap" remakes)
- [ ] Asset Creation (these will be simple, not intended to be used)
- [ ] Asset Management (packing assets into something like a zip file but encrypted?)
- [ ] Menu / UI Rendering
- [ ] Player / Entity Management (basically storing information on Map NPCs, Map Players to be used in rendering)
- [ ] Player / Entity Rendering
- [ ] Game Client Development (putting the puzzle together, giving us something to showcase)

## Some Footnotes
Developing this client, for me, is a learning experience.  My code will be messy, my code will have mistakes and things that could be done better.  That's where you, the community, can come in.  Any assistance on this project is greatly appreciated, and highly encouraged.

## Licensing
Unsure of where to go with this.  You are free to use, modify, redistribute any code within this repo.
