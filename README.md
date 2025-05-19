---

# 🏷️ Vickrey Auction Kata

A Kotlin implementation of a **Vickrey Auction** (Second-Price Sealed-Bid Auction) where multiple bidders place secret bids, and the highest bidder wins but pays the second-highest bid.

---

## 📌 **Problem Statement**

In a Vickrey Auction:

* Each bidder submits their bids privately—others cannot see them.
* There is a **reserve price**, which is the minimum acceptable bid.
* The **highest valid bid** that meets or exceeds the reserve price **wins**.
* The winning price is the **second-highest valid bid**.
* If there is no other valid bid, the **reserve price** is used.

---

## ✅ **Example**

```
Reserve price: 100

Bidders:
  - A: [110, 130]
  - B: []
  - C: [125]
  - D: [105, 115, 90]
  - E: [132, 135, 140]

Result:
  - Winner: E (highest bid of 140)
  - Winning price: 130 (next highest valid bid from another bidder, A's bid)
```

---

## 🔍 **Core Logic**

The main logic resides in `DetermineAuctionWinnerUseCase.kt`:

1. **Collect all valid bids**:

   * Bids that are above or equal to the reserve price.
2. **Sort them in descending order**.
3. **Determine the winner**:

   * The first occurrence of the highest bid wins.
4. **Calculate the winning price**:

   * The highest valid bid from another bidder, or fall back to the reserve price if none exist.

---

## 🔄 **Edge Cases Handled**

* ✅ No valid bids → `null` is returned.
* ✅ Only one valid bid → Winner pays the **reserve price**.
* ✅ Multiple highest bids (ties) → The **first bidder in order** is declared the winner.
* ✅ All bids below reserve price → `null` is returned.

---

## 🧪 **Test Suite**

The test suite is comprehensive and covers:

1. **Basic Auction Flow**
2. **Edge Cases**:

   * Empty list of bidders
   * Single bidder
   * Multiple bids from one bidder
   * Ties in highest bids
   * All bids below the reserve price
