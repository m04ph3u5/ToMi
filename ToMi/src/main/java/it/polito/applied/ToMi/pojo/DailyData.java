package it.polito.applied.ToMi.pojo;

import java.util.ArrayList;
import java.util.List;

public class DailyData {
	
	private List<DailyInfo> tomi;
	private List<DailyInfo> mito;
	
	private DailyData(){
		tomi = new ArrayList<DailyInfo>();
		mito = new ArrayList<DailyInfo>();
	}
	
	public void addInfoTomi(DailyInfo d){
		tomi.add(d);
	}
	
	public void addInfoMito(DailyInfo d){
		mito.add(d);
	}
	
	public List<DailyInfo> getTomi() {
		return tomi;
	}
	public void setTomi(List<DailyInfo> tomi) {
		this.tomi = tomi;
	}
	public List<DailyInfo> getMito() {
		return mito;
	}
	public void setMito(List<DailyInfo> mito) {
		this.mito = mito;
	}
	
	

}
