/** INTRODUCTION *****************************************/
Project:	Purfect Litter
Group:		Catatonic

/** STRUCTURE ********************************************/

Purfect_Litter
- src
	- com.cs446.purfect_litter
	- com.cs446.purfect_litter.LogicManager
	- com.cs446.purfect_litter.LogicManager.CardManager
	- com.cs446.purfect_litter.LogicManager.Phases
	- com.cs446.purfect_litter.SessionManager
- res
	- drawable-*
	- layout
	- menu
	- values
	- values-large
AndroidManifest.xml
README.txt

/** SUBSYSTEMS *******************************************/

* Note: More detailed comments available in each file.

1. LogicManager
Game.java: Facade interface for communication between UI and back-end (acts as "model").
CardManager component: implements the Factory pattern to create the library of cards.
Phases component: implements State pattern to abstract phases into a uniform interface.

2. SessionManager
Originally planned to use Template pattern, however realized that Strategy pattern was
more fitting during implementation.

3. UI
MainActivity.java: inherits from the Android Activity class. 
- Instantiates the application.
- Registers as an observer of the "model".
- Provides update method for Game.java to call when UI needs to be updated.
- Registers controllers and listeners for UI components.
res/layout, res/menu
- Per Android platform conventions, screen layouts are defined as XML files to separate
controller code from presentation code.
