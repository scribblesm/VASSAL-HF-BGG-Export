# VASSAL-HF-BGG-Export
High Frontiers Extension for VASSAL to make play by forums easier on board game geek.

Adds a button to the Patents map to export the list of all cards on a decks to a text file.
Each line can be pasted in the board game geek forums as a roll to choose the next random card.

For example to perform the inspiration event, remove the top card from each deck.  Then export the file and paste each decklist as a seperate roll.


Code notes:

    Each game piece that is part of a deck will be exported to file.
    Items in the "offboard" area will be omited.
    Rough check for 255 character limit on the export.
    Limits 14 character strings which works for current high frontiers names.
    
Wish list:
	The name limit should be updated dynamically or less crudely.
	Add option to export to clipboard instead of file.
	Add import to move chosen card from BGG to the top of the deck.
