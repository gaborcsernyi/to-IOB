
public class Store_tags {
	public String tag;
	public int start;
	public int end;
	
	public Store_tags(String tag, int start, int end){
		this.tag=tag;
		this.start=start;
		this.end=end;
	}
	
	public String toString(){
		return "tag: " + tag + "\tstart: " + start + "\tend: " + end;
	}
	
}
