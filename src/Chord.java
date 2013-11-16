import java.util.*;

public class Chord {
	private class MyArrayList<T> extends ArrayList<T> {
		public MyArrayList() {
			super();
		}
		
		public boolean containsAnyDisjoint(List<T> sub, List<T> whole) {
			List<T> disjoint=new ArrayList<T>();
			for(int i=0; i<whole.size(); i++)
				Collections.addAll(disjoint, whole.get(i));
			disjoint.removeAll(sub);
			
			boolean b=false;
			for(int i=0; i<this.size(); i++) 
				for(int j=0; j<disjoint.size(); j++)
					if(this.get(i).equals((disjoint).get(j)))
						b=true;
			return b;
		}
	}
	private String name;
	private MyArrayList<String> chord;
	private List<String> notes=Arrays.asList("C","C#/Db","D","D#/Eb","E","F",
						  "F#/Gb","G","G#/Ab","A","A#/Bb","B"),
						
						 cMajor=Arrays.asList("C", "E", "G"),
			 			 csMajor=Arrays.asList("C#/Db", "F", "G#/Ab"),
			 			 dMajor=Arrays.asList("D", "F#/Gb", "A"),
			 			 dsMajor=Arrays.asList("D#/Eb", "G", "A#/Bb"),
			 			 eMajor=Arrays.asList("E", "G#/Ab", "B"),
			 			 fMajor=Arrays.asList("F", "A", "C"),
			 			 fsMajor=Arrays.asList("F#/Gb", "A#/Bb", "C#/Db"),
			 			 gMajor=Arrays.asList("G", "B", "D"),
			 			 gsMajor=Arrays.asList("G#/Ab", "C", "D#/Eb"),
			 			 aMajor=Arrays.asList("A", "C#/Db", "E"),
			 			 asMajor=Arrays.asList("A#/Bb", "D", "F"),
			 			 bMajor=Arrays.asList("B", "D#/Eb", "F#/Gb"),
			 			 
			 			 cMinor=Arrays.asList("C", "D#/Eb", "G"),
			 			 csMinor=Arrays.asList("C#/Db", "E", "G#/Ab"),
			 			 dMinor=Arrays.asList("D", "F", "A"),
			 			 dsMinor=Arrays.asList("D#/Eb", "F#/Gb", "A#/Bb"),
			 			 eMinor=Arrays.asList("E", "G", "B"),
			 			 fMinor=Arrays.asList("F", "G#/Ab", "C"),
			 			 fsMinor=Arrays.asList("F#/Gb", "A", "C#/Db"),
			 			 gMinor=Arrays.asList("G", "A#/Bb", "D"),
			 			 gsMinor=Arrays.asList("G#/Ab", "B", "D#/Eb"),
			 			 aMinor=Arrays.asList("A", "C", "E"),
			 			 asMinor=Arrays.asList("A#/Bb", "C#/Db", "F"),
			 			 bMinor=Arrays.asList("B", "D", "F#/Gb");
	
	public Chord() {
		chord=new MyArrayList<String>();
	}
	
	public Chord(MyArrayList<String> notes) {
		chord=new MyArrayList<String>();
		for(int i=0; i<notes.size(); i++)
			Collections.addAll(chord, notes.get(i));
	}
	
	public void addNote(String note) {
		chord.add(note);
	}
	
	public void removeNote(String note) {
		chord.remove(note);
	}
	
	public String toString() {
		return chord.toString();
	}
	
	public String getChord() {
		return name;
	}
	
	public boolean isChord() {
		///////////////////////////Major Chords/////////////////////////////////////////
		if(chord.containsAll(cMajor)&&(!chord.containsAnyDisjoint(cMajor,notes)))
			name="C Major";
		else if(chord.containsAll(csMajor)&&(!chord.containsAnyDisjoint(csMajor,notes)))
			name="C#/Db Major";
		else if(chord.containsAll(dMajor)&&(!chord.containsAnyDisjoint(dMajor,notes)))
			name="D Major";
		else if(chord.containsAll(dsMajor)&&(!chord.containsAnyDisjoint(dsMajor,notes)))
			name="D#/Eb Major";
		else if(chord.containsAll(eMajor)&&(!chord.containsAnyDisjoint(eMajor,notes)))
			name="E Major";
		else if(chord.containsAll(fMajor)&&(!chord.containsAnyDisjoint(fMajor,notes)))
			name="F Major";
		else if(chord.containsAll(fsMajor)&&(!chord.containsAnyDisjoint(fsMajor,notes)))
			name="F#/Gb Major";
		else if(chord.containsAll(gMajor)&&(!chord.containsAnyDisjoint(gMajor,notes)))
			name="G Major";
		else if(chord.containsAll(gsMajor)&&(!chord.containsAnyDisjoint(gsMajor,notes)))
			name="G#/Ab Major";
		else if(chord.containsAll(aMajor)&&(!chord.containsAnyDisjoint(aMajor,notes)))
			name="A Major";
		else if(chord.containsAll(asMajor)&&(!chord.containsAnyDisjoint(asMajor,notes)))
			name="A#/Bb Major";
		else if(chord.containsAll(bMajor)&&(!chord.containsAnyDisjoint(bMajor,notes)))
			name="B Major";
		///////////////////////////Minor Chords//////////////////////////////////////////
		else if(chord.containsAll(cMinor)&&(!chord.containsAnyDisjoint(cMinor,notes)))
			name="C Minor";
		else if(chord.containsAll(csMinor)&&(!chord.containsAnyDisjoint(csMinor,notes)))
			name="C#/Db Minor";
		else if(chord.containsAll(dMinor)&&(!chord.containsAnyDisjoint(dMinor,notes)))
			name="D Minor";
		else if(chord.containsAll(dsMinor)&&(!chord.containsAnyDisjoint(dsMinor,notes)))
			name="D#/Eb Minor";
		else if(chord.containsAll(eMinor)&&(!chord.containsAnyDisjoint(eMinor,notes)))
			name="E Minor";
		else if(chord.containsAll(fMinor)&&(!chord.containsAnyDisjoint(fMinor,notes)))
			name="F Minor";
		else if(chord.containsAll(fsMinor)&&(!chord.containsAnyDisjoint(fsMinor,notes)))
			name="F#/Gb Minor";
		else if(chord.containsAll(gMinor)&&(!chord.containsAnyDisjoint(gMinor,notes)))
			name="G Minor";
		else if(chord.containsAll(gsMinor)&&(!chord.containsAnyDisjoint(gsMinor,notes)))
			name="G#/Ab Minor";
		else if(chord.containsAll(aMinor)&&(!chord.containsAnyDisjoint(aMinor,notes)))
			name="A Minor";
		else if(chord.containsAll(asMinor)&&(!chord.containsAnyDisjoint(asMinor,notes)))
			name="A#/Bb Minor";
		else if(chord.containsAll(bMinor)&&(!chord.containsAnyDisjoint(bMinor,notes)))
			name="B Minor";
		else
			name="";
		
		if(!name.equals(""))
			return true;
		else return false;
	}
}
