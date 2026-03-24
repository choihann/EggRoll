# Eggroll

---

# Mid-Project Review Report 3/23/2026

**Design patterns:**

**Factory Pattern**
* Where: src\main\java\eggroll\pet\petfactory
* GachaMachine.pullOne() needs to create a Pet but must not know the type of the species. 
* It calls factory.createPet(rarity) and receives a Pet while the concrete type is entirely hidden. 
* GachaMachine, TurnManager, and the UI can remain untouched.


**State Pattern**
* Where: src\main\java\eggroll\pet\petstate
* How: Pet holds a currentState field and delegates every action method to it. 
* When the player calls pet.play(), Pet doesn't decide what happens, it instead calls currentState.play(DEFAULT_MAX_STAT) and the state object handles it. 
* UnbornState.play() increases happiness but blocks all other actions silently.

**Command Pattern** 
* Where: src\main\java\eggroll\command
* Every player action is encapsulated as an object implementing Command. 
* Each command holds a reference to its target Pet and calls the corresponding method on it in execute(). 
* Because DayManager only knows about the Command interface, 
* adding a new action (e.g. ExerciseCommand) requires no changes to DayManager, GachaMachine, or any UI panel.