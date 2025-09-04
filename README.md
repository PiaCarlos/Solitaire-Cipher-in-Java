# Solitaire Cipher in Java

## Overview
A Java implementation of the **Solitaire Cipher** (Pontifex cipher), a card-based stream cipher for encrypting and decrypting messages.  
The project features a custom **circular doubly linked list** to represent a deck of cards and efficiently generate pseudorandom keystreams.

## Features
- Encode and decode messages using the Solitaire Cipher.  
- Deck manipulation: joker moves, triple cut, count cut.  
- Efficient linked-list data structure for deck operations.

## Features
- **Cryptography Concepts**
  - Implements the Solitaire Cipher for message encoding/decoding.
  - Demonstrates stream cipher design using pseudorandom keystreams.
- **Card Deck Data Structure**
  - Custom **circular doubly linked list** to represent a deck of cards.
  - Supports deck operations such as joker moves, triple cut, count cut, and keystream generation.
- **Message Processing**
  - Strips non-letter characters and normalizes text.
  - Encrypts and decrypts alphabetic messages with shared keys.
- **Efficiency**
  - Emphasizes algorithm performance, not just correctness.
  - Uses efficient linked-list manipulation for deck operations.
