package genDataNOapplication;

import genDataNOapplication.model.ConfigurationModel;
import javafx.concurrent.Task;
 

public class Controller extends Task<Integer> {
	
	protected ConfigurationModel configuration;

	@Override
	protected Integer call() throws Exception {		
		return GenDataNO.main(configuration);
	}
	
	public void setConfiguration(ConfigurationModel configuration) { this.configuration = configuration; }
}
