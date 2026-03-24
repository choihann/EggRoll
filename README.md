# Eggroll

---

# Mid-Project Review Report 3/23/2026

**Design patterns:**

**Factory Pattern**
* Pet factory is a master class acting as an interface for other classes so that concrete pet classes can be instantiated with individual behaviors and custom fields.
* GachaMachine objects will call on petfactories to return new pet objects to dispense

**State Pattern**
* PetState governs particular state-dependent methods and allows pets to change their internal state.
* For example, depending on PetState, certain pet actions may be locked or have different effects.
* These states change depending on internal pet fields, their stats- if a pet has too little "fullness", they enter a hungry state.

**Command Pattern** 
* Commands abstract away the many functions that have to execute in order for a player to functionally make an action.
* For example, when a player wants to "Bathe" their pet, many pieces have to execute.
* The game has to process the action, check for penalties, apply those penalties, increase the pet stats, check if pets should enter different states, etc.
* All these issues are encapsulated into a BatheCommand, likewise for other player actions taking care of pets.