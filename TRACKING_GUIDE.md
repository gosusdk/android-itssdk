# SDK Tracking In-app Events Guide 

## Overview

* [In-app events—Overview](#in-app-events-overview)
* [Lifecycle events](#lifecycle-events)
* [Recommended In-app events in Gaming](#recommended-in-app-events-in-gaming)

## In-app events—Overview

Recording in-app events helps analyze user behavior, assess user value (LTV), and campaign performance (ROI). When users perform actions like registration, completing tutorials, adding items to a cart, or making purchases, event data provides insights to measure KPIs.

In-app events include an event name and can have event parameters that add context and details (e.g., type of transaction, destination, revenue). Implementing in-app events is mandatory for post-installation analysis.

## Lifecycle events

SDK automatically tracks the following application lifecycle events:

- **Application Installed**
  - This event is tracked when the application is installed on the user's device.
- **Application Opened**
  - This event is tracked when the user opens the application.
- **Application Updated**
  - This event is tracked when the application is updated to a new version.
- **Application Backgrounded**
  - This event is tracked when the application is sent to the background.

## Recommended In-app Events in Gaming

### 1. Complete Registration

This function tracks when a user completes the registration process.

**Parameters:**
- `userId`: User ID

```java
ITSSdk.getInstance().completeRegistration(String userId);
```
---

### 2. Login

This function tracks when a user login.

**Parameters:**
- `userId`: User ID
- `userName`: Username
- `userEmail`: The email of the user.

```java
ITSSdk.getInstance().login(String userId, String userName, String userEmail);
```
---
### 3. Create New Character

This event tracks when a user creates a new character in the game.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `characterName`: The name of the character
- `serverInfo`: Server information

```java
ITSSdk.getInstance().createNewCharacter(
    String userId, 
    String characterId, 
    String characterName, 
    String serverInfo
);
```
---
### 4. Enter Game

This event tracks when a user enters the game with a specific character.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `characterName`: The name of the character
- `serverInfo`: Server information

```java
ITSSdk.getInstance().enterGame(
    String userId, 
    String characterId, 
    String characterName, 
    String serverInfo
);
```

---

### 5. Start Tutorial

This event tracks when a user starts the tutorial.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `characterName`: The name of the character
- `serverInfo`: Server information

```java
ITSSdk.getInstance().startTutorial(
    String userId, 
    String characterId, 
    String characterName, 
    String serverInfo
);
```

---

### 6. Complete Tutorial

This event tracks when a user completes the tutorial.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `characterName`: The name of the character
- `serverInfo`: Server information

```java
ITSSdk.getInstance().completeTutorial(
    String userId, 
    String characterId, 
    String characterName, 
    String serverInfo
);
```

---

### 7. Checkout

This event tracks when a user initiates a checkout process.

**Parameters:**
- `orderId`: The unique order ID
- `userId`: User ID
- `characterId`: The ID of the character making the purchase
- `serverInfo`: Server information
- `productId`: The ID of the product
- `brand`: Brand/vendor of the product
- `quantity`: Quantity of items
- `category`: Product category
- `price`: Price per item
- `currency`: Currency code (e.g., "USD", "VND")
- `revenue`: Total revenue amount

```java
ITSSdk.getInstance().checkout(
    String orderId,
    String userId,
    String characterId,
    String serverInfo,
    String productId,
    String brand,
    Integer quantity,
    String category,
    Float price,
    String currency,
    Float revenue
);
```
---

### 8. Purchase

This event tracks when a user completes a purchase.

**Parameters:**
- `orderId`: The unique order ID
- `userId`: User ID
- `characterId`: The ID of the character making the purchase
- `serverInfo`: Server information
- `productId`: The ID of the product purchased
- `brand`: Brand/vendor of the product
- `quantity`: Quantity purchased
- `category`: Product category
- `price`: Price per item
- `currency`: Currency code (e.g., "USD", "VND")
- `revenue`: Total revenue from the purchase

```java
ITSSdk.getInstance().purchase(
    String orderId,
    String userId,
    String characterId,
    String serverInfo,
    String productId,
    String brand,
    Integer quantity,
    String category,
    Float price,
    String currency,
    Float revenue
);
```
---

### 9. Level Up

This event tracks when a character levels up in the game.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `serverInfo`: Server information
- `level`: The new level achieved

```java
ITSSdk.getInstance().levelUp(
    String userId,
    String characterId,
    String serverInfo,
    Integer level
);
```

---

### 10. VIP Level Up

This event tracks when a user achieves a new VIP level.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `serverInfo`: Server information
- `vipLevel`: The new VIP level achieved

```java
ITSSdk.getInstance().vipUp(
    String userId,
    String characterId,
    String serverInfo,
    Integer vipLevel
);
```

---

### 11. Use Item

This event tracks when a user uses an in-game item.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `serverInfo`: Server information
- `itemId`: The ID of the item used
- `quantity`: The quantity of items used

```java
ITSSdk.getInstance().useItem(
    String userId,
    String characterId,
    String serverInfo,
    String itemId,
    Integer quantity
);
```

---

### 12. Track Activity Result

This event tracks the result of a specific in-game activity or quest.

**Parameters:**
- `userId`: User ID
- `characterId`: The ID of the character
- `serverInfo`: Server information
- `activityId`: The ID of the activity/quest
- `activityResult`: The result of the activity (e.g., "completed", "failed", "abandoned")

```java
ITSSdk.getInstance().trackActivityResult(
    String userId,
    String characterId,
    String serverInfo,
    String activityId,
    String activityResult
);
```

---

### 13. Track Custom Event

This event tracks custom events defined by developers for specific game mechanics.

**Parameters:**
- `eventName`: The name of the custom event
- `trackingProperty`: Custom properties object containing event data

```java
ITSSdk.getInstance().trackCustomEvent(
    String eventName,
    ItsTrackingProperty trackingProperty
);
```

**Example:**

```java
ItsTrackingProperty properties = new ItsTrackingProperty();
properties.put("event_type", "boss_defeated");
properties.put("boss_name", "Dragon King");
properties.put("time_taken", 120);
properties.put("difficulty", "hard");

ITSSdk.getInstance().trackCustomEvent("boss_battle", properties);
```
---

### 14. Logout

This event tracks when a user logs out of the application.

```java
ITSSdk.getInstance().logout();
```
---
