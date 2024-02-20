# Justification for handling state
Below, describe where you stored each of the following states and justify your answers with design principles/goals/heuristics/patterns. Discuss the alternatives and trade-offs you considered during your design process.

## Players
<fill your anwsers here>
Storage: In the GameController class.
Justification: Centralizing the players in the GameController supports the Mediator Pattern, allowing the game controller to manage player interactions and game flow seamlessly. This encapsulates the player management responsibility within the game's central logic controller.
Alternatives: Storing player references within each Worker or Cell. This approach was dismissed due to increased coupling and complexity, diverging from the Single Responsibility Principle.

## Current player
<fill your anwsers here>
Storage: In the GameController class.
Justification: Storing the current player in GameController simplifies turn management and enforces a clear game flow control, aligning with the Information Expert principle by placing the information (current player state) close to where it is most utilized.
Alternatives: Using a static or global variable accessible by all classes. This was avoided to prevent global state management issues and maintain encapsulation.

## Worker locations
<fill your anwsers here>
Storage: In the Cell class via association with Worker.
Justification: Each Cell potentially holding a reference to a Worker enforces a real-world analogy where physical locations contain or do not contain a worker, adhering to the Principle of Least Astonishment. It also minimizes the navigation path to determine worker locations, following the Law of Demeter.
Alternatives: Maintaining a separate mapping of workers to locations in the Grid or GameController. This approach was deemed less intuitive and risked redundancy and synchronization issues.

## Towers
<fill your anwsers here>
Storage: Each Cell contains a Tower.
Justification: This mimics the physical structure of the game, where each cell on the board can have a tower built upon it. It adheres to the Single Responsibility Principle, with the Cell class managing its state, including any towers.
Alternatives: A global tower state within Grid or external to both Cell and Grid. This was rejected to avoid unnecessary complexity and to keep the tower's state localized, simplifying interactions and state management.

## Winner
<fill your anwsers here>
Storage: Determined dynamically by the GameController through the state of Player and Worker objects.
Justification: Avoids storing redundant or derived state, keeping the system simpler and more maintainable. The game's winner is a result of the game's current state, not a state in itself. This approach aligns with the principle of avoiding premature optimization by not storing data that can be derived.
Alternatives: Storing the winner as a state variable updated throughout the game. This was considered unnecessary and potentially error-prone, as it introduces another state to keep synchronized.

## Design goals/principles/heuristics considered
<fill your anwsers here>
Encapsulation: Each class manages its state, exposing only necessary information and actions through methods. This principle is applied to keep the state management intuitive and localized, reducing dependencies and potential for errors.

Law of Demeter: Objects interact with closely related objects, minimizing dependencies.

Single Responsibility Principle (SRP): Each class has a clearly defined role, ensuring that state management is coherent and aligned with the class's purpose. For example, the GameController manages game flow and outcome, while Cell and Tower manage specific game elements.

Information Expert: The class that has the information necessary to fulfill a responsibility should be the one to execute it.

Cohesion: Storing related state information within relevant classes enhances the logical grouping and interaction of data, leading to a more organized and maintainable codebase.

## Alternatives considered and analysis of trade-offs
<fill your anwsers here>
Strategy Pattern for Dynamic Behavior: While the strategy pattern was considered for encapsulating algorithms (e.g., different win conditions), it was determined that the composite pattern offered a more structured and hierarchical approach to managing game state consistent with the game's object model, in order to better extend the game with god cards and UI, making every object as independent from each other as possible was my choice.

Direct Worker-to-Grid Communication: Rejected to avoid tight coupling between workers and the grid, which would complicate modifications to either structure. Another option was to directly associate all game components (e.g., workers directly modifying towers). This was rejected in favor of the composite pattern to maintain a clear hierarchy and reduce coupling between components.

Global State Management: An alternative was to manage all game state in a single, centralized location. However, this approach was dismissed due to potential scalability issues and reduced modularity, making it harder to maintain and extend the game.