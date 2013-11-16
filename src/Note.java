public class Note {
	private String fret, note;
	
	public Note() {
		fret="";
		note="";
	}
	
	public Note(String f) {
		fret=f;
		setNote();
	}
	
	public Note(String f, String n) {
		fret=f;
		note=n;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote() {
		switch(fret) {
			case "10":
			case "112":
			case "27":
			case "32":
			case "49":
			case "55":
			case "60":
			case "612": note="E"; break;
			case "11":
			case "28":
			case "33":
			case "410":
			case "56":
			case "61": note="F"; break;
			case "12":
			case "29":
			case "34":
			case "411":
			case "57":
			case "62": note="F#/Gb"; break;
			case "13":
			case "210":
			case "35":
			case "40":
			case "412":
			case "58":
			case "63": note="G"; break;
			case "14":
			case "211":
			case "36":
			case "41":
			case "59":
			case "64": note="G#/Ab"; break;
			case "15":
			case "20":
			case "212":
			case "37":
			case "42":
			case "510":
			case "65": note="A"; break;
			case "16":
			case "21":
			case "38":
			case "43":
			case "511":
			case "66": note="A#/Bb"; break;
			case "17":
			case "22":
			case "39":
			case "44":
			case "50":
			case "512":
			case "67": note="B"; break;
			case "18":
			case "23":
			case "310":
			case "45":
			case "51":
			case "68": note="C"; break;
			case "19":
			case "24":
			case "311":
			case "46":
			case "52":
			case "69": note="C#/Db"; break;
			case "110":
			case "25":
			case "30":
			case "312":
			case "47":
			case "53":
			case "610": note="D"; break;
			case "111":
			case "26":
			case "31":
			case "48":
			case "54":
			case "611": note="D#/Eb"; break;
		}
	}
}
