package it.polito.applied.ToMi.pojo;

public class BusRunDetector {
	
	private BusStop first;
	private BusStop last;
	
	public BusStop getFirst() {
		return first;
	}
	public void setFirst(BusStop first) {
		this.first = first;
	}
	public BusStop getLast() {
		return last;
	}
	public void setLast(BusStop last) {
		this.last = last;
	}
	
	public Integer getIdProgFirst(){
		if(first!=null)
			return first.getIdProg();
		else
			return null;
	}
	
	public Integer getIdProgLast(){
		if(last!=null)
			return last.getIdProg();
		else
			return null;
	}
	
	public boolean isValid(){
		if(first!=null && last!=null)
			return true;
		else
			return false;
	}
}
