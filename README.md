# Exercise-4--Advanced-OOP---Univeristy-of-Turku

## 1. Student ID
Student ID: 2406530

## 2. Exercise
Turku regional public transportation Föli wants to modernize its payment card system to be Java-based. The operation of the cards is simplified so that the card can be loaded with X euros. The current balance of the card can also be inquired. Amounts may also include individual euro cents. Devices suitable for these operations will be placed in buses and public transport service points.

In addition, when boarding a bus, one can pay for the journey using the card on the bus's reader device. When purchasing a journey, the device offers options to buy

a single ticket (valid for 2 hours, price 3 euros),
a day ticket (valid for 24 hours, price 8 euros),
or a monthly ticket (valid for 30 days, price 55 euros).
The price of the ticket is deducted from the card. If a previously purchased ticket is still valid, there is no need to buy a new ticket for a transfer. A transfer ticket also does not extend the validity of the previously purchased ticket. Of course, if one boards the bus during the ticket's validity period, one may travel until the bus route's service ends. This does not need to be recorded in the systems. If the card does not have enough loaded money, a ticket cannot be purchased, and traveling on the bus is not possible. To enable purchasing, more money must be loaded onto the card.

In the implementation, it has been decided to use the method System.currentTimeMillis(), which returns the system time in milliseconds, to track time. For example,

the duration of a 2-hour ticket is 100060602 milliseconds,

a 24-hour ticket 1000606024,

and a 30-day ticket 100060602430 milliseconds.

Time can thus be conveniently compared to the expiration time calculated when buying a ticket. It is assumed that all bus clocks are synchronized outside this system using the NTP protocol and any inaccuracy in clock synchronization is to the detriment of the customer.

Task: Model the public transportation payment card system with the described precision using object design principles.

Define classes, methods, and member variables, and all related agreements (routine definitions, class invariants, and encapsulation).

Provide brief justifications for your choices in the verbal descriptions. Pay special attention to whose responsibility each aspect of the plan is, which data types you end up with, and how the system's parts communicate with each other. The description's result is an abstract functional model of the system and is not expected to work together with physical payment terminals, cards, and other systems at this stage.

You can also implement the program if you wish, but just a specification is enough. The implementation could, for example, print descriptions of different operations' functioning on the screen.

## 3. Specifications
### Purpose
The **TravelCard** system models a smart card used in a public transport service such as Föli. It allows users to load money onto their card, purchase time-based tickets (single, day, or monthly), and check if a valid ticket is available for travel. 

### Class overview

**Enum name**: "TicketType"
*Values*: "SINGLE", "DAY", "MONTHLY"
Each enum constant has associated:

* "double price" — the cost of the ticket
* "long durationMillis" — how long the ticket is valid (in milliseconds)

**Class name**: "Ticket"
**Fields**:

* "TicketType type" — the type of ticket purchased
* "long purchaseTimeMillis" — the timestamp of purchase
  **Constructor**: "Ticket(TicketType type)" — initializes the purchase time and stores the type
  **Method**: "boolean isValid()" — returns true if current time is before purchaseTime + duration
  **Method**: "TicketType getType()" — returns the type of ticket

**Class name**: "TravelCard"
**Fields**:

* "double balance" — the card balance
* "Ticket currentTicket" — the currently active ticket (can be null)
  **Constructor**: "TravelCard()* — creates a new card with 0 balance
  **Method**: "void loadMoney(double amount)" — adds to balance if amount > 0
  **Method**: "boolean hasValidTicket()" — returns true if currentTicket is not null and valid
  **Method**: "boolean purchaseTicket(TicketType type)" — charges the card if enough balance and assigns a new ticket

### Data validation rules

* "loadMoney" only accepts positive values; otherwise, no action is taken
* "purchaseTicket" fails (returns false) if balance is insufficient
* "Ticket" must not be created with null type
* "TicketType" values use final to ensure immutability of price and durationMillis

### Routine abstraction and reusability

* "TicketType" enum encapsulates both ticket cost and duration
* "Ticket" class handles all time-related logic (validity, duration check)
* "TravelCard" delegates to Ticket to check if a user can travel
* Logic is separated into classes to follow single-responsibility principle

## 4. Justification of Choices
The system was designed with clear separation of responsibilities across TicketType, Ticket, and TravelCard. Each of them encapsulates its own logic and data to ensure modularity.

"TicketType" is an enum used to define constant values for ticket pricing and duration. By associating each type with a fixed price (double) and durationMillis (long), the system avoids repeated logic and ensures immutability. Using long for time-related values provides millisecond-level precision and prevents rounding issues that could occur with floating-point types, which the exercise requires.

"Ticket" represents a purchased ticket. It stores the purchase time and the TicketType selected. Its responsibility is to determine whether it is still valid based on the current system time and the duration associated with the ticket type. The validity check is encapsulated in the isValid() method, which allows external classes like TravelCard to simply delegate this logic.

"TravelCard" represents a user’s card balance and current ticket status. It controls operations such as loading money, purchasing tickets, and checking ticket validity. The card does not handle time or pricing logic directly, as those responsibilities belong to the Ticket and TicketType respectively. This division makes the code easier to maintain and extend to support additional ticket types or payment strategies.
