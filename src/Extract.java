import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
//import java.util.Vector;
//import java.util.Collections;
public class Extract {
	public static void main(String[] args) {
		File inputdir = new File(args[0]);
		//System.out.println("### " + inputdir + " ###");
		File[] files = inputdir.listFiles();
		
		try{
			FileOutputStream out1 = new FileOutputStream(args[0] + "/new_sel-NE.out");
		    OutputStreamWriter tagfile = new OutputStreamWriter(out1,"UTF8");
		    
			ArrayList<Store_tags> tgroup = new ArrayList<Store_tags>();
			ArrayList<String> filelist = new ArrayList<String>();
			
			
			for (int fls=0; fls<files.length; fls++){
				String currfile = files[fls].getAbsolutePath();
				filelist.add(currfile);
			}
			
			Collections.sort(filelist);
			
			for (int fctr=0; fctr<filelist.size(); fctr++){
				String annotated_file = filelist.get(fctr);
				if (annotated_file.endsWith(".annotation")){
					//System.out.println(annotated_file);
					String raw_text_file = annotated_file.substring(0, annotated_file.indexOf(".annotation"));
					BufferedReader ant = new BufferedReader(new FileReader(annotated_file));
					FileInputStream fis = new FileInputStream(raw_text_file);
					BufferedReader raw = new BufferedReader (new InputStreamReader (fis,"UTF-8"));
					//BufferedReader raw = new BufferedReader(new FileReader(raw_text_file));
					String s="";
					tgroup.clear();
// PROCESSING OF THE MWE-ANNOTATED FILE		(annotated_file)
					int cnt=0;
					//System.out.println(raw_text_file);
				    while ( (s = ant.readLine()) != null ){
				    	++cnt;
				    	if (cnt>2){
				    		String[] split_tag_line;
				    		split_tag_line = s.split("\t");
				    	   	Store_tags st = new Store_tags(split_tag_line[0], Integer.parseInt(split_tag_line[1]), Integer.parseInt(split_tag_line[2]));
				    	   	tgroup.add(st);
				    	    //System.out.println(st);
				    	}
				    }
				    
// PROCESSING OF THE RAW TEXT FILE			(raw_text_file)	    
				    String text="";
				    int cnt2=0;
				    while ( (s = raw.readLine()) != null ){
				    	if (cnt2>0) 
				    		text= text + " " + s;
				    	else 
				    		text = text + s;
				    	cnt2++;
				    	//System.out.println(s);
				    	//System.out.println("\n#################################################\n");
				    }
				    System.out.println(text);
				    for (int i=0; i<tgroup.size(); ++i){
				    	String tag = tgroup.get(i).tag;
				    	int start = tgroup.get(i).start;
				    	int end = tgroup.get(i).end;
				    	if (tag.equals("MWE_COMPOUND_NOUN")){
				    		;
				    		//System.out.println(text.substring(start, end) + "\t" + start + "\t" + end);
				    	}
				    }
				    
//PROCESSING OF THE POS-TAGGED FILE			(raw_text_file + ".pos")			    
				    FileInputStream fis2 = new FileInputStream(raw_text_file + ".pos");
					BufferedReader raw2 = new BufferedReader (new InputStreamReader (fis2, "UTF-8"));
					String s2="";
					int cnt3=0;
					//System.out.println(raw_text_file);
					String[] word_pos;
					int prevstapos = 0;
				    while ( (s2 = raw2.readLine()) != null ){
				    	String tmp_tosplit = s2.substring(2,s2.length()-2);
				    	word_pos = tmp_tosplit.split("\\), \\(");
				    	String tmp_to_file = "";
				    	for (int wpc=0; wpc<word_pos.length; ++wpc){
				    		String[] wp_sep;
				    		wp_sep = word_pos[wpc].split(", ");
				    		String wform = wp_sep[0].substring(1, wp_sep[0].length()-1);
				    		String wpos = wp_sep[1].substring(1, wp_sep[1].length()-1);
				    		//System.out.println(wform + "\t" + wpos);
				    		int stapos = text.indexOf(wform, prevstapos);
				    		int stopos = stapos + wform.length();
				    		if (stapos != -1) {
				    			String erf = text.substring(stapos, stopos);
				    			//System.out.println("" + erf + "\t" + wpos + "\t"+ stapos + "\t" + stopos);
				    			String into_file = "";
// CREATIN & FORMATTING OUTPUT, ADDING SPECIAL TAGS 
				    			for (int i=0; i<tgroup.size(); ++i){
				    				Store_tags stcur = tgroup.get(i);
				    				//if (stcur.tag.equals("MWE_COMPOUND_NOUN")){
				    				if (stcur.tag.equals("NE_PER") || stcur.tag.equals("NE_ORG") || stcur.tag.equals("NE_LOC") || stcur.tag.equals("NE_MISC")){
				    					if (stapos >= stcur.start && stopos <= stcur.end){
				    						if (stapos == stcur.start){
				    							into_file = wform + "\t" + wpos + "\t"+ "B-NP";
				    							//System.out.println(wform + "\t" + wpos + "\t"+ "CN-B");
				    						}
				    						else {
				    							into_file = wform + "\t" + wpos + "\t"+ "I-NP";
				    							//System.out.println(wform + "\t" + wpos + "\t"+ "CN-I");
				    						}
				    					}
				    				}
				    			}
				    			if (into_file.equals("")){
				    				into_file = wform + "\t" + wpos + "\t"+ "O";
				    			}
				    			tmp_to_file = tmp_to_file + into_file + "\n";
				    			//tagfile.write(into_file + "\n");
				    			prevstapos = stopos;

				    		}
				    		++cnt3;
				    	}	
				    	//System.out.println(tmp_to_file + "\n ########################xx ");
				    	if (tmp_to_file.indexOf("B-NP")!=-1)
				    		tagfile.write(tmp_to_file);
				    	//tagfile.write("\n");

				    }
				    //System.out.println(annotated_file + ", " + raw_text_file + ", " + raw_text_file + ".pos" + " processed");
				    //tagfile.write(annotated_file + ", " + raw_text_file + ", " + raw_text_file + ".pos" + " processed\n");
				    System.out.println("["+ raw_text_file + "]number of POS-tagged items in file: " + cnt3);			    
				    
				    fis.close();
					fis2.close();
					//break;
				}
				tagfile.flush();
			}
			tagfile.close();
		}
		
		catch (FileNotFoundException exc){
			System.err.println("File could not be found");
			System.exit(1);
		}
			    
		catch (IOException exc){
			System.err.println("I/O error");
			exc.printStackTrace();
			System.exit(2);
		}
			    
	}	
	
}
