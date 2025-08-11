# Minesweeper Game (Java CLI)

A **command-line Minesweeper game** implemented in **Java**.  
Players can choose the grid size and number of mines, then uncover cells until they either win or hit a mine.

---

## 🎯 Features
- Custom **grid size** and **mine count** (max 35% of total cells)
- Random **mine placement**
- **Automatic reveal** of empty areas (cells with 0 adjacent mines)
- Clear **CLI display** after each move
- Win/loss detection
- Input validation for better user experience

---

## 📦 Requirements
- Java **17 or higher**
- Terminal / Command Prompt/Any Java development tool
- maven 3.10.1 or above 


---

## ⚙️ How to Compile & Run

```bash
# Compile
mvn clean compile 

# Run
mvn exec:java

#Run Test
mvn test
```

## 📜 Example Gameplay
✅ Win Scenario

Welcome to Minesweeper!

Enter the size of the grid (e.g. 4 for a 4x4 grid): 4

Enter the number of mines to place on the grid (maximum is 5): 3

Here is your minefield:
````
1 2 3 4
A _ _ _ _
B _ _ _ _
C _ _ _ _
D _ _ _ _
````
Select a square to reveal (e.g. A1): D4  

This square contains 0 adjacent mines.

Here is your updated minefield:
````
1 2 3 4
A _ _ 2 0
B _ _ 2 0
C _ 2 1 0
D _ 1 0 0
````

Select a square to reveal (e.g. A1): A1  

This square contains 0 adjacent mines.

Here is your updated minefield:
````
1 2 3 4
A 0 0 2 0
B 0 0 2 0
C 0 2 1 0
D 0 1 0 0
````

🎉 Congratulations, you have won the game!
