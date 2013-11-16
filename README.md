GuitarChordGUI
==============

Guitar fret board simulator that displays and determines chords.

All code is in 'src' folder

'FretboardGUI.java' is the main class, and contains the 'main' method. This class builds the GUI, and works with the classes 'Chord.java' and 'Note.java' to store and interpret data input from the user's GUI interactions. There are also several inner classes within 'Fretboard.java': 'MouseListener' which is a custom MouseAdapter class used for button actions; 'LabelListener' which is a custom MouseAdapter class used for text actions; and 'MajorListener' and 'MinorListener' which are ActionListener classes used for menu actions.

'Note.java' interprets actions sent by buttons in the GUI to determine the note name of the button pressed.

'Chord.java' is essentially an ArrayList used to store the notes which are determined by the buttons pressed on the GUI. It also determines whether or not the notes it holds makes up a chord. There is an inner class called 'MyArrayList' which has a method I thought should be in Oracle's ArrayList class called containsAnyDisjoint, which determines whether two sets contain any disjoint elements.
