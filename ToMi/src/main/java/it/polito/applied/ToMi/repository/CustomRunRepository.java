package it.polito.applied.ToMi.repository;

import java.util.List;

import it.polito.applied.ToMi.pojo.BusStop;
import it.polito.applied.ToMi.pojo.DailyInfo;

public interface CustomRunRepository {
	public void updateRun(String idRun, String idLine, String day, BusStop getIn, BusStop getOut);
	public List<DailyInfo> getDailyInfoTomi();
	public List<DailyInfo> getDailyInfoMito();

}
